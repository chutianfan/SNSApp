package com.gitrose.mobile.model;

//import io.rong.imlib.RongIMClient.Conversation;

public class MessageRelationModel {
    private String avatartime;
//    private Conversation conversatin;
    private String isblock;
    private String isblockme;
    private String lastmsg;
    private String lastmsgtime;
    private String nickname;
    private String relation;
    private String topicblock;
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

    public String getIsblock() {
        return this.isblock;
    }

    public void setIsblock(String isblock) {
        this.isblock = isblock;
    }

    public String getTopicblock() {
        return this.topicblock;
    }

    public void setTopicblock(String topicblock) {
        this.topicblock = topicblock;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

//    public Conversation getConversatin() {
//        return this.conversatin;
//    }
//
//    public void setConversatin(Conversation conversatin) {
//        this.conversatin = conversatin;
//    }

    public String getIsblockme() {
        return this.isblockme;
    }

    public void setIsblockme(String isblockme) {
        this.isblockme = isblockme;
    }

    public String getLastmsgtime() {
        return this.lastmsgtime;
    }

    public void setLastmsgtime(String lastmsgtime) {
        this.lastmsgtime = lastmsgtime;
    }

    public String getLastmsg() {
        return this.lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }

    public String getAvatartime() {
        return this.avatartime;
    }

    public void setAvatartime(String avatartime) {
        this.avatartime = avatartime;
    }
}
