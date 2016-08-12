package com.yingwumeijia.android.funcation.splash;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;

import com.yingwumeijia.android.MainActivity;
import com.yingwumeijia.android.data.bean.UserBean;
import com.yingwumeijia.android.funcation.login.LoginDataProvider;
import com.yingwumeijia.android.funcation.login.LoginRobot;
import com.yingwumeijia.android.utils.constants.Constant;


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
                            context,
                            Constant.getUserPhone(context),
                            Constant.getUserPassword(context),
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
