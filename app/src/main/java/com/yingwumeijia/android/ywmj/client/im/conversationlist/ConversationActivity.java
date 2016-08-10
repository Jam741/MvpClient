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
    private String title;

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
     * 目标 Id
     */

    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String targetId;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;


    private static final String KEY_TARGET_ID = "KEY_TARGET_ID";
    private static final String KEY_TITLE = "KEY_TITLE";
    private static final String KEY_CASE_INFO = "KEY_CASE_INFO";
    private static final String KEY_AVAILABLE = "KEY_AVAILABLE";
    private static final String KEY_TEAM_PHONE = "KEY_TEAM_PHONE";

    public static void start(Activity activity, String taegerId, String title, GroupResultBean.GroupConversationBean.CaseInfoBean caseInfoBean, String teamPhone, boolean available) {
        Intent intent = new Intent(activity, ConversationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TARGET_ID, taegerId);
        bundle.putString(KEY_TITLE, title);
        bundle.putSerializable(KEY_CASE_INFO, caseInfoBean);
        bundle.putString(KEY_TEAM_PHONE, teamPhone);
        bundle.putBoolean(KEY_AVAILABLE, available);
        intent.putExtra("bundle", bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_act);
        ButterKnife.bind(this);
        context = this;
        initData();
        initActionBar();
        if (caseInfoBean != null) {
            initCaseInfo();
        } else {
            caseLayout.setVisibility(View.GONE);
        }


    }

    private void initData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle == null) {
            isReconnect(getIntent());
            getSessionInfo();
            return;
        }
        targetId = bundle.getString(KEY_TARGET_ID);
        title = bundle.getString(KEY_TITLE);
        caseInfoBean = (GroupResultBean.GroupConversationBean.CaseInfoBean) bundle.getSerializable(KEY_CASE_INFO);
        available = bundle.getBoolean(KEY_AVAILABLE, true);
        teamPhone = bundle.getString(KEY_TEAM_PHONE);
        enterFragment(Conversation.ConversationType.GROUP, targetId);
    }

    /**
     */
    private void getSessionInfo() {
        Uri uri = getIntent().getData();
        targetId = uri.getQueryParameter("targetId");
        mConversationType = Conversation.ConversationType.valueOf(getIntent().getData().getLastPathSegment().toUpperCase(Locale.getDefault()));

        MyApp
                .getApiService()
                .getConversionInfo(targetId)
                .enqueue(new Callback<GroupResultBean>() {
                    @Override
                    public void onResponse(Call<GroupResultBean> call, Response<GroupResultBean> response) {
                        if (response.body().getSucc()) {
                            title = response.body().getData().getName();
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
        topTitle.setText(title);
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
                StartActivityManager.startCaseDetailActivity(ConversationActivity.this, caseInfoBean.getId());
                break;
        }
    }

    public static final int request_code_call_phone = 001;


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

    private void checkPermession() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(ConversationActivity.this, new String[]{Manifest.permission.CALL_PHONE}, request_code_call_phone);
        } else {
            callTeamPhone();
        }
    }

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
                .appendPath("conversation").appendPath(Conversation.ConversationType.GROUP.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);
    }

    /**
     * 报告拨号次数
     */
    private void report() {
        MyApp
                .getApiService()
                .report(targetId)
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

        LogUtil.getInstance().debug("isReconnect");
        String token = null;

        if (!Constant.getIMToken(context).equals("")) {
            token = Constant.getIMToken(context);
        }


        //push或通知过来
        if (intent != null && intent.getData() != null && intent.getData().getScheme().equals("rong")) {
            reconnect(token);
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
                    enterFragment(mConversationType, targetId);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogUtil.getInstance().debug("onError");
                }
            });
        }
    }
}
