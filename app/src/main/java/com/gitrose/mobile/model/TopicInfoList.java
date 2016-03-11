package com.gitrose.mobile.model;

import com.gitrose.greendao.TopicInfo;

import java.util.ArrayList;
import java.util.List;

public class TopicInfoList {
    private List<String> deletelist;
    private ArrayList<TopicInfo> list;
    private int newmessagecount;
    private int newtipscount;
    private int total;

    public ArrayList<TopicInfo> getList() {
        return this.list;
    }

    public void setList(ArrayList<TopicInfo> list) {
        this.list = list;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNewmessagecount() {
        return this.newmessagecount;
    }

    public void setNewmessagecount(int newmessagecount) {
        this.newmessagecount = newmessagecount;
    }

    public int getNewtipscount() {
        return this.newtipscount;
    }

    public void setNewtipscount(int newtipscount) {
        this.newtipscount = newtipscount;
    }

    public List<String> getDeletelist() {
        return this.deletelist;
    }

    public void setDeletelist(List<String> deletelist) {
        this.deletelist = deletelist;
    }
}
