package com.yingwumeijia.android.worker.funcation.login;

import android.content.Context;
import android.util.Log;


import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.WorkerApp;
import com.yingwumeijia.android.worker.data.bean.FindPwdResultBean;
import com.yingwumeijia.android.worker.data.bean.LoginResultBean;
import com.yingwumeijia.android.worker.data.bean.RegisterResultBean;
import com.yingwumeijia.android.worker.data.bean.TokenResultBean;
import com.yingwumeijia.android.worker.im.infoprovider.MyGroupInfoProvider;
import com.yingwumeijia.android.worker.im.infoprovider.MyUserInfoProvider;
import com.yingwumeijia.android.worker.utils.constants.Constant;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 2016/8/3 15:47.
 * Describe:
 */
public class LoginRobot implements LoginDataProvider {

    private Response<LoginResultBean> mResponse;
    private Response<RegisterResultBean> mRegisterResponse;
    private Response<FindPwdResultBean> mFindpasswordResponse;
    private LoginCallBack mLoginCallBack;
    private int error_count = 0;
    private Context mContext;
    private STATUS mCurrentStatus = STATUS.LOGIN;
    private String mPhone,mPassword;

    enum STATUS {
        LOGIN,
        REGISTER,
        FINDPASSWORD
    }

    //构造函数初始化 登陆用
    public LoginRobot(Context context, String phone, String password, LoginCallBack loginCallBack) {
        this.mLoginCallBack = loginCallBack;
        this.mContext = context;
        this.mCurrentStatus = STATUS.LOGIN;
        this.mPhone = phone;
        this.mPassword = password;
        login(phone, password, null);
    }


    //构造函数初始化 找回密码用
    public LoginRobot(Context context, String phone, String smsCode, String password, boolean b, LoginCallBack loginCallBack) {
        this.mLoginCallBack = loginCallBack;
        this.mContext = context;
        this.mCurrentStatus = STATUS.FINDPASSWORD;
        this.mPhone = phone;
        this.mPassword = password;
        findPassword(phone, smsCode, password);
    }


    /**
     * 创建登录机器人
     *
     * @param phone
     * @param password
     * @param loginCallBack
     */
    public static void createLoginRobot(Context context, String phone, String password, LoginCallBack loginCallBack) {
        new LoginRobot(context, phone, password, loginCallBack);
    }


    public static void createLoginRobotForFindPassword(Context context, String phone, String smsCode, String password, LoginCallBack loginCallBack) {
        new LoginRobot(context, phone, smsCode, password, true, loginCallBack);
    }


    @Override
    public void login(String phone, String password, String verifyCode) {
        Constant.saveUserLoginInfo(phone, password, mContext);
        WorkerApp
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
    public void findPassword(String phone, String smsCode, String password) {
        WorkerApp
                .getApiService()
                .getBackPassword(phone, password, smsCode)
                .enqueue(findPasswordCallback);
    }

    @Override
    public void getToken() {
        WorkerApp
                .getApiService()
                .getIMToken()
                .enqueue(new Callback<TokenResultBean>() {
                    @Override
                    public void onResponse(Call<TokenResultBean> call, Response<TokenResultBean> response) {
                        if (response.body().getSucc()) {
                            //连接融云
                            Constant.saveIMToken(response.body().getData().getToken(), mContext);
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

    /**
     * 找回密码回调
     */
    Callback<FindPwdResultBean> findPasswordCallback = new Callback<FindPwdResultBean>() {
        @Override
        public void onResponse(Call<FindPwdResultBean> call, Response<FindPwdResultBean> response) {
            if (response.body().getSucc()) {

            } else {
            }
        }

        @Override
        public void onFailure(Call<FindPwdResultBean> call, Throwable t) {
        }
    };

    @Override
    public void connectRongIM(String token) {
        Log.d("jam", "token:" + token);
        if (WorkerApp.appContext().getApplicationInfo().packageName.equals(WorkerApp.getCurProcessName(WorkerApp.appContext().getApplicationContext()))) {

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
                    Constant.setLoginIn(mContext);
                    Constant.saveUserLoginInfo(mPhone, mPassword, mContext);
                    if (mCurrentStatus == STATUS.LOGIN) {
                        mLoginCallBack.loginSuccess(mResponse.body().getData());
                    } else if (mCurrentStatus == STATUS.REGISTER) {
                        mLoginCallBack.loginSuccess(null);
                    } else if (mCurrentStatus == STATUS.FINDPASSWORD) {
                        mLoginCallBack.loginSuccess(null);
                    }

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    mLoginCallBack.loginError("登录失败");
                    Log.d("LoginActivity", "-=========================-onError : " + errorCode);
                    if (RongIM.getInstance() != null) {
                        RongIM.getInstance().disconnect();
                        RongIM.getInstance().logout();
                    }
                }
            });
        }
    }


}
