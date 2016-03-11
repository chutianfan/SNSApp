package com.gitrose.mobile.model;

import com.gitrose.greendao.TutuUsers;

public class SyncSelfInfo {
    private String updatetime;
    private TutuUsers userinfo;

    public TutuUsers getUserinfo() {
        return this.userinfo;
    }

    public void setUserinfo(TutuUsers userinfo) {
        this.userinfo = userinfo;
    }

    public String getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
