package com.atwbin.myokhttps.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

import com.lzy.okgo.request.BaseRequest;

/**
 * @author Wbin
 * @title xxxxx.java
 * @project 千品猫商城
 * @date 2017/2/6 0006 17:18
 * @ copyright  Copyright © 2016 qpmall.com Inc. All Rights Reserved.
 * @description 系统名称： 对于网络请求是否需要弹出进度对话框
 */
public abstract class DialogCallback<T> extends JsonCallback<T> {
    private ProgressDialog mDialog;

    private void initDialog(Activity activity) {
        mDialog = new ProgressDialog(activity);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage("网络请求中...");
    }

    public DialogCallback(Activity activity) {
        super();
        initDialog(activity);
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }


    @Override
    public void onAfter(T t, Exception e) {
        super.onAfter(t, e);
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }
}
