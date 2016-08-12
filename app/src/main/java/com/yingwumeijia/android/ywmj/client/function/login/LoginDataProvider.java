package com.yingwumeijia.android.ywmj.client.function.login;

import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;

/**
 * Created by Jam on 2016/8/3 15:45.
 * Describe:
 */
public interface LoginDataProvider {

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param smsCode
     */
    void register(String phone, String password, String smsCode);


    /**
     * 登陆确认
     *
     * @param phone
     * @param token
     */
    void confirmOperation(String phone, String token);

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
     * 当用户注册操作需要确认的时候显示对话框（E端用户注册C端账号）
     *
     * @param phone
     * @param token 确认token 在注册的时候返货
     */
    void showRegisterConfirmDialog(String phone, String token, DialogConfirmCallback confirmCallback);

    interface DialogConfirmCallback {

        void confirm(String phone, String token);

        void cancel();
    }

    /**
     * 登陆&注册操作完成回调
     */
    interface LoginCallBack {

        void loginSuccess(UserBean userBean);

        void loginError(String msg);

        void connectError();
    }


}
