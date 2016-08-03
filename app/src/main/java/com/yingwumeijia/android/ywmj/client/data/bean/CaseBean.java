package com.yingwumeijia.android.ywmj.client.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jam on 16/6/15 上午10:30.
 * Describe:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseBean {
    /**
     * caseCover : http://o8nljewkg.bkt.clouddn.com/o_1amdfpmii1rn61vmptcb16k248k1r.jpg
     * caseId : 1062
     * collectionNumber : 1
     * isCollected : false
     * caseName : J
     */

    private String caseCover;
    private int caseId;
    private int collectionNumber;
    private boolean isCollected;
    private String caseName;

    public String getCaseCover() {
        return caseCover;
    }

    public void setCaseCover(String caseCover) {
        this.caseCover = caseCover;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public int getCollectionNumber() {
        return collectionNumber;
    }

    public void setCollectionNumber(int collectionNumber) {
        this.collectionNumber = collectionNumber;
    }

    public boolean isIsCollected() {
        return isCollected;
    }

    public void setIsCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }


}
