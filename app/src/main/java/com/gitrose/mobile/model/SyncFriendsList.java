package com.gitrose.mobile.model;

import com.gitrose.greendao.FriendsInfo;
import java.util.List;

public class SyncFriendsList {
    private List<FriendsInfo> addlist;
    private List<FriendsInfo> dellist;
    private String updatetime;

    public String getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public List<FriendsInfo> getAddlist() {
        return this.addlist;
    }

    public void setAddlist(List<FriendsInfo> addlist) {
        this.addlist = addlist;
    }

    public List<FriendsInfo> getDellist() {
        return this.dellist;
    }

    public void setDellist(List<FriendsInfo> dellist) {
        this.dellist = dellist;
    }
}
