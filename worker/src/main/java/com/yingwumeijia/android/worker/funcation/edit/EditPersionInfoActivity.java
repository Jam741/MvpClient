package com.yingwumeijia.android.worker.funcation.edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rx.android.jamspeedlibrary.utils.PhoneNumberUtils;
import com.rx.android.jamspeedlibrary.utils.T;
import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.worker.R;
import com.yingwumeijia.android.worker.WorkerApp;
import com.yingwumeijia.android.worker.data.bean.BaseBean;
import com.yingwumeijia.android.worker.utils.base.activity.BaseActivity;
import com.yingwumeijia.android.worker.utils.constants.Constant;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 16/6/16 上午10:32.
 * Describe:
 */
public class EditPersionInfoActivity extends BaseActivity {

    @Bind(R.id.topTitle)
    TextView topTitle;
    @Bind(R.id.topLeft)
    TextView topLeft;
    @Bind(R.id.topLeft_second)
    TextView topLeftSecond;
    @Bind(R.id.topRight)
    TextView topRight;
    @Bind(R.id.ed_input)
    EditText edInput;
    @Bind(R.id.btn_clear_edit)
    ImageView btnClearEdit;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    public static final String KEY_TYPE = "key_type";
    public static final String KEY_INPUT_TEXT = "KEY_INPUT_TEXT";

    enum EDIT_TYPE {
        NICKNAME, MOB
    }

    private String mTitle;
    private EDIT_TYPE mCurrentType;
    private String mInputString = "";
    private boolean edInput_ok;
    private String input_error_msg = "";

    public static void start(Activity activity, EDIT_TYPE type, int request_code, String inputText) {
        Intent intent = new Intent(activity, EditPersionInfoActivity.class);
        intent.putExtra(KEY_TYPE, type);
        intent.putExtra(KEY_INPUT_TEXT, inputText);
        ActivityCompat.startActivityForResult(activity, intent, request_code, Bundle.EMPTY);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.edit_person_info_act;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        initData();
        initActionBar();
        initView();
    }

    private void initView() {
        switch (mCurrentType) {
            case NICKNAME:
                edInput.setHint("请输入昵称");
                break;
            case MOB:
                edInput.setHint("请输入电话号码");
                break;
        }

        edInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnConfirm.setEnabled(edInput.getText().toString().length() > 0);
                btnClearEdit.setVisibility(edInput.getText().toString().length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initData() {
        mCurrentType = (EDIT_TYPE) getIntent().getSerializableExtra(KEY_TYPE);
        mInputString = getIntent().getStringExtra(KEY_INPUT_TEXT);
        edInput.setText(mInputString);
        edInput.setSelection(mInputString.length());
        switch (mCurrentType) {
            case NICKNAME:
                mTitle = "更改昵称";
                edInput.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case MOB:
                mTitle = "更改手机号";
                edInput.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
        }
    }

    private void initActionBar() {
        topTitle.setText(mTitle);
        TextViewUtils.setDrawableToLeft(context, topLeft, R.mipmap.back_ico);
    }

    @OnClick({R.id.topLeft, R.id.btn_clear_edit, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                ActivityCompat.finishAfterTransition(context);
                break;
            case R.id.btn_clear_edit:
                edInput.setText("");
                break;
            case R.id.btn_confirm:
                if (inputCheckOk()) {

                    mInputString = edInput.getText().toString();
                    editOperation(mInputString);
                }
                break;
        }
    }

    private void editOperation(final String mInputString) {
        showBaseProgresDialog();
        WorkerApp
                .getApiService()
                .updateCustomerInfo(mInputString)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        dismisBaseProgressDialog();
                        if (response.body().getSucc()) {
                            T.showShort(context, "修改成功");
                            Intent intent = new Intent(context, PersonInfoActivity.class);
                            intent.putExtra("KEY_INPUT_RESULT", mInputString);
                            setResult(RESULT_OK, intent);
                            finish();
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
        if (mCurrentType == EDIT_TYPE.NICKNAME) {
            return nikeCheckOk();
        }
        if (mCurrentType == EDIT_TYPE.MOB) {
            return mobCheckOk();
        }
        return true;
    }

    private boolean mobCheckOk() {
        if (!PhoneNumberUtils.isMobile(edInput.getText().toString())) {
            T.showShort(context, R.string.input_phone_error);
            return false;
        }
        return true;
    }

    private boolean nikeCheckOk() {
        if (!Constant.nikeNameRuleOk(edInput.getText().toString())) {
            T.showShort(context, R.string.input_nikename_error);
            return false;
        }
        return true;
    }
}
