package com.gitrose.mobile.model;

public class City {
    private String cityName;
    private int citySort;
    private int proID;

    public int getCitySort() {
        return this.citySort;
    }

    public void setCitySort(int citySort) {
        this.citySort = citySort;
    }

    public int getProID() {
        return this.proID;
    }

    public void setProID(int proID) {
        this.proID = proID;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
