package com.yingwumeijia.android.worker.funcation.setting;

import android.app.Activity;
import android.content.Context;

import com.rx.android.jamspeedlibrary.utils.DataCleanManager;
import com.yingwumeijia.android.worker.WorkerApp;
import com.yingwumeijia.android.worker.data.bean.BaseBean;
import com.yingwumeijia.android.worker.funcation.aboutus.AboutUsActivity;
import com.yingwumeijia.android.worker.funcation.login.LoginActivity;
import com.yingwumeijia.android.worker.funcation.setpassword.SetPwdActivity;
import com.yingwumeijia.android.worker.utils.StartActivityManager;
import com.yingwumeijia.android.worker.utils.UserManager;
import com.yingwumeijia.android.worker.utils.constants.Constant;

import io.rong.imkit.RongIM;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jam on 2016/8/8 10:23.
 * Describe:
 */
public class SettingPresenter implements SettingContract.Presenter {

    private final Context context;
    private final SettingContract.View mView;


    public SettingPresenter(Context context, SettingContract.View mView) {
        this.context = context;
        this.mView = mView;
        this.mView.setPresener(this);
    }

    @Override
    public void clearCache() {
        mView.showProgressBar();
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                DataCleanManager.cleanInternalCache(context);
                subscriber.onCompleted();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        mView.dismissProgressBar();
                        mView.showCurrentCache(getCacheSize());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });
    }

    @Override
    public void startAgreementActivity() {
        StartActivityManager.startAgreementActivity(context);
    }


    @Override
    public void startSetPassword() {
        if (!UserManager.userPrecondition(context)) return;
        SetPwdActivity.start((Activity) context);
    }

    @Override
    public void startAboutUs() {
        AboutUsActivity.start(context);
    }

    @Override
    public String getCacheSize() {
        try {
            return  "当前缓存 " + DataCleanManager.getCacheSize(context.getCacheDir());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void loginOut() {
        WorkerApp
                .getApiService()
                .setLogout()
                .enqueue(loginOutCallback);
    }

    @Override
    public void start() {
        if (Constant.isLogin(context)){
            mView.showLoginOutButton();
        }else {
            mView.hideLoginOutButton();
        }
        mView.showCurrentCache(getCacheSize());
    }


    Callback<BaseBean> loginOutCallback = new Callback<BaseBean>() {
        @Override
        public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
            mView.dismissProgressBar();
            if (response.body().getSucc()){
                mView.hideLoginOutButton();
                Constant.setLoginOut(context);
                if (RongIM.getInstance()!=null)RongIM.getInstance().logout();
                LoginActivity.start(context);
            }else {
                mView.showLoginOutFail(response.body().getMessage());
            }

        }

        @Override
        public void onFailure(Call<BaseBean> call, Throwable t) {
            mView.dismissProgressBar();
            mView.showNetConnectError();
        }
    };
}
