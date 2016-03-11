package com.gitrose.mobile.model;

import java.io.Serializable;

public class HuaTi implements Serializable {
    private static final long serialVersionUID = 8587359743357315521L;
    private String content;
    private String huatitext;
    private String topicid;
    private boolean type;

    public HuaTi() {
        this.type = true;
    }

    public String getHuatitext() {
        return this.huatitext;
    }

    public void setHuatitext(String huatitext) {
        this.huatitext = huatitext;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopicid() {
        return this.topicid;
    }

    public void setTopicid(String topicid) {
        this.topicid = topicid;
    }

    public boolean isType() {
        return this.type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
