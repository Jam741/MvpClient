package com.yingwumeijia.android.ywmj.client.utils;

import android.content.Context;

import com.yingwumeijia.android.ywmj.client.function.mainfunction.MainActivity;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public class StartActivityManager {


    /**
     * 跳转到主页面
     *
     * @param context
     */
    public static void startMain(Context context) {
        MainActivity.start(context);
    }

    /**
     * 跳转到用户服务页面
     *
     * @param context
     */
    public static void startAgreementActivity(Context context) {

    }

    /**
     * 跳转到案例详情页面
     *
     * @param context
     * @param caseId
     */
    public static void startCaseDetailActivity(Context context, int caseId) {

    }


    /**
     * 跳转到登陆页面
     *
     * @param context
     */
    public static void startLoginActivity(Context context) {

    }


    /**
     * 跳转到编辑个人信息页面
     *
     * @param context
     * @param portraitUrl
     * @param showName
     * @param showPhone
     */
    public static void startEditPersonActivity(Context context,
                                               String portraitUrl,
                                               String showName,
                                               String showPhone) {

    }

}
