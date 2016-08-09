package com.yingwumeijia.android.ywmj.client.function.mainfunction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.rx.android.jamspeedlibrary.utils.T;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.function.person.PersonActivity;
import com.yingwumeijia.android.ywmj.client.function.search.SearchActivity;
import com.yingwumeijia.android.ywmj.client.utils.ActivityUtils;
import com.yingwumeijia.android.ywmj.client.utils.base.activity.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jam on 2016/8/4 17:25.
 * Describe:
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.iv_message)
    ImageView ivMessage;
    @Bind(R.id.iv_mine)
    ImageView ivMine;

    private CaseListContract.Presenter mCaseListPresenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_act;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(context);
        CaseListFragment caseListFragment =
                (CaseListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFragment);
        if (caseListFragment == null) {
            //create CaseListFragment
            caseListFragment = CaseListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    caseListFragment,
                    R.id.contentFragment
            );
        }

        //create CaseListPresenter
        if (mCaseListPresenter == null) {
            mCaseListPresenter = new CaseListPresenter(context, caseListFragment);
        }

    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            T.showShort(this, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            ActivityCompat.finishAffinity(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_search, R.id.iv_message, R.id.iv_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                SearchActivity.start(context);
                break;
            case R.id.iv_message:

                break;
            case R.id.iv_mine:
                PersonActivity.start(context);
                break;
        }
    }
}
