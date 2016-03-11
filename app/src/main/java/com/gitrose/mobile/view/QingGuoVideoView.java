package com.gitrose.mobile.view;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.support.v4.media.TransportMediator;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
//import com.umeng.common.net.NotificationUtils;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
//import shouji.gexing.framework.utils.DebugUtils;

public class QingGuoVideoView extends SurfaceView implements MediaPlayerControl {
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    public static final String TAG = "GEXING_VIDEO";
    private FileDescriptor fd;
    private FileInputStream fis;
    private OnBufferingUpdateListener mBufferingUpdateListener;
    private boolean mCanPause;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;
    private OnCompletionListener mCompletionListener;
    private Context mContext;
    private int mCurrentBufferPercentage;
    private int mCurrentState;
    private OnErrorListener mErrorListener;
    private File mFile;
    private Map<String, String> mHeaders;
    private MediaController mMediaController;
    private MediaPlayer mMediaPlayer;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private OnPreparedListener mOnPreparedListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    OnPreparedListener mPreparedListener;
    Callback mSHCallback;
    private int mSeekWhenPrepared;
    OnVideoSizeChangedListener mSizeChangedListener;
    private int mSurfaceHeight;
    private SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private int mTargetState;
    private Uri mUri;
    private int mVideoHeight;
    private int mVideoWidth;
    private int offset;
    private PlayerStatusChangedListener statusChangedListener;
    private int videoType;

    /* renamed from: com.gitrose.mobile.view.QingGuoVideoView.1 */
    class C09431 implements OnVideoSizeChangedListener {
        C09431() {
        }

        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            QingGuoVideoView.this.mVideoWidth = mp.getVideoWidth();
            QingGuoVideoView.this.mVideoHeight = mp.getVideoHeight();
            if (QingGuoVideoView.this.mVideoWidth != 0 && QingGuoVideoView.this.mVideoHeight != 0) {
                QingGuoVideoView.this.getHolder().setFixedSize(QingGuoVideoView.this.mVideoWidth, QingGuoVideoView.this.mVideoHeight);
                QingGuoVideoView.this.requestLayout();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.view.QingGuoVideoView.2 */
    class C09442 implements OnPreparedListener {
        C09442() {
        }

        public void onPrepared(MediaPlayer mp) {
            QingGuoVideoView.this.mCurrentState = QingGuoVideoView.STATE_PREPARED;
            QingGuoVideoView qingGuoVideoView = QingGuoVideoView.this;
            QingGuoVideoView qingGuoVideoView2 = QingGuoVideoView.this;
            QingGuoVideoView.this.mCanSeekForward = true;
            qingGuoVideoView2.mCanSeekBack = true;
            qingGuoVideoView.mCanPause = true;
            if (QingGuoVideoView.this.mOnPreparedListener != null) {
                QingGuoVideoView.this.mOnPreparedListener.onPrepared(QingGuoVideoView.this.mMediaPlayer);
            }
            if (QingGuoVideoView.this.mMediaController != null) {
                QingGuoVideoView.this.mMediaController.setEnabled(true);
            }
            QingGuoVideoView.this.mVideoWidth = mp.getVideoWidth();
            QingGuoVideoView.this.mVideoHeight = mp.getVideoHeight();
            int seekToPosition = QingGuoVideoView.this.mSeekWhenPrepared;
            if (seekToPosition != 0) {
                QingGuoVideoView.this.seekTo(seekToPosition);
            }
            if (QingGuoVideoView.this.mVideoWidth != 0 && QingGuoVideoView.this.mVideoHeight != 0) {
                QingGuoVideoView.this.getHolder().setFixedSize(QingGuoVideoView.this.mVideoWidth, QingGuoVideoView.this.mVideoHeight);
                if (QingGuoVideoView.this.mSurfaceWidth != QingGuoVideoView.this.mVideoWidth || QingGuoVideoView.this.mSurfaceHeight != QingGuoVideoView.this.mVideoHeight) {
                    return;
                }
                if (QingGuoVideoView.this.mTargetState == QingGuoVideoView.STATE_PLAYING) {
                    QingGuoVideoView.this.start();
                    if (QingGuoVideoView.this.mMediaController != null) {
                        QingGuoVideoView.this.mMediaController.show();
                    }
                } else if (!QingGuoVideoView.this.isPlaying()) {
                    if ((seekToPosition != 0 || QingGuoVideoView.this.getCurrentPosition() > 0) && QingGuoVideoView.this.mMediaController != null) {
                        QingGuoVideoView.this.mMediaController.show(QingGuoVideoView.STATE_IDLE);
                    }
                }
            } else if (QingGuoVideoView.this.mTargetState == QingGuoVideoView.STATE_PLAYING) {
                QingGuoVideoView.this.start();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.view.QingGuoVideoView.3 */
    class C09453 implements OnCompletionListener {
        C09453() {
        }

        public void onCompletion(MediaPlayer mp) {
            QingGuoVideoView.this.mCurrentState = QingGuoVideoView.STATE_PLAYBACK_COMPLETED;
            QingGuoVideoView.this.mTargetState = QingGuoVideoView.STATE_PLAYBACK_COMPLETED;
            if (QingGuoVideoView.this.mMediaController != null) {
                QingGuoVideoView.this.mMediaController.hide();
            }
            if (QingGuoVideoView.this.mOnCompletionListener != null) {
                QingGuoVideoView.this.mOnCompletionListener.onCompletion(QingGuoVideoView.this.mMediaPlayer);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.view.QingGuoVideoView.4 */
    class C09464 implements OnErrorListener {
        C09464() {
        }

        public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
            Log.d(QingGuoVideoView.TAG, "Error: " + framework_err + "," + impl_err);
            QingGuoVideoView.this.mCurrentState = QingGuoVideoView.STATE_ERROR;
            QingGuoVideoView.this.mTargetState = QingGuoVideoView.STATE_ERROR;
            if (QingGuoVideoView.this.mMediaController != null) {
                QingGuoVideoView.this.mMediaController.hide();
            }
            if ((QingGuoVideoView.this.mOnErrorListener == null || !QingGuoVideoView.this.mOnErrorListener.onError(QingGuoVideoView.this.mMediaPlayer, framework_err, impl_err)) && QingGuoVideoView.this.getWindowToken() != null) {
                QingGuoVideoView.this.mContext.getResources();
            }
            return true;
        }
    }

    /* renamed from: com.gitrose.mobile.view.QingGuoVideoView.5 */
    class C09475 implements OnBufferingUpdateListener {
        C09475() {
        }

        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            QingGuoVideoView.this.mCurrentBufferPercentage = percent;
            if (QingGuoVideoView.this.statusChangedListener != null) {
                QingGuoVideoView.this.statusChangedListener.onBufferUpdate(percent);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.view.QingGuoVideoView.6 */
    class C09486 implements Callback {
        C09486() {
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            QingGuoVideoView.this.mSurfaceWidth = w;
            QingGuoVideoView.this.mSurfaceHeight = h;
            boolean isValidState;
            if (QingGuoVideoView.this.mTargetState == QingGuoVideoView.STATE_PLAYING) {
                isValidState = true;
            } else {
                isValidState = false;
            }
            boolean hasValidSize;
            if (QingGuoVideoView.this.mVideoWidth == w && QingGuoVideoView.this.mVideoHeight == h) {
                hasValidSize = true;
            } else {
                hasValidSize = false;
            }
            if (QingGuoVideoView.this.mMediaPlayer != null && isValidState && hasValidSize) {
                if (QingGuoVideoView.this.mSeekWhenPrepared != 0) {
                    QingGuoVideoView.this.seekTo(QingGuoVideoView.this.mSeekWhenPrepared);
                }
                QingGuoVideoView.this.start();
            }
        }

        public void surfaceCreated(SurfaceHolder holder) {
            QingGuoVideoView.this.mSurfaceHolder = holder;
            QingGuoVideoView.this.openVideo();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            QingGuoVideoView.this.mSurfaceHolder = null;
            if (QingGuoVideoView.this.mMediaController != null) {
                QingGuoVideoView.this.mMediaController.hide();
            }
            QingGuoVideoView.this.release(true);
        }
    }

    public interface PlayerStatusChangedListener {
        void onBufferUpdate(int i);

        void onPaused();

        void onResumed();

        void onStarted();
    }

    public QingGuoVideoView(Context context) {
        super(context);
        this.videoType = STATE_ERROR;
        this.mCurrentState = STATE_IDLE;
        this.mTargetState = STATE_IDLE;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mSizeChangedListener = new C09431();
        this.mPreparedListener = new C09442();
        this.mCompletionListener = new C09453();
        this.mErrorListener = new C09464();
        this.mBufferingUpdateListener = new C09475();
        this.mSHCallback = new C09486();
        this.mContext = context;
        initVideoView();
    }

    public QingGuoVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.videoType = STATE_ERROR;
        this.mCurrentState = STATE_IDLE;
        this.mTargetState = STATE_IDLE;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mSizeChangedListener = new C09431();
        this.mPreparedListener = new C09442();
        this.mCompletionListener = new C09453();
        this.mErrorListener = new C09464();
        this.mBufferingUpdateListener = new C09475();
        this.mSHCallback = new C09486();
        this.mContext = context;
        initVideoView();
    }

    public QingGuoVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.videoType = STATE_ERROR;
        this.mCurrentState = STATE_IDLE;
        this.mTargetState = STATE_IDLE;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mSizeChangedListener = new C09431();
        this.mPreparedListener = new C09442();
        this.mCompletionListener = new C09453();
        this.mErrorListener = new C09464();
        this.mBufferingUpdateListener = new C09475();
        this.mSHCallback = new C09486();
        this.mContext = context;
        initVideoView();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.videoType == 0) {
            int width = getDefaultSize(this.mVideoWidth, widthMeasureSpec);
            int height = getDefaultSize(this.mVideoHeight, heightMeasureSpec);
            if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
                int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
                int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
                int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
                int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
                if (widthSpecMode == 1073741824 && heightSpecMode == 1073741824) {
                    width = widthSpecSize;
                    height = heightSpecSize;
                    if (this.mVideoWidth * height < this.mVideoHeight * width) {
                        width = (this.mVideoWidth * height) / this.mVideoHeight;
                    } else if (this.mVideoWidth * height > this.mVideoHeight * width) {
                        height = (this.mVideoHeight * width) / this.mVideoWidth;
                    }
                } else if (widthSpecMode == 1073741824) {
                    width = widthSpecSize;
                    height = (this.mVideoHeight * width) / this.mVideoWidth;
                    if (heightSpecMode == ExploreByTouchHelper.INVALID_ID && height > heightSpecSize) {
                        height = heightSpecSize;
                    }
                } else if (heightSpecMode == 1073741824) {
                    height = heightSpecSize;
                    width = (this.mVideoWidth * height) / this.mVideoHeight;
                    if (widthSpecMode == ExploreByTouchHelper.INVALID_ID && width > widthSpecSize) {
                        width = widthSpecSize;
                    }
                } else {
                    width = this.mVideoWidth;
                    height = this.mVideoHeight;
                    if (heightSpecMode == ExploreByTouchHelper.INVALID_ID && height > heightSpecSize) {
                        height = heightSpecSize;
                        width = (this.mVideoWidth * height) / this.mVideoHeight;
                    }
                    if (widthSpecMode == ExploreByTouchHelper.INVALID_ID && width > widthSpecSize) {
                        width = widthSpecSize;
                        height = (this.mVideoHeight * width) / this.mVideoWidth;
                    }
                }
            }
            setMeasuredDimension(width, height);
            return;
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    public int resolveAdjustedSize(int desiredSize, int measureSpec) {
        int result = desiredSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case ExploreByTouchHelper.INVALID_ID /*-2147483648*/:
                return Math.min(desiredSize, specSize);
            case STATE_IDLE /*0*/:
                return desiredSize;
            case 1073741824:
                return specSize;
            default:
                return result;
        }
    }

    private void initVideoView() {
        this.mVideoWidth = STATE_IDLE;
        this.mVideoHeight = STATE_IDLE;
        getHolder().addCallback(this.mSHCallback);
        getHolder().setType(STATE_PLAYING);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.mCurrentState = STATE_IDLE;
        this.mTargetState = STATE_IDLE;
    }

    public void setVideoURI(Uri uri) {
        setVideoURI(uri, null);
    }

    public void setVideoURI(Uri uri, int offset) {
        this.offset = offset;
        setVideoURI(uri, null);
    }

    public void setVideoURI(Uri uri, Map<String, String> headers) {
        this.mUri = uri;
        this.mHeaders = headers;
        this.mSeekWhenPrepared = STATE_IDLE;
        openVideo();
        requestLayout();
        invalidate();
    }

    public void stopPlayback() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = STATE_IDLE;
            this.mTargetState = STATE_IDLE;
        }
    }

    private void openVideo() {
        if (this.mUri != null && this.mSurfaceHolder != null) {
            Intent i = new Intent("com.android.music.musicservicecommand");
            i.putExtra("command", "pause");
            this.mContext.sendBroadcast(i);
            release(false);
            try {
                this.mMediaPlayer = new MediaPlayer();
                this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
                this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
                this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
                this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
                this.mMediaPlayer.setOnInfoListener(this.mOnInfoListener);
                this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
                this.mMediaPlayer.setOnSeekCompleteListener(this.mOnSeekCompleteListener);
                this.mCurrentBufferPercentage = STATE_IDLE;
                if (this.offset != 0) {
                    this.mFile = new File(this.mUri.getPath());
                    this.fis = new FileInputStream(this.mFile);
                    this.fd = this.fis.getFD();
                    this.mMediaPlayer.setDataSource(this.fd, (long) this.offset, this.mFile.length() - ((long) this.offset));
                } else {
                    this.mMediaPlayer.setDataSource(this.mContext, this.mUri);
                }
                this.mMediaPlayer.setDisplay(this.mSurfaceHolder);
                this.mMediaPlayer.setAudioStreamType(STATE_PLAYING);
                this.mMediaPlayer.setScreenOnWhilePlaying(true);
                this.mMediaPlayer.prepareAsync();
                this.mCurrentState = STATE_PREPARING;
                attachMediaController();
            } catch (IOException ex) {
                Log.w(TAG, "Unable to open content: " + this.mUri, ex);
                this.mCurrentState = STATE_ERROR;
                this.mTargetState = STATE_ERROR;
                this.mErrorListener.onError(this.mMediaPlayer, STATE_PREPARING, STATE_IDLE);
            } catch (IllegalArgumentException ex2) {
                Log.w(TAG, "Unable to open content: " + this.mUri, ex2);
                this.mCurrentState = STATE_ERROR;
                this.mTargetState = STATE_ERROR;
                this.mErrorListener.onError(this.mMediaPlayer, STATE_PREPARING, STATE_IDLE);
            }
        }
    }

    public void setMediaController(MediaController controller) {
        if (this.mMediaController != null) {
            this.mMediaController.hide();
        }
        this.mMediaController = controller;
        attachMediaController();
    }

    private void attachMediaController() {
        if (this.mMediaPlayer != null && this.mMediaController != null) {
            View anchorView;
            this.mMediaController.setMediaPlayer(this);
            if (getParent() instanceof View) {
                anchorView = (View) getParent();
            } else {
                anchorView = this;
            }
            this.mMediaController.setAnchorView(anchorView);
            this.mMediaController.setEnabled(isInPlaybackState());
        }
    }

    public void setOnPreparedListener(OnPreparedListener l) {
        this.mOnPreparedListener = l;
    }

    public void setOnCompletionListener(OnCompletionListener l) {
        this.mOnCompletionListener = l;
    }

    public void setOnErrorListener(OnErrorListener l) {
        this.mOnErrorListener = l;
    }

    public void setOnInfoListener(OnInfoListener l) {
        this.mOnInfoListener = l;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener listener) {
        this.mOnSeekCompleteListener = listener;
    }

    private void release(boolean cleartargetstate) {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = STATE_IDLE;
            if (cleartargetstate) {
                this.mTargetState = STATE_IDLE;
            }
            if (this.fis != null) {
                try {
                    this.fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisiblity();
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent ev) {
        if (isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisiblity();
        }
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isKeyCodeSupported;
        if (keyCode == STATE_PAUSED || keyCode == 24 || keyCode == 25 || keyCode == 164 || keyCode == 82 || keyCode == STATE_PLAYBACK_COMPLETED || keyCode == 6) {
            isKeyCodeSupported = false;
        } else {
            isKeyCodeSupported = true;
        }
        if (isInPlaybackState() && isKeyCodeSupported && this.mMediaController != null) {
            if (keyCode == 79 || keyCode == 85) {
                if (this.mMediaPlayer.isPlaying()) {
                    pause();
                    this.mMediaController.show();
                    return true;
                }
                start();
                this.mMediaController.hide();
                return true;
            } else if (keyCode == TransportMediator.KEYCODE_MEDIA_PLAY) {
                if (this.mMediaPlayer.isPlaying()) {
                    return true;
                }
                start();
                this.mMediaController.hide();
                return true;
            } else if (keyCode != 86 && keyCode != TransportMediator.KEYCODE_MEDIA_PAUSE) {
                toggleMediaControlsVisiblity();
            } else if (!this.mMediaPlayer.isPlaying()) {
                return true;
            } else {
                pause();
                this.mMediaController.show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void toggleMediaControlsVisiblity() {
        if (this.mMediaController.isShowing()) {
            this.mMediaController.hide();
        } else {
            this.mMediaController.show();
        }
    }

    public void start() {
        if (isInPlaybackState()) {
//            DebugUtils.debug("mSurfaceHolder = holder" + (this.mSurfaceHolder != null));
            this.mMediaPlayer.start();
            this.mCurrentState = STATE_PLAYING;
        }
        this.mTargetState = STATE_PLAYING;
        if (this.statusChangedListener != null) {
            this.statusChangedListener.onStarted();
        }
//        DebugUtils.debug("qingguo_video:start");
    }

    public void pause() {
        if (isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            this.mCurrentState = STATE_PAUSED;
        }
        this.mTargetState = STATE_PAUSED;
        if (this.statusChangedListener != null) {
            this.statusChangedListener.onPaused();
        }
//        DebugUtils.debug("qingguo_video:pause");
    }

    public void suspend() {
        release(false);
    }

    public void resume() {
        if (isInPlaybackState() && !this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.start();
            this.mCurrentState = STATE_PLAYING;
        }
        if (this.statusChangedListener != null) {
            this.statusChangedListener.onResumed();
        }
//        DebugUtils.debug("qingguo_video:resume");
    }

    public int getDuration() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getDuration();
        }
        return STATE_ERROR;
    }

    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        return STATE_IDLE;
    }

    public void seekTo(int msec) {
        if (isInPlaybackState()) {
            this.mMediaPlayer.seekTo(msec);
            this.mSeekWhenPrepared = STATE_IDLE;
            return;
        }
        this.mSeekWhenPrepared = msec;
    }

    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    public int getBufferPercentage() {
        if (this.mMediaPlayer != null) {
            return this.mCurrentBufferPercentage;
        }
        return STATE_IDLE;
    }

    private boolean isInPlaybackState() {
        return (this.mMediaPlayer == null || this.mCurrentState == STATE_ERROR || this.mCurrentState == 0 || this.mCurrentState == STATE_PREPARING) ? false : true;
    }

    public boolean canPause() {
        return this.mCanPause;
    }

    public boolean canSeekBackward() {
        return this.mCanSeekBack;
    }

    public boolean canSeekForward() {
        return this.mCanSeekForward;
    }

    public void setStatusChangedListener(PlayerStatusChangedListener listener) {
        this.statusChangedListener = listener;
    }

    public MediaPlayer getMediaPlay() {
        return this.mMediaPlayer;
    }

    public void setVideoType(int type) {
        this.videoType = type;
    }

    public int getAudioSessionId() {
        return STATE_IDLE;
    }
}
