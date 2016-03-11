package com.gitrose.mobile.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View.MeasureSpec;
import com.android.volley.DefaultRetryPolicy;
import java.io.IOException;

@SuppressLint({"NewApi"})
public class VideoPlayTextureView extends TextureView implements SurfaceTextureListener, OnPreparedListener, OnCompletionListener {
    private String VideoPath;
    private MediaState currentMediaState;
    private boolean isChange;
    private float mVideoHeight;
    private float mVideoWidth;
    private MediaPlayer mediaPlayer;
    private int size;
    private Surface surface;

    /* renamed from: com.gitrose.mobile.view.VideoPlayTextureView.1 */
    class C09641 implements Runnable {
        C09641() {
        }

        public void run() {
            VideoPlayTextureView.this.setKeepScreenOn(false);
        }
    }

    /* renamed from: com.gitrose.mobile.view.VideoPlayTextureView.2 */
    class C09652 implements OnVideoSizeChangedListener {
        C09652() {
        }

        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            try {
                VideoPlayTextureView.this.mVideoWidth = (float) width;
                VideoPlayTextureView.this.mVideoHeight = (float) height;
                VideoPlayTextureView.this.updateTextureViewSize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public enum MediaState {
        RESET(5),
        PREPARE(1),
        COMPLETE(2),
        PLAY(3),
        PAUSE(4);
        
        private int mIntValue;

        static MediaState mapIntToValue(int stateInt) {
            for (MediaState value : values()) {
                if (stateInt == value.getIntValue()) {
                    return value;
                }
            }
            return RESET;
        }

        private MediaState(int intValue) {
            this.mIntValue = intValue;
        }

        int getIntValue() {
            return this.mIntValue;
        }
    }

    public void setChange(boolean change) {
        this.isChange = change;
    }

    public boolean isChange() {
        return this.isChange;
    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public VideoPlayTextureView(Context context) {
        super(context);
        this.currentMediaState = MediaState.RESET;
        this.isChange = true;
        init(context);
    }

    public VideoPlayTextureView(Context context, AttributeSet paramAttributeSet) {
        super(context, paramAttributeSet);
        this.currentMediaState = MediaState.RESET;
        this.isChange = true;
        init(context);
    }

    public VideoPlayTextureView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        this.currentMediaState = MediaState.RESET;
        this.isChange = true;
        init(paramContext);
    }

    private void init(Context context) {
        this.mediaPlayer = new MediaPlayer();
        setMediaPlayer(this.mediaPlayer);
        setSurfaceTextureListener(this);
        this.mediaPlayer.setOnCompletionListener(this);
        this.mediaPlayer.setOnPreparedListener(this);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        setMeasuredDimension(MeasureSpec.getSize(paramInt1), MeasureSpec.getSize(paramInt2));
    }

    public void play() {
        try {
            this.currentMediaState = MediaState.PLAY;
            if (this.mediaPlayer != null && !this.mediaPlayer.isPlaying()) {
                this.mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isInPlaybackState() {
        return (this.mediaPlayer == null || this.currentMediaState == MediaState.PAUSE || this.currentMediaState == MediaState.PREPARE || this.currentMediaState == MediaState.RESET) ? false : true;
    }

    public void stopPlayback1() throws IllegalStateException, IOException {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
    }

    public void stopPlayback() {
        if (this.mediaPlayer != null) {
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).runOnUiThread(new C09641());
            }
            release(true);
        }
    }

    public void release(boolean paramBoolean) {
        this.currentMediaState = MediaState.PLAY;
        if (this.mediaPlayer == null || isInPlaybackState()) {
            try {
            } catch (IllegalStateException e) {
                while (true) {
                }
            }
        }
        this.mediaPlayer.reset();
        if (this.mediaPlayer != null) {
            this.mediaPlayer.release();
        }
        this.mediaPlayer = null;
    }

    public void pause() {
        if (this.mediaPlayer != null && this.mediaPlayer.isPlaying()) {
            this.currentMediaState = MediaState.PAUSE;
            this.mediaPlayer.pause();
        }
    }

    public void stop() {
        if (this.mediaPlayer != null && this.mediaPlayer.isPlaying()) {
            this.currentMediaState = MediaState.RESET;
            this.mediaPlayer.stop();
            this.mediaPlayer.reset();
        }
    }

    public void reset() {
        this.currentMediaState = MediaState.RESET;
        this.mediaPlayer.reset();
    }

    public void prepare(String path) {
        try {
            if (!this.mediaPlayer.isPlaying()) {
                this.mediaPlayer.reset();
            }
            this.currentMediaState = MediaState.PREPARE;
            this.mediaPlayer.setAudioStreamType(3);
            this.mediaPlayer.setDataSource(path);
            this.mediaPlayer.setSurface(this.surface);
            prepare();
            this.mediaPlayer.prepare();
        } catch (Exception e) {
        }
    }

    public MediaState getMediaState() {
        return this.currentMediaState;
    }

    public void onSurfaceTextureAvailable(SurfaceTexture arg0, int arg1, int arg2) {
        this.surface = new Surface(arg0);
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture arg0) {
        return false;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture arg0, int arg1, int arg2) {
    }

    public void onSurfaceTextureUpdated(SurfaceTexture arg0) {
    }

    public void onCompletion(MediaPlayer mp) {
        this.currentMediaState = MediaState.COMPLETE;
    }

    public void onPrepared(MediaPlayer mp) {
    }

    public String getVideoPath() {
        return this.VideoPath;
    }

    public void setVideoPath(String videoPath) {
        this.VideoPath = videoPath;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public MediaState getCurrentMediaState() {
        return this.currentMediaState;
    }

    private void updateTextureViewSize() {
        float scale = (this.mVideoHeight * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) / this.mVideoWidth;
        int pivotPointX = (int) (((float) getWidth()) / 2.0f);
        int pivotPointY = (int) (((float) getHeight()) / 2.0f);
        Matrix matrix = new Matrix();
        matrix.setScale(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, scale, (float) pivotPointX, (float) pivotPointY);
        setTransform(matrix);
    }

    private void prepare() {
        this.mediaPlayer.setOnVideoSizeChangedListener(new C09652());
    }
}
