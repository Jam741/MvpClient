package com.yingwumeijia.android.ywmj.client.function.findpassword;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;

import com.rx.android.jamspeedlibrary.utils.PhoneNumberUtils;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.FindPwdResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;
import com.yingwumeijia.android.ywmj.client.function.login.LoginDataProvider;
import com.yingwumeijia.android.ywmj.client.function.login.LoginRobot;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 2016/8/4 12:21.
 * Describe:
 */
public class FindPasswordPresenter implements FindPasswordContract.Presenter {

    private final Context context;

    private final FindPasswordContract.View mFindPasswordView;

    private MyCountDownTimer myCountDownTimer;
    private int toal_time = 60000;
    private int cycles_time = 1000;

    public FindPasswordPresenter(Context context, FindPasswordContract.View mFindPasswordView) {
        this.context = context;
        this.mFindPasswordView = mFindPasswordView;
        this.mFindPasswordView.setPresener(this);
    }


    @Override
    public boolean checkInputPhone(String phone) {
        if (PhoneNumberUtils.isMobile(phone)) return true;
        return false;
    }

    @Override
    public boolean checkInputSmsCode(String smsCode) {
        if (Constant.smsCodeRuleOk(smsCode)) return true;
        return false;
    }

    @Override
    public boolean checkInputPassword(String password) {
        if (Constant.passwordRuleOk(password)) return true;
        return false;
    }

    @Override
    public void sendSmsCode(String phone) {
        if (!checkInputPhone(phone)) return;
        MyApp
                .getApiService()
                .sendSmsCode(phone, Constant.PARAM_FIND)
                .enqueue(sendSmsCallback);
    }

    @Override
    public void findSuccessOperation(UserBean userBean) {
        mFindPasswordView.finish();
    }

    @Override
    public void findPassword(String phone, String smsCode, String password) {
        if (!checkInputPhone(phone)) return;
        if (!checkInputSmsCode(smsCode)) return;
        if (!checkInputPassword(password)) return;
        mFindPasswordView.showProgressBar();

        LoginRobot.createLoginRobotForFindPassword(
                context,
                phone,
                smsCode,
                password,
                loginCallBack
        );
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
     * 登陆&注册&找回密码的回调
     */
    LoginDataProvider.LoginCallBack loginCallBack = new LoginDataProvider.LoginCallBack() {
        @Override
        public void loginSuccess(UserBean userBean) {
            mFindPasswordView.dismissProgressBar();
            mFindPasswordView.showFindPasswordSuccess();
            findSuccessOperation(userBean);
        }

        @Override
        public void loginError(String msg) {
            mFindPasswordView.dismissProgressBar();
            mFindPasswordView.showFindPasswordError(msg);
        }

        @Override
        public void connectError() {
            mFindPasswordView.dismissProgressBar();

        }
    };


    /**
     * 发送短信验证码回调
     */
    Callback<BaseBean> sendSmsCallback = new Callback<BaseBean>() {

        @Override
        public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
            mFindPasswordView.dismissProgressBar();
            if (response.body().getSucc()) {
                mFindPasswordView.showSendSmsCodeSucess();
                if (myCountDownTimer != null) {
                    myCountDownTimer.cancel();
                    myCountDownTimer = null;
                }
                myCountDownTimer = new MyCountDownTimer(toal_time, cycles_time);
                myCountDownTimer.start();
            } else {
                mFindPasswordView.showSendSmsCodeError(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<BaseBean> call, Throwable t) {
            mFindPasswordView.dismissProgressBar();
            mFindPasswordView.showNetConnectError();
        }
    };


    /**
     * 倒计时函数
     */
    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long l) {
            mFindPasswordView.refreshSendSmsButtonText(l / cycles_time + "s");
        }

        @Override
        public void onFinish() {
            mFindPasswordView.unLockSendSmsButton();
        }
    }
}
