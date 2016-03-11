package com.gitrose.mobile.model;

public class HistoryExtraContent {
    String buttonText;
    String buttonlink;
    String contentLink;

    public String getButtonlink() {
        return this.buttonlink;
    }

    public void setButtonlink(String buttonlink) {
        this.buttonlink = buttonlink;
    }

    public String getButtonText() {
        return this.buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getContentLink() {
        return this.contentLink;
    }

    public void setContentLink(String contentLink) {
        this.contentLink = contentLink;
    }

    public String toString() {
        String extracontentString = "{";
        if (getContentLink() != null) {
            extracontentString = new StringBuilder(String.valueOf(extracontentString)).append("\"contentLink\":\"").append(getContentLink()).append("\"").toString();
        }
        if (getButtonlink() != null) {
            extracontentString = new StringBuilder(String.valueOf(extracontentString)).append(",\"buttonlink\":\"").append(getButtonlink()).append("\"").toString();
        }
        if (getButtonText() != null) {
            extracontentString = new StringBuilder(String.valueOf(extracontentString)).append(",\"buttonText\":\"").append(getButtonText()).append("\"").toString();
        }
        return new StringBuilder(String.valueOf(extracontentString)).append("}").toString();
    }
}
