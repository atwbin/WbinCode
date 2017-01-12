package com.atwbin.myokhttps.rxjava;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.atwbin.myokhttps.R;
import com.atwbin.myokhttps.base.BaseRxDetailActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class RxBitmapActivity extends BaseRxDetailActivity {

    @Bind(R.id.imageView)
    ImageView mImageView;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rx_bitmap);
        ButterKnife.bind(this);
        setTitle(getString(R.string.pic));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    @OnClick(R.id.requestImage)
    public void requestImage(View view) {
        Subscription subscription = ServerApi.getBitmap("aaa", "bbb")
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        dismissLoading();
                        handleResponse(bitmap, null, null);
                        //请求成功后赋值
                        mImageView.setImageBitmap(bitmap);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();            //请求失败
                        showToast("请求失败");
                        dismissLoading();
                        handleError(null, null);
                    }
                });
        addSubscribe(subscription);
    }
}
