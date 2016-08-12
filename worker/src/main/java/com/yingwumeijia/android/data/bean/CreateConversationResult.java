package com.yingwumeijia.android.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jam on 16/7/18 下午6:15.
 * Describe:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateConversationResult extends BaseBean<CreateConversationResult.GroupConversationBean> {


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GroupConversationBean implements Serializable {

        /**
         * id : 53
         * userId : 33
         * caseId : 24
         * companyId : 1
         * name : 测试案例20160727002-本案实施团队
         * callTotal : 0
         * warning : false
         * available : true
         * createTime : 1469764172384
         * updateTime : 1469764172384
         * members : [{"userId":33,"userJoinType":1},{"userId":9,"userJoinType":2},{"userId":29,"userJoinType":3},{"userId":30,"userJoinType":3},{"userId":31,"userJoinType":3}]
         */

        private int id;
        private int userId;
        private int caseId;
        private int companyId;
        private String name;
        private int callTotal;
        private boolean warning;
        private boolean available;
        private long createTime;
        private long updateTime;
        /**
         * userId : 33
         * userJoinType : 1
         */

        private List<MembersBean> members;

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

        public List<MembersBean> getMembers() {
            return members;
        }

        public void setMembers(List<MembersBean> members) {
            this.members = members;
        }

        public static class MembersBean {
            private int userId;
            private int userJoinType;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getUserJoinType() {
                return userJoinType;
            }

            public void setUserJoinType(int userJoinType) {
                this.userJoinType = userJoinType;
            }
        }
    }
}
