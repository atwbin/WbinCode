package com.atwbin.myokhttps.okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.atwbin.myokhttps.R;
import com.atwbin.myokhttps.base.BaseDetailActivity;
import com.atwbin.myokhttps.callback.DialogCallback;
import com.atwbin.myokhttps.model.LzyResponse;
import com.atwbin.myokhttps.model.ServerModel;
import com.atwbin.myokhttps.utils.Urls;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheManager;
import com.lzy.okgo.cache.CacheMode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class CacheActivity extends BaseDetailActivity {


    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cache);
        ButterKnife.bind(this);
        setTitle("网络缓存基本用法");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    @OnClick(R.id.getAll)
    public void getAll(View view) {
        List<CacheEntity<Object>> all = CacheManager.INSTANCE.getAll();
        StringBuilder sb = new StringBuilder();
        sb.append("总共" + all.size() + "条目").append("\n\n");
        for (int i = 0; i < all.size(); i++) {
            CacheEntity<Object> entity = all.get(i);
            sb.append("第" + (i + 1) + "条缓存：").append("\n").append(entity).append("\n\n");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("所有缓存显示").setMessage(sb.toString()).show();
    }

    @OnClick(R.id.clear)
    public void clear(View view) {
        boolean clear = CacheManager.INSTANCE.clear();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("清除缓存").setMessage("是否清除成功" + clear).show();
    }

    @OnClick(R.id.no_cache)
    public void no_cache(View view) {
        OkGo.get(Urls.URL_CACHE)
                .tag(this)
                .cacheKey("no_cache")
                .cacheTime(5000)
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new CacheCallBack(this));
    }

    @OnClick(R.id.cache_default)
    public void cache_default(View view) {
        OkGo.get(Urls.URL_CACHE)
                .tag(this)
                .cacheKey("cache_default")
                .cacheTime(5000)
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new CacheCallBack(this));

    }

    @OnClick(R.id.request_failed_read_cache)
    public void request_failed_read_cache(View view) {

        OkGo.get(Urls.URL_CACHE)
                .tag(this)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("request_failed_read_cache")
                .cacheTime(5000)
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new CacheCallBack(this));
    }

    @OnClick(R.id.if_none_cache_request)
    public void if_none_cache_request(View view) {
        OkGo.get(Urls.URL_CACHE)
                .tag(this)
                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .cacheKey("request_failed_read_cache")
                .cacheTime(5000)
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new CacheCallBack(this));
    }

    @OnClick(R.id.first_cache_then_request)
    public void first_cache_then_request(View view) {
        OkGo.get(Urls.URL_CACHE)
                .tag(this)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .cacheKey("request_failed_read_cache")
                .cacheTime(5000)
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new CacheCallBack(this));
    }


    public class CacheCallBack extends DialogCallback<LzyResponse<ServerModel>> {


        public CacheCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccess(LzyResponse<ServerModel> serverModelLzyResponse, Call call, Response response) {
            handleResponse(serverModelLzyResponse.data, call, response);
            requestState.setText("请求成功  是否来自缓存：false  请求方式：" + call.request().method() + "\n" + "url：" + call.request().url());
        }

        @Override
        public void onCacheSuccess(LzyResponse<ServerModel> responseData, Call call) {
            handleResponse(responseData.data, call, null);
            requestState.setText("请求成功  是否来自缓存：false  请求方式：" + call.request().method() + "\n" + "url：" + call.request().url());
        }

        @Override
        public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
            handleError(call, response);
        }

        @Override
        public void onCacheError(Call call, Exception e) {
            super.onCacheError(call, e);
            handleError(call, null);
            requestState.setText("请求失败  是否来自缓存：true  请求方式：" + call.request().method() + "\n" + "url：" + call.request().url());
        }
    }
}
