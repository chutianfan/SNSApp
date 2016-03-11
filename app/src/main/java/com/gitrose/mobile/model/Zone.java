package com.gitrose.mobile.model;

public class Zone {
    private int cityID;
    private int zoneID;
    private String zoneName;

    public int getZoneID() {
        return this.zoneID;
    }

    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }

    public String getZoneName() {
        return this.zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public int getCityID() {
        return this.cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }
}
