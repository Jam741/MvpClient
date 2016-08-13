package com.yingwumeijia.android.worker.funcation.web;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by Jam on 2016/8/9 15:12.
 * Describe:
 */
public class MyWebChromeClient extends WebChromeClient {

    private ProgressBar mProgressBar;

    private MyWebChromeClient() {
    }

    public MyWebChromeClient(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (newProgress < 100) {
            mProgressBar.setProgress(newProgress);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
