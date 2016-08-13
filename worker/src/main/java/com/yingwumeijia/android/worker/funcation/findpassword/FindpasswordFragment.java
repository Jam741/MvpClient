package com.yingwumeijia.android.worker.funcation.findpassword;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.T;
import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.utils.base.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jam on 2016/8/4 15:59.
 * Describe:
 */
public class FindpasswordFragment extends BaseFragment implements FindPasswordContract.View ,
        TextView.OnEditorActionListener{

    View root;
    @Bind(R.id.ed_phone)
    TextInputEditText edPhone;
    @Bind(R.id.ed_smsCode)
    TextInputEditText edSmsCode;
    @Bind(R.id.btn_send_code)
    Button btnSendSmsCode;
    @Bind(R.id.ed_password)
    TextInputEditText edPassword;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    private FindPasswordContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (root == null) {
            root = inflater.inflate(R.layout.findpassword_frag, container, false);
            ButterKnife.bind(this, root);
            edPhone.addTextChangedListener(phoneTextWatcher);
        }
        return root;
    }

    @Override
    public void showFindPasswordSuccess() {
        T.showShort(context, "密码重置成功");
    }

    @Override
    public void showFindPasswordError(String msg) {
        T.showShort(context, msg);
    }

    @Override
    public void showInputPhoneError() {
        edPhone.setError(getResources().getString(R.string.input_phone_error));
    }

    @Override
    public void showInputPasswordError() {
        edPassword.setError(getResources().getString(R.string.input_password_error));
    }

    @Override
    public void showInputSmsCodeError() {
        edSmsCode.setError(getResources().getString(R.string.input_smsCode_error));
    }

    @Override
    public void unlockSendSmsCode() {
        btnSendSmsCode.setEnabled(true);
    }

    @Override
    public void unlockFindPassword() {
        btnConfirm.setEnabled(true);
    }

    @Override
    public void startLoginFunction() {

    }

    @Override
    public void lockSendSmsButton() {
        btnSendSmsCode.setEnabled(false);
        btnSendSmsCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_gradual_bg_round));
        btnSendSmsCode.setBackgroundColor(Color.parseColor("#F7A3C0"));
        btnSendSmsCode.setText("60s");
        btnSendSmsCode.setTextColor(getResources().getColor(R.color.text_color_whide));
    }

    @Override
    public void showSendSmsCodeSucess() {
        T.showShort(context, R.string.sendSmsCode_succ);
    }

    @Override
    public void showSendSmsCodeError(String message) {
        T.showShort(context, message);
    }

    @Override
    public void refreshSendSmsButtonText(String text) {
        btnSendSmsCode.setText(text);
    }

    @Override
    public void unLockSendSmsButton() {
        btnSendSmsCode.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        btnSendSmsCode.setEnabled(true);
        btnSendSmsCode.setText("重新发出");
    }

    @Override
    public void finish() {
        ActivityCompat.finishAfterTransition(context);
    }

    @Override
    public void setPresener(FindPasswordContract.Presenter presenter) {
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

    public static FindpasswordFragment newInstance() {
        return new FindpasswordFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_send_code, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code:
                mPresenter.sendSmsCode(edPhone.getText().toString());
                break;
            case R.id.btn_confirm:
                mPresenter.findPassword(edPhone.getText().toString(),
                        edSmsCode.getText().toString(),
                        edPassword.getText().toString());
                break;
        }
    }


    TextWatcher phoneTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            unlockSendSmsCode();
            unlockFindPassword();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destory();
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_GO){

            InputMethodManager imm =
                    (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm.isActive()){
                imm.hideSoftInputFromWindow(textView.getApplicationWindowToken(),0);
            }


            mPresenter.findPassword(edPhone.getText().toString(),
                    edSmsCode.getText().toString(),
                    edPassword.getText().toString());
            return true;
        }
        return false;
    }
}
