package com.yingwumeijia.android.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jam on 16/7/18 下午2:14.
 * Describe:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenBean {

    private int userId;
    private String token;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
