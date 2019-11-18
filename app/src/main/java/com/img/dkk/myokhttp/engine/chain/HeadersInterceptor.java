package com.img.dkk.myokhttp.engine.chain;

import com.img.dkk.myokhttp.engine.HttpUrl;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by  dingkangkang on 2019/11/18
 * email：851615943@qq.com
 */
public class HeadersInterceptor implements Interceptor{
    @Override public InputStream intercept(InterceptorChain interceptorChain) {

        HttpUrl httpUrl = interceptorChain.createSocket.getHttpUrl();

        Map<String, String> headers = httpUrl.getHeaders();


        if(!headers.containsKey("Host")){
            headers.put("Host",httpUrl.getHost());
        }
        if(!headers.containsKey("Connection")) {
            headers.put("Connection", "Keep-Alive");
        }

        return interceptorChain.proceed();
    }
}
