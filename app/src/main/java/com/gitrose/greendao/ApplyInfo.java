package com.gitrose.greendao;

import java.io.Serializable;
import java.util.List;

public class ApplyInfo implements Serializable {
    private String age;
    private String applymsg;
    List<ApplyMsgInfo> applymsglist;
    private String applystatus;
    private String applytime;
    private String applytype;
    private String avatartime;
    boolean checked;
    private String frienduid;
    private String gender;
    private Long id;
    private String isDel;
    private String isblock;
    private String isread;
    private String nickname;
    private String relation;
    private String relation_uid;
    private String remarkname;
    private String sign;
    private String topicblock;
    private String uptime;
    private String userhonorlevel;

    public ApplyInfo(Long id) {
        this.id = id;
    }

    public ApplyInfo(Long id, String frienduid, String relation_uid, String relation, String remarkname, String nickname, String avatartime, String gender, String sign, String isblock, String age, String userhonorlevel, String topicblock, String applymsg, String applystatus, String applytype, String applytime, String uptime, String isDel, String isread) {
        this.id = id;
        this.frienduid = frienduid;
        this.relation_uid = relation_uid;
        this.relation = relation;
        this.remarkname = remarkname;
        this.nickname = nickname;
        this.avatartime = avatartime;
        this.gender = gender;
        this.sign = sign;
        this.isblock = isblock;
        this.age = age;
        this.userhonorlevel = userhonorlevel;
        this.topicblock = topicblock;
        this.applymsg = applymsg;
        this.applystatus = applystatus;
        this.applytype = applytype;
        this.applytime = applytime;
        this.uptime = uptime;
        this.isDel = isDel;
        this.isread = isread;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrienduid() {
        return this.frienduid;
    }

    public void setFrienduid(String frienduid) {
        this.frienduid = frienduid;
    }

    public String getRelation_uid() {
        return this.relation_uid;
    }

    public void setRelation_uid(String relation_uid) {
        this.relation_uid = relation_uid;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRemarkname() {
        return this.remarkname;
    }

    public void setRemarkname(String remarkname) {
        this.remarkname = remarkname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatartime() {
        return this.avatartime;
    }

    public void setAvatartime(String avatartime) {
        this.avatartime = avatartime;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIsblock() {
        return this.isblock;
    }

    public void setIsblock(String isblock) {
        this.isblock = isblock;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUserhonorlevel() {
        return this.userhonorlevel;
    }

    public void setUserhonorlevel(String userhonorlevel) {
        this.userhonorlevel = userhonorlevel;
    }

    public String getTopicblock() {
        return this.topicblock;
    }

    public void setTopicblock(String topicblock) {
        this.topicblock = topicblock;
    }

    public String getApplymsg() {
        return this.applymsg;
    }

    public void setApplymsg(String applymsg) {
        this.applymsg = applymsg;
    }

    public String getApplystatus() {
        return this.applystatus;
    }

    public void setApplystatus(String applystatus) {
        this.applystatus = applystatus;
    }

    public String getApplytype() {
        return this.applytype;
    }

    public void setApplytype(String applytype) {
        this.applytype = applytype;
    }

    public String getApplytime() {
        return this.applytime;
    }

    public void setApplytime(String applytime) {
        this.applytime = applytime;
    }

    public String getUptime() {
        return this.uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getIsDel() {
        return this.isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getIsread() {
        return this.isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<ApplyMsgInfo> getApplymsglist() {
        return this.applymsglist;
    }

    public void setApplymsglist(List<ApplyMsgInfo> applymsglist) {
        this.applymsglist = applymsglist;
    }
}
