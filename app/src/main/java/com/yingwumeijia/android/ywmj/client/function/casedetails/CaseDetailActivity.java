package com.yingwumeijia.android.ywmj.client.function.casedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;

import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.utils.base.activity.BaseActivity;

/**
 * Created by Jam on 2016/8/9 11:30.
 * Describe:
 */
public class CaseDetailActivity extends BaseActivity implements CaseDetailContract.View {


    private CaseDetailContract.Presenter mPresenter;
    private int mCaseId;

    public static void start(Context context, int caseId) {
        Intent starter = new Intent(context, CaseDetailActivity.class);
        starter.putExtra("KEY_CASE_ID", caseId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get data from intent
        getIntentData();

        if (mPresenter == null) {
            mPresenter = new CaseDetailPresenter(this, context);
        }
        mPresenter.start();
    }

    private void getIntentData() {
        mCaseId = getIntent().getIntExtra("KEY_CASE_ID", 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.case_detail_act;
    }

    @Override
    public void showLoadDataFail(String msg) {

    }

    @Override
    public void getViewPager() {

    }

    @Override
    public void showNavMenuButton() {

    }

    @Override
    public void hideNavMenuButton() {

    }

    @Override
    public void showDrawerLayout() {

    }

    @Override
    public void clostDrawerLayout() {

    }

    @Override
    public FragmentManager getMyFragmentManager() {
        return null;
    }

    @Override
    public RecyclerView getNavRecyclerView() {
        return null;
    }

    @Override
    public void bindAdapterForTab() {

    }

    @Override
    public void setPresener(CaseDetailContract.Presenter presenter) {

    }

    @Override
    public void showNetConnectError() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }
}
