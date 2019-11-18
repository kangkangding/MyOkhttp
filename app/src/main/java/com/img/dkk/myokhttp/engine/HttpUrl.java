package com.img.dkk.myokhttp.engine;

import android.text.TextUtils;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by  dingkangkang on 2019/10/23
 * email：851615943@qq.com
 */
public class HttpUrl {

    String protocol;//协议，http或者https
    String host;//服务器地址
    String file;//请求服务器文件路径
    int port;//服务器服务端口
    private Map<String,String> headers;//http包请求头

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public HttpUrl(String url) {
        URL localUrl = null;//url格式化
        try {
            localUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        host = localUrl.getHost();
        protocol = localUrl.getProtocol();
        file = localUrl.getFile();
        port = localUrl.getPort();
        if(port == -1){
            //代表url中没有端口信息，就是使用默认端口，http:80,https:443
            port = localUrl.getDefaultPort();
        }

        if(TextUtils.isEmpty(file)){
            //如果为空，默认加上"/"
            file = "/";
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public String getFile() {
        return file;
    }

    public int getPort() {
        return port;
    }
}
