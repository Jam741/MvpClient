package com.yingwumeijia.android.funcation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Jam on 2016/6/8 17:34.
 * Describe:
 */
public class TabWithPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> mTabs;
    private List<Fragment> mFragments;

    public TabWithPagerAdapter(FragmentManager fm, List<String> mTabs, List<Fragment> mFragments) {
        super(fm);
        this.mTabs = mTabs;
        this.mFragments = mFragments;
    }

    public TabWithPagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
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

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position);
    }
}
