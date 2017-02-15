package com.atwbin.myokhttps.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;


/**
 * @author Wbin
 * @title xxxxx.java
 * @project 千品猫商城
 * @date 2017/2/15 0015 14:16
 * @ copyright  Copyright © 2016 qpmall.com Inc. All Rights Reserved.
 * @description 系统名称：
 */
public abstract class StringDialogCallback extends StringCallback {

    private ProgressDialog mDialog;


    public void initDialog(Activity activity) {
        mDialog = new ProgressDialog(activity);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage("网络请求中...");

    }

    @Override
    public void onBefore(BaseRequest request) {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }

    @Override
    public void onAfter(String s, Exception e) {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }
}
