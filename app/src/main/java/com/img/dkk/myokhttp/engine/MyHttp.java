package com.img.dkk.myokhttp.engine;

/**
 * Created by  dingkangkang on 2019/10/16
 * email：851615943@qq.com
 */
public class MyHttp {

    public static<T,M> void sendJsonRequest(String url,T requestData,Class<M> response,IJsonTranserListener iJsonTranserListener){

        HttpRequest httpRequest = new HttpRequestImp();
        CallBackListener callBackListener = new CovertCallBackListener<>(response,iJsonTranserListener);
        HttpTask httpTask = new HttpTask(url,requestData,httpRequest,callBackListener);

        ThreadPoolManager.getInstance().addTask(httpTask);
    }
}
