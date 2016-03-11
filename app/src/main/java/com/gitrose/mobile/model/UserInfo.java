package com.gitrose.mobile.model;

import com.gitrose.greendao.TopicInfo;

import java.io.Serializable;
import java.util.List;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1;
    private String addtime;
    private String age;
    private String area;
    private String avatartime;
    private String birthday;
    private String city;
    private String distance;
    private List<TopicInfo> favlist;
    private String gender;
    private int isbind_phone;
    private int isbind_qq;
    private int isbind_weibo;
    private int isblock;
    private String isblockme;
    private String isqqlogin;
    private String lasttime;
    private String locationstatus;
    private String logintype;
    private int newmessagecount;
    private int newtipscount;
    private String nickname;
    private String province;
    private String rctoken;
    private String relation;
    private String sign;
    private int topicblock;
    private List<TopicInfo> topiclist;
    private String uid;

    public List<TopicInfo> getTopiclist() {
        return this.topiclist;
    }

    public void setTopiclist(List<TopicInfo> topiclist) {
        this.topiclist = topiclist;
    }

    public int getIsblock() {
        return this.isblock;
    }

    public void setIsblock(int isblock) {
        this.isblock = isblock;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<TopicInfo> getFavlist() {
        return this.favlist;
    }

    public void setFavlist(List<TopicInfo> favlist) {
        this.favlist = favlist;
    }

    public String getLasttime() {
        return this.lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public int getNewtipscount() {
        return this.newtipscount;
    }

    public void setNewtipscount(int newtipscount) {
        this.newtipscount = newtipscount;
    }

    public int getNewmessagecount() {
        return this.newmessagecount;
    }

    public void setNewmessagecount(int newmessagecount) {
        this.newmessagecount = newmessagecount;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIsqqlogin() {
        return this.isqqlogin;
    }

    public void setIsqqlogin(String isqqlogin) {
        this.isqqlogin = isqqlogin;
    }

    public int getTopicblock() {
        return this.topicblock;
    }

    public void setTopicblock(int topicblock) {
        this.topicblock = topicblock;
    }

    public int getIsbind_qq() {
        return this.isbind_qq;
    }

    public void setIsbind_qq(int isbind_qq) {
        this.isbind_qq = isbind_qq;
    }

    public int getIsbind_weibo() {
        return this.isbind_weibo;
    }

    public void setIsbind_weibo(int isbind_weibo) {
        this.isbind_weibo = isbind_weibo;
    }

    public int getIsbind_phone() {
        return this.isbind_phone;
    }

    public void setIsbind_phone(int isbind_phone) {
        this.isbind_phone = isbind_phone;
    }

    public String getLogintype() {
        return this.logintype;
    }

    public void setLogintype(String logintype) {
        this.logintype = logintype;
    }

    public String getLocationstatus() {
        return this.locationstatus;
    }

    public void setLocationstatus(String locationstatus) {
        this.locationstatus = locationstatus;
    }

    public String getRctoken() {
        return this.rctoken;
    }

    public void setRctoken(String rctoken) {
        this.rctoken = rctoken;
    }

    public String getIsblockme() {
        return this.isblockme;
    }

    public void setIsblockme(String isblockme) {
        this.isblockme = isblockme;
    }
}
