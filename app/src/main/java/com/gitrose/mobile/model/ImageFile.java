package com.gitrose.mobile.model;

public class ImageFile {
    private String filePath;
    private boolean selected;

    public ImageFile(String filePath, boolean selectd) {
        this.filePath = filePath;
        this.selected = selectd;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
