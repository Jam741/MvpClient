package com.yingwumeijia.android.ywmj.client.utils;

import com.rx.android.jamspeedlibrary.utils.SPUtils;
import com.yingwumeijia.android.ywmj.client.MyApp;
import com.yingwumeijia.android.ywmj.client.utils.constants.Constant;
import com.yingwumeijia.android.ywmj.client.data.bean.UserBean;

/**
 * Created by Jam on 2016/8/2.
 * jamisonline.he@gmail.com
 */
public class UserManager {

    private static final String EMPTY_STRING = null;
    private static final int  EMPTY_INTEGER = 0;

    public static void saveRongIMToken(String token){
        SPUtils.put(MyApp.appContext(),"rong_im_token",token);
    }

    public static void getRongIMToken(){
        SPUtils.get(MyApp.appContext(),"rong_im_token",EMPTY_STRING);
    }

    public static void saveUserPhone(String phone){
        SPUtils.put(MyApp.appContext(),"user_phone",phone);
    }

    public static void getUserPhone(){
        SPUtils.get(MyApp.appContext(),"user_phone",EMPTY_STRING);
    }

     public static void saveUserPassword(String password){
        SPUtils.put(MyApp.appContext(),"user_password",password);
    }

    public static void geUserPassword(){
        SPUtils.get(MyApp.appContext(),"user_password",EMPTY_STRING);
    }

    public static void saveUserId(int userId){
        SPUtils.put(MyApp.appContext(),"user_id",userId);
    }

    public static void getUserId(){
        SPUtils.get(MyApp.appContext(),"user_id",EMPTY_INTEGER);
    }


    public static void saveUserInfo(UserBean userBean){
        Constant.setLoginIn(MyApp.appContext());
        saveUserId(userBean.getId());
        saveUserPhone(userBean.getPhone());
    }

}