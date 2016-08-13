package com.yingwumeijia.android.worker.funcation.splash;


import com.yingwumeijia.android.worker.BasePresenter;
import com.yingwumeijia.android.worker.BaseView;

/**
 * Created by Jam on 2016/8/8 17:57.
 * Describe:
 */
public interface SplashContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void login();

        void startMainActivity();
    }
}
