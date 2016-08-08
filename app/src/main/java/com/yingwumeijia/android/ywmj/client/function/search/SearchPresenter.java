package com.yingwumeijia.android.ywmj.client.function.search;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.adapter.CommonRecyclerAdapter;
import com.rx.android.jamspeedlibrary.utils.adapter.RecyclerViewHolder;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.XRecyclerView;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseBean;
import com.yingwumeijia.android.ywmj.client.utils.StartActivityManager;

import java.util.List;

/**
 * Created by Jam on 2016/8/8 15:36.
 * Describe:
 */
public class SearchPresenter implements SearchContract.Presenter,XRecyclerView.LoadingListener {

    private final Context context;

    private final SearchContract.View mView;

    private int page_num = 1;

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

    }

    @Override
    public void start() {
        mXRecyclerView.setLoadingListener(this);
        createListAdapter();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
