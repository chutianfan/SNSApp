package com.gitrose.greendao;

public class TablesUpdateInfo {
    private String friends_table;
    private Long id;
    private String topic_table;
    private String uid;
    private String user_table;

    public TablesUpdateInfo(Long id) {
        this.id = id;
    }

    public TablesUpdateInfo(Long id, String uid, String user_table, String topic_table, String friends_table) {
        this.id = id;
        this.uid = uid;
        this.user_table = user_table;
        this.topic_table = topic_table;
        this.friends_table = friends_table;
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

    public String getUser_table() {
        return this.user_table;
    }

    public void setUser_table(String user_table) {
        this.user_table = user_table;
    }

    public String getTopic_table() {
        return this.topic_table;
    }

    public void setTopic_table(String topic_table) {
        this.topic_table = topic_table;
    }

    public String getFriends_table() {
        return this.friends_table;
    }

    public void setFriends_table(String friends_table) {
        this.friends_table = friends_table;
    }
}
