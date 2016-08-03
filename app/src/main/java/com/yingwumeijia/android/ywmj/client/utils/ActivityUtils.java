package com.yingwumeijia.android.ywmj.client.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public class ActivityUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment,
                                             int fragmentId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragmentId,fragment);
        transaction.commit();
    }
}
