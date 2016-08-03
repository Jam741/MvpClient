package com.rx.android.jamspeedlibrary.utils.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Jam on 2016/3/10.
 * 多布局万能适配器
 */
public abstract class MultiItemCommonAdapter<T> extends CommonAdapter<T> {

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemCommonAdapter(Context mContext, List<T> mDatas, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(mContext, mDatas, -1);
        mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getViewTypeCount() {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getViewTypeCount();
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mMultiItemTypeSupport == null)
            return super.getView(position, convertView, parent);
        int layoutId = mMultiItemTypeSupport.getLayoutId(position, mDatas.get(position));
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
        conver(viewHolder, getItem(position),position);
        return viewHolder.getConvertView();
    }
}
