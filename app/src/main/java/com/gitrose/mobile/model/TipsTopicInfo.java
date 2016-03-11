package com.gitrose.mobile.model;

public class TipsTopicInfo {
    private String content;
    private String desc;
    private String fromrepost;
    private String topicid;
    private String type;

    public String getTopicid() {
        return this.topicid;
    }

    public void setTopicid(String topicid) {
        this.topicid = topicid;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromrepost() {
        return this.fromrepost;
    }

    public void setFromrepost(String fromrepost) {
        this.fromrepost = fromrepost;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
