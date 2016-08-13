package com.yingwumeijia.android.worker.funcation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.yingwumeijia.android.worker.funcation.web.JsIntelligencer;
import com.yingwumeijia.android.worker.funcation.web.MyWebChromeClient;
import com.yingwumeijia.android.worker.funcation.web.MyWebViewClient;
import com.yingwumeijia.android.worker.utils.base.fragment.BaseFragment;
import com.yingwumeijia.android.worker.utils.constants.Constant;

/**
 * Created by Jam on 2016/8/8 17:21.
 * Describe:
 */
public class HtmlFragment extends BaseFragment {

    private FrameLayout root;
    private WebView mWebView;
    private String mUrl;

    private ProgressBar mProgressBar;

    private int mCheckPosition;

    public static HtmlFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString("KET_URL", url);
        Log.d("jam", "url:" + url);

        HtmlFragment fragment = new HtmlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            root = new FrameLayout(context);
            root.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mWebView = new WebView(context);
            mWebView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            root.addView(mWebView);

            mProgressBar = new ProgressBar(context);
            mProgressBar.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
            root.addView(mProgressBar);
        Log.d("jam", "mUrl:onCreateView" + mUrl);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get url for arguments
        getData();

        loadUrl();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_SCROLL_NAV);
        context.registerReceiver(myReceiver, filter);
    }

    private void loadUrl() {

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient(mProgressBar));
        mWebView.addJavascriptInterface(new JsIntelligencer(context), "jsIntelligencer");

        mWebView.loadUrl(mUrl);
    }

    private void getData() {
        mUrl = getArguments().getString("KET_URL");
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(myReceiver);
        root.removeView(mWebView);
        mWebView.destroy();
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mCheckPosition = intent.getIntExtra("POSITION", 0);
            LogUtil.getInstance().debug("jam", "------------------------" + mCheckPosition);
            handler.sendEmptyMessage(0);
        }
    };

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    mWebView.loadUrl("javascript:scrollFunc('" + mCheckPosition + "')");
                    break;
            }
        }
    };
}
