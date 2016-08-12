package com.yingwumeijia.android.ywmj.client.data.bean;

/**
 * Created by Jam on 2016/8/12 14:10.
 * Describe:
 */
public class CustomerResultBean extends BaseBean<CustomerResultBean.CustomerInfo> {


    public static class CustomerInfo {

        private UserBean customerDto;
        private int collectionCount;

        public UserBean getCustomerDto() {
            return customerDto;
        }

        public void setCustomerDto(UserBean customerDto) {
            this.customerDto = customerDto;
        }

        public int getCollectionCount() {
            return collectionCount;
        }

        public void setCollectionCount(int collectionCount) {
            this.collectionCount = collectionCount;
        }
    }
}
