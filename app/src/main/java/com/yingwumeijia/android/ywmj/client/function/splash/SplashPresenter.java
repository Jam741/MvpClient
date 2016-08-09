package com.yingwumeijia.android.ywmj.client.function.splash;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;

import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;
import com.yingwumeijia.android.ywmj.client.function.collect.CollectContract;
import com.yingwumeijia.android.ywmj.client.function.login.LoginDataProvider;
import com.yingwumeijia.android.ywmj.client.function.login.LoginRobot;
import com.yingwumeijia.android.ywmj.client.function.mainfunction.MainActivity;
import com.yingwumeijia.android.ywmj.client.utils.UserManager;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

/**
 * Created by Jam on 2016/8/8 18:01.
 * Describe:
 */
public class SplashPresenter implements SplashContract.Presenter {

    private final SplashContract.View mView;
    private final Context context;

    public SplashPresenter(SplashContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
        this.mView.setPresener(this);
    }

    @Override
    public void login() {
        if (Constant.isLogin(context)) {
            LoginRobot
                    .createLoginRobot(
                            Constant.getUserPhone(context),
                            Constant.getUserPassword(context),
                            null,
                            loginCallBack
                    );
        } else {
            startMainActivity();
        }
    }


    @Override
    public void startMainActivity() {
        ActivityCompat.finishAfterTransition((Activity) context);
        MainActivity.start(context);
    }

    @Override
    public void start() {
        login();
    }

    LoginDataProvider.LoginCallBack loginCallBack = new LoginDataProvider.LoginCallBack() {
        @Override
        public void loginSuccess(UserBean userBean) {
            Constant.setLoginOut(context);
            startMainActivity();
        }

        @Override
        public void loginError(String msg) {
            Constant.setLoginOut(context);
            startMainActivity();
        }

        @Override
        public void connectError() {
            mView.showNetConnectError();
            startMainActivity();
        }
    };
}