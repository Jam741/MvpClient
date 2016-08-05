package com.yingwumeijia.android.ywmj.client.function.register;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.muzhi.mtools.utils.T;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;
import com.yingwumeijia.android.ywmj.client.function.login.LoginDataProvider;
import com.yingwumeijia.android.ywmj.client.utils.StartActivityManager;
import com.yingwumeijia.android.ywmj.client.utils.base.fragment.BaseFragment;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

/**
 * Created by Jam on 2016/8/4 10:46.
 * Describe:
 */
public class RegisterFragment extends BaseFragment implements RegisterContract.View,
        View.OnClickListener {

    private RegisterContract.Presenter mRegisterPresenter;
    private View root;
    private TextInputEditText ed_phone;
    private TextInputEditText ed_smsCode;
    private TextInputEditText ed_password;
    private TextView btnSendSmsCode;
    private CheckBox cb_agree;
    private TextView btnCheckAgreement;
    private Button btnRegister;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.register_frag, container, false);

            //bind view
            ed_phone = (TextInputEditText) root.findViewById(R.id.ed_phone);
            ed_password = (TextInputEditText) root.findViewById(R.id.ed_password);
            ed_smsCode = (TextInputEditText) root.findViewById(R.id.ed_smsCode);
            btnSendSmsCode = (TextView) root.findViewById(R.id.btn_sendSmsCode);
            cb_agree = (CheckBox) root.findViewById(R.id.checkbox_agree);
            btnCheckAgreement = (TextView) root.findViewById(R.id.btn_checkAgreement);
            btnRegister = (Button) root.findViewById(R.id.btn_register);

            //setLisenter
            ed_phone.addTextChangedListener(phoneTextWatcher);
            btnSendSmsCode.setOnClickListener(this);

        }
        return root;
    }

    @Override
    public void showPhoneError() {
        ed_phone.setError(getResources().getString(R.string.input_phone_error));
    }

    @Override
    public void showPasswordError() {
        ed_password.setError(getResources().getString(R.string.input_password_error));
    }

    @Override
    public void showSmsCodeError() {
        ed_smsCode.setError(getResources().getString(R.string.input_smsCode_error));
    }

    @Override
    public void sendSmsCodeUnlock() {
        btnSendSmsCode.setEnabled(true);
    }

    @Override
    public void registerUnlock() {
        btnRegister.setEnabled(true);
    }

    @Override
    public void showSendSmsCodeSuccess() {
        T.showShort(context, R.string.sendSmsCode_succ);
    }

    @Override
    public void showSendSmsCodeError(String msg) {
        T.showShort(context, R.string.sendSmsCode_error);
    }

    @Override
    public void showRegisterSuccess() {
        T.showShort(context, R.string.register_succ);
    }

    @Override
    public void showRegisterError(String msg) {
        T.showShort(context, msg);
    }

    @Override
    public void showUnAgreement() {
        T.showShort(context, R.string.must_agree_agreement);
    }

    @Override
    public boolean returnAgreementStatus() {
        return cb_agree.isChecked();
    }

    @Override
    public void lockSendSmsButton() {
        Log.i("jam", "lockSendSmsButton");
        btnSendSmsCode.setEnabled(false);
        btnSendSmsCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_gradual_bg_round));
        btnSendSmsCode.setBackgroundColor(Color.parseColor("#F7A3C0"));
        btnSendSmsCode.setText("60s");
        btnSendSmsCode.setTextColor(getResources().getColor(R.color.text_color_whide));
    }

    @Override
    public void unlockSendSmsButton() {
        btnSendSmsCode.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        btnSendSmsCode.setEnabled(true);
        btnSendSmsCode.setText("重新发出");
    }

    @Override
    public void refreshSendSmsButtonText(String text) {
        btnSendSmsCode.setText(text);
    }

    @Override
    public void finish() {
        ActivityCompat.finishAfterTransition(context);
    }

    @Override
    public void setPresener(RegisterContract.Presenter presenter) {
        mRegisterPresenter = presenter;
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

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    TextWatcher phoneTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            sendSmsCodeUnlock();
            registerUnlock();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sendSmsCode:
                mRegisterPresenter.sendSmsCode(ed_phone.getText().toString(), Constant.PARAM_REGISTER);
                break;
            case R.id.btn_checkAgreement:
                StartActivityManager.startAgreementActivity(context);
                break;
            case R.id.btn_register:
                mRegisterPresenter.register(ed_phone.getText().toString(), ed_smsCode.getText().toString(), ed_password.getText().toString());
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRegisterPresenter.destory();
    }
}
