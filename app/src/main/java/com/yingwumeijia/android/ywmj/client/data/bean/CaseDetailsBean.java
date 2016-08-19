package com.yingwumeijia.android.ywmj.client.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Jam on 16/6/23 下午5:32.
 * Describe:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseDetailsBean {


    /**
     * layout : http://192.168.28.15:8082/consoleMobile/template/index.html?caseId=4&type=roomList
     * layoutStrList : ["客厅","主卧"]
     * cost : http://192.168.28.15:8082/consoleMobile/template/index.html?caseId=4&type=projectCost
     * material : http://192.168.28.15:8082/consoleMobile/template/index.html?caseId=4&type=materialBrand
     * plain : http://192.168.28.15:8082/consoleMobile/template/index.html?caseId=4&type=plainLayout
     * previewOf720 : http://192.168.28.15:8182/images/a2634cbb02ae4ca9921d18ddc38332a9/720-15/panos/01-6000.tiles/thumb.jpg
     * company : http://192.168.28.15:8082/consoleMobile/template/index.html?caseId=4&type=company
     * team : http://192.168.28.15:8082/consoleMobile/template/index.html?caseId=4&type=team
     * designer : http://192.168.28.15:8082/consoleMobile/template/index.html?caseId=4&type=employeeDesigner
     * path_720 : http://192.168.28.15:8182/images/a2634cbb02ae4ca9921d18ddc38332a9/720-15/index.html?caseId=4&isContact=true&type=720
     * isContact : true
     * isCollected : false
     */

    private String layout;
    private String cost;
    private String material;
    private String plain;
    private String previewOf720;
    private String company;
    private String team;
    private String designer;
    private String path_720;
    private boolean isContact;
    private boolean isCollected;
    private List<String> layoutStrList;

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

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public String getPreviewOf720() {
        return previewOf720;
    }

    public void setPreviewOf720(String previewOf720) {
        this.previewOf720 = previewOf720;
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

    public String getPath_720() {
        return path_720;
    }

    public void setPath_720(String path_720) {
        this.path_720 = path_720;
    }

    public boolean isIsContact() {
        return isContact;
    }

    public void setIsContact(boolean isContact) {
        this.isContact = isContact;
    }

    public boolean isIsCollected() {
        return isCollected;
    }

    public void setIsCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }

    public List<String> getLayoutStrList() {
        return layoutStrList;
    }

    public void setLayoutStrList(List<String> layoutStrList) {
        this.layoutStrList = layoutStrList;
    }
}
