package com.yingwumeijia.android.ywmj.client.function.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.ywmj.client.function.findpassword.FindPasswordPresenter;
import com.yingwumeijia.android.ywmj.client.function.findpassword.FindpasswordFragment;
import com.yingwumeijia.android.ywmj.client.function.register.RegisterFragment;
import com.yingwumeijia.android.ywmj.client.function.register.RegisterPresenter;
import com.yingwumeijia.android.ywmj.client.utils.base.activity.BaseActivity;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.utils.ActivityUtils;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private LoginContract.Presenter mLoginPresenter;
    private LoginFragment loginFragment;
    private RegisterPresenter mRegisterPresenter;
    private RegisterFragment registerFragment;
    private FindPasswordPresenter mFindpasswordPresenter;
    private FindpasswordFragment findpasswordFragment;

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

//    public void switchFragment(int fragemntPosition) {
//        Fragment mCurrentFragment = null;
//        switch (fragemntPosition) {
//            case 0:
//                if (findpasswordFragment == null) {
//                    findpasswordFragment = FindpasswordFragment.newInstance();
//                }
//                if (mFindpasswordPresenter == null) {
//                    mFindpasswordPresenter = new FindPasswordPresenter(context, findpasswordFragment);
//                }
//                mCurrentFragment = findpasswordFragment;
//                break;
//            case 1:
//                if (loginFragment == null) {
//                    loginFragment = LoginFragment.newInstance();
//                }
//                if (mLoginPresenter == null) {
//                    mLoginPresenter = new LoginPresenter(context, loginFragment);
//                }
//                mCurrentFragment = loginFragment;
//                break;
//            case 2:
//                if (registerFragment == null) {
//                    registerFragment = RegisterFragment.newInstance();
//                }
//                if (mRegisterPresenter == null) {
//                    mRegisterPresenter = new RegisterPresenter(context, registerFragment);
//                }
//                mCurrentFragment = registerFragment;
//                break;
//        }
//
//        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(),
//                mCurrentFragment,
//                R.id.contentFragment);
//    }

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
        TextView topTitle = (TextView) findViewById(R.id.topTitle);
        TextView topLeft = (TextView) findViewById(R.id.topLeft);
        topTitle.setText("账号登录");
        TextViewUtils.setDrawableToLeft(context, topLeft, R.mipmap.back_ico);
        topLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                ActivityCompat.finishAfterTransition(context);
                break;
        }
    }
}
