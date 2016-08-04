package com.yingwumeijia.android.ywmj.client.function.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.utils.ActivityUtils;
import com.yingwumeijia.android.ywmj.client.utils.base.activity.BaseActivity;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private TextView topTitle;
    private TextView topLeft;
    private RegisterPresenter mRegisterPresenter;
    private Intent mIntent;

    public static void start(Context context, String phone) {
        Intent starter = new Intent(context, RegisterActivity.class);
        starter.putExtra("phone", phone);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentData();

        setUpActionBar();

        //初始化Fragment
        initFragment();

    }

    private void getIntentData() {
        mIntent = getIntent();
        String phone = mIntent.getStringExtra("phone");
    }

    private void initFragment() {
        RegisterFragment registerFragment =
                (RegisterFragment) getSupportFragmentManager().findFragmentById(R.id.contentFragment);

        if (registerFragment == null) {
            //create Fragment
            registerFragment = RegisterFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    registerFragment,
                    R.id.contentFragment
            );
        }

        //create presenter
        if (mRegisterPresenter == null) {
            mRegisterPresenter = new RegisterPresenter(context, registerFragment);
        }
    }

    private void setUpActionBar() {
        topTitle = (TextView) findViewById(R.id.topTitle);
        topLeft = (TextView) findViewById(R.id.topLeft);
        topTitle.setText("注册新账号");
    }


    @Override
    protected int getLayoutId() {
        return R.layout.login_act;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.topLeft:
                ActivityCompat.finishAfterTransition(this);
                break;
        }
    }
}
