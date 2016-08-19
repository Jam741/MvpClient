package com.yingwumeijia.android.ywmj.client.im.conversationlist;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.function.login.LoginActivity;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by Jam on 16/8/18 下午10:43.
 * Describe:
 */
public class ConversationListActivity extends AppCompatActivity {


    @Bind(R.id.topTitle)
    TextView topTitle;
    @Bind(R.id.topLeft)
    TextView topLeft;
    @Bind(R.id.rong_content)
    FrameLayout rongContent;
    @Bind(R.id.notLogin_layout)
    LinearLayout notLoginLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        ButterKnife.bind(this);

        setupActionBar();


        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //rong_content 为你要加载的 id
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constant.isLogin(this)) {
            rongContent.setVisibility(View.VISIBLE);
            notLoginLayout.setVisibility(View.GONE);
        } else {
            notLoginLayout.setVisibility(View.VISIBLE);
            rongContent.setVisibility(View.GONE);
        }
    }

    private void setupActionBar() {

        TextViewUtils.setDrawableToLeft(this, topLeft, R.mipmap.back_ico);
        topTitle.setText("聊天列表");
    }

    @OnClick({R.id.topLeft, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                ActivityCompat.finishAfterTransition(this);
                break;
            case R.id.btn_login:
                LoginActivity.start(this, true);
                break;
        }
    }
}
