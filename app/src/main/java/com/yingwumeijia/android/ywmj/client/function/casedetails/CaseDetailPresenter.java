package com.yingwumeijia.android.ywmj.client.function.casedetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.rx.android.jamspeedlibrary.utils.T;
import com.rx.android.jamspeedlibrary.utils.adapter.CommonRecyclerAdapter;
import com.rx.android.jamspeedlibrary.utils.adapter.RecyclerViewHolder;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.TabEntity;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseDetailsBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseDetailsResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CreateConversationResult;
import com.yingwumeijia.android.ywmj.client.data.bean.ShareModel;
import com.yingwumeijia.android.ywmj.client.function.HtmlFragment;
import com.yingwumeijia.android.ywmj.client.function.HtmlOf720Fragment;
import com.yingwumeijia.android.ywmj.client.function.TabWithPagerAdapter;
import com.yingwumeijia.android.ywmj.client.function.login.LoginFragment;
import com.yingwumeijia.android.ywmj.client.function.share.SharePopupWindow;
import com.yingwumeijia.android.ywmj.client.im.conversationlist.ConversationActivity;
import com.yingwumeijia.android.ywmj.client.utils.StartActivityManager;
import com.yingwumeijia.android.ywmj.client.utils.UserManager;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;
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

    //share
    private SharePopupWindow sharePopupWindow;

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
    public void undateVisitNum(int caseId) {
        MyApp.getApiService()
                .upDateVisitNum(caseId)
                .enqueue(updateVisitCallback);
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
        mFragments.add(HtmlOf720Fragment.newInstance(caseDetailsBean.getPath_720(), caseDetailsBean.getPreviewOf720()));
        mFragments.add(HtmlFragment.newInstance(caseDetailsBean.getLayout()));
        mFragments.add(HtmlFragment.newInstance(caseDetailsBean.getCost()));
        mFragments.add(HtmlFragment.newInstance(caseDetailsBean.getMaterial()));
        mFragments.add(HtmlFragment.newInstance(caseDetailsBean.getPlain()));
        mFragments.add(HtmlFragment.newInstance(caseDetailsBean.getTeam()));
    }

    @Override
    public void loadDetailData(int caseId) {
        mView.showProgressBar();
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
        Intent intent = new Intent(Constant.ACTION_SCROLL_NAV);
        intent.putExtra("POSITION", position);
        context.sendBroadcast(intent);
    }

    @Override
    public void collect(int caseId) {
        MyApp
                .getApiService()
                .collectionCase(caseId)
                .enqueue(collectCallbace);
    }

    @Override
    public void cancelCollect(int caseId) {
        MyApp
                .getApiService()
                .cancelCollection(caseId)
                .enqueue(cancelCollectCallback);
    }


    @Override
    public void connectWithTeam(int caseId) {
        //立即联系他们
        MyApp
                .getApiService()
                .createGroupConversation(caseId)
                .enqueue(connectCallback);

    }

    @Override
    public void launchShareSDK() {
        if (sharePopupWindow == null)
            sharePopupWindow = new SharePopupWindow((Activity) context, createShareModel());
        sharePopupWindow.showPopupWindow();
    }

    @Override
    public ShareModel createShareModel() {
        return null;
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


    Callback<BaseBean> updateVisitCallback = new Callback<BaseBean>() {
        @Override
        public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {

        }

        @Override
        public void onFailure(Call<BaseBean> call, Throwable t) {

        }
    };


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


    Callback<BaseBean> collectCallbace = new Callback<BaseBean>() {
        @Override
        public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
            mView.dismissProgressBar();
            if (response.body().getSucc()) {
                mView.setCollected();
            } else {
                mView.showLoadDataFail(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<BaseBean> call, Throwable t) {
            mView.dismissProgressBar();
            mView.showNetConnectError();
        }
    };

    Callback<BaseBean> cancelCollectCallback = new Callback<BaseBean>() {
        @Override
        public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
            mView.dismissProgressBar();
            if (response.body().getSucc()) {
                mView.setUnCollected();
            } else {
                mView.showLoadDataFail(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<BaseBean> call, Throwable t) {
            mView.showNetConnectError();
        }
    };

    Callback<CreateConversationResult> connectCallback = new Callback<CreateConversationResult>() {
        @Override
        public void onResponse(Call<CreateConversationResult> call, Response<CreateConversationResult> response) {
            mView.dismissProgressBar();
            if (response.body().getSucc()) {
                CreateConversationResult.GroupConversationBean conversationBean = response.body().getData();
                String mCurrentSessionID = String.valueOf(conversationBean.getId());
                StartActivityManager.startConversation(context, mCurrentSessionID, "title");
            } else {
                if (!UserManager.userPrecondition(context)) return;
                mView.showLoadDataFail(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<CreateConversationResult> call, Throwable t) {
            mView.dismissProgressBar();
            mView.showNetConnectError();
        }
    };
}
