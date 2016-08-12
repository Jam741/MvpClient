package com.yingwumeijia.android.funcation.web;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Jam on 2016/8/9 14:57.
 * Describe:
 */
public class MyWebViewClient extends WebViewClient {

    private WebView mWebView;
    private String mUrl;

    public MyWebViewClient(WebView mWebView, String mUrl) {
        this.mWebView = mWebView;
        this.mUrl = mUrl;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    public MyWebViewClient() {
    }


}
