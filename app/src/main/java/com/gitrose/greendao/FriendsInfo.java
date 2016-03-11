package com.gitrose.greendao;

import java.io.Serializable;

public class FriendsInfo implements Serializable {
    private String addtime;
    private Integer age;
    private String area;
    private String authreason;
    private String avatartime;
    private String birthday;
    private String canchat;
    private String city;
    private String constellation;
    private String followtime;
    private Integer gender;
    private Long id;
    private String isauth;
    private Integer isblock;
    private Integer isblockme;
    private String last_talk_time;
    private String nickname;
    private String province;
    private Integer relation;
    private String relation_uid;
    private String remarkname;
    private String sign;
    private String sortLetters;
    private Integer topicblock;
    private String uid;
    private String updatetime;
    private Integer userhonorlevel;

    public String getSortLetters() {
        return this.sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public FriendsInfo(Long id) {
        this.id = id;
    }

    public FriendsInfo(Long id, String uid, String relation_uid, String nickname, String remarkname, String avatartime, Integer gender, String birthday, String province, String city, String area, String sign, String addtime, Integer age, Integer userhonorlevel, String constellation, String last_talk_time, Integer relation, Integer isblock, Integer isblockme, Integer topicblock, String canchat, String updatetime, String followtime, String isauth, String authreason) {
        this.id = id;
        this.uid = uid;
        this.relation_uid = relation_uid;
        this.nickname = nickname;
        this.remarkname = remarkname;
        this.avatartime = avatartime;
        this.gender = gender;
        this.birthday = birthday;
        this.province = province;
        this.city = city;
        this.area = area;
        this.sign = sign;
        this.addtime = addtime;
        this.age = age;
        this.userhonorlevel = userhonorlevel;
        this.constellation = constellation;
        this.last_talk_time = last_talk_time;
        this.relation = relation;
        this.isblock = isblock;
        this.isblockme = isblockme;
        this.topicblock = topicblock;
        this.canchat = canchat;
        this.updatetime = updatetime;
        this.followtime = followtime;
        this.isauth = isauth;
        this.authreason = authreason;
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

    public String getRelation_uid() {
        return this.relation_uid;
    }

    public void setRelation_uid(String relation_uid) {
        this.relation_uid = relation_uid;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRemarkname() {
        return this.remarkname;
    }

    public void setRemarkname(String remarkname) {
        this.remarkname = remarkname;
    }

    public String getAvatartime() {
        return this.avatartime;
    }

    public void setAvatartime(String avatartime) {
        this.avatartime = avatartime;
    }

    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAddtime() {
        return this.addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getUserhonorlevel() {
        return this.userhonorlevel;
    }

    public void setUserhonorlevel(Integer userhonorlevel) {
        this.userhonorlevel = userhonorlevel;
    }

    public String getConstellation() {
        return this.constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getLast_talk_time() {
        return this.last_talk_time;
    }

    public void setLast_talk_time(String last_talk_time) {
        this.last_talk_time = last_talk_time;
    }

    public Integer getRelation() {
        return this.relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public Integer getIsblock() {
        return this.isblock;
    }

    public void setIsblock(Integer isblock) {
        this.isblock = isblock;
    }

    public Integer getIsblockme() {
        return this.isblockme;
    }

    public void setIsblockme(Integer isblockme) {
        this.isblockme = isblockme;
    }

    public Integer getTopicblock() {
        return this.topicblock;
    }

    public void setTopicblock(Integer topicblock) {
        this.topicblock = topicblock;
    }

    public String getCanchat() {
        return this.canchat;
    }

    public void setCanchat(String canchat) {
        this.canchat = canchat;
    }

    public String getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getFollowtime() {
        return this.followtime;
    }

    public void setFollowtime(String followtime) {
        this.followtime = followtime;
    }

    public String getIsauth() {
        return this.isauth;
    }

    public void setIsauth(String isauth) {
        this.isauth = isauth;
    }

    public String getAuthreason() {
        return this.authreason;
    }

    public void setAuthreason(String authreason) {
        this.authreason = authreason;
    }
}
