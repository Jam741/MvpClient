package com.yingwumeijia.android.worker.im;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;


import com.yingwumeijia.android.worker.DemoContext;
import com.yingwumeijia.android.worker.im.infoprovider.MyGroupInfoProvider;
import com.yingwumeijia.android.worker.im.infoprovider.MyUserInfoProvider;
import com.yingwumeijia.android.worker.im.listener.MyConnectionStatusListener;
import com.yingwumeijia.android.worker.im.map.AMAPLocationActivity;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imkit.widget.provider.TextInputProvider;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.message.LocationMessage;

/**
 * Created by Jam on 16/8/21 下午9:56.
 * Describe:
 */
public class RongCloudEvent implements Handler.Callback ,RongIM.LocationProvider,RongIM.ConversationBehaviorListener {

    private static final String TAG = RongCloudEvent.class.getSimpleName();

    private static RongCloudEvent mRongCloudInstance;

    private Context mContext;

    private Handler mHandler;


    /**
     * 初始化 RongCloud.
     *
     * @param context 上下文。
     */
    public static void init(Context context) {

        if (mRongCloudInstance == null) {

            synchronized (RongCloudEvent.class) {

                if (mRongCloudInstance == null) {
                    mRongCloudInstance = new RongCloudEvent(context);
                }
            }
        }
    }

    /**
     * 构造方法。
     *
     * @param context 上下文。
     */
    private RongCloudEvent(Context context) {
        mContext = context;
        initDefaultListener();
        mHandler = new Handler(this);
        setOtherListener();
    }


    /**
     * 获取RongCloud 实例。
     *
     * @return RongCloud。
     */
    public static RongCloudEvent getInstance() {
        return mRongCloudInstance;
    }




    private void initDefaultListener() {
        RongIM.setUserInfoProvider(new MyUserInfoProvider(), true);//设置用户信息提供者。
        RongIM.setGroupInfoProvider(new MyGroupInfoProvider(), true);//设置群组信息提供者。
        RongIM.setConversationBehaviorListener(this);//设置会话界面操作的监听器。
        RongIM.setLocationProvider(this);//设置地理位置提供者,不用位置的同学可以注掉此行代码
//        RongIM.setConversationListBehaviorListener(this);//会话列表界面操作的监听器
//        RongIM.getInstance().setSendMessageListener(this);//设置发出消息接收监听器.

//        RongIM.setGroupUserInfoProvider(this, true);
    }



    private void setOtherListener() {
//        RongIM.setOnReceiveMessageListener(this);//设置消息接收监听器。
        RongIM.setConnectionStatusListener(new MyConnectionStatusListener());//设置连接状态监听器。

        TextInputProvider textInputProvider = new TextInputProvider(RongContext.getInstance());
        RongIM.setPrimaryInputProvider(textInputProvider);

//        扩展功能自定义
        InputProvider.ExtendProvider[] provider = {
                new ImageInputProvider(RongContext.getInstance()),//图片
                new LocationInputProvider(RongContext.getInstance()),//地理位置
        };

        RongIM.resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider);
    }


    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }




    /**
     * 位置信息提供者:LocationProvider 的回调方法，打开第三方地图页面。
     *
     * @param context  上下文
     * @param callback 回调
     */
    @Override
    public void onStartLocation(Context context, LocationCallback callback) {
        /**
         * demo 代码  开发者需替换成自己的代码。
         */
        DemoContext.getInstance().setLastLocationCallback(callback);

        Intent intent = new Intent(context, AMAPLocationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, io.rong.imlib.model.Message message) {

        if (message.getContent() instanceof LocationMessage) {
            Intent intent = new Intent(context, AMAPLocationActivity.class);
            intent.putExtra("location", message.getContent());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, io.rong.imlib.model.Message message) {
        return false;
    }
}
