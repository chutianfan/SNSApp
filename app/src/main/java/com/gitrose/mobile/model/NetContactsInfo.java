package com.gitrose.mobile.model;

import java.util.List;

public class NetContactsInfo {
    String deviceid;
    List<NetContactUserInfo> list;

    public List<NetContactUserInfo> getList() {
        return this.list;
    }

    public void setList(List<NetContactUserInfo> list) {
        this.list = list;
    }

    public String getDeviceid() {
        return this.deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}
