package com.img.dkk.myokhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.img.dkk.myokhttp.engine.IJsonTranserListener;
import com.img.dkk.myokhttp.engine.MyHttp;

public class MainActivity extends AppCompatActivity {

    String url = "http://display.miguvideo.com/display/v3/static/PERSIONAL_CENTER/fc035faeaa3544a5a553e6c30f632d96";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void sendHttp() {
        MyHttp.sendJsonRequest(url, null, ResponseDataBean.class, new IJsonTranserListener<ResponseDataBean>() {

            @Override public void onSuccess(ResponseDataBean m) {

                Log.e("MainActivity","====="+m.toString());
            }

            @Override public void onFailure() {

            }
        });
    }

    public void onClick(View view) {
        sendHttp();
    }
}
