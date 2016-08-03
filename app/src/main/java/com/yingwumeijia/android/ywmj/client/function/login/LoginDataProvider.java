package com.yingwumeijia.android.ywmj.client.function.login;

import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;

/**
 * Created by Jam on 2016/8/3 15:45.
 * Describe:
 */
public interface LoginDataProvider {

    void login(String phone,String password,String smsCode);

    void getToken();

    void connectRongIM(String token);

    interface LoginCallBack{

        void loginSuccess(UserBean userBean);

        void loginError(String msg);

        void connectError();
    }


}
