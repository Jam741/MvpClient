package com.yingwumeijia.android.ywmj.client.function.person;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.T;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.utils.base.fragment.BaseFragment;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;
import com.yingwumeijia.android.ywmj.client.utils.net.GlideUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jam on 2016/8/5 18:00.
 * Describe:
 */
public class PersonFragment extends BaseFragment implements PersonContract.View {

    private PersonContract.Presenter mPresenter;
    private View root;
    @Bind(R.id.top_back)
    TextView topBack;
    @Bind(R.id.iv_portrait)
    ImageView ivPortrait;
    @Bind(R.id.portrait_layout)
    RelativeLayout portraitLayout;
    @Bind(R.id.btn_login)
    TextView btnLogin;
    @Bind(R.id.login_out_layout)
    LinearLayout loginOutLayout;
    @Bind(R.id.tv_nike_name)
    TextView tvNikeName;
    @Bind(R.id.tv_collect_count)
    TextView tvCollectCount;
    @Bind(R.id.login_in_layout)
    LinearLayout loginInLayout;
    @Bind(R.id.btn_ed_person)
    RelativeLayout btnEdPerson;
    @Bind(R.id.tab_nav)
    TabLayout tabNav;
    @Bind(R.id.vp_content)
    ViewPager vpContent;

    /**
     * 创建实例
     *
     * @return
     */
    public static PersonFragment newInstance() {

        Bundle args = new Bundle();

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.person_frag, container, false);
            ButterKnife.bind(this, root);
        }
        return root;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_CHANGE_COLLECT_COUNT);
        context.registerReceiver(broadcastReceiver,filter);

        mPresenter.bingPageAdapter(vpContent);
        mPresenter.bingViewPager(vpContent, tabNav);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        context.unregisterReceiver(broadcastReceiver);
    }

    @OnClick({R.id.top_back, R.id.iv_portrait, R.id.btn_login, R.id.btn_ed_person})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.iv_portrait:
                mPresenter.startEditPersonActivity();
                break;
            case R.id.btn_login:
                mPresenter.startLoginActivity();
                break;
            case R.id.btn_ed_person:
                mPresenter.startEditPersonActivity();
                break;
        }
    }

    @Override
    public void showNotloginLayout() {
        loginInLayout.setVisibility(View.GONE);
        loginOutLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoginInLayout() {
        loginInLayout.setVisibility(View.VISIBLE);
        loginOutLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingFail(String msg) {
        T.showShort(context, "数据加载失败");
    }

    @Override
    public void setNikeName(String nikeName) {
        tvNikeName.setText(nikeName);
    }

    @Override
    public void showCollectCount(String collectCount) {
        tvCollectCount.setText(collectCount);
    }

    @Override
    public void setUserPortrait(String userPortrait) {
        GlideUtils.loadCriclePortraitImage(
                context,
                ivPortrait,
                userPortrait
        );
    }

    @Override
    public void finish() {
        ActivityCompat.finishAfterTransition(context);
    }

    @Override
    public void setPresener(PersonContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNetConnectError() {
        showBaseNetConnectError();
    }

    @Override
    public void showProgressBar() {
        showBaseProgresDialog();
    }

    @Override
    public void dismissProgressBar() {
        dismisBaseProgressDialog();
    }


    public static final String ACTION_CHANGE_COLLECT_COUNT = "com.ywmj.android.cliect.collectcount";

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("jam","broad");
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mPresenter.start();
                }
            });
        }
    };
}

