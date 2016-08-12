package com.yingwumeijia.android.ywmj.client.data.bean;

/**
 * Created by Jam on 2016/6/12 17:02.
 * Describe:
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterResultBean extends BaseBean<RegisterResultBean.RegisterBean> {

     public static class RegisterBean {

        boolean needConfirm;

        String message;

        String token;

        public boolean isNeedConfirm() {
            return needConfirm;
        }

        public void setNeedConfirm(boolean needConfirm) {
            this.needConfirm = needConfirm;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}
