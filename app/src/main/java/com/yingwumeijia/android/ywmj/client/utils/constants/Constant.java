package com.yingwumeijia.android.ywmj.client.utils.constants;

import android.content.Context;

import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.rx.android.jamspeedlibrary.utils.SPUtils;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;

import io.rong.imkit.RongIM;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public class Constant {


    //王经纬
    public static final String BASE_URL_WANG = "http://192.168.28.193:8082/";

    //sever
    public static final String BASE_URL_DEV = "http://192.168.28.16:8182/";
    //陈驰
    public static final String BASE_URL_CHENCHI = "http://192.168.28.114:8182/";
    //test
    public static final String BASE_URL_TEST = "http://192.168.28.15:8182/";
    // 阿里云服务器
    public static final String BASE_URL_RELEASE = "http://139.196.233.188:8182/";
    public static final String BASE_QINIU_URL = "http://o8nljewkg.bkt.clouddn.com/";


    /*-----------------------------Internet status-------------------------*/
    public static final int STATUS_SUCCESS = 0;
    public static final int PAGE_SIZE = 10;//每页有多少项


    /*-----------------------------length----------------------------------*/
    public static final int SMS_CODE_LENGTH = 6;
    public static final int PASSWORD_LENGTH_MINI = 8;
    public static final int PASSWORD_LENGTH_MAX = 20;
    public static final int NIKENAME_LENGTH_MINI = 1;
    public static final int NIKENAME_LENGTH_MAX = 15;


    /*-----------------------------parma------------------------------------*/
    public static final int PARAM_REGISTER = 1;
    public static final int PARAM_FIND = 2;
    public static final int NOT_LOGIN_STATECODE = 312;


    /*---------------------------user login info----------------------------*/
    public static final String SP_LOGIN = "sp_login";
    public static final String KEY_LOGIN = "key_login";
    public static final boolean LOGIN_OUT = false;
    public static final boolean LOGIN_IN = true;
    public static final String KEY_HEADER = "KEY_HEADER";

    /*---------------------------net work-----------------------------------*/
    public static final String NET_WORK_RECEIVER_ACTION = "com.ywmj.receiver.networkreceiver";
    public static final String NET_TYPE = "net_type1";


    /*---------------------------------SQLite----------------------------------*/
    public static final String SQLITE_NAME = "ywmj.db";
    public static final int SQLITE_VERSION = 1;

    public static void setLoginIn(Context context) {
        setLoginInfo(LOGIN_IN, context);
    }

    public static void setLoginOut(Context context) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().logout();
        }
        setLoginInfo(LOGIN_OUT, context);
        RongIM.getInstance().disconnect();
        SPUtils.remove(context, KEY_HEADER);
        SPUtils.remove(context, "key_phone");
        SPUtils.remove(context, "key_password");
    }

    public static void setLoginInfo(boolean loginInfo, Context context) {
        SPUtils.put(context, KEY_LOGIN, loginInfo);
    }

    public static boolean getLoginInfo(Context context) {
        return (boolean) SPUtils.get(context, KEY_LOGIN, LOGIN_OUT);
    }

    public static boolean isLogin(Context context) {
        if (getLoginInfo(context) == LOGIN_IN)
            return true;
        else return false;
    }

    /**
     * 保存用户信息
     *
     * @param userData
     */
    public static void saveUserInfo(UserBean userData, Context context) {
        SPUtils.put(context, "user_id", userData.getId());
    }

    public int getUserId(Context context) {
        return (int) SPUtils.get(context, "user_id", 0);
    }

    public static void saveUserLoginInfo(String phone, String password, Context context) {
        SPUtils.put(context, "key_phone", phone);
        SPUtils.put(context, "key_password", password);
    }

    public static void saveUserPassword(String password, Context context) {
        SPUtils.put(context, "key_password", password);
    }

    public static String getUserPhone(Context context) {
        return (String) SPUtils.get(context, "key_phone", "");
    }

    public static String getUserPassword(Context context) {
        return (String) SPUtils.get(context, "key_password", "");
    }

    public static void saveHeader(String header, Context context) {
        LogUtil.getInstance().debug("saveHeader", "========================" + header);
        if (header != null) {
            SPUtils.put(context, KEY_HEADER, header);
        }
    }

    public static String getHeader(Context context) {
        return (String) SPUtils.get(context, KEY_HEADER, "");
    }

    public static boolean passwordRuleOk(String password) {
        return (password.length() >= PASSWORD_LENGTH_MINI && password.length() <= PASSWORD_LENGTH_MAX);
    }

    public static boolean captchaRuleOk(String captcha) {
        return !captcha.isEmpty() && captcha.length() == 4;
    }

    public static boolean nikeNameRuleOk(String nikeName) {
        if (nikeName.trim().length() >= NIKENAME_LENGTH_MINI && nikeName.trim().length() <= NIKENAME_LENGTH_MAX) {
            return true;
        }
        return false;
    }

    public static boolean smsCodeRuleOk(String smsCode) {
        return smsCode.trim().length() == Constant.SMS_CODE_LENGTH;
    }

    public static void checkSessionLost(String s) {

    }

    public static void saveIMToken(String token, Context context) {
        SPUtils.put(context, "IMTOKEN", token);
    }

    public static String getIMToken(Context context) {
        return (String) SPUtils.get(context, "IMTOKEN", "");
    }

    //微信授权KEY
    public static String WX_APP_ID = "wxa57345f69f5a674d";
    //微博授权KEY
    public static String WB_APP_KEY = "2293291411";


    public static String ACTION_SCROLL_NAV = "com.ywmj.client.scroll";

    public static String ACTION_NOT_LOGIN = "com.ywmj.client.notlogin";

}
