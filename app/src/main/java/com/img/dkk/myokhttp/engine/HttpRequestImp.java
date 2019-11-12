package com.img.dkk.myokhttp.engine;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

/**
 * Created by  dingkangkang on 2019/10/16
 * email：851615943@qq.com
 */
public class HttpRequestImp implements HttpRequest {

    private byte[] data;
    private String url;
    private CallBackListener callBackListener;
    private HttpUrl httpUrl;

    @Override public void setData(byte[] data) {
        this.data = data;
    }

    @Override public void setUrl(String url) {
        this.url = url;

        httpUrl = new HttpUrl(url);
    }

    @Override public void setListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    @Override public void excute() {
        try {

            CreateSocket createSocket = new CreateSocket();
            createSocket.connectSocket(httpUrl.getHost(),httpUrl.getPort());

            createSocket.writeRequest(httpUrl);

            InputStream inputStream = createSocket.getInputStream();

            callBackListener.onSuccess(inputStream);

            //String url = "https://www.baidu.com/";
            //URL urls = new URL(url);
            ////得到connection对象。
            //HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
            ////设置请求方式
            //connection.setRequestMethod("GET");
            ////连接
            //connection.connect();
            ////得到响应码
            //int responseCode = connection.getResponseCode();
            //if(responseCode == HttpURLConnection.HTTP_OK){
            //    //得到响应流
            //    InputStream inputStream = connection.getInputStream();
            //    //将响应流转换成字符串
            //    callBackListener.onSuccess(inputStream);
            //}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
