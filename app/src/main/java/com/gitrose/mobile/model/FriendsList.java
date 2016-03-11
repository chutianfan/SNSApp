package com.gitrose.mobile.model;

import com.gitrose.greendao.TutuUsers;

import java.util.List;

public class FriendsList {
    private List<TutuUsers> list;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TutuUsers> getList() {
        return this.list;
    }

    public void setList(List<TutuUsers> list) {
        this.list = list;
    }
}
