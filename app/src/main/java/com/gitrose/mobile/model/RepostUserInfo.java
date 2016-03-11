package com.gitrose.mobile.model;

import java.io.Serializable;
import java.util.ArrayList;

public class RepostUserInfo implements Serializable {
    private static final long serialVersionUID = -4494269078795666171L;
    private int total;
    private ArrayList<RepostUserList> userlist;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<RepostUserList> getUserlist() {
        return this.userlist;
    }

    public void setUserlist(ArrayList<RepostUserList> userlist) {
        this.userlist = userlist;
    }
}
