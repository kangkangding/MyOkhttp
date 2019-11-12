package com.img.dkk.myokhttp.engine;

import android.os.Handler;
import android.os.Looper;
import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by  dingkangkang on 2019/10/16
 * email：851615943@qq.com
 */
public class CovertCallBackListener<T> implements CallBackListener {

    private Class<T> responseClazz;
    private IJsonTranserListener jsonTranserListener;
    private Handler handler = new Handler(Looper.getMainLooper());

    public CovertCallBackListener(Class<T> clazz,IJsonTranserListener jsonTranserListener) {
        this.responseClazz = clazz;
        this.jsonTranserListener = jsonTranserListener;
    }

    @Override public void onSuccess(InputStream inputStream) {

        String response = null;
        try {
            response = getContent(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final T t = JSON.parseObject(response, responseClazz);
        handler.post(new Runnable() {
            @Override public void run() {
                jsonTranserListener.onSuccess(t);
            }
        });

    }

    private String getContent(InputStream inputStream) throws IOException {

        //获取响应状态行
        String stateline = getLine(inputStream);
        //获取服务器响应头
        Map<String,String> headers = getHeaders(inputStream);

        int contentLength = -1;
        if(headers.containsKey("Content-Length")){
            contentLength = Integer.parseInt(headers.get("Content-Length"));
        }

        boolean isChunked = false;

        if(headers.containsKey("Transfer-Encoding")){
            isChunked = headers.get("Transfer-Encoding").equalsIgnoreCase("chunked");
        }

        String response = null;
        if(contentLength > 0){
            byte[] bodyBytes = getBody(inputStream,contentLength);
            response = new String(bodyBytes,"utf-8");
        }else if(isChunked){

            response = readChunk(inputStream,contentLength);
        }

        return response;
    }

    //获取行
    public String getLine(InputStream inputStream) throws IOException {

        ByteBuffer byteBuffer = ByteBuffer.allocate(10*1024);
        byteBuffer.clear();
        byteBuffer.mark();
        byte b;

        while ((b = (byte) inputStream.read()) != -1){
            byteBuffer.put(b);
            if(b==10){
                break;
            }
        }
        byte[] bytes = new byte[byteBuffer.position()];
        byteBuffer.reset();
        byteBuffer.get(bytes);
        if(bytes.length >0){
            inputStream.close();
            return new String(bytes);
        }

        return null;
    }

    //获取分块数据
    private String readChunk(InputStream inputStream, int length) throws IOException {

        int len = -1;
        boolean isEmptyData = false;
        StringBuffer chunked = new StringBuffer();
        while(true){
            if(len < 0){
                //获取块的长度
                String line = getLine(inputStream);
                length += line.length();
                //去掉/r/n
                line = line.substring(0,line.length() - 2);
                //获得长度 16进制字符串转成10进制整型
                len = Integer.valueOf(line,16);

                //如果读到的是0，则再读一个/r/n就结束了
                isEmptyData = len == 0;
            }else{
                length += (len + 2);
                byte[] bytes = readBytes(inputStream,len + 2);//读的时候，加上2，/r/n
                chunked.append(new String(bytes,"UTF-8"));
                len = -1;
                if(isEmptyData){
                    return chunked.toString();
                }
            }
        }
    }

    // 根据长度读取字节数据
    public byte[] readBytes(InputStream is,int length) throws IOException{
        byte[] bytes = new byte[length];
        int readNum = 0;
        while(true){
            readNum = is.read(bytes,readNum,length - readNum);
            if(readNum == length){
                return bytes;
            }
        }
    }

    //获取消息体
    private byte[] getBody(InputStream inputStream, int contentLength) throws IOException {

        byte[] bytes = new byte[contentLength];

        while (true){
            inputStream.read(bytes,0,contentLength);
            return bytes;
        }

    }

    private Map<String, String> getHeaders(InputStream inputStream) throws IOException {

        Map<String,String> headMaps = new HashMap<>();
        String line;
        while ((line = getLine(inputStream)) != null){

            if("\r\n".equals(line)){
                break;
            }
            int index = line.indexOf(":");

            if(index > 0){
                String key = line.substring(0,index);

                String value = line.substring(index+2,line.length() -2);

                headMaps.put(key,value);
            }
        }

        return headMaps;
    }

    @Override public void onFailure() {

    }
}
