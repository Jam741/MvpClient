package com.rx.android.jamspeedlibrary.utils;

import android.app.Activity;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Activity &Fragment 公共工具类
 *
 * @author Jam
 *         create at 2016/5/19 15:11
 */
public class StarterCommon {

    private KProgressHUD mHud;
    private Activity activity;

    public StarterCommon(Activity activity) {
        Preconditions.checkNotNull(activity, "activity == null");
        this.activity = activity;
    }

    public static StarterCommon create(Activity activity) {
        return new StarterCommon(activity);
    }

    public void onDestroy() {
        mHud = null;
        activity = null;
    }

    //hud加载框
    public void showHud(String text, boolean isCancellable) {
        if (!isFinishing()) {
            mHud = HudUtils.showHud(activity, text, isCancellable);
        }
    }

    public void showHud(String text) {
        if (!isFinishing()) {
            showHud(text, false);
        }
    }

    public void showHud(int resId) {
        if (!isFinishing()) {
            showHud(activity.getString(resId));
        }
    }


    public void dismissHud() {
        if (mHud != null && !isFinishing()) {
            mHud.dismiss();
        }
    }

    //keyboard键盘

    /**
     * 隐藏软键盘
     */
    public void hideSoftInputMethod() {
        try {
            if (activity.getCurrentFocus() != null) {
                KeyboardUtils.hide(activity, activity.getCurrentFocus().getWindowToken());
            }
        } catch (Exception e) {
            //Nothing
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInputMethod() {
        try {
            KeyboardUtils.show(activity);
        } catch (Exception e) {
            //Nothing
        }
    }

    public boolean isImmActive() {
        return KeyboardUtils.isActive(activity);
    }

    private boolean isFinishing() {
        return activity == null || activity.isFinishing();
    }

}
