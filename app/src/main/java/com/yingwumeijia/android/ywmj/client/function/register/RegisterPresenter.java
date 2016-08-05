package com.yingwumeijia.android.ywmj.client.function.register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import com.rx.android.jamspeedlibrary.utils.PhoneNumberUtils;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.LoginResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.RegisterResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;
import com.yingwumeijia.android.ywmj.client.function.login.LoginDataProvider;
import com.yingwumeijia.android.ywmj.client.function.login.LoginRobot;
import com.yingwumeijia.android.ywmj.client.utils.StartActivityManager;
import com.yingwumeijia.android.ywmj.client.utils.UserManager;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 2016/8/3 18:01.
 * Describe:
 */
public class RegisterPresenter implements RegisterContract.Presenter {

    private final Context context;

    private final RegisterContract.View mRegisterView;

    private MyCountDownTimer myCountDownTimer;
    private int toal_time = 60000;
    private int cycles_time = 1000;

    public RegisterPresenter(Context context, RegisterContract.View mRegisterView) {
        this.context = context;
        this.mRegisterView = mRegisterView;
        this.mRegisterView.setPresener(this);
    }


    @Override
    public boolean checkAgreementBox(boolean agree) {
        if (!agree) {
            mRegisterView.showUnAgreement();
            return false;
        }
        return true;
    }

    @Override
    public boolean checkPhone(String phone) {
        if (!PhoneNumberUtils.isMobile(phone)) {
            mRegisterView.showPhoneError();
            return false;
        }
        return true;
    }

    @Override
    public boolean checkPassword(String password) {
        if (!Constant.passwordRuleOk(password)) {
            mRegisterView.showPasswordError();
            return false;
        }
        return true;
    }

    @Override
    public boolean checkSmsCode(String smsCode) {
        if (!Constant.smsCodeRuleOk(smsCode)) {
            mRegisterView.showSmsCodeError();
            return false;
        }
        return true;
    }

    @Override
    public void sendSmsCode(String phone, int source) {
        if (!checkPhone(phone)) return;
        mRegisterView.showProgressBar();
        MyApp
                .getApiService()
                .sendSmsCode(phone, source)
                .enqueue(sendSmsCallback);
    }

    @Override
    public void registerSuccess(UserBean userBean) {
        UserManager.saveUserInfo(userBean);
        mRegisterView.finish();
        StartActivityManager.startMain(context);
    }

    @Override
    public void register(String phone, String password, String smsCode) {
        Log.i("jam", "register presenter");
        Log.i("jam", "register presenter" + mRegisterView.returnAgreementStatus());
        if (!checkPhone(phone)) return;
        if (!checkSmsCode(smsCode)) return;
        if (!checkPassword(password)) return;
        if (!checkAgreementBox(mRegisterView.returnAgreementStatus())) return;
        mRegisterView.showProgressBar();
        LoginRobot.createLoginRobotForRegister(phone, password, smsCode, loginCallBack);
    }

    @Override
    public void destory() {
        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
            myCountDownTimer = null;
        }
    }

    @Override
    public void start() {

    }


    /**
     * 发送短信验证码回调
     */
    Callback<BaseBean> sendSmsCallback = new Callback<BaseBean>() {
        @Override
        public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
            mRegisterView.dismissProgressBar();
            Log.i("jam", "showSendSmsCodeError");
            if (response.body().getSucc()) {
                mRegisterView.showSendSmsCodeSuccess();
                mRegisterView.lockSendSmsButton();
                if (myCountDownTimer != null) {
                    myCountDownTimer.cancel();
                    myCountDownTimer = null;
                }
                myCountDownTimer = new MyCountDownTimer(toal_time, cycles_time);
                myCountDownTimer.start();
            } else {
                Log.i("jam", "showSendSmsCodeError");
                mRegisterView.showSendSmsCodeError(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<BaseBean> call, Throwable t) {
            mRegisterView.dismissProgressBar();
            mRegisterView.showNetConnectError();
        }
    };


    /**
     * 登陆&注册回调
     */
    LoginDataProvider.LoginCallBack loginCallBack = new LoginDataProvider.LoginCallBack() {
        @Override
        public void loginSuccess(UserBean userBean) {
            mRegisterView.dismissProgressBar();
            mRegisterView.showRegisterSuccess();
            registerSuccess(userBean);
        }

        @Override
        public void loginError(String msg) {
            mRegisterView.dismissProgressBar();
            mRegisterView.showRegisterError(msg);
        }

        @Override
        public void connectError() {
            mRegisterView.dismissProgressBar();

        }
    };

    /**
     * 短信验证码倒数计时
     */
    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long l) {
            mRegisterView.refreshSendSmsButtonText(l / cycles_time + "s");
        }

        @Override
        public void onFinish() {
            mRegisterView.unlockSendSmsButton();
        }
    }
}
