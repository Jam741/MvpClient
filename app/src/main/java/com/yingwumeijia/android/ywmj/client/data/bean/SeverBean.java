package com.yingwumeijia.android.ywmj.client.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Administrator on 2016/8/18.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
public class SeverBean {


    /**
     * serverUrl :
     * appImkey :
     * upgrade : false
     */

    private String serverUrl;
    private String appImkey;
    private boolean upgrade;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAppImkey() {
        return appImkey;
    }

    public void setAppImkey(String appImkey) {
        this.appImkey = appImkey;
    }

    public boolean isUpgrade() {
        return upgrade;
    }

    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }
}
