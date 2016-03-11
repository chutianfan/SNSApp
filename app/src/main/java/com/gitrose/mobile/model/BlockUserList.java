package com.gitrose.mobile.model;

import java.util.List;

public class BlockUserList {
    private List<UserInfo> list;
    private String total;

    public String getTotal() {
        return this.total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<UserInfo> getList() {
        return this.list;
    }

    public void setList(List<UserInfo> list) {
        this.list = list;
    }
}
