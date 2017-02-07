package com.atwbin.myokhttps.rxjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.atwbin.myokhttps.R;
import com.atwbin.myokhttps.base.BaseRxDetailActivity;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class RxFileDownloadActivity extends BaseRxDetailActivity {

    @Bind(R.id.fileDownload)
    Button fileDownload;
    @Bind(R.id.downloadSize)
    TextView downloadSize;
    @Bind(R.id.netSpeed)
    TextView netSpeed;
    @Bind(R.id.tvProgress)
    TextView tvProgress;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rx_file_download);
        ButterKnife.bind(this);
        setTitle("文件上传");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    @OnClick(R.id.fileDownload)
    public void setFileDownload(View view) {
        ServerApi.getFile("aaa", "bbb")
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        fileDownload.setText("正在下载中...\n使用Rx方式做进度监听稍显麻烦,推荐使用回调方式");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        fileDownload.setText("文件上传成功");
                        handleResponse(null, null, null);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showToast("请求失败");
                        fileDownload.setText("文件上传失败");
                        handleError(null, null);
                    }
                });
    }
}
