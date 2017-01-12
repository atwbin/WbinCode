package com.atwbin.myokhttps.base;


import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Wbin
 * @title xxxxx.java
 * @project 千品猫商城
 * @date 2017/1/10 0010 18:10
 * @ copyright  Copyright © 2016 qpmall.com Inc. All Rights Reserved.
 * @description 系统名称： 统一管理所有的订阅生命周期
 */
public abstract class BaseRxDetailActivity extends BaseDetailActivity {

    private CompositeSubscription compositeSubscription;

    public void addSubscribe(Subscription subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }

    public void unSubscribe() {
        if (compositeSubscription != null) compositeSubscription.unsubscribe();
    }

}
