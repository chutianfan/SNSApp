package com.gitrose.mobile.model;

import java.util.ArrayList;

public class TipsList {
    private ArrayList<TipsModel> historytiplist;
    private ArrayList<TipsModel> newtiplist;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<TipsModel> getNewtiplist() {
        return this.newtiplist;
    }

    public void setNewtiplist(ArrayList<TipsModel> newtiplist) {
        this.newtiplist = newtiplist;
    }

    public ArrayList<TipsModel> getHistorytiplist() {
        return this.historytiplist;
    }

    public void setHistorytiplist(ArrayList<TipsModel> historytiplist) {
        this.historytiplist = historytiplist;
    }
}
