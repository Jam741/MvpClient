package com.yingwumeijia.android.funcation.splash;


import com.yingwumeijia.android.BasePresenter;
import com.yingwumeijia.android.BaseView;

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
