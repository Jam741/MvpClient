package com.yingwumeijia.android.worker;

import com.yingwumeijia.android.worker.data.bean.BaseBean;
import com.yingwumeijia.android.worker.data.bean.CaseDetailsResultBean;
import com.yingwumeijia.android.worker.data.bean.CaseListResultBean;
import com.yingwumeijia.android.worker.data.bean.CaseTypeResultBean;
import com.yingwumeijia.android.worker.data.bean.CustomerResultBean;
import com.yingwumeijia.android.worker.data.bean.FindPwdResultBean;
import com.yingwumeijia.android.worker.data.bean.GroupResultBean;
import com.yingwumeijia.android.worker.data.bean.LoginResultBean;
import com.yingwumeijia.android.worker.data.bean.RegisterResultBean;
import com.yingwumeijia.android.worker.data.bean.ShareBean;
import com.yingwumeijia.android.worker.data.bean.TokenResultBean;
import com.yingwumeijia.android.worker.data.bean.UserBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Jam on 16/6/14 下午6:32.
 * Describe:
 */
public interface ApiService {


    @GET("serviceQuery/server")
    Call<BaseBean<String>> getService(@Path("appType") String appType,
                                      @Path("version") String version);

    /**
     * 登陆
     *
     * @param phone
     * @param password
     * @param verifyCode
     * @return
     */
    @POST("user/login")
    Call<LoginResultBean> login(@Query("phone") String phone,
                                @Query("password") String password,
                                @Query("verifyCode") String verifyCode);


    /**
     * 退出登录
     *
     * @return
     */
    @POST("user/logout")
    Call<BaseBean> setLogout();

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    @POST("user/sendSmsCode")
    Call<BaseBean> sendSmsCode(@Query("phone") String phone,
                               @Query("source") int source);


    /**
     * 找回密码
     *
     * @param phone
     * @param newPassword
     * @param verifyCode
     * @return
     */
    @PUT("user/getBackPassword")
    Call<FindPwdResultBean> getBackPassword(@Query("phone") String phone,
                                            @Query("newPassword") String newPassword,
                                            @Query("smsCode") String verifyCode);

    /**
     * 密码设置
     *
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PUT("user/setPassword")
    Call<BaseBean> setPassword(@Query("oldPassword") String oldPassword,
                               @Query("newPassword") String newPassword);


    /**
     * 个人中心
     *
     * @return
     */
    @GET("employee/getEmployeeInfo")
    Call<CustomerResultBean> getCustomerInfo();

    /**
     * 修改昵称
     *
     * @param nikeName
     * @return
     */
    @PUT("customer/updateEmployeeInfo")
    Call<BaseBean> updateCustomerInfo(@Query("nikeName") String nikeName);

    //employee/uploadHeadImage

    /**
     * 获取七牛Token
     *
     * @return
     */
    @GET("upload/getToken")
    Call<BaseBean<String>> getUpLoadToken();


    /**
     * 上传头像
     *
     * @param headImage
     * @return
     */
    @PUT("employee/upLoadHeadImage")
    Call<BaseBean> uploadHeadImage(@Query("headImage") String headImage);


    /**
     * 首页案例列表
     *
     * @param pageNum   页码
     * @param pageSize  每页有多少项
     * @param style     风格
     * @param houseType 房型
     * @param cost      造价
     * @return
     */
    @GET("case/employee/list/{pageNum}/{pageSize}")
    Call<CaseListResultBean> getCaseList(@Path("pageNum") int pageNum,
                                         @Path("pageSize") int pageSize,
                                         @Query("style") int style,
                                         @Query("houseType") int houseType,
                                         @Query("cost") int cost);

    /**
     * 获取分享信息
     *
     * @param caseId
     * @return
     */
    @GET("case/employee/share/{caseId}")
    Call<BaseBean<ShareBean>> getShareData(@Path("caseId") int caseId);


    /**
     * 案例筛选枚举集合
     *
     * @return
     */
    @GET("case/employee/types")
    Call<CaseTypeResultBean> getCaseTypeSet();


    /**
     * 收藏案例
     *
     * @param caseId
     * @return
     */
    @POST("case/employee/collection/{caseId}")
    Call<BaseBean> collectionCase(@Path("caseId") int caseId);

    /**
     * 更新案例访问数
     *
     * @param caseId
     * @return
     */
    @POST("case/employee/visit/{caseId}")
    Call<BaseBean> upDateVisitNum(@Path("caseId") int caseId);

    /**
     * 案例详情url列表
     *
     * @param caseId
     * @return
     */
    @GET("case/employee/detail/urls/{caseId}")
    Call<CaseDetailsResultBean> getCaseDetail(@Path("caseId") int caseId);


    /**
     * 搜索案例
     *
     * @param keyWords
     * @param page_num
     * @param page_size
     * @return
     */
    @GET("case/employee/search/{keyWords}/{page_num}/{page_size}")
    Call<CaseListResultBean> getSearchCaseList(@Path("keyWords") String keyWords,
                                               @Path("page_num") int page_num,
                                               @Path("page_size") int page_size);


    /**
     * 获取收藏列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("case/employee/collection/list/{pageNum}/{pageSize}")
    Call<CaseListResultBean> getCollectionList(@Path("pageNum") int pageNum,
                                               @Path("pageSize") int pageSize);


    /**
     * 取消收藏
     *
     * @param caseId
     * @return
     */
    @POST("case/employee/cancelCollection/{caseId}")
    Call<BaseBean> cancelCollection(@Path("caseId") int caseId);


    /**
     * 获取融云token
     *
     * @return
     */
    @GET("im/getToken")
    Call<TokenResultBean> getIMToken();


    /**
     * 获取会话信息
     *
     * @param sessionId 会话ID
     * @return
     */
    @GET("im/caseSession/{sessionId}")
    Call<GroupResultBean> getConversionInfo(@Path("sessionId") String sessionId);

    /**
     * @return
     */
    @GET("user/captcha")
    Call<ResponseBody> getCaptcha();


    @GET("im/sessionMember/{memberId}")
    Call<BaseBean<UserBean>> getMemberInfo(@Path("memberId") String memberId);

}
