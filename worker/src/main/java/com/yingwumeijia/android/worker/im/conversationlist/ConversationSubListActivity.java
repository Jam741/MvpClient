package com.yingwumeijia.android.worker.im.conversationlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.T;
import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.funcation.caselist.CaseListActivity;
import com.yingwumeijia.android.worker.funcation.person.PersonActivity;

import java.util.Timer;
import java.util.TimerTask;

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

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ConversationSubListActivity.class);
        ActivityCompat.startActivity(activity, intent, Bundle.EMPTY);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_lsit_sub);
//                    RongIM.setConversationSListBehaviorListener(new MyConversationListBehaviorListener());
        RongIM.setConversationListBehaviorListener(new MyConversationListBehaviorListener());

        ButterKnife.bind(this);
        initActionBar();

    }


    private void initActionBar() {
        topTitle.setText("聊天列表");
    }


    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick({R.id.topLeft, R.id.topRight, R.id.topRight_second})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                break;
            case R.id.topRight:
                PersonActivity.start(this);
                break;
            case R.id.topRight_second:
                CaseListActivity.start(this);
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


    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            T.showShort(this, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            ActivityCompat.finishAffinity(this);
        }
    }
}
