package com.yingwumeijia.android.worker.funcation.person;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;


import com.yingwumeijia.android.worker.BasePresenter;
import com.yingwumeijia.android.worker.BaseView;

import java.util.List;

/**
 * Created by Jam on 2016/8/5 16:30.
 * Describe:
 */
public interface PersonContract {

    interface View extends BaseView<Presenter> {

        /**
         * 显示未登录页面
         */
        void showNotloginLayout();


        /**
         * 显示登陆页面
         */
        void showLoginInLayout();

        void showLoadingFail(String msg);

        void setNikeName(String nikeName);

        void showCollectCount(String collectCount);

        void setUserPortrait(String userPortrait);

        void finish();

        FragmentManager getFragmentManager();
    }

    interface Presenter extends BasePresenter {

        /**
         * 跳转到修改个人信息页面
         */
        void startEditPersonActivity();

        /**
         * 跳转到登陆页面
         */
        void startLoginActivity();

        /**
         * 加载个人数据
         */
        void loadingPersonData();


        void bingViewPager(ViewPager viewPager, TabLayout tabLayout);

        void bingPageAdapter(ViewPager viewPager);

        FragmentStatePagerAdapter createViewPagerAdapter(List<Fragment> fragments,
                                                         List<String> tabs);

        /**
         * 创建FragmentList
         *
         * @return
         */
        List<Fragment> createFragments();

        /**
         * 创建Tabs
         *
         * @return
         */
        List<String> createTabs();


    }

}
