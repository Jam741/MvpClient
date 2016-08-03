package com.rx.android.jamspeedlibrary.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.rx.android.jamspeedlibrary.utils.StarterCommon;

import butterknife.ButterKnife;

/**
 * Created by Jam on 2016/5/30.
 */

public abstract class StarterFragment extends Fragment {

    private StarterCommon starterCommon;

    protected Activity context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        starterCommon = StarterCommon.create(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    protected abstract int getFragmentLayout();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        LogUtil.getInstance().debug("JAM","------------"+starterCommon);
//        starterCommon.onDestroy();
//        starterCommon = null;
    }

    /**
     * View 有效，未被销毁
     *
     * @param view
     * @return
     */
    public boolean viewValid(View view) {
        return getActivity() != null && !isDetached() && view != null;
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

    public void showHud(String text, boolean isCanclelable) {
        if (starterCommon != null) {
            starterCommon.showHud(text, isCanclelable);
        }
    }

    public void dismissHud() {
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
}