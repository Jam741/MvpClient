package com.yingwumeijia.android.ywmj.client;

/**
 * Created by Jam on 2016/8/1.
 * jamisonline.he@gmail.com
 */
public interface BaseView<T> {

    void setPresener(T presenter);

    void showNetConnectError();

    void showProgressBar();

    void dismissProgressBar();

}
