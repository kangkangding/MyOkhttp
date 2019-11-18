package com.img.dkk.myokhttp.engine.chain;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by  dingkangkang on 2019/11/18
 * email：851615943@qq.com
 */
public class CallServiceInterceptor implements Interceptor {
    @Override public InputStream intercept(InterceptorChain interceptorChain) {
        InputStream inputStream = null;
        interceptorChain.createSocket.connectSocket();
        try {
            interceptorChain.createSocket.writeRequest();

            inputStream = interceptorChain.createSocket.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}
