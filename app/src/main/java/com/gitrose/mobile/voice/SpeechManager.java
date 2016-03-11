package com.gitrose.mobile.voice;

import android.content.Context;
import android.media.AudioRecord;
import android.os.Handler;
import android.util.Log;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SpeechManager {
    private MyApplication mApp;
    private Context mContent;
    private long mDuration;
    private String mOutputPath;
    private int mRecordFlag;
    private String mTargetID;
    private VoiceRecordThread mVThread;
    private int mVolume;

    private class VoiceRecordNotify implements Runnable {
        int err;

        public VoiceRecordNotify(int err) {
            this.err = err;
        }

        public void run() {
            switch (this.err) {
                case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
                case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
                default:
                    Log.e("jinkuan", "VoiceRecordNotify,,,err,,," + this.err);
            }
        }
    }

    private class VoiceRecordThread implements Runnable {
        private AudioRecord mAr;
        private Context mContext;
        private boolean mExit;
        private String mF;
        private Handler mH;

        public VoiceRecordThread(Context mContext, Handler h, String f) {
            this.mContext = mContext;
            this.mH = h;
            this.mF = f;
            this.mExit = false;
        }

        public void stopRecord() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            this.mExit = true;
            if (this.mAr != null) {
                try {
                    this.mAr.stop();
                } catch (IllegalStateException e) {
                }
            }
        }

        public void run() {
            String codfile = this.mF;
            this.mAr = new AudioRecord(1, 8000, 16, 2, 655360);
            this.mAr.startRecording();
            byte[] audiodata = new byte[9600];
            FileOutputStream fos = null;
            try {
                File file = new File(codfile);
                if (file.exists()) {
                    file.delete();
                }
                fos = new FileOutputStream(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (-3 != this.mAr.read(audiodata, 0, 9600)) {
                try {
                    fos.write(audiodata);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            try {
                fos.close();
            } catch (IOException e22) {
                e22.printStackTrace();
            }
            this.mAr.release();
            this.mAr = null;
            if (SpeechManager.this.mRecordFlag != 0) {
                SpeechManager.fileDel(this.mF);
                SpeechManager.fileDel(codfile);
            }
            this.mH.post(new VoiceRecordNotify(0));
            SpeechManager.this.mVThread = null;
        }
    }

    public SpeechManager(MyApplication app, Context content) {
        this.mVThread = null;
        this.mApp = app;
        this.mContent = content;
    }

    public SpeechManager(MyApplication app, Context content, String targetID) {
        this.mVThread = null;
        this.mApp = app;
        this.mContent = content;
        this.mTargetID = targetID;
    }

    public int getMaxRmp() {
        return this.mVolume;
    }

    public long getDuration() {
        return this.mDuration;
    }

    private static boolean fileDel(String file) {
        return new File(file).delete();
    }

    public void setOutputFile(String filename) {
        this.mOutputPath = filename;
    }

    public void prepare() {
    }

    public void start() {
        voiceRecordStart(this.mOutputPath);
    }

    public void stop() {
        voiceRecordStop(0);
    }

    public void cancel() {
        voiceRecordStop(1);
    }

    public void release() {
    }

    private void voiceRecordStart(String f) {
        this.mVThread = new VoiceRecordThread(this.mContent, new Handler(), f);
        new Thread(this.mVThread).start();
        this.mDuration = System.currentTimeMillis();
    }

    private void voiceRecordStop(int flag) {
        this.mRecordFlag = flag;
        if (this.mVThread != null) {
            this.mVThread.stopRecord();
        }
        this.mDuration = System.currentTimeMillis() - this.mDuration;
    }

    public static int byteToInt2(byte[] b) {
        return (((b[0] << 24) + (b[1] << 16)) + (b[2] << 8)) + b[3];
    }

    public static int byteToInt16(byte[] b, int start) {
        return (b[start] << 8) + b[start + 1];
    }
}
