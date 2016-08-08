package com.yingwumeijia.android.ywmj.client.function.search;

import android.content.Context;
import android.content.Intent;

import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.utils.base.activity.BaseActivity;

/**
 * Created by Jam on 2016/8/8 15:16.
 * Describe:
 */
public class SearchActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SearchActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.search_act;
    }
}
