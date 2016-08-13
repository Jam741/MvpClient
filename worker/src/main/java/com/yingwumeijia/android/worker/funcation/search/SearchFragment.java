package com.yingwumeijia.android.worker.funcation.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.T;
import com.rx.android.jamspeedlibrary.view.xrecyclerview.XRecyclerView;
import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.utils.base.fragment.BaseFragment;

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

        mPresenter.start();

        edKeyWords.addTextChangedListener(searchTextWatch);

        edKeyWords.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    mPresenter.search(getKeyWords());
                }
                return false;
            }
        });
    }

    @Override
    public void showSearchFail(String msg) {
        T.showShort(context, msg);
    }

    @Override
    public void showKeywordsError() {
        T.showShort(context, R.string.input_keywords_error);
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
        return edKeyWords.getText().toString().trim();
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
