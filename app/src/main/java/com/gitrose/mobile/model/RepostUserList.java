package com.gitrose.mobile.model;

import java.io.Serializable;

public class RepostUserList implements Serializable {
    private static final long serialVersionUID = -6526939719184880795L;
    private String nickname;
    private String remarkname;
    private String uid;

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRemarkname() {
        return this.remarkname;
    }

    public void setRemarkname(String remarkname) {
        this.remarkname = remarkname;
    }
}
