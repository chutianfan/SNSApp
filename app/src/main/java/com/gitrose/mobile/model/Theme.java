package com.gitrose.mobile.model;

import com.gitrose.greendao.TutuUsers;
import java.io.Serializable;
import java.util.List;

public class Theme implements Serializable {
    private static final long serialVersionUID = 1;
    private String addtime;
    private String avatartime;
    private String client;
    private String content;
    private String desc;
    private String emptycommenttxt;
    private String formattime;
    private int fromrepost;
    private String height;
    private List<Comment> hotcommentlist;
    private List<HuaTi> huatilist;
    private Long id;
    private String ids;
    private String idstext;
    private boolean isUserComment;
    private Integer is_del;
    private Integer isfav;
    private String isfollow;
    private String iskana;
    private int islike;
    private String joinusercount;
    private int likenum;
    private String localRequestId;
    private int localViews;
    private String locallisttype;
    private String localtopicid;
    private String location;
    private String morelink;
    private List<Comment> newcommentlist;
    private String nickname;
    private String poiid;
    private String poitext;
    private String remarkname;
    private int repostnum;
    private int showtype;
    private String size;
    private String smallcontent;
    private Operation specialdata;
    private Integer status;
    private Integer times;
    private String topiccount;
    private String topicid;
    private int totalcomment;
    private Integer type;
    private String uid;
    private String updatetime;
    private String usercount;
    private TutuUsers userinfo;
    private int userisrepost;
    private int videotimes;
    private String videourl;
    private Integer views;
    private String width;

    public Theme() {
        this.localViews = -1;
    }

    public String getIsfollow() {
        return this.isfollow;
    }

    public void setIsfollow(String isfollow) {
        this.isfollow = isfollow;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopicid() {
        return this.topicid;
    }

    public void setTopicid(String topicid) {
        this.topicid = topicid;
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

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLocaltopicid() {
        return this.localtopicid;
    }

    public void setLocaltopicid(String localtopicid) {
        this.localtopicid = localtopicid;
    }

    public String getAddtime() {
        return this.addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getVideourl() {
        return this.videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public Integer getTimes() {
        return this.times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getIsfav() {
        return this.isfav;
    }

    public void setIsfav(Integer isfav) {
        this.isfav = isfav;
    }

    public Integer getViews() {
        return this.views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocallisttype() {
        return this.locallisttype;
    }

    public void setLocallisttype(String locallisttype) {
        this.locallisttype = locallisttype;
    }

    public String getFormattime() {
        return this.formattime;
    }

    public void setFormattime(String formattime) {
        this.formattime = formattime;
    }

    public String getSmallcontent() {
        return this.smallcontent;
    }

    public void setSmallcontent(String smallcontent) {
        this.smallcontent = smallcontent;
    }

    public Integer getIs_del() {
        return this.is_del;
    }

    public void setIs_del(Integer is_del) {
        this.is_del = is_del;
    }

    public String getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getIslike() {
        return this.islike;
    }

    public void setIslike(int islike) {
        this.islike = islike;
    }

    public int getLikenum() {
        return this.likenum;
    }

    public void setLikenum(int likenum) {
        this.likenum = likenum;
    }

    public int getTotalcomment() {
        return this.totalcomment;
    }

    public void setTotalcomment(int totalcomment) {
        this.totalcomment = totalcomment;
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

    public String getEmptycommenttxt() {
        return this.emptycommenttxt;
    }

    public void setEmptycommenttxt(String emptycommenttxt) {
        this.emptycommenttxt = emptycommenttxt;
    }

    public boolean isUserComment() {
        return this.isUserComment;
    }

    public void setUserComment(boolean isUserComment) {
        this.isUserComment = isUserComment;
    }

    public String getLocalRequestId() {
        return this.localRequestId;
    }

    public void setLocalRequestId(String localRequestId) {
        this.localRequestId = localRequestId;
    }

    public int getShowtype() {
        return this.showtype;
    }

    public void setShowtype(int showtype) {
        this.showtype = showtype;
    }

    public Operation getSpecialdata() {
        return this.specialdata;
    }

    public void setSpecialdata(Operation specialdata) {
        this.specialdata = specialdata;
    }

    public int getVideotimes() {
        return this.videotimes;
    }

    public void setVideotimes(int videotimes) {
        this.videotimes = videotimes;
    }

    public List<Comment> getNewcommentlist() {
        return this.newcommentlist;
    }

    public void setNewcommentlist(List<Comment> newcommentlist) {
        this.newcommentlist = newcommentlist;
    }

    public List<Comment> getHotcommentlist() {
        return this.hotcommentlist;
    }

    public void setHotcommentlist(List<Comment> hotcommentlist) {
        this.hotcommentlist = hotcommentlist;
    }

    public int getLocalViews() {
        return this.localViews;
    }

    public void setLocalViews(int localViews) {
        this.localViews = localViews;
    }

    public String getRemarkname() {
        return this.remarkname;
    }

    public void setRemarkname(String remarkname) {
        this.remarkname = remarkname;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPoiid() {
        return this.poiid;
    }

    public void setPoiid(String poiid) {
        this.poiid = poiid;
    }

    public String getPoitext() {
        return this.poitext;
    }

    public void setPoitext(String poitext) {
        this.poitext = poitext;
    }

    public String getIskana() {
        return this.iskana;
    }

    public void setIskana(String iskana) {
        this.iskana = iskana;
    }

    public String getClient() {
        return this.client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getMorelink() {
        return this.morelink;
    }

    public void setMorelink(String morelink) {
        this.morelink = morelink;
    }

    public List<HuaTi> getHuatilist() {
        return this.huatilist;
    }

    public void setHuatilist(List<HuaTi> huatilist) {
        this.huatilist = huatilist;
    }

    public TutuUsers getUserinfo() {
        return this.userinfo;
    }

    public void setUserinfo(TutuUsers userinfo) {
        this.userinfo = userinfo;
    }

    public int getRepostnum() {
        return this.repostnum;
    }

    public void setRepostnum(int repostnum) {
        this.repostnum = repostnum;
    }

    public int getUserisrepost() {
        return this.userisrepost;
    }

    public void setUserisrepost(int userisrepost) {
        this.userisrepost = userisrepost;
    }

    public int getFromrepost() {
        return this.fromrepost;
    }

    public void setFromrepost(int fromrepost) {
        this.fromrepost = fromrepost;
    }

    public String getTopiccount() {
        return this.topiccount;
    }

    public void setTopiccount(String topiccount) {
        this.topiccount = topiccount;
    }

    public String getUsercount() {
        return this.usercount;
    }

    public void setUsercount(String usercount) {
        this.usercount = usercount;
    }

    public String getJoinusercount() {
        return this.joinusercount;
    }

    public void setJoinusercount(String joinusercount) {
        this.joinusercount = joinusercount;
    }

    public String getIds() {
        return this.ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getIdstext() {
        return this.idstext;
    }

    public void setIdstext(String idstext) {
        this.idstext = idstext;
    }
}
