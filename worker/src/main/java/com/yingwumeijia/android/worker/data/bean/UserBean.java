package com.yingwumeijia.android.worker.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jam
 * on 2016/6/1 17:49
 * Describe:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBean {

    /**
     * id : 37
     * userName : 15681112269
     * userPhone : 15681112269
     * userType : e
     * userTypeId : 48
     * deleted : false
     * available : true
     * name : 王二麻子
     * gender : 0
     * createUserId : 4
     * createTime : 1471312625000
     * updateTime : 1471320607000
     * lastLoginTime : 1471314751000
     * entityId : 14
     * userDetailType : 1
     * companyId : 1
     * headImage : http://o8nljewkg.bkt.clouddn.com/FhD9O-olKl1WIn1FGt4EXa5lSVCP
     * lifePhotos : http://o8nljewkg.bkt.clouddn.com/o_1aq8gvbgeh3aa032cs31p1ka6c.png
     * resume : 里斯本竞技ytrytrytyrty
     * showName : 王二麻子
     * userDetailTypeDesc : 设计师
     * showHead : http://o8nljewkg.bkt.clouddn.com/FhD9O-olKl1WIn1FGt4EXa5lSVCP
     */

    private int id;
    private String userName;
    private String userPhone;
    private String userType;
    private int userTypeId;
    private boolean deleted;
    private boolean available;
    private String name;
    private int gender;
    private int createUserId;
    private long createTime;
    private long updateTime;
    private long lastLoginTime;
    private int entityId;
    private int userDetailType;
    private int companyId;
    private String headImage;
    private String lifePhotos;
    private String resume;
    private String showName;
    private String userDetailTypeDesc;
    private String showHead;
    private String userTitle;

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getUserDetailType() {
        return userDetailType;
    }

    public void setUserDetailType(int userDetailType) {
        this.userDetailType = userDetailType;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getLifePhotos() {
        return lifePhotos;
    }

    public void setLifePhotos(String lifePhotos) {
        this.lifePhotos = lifePhotos;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getUserDetailTypeDesc() {
        return userDetailTypeDesc;
    }

    public void setUserDetailTypeDesc(String userDetailTypeDesc) {
        this.userDetailTypeDesc = userDetailTypeDesc;
    }

    public String getShowHead() {
        return showHead;
    }

    public void setShowHead(String showHead) {
        this.showHead = showHead;
    }
}
