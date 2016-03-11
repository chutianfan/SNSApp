package com.gitrose.mobile.model;

public class Province {
    private String proName;
    private String proRemark;
    private int proSort;

    public int getProSort() {
        return this.proSort;
    }

    public void setProSort(int proSort) {
        this.proSort = proSort;
    }

    public String getProName() {
        return this.proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProRemark() {
        return this.proRemark;
    }

    public void setProRemark(String proRemark) {
        this.proRemark = proRemark;
    }
}
