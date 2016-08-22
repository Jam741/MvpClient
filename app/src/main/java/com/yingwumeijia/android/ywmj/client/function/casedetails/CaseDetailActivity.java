package com.yingwumeijia.android.ywmj.client.function.casedetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.muzhi.camerasdk.model.Constants;
import com.rx.android.jamspeedlibrary.utils.ScreenUtils;
import com.rx.android.jamspeedlibrary.utils.T;
import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.ShareModel;
import com.yingwumeijia.android.ywmj.client.function.share.ShareConstant;
import com.yingwumeijia.android.ywmj.client.function.share.SharePopupWindow;
import com.yingwumeijia.android.ywmj.client.utils.UserManager;
import com.yingwumeijia.android.ywmj.client.utils.base.activity.BaseActivity;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;
import com.yingwumeijia.android.ywmj.client.utils.view.IndexViewPager;

import java.io.ByteArrayOutputStream;
import java.security.PrivateKey;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jam on 2016/8/9 11:30.
 * Describe:
 */
public class CaseDetailActivity extends BaseActivity implements CaseDetailContract.View,
        ViewPager.OnPageChangeListener, OnTabSelectListener, IWeiboHandler.Response {


    @Bind(R.id.topTitle)
    TextView topTitle;
    @Bind(R.id.topLeft)
    TextView topLeft;
    @Bind(R.id.topRight_second)
    ImageView btnCollect;
    @Bind(R.id.ctab_nav)
    CommonTabLayout ctabNav;
    @Bind(R.id.hsv_nav)
    HorizontalScrollView hsvNav;
    @Bind(R.id.vp_content)
    IndexViewPager vpContent;
    @Bind(R.id.btn_menu)
    ImageButton btnMenu;
    @Bind(R.id.tv_sliding_title)
    TextView tvSlidingTitle;
    @Bind(R.id.rv_sliding_nav)
    RecyclerView rvSlidingNav;
    @Bind(R.id.right_drawer)
    RelativeLayout rightDrawer;
    @Bind(R.id.drawer_root)
    DrawerLayout drawerRoot;
    @Bind(R.id.btn_connectTeam)
    TextView btnConnectTeam;
    private CaseDetailContract.Presenter mPresenter;
    private int mCaseId;
    private int mCurrentPosition = 0;
    private boolean isCollect;
    private boolean isBack;

    public static void start(Context context, int caseId) {
        Intent starter = new Intent(context, CaseDetailActivity.class);
        starter.putExtra("KEY_CASE_ID", caseId);
        context.startActivity(starter);
    }

    public static void start(Context context, int caseId, boolean back) {
        Intent starter = new Intent(context, CaseDetailActivity.class);
        starter.putExtra("KEY_CASE_ID", caseId);
        starter.putExtra("KEY_BACK", back);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        //initActionBar
        initActionBar();

        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constant.WB_APP_KEY);

        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();

        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }

        //get data from intent
        getIntentData();

        //lock sliding
        drawerRoot.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        //init drawerLayout
        ViewGroup.LayoutParams lp = rightDrawer.getLayoutParams();
        lp.width = ScreenUtils.getScreenWidth() * 8 / 12;
        rightDrawer.setLayoutParams(lp);

        //set listener
        vpContent.addOnPageChangeListener(this);
        vpContent.setOffscreenPageLimit(5);
        vpContent.setScanScroll(false);
        ctabNav.setOnTabSelectListener(this);


        if (mPresenter == null) {
            mPresenter = new CaseDetailPresenter(this, context);
        }
        mPresenter.start();
        mPresenter.getShareData(mCaseId);
        mPresenter.undateVisitNum(mCaseId);
        mPresenter.loadDetailData(mCaseId);

        registerToWx();
//        registerToWb();
    }

    private void initActionBar() {
        topTitle.setText("案例详情");
        TextViewUtils.setDrawableToLeft(context, topLeft, R.mipmap.back_ico);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mCaseId = intent.getIntExtra("KEY_CASE_ID", 0);
        isBack = intent.getBooleanExtra("KEY_BACK", false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.case_detail_act;
    }

    @Override
    public void showLoadDataFail(String msg) {
        T.showShort(context, msg);
    }

    @Override
    public ViewPager getViewPager() {
        return vpContent;
    }

    @Override
    public void showNavMenuButton() {
        btnMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNavMenuButton() {
        btnMenu.setVisibility(View.GONE);
    }

    @Override
    public void showDrawerLayout() {
        drawerRoot.openDrawer(Gravity.RIGHT);
    }

    @Override
    public void clostDrawerLayout() {
        drawerRoot.closeDrawer(Gravity.RIGHT);
    }

    @Override
    public FragmentManager getMyFragmentManager() {
        return getSupportFragmentManager();
    }

    @Override
    public RecyclerView getNavRecyclerView() {
        return rvSlidingNav;
    }

    @Override
    public CommonTabLayout getTabView() {
        return ctabNav;
    }

    @Override
    public void setCollected() {
        isCollect = true;
        btnCollect.setImageResource(R.mipmap.case_details_like_light_ic);
    }

    @Override
    public void setUnCollected() {
        isCollect = false;
        btnCollect.setImageResource(R.mipmap.case_details_like_ico);
    }

    @Override
    public void showIsContact(boolean isContact) {
        if (isContact) btnConnectTeam.setText("回到聊天");
        else btnConnectTeam.setText("立即联系他们");
    }


    @Override
    public void setPresener(CaseDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNetConnectError() {
        showBaseNetConnectError();
    }

    @Override
    public void showProgressBar() {
        showBaseProgresDialog();
    }

    @Override
    public void dismissProgressBar() {
        dismisBaseProgressDialog();
    }

    @OnClick({R.id.topLeft, R.id.btn_menu, R.id.topRight, R.id.btn_connectTeam, R.id.topRight_second})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                ActivityCompat.finishAfterTransition(context);
                break;
            case R.id.btn_menu:
                showDrawerLayout();
                break;
            case R.id.topRight:
                mPresenter.launchShareSDK();
//                showPopWindow();
                break;
            case R.id.btn_connectTeam:
                if (isBack)
                    ActivityCompat.finishAfterTransition(context);
                else
                    mPresenter.connectWithTeam(mCaseId);
                break;
            case R.id.topRight_second:
                if (!UserManager.userPrecondition(context)) return;
                if (isCollect) {
                    mPresenter.cancelCollect(mCaseId);
                } else {
                    mPresenter.collect(mCaseId);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("jam", "psoiton:" + position);
        ctabNav.setCurrentTab(position);
        btnMenu.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
        vpContent.setScanScroll(position == 0 ? false : true);
        int scrollSize = hsvNav.getMaxScrollAmount();
        hsvNav.smoothScrollTo((scrollSize / 6) * (position), 0);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelect(int position) {
        vpContent.setCurrentItem(position);
    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    private PopupWindow sharePopWindow;


    /*和微博通讯的api接口*/
    IWeiboShareAPI mWeiboShareAPI;

    /*和微信通讯的api接口*/
    IWXAPI iwxapi;

    private void showPopWindow() {

        TextView btnWx;
        TextView btnPyq;
        TextView btnWb;

        if (sharePopWindow == null) {
            sharePopWindow = new PopupWindow(context);
            View popView = LayoutInflater.from(context).inflate(R.layout.share_popu, null);
            sharePopWindow.setContentView(popView);
            btnWx = (TextView) popView.findViewById(R.id.btn_shareToWeChat);
            btnPyq = (TextView) popView.findViewById(R.id.btn_shareToFriends);
            btnWb = (TextView) popView.findViewById(R.id.btn_shareToWeibo);

            btnWx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wechatShare(0);
                }
            });

            btnPyq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wechatShare(1);
                }
            });

            btnWb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareWebPage(mPresenter.getShareModel(), context);
                }
            });
        }

        sharePopWindow.showAsDropDown(btnCollect);
    }

    /**
     * 创建微博授权类
     */
    public void registerToWb() {
        if (mWeiboShareAPI == null) {
            mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, ShareConstant.APP_KEY);
            mWeiboShareAPI.registerApp();    // 将应用注册到微博客户端
        }
    }


    /**
     * 注册到微信
     */
    private void registerToWx() {
        if (iwxapi == null) {
            /*通过WXAPIFactory工程获取IWXAPI实例*/
            iwxapi = WXAPIFactory.createWXAPI(context, Constant.WX_APP_ID, true);
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
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(context, "您未安装微信，请下载后分享", Toast.LENGTH_SHORT).show();
            return;
        }


        ShareModel mShareModel = mPresenter.getShareModel();

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

//    /**
//     * 发送消息到微博
//     *
//     * @param hasText
//     * @param hasImage
//     * @param hasWebpage
//     * @param hasMusic
//     * @param hasVideo
//     * @param hasVoice
//     */
//    private void sendMultiMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
//                                  boolean hasMusic, boolean hasVideo, boolean hasVoice) {
//
//        if (!iwxapi.isWXAppInstalled()) {
//            Toast.makeText(mContext, "您未安装微博，请下载后分享", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();//初始化微博的分享消息
//        if (hasText) weiboMessage.textObject = getTextObj();
//        if (hasImage) weiboMessage.imageObject = getImageObj();
//        if (hasWebpage) weiboMessage.mediaObject = getWeiboPageObj();
//        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.multiMessage = weiboMessage;
//        mWeiboShareAPI.sendRequest(mContext, request); //发送请求消息到微博，唤起微博分享界面
//    }


    private void shareWebPage(ShareModel shareContent, Context context) {
        WeiboMessage weiboMessage = new WeiboMessage();
        weiboMessage.mediaObject = getWebpageObj(shareContent, context);
        //初始化从第三方到微博的消息请求
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        request.transaction = buildTransaction("sinatext");
        request.message = weiboMessage;
        //发送请求信息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest((Activity) context, request);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private WebpageObject getWebpageObj(ShareModel shareContent, Context context) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = shareContent.getmShareTitle();
        mediaObject.description = shareContent.getmDescription();

        // 设置 Bitmap 类型的图片到视频对象里
//        msg.thumbData = bmpToByteArray(shareContent.getmShareImg(), true);
//        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), shareContent.getPicResource());
        byte[] bytes = bmpToByteArray(shareContent.getmShareImg(), true);
        mediaObject.setThumbImage(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        mediaObject.actionUrl = shareContent.getmShareUrl();
        mediaObject.defaultText = "content";
        return mediaObject;
    }

    /**
     * @see {@link Activity#onNewIntent}
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    /**
     * 接收微客户端博请求的数据。
     * 当微博客户端唤起当前应用并进行分享时，该方法被调用。
     *
     * @param baseRequest 微博请求数据对象
     * @see {@link IWeiboShareAPI#handleWeiboRequest}
     */
    @Override
    public void onResponse(BaseResponse baseResp) {
        if (baseResp != null) {
            switch (baseResp.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    Toast.makeText(this, R.string.weibosdk_demo_toast_share_success, Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    Toast.makeText(this, R.string.weibosdk_demo_toast_share_canceled, Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    Toast.makeText(this,
                            getString(R.string.weibosdk_demo_toast_share_failed) + "Error Message: " + baseResp.errMsg,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
