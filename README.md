# **人人都能封装的OkHttp** #


## **. 模拟OkHttp,手写简易Http框架** ##

MyHttp.sendJsonRequest(url, null, ResponseDataBean.class, new IJsonTranserListener<ResponseDataBean>() {

            @Override public void onSuccess(ResponseDataBean m) {

                Log.e("MainActivity","====="+m.toString());
            }

            @Override public void onFailure() {

            }
        });

