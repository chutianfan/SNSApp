package com.gitrose.mobile.model;

import java.io.Serializable;

public class UploadTopic implements Serializable {
    public static final int PIC_TYPE = 1;
    public static final int THEME_TYPE = 9;
    public static final int VIDEO_TYPE = 5;
    private static final long serialVersionUID = 1;
    private String commentContent;
    private String commentLocationX;
    private String commentLocationY;
    private String commentTxtframe;
    private String isName;
    private String ossPicUri;
    private String ossVideoUri;
    private String picFile;
    private int pic_height;
    private int pic_width;
    private String pointId;
    private String pointText;
    private int sync;
    private String topicDesc;
    private int type;
    private String videoFile;
    private int videoTime;

    public UploadTopic(int type, String picFile, String videoFile, int videoTime, String commentContent, String commentLocationX, String commentLocationY, String commentTxtframe, String topicDesc, String pointId, String pointText, String isName) {
        this.type = PIC_TYPE;
        this.type = type;
        this.picFile = picFile;
        this.videoFile = videoFile;
        this.videoTime = videoTime;
        this.commentContent = commentContent;
        this.commentLocationX = commentLocationX;
        this.commentLocationY = commentLocationY;
        this.commentTxtframe = commentTxtframe;
        this.topicDesc = topicDesc;
        this.pointId = pointId;
        this.pointText = pointText;
        this.isName = isName;
    }

    public UploadTopic() {
        this.type = PIC_TYPE;
    }

    public UploadTopic(int type, String picFile, String commentContent, String commentLocationX, String commentLocationY, String commentTxtframe, String topicDesc, String pointId, String pointText, String isName) {
        this.type = PIC_TYPE;
        this.type = type;
        this.picFile = picFile;
        this.commentContent = commentContent;
        this.commentLocationX = commentLocationX;
        this.commentLocationY = commentLocationY;
        this.commentTxtframe = commentTxtframe;
        this.topicDesc = topicDesc;
        this.pointId = pointId;
        this.pointText = pointText;
        this.isName = isName;
    }

    public UploadTopic(int type, String picFile, String videoFile, int videoTime, String topicDesc, String pointId, String pointText, String isName) {
        this.type = PIC_TYPE;
        this.type = type;
        this.picFile = picFile;
        this.videoFile = videoFile;
        this.videoTime = videoTime;
        this.topicDesc = topicDesc;
        this.pointId = pointId;
        this.pointText = pointText;
        this.isName = isName;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPicFile() {
        return this.picFile;
    }

    public void setPicFile(String picFile) {
        this.picFile = picFile;
    }

    public String getVideoFile() {
        return this.videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    public int getVideoTime() {
        return this.videoTime;
    }

    public void setVideoTime(int videoTime) {
        this.videoTime = videoTime;
    }

    public String getCommentContent() {
        return this.commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentLocationX() {
        return this.commentLocationX;
    }

    public void setCommentLocationX(String commentLocationX) {
        this.commentLocationX = commentLocationX;
    }

    public String getCommentLocationY() {
        return this.commentLocationY;
    }

    public void setCommentLocationY(String commentLocationY) {
        this.commentLocationY = commentLocationY;
    }

    public String getCommentTxtframe() {
        return this.commentTxtframe;
    }

    public void setCommentTxtframe(String commentTxtframe) {
        this.commentTxtframe = commentTxtframe;
    }

    public String getTopicDesc() {
        return this.topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }

    public String getPointId() {
        return this.pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getPointText() {
        return this.pointText;
    }

    public void setPointText(String pointText) {
        this.pointText = pointText;
    }

    public String getIsName() {
        return this.isName;
    }

    public void setIsName(String isName) {
        this.isName = isName;
    }

    public int getSync() {
        return this.sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
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

    public int getPic_width() {
        return this.pic_width;
    }

    public void setPic_width(int pic_width) {
        this.pic_width = pic_width;
    }

    public int getPic_height() {
        return this.pic_height;
    }

    public void setPic_height(int pic_height) {
        this.pic_height = pic_height;
    }
}
