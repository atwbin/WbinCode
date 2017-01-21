package com.atwbin.myokhttps.rxjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atwbin.myokhttps.R;
import com.atwbin.myokhttps.base.BaseActivity;
import com.atwbin.myokhttps.base.BaseRecyclerAdapter;
import com.atwbin.myokhttps.base.DividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RxActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToorBar;
    @Bind(R.id.rx_recycleview)
    RecyclerView mRecycleView;
    private ArrayList<String[]> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        initToolBar(mToorBar, true, "OkRx使用示例");
        initData();

        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecycleView.setAdapter(new RxActivity.MainAdapter(this));

    }

    private void initData() {
        data = new ArrayList<>();
        data.add(new String[]{"基本请求", "基本的使用方法,包括JsonCallback解析,上传Json文本等"});
        data.add(new String[]{"请求图片", "请求服务器返回bitmap对象"});
        data.add(new String[]{"文件上传", "支持参数和文件一起上传,并回调上传进度"});
        data.add(new String[]{"文件下载", "支持下载进度回调"});
    }

    private class MainAdapter extends BaseRecyclerAdapter<String[], RxActivity.ViewHolder> {

        public MainAdapter(Context mContext) {
            super(mContext, data);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_main_list, parent, false);
            return new RxActivity.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RxActivity.ViewHolder holder, int position) {
            String[] strings = data.get(position);
            holder.bind(position, strings);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.des)
        TextView des;

        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        //绑定数据
        public void bind(int position, String[] strings) {
            this.position = position;
            title.setText(strings[0]);
            des.setText(strings[1]);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (position == 0) startActivity(new Intent(RxActivity.this, RxCommonActivity.class));
            if (position == 1) startActivity(new Intent(RxActivity.this, RxBitmapActivity.class));
            if (position==2) startActivity(new Intent(RxActivity.this, RxFormUploadActivity.class));
            if (position ==3)startActivity(new Intent(RxActivity.this,RxFileDownloadActivity.class));
        }
    }
}
