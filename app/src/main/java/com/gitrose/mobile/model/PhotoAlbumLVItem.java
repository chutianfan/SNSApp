package com.gitrose.mobile.model;

public class PhotoAlbumLVItem {
    private int fileCount;
    private String firstImagePath;
    private String pathName;

    public PhotoAlbumLVItem(String pathName, int fileCount, String firstImagePath) {
        this.pathName = pathName;
        this.fileCount = fileCount;
        this.firstImagePath = firstImagePath;
    }

    public String getPathName() {
        return this.pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public int getFileCount() {
        return this.fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public String getFirstImagePath() {
        return this.firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String toString() {
        return "SelectImgGVItem{pathName='" + this.pathName + '\'' + ", fileCount=" + this.fileCount + ", firstImagePath='" + this.firstImagePath + '\'' + '}';
    }
}
