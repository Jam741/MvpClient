package com.yingwumeijia.android.worker.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jam on 16/7/18 下午5:04.
 * Describe:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupResultBean extends BaseBean<GroupResultBean.GroupConversationBean> {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GroupConversationBean {

        /**
         * id : 134
         * name : 测试题我的小时候我是有点过了几-GGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
         * caseInfo : {"id":12,"caseName":"GGGGGGGGGGGGGGGGGGGGGGGGGGGGGG","caseCover":"http://o8nljewkg.bkt.clouddn.com/o_1aqdrn2ca1n3415mj1k4pr2g3sq2l.jpg","status":1}
         * available : true
         * createTime : 2016-08-18 14:02:31
         * members : [{"userId":1,"userJoinType":2,"userType":"m","userDetailType":2,"userTitle":"客服","showName":"小鹦","showHead":"http://o8nljewkg.bkt.clouddn.com/o_1ao3jmbhk11qj1q031k9s10685qac.png","gender":0,"sessionId":134,"imUid":"m1"},{"userId":146,"userJoinType":3,"userType":"e","userDetailType":1,"userTitle":"设计师","showName":"一号设计师","showHead":"http://o8nljewkg.bkt.clouddn.com/o_1aqbrosu0jev1cmdl2bbum1bhn10.jpg","gender":0,"phone":"18908069601","sessionId":134,"imUid":"e146"},{"userId":147,"userJoinType":3,"userType":"e","userDetailType":2,"userTitle":"项目经理","showName":"一号项目经理","showHead":"http://o8nljewkg.bkt.clouddn.com/o_1aqbrqqnj14n71l4jvdfoce1s1f28.jpg","gender":0,"phone":"18908069602","sessionId":134,"imUid":"e147"},{"userId":148,"userJoinType":3,"userType":"e","userDetailType":3,"userTitle":"家居顾问","showName":"一号家居顾问","showHead":"http://o8nljewkg.bkt.clouddn.com/o_1aqbruu5b3d53lcaio145b1a5q36.jpg","gender":0,"phone":"18908069611","sessionId":134,"imUid":"e148"},{"userId":151,"userJoinType":1,"userType":"c","showName":"测试题我的小时候我是有点过了几","showHead":"http://o8nljewkg.bkt.clouddn.com/FoO42rqzJuK3pR_5je7RfFvPwSG3","gender":0,"phone":"13550279532","sessionId":134,"imUid":"c151"}]
         */

        private int id;
        private String name;
        /**
         * id : 12
         * caseName : GGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
         * caseCover : http://o8nljewkg.bkt.clouddn.com/o_1aqdrn2ca1n3415mj1k4pr2g3sq2l.jpg
         * status : 1
         */

        private CaseInfoBean caseInfo;
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
         * gender : 0
         * sessionId : 134
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

        @JsonIgnoreProperties(ignoreUnknown = true)
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

        @JsonIgnoreProperties(ignoreUnknown = true)
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
