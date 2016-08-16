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
         * id : 7
         * name : iiii-本案实施团队
         * caseInfo : {"id":1055,"caseName":"iiii","caseCover":"http://o8nljewkg.bkt.clouddn.com/o_1aq6pduqm7su1s4u12glvvlb2e1a.jpg","status":1}
         * teamPhone : 13411111111
         * available : true
         * createTime : 2016-08-15 18:07:06
         * members : [{"userId":1,"userJoinType":2,"userType":"m","userDetailType":2,"userTitle":"客服","showName":"小鹦","showHead":"http://o8nljewkg.bkt.clouddn.com/o_1ao3jmbhk11qj1q031k9s10685qac.png","gender":2,"sessionId":7,"imUid":"m1"},{"userId":3,"userJoinType":3,"userType":"e","userDetailType":1,"userTitle":"设计师","showName":"我是业者－设计师","showHead":"http://o8nljewkg.bkt.clouddn.com/o_1ao3jmbhk11qj1q031k9s10685qac.png","gender":1,"sessionId":7,"imUid":"e3"},{"userId":5,"userJoinType":3,"userType":"e","userDetailType":2,"userTitle":"项目经理","showName":"经纬哥","showHead":"http:test","gender":1,"sessionId":7,"imUid":"e5"},{"userId":17,"userJoinType":3,"userType":"e","userDetailType":3,"userTitle":"家居顾问","showName":"顾问一号","showHead":"http://o8nljewkg.bkt.clouddn.com/o_1aq60pk8skmbsbl14e0l3710gur.jpg","gender":0,"sessionId":7,"imUid":"e17"},{"userId":26,"userJoinType":1,"userType":"c","showName":"业主2372","showHead":"http://o8nljewkg.bkt.clouddn.com/1471253996160.jpg","gender":0,"sessionId":7,"imUid":"c26"}]
         */

        private int id;
        private String name;
        /**
         * id : 1055
         * caseName : iiii
         * caseCover : http://o8nljewkg.bkt.clouddn.com/o_1aq6pduqm7su1s4u12glvvlb2e1a.jpg
         * status : 1
         */

        private CaseInfoBean caseInfo;
        private String teamPhone;
        private boolean available;
        private String createTime;
        /**
         * userId : 1
         * userJoinType : 2
         * userType : m
         * userDetailType : 2
         * userTitle : 客服
         * showName : 小鹦
         * showHead : http://o8nljewkg.bkt.clouddn.com/o_1ao3jmbhk11qj1q031k9s10685qac.png
         * gender : 2
         * sessionId : 7
         * imUid : m1
         */

        private List<MembersBean> members;

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

        public static class CaseInfoBean {
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
            private String userTitle;
            private String showName;
            private String showHead;
            private int gender;
            private int sessionId;
            private String imUid;

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

            public String getUserTitle() {
                return userTitle;
            }

            public void setUserTitle(String userTitle) {
                this.userTitle = userTitle;
            }

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

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public int getSessionId() {
                return sessionId;
            }

            public void setSessionId(int sessionId) {
                this.sessionId = sessionId;
            }

            public String getImUid() {
                return imUid;
            }

            public void setImUid(String imUid) {
                this.imUid = imUid;
            }
        }
    }
}
