package com.yingwumeijia.android.worker.im.listener;

import android.text.TextUtils;
import android.util.Log;

import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.yingwumeijia.android.worker.WorkerApp;
import com.yingwumeijia.android.worker.utils.constants.Constant;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by Jam on 2016/8/3 16:13.
 * Describe:
 */
public class MyConnectionStatusListener implements RongIMClient.ConnectionStatusListener{

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {
        switch (connectionStatus){
            case CONNECTED://连接成功。
                LogUtil.getInstance().debug("连接成功");
                break;
            case DISCONNECTED://断开连接。
                LogUtil.getInstance().debug("断开连接");
                if (!TextUtils.isEmpty(Constant.getIMToken(WorkerApp.appContext()))) {
                    connect(Constant.getIMToken(WorkerApp.appContext()));
                }
                break;
            case CONNECTING://连接中。
                LogUtil.getInstance().debug("连接中");
                break;
            case NETWORK_UNAVAILABLE://网络不可用。
                LogUtil.getInstance().debug("网络不可用");
                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                LogUtil.getInstance().debug("用户账户在其他设备登录");
                break;
        }
    }


    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    private void connect(String token) {

        if (WorkerApp.appContext().getApplicationInfo().packageName.equals(WorkerApp.getCurProcessName(WorkerApp.appContext().getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.d("LoginActivity", "-========================-onTokenIncorrect : ");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    Log.d("LoginActivity", "--=======================-onSuccess : " + userid);

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d("LoginActivity", "-=========================-onError : " + errorCode);
                }
            });
        }
    }
}