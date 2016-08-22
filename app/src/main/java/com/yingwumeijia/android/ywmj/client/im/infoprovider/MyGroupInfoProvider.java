package com.yingwumeijia.android.ywmj.client.im.infoprovider;

import android.net.Uri;

import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.data.bean.GroupResultBean;
import com.yingwumeijia.android.ywmj.client.function.mainfunction.MainActivity;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Jam on 2016/8/3 16:24.
 * Describe:
 */
public class MyGroupInfoProvider implements RongIM.GroupInfoProvider {

    @Override
    public Group getGroupInfo(String s) {
        return findGroupById(s);
    }

    private Group findGroupById(final String groupId) {
        MainActivity
                .getApiService()
                .getConversionInfo(groupId)
                .enqueue(new Callback<GroupResultBean>() {
                    @Override
                    public void onResponse(Call<GroupResultBean> call, retrofit2.Response<GroupResultBean> response) {
                        if (response.body().getSucc()) {
                            final Group group = new Group(
                                    groupId,
                                    response.body().getData().getName(),
                                    Uri.parse(response.body().getData().getCaseInfo().getCaseCover())
                            );
                            LogUtil.getInstance().debug("findGroupById", "----------------------------------------------------findGroupById:" + group.getName());
                            RongIM.getInstance().refreshGroupInfoCache(group);
                        }
                        LogUtil.getInstance().debug("findGroupById", "------------------------------error----------------------findGroupById:" + groupId);
                    }

                    @Override
                    public void onFailure(Call<GroupResultBean> call, Throwable t) {
                        LogUtil.getInstance().debug("findGroupById", "------------------------------error--e--------------------findGroupById:" + groupId);
                    }
                });
        return null;
    }


}
