package com.yingwumeijia.android.worker.funcation.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.utils.ActivityUtils;
import com.yingwumeijia.android.worker.utils.base.activity.BaseActivity;

/**
 * Created by Jam on 2016/8/9 12:14.
 * Describe:
 */
public class SplashActivity extends BaseActivity {

    private SplashContract.Presenter mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init fragment
        SplashFragment splashFragment =
                (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.contentFragment);
        if (splashFragment == null) {
            //create fragment
            splashFragment = SplashFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    splashFragment,
                    R.id.contentFragment
            );
        }

        //create presenter
        if (mPresenter == null) {
            mPresenter = new SplashPresenter(splashFragment, context);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.splash_act;
    }
}
