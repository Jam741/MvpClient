package com.yingwumeijia.android.worker.funcation.caselist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.ScreenUtils;
import com.rx.android.jamspeedlibrary.utils.T;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.LoadingMoreFooter;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.ProgressStyle;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.XRecyclerView;
import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.data.bean.CaseBean;
import com.yingwumeijia.android.worker.funcation.search.SearchActivity;
import com.yingwumeijia.android.worker.utils.StartActivityManager;
import com.yingwumeijia.android.worker.utils.base.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jam on 2016/8/5 11:42.
 * Describe:
 */
public class CaseListFragment extends BaseFragment implements CaseListContract.View {

    private CaseListContract.Presenter mPresenter;
    private View root;
    @Bind(R.id.tv_style)
    TextView tvStyle;
    @Bind(R.id.btn_style)
    RelativeLayout btnStyle;
    @Bind(R.id.tv_hx)
    TextView tvHx;
    @Bind(R.id.btn_hx)
    RelativeLayout btnHx;
    @Bind(R.id.tv_time)
    TextView tvCost;
    @Bind(R.id.btn_time)
    RelativeLayout btnCost;
    @Bind(R.id.rv_case)
    XRecyclerView rvCase;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;
    @Bind(R.id.netError_layout)
    LinearLayout netErrorLayout;
    @Bind(R.id.tv_sliding_title)
    TextView tvSlidingTitle;
    @Bind(R.id.rv_sliding_nav)
    RecyclerView rvSlidingNav;
    @Bind(R.id.right_drawer)
    RelativeLayout rightDrawer;
    @Bind(R.id.drawer_root)
    DrawerLayout drawerRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.case_list_frag, container, false);
            ButterKnife.bind(this, root);
        }
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("jam", "context:" + context);

        //init drawerLayout
        ViewGroup.LayoutParams lp = rightDrawer.getLayoutParams();
        lp.width = ScreenUtils.getScreenWidth() * 8 / 12;
        rightDrawer.setLayoutParams(lp);

        //init recyclerView for caseList&slidingNavigation
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.case_list_empty, null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rvCase.setEmptyView(emptyView);
        rvCase.setLayoutManager(linearLayoutManager);
        rvCase.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        rvCase.addFootView(new LoadingMoreFooter(context, getString(R.string.no_more_case_load)));

        rvSlidingNav.setLayoutManager(new LinearLayoutManager(context));

        //start loading data.
        mPresenter.start();
    }

    private int mNavigationPosition = 0;

    @Override
    public int getNavigationPosition() {
        return mNavigationPosition;
    }

    @Override
    public void showDrawerLayout() {
        drawerRoot.openDrawer(Gravity.RIGHT);
    }

    @Override
    public void refreshNavigationStatus(String showText, int navigationPosition) {
        switch (navigationPosition) {
            case 0:
                if (showText.equals("全部")) {
                    tvStyle.setTextColor(getResources().getColor(R.color.text_color_black));
                    tvStyle.setText(R.string.case_list_nav_style);
                } else {
                    tvStyle.setTextColor(getResources().getColor(R.color.colorAccent));
                    tvStyle.setText(showText);

                }
                break;
            case 1:
                if (showText.equals("全部")) {
                    tvHx.setTextColor(getResources().getColor(R.color.text_color_black));
                    tvHx.setText(R.string.case_list_nav_fx);
                } else {
                    tvHx.setTextColor(getResources().getColor(R.color.colorAccent));
                    tvHx.setText(showText);
                }

                break;
            case 2:
                if (showText.equals("全部")) {
                    tvCost.setTextColor(getResources().getColor(R.color.text_color_black));
                    tvCost.setText(R.string.case_list_nav_time);
                } else {
                    tvCost.setTextColor(getResources().getColor(R.color.colorAccent));
                    tvCost.setText(showText);

                }
                break;
        }
    }

    @Override
    public void showLoadNoMore(boolean isNoMore) {

    }

    @Override
    public void showEmptyLayout(boolean isEmpty) {
            emptyLayout.setVisibility(isEmpty==true?View.VISIBLE:View.GONE);
    }

    @Override
    public void showLoadDataFail() {
        T.showShort(context, "加载失败...");
    }

    @Override
    public void showNotNetConnect(boolean isNoConnect) {
        showBaseNetConnectError();
    }

    @Override
    public void closeDrawerLayout() {
        drawerRoot.closeDrawer(Gravity.RIGHT);
    }

    @Override
    public XRecyclerView getCaseListView() {
        return rvCase;
    }

    @Override
    public void caseListRefreshComplete() {
        rvCase.refreshComplete();
    }

    @Override
    public void caseListLoadMoreComplete() {
        rvCase.loadMoreComplete();
    }

    @Override
    public void caseListLoadNomore() {
        rvCase.setIsnomore(true);
    }

    @Override
    public void caseListLoadRset() {
        rvCase.reset();
    }

    @Override
    public RecyclerView getCaseTypeListView() {
        return rvSlidingNav;
    }

    @Override
    public void startCaseDetailActivity(CaseBean caseBean) {
        StartActivityManager.startCaseDetailActivity(context, caseBean.getCaseId());
    }

    @Override
    public void setPresener(CaseListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showNetConnectError() {
        showBaseNetConnectError();
    }

    @Override
    public void showProgressBar() {
        showBaseProgresDialog();
    }

    @Override
    public void dismissProgressBar() {
        dismisBaseProgressDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_style, R.id.btn_hx, R.id.btn_time, R.id.iv_search,R.id.topLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_style:
                tvSlidingTitle.setText(getResources().getString(R.string.case_list_nav_style) + "筛选");
                mNavigationPosition = 0;
                mPresenter.refreshNavigationData(0);
                break;
            case R.id.btn_hx:
                tvSlidingTitle.setText(getResources().getString(R.string.case_list_nav_fx) + "筛选");
                mNavigationPosition = 1;
                mPresenter.refreshNavigationData(1);
                break;
            case R.id.btn_time:
                tvSlidingTitle.setText(getResources().getString(R.string.case_list_nav_time) + "筛选");
                mNavigationPosition = 2;
                mPresenter.refreshNavigationData(2);
                break;
            case R.id.iv_search:
                SearchActivity.start(context);
                break;
            case R.id.topLeft:
                ActivityCompat.finishAfterTransition(context);
                break;
        }
    }

    /**
     * 创建CaseListfragmnet实例
     *
     * @return
     */
    public static CaseListFragment newInstance() {
        return new CaseListFragment();
    }
}
