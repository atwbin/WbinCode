package com.atwbin.myokhttps.okhttp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atwbin.myokhttps.R;
import com.atwbin.myokhttps.base.BaseDetailActivity;
import com.atwbin.myokhttps.utils.Urls;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class FileDownloadActivity extends BaseDetailActivity {

    @Bind(R.id.down_start)
    TextView mDownStart;
    @Bind(R.id.downSize)
    TextView downSize;
    @Bind(R.id.netSpeed)
    TextView mNetSpeed;
    @Bind(R.id.netProgress)
    TextView mNetProgress;
    @Bind(R.id.pbProgress)
    ProgressBar pbProgress;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_file_download);
        ButterKnife.bind(this);
        setTitle("文件下载");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    @OnClick(R.id.down_start)
    public void down_start(View view) {
        OkGo.get(Urls.URL_DOWNLOAD)
                .tag(this)
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        handleResponse(file, call, response);
                        mDownStart.setText("下载完成");
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                        String downedLength = android.text.format.Formatter.formatFileSize(getApplicationContext(), currentSize);
                        String totalLength = android.text.format.Formatter.formatFileSize(getApplicationContext(), totalSize);
                        downSize.setText(downedLength + "/" + totalLength);
                        String netSpeed = android.text.format.Formatter.formatFileSize(getApplicationContext(), networkSpeed);
                        mNetSpeed.setText(netSpeed + "/s");
                        mNetProgress.setText((Math.round(progress * 10000) * 1.0f / 100) + "%");
                        pbProgress.setMax(100);
                        pbProgress.setProgress((int) (progress * 100));
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        handleError(call, response);
                        mDownStart.setText("下载出错");
                    }

                });
    }

}
