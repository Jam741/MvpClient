package com.yingwumeijia.android.worker.funcation.login;

import com.yingwumeijia.android.worker.data.bean.UserBean;

/**
 * Created by Jam on 2016/8/3 15:45.
 * Describe:
 */
public interface LoginDataProvider {



    /**
     * 登陆
     *
     * @param phone
     * @param password
     * @param smsCode
     */
    void login(String phone, String password, String smsCode);

    /**
     * 找回密码
     *
     * @param phone
     * @param smsCode
     * @param password
     */
    void findPassword(String phone, String smsCode, String password);

    /**
     * 获取融云Token
     */
    void getToken();

    /**
     * 链接融云服务
     *
     * @param token
     */
    void connectRongIM(String token);

    /**
     * 登陆&注册操作完成回调
     */
    interface LoginCallBack {

        void loginSuccess(UserBean userBean);

        void loginError(String msg);

        void connectError();
    }


}
