package com.rx.android.jamspeedlibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * @author Jam
 *         create at 2016/5/19 17:43
 */
public final class AppInfo {

    public String os;
    public String deviceName;
    public String deviceId;
    public String version;
    public String versionCode;
    public String channel;
    public int screenWidth;
    public int screenHeight;

    public AppInfo(Context context) {
        initDeviceId(context);
        initVersion(context);
        initChannel(context);
        initOs();
        initDeviceName();
        initMetrics();
    }

    private void initDeviceName() {
        this.deviceName = Build.DEVICE;
    }

    private void initOs() {
        this.os = Build.MODEL + "," + Build.VERSION.SDK_INT + "," + Build.VERSION.RELEASE;
    }

    /**
     * 初始化尺度
     */
    private void initMetrics() {
        this.screenWidth = ScreenUtils.getScreenWidth();
        this.screenHeight = ScreenUtils.getScreenHeight();
    }

    private void initDeviceId(Context context) {
        this.deviceId = DeviceID.getDeviceID(context);
    }

    private void initVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = "";
        String cdde = "";
        if (packageInfo != null) {
            version = packageInfo.versionName;
            cdde = Integer.valueOf(packageInfo.versionCode).toString();
        }

        this.version = version;
        this.versionCode = cdde;
    }

    private void initChannel(Context context) {
        this.channel = AndroidUtilities.getMetaData(context, "UMENG_CHANNEL");
    }
}
