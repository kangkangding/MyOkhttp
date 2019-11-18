package com.img.dkk.myokhttp.engine;

import android.util.ArrayMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;

/**
 * Created by  dingkangkang on 2019/10/22
 * email：851615943@qq.com
 */
public class CreateSocket {
    Socket socket;
    InputStream inputStream;
    OutputStream outputStream;
    HttpUrl httpUrl;

    static final String CRLF = "\r\n";
    static final int CR = 13;//回车的ASCII码
    static final int LF = 10;//换行的ASCII码
    static final String SPACE = " ";//一个空格
    static final String HTTP_VERSION = "HTTP/1.1";//http的版本信息
    static final String COLON = ":";//冒号

    public void setHttpUrl(HttpUrl httpUrl) {
        this.httpUrl = httpUrl;
    }

    public HttpUrl getHttpUrl() {
        return httpUrl;
    }

    public void connectSocket(){

        SocketAddress socketAddress = new InetSocketAddress(httpUrl.getHost(),httpUrl.getPort());

        socket = new Socket();

        try {
            socket.connect(socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeRequest() throws IOException{

        StringBuffer sb = new StringBuffer();
        //GET /v3/weather/weatherInfo?key=064a7778b8389441e30f91b8a60c9b23&city=%25E6%25B7%25B1%25E5%259C%25B3 HTTP/1.1
        sb.append("GET");// GET
        sb.append(SPACE);//一个空格
        sb.append(httpUrl.file);// "/v3/weather/weatherInfo?key=064a7778b8389441e30f91b8a60c9b23&city=%25E6%25B7%25B1%25E5%259C%25B3"
        sb.append(SPACE);//一个空格
        sb.append(HTTP_VERSION);// HTTP/1.1
        sb.append(CRLF);//一个回车换行

        //TODO 拼接请求头

        Map<String,String> headers = new ArrayMap<>();

        headers = httpUrl.getHeaders();

        //headers.put("Host",httpUrl.getHost());
        //headers.put("Connection", "Keep-Alive");

        for(Map.Entry<String,String> entry : headers.entrySet()){
            //Content-Type:	application/json;charset=UTF-8

            sb.append(entry.getKey());//map中的key值,例如：Content-Type
            sb.append(COLON);//一个冒号
            sb.append(SPACE);//一个空格
            sb.append(entry.getValue());//map中value值，例如：application/json;charset=UTF-8
            sb.append(CRLF);//最后面跟一个回车和换行
        }

        sb.append(CRLF);//请求头最后，还需要跟一格回车和换行
        //
        ////TODO 拼接请求体
        //RequestBody requestBody = request.getRequestBody();
        //if(null != requestBody){
        //    sb.append(requestBody.getBody());
        //}

        outputStream.write(sb.toString().getBytes());
        outputStream.flush();
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
