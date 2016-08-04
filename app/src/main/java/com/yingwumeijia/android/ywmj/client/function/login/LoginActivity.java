package com.yingwumeijia.android.ywmj.client.function.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.ywmj.client.utils.base.activity.BaseActivity;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.utils.ActivityUtils;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private LoginPresenter mLoginPresenter;

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

        //setup the ActionBar
        setUpToolBar();

        //init content fragment
        initFragment();

    }

    private void initFragment() {
        LoginFragment loginFragment =
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
       TextView topTitle = (TextView) findViewById(R.id.topTitle);
        TextView    topLeft = (TextView) findViewById(R.id.topLeft);
        topTitle.setText("账号登录");
        TextViewUtils.setDrawableToLeft(context,topLeft,R.mipmap.back_ico);
    }

    @Override
    public void onClick(View view) {

    }
}
