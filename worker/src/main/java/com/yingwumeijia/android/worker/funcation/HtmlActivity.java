package com.yingwumeijia.android.worker.funcation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.utils.ActivityUtils;
import com.yingwumeijia.android.worker.utils.base.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jam on 2016/8/11 14:41.
 * Describe:
 */
public class HtmlActivity extends BaseActivity {

    @Bind(R.id.topTitle)
    TextView topTitle;
    @Bind(R.id.topLeft)
    TextView topLeft;
    private String mTitle;

    private String mUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.common_title_act;
    }


    public static void start(Context context, String title, String url) {
        Intent starter = new Intent(context, HtmlActivity.class);
        starter.putExtra("KEY_TITLE", title);
        starter.putExtra("KEY_URL", url);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        //get intent data
        getIntentData();

        //initACtionBar
        initActionBar();

        //init web fragment
        initFragment();
    }

    private void initFragment() {
        HtmlFragment htmlFragment =
                (HtmlFragment) getSupportFragmentManager().findFragmentById(R.id.contentFragment);
        if (htmlFragment == null) {
            htmlFragment = HtmlFragment.newInstance(mUrl);

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    htmlFragment,
                    R.id.contentFragment
            );
        }
    }

    private void initActionBar() {
        topTitle.setText(mTitle);
        TextViewUtils.setDrawableToLeft(context, topLeft, R.mipmap.back_ico);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("KEY_TITLE");
        mUrl = intent.getStringExtra("KEY_URL");
    }

    @OnClick(R.id.topLeft)
    public void onClick() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
