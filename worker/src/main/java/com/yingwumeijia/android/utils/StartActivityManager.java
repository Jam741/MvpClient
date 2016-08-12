package com.yingwumeijia.android.utils;

import android.app.Activity;
import android.content.Context;

import com.yingwumeijia.android.MainActivity;
import com.yingwumeijia.android.funcation.casedetails.CaseDetailActivity;
import com.yingwumeijia.android.funcation.edit.PersonInfoActivity;
import com.yingwumeijia.android.funcation.login.LoginActivity;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

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
     */
    public static void startCaseDetailActivity(Context context, int caseId) {
        CaseDetailActivity.start(context, caseId);
    }


    /**
     * 跳转到登陆页面
     *
     * @param context
     */
    public static void startLoginActivity(Context context) {
        LoginActivity.start(context);
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
        PersonInfoActivity.start((Activity) context,
                portraitUrl,
                showName,
                showPhone);

    }

    /**
     * 跳转到聚合回话列表
     *
     * @param context
     */
    public static void startSubConversationListActivity(Context context) {
        if (!UserManager.userPrecondition(context)) return;
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().startSubConversationList(context, Conversation.ConversationType.GROUP);
        }
    }


    public static void startConversation(Context context,String sessionId,String title) {
        if (!UserManager.userPrecondition(context)) return;
        if (RongIM.getInstance()!=null){
            RongIM.getInstance().startConversation(context, Conversation.ConversationType.GROUP,sessionId,title);
        }
    }

}
