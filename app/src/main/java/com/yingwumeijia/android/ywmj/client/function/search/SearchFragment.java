package com.yingwumeijia.android.ywmj.client.function.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yingwumeijia.android.ywmj.client.utils.base.fragment.BaseFragment;

/**
 * Created by Jam on 2016/8/8 15:22.
 * Describe:
 */
public class SearchFragment extends BaseFragment {

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
