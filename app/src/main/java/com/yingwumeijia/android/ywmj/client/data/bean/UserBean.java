package com.yingwumeijia.android.ywmj.client.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jam
 * on 2016/6/1 17:49
 * Describe:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBean {

    /**
     * id : 0
     * userType :
     * userTypeStr :
     * userDetailType :
     * userDetailTypeStr :
     * userDetailTypeCnStr :
     * userTitle :
     * userName :
     * phone :
     * name :
     * nickName :
     * headImage :
     * registerTime :
     * lastLoginTime :
     * available : false
     * deleted : false
     * mail :
     * idCard :
     * idImageA :
     * idImageB :
     * idHoldImage :
     * lifePhotos :
     * resume :
     * registerPhone :
     * caseNum : 0
     */

    private int id;
    private String userType;
    private String userTypeStr;
    private String userDetailType;
    private String userDetailTypeStr;
    private String userDetailTypeCnStr;
    private String userTitle;
    private String userName;
    private String phone;
    private String name;
    private String nickName;
    private String headImage;
    private String registerTime;
    private String lastLoginTime;
    private boolean available;
    private boolean deleted;
    private String mail;
    private String idCard;
    private String idImageA;
    private String idImageB;
    private String idHoldImage;
    private String lifePhotos;
    private String resume;
    private String registerPhone;
    private int caseNum;
    private String showHead;
    private String showName;


    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowHead() {
        return showHead;
    }

    public void setShowHead(String showHead) {
        this.showHead = showHead;
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

    public String getUserDetailTypeStr() {
        return userDetailTypeStr;
    }

    public void setUserDetailTypeStr(String userDetailTypeStr) {
        this.userDetailTypeStr = userDetailTypeStr;
    }

    public String getUserDetailTypeCnStr() {
        return userDetailTypeCnStr;
    }

    public void setUserDetailTypeCnStr(String userDetailTypeCnStr) {
        this.userDetailTypeCnStr = userDetailTypeCnStr;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdImageA() {
        return idImageA;
    }

    public void setIdImageA(String idImageA) {
        this.idImageA = idImageA;
    }

    public String getIdImageB() {
        return idImageB;
    }

    public void setIdImageB(String idImageB) {
        this.idImageB = idImageB;
    }

    public String getIdHoldImage() {
        return idHoldImage;
    }

    public void setIdHoldImage(String idHoldImage) {
        this.idHoldImage = idHoldImage;
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

    public String getRegisterPhone() {
        return registerPhone;
    }

    public void setRegisterPhone(String registerPhone) {
        this.registerPhone = registerPhone;
    }

    public int getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(int caseNum) {
        this.caseNum = caseNum;
    }
}
