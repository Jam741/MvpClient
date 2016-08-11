package com.yingwumeijia.android.data.bean;

/**
 * Created by Jam on 16/6/15 上午10:30.
 * Describe:
 */
public class CaseBean {


    /**
     * caseCover : http://o8nljewkg.bkt.clouddn.com/o_1an2bpg308m61fp73si1o76ph317.jpg
     * caseId : 1077
     * collectionNumber : 8
     * isCollected : false
     * caseName : 天水怡景C户型（面积188㎡）
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
