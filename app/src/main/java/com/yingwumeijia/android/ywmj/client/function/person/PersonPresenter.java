package com.yingwumeijia.android.ywmj.client.function.person;

import android.content.Context;
import android.text.TextUtils;

import com.rx.android.jamspeedlibrary.utils.PhoneNumberUtils;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;
import com.yingwumeijia.android.ywmj.client.utils.StartActivityManager;
import com.yingwumeijia.android.ywmj.client.utils.UserManager;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 2016/8/5 17:06.
 * Describe:
 */
public class PersonPresenter implements PersonContract.Presenter {

    private final Context context;

    private final PersonContract.View mView;

    private String portraitUrl;
    private String showName;
    private String showPhone;

    public PersonPresenter(Context context, PersonContract.View mView) {
        this.context = context;
        this.mView = mView;
        this.mView.setPresener(this);
    }

    @Override
    public void startEditPersonActivity() {
        StartActivityManager.startEditPersonActivity(
                context,
                portraitUrl,
                showName,
                showPhone
        );
    }

    @Override
    public void startLoginActivity() {
        StartActivityManager.startLoginActivity(context);
    }

    @Override
    public void loadingPersonData() {
        mView.showProgressBar();
        MyApp
                .getApiService()
                .getCustomerInfo()
                .enqueue(userInfoCallback);
    }

    @Override
    public void start() {
        if (Constant.isLogin(context)) {
            mView.showLoginInLayout();
            loadingPersonData();
        } else {
            mView.showNotloginLayout();
        }
    }


    /**
     * 用户数据回调
     */
    Callback<BaseBean<UserBean>> userInfoCallback = new Callback<BaseBean<UserBean>>() {
        @Override
        public void onResponse(Call<BaseBean<UserBean>> call, Response<BaseBean<UserBean>> response) {
            mView.dismissProgressBar();
            if (response.body().getSucc()) {
                UserBean userBean = response.body().getData();
                showName = userBean.getNickName();
                portraitUrl = userBean.getHeadImage();
                showPhone = PhoneNumberUtils.getCryptographicPhone(userBean.getPhone());
                mView.setNikeName(TextUtils.isEmpty(showName) ? "点击编辑信息" : showName);
                mView.setUserPortrait(portraitUrl);
                mView.showCollectCount(userBean.getCaseNum() + "个收藏");
            } else {
                mView.showLoadingFail(response.body().getMessage());
                mView.showNetConnectError();
            }
        }

        @Override
        public void onFailure(Call<BaseBean<UserBean>> call, Throwable t) {
            mView.dismissProgressBar();
            mView.showNetConnectError();
        }
    };

}
