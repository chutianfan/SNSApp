package com.gitrose.mobile.model;

public class ImageFileStatus {
    private String filePath;
    private int selectedCount;

    public ImageFileStatus(String filePath, int count) {
        this.selectedCount = 0;
        this.filePath = filePath;
        this.selectedCount = count;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getSelectedCount() {
        return this.selectedCount;
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount = selectedCount;
    }
}
