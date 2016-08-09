package com.yingwumeijia.android.ywmj.client.function.search;

import android.support.v7.widget.RecyclerView;

import com.rx.android.jamspeedlibrary.view.xrecyclerview.XRecyclerView;
import com.yingwumeijia.android.ywmj.client.BasePresenter;
import com.yingwumeijia.android.ywmj.client.BaseView;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseBean;

import java.util.List;

/**
 * Created by Jam on 2016/8/8 15:29.
 * Describe
 */
public interface SearchContract {


    interface View extends BaseView<Presenter> {

        void showSearchFail(String msg);

        void showKeywordsError();

        void showEmptyLayout();

        void showListLayout();

        RecyclerView getRecyclerView();

        void showClearEditButton();

        void hideClearEditButton();

        void clearEdit();

        String getKeyWords();
    }

    interface Presenter extends BasePresenter {


        void createListAdapter();

        void refrshData(List<CaseBean> caseBeanList);

        void loadMoreData(List<CaseBean> caseBeanList);

        void bindAdapterWithList();

        void listItemClickOperation(CaseBean caseBean);

        void search(String keyWords);

        boolean checkKeywords(String keyWords);
    }

}