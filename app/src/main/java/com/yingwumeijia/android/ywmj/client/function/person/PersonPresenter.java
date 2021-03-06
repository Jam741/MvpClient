package com.yingwumeijia.android.ywmj.client.function.person;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;

import com.rx.android.jamspeedlibrary.utils.PhoneNumberUtils;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CustomerResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;
import com.yingwumeijia.android.ywmj.client.function.TabWithPagerAdapter;
import com.yingwumeijia.android.ywmj.client.function.collect.CollectFragment;
import com.yingwumeijia.android.ywmj.client.function.collect.CollectOldFragment;
import com.yingwumeijia.android.ywmj.client.function.collect.CollectPresenter;
import com.yingwumeijia.android.ywmj.client.function.mainfunction.MainActivity;
import com.yingwumeijia.android.ywmj.client.function.setting.SettingFragment;
import com.yingwumeijia.android.ywmj.client.function.setting.SettingPresenter;
import com.yingwumeijia.android.ywmj.client.utils.StartActivityManager;
import com.yingwumeijia.android.ywmj.client.utils.UserManager;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
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
        MainActivity
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
//            CollectFragment collectFragment = CollectFragment.newInstance();
//            CollectPresenter collectPresenter = new CollectPresenter(context, collectFragment);
//            SettingFragment settingFragment = SettingFragment.newInstance();
//            SettingPresenter settingPresenter = new SettingPresenter(context, settingFragment);
//            fragments.add(collectFragment);
//            fragments.add(settingFragment);
            fragments.add(new CollectOldFragment());
            SettingFragment settingFragment = SettingFragment.newInstance();
            SettingPresenter settingPresenter = new SettingPresenter(context, settingFragment);
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
    public void sendLoginStateBroadcast(boolean isLogin) {
        Intent intent = new Intent(Constant.ACTION_NOT_LOGIN);
        intent.putExtra(Constant.KEY_LOGIN, isLogin);
        context.sendBroadcast(intent);
    }

    @Override
    public void start() {
        if (Constant.isLogin(context)) {
            mView.showLoginInLayout();
            loadingPersonData();
        } else {
            mView.showNotloginLayout();
        }
        sendLoginStateBroadcast(Constant.isLogin(context));
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
                showName = userBean.getShowName();
                portraitUrl = userBean.getShowHead();
                showPhone = PhoneNumberUtils.getCryptographicPhone(userBean.getUserPhone());
                mView.setNikeName(TextUtils.isEmpty(showName) ? "点击编辑信息" : showName);
                mView.setUserPortrait(portraitUrl);
                mView.showCollectCount(response.body().getData().getCollectionCount() + "个收藏");

                if (RongIM.getInstance() != null)
                    RongIM.getInstance().refreshUserInfoCache(
                            new UserInfo(
                                    Constant.getImId(context),
                                    showName,
                                    Uri.parse(portraitUrl))
                    );

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
