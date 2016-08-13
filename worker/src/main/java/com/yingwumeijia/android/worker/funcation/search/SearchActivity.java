package com.yingwumeijia.android.worker.funcation.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.utils.ActivityUtils;
import com.yingwumeijia.android.worker.utils.base.activity.BaseActivity;


/**
 * Created by Jam on 2016/8/8 15:16.
 * Describe:
 */
public class SearchActivity extends BaseActivity {

    private SearchContract.Presenter mPresenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, SearchActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initFragment
        SearchFragment searchFragment =
                (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.contentFragment);
        if (searchFragment == null) {
            //create fragment
            searchFragment = SearchFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    searchFragment,
                    R.id.contentFragment
            );
        }

        //create Presenter
        if (mPresenter == null) {
            mPresenter = new SearchPresenter(context, searchFragment);
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.search_act;
    }
}
