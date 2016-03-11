package com.gitrose.mobile.model;

//import com.google.gson.Gson;

public class HistoryContent {
    String content;
    String duration;
    HistoryExtraContent extra;
    String imageUri;
    String title;

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImageUri() {
        return this.imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public HistoryExtraContent getExtra() {
        return this.extra;
    }

    public void setExtra(HistoryExtraContent extra) {
        this.extra = extra;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
//        Object content = new HistoryContent();
//        content.setImageUri(getImageUri());
//        content.setTitle(getTitle());
//        content.setContent(getContent());
//        content.setExtra(getExtra());
//        return new Gson().toJson(content);

        return "";
    }

    public String beJsonExtra() {
//        Object hec = new HistoryExtraContent();
//        hec.setButtonlink(getExtra().getButtonlink());
//        hec.setContentLink(getExtra().getContentLink());
//        hec.setButtonText(getExtra().getButtonText());
//        return new Gson().toJson(hec);

        return "";
    }

//    public String beJsonExtraButtonL() {
//        return new Gson().toJson(getExtra().getButtonlink());
//    }
}
