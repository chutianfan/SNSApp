package com.gitrose.mobile.model;

import com.gitrose.greendao.TopicInfo;
import java.util.List;

public class SyncTopicList {
    private List<TopicInfo> addlist;
    private List<TopicInfo> dellist;
    private String updatetime;

    public String getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public List<TopicInfo> getAddlist() {
        return this.addlist;
    }

    public void setAddlist(List<TopicInfo> addlist) {
        this.addlist = addlist;
    }

    public List<TopicInfo> getDellist() {
        return this.dellist;
    }

    public void setDellist(List<TopicInfo> dellist) {
        this.dellist = dellist;
    }
}
