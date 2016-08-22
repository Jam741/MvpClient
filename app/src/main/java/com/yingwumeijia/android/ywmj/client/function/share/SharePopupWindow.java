package com.yingwumeijia.android.ywmj.client.function.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.example.popuwindowlibrary.BasePopupWindow;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.utils.Utility;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.bean.ShareModel;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jam on 2016/8/10 15:45.
 * Describe:
 */
public class SharePopupWindow extends BasePopupWindow implements View.OnClickListener,
        IWeiboHandler.Response {
    private View shareLayout;

    private ViewHolder viewHolder;

    private ShareModel mShareModel;

    /*和微博通讯的api接口*/
    IWeiboShareAPI mWeiboShareAPI;

    private byte[] bitmapBytes;

    /*和微信通讯的api接口*/
    IWXAPI iwxapi;

    public SharePopupWindow(Activity context, ShareModel shareModel) {
        super(context);
        this.mShareModel = shareModel;
        bitmapBytes = bmpToByteArray(mShareModel.getmShareImg(), false);
    }


    /**
     * 绑定控件
     */
    private void initView() {
        viewHolder = new ViewHolder(shareLayout);
        viewHolder.btnShareToWeChat.setOnClickListener(this);
        viewHolder.btnShareToFriends.setOnClickListener(this);
        viewHolder.btnShareToWeibo.setOnClickListener(this);
        viewHolder.rootLayout.setOnClickListener(this);
        viewHolder.cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_shareToWeChat:
                registerToWx();
                wechatShare(0);
                dismiss();
                break;
            case R.id.btn_shareToFriends:
                registerToWx();
                wechatShare(1);
                dismiss();
                break;
            case R.id.btn_shareToWeibo:
                registerToWb();
//                sendMultiMessage(false, false, true, false, false, false);
//                shareWebPage(mShareModel, mContext);
                sendSingleMessage(true, false, true, false, false);
                dismiss();
                break;
            case R.id.cancel:
                dismiss();
                break;
            case R.id.rootLayout:
                dismiss();
                break;
        }
    }

    @Override
    protected Animation getShowAnimation() {
        return getTranslateAnimation(250 * 2, 0, 300);
    }

    @Override
    protected View getClickToDismissView() {
        return viewHolder.cancel;
    }

    @Override
    public View getPopupView() {
        shareLayout = LayoutInflater.from(mContext).inflate(R.layout.share_popu, null);
        initView();
        return shareLayout;
    }

    @Override
    public View getAnimaView() {
        return null;
    }

    static class ViewHolder {
        @Bind(R.id.btn_shareToWeChat)
        TextView btnShareToWeChat;
        @Bind(R.id.btn_shareToFriends)
        TextView btnShareToFriends;
        @Bind(R.id.btn_shareToWeibo)
        TextView btnShareToWeibo;
        @Bind(R.id.cancel)
        TextView cancel;
        @Bind(R.id.rootLayout)
        RelativeLayout rootLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    private void getCreateShareBitmap() {


    }


    /**
     * 创建微博授权类
     */
    public void registerToWb() {
        if (mWeiboShareAPI == null) {
            // 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
            mAuthInfo = new AuthInfo(mContext, ShareConstant.APP_KEY, ShareConstant.REDIRECT_URL, ShareConstant.SCOPE);

            mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mContext, ShareConstant.APP_KEY);
            mWeiboShareAPI.registerApp();    // 将应用注册到微博客户端
        }
    }

    private AuthInfo mAuthInfo;


    /**
     * 注册到微信
     */
    private void registerToWx() {
        if (iwxapi == null) {
            /*通过WXAPIFactory工程获取IWXAPI实例*/
            iwxapi = WXAPIFactory.createWXAPI(mContext, Constant.WX_APP_ID, true);
            /*将应用的appID注册到微信*/
            iwxapi.registerApp(Constant.WX_APP_ID);
        }

    }

    SendMessageToWX.Req req;
    WXMediaMessage msg;

    /**
     * 发起微信分享
     *
     * @param flag 0为微信会话 ，2为朋友圈
     */
    private void wechatShare(int flag) {
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(mContext, "您未安装微信，请下载后分享", Toast.LENGTH_SHORT).show();
            return;
        }


        req = new SendMessageToWX.Req();
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = mShareModel.getmShareUrl();

        if (msg == null) {
            msg = new WXMediaMessage(webpage);
            msg.title = mShareModel.getmShareTitle();
            msg.description = mShareModel.getmDescription();
            //这里替换一张自己工程里的图片资源
            Log.d("jam", "share_wx__" + mShareModel.getmShareUrl());
//            msg.setThumbImage(mShareModel.getmShareImg());
            msg.thumbData = bitmapBytes;
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;


        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        iwxapi.sendReq(req);
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        int i = 3276800 / output.toByteArray().length;
        if (i < 100) {
            output.reset();// 重置baos即清空baos
            bmp.compress(Bitmap.CompressFormat.JPEG, i, output);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    WeiboMultiMessage weiboMessage;

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 当{@link IWeiboShareAPI#getWeiboAppSupportAPI()} < 10351 时，只支持分享单条消息，即
     * 文本、图片、网页、音乐、视频中的一种，不支持Voice消息。
     *
     * @param hasText    分享的内容是否有文本
     * @param hasImage   分享的内容是否有图片
     * @param hasWebpage 分享的内容是否有网页
     * @param hasMusic   分享的内容是否有音乐
     * @param hasVideo   分享的内容是否有视频
     */
    private void sendSingleMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
                                   boolean hasMusic, boolean hasVideo/*, boolean hasVoice*/) {


        if (!mWeiboShareAPI.isWeiboAppInstalled()) {
            Toast.makeText(mContext, "您未安装微博，请下载后分享", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. 初始化微博的分享消息
        // 用户可以分享文本、图片、网页、音乐、视频中的一种
        if (weiboMessage == null) {
            weiboMessage = new WeiboMultiMessage();
            if (hasWebpage) {
                weiboMessage.mediaObject = getWeiboPageObj();
            }

            if (hasText)
                weiboMessage.textObject = getTextObj("我在鹦鹉美家发现了一个精美案例:" + mShareModel.getmShareTitle() + "@鹦鹉美家");
        }

        // 2. 初始化从第三方到微博的消息请求
//        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(mContext, request);
    }


    private WebpageObject getWeiboPageObj() {
        WebpageObject webpageObject = new WebpageObject();
        webpageObject.thumbData = bitmapBytes;
        webpageObject.title = mShareModel.getmShareTitle();//不能超过512
        webpageObject.actionUrl = mShareModel.getmShareUrl();// 不能超过512
        webpageObject.description = mShareModel.getmDescription();//不能超过1024
        webpageObject.identify = UUID.randomUUID().toString();//这个不知道做啥的
        webpageObject.defaultText = "Webpage 默认文案";//这个也不知道做啥的
        return webpageObject;
    }


    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj(String text) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        return textObject;
    }


    @Override
    public void onResponse(BaseResponse baseResp) {//接收微客户端博请求的数据。
        switch (baseResp.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                break;
        }
    }


}
