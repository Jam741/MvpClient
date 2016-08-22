package com.yingwumeijia.android.worker.funcation.splash;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.utils.SystemUtils;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import timber.log.Timber;

import com.muzhi.camerasdk.model.Constants;
import com.rx.android.jamspeedlibrary.utils.AppUtils;
import com.rx.android.jamspeedlibrary.utils.T;
import com.yingwumeijia.android.worker.ApiService;
import com.yingwumeijia.android.worker.DemoContext;
import com.yingwumeijia.android.worker.MainActivity;
import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.data.bean.BaseBean;
import com.yingwumeijia.android.worker.data.bean.SeverBean;
import com.yingwumeijia.android.worker.data.bean.UserBean;
import com.yingwumeijia.android.worker.funcation.login.LoginDataProvider;
import com.yingwumeijia.android.worker.funcation.login.LoginRobot;
import com.yingwumeijia.android.worker.im.RongCloudEvent;
import com.yingwumeijia.android.worker.im.listener.MyConnectionStatusListener;
import com.yingwumeijia.android.worker.utils.StartActivityManager;
import com.yingwumeijia.android.worker.utils.constants.Constant;
import com.yingwumeijia.android.worker.utils.net.retrofit.CookieManger;
import com.yingwumeijia.android.worker.utils.net.retrofit.RetrofitBuilder;


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
    public void loadBaseUrl() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(defaultClient())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        apiService
                .getService(String.valueOf(2), AppUtils.getVersionName(context))
                .enqueue(severCallback);
    }

    private OkHttpClient defaultClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.d(message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        builder.cookieJar(new CookieManger(context));
        return builder.build();
    }


    @Override
    public void startMainActivity() {
        ActivityCompat.finishAfterTransition((Activity) context);
        StartActivityManager.startMain(context);
    }

    @Override
    public void start() {
        loadBaseUrl();
    }

    LoginDataProvider.LoginCallBack loginCallBack = new LoginDataProvider.LoginCallBack() {


        @Override
        public void loginSuccess(UserBean userBean) {
            Constant.setLoginIn(context);
            ActivityCompat.finishAfterTransition((Activity) context);
            StartActivityManager.startMain(context);
        }

        @Override
        public void loginError(String msg) {
            Constant.setLoginOut(context);
            StartActivityManager.startLoginActivity(context);
        }

        @Override
        public void connectError() {
            mView.showNetConnectError();
            StartActivityManager.startLoginActivity(context);
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

                loadBaseUrl();

                baseUrlErrorCount++;
                checkErrorCount();
            }

        }

        @Override
        public void onFailure(Call<BaseBean<SeverBean>> call, Throwable t) {
            baseUrlErrorCount++;
            if (Constant.getBaseUrl(context).equals("")) {
                ActivityCompat.finishAffinity((Activity) context);
                new RetrofitBuilder.Builder().context(context).baseUrl(Constant.BASE_URL_RELEASE).build();
            } else {
                new RetrofitBuilder.Builder().context(context).baseUrl(Constant.getBaseUrl(context)).build();
            }
            ActivityCompat.finishAfterTransition((Activity) context);
//            initRongClound(Constant.RONG_CLOUD_APP_KEY_RELASE);
//
        }
    };


    private void checkErrorCount() {
        if (baseUrlErrorCount >= 3) {
            if (Constant.getBaseUrl(context).equals("")) {
                new RetrofitBuilder.Builder().context(context).baseUrl(Constant.BASE_URL_RELEASE).build();

            }
            new RetrofitBuilder.Builder().context(context).baseUrl(Constant.getBaseUrl(context)).build();

            ActivityCompat.finishAfterTransition((Activity) context);
            MainActivity.start(context);
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


        String current = SystemUtils.getCurProcessName(context);
        String mainProcessName = context.getPackageName();

        Log.d("jam", "current :" + current);
        Log.d("jam", "mainProcessName :" + mainProcessName);
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


            DemoContext.init(context);
            RongCloudEvent.init(context);

//            if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
//
//                DemoContext.init(this);
//            }
            //扩展功能自定义
//            InputProvider.ExtendProvider[] provider = {
//                    new ImageInputProvider(RongContext.getInstance()),//图片
//                    new LocationInputProvider(RongContext.getInstance()),//地理位置
////                    new VoIPInputProvider(RongContext.getInstance()),// 语音通话
////                     new ContactsProvider(RongContext.getInstance())//自定义通讯录
//            };
//            RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.GROUP, provider);
            RongIM.setConnectionStatusListener(new MyConnectionStatusListener());
        }
    }
}
