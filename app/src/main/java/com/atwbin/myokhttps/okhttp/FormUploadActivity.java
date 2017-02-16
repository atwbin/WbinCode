package com.atwbin.myokhttps.okhttp;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.atwbin.myokhttps.R;
import com.atwbin.myokhttps.base.BaseDetailActivity;
import com.atwbin.myokhttps.callback.JsonCallback;
import com.atwbin.myokhttps.model.LzyResponse;
import com.atwbin.myokhttps.model.ServerModel;
import com.atwbin.myokhttps.rxjava.GlideImageLoader;
import com.atwbin.myokhttps.ui.NumberProgressBar;
import com.atwbin.myokhttps.utils.Urls;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageFolder;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.BaseRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class FormUploadActivity extends BaseDetailActivity {

    @Bind(R.id.formUpload)
    Button btnFormUpload;
    @Bind(R.id.downloadSize)
    TextView tvDownloadSize;
    @Bind(R.id.tvProgress)
    TextView tvProgress;
    @Bind(R.id.netSpeed)
    TextView tvNetSpeed;
    @Bind(R.id.pbProgress)
    NumberProgressBar pbProgress;
    @Bind(R.id.images)
    TextView tvImages;
    private ArrayList<ImageItem> imageItems;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_form_upload);
        ButterKnife.bind(this);
        setTitle("文件上传");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    @OnClick(R.id.selectImage)
    public void selectImage(View view) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setMultiMode(true);
        imagePicker.setCrop(false);  //不支持裁剪
        imagePicker.setSelectLimit(9);
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (imageItems != null && imageItems.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < imageItems.size(); i++) {
                        if (i == imageItems.size() - 1)
                            sb.append("图片").append(i + 1).append(": ").append(imageItems.get(i).path);
                        else
                            sb.append("图片").append(i + 1).append(": ").append(imageItems.get(i).path).append("\n");

                        tvImages.setText(sb.toString());
                    }
                } else
                    tvImages.setText("---");
            } else {
                Toast.makeText(this, "没有选择照片", Toast.LENGTH_SHORT).show();
                tvImages.setText("---");
            }
        }
    }

    @OnClick(R.id.formUpload)
    public void btnFormUpload(View view) {
        List<File> files = new ArrayList<>();
        if (imageItems != null && imageItems.size() > 0) {
            for (int i = 0; i < imageItems.size(); i++) {
                files.add(new File(imageItems.get(i).path));
            }
        }
        OkGo.post(Urls.URL_FORM_UPLOAD)
                .tag(this)
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .addFileParams("files", files)
                .execute(new JsonCallback<LzyResponse<ServerModel>>() {

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        btnFormUpload.setText("正在上传");
                    }

                    @Override
                    public void onSuccess(LzyResponse<ServerModel> serverModelLzyResponse, Call call, Response response) {
                        handleResponse(serverModelLzyResponse.data, call, response);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        handleError(call, response);
                        btnFormUpload.setText("上传出错");
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        super.upProgress(currentSize, totalSize, progress, networkSpeed);
                        String downloadLength = Formatter.formatFileSize(getApplicationContext(), currentSize);
                        String totalLength = Formatter.formatFileSize(getApplicationContext(), totalSize);

                        tvDownloadSize.setText(downloadLength + "/" + totalLength);
                        String netSpeed = android.text.format.Formatter.formatFileSize(getApplicationContext(), networkSpeed);
                        tvNetSpeed.setText(netSpeed + "/s");
                        tvProgress.setText((Math.round(progress * 10000) * 1.0f / 100) + "%");
                        pbProgress.setMax(100);
                        pbProgress.setProgress((int) (progress*100));
                    }
                });
    }
}
