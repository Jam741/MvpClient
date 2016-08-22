package com.yingwumeijia.android.ywmj.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.rong.imkit.RongIM;

/**
 * Created by Jam on 16/8/21 下午9:51.
 * Describe:
 */
public class DemoContext {

    private static DemoContext mDemoContext;
    private Context mContext;
    private SharedPreferences mPreferences;
    private RongIM.LocationProvider.LocationCallback mLastLocationCallback;


    public static void init(Context context) {
        mDemoContext = new DemoContext(context);
    }

    public static DemoContext getInstance() {

        if (mDemoContext == null) {
            mDemoContext = new DemoContext();
        }
        return mDemoContext;
    }

    private DemoContext() {

    }

    public SharedPreferences getSharedPreferences() {
        return mPreferences;
    }


    private DemoContext(Context context) {
        mContext = context;
        mDemoContext = this;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public RongIM.LocationProvider.LocationCallback getLastLocationCallback() {
        return mLastLocationCallback;
    }

    public void setLastLocationCallback(RongIM.LocationProvider.LocationCallback lastLocationCallback) {
        this.mLastLocationCallback = lastLocationCallback;
    }


}
