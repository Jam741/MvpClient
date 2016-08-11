package com.yingwumeijia.android.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jam on 16/6/24 下午1:58.
 * Describe:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomBean {


    /**
     * id : 4
     * userType : EMPLOYEE
     * userTypeStr : e
     * userDetailType : PM
     * userDetailTypeStr : 2
     * userName : 13975847474
     * name : Jacky1
     * registerTime : 1466582491000
     * lastLoginTime : 1467117078000
     * available : true
     * deleted : false
     */

    private int id;
    private String userType;
    private String userTypeStr;
    private String userDetailType;
    private int userDetailTypeStr;
    private String userName;
    private String name;
    private String headImage;
    private long registerTime;
    private long lastLoginTime;
    private boolean available;
    private boolean deleted;

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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
