package com.yingwumeijia.android.ywmj.client.function.setpassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.rx.android.jamspeedlibrary.utils.T;
import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.utils.base.activity.BaseActivity;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 16/6/17 下午9:46.
 * Describe:
 */
public class SetPwdActivity extends BaseActivity {
    @Bind(R.id.topTitle)
    TextView topTitle;
    @Bind(R.id.topLeft)
    TextView topLeft;
    @Bind(R.id.topLeft_second)
    TextView topLeftSecond;
    @Bind(R.id.topRight)
    TextView topRight;
    @Bind(R.id.ed_Old_pwd)
    EditText edOldPwd;
    @Bind(R.id.ed_new_pwd)
    EditText edNewPwd;
    @Bind(R.id.ed_new_pwd_confirm)
    EditText edNewPwdConfirm;
    @Bind(R.id.btn_confirm)
    Button btnConfrm;

    private String old_pwd;
    private String new_pwd;
    private String new_pwd_confirm;

    private boolean old_pwd_ok;
    private boolean new_pwd_ok;
    private boolean new_pwd_confirm_ok;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SetPwdActivity.class);
        ActivityCompat.startActivity(activity, intent, Bundle.EMPTY);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.password_set_act;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        initActionBar();
        initView();
    }

    private void initView() {
        edOldPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                old_pwd_ok = oldPwdCheck(edOldPwd);
            }

            @Override
            public void afterTextChanged(Editable s) {
                btnConfrm.setEnabled(s.toString().length() > 0);
            }
        });

        edNewPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new_pwd_ok = newPwdCheckOk(edNewPwd);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edNewPwdConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new_pwd_confirm_ok = newPwdConfirmCheckOk(edNewPwdConfirm);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean newPwdConfirmCheckOk(EditText edNewPwdConfirm) {
        return edNewPwdConfirm.getText().toString().equals(new_pwd);
    }

    private boolean newPwdCheckOk(EditText edNewPwd) {
        new_pwd = edNewPwd.getText().toString();
        return edNewPwd.getText().toString().length() >= Constant.PASSWORD_LENGTH_MINI && edNewPwd.getText().toString().length() <= Constant.PASSWORD_LENGTH_MAX;
    }

    private boolean oldPwdCheck(EditText edOldPwd) {
        return edOldPwd.getText().toString().length() >= Constant.PASSWORD_LENGTH_MINI && edOldPwd.getText().toString().length() <= Constant.PASSWORD_LENGTH_MAX;
    }


    private void initActionBar() {
        topTitle.setText("密码设置");
        TextViewUtils.setDrawableToLeft(context, topLeft, R.mipmap.back_ico);
    }

    @OnClick({R.id.topLeft, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                ActivityCompat.finishAfterTransition(context);
                break;
            case R.id.btn_confirm:
                if (inputCheckOk()) {
                    changePassword();
                }
                break;
        }
    }

    private void changePassword() {
        showBaseProgresDialog();
        MyApp
                .getApiService()
                .setPassword(old_pwd, new_pwd_confirm)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        dismisBaseProgressDialog();
                        if (response.body().getSucc()) {
                            T.showShort(context, R.string.edit_password_succ);
                            Constant.saveUserPassword(new_pwd_confirm, context);
                            ActivityCompat.finishAfterTransition(context);
                        } else {
                            T.showShort(context, response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        dismisBaseProgressDialog();
                        showBaseNetConnectError();
                    }
                });
    }

    private boolean inputCheckOk() {
        if (!old_pwd_ok) {
            edOldPwd.setError(getResources().getString(R.string.input_password_error));
            return false;
        }

        if (!new_pwd_ok) {
            edNewPwd.setError(getResources().getString(R.string.input_password_error));
            return false;
        }


        if (!new_pwd_confirm_ok) {
            edNewPwdConfirm.setError(getResources().getString(R.string.input_password_error));
            return false;
        }

        old_pwd = edOldPwd.getText().toString();
        new_pwd = edNewPwd.getText().toString();
        new_pwd_confirm = edNewPwdConfirm.getText().toString();
        return true;
    }
}
