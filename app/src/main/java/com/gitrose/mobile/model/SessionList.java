package com.gitrose.mobile.model;

import java.util.ArrayList;

public class SessionList {
    private ArrayList<SessionModel> sessionlist;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<SessionModel> getSessionlist() {
        return this.sessionlist;
    }

    public void setSessionlist(ArrayList<SessionModel> sessionlist) {
        this.sessionlist = sessionlist;
    }
}
