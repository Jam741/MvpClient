package com.yingwumeijia.android.worker.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jam on 2016/8/12 14:10.
 * Describe:
 */
public class CustomerResultBean extends BaseBean<CustomerResultBean.CustomerInfo> {


    /**
     * id : 37
     * userName : 15681112269
     * userPhone : 15681112269
     * userType : e
     * userTypeId : 48
     * deleted : false
     * available : true
     * name : 王二麻子
     * gender : 0
     * createUserId : 4
     * createTime : 1471312625000
     * updateTime : 1471320607000
     * lastLoginTime : 1471339974000
     * entityId : 14
     * userDetailType : 1
     * companyId : 1
     * headImage : http://o8nljewkg.bkt.clouddn.com/FhD9O-olKl1WIn1FGt4EXa5lSVCP
     * lifePhotos : http://o8nljewkg.bkt.clouddn.com/o_1aq8gvbgeh3aa032cs31p1ka6c.png
     * resume : 里斯本竞技ytrytrytyrty
     * showName : 王二麻子
     * userDetailTypeDesc : 设计师
     * showHead : http://o8nljewkg.bkt.clouddn.com/FhD9O-olKl1WIn1FGt4EXa5lSVCP
     */


    @JsonIgnoreProperties (ignoreUnknown = true)
    public static class CustomerInfo {
        private UserBean employeeDto;
        /**
         * employeeDto : {"id":37,"userName":"15681112269","userPhone":"15681112269","userType":"e","userTypeId":48,"deleted":false,"available":true,"name":"王二麻子","gender":0,"createUserId":4,"createTime":1471312625000,"updateTime":1471320607000,"lastLoginTime":1471339974000,"entityId":14,"userDetailType":1,"companyId":1,"headImage":"http://o8nljewkg.bkt.clouddn.com/FhD9O-olKl1WIn1FGt4EXa5lSVCP","lifePhotos":"http://o8nljewkg.bkt.clouddn.com/o_1aq8gvbgeh3aa032cs31p1ka6c.png","resume":"里斯本竞技ytrytrytyrty","showName":"王二麻子","userDetailTypeDesc":"设计师","showHead":"http://o8nljewkg.bkt.clouddn.com/FhD9O-olKl1WIn1FGt4EXa5lSVCP"}
         * collectionCount : 0
         */

        private int collectionCount;

        public UserBean getEmployeeDto() {
            return employeeDto;
        }

        public void setEmployeeDto(UserBean employeeDto) {
            this.employeeDto = employeeDto;
        }

        public int getCollectionCount() {
            return collectionCount;
        }

        public void setCollectionCount(int collectionCount) {
            this.collectionCount = collectionCount;
        }

    }

}
