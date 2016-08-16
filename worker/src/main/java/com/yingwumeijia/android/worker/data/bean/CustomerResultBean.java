package com.yingwumeijia.android.worker.data.bean;

/**
 * Created by Jam on 2016/8/12 14:10.
 * Describe:
 */
public class CustomerResultBean extends BaseBean<CustomerResultBean.CustomerInfo> {


    public static class CustomerInfo {

        private UserBean employeeDto;
        private int collectionCount;

        public UserBean getCustomerDto() {
            return employeeDto;
        }

        public void setCustomerDto(UserBean employeeDto) {
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
