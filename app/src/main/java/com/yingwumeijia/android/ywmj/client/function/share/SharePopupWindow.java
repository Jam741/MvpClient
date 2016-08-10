package com.yingwumeijia.android.ywmj.client.function.share;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.view.popuwindow.BasePopupWindow;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jam on 2016/8/10 15:45.
 * Describe:
 */
public class SharePopupWindow extends BasePopupWindow implements View.OnClickListener {

    private View shareLayout;

    private Context context;

    private ViewHolder viewHolder;

    public SharePopupWindow(Activity context) {
        super(context);
        this.context = context;
//        initView();

    }

    /**
     * 绑定控件
     */
    private void initView() {
        if (shareLayout == null) {
            shareLayout = LayoutInflater.from(MyApp.appContext()).inflate(R.layout.share_popu, null);
            viewHolder = new ViewHolder(shareLayout);
            viewHolder.btnShareToWeChat.setOnClickListener(this);
            viewHolder.btnShareToFriends.setOnClickListener(this);
            viewHolder.btnShareToWeibo.setOnClickListener(this);
            viewHolder.cancel.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_shareToWeChat:
                break;
            case R.id.btn_shareToFriends:
                break;
            case R.id.btn_shareToWeibo:
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    public SharePopupWindow(Activity context, int w, int h) {
        super(context, w, h);
    }

    @Override
    protected Animation getShowAnimation() {
        return new AnimationSet(true);
    }

    @Override
    protected View getClickToDismissView() {
        return viewHolder.cancel;
    }

    @Override
    public View getPopupView() {
        initView();
        return shareLayout;
    }

    @Override
    public View getAnimaView() {
        return shareLayout;
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
