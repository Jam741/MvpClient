package com.yingwumeijia.android.ywmj.client.function.casedetails;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.rx.android.jamspeedlibrary.utils.adapter.CommonRecyclerAdapter;
import com.rx.android.jamspeedlibrary.utils.adapter.RecyclerViewHolder;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.TabEntity;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseDetailsBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseDetailsResultBean;
import com.yingwumeijia.android.ywmj.client.function.HtmlFragment;
import com.yingwumeijia.android.ywmj.client.function.TabWithPagerAdapter;
import com.yingwumeijia.android.ywmj.client.function.login.LoginFragment;
import com.yingwumeijia.android.ywmj.client.utils.view.IndexViewPager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 2016/8/8 20:15.
 * Describe:
 */
public class CaseDetailPresenter implements CaseDetailContract.Presenter {

    private final CaseDetailContract.View mView;
    private final Context context;

    //Page
    private List<Fragment> mFragments;
    private IndexViewPager mViewPager;
    private TabWithPagerAdapter mTabWithPagerAdapter;
    private ArrayList<CustomTabEntity> mTabEntities;
    private CommonTabLayout mTabView;
    private final String[] mTabsStrings = {"   720全景", "   细节图片", "   项目造价", "   材料品牌", "   平面布置", "   团队简介"};

    //sliding menu
    private CommonRecyclerAdapter<String> mNavigationAdapter;
    private List<String> mNavData;
    private RecyclerView mNacRecyclerView;

    public CaseDetailPresenter(CaseDetailContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
        this.mView.setPresener(this);
    }


    @Override
    public void bindAdapterForPager() {
        mViewPager.setAdapter(mTabWithPagerAdapter);
    }

    @Override
    public void createPageAdapter() {
        mTabWithPagerAdapter = new TabWithPagerAdapter(mView.getMyFragmentManager(), mFragments);
    }

    @Override
    public void createTabs() {
        if (mTabEntities == null) {
            mTabEntities = new ArrayList<>();
        }
        mTabEntities.clear();
        for (int i = 0; i < mTabsStrings.length - 1; i++) {
            mTabEntities.add(new TabEntity(mTabsStrings[i], R.mipmap.tab_bar_right_arrow, R.mipmap.tab_bar_right_arrow));
        }
        mTabEntities.add(new TabEntity(mTabsStrings[mTabsStrings.length - 1], R.mipmap.tab_bar_transation, R.mipmap.tab_bar_transation));
    }

    @Override
    public void bindAdapterForTab() {
        mTabView.setTabData(mTabEntities);
    }

    @Override
    public void createFragments(CaseDetailsBean caseDetailsBean) {
        if (mFragments == null) mFragments = new ArrayList<>();
        mFragments.clear();
        mFragments.add(HtmlFragment.newInstance(caseDetailsBean.getPath_720()));
        mFragments.add(HtmlFragment.newInstance(caseDetailsBean.getLayout()));
        mFragments.add(HtmlFragment.newInstance(caseDetailsBean.getCost()));
        mFragments.add(HtmlFragment.newInstance(caseDetailsBean.getMaterial()));
        mFragments.add(HtmlFragment.newInstance(caseDetailsBean.getPlain()));
        mFragments.add(HtmlFragment.newInstance(caseDetailsBean.getTeam()));
    }

    @Override
    public void loadDetailData(int caseId) {
        MyApp
                .getApiService()
                .getCaseDetail(caseId)
                .enqueue(caseDataCallback);
    }

    @Override
    public void createNavAdapter(List<String> navData) {
        mNavigationAdapter = new CommonRecyclerAdapter<String>(navData, context, R.layout.item_case_type) {
            @Override
            public void convert(RecyclerViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_type, s)
                        .setOnItemClickListener(new RecyclerViewHolder.OnItemCliceListener() {
                            @Override
                            public void itemClick(View itemView, int position) {
                                mView.clostDrawerLayout();
                                navItemSelected(position);
                            }
                        });
            }
        };
    }

    @Override
    public void bindAdapterForNav() {
        mView.getNavRecyclerView().setAdapter(mNavigationAdapter);
    }

    @Override
    public void navItemSelected(int position) {
        //send BordCost to Fragment
    }

    @Override
    public void conversationWithTeam(int caseId) {
        //立即联系他们
    }

    @Override
    public void start() {
        mTabView = mView.getTabView();
        mViewPager = (IndexViewPager) mView.getViewPager();
        mNacRecyclerView = mView.getNavRecyclerView();
        mNacRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        createTabs();
        bindAdapterForTab();
    }


    Callback<CaseDetailsResultBean> caseDataCallback = new Callback<CaseDetailsResultBean>() {
        @Override
        public void onResponse(Call<CaseDetailsResultBean> call, Response<CaseDetailsResultBean> response) {
            mView.dismissProgressBar();
            if (response.body().getSucc()) {
                createNavAdapter(response.body().getData().getLayoutStrList());
                createFragments(response.body().getData());
                createPageAdapter();
                bindAdapterForPager();
                bindAdapterForNav();
            } else {
                mView.showLoadDataFail(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<CaseDetailsResultBean> call, Throwable t) {
            mView.dismissProgressBar();
            mView.showNetConnectError();
        }
    };
}
