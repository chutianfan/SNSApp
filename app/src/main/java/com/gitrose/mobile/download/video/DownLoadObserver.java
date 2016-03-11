package com.gitrose.mobile.download.video;

import com.gitrose.mobile.model.VideoDownLoad;

public interface DownLoadObserver<T extends VideoDownLoad> {
    VideoDownloader getDownloader();

    void notifyData(T t);
}
