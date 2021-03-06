package com.yingwumeijia.android.worker.utils;

import android.content.Context;

import com.rx.android.jamspeedlibrary.utils.SPUtils;
import com.yingwumeijia.android.worker.WorkerApp;
import com.yingwumeijia.android.worker.data.bean.UserBean;
import com.yingwumeijia.android.worker.utils.constants.Constant;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public class UserManager {

    private static final String EMPTY_STRING = null;
    private static final int EMPTY_INTEGER = 0;

    public static void saveRongIMToken(String token) {
        SPUtils.put(WorkerApp.appContext(), "rong_im_token", token);
    }

    public static void getRongIMToken() {
        SPUtils.get(WorkerApp.appContext(), "rong_im_token", EMPTY_STRING);
    }

    public static void saveUserPhone(String phone) {
        if (phone != null)
            SPUtils.put(WorkerApp.appContext(), "user_phone", phone);
    }

    public static void getUserPhone() {
        SPUtils.get(WorkerApp.appContext(), "user_phone", EMPTY_STRING);
    }

    public static void saveUserPassword(String password) {
        SPUtils.put(WorkerApp.appContext(), "user_password", password);
    }

    public static void geUserPassword() {
        SPUtils.get(WorkerApp.appContext(), "user_password", EMPTY_STRING);
    }

    public static void saveUserId(int userId) {
        SPUtils.put(WorkerApp.appContext(), "user_id", userId);
    }

    public static void getUserId() {
        SPUtils.get(WorkerApp.appContext(), "user_id", EMPTY_INTEGER);
    }


    public static void saveUserInfo(UserBean userBean) {
        Constant.setLoginIn(WorkerApp.appContext());
        saveUserId(userBean.getId());
        // saveUserPhone(userBean.getPhone());
    }

    /**
     * 执行用户相关操作的先决条件
     *
     * @param context
     */
    public static boolean userPrecondition(Context context) {
        if (Constant.isLogin(context)) {
            return true;
        } else {
            StartActivityManager.startLoginActivity(context);
            return false;
        }
    }

}
