package com.yingwumeijia.android.ywmj.client.function.splash;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;

import com.rx.android.jamspeedlibrary.utils.AppUtils;
import com.rx.android.jamspeedlibrary.utils.T;
import com.yingwumeijia.android.ywmj.client.ApiService;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;
import com.yingwumeijia.android.ywmj.client.function.collect.CollectContract;
import com.yingwumeijia.android.ywmj.client.function.login.LoginDataProvider;
import com.yingwumeijia.android.ywmj.client.function.login.LoginRobot;
import com.yingwumeijia.android.ywmj.client.function.mainfunction.MainActivity;
import com.yingwumeijia.android.ywmj.client.utils.StartActivityManager;
import com.yingwumeijia.android.ywmj.client.utils.UserManager;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;
import com.yingwumeijia.android.ywmj.client.utils.net.retrofit.RetrofitBuilder;

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


    Callback<BaseBean<String>> severCallback = new Callback<BaseBean<String>>() {
        @Override
        public void onResponse(Call<BaseBean<String>> call, Response<BaseBean<String>> response) {
            if (response.body().getSucc()) {
                Constant.saveBaseUrl(context, response.body().getData());
                new RetrofitBuilder.Builder().context(context).baseUrl(response.body().getData()).build();
                login();
            } else {

                login();

                baseUrlErrorCount++;
                checkErrorCount();
            }

        }

        @Override
        public void onFailure(Call<BaseBean<String>> call, Throwable t) {
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
}
