package com.gitrose.mobile.model;

public class PrivateMeassageModel extends UserInfo {
    private static final long serialVersionUID = -604678434484774619L;
    private String addtime;
    private String content;
    private String messageid;
    private MessagePic messagepictext;
    private String type;

    public String getMessageid() {
        return this.messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddtime() {
        return this.addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MessagePic getMessagepictext() {
        return this.messagepictext;
    }

    public void setMessagepictext(MessagePic messagepictext) {
        this.messagepictext = messagepictext;
    }
}
