package com.yingwumeijia.android.ywmj.client.function.mainfunction;

import android.support.v7.widget.RecyclerView;

import com.rx.android.jamspeedlibrary.utils.adapter.CommonRecyclerAdapter;
import com.yingwumeijia.android.ywmj.client.BasePresenter;
import com.yingwumeijia.android.ywmj.client.BaseView;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseTypeEnum;

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
         *
         * @param caseTypeData
         */
        void showDrawerLayout(List<CaseTypeEnum> caseTypeData);


        /**
         * 刷新导航栏数据
         *
         * @param caseTypeEnum
         * @param navigationPosition
         */
        void refreshNavigationStatus(CaseTypeEnum caseTypeEnum, int navigationPosition);

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
        RecyclerView getCaseListView();


        RecyclerView getCaseTypeListView();


        void startCaseDetailActivity(CaseBean caseBean);

    }

    interface Presenter extends BasePresenter {

        /**
         * 创建案例筛选列表适配器
         *
         * @return
         */
        CommonRecyclerAdapter<CaseBean> createCaseTypeAdapter();

        /**
         * 创建案例列表适配器
         *
         * @return
         */
        CommonRecyclerAdapter<CaseTypeEnum> createCaseListAdapter();

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


        void loadCaseListDate();

        void loadCaseTypeData();

    }

}
