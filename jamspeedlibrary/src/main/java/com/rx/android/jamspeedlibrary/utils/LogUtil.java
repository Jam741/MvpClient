package com.rx.android.jamspeedlibrary.utils;

import android.util.Log;

/**
 * Created by Jam on 2016/3/9.
 * 单例模式 打印日志工具
 */
public class LogUtil {

    public static final int DEBUG = 0;
    public static final int INFO = 1;
    public static final int ERROR = 2;
    public static final int NOTHING = 3;
    public static final String TAG = "default_tag";

    public int leve = DEBUG;

    public void debug(String s) {
        if (leve <= DEBUG) {
            Log.d(TAG, ":------------->" + s);
        }
    }

    public void debug(String tag, String s) {
        if (leve <= DEBUG) {
            Log.d(tag, ":------------->" + s);
        }
    }

    public void info(String s) {
        if (leve <= INFO) {
            Log.i(TAG, ":------------->" + s);
        }
    }

    public void info(String tag, String s) {
        if (leve <= INFO) {
            Log.i(tag, ":------------->" + s);
        }
    }

    public void error(String s) {
        if (leve <= ERROR) {
            Log.e(TAG, ":------------->" + s);
        }
    }

    public void error(String tag, String s) {
        if (leve <= ERROR) {
            Log.e(tag, ":------------->" + s);
        }
    }

    private LogUtil() {
    }

    private static LogUtil logUtil;

    public static LogUtil getInstance() {
        if (logUtil == null) {
            synchronized (LogUtil.class) {
                logUtil = new LogUtil();
            }
        }
        return logUtil;
    }
}
