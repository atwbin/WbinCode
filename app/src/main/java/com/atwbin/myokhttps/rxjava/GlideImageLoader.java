package com.atwbin.myokhttps.rxjava;

import android.app.Activity;
import android.widget.ImageView;

import com.atwbin.myokhttps.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * @author Wbin
 * @title xxxxx.java
 * @project 千品猫商城
 * @date 2017/1/19 0019 17:27
 * @ copyright  Copyright © 2016 qpmall.com Inc. All Rights Reserved.
 * @description 系统名称：
 */
public class GlideImageLoader implements ImageLoader {


    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity).load(new File(path))
                .placeholder(R.drawable.ic_default_color)
                .error(R.drawable.ic_default_color)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }

}
