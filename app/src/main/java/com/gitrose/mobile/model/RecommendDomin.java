package com.gitrose.mobile.model;

import qalsdk.BaseConstants;

public class RecommendDomin {
    int age;
    String content;
    int header;
    int honorlevel;
    String imgURL;
    String isauth;
    String poiID;
    boolean selected;
    int sex;
    String themeID;
    String title;
    int type;
    String userID;

    public String getIsauth() {
        return this.isauth;
    }

    public void setIsauth(String isauth) {
        this.isauth = isauth;
    }

    public int getHonorlevel() {
        return this.honorlevel;
    }

    public void setHonorlevel(int honorlevel) {
        this.honorlevel = honorlevel;
    }

    public RecommendDomin(String themeID, String title, String content, String imgURL, int type, int header, boolean selected) {
        this.honorlevel = 0;
        this.isauth = BaseConstants.ah;
        if (type == 1) {
            this.themeID = themeID;
        } else if (type == 2) {
            this.poiID = themeID;
        }
        this.title = title;
        this.content = content;
        this.imgURL = imgURL;
        this.type = type;
        this.header = header;
        this.selected = selected;
    }

    public RecommendDomin(String userID, String title, String content, String imgURL, int type, int header, boolean selected, int sex, int age, int honorlevel, String isauth) {
        this.honorlevel = 0;
        this.isauth = BaseConstants.ah;
        this.userID = userID;
        this.title = title;
        this.content = content;
        this.imgURL = imgURL;
        this.type = type;
        this.header = header;
        this.selected = selected;
        this.sex = sex;
        this.age = age;
        this.honorlevel = honorlevel;
        this.isauth = isauth;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getThemeID() {
        return this.themeID;
    }

    public void setThemeID(String themeID) {
        this.themeID = themeID;
    }

    public String getPoiID() {
        return this.poiID;
    }

    public void setPoiID(String poiID) {
        this.poiID = poiID;
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

    public String getImgURL() {
        return this.imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHeader() {
        return this.header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
