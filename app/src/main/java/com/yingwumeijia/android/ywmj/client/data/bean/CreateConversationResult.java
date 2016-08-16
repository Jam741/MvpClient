package com.yingwumeijia.android.ywmj.client.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Jam on 16/7/18 下午6:15.
 * Describe:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateConversationResult extends BaseBean<CreateConversationResult.GroupConversationBean> {


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GroupConversationBean implements Serializable {


        /**
         * id : 7
         * userId : 26
         * userType : c
         * caseId : 1055
         * companyId : 1
         * name : iiii-本案实施团队
         * callTotal : 6
         * warning : false
         * available : true
         * createTime : 1471255626000
         * updateTime : 1471255626000
         */

        private int id;
        private int userId;
        private String userType;
        private int caseId;
        private int companyId;
        private String name;
        private int callTotal;
        private boolean warning;
        private boolean available;
        private long createTime;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public int getCaseId() {
            return caseId;
        }

        public void setCaseId(int caseId) {
            this.caseId = caseId;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCallTotal() {
            return callTotal;
        }

        public void setCallTotal(int callTotal) {
            this.callTotal = callTotal;
        }

        public boolean isWarning() {
            return warning;
        }

        public void setWarning(boolean warning) {
            this.warning = warning;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
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
    }
}
