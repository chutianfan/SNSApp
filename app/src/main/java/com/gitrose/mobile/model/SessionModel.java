package com.gitrose.mobile.model;

public class SessionModel extends UserInfo {
    private int count;
    private boolean isBlock;
    private String message;
    private String messageid;
    private String uptime;

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageid() {
        return this.messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getUptime() {
        return this.uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public boolean isBlock() {
        return this.isBlock;
    }

    public void setBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }
}
