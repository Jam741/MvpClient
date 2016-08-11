package com.yingwumeijia.android.ywmj.client.data.bean;

/**
 * Created by Jam on 2016/8/11 11:38.
 * Describe:
 */
public class ShareModel {

    private String mShareUrl;

    private String mShareImg;

    private String mDescription;

    private String mShareTitle;

    private String WX_APP_ID;


    public ShareModel(String mShareUrl, String mShareImg, String mDescription, String mShareTitle, String WX_APP_ID) {
        this.mShareUrl = mShareUrl;
        this.mShareImg = mShareImg;
        this.mDescription = mDescription;
        this.mShareTitle = mShareTitle;
        this.WX_APP_ID = WX_APP_ID;
    }

    public String getmShareUrl() {
        return mShareUrl;
    }

    public void setmShareUrl(String mShareUrl) {
        this.mShareUrl = mShareUrl;
    }

    public String getmShareImg() {
        return mShareImg;
    }

    public void setmShareImg(String mShareImg) {
        this.mShareImg = mShareImg;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmShareTitle() {
        return mShareTitle;
    }

    public void setmShareTitle(String mShareTitle) {
        this.mShareTitle = mShareTitle;
    }

    public String getWX_APP_ID() {
        return WX_APP_ID;
    }

    public void setWX_APP_ID(String WX_APP_ID) {
        this.WX_APP_ID = WX_APP_ID;
    }
}
