package com.yingwumeijia.android.ywmj.client.function.findpassword;

import android.content.Context;

import com.rx.android.jamspeedlibrary.utils.PhoneNumberUtils;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;

/**
 * Created by Jam on 2016/8/4 12:21.
 * Describe:
 */
public class FindPasswordPresenter implements FindPasswordContract.Presenter {

    private final Context context;

    private final FindPasswordContract.View mFindPasswordView;

    public FindPasswordPresenter(Context context, FindPasswordContract.View mFindPasswordView) {
        this.context = context;
        this.mFindPasswordView = mFindPasswordView;
        this.mFindPasswordView.setPresener(this);
    }


    @Override
    public boolean checkInputPhone(String phone) {
        if (PhoneNumberUtils.isMobile(phone)) return true;
        return false;
    }

    @Override
    public boolean checkInputSmsCode(String smsCode) {
        if (Constant.smsCodeRuleOk(smsCode)) return true;
        return false;
    }

    @Override
    public boolean checkInputPassword(String password) {
        if (Constant.passwordRuleOk(password)) return true;
        return false;
    }

    @Override
    public void findSuccessOperation(UserBean userBean) {
        
    }

    @Override
    public void findPassword(String phone, String smsCode, String password) {

    }

    @Override
    public void start() {

    }
}
