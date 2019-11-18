package com.img.dkk.myokhttp.engine;

import com.img.dkk.myokhttp.engine.chain.CallServiceInterceptor;
import com.img.dkk.myokhttp.engine.chain.HeadersInterceptor;
import com.img.dkk.myokhttp.engine.chain.Interceptor;
import com.img.dkk.myokhttp.engine.chain.InterceptorChain;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<String,String> headersMap = new HashMap<>();

        httpUrl.setHeaders(headersMap);
    }

    @Override public void setListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    @Override public void excute() {
        try {

            List<Interceptor> interceptorList = new ArrayList<>();

            interceptorList.add(new HeadersInterceptor());
            interceptorList.add(new CallServiceInterceptor());

            CreateSocket createSocket = new CreateSocket();
            createSocket.setHttpUrl(httpUrl);
            InterceptorChain interceptorChain = new InterceptorChain(interceptorList,createSocket,0);

            InputStream inputStream = interceptorChain.proceed();

            callBackListener.onSuccess(inputStream);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
