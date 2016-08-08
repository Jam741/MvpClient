package com.yingwumeijia.android.ywmj.client.function.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.T;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.utils.base.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jam on 2016/8/8 10:45.
 * Describe:
 */
public class SettingFragment extends BaseFragment implements SettingContract.View {

    private View root;
    @Bind(R.id.btn_set_pwd)
    LinearLayout btnSetPwd;
    @Bind(R.id.tv_cache)
    TextView tvCache;
    @Bind(R.id.btn_clear_cache)
    LinearLayout btnClearCache;
    @Bind(R.id.btn_service_agreement)
    LinearLayout btnServiceAgreement;
    @Bind(R.id.btn_aboout_us)
    LinearLayout btnAbooutUs;
    @Bind(R.id.btn_login_out)
    TextView btnLoginOut;
    private SettingContract.Presenter mPresenter;

    public static SettingFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(root == null){
         root =inflater.inflate(R.layout.setting_frag, container, false);
            ButterKnife.bind(this, root);
        }
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void showLoginOutFail(String msg) {
        T.showShort(context,msg);
    }

    @Override
    public void showLoginOutButton() {
        btnLoginOut.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoginOutButton() {
        btnLoginOut.setVisibility(View.GONE);
    }

    @Override
    public void showCurrentCache(String cacheSize) {
        tvCache.setText(cacheSize);
    }

    @Override
    public void showClearCacheDialog() {
        AlertDialog builder  = new AlertDialog.Builder(context)
                .setMessage(R.string.dialog_clear_cache)
                .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.clearCache();
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    @Override
    public void setPresener(SettingContract.Presenter presenter) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_set_pwd, R.id.btn_clear_cache, R.id.btn_service_agreement, R.id.btn_aboout_us, R.id.btn_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set_pwd:
                mPresenter.startSetPassword();
                break;
            case R.id.btn_clear_cache:
                showClearCacheDialog();
                break;
            case R.id.btn_service_agreement:
                mPresenter.startAgreementActivity();
                break;
            case R.id.btn_aboout_us:
                mPresenter.startAgreementActivity();
                break;
            case R.id.btn_login_out:
                mPresenter.loginOut();
                break;
        }
    }
}
