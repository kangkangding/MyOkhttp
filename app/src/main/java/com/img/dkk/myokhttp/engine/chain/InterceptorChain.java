package com.img.dkk.myokhttp.engine.chain;

import com.img.dkk.myokhttp.engine.CreateSocket;
import java.io.InputStream;
import java.util.List;

/**
 * Created by  dingkangkang on 2019/11/18
 * email：851615943@qq.com
 */
public class InterceptorChain {

    List<Interceptor> interceptors;
    CreateSocket createSocket;

    int index = 0;

    public InterceptorChain(List<Interceptor> interceptors,
        CreateSocket createSocket,int index) {
        this.interceptors = interceptors;
        this.createSocket = createSocket;
        this.index = index;
    }

    public InputStream proceed(){

        InterceptorChain chain = new InterceptorChain(interceptors,createSocket,index + 1);
        Interceptor interceptor = interceptors.get(index);

        InputStream response = interceptor.intercept(chain);

        return  response;
    }


}
