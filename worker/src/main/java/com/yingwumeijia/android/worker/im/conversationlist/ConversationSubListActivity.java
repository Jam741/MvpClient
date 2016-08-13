package com.yingwumeijia.android.worker.im.conversationlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.funcation.person.PersonActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jam on 16/7/6 下午3:55.
 * Describe:
 */
public class ConversationSubListActivity extends AppCompatActivity {


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ConversationSubListActivity.class);
        ActivityCompat.startActivity(activity, intent, Bundle.EMPTY);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_lsit_sub);

        ButterKnife.bind(this);

    }


    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick({R.id.topRight, R.id.topRight_second})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topRight:
                PersonActivity.start(this);
                break;
            case R.id.topRight_second:

                break;
        }
    }
}
