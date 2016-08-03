package com.rx.android.jamspeedlibrary.mvp;


import java.lang.ref.WeakReference;

/**
 * Created by Jam on 2016/5/30.
 */

public class BasePresenter<T> {

    protected WeakReference<T> mViewRef;

    /**
     * 关联View
     */
    public void attachView(T view){
        mViewRef = new WeakReference<T>(view);
    }

    /**
     * 解除关联
     */
    public void dettachView(){
        if (mViewRef!=null){
            mViewRef.clear();
            mViewRef = null;
        }
    }

    protected T getView(){
        return mViewRef.get();
    }
}
