package com.yingwumeijia.android.ywmj.client.function.search;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.T;
import com.rx.android.jamspeedlibrary.utils.adapter.CommonRecyclerAdapter;
import com.rx.android.jamspeedlibrary.utils.adapter.RecyclerViewHolder;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.XRecyclerView;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseListResultBean;
import com.yingwumeijia.android.ywmj.client.utils.StartActivityManager;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 2016/8/8 15:36.
 * Describe:
 */
public class SearchPresenter implements SearchContract.Presenter, XRecyclerView.LoadingListener {

    private final Context context;

    private final SearchContract.View mView;

    private boolean isRefresh = true;
    private int page_num = 1;
    private String mKey_words = null;

    private CommonRecyclerAdapter<CaseBean> mAdapter;
    private final XRecyclerView mXRecyclerView;

    public SearchPresenter(Context context, SearchContract.View mView) {
        this.context = context;
        this.mView = mView;
        this.mView.setPresener(this);
        this.mXRecyclerView = (XRecyclerView) mView.getRecyclerView();
    }

    @Override
    public void createListAdapter() {
        mAdapter = new CommonRecyclerAdapter<CaseBean>(null, context, R.layout.item_case_list) {
            @Override
            public void convert(RecyclerViewHolder holder, final CaseBean caseBean, int position) {

                holder
                        .setText(R.id.tv_collect_count, "收藏" + caseBean.getCollectionNumber())
                        .setText(R.id.tv_name, caseBean.getCaseName())
                        .setImageURL(R.id.iv_icon, caseBean.getCaseCover(), context)
                        .setOnItemClickListener(new RecyclerViewHolder.OnItemCliceListener() {
                            @Override
                            public void itemClick(View itemView, int position) {
                                listItemClickOperation(caseBean);
                            }
                        });
            }
        };
    }

    @Override
    public void refrshData(List<CaseBean> caseBeanList) {
        mAdapter.refreshData(caseBeanList);
    }

    @Override
    public void loadMoreData(List<CaseBean> caseBeanList) {
        mAdapter.addRange(caseBeanList);
    }

    @Override
    public void bindAdapterWithList() {
        mXRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void listItemClickOperation(CaseBean caseBean) {
        StartActivityManager.startCaseDetailActivity(context, caseBean.getCaseId());
    }

    @Override
    public void search(String keyWords) {
        mKey_words = keyWords;
        MyApp
                .getApiService()
                .getSearchCaseList(mKey_words, page_num, Constant.PAGE_SIZE)
                .enqueue(searchKeyWordsCallback);
    }

    @Override
    public void start() {
        mXRecyclerView.setLoadingListener(this);
        createListAdapter();
    }

    @Override
    public void onRefresh() {
        page_num = 1;
        isRefresh = true;
        search(mKey_words);
    }

    @Override
    public void onLoadMore() {
        page_num++;
        isRefresh = false;
        search(mKey_words);
    }

    Callback<CaseListResultBean> searchKeyWordsCallback = new Callback<CaseListResultBean>() {
        @Override
        public void onResponse(Call<CaseListResultBean> call, Response<CaseListResultBean> response) {
            mView.dismissProgressBar();
            if (response.body().getSucc()) {
                if (isRefresh) {
                    mXRecyclerView.refreshComplete();
                    mXRecyclerView.reset();
                    refrshData(response.body().getData());
                } else {
                    mXRecyclerView.loadMoreComplete();
                    if (response.body().getData() == null || response.body().getData().size() == 0) {
                        mXRecyclerView.setIsnomore(true);
                    } else {
                        loadMoreData(response.body().getData());
                    }
                }

            } else {
                T.showShort(context, response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<CaseListResultBean> call, Throwable t) {
            mView.dismissProgressBar();
            mView.showNetConnectError();
        }
    };
}
