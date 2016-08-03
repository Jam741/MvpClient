package com.rx.android.jamspeedlibrary.utils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jam on 2016/4/1.
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    protected List<T> mDatas;
    protected Context mContext;
    private int mLayoutId;

    public CommonRecyclerAdapter(List<T> mDatas, Context mContext, int mLayoutId) {
        if (mDatas == null) this.mDatas = new ArrayList<>();
        else this.mDatas = mDatas;
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecyclerViewHolder.get(mContext, parent, mLayoutId, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.setmPosition(position);
        convert(holder, mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract void convert(RecyclerViewHolder holder, T t, int position);

    public T getItemData(int position) {
        return this.mDatas.get(position);
    }

    public void refreshData(List<T> data) {
        this.mDatas.clear();
        this.mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.mDatas.clear();
        notifyDataSetChanged();
    }


    public void insert(T t) {
        insert(t, mDatas.size());
    }

    public void insert(T t, int position) {
        this.mDatas.add(position, t);
        notifyItemInserted(position);
    }

    public void upDate(T t, int position) {
        this.mDatas.set(position, t);
        notifyItemChanged(position);
    }

    public void remove(int position) {
        this.mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public void addRange(List<T> data, int position) {
        this.mDatas.addAll(position, data);
        notifyItemRangeInserted(position, data.size());
    }

    public void addRange(List<T> data) {
        this.mDatas.addAll(data);
        notifyItemRangeInserted(mDatas.size(), data.size());
    }

    public void removeRange(List<T> mDatas, int position) {
        this.mDatas.removeAll(mDatas);
        notifyItemRangeRemoved(position, mDatas.size());
    }

}
