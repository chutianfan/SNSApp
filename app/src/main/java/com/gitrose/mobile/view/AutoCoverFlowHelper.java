package com.gitrose.mobile.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import com.gitrose.mobile.R;
import com.gitrose.mobile.base.ITopicDisplay;
import shouji.gexing.framework.utils.UiUtils;

public class AutoCoverFlowHelper {
    public static final int DOWNLOAD_VIDEO_DELAY_TIME = 600;
    public static final int FILTE_PARAMS = 4;
    public static final int FIRST_DELAY_TIME = 1500;
    public static final int MOVE_TIME_INTERVAL = 1500;
    public static final int NEXT = 0;
    private static final int PER = 1;
    public static final int RAPID_DELAY_TIME = 500;
    public static final int REFLOW_WHAT = 1;
    public static final int RESUME_DELAY_TIME = 2500;
    private int filteIndex;
    private Fragment fragment;
    private boolean isPersonal;
    private boolean isTopicDetail;
    private Context mContext;
    private int mCurrentPosition;
    private DownloadVideoRunnable mDownloadRunnable;
    private FlowRunnable mFlowRunnable;
    private int mHalfScreenHeight;
    private Handler mHandler;
    public int mHeaderCount;
    private ListView mListView;
    private int[] mLocations;
    private OnFlowTouchListener mOnFlowTouchListener;
    private OnScrollListPosListener mOnScrollListPosListener;
    private boolean newVerison;

    private class DownloadVideoRunnable implements Runnable {
        private CommentImageView commentImageView;

        private DownloadVideoRunnable() {
        }

        public void setCommentImageView(CommentImageView commentImageView, int time) {
            this.commentImageView = commentImageView;
            AutoCoverFlowHelper.this.mHandler.postDelayed(this, (long) time);
        }

        public void run() {
            if (this.commentImageView != null) {
                this.commentImageView.notifyDownloadVideo();
            }
        }
    }

    private class FlowRunnable implements Runnable {
        private int direction;
        private AutoCoverFlow fancyCoverFlow;

        private FlowRunnable() {
        }

        public void setFancyCoverFlow(AutoCoverFlow fancyCoverFlow, int time) {
            this.direction = AutoCoverFlowHelper.NEXT;
            this.fancyCoverFlow = fancyCoverFlow;
            this.fancyCoverFlow.setOnTouchListener(AutoCoverFlowHelper.this.mOnFlowTouchListener);
            AutoCoverFlowHelper.this.mHandler.postDelayed(this, (long) time);
        }

        public void run() {
            if (this.fancyCoverFlow != null) {
                autoMoveFlow();
                AutoCoverFlowHelper.this.mHandler.postDelayed(this, 1500);
            }
        }

        private void autoMoveFlow() {
            int index = this.fancyCoverFlow.getSelectedItemPosition();
            if (this.direction == 0) {
                if (index == this.fancyCoverFlow.getCount() - 2) {
                    this.direction = AutoCoverFlowHelper.REFLOW_WHAT;
                } else {
                    this.fancyCoverFlow.next();
                }
            } else if (this.direction == AutoCoverFlowHelper.REFLOW_WHAT) {
                AutoCoverFlowHelper.this.goneCommnentView(this.fancyCoverFlow);
            }
        }
    }

    class OnFlowTouchListener implements OnTouchListener {
        OnFlowTouchListener() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case AutoCoverFlowHelper.NEXT /*0*/:
                    AutoCoverFlowHelper.this.mHandler.removeCallbacks(AutoCoverFlowHelper.this.mFlowRunnable);
                    AutoCoverFlowHelper.this.mFlowRunnable.setFancyCoverFlow((AutoCoverFlow) v, AutoCoverFlowHelper.RESUME_DELAY_TIME);
                    break;
            }
            return false;
        }
    }

    public interface OnScrollListPosListener {
        void onScrollPos(ListView listView, int i);
    }

    public AutoCoverFlowHelper(Context context, ListView listView, boolean isPersonal) {
        boolean z = false;
        this.mCurrentPosition = -1;
        this.mHalfScreenHeight = 640;
        this.mLocations = new int[2];
        this.mHeaderCount = NEXT;
        this.isPersonal = false;
        this.isPersonal = isPersonal;
        this.mContext = context;
        this.mListView = listView;
        this.mOnFlowTouchListener = new OnFlowTouchListener();
        this.mFlowRunnable = new FlowRunnable();
        this.mDownloadRunnable = new DownloadVideoRunnable();
        this.mHalfScreenHeight = UiUtils.getInstance(context).getmScreenHeight() / 2;
        this.mHandler = new Handler();
        if (this.mListView != null) {
            this.mHeaderCount = this.mListView.getHeaderViewsCount();
        }
        if (VERSION.SDK_INT >= 14) {
            z = true;
        }
        this.newVerison = z;
    }

    public AutoCoverFlowHelper(Context context, ListView listView, Fragment fragment) {
        boolean z = false;
        this.mCurrentPosition = -1;
        this.mHalfScreenHeight = 640;
        this.mLocations = new int[2];
        this.mHeaderCount = NEXT;
        this.isPersonal = false;
        this.fragment = fragment;
        this.mContext = context;
        this.mListView = listView;
        this.mOnFlowTouchListener = new OnFlowTouchListener();
        this.mFlowRunnable = new FlowRunnable();
        this.mDownloadRunnable = new DownloadVideoRunnable();
        if (context != null) {
            this.mHalfScreenHeight = UiUtils.getInstance(context).getmScreenHeight() / 2;
        }
        this.mHandler = new Handler();
        if (this.mListView != null) {
            this.mHeaderCount = this.mListView.getHeaderViewsCount();
        }
        if (VERSION.SDK_INT >= 14) {
            z = true;
        }
        this.newVerison = z;
    }

    public AutoCoverFlowHelper(Context context, boolean isPersonal) {
        this(context, null, isPersonal);
        this.isTopicDetail = true;
    }

    public void startAutoFlow(int visibleItemCount) {
        if (this.filteIndex == FILTE_PARAMS) {
            this.filteIndex = NEXT;
            startAutoFlow(getScreenListPos(visibleItemCount), false, false);
            return;
        }
        this.filteIndex += REFLOW_WHAT;
    }

    public synchronized void startAutoFlow(int position, boolean isImageLoadFinish, boolean isResume) {
        if (position != this.mCurrentPosition || isImageLoadFinish) {
            if (this.mOnScrollListPosListener != null) {
                this.mOnScrollListPosListener.onScrollPos(this.mListView, position);
            }
            View convertView = getCovertView(position);
            if (convertView != null) {
                CommentImageView commentView = (CommentImageView) convertView.findViewById(R.id.toastView_topic);
                this.mHandler.removeCallbacks(this.mFlowRunnable);
                this.mHandler.removeCallbacks(this.mDownloadRunnable);
                if (!isResume) {
                    resetMediaPlay();
                }
                if (commentView != null) {
                    this.mCurrentPosition = position;
                    if (commentView.mImgLoadStatus == REFLOW_WHAT) {
                        if (commentView.isHasVideo && this.newVerison) {
                            this.mDownloadRunnable.setCommentImageView(commentView, DOWNLOAD_VIDEO_DELAY_TIME);
                        } else
                        {
                            AutoCoverFlow fancyCoverFlow = (AutoCoverFlow) convertView.findViewById(R.id.fancyCoverFlow);
                            if (fancyCoverFlow.getCount() > 2) {
                                this.mFlowRunnable.setFancyCoverFlow(fancyCoverFlow, MOVE_TIME_INTERVAL);
                            }
                        }
                    }
                }
            }
        }
    }

    private View getCovertView(int position) {
        if (!this.isTopicDetail) {
            return this.mListView.getChildAt(position - this.mListView.getFirstVisiblePosition());
        }
        if (this.fragment != null) {
            return ((ITopicDisplay) this.fragment).getConvertView();
        }
        return ((ITopicDisplay) this.mContext).getConvertView();
    }

    public void videoAutoFlow(int position) {
        videoAutoFlow(position, NEXT);
    }

    public void resetCurrentPosition(int pos) {
        this.mCurrentPosition = pos;
    }

    public void videoAutoFlow(int position, int selectionPos) {
        View convertView = getCovertView(position);
        if (convertView != null) {
            this.mHandler.removeCallbacks(this.mFlowRunnable);
            this.mCurrentPosition = position;
            CommentImageView commentView = (CommentImageView) convertView.findViewById(R.id.toastView_topic);
            AutoCoverFlow fancyCoverFlow = (AutoCoverFlow) convertView.findViewById(R.id.fancyCoverFlow);
            if (commentView != null && fancyCoverFlow != null && commentView.isOpenComment && fancyCoverFlow.getCount() > 2) {
                this.mFlowRunnable.setFancyCoverFlow(fancyCoverFlow, MOVE_TIME_INTERVAL);
            }
        }
    }

    public void resumeAutoFlow() {
        this.mHandler.postDelayed(this.mFlowRunnable, 1500);
    }

    public void resumeShortAutoFlow() {
        this.mHandler.postDelayed(this.mFlowRunnable, 500);
    }

    public void stopAutoFlow() {
        this.mHandler.removeCallbacks(this.mFlowRunnable);
    }

    public synchronized void startAvatarFlow(int position) {
        View convertView = getCovertView(position);
        if (convertView != null) {
            CommentImageView commentView = (CommentImageView) convertView.findViewById(R.id.toastView_topic);
            this.mHandler.removeCallbacks(this.mFlowRunnable);
            if (commentView != null) {
                this.mCurrentPosition = position;
                if (commentView.mImgLoadStatus == REFLOW_WHAT) {
                    AutoCoverFlow fancyCoverFlow = (AutoCoverFlow) convertView.findViewById(R.id.fancyCoverFlow);
                    if (fancyCoverFlow.getCount() > 2) {
                        this.mFlowRunnable.setFancyCoverFlow(fancyCoverFlow, MOVE_TIME_INTERVAL);
                    }
                }
            }
        }
    }

    private void goneCommnentView(AutoCoverFlow fancyCoverFlow) {
        ((CommentImageView) ((View) fancyCoverFlow.getParent().getParent()).findViewById(R.id.toastView_topic)).hideComment();
    }

    public void StopCommentShow() {
        stopAutoFlow();
        CommentImageView commentImageView = getCurrentCommentView();
        if (commentImageView != null) {
            commentImageView.isOpenComment = false;
            commentImageView.hideComment();
        }
    }

    public void resumeCommentShow() {
        resumeAutoFlow();
        CommentImageView commentImageView = getCurrentCommentView();
        if (commentImageView != null) {
            commentImageView.isOpenComment = true;
            commentImageView.showComment();
        }
    }

    public void updateVideoProgress(int progress, int status, String fileName) {
        CommentImageView commentImageView = getCurrentCommentView();
        if (commentImageView != null) {
            commentImageView.updateVideoProgress(progress, status, fileName);
        }
    }

    public void pauseVideoPlay() {
        CommentImageView commentImageView = getCurrentCommentView();
        if (commentImageView != null) {
            commentImageView.pauseVideoView();
        } else {
            resetMediaPlay();
        }
        stopAutoFlow();
    }

    public void startVideoPlay() {
        CommentImageView commentImageView = getCurrentCommentView();
        if (commentImageView != null) {
            commentImageView.startVideoView();
        } else {
            resetMediaPlay();
        }
        resumeAutoFlow();
    }

    public void pauseVideoPlay(boolean isError) {
        CommentImageView commentImageView = getCurrentCommentView();
        if (commentImageView != null) {
            commentImageView.pauseVideoView();
            commentImageView.showCover();
            return;
        }
        resetMediaPlay();
    }

    final CommentImageView getCurrentCommentView() {
        View convertView = getCovertView(this.mCurrentPosition);
        if (convertView != null) {
            return (CommentImageView) convertView.findViewById(R.id.toastView_topic);
        }
        return null;
    }

    final int getScreenListPos(int visibleItemCount) {
        int firstVisibleItem = this.mListView.getFirstVisiblePosition();
        if (this.isPersonal) {
            if (visibleItemCount == 3) {
                return -1;
            }
            if (visibleItemCount == REFLOW_WHAT) {
                return firstVisibleItem;
            }
            if (visibleItemCount % 2 == REFLOW_WHAT) {
                return firstVisibleItem + this.mListView.getHeaderViewsCount();
            }
            this.mListView.getChildAt(REFLOW_WHAT).getLocationInWindow(this.mLocations);
            if (this.mLocations[REFLOW_WHAT] < this.mHalfScreenHeight) {
                return firstVisibleItem + REFLOW_WHAT;
            }
            return firstVisibleItem;
        } else if (visibleItemCount == REFLOW_WHAT) {
            return firstVisibleItem;
        } else {
            if (visibleItemCount % 2 == REFLOW_WHAT) {
                return firstVisibleItem + this.mListView.getHeaderViewsCount();
            }
            this.mListView.getChildAt(REFLOW_WHAT).getLocationInWindow(this.mLocations);
            if (this.mLocations[REFLOW_WHAT] < this.mHalfScreenHeight) {
                return firstVisibleItem + REFLOW_WHAT;
            }
            return firstVisibleItem;
        }
    }

    public void setOnScrollListPosListener(OnScrollListPosListener onScrollListPosListener) {
        this.mOnScrollListPosListener = onScrollListPosListener;
    }

    private void startMediaPlay() {
        MediaPlayer mediaPlayer;
        if (this.fragment != null) {
            mediaPlayer = ((ITopicDisplay) this.fragment).getMediaPlayer();
        } else {
            mediaPlayer = ((ITopicDisplay) this.mContext).getMediaPlayer();
        }
        try {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetMediaPlay() {
        MediaPlayer mediaPlayer;
        if (this.fragment != null) {
            mediaPlayer = ((ITopicDisplay) this.fragment).getMediaPlayer();
        } else {
            mediaPlayer = ((ITopicDisplay) this.mContext).getMediaPlayer();
        }
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mediaPlayer.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void releaseMediaPlay() {
        MediaPlayer mediaPlayer;
        if (this.fragment != null) {
            mediaPlayer = ((ITopicDisplay) this.fragment).getMediaPlayer();
        } else {
            mediaPlayer = ((ITopicDisplay) this.mContext).getMediaPlayer();
        }
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
            mediaPlayer.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
