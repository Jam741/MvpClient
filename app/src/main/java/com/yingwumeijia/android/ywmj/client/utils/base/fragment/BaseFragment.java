package com.yingwumeijia.android.ywmj.client.utils.base.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.rx.android.jamspeedlibrary.utils.T;
import com.yingwumeijia.android.ywmj.client.R;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 * Fragment 的父类
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment-------> :";

    /**
     * 上下文
     */
    protected Activity context;

    /**
     * 默认加载进度条
     */
    protected ProgressDialog baseProgressDialog;

    protected KProgressHUD progressBar;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        this.context = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    protected void showBaseProgresDialog() {
        if (progressBar == null) {
            progressBar = KProgressHUD.create(context);
        }
        progressBar.show();
    }

    protected void dismisBaseProgressDialog() {
        if (progressBar != null && progressBar.isShowing())
            progressBar.dismiss();
    }


    protected void showBaseProgresDialog(String message) {
        if (baseProgressDialog == null) {
            baseProgressDialog = new ProgressDialog(context);
        }
        baseProgressDialog.setMessage(message);
        baseProgressDialog.show();
    }

    protected void showBaseNetConnectError() {
        T.showShort(context, R.string.net_connect_error);
    }
}
