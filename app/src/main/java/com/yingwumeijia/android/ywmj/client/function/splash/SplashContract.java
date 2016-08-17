package com.yingwumeijia.android.ywmj.client.function.splash;

import com.yingwumeijia.android.ywmj.client.BasePresenter;
import com.yingwumeijia.android.ywmj.client.BaseView;

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

        void loadBaseUrl();

    }
}
