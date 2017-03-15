package com.angcyo.rxjava2demo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

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
public class Rx2 {
    static Disposable sDisposable;
    private static CompositeDisposable Disposables = new CompositeDisposable();
    private static CompositeSubscription Subscriptions = new CompositeSubscription();

    public static void create() {
        Observable<String> stringObservable = Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        L.e("subscribe: " + e.isDisposed());
                        e.onNext("1" + e.isDisposed());
                        e.onNext("2" + e.isDisposed());
                        e.onNext("3" + e.isDisposed());
                        //int i = 1 / 0;
                        e.onComplete();
                        e.onNext("4" + e.isDisposed());
                        e.onNext("5" + e.isDisposed());
                        //e.onError(new NullPointerException("--"));
                        L.e("subscribe: ------------end " + sDisposable.isDisposed());
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.newThread());

        stringObservable.subscribe(new Observer<String>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                L.e("onSubscribe: ");
                Disposables.add(d);
                sDisposable = d;
                //d.dispose();
            }

            @Override
            public void onNext(String s) {
                L.e("onNext: " + s + " " + sDisposable.isDisposed());
//                if ("2".equalsIgnoreCase(s)) {
//                    disposable.dispose();
//                }
            }

            @Override
            public void onError(Throwable e) {
                L.e("onError: ");
            }

            @Override
            public void onComplete() {
                L.e("onComplete: " + sDisposable.isDisposed());
                Disposables.dispose();
                L.e("onComplete: " + sDisposable.isDisposed());
            }
        });

//        stringObservable.subscribe(new Consumer<String>() {
//            @Override
//            public void accept(@NonNull String s) throws Exception {
//                L.e("11:23 call: accept([s])-> ");
//            }
//        });


//        Observable
//                .create(new ObservableOnSubscribe<String>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<String> e) throws Exception {
//                        L.e("subscribe: ");
//                        e.onNext("1");
//                        e.onNext("2");
//                        e.onNext("3");
//                        e.onComplete();
//                        e.onNext("4");
//                        e.onNext("5");
//                        //e.onError(new NullPointerException("--"));
//                        L.e("subscribe: ------------end");
//                    }
//                })
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        L.e("call: accept([s]):" + s);
//                    }
//                });
    }

    public static void flowable() {
//        final CompositeSubscription compositeSubscription = new CompositeSubscription();
        Flowable<String> stringFlowable = Flowable
                .create(new FlowableOnSubscribe<String>() {
                    @Override
                    public void subscribe(FlowableEmitter<String> e) throws Exception {
                        L.e("13:40 call: subscribe([e])-> ");
                        e.onNext("1");
                        e.onNext("2");
                        e.onComplete();
                        //e.onError(new NullPointerException());
                        L.e("13:40 call: subscribe([e])-> end----");
                    }
                }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread());

        stringFlowable.subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                L.e("13:40 call: onSubscribe([s])-> ");
//                compositeSubscription.add(s);
                //s.request(1);
                // 参数表示接受多少次onNext回调.
                // 当onNext回调次数和这个参数不一致时, 则通过BackpressureStrategy.ERROR参数决定之后的处理
                // 不调用等价于request(0).
                // 典型的错误MissingBackpressureException异常
                // 注意: Flowable请尽量在异步线程使用,否则很容易出现MissingBackpressureException异常
            }

            @Override
            public void onNext(String s) {
                L.e("13:40 call: onNext([s])-> ");
            }

            @Override
            public void onError(Throwable t) {
                L.e("13:40 call: onError([t])-> ");
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                L.e("13:40 call: onComplete([])-> ");
            }
        });
    }
}
