package com.rx.android.jamspeedlibrary;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.rx.android.jamspeedlibrary.utils.AppInfo;
import com.rx.android.jamspeedlibrary.utils.FakeCrashLibrary;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;

/**
 * 所有项目Application的父类
 *
 * @author Jam
 *         create at 2016/5/19 15:13
 */
public class StarterApplication extends Application {

    private static volatile Context sAppContent;
    private static volatile StarterApplication mInstance;
    private static volatile Handler sAppHandler;
    private static volatile AppInfo mAppInfo;

//    public static RefWatcher getRefWatcher(Context context) {
//        StarterApplication application = (StarterApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }
//
//    private RefWatcher refWatcher;


    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }


    private void initialize() {
//        refWatcher = LeakCanary.install(this);
        AndroidThreeTen.init(this);
//        LeakCanary.install(this);
//        refWatcher = installLeakCanary();
        mInstance = this;
        sAppContent = getApplicationContext();
        sAppHandler = new Handler(sAppContent.getMainLooper());
    }

//    protected RefWatcher installLeakCanary() {
//        return RefWatcher.DISABLED;
//    }

    public static AppInfo appInfo() {
        if (mAppInfo == null) {
            mAppInfo = new AppInfo(appContext());
        }
        return mAppInfo;
    }

    public static Context appContext() {
        return sAppContent;
    }

    public static Resources appResources() {
        return appContext().getResources();
    }

    /**
     * application handler
     */
    public static Handler appHandler() {
        return sAppHandler;
    }

    /**
     * current application instance
     */
    /**
     * @return current application instance
     */
    public static StarterApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        sAppContent = null;
        mInstance = null;
        sAppHandler = null;
        mAppInfo = null;
    }

}
