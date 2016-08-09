package com.yingwumeijia.android.ywmj.client.function.casedetails;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;

import com.yingwumeijia.android.ywmj.client.BasePresenter;
import com.yingwumeijia.android.ywmj.client.BaseView;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseDetailsBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseDetailsResultBean;

import java.util.List;

/**
 * Created by Jam on 2016/8/8 20:07.
 * Describe:
 */
public interface CaseDetailContract {

    interface Presenter extends BasePresenter {


        void bindAdapterForPager();

        void createPageAdapter();

        void createTabs();

        void createFragments(CaseDetailsBean caseDetailsBean);

        void loadDetailData(int caseId);

        void createNavAdapter(List<String> navData);

        void bindAdapterForNav();

        void navItemSelected(int position);

    }

    interface View extends BaseView<Presenter> {


        void showLoadDataFail(String msg);

        void getViewPager();

        void showNavMenuButton();

        void hideNavMenuButton();

        void showDrawerLayout();

        void clostDrawerLayout();

        FragmentManager getMyFragmentManager();

        RecyclerView getNavRecyclerView();

        void bindAdapterForTab();
    }
}
