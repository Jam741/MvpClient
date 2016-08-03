package com.rx.android.jamspeedlibrary.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rx.android.jamspeedlibrary.ui.activity.StarterActivity;

/**
 * Created by Jam on 2016/5/30.
 */

public abstract class MvpActivity<V, T extends BasePresenter<V>> extends StarterActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dettachView();
    }
}
