package com.gitrose.mobile.model;

import java.util.List;

public class Operation {
    private List<OperationButton> buttonlist;
    private int displaystyle;
    private String gotourl;
    private String title;

    public String getGotourl() {
        return this.gotourl;
    }

    public void setGotourl(String gotourl) {
        this.gotourl = gotourl;
    }

    public int getDisplaystyle() {
        return this.displaystyle;
    }

    public void setDisplaystyle(int displaystyle) {
        this.displaystyle = displaystyle;
    }

    public List<OperationButton> getButtonlist() {
        return this.buttonlist;
    }

    public void setButtonlist(List<OperationButton> buttonlist) {
        this.buttonlist = buttonlist;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
