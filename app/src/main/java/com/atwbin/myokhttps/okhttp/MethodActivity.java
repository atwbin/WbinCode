package com.atwbin.myokhttps.okhttp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.atwbin.myokhttps.R;
import com.atwbin.myokhttps.base.BaseDetailActivity;
import com.atwbin.myokhttps.callback.DialogCallback;
import com.atwbin.myokhttps.model.LzyResponse;
import com.atwbin.myokhttps.model.ServerModel;
import com.atwbin.myokhttps.utils.ColorUtils;
import com.atwbin.myokhttps.utils.Urls;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MethodActivity extends BaseDetailActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.exGridView)
    GridView gridview;

    private String[] methods = {"GET", "HEAD\n只有请求头", "OPTIONS\n获取服务器支持的HTTP请求方式",//
            "POST", "PUT\n用法同POST主要用于创建资源", "DELETE\n与PUT对应主要用于删除资源"};

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_method);
        ButterKnife.bind(this);
        setTitle("请求方法演示");
        gridview.setAdapter(new MyAdapter());
        gridview.setOnItemClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                OkGo.get(Urls.URL_METHOD)
                        .tag(this)
                        .headers("header1", "headerValue1")
                        .params("param", "paramValue1")
                        .execute(new DialogCallback<LzyResponse<ServerModel>>(this) {
                            @Override
                            public void onSuccess(LzyResponse<ServerModel> responseData, Call call, Response response) {
                                handleResponse(responseData.data, call, response);
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                handleError(call, response);
                            }
                        });
                break;
            case 1:
                OkGo.head(Urls.URL_METHOD)
                        .tag(this)
                        .headers("header1", "headerValue1")
                        .params("param", "paramValue1")
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
                break;
            case 2:
                OkGo.options(Urls.URL_METHOD)
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
                break;
            case 3:
                OkGo.post(Urls.URL_METHOD)
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
                break;
            case 4:
                OkGo.put(Urls.URL_METHOD)
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
                break;
            case 5:
                OkGo.delete(Urls.URL_METHOD)
                        .tag(this)
                        .headers("header1", "headerValue1")//
                        .params("param1", "paramValue1")//
                        .requestBody(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), "这是要上传的数据"))
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
                break;
        }
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return methods.length;
        }

        @Override
        public String getItem(int i) {
            return methods[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = new TextView(getApplicationContext());
            TextView textView = (TextView) view;
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.RED);
            textView.setHeight(200);
            textView.setTextSize(18);
            textView.setText(getItem(i));
            textView.setBackgroundColor(ColorUtils.randomColor());
            return textView;
        }
    }
}
