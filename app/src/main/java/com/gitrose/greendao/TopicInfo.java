package com.gitrose.greendao;

import com.gitrose.mobile.model.Comment;
import com.gitrose.mobile.model.HomeHuatiPoi;
import com.gitrose.mobile.model.HuaTi;
import com.gitrose.mobile.model.Operation;
import com.gitrose.mobile.model.RepostUserInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TopicInfo implements Serializable {
    private static final long serialVersionUID = 3703324341299497161L;
    private String addtime;
    private List<Comment> commentlist;
    private String content;
    private String createtime;
    private String desc;
    private String emptycommenttxt;
    private String formattime;
    private String fromrepost;
    private String height;
    private List<HuaTi> huatilist;
    private Long id;
    private String idtext;
    private boolean isCamera;
    private boolean isUserComment;
    private Integer is_del;
    private Integer isfav;
    private String iskana;
    private int islike;
    private String isredpacket;
    private int likenum;
    private String livechatgroupid;
    private String localRequestId;
    private int localViews;
    private String locallisttype;
    private String localtopicid;
    private String location;
    private String morelink;
    private int newcount;
    private String ossPicUri;
    private String ossVideoUri;
    private String poiid;
    private String poitext;
    private int repostnum;
    private String reposttopicid;
    private RepostUserInfo repostuserinfo;
    private String roottopicid;
    private int showtype;
    private String size;
    private String smallcontent;
    private Operation specialdata;
    private Integer status;
    private Integer times;
    private String topicid;
    private ArrayList<HomeHuatiPoi> topiclist;
    private int totalcomment;
    private Integer type;
    private String uid;
    private String updatetime;
    private int userhonorlevel;
    private TutuUsers userinfo;
    private int userisrepost;
    private int videotimes;
    private String videourl;
    private Integer views;
    private String width;

    public TopicInfo() {
        this.localViews = -1;
    }

    public TopicInfo(Long id) {
        this.localViews = -1;
        this.id = id;
    }

    public TopicInfo(Long id, String topicid, String uid, String content, String height, String width, Integer type, Integer status, String localtopicid, String addtime, String videourl, Integer times, String size, Integer isfav, Integer views, String location, String locallisttype, String formattime, String smallcontent, Integer is_del, String updatetime, String reposttopicid, String roottopicid, String createtime, String fromrepost, String isredpacket) {
        this.localViews = -1;
        this.id = id;
        this.topicid = topicid;
        this.uid = uid;
        this.content = content;
        this.height = height;
        this.width = width;
        this.type = type;
        this.status = status;
        this.localtopicid = localtopicid;
        this.addtime = addtime;
        this.videourl = videourl;
        this.times = times;
        this.size = size;
        this.isfav = isfav;
        this.views = views;
        this.location = location;
        this.locallisttype = locallisttype;
        this.formattime = formattime;
        this.smallcontent = smallcontent;
        this.is_del = is_del;
        this.updatetime = updatetime;
        this.reposttopicid = reposttopicid;
        this.roottopicid = roottopicid;
        this.createtime = createtime;
        this.fromrepost = fromrepost;
        this.isredpacket = isredpacket;
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

    public String getReposttopicid() {
        return this.reposttopicid;
    }

    public void setReposttopicid(String reposttopicid) {
        this.reposttopicid = reposttopicid;
    }

    public String getRoottopicid() {
        return this.roottopicid;
    }

    public void setRoottopicid(String roottopicid) {
        this.roottopicid = roottopicid;
    }

    public String getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getFromrepost() {
        return this.fromrepost;
    }

    public void setFromrepost(String fromrepost) {
        this.fromrepost = fromrepost;
    }

    public String getIsredpacket() {
        return this.isredpacket;
    }

    public void setIsredpacket(String isredpacket) {
        this.isredpacket = isredpacket;
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

    public int getLocalViews() {
        return this.localViews;
    }

    public void setLocalViews(int localViews) {
        this.localViews = localViews;
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

    public RepostUserInfo getRepostuserinfo() {
        return this.repostuserinfo;
    }

    public void setRepostuserinfo(RepostUserInfo repostuserinfo) {
        this.repostuserinfo = repostuserinfo;
    }

    public String getIdtext() {
        return this.idtext;
    }

    public void setIdtext(String idtext) {
        this.idtext = idtext;
    }

    public int getNewcount() {
        return this.newcount;
    }

    public void setNewcount(int newcount) {
        this.newcount = newcount;
    }

    public ArrayList<HomeHuatiPoi> getTopiclist() {
        return this.topiclist;
    }

    public void setTopiclist(ArrayList<HomeHuatiPoi> topiclist) {
        this.topiclist = topiclist;
    }

    public int getUserhonorlevel() {
        return this.userhonorlevel;
    }

    public void setUserhonorlevel(int userhonorlevel) {
        this.userhonorlevel = userhonorlevel;
    }

    public boolean isCamera() {
        return this.isCamera;
    }

    public void setCamera(boolean isCamera) {
        this.isCamera = isCamera;
    }

    public List<Comment> getCommentlist() {
        return this.commentlist;
    }

    public void setCommentlist(List<Comment> commentlist) {
        this.commentlist = commentlist;
    }

    public String getOssPicUri() {
        return this.ossPicUri;
    }

    public void setOssPicUri(String ossPicUri) {
        this.ossPicUri = ossPicUri;
    }

    public String getOssVideoUri() {
        return this.ossVideoUri;
    }

    public void setOssVideoUri(String ossVideoUri) {
        this.ossVideoUri = ossVideoUri;
    }

    public String getLivechatgroupid() {
        return this.livechatgroupid;
    }

    public void setLivechatgroupid(String livechatgroupid) {
        this.livechatgroupid = livechatgroupid;
    }
}
