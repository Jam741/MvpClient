package com.yingwumeijia.android.ywmj.client.function.login;

import android.content.Context;

import com.rx.android.jamspeedlibrary.utils.PhoneNumberUtils;
import com.yingwumeijia.android.ywmj.client.utils.StartActivityManager;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;
import com.yingwumeijia.android.ywmj.client.function.findpassword.FindPasswordActivity;
import com.yingwumeijia.android.ywmj.client.function.register.RegisterActivity;
import com.yingwumeijia.android.ywmj.client.utils.UserManager;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public class LoginPresenter implements LoginContract.Presenter {

    private final Context context;
    private final LoginContract.View mLognView;

    public LoginPresenter(Context context, LoginContract.View mLognView) {
        this.context = context;
        this.mLognView = mLognView;
        this.mLognView.setPresener(this);
    }

    @Override
    public void findPassword(String phone) {
        FindPasswordActivity.start(context, phone);
    }

    @Override
    public void register(String phone) {
        RegisterActivity.start(context, phone);
    }

    @Override
    public void loginSuccessOperation(UserBean userBean) {
        mLognView.showLoginSuccess();
        UserManager.saveUserInfo(userBean);
        mLognView.finish();
        
        if (!mLognView.isCurrent())
            StartActivityManager.startMain(context);
    }

    @Override
    public void login(String phone, String password, String safetyCode) {
        if (!checkPhone(phone)) return;
        if (!checkPassword(password)) return;
        mLognView.showProgressBar();
        LoginRobot.createLoginRobot(context, phone, password, new LoginDataProvider.LoginCallBack() {
            @Override
            public void loginSuccess(UserBean userBean) {
                mLognView.dismissProgressBar();
                loginSuccessOperation(userBean);
            }

            @Override
            public void loginError(String msg) {
                mLognView.dismissProgressBar();
                mLognView.showLoginError(msg);
            }

            @Override
            public void connectError() {
                mLognView.dismissProgressBar();
                mLognView.showNetConnectError();
            }
        });
    }

    @Override
    public boolean checkPhone(String phone) {
        if (!PhoneNumberUtils.isMobile(phone)) {
            mLognView.showPhoneInputError();
            return false;
        }
        return true;
    }

    @Override
    public boolean checkPassword(String passwored) {
        if (!Constant.passwordRuleOk(passwored)) {
            mLognView.showPassordInputError();
            return false;
        }
        return true;
    }


    @Override
    public void start() {

    }
}
