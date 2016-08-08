package com.yingwumeijia.android.ywmj.client.function.collect;

import android.content.Context;
import android.view.View;

import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.rx.android.jamspeedlibrary.utils.ScreenUtils;
import com.rx.android.jamspeedlibrary.utils.adapter.CommonRecyclerAdapter;
import com.rx.android.jamspeedlibrary.utils.adapter.RecyclerViewHolder;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.LoadingMoreFooter;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.XRecyclerView;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseListResultBean;
import com.yingwumeijia.android.ywmj.client.utils.StartActivityManager;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 2016/8/7 10:16.
 * Describe:
 */
public class CollectPresenter implements CollectContract.Presenter,
        XRecyclerView.LoadingListener {

    private final Context context;

    private final CollectContract.View mCollectView;

    private XRecyclerView xRecyclerView;
    private CommonRecyclerAdapter<CaseBean> mListAdapter;
    private boolean isRefresh = true;
    private int page_num;
    private int currentCaseId;
    private int currentPosition;

    public CollectPresenter(Context context, CollectContract.View mCollectView) {
        this.context = context;
        this.mCollectView = mCollectView;
        this.mCollectView.setPresener(this);
        this.xRecyclerView = mCollectView.getRecyclerView();
    }

    @Override
    public void cancelCollect(int caseId) {
        MyApp
                .getApiService()
                .cancelCollection(caseId)
                .enqueue(cancleCollectCallback);
    }

    @Override
    public void cancelOperation() {
        mListAdapter.remove(currentPosition);
    }

    @Override
    public void getCollectList() {
        MyApp
                .getApiService()
                .getCollectionList(page_num, Constant.PAGE_SIZE)
                .enqueue(caseListCallback);
    }


    @Override
    public void createCollectListAdapter() {
        mListAdapter = new CommonRecyclerAdapter<CaseBean>(null, context, R.layout.item_collect_list) {
            @Override
            public void convert(RecyclerViewHolder holder, final CaseBean caseBean, final int position) {
                holder.setText(R.id.tv_name, caseBean.getCaseName())
                        .setImageURL(R.id.iv_case, caseBean.getCaseCover(), context)
                        .setSize(R.id.item_layout, (ScreenUtils.getScreenWidth() - 10) / 2, ((ScreenUtils.getScreenWidth() - 10) / 2) * 680 / 720)
                        .setOnClickListener(R.id.iv_cancel_collection, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LogUtil.getInstance().debug("jam", "========position:" + position);
                                currentCaseId = caseBean.getCaseId();
                                currentPosition = position;
                                mCollectView.showCancelDialog(onCancelClickListener);
                            }
                        })
                        .setOnItemClickListener(new RecyclerViewHolder.OnItemCliceListener() {
                            @Override
                            public void itemClick(View itemView, int position) {
                                StartActivityManager.startCaseDetailActivity(context, caseBean.getCaseId());
                            }
                        });
            }
        };
    }


    CollectContract.View.OnCancelClickListener onCancelClickListener = new CollectContract.View.OnCancelClickListener() {
        @Override
        public void confirm() {
            cancelCollect(currentCaseId);
        }

        @Override
        public void cancel() {

        }
    };

    @Override
    public void refreshData(List<CaseBean> caseBeanList) {
        mListAdapter.refreshData(caseBeanList);
    }

    @Override
    public void loadMoreData(List<CaseBean> caseBeanList) {
        mListAdapter.addRange(caseBeanList);
    }

    @Override
    public void start() {
        if (Constant.isLogin(context)) {
            createCollectListAdapter();
            xRecyclerView = mCollectView.getRecyclerView();
            xRecyclerView.setLoadingListener(this);
            xRecyclerView.addFootView(new LoadingMoreFooter(context,"已经全部加载完毕"));
            xRecyclerView.setAdapter(mListAdapter);
        }
    }


    Callback<CaseListResultBean> caseListCallback = new Callback<CaseListResultBean>() {
        @Override
        public void onResponse(Call<CaseListResultBean> call, Response<CaseListResultBean> response) {
            mCollectView.dismissProgressBar();
            if (response.body().getSucc()) {
                if (isRefresh) {
                    mCollectView.refreshComplete();
                    mCollectView.loadRset();
                    refreshData(response.body().getData());
                } else {
                    mCollectView.loadMoreComplete();
                    if (response.body().getData() == null || response.body().getData().size() == 0) {
                        mCollectView.loadNoMore();
                    } else {
                        loadMoreData(response.body().getData());
                    }
                }
            } else {
                mCollectView.showCancelFail(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<CaseListResultBean> call, Throwable t) {
            mCollectView.dismissProgressBar();
            mCollectView.showNetConnectError();
        }
    };

    Callback<BaseBean> cancleCollectCallback = new Callback<BaseBean>() {
        @Override
        public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
            mCollectView.dismissProgressBar();
            if (response.body().getSucc()) {
                mListAdapter.remove(currentPosition);
            } else {
                mCollectView.showCancelFail(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<BaseBean> call, Throwable t) {
            mCollectView.dismissProgressBar();
            mCollectView.showNetConnectError();
        }
    };

    @Override
    public void onRefresh() {
        page_num = 1;
        getCollectList();
    }

    @Override
    public void onLoadMore() {
        page_num++;
        getCollectList();

    }
}
