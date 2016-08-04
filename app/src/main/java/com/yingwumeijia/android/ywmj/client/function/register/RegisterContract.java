package com.yingwumeijia.android.ywmj.client.function.register;

import com.yingwumeijia.android.ywmj.client.BasePresenter;
import com.yingwumeijia.android.ywmj.client.BaseView;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;
import com.yingwumeijia.android.ywmj.client.function.login.LoginDataProvider;

/**
 * Created by Jam on 2016/8/3 17:42.
 * Describe:
 */
public interface RegisterContract {

    interface View extends BaseView<Presenter> {

        /**
         * 电话号码错误
         */
        void showPhoneError();

        /**
         * 密码错误
         */
        void showPasswordError();

        /**
         * 短息验证码错误
         */
        void showSmsCodeError();

        /**
         * 解锁发送短信验证码
         */
        void sendSmsCodeUnlock();

        /**
         * 解锁注册
         */
        void registerUnlock();


        /**
         * 发送短信验证码成功
         */
        void showSendSmsCodeSuccess();

        /**
         * 发送短信验证码失败
         *
         * @param msg
         */
        void showSendSmsCodeError(String msg);

        /**
         * 注册成功
         */
        void showRegisterSuccess();


        /**
         * 注册失败
         *
         * @param msg
         */
        void showRegisterError(String msg);


        /**
         * 提示未同意用户协议
         */
        void showUnAgreement();

        boolean returnAgreementStatus();

    }

    interface Presenter extends BasePresenter {

        /**
         * 检查用户协议
         */
        boolean checkAgreementBox(boolean agree);

        /**
         * 检查电话号码
         */
        boolean checkPhone(String phone);

        /**
         * 检查密码
         */
        boolean checkPassword(String password);

        /**
         * 检查短信验证码
         */
        boolean checkSmsCode(String smsCode);

        /**
         * 发送短信验证码
         */
        void sendSmsCode(String phone, int source);

        /**
         * 注册成功操作
         *
         * @param userBean
         */
        void registerSuccess(UserBean userBean);

        /**
         * 注册
         *
         * @param phone
         * @param password
         * @param smsCode
         */
        void register(String phone, String password, String smsCode);
    }
}
