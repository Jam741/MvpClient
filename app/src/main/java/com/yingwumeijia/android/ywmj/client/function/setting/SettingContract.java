package com.yingwumeijia.android.ywmj.client.function.setting;

import com.yingwumeijia.android.ywmj.client.BasePresenter;
import com.yingwumeijia.android.ywmj.client.BaseView;

/**
 * Created by Jam on 2016/8/8 9:33.
 * Describe:
 */
public interface SettingContract {


    interface View extends BaseView<Presenter>{


        void showLoginOutFail(String msg);

        void showLoginOutButton();

        void hideLoginOutButton();

        void showCurrentCache(String cacheSize);

        void showClearCacheDialog();
    }

    interface Presenter extends BasePresenter{

        void clearCache();

        void startAgreementActivity();

        void startSetPassword();

        void startAboutUs();

        String getCacheSize();

        void loginOut();
    }
}
