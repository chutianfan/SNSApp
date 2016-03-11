package com.gitrose.mobile.download.video;

import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
//import com.sina.weibo.sdk.openapi.InviteAPI;
import com.gitrose.mobile.HomeFragment;
//import com.gitrose.mobile.PersonalNewActivity;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.model.VideoDownLoad;
import com.gitrose.mobile.utils.FileUtil;
import com.gitrose.mobile.utils.VideoUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.litepal.crud.DataSupport;
import qalsdk.BaseConstants;

public class DownLoadClientImpl extends TutuObservable<DownLoadObserver<VideoDownLoad>> implements DownLoadClient<VideoDownLoad> {
    private static final int INITED = -2;
    private static final int INITING = -1;
    private static final int UNINIT = 0;
    private static final int UPDATE_TIME = 100;
    private static DownLoadClientImpl impl;
    private HashMap<String, VideoDownLoad> downloadArray;
    private HashMap<String, VideoDownLoad> historyArray;
    private AtomicInteger initStatus;
    private Handler mHandler;
    private final Map<VideoDownLoad, RequestHandle> mHashMap;
    private long preTime;

    /* renamed from: com.gitrose.mobile.download.video.DownLoadClientImpl.1 */
    class C08871 extends Handler {
        C08871(Looper $anonymous0) {
            super($anonymous0);
        }

        public void handleMessage(Message msg) {
            DownLoadClientImpl.this.handleMsg(msg);
        }
    }

    /* renamed from: com.gitrose.mobile.download.video.DownLoadClientImpl.2 */
    class C08882 extends Thread {
        C08882() {
        }

        public void run() {
            int i = DownLoadClientImpl.UNINIT;
            DataSupport.deleteAll(VideoDownLoad.class, new String[DownLoadClientImpl.UNINIT]);
            File vidoPathfile = new File(FileUtil.getSaveDir(MyApplication.getInstance().getApplicationContext()));
            if (vidoPathfile.exists()) {
                File[] listFile = vidoPathfile.listFiles();
                int length = listFile.length;
                while (i < length) {
                    File file = listFile[i];
                    if (!file.isDirectory()) {
                        file.delete();
                    }
                    i++;
                }
            }
        }
    }

    /* renamed from: com.gitrose.mobile.download.video.DownLoadClientImpl.3 */
    class C08893 extends Thread {
        private final /* synthetic */ int val$offsetSize;

        C08893(int i) {
            this.val$offsetSize = i;
        }

        public void run() {
//            Cursor cursor = DataSupport.findBySQL("select url from VideoDownLoad order by addTime desc limit (select count(url) from VideoDownLoad) offset " + this.val$offsetSize);
//            while (cursor.moveToNext()) {
//                String videoUrl = cursor.getString(cursor.getColumnIndex(InviteAPI.KEY_URL));
//                if (videoUrl != null) {
//                    DataSupport.deleteAll(VideoDownLoad.class, "url='" + videoUrl + "'");
//                    File file = new File(VideoUtils.getVideoAbsoluteFileNameByUrl(MyApplication.getInstance().getApplicationContext(), videoUrl));
//                    if (file.exists()) {
//                        file.delete();
//                    }
//                }
//            }
        }
    }

    private DownLoadClientImpl() {
        this.initStatus = new AtomicInteger(UNINIT);
        this.preTime = 0;
        this.mHandler = new C08871(Looper.getMainLooper());
        this.mHashMap = new WeakHashMap();
        this.downloadArray = new HashMap();
        this.historyArray = new HashMap();
        init();
    }

    public static DownLoadClientImpl getInstance() {
        if (impl != null) {
            return impl;
        }
        DownLoadClientImpl downLoadClientImpl = new DownLoadClientImpl();
        impl = downLoadClientImpl;
        return downLoadClientImpl;
    }

    public void download(VideoDownLoad video) {
        if (video != null) {
            RequestHandle handler;
            if (this.downloadArray.containsKey(video.getUrl())) {
                video = (VideoDownLoad) this.downloadArray.get(video.getUrl());
                handler = (RequestHandle) this.mHashMap.get(video);
                if (handler == null || handler.isFinished()) {
                    start(video);
                    return;
                }
                return;
            }
            handler = (RequestHandle) this.mHashMap.get(video);
            if (handler != null && !handler.isFinished()) {
                handler.cancel(true);
                this.mHashMap.remove(video);
                start(video);
            } else if (this.historyArray.containsKey(video.getUrl())) {
                VideoDownLoad temp = (VideoDownLoad) this.historyArray.get(video.getUrl());
                if (new File(temp.getTempPath()).exists()) {
                    this.historyArray.remove(video.getUrl());
                    start(temp);
                } else if (!new File(video.getRealPath()).exists()) {
                    this.historyArray.remove(video.getUrl());
                    start(temp);
                }
            } else {
                start(video);
            }
        }
    }

    private void start(VideoDownLoad video) {
        DownLoadRunnable<VideoDownLoad> request = DownloaderFactory.getInstance().getDownloader(6, video);
        if (request == null) {
            notifyData(video, 7);
        } else {
            notifyData(video, 3);
//            if (DataSupport.findBySQL("select * from VideoDownLoad where videoId ='" + video.getVideoId() + "'").moveToFirst()) {
//                video.updateAll("videoId =?", video.getVideoId());
//            } else {
//                video.save();
//            }
            ThreadPoolFactory.createSingleInstance().submit(request);
            this.mHashMap.put(video, new RequestHandle(request));
        }
        checkRemove();
    }

    private void checkRemove() {
        Iterator<Entry<VideoDownLoad, RequestHandle>> iterator = this.mHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            if (((RequestHandle) ((Entry) iterator.next()).getValue()).shouldBeGarbageCollected()) {
                iterator.remove();
            }
        }
    }

    public boolean isInited() {
        return this.initStatus.get() == INITED;
    }

    public void init() {
        if (!isInited()) {
            String videoPath = FileUtil.getSaveDir(MyApplication.getInstance().getApplicationContext());
            if (!(videoPath == null || BaseConstants.ah.equals(videoPath))) {
                File file = new File(videoPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            this.initStatus.set(INITING);
//            List<VideoDownLoad> mList = DataSupport.where("uid = ?", MyApplication.getInstance().login_uid).find(VideoDownLoad.class);
//            if (mList != null && mList.size() > 0) {
//                for (VideoDownLoad videoBean : mList) {
//                    if (videoBean.getDownType() == 0) {
//                        this.downloadArray.put(videoBean.getUrl(), videoBean);
//                    } else {
//                        try {
//                            this.historyArray.put(videoBean.getUrl(), videoBean);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
            this.initStatus.set(INITED);
        }
    }

    protected boolean abortRequest(VideoDownLoad file) {
        RequestHandle request = (RequestHandle) this.mHashMap.get(file);
        this.mHashMap.remove(file);
        if (request != null) {
            return request.cancel(true);
        }
        return false;
    }

    public void pause(VideoDownLoad file) {
        RequestHandle request = (RequestHandle) this.mHashMap.get(file);
        this.mHashMap.remove(file);
        if (request != null) {
            request.pause();
            notifyData(file, 4);
        }
    }

    public void pauseAll() {
        for (int i = UNINIT; i < this.downloadArray.size(); i++) {
            for (String key : this.downloadArray.keySet()) {
                pause((VideoDownLoad) this.downloadArray.get(key));
            }
        }
    }

    public void cancel(VideoDownLoad file) {
        if (file != null) {
            abortRequest(file);
            String url = file.getUrl();
            if (this.downloadArray.containsKey(url)) {
                this.downloadArray.remove(url);
            } else {
                this.historyArray.remove(url);
            }
            deleteFile(file);
        }
    }

    private void deleteFile(VideoDownLoad file) {
        File tempFile = new File(file.getTempPath());
        File realFile = new File(file.getRealPath());
        if (tempFile.exists()) {
            tempFile.delete();
        }
        if (realFile.exists()) {
            realFile.delete();
        }
    }

    public void cancelAll() {
        if (this.downloadArray != null && this.downloadArray.size() > 0) {
//            for (String key : this.downloadArray.keySet()) {
//                DataSupport.deleteAll(VideoDownLoad.class, "videoId= ? ", ((VideoDownLoad) this.downloadArray.get(key)).getVideoId());
//                abortRequest(t);
//            }
            this.downloadArray.clear();
        }
    }

    public void downAll() {
        if (this.downloadArray != null && this.downloadArray.size() > 0) {
            for (String key : this.downloadArray.keySet()) {
                download((VideoDownLoad) this.downloadArray.get(key));
            }
        }
    }

    public int getHistoryCount(String uid) {
        return this.historyArray.size();
    }

    public int getDownloadCount(String uid) {
        return this.downloadArray.size();
    }

    public int getCount(String uid) {
//        return DataSupport.count(VideoDownLoad.class);
        return  0;
    }

    public VideoDownLoad getDownloadBeanByIndex(String url) {
        return (VideoDownLoad) this.downloadArray.get(url);
    }

    public VideoDownLoad getHistoryBeanByIndex(String url) {
        return (VideoDownLoad) this.historyArray.get(url);
    }

    public void notifyData(VideoDownLoad video, int state) {
        long time = System.currentTimeMillis() - this.preTime;
        if (state == 1) {
            video.setStatus(state);
            if (time > 100) {
                this.mHandler.obtainMessage(state, video).sendToTarget();
                this.preTime = System.currentTimeMillis();
            }
        } else if (state != video.getStatus()) {
            video.setStatus(state);
            if (Looper.myLooper() != null) {
                handleMsg(this.mHandler.obtainMessage(state, video));
            } else {
                this.mHandler.obtainMessage(state, video).sendToTarget();
            }
        }
    }

    private void notifyData(VideoDownLoad t) {
        Iterator it = this.mObservers.iterator();
        while (it.hasNext()) {
            ((DownLoadObserver) it.next()).notifyData(t);
        }
    }

    private void handleMsg(Message msg) {
        VideoDownLoad video = (VideoDownLoad) msg.obj;
        String url = video.getUrl();
        boolean pos = this.downloadArray.containsKey(url);
        switch (msg.what) {
//            case PersonalNewActivity.FRAGMENT_FOURE /*3*/:
//                video.setDownType(UNINIT);
//                this.downloadArray.put(video.getUrl(), video);
//                break;
            case HomeFragment.ADD_COMMENT_RESULT /*9*/:
                video.setDownType(1);
                if (pos) {
                    this.downloadArray.remove(url);
                    this.historyArray.put(url, video);
                    break;
                }
                break;
        }
        notifyData(video);
    }

    public static void clearStance() {
        impl = null;
    }

    public void clearAll() {
        new C08882().start();
    }

    public int getDownloadSize() {
        int totalLength = UNINIT;
        File vidoPathfile = new File(FileUtil.getSaveDir(MyApplication.getInstance().getApplicationContext()));
        if (vidoPathfile.exists()) {
            File[] listFile = vidoPathfile.listFiles();
            int length = listFile.length;
            for (int i = UNINIT; i < length; i++) {
                File file = listFile[i];
                if (file.isFile()) {
                    totalLength = (int) (((long) totalLength) + file.length());
                }
            }
        }
        return totalLength;
    }

    public void clearExpire(int beignSize, int offsetSize) {
//        if (DataSupport.count(VideoDownLoad.class) > beignSize) {
//            new C08893(offsetSize).start();
//        }
    }
}
