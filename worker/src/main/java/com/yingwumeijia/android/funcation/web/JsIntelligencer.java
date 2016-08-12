package com.yingwumeijia.android.funcation.web;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.yingwumeijia.android.funcation.HtmlActivity;
import com.yingwumeijia.android.utils.StartActivityManager;


/**
 * Created by Jam on 16/6/23 下午5:09.
 * Describe:
 */
public class JsIntelligencer {

    private Activity activity;
    private String mCurrentCaseId;

    public JsIntelligencer(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void startNewHtmlActivity(String title, String url) {
        HtmlActivity.start(activity, title, url);
    }

    @JavascriptInterface
    public void startCaseDetailActivity(String caseId) {
        mCurrentCaseId = caseId;
        StartActivityManager.startCaseDetailActivity(activity, Integer.valueOf(mCurrentCaseId));
    }
}
