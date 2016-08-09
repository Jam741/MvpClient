package com.yingwumeijia.android.ywmj.client.function.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.T;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.XRecyclerView;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.utils.ActivityUtils;
import com.yingwumeijia.android.ywmj.client.utils.base.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jam on 2016/8/8 15:22.
 * Describe:
 */
public class SearchFragment extends BaseFragment implements SearchContract.View {

    @Bind(R.id.btn_cancel)
    TextView btnCancel;
    @Bind(R.id.ed_key_words)
    EditText edKeyWords;
    @Bind(R.id.btn_clear_edit)
    ImageView btnClearEdit;
    @Bind(R.id.rv_case)
    XRecyclerView rvCase;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;
    private View root;
    private SearchContract.Presenter mPresenter;

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.search_frag, container, false);
            ButterKnife.bind(this, root);
        }
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void showSearchFail(String msg) {
        T.showShort(context, msg);
    }

    @Override
    public void showEmptyLayout() {
        emptyLayout.setVisibility(View.VISIBLE);
        rvCase.setVisibility(View.GONE);
    }

    @Override
    public void showListLayout() {
        emptyLayout.setVisibility(View.GONE);
        rvCase.setVisibility(View.VISIBLE);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return rvCase;
    }

    @Override
    public void showClearEditButton() {
        btnClearEdit.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideClearEditButton() {
        btnClearEdit.setVisibility(View.GONE);
    }

    @Override
    public void clearEdit() {
        edKeyWords.setText("");
    }

    @Override
    public String getKeyWords() {
        return edKeyWords.getText().toString();
    }

    @Override
    public void setPresener(SearchContract.Presenter presenter) {
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

    @OnClick({R.id.btn_cancel, R.id.btn_clear_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                ActivityCompat.finishAfterTransition(context);
                break;
            case R.id.btn_clear_edit:
                clearEdit();
                break;
        }
    }

    TextWatcher searchTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (edKeyWords.getText().toString().length() > 0)
                showClearEditButton();
            else hideClearEditButton();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
