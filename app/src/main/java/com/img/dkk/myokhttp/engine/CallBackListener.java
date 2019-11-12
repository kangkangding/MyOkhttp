package com.img.dkk.myokhttp.engine;

import java.io.InputStream;

/**
 * Created by  dingkangkang on 2019/10/16
 * email：851615943@qq.com
 */
public interface CallBackListener {

    void onSuccess(InputStream inputStream);

    void onFailure();
}
