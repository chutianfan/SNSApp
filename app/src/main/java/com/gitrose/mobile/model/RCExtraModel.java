package com.gitrose.mobile.model;

//import io.rong.message.BuildConfig;

public class RCExtraModel {
    private String buttonText;
    private String buttonlink;
    private String contentLink;
    private String counter;
    private String isblock;
    private String ptId;

//    public RCExtraModel() {
//        this.ptId = BuildConfig.FLAVOR;
//    }

    public String getIsblock() {
        return this.isblock;
    }

    public void setIsblock(String isblock) {
        this.isblock = isblock;
    }

    public String getButtonText() {
        return this.buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getButtonlink() {
        return this.buttonlink;
    }

    public void setButtonlink(String buttonlink) {
        this.buttonlink = buttonlink;
    }

    public String getContentLink() {
        return this.contentLink;
    }

    public void setContentLink(String contentLink) {
        this.contentLink = contentLink;
    }

    public String getPtId() {
        return this.ptId;
    }

    public void setPtId(String ptId) {
        this.ptId = ptId;
    }

    public String getCounter() {
        return this.counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }
}
