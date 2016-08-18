package com.yingwumeijia.android.worker.funcation.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.utils.ActivityUtils;
import com.yingwumeijia.android.worker.utils.base.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.topTitle)
    TextView topTitle;
    @Bind(R.id.topLeft)
    TextView topLeft;
    private LoginContract.Presenter mLoginPresenter;
    private LoginFragment loginFragment;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_act;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        //setup the ActionBar
        setUpToolBar();

        //init content fragment
        initFragment();

    }

    private void initFragment() {
        loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFragment);
        if (loginFragment == null) {
            //create Fragment
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    loginFragment,
                    R.id.contentFragment);
        }

        if (mLoginPresenter == null) {
            mLoginPresenter = new LoginPresenter(context, loginFragment);
        }
    }

    private void setUpToolBar() {
        topTitle.setText("账号登录");
        TextViewUtils.setDrawableToLeft(context, topLeft, R.mipmap.back_ico);
        topLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                ActivityCompat.finishAffinity(context);
                break;
        }
    }
}
