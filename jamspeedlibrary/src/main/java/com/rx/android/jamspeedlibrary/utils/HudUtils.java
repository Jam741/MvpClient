package com.rx.android.jamspeedlibrary.utils;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by Jam on 2016/3/10.
 * KProgressHUD 工具类
 */
public final class HudUtils {

    public static KProgressHUD showHud(Context context, String lable) {
        return showHud(context, lable, false);
    }

    public static KProgressHUD showHud(Context context) {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
                .show();
    }

    public static KProgressHUD showHud(Context context, String lable, boolean isCancelable) {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(lable)
                .setCancellable(isCancelable)
                .setDimAmount(0.5f)
                .show();
    }
}
