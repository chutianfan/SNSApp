package com.gitrose.mobile.download.video;

public interface DownLoadClient<T> {
    void cancel(T t);

    void cancelAll();

    void clearAll();

    void clearExpire(int i, int i2);

    void downAll();

    void download(T t);

    int getCount(String str);

    T getDownloadBeanByIndex(String str);

    int getDownloadCount(String str);

    T getHistoryBeanByIndex(String str);

    int getHistoryCount(String str);

    void init();

    boolean isInited();

    void pause(T t);

    void pauseAll();
}
