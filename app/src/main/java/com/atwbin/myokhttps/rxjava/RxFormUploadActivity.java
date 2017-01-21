package com.atwbin.myokhttps.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.atwbin.myokhttps.R;
import com.atwbin.myokhttps.base.BaseRxDetailActivity;
import com.atwbin.myokhttps.utils.Urls;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;


import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class RxFormUploadActivity extends BaseRxDetailActivity {

    @Bind(R.id.selectImage)
    TextView mSelectImage;
    @Bind(R.id.formUpload)
    TextView upLoadImage;

    private ArrayList<ImageItem> imageItems;


    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rx_form_upload);
        ButterKnife.bind(this);
        setTitle("文件上传");
    }


    @OnClick(R.id.selectImage)
    public void selectImage(View view) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(true);
        imagePicker.setShowCamera(true);
        imagePicker.setSelectLimit(9);
        imagePicker.setCrop(false);
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 120);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (imageItems != null && imageItems.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < imageItems.size(); i++) {
                        if (i == imageItems.size() - 1)
                            sb.append("图片").append(i + 1).append(" ： ").append(imageItems.get(i).path);
                        else
                            sb.append("图片").append(i + 1).append(" ： ").append(imageItems.get(i).path).append("\n");
                    }
                    mSelectImage.setText(sb.toString());
                } else {
                    mSelectImage.setText("暂无照片");
                }

            }
        }
    }

    @OnClick(R.id.formUpload)
    public void formUpload(View view) {
        ArrayList<File> files = new ArrayList<>();
        if (imageItems != null && imageItems.size() != 0) {
            for (int i = 0; i < imageItems.size(); i++) {
                files.add(new File(imageItems.get(i).path));
            }
        }

        OkGo.post(Urls.URL_FORM_UPLOAD)
                .tag(this)
                .headers("header1", "headerValue1")//
                .headers("header2", "headerValue2")//
                .params("param1", "paramValue1")//
                .params("param2", "paramValue2")//
                .addFileParams("file", files)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        upLoadImage.setText("正在上传中...\n" +
                                "使用Rx方式做进度监听稍显麻烦,推荐使用回调方式");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())  //切换到主线程
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        upLoadImage.setText("上传完成");
                        handleResponse(s, null, null);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        upLoadImage.setText("上传出错");
                        handleError(null, null);
                        showToast("上传出错");
                    }
                });

    }
}
