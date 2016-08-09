package com.yingwumeijia.android.ywmj.client.function.login;

import android.net.Uri;
import android.util.Log;

import com.rx.android.jamspeedlibrary.utils.AppInfo;
import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.GroupResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.LoginResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.TokenResultBean;
import com.yingwumeijia.android.ywmj.client.im.infoprovider.MyGroupInfoProvider;
import com.yingwumeijia.android.ywmj.client.im.infoprovider.MyUserInfoProvider;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 2016/8/3 15:47.
 * Describe:
 */
public class LoginRobot implements LoginDataProvider {

    private Response<LoginResultBean> mResponse;
    private LoginCallBack mLoginCallBack;
    private int error_count = 0;

    //构造函数初始化 登陆用
    public LoginRobot(String phone, String password, String verifyCode, boolean islogin, LoginCallBack loginCallBack) {
        this.mLoginCallBack = loginCallBack;
        login(phone, password, verifyCode);
    }

    //构造函数初始化 注册用
    public LoginRobot(String phone, String password, String smsCode, LoginCallBack loginCallBack) {
        this.mLoginCallBack = loginCallBack;
        register(phone, password, smsCode);
    }


    /**
     * 创建登录机器人
     *
     * @param phone
     * @param password
     * @param verifyCode
     * @param loginCallBack
     */
    public static void createLoginRobot(String phone, String password, String verifyCode, LoginCallBack loginCallBack) {
        new LoginRobot(phone, password, verifyCode, true, loginCallBack);
    }

    /**
     * 创建登录机器人 来注册新用户
     *
     * @param phone
     * @param password
     * @param smsCode
     * @param loginCallBack
     */
    public static void createLoginRobotForRegister(String phone, String password, String smsCode, LoginCallBack loginCallBack) {
        new LoginRobot(phone, password, smsCode, loginCallBack);
    }


    @Override
    public void register(String phone, String password, String smsCode) {
        MyApp
                .getApiService()
                .register(phone, password, smsCode)
                .enqueue(new Callback<LoginResultBean>() {
                    @Override
                    public void onResponse(Call<LoginResultBean> call, Response<LoginResultBean> response) {
                        if (response.body().getSucc()) {
                            //初始化Response
                            mResponse = response;
                            //获取融云Token
                            getToken();
                        } else {
                            mLoginCallBack.loginError(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResultBean> call, Throwable t) {
                        mLoginCallBack.connectError();
                    }
                });
    }

    @Override
    public void login(String phone, String password, String verifyCode) {
        Constant.saveUserLoginInfo(phone, password, MyApp.appContext());
        MyApp
                .getApiService()
                .login(phone, password, verifyCode)
                .enqueue(new Callback<LoginResultBean>() {
                    @Override
                    public void onResponse(Call<LoginResultBean> call, Response<LoginResultBean> response) {
                        if (response.body().getSucc()) {
                            //初始化Response
                            mResponse = response;
                            //获取融云Token
                            getToken();
                        } else {
                            mLoginCallBack.loginError(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResultBean> call, Throwable t) {
                        mLoginCallBack.connectError();
                    }
                });
    }

    @Override
    public void getToken() {
        MyApp
                .getApiService()
                .getIMToken()
                .enqueue(new Callback<TokenResultBean>() {
                    @Override
                    public void onResponse(Call<TokenResultBean> call, Response<TokenResultBean> response) {
                        if (response.body().getSucc()) {
                            //连接融云
                            connectRongIM(response.body().getData().getToken());
                        } else {
                            mLoginCallBack.loginError(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenResultBean> call, Throwable t) {
                        mLoginCallBack.connectError();
                    }
                });
    }

    @Override
    public void connectRongIM(String token) {
        if (MyApp.appContext().getApplicationInfo().packageName.equals(MyApp.getCurProcessName(MyApp.appContext().getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("LoginActivity", "-========================-onTokenIncorrect : ");
                    error_count++;
                    if (error_count <= 2) {
                        getToken();
                    } else {
                        mLoginCallBack.loginError("登录失败");
                        if (RongIM.getInstance() != null)
                            RongIM.getInstance().disconnect();
                    }

                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    /**
                     * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
                     *
                     * @param userInfoProvider 用户信息提供者。
                     * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
                     *                         如果 App 提供的 UserInfoProvider
                     *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
                     *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
                     * @see UserInfoProvider
                     */
                    RongIM.setUserInfoProvider(new MyUserInfoProvider(), true);
                    RongIM.setGroupInfoProvider(new MyGroupInfoProvider(), true);
                    Log.d("LoginActivity", "--=======================-onSuccess : " + userid);
                    mLoginCallBack.loginSuccess(mResponse.body().getData());

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    mLoginCallBack.loginError("登录失败");
                    Log.d("LoginActivity", "-=========================-onError : " + errorCode);
                    if (RongIM.getInstance() != null)
                        RongIM.getInstance().disconnect();
                }
            });
        }
    }
}
