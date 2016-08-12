package com.yingwumeijia.android.funcation.person;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.rx.android.jamspeedlibrary.utils.PhoneNumberUtils;
import com.yingwumeijia.android.WorkerApp;
import com.yingwumeijia.android.data.bean.CustomerResultBean;
import com.yingwumeijia.android.data.bean.UserBean;
import com.yingwumeijia.android.funcation.TabWithPagerAdapter;
import com.yingwumeijia.android.funcation.collect.CollectFragment;
import com.yingwumeijia.android.funcation.collect.CollectPresenter;
import com.yingwumeijia.android.funcation.setting.SettingFragment;
import com.yingwumeijia.android.funcation.setting.SettingPresenter;
import com.yingwumeijia.android.utils.StartActivityManager;
import com.yingwumeijia.android.utils.constants.Constant;

import java.util.ArrayList;
import java.util.List;

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
        WorkerApp
                .getApiService()
                .getCustomerInfo()
                .enqueue(userInfoCallback);
    }

    @Override
    public void bingViewPager(ViewPager viewPager, TabLayout tabLayout) {
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void bingPageAdapter(ViewPager viewPager) {
        TabWithPagerAdapter tabWithPagerAdapter = (TabWithPagerAdapter) createViewPagerAdapter(createFragments(), createTabs());
        viewPager.setAdapter(tabWithPagerAdapter);
    }

    @Override
    public FragmentStatePagerAdapter createViewPagerAdapter(List<Fragment> fragments, List<String> tabs) {
        return new TabWithPagerAdapter(mView.getFragmentManager(), tabs, fragments);
    }


    @Override
    public List<Fragment> createFragments() {
        List<Fragment> fragments = new ArrayList<>();
        if (fragments.size() == 0) {
            CollectFragment collectFragment = CollectFragment.newInstance();
            CollectPresenter collectPresenter = new CollectPresenter(context, collectFragment);
            SettingFragment settingFragment = SettingFragment.newInstance();
            SettingPresenter settingPresenter = new SettingPresenter(context, settingFragment);
            fragments.add(collectFragment);
            fragments.add(settingFragment);
        }
        return fragments;
    }

    @Override
    public List<String> createTabs() {
        List<String> tabs = new ArrayList<>();
        tabs.add("收藏");
        tabs.add("设置");
        return tabs;
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
    Callback<CustomerResultBean> userInfoCallback = new Callback<CustomerResultBean>() {
        @Override
        public void onResponse(Call<CustomerResultBean> call, Response<CustomerResultBean> response) {
            mView.dismissProgressBar();
            if (response.body().getSucc()) {
                UserBean userBean = response.body().getData().getCustomerDto();
                showName = userBean.getNickName();
                portraitUrl = userBean.getShowHead();
                showPhone = PhoneNumberUtils.getCryptographicPhone(userBean.getUserPhone());
                mView.setNikeName(TextUtils.isEmpty(showName) ? "点击编辑信息" : showName);
                mView.setUserPortrait(portraitUrl);
                mView.showCollectCount(response.body().getData().getCollectionCount() + "个收藏");
            } else {
                mView.showLoadingFail(response.body().getMessage());
                mView.showNetConnectError();
            }
        }

        @Override
        public void onFailure(Call<CustomerResultBean> call, Throwable t) {
            mView.dismissProgressBar();
            mView.showNetConnectError();
        }
    };

}
