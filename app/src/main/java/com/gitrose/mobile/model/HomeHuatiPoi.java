package com.gitrose.mobile.model;

import java.io.Serializable;

public class HomeHuatiPoi implements Serializable {
    private static final long serialVersionUID = 7992248656792768797L;
    private String content;
    private String topicid;

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
}
