package com.yingwumeijia.android.ywmj.client.function.login;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.rx.android.jamspeedlibrary.utils.AppInfo;
import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.FindPwdResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.GroupResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.LoginResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.RegisterResultBean;
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
    private Response<RegisterResultBean> mRegisterResponse;
    private Response<FindPwdResultBean> mFindpasswordResponse;
    private LoginCallBack mLoginCallBack;
    private int error_count = 0;
    private Context mContext;
    private STATUS mCurrentStatus = STATUS.LOGIN;
    private String mPhone;
    private String mPassword;

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

    //构造函数初始化 注册用
    public LoginRobot(Context context, String phone, String password, String smsCode, LoginCallBack loginCallBack) {
        this.mLoginCallBack = loginCallBack;
        this.mContext = context;
        this.mCurrentStatus = STATUS.REGISTER;
        this.mPhone = phone;
        this.mPassword = password;
        register(phone, password, smsCode);
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

    /**
     * 创建登录机器人 来注册新用户
     *
     * @param phone
     * @param password
     * @param smsCode
     * @param loginCallBack
     */
    public static void createLoginRobotForRegister(Context context, String phone, String password, String smsCode, LoginCallBack loginCallBack) {
        new LoginRobot(context, phone, password, smsCode, loginCallBack);
    }


    public static void createLoginRobotForFindPassword(Context context, String phone, String smsCode, String password, LoginCallBack loginCallBack) {
        new LoginRobot(context, phone, smsCode, password, true, loginCallBack);
    }


    @Override
    public void register(final String phone, String password, String smsCode) {
        MyApp
                .getApiService()
                .register(phone, password, smsCode)
                .enqueue(new Callback<RegisterResultBean>() {
                    @Override
                    public void onResponse(Call<RegisterResultBean> call, Response<RegisterResultBean> response) {
                        if (response.body().getSucc()) {
                            //初始化Response
                            mRegisterResponse = response;
                            //获取融云Token
                            if (response.body().getData().isNeedConfirm()) {
                                showRegisterConfirmDialog(phone, response.body().getData().getToken(), dialogConfirmCallback);
                            } else {
                                getToken();
                            }

                        } else {
                            mLoginCallBack.loginError(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResultBean> call, Throwable t) {
                        mLoginCallBack.connectError();
                    }
                });
    }

    @Override
    public void confirmOperation(String phone, String token) {
        MyApp
                .getApiService()
                .confirm(phone, token)
                .enqueue(confirmCallback);
    }


    Callback<BaseBean> confirmCallback = new Callback<BaseBean>() {
        @Override
        public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
            if (response.body().getSucc()) {
                getToken();
            } else {
                mLoginCallBack.loginError(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<BaseBean> call, Throwable t) {
            mLoginCallBack.connectError();
        }
    };

    /**
     * 弹出确认对话框
     */
    @Override
    public void showRegisterConfirmDialog(final String phone, final String token, final DialogConfirmCallback confirmCallback) {
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle(R.string.dialog_title)
                .setMessage(mRegisterResponse.body().getData().getMessage())
                .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        confirmCallback.confirm(phone, token);
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        confirmCallback.cancel();
                    }
                })
                .show();
    }

    DialogConfirmCallback dialogConfirmCallback = new DialogConfirmCallback() {
        @Override
        public void confirm(String phone, String token) {
            confirmOperation(phone, token);
        }

        @Override
        public void cancel() {

        }
    };

    @Override
    public void login(String phone, String password, String verifyCode) {
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
    public void findPassword(String phone, String smsCode, String password) {
        MyApp
                .getApiService()
                .getBackPassword(phone, password, smsCode)
                .enqueue(findPasswordCallback);
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
                getToken();
            } else {
                mLoginCallBack.loginError(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<FindPwdResultBean> call, Throwable t) {
            mLoginCallBack.connectError();
        }
    };

    @Override
    public void connectRongIM(String token) {
        Log.d("jam", "token:" + token);
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
                        Constant.setLoginOut(mContext);
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
                    Log.d("LoginActivity", "--=============login==========-onSuccess : " + userid);
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
                    Constant.setLoginOut(mContext);
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
