package com.gitrose.mobile;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
//import com.gexing.tutu.jni.MediaEditor;
//import com.tencent.TIMGroupManager;
//import com.tencent.TIMImageElem;
import com.gexing.tutu.jni.MediaEditor;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.UploadTopic;
import com.gitrose.mobile.utils.BitmapUtils;
import com.gitrose.mobile.utils.FileUtil;
import com.gitrose.mobile.utils.RecoderUtil;
import com.gitrose.mobile.view.BaseDialog;
import com.gitrose.mobile.view.CircleImageView;
import com.gitrose.mobile.view.MakeVideoDialog;
import com.gitrose.mobile.view.QingGuoVideoView;
//import io.rong.common.ResourceUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.Vector;

import io.rong.common.ResourceUtils;
import qalsdk.BaseConstants;
//import shouji.gexing.framework.utils.DebugUtils;
import shouji.gexing.framework.utils.UiUtils;

@SuppressLint({"NewApi"})
public class FFmpegPreviewActivity extends BaseActivity implements OnClickListener, OnTouchListener, OnSeekBarChangeListener {
    public static final int BORDER_WIDTH = 1;
    public static final int CORNER_RADIUSE = 4;
    public static final int COVER_IMAGE_COUNT = 10;
    public static final String END_FORMAT = ".mp4";
    public static final int GET_ALL_IMAGE_PATH = 0;
    public static final int IMAGE_COUNT = 11;
    public static final int IMG_TO_VIDEO = 1;
    public static final int MAX_RECODER_TIME = 120;
    public static final int RECODER_VIDEO = 0;
    public static final int UNSELECTED_MUSIC_ID = -1;
    public static final int VIDEO_FILE = 2;
    public static final int VIDEO_TO_VIDEO = 2;
    private static int[] volumeRes;
    private int MSG_PAUSE;
    private int MSG_STOP;
    private int MSG_TIMER;
    private MediaRecorder audioRecord;
    private float bgSound;
    private Rect chat_vioce_rect;
    private View choose_cover_line;
    private RelativeLayout choose_cover_rl;
    private int currentVideoType;
    MakeVideoDialog dialog;
    private boolean exit;
    private ArrayList<String> file_list;
    private File final_audio_file;
    private int imageHeight;
    private String[] imagePathArray;
    private int imageWidth;
    private boolean isError;
    private boolean isFirstInit;
    private boolean isRecord;
    private boolean isRecordPause;
    private ImageView iv_record_voice;
    private ArrayList<Bitmap> mALLCoverBitmaps;
    private FrameLayout mAllCoverImageFL;
    private LinearLayout mAllCoverImageLL;
    private LinearLayout mButtomSeekBarLL;
    private LinearLayout mButtomSoundRecodingLL;
    private TextView mChooseCoverTextView;
    private ArrayList<String> mCoverFilePathList;
    private ImageView mCoverImageView;
    private Uri mCurrentRecUri;
    private String mDefaultText;
    private BaseDialog mDialog;
    private long mDuration;
    private Handler mHandler;
    private int mLastX;
    private FrameLayout mLocalMusicFL;
    private ImageView mLocalMusicIV;
    private RelativeLayout mLocalMusicRL;
    private LinearLayout mMainMusicListLL;
    private int mMargin;
    private MediaEditor mMediaEditor;
    private MediaPlayer mMediaPlayer;
    private TextView mMixMusicTextView;
    private ImageView mMoveSelectdIV;
    private FrameLayout mMovedSelectedFL;
    private LinearLayout mMusicAndCoverGuideLL;
    private LinearLayout mMusicAndCoverListLL;
    private String[] mMusicCoverArray;
    private int[] mMusicCoverResourceIds;
    private HorizontalScrollView mMusicListHV;
    private LinearLayout mMusicListLL;
    private int mScreenWidth;
    private SeekBar mSeekBar;
    private int mSelectedCoverID;
    private FrameLayout mSelectedFrameLayout;
    private int mSelectedMusicId;
    private BaseDialog mShowDialog;
    private FrameLayout mSoundRecodingFrameLayout;
    private CircleImageView mSoundRecodingIV;
    private RelativeLayout mSoundRecodingLL;
    private ProgressBar mSoundRecodingProgress;
    private String mVideoPath;
    private QingGuoVideoView mVideoView;
    private boolean makeWithImage;
    private RelativeLayout mix_miu_rl;
    private View mix_music_line;
    private String musicPath;
    String path;
    private ShowVolumeHandler recordHandler;
    private int rotation;
    private boolean sendToTargetFlag;
    private boolean silentPlay;
    private long start_mill;
    private VoiceVolumeThread threadVolume;
    private Timer timer;
    private int total_seconds;
    private TextView tv_record_time;
    private float videoSound;
    private int videoTime;
    private RelativeLayout videoViewRL;

    /* renamed from: com.gitrose.mobile.FFmpegPreviewActivity.1 */
    class C06431 extends Handler {
        C06431() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FFmpegPreviewActivity.RECODER_VIDEO /*0*/:
                    FFmpegPreviewActivity.this.setDefalutCoverImage();
                    FFmpegPreviewActivity.this.createImagePickView();
                case FFmpegPreviewActivity.VIDEO_TO_VIDEO /*2*/:
                    if (FFmpegPreviewActivity.this.dialog != null) {
                        FFmpegPreviewActivity.this.dialog.dismiss();
                    }
//                    FFmpegPreviewActivity.this.startBack2MainPage(msg.obj, true);
                default:
            }
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegPreviewActivity.2 */
    class C06442 implements Runnable {
        C06442() {
        }

        public void run() {
            try {
                if (!FFmpegPreviewActivity.this.exit && !FFmpegPreviewActivity.this.silentPlay) {
                    FFmpegPreviewActivity.this.mVideoView.getMediaPlay().setVolume(FFmpegPreviewActivity.this.videoSound, FFmpegPreviewActivity.this.videoSound);
                } else if (!FFmpegPreviewActivity.this.exit && FFmpegPreviewActivity.this.silentPlay) {
                    FFmpegPreviewActivity.this.mVideoView.getMediaPlay().setVolume(0.0f, 0.0f);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegPreviewActivity.3 */
    class C06453 implements OnTouchListener {
        C06453() {
        }

        public boolean onTouch(View v, MotionEvent e) {
            switch (e.getAction()) {
                case FFmpegPreviewActivity.RECODER_VIDEO /*0*/:
                    FFmpegPreviewActivity.this.mLastX = (int) e.getX();
                    int coverLeft = FFmpegPreviewActivity.this.mMovedSelectedFL.getLeft();
                    if (FFmpegPreviewActivity.this.mLastX >= coverLeft && FFmpegPreviewActivity.this.mLastX <= FFmpegPreviewActivity.this.mMovedSelectedFL.getWidth() + coverLeft) {
                        return true;
                    }
                case FFmpegPreviewActivity.IMG_TO_VIDEO /*1*/:
                    FFmpegPreviewActivity.this.setMoveSelectedIvLayoutParmas(((int) e.getX()) - FFmpegPreviewActivity.this.mLastX);
                    FFmpegPreviewActivity.this.mLastX = FFmpegPreviewActivity.RECODER_VIDEO;
                    return true;
                case FFmpegPreviewActivity.VIDEO_TO_VIDEO /*2*/:
                    int x = (int) e.getX();
                    FFmpegPreviewActivity.this.setMoveSelectedIvLayoutParmas(x - FFmpegPreviewActivity.this.mLastX);
                    FFmpegPreviewActivity.this.mLastX = x;
                    return true;
            }
            return false;
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegPreviewActivity.4 */
    class C06464 implements OnClickListener {
        C06464() {
        }

        public void onClick(View v) {
            if (FFmpegPreviewActivity.this.mSoundRecodingFrameLayout.isSelected()) {
                FFmpegPreviewActivity.this.mSoundRecodingFrameLayout.setSelected(false);
                FFmpegPreviewActivity.this.stopAnimation(FFmpegPreviewActivity.this.mSoundRecodingFrameLayout);
                if (FFmpegPreviewActivity.this.final_audio_file != null && FFmpegPreviewActivity.this.final_audio_file.exists()) {
                    FFmpegPreviewActivity.this.final_audio_file.delete();
                }
            }
            if (FFmpegPreviewActivity.this.mLocalMusicFL.isSelected()) {
                FFmpegPreviewActivity.this.mLocalMusicFL.setSelected(false);
                FFmpegPreviewActivity.this.stopAnimation(FFmpegPreviewActivity.this.mLocalMusicFL);
                if (FFmpegPreviewActivity.this.musicPath != null) {
                    File file = new File(FFmpegPreviewActivity.this.musicPath);
                    if (file != null && file.exists()) {
                        file.delete();
                    }
                }
            }
            int id = v.getId() + FFmpegPreviewActivity.UNSELECTED_MUSIC_ID;
            if (v.isSelected()) {
                v.setSelected(false);
                FFmpegPreviewActivity.this.mSelectedMusicId = FFmpegPreviewActivity.UNSELECTED_MUSIC_ID;
                FFmpegPreviewActivity.this.stopMusic();
                FFmpegPreviewActivity.this.stopAnimation(v);
                FFmpegPreviewActivity.this.mButtomSeekBarLL.setVisibility(View.GONE);
                FFmpegPreviewActivity.this.reSetSeekBarStatus();
            } else {
                v.setSelected(true);
                FFmpegPreviewActivity.this.mSelectedMusicId = id;
                FFmpegPreviewActivity.this.mButtomSeekBarLL.setVisibility(View.VISIBLE);
                if (!(FFmpegPreviewActivity.this.mSelectedFrameLayout == null || !FFmpegPreviewActivity.this.mSelectedFrameLayout.isSelected() || FFmpegPreviewActivity.this.mSelectedFrameLayout == v)) {
                    FFmpegPreviewActivity.this.mSelectedFrameLayout.setSelected(false);
                    FFmpegPreviewActivity.this.stopAnimation(FFmpegPreviewActivity.this.mSelectedFrameLayout);
                }
                FFmpegPreviewActivity.this.playMusic(id);
                FFmpegPreviewActivity.this.startAnimation(v);
            }
            FFmpegPreviewActivity.this.mSelectedFrameLayout = (FrameLayout) v;
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegPreviewActivity.5 */
    class C06475 implements OnCompletionListener {
        C06475() {
        }

        public void onCompletion(MediaPlayer mp) {
            if (!FFmpegPreviewActivity.this.isError) {
                FFmpegPreviewActivity.this.mVideoView.setVideoURI(Uri.fromFile(new File(FFmpegPreviewActivity.this.mVideoPath)));
                FFmpegPreviewActivity.this.mVideoView.start();
                FFmpegPreviewActivity.this.adjustVolume();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegPreviewActivity.6 */
    class C06496 implements OnErrorListener {

        /* renamed from: com.gitrose.mobile.FFmpegPreviewActivity.6.1 */
        class C06481 implements Runnable {
            C06481() {
            }

            public void run() {
                FFmpegPreviewActivity.this.finish();
            }
        }

        C06496() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            FFmpegPreviewActivity.this.isError = true;
            FFmpegPreviewActivity.this.exit = true;
            Toast.makeText(FFmpegPreviewActivity.this, FFmpegPreviewActivity.this.getResources().getString(R.string.play_video_error), Toast.LENGTH_LONG).show();
            FFmpegPreviewActivity.this.releaseResource();
            if (FFmpegPreviewActivity.this.currentVideoType == 0) {
                Intent intent = new Intent(FFmpegPreviewActivity.this, FFmpegRecorderActivity.class);
                if (FFmpegPreviewActivity.this.mDefaultText != null) {
                    intent.putExtra("mDefaultText", FFmpegPreviewActivity.this.mDefaultText);
                }
                FFmpegPreviewActivity.this.startActivity(intent);
                FFmpegPreviewActivity.this.mHandler.postDelayed(new C06481(), 500);
            } else {
                FFmpegPreviewActivity.this.finish();
            }
            FFmpegPreviewActivity.this.overridePendingTransition(R.anim.main_translatexf100to0, R.anim.main_translatex0to100);
            return false;
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegPreviewActivity.7 */
    class C06507 implements Runnable {
        C06507() {
        }

        public void run() {
            FFmpegPreviewActivity.this.finish();
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegPreviewActivity.8 */
    class C06518 implements OnDismissListener {
        C06518() {
        }

        public void onDismiss(DialogInterface dialog) {
            FFmpegPreviewActivity.this.sendToTargetFlag = false;
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegPreviewActivity.9 */
    class C06529 implements Runnable {
        C06529() {
        }

        public void run() {
            try {
                File audiofile;
                if (FFmpegPreviewActivity.this.mSelectedMusicId != FFmpegPreviewActivity.UNSELECTED_MUSIC_ID) {
                    audiofile = createFileFromInputStream(FFmpegPreviewActivity.this.getAssets().open("music_file" + FFmpegPreviewActivity.this.mSelectedMusicId + ".m4a"));
                } else if (FFmpegPreviewActivity.this.mSoundRecodingFrameLayout.isSelected()) {
                    audiofile = FFmpegPreviewActivity.this.final_audio_file;
                } else {
                    audiofile = new File(FFmpegPreviewActivity.this.musicPath);
                }
                String outMediaFilePath = RecoderUtil.createFinalPath(FFmpegPreviewActivity.this);
                if (FFmpegPreviewActivity.this.mMediaEditor == null) {
                    FFmpegPreviewActivity.this.mMediaEditor = new MediaEditor();
                }
                if (FFmpegPreviewActivity.this.currentVideoType == FFmpegPreviewActivity.IMG_TO_VIDEO && ((long) FFmpegPreviewActivity.this.videoTime) < FFmpegPreviewActivity.this.mDuration * 1000) {
                    String videoPath = reCreateVideo(FFmpegPreviewActivity.this.mVideoPath, FFmpegPreviewActivity.this.mMediaEditor, (int) FFmpegPreviewActivity.this.mDuration);
                    if (videoPath != null) {
                        FFmpegPreviewActivity.this.mVideoPath = videoPath;
                    }
                }
                if (FFmpegPreviewActivity.this.mMediaEditor.StartMixMediaAudio(FFmpegPreviewActivity.this.mVideoPath, outMediaFilePath, null, audiofile.getAbsolutePath(), FFmpegPreviewActivity.this.videoSound, 0.0f, FFmpegPreviewActivity.this.bgSound) == 0) {
                    FFmpegPreviewActivity.this.sendToMainThread(outMediaFilePath);
                }
                if (FFmpegPreviewActivity.this.mSelectedMusicId != FFmpegPreviewActivity.UNSELECTED_MUSIC_ID) {
                    audiofile.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String reCreateVideo(String mVideoPath, MediaEditor mMediaEditor, int mDuration) {
            int imageCount = FFmpegPreviewActivity.this.imagePathArray.length;
            int needImageCount = (mDuration * 40) / FFmpegPreviewActivity.MAX_RECODER_TIME;
            Vector<String> vector = new Vector();
            for (int x = FFmpegPreviewActivity.RECODER_VIDEO; x < FFmpegPreviewActivity.this.imagePathArray.length; x += FFmpegPreviewActivity.IMG_TO_VIDEO) {
                vector.add(FFmpegPreviewActivity.this.imagePathArray[x]);
            }
            if (needImageCount > imageCount) {
                int dur = needImageCount - imageCount;
                int count = dur / imageCount;
                if (count > 0) {
                    for (int i = FFmpegPreviewActivity.RECODER_VIDEO; i < count; i += FFmpegPreviewActivity.IMG_TO_VIDEO) {
                        for (int y = FFmpegPreviewActivity.RECODER_VIDEO; y < FFmpegPreviewActivity.this.imagePathArray.length; y += FFmpegPreviewActivity.IMG_TO_VIDEO) {
                            vector.add(FFmpegPreviewActivity.this.imagePathArray[y]);
                        }
                    }
                } else {
                    for (int j = FFmpegPreviewActivity.RECODER_VIDEO; j < dur; j += FFmpegPreviewActivity.IMG_TO_VIDEO) {
                        vector.add(FFmpegPreviewActivity.this.imagePathArray[j]);
                    }
                }
            }
            //mMediaEditor.addPictureList(vector);
            String newVideoPath = RecoderUtil.createFinalPath(FFmpegPreviewActivity.this);
            if (mMediaEditor.startPicturesToVideo(newVideoPath, FFmpegRecorderActivity.RECODER_WIDTH, FFmpegRecorderActivity.RECODER_WIDTH, 2000000, (float) (mDuration * BaseConstants.f3843b)) != 0) {
                return null;
            }
            File file = new File(mVideoPath);
            if (!file.exists()) {
                return newVideoPath;
            }
            file.delete();
            return newVideoPath;
        }

        private File createFileFromInputStream(InputStream inputStream) {
            IOException e;
            File f = new File(FFmpegPreviewActivity.this.getCacheDir() + "/" + System.currentTimeMillis() + "temp.m4a");
            try {
                OutputStream outputStream = new FileOutputStream(f);
                byte[] buffer = null;//new byte[TIMGroupManager.TIM_GET_GROUP_BASE_INFO_FLAG_NOTIFICATION];
                while (true) {
                    int length = inputStream.read(buffer);
                    if (length <= 0) {
                        outputStream.close();
                        inputStream.close();
                        return f;
                    }
                    outputStream.write(buffer, FFmpegPreviewActivity.RECODER_VIDEO, length);
                }
            } catch (IOException e2) {
                e = e2;
                File file = f;
                e.printStackTrace();
                return null;
            }
        }
    }

    private class ShowVolumeHandler extends Handler {
        WeakReference<FFmpegPreviewActivity> mActivity;

        public ShowVolumeHandler(FFmpegPreviewActivity activity) {
            this.mActivity = new WeakReference(activity);
        }

        public void handleMessage(Message msg) {
            FFmpegPreviewActivity theActivity = (FFmpegPreviewActivity) this.mActivity.get();
            if (theActivity != null) {
                try {
                    if (msg.what < 3) {
                        theActivity.iv_record_voice.setImageResource(FFmpegPreviewActivity.volumeRes[msg.what]);
                    } else if (msg.what == FFmpegPreviewActivity.this.MSG_PAUSE) {
                        theActivity.iv_record_voice.setImageResource(R.drawable.chat_voice_stop_3);
                    } else if (msg.what == FFmpegPreviewActivity.this.MSG_STOP) {
                        theActivity.iv_record_voice.setImageResource(FFmpegPreviewActivity.volumeRes[FFmpegPreviewActivity.RECODER_VIDEO]);
                    } else if (!(msg.what != FFmpegPreviewActivity.this.MSG_TIMER || FFmpegPreviewActivity.this.tv_record_time == null || FFmpegPreviewActivity.this.isRecordPause)) {
                        if (FFmpegPreviewActivity.this.tv_record_time.getVisibility() != View.VISIBLE) {
                            FFmpegPreviewActivity.this.tv_record_time.setVisibility(View.VISIBLE);
                        }
                        int current_time = ((int) ((FFmpegPreviewActivity.this.mDuration + System.currentTimeMillis()) - FFmpegPreviewActivity.this.start_mill)) / BaseConstants.f3843b;
                        FFmpegPreviewActivity.this.tv_record_time.setText(new StringBuilder(String.valueOf(FFmpegPreviewActivity.this.total_seconds - current_time)).append("\"").toString());
                        FFmpegPreviewActivity.this.mSoundRecodingProgress.setProgress((current_time * 100) / FFmpegPreviewActivity.this.total_seconds);
                    }
                    if (((FFmpegPreviewActivity.this.mDuration + System.currentTimeMillis()) - FFmpegPreviewActivity.this.start_mill) / 1000 > ((long) FFmpegPreviewActivity.this.total_seconds)) {
                        FFmpegPreviewActivity.this.stopRecording();
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class VoiceVolumeThread extends Thread {
        private int mFack;
        private int pause;
        private boolean running;

        private VoiceVolumeThread() {
            this.running = true;
            this.mFack = FFmpegPreviewActivity.RECODER_VIDEO;
            this.pause = FFmpegPreviewActivity.RECODER_VIDEO;
        }

        public void exit() {
            FFmpegPreviewActivity.this.iv_record_voice.setImageResource(R.drawable.chat_voice_recording_1);
            this.running = false;
            FFmpegPreviewActivity.this.recordHandler.sendEmptyMessageDelayed(FFmpegPreviewActivity.this.MSG_STOP, 200);
        }

        public void pause() {
            this.pause = FFmpegPreviewActivity.COVER_IMAGE_COUNT;
            FFmpegPreviewActivity.this.mDuration = (System.currentTimeMillis() - FFmpegPreviewActivity.this.start_mill) + FFmpegPreviewActivity.this.mDuration;
            FFmpegPreviewActivity.this.recordHandler.sendEmptyMessageDelayed(FFmpegPreviewActivity.this.MSG_PAUSE, 200);
            if (FFmpegPreviewActivity.this.audioRecord != null) {
                FFmpegPreviewActivity.this.audioRecord.stop();
                FFmpegPreviewActivity.this.audioRecord.release();
                FFmpegPreviewActivity.this.audioRecord = null;
            }
        }

        public void restart() {
            FFmpegPreviewActivity.this.start_mill = System.currentTimeMillis();
            this.pause = FFmpegPreviewActivity.RECODER_VIDEO;
            FFmpegPreviewActivity.this.startRecording();
        }

        public void run() {
            while (this.running) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i = this.mFack + FFmpegPreviewActivity.IMG_TO_VIDEO;
                this.mFack = i;
                if (i > FFmpegPreviewActivity.VIDEO_TO_VIDEO) {
                    this.mFack = FFmpegPreviewActivity.RECODER_VIDEO;
                }
                FFmpegPreviewActivity.this.recordHandler.sendEmptyMessage(this.mFack + this.pause);
            }
        }
    }

    public FFmpegPreviewActivity() {
        this.mSelectedMusicId = UNSELECTED_MUSIC_ID;
        this.mSelectedCoverID = RECODER_VIDEO;
        this.videoTime = RECODER_VIDEO;
        this.chat_vioce_rect = null;
        this.isRecord = false;
        this.isRecordPause = false;
        this.total_seconds = RECODER_VIDEO;
        this.MSG_PAUSE = 15;
        this.MSG_STOP = 16;
        this.MSG_TIMER = 17;
        this.makeWithImage = true;
        this.isFirstInit = true;
        this.sendToTargetFlag = true;
        this.isError = false;
        this.videoSound = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
        this.bgSound = 0.08f;
        this.silentPlay = false;
        this.mHandler = new C06431();
        this.currentVideoType = RECODER_VIDEO;
        this.mMusicCoverResourceIds = new int[]{R.drawable.music_cover08, R.drawable.music_cover01, R.drawable.music_cover02, R.drawable.music_cover03, R.drawable.music_cover04, R.drawable.music_cover05, R.drawable.music_cover06, R.drawable.music_cover07, R.drawable.music_cover09, R.drawable.music_cover10, R.drawable.music_cover11};
        this.exit = false;
    }

    static {
        volumeRes = new int[]{R.drawable.chat_voice_recording_1, R.drawable.chat_voice_recording_2, R.drawable.chat_voice_recording_3};
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        this.path = FileUtil.getSaveDir(getApplicationContext());
        this.mVideoPath = getIntent().getStringExtra("videoPath");
        this.mMediaEditor = new MediaEditor();
        this.mMediaEditor.destroy();
        this.mMediaEditor = null;
        this.mMediaEditor = new MediaEditor();
        this.videoTime = getIntent().getIntExtra("videoTime", RECODER_VIDEO);
        this.currentVideoType = getIntent().getIntExtra(ResourceUtils.id, RECODER_VIDEO);
        this.imagePathArray = getIntent().getStringArrayExtra("pathArray");
        this.mDefaultText = getIntent().getStringExtra("mDefaultText");
        initVideoMessage();
        this.mCoverFilePathList = new ArrayList();
        this.mALLCoverBitmaps = new ArrayList();
        initView();
        getVideoCoverList();
    }

    private void initVideoMessage() {
        this.mMediaEditor.startDecodeFile(this.mVideoPath, RECODER_VIDEO, RECODER_VIDEO, RECODER_VIDEO);
        if (this.currentVideoType == 0) {
            this.videoTime = (int) (this.mMediaEditor.getDecodeFileDuration() * 1000.0f);
        }
        int width = this.mMediaEditor.getDecodeFileWidth();
        int height = this.mMediaEditor.getDecodeFileHeight();
        this.rotation = this.mMediaEditor.getDecodeFileRotation();
        this.imageWidth = width / VIDEO_TO_VIDEO;
        this.imageHeight = height / VIDEO_TO_VIDEO;
        this.mMediaEditor.destroy();
        this.mMediaEditor = null;
        this.mMediaEditor = new MediaEditor();
        this.mMediaEditor.startDecodeFile(this.mVideoPath, this.imageWidth, this.imageHeight, RECODER_VIDEO);
    }

    protected void onPause() {
        super.onPause();
        if (this.mVideoView.isPlaying()) {
            this.mVideoView.pause();
        }
        stopMusic();
    }

    protected void onResume() {
        File file;
        super.onResume();
        if (!(this.isFirstInit || this.mVideoView.isPlaying() || this.isError)) {
            this.mVideoView.start();
        }
        if (!this.isFirstInit && this.mSelectedMusicId != UNSELECTED_MUSIC_ID) {
            playMusic(this.mSelectedMusicId);
        } else if (!this.isFirstInit && this.mSoundRecodingFrameLayout.isSelected() && this.final_audio_file != null && this.final_audio_file.exists()) {
            playAudioFile(this.final_audio_file);
        } else if (!(this.isFirstInit || !this.mLocalMusicFL.isSelected() || this.musicPath == null)) {
            file = new File(this.musicPath);
            if (file != null && file.exists()) {
                playAudioFile(file);
            }
        }
        if (!(this.isFirstInit || this.mLocalMusicFL.isSelected() || this.musicPath == null)) {
            file = new File(this.musicPath);
            if (file != null && file.exists()) {
                file.delete();
            }
        }
        if (!(this.isFirstInit || this.mSoundRecodingFrameLayout.isSelected() || this.final_audio_file == null || !this.final_audio_file.exists())) {
            this.final_audio_file.delete();
        }
        this.isFirstInit = false;
    }

    protected void createImagePickView() {
        this.mMargin = getResources().getDimensionPixelOffset(R.dimen.forget_margin);
        for (int i = RECODER_VIDEO; i < this.mCoverFilePathList.size(); i += IMG_TO_VIDEO) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams((this.mScreenWidth - (this.mMargin * VIDEO_TO_VIDEO)) / COVER_IMAGE_COUNT, (this.mScreenWidth - (this.mMargin * VIDEO_TO_VIDEO)) / COVER_IMAGE_COUNT));
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(new File((String) this.mCoverFilePathList.get(i))));
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ScaleType.CENTER_CROP);
                this.mALLCoverBitmaps.add(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.mAllCoverImageLL.addView(imageView);
        }
        this.mMovedSelectedFL.getLayoutParams().width = (((this.mScreenWidth - (this.mMargin * VIDEO_TO_VIDEO)) / COVER_IMAGE_COUNT) * 5) / 3;
        this.mMovedSelectedFL.getLayoutParams().height = (((this.mScreenWidth - (this.mMargin * VIDEO_TO_VIDEO)) / COVER_IMAGE_COUNT) * 5) / 3;
        this.mMovedSelectedFL.requestLayout();
        this.mMovedSelectedFL.setVisibility(View.VISIBLE);
    }

    protected void setDefalutCoverImage() {
        if (this.mCoverFilePathList.size() > 0) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(new File((String) this.mCoverFilePathList.get(RECODER_VIDEO))));
                this.mCoverImageView.setImageBitmap(bitmap);
                this.mMoveSelectdIV.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        this.mScreenWidth = displaymetrics.widthPixels;
        findViewById(R.id.back_iv).setOnClickListener(this);
        findViewById(R.id.make_iv).setOnClickListener(this);
        this.mSeekBar = (SeekBar) findViewById(R.id.seekbar_sound);
        this.mSeekBar.setOnSeekBarChangeListener(this);
        this.mButtomSeekBarLL = (LinearLayout) findViewById(R.id.buttom_seekbar_ll);
        this.mMixMusicTextView = (TextView) findViewById(R.id.mix_miu_tv);
        this.mChooseCoverTextView = (TextView) findViewById(R.id.choose_cover_tv);
        this.mCoverImageView = (ImageView) findViewById(R.id.cover_iv);
        this.mMusicListLL = (LinearLayout) findViewById(R.id.music_list_ll);
        this.mAllCoverImageFL = (FrameLayout) findViewById(R.id.all_cover_image_rl);
        this.mAllCoverImageLL = (LinearLayout) findViewById(R.id.all_cover_image_ll);
        this.mMusicListHV = (HorizontalScrollView) findViewById(R.id.music_list_hs);
        this.mMainMusicListLL = (LinearLayout) findViewById(R.id.main_music_list_ll);
        this.mButtomSoundRecodingLL = (LinearLayout) findViewById(R.id.buttom_sound_recoding_linearlayout);
        this.mSoundRecodingProgress = (ProgressBar) findViewById(R.id.sound_recoding_progress);
        this.mMusicAndCoverListLL = (LinearLayout) findViewById(R.id.music_and_cover_list_linearLayout);
        this.mMusicAndCoverGuideLL = (LinearLayout) findViewById(R.id.music_and_cover_guide_linearlayout);
        this.videoViewRL = (RelativeLayout) findViewById(R.id.video_view_rl);
        this.mVideoView = (QingGuoVideoView) findViewById(R.id.video_view);
        this.mVideoView.setVideoType(this.currentVideoType);
        this.mLocalMusicRL = (RelativeLayout) findViewById(R.id.local_music_rl);
        this.mLocalMusicFL = (FrameLayout) findViewById(R.id.local_music_fl);
        this.mLocalMusicFL.setOnClickListener(this);
        this.mLocalMusicIV = (ImageView) findViewById(R.id.local_music_iv);
        this.choose_cover_rl = (RelativeLayout) findViewById(R.id.choose_cover_rl);
        this.choose_cover_rl.setBackgroundColor(Color.parseColor("#292f2d"));
        this.mix_miu_rl = (RelativeLayout) findViewById(R.id.mix_miu_rl);
        this.mix_miu_rl.setBackgroundColor(Color.parseColor("#1f2422"));
        this.mix_music_line = findViewById(R.id.mix_miu_line);
        this.choose_cover_line = findViewById(R.id.choose_cover_line);
        this.choose_cover_line.setVisibility(View.INVISIBLE);
        RelativeLayout.LayoutParams layoutParams;
        if (this.currentVideoType == IMG_TO_VIDEO) {
            layoutParams = new RelativeLayout.LayoutParams(this.mScreenWidth, this.mScreenWidth);
            layoutParams.topMargin = getResources().getDimensionPixelOffset(R.dimen.recoder_top_height);
            this.videoViewRL.setLayoutParams(layoutParams);
        } else if (this.currentVideoType == VIDEO_TO_VIDEO) {
            layoutParams = new RelativeLayout.LayoutParams(this.mScreenWidth, this.mScreenWidth);
            layoutParams.topMargin = getResources().getDimensionPixelOffset(R.dimen.recoder_top_height);
            this.videoViewRL.setLayoutParams(layoutParams);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(UNSELECTED_MUSIC_ID, UNSELECTED_MUSIC_ID);
            params.addRule(13);
            this.mVideoView.setLayoutParams(params);
        }
        this.videoViewRL.requestLayout();
        this.mSoundRecodingLL = (RelativeLayout) findViewById(R.id.sound_recoding_ll);
        this.mSoundRecodingFrameLayout = (FrameLayout) findViewById(R.id.sound_recoding_fl);
        this.mSoundRecodingIV = (CircleImageView) findViewById(R.id.sound_recoding_iv);
        this.mMovedSelectedFL = (FrameLayout) findViewById(R.id.moved_selected_image_fl);
        this.mMoveSelectdIV = (ImageView) findViewById(R.id.move_selectd_Image_iv);
        this.mMoveSelectdIV.setScaleType(ScaleType.CENTER_CROP);
        this.tv_record_time = (TextView) findViewById(R.id.tv_record_time);
        this.iv_record_voice = (ImageView) findViewById(R.id.iv_record_voice);
        this.iv_record_voice.setOnTouchListener(this);
        this.recordHandler = new ShowVolumeHandler(this);
        this.mCoverImageView.getLayoutParams().height = this.mScreenWidth;
        dealMusicListHV();
        switchGuideStatus(true);
        this.mVideoView.setVideoURI(Uri.fromFile(new File(this.mVideoPath)));
        this.mVideoView.start();
        adjustVolume();
        setVideoPlayCompletionListener();
        setCoverListOnTouchListener();
        setVideoPlayErrorListener();
        if (this.currentVideoType == IMG_TO_VIDEO) {
            this.makeWithImage = true;
            this.total_seconds = MAX_RECODER_TIME;
        } else {
            this.makeWithImage = false;
            this.total_seconds = this.videoTime / BaseConstants.f3843b;
        }
        switchMakeWith(this.makeWithImage);
    }

    private void adjustVolume() {
        this.mHandler.postDelayed(new C06442(), 200);
    }

    private void switchMakeWith(boolean makewith) {
        if (makewith) {
            this.total_seconds = MAX_RECODER_TIME;
        }
        this.mSoundRecodingProgress.setMax(100);
        this.tv_record_time.setVisibility(View.VISIBLE);
        this.tv_record_time.setText(this.total_seconds + "\"");
    }

    private void setCoverListOnTouchListener() {
        this.mAllCoverImageFL.setOnTouchListener(new C06453());
    }

    public void setMoveSelectedIvLayoutParmas(int deltaX) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) this.mMovedSelectedFL.getLayoutParams();
        int marginLeft = lp.leftMargin + deltaX;
        int width = this.mMovedSelectedFL.getWidth();
        int parentWidth = this.mAllCoverImageFL.getWidth();
        if (marginLeft < 0) {
            marginLeft = RECODER_VIDEO;
        } else if (marginLeft > parentWidth - width) {
            marginLeft = parentWidth - width;
        }
        int picPos = (this.mCoverFilePathList.size() * (marginLeft + (width >> IMG_TO_VIDEO))) / parentWidth;
        if (marginLeft <= ((this.mScreenWidth - (this.mMargin * VIDEO_TO_VIDEO)) / COVER_IMAGE_COUNT) / VIDEO_TO_VIDEO) {
            picPos = RECODER_VIDEO;
        }
        this.mSelectedCoverID = picPos;
        showCoverImage(picPos);
        lp.setMargins(marginLeft, RECODER_VIDEO, RECODER_VIDEO, RECODER_VIDEO);
        this.mMovedSelectedFL.setLayoutParams(lp);
        this.mMovedSelectedFL.setBackgroundResource(R.drawable.shape_moved_image_bg);
    }

    private void showCoverImage(int picPos) {
        if (picPos >= 0 && picPos < this.mALLCoverBitmaps.size()) {
            Bitmap bitmap = (Bitmap) this.mALLCoverBitmaps.get(picPos);
            this.mMoveSelectdIV.setImageBitmap(bitmap);
            this.mCoverImageView.setImageBitmap(bitmap);
        }
    }

    public void switchGuideStatus(boolean isSelectedMusic) {
        boolean z = false;
        if (isSelectedMusic) {
            this.mix_music_line.setVisibility(View.VISIBLE);
            this.choose_cover_line.setVisibility(View.INVISIBLE);
            this.mix_miu_rl.setBackgroundColor(Color.parseColor("#1f2422"));
            this.choose_cover_rl.setBackgroundColor(Color.parseColor("#292f2d"));
        } else {
            this.mix_music_line.setVisibility(View.INVISIBLE);
            this.choose_cover_line.setVisibility(View.VISIBLE);
            this.mix_miu_rl.setBackgroundColor(Color.parseColor("#292f2d"));
            this.choose_cover_rl.setBackgroundColor(Color.parseColor("#1f2422"));
        }
        this.mMixMusicTextView.setSelected(isSelectedMusic);
        TextView textView = this.mChooseCoverTextView;
        if (!isSelectedMusic) {
            z = true;
        }
        textView.setSelected(z);
    }

    @SuppressLint({"ResourceAsColor"})
    private void dealMusicListHV() {
        this.mMusicCoverArray = getResources().getStringArray(R.array.music_cover_name);
        boolean flag = "MEIZU".equalsIgnoreCase(Build.BRAND);
        if (flag) {
            this.mSoundRecodingLL.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_width);
            this.mSoundRecodingFrameLayout.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_width);
            this.mSoundRecodingFrameLayout.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_height);
            this.mSoundRecodingIV.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_width);
            this.mSoundRecodingIV.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_height);
            this.mLocalMusicRL.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_width);
            this.mLocalMusicFL.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_width);
            this.mLocalMusicFL.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_height);
            this.mLocalMusicIV.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_width);
            this.mLocalMusicIV.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_height);
        }
        for (int i = RECODER_VIDEO; i < IMAGE_COUNT; i += IMG_TO_VIDEO) {
            LayoutParams mainLayoutParams;
            RelativeLayout.LayoutParams layoutParams;
            FrameLayout.LayoutParams layoutParams2;
            RelativeLayout mainLayout = new RelativeLayout(this);
            if (flag) {
                mainLayoutParams = new LayoutParams(getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_width), UNSELECTED_MUSIC_ID);
            } else {
                mainLayoutParams = new LayoutParams(getResources().getDimensionPixelOffset(R.dimen.music_cover_width), UNSELECTED_MUSIC_ID);
            }
            mainLayoutParams.gravity = Gravity.CENTER;
            mainLayout.setLayoutParams(mainLayoutParams);
            mainLayout.setGravity(17);
            if (i == 0) {
                mainLayoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.music_cover_margin);
            }
            mainLayoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen.music_cover_margin);
            FrameLayout frameLayout = new FrameLayout(this);
            if (flag) {
                layoutParams = new RelativeLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_width), getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_height));
            } else {
                layoutParams = new RelativeLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.music_cover_width), getResources().getDimensionPixelOffset(R.dimen.music_cover_height));
            }
            frameLayout.setLayoutParams(layoutParams);
            CircleImageView imageView = new CircleImageView(this);
            if (flag) {
                layoutParams2 = new FrameLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_width), getResources().getDimensionPixelOffset(R.dimen.meizu_music_cover_height));
            } else {
                layoutParams2 = new FrameLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.music_cover_width), getResources().getDimensionPixelOffset(R.dimen.music_cover_height));
            }
            imageView.setImageResource(this.mMusicCoverResourceIds[i]);
            imageView.setLayoutParams(layoutParams2);
            frameLayout.addView(imageView);
            frameLayout.setForeground(getResources().getDrawable(R.drawable.music_cover_foreground));
            frameLayout.setId(i + IMG_TO_VIDEO);
            mainLayout.addView(frameLayout);
            TextView textView = new TextView(this);
            RelativeLayout.LayoutParams textViewLayout = new RelativeLayout.LayoutParams(UNSELECTED_MUSIC_ID, -2);
            textViewLayout.addRule(3, i + IMG_TO_VIDEO);
            textView.setPadding(RECODER_VIDEO, getResources().getDimensionPixelOffset(R.dimen.music_cover_margin2), RECODER_VIDEO, RECODER_VIDEO);
            mainLayout.addView(textView, textViewLayout);
            textView.setGravity(17);
            textView.setText(this.mMusicCoverArray[i]);
            textView.setTextColor(getResources().getColor(R.color.white));
            this.mMusicListLL.addView(mainLayout);
            setMusicCoverClickListener(frameLayout);
        }
    }

    private void setMusicCoverClickListener(FrameLayout frameLayout) {
        frameLayout.setOnClickListener(new C06464());
    }

    protected void startAnimation(View v) {
        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.tip);
        operatingAnim.setInterpolator(new LinearInterpolator());
        v.startAnimation(operatingAnim);
    }

    protected void stopAnimation(View v) {
        v.clearAnimation();
    }

    public void reSetSeekBarStatus() {
        this.mSeekBar.setProgress(5);
        this.videoSound = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
        this.bgSound = 0.08f;
        changeVolume(this.videoSound, this.bgSound);
    }

    protected void stopMusic() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
    }

    protected void playMusic(int id) {
        AssetManager manager = getAssets();
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
        }
        this.mMediaPlayer = new MediaPlayer();
        this.mMediaPlayer.setLooping(true);
        try {
            AssetFileDescriptor fileDescriptor = manager.openFd("music_file" + id + ".m4a");
            this.mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            this.mMediaPlayer.prepare();
            this.mMediaPlayer.setVolume(this.bgSound, this.bgSound);
            this.mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setVideoPlayCompletionListener() {
        this.mVideoView.setOnCompletionListener(new C06475());
    }

    private void setVideoPlayErrorListener() {
        this.mVideoView.setOnErrorListener(new C06496());
    }

    public void onClick(View v) {
        File file;
        Intent intent;
        switch (v.getId()) {
            case R.id.back_iv:
                backAction();
            case R.id.make_iv:
                makeAction();
            case R.id.sound_recoding_fl:
                stopMusic();
                if (this.mSoundRecodingFrameLayout.isSelected()) {
                    this.mSoundRecodingFrameLayout.setSelected(false);
                    this.mButtomSeekBarLL.setVisibility(View.GONE);
                    stopAnimation(v);
                    reSetSeekBarStatus();
                    return;
                }
                this.mMusicAndCoverGuideLL.setVisibility(View.GONE);
                this.mMusicAndCoverListLL.setVisibility(View.GONE);
                this.mButtomSoundRecodingLL.setVisibility(View.VISIBLE);
                this.mSoundRecodingProgress.setProgress(RECODER_VIDEO);
                this.silentPlay = true;
                silentPlayVideo();
            case R.id.local_music_fl:
                if (v.isSelected()) {
                    v.setSelected(false);
                    stopAnimation(v);
                    stopMusic();
                    this.mButtomSeekBarLL.setVisibility(View.GONE);
                    if (this.musicPath != null) {
                        file = new File(this.musicPath);
                        if (file != null && file.exists()) {
                            file.delete();
                            return;
                        }
                        return;
                    }
                    return;
                }
                intent = new Intent(this, AudioListActicity.class);
                intent.putExtra("videoTime", this.videoTime);
                startActivityForResult(intent, 10000);
            case R.id.sound_recoding_close_iv:
                this.silentPlay = false;
                playVideo();
                if (this.mSelectedMusicId != UNSELECTED_MUSIC_ID) {
                    playMusic(this.mSelectedMusicId);
                } else if (this.mLocalMusicFL.isSelected() && this.musicPath != null && new File(this.musicPath).exists()) {
                    playAudioFile(new File(this.musicPath));
                }
                this.mMusicAndCoverGuideLL.setVisibility(View.VISIBLE);
                this.mMusicAndCoverListLL.setVisibility(View.VISIBLE);
                this.mButtomSoundRecodingLL.setVisibility(View.GONE);
                this.mSoundRecodingFrameLayout.setSelected(false);
                if (this.final_audio_file != null && this.final_audio_file.exists()) {
                    this.final_audio_file.delete();
                }
            case R.id.sound_recoding_ok_iv:
                this.silentPlay = false;
                playVideo();
                if (this.final_audio_file != null && this.final_audio_file.exists()) {
                    playAudioFile(this.final_audio_file);
                    this.mSoundRecodingFrameLayout.setSelected(true);
                    startAnimation(this.mSoundRecodingFrameLayout);
                    if (this.mLocalMusicFL.isSelected()) {
                        this.mLocalMusicFL.setSelected(false);
                        stopAnimation(this.mLocalMusicFL);
                        if (this.musicPath != null) {
                            new File(this.musicPath).delete();
                        }
                    }
                    this.mButtomSeekBarLL.setVisibility(View.VISIBLE);
                    if (this.mSelectedFrameLayout != null) {
                        this.mSelectedFrameLayout.setSelected(false);
                        stopAnimation(this.mSelectedFrameLayout);
                        this.mSelectedMusicId = UNSELECTED_MUSIC_ID;
                    }
                } else if (this.mSelectedMusicId != UNSELECTED_MUSIC_ID) {
                    playMusic(this.mSelectedMusicId);
                } else if (this.mLocalMusicFL.isSelected() && this.musicPath != null) {
                    file = new File(this.musicPath);
                    if (file != null && file.exists()) {
                        playAudioFile(file);
                    }
                }
                this.mMusicAndCoverGuideLL.setVisibility(View.VISIBLE);
                this.mMusicAndCoverListLL.setVisibility(View.VISIBLE);
                this.mButtomSoundRecodingLL.setVisibility(View.GONE);
            case R.id.mix_miu_tv:
                switchVideoView();
            case R.id.choose_cover_tv:
                switchChooseCoverView();
            case R.id.pop_tv_submit:
                this.exit = true;
                this.mDialog.dismiss();
                releaseResource();
                if (this.currentVideoType == 0) {
                    intent = new Intent(this, FFmpegRecorderActivity.class);
                    if (this.mDefaultText != null) {
                        intent.putExtra("mDefaultText", this.mDefaultText);
                    }
                    startActivity(intent);
                    this.mHandler.postDelayed(new C06507(), 500);
                } else {
                    finish();
                }
                overridePendingTransition(R.anim.main_translatexf100to0, R.anim.main_translatex0to100);
            case R.id.pop_tv_cancel:
                this.mDialog.dismiss();
            default:
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10000 && data != null) {
            this.musicPath = data.getStringExtra("musicPath");
            if (this.musicPath != null) {
                File file = new File(this.musicPath);
                if (file != null && file.exists()) {
                    playAudioFile(file);
                    this.mLocalMusicFL.setSelected(true);
                    startAnimation(this.mLocalMusicFL);
                    this.mButtomSeekBarLL.setVisibility(View.VISIBLE);
                    if (this.mSoundRecodingFrameLayout.isSelected()) {
                        this.mSoundRecodingFrameLayout.setSelected(false);
                        if (this.final_audio_file != null && this.final_audio_file.exists()) {
                            this.final_audio_file.delete();
                        }
                    }
                    if (this.mSelectedFrameLayout != null) {
                        this.mSelectedFrameLayout.setSelected(false);
                        stopAnimation(this.mSelectedFrameLayout);
                        this.mSelectedMusicId = UNSELECTED_MUSIC_ID;
                    }
                }
            }
        }
    }

    private void playAudioFile(File Audiofile) {
        String path = Audiofile.getAbsolutePath();
        if (this.mMediaPlayer != null && this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
        }
        this.mMediaPlayer = new MediaPlayer();
        this.mMediaPlayer.setLooping(true);
        try {
            this.mMediaPlayer.setDataSource(Audiofile.getAbsolutePath());
            this.mMediaPlayer.prepare();
            this.mMediaPlayer.setVolume(this.bgSound, this.bgSound);
            this.mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void silentPlayVideo() {
        if (this.mVideoView != null && this.mVideoView.isPlaying()) {
            this.mVideoView.getMediaPlay().setVolume(0.0f, 0.0f);
        }
    }

    private void releaseResource() {
        if (this.mMediaEditor != null) {
            this.mMediaEditor.destroy();
            this.mMediaEditor = null;
        }
        deleteTempFile();
    }

    private void switchChooseCoverView() {
        switchGuideStatus(false);
        this.mMainMusicListLL.setVisibility(View.GONE);
        this.mAllCoverImageFL.setVisibility(View.VISIBLE);
        this.mMoveSelectdIV.setVisibility(View.VISIBLE);
        this.mMoveSelectdIV.requestLayout();
        this.mCoverImageView.setVisibility(View.VISIBLE);
        this.videoViewRL.setVisibility(View.GONE);
    }

    private void switchVideoView() {
        switchGuideStatus(true);
        this.mMainMusicListLL.setVisibility(View.VISIBLE);
        this.mAllCoverImageFL.setVisibility(View.GONE);
        this.mCoverImageView.setVisibility(View.INVISIBLE);
        this.videoViewRL.setVisibility(View.VISIBLE);
        if (!this.isError) {
            this.mVideoView.setVideoURI(Uri.fromFile(new File(this.mVideoPath)));
            this.mVideoView.start();
            adjustVolume();
        }
    }

    private void showDialog() {
        this.dialog = new MakeVideoDialog(this, 17);
        View view = LayoutInflater.from(this).inflate(R.layout.make_video_progress, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.make_video_progress_iv);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.mvlayout_ll);
        LayoutParams layoutParams = new LayoutParams(getResources().getDimensionPixelOffset(R.dimen.make_video_progress_width), getResources().getDimensionPixelOffset(R.dimen.make_video_progress_height));
        layoutParams.topMargin = getResources().getDimensionPixelOffset(R.dimen.action_bar_height) + ((UiUtils.getInstance(this).getmScreenWidth() - getResources().getDimensionPixelOffset(R.dimen.make_video_progress_height)) / VIDEO_TO_VIDEO);
        linearLayout.setLayoutParams(layoutParams);
        ((AnimationDrawable) imageView.getDrawable()).start();
        this.dialog.setOnDismissListener(new C06518());
        this.dialog.showFullScreen(view);
    }

    private void makeAction() {
        stopMusic();
        pauseVideo();
        if (this.mSelectedMusicId != UNSELECTED_MUSIC_ID || this.mSoundRecodingFrameLayout.isSelected() || this.mLocalMusicFL.isSelected()) {
            showDialog();
            this.sendToTargetFlag = true;
            startMergerVideo();
            return;
        }
        startBack2MainPage(this.mVideoPath, false);
    }

    private void startBack2MainPage(String videoPath, boolean hasMusic) {
//        UploadTopic topic;
//        Intent intent2topicnew = new Intent(this, TopicNewActivity.class);
//        if (this.mCoverFilePathList.size() == 0) {
//            topic = new UploadTopic(5, BaseConstants.ah, videoPath, this.videoTime, BaseConstants.ah, BaseConstants.ah, BaseConstants.ah, BaseConstants.ah);
//        } else {
//            topic = new UploadTopic(5, (String) this.mCoverFilePathList.get(this.mSelectedCoverID), videoPath, this.videoTime, BaseConstants.ah, BaseConstants.ah, BaseConstants.ah, BaseConstants.ah);
//        }
//        intent2topicnew.putExtra("uploadTopic", topic);
//        intent2topicnew.putExtra("hasMusic", hasMusic);
//        intent2topicnew.putExtra("originalVideoPath", this.mVideoPath);
//        intent2topicnew.putExtra("selectedCoverId", this.mSelectedCoverID);
//        if (this.mSoundRecodingFrameLayout.isSelected() && this.final_audio_file != null && this.final_audio_file.exists()) {
//            intent2topicnew.putExtra("audioFilePath", this.final_audio_file.getAbsolutePath());
//        } else if (this.mLocalMusicFL.isSelected() && this.musicPath != null) {
//            intent2topicnew.putExtra("audioFilePath", this.musicPath);
//        }
//        intent2topicnew.putStringArrayListExtra("videoCovers", this.mCoverFilePathList);
//        intent2topicnew.setAction(Constant.UPLOAD_TOPIC_ACTION);
//        if (this.mDefaultText != null) {
//            intent2topicnew.putExtra("mDefaultText", this.mDefaultText);
//        }
//        startActivity(intent2topicnew);
//        overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
    }

    private void startMergerVideo() {
        new Thread(new C06529()).start();
    }

    protected void sendToMainThread(String videoPath) {
        if (this.sendToTargetFlag) {
            Message message = new Message();
            message.what = VIDEO_TO_VIDEO;
            message.obj = videoPath;
            this.mHandler.sendMessage(message);
        } else if (videoPath != null) {
            new File(videoPath).delete();
        }
    }

    private void pauseVideo() {
        if (this.mVideoView.isPlaying()) {
            this.mVideoView.pause();
        }
    }

    private void playVideo() {
        if (!this.isError) {
            if (this.mVideoView.isPlaying()) {
                adjustVolume();
                return;
            }
            this.mVideoView.start();
            adjustVolume();
        }
    }

    private void deleteOtherCoverImg() {
        new Thread(new Runnable() {
            public void run() {
                if (FFmpegPreviewActivity.this.mCoverFilePathList.size() > 0) {
                    for (int i = FFmpegPreviewActivity.RECODER_VIDEO; i < FFmpegPreviewActivity.this.mCoverFilePathList.size(); i += FFmpegPreviewActivity.IMG_TO_VIDEO) {
                        if (i != FFmpegPreviewActivity.this.mSelectedCoverID) {
                            new File((String) FFmpegPreviewActivity.this.mCoverFilePathList.get(i)).delete();
                        }
                    }
                }
            }
        }).start();
    }

    private void backAction() {
        showPop();
    }

    private void showPop() {
        this.mDialog = new BaseDialog(this);
        View delPop = getLayoutInflater().inflate(R.layout.dialog_base_layout, null);
        delPop.findViewById(R.id.pop_tv_submit).setOnClickListener(this);
        delPop.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
        ((TextView) delPop.findViewById(R.id.tv_tip_title)).setText(getResources().getString(R.string.give_up_video));
        this.mDialog.show(delPop);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != CORNER_RADIUSE) {
            return super.onKeyDown(keyCode, event);
        }
        backAction();
        return true;
    }

    private void getVideoCoverList() {
        new Thread(new Runnable() {
            public void run() {
                byte[] imgBuf = new byte[((FFmpegPreviewActivity.this.imageWidth * FFmpegPreviewActivity.this.imageHeight) * FFmpegPreviewActivity.CORNER_RADIUSE)];
                int[] imgIBuf = new int[(FFmpegPreviewActivity.this.imageWidth * FFmpegPreviewActivity.this.imageHeight)];
                String dir = FileUtil.getSaveDir(FFmpegPreviewActivity.this);
                int timePos = FFmpegPreviewActivity.this.videoTime / FFmpegPreviewActivity.COVER_IMAGE_COUNT;
                int index = FFmpegPreviewActivity.RECODER_VIDEO;
                while (index < FFmpegPreviewActivity.COVER_IMAGE_COUNT) {
                    try {
                        if (FFmpegPreviewActivity.this.mMediaEditor != null) {
                            int status = FFmpegPreviewActivity.this.mMediaEditor.setDecodeVideoPos((float) (index * timePos));
                            if (FFmpegPreviewActivity.this.mMediaEditor.getDecodeOneImage(imgBuf) >= 0.0d) {
                                for (int i = FFmpegPreviewActivity.RECODER_VIDEO; i < FFmpegPreviewActivity.this.imageWidth * FFmpegPreviewActivity.this.imageHeight; i += FFmpegPreviewActivity.IMG_TO_VIDEO) {
                                    imgIBuf[i] = ((((imgBuf[(i * FFmpegPreviewActivity.CORNER_RADIUSE) + 3] & 255) << 24) | ((imgBuf[i * FFmpegPreviewActivity.CORNER_RADIUSE] & 255) << 16)) | ((imgBuf[(i * FFmpegPreviewActivity.CORNER_RADIUSE) + FFmpegPreviewActivity.IMG_TO_VIDEO] & 255) << 8)) | (imgBuf[(i * FFmpegPreviewActivity.CORNER_RADIUSE) + FFmpegPreviewActivity.VIDEO_TO_VIDEO] & 255);
                                }
                                Bitmap bmp = Bitmap.createBitmap(imgIBuf, FFmpegPreviewActivity.this.imageWidth, FFmpegPreviewActivity.this.imageHeight, Config.ARGB_8888);
                                if (FFmpegPreviewActivity.this.rotation != 0) {
                                    bmp = BitmapUtils.getRotateBitmap(bmp, (float) FFmpegPreviewActivity.this.rotation);
                                }
                                String path = new StringBuilder(String.valueOf(dir)).append("/").append(System.currentTimeMillis()).append("temp.png").toString();
                                if (bmp.compress(CompressFormat.PNG, 100, new FileOutputStream(new File(path)))) {
                                    FFmpegPreviewActivity.this.mCoverFilePathList.add(path);
                                }
                            }
                            index += FFmpegPreviewActivity.IMG_TO_VIDEO;
                        } else {
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                FFmpegPreviewActivity.this.mHandler.sendEmptyMessage(FFmpegPreviewActivity.RECODER_VIDEO);
            }
        }).start();
    }

    protected void onDestroy() {
        if (this.mMediaEditor != null) {
            this.mMediaEditor.destroy();
            this.mMediaEditor = null;
        }
        super.onDestroy();
    }

    public void deleteTempFile() {
        new Thread(new Runnable() {
            public void run() {
                boolean bool = new File(FFmpegPreviewActivity.this.mVideoPath).delete();
                if (FFmpegPreviewActivity.this.final_audio_file != null && FFmpegPreviewActivity.this.final_audio_file.exists()) {
                    FFmpegPreviewActivity.this.final_audio_file.delete();
                }
                if (FFmpegPreviewActivity.this.musicPath != null) {
                    File file = new File(FFmpegPreviewActivity.this.musicPath);
                    if (file != null && file.exists()) {
                        file.delete();
                    }
                }
                if (FFmpegPreviewActivity.this.mCoverFilePathList.size() > 0) {
                    for (int i = FFmpegPreviewActivity.RECODER_VIDEO; i < FFmpegPreviewActivity.this.mCoverFilePathList.size(); i += FFmpegPreviewActivity.IMG_TO_VIDEO) {
                        new File((String) FFmpegPreviewActivity.this.mCoverFilePathList.get(i)).delete();
                    }
                }
            }
        }).start();
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (this.chat_vioce_rect == null) {
            this.chat_vioce_rect = new Rect(this.iv_record_voice.getLeft(), this.iv_record_voice.getTop(), this.iv_record_voice.getRight(), this.iv_record_voice.getBottom());
            int[] ps = new int[VIDEO_TO_VIDEO];
            this.iv_record_voice.getLocationOnScreen(ps);
            this.chat_vioce_rect.offsetTo(ps[RECODER_VIDEO], ps[IMG_TO_VIDEO]);
            Rect rect = this.chat_vioce_rect;
            rect.top -= this.chat_vioce_rect.height() / VIDEO_TO_VIDEO;
            rect = this.chat_vioce_rect;
            rect.bottom += this.chat_vioce_rect.height() / VIDEO_TO_VIDEO;
        }
        float x = event.getRawX();
        float y = event.getRawY();
        switch (event.getAction()) {
            case RECODER_VIDEO /*0*/:
//                DebugUtils.error("ontouch  down-------");
                this.mDuration = 0;
                this.isRecordPause = false;
                this.file_list = new ArrayList();
                initViewAndStartRecord();
                startTimer();
                break;
            case IMG_TO_VIDEO /*1*/:
//                DebugUtils.error("ontouch  up-------");
                this.iv_record_voice.setImageResource(R.drawable.chat_voice_recording_1);
                this.tv_record_time.setVisibility(View.INVISIBLE);
                cancelTimer();
                if (!this.chat_vioce_rect.contains((int) x, (int) y)) {
                    if (this.threadVolume != null) {
                        this.threadVolume.exit();
                        this.threadVolume = null;
                    }
                    if (this.audioRecord != null) {
                        this.audioRecord.release();
                        this.audioRecord = null;
                        break;
                    }
                }
                stopRecording();
                break;
            case VIDEO_TO_VIDEO /*2*/:
                if (!this.chat_vioce_rect.contains((int) x, (int) y)) {
                    this.iv_record_voice.setImageResource(R.drawable.chat_voice_stop_3);
                    if (!(this.threadVolume == null || this.isRecordPause)) {
                        this.threadVolume.pause();
                    }
                    this.isRecordPause = true;
                    break;
                }
                if (this.threadVolume != null && this.isRecordPause) {
                    this.threadVolume.restart();
                }
                this.isRecordPause = false;
                break;
            case 3:
                this.iv_record_voice.setImageResource(R.drawable.chat_voice_recording_1);
                this.tv_record_time.setVisibility(View.INVISIBLE);
                cancelTimer();
                if (this.threadVolume != null) {
                    this.threadVolume.exit();
                    this.threadVolume = null;
                }
                if (this.audioRecord != null) {
                    this.audioRecord.release();
                    this.audioRecord = null;
                    break;
                }
                break;
        }
        return true;
    }

    private void initViewAndStartRecord() {
        this.threadVolume = new VoiceVolumeThread();
        this.threadVolume.start();
        startRecording();
    }

    private void startRecording() {
        creatAudioRecord();
        startRecord();
    }

    private void startTimer() {
        TimerTask task = new TimerTask() {
            public void run() {
                FFmpegPreviewActivity.this.recordHandler.sendEmptyMessage(FFmpegPreviewActivity.this.MSG_TIMER);
            }
        };
        this.timer = new Timer(true);
        this.timer.schedule(task, 0, 1000);
    }

    private void cancelTimer() {
        if (this.timer != null) {
            this.timer.cancel();
        }
    }

    private void stopRecording() {
        this.tv_record_time.setVisibility(View.INVISIBLE);
        if (this.threadVolume != null) {
            this.threadVolume.exit();
            this.threadVolume = null;
        }
        cancelTimer();
        closeRecord();
        if (this.mDuration < 1) {
            Toast.makeText(this, getResources().getString(R.string.press_talk), Toast.LENGTH_LONG).show();
        } else if (this.file_list != null && this.file_list.size() >= IMG_TO_VIDEO) {
            getInputCollection(this.file_list);
            try {
                this.iv_record_voice.setImageResource(R.drawable.chat_voice_recording_1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.audioRecord != null) {
                this.audioRecord.release();
                this.audioRecord = null;
            }
        }
    }

    private void closeRecord() {
        if (this.audioRecord != null) {
            this.isRecord = false;
            if (this.audioRecord != null) {
                try {
                    this.audioRecord.stop();
                } catch (Exception e) {
                }
            }
        }
        this.mDuration = ((System.currentTimeMillis() - this.start_mill) + this.mDuration) / 1000;
    }

    public void getInputCollection(List list) {
        if (this.final_audio_file != null && this.final_audio_file.exists()) {
            this.final_audio_file.delete();
        }
        this.final_audio_file = new File(this.path, new StringBuilder(String.valueOf(UUID.randomUUID().toString())).append(".amr").toString());
        FileOutputStream fileOutputStream = null;
        if (!this.final_audio_file.exists()) {
            try {
                this.final_audio_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileOutputStream = new FileOutputStream(this.final_audio_file);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        for (int i = RECODER_VIDEO; i < list.size(); i += IMG_TO_VIDEO) {
            try {
                FileInputStream fileInputStream = new FileInputStream(new File((String) list.get(i)));
                byte[] myByte = new byte[fileInputStream.available()];
                int length = myByte.length;
                if (i == 0) {
                    while (fileInputStream.read(myByte) != UNSELECTED_MUSIC_ID) {
                        fileOutputStream.write(myByte, RECODER_VIDEO, length);
                    }
                } else {
                    while (fileInputStream.read(myByte) != UNSELECTED_MUSIC_ID) {
                        fileOutputStream.write(myByte, 6, length - 6);
                    }
                }
                fileOutputStream.flush();
                fileInputStream.close();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (Exception e32) {
                e32.printStackTrace();
            }
        }
        deleteListRecord();
    }

    private void deleteListRecord() {
        for (int i = RECODER_VIDEO; i < this.file_list.size(); i += IMG_TO_VIDEO) {
            File file = new File((String) this.file_list.get(i));
            if (file.exists()) {
                file.delete();
            }
        }
        this.file_list = null;
    }

    private void creatAudioRecord() {
        if (this.audioRecord != null) {
            this.audioRecord.release();
            this.audioRecord = null;
        }
        this.audioRecord = new MediaRecorder();
        this.audioRecord.setAudioSource(IMG_TO_VIDEO);
        this.audioRecord.setOutputFormat(3);
        this.audioRecord.setAudioEncoder(IMG_TO_VIDEO);
        this.mCurrentRecUri = Uri.fromFile(new File(this.path, UUID.randomUUID().toString()));
        this.audioRecord.setOutputFile(this.mCurrentRecUri.getPath());
        this.file_list.add(this.mCurrentRecUri.getPath());
    }

    private void startRecord() {
        try {
            this.audioRecord.prepare();
            this.audioRecord.start();
        } catch (Exception e) {
            this.audioRecord.reset();
            this.audioRecord.release();
        }
        this.isRecord = true;
        this.start_mill = System.currentTimeMillis();
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        if (this.mSelectedMusicId != UNSELECTED_MUSIC_ID || this.mSoundRecodingFrameLayout.isSelected() || this.mLocalMusicFL.isSelected()) {
            int progress = seekBar.getProgress();
            if (progress == 50) {
                this.bgSound = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
                this.videoSound = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
            } else if (progress > 50) {
                this.bgSound = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
                this.videoSound = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT - ((((float) (progress - 50)) * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) / 50.0f);
            } else {
                this.videoSound = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
                this.bgSound = (((float) progress) * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) / 50.0f;
            }
            changeVolume(this.videoSound, this.bgSound);
        }
    }

    private void changeVolume(float playVideoSound, float playMusicsound) {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setVolume(playMusicsound, playMusicsound);
        }
        if (this.mVideoView != null) {
            this.mVideoView.getMediaPlay().setVolume(playVideoSound, playVideoSound);
        }
    }
}
