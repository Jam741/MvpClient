package com.rx.android.jamspeedlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;

import com.rx.android.jamspeedlibrary.StarterApplication;

import java.lang.reflect.Method;


/**
 * Created by Jam on 2016/3/14.
 * 屏幕工具
 */
public class ScreenUtils {

    //屏幕度量类
    private static DisplayMetrics metrics;

    public static DisplayMetrics getDisplayMetrics() {
        if (metrics == null) {
            metrics = StarterApplication.appResources().getDisplayMetrics();
        }
        return metrics;
    }

    /**
     * 获取屏幕密度
     *
     * @return
     */
    public static float getDensity() {
        if (metrics == null) {
            getDisplayMetrics();
        }
        return metrics.scaledDensity;
    }

    /**
     * 获取屏幕宽度像素
     *
     * @return
     */
    public static int getScreenWidth() {
        if (metrics == null) {
            getDisplayMetrics();
        }
        return metrics.widthPixels;
    }

    public static int getScreenHeight() {
        if (metrics == null) {
            getDisplayMetrics();
        }
        return metrics.heightPixels;
    }

    public static int px2db(float pxValue) {
        if (metrics == null) {
            getDisplayMetrics();
        }
        return (int) (pxValue / metrics.density + 0.5f);
    }

    public static int dp2px(float dpValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2sp(float pxValue) {
        if (metrics == null) {
            getDisplayMetrics();
        }
        return (int) (pxValue / metrics.scaledDensity + 0.5f);
    }

    public static int sp2px(float spValue) {
        if (metrics == null) {
            getDisplayMetrics();
        }
        return (int) (spValue * metrics.scaledDensity + 0.5f);
    }

    public static float getTextLength(float textSize, String text) {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        return paint.measureText(text);
    }

    /**
     * 获取实际屏幕高度
     * 如 1920 * 1080
     *
     * @param activity
     * @return
     */
    public static int[] getRealMetrics(Activity activity) {
        int[] dpi = new int[2];
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes") Class c;
        try {
            c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi[0] = dm.widthPixels;
            dpi[1] = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 获取状态栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }
}
