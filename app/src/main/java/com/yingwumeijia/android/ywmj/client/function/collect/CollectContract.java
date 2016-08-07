package com.yingwumeijia.android.ywmj.client.function.collect;

import com.rx.android.jamspeedlibrary.view.xrecyclerview.XRecyclerView;
import com.yingwumeijia.android.ywmj.client.BasePresenter;
import com.yingwumeijia.android.ywmj.client.BaseView;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseBean;

import java.util.List;

/**
 * Created by Jam on 2016/8/7 10:09.
 * Describe:
 */
public interface CollectContract {


    interface Presenter extends BasePresenter {

        void cancelCollect(int caseId);

        void cancelOperation();

        void getCollectList();

        void createCollectListAdapter();

        void refreshData(List<CaseBean> caseBeanList);

        void loadMoreData(List<CaseBean> caseBeanList);

    }


    interface View extends BaseView<Presenter> {


        boolean showCancelDialog(OnCancelClickListener onCancelClickListener);

        void showCancelFail(String msg);

        void showEmptyLayout();

        void showListLayout();

        void showGetListFail(String msg);

        void loadMoreComplete();

        void refreshComplete();

        void loadNoMore();

        void loadRset();

        XRecyclerView getRecyclerView();

        void CreateCollectListAdapter();

        void addCaseList(List<CaseBean> caseBeanList);

        void refreshList(List<CaseBean> caseBeanList);

        interface OnCancelClickListener {

            void confirm();

            void cancel();
        }
    }
}
