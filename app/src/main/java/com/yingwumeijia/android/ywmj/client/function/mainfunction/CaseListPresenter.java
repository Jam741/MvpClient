package com.yingwumeijia.android.ywmj.client.function.mainfunction;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.rx.android.jamspeedlibrary.utils.adapter.CommonRecyclerAdapter;
import com.rx.android.jamspeedlibrary.utils.adapter.RecyclerViewHolder;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.LoadingMoreFooter;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.XRecyclerView;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseListResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseTypeEnum;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseTypeResultBean;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private CaseTypeAdapter mNavigationAdapter;

    private boolean isRefresh = true;
    private int page_Num = 1;//页码
    private int stytle_id = 0;//造价ID
    private int hoseType_id = 0;//房型ID
    private int cost_id = 0;//造价ID

    private static final String KEY_HOUS_TYPE = "KEY_HOUS_TYPE";
    private static final String KEY_COST_RANGE_TYPE = "KEY_COST_RANGE_TYPE";
    private static final String KEY_DECORATE_STYLE_TYPE = "KEY_DECORATE_STYLE_TYPE";

    private Map<String, List<CaseTypeEnum>> mType_set;

    public CaseListPresenter(Context context, CaseListContract.View mCaseListView) {
        this.context = context;
        this.mCaseListView = mCaseListView;
        this.mCaseListView.setPresener(this);
    }

    @Override
    public CommonRecyclerAdapter<CaseBean> createCaseListAdapter() {
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
    public CaseTypeAdapter createCaseTypeAdapter() {
        mNavigationAdapter = new CaseTypeAdapter(context);
        mNavigationAdapter.setOnMyItemClickLisenter(new CaseTypeAdapter.OnMyItemClickLisenter() {
            @Override
            public void itemClick(CaseTypeEnum caseTypeEnum, int positon) {
                mNavigationAdapter.setSelectedPosition(positon);
                caseTypeListItemSelected(caseTypeEnum, mCaseListView.getNavigationPosition());
            }
        });

        return mNavigationAdapter;
    }

    @Override
    public void refreshCaseData(List<CaseBean> caseBeanList) {
        Log.d("jam", "caseList" + caseBeanList.size() + "");
        mCaseListAdapter.refreshData(caseBeanList);
    }

    @Override
    public void addCaseData(List<CaseBean> caseBeanList) {
        mCaseListAdapter.addRange(caseBeanList);
    }

    @Override
    public void bindCaseListAdapter() {
        mCaseListView.getCaseListView().setLoadingListener(this);
        mCaseListView.getCaseListView().setAdapter(mCaseListAdapter);
    }

    @Override
    public void bindCaseTypeAdapter() {
        mCaseListView.getCaseTypeListView().setAdapter(mNavigationAdapter);
    }

    @Override
    public void caseTypeListItemSelected(CaseTypeEnum caseTypeEnum, int navigationPosition) {
        int id = caseTypeEnum.getId();
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
        mCaseListView.closeDrawerLayout();
        mCaseListView.refreshNavigationStatus(caseTypeEnum.getName(), navigationPosition);
        mCaseListView.caseListLoadRset();
        isRefresh = true;
        page_Num = 1;
        mCaseListView.showProgressBar();
        loadCaseListDate();
    }

    @Override
    public void caseListItemClick(CaseBean caseBean) {
        mCaseListView.startCaseDetailActivity(caseBean);
    }

    @Override
    public void loadCaseListDate() {
        MyApp
                .getApiService()
                .getCaseList(page_Num, Constant.PAGE_SIZE, stytle_id, hoseType_id, cost_id)
                .enqueue(caseListCallback);
    }

    @Override
    public void loadCaseTypeData() {
        MyApp
                .getApiService()
                .getCaseTypeSet()
                .enqueue(caseTypeCallback);
    }

    @Override
    public void refreshNavigationData(int navigationPosition) {
        if (mType_set==null|mType_set.size()==0)return;
        switch (mCaseListView.getNavigationPosition()) {
            case 0:
                mNavigationAdapter.refreshData(mType_set.get(KEY_DECORATE_STYLE_TYPE));
                break;
            case 1:
                mNavigationAdapter.refreshData(mType_set.get(KEY_HOUS_TYPE));
                break;
            case 2:
                mNavigationAdapter.refreshData(mType_set.get(KEY_COST_RANGE_TYPE));
                break;
        }
        mCaseListView.showDrawerLayout();
    }

    @Override
    public void start() {
        mCaseListView.showProgressBar();
        createCaseListAdapter();
        createCaseTypeAdapter();
        bindCaseListAdapter();
        bindCaseTypeAdapter();
        loadCaseListDate();
        loadCaseTypeData();
    }

    @Override
    public void onRefresh() {
        page_Num = 1;
        isRefresh = true;
        loadCaseListDate();
    }

    @Override
    public void onLoadMore() {
        page_Num++;
        isRefresh = false;
        loadCaseListDate();
    }


    /**
     * 案例列表回调
     */
    Callback<CaseListResultBean> caseListCallback = new Callback<CaseListResultBean>() {
        @Override
        public void onResponse(Call<CaseListResultBean> call, Response<CaseListResultBean> response) {
            mCaseListView.dismissProgressBar();
            if (response.body().getSucc()) {
                if (isRefresh) {
                    mCaseListView.caseListRefreshComplete();
                    mCaseListView.caseListLoadRset();
                    refreshCaseData(response.body().getData());
                    mCaseListView.showEmptyLayout(response.body().getData().size() == 0);
                } else {
                    mCaseListView.caseListLoadMoreComplete();
                    if (response.body().getData() == null || response.body().getData().size() == 0) {
                        mCaseListView.caseListLoadNomore();
                        page_Num = 1;
                    } else {
                        addCaseData(response.body().getData());
                    }
                }
            } else {
                mCaseListView.showLoadDataFail();
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
            if (response.body().getSucc()) {
                assemblesCaseTypeData(response.body().getData());
            } else {
                mCaseListView.showLoadDataFail();
            }
        }

        @Override
        public void onFailure(Call<CaseTypeResultBean> call, Throwable t) {
            mCaseListView.dismissProgressBar();
            mCaseListView.showNetConnectError();
        }
    };


    private void assemblesCaseTypeData(CaseTypeResultBean.CaseTypeSetBean data) {
        if (mType_set == null) {
            mType_set = new HashMap<>();
        }
        mType_set.clear();
        mType_set.put(KEY_HOUS_TYPE, data.getHouseType());
        mType_set.put(KEY_DECORATE_STYLE_TYPE, data.getDecorateStyleType());
        mType_set.put(KEY_COST_RANGE_TYPE, data.getCostRangeType());


        mNavigationAdapter.refreshData(data.getDecorateStyleType());
    }

}
