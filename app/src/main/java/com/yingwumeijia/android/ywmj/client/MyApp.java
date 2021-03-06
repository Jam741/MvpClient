package com.yingwumeijia.android.ywmj.client;

import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.rx.android.jamspeedlibrary.StarterApplication;
import com.rx.android.jamspeedlibrary.utils.FakeCrashLibrary;
import com.yingwumeijia.android.ywmj.client.im.listener.MyConnectionStatusListener;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;
import com.yingwumeijia.android.ywmj.client.utils.net.retrofit.RetrofitBuilder;

import io.rong.common.WakeLockUtils;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.utils.SystemUtils;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.model.Conversation;
import timber.log.Timber;

/**
 * Created by Jam on 2016/8/1.
 * jamisonline.he@gmail.com
 */
public class MyApp extends StarterApplication {


//    private static class ApiServiceHolder {
//        private static final ApiService API_SERVICE = null;
////                RetrofitBuilder.get().retrofit().create(ApiService.class);
//    }
//
//    public static ApiService getApiService() {
//        return ApiServiceHolder.API_SERVICE;
//    }
//


    @Override
    public void onCreate() {
        super.onCreate();

        //createRetrofit;

        initTimber();


//        RongIM.init(getApplicationContext(), Constant.RONG_CLOUD_APP_KEY_TEST);
//
//        initRongClound(null);

    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }


    /**
     * 初始化融云
     */
    private void initRongClound(String imKey) {

        /**
         *
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
            RongIM.init(this);
            /**
             * IMKit SDK调用第一步 初始化
             */


//            if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
//
//                DemoContext.init(this);
//            }
            //扩展功能自定义
            InputProvider.ExtendProvider[] provider = {
                    new ImageInputProvider(RongContext.getInstance()),//图片
                    new CameraInputProvider(RongContext.getInstance()),//相机
                    new LocationInputProvider(RongContext.getInstance()),//地理位置
//                    new VoIPInputProvider(RongContext.getInstance()),// 语音通话
//                     new ContactsProvider(RongContext.getInstance())//自定义通讯录
            };
//            RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.GROUP, provider);
            RongIM.setConnectionStatusListener(new MyConnectionStatusListener());
        }
    }

    /**
     * 初始化Timber
     */
    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    /**
     * A tree which logs important information for crash reporting.
     * 日志报告
     */
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
