package com.yingwumeijia.android.ywmj.client.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jam on 16/6/24 下午1:58.
 * Describe:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomBean {


    /**
     * id : 3
     * userType : CUSTOMER
     * userTypeStr : c
     * userDetailType : EMPTY
     * userDetailTypeStr : 0
     * userName : 15198011383
     * phone : 15198011383
     * nickName : 15198011383
     * headImage : http://o8nljewkg.bkt.clouddn.com/1467255629356.jpg
     * available : true
     * deleted : false
     */

    private int id;
    private String userType;
    private String userTypeStr;
    private String userDetailType;
    private int userDetailTypeStr;
    private String userName;
    private String phone;
    private String nickName;
    private String headImage;
    private boolean available;
    private boolean deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserTypeStr() {
        return userTypeStr;
    }

    public void setUserTypeStr(String userTypeStr) {
        this.userTypeStr = userTypeStr;
    }

    public String getUserDetailType() {
        return userDetailType;
    }

    public void setUserDetailType(String userDetailType) {
        this.userDetailType = userDetailType;
    }

    public int getUserDetailTypeStr() {
        return userDetailTypeStr;
    }

    public void setUserDetailTypeStr(int userDetailTypeStr) {
        this.userDetailTypeStr = userDetailTypeStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
