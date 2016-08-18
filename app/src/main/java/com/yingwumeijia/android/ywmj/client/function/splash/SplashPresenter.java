package com.yingwumeijia.android.ywmj.client.function.splash;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.app.ActivityCompat;

import com.rx.android.jamspeedlibrary.utils.AppUtils;
import com.rx.android.jamspeedlibrary.utils.T;
import com.yingwumeijia.android.ywmj.client.ApiService;
import com.yingwumeijia.android.ywmj.client.BuildConfig;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.SeverBean;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;
import com.yingwumeijia.android.ywmj.client.function.collect.CollectContract;
import com.yingwumeijia.android.ywmj.client.function.login.LoginDataProvider;
import com.yingwumeijia.android.ywmj.client.function.login.LoginRobot;
import com.yingwumeijia.android.ywmj.client.function.mainfunction.MainActivity;
import com.yingwumeijia.android.ywmj.client.im.listener.MyConnectionStatusListener;
import com.yingwumeijia.android.ywmj.client.utils.StartActivityManager;
import com.yingwumeijia.android.ywmj.client.utils.UserManager;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;
import com.yingwumeijia.android.ywmj.client.utils.net.retrofit.RetrofitBuilder;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Jam on 2016/8/8 18:01.
 * Describe:
 */
public class SplashPresenter implements SplashContract.Presenter {

    private final SplashContract.View mView;
    private final Context context;
    private int baseUrlErrorCount = 0;


    public SplashPresenter(SplashContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
        this.mView.setPresener(this);
    }

    @Override
    public void login() {
        if (Constant.isLogin(context)) {
            LoginRobot
                    .createLoginRobot(
                            context,
                            Constant.getUserPhone(context),
                            Constant.getUserPassword(context),
                            loginCallBack
                    );
        } else {
            startMainActivity();
        }
    }


    @Override
    public void startMainActivity() {
        ActivityCompat.finishAfterTransition((Activity) context);
        MainActivity.start(context);
    }

    @Override
    public void loadBaseUrl() {
        MyApp.getApiService()
                .getService(String.valueOf(1), AppUtils.getVersionName(context))
                .enqueue(severCallback);
    }

    @Override
    public void start() {

    }

    LoginDataProvider.LoginCallBack loginCallBack = new LoginDataProvider.LoginCallBack() {
        @Override
        public void loginSuccess(UserBean userBean) {
            Constant.setLoginIn(context);
            startMainActivity();
        }

        @Override
        public void loginError(String msg) {
            Constant.setLoginOut(context);
            startMainActivity();
        }

        @Override
        public void connectError() {
            mView.showNetConnectError();
            startMainActivity();
        }
    };


    Callback<BaseBean<SeverBean>> severCallback = new Callback<BaseBean<SeverBean>>() {
        @Override
        public void onResponse(Call<BaseBean<SeverBean>> call, Response<BaseBean<SeverBean>> response) {
            if (response.body().getSucc()) {
                Constant.saveBaseUrl(context, response.body().getData().getServerUrl());
                new RetrofitBuilder.Builder().context(context).baseUrl(response.body().getData().getServerUrl()).build();

                //初始化融云
                initRongClound(response.body().getData().getAppImkey());
                login();
            } else {

                login();

                baseUrlErrorCount++;
                checkErrorCount();
            }

        }

        @Override
        public void onFailure(Call<BaseBean<SeverBean>> call, Throwable t) {
            baseUrlErrorCount++;
            login();
        }
    };

    private void checkErrorCount() {
        if (baseUrlErrorCount >= 3) {
            if (Constant.getBaseUrl(context).equals("")) {
                ActivityCompat.finishAffinity((Activity) context);
            }
            new RetrofitBuilder.Builder().context(context).baseUrl(Constant.getBaseUrl(context)).build();
        }
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }


    /**
     * 初始化融云
     */
    private void initRongClound(String imKey) {


        /**
         *
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(context.getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(context, imKey);

//            if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
//
//                DemoContext.init(this);
//            }
            //扩展功能自定义
            InputProvider.ExtendProvider[] provider = {
                    new ImageInputProvider(RongContext.getInstance()),//图片
                    new CameraInputProvider(RongContext.getInstance()),//相机
                    new LocationInputProvider(RongContext.getInstance()),//地理位置
//                    new VoIPInputProvider(RongContext.getInstance()),// 语音通话
//                     new ContactsProvider(RongContext.getInstance())//自定义通讯录
            };
//            RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.GROUP, provider);
            RongIM.setConnectionStatusListener(new MyConnectionStatusListener());
        }
    }
}
