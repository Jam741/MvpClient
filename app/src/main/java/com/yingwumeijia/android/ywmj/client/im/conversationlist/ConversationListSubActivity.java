package com.yingwumeijia.android.ywmj.client.im.conversationlist;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.utils.ActivityUtils;
import com.yingwumeijia.android.ywmj.client.utils.base.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jam on 2016/8/10 11:54.
 * Describe:
 */
public class ConversationListSubActivity extends BaseActivity {

    @Bind(R.id.topTitle)
    TextView topTitle;
    @Bind(R.id.topLeft)
    TextView topLeft;


    @Override
    protected int getLayoutId() {
        return R.layout.conversation_common_act;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        //init actionBar
        initActionBar();

        //init conversation fragment
        initConversationFragemnt();
    }

    /**
     * 初始化聊天列表
     */
    private void initConversationFragemnt() {
        ConversationListSubFragment conversationListSubFragment =
                (ConversationListSubFragment) getSupportFragmentManager().findFragmentById(R.id.contentFragment);

        if (conversationListSubFragment == null) {
            conversationListSubFragment = ConversationListSubFragment.newInstance();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    conversationListSubFragment,
                    R.id.contentFragment);
        }
    }

    private void initActionBar() {
        topTitle.setText("聊天列表");
        TextViewUtils.setDrawableToLeft(this, topLeft, R.mipmap.back_ico);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.topLeft)
    public void onClick() {
        ActivityCompat.finishAfterTransition(this);
    }
}
