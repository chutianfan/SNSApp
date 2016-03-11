package com.gitrose.mobile.model;

public class TipsModel {
    private String action;
    private String actionid;
    private String actionuid;
    private String addtime;
    private String authreason;
    private String avatartime;
    private TipsCommentInfo commentinfo;
    private String data;
    private String formattime;
    private int isHead;
    private String isauth;
    private String nickname;
    private int read;
    private int relation;
    private String remarkname;
    private TipsCommentInfo replycommentinfo;
    private String routeid;
    private String tipid;
    private String toavatartime;
    private String tohomecoverurl;
    private TipsTopicInfo topicinfo;
    private String touid;
    private String userhonorlevel;

    public TipsModel(int isHead) {
        this.isHead = 0;
        this.isHead = isHead;
    }

    public String getIsauth() {
        return this.isauth;
    }

    public void setIsauth(String isauth) {
        this.isauth = isauth;
    }

    public String getTipid() {
        return this.tipid;
    }

    public void setTipid(String tipid) {
        this.tipid = tipid;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRouteid() {
        return this.routeid;
    }

    public void setRouteid(String routeid) {
        this.routeid = routeid;
    }

    public String getAddtime() {
        return this.addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getActionuid() {
        return this.actionuid;
    }

    public void setActionuid(String actionuid) {
        this.actionuid = actionuid;
    }

    public String getAvatartime() {
        return this.avatartime;
    }

    public void setAvatartime(String avatartime) {
        this.avatartime = avatartime;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRead() {
        return this.read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public String getActionid() {
        return this.actionid;
    }

    public void setActionid(String actionid) {
        this.actionid = actionid;
    }

    public String getUserhonorlevel() {
        return this.userhonorlevel;
    }

    public void setUserhonorlevel(String userhonorlevel) {
        this.userhonorlevel = userhonorlevel;
    }

    public int getIsHead() {
        return this.isHead;
    }

    public void setIsHead(int isHead) {
        this.isHead = isHead;
    }

    public String getRemarkname() {
        return this.remarkname;
    }

    public void setRemarkname(String remarkname) {
        this.remarkname = remarkname;
    }

    public String getAuthreason() {
        return this.authreason;
    }

    public void setAuthreason(String authreason) {
        this.authreason = authreason;
    }

    public String getFormattime() {
        return this.formattime;
    }

    public void setFormattime(String formattime) {
        this.formattime = formattime;
    }

    public String getTohomecoverurl() {
        return this.tohomecoverurl;
    }

    public void setTohomecoverurl(String tohomecoverurl) {
        this.tohomecoverurl = tohomecoverurl;
    }

    public String getToavatartime() {
        return this.toavatartime;
    }

    public void setToavatartime(String toavatartime) {
        this.toavatartime = toavatartime;
    }

    public String getTouid() {
        return this.touid;
    }

    public void setTouid(String touid) {
        this.touid = touid;
    }

    public TipsTopicInfo getTopicinfo() {
        return this.topicinfo;
    }

    public void setTopicinfo(TipsTopicInfo topicinfo) {
        this.topicinfo = topicinfo;
    }

    public TipsCommentInfo getCommentinfo() {
        return this.commentinfo;
    }

    public void setCommentinfo(TipsCommentInfo commentinfo) {
        this.commentinfo = commentinfo;
    }

    public TipsCommentInfo getReplycommentinfo() {
        return this.replycommentinfo;
    }

    public void setReplycommentinfo(TipsCommentInfo replycommentinfo) {
        this.replycommentinfo = replycommentinfo;
    }

    public int getRelation() {
        return this.relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }
}
