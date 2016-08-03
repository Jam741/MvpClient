package com.rx.android.jamspeedlibrary.utils.adapter;

/**
 * Created by Jam on 2016/3/10.
 * 万能适配器多种布局接口
 */
public interface MultiItemTypeSupport<T> {

    int getLayoutId(int position, T t);

    int getViewTypeCount();

    int getItemViewType(int position, T t);
}
