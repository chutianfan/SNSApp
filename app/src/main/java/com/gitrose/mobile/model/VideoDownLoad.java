package com.gitrose.mobile.model;

import java.io.File;
import org.litepal.crud.DataSupport;

public class VideoDownLoad extends DataSupport {

    private long addTime;
    private long curPos;
    private int downType;
    private long endPos;
    private int id;
    private long loadPos;
    private String name;
    protected String realPath;
    private String savepath;
    private long startPos;
    private int status;
    protected String tempPath;
    private String type;
    private String uid;
    private String url;
    private String videoId;

    public VideoDownLoad(String uid, String url, String videoPath, String fileName) {
        this(uid, fileName, fileName, url, videoPath, 0, -1, 2, "mp4", System.currentTimeMillis());
    }

    public VideoDownLoad(String uid, String videoId, String name, String url, String saveDir, long startPos, long endPos, int status, String type, long addTime) {
        this.uid = uid;
        this.videoId = videoId;
        this.name = name;
        this.url = url;
        this.savepath = saveDir;
        this.startPos = startPos;
        this.endPos = endPos;
        this.status = status;
        this.type = type;
        this.addTime = addTime;
    }

    public int getDownType() {
        return this.downType;
    }

    public void setDownType(int downType) {
        this.downType = downType;
    }

    public String getRealPath() {
        return this.savepath + File.separator + this.name + "." + this.type;
    }

    public String getTempPath() {
        return this.savepath + File.separator + this.name + ".temp";
    }

    public String getType() {
        return this.type;
    }

    public long getAddTime() {
        return this.addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCurPos() {
        return this.curPos;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCurPos(long curPos) {
        this.curPos = curPos;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getStartPos() {
        return this.startPos;
    }

    public void setStartPos(long startPos) {
        this.startPos = startPos;
    }

    public long getEndPos() {
        return this.endPos;
    }

    public void setEndPos(long endPos) {
        this.endPos = endPos;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public long getLoadPos() {
        return this.loadPos;
    }

    public void setLoadPos(long loadPos) {
        this.loadPos = loadPos;
    }

    public String getSavepath() {
        return this.savepath;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public int getProgress() {
        if (this.endPos != 0) {
            return (int) ((this.startPos * 100) / this.endPos);
        }
        return 0;
    }
}
