package com.yingwumeijia.android.worker.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jam on 16/6/22 上午11:37.
 * Describe:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseTypeEnum {
    private int id;
    private String name;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

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
}
