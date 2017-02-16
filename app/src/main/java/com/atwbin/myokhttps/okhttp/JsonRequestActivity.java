package com.atwbin.myokhttps.okhttp;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.atwbin.myokhttps.R;
import com.atwbin.myokhttps.base.BaseDetailActivity;
import com.atwbin.myokhttps.callback.DialogCallback;
import com.atwbin.myokhttps.model.LzyResponse;
import com.atwbin.myokhttps.model.ServerModel;
import com.atwbin.myokhttps.utils.Urls;
import com.lzy.okgo.OkGo;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class JsonRequestActivity extends BaseDetailActivity {


    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_json_request);
        ButterKnife.bind(this);
        setTitle("自动解析Json对象");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }


    @OnClick(R.id.requestJson)
    public void requestJson(View view) {
        OkGo.get(Urls.URL_JSONOBJECT)
                .tag(this)
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new DialogCallback<LzyResponse<ServerModel>>(this) {
                    @Override
                    public void onSuccess(LzyResponse<ServerModel> serverModelLzyResponse, Call call, Response response) {
                        handleResponse(serverModelLzyResponse.data, call, response);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        handleError(call, response);
                    }
                });

    }

    @OnClick(R.id.requestJsonArray)
    public void requestJsonArray(View view) {
        OkGo.get(Urls.URL_JSONARRAY)//
                .tag(this)//
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new DialogCallback<LzyResponse<List<ServerModel>>>(this) {
                    @Override
                    public void onSuccess(LzyResponse<List<ServerModel>> responseData, Call call, Response response) {
                        handleResponse(responseData.data, call, response);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        handleError(call, response);
                    }
                });
    }

}
