package com.yingwumeijia.android.ywmj.client.function.mainfunction;

import android.content.Context;
import android.view.View;

import com.rx.android.jamspeedlibrary.utils.adapter.CommonRecyclerAdapter;
import com.rx.android.jamspeedlibrary.utils.adapter.RecyclerViewHolder;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.XRecyclerView;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseListResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseTypeEnum;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseTypeResultBean;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 2016/8/4 19:55.
 * Describe:
 */
public class CaseListPresenter implements CaseListContract.Presenter, XRecyclerView.LoadingListener {

    private final Context context;

    private final CaseListContract.View mCaseListView;

    private CommonRecyclerAdapter<CaseBean> mCaseListAdapter;

    private CommonRecyclerAdapter<CaseTypeEnum> mNavigationAdapter;

    private boolean isRefresh;
    private int page_Num = 1;//页码
    private int stytle_id = 0;//造价ID
    private int hoseType_id = 0;//房型ID
    private int cost_id = 0;//造价ID

    public CaseListPresenter(Context context, CaseListContract.View mCaseListView) {
        this.context = context;
        this.mCaseListView = mCaseListView;
    }

    @Override
    public CommonRecyclerAdapter<CaseBean> createCaseTypeAdapter() {
        mCaseListAdapter = new CommonRecyclerAdapter<CaseBean>(null, context, R.layout.item_case_list) {
            @Override
            public void convert(RecyclerViewHolder holder, final CaseBean caseBean, int position) {

                holder
                        .setText(R.id.tv_collect_count, "收藏" + caseBean.getCollectionNumber())
                        .setText(R.id.tv_name, caseBean.getCaseName())
                        .setImageURL(R.id.iv_icon, caseBean.getCaseCover(), context)
                        .setOnItemClickListener(new RecyclerViewHolder.OnItemCliceListener() {
                            @Override
                            public void itemClick(View itemView, int position) {
                                caseListItemClick(caseBean);
                            }
                        });
            }
        };
        return mCaseListAdapter;
    }

    @Override
    public CommonRecyclerAdapter<CaseTypeEnum> createCaseListAdapter() {
        mNavigationAdapter = new CommonRecyclerAdapter<CaseTypeEnum>(null, context, R.layout.item_case_type) {
            @Override
            public void convert(RecyclerViewHolder holder, final CaseTypeEnum caseTypeEnum, int position) {
                holder
                        .setText(R.id.tv_type, caseTypeEnum.getName())
                        .setOnItemClickListener(new RecyclerViewHolder.OnItemCliceListener() {
                            @Override
                            public void itemClick(View itemView, int position) {
                                mCaseListView.closeDrawerLayout();
                                int id = caseTypeEnum.getId();
                                caseTypeEnum.setSelected(true);
                                switch (mCaseListView.getNavigationPosition()) {
                                    case 0:
                                        stytle_id = id;
                                        break;
                                    case 1:
                                        hoseType_id = id;
                                        break;
                                    case 2:
                                        cost_id = id;
                                        break;
                                }
                                caseTypeListItemSelected(caseTypeEnum, mCaseListView.getNavigationPosition());
                            }
                        });
            }
        };
        return mNavigationAdapter;
    }

    @Override
    public void refreshCaseData(List<CaseBean> caseBeanList) {
        mCaseListAdapter.refreshData(caseBeanList);
    }

    @Override
    public void addCaseData(List<CaseBean> caseBeanList) {
        mCaseListAdapter.addRange(caseBeanList);
    }

    @Override
    public void bindCaseListAdapter() {
        mCaseListView.getCaseListView().setAdapter(mCaseListAdapter);
    }

    @Override
    public void bindCaseTypeAdapter() {
        mCaseListView.getCaseTypeListView().setAdapter(mNavigationAdapter);
    }

    @Override
    public void caseTypeListItemSelected(CaseTypeEnum caseTypeEnum, int navigationPosition) {
        mCaseListView.refreshNavigationStatus(caseTypeEnum, navigationPosition);
    }

    @Override
    public void caseListItemClick(CaseBean caseBean) {
        mCaseListView.startCaseDetailActivity(caseBean);
    }

    @Override
    public void loadCaseListDate() {
        mCaseListView.showProgressBar();
        MyApp
                .getApiService()
                .getCaseList(page_Num, Constant.PAGE_SIZE, stytle_id, hoseType_id, cost_id)
                .enqueue(caseListCallback);

    }

    @Override
    public void loadCaseTypeData() {
        mCaseListView.showProgressBar();
        MyApp
                .getApiService()
                .getCaseTypeSet()
                .enqueue(caseTypeCallback);
    }

    @Override
    public void start() {
        loadCaseListDate();
        loadCaseTypeData();
    }

    @Override
    public void onRefresh() {
        page_Num = 1;
        isRefresh = true;
        start();
    }

    @Override
    public void onLoadMore() {
        page_Num++;
        isRefresh = false;
        start();
    }


    /**
     * 案例列表回调
     */
    Callback<CaseListResultBean> caseListCallback = new Callback<CaseListResultBean>() {
        @Override
        public void onResponse(Call<CaseListResultBean> call, Response<CaseListResultBean> response) {
            mCaseListView.dismissProgressBar();
            if (response.body().getSucc()) {

            } else {

            }
        }

        @Override
        public void onFailure(Call<CaseListResultBean> call, Throwable t) {
            mCaseListView.dismissProgressBar();
            mCaseListView.showNetConnectError();
        }
    };

    /**
     * 筛选列表回调
     */
    Callback<CaseTypeResultBean> caseTypeCallback = new Callback<CaseTypeResultBean>() {
        @Override
        public void onResponse(Call<CaseTypeResultBean> call, Response<CaseTypeResultBean> response) {
            mCaseListView.dismissProgressBar();

        }

        @Override
        public void onFailure(Call<CaseTypeResultBean> call, Throwable t) {
            mCaseListView.dismissProgressBar();
            mCaseListView.showNetConnectError();
        }
    };
}
