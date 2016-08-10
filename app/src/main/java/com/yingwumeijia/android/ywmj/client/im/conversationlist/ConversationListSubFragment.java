package com.yingwumeijia.android.ywmj.client.im.conversationlist;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.utils.base.fragment.BaseFragment;

import io.rong.imkit.fragment.SubConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by Jam on 2016/8/10 13:06.
 * Describe:
 */
public class ConversationListSubFragment extends BaseFragment {


    public static ConversationListSubFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ConversationListSubFragment fragment = new ConversationListSubFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.conversation_common_act, container, false);

        SubConversationListFragment fragment = new SubConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);


        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.contentFragment, fragment);
        transaction.commit();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //init actionbar
    }
    
}
