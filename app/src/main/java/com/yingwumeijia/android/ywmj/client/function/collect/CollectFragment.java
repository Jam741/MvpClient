package com.yingwumeijia.android.ywmj.client.function.collect;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rx.android.jamspeedlibrary.utils.T;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.XRecyclerView;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseBean;
import com.yingwumeijia.android.ywmj.client.utils.base.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jam on 2016/8/8 11:21.
 * Describe:
 */
public class CollectFragment extends BaseFragment implements CollectContract.View {

    private CollectContract.Presenter mPresenter;
    private View root;
    @Bind(R.id.rv_collect)
    XRecyclerView rvCollect;

    public static CollectFragment newInstance() {

        Bundle args = new Bundle();

        CollectFragment fragment = new CollectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.collect_frag, container, false);
            ButterKnife.bind(this, root);
        }
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    @Override
    public boolean showCancelDialog(final OnCancelClickListener onCancelClickListener) {
        AlertDialog builder = new AlertDialog.Builder(context)
                .setMessage(R.string.dialog_clear_cache)
                .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onCancelClickListener.confirm();
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onCancelClickListener.cancel();
                    }
                })
                .show();
        return false;
    }

    @Override
    public void showCancelFail(String msg) {
        T.showShort(context, msg);
    }

    @Override
    public void showEmptyLayout() {

    }

    @Override
    public void showListLayout() {

    }

    @Override
    public void showGetListFail(String msg) {
        T.showShort(context, msg);
    }

    @Override
    public void loadMoreComplete() {
        rvCollect.loadMoreComplete();
    }

    @Override
    public void refreshComplete() {
        rvCollect.refreshComplete();
    }

    @Override
    public void loadNoMore() {
        rvCollect.noMoreLoading();
    }

    @Override
    public void loadRset() {
        rvCollect.reset();
    }

    @Override
    public XRecyclerView getRecyclerView() {
        return rvCollect;
    }

    @Override
    public void setPresener(CollectContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNetConnectError() {
        showBaseNetConnectError();
    }

    @Override
    public void showProgressBar() {
        showBaseProgresDialog();
    }

    @Override
    public void dismissProgressBar() {
        dismisBaseProgressDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
