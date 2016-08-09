package com.yingwumeijia.android.ywmj.client.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewFragment;
import android.widget.FrameLayout;

import com.yingwumeijia.android.ywmj.client.function.web.MyWebChromeClient;
import com.yingwumeijia.android.ywmj.client.function.web.MyWebViewClient;
import com.yingwumeijia.android.ywmj.client.utils.base.fragment.BaseFragment;

/**
 * Created by Jam on 2016/8/8 17:21.
 * Describe:
 */
public class HtmlFragment extends BaseFragment {

    private FrameLayout root;
    private WebView mWebView;
    private String mUrl;

    public static HtmlFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString("KET_URL", url);

        HtmlFragment fragment = new HtmlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = new FrameLayout(context);
            mWebView = new WebView(context);
            mWebView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            root.addView(mWebView);
        }
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get url for arguments
        getData();

        loadUrl();
    }

    private void loadUrl() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.loadUrl(mUrl);
    }

    private void getData() {
        mUrl = getArguments().getString("KEY_URL");
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
}
