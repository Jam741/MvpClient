package com.yingwumeijia.android.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by Jam on 16/6/23 下午5:32.
 * Describe:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseDetailsBean {
    /**
     * layout : ?type=0
     * layoutStrList : ["客厅","主卧","厨房","次卧","次卧"]
     * cost : ?type=2
     * material : ?type=3
     * path_720 :
     * plain : ?type=1
     * company : ?type=5
     * team : ?type=4
     * designer : ?type=6
     */

    private String layout;
    private String cost;
    private String material;
    private String path_720;
    private String plain;
    private String company;
    private String team;
    private String designer;
    private String sizeOf720;
    private String previewOf720;
    private ArrayList<String> layoutStrList;

    public String getSizeOf720() {
        return sizeOf720;
    }

    public void setSizeOf720(String sizeOf720) {
        this.sizeOf720 = sizeOf720;
    }

    public String getPreviewOf720() {
        return previewOf720;
    }

    public void setPreviewOf720(String previewOf720) {
        this.previewOf720 = previewOf720;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPath_720() {
        return path_720;
    }

    public void setPath_720(String path_720) {
        this.path_720 = path_720;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public ArrayList<String> getLayoutStrList() {
        return layoutStrList;
    }

    public void setLayoutStrList(ArrayList<String> layoutStrList) {
        this.layoutStrList = layoutStrList;
    }


    /**
     * 720 :
     * designer : /case/app/employeeDesigner/
     * company : /case/app/company/1001
     * layout : /case/app/roomList/1001
     * team : /case/app/team/1001
     * plain : /case/app/plainLayout/1001
     * cost : /case/app/projectCost/1001
     * material : /case/app/materialBrand/1001
     */

}
