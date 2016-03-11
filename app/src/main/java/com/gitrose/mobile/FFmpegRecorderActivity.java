package com.gitrose.mobile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.media.AudioRecord;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.gexing.tutu.jni.MediaEditor;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
//import com.tencent.mobileqq.pb.CodedOutputStreamMicro;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.auth.CookieParser;
import com.gitrose.mobile.avsdk.control.Util;
import com.gitrose.mobile.base.BaseUploadActivity;
import com.gitrose.mobile.base.BaseUploadActivity.IUploadCall;
import com.gitrose.mobile.utils.RecoderUtil;
import com.gitrose.mobile.utils.RecoderUtil.ResolutionComparator;
import com.gitrose.mobile.view.BaseDialog;
import com.gitrose.mobile.view.MakeVideoDialog;
import io.rong.common.ResourceUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.json.JSONException;
import shouji.gexing.framework.utils.UiUtils;

public class FFmpegRecorderActivity extends BaseUploadActivity implements OnClickListener, IUploadCall {
    private static final String CLASS_LABEL = "FFmpegRecorderActivity";
    public static final int INITIATE_RECORD = 3;
    private static final String LOG_TAG = "FFmpegRecorderActivity";
    public static final int MINIMUM_TIME = 4;
    public static final int RECODER_WIDTH = 480;
    public static final int RECORDER_STATE_SUCCESS = 5;
    public static final int RECORD_OK = 6;
    public static boolean ffmpeg_frame_recorder_use_able;
    public FFmpegRecorderActivity activity;
    private AudioRecord audioRecord;
    private AudioRecordRunnable audioRecordRunnable;
    private Thread audioThread;
    private Camera cameraDevice;
    Parameters cameraParameters;
    int cameraSelection;
    private CameraView cameraView;
    ImageView cancelBtn;
    private Dialog creatingProgress;
    private RecorderState currentRecorderState;
    private int currentResolution;
    int defaultCameraId;
    int defaultScreenResolution;
    private Dialog dialog;
    private File fileVideoPath;
    private byte[] firstData;
    TextView flashIcon;
    private int frameCount;
    private int frameRate;
    private boolean hasFlushFeature;
    private boolean hasInitCamera;
    private boolean hasPressPause;
    private boolean hasPressRecoderMenu;
    private ImageLoader imageLoader;
    private String imagePath;
    private boolean initSuccess;
    private boolean isBufferChange;
    private boolean isFinalizing;
    private boolean isFirstPress;
    private boolean isFirstStart;
    boolean isFlashOn;
    private boolean isPreviewOn;
    private boolean isRecordingSaved;
    boolean isRecordingStarted;
    private boolean isStopAudio;
    private final int[] mAudioRecordLock;
    private volatile long mAudioTimeRecorded;
    private ImageView mChoosePic2VideoImageView;
    private String mDefaultText;
    private ImageView mDeleteVideoImageView;
    private String mFirstImgPath;
    public Handler mHandler;
    private long mLastAudioTimestamp;
    private MediaEditor mMediaEditor;
    private ImageView mOkVideoImageView;
    private boolean mProgressBarHasStart;
    private ImageView mRecoderMenuImageView;
    private BaseDialog mShowDialog;
    private ImageView mStopRecoderImageView;
    private ImageView mSwitchCameraImageView;
    private View mTakePicDialogView;
    private final int[] mVideoRecordLock;
    private WakeLock mWakeLock;
    private boolean mhastopPreview;
    ImageView nextBtn;
    DisplayImageOptions options;
    private long pauseTime;
    private long pressPauseTime;
    private int previewHeight;
    private int previewWidth;
    private ProgressBar progressView;
    private boolean rec;
    boolean recordFinish;
    boolean recording;
    volatile boolean runAudioThread;
    private int sampleRate;
    private int screenHeight;
    private int screenWidth;
    private TextView small_textview;
    private long startRecodingTime;
    private String strVideoPath;
    ImageView switchCameraIcon;
    RelativeLayout topLayout;
    int totalFrameCount;
    TextView txtRecordingSize;
    TextView txtTimer;
    private Uri uriVideoPath;
    final Runnable videoProgressRunnable;

    /* renamed from: com.gitrose.mobile.FFmpegRecorderActivity.1 */
    class C06531 implements Runnable {
        C06531() {
        }

        public void run() {
            boolean z = false;
            if (!FFmpegRecorderActivity.this.hasPressRecoderMenu) {
                int i;
                FFmpegRecorderActivity.this.progressView.postDelayed(this, 1000);
                ProgressBar access$1 = FFmpegRecorderActivity.this.progressView;
                if (FFmpegRecorderActivity.this.isBufferChange) {
                    i = 0;
                } else {
                    i = 1;
                }
                access$1.setProgress(i);
                FFmpegRecorderActivity fFmpegRecorderActivity = FFmpegRecorderActivity.this;
                if (!FFmpegRecorderActivity.this.isBufferChange) {
                    z = true;
                }
                fFmpegRecorderActivity.isBufferChange = z;
            }
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegRecorderActivity.2 */
    class C06542 extends Handler {
        C06542() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FFmpegRecorderActivity.INITIATE_RECORD /*3*/:
                    if (!FFmpegRecorderActivity.this.recording) {
                        FFmpegRecorderActivity.this.initiateRecording(true);
                    }
                    FFmpegRecorderActivity.this.rec = true;
                    FFmpegRecorderActivity.this.progressBarStart();
                    break;
                case FFmpegRecorderActivity.MINIMUM_TIME /*4*/:
                    FFmpegRecorderActivity.this.rec = false;
                    FFmpegRecorderActivity.this.mMediaEditor.pauseMediaRecord();
                    if (FFmpegRecorderActivity.this.frameCount >= FFmpegRecorderActivity.this.frameRate * 2) {
                        FFmpegRecorderActivity.this.currentRecorderState = RecorderState.SUCCESS;
                    }
                    break;
                case FFmpegRecorderActivity.RECORDER_STATE_SUCCESS /*5*/:
                    FFmpegRecorderActivity.this.currentRecorderState = RecorderState.SUCCESS;
                    FFmpegRecorderActivity.this.mOkVideoImageView.setImageResource(R.drawable.save_video_img);
                    break;
                case FFmpegRecorderActivity.RECORD_OK /*6*/:
                    FFmpegRecorderActivity.this.saveRecording();
                    break;
                default:
            }
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegRecorderActivity.3 */
    class C06553 implements Runnable {
        C06553() {
        }

        public void run() {
            if (FFmpegRecorderActivity.this.recording) {
                FFmpegRecorderActivity.this.progressView.setProgress((int) ((long) ((FFmpegRecorderActivity.this.frameCount * 100) / (FFmpegRecorderActivity.this.frameRate * 16))));
            }
            FFmpegRecorderActivity.this.mHandler.postDelayed(this, 100);
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegRecorderActivity.4 */
    class C06564 extends AsyncTask<String, Integer, Boolean> {
        C06564() {
        }

        protected void onPreExecute() {
            if (!(FFmpegRecorderActivity.this.topLayout == null || FFmpegRecorderActivity.this.topLayout.getChildCount() <= 0 || FFmpegRecorderActivity.this.isFirstStart)) {
                FFmpegRecorderActivity.this.topLayout.removeAllViews();
            }
            FFmpegRecorderActivity.this.isFirstStart = false;
        }

        protected Boolean doInBackground(String... params) {
            return Boolean.valueOf(FFmpegRecorderActivity.this.setCamera());
        }

        protected void onPostExecute(Boolean result) {
            if (!result.booleanValue() || FFmpegRecorderActivity.this.cameraDevice == null) {
                FFmpegRecorderActivity.this.finish();
                return;
            }
            FFmpegRecorderActivity.this.cameraView = new CameraView(FFmpegRecorderActivity.this, FFmpegRecorderActivity.this.cameraDevice);
            FFmpegRecorderActivity.this.handleSurfaceChanged();
            LayoutParams layoutParam1 = new LayoutParams(FFmpegRecorderActivity.this.screenWidth, (int) (((float) FFmpegRecorderActivity.this.screenWidth) * (((float) FFmpegRecorderActivity.this.previewWidth) / (((float) FFmpegRecorderActivity.this.previewHeight) * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))));
            layoutParam1.addRule(10, -1);
            FFmpegRecorderActivity.this.topLayout.addView(FFmpegRecorderActivity.this.cameraView, layoutParam1);
            FFmpegRecorderActivity.this.progressView.setVisibility(View.VISIBLE);
            if (FFmpegRecorderActivity.this.hasFlushFeature) {
                if (FFmpegRecorderActivity.this.cameraSelection == 1) {
                    FFmpegRecorderActivity.this.flashIcon.setVisibility(View.GONE);
                } else {
                    FFmpegRecorderActivity.this.flashIcon.setVisibility(View.VISIBLE);
                    FFmpegRecorderActivity.this.flashIcon.setText(R.string.camera_open_tip);
                }
            }
            FFmpegRecorderActivity.this.hasInitCamera = true;
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegRecorderActivity.5 */
    class C06575 implements Runnable {
        C06575() {
        }

        public void run() {
            FFmpegRecorderActivity.this.finish();
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegRecorderActivity.6 */
    class C06586 implements Runnable {
        C06586() {
        }

        public void run() {
            FFmpegRecorderActivity.this.finish();
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegRecorderActivity.7 */
    class C06597 implements Runnable {
        C06597() {
        }

        public void run() {
            FFmpegRecorderActivity.this.resetStatus();
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegRecorderActivity.8 */
    class C06608 implements Runnable {
        C06608() {
        }

        public void run() {
            FFmpegRecorderActivity.this.finish();
        }
    }

    /* renamed from: com.gitrose.mobile.FFmpegRecorderActivity.9 */
    class C06619 implements Runnable {
        C06619() {
        }

        public void run() {
            FFmpegRecorderActivity.this.videoTheEnd();
        }
    }

    public static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final int LITTLE_ENDIAN_32_SIZE = 4;
    public static final int LITTLE_ENDIAN_64_SIZE = 8;

    class AudioRecordRunnable implements Runnable {
        byte[] audioData;
        private final AudioRecord audioRecord;
        int bufferReadResult;
        int bufferSize;
        public volatile boolean isInitialized;
        byte[] lastData;

        private AudioRecordRunnable() {
            this.bufferSize = AudioRecord.getMinBufferSize(FFmpegRecorderActivity.this.sampleRate, 12, 2);
            this.audioRecord = new AudioRecord(1, FFmpegRecorderActivity.this.sampleRate, 12, 2, this.bufferSize);
            this.audioData = new byte[this.bufferSize];
        }

        private void record(byte[] audioData, int bufferReadResult) {
            synchronized (FFmpegRecorderActivity.this.mAudioRecordLock) {
                int count;
                int i;
                if (this.bufferSize % DEFAULT_BUFFER_SIZE == 0) {
                    count = this.bufferSize / DEFAULT_BUFFER_SIZE;
                    for (i = 0; i < count; i++) {
                        FFmpegRecorderActivity.this.mMediaEditor.putMediaRecordAudio(Arrays.copyOfRange(audioData, i * DEFAULT_BUFFER_SIZE, (i + 1) * DEFAULT_BUFFER_SIZE), DEFAULT_BUFFER_SIZE);
                    }
                } else if (this.lastData != null) {
                    byte[] remainBytes = Arrays.copyOfRange(audioData, 0, bufferReadResult);
                    byte[] resultBytes = byteMerger(this.lastData, remainBytes);
                    if (resultBytes.length >= DEFAULT_BUFFER_SIZE) {
                        count = resultBytes.length / DEFAULT_BUFFER_SIZE;
                        if (resultBytes.length % DEFAULT_BUFFER_SIZE == 0) {
                            this.lastData = null;
                        } else {
                            this.lastData = Arrays.copyOfRange(resultBytes, count * DEFAULT_BUFFER_SIZE, resultBytes.length);
                        }
                        for (i = 0; i < count; i++) {
                            FFmpegRecorderActivity.this.mMediaEditor.putMediaRecordAudio(Arrays.copyOfRange(resultBytes, i * DEFAULT_BUFFER_SIZE, (i + 1) * DEFAULT_BUFFER_SIZE), DEFAULT_BUFFER_SIZE);
                        }
                    } else {
                        this.lastData = remainBytes;
                    }
                } else if (this.bufferSize < DEFAULT_BUFFER_SIZE) {
                    this.lastData = Arrays.copyOfRange(audioData, 0, bufferReadResult);
                } else {
                    FFmpegRecorderActivity.this.mMediaEditor.putMediaRecordAudio(audioData, DEFAULT_BUFFER_SIZE);
                    this.lastData = Arrays.copyOfRange(audioData, DEFAULT_BUFFER_SIZE, bufferReadResult);
                }
            }
        }

        public byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
            byte[] byte_3 = new byte[(byte_1.length + byte_2.length)];
            System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
            System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
            return byte_3;
        }

        public void run() {
            Process.setThreadPriority(-19);
            this.isInitialized = false;
            if (this.audioRecord != null) {
                while (this.audioRecord.getState() == 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
                this.isInitialized = true;
                this.audioRecord.startRecording();
                while (FFmpegRecorderActivity.this.runAudioThread && !FFmpegRecorderActivity.this.isStopAudio) {
                    this.bufferReadResult = this.audioRecord.read(this.audioData, 0, this.bufferSize);
                    if (this.bufferReadResult > 0 && FFmpegRecorderActivity.this.recording && FFmpegRecorderActivity.this.rec && FFmpegRecorderActivity.this.frameCount < FFmpegRecorderActivity.this.frameRate * 16) {
                        record(this.audioData, this.bufferReadResult);
                    }
                }
                this.audioRecord.stop();
                this.audioRecord.release();
            }
        }
    }

    class CameraView extends SurfaceView implements Callback, PreviewCallback {
        private SurfaceHolder mHolder;

        public CameraView(Context context, Camera camera) {
            super(context);
            try {
                FFmpegRecorderActivity.this.cameraParameters = FFmpegRecorderActivity.this.cameraDevice.getParameters();
                this.mHolder = getHolder();
                this.mHolder.addCallback(this);
                this.mHolder.setType(FFmpegRecorderActivity.INITIATE_RECORD);
                FFmpegRecorderActivity.this.cameraDevice.setPreviewCallback(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void surfaceCreated(SurfaceHolder holder) {
            try {
                stopPreview();
                if (FFmpegRecorderActivity.this.cameraDevice != null) {
                    FFmpegRecorderActivity.this.cameraDevice.setPreviewDisplay(holder);
                }
            } catch (IOException e) {
                FFmpegRecorderActivity.this.cameraDevice.release();
                FFmpegRecorderActivity.this.cameraDevice = null;
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (FFmpegRecorderActivity.this.isPreviewOn && FFmpegRecorderActivity.this.cameraDevice != null) {
                FFmpegRecorderActivity.this.cameraDevice.stopPreview();
            }
            FFmpegRecorderActivity.this.handleSurfaceChanged();
            startPreview();
            if (FFmpegRecorderActivity.this.cameraDevice != null) {
                FFmpegRecorderActivity.this.cameraDevice.autoFocus(null);
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            try {
                this.mHolder.addCallback(null);
                FFmpegRecorderActivity.this.cameraDevice.setPreviewCallback(null);
            } catch (RuntimeException e) {
            }
        }

        public void startPreview() {
            if (!FFmpegRecorderActivity.this.isPreviewOn && FFmpegRecorderActivity.this.cameraDevice != null) {
                FFmpegRecorderActivity.this.isPreviewOn = true;
                FFmpegRecorderActivity.this.cameraDevice.startPreview();
            }
        }

        public void stopPreview() {
            if (FFmpegRecorderActivity.this.isPreviewOn && FFmpegRecorderActivity.this.cameraDevice != null) {
                FFmpegRecorderActivity.this.isPreviewOn = false;
                FFmpegRecorderActivity.this.cameraDevice.stopPreview();
            }
        }

        private byte[] rotateYUV420Degree90(byte[] data, int imageWidth, int imageHeight) {
            int x;
            byte[] yuv = new byte[(((imageWidth * imageHeight) * FFmpegRecorderActivity.INITIATE_RECORD) / 2)];
            int i = 0;
            for (x = 0; x < imageWidth; x++) {
                int y;
                for (y = imageHeight - 1; y >= 0; y--) {
                    yuv[i] = data[(y * imageWidth) + x];
                    i++;
                }
            }
            i = (((imageWidth * imageHeight) * FFmpegRecorderActivity.INITIATE_RECORD) / 2) - 1;
            for (x = imageWidth - 1; x > 0; x -= 2) {
                for (int y = 0; y < imageHeight / 2; y++) {
                    yuv[i] = data[((imageWidth * imageHeight) + (y * imageWidth)) + x];
                    i--;
                    yuv[i] = data[((imageWidth * imageHeight) + (y * imageWidth)) + (x - 1)];
                    i--;
                }
            }
            return yuv;
        }

        private byte[] rotateYUV420Degree180(byte[] data, int imageWidth, int imageHeight) {
            int i;
            byte[] yuv = new byte[(((imageWidth * imageHeight) * FFmpegRecorderActivity.INITIATE_RECORD) / 2)];
            int count = 0;
            for (i = (imageWidth * imageHeight) - 1; i >= 0; i--) {
                yuv[count] = data[i];
                count++;
            }
            i = (((imageWidth * imageHeight) * FFmpegRecorderActivity.INITIATE_RECORD) / 2) - 1;
            int count2 = count;
            for (i = (((imageWidth * imageHeight) * FFmpegRecorderActivity.INITIATE_RECORD) / 2) - 1; i >= imageWidth * imageHeight; i -= 2) {
                count = count2 + 1;
                yuv[count2] = data[i - 1];
                count2 = count + 1;
                yuv[count] = data[i];
            }
            return yuv;
        }

        private byte[] rotateYUV420Degree270(byte[] data, int imageWidth, int imageHeight) {
            int i;
            byte[] yuv = new byte[(((imageWidth * imageHeight) * FFmpegRecorderActivity.INITIATE_RECORD) / 2)];
            int wh = 0;
            int uvHeight = 0;
            if (!(imageWidth == 0 && imageHeight == 0)) {
                int nWidth = imageWidth;
                int nHeight = imageHeight;
                wh = imageWidth * imageHeight;
                uvHeight = imageHeight >> 1;
            }
            int k = 0;
            for (i = 0; i < imageWidth; i++) {
                int j;
                int nPos = 0;
                for (j = 0; j < imageHeight; j++) {
                    yuv[k] = data[nPos + i];
                    k++;
                    nPos += imageWidth;
                }
            }
            for (i = 0; i < imageWidth; i += 2) {
                int nPos = wh;
                for (int j = 0; j < uvHeight; j++) {
                    yuv[k] = data[nPos + i];
                    yuv[k + 1] = data[(nPos + i) + 1];
                    k += 2;
                    nPos += imageWidth;
                }
            }
            return rotateYUV420Degree180(yuv, imageWidth, imageHeight);
        }

        public byte[] cropYUV420(byte[] data, int imageW, int imageH, int newImageH) {
            byte[] yuv = new byte[(((imageW * newImageH) * FFmpegRecorderActivity.INITIATE_RECORD) / 2)];
            int cropH = (imageH - newImageH) / 2;
            int count = 0;
            int j = cropH;
            while (j < cropH + newImageH) {
                int i = 0;
                int count2 = count;
                while (i < imageW) {
                    count = count2 + 1;
                    yuv[count2] = data[(j * imageW) + i];
                    i++;
                    count2 = count;
                }
                j++;
                count = count2;
            }
            int tmp = imageH + (cropH / 2);
            j = tmp;
            while (j < (newImageH / 2) + tmp) {
                int i = 0;
                int count2 = count;
                while (i < imageW) {
                    count = count2 + 1;
                    yuv[count2] = data[(j * imageW) + i];
                    i++;
                    count2 = count;
                }
                j++;
                count = count2;
            }
            return yuv;
        }

        public void onPreviewFrame(byte[] data, Camera camera) {
            synchronized (FFmpegRecorderActivity.this.mVideoRecordLock) {
                if (FFmpegRecorderActivity.this.recording && FFmpegRecorderActivity.this.rec) {
                    if (FFmpegRecorderActivity.this.frameCount >= FFmpegRecorderActivity.this.frameRate * 16) {
                        FFmpegRecorderActivity.this.mHandler.sendEmptyMessage(FFmpegRecorderActivity.RECORD_OK);
                        return;
                    }
                    if (FFmpegRecorderActivity.this.frameCount >= FFmpegRecorderActivity.this.frameRate * 2) {
                        FFmpegRecorderActivity.this.mHandler.sendEmptyMessage(FFmpegRecorderActivity.RECORDER_STATE_SUCCESS);
                    }
                    byte[] tempData = rotateYUV420Degree90(data, FFmpegRecorderActivity.this.previewWidth, FFmpegRecorderActivity.this.previewHeight);
                    if (FFmpegRecorderActivity.this.cameraSelection == 1) {
                        tempData = rotateYUV420Degree270(data, FFmpegRecorderActivity.this.previewWidth, FFmpegRecorderActivity.this.previewHeight);
                    }
                    long needCount = ((((long) FFmpegRecorderActivity.this.totalFrameCount) * ((System.currentTimeMillis() - FFmpegRecorderActivity.this.startRecodingTime) - FFmpegRecorderActivity.this.pauseTime)) / 16000) - ((long) FFmpegRecorderActivity.this.frameCount);
                    if (needCount < 0) {
                        return;
                    } else if (needCount > 1) {
                        for (int i = 0; ((long) i) < needCount; i++) {
//                            FFmpegRecorderActivity.this.mMediaEditor.putMediaRecordImage(tempData);
                            FFmpegRecorderActivity.this.frameCount = FFmpegRecorderActivity.this.frameCount + 1;
                        }
                    } else {
//                        FFmpegRecorderActivity.this.mMediaEditor.putMediaRecordImage(tempData);
                        FFmpegRecorderActivity.this.frameCount = FFmpegRecorderActivity.this.frameCount + 1;
                    }
                }
            }
        }
    }

    public enum RecorderState {
        PRESS(1),
        LOOSEN(2),
        CHANGE(FFmpegRecorderActivity.INITIATE_RECORD),
        SUCCESS(FFmpegRecorderActivity.MINIMUM_TIME);
        
        private int mIntValue;

        static RecorderState mapIntToValue(int stateInt) {
            for (RecorderState value : values()) {
                if (stateInt == value.getIntValue()) {
                    return value;
                }
            }
            return PRESS;
        }

        private RecorderState(int intValue) {
            this.mIntValue = intValue;
        }

        int getIntValue() {
            return this.mIntValue;
        }
    }

    public FFmpegRecorderActivity() {
        this.strVideoPath = null;
        this.fileVideoPath = null;
        this.uriVideoPath = null;
        this.rec = false;
        this.recording = false;
        this.isRecordingStarted = false;
        this.isFlashOn = false;
        this.switchCameraIcon = null;
        this.isPreviewOn = false;
        this.currentResolution = 1;
        this.previewWidth = RECODER_WIDTH;
        this.screenWidth = RECODER_WIDTH;
        this.previewHeight = RECODER_WIDTH;
        this.screenHeight = 800;
        this.sampleRate = 44100;
        this.runAudioThread = true;
        this.cameraParameters = null;
        this.defaultCameraId = -1;
        this.defaultScreenResolution = -1;
        this.cameraSelection = 0;
        this.dialog = null;
        this.topLayout = null;
        this.frameRate = 20;
        this.recordFinish = false;
        this.mVideoRecordLock = new int[0];
        this.mAudioRecordLock = new int[0];
        this.mLastAudioTimestamp = 0;
        this.isRecordingSaved = false;
        this.isFinalizing = false;
        this.imagePath = null;
        this.currentRecorderState = RecorderState.PRESS;
        this.firstData = null;
        this.isStopAudio = false;
        this.mProgressBarHasStart = false;
        this.mhastopPreview = false;
        this.isFirstStart = true;
        this.hasInitCamera = false;
        this.hasFlushFeature = false;
        this.frameCount = 0;
        this.totalFrameCount = this.frameRate * 16;
        this.startRecodingTime = 0;
        this.pauseTime = 0;
        this.isFirstPress = true;
        this.pressPauseTime = 0;
        this.hasPressPause = false;
        this.options = new Builder().imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Config.RGB_565).considerExifParams(true).cacheInMemory(false).displayer(new RoundedBitmapDisplayer(10)).build();
        this.initSuccess = false;
        this.videoProgressRunnable = new C06531();
    }

    static {
        ffmpeg_frame_recorder_use_able = false;
    }

    private void initHandler() {
        this.mHandler = new C06542();
    }

    protected void progressBarStart() {
        if (!this.mProgressBarHasStart) {
            this.mProgressBarHasStart = true;
            this.mHandler.postDelayed(new C06553(), 100);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;
        this.mMediaEditor = new MediaEditor();
        if (getIntent() != null) {
            this.mDefaultText = getIntent().getStringExtra("mDefaultText");
        }
        setContentView(R.layout.activity_recorder);
        this.mWakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(10, LOG_TAG);
        this.mWakeLock.acquire();
        this.imageLoader = ImageLoader.getInstance();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        this.screenWidth = displaymetrics.widthPixels;
        this.screenHeight = displaymetrics.heightPixels;
        initHandler();
        initLayout();
    }

    protected void onResume() {
        super.onResume();
        if (this.mhastopPreview) {
            initCameraLayout();
        }
        if (this.mWakeLock == null) {
            this.mWakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(10, LOG_TAG);
            this.mWakeLock.acquire();
        }
    }

    protected void onPause() {
        super.onPause();
        if (this.cameraView != null) {
            this.cameraView.stopPreview();
            if (this.cameraDevice != null) {
                try {
                    this.cameraDevice.setPreviewCallback(null);
                    this.cameraDevice.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.cameraDevice = null;
            this.cameraView = null;
        }
        if (this.topLayout != null && this.topLayout.getChildCount() > 0) {
            this.topLayout.removeAllViews();
        }
        if (this.mWakeLock != null) {
            this.mWakeLock.release();
            this.mWakeLock = null;
        }
        this.mhastopPreview = true;
    }

    protected void onDestroy() {
        this.recording = false;
        this.runAudioThread = false;
        releaseResources();
        if (this.cameraView != null) {
            this.cameraView.stopPreview();
            if (this.cameraDevice != null) {
                try {
                    this.cameraDevice.setPreviewCallback(null);
                    this.cameraDevice.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.cameraDevice = null;
        }
        this.firstData = null;
        this.cameraDevice = null;
        this.cameraView = null;
        if (this.mWakeLock != null) {
            this.mWakeLock.release();
            this.mWakeLock = null;
        }
        if (this.strVideoPath != null) {
            File file = new File(this.strVideoPath);
            if (file.exists()) {
                file.delete();
            }
        }
        super.onDestroy();
    }

    private void initLayout() {
        this.progressView = (ProgressBar) findViewById(R.id.recorder_progress);
        this.flashIcon = (TextView) findViewById(R.id.recoder_switch_flush_iv);
        this.switchCameraIcon = (ImageView) findViewById(R.id.recoder_switch_carmera_iv);
        this.mOkVideoImageView = (ImageView) findViewById(R.id.ok_video_iv);
        this.mDeleteVideoImageView = (ImageView) findViewById(R.id.delete_video_iv);
        this.mSwitchCameraImageView = (ImageView) findViewById(R.id.switch_camera_iv);
        this.mChoosePic2VideoImageView = (ImageView) findViewById(R.id.choose_pic_2_video_iv);
        this.small_textview = (TextView) findViewById(R.id.small_textview);
        this.mRecoderMenuImageView = (ImageView) findViewById(R.id.recoder_menu_iv);
        this.mStopRecoderImageView = (ImageView) findViewById(R.id.stop_recoder_menu_iv);
        this.mRecoderMenuImageView.setOnClickListener(this);
        this.mChoosePic2VideoImageView.setOnClickListener(this);
        this.mSwitchCameraImageView.setOnClickListener(this);
        this.mDeleteVideoImageView.setOnClickListener(this);
        this.mOkVideoImageView.setOnClickListener(this);
        this.mStopRecoderImageView.setOnClickListener(this);
        this.switchCameraIcon.setOnClickListener(this);
        this.mOkVideoImageView.setVisibility(View.GONE);
        this.mDeleteVideoImageView.setVisibility(View.GONE);
        findViewById(R.id.recoder_close_iv).setOnClickListener(this);
        this.topLayout = (RelativeLayout) findViewById(R.id.recorder_surface_parent);
        this.flashIcon.setOnClickListener(this);
        if (getPackageManager().hasSystemFeature("android.hardware.camera.front")) {
            this.switchCameraIcon.setVisibility(View.VISIBLE);
        }
        if (getPackageManager().hasSystemFeature("android.hardware.camera.flash")) {
            this.hasFlushFeature = true;
        }
        ffmpeg_frame_recorder_use_able = false;
        initCameraLayout();
        skipVideoProgress();
        getLatestImagePath();
        showLastImage();
    }

    private void showLastImage() {
        if (this.mFirstImgPath != null) {
            this.imageLoader.displayImage("file://" + this.mFirstImgPath, this.mChoosePic2VideoImageView, this.options);
        }
    }

    private void getLatestImagePath() {
        Uri mImageUri = Media.EXTERNAL_CONTENT_URI;
        String key_MIME_TYPE = "mime_type";
        ContentResolver mContentResolver = getContentResolver();
        String[] strArr = new String[]{"_data"};
        String stringBuilder = new StringBuilder(String.valueOf(key_MIME_TYPE)).append("=? or ").append(key_MIME_TYPE).append("=? or ").append(key_MIME_TYPE).append("=?").toString();
        String[] strArr2 = new String[INITIATE_RECORD];
        strArr2[0] = "image/jpg";
        strArr2[1] = "image/jpeg";
        strArr2[2] = "image/png";
        Cursor cursor = mContentResolver.query(mImageUri, strArr, stringBuilder, strArr2, "date_modified");
        if (cursor != null) {
            if (cursor.moveToLast()) {
                this.mFirstImgPath = cursor.getString(0);
            }
            cursor.close();
        }
    }

    private void initCameraLayout() {
        new C06564().execute(new String[0]);
    }

    @SuppressLint({"NewApi"})
    private boolean setCamera() {
        try {
            if (VERSION.SDK_INT > 8) {
                int numberOfCameras = Camera.getNumberOfCameras();
                CameraInfo cameraInfo = new CameraInfo();
                for (int i = 0; i < numberOfCameras; i++) {
                    Camera.getCameraInfo(i, cameraInfo);
                    if (cameraInfo.facing == this.cameraSelection) {
                        this.defaultCameraId = i;
                    }
                }
            }
            stopPreview();
            if (this.cameraDevice != null) {
                this.cameraDevice.release();
                this.cameraDevice = null;
            }
            if (this.defaultCameraId >= 0) {
                this.cameraDevice = Camera.open(this.defaultCameraId);
            } else {
                this.cameraDevice = Camera.open();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void initVideoRecorder() {
        this.strVideoPath = RecoderUtil.createFinalPath(this);
        this.fileVideoPath = new File(this.strVideoPath);
        this.mMediaEditor.setMediaRecordPixFormate(2);
        this.mMediaEditor.setMediaRecordAudioChannels(2);
        this.mMediaEditor.startMediaRecord(this.strVideoPath, this.previewHeight, this.previewWidth, this.previewHeight, this.previewWidth, 800000, this.frameRate);
        this.audioRecordRunnable = new AudioRecordRunnable();
        this.audioThread = new Thread(this.audioRecordRunnable);
    }

    public void startRecording() {
        this.runAudioThread = true;
        this.isStopAudio = false;
        this.audioThread.start();
    }

    public void stopPreview() {
        if (this.isPreviewOn && this.cameraDevice != null) {
            this.isPreviewOn = false;
            this.cameraDevice.stopPreview();
        }
    }

    @SuppressLint({"NewApi"})
    private void handleSurfaceChanged() {
        if (this.cameraDevice == null) {
            finish();
            return;
        }
        try {
            List<Size> resolutionList = RecoderUtil.getResolutionList(this.cameraDevice);
            if (resolutionList != null && resolutionList.size() > 0) {
                Collections.sort(resolutionList, new ResolutionComparator());
                Size previewSize = null;
                if (this.defaultScreenResolution == -1) {
                    boolean hasSize = false;
                    for (int i = 0; i < resolutionList.size(); i++) {
                        Size size = (Size) resolutionList.get(i);
                        if (size != null && size.width == 640 && size.height == RECODER_WIDTH) {
                            previewSize = size;
                            hasSize = true;
                            break;
                        }
                    }
                    if (!hasSize) {
                        int mediumResolution = resolutionList.size() / 2;
                        if (mediumResolution >= resolutionList.size()) {
                            mediumResolution = resolutionList.size() - 1;
                        }
                        previewSize = (Size) resolutionList.get(mediumResolution);
                    }
                } else {
                    if (this.defaultScreenResolution >= resolutionList.size()) {
                        this.defaultScreenResolution = resolutionList.size() - 1;
                    }
                    previewSize = (Size) resolutionList.get(this.defaultScreenResolution);
                }
                if (previewSize != null) {
                    this.previewWidth = previewSize.width;
                    this.previewHeight = previewSize.height;
                    this.cameraParameters.setPreviewSize(this.previewWidth, this.previewHeight);
                    if (!this.initSuccess) {
                        initVideoRecorder();
                        startRecording();
                        this.initSuccess = true;
                    }
                }
            }
            if (VERSION.SDK_INT > 8) {
                this.cameraDevice.setDisplayOrientation(RecoderUtil.determineDisplayOrientation(this, this.defaultCameraId));
                List<String> focusModes = this.cameraParameters.getSupportedFocusModes();
                if (focusModes != null) {
                    if ((Build.MODEL.startsWith("GT-I950") || Build.MODEL.endsWith("SCH-I959") || Build.MODEL.endsWith("MEIZU MX3")) && focusModes.contains("continuous-picture")) {
                        this.cameraParameters.setFocusMode("continuous-picture");
                    } else if (focusModes.contains("continuous-video")) {
                        this.cameraParameters.setFocusMode("continuous-video");
                    } else {
                        this.cameraParameters.setFocusMode((String) focusModes.get(0));
                    }
                }
            } else {
                this.cameraDevice.setDisplayOrientation(90);
            }
            this.cameraDevice.setParameters(this.cameraParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        if (!this.hasInitCamera) {
            return;
        }
        if (v.getId() == R.id.recoder_switch_flush_iv) {
            if (this.hasFlushFeature && this.cameraParameters != null) {
                if (this.isFlashOn) {
                    this.isFlashOn = false;
                    this.cameraParameters.setFlashMode("off");
                    this.flashIcon.setText(R.string.camera_open_tip);
                } else {
                    this.isFlashOn = true;
                    this.cameraParameters.setFlashMode("torch");
                    this.flashIcon.setText(R.string.camera_close_tip);
                }
                this.cameraDevice.setParameters(this.cameraParameters);
            }
        } else if (v.getId() == R.id.recoder_switch_carmera_iv) {
            if (this.hasInitCamera) {
                int i;
                this.hasInitCamera = false;
                if (this.cameraSelection == 0) {
                    i = 1;
                } else {
                    i = 0;
                }
                this.cameraSelection = i;
                initCameraLayout();
                if (!this.hasFlushFeature) {
                    return;
                }
                if (this.cameraSelection == 1) {
                    this.flashIcon.setVisibility(View.GONE);
                } else {
                    this.flashIcon.setVisibility(View.VISIBLE);
                }
            }
        } else if (v.getId() == R.id.recoder_close_iv) {
            backAction();
        } else if (v.getId() == R.id.recoder_menu_iv) {
            this.hasPressRecoderMenu = true;
            if (!this.recordFinish && this.frameCount < this.frameRate * 16) {
                if (this.isFirstPress) {
                    this.isFirstPress = false;
                    this.startRecodingTime = System.currentTimeMillis();
                }
                if (this.hasPressPause) {
                    this.pauseTime += System.currentTimeMillis() - this.pressPauseTime;
                    this.hasPressPause = false;
                }
                this.mMediaEditor.resumeMediaRecord();
                this.mHandler.removeMessages(INITIATE_RECORD);
                this.mHandler.removeMessages(MINIMUM_TIME);
                this.mHandler.sendEmptyMessageDelayed(INITIATE_RECORD, 300);
                v.setVisibility(View.GONE);
                this.mStopRecoderImageView.setVisibility(View.VISIBLE);
                this.mChoosePic2VideoImageView.setVisibility(View.GONE);
                this.small_textview.setVisibility(View.GONE);
                this.mSwitchCameraImageView.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.stop_recoder_menu_iv) {
            this.hasPressPause = true;
            this.pressPauseTime = System.currentTimeMillis();
            stopAction();
        } else if (v.getId() == R.id.choose_pic_2_video_iv) {
            showPublishTopicDialog();
        } else if (v.getId() == R.id.switch_camera_iv) {
            modifyPicture(this, true);
            getPicFromCapture();
        } else if (v.getId() == R.id.delete_video_iv) {
            deleteAction();
            this.isStopAudio = true;
            this.runAudioThread = false;
            this.recording = false;
            delay2InitStatus();
            releaseResources();
            if (this.fileVideoPath != null && this.fileVideoPath.exists()) {
                this.fileVideoPath.delete();
            }
        } else if (v.getId() == R.id.ok_video_iv) {
            if (this.frameCount >= this.frameRate * 2) {
                saveRecording();
            }
        } else if (v.getId() == R.id.use_photo_to_video) {
            this.mShowDialog.dismiss();
            Intent intent = new Intent(this, ChooImg2VideoActivity.class);
            if (this.mDefaultText != null) {
                intent.putExtra("mDefaultText", this.mDefaultText);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
            this.mHandler.postDelayed(new C06575(), 500);
        } else if (v.getId() == R.id.use_video_to_video) {
//            this.mShowDialog.dismiss();
//            Intent intent = new Intent(this, AllVideoListActivity.class);
//            if (this.mDefaultText != null) {
//                intent.putExtra("mDefaultText", this.mDefaultText);
//            }
//            startActivity(intent);
//            overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
//            this.mHandler.postDelayed(new C06586(), 500);
        } else if (v.getId() == R.id.photo_pop_tv_cancel) {
            this.mShowDialog.dismiss();
        }
    }

    private void deleteAction() {
        this.mHandler.removeMessages(INITIATE_RECORD);
        this.mHandler.removeMessages(MINIMUM_TIME);
        if (this.rec) {
            this.mHandler.sendEmptyMessage(MINIMUM_TIME);
        }
        this.mStopRecoderImageView.setVisibility(View.GONE);
        this.mRecoderMenuImageView.setVisibility(View.VISIBLE);
        this.mDeleteVideoImageView.setVisibility(View.GONE);
        this.mOkVideoImageView.setVisibility(View.GONE);
        this.mSwitchCameraImageView.setVisibility(View.VISIBLE);
        this.mChoosePic2VideoImageView.setVisibility(View.VISIBLE);
        this.small_textview.setVisibility(View.VISIBLE);
    }

    private void backAction() {
        finish();
        overridePendingTransition(R.anim.main_translatexf100to0, R.anim.main_translatex0to100);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != MINIMUM_TIME) {
            return super.onKeyDown(keyCode, event);
        }
        backAction();
        return true;
    }

    private void delay2InitStatus() {
        this.mHandler.postDelayed(new C06597(), 200);
    }

    private void resetStatus() {
        this.mSwitchCameraImageView.setVisibility(View.VISIBLE);
        this.mChoosePic2VideoImageView.setVisibility(View.VISIBLE);
        this.small_textview.setVisibility(View.VISIBLE);
        this.mDeleteVideoImageView.setVisibility(View.GONE);
        this.mOkVideoImageView.setVisibility(View.GONE);
        this.recordFinish = false;
        this.frameCount = 0;
        this.progressView.setProgress(0);
        this.hasPressRecoderMenu = false;
        this.startRecodingTime = 0;
        this.pauseTime = 0;
        this.isFirstPress = true;
        this.pressPauseTime = 0;
        this.hasPressPause = false;
        this.currentRecorderState = RecorderState.PRESS;
        this.firstData = null;
        this.mLastAudioTimestamp = 0;
        this.mOkVideoImageView.setImageResource(R.drawable.ok_video_click);
        initVideoRecorder();
        startRecording();
        this.isRecordingSaved = false;
        this.isFinalizing = false;
        skipVideoProgress();
    }

    private void stopAction() {
        this.mHandler.removeMessages(INITIATE_RECORD);
        this.mHandler.removeMessages(MINIMUM_TIME);
        if (this.rec) {
            this.mHandler.sendEmptyMessage(MINIMUM_TIME);
        }
        this.mStopRecoderImageView.setVisibility(View.GONE);
        this.mRecoderMenuImageView.setVisibility(View.VISIBLE);
        this.mDeleteVideoImageView.setVisibility(View.VISIBLE);
        this.mOkVideoImageView.setVisibility(View.VISIBLE);
    }

    public void videoTheEnd() {
        releaseResources();
        returnToCaller();
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (this.fileVideoPath != null && this.fileVideoPath.exists()) {
            this.fileVideoPath.delete();
        }
    }

    private void returnToCaller() {
        if (new File(this.strVideoPath).exists()) {
            Intent intent = new Intent(this, FFmpegPreviewActivity.class);
            intent.putExtra("videoPath", this.strVideoPath);
            intent.putExtra("videoTime", 16000);
            intent.putExtra(ResourceUtils.id, 0);
            if (this.mDefaultText != null) {
                intent.putExtra("mDefaultText", this.mDefaultText);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
            this.mHandler.postDelayed(new C06608(), 500);
        }
    }

    private void saveRecording() {
        showDialog();
        stopAction();
        this.rec = false;
        this.recording = false;
        this.runAudioThread = false;
        this.isStopAudio = true;
        this.mHandler.postDelayed(new C06619(), 50);
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                FFmpegRecorderActivity.this.resetStatus();
            }
        }, 200);
    }

    private void releaseResources() {
        if (this.mMediaEditor != null) {
            this.mMediaEditor.stopMediaRecord();
        }
    }

    private void initiateRecording(boolean isActionDown) {
        this.isRecordingStarted = true;
        this.recording = true;
    }

    public void skipVideoProgress() {
        this.progressView.setVisibility(View.VISIBLE);
        if (!this.hasPressRecoderMenu) {
            this.progressView.post(this.videoProgressRunnable);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String authToken = CookieParser.parseCookies2Json(MyApplication.getInstance().getCookieStore());
        outState.putSerializable(Util.JSON_KEY_USER_INFO, MyApplication.getInstance().getUserinfo());
        outState.putString("authToken", authToken);
        outState.putSerializable("mCurrentPhotoFile", this.mCurrentPhotoFile);
        outState.putSerializable("mCropPhotoFile", this.mCropPhotoFile);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            TutuUsers userinfo = (TutuUsers) savedInstanceState.getSerializable(Util.JSON_KEY_USER_INFO);
            String authToken = savedInstanceState.getString("authToken");
            this.mCurrentPhotoFile = (File) savedInstanceState.getSerializable("mCurrentPhotoFile");
            this.mCropPhotoFile = (File) savedInstanceState.getSerializable("mCropPhotoFile");
            MyApplication.getInstance().recoverData(CookieParser.pareseJson2Cookies(authToken), userinfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void gpuback(File file) {
        MyApplication.getInstance().themeFile = file;
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("theme", true);
        startActivityForNew(intent);
        finish();
    }

    private final void showPublishTopicDialog() {
        this.mTakePicDialogView = View.inflate(this, R.layout.dialog_make_video, null);
        this.mTakePicDialogView.findViewById(R.id.use_photo_to_video).setOnClickListener(this);
        this.mTakePicDialogView.findViewById(R.id.use_video_to_video).setOnClickListener(this);
        this.mTakePicDialogView.findViewById(R.id.photo_pop_tv_cancel).setOnClickListener(this);
        this.mShowDialog = new BaseDialog(this);
        this.mShowDialog.show(this.mTakePicDialogView);
    }

    private void showDialog() {
        MakeVideoDialog dialog = new MakeVideoDialog(this, 17);
        View view = LayoutInflater.from(this).inflate(R.layout.make_video_progress, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.make_video_progress_iv);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.mvlayout_ll);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.make_video_progress_width), getResources().getDimensionPixelOffset(R.dimen.make_video_progress_height));
        layoutParams.topMargin = getResources().getDimensionPixelOffset(R.dimen.action_bar_height) + ((UiUtils.getInstance(this).getmScreenWidth() - getResources().getDimensionPixelOffset(R.dimen.make_video_progress_height)) / 2);
        linearLayout.setLayoutParams(layoutParams);
        ((AnimationDrawable) imageView.getDrawable()).start();
        dialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                FFmpegRecorderActivity.this.finish();
            }
        });
        dialog.showFullScreen(view);
    }
}
