package com.atwbin.myokhttps.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * @author Wbin
 * @title xxxxx.java
 * @project 千品猫商城
 * @date 2017/2/6 0006 16:55
 * @ copyright  Copyright © 2016 qpmall.com Inc. All Rights Reserved.
 * @description 系统名称：
 */
public class ColorUtils {

    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(150) + 50;
        int green = random.nextInt(150) + 50;
        int blue = random.nextInt(150) + 50;
        return Color.rgb(red, green, blue);
    }
}
