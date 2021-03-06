package com.yingwumeijia.android.worker.funcation.casedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.rx.android.jamspeedlibrary.utils.ScreenUtils;
import com.rx.android.jamspeedlibrary.utils.T;
import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.data.bean.ShareModel;
import com.yingwumeijia.android.worker.utils.base.activity.BaseActivity;
import com.yingwumeijia.android.worker.utils.constants.Constant;
import com.yingwumeijia.android.worker.utils.view.IndexViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jam on 2016/8/9 11:30.
 * Describe:
 */
public class CaseDetailActivity extends BaseActivity implements CaseDetailContract.View,
        ViewPager.OnPageChangeListener, OnTabSelectListener {


    @Bind(R.id.topTitle)
    TextView topTitle;
    @Bind(R.id.topLeft)
    TextView topLeft;
    @Bind(R.id.topRight_second)
    ImageView btnCollect;
    @Bind(R.id.ctab_nav)
    CommonTabLayout ctabNav;
    @Bind(R.id.hsv_nav)
    HorizontalScrollView hsvNav;
    @Bind(R.id.vp_content)
    IndexViewPager vpContent;
    @Bind(R.id.btn_menu)
    ImageButton btnMenu;
    @Bind(R.id.tv_sliding_title)
    TextView tvSlidingTitle;
    @Bind(R.id.rv_sliding_nav)
    RecyclerView rvSlidingNav;
    @Bind(R.id.right_drawer)
    RelativeLayout rightDrawer;
    @Bind(R.id.drawer_root)
    DrawerLayout drawerRoot;
    private CaseDetailContract.Presenter mPresenter;
    private int mCaseId;
    private int mCurrentPosition = 0;
    private boolean isCollect;

    public static void start(Context context, int caseId) {
        Intent starter = new Intent(context, CaseDetailActivity.class);
        starter.putExtra("KEY_CASE_ID", caseId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        //initActionBar
        initActionBar();

        //get data from intent
        getIntentData();

        //lock sliding
        drawerRoot.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        //init drawerLayout
        ViewGroup.LayoutParams lp = rightDrawer.getLayoutParams();
        lp.width = ScreenUtils.getScreenWidth() * 8 / 12;
        rightDrawer.setLayoutParams(lp);

        //set listener
        vpContent.addOnPageChangeListener(this);
        vpContent.setOffscreenPageLimit(5);
        vpContent.setScanScroll(false);
        ctabNav.setOnTabSelectListener(this);


        if (mPresenter == null) {
            mPresenter = new CaseDetailPresenter(this, context);
        }
        mPresenter.start();
        mPresenter.getShareData(mCaseId);
        mPresenter.undateVisitNum(mCaseId);
        mPresenter.loadDetailData(mCaseId);
    }

    private void initActionBar() {
        topTitle.setText("案例详情");
        TextViewUtils.setDrawableToLeft(context, topLeft, R.mipmap.back_ico);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mCaseId = intent.getIntExtra("KEY_CASE_ID", 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.case_detail_act;
    }

    @Override
    public void showLoadDataFail(String msg) {
        T.showShort(context, msg);
    }

    @Override
    public ViewPager getViewPager() {
        return vpContent;
    }

    @Override
    public void showNavMenuButton() {
        btnMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNavMenuButton() {
        btnMenu.setVisibility(View.GONE);
    }

    @Override
    public void showDrawerLayout() {
        drawerRoot.openDrawer(Gravity.RIGHT);
    }

    @Override
    public void clostDrawerLayout() {
        drawerRoot.closeDrawer(Gravity.RIGHT);
    }

    @Override
    public FragmentManager getMyFragmentManager() {
        return getSupportFragmentManager();
    }

    @Override
    public RecyclerView getNavRecyclerView() {
        return rvSlidingNav;
    }

    @Override
    public CommonTabLayout getTabView() {
        return ctabNav;
    }

    @Override
    public void setCollected() {
        isCollect = true;
        btnCollect.setImageResource(R.mipmap.case_details_like_light_ic);
    }

    @Override
    public void setUnCollected() {
        isCollect = false;
        btnCollect.setImageResource(R.mipmap.case_details_like_ico);
    }


    @Override
    public void setPresener(CaseDetailContract.Presenter presenter) {
        mPresenter = presenter;
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

    @OnClick({R.id.topLeft, R.id.btn_menu, R.id.topRight, R.id.btn_connectTeam, R.id.topRight_second})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                ActivityCompat.finishAfterTransition(context);
                break;
            case R.id.btn_menu:
                showDrawerLayout();
                break;
            case R.id.topRight:
                mPresenter.launchShareSDK();
                break;
            case R.id.btn_connectTeam:
                mPresenter.connectWithTeam(mCaseId);
                break;
            case R.id.topRight_second:
                if (isCollect) {
                    mPresenter.cancelCollect(mCaseId);
                } else {
                    mPresenter.collect(mCaseId);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("jam", "psoiton:" + position);
        ctabNav.setCurrentTab(position);
        btnMenu.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
        vpContent.setScanScroll(position == 0 ? false : true);
        int scrollSize = hsvNav.getMaxScrollAmount();
        hsvNav.smoothScrollTo((scrollSize / 6) * (position), 0);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelect(int position) {
        vpContent.setCurrentItem(position);
    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
