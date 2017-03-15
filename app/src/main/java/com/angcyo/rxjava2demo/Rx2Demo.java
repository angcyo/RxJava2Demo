package com.angcyo.rxjava2demo;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/03/15 10:40
 * 修改人员：Robi
 * 修改时间：2017/03/15 10:40
 * 修改备注：
 * Version: 1.0.0
 */
public class Rx2Demo {
    public static void test() {
        Rx2.create();

        final CompositeDisposable compositeDisposable = new CompositeDisposable();
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                //e.onComplete();
                e.onError(new NullPointerException());
            }
        });

        //subscribe方法返回void类型
        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        compositeDisposable.dispose();
    }
}
