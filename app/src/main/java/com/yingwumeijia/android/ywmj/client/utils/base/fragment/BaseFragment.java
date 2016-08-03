package com.yingwumeijia.android.ywmj.client.utils.base.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.rx.android.jamspeedlibrary.utils.T;
import com.yingwumeijia.android.ywmj.client.R;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 * Fragment 的父类
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 上下文
     */
    protected Activity context;

    /**
     * 默认加载进度条
     */
    protected ProgressDialog baseProgressDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.context = getActivity();
    }


    protected void showBaseProgresDialog() {
        if (baseProgressDialog == null) {
            baseProgressDialog = new ProgressDialog(context);
        }
        baseProgressDialog.show();
    }

    protected void dismisBaseProgressDialog() {
        if (baseProgressDialog != null && baseProgressDialog.isShowing())
            baseProgressDialog.dismiss();
    }


    protected void showBaseProgresDialog(String message) {
        if (baseProgressDialog == null) {
            baseProgressDialog = new ProgressDialog(context);
        }
        baseProgressDialog.setMessage(message);
        baseProgressDialog.show();
    }

    protected void showBaseNetConnectError(){
        T.showShort(context, R.string.net_connect_error);
    }
}
