package com.gitrose.mobile.model;

import com.gitrose.greendao.TutuUsers;

import java.util.List;

public class UserInfoList {
    private String code;
    private List<TutuUsers> data;

    public List<TutuUsers> getFollowlist() {
        return this.data;
    }

    public void setFollowlist(List<TutuUsers> data) {
        this.data = data;
    }
}
