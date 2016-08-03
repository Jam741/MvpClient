package com.yingwumeijia.android.ywmj.client.function.findpassword;

import android.content.Context;
import android.content.Intent;

import com.yingwumeijia.android.ywmj.client.utils.base.activity.BaseActivity;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public class FindPasswordActivity extends BaseActivity {

    public static void start(Context context,String phone) {
        Intent starter = new Intent(context, FindPasswordActivity.class);
        starter.putExtra("phone",phone);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
