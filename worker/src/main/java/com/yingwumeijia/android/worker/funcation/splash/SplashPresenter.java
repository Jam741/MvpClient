package com.yingwumeijia.android.worker.funcation.splash;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;

import com.yingwumeijia.android.worker.MainActivity;
import com.yingwumeijia.android.worker.data.bean.UserBean;
import com.yingwumeijia.android.worker.funcation.login.LoginDataProvider;
import com.yingwumeijia.android.worker.funcation.login.LoginRobot;
import com.yingwumeijia.android.worker.utils.StartActivityManager;
import com.yingwumeijia.android.worker.utils.constants.Constant;


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
        StartActivityManager.startMain(context);
    }

    @Override
    public void start() {
        login();
    }

    LoginDataProvider.LoginCallBack loginCallBack = new LoginDataProvider.LoginCallBack() {


        @Override
        public void loginSuccess(UserBean userBean) {
            Constant.setLoginIn(context);
            StartActivityManager.startMain(context);
        }

        @Override
        public void loginError(String msg) {
            Constant.setLoginOut(context);
            StartActivityManager.startLoginActivity(context);
        }

        @Override
        public void connectError() {
            mView.showNetConnectError();
            StartActivityManager.startLoginActivity(context);
        }
    };
}
