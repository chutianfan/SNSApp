package com.gitrose.greendao;

import java.io.Serializable;
import java.util.List;

public class TutuUsers implements Serializable {
    private static final long serialVersionUID = 6798395535082066440L;
    private String addtime;
    private Integer age;
    private String area;
    private String authreason;
    private String avatartime;
    private String beannum;
    private String birthday;
    private String cansendmessage;
    private String city;
    private String coinnum;
    private String constellation;
    private String descinfo;
    private String distance;
    private String errormsg;
    private Integer fansnum;
    private List<TopicInfo> favlist;
    private Integer favnum;
    private Integer follownum;
    private Integer gender;
    public boolean hasGetInfo;
    private String homecoverurl;
    private Long id;
    private boolean isCreater;
    public boolean isShareMovie;
    public boolean isShareSrc;
    public boolean isSpeaking;
    public boolean isVideoIn;
    private Integer is_current;
    private String isauth;
    private Integer isbind_phone;
    private Integer isbind_qq;
    private Integer isbind_weibo;
    private int isblock;
    private String isblockme;
    private String isliked;
    private Integer isqqlogin;
    private String lasttime;
    private Integer likenum;
    private int liveroomnum;
    private String locationstatus;
    private String logintype;
    private int newmessagecount;
    private int newtipscount;
    private String mb_nick;
    private String province;
    private String rctoken;
    private String relation;
    private String mb_name;
    private String sendcoinnum;
    private String sign;
    private String status;
    private int topicblock;
    private List<TopicInfo> topiclist;
    private Integer topicnum;
    private String mb_id;
    private String updatetime;
    private Integer userhonorlevel;
    private String mb_hp;
    private String mb_profile_file;

    public TutuUsers() {
        this.isSpeaking = false;
        this.isVideoIn = false;
        this.isShareSrc = false;
        this.isShareMovie = false;
        this.hasGetInfo = false;
        this.isCreater = false;
    }

    public TutuUsers(Long id) {
        this.isSpeaking = false;
        this.isVideoIn = false;
        this.isShareSrc = false;
        this.isShareMovie = false;
        this.hasGetInfo = false;
        this.isCreater = false;
        this.id = id;
    }

    public TutuUsers(Long id, String uid, String nickname, String avatartime, Integer gender, String birthday, String province, String city, String area, String sign, String addtime, Integer age, String logintype, Integer isqqlogin, Integer userhonorlevel, String constellation, String locationstatus, Integer isbind_qq, Integer isbind_weibo, Integer isbind_phone, Integer likenum, String homecoverurl, Integer is_current, String cansendmessage, String errormsg, Integer topicnum, Integer favnum, Integer follownum, String updatetime, String status, Integer fansnum, String isauth, String authreason) {
        this.isSpeaking = false;
        this.isVideoIn = false;
        this.isShareSrc = false;
        this.isShareMovie = false;
        this.hasGetInfo = false;
        this.isCreater = false;
        this.id = id;
        this.mb_id = uid;
        this.mb_nick = nickname;
        this.avatartime = avatartime;
        this.gender = gender;
        this.birthday = birthday;
        this.province = province;
        this.city = city;
        this.area = area;
        this.sign = sign;
        this.addtime = addtime;
        this.age = age;
        this.logintype = logintype;
        this.isqqlogin = isqqlogin;
        this.userhonorlevel = userhonorlevel;
        this.constellation = constellation;
        this.locationstatus = locationstatus;
        this.isbind_qq = isbind_qq;
        this.isbind_weibo = isbind_weibo;
        this.isbind_phone = isbind_phone;
        this.likenum = likenum;
        this.homecoverurl = homecoverurl;
        this.is_current = is_current;
        this.cansendmessage = cansendmessage;
        this.errormsg = errormsg;
        this.topicnum = topicnum;
        this.favnum = favnum;
        this.follownum = follownum;
        this.updatetime = updatetime;
        this.status = status;
        this.fansnum = fansnum;
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
        return this.mb_id;
    }

    public void setUid(String uid) {
        this.mb_id = uid;
    }

    public String getNickname() {
        return this.mb_nick;
    }

    public void setNickname(String nickname) {
        this.mb_nick = nickname;
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

    public String getLogintype() {
        return this.logintype;
    }

    public void setLogintype(String logintype) {
        this.logintype = logintype;
    }

    public Integer getIsqqlogin() {
        return this.isqqlogin;
    }

    public void setIsqqlogin(Integer isqqlogin) {
        this.isqqlogin = isqqlogin;
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

    public String getLocationstatus() {
        return this.locationstatus;
    }

    public void setLocationstatus(String locationstatus) {
        this.locationstatus = locationstatus;
    }

    public Integer getIsbind_qq() {
        return this.isbind_qq;
    }

    public void setIsbind_qq(Integer isbind_qq) {
        this.isbind_qq = isbind_qq;
    }

    public Integer getIsbind_weibo() {
        return this.isbind_weibo;
    }

    public void setIsbind_weibo(Integer isbind_weibo) {
        this.isbind_weibo = isbind_weibo;
    }

    public Integer getIsbind_phone() {
        return this.isbind_phone;
    }

    public void setIsbind_phone(Integer isbind_phone) {
        this.isbind_phone = isbind_phone;
    }

    public Integer getLikenum() {
        return this.likenum;
    }

    public void setLikenum(Integer likenum) {
        this.likenum = likenum;
    }

    public String getHomecoverurl() {
        return this.homecoverurl;
    }

    public void setHomecoverurl(String homecoverurl) {
        this.homecoverurl = homecoverurl;
    }

    public Integer getIs_current() {
        return this.is_current;
    }

    public void setIs_current(Integer is_current) {
        this.is_current = is_current;
    }

    public String getCansendmessage() {
        return this.cansendmessage;
    }

    public void setCansendmessage(String cansendmessage) {
        this.cansendmessage = cansendmessage;
    }

    public String getErrormsg() {
        return this.errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public Integer getTopicnum() {
        return this.topicnum;
    }

    public void setTopicnum(Integer topicnum) {
        this.topicnum = topicnum;
    }

    public Integer getFavnum() {
        return this.favnum;
    }

    public void setFavnum(Integer favnum) {
        this.favnum = favnum;
    }

    public Integer getFollownum() {
        return this.follownum;
    }

    public void setFollownum(Integer follownum) {
        this.follownum = follownum;
    }

    public String getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFansnum() {
        return this.fansnum;
    }

    public void setFansnum(Integer fansnum) {
        this.fansnum = fansnum;
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

    public String getRelation() {
        return this.relation;
    }

    public String getDescinfo() {
        return this.descinfo;
    }

    public void setDescinfo(String descinfo) {
        this.descinfo = descinfo;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public int getIsblock() {
        return this.isblock;
    }

    public void setIsblock(int isblock) {
        this.isblock = isblock;
    }

    public int getTopicblock() {
        return this.topicblock;
    }

    public void setTopicblock(int topicblock) {
        this.topicblock = topicblock;
    }

    public List<TopicInfo> getTopiclist() {
        return this.topiclist;
    }

    public void setTopiclist(List<TopicInfo> topiclist) {
        this.topiclist = topiclist;
    }

    public List<TopicInfo> getFavlist() {
        return this.favlist;
    }

    public void setFavlist(List<TopicInfo> favlist) {
        this.favlist = favlist;
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

    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLasttime() {
        return this.lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
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

    public String getIsliked() {
        return this.isliked;
    }

    public void setIsliked(String isliked) {
        this.isliked = isliked;
    }

    public String getRemarkname() {
        return this.mb_name;
    }

    public void setRemarkname(String remarkname) {
        this.mb_name = remarkname;
    }

    public boolean isSpeaking() {
        return this.isSpeaking;
    }

    public void setSpeaking(boolean isSpeaking) {
        this.isSpeaking = isSpeaking;
    }

    public boolean isVideoIn() {
        return this.isVideoIn;
    }

    public void setVideoIn(boolean isVideoIn) {
        this.isVideoIn = isVideoIn;
    }

    public boolean isShareSrc() {
        return this.isShareSrc;
    }

    public void setShareSrc(boolean isShareSrc) {
        this.isShareSrc = isShareSrc;
    }

    public boolean isShareMovie() {
        return this.isShareMovie;
    }

    public void setShareMovie(boolean isShareMovie) {
        this.isShareMovie = isShareMovie;
    }

    public boolean isHasGetInfo() {
        return this.hasGetInfo;
    }

    public void setHasGetInfo(boolean hasGetInfo) {
        this.hasGetInfo = hasGetInfo;
    }

    public boolean isCreater() {
        return this.isCreater;
    }

    public void setCreater(boolean isCreater) {
        this.isCreater = isCreater;
    }

    public String getBeannum() {
        return this.beannum;
    }

    public void setBeannum(String beannum) {
        this.beannum = beannum;
    }

    public String getCoinnum() {
        return this.coinnum;
    }

    public void setCoinnum(String coinnum) {
        this.coinnum = coinnum;
    }

    public String getSendcoinnum() {
        return this.sendcoinnum;
    }

    public void setSendcoinnum(String sendcoinnum) {
        this.sendcoinnum = sendcoinnum;
    }

    public int getLiveroomnum() {
        return this.liveroomnum;
    }

    public void setLiveroomnum(int liveroomnum) {
        this.liveroomnum = liveroomnum;
    }

    public String getPhoneNumbe() {return this.mb_hp; }

    public void setPhoneNumber(String strPhoneNumber){ this.mb_hp = strPhoneNumber; }

    public String getProfileFile() { return this.mb_profile_file; }

    public void setProfileFile(String strProfileFile) {this.mb_profile_file = strProfileFile; }
}
