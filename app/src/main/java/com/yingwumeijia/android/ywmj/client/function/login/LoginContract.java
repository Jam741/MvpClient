package com.yingwumeijia.android.ywmj.client.function.login;

import com.yingwumeijia.android.ywmj.client.BasePresenter;
import com.yingwumeijia.android.ywmj.client.BaseView;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {

        /**
         * 显示电话号码输入错误
         */
        void showPhoneInputError();

        /**
         * 显示密码输入错误
         */
        void showPassordInputError();

        /**
         * 显示登陆错误
         */
        void showLoginError(String errorMessage);

        /**
         * 显示登陆成功
         */
        void showLoginSuccess();

        /**
         * 清除密码
         */
        void cleanPassord();

        /**
         * 登陆按钮解锁
         */
        void loginUnlock();

        /**
         * 关闭activity
         */
        void finish();

    }

    interface Presenter extends BasePresenter {

        /**
         * 找回密码
         */
        void findPassword(String phone);

        /**
         * 注册新账号
         */
        void register(String phone);


        /**
         * 登陆成功操作
         */
        void loginSuccessOperation(UserBean userBean);

        /**
         * 登陆
         */
        void login(String phone, String password, String safetyCode);

        /**
         * 检查电话号码格式
         *
         * @param phone
         */
        boolean checkPhone(String phone);

        /**
         * 检查密码格式
         *
         * @param passwored
         */
        boolean checkPassword(String passwored);
    }
}
