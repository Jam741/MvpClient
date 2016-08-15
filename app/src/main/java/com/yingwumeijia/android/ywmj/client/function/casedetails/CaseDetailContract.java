package com.yingwumeijia.android.ywmj.client.function.casedetails;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import com.flyco.tablayout.CommonTabLayout;
import com.yingwumeijia.android.ywmj.client.BasePresenter;
import com.yingwumeijia.android.ywmj.client.BaseView;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseDetailsBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseDetailsResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.ShareModel;

import java.util.List;

/**
 * Created by Jam on 2016/8/8 20:07.
 * Describe:
 */
public interface CaseDetailContract {

    interface Presenter extends BasePresenter {

        /**
         * 更新访问数
         */
        void undateVisitNum(int caseId);

        void bindAdapterForPager();

        void createPageAdapter();

        void createTabs();

        void bindAdapterForTab();

        void createFragments(CaseDetailsBean caseDetailsBean);

        void loadDetailData(int caseId);

        void createNavAdapter(List<String> navData);

        void bindAdapterForNav();

        void navItemSelected(int position);

        void collect(int caseId);

        void cancelCollect(int caseId);

        /**
         * 立即联系他们
         *
         * @param caseId
         */
        void connectWithTeam(int caseId);

        void launchShareSDK();

        void getShareData(int caseId);

        ShareModel createShareModel();

    }

    interface View extends BaseView<Presenter> {


        void showLoadDataFail(String msg);

        ViewPager getViewPager();

        void showNavMenuButton();

        void hideNavMenuButton();

        void showDrawerLayout();

        void clostDrawerLayout();

        FragmentManager getMyFragmentManager();

        RecyclerView getNavRecyclerView();

        CommonTabLayout getTabView();

        void setCollected();

        void setUnCollected();

        /**
         * 显示是否有过会话
         *
         * @param isContact
         */
        void showIsContact(boolean isContact);

    }
}
