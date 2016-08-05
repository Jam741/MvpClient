package com.yingwumeijia.android.ywmj.client;


import com.yingwumeijia.android.ywmj.client.data.bean.BaseBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseDetailsResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseListResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CaseTypeResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.CreateConversationResult;
import com.yingwumeijia.android.ywmj.client.data.bean.CustomResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.FindPwdResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.GroupResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.LoginResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.RegisterResultBean;
import com.yingwumeijia.android.ywmj.client.data.bean.TokenResultBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Jam
 * on 2016/6/1 14:56
 * Describe:网络接口
 */
public interface ApiService {


    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param verifyCode
     * @return
     */
    @POST("user/register")
    Call<LoginResultBean> register(@Query("phone") String phone,
                                      @Query("password") String password,
                                      @Query("smsCode") String verifyCode);

    /**
     * 发送验证码
     *
     * @param phone
     * @param source 1-注册，2-找回
     * @return
     */
    @POST("user/sendSmsCode")
    Call<BaseBean> sendSmsCode(@Query("phone") String phone,
                               @Query("source") int source);


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
    @PUT("user/changePassword")
    Call<BaseBean> setPassword(@Query("oldPassword") String oldPassword,
                               @Query("newPassword") String newPassword);


    /**
     * 个人中心
     *
     * @return
     */
    @GET("customer/getCustomerInfo")
    Call<CustomResultBean> getCustomerInfo();

    /**
     * 修改昵称
     *
     * @param nickName
     * @return
     */
    @POST("customer/updateNickName")
    Call<BaseBean> updateNickName(@Query("nickName") String nickName);


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
    @POST("customer/upLoadHeadImage")
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
    @GET("case/app/list/{pageNum}/{pageSize}")
    Call<CaseListResultBean> getCaseList(@Path("pageNum") int pageNum,
                                         @Path("pageSize") int pageSize,
                                         @Query("style") int style,
                                         @Query("houseType") int houseType,
                                         @Query("cost") int cost);


    /**
     * 案例筛选枚举集合
     *
     * @return
     */
    @GET("case/app/types")
    Call<CaseTypeResultBean> getCaseTypeSet();


    /**
     * 收藏案例
     *
     * @param caseId
     * @return
     */
    @POST("case/app/collection/{caseId}")
    Call<BaseBean> collectionCase(@Path("caseId") int caseId);


    /**
     * 案例详情url列表
     *
     * @param caseId
     * @return
     */
    @GET("case/app/detail/urls/{caseId}")
    Call<CaseDetailsResultBean> getCaseDetail(@Path("caseId") int caseId);


    /**
     * 搜索案例
     *
     * @param keyWords
     * @param page_num
     * @param page_size
     * @return
     */
    @GET("case/app/search/{keyWords}/{page_num}/{page_size}")
    Call<CaseListResultBean> getSearchCaseList(@Path("keyWords") String keyWords,
                                               @Path("page_num") int page_num,
                                               @Path("page_size") int page_size);

    /**
     * 退出登录
     *
     * @return
     */
    @POST("user/logout")
    Call<BaseBean> setLogout();


    /**
     * 更新案例访问数
     *
     * @param caseId
     * @return
     */
    @POST("case/app/visit/{caseId}")
    Call<BaseBean> upDateVisitNum(@Path("caseId") int caseId);

    /**
     * 获取收藏列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("case/app/collection/list/{pageNum}/{pageSize}")
    Call<CaseListResultBean> getCollectionList(@Path("pageNum") int pageNum,
                                               @Path("pageSize") int pageSize);

    /**
     * 取消收藏
     *
     * @param caseId
     * @return
     */
    @POST("case/app/cancelCollection/{caseId}")
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
     * 创建会话
     *
     * @param caseId
     * @return
     */
    @POST("im/caseSession")
    Call<CreateConversationResult> createGroupConversation(@Query("caseId") int caseId);


    /**
     * 报告电话联系次数
     *
     * @param sessionId
     * @return
     */
    @POST("im/caseSession/{sessionId}/report")
    Call<BaseBean> report(@Path("sessionId") String sessionId);


    /**
     * @return
     */
    @GET("user/captcha")
    Call<ResponseBody> getCaptcha();


    @GET("im/sessionMember/{memberId}")
    Call<BaseBean<GroupResultBean.GroupConversationBean.MembersBean>> getMemberInfo(@Path("memberId") String memberId);

}
