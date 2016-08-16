package com.yingwumeijia.android.worker.funcation.caselist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.utils.ActivityUtils;
import com.yingwumeijia.android.worker.utils.base.activity.BaseActivity;

/**
 * Created by Jam on 16/8/13 下午3:16.
 * Describe:
 */
public class CaseListActivity extends BaseActivity {

    private CaseListContract.Presenter mPresenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, CaseListActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_no_title_layout;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CaseListFragment caseListFragment =
                (CaseListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFragment);

        if (caseListFragment == null) {
            //create fragment
            caseListFragment = CaseListFragment.newInstance();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    caseListFragment,
                    R.id.contentFragment
            );
        }

        //create presenter
        if (mPresenter == null){
            mPresenter = new CaseListPresenter(context,caseListFragment);
        }
    }
}
