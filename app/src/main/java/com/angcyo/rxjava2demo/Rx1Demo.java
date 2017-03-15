package com.angcyo.rxjava2demo;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/03/15 11:32
 * 修改人员：Robi
 * 修改时间：2017/03/15 11:32
 * 修改备注：
 * Version: 1.0.0
 */
public class Rx1Demo {
    public static void create() {
        CompositeSubscription compositeSubscription = new CompositeSubscription();
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                subscriber.onNext("2");
                //e.onComplete();
                subscriber.onError(new NullPointerException());
            }
        });
        Subscription subscribe = observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });

        //取消订阅
        subscribe.unsubscribe();
        //或者
        compositeSubscription.add(subscribe);
        compositeSubscription.unsubscribe();
    }
}
