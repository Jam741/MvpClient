package com.yingwumeijia.android.ywmj.client.function.register;

import android.content.Context;

import com.rx.android.jamspeedlibrary.utils.PhoneNumberUtils;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.RegisterResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;
import com.yingwumeijia.android.ywmj.client.function.login.LoginDataProvider;
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
        if (!checkPhone(phone)) {
            return;
        }
        mRegisterView.showProgressBar();
        MyApp
                .getApiService()
                .sendSmsCode(phone, source)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        mRegisterView.dismissProgressBar();
                        if (response.body().getSucc()) {
                            mRegisterView.showSendSmsCodeSuccess();
                        } else {
                            mRegisterView.showSmsCodeError();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        mRegisterView.dismissProgressBar();
                        mRegisterView.showNetConnectError();
                    }
                });
    }

    @Override
    public void registerSuccess(UserBean userBean) {
        UserManager.saveUserInfo(userBean);
    }

    @Override
    public void register(String phone, String password, String smsCode, LoginDataProvider.LoginCallBack loginCallBack) {

        if (!checkPhone(phone) && checkSmsCode(smsCode) && checkPassword(password))
            checkPhone(phone);
        checkSmsCode(smsCode);
        checkPassword(password);
        mRegisterView.showProgressBar();
        MyApp
                .getApiService()
                .register(phone, password, smsCode)
                .enqueue(new Callback<RegisterResultBean>() {
                    @Override
                    public void onResponse(Call<RegisterResultBean> call, Response<RegisterResultBean> response) {
                        mRegisterView.dismissProgressBar();
                    }

                    @Override
                    public void onFailure(Call<RegisterResultBean> call, Throwable t) {
                        mRegisterView.dismissProgressBar();
                    }
                });
    }

    @Override
    public void start() {

    }
}
