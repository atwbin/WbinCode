package com.atwbin.myokhttps.okhttp;

import android.os.Bundle;
import android.view.View;

import com.atwbin.myokhttps.R;
import com.atwbin.myokhttps.base.BaseDetailActivity;
import com.atwbin.myokhttps.callback.StringDialogCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class HttpsActivity extends BaseDetailActivity {

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_https);
        ButterKnife.bind(this);
        setTitle("Https展示");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    @OnClick(R.id.btn_none_https_request)
    public void btn_none_https_request(View view) {
        OkGo.get("https://github.com/jeasonlzy")
                .tag(this)
                .headers("header1", "headerValue1")
                .params("param1", "paramValue1")
                .execute(new StringDialogCallback() {

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        handleResponse(s, call, response);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        handleError(call, response);
                    }
                });
    }


    @OnClick(R.id.btn_https_request)
    public void btn_https_request(View view) {
        try {
            OkGo.get("https://kyfw.12306.cn/otn")
                    .tag(this)
                    .headers("Connection", "close")                         //如果对于部分自签名的https访问不成功，需要加上该控制头
                    .headers("header1", "headerValue1")
                    .params("param1", "paramValue1")
                    .setCertificates(getAssets().open("srca.cer"))                       //方法二：也可以设置https证书（选一种即可）
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            handleResponse(s, call, response);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            handleError(call, response);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
