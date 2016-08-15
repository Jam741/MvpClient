package com.yingwumeijia.android.worker.funcation.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.popuwindowlibrary.BasePopupWindow;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.data.bean.ShareModel;
import com.yingwumeijia.android.worker.utils.constants.Constant;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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

    /*和微信通讯的api接口*/
    IWXAPI iwxapi;

    public SharePopupWindow(Activity context, ShareModel shareModel) {
        super(context);
        this.mShareModel = shareModel;
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
                sendMultiMessage(true, true, false, false, false, false);
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


    /**
     * 创建微博授权类
     */
    public void registerToWb() {
        if (mWeiboShareAPI == null) {
            mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mContext, ShareConstant.APP_KEY);
            mWeiboShareAPI.registerApp();    // 将应用注册到微博客户端
        }
    }


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

    /**
     * 发起微信分享
     *
     * @param flag 0为微信会话 ，2为朋友圈
     */
    private void wechatShare(int flag) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = mShareModel.getmShareUrl();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = mShareModel.getmShareTitle();
        msg.description = mShareModel.getmDescription();
        //这里替换一张自己工程里的图片资源
        Log.d("jam", "share_wx__" + mShareModel.getmShareUrl());
//        msg.setThumbImage(compressImage(mShareModel.getmShareImg()));
        msg.thumbData = bmpToByteArray(mShareModel.getmShareImg(), true);
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


    /**
     * 发送消息到微博
     *
     * @param hasText
     * @param hasImage
     * @param hasWebpage
     * @param hasMusic
     * @param hasVideo
     * @param hasVoice
     */
    private void sendMultiMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
                                  boolean hasMusic, boolean hasVideo, boolean hasVoice) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();//初始化微博的分享消息
        if (hasText) weiboMessage.textObject = getTextObj();
        if (hasImage) weiboMessage.imageObject = getImageObj();
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        mWeiboShareAPI.sendRequest(mContext, request); //发送请求消息到微博，唤起微博分享界面
    }

    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = mShareModel.getmDescription();
        return textObject;
    }

    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(mShareModel.getmShareImg());
        return imageObject;
    }

    private VoiceObject getVoiceObj() {
        VoiceObject voiceObject = new VoiceObject();
        voiceObject.dataHdUrl = mShareModel.getmShareUrl();
        return voiceObject;
    }

//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        mWeiboShareAPI.handleWeiboResponse(intent, this); //当前应用唤起微博分享后，返回当前应用
//    }

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
