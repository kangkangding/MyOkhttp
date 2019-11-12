package com.img.dkk.myokhttp.engine;

import com.alibaba.fastjson.JSON;
import java.io.UnsupportedEncodingException;

/**
 * Created by  dingkangkang on 2019/10/16
 * email：851615943@qq.com
 */
public class HttpTask<T> implements Runnable{


    HttpRequest httpRequest;

    public HttpTask(String url ,T requestData,HttpRequest httpRequest,CallBackListener callBackListener) {
        this.httpRequest = httpRequest;

        httpRequest.setUrl(url);
        httpRequest.setListener(callBackListener);

        String content = JSON.toJSONString(requestData);

        try {
            httpRequest.setData(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override public void run() {
        httpRequest.excute();
    }
}
