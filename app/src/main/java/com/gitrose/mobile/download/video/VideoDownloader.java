package com.gitrose.mobile.download.video;

//import com.sina.weibo.sdk.exception.WeiboAuthException;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.ITopicDisplay;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.VideoDownLoad;
import com.gitrose.mobile.utils.FileUtil;
import com.gitrose.mobile.utils.VideoUtils;
import java.util.HashMap;
import java.util.List;

public class VideoDownloader {
    private int currentPos;
    private HashMap<String, VideoDownLoad> mDownloadMap;
    private ITopicDisplay mITopicDisplay;
    private List<TopicInfo> mTopicInfos;
    private String savePath;
    private TutuUsers userInfo;

    /* renamed from: com.gitrose.mobile.download.video.VideoDownloader.1 */
    class C08911 extends Thread {
        C08911() {
        }

        public void run() {
            int pos = 0;
            if (VideoDownloader.this.mITopicDisplay != null) {
                pos = VideoDownloader.this.mITopicDisplay.getDownloadPos();
            }
            if (pos != VideoDownloader.this.currentPos) {
                VideoDownloader.this.pauseVideo();
                VideoDownloader.this.currentPos = pos;
                VideoDownloader.this.resumeVideo();
            }
        }
    }

    public VideoDownloader(ITopicDisplay iTopicDisplay, List<TopicInfo> topicInfos) {
        this.currentPos = -1;
        this.mTopicInfos = topicInfos;
        this.mDownloadMap = new HashMap();
        this.userInfo = MyApplication.getInstance().getUserinfo();
        this.mITopicDisplay = iTopicDisplay;
        this.savePath = FileUtil.getSaveDir(MyApplication.getInstance().getApplicationContext());
    }

    public void setTopicInfos(List<TopicInfo> topicInfos) {
        this.mTopicInfos = topicInfos;
    }

    public void downoadVieo() {
        new C08911().start();
    }

    public void pauseVideo() {
        selectedDownload(this.currentPos, true);
    }

    public void resumeVideo() {
        selectedDownload(this.currentPos, false);
    }

    private void selectedDownload(int centerPos, boolean isPause) {
        String uid = "";
        if (!(this.userInfo == null || this.userInfo.getUid() == null)) {
            uid = this.userInfo.getUid();
        }
        int perDownloadCount = 0;
        if (Constant.netWorkStatus == 1) {
            perDownloadCount = 1;
        }
        int startPos = centerPos - perDownloadCount;
        while (startPos <= centerPos + perDownloadCount) {
            if (this.mTopicInfos.size() > startPos && startPos >= 0) {
                TopicInfo topicInfo = (TopicInfo) this.mTopicInfos.get(startPos);
                String videoUrl = topicInfo.getVideourl();
                if (topicInfo.getType().intValue() == 5 && videoUrl != null) {
                    String fileName = VideoUtils.generateByUrl(videoUrl);
                    if (!isPause) {
                        VideoDownLoad videoDownLoad;
                        if (this.mDownloadMap.containsKey(videoUrl)) {
                            videoDownLoad = this.mDownloadMap.get(videoUrl);
                        } else {
                            videoDownLoad = new VideoDownLoad(uid, videoUrl, this.savePath, fileName);
                            this.mDownloadMap.put(videoUrl, videoDownLoad);
                        }
                        DownLoadClientImpl.getInstance().download(videoDownLoad);
                    } else if (this.mDownloadMap.containsKey(videoUrl)) {
                        DownLoadClientImpl.getInstance().pause((VideoDownLoad) this.mDownloadMap.get(videoUrl));
                    }
                }
            }
            startPos++;
        }
        DownLoadClientImpl.getInstance().clearExpire(50, 25);
    }
}
