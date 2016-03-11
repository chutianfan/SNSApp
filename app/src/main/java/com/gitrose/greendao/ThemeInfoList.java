package com.gitrose.greendao;

import java.io.Serializable;
import java.util.ArrayList;

public class ThemeInfoList implements Serializable {
    private static final long serialVersionUID = 1;
    private String ids;
    private String idtext;
    private int isfollow;
    private int topiccount;
    private ArrayList<TopicInfo> topiclist;
    private int usercount;
    private ArrayList<TutuUsers> userlist;
    private int viewcount;
    private String viewhumancount;

    public ThemeInfoList(String ids, String idtext, int isfollow, int viewcount, String viewhumancount, int topiccount, int usercount, ArrayList<TutuUsers> userlist, ArrayList<TopicInfo> topiclist) {
        this.ids = ids;
        this.idtext = idtext;
        this.isfollow = isfollow;
        this.viewcount = viewcount;
        this.topiccount = topiccount;
        this.usercount = usercount;
        this.userlist = userlist;
        this.topiclist = topiclist;
        this.viewhumancount = viewhumancount;
    }

    public String getViewhumancount() {
        return this.viewhumancount;
    }

    public void setViewhumancount(String viewhumancount) {
        this.viewhumancount = viewhumancount;
    }

    public String getIds() {
        return this.ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getIdtext() {
        return this.idtext;
    }

    public void setIdtext(String idtext) {
        this.idtext = idtext;
    }

    public int getIsfollow() {
        return this.isfollow;
    }

    public void setIsfollow(int isfollow) {
        this.isfollow = isfollow;
    }

    public int getViewcount() {
        return this.viewcount;
    }

    public void setViewcount(int viewcount) {
        this.viewcount = viewcount;
    }

    public int getTopiccount() {
        return this.topiccount;
    }

    public void setTopiccount(int topiccount) {
        this.topiccount = topiccount;
    }

    public int getUsercount() {
        return this.usercount;
    }

    public void setUsercount(int usercount) {
        this.usercount = usercount;
    }

    public ArrayList<TutuUsers> getUserlist() {
        return this.userlist;
    }

    public void setUserlist(ArrayList<TutuUsers> userlist) {
        this.userlist = userlist;
    }

    public ArrayList<TopicInfo> getTopiclist() {
        return this.topiclist;
    }

    public void setTopiclist(ArrayList<TopicInfo> topiclist) {
        this.topiclist = topiclist;
    }
}
