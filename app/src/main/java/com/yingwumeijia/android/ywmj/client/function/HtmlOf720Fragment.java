package com.yingwumeijia.android.ywmj.client.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.rx.android.jamspeedlibrary.utils.NetUtils;
import com.yingwumeijia.android.ywmj.client.function.web.MyWebChromeClient;
import com.yingwumeijia.android.ywmj.client.function.web.MyWebViewClient;
import com.yingwumeijia.android.ywmj.client.utils.base.fragment.BaseFragment;

/**
 * Created by Jam on 2016/8/8 17:21.
 * Describe:
 */
public class HtmlOf720Fragment extends BaseFragment implements View.OnClickListener {

    private FrameLayout root;
    private WebView mWebView;
    private String mUrl;

    private ProgressBar mProgressBar;

    /*preview*/
    FrameLayout previewLayout;

    public static HtmlOf720Fragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString("KET_URL", url);
        Log.d("jam", "url:" + url);

        HtmlOf720Fragment fragment = new HtmlOf720Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = new FrameLayout(context);
            root.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mWebView = new WebView(context);
            mWebView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            root.addView(mWebView);

            mProgressBar = new ProgressBar(context);
            mProgressBar.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            root.addView(mProgressBar);

            previewLayout = new FrameLayout(context);
            previewLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView playButton = new ImageView(context);
            playButton.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
            previewLayout.addView(playButton);
            root.addView(previewLayout);

        }
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get url for arguments
        getData();

        //init web
        initWebView();

        //load url
        if (NetUtils.isWifi(context)) {
            hidePreviewLayout();
            loadUrl();
        } else {
            showPreviewLayout();
        }
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient(mProgressBar));
    }

    /**
     * 加载url
     */
    private void loadUrl() {
        mWebView.loadUrl(mUrl);
    }


    private void showPreviewLayout() {
        previewLayout.setVisibility(View.VISIBLE);
    }

    private void hidePreviewLayout() {
        previewLayout.setVisibility(View.GONE);
    }

    /**
     * 获取url
     */
    private void getData() {
        mUrl = getArguments().getString("KET_URL");
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.clearHistory();
        root.removeView(mWebView);
        mWebView.destroy();
    }

    @Override
    public void onClick(View view) {
        if (mWebView != null) {
            loadUrl();
            hidePreviewLayout();
        }
    }
}
