package com.gitrose.mobile.model;

public class Audio {
    String artist;
    int duration;
    boolean selected;
    int startTime;
    String title;
    String url;

    public Audio(String title, String artist, String url, int duration, boolean selected) {
        this.title = title;
        this.artist = artist;
        this.url = url;
        this.duration = duration;
        this.selected = selected;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
