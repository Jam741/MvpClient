package com.yingwumeijia.android.ywmj.client.data.bean;

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
         * id : 21
         * name : J - 187****2397
         * caseInfo : {"id":1062,"caseName":"J","caseCover":"http://o8nljewkg.bkt.clouddn.com/o_1amdfpmii1rn61vmptcb16k248k1r.jpg","status":1}
         * available : true
         * createTime : 2016-07-21 15:53:43
         * members : [{"userId":81,"userJoinType":1,"userType":"c","userDetailType":0,"showName":"哈哈","headImage":"http://o8nljewkg.bkt.clouddn.com/1467343405422.jpg","userTypeEnum":"CUSTOMER","userDetailTypeEnum":"EMPTY","userJoinTypeEnum":"CREATOR"},{"userId":171,"userJoinType":2,"userType":"m","userDetailType":0,"showName":"测试客服","headImage":"http://o8nljewkg.bkt.clouddn.com/o_1ao3jmbhk11qj1q031k9s10685qac.png","userTypeEnum":"MANAGER","userDetailTypeEnum":"EMPTY","userJoinTypeEnum":"SERVANT"}]
         * memberIds : [81,171]
         */

        private int id;
        private String name;
        /**
         * id : 1062
         * caseName : J
         * caseCover : http://o8nljewkg.bkt.clouddn.com/o_1amdfpmii1rn61vmptcb16k248k1r.jpg
         * status : 1
         */

        private CaseInfoBean caseInfo;
        private boolean available;
        private String createTime;
        private String teamPhone;

        public String getTeamPhone() {
            return teamPhone;
        }

        public void setTeamPhone(String teamPhone) {
            this.teamPhone = teamPhone;
        }

        /**
         * userId : 81
         * userJoinType : 1
         * userType : c
         * userDetailType : 0
         * showName : 哈哈
         * headImage : http://o8nljewkg.bkt.clouddn.com/1467343405422.jpg
         * userTypeEnum : CUSTOMER
         * userDetailTypeEnum : EMPTY
         * userJoinTypeEnum : CREATOR
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

        public static class CaseInfoBean implements Serializable {
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
            private int userJoinType;
            private String userType;
            private int userDetailType;
            private String showName;
            private String headImage;
            private String userTypeEnum;
            private String userDetailTypeEnum;
            private String userJoinTypeEnum;

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

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public int getUserDetailType() {
                return userDetailType;
            }

            public void setUserDetailType(int userDetailType) {
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

            public String getUserDetailTypeEnum() {
                return userDetailTypeEnum;
            }

            public void setUserDetailTypeEnum(String userDetailTypeEnum) {
                this.userDetailTypeEnum = userDetailTypeEnum;
            }

            public String getUserJoinTypeEnum() {
                return userJoinTypeEnum;
            }

            public void setUserJoinTypeEnum(String userJoinTypeEnum) {
                this.userJoinTypeEnum = userJoinTypeEnum;
            }
        }
    }
}
