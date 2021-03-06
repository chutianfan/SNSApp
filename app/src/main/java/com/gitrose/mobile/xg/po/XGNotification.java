package com.gitrose.mobile.xg.po;

public class XGNotification {
    private String activity;
    private String content;
    private Integer id;
    private Long msg_id;
    private String title;
    private String update_time;
    private String url;

    public XGNotification(Integer id, Long msg_id, String title, String content, String activity, String url, String update_time) {
        this.id = id;
        this.msg_id = msg_id;
        this.title = title;
        this.content = content;
        this.activity = activity;
        this.url = url;
        this.update_time = update_time;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getMsg_id() {
        return this.msg_id;
    }

    public void setMsg_id(Long msg_id) {
        this.msg_id = msg_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
