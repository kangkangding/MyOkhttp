package com.img.dkk.myokhttp.engine.chain;

import java.io.InputStream;

/**
 * Created by  dingkangkang on 2019/11/13
 * email：851615943@qq.com
 */
public interface Interceptor {

    InputStream intercept(InterceptorChain interceptorChain);
}
