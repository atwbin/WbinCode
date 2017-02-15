package com.atwbin.myokhttps.okhttp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.atwbin.myokhttps.R;
import com.atwbin.myokhttps.base.BaseDetailActivity;
import com.atwbin.myokhttps.utils.Urls;
import com.lzy.okgo.OkGo;

import java.io.IOException;

import butterknife.OnClick;
import okhttp3.Response;


public class SyncActivity extends BaseDetailActivity {

    private Handler mHandler = new Handler();

    private static class InHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Response response = (Response) msg.obj;
            String data = response.body().toString();

            System.out.print("同步请求的数据" + data);
        }
    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sync);
        setTitle("同步请求");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    @OnClick(R.id.sync)
    public void sync(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //同步会阻塞主线程，必须开线程
                Response response = null;  //不传callback即为同步请求
                try {
                    response = OkGo.get(Urls.URL_JSONOBJECT)//
                            .tag(this)//
                            .headers("header1", "headerValue1")//
                            .params("param1", "paramValue1")//
                            .execute();

                    Message message = Message.obtain();
                    message.obj = response;
                    mHandler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
