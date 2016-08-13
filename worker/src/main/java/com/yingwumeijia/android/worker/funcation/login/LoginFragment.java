package com.yingwumeijia.android.worker.funcation.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.T;
import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.funcation.findpassword.FindPasswordActivity;
import com.yingwumeijia.android.worker.utils.base.fragment.BaseFragment;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public class LoginFragment extends BaseFragment implements LoginContract.View,
        View.OnClickListener, TextView.OnEditorActionListener {

    private TextInputEditText ed_phone;
    private EditText ed_password;
    private Button btnLogin;
    private TextView btnFindPassword;
    private LoginContract.Presenter mPresenter;
    private View root;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (root == null) {
            root = inflater.inflate(R.layout.login_frag, container, false);

            //bind View
            ed_phone = (TextInputEditText) root.findViewById(R.id.ed_phone);
            ed_password = (EditText) root.findViewById(R.id.ed_password);
            btnLogin = (Button) root.findViewById(R.id.btn_login);
            btnFindPassword = (TextView) root.findViewById(R.id.btn_findPwd);

            //set Listener
            btnLogin.setOnClickListener(this);
            btnFindPassword.setOnClickListener(this);
            ed_password.setOnEditorActionListener(this);
            ed_phone.addTextChangedListener(phoneTextWatcher);
        }
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mPresenter.login(ed_phone.getText().toString(), ed_password.getText().toString(), null);
                break;
            case R.id.btn_findPwd:
                FindPasswordActivity.start(context, ed_phone.getText().toString());
                break;
        }
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
    public void showPhoneInputError() {
        ed_phone.setError(getResources().getString(R.string.input_phone_error));
    }

    @Override
    public void showPassordInputError() {
        ed_password.setError(getResources().getString(R.string.input_password_error));
    }

    @Override
    public void showLoginError(String errorMessage) {
        T.showShort(context,errorMessage);
    }

    @Override
    public void showLoginSuccess() {
        T.showShort(context, R.string.login_sucess);
    }

    @Override
    public void cleanPassord() {
//        ed_phone.setText("");
//        ed_password.setText("");
    }

    @Override
    public void loginUnlock() {
        btnLogin.setEnabled(true);
    }

    @Override
    public void finish() {
        ActivityCompat.finishAfterTransition(getActivity());
    }


    @Override
    public void setPresener(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNetConnectError() {
        showBaseNetConnectError();
    }


    TextWatcher phoneTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            loginUnlock();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        Log.d("jam",""+i);
        if (i == EditorInfo.IME_ACTION_GO) {
                /*隐藏软键盘*/
            InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
            }
            mPresenter.login(ed_phone.getText().toString(), ed_password.getText().toString(), null);
            return true;
        }
        return false;
    }
}
