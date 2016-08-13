package com.yingwumeijia.android.worker.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Jam on 16/6/22 上午11:39.
 * Describe:
 */
public class CaseTypeResultBean extends BaseBean<CaseTypeResultBean.CaseTypeSetBean> {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CaseTypeSetBean {
        List<CaseTypeEnum> houseType;
        List<CaseTypeEnum> costRangeType;
        List<CaseTypeEnum> decorateStyleType;

        public List<CaseTypeEnum> getHouseType() {
            return houseType;
        }

        public void setHouseType(List<CaseTypeEnum> houseType) {
            this.houseType = houseType;
        }

        public List<CaseTypeEnum> getCostRangeType() {
            return costRangeType;
        }

        public void setCostRangeType(List<CaseTypeEnum> costRangeType) {
            this.costRangeType = costRangeType;
        }

        public List<CaseTypeEnum> getDecorateStyleType() {
            return decorateStyleType;
        }

        public void setDecorateStyleType(List<CaseTypeEnum> decorateStyleType) {
            this.decorateStyleType = decorateStyleType;
        }
    }
}
