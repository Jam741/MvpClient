package com.yingwumeijia.android.worker.im.infoprovider;

import android.net.Uri;

import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.yingwumeijia.android.worker.WorkerApp;
import com.yingwumeijia.android.worker.data.bean.BaseBean;
import com.yingwumeijia.android.worker.data.bean.UserBean;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Jam on 2016/8/3 16:22.
 * Describe:
 */
public class MyUserInfoProvider implements RongIM.UserInfoProvider {

    @Override
    public UserInfo getUserInfo(String s) {
        return findUserById(s);
    }

    private UserInfo findUserById(final String imUid) {
        WorkerApp
                .getApiService()
                .getMemberInfo(imUid)
                .enqueue(new Callback<BaseBean<UserBean>>() {
                    @Override
                    public void onResponse(Call<BaseBean<UserBean>> call, retrofit2.Response<BaseBean<UserBean>> response) {
                        if (response.body().getSucc()) {
                            String showName = response.body().getData().getShowName();
                            String userType = response.body().getData().getUserType();
                            if (userType.equals("c")) {
                                showName = showName + "-" + "客户";
                            } else if (userType.equals("e")) {
                                showName = showName + "-" + response.body().getData().getUserTitle();
                            } else if (userType.equals("m")) {
                                showName = showName + "-" + "美家客服";
                            }
                            UserInfo userInfo = new UserInfo(
                                    imUid,
                                    showName,
                                    Uri.parse(response.body().getData().getShowHead())
                            );
                            LogUtil.getInstance().debug("findimid", "----------------------------------------------------findUserById:" + userInfo.getUserId() + userInfo.getName() + userInfo.getPortraitUri());
                            RongIM.getInstance().refreshUserInfoCache(userInfo);
                        } else {
                            LogUtil.getInstance().debug("findimid", "---------------------------error-------------------------findUserById:" + imUid);

                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<UserBean>> call, Throwable t) {
                        LogUtil.getInstance().debug("findimid", "---------------------------error----e---------------------findUserById:" + imUid);
                    }
                });
        return null;
    }
}
