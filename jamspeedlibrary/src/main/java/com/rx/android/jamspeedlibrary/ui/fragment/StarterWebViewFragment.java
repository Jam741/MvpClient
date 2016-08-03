package com.rx.android.jamspeedlibrary.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;

import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.rx.android.jamspeedlibrary.utils.StarterCommon;

import butterknife.ButterKnife;

/**
 * Created by Jam on 16/6/30 下午2:55.
 * Describe:
 */
public abstract class StarterWebViewFragment extends WebViewFragment {


    private StarterCommon starterCommon;

    protected Activity context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        starterCommon = StarterCommon.create(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.getInstance().debug("JAM","------------"+starterCommon);
        starterCommon.onDestroy();
        starterCommon = null;
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
