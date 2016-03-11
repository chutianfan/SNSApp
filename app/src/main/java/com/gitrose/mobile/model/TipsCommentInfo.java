package com.gitrose.mobile.model;

public class TipsCommentInfo {
    private String commentid;
    private String content;
    private String iskana;
    private String replycommentid;
    private String txtframe;
    private String type;

    public String getCommentid() {
        return this.commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getReplycommentid() {
        return this.replycommentid;
    }

    public void setReplycommentid(String replycommentid) {
        this.replycommentid = replycommentid;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTxtframe() {
        return this.txtframe;
    }

    public void setTxtframe(String txtframe) {
        this.txtframe = txtframe;
    }

    public String getIskana() {
        return this.iskana;
    }

    public void setIskana(String iskana) {
        this.iskana = iskana;
    }
}
