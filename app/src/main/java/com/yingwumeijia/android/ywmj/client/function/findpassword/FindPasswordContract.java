package com.yingwumeijia.android.ywmj.client.function.findpassword;

import android.content.Context;

import com.yingwumeijia.android.ywmj.client.BasePresenter;
import com.yingwumeijia.android.ywmj.client.BaseView;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;

/**
 * Created by Jam on 2016/8/4 12:03.
 * Describe:
 */
public interface FindPasswordContract {

    interface View extends BaseView<Presenter> {

        void showFindPasswordSuccess();

        void showFindPasswordError(String msg);

        void showInputPhoneError();

        void showInputPasswordError();

        void showInputSmsCodeError();

        void unlockSendSmsCode();

        void unlockFindPassword();

        void startLoginFunction();

        void finish();
    }

    interface Presenter extends BasePresenter {

        boolean checkInputPhone(String phone);

        boolean checkInputSmsCode(String smsCode);

        boolean checkInputPassword(String password);

        void findSuccessOperation(UserBean userBean);

        void findPassword(String phone,String smsCode,String password);

    }
}
