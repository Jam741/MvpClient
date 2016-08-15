package com.yingwumeijia.android.worker.funcation.collect;

import com.rx.android.jamspeedlibrary.view.xrecyclerview.XRecyclerView;
import com.yingwumeijia.android.worker.BasePresenter;
import com.yingwumeijia.android.worker.BaseView;
import com.yingwumeijia.android.worker.data.bean.CaseBean;

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


        void loadMoreComplete();

        void refreshComplete();

        void loadNoMore();

        void loadRset();

        void hideEmptyLayout();

        XRecyclerView getRecyclerView();

        interface OnCancelClickListener {

            void confirm();

            void cancel();
        }
    }
}
