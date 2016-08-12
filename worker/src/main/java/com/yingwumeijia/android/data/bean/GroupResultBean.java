package com.yingwumeijia.android.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jam on 16/7/18 下午5:04.
 * Describe:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupResultBean extends BaseBean<GroupResultBean.GroupConversationBean> {

    public static class GroupConversationBean implements Serializable{


        /**
         * id : 0
         * name :
         * caseInfo : {"id":0,"caseName":"","caseCover":"","status":""}
         * teamPhone :
         * available : false
         * createTime :
         * members : [{"userId":0,"userJoinType":"","userType":"","userDetailType":"","showName":"","headImage":"","userTypeEnum":"","userJoinTypeEnum":"","userDetailTypeEnum":""}]
         * memberIds : [0]
         */

        private int id;
        private String name;
        /**
         * id : 0
         * caseName :
         * caseCover :
         * status :
         */

        private CaseInfoBean caseInfo;
        private String teamPhone;
        private boolean available;
        private String createTime;
        /**
         * userId : 0
         * userJoinType :
         * userType :
         * userDetailType :
         * showName :
         * headImage :
         * userTypeEnum :
         * userJoinTypeEnum :
         * userDetailTypeEnum :
         */

        private List<MembersBean> members;
        private List<Integer> memberIds;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public CaseInfoBean getCaseInfo() {
            return caseInfo;
        }

        public void setCaseInfo(CaseInfoBean caseInfo) {
            this.caseInfo = caseInfo;
        }

        public String getTeamPhone() {
            return teamPhone;
        }

        public void setTeamPhone(String teamPhone) {
            this.teamPhone = teamPhone;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<MembersBean> getMembers() {
            return members;
        }

        public void setMembers(List<MembersBean> members) {
            this.members = members;
        }

        public List<Integer> getMemberIds() {
            return memberIds;
        }

        public void setMemberIds(List<Integer> memberIds) {
            this.memberIds = memberIds;
        }

        public static class CaseInfoBean implements Serializable{
            private int id;
            private String caseName;
            private String caseCover;
            private int status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCaseName() {
                return caseName;
            }

            public void setCaseName(String caseName) {
                this.caseName = caseName;
            }

            public String getCaseCover() {
                return caseCover;
            }

            public void setCaseCover(String caseCover) {
                this.caseCover = caseCover;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class MembersBean {
            private int userId;
            private String userJoinType;
            private String userType;
            private String userDetailType;
            private String showName;
            private String headImage;
            private String userTypeEnum;
            private String userJoinTypeEnum;
            private String userDetailTypeEnum;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUserJoinType() {
                return userJoinType;
            }

            public void setUserJoinType(String userJoinType) {
                this.userJoinType = userJoinType;
            }

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public String getUserDetailType() {
                return userDetailType;
            }

            public void setUserDetailType(String userDetailType) {
                this.userDetailType = userDetailType;
            }

            public String getShowName() {
                return showName;
            }

            public void setShowName(String showName) {
                this.showName = showName;
            }

            public String getHeadImage() {
                return headImage;
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }

            public String getUserTypeEnum() {
                return userTypeEnum;
            }

            public void setUserTypeEnum(String userTypeEnum) {
                this.userTypeEnum = userTypeEnum;
            }

            public String getUserJoinTypeEnum() {
                return userJoinTypeEnum;
            }

            public void setUserJoinTypeEnum(String userJoinTypeEnum) {
                this.userJoinTypeEnum = userJoinTypeEnum;
            }

            public String getUserDetailTypeEnum() {
                return userDetailTypeEnum;
            }

            public void setUserDetailTypeEnum(String userDetailTypeEnum) {
                this.userDetailTypeEnum = userDetailTypeEnum;
            }
        }
    }
}
