package com.gitrose.mobile.download.video;

import android.util.SparseArray;
import com.gitrose.mobile.model.VideoDownLoad;

public class DownloaderFactory {
    private static Class<? extends DownLoadRunnable> DEFAULT = null;
    public static final int First_Version = 6;
    private static DownloaderFactory INSTANCE;
    private SparseArray<Class<? extends DownLoadRunnable>> downloadClazz;

    static {
        DEFAULT = null;
        INSTANCE = null;
    }

    private DownloaderFactory() {
        this.downloadClazz = new SparseArray();
        this.downloadClazz.put(First_Version, DownLoadRunnableImpl.class);
        DEFAULT = DownLoadRunnableImpl.class;
        for (int i = 0; i < this.downloadClazz.size(); i++) {
            try {
                Class.forName(((Class) this.downloadClazz.get(this.downloadClazz.keyAt(i))).getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized DownloaderFactory getInstance() {
        DownloaderFactory downloaderFactory;
        synchronized (DownloaderFactory.class) {
            if (INSTANCE == null) {
                INSTANCE = new DownloaderFactory();
            }
            downloaderFactory = INSTANCE;
        }
        return downloaderFactory;
    }

    public DownLoadRunnable getDownloader(int version, VideoDownLoad bean) {
        try {
            Class<? extends DownLoadRunnable> clazz = (Class) this.downloadClazz.get(version);
            if (clazz == null) {
                clazz = DEFAULT;
            }
            return (DownLoadRunnable) clazz.getConstructors()[0].newInstance(new Object[]{bean});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public DownLoadRunnable getDownloader(int i, VideoDownLoad videodownload)
//    {
//        Class class1;
//        Class class2;
//        try
//        {
//            class2 = (Class)downloadClazz.get(i);
//        }
//        // Misplaced declaration of an exception variable
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            return null;
//        }
//        class1 = class2;
//        if (class2 != null)
//        {
//            break MISSING_BLOCK_LABEL_25;
//        }
//        class1 = DEFAULT;
//        videodownload = (DownLoadRunnable)class1.getConstructors()[0].newInstance(new Object[] {
//                videodownload
//        });
//        return videodownload;
//    }
}
