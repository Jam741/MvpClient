package com.yingwumeijia.android.worker.funcation.collect;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.rx.android.jamspeedlibrary.utils.ScreenUtils;
import com.rx.android.jamspeedlibrary.utils.T;
import com.rx.android.jamspeedlibrary.utils.adapter.CommonRecyclerAdapter;
import com.rx.android.jamspeedlibrary.utils.adapter.RecyclerViewHolder;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.LoadingMoreFooter;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.XRecyclerView;
import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.WorkerApp;
import com.yingwumeijia.android.worker.data.bean.BaseBean;
import com.yingwumeijia.android.worker.data.bean.CaseBean;
import com.yingwumeijia.android.worker.data.bean.CaseListResultBean;
import com.yingwumeijia.android.worker.funcation.person.PersonFragment;
import com.yingwumeijia.android.worker.utils.StartActivityManager;
import com.yingwumeijia.android.worker.utils.base.fragment.BaseFragment;
import com.yingwumeijia.android.worker.utils.constants.Constant;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 2016/6/8 17:29.
 * Describe:
 */
public class CollectOldFragment extends BaseFragment {

    @Bind(R.id.rv_collect)
    XRecyclerView rvCollect;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;

    private int pageNum = 1;

    private CommonRecyclerAdapter<CaseBean> mAdapter;
    private AlertDialog.Builder dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Constant.isLogin(context)) {
            initData();
            initView();
        } else {
            showEmptyLayout(true);
        }
    }

    private void initView() {
        rvCollect.setLayoutManager(new GridLayoutManager(context, 2));
        rvCollect.setAdapter(mAdapter);
        rvCollect.setLoadingListener(loadingListener);
        rvCollect.addFootView(new LoadingMoreFooter(context, "已经全部加载完毕"));
        rvCollect.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).margin(R.dimen.vertical_margin).build());
    }

    private void initData() {
        createAdapter();
        getCollectList(true);
    }

    /**
     * 创建收藏列表适配器
     */
    private void createAdapter() {
        mAdapter = new CommonRecyclerAdapter<CaseBean>(null, context, R.layout.item_collect_list) {
            @Override
            public void convert(RecyclerViewHolder holder, final CaseBean caseBean, final int position) {
                holder.setText(R.id.tv_name, caseBean.getCaseName())
                        .setImageURL(R.id.iv_case, caseBean.getCaseCover(), context)
                        .setSize(R.id.item_layout, (ScreenUtils.getScreenWidth() - 10) / 2, ((ScreenUtils.getScreenWidth() - 10) / 2) * 680 / 720)
                        .setOnClickListener(R.id.iv_cancel_collection, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                currentCaseId = caseBean.getCaseId();
                                currentPositon = position;
                                showCancelDialog();
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


    private int currentPositon;
    private int currentCaseId;

    private void showCancelDialog() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(context)
                    .setMessage("确定取消收藏?")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelCollect();
                        }
                    });
        }
        dialog.show();
    }


    /**
     * 取消收藏
     */
    private void cancelCollect() {
        WorkerApp
                .getApiService()
                .cancelCollection(currentCaseId)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if (response.body().getSucc()) {
                            mAdapter.remove(currentPositon);
                            sendMessage();
                            checkCllectAdapterNotNull();
                            pageNum = (mAdapter.getItemCount()) / 10;
                            LogUtil.getInstance().debug("jam", "-----xx--==============on==" + pageNum);
                        } else {
                            T.showShort(context, response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        showBaseNetConnectError();
                    }
                });

    }

    private void sendMessage() {
        Intent intent = new Intent(PersonFragment.ACTION_CHANGE_COLLECT_COUNT);
        context.sendBroadcast(intent);
    }

    private void checkCllectAdapterNotNull() {
        if (mAdapter.getItemCount() == 0) {
            pageNum = 1;
            getCollectList(false);
        }
    }


    private void checkAdapterNotNull() {
        if (mAdapter.getItemCount() == 0) showEmptyLayout(true);
        else showEmptyLayout(false);
    }

    /**
     * 获取收藏数据
     *
     * @param isRef
     */
    private void getCollectList(final boolean isRef) {

        WorkerApp
                .getApiService()
                .getCollectionList(pageNum, Constant.PAGE_SIZE)
                .enqueue(new Callback<CaseListResultBean>() {
                    @Override
                    public void onResponse(Call<CaseListResultBean> call, Response<CaseListResultBean> response) {
                        if (response.body().getSucc()) {
                            if (response.body().getData().size() == 0 || response.body().getData() == null) {
                                checkAdapterNotNull();
                                if (isRef) {
                                    rvCollect.refreshComplete();
                                    showEmptyLayout(true);
                                } else {
                                    pageNum = 1;
                                    rvCollect.loadMoreComplete();
                                    rvCollect.noMoreLoading();
                                }
                            } else {
                                if (isRef) {
                                    rvCollect.refreshComplete();
                                    rvCollect.setIsnomore(false);
                                    mAdapter.refreshData(response.body().getData());
                                } else {
                                    rvCollect.loadMoreComplete();
                                    mAdapter.addRange(removeRepeta(response.body().getData()));
                                }
                                checkAdapterNotNull();
                            }
                        } else {
                            T.showShort(context, getString(R.string.net_network_fail));
                            rvCollect.loadMoreComplete();
                            rvCollect.refreshComplete();
                        }
                    }

                    @Override
                    public void onFailure(Call<CaseListResultBean> call, Throwable t) {
                        showBaseNetConnectError();
                    }
                });
    }


    /**
     * 收藏数据去重
     *
     * @param data
     * @return
     */
    private List<CaseBean> removeRepeta(List<CaseBean> data) {
        List<CaseBean> adapterList = mAdapter.getDatas();
        List<CaseBean> newList = data;
        List<CaseBean> deleList = new ArrayList<>();
        deleList.clear();
        for (CaseBean newCaseBean : data) {
            for (CaseBean oldCaseBean : adapterList) {
                if (newCaseBean.getCaseId() == oldCaseBean.getCaseId())
                    deleList.add(newCaseBean);
            }
        }
        newList.removeAll(deleList);
        return data;
    }

    XRecyclerView.LoadingListener loadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {
            pageNum = 1;
            getCollectList(true);
        }

        @Override
        public void onLoadMore() {
            pageNum++;
            getCollectList(false);
        }
    };

    /**
     * 是否显示空
     *
     * @param isEmpty
     */
    public void showEmptyLayout(boolean isEmpty) {
        if (isEmpty) {
            emptyLayout.setVisibility(View.VISIBLE);
            rvCollect.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.GONE);
            rvCollect.setVisibility(View.VISIBLE);
        }
    }

    public interface OnCollectionCancelListener {
        void canceled(boolean isCancel);
    }
}
