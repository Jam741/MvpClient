package com.yingwumeijia.android.ywmj.client.im.infoprovider;

import android.net.Uri;

import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.GroupResultBean;

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

    private UserInfo findUserById(final String userId) {
        MyApp
                .getApiService()
                .getMemberInfo(userId)
                .enqueue(new Callback<BaseBean<GroupResultBean.GroupConversationBean.MembersBean>>() {
                    @Override
                    public void onResponse(Call<BaseBean<GroupResultBean.GroupConversationBean.MembersBean>> call, retrofit2.Response<BaseBean<GroupResultBean.GroupConversationBean.MembersBean>> response) {
                        if (response.body().getSucc()) {
                            String showName = response.body().getData().getShowName();
                            String type = response.body().getData().getUserTypeEnum();
                            if (type.equals("CUSTOMER")) {
                                // Nothing to do
                            } else if (type.equals("EMPLOYEE")) {
                                showName = showName + "-" + response.body().getData().getUserDetailTypeEnum();
                            } else if (type.equals("MANAGER")) {
                                showName = showName + "-" + "美家客服";
                            }
                            UserInfo userInfo = new UserInfo(
                                    userId,
                                    showName,
                                    Uri.parse(response.body().getData().getHeadImage())
                            );
                            LogUtil.getInstance().debug("findimid", "----------------------------------------------------findUserById:" + userInfo.getUserId() + userInfo.getName() + userInfo.getPortraitUri());
                            RongIM.getInstance().refreshUserInfoCache(userInfo);
                        }else {
                            LogUtil.getInstance().debug("findimid", "---------------------------error-------------------------findUserById:" +userId);

                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<GroupResultBean.GroupConversationBean.MembersBean>> call, Throwable t) {
                        LogUtil.getInstance().debug("findimid", "---------------------------error----e---------------------findUserById:" +userId);
                    }
                });
        return null;
    }
}
