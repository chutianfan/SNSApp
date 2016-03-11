package com.gitrose.mobile.base;

import android.media.MediaPlayer;
import android.view.View;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.mobile.view.AutoCoverFlowHelper;

public interface ITopicDisplay {
    int getBlockTopic();

    View getConvertView();

    int getDownloadPos();

    AutoCoverFlowHelper getFlowHelper();

    MediaPlayer getMediaPlayer();

    boolean isPause();

    void onDelCommentListener(View view, int i, String str, int i2, String str2, boolean z, TopicInfo topicInfo);

    void showLayerMask();
}
