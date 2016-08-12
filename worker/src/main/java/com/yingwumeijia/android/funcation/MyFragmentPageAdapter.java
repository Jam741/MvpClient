package com.yingwumeijia.android.funcation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Jam on 2016/8/9 16:49.
 * Describe:
 */
public class MyFragmentPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public MyFragmentPageAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
