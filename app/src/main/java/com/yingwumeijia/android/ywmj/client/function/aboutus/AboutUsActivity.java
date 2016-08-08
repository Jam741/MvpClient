package com.yingwumeijia.android.ywmj.client.function.aboutus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.AppUtils;
import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.utils.base.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jam on 2016/8/8 10:34.
 * Describe:
 */
public class AboutUsActivity extends BaseActivity {

    @Bind(R.id.topTitle)
    TextView topTitle;
    @Bind(R.id.topLeft)
    TextView topLeft;
    @Bind(R.id.topLeft_second)
    TextView topLeftSecond;
    @Bind(R.id.topRight)
    TextView topRight;
    @Bind(R.id.toolbar)
    RelativeLayout toolbar;
    @Bind(R.id.tv_version_name)
    TextView tvVersionName;
    @Bind(R.id.btn_function_describe)
    LinearLayout btnFunctionDescribe;

    public static void start(Context context) {
        Intent starter = new Intent(context, AboutUsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.about_us_act;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        initActionBar();
    }

    private void initActionBar() {
        topTitle.setText("关于鹦鹉美家");
        TextViewUtils.setDrawableToLeft(context, topLeft, R.mipmap.back_ico);
        tvVersionName.setText(AppUtils.getAppName(context) + AppUtils.getVersionName(context));    }

    @OnClick({R.id.topLeft, R.id.btn_function_describe})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                ActivityCompat.finishAfterTransition(context);
                break;
            case R.id.btn_function_describe:

                break;
        }
    }
}
