package com.yingwumeijia.android.ywmj.client.im.conversationlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.function.login.LoginActivity;
import com.yingwumeijia.android.ywmj.client.utils.StartActivityManager;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;

/**
 * Created by Jam on 16/7/6 下午3:55.
 * Describe:
 */
public class ConversationSubListActivity extends AppCompatActivity {

    @Bind(R.id.topTitle)
    TextView topTitle;
    @Bind(R.id.topLeft)
    TextView topLeft;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ConversationSubListActivity.class);
        ActivityCompat.startActivity(activity, intent, Bundle.EMPTY);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conversation_lsit_sub);
        ButterKnife.bind(this);

//                    RongIM.setConversationSListBehaviorListener(new MyConversationListBehaviorListener());
        RongIM.setConversationListBehaviorListener(new MyConversationListBehaviorListener());


        initActionBar();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constant.isLogin(this)) {
            emptyLayout.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    private void initActionBar() {
        topTitle.setText("聊天列表");
        TextViewUtils.setDrawableToLeft(this, topLeft, R.mipmap.back_ico);
    }

    @OnClick(R.id.topLeft)
    public void onClick() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick({R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                LoginActivity.start(this, true);
                break;
        }
    }


    private class MyConversationListBehaviorListener implements RongIM.ConversationListBehaviorListener {

        @Override
        public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
            return false;
        }

        @Override
        public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
            return false;
        }

        /**
         * 长按会话列表中的 item 时执行。
         *
         * @param context        上下文。
         * @param view           触发点击的 View。
         * @param uiConversation 长按时的会话条目。
         * @return 如果用户自己处理了长按会话后的逻辑处理，则返回 true， 否则返回 false，false 走融云默认处理方式。
         */
        @Override
        public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
            return false;
        }

        /**
         * 点击会话列表中的 item 时执行。
         *
         * @param context        上下文。
         * @param view           触发点击的 View。
         * @param uiConversation 会话条目。
         * @return 如果用户自己处理了点击会话后的逻辑处理，则返回 true， 否则返回 false，false 走融云默认处理方式。
         */
        @Override
        public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
            return false;
        }
    }
}
