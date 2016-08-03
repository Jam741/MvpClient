package com.rx.android.jamspeedlibrary.ui.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rx.android.jamspeedlibrary.utils.StarterCommon;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Jam on 2016/5/19.
 */
public class StarterActivity extends AppCompatActivity {

    protected Activity context;
    private StarterCommon starterCommon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        starterCommon = StarterCommon.create(this);
        Timber.d("Activity", "------------------->onCreate:" + this.getClass().getSimpleName());
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }


    @Override
    public void finish() {
        hideSoftInputMethod();
        super.finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        starterCommon.onDestroy();
        starterCommon = null;
    }

    public void showHud() {
        showHud(null);
    }

    public void showHud(int resId) {
        showHud(getString(resId));
    }

    public void showHud(String text) {
        showHud(text, true);
    }

    public void showHud(String text, boolean isCancellable) {
        if (starterCommon != null) {
            starterCommon.showHud(text, isCancellable);
        }
    }

    public void dismisHud() {
        if (starterCommon != null) {
            starterCommon.dismissHud();
        }
    }

    public void hideSoftInputMethod() {
        if (starterCommon != null) {
            starterCommon.hideSoftInputMethod();
        }
    }

    public void showSoftInputMethod() {
        if (starterCommon != null) {
            starterCommon.showSoftInputMethod();
        }
    }

    public boolean isImmActive() {
        return starterCommon != null && starterCommon.isImmActive();
    }

    /**
     * Converts an intent into a {@link android.os.Bundle} suitable for use as fragment arguments.
     */
    public static Bundle intentToFragmentArguments(Intent intent) {
        Bundle arguments = new Bundle();
        if (intent == null) {
            return arguments;
        }

        final Uri data = intent.getData();
        if (data != null) {
            arguments.putParcelable("_uri", data);
        }

        final Bundle extras = intent.getExtras();
        if (extras != null) {
            arguments.putAll(intent.getExtras());
        }

        return arguments;
    }


    /**
     * Converts a fragment arguments bundle into an intent.
     */
    public static Intent fragmentArgumentsToIntent(Bundle arguments) {
        Intent intent = new Intent();
        if (arguments == null) {
            return intent;
        }

        final Uri data = arguments.getParcelable("_uri");
        if (data != null) {
            intent.setData(data);
        }

        intent.putExtras(arguments);
        intent.removeExtra("_uri");
        return intent;
    }

}

