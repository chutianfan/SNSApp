package com.gitrose.greendao;

import java.io.Serializable;

public class ApplyMsgInfo implements Serializable {
    private String applymsg;
    private String friend_uid;
    private Long id;
    private String isDel;
    private String isme;
    private String relation_uid;
    private String uid;

    public ApplyMsgInfo(Long id) {
        this.id = id;
    }

    public ApplyMsgInfo(Long id, String uid, String friend_uid, String relation_uid, String isme, String applymsg, String isDel) {
        this.id = id;
        this.uid = uid;
        this.friend_uid = friend_uid;
        this.relation_uid = relation_uid;
        this.isme = isme;
        this.applymsg = applymsg;
        this.isDel = isDel;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFriend_uid() {
        return this.friend_uid;
    }

    public void setFriend_uid(String friend_uid) {
        this.friend_uid = friend_uid;
    }

    public String getRelation_uid() {
        return this.relation_uid;
    }

    public void setRelation_uid(String relation_uid) {
        this.relation_uid = relation_uid;
    }

    public String getIsme() {
        return this.isme;
    }

    public void setIsme(String isme) {
        this.isme = isme;
    }

    public String getApplymsg() {
        return this.applymsg;
    }

    public void setApplymsg(String applymsg) {
        this.applymsg = applymsg;
    }

    public String getIsDel() {
        return this.isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }
}
