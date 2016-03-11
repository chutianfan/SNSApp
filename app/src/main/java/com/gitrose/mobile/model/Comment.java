package com.gitrose.mobile.model;

import com.gitrose.greendao.TutuUsers;
import java.io.Serializable;

public class Comment implements Serializable {
    private static final long serialVersionUID = 3358548763353617164L;
    private String addtime;
    private String commentid;
    private String content;
    private String formataddtime;
    private int iskana;
    private String localCommentId;
    private String localTopicId;
    private String replyCommentId;
    private Comment replydata;
    private String topicId;
    private String topicUid;
    private String txtframe;
    private String uid;
    private TutuUsers userinfo;

    public String getFormataddtime() {
        return this.formataddtime;
    }

    public void setFormataddtime(String formataddtime) {
        this.formataddtime = formataddtime;
    }

    public String getCommentid() {
        return this.commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public Comment getReplydata() {
        return this.replydata;
    }

    public void setReplydata(Comment replydata) {
        this.replydata = replydata;
    }

    public int getIskana() {
        return this.iskana;
    }

    public void setIskana(int iskana) {
        this.iskana = iskana;
    }

    public String getAddtime() {
        return this.addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public TutuUsers getUserinfo() {
        return this.userinfo;
    }

    public void setUserinfo(TutuUsers userinfo) {
        this.userinfo = userinfo;
    }

    public String getLocalTopicId() {
        return this.localTopicId;
    }

    public void setLocalTopicId(String localTopicId) {
        this.localTopicId = localTopicId;
    }

    public String getReplyCommentId() {
        return this.replyCommentId;
    }

    public void setReplyCommentId(String replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public String getTopicId() {
        return this.topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicUid() {
        return this.topicUid;
    }

    public void setTopicUid(String topicUid) {
        this.topicUid = topicUid;
    }

    public String getLocalCommentId() {
        return this.localCommentId;
    }

    public void setLocalCommentId(String localCommentId) {
        this.localCommentId = localCommentId;
    }
}
