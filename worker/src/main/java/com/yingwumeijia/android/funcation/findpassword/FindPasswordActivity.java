package com.yingwumeijia.android.funcation.findpassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.R;
import com.yingwumeijia.android.utils.ActivityUtils;
import com.yingwumeijia.android.utils.base.activity.BaseActivity;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public class FindPasswordActivity extends BaseActivity implements View.OnClickListener {

    private FindPasswordContract.Presenter mFindPasswordPresenter;

    public static void start(Context context, String phone) {
        Intent starter = new Intent(context, FindPasswordActivity.class);
        starter.putExtra("phone", phone);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set up actionbar
        setUpActionBar();


        //init fragment
        initFragment();
    }

    private void initFragment() {
        FindpasswordFragment findpasswordFragment =
                (FindpasswordFragment) getSupportFragmentManager().findFragmentById(R.id.contentFragment);
        if (findpasswordFragment == null) {
            //create fragment
            findpasswordFragment = FindpasswordFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    findpasswordFragment,
                    R.id.contentFragment);
        }

        //create presenter
        if (mFindPasswordPresenter == null) {
            mFindPasswordPresenter = new FindPasswordPresenter(context, findpasswordFragment);
        }
    }

    private void setUpActionBar() {
        TextView topTitle = (TextView) findViewById(R.id.topTitle);
        TextView topLeft = (TextView) findViewById(R.id.topLeft);
        topTitle.setText("注册新账号");
        TextViewUtils.setDrawableToLeft(context, topLeft, R.mipmap.back_ico);
        topLeft.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.findpassword_act;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                ActivityCompat.finishAfterTransition(this);
                break;
        }
    }
}
