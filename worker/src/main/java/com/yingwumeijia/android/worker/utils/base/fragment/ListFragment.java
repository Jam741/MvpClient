package com.yingwumeijia.android.worker.utils.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public class ListFragment extends android.support.v4.app.ListFragment {

    static final int INTERNAL_EMPTY_ID = 0x00ff0004;
    static final int INTERNAL_PROGRESS_CONTAINER_ID = 0x00ff0005;
    static final int INTERNAL_LIST_CONTAINER_ID = 0x00ff0006;

    final private Handler mHandler = new Handler();

    final private Runnable mRequestFocus = new Runnable() {
        @Override
        public void run() {
            mList.focusableViewAvailable(mList);
        }
    };

    ListAdapter mAdapter;
    ListView mList;
    View emptyView;
    TextView mStandardEmptyViw;
    View mProgressContainer;
    View mListCntainer;
    CharSequence mEnptyText;
    boolean mListShown;

    public ListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context context = getContext();
        FrameLayout root = new FrameLayout(context);

        //----------------------------------------------------------------
        LinearLayout pframe = new LinearLayout(context);
        pframe.setId(INTERNAL_PROGRESS_CONTAINER_ID);
        pframe.setOrientation(LinearLayout.VERTICAL);
        pframe.setGravity(Gravity.CENTER);
        pframe.setVisibility(View.GONE);

        ProgressBar progressBar = new ProgressBar(
                context,
                null,
                android.R.attr.progressBarStyleLarge
        );
        pframe.addView(
                progressBar,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        root.addView(
                pframe,
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        );

        //-------------------------------------------------------------------
        FrameLayout lframe = new FrameLayout(context);
        lframe.setId(INTERNAL_LIST_CONTAINER_ID);

        ListView lv = new ListView(context);
        lv.setId(android.R.id.list);
        lv.setDrawSelectorOnTop(false);
        lframe.addView(
                lframe,
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        );
        root.addView(
                lframe,
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        );
        return root;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ensureList();
    }

    private void ensureList() {
        if (mList != null) {
            return;
        }

    }
}
