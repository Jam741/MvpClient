package com.rx.android.jamspeedlibrary.utils.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by Jam on 2016/4/6.
 */
public abstract class MultiItemCommonRecyclerAdapter<T> extends CommonRecyclerAdapter<T> {

    private MultiItemTypeSupport<T> multiItemTypeSupport;

    public MultiItemCommonRecyclerAdapter(List<T> mDatas, Context mContext, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(mDatas, mContext, -1);
        this.multiItemTypeSupport = multiItemTypeSupport;
    }


    @Override
    public int getItemViewType(int position) {
        if (multiItemTypeSupport == null) return super.getItemViewType(position);
        return multiItemTypeSupport.getItemViewType(position, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        if (multiItemTypeSupport == null) return super.getItemCount();
        return multiItemTypeSupport.getViewTypeCount();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }
}
