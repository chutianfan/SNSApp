package com.gitrose.mobile.model;

import java.util.ArrayList;

public class PrivateMessageList {
    private int isblock;
    private ArrayList<PrivateMeassageModel> messagelist;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<PrivateMeassageModel> getMessagelist() {
        return this.messagelist;
    }

    public void setMessagelist(ArrayList<PrivateMeassageModel> messagelist) {
        this.messagelist = messagelist;
    }

    public int getIsblock() {
        return this.isblock;
    }

    public void setIsblock(int isblock) {
        this.isblock = isblock;
    }
}
