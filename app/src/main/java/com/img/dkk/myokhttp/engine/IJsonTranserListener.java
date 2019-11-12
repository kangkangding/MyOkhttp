package com.img.dkk.myokhttp.engine;

/**
 * Created by  dingkangkang on 2019/10/16
 * email：851615943@qq.com
 */
public interface IJsonTranserListener<T> {

    void onSuccess(T m);

    void onFailure();
}
