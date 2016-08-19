package com.yingwumeijia.android.worker.im.conversationlist;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.funcation.caselist.CaseListActivity;
import com.yingwumeijia.android.worker.funcation.person.PersonActivity;

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
    @Bind(R.id.topRight)
    TextView topRight;
    @Bind(R.id.topRight_second)
    TextView topRightSecond;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        ButterKnife.bind(this);


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

    @OnClick({R.id.topRight, R.id.topRight_second})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topRight:
                PersonActivity.start(this);
                break;
            case R.id.topRight_second:
                CaseListActivity.start(this);
                break;
        }
    }
}
