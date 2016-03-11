package com.gitrose.mobile.model;

public class SubjectInfo {
    private String desc;
    private String htid;
    private String httext;
    private String joinusercount;
    private String picurl;
    private String sortLetters;
    private String title;
    private String topiccount;
    private String usercount;
    private String viewcount;

    public String getJoinusercount() {
        return this.joinusercount;
    }

    public void setJoinusercount(String joinusercount) {
        this.joinusercount = joinusercount;
    }

    public String getHttext() {
        return this.httext;
    }

    public void setHttext(String httext) {
        this.httext = httext;
    }

    public String getViewcount() {
        return this.viewcount;
    }

    public void setViewcount(String viewcount) {
        this.viewcount = viewcount;
    }

    public String getTopiccount() {
        return this.topiccount;
    }

    public void setTopiccount(String topiccount) {
        this.topiccount = topiccount;
    }

    public String getUsercount() {
        return this.usercount;
    }

    public void setUsercount(String usercount) {
        this.usercount = usercount;
    }

    public String getPicurl() {
        return this.picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSortLetters() {
        return this.sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getHtid() {
        return this.htid;
    }

    public void setHtid(String htid) {
        this.htid = htid;
    }
}
