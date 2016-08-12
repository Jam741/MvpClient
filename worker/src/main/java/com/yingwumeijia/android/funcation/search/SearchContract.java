package com.yingwumeijia.android.funcation.search;

import android.support.v7.widget.RecyclerView;


import com.yingwumeijia.android.BasePresenter;
import com.yingwumeijia.android.BaseView;
import com.yingwumeijia.android.data.bean.CaseBean;

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
