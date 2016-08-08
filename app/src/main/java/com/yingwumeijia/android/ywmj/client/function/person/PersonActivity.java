package com.yingwumeijia.android.ywmj.client.function.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.utils.ActivityUtils;
import com.yingwumeijia.android.ywmj.client.utils.base.activity.BaseActivity;

/**
 * Created by Jam on 2016/8/5 17:58.
 * Describe:
 */
public class PersonActivity extends BaseActivity {

    private PersonContract.Presenter mPersonPresenter;


    public static void start(Context context) {
        Intent starter = new Intent(context, PersonActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_act;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init Fragment
        PersonFragment personFragment =
                (PersonFragment) getSupportFragmentManager().findFragmentById(R.id.contentFragment);

        if (personFragment == null) {
            //create fragment
            personFragment = new PersonFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    personFragment,
                    R.id.contentFragment
            );
        }

        //create Presenter
        mPersonPresenter = new PersonPresenter(context, personFragment);
    }
}
