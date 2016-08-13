package com.yingwumeijia.android.worker.funcation.caselist;

import android.support.v7.widget.RecyclerView;

import com.rx.android.jamspeedlibrary.view.xrecyclerview.XRecyclerView;
import com.yingwumeijia.android.worker.BasePresenter;
import com.yingwumeijia.android.worker.BaseView;
import com.yingwumeijia.android.worker.data.bean.CaseBean;
import com.yingwumeijia.android.worker.data.bean.CaseTypeEnum;

import java.util.List;

/**
 * Created by Jam on 2016/8/4 17:40.
 * Describe:
 */
public interface CaseListContract {

    interface View extends BaseView<Presenter> {


        /**
         * 返回筛选条件类型
         *
         * @return
         */
        int getNavigationPosition();

        /**
         * 显示抽屉筛选栏
         */
        void showDrawerLayout();


        /**
         * 刷新导航栏数据
         *
         * @param navigationPosition
         */
        void refreshNavigationStatus(String showText, int navigationPosition);

        /**
         * 显示没有更多
         */
        void showLoadNoMore(boolean isNoMore);

        /**
         * 显示空布局
         *
         * @param isEmpty
         */
        void showEmptyLayout(boolean isEmpty);

        /**
         * 请求数据失败
         */
        void showLoadDataFail();

        /**
         * 显示没有网络链接
         *
         * @param isNoConnect
         */
        void showNotNetConnect(boolean isNoConnect);

        /**
         * 关闭抽屉筛选栏
         */
        void closeDrawerLayout();


        /**
         * 返回列表View
         *
         * @return
         */
        XRecyclerView getCaseListView();

        /**
         * 刷新完成
         */
        void caseListRefreshComplete();

        /**
         * 加载完成
         */
        void caseListLoadMoreComplete();

        /**
         * 加载没有更多数据
         */
        void caseListLoadNomore();

        /**
         * 重置加载状态
         */
        void caseListLoadRset();

        RecyclerView getCaseTypeListView();


        void startCaseDetailActivity(CaseBean caseBean);


    }

    interface Presenter extends BasePresenter {

        /**
         * 创建案例筛选列表适配器
         *
         * @return
         */
        RecyclerView.Adapter createCaseTypeAdapter();

        /**
         * 创建案例列表适配器
         *
         * @return
         */
        RecyclerView.Adapter createCaseListAdapter();

        /**
         * 刷新列表数据
         *
         * @param caseBeanList
         */
        void refreshCaseData(List<CaseBean> caseBeanList);

        /**
         * 添加数据
         *
         * @param caseBeanList
         */
        void addCaseData(List<CaseBean> caseBeanList);


        /**
         * 绑定案例列表适配器
         */
        void bindCaseListAdapter();


        /**
         * 绑定案例筛选适配器
         */
        void bindCaseTypeAdapter();


        /**
         * 案例筛选列表点击触发事件
         *
         * @param caseTypeEnum
         * @param navigationPosition
         */
        void caseTypeListItemSelected(CaseTypeEnum caseTypeEnum, int navigationPosition);


        /**
         * 案例列表点击
         *
         * @param caseBean
         */
        void caseListItemClick(CaseBean caseBean);


        /**
         * 加载案例列表数据
         */
        void loadCaseListDate();

        /**
         * 加载筛选列表数据
         */
        void loadCaseTypeData();

        /**
         * 刷新筛选导航栏数据
         *
         * @param navigationPosition
         */
        void refreshNavigationData(int navigationPosition);

    }

}
