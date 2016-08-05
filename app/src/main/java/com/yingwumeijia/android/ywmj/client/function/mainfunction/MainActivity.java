package com.yingwumeijia.android.ywmj.client.function.mainfunction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.utils.ActivityUtils;
import com.yingwumeijia.android.ywmj.client.utils.base.activity.BaseActivity;

/**
 * Created by Jam on 2016/8/4 17:25.
 * Describe:
 */
public class MainActivity extends BaseActivity {

    private CaseListContract.Presenter mCaseListPresenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_act;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CaseListFragment caseListFragment =
                (CaseListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFragment);
        if (caseListFragment == null) {
            //create CaseListFragment
            caseListFragment = CaseListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    caseListFragment,
                    R.id.contentFragment
            );
        }

        //create CaseListPresenter
        if (mCaseListPresenter == null) {
            mCaseListPresenter = new CaseListPresenter(context, caseListFragment);
        }

    }
}
