package com.yingwumeijia.android.ywmj.client.im.conversationlist;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.CallUtils;
import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.rx.android.jamspeedlibrary.utils.T;
import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.GroupResultBean;
import com.yingwumeijia.android.ywmj.client.utils.StartActivityManager;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;
import com.yingwumeijia.android.ywmj.client.utils.net.GlideUtils;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 16/7/6 下午4:02.
 * Describe:
 */
public class ConversationActivity extends AppCompatActivity {
    @Bind(R.id.topTitle)
    TextView topTitle;
    @Bind(R.id.topLeft)
    TextView topLeft;
    @Bind(R.id.topRight)
    TextView topRight;
    @Bind(R.id.case_layout)
    RelativeLayout caseLayout;
    @Bind(R.id.state_layput)
    LinearLayout stateLayout;
    @Bind(R.id.tv_case_state)
    TextView tvCaseState;
    @Bind(R.id.tv_case_name)
    TextView tvCaseName;
    @Bind(R.id.iv_case_img)
    ImageView ivCaseImg;

    private Activity context;

    /**
     * 会话title
     */
    private String mTitle;

    /**
     * 案例信息
     */
    private GroupResultBean.GroupConversationBean.CaseInfoBean caseInfoBean;

    /**
     * 联系电话
     */
    private String teamPhone;

    /**
     * 会话是否关闭
     */
    private boolean available;


    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetId;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_act);
        ButterKnife.bind(this);
        context = this;

        getIntentData();

        initActionBar();

        isReconnect(getIntent());

        enterFragment(Conversation.ConversationType.GROUP, mTargetId);

        report();
    }


    /**
     * 获取会话案例信息
     */
    private void getSessionCaseInfo(String targetId) {
        MyApp
                .getApiService()
                .getConversionInfo(targetId)
                .enqueue(new Callback<GroupResultBean>() {
                    @Override
                    public void onResponse(Call<GroupResultBean> call, Response<GroupResultBean> response) {
                        if (response.body().getSucc()) {
                            mTitle = response.body().getData().getName();
                            caseInfoBean = response.body().getData().getCaseInfo();
                            available = response.body().getData().isAvailable();
                            teamPhone = response.body().getData().getTeamPhone();
                            initCaseInfo();
                            initActionBar();
                        }

                    }

                    @Override
                    public void onFailure(Call<GroupResultBean> call, Throwable t) {

                    }
                });
    }

    /**
     * 初始化案例状态
     *
     * @param caseStatus 案例状态：1 = 上架，2 = 下架，3 = 强制下架，4 = 待完善
     */
    private void initCaseState(int caseStatus) {
        switch (caseStatus) {
            case 1:

                break;
            case 2:
                caseLayout.setVisibility(View.GONE);
                stateLayout.setVisibility(View.VISIBLE);
                tvCaseState.setText("您所关注的本案案例已被下架");
                break;
            case 3:
                caseLayout.setVisibility(View.GONE);
                stateLayout.setVisibility(View.VISIBLE);
                tvCaseState.setText("您所关注的本案案例已被下架");
                break;
            case 4:

                break;
        }
    }

    /**
     * 初始化案例信息
     */
    private void initCaseInfo() {
        GlideUtils.loadSimpleImage(context, ivCaseImg, caseInfoBean.getCaseCover());
        tvCaseName.setText(caseInfoBean.getCaseName());
        initCaseState(caseInfoBean.getStatus());
    }


    private void initActionBar() {
        topTitle.setText(mTitle);
        TextViewUtils.setDrawableToLeft(context, topLeft, R.mipmap.back_ico);
        TextViewUtils.setDrawableToLeft(context, topRight, R.mipmap.im_detail_call_ico);

    }


    @OnClick({R.id.topLeft, R.id.btn__close_caseLayout, R.id.btn__close_stateLayout, R.id.topRight, R.id.btn_lookBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                ActivityCompat.finishAfterTransition(context);
                break;
            case R.id.btn__close_stateLayout:
                stateLayout.setVisibility(View.GONE);
                break;
            case R.id.btn__close_caseLayout:
                caseLayout.setVisibility(View.GONE);
                break;
            case R.id.topRight:
                if (!TextUtils.isEmpty(teamPhone)) {
                    call(teamPhone);
                } else {
                    T.showShort(context, "没有电话号码");
                }
                break;
            case R.id.btn_lookBack:
                CaseBean caseBean = new CaseBean();
                caseBean.setCaseCover(caseInfoBean.getCaseCover());
                caseBean.setCaseId(caseInfoBean.getId());
                caseBean.setCaseName(caseInfoBean.getCaseName());
                StartActivityManager.startCaseDetailActivity(ConversationActivity.this, caseBean.getCaseId());
                break;
        }
    }

    public static final int request_code_call_phone = 001;


    /**
     * 拨打电话提示
     *
     * @param teamPhone
     */
    private void call(final String teamPhone) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.dialog_title)
                .setMessage("确定联系本案团队家居顾问吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkPermession();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    /**
     * 检查权限
     */
    private void checkPermession() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(ConversationActivity.this, new String[]{Manifest.permission.CALL_PHONE}, request_code_call_phone);
        } else {
            callTeamPhone();
        }
    }

    /**
     * 拨打电话
     */
    private void callTeamPhone() {
        if (TextUtils.isEmpty(teamPhone)) {
            T.showShort(context, "没有电话号码");
        } else {
            CallUtils.callPhone(teamPhone, ConversationActivity.this);
            report();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case request_code_call_phone:
                doNext(requestCode, grantResults);
                break;
        }
    }

    /**
     * 拨打电话权限回调
     *
     * @param requestCode
     * @param grantResults
     */
    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == request_code_call_phone) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                callTeamPhone();
            } else {
                // Permission Denied
                T.showShort(context, R.string.no_pressmion);
            }
        }
    }


    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType
     * @param mTargetId
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);
    }

    /**
     * 报告拨号次数
     */
    private void report() {
        MyApp
                .getApiService()
                .report(mTargetId)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {

                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    /**
     * 判断消息是否是 push 消息
     */
    private void isReconnect(Intent intent) {


        String token = null;

        if (!TextUtils.isEmpty(Constant.getIMToken(MyApp.appContext()))) {

            token = Constant.getIMToken(context);
        }

        //push或通知过来
        if (intent != null && intent.getData() != null && intent.getData().getScheme().equals("rong")) {

            //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息
            if (intent.getData().getQueryParameter("push") != null
                    && intent.getData().getQueryParameter("push").equals("true")) {

                reconnect(token);
            } else {
                Log.d("jam", "no push");
                //程序切到后台，收到消息后点击进入,会执行这里
                if (RongIM.getInstance() == null || RongIM.getInstance().getRongIMClient() == null) {
                    Log.d("jam", "n  reconnect");

                    reconnect(token);
                } else {
                    enterFragment(mConversationType, mTargetId);
                }
            }
        }
    }


    /**
     * 重连
     *
     * @param token
     */
    private void reconnect(String token) {

        LogUtil.getInstance().debug(token);


        if (getApplicationInfo().packageName.equals(MyApp.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    LogUtil.getInstance().debug("onTokenIncorrect");
                }

                @Override
                public void onSuccess(String s) {
                    LogUtil.getInstance().debug("onSuccess");
                    enterFragment(mConversationType, mTargetId);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogUtil.getInstance().debug("onError");
                }
            });
        }
    }

    public void getIntentData() {
        Uri uri = getIntent().getData();
        mTargetId = uri.getQueryParameter("targetId");
        mTitle = uri.getQueryParameter("title");
        mConversationType = Conversation.ConversationType.valueOf(getIntent().getData().getLastPathSegment().toUpperCase(Locale.getDefault()));

    }
}
