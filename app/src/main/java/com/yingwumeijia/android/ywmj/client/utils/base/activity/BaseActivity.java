package com.yingwumeijia.android.ywmj.client.utils.base.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jam on 2016/8/1.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ProgressDialog baseProgressDialog;

    protected Activity context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        this.context = this;
    }

    protected abstract
    @LayoutRes
    int getLayoutId();

    protected void showProgresDialog() {
        if (baseProgressDialog == null) {
            baseProgressDialog = new ProgressDialog(context);
        }
        baseProgressDialog.show();
    }

    protected void dismisProgressDialog() {
        if (baseProgressDialog != null && baseProgressDialog.isShowing())
            baseProgressDialog.dismiss();
    }


    protected void showProgresDialog(String message) {
        if (baseProgressDialog == null) {
            baseProgressDialog = new ProgressDialog(context);
        }
        baseProgressDialog.setMessage(message);
        baseProgressDialog.show();
    }
}
