package com.gexing.tutu.jni;

import java.util.Vector;

public class MediaEditor {
    public static final int FMT_IMG_RGBA = 0;
    public static final int FMT_IMG_YUV420P = 1;
    private static final int MSG_MIX_COMPLETED = 1;
    private static final int MSG_MIX_ERROR = 1000;
    private static final int MSG_MIX_ERROR_FORMATNOTSUPPORTCODEC = 102;
    private static final int MSG_MIX_ERROR_OPENOUTFILEFORBIDDEN = 101;
    private static final int MSG_MIX_PROGRESS = 2;

    public interface OnCompleteListener {
        void onComplete();
    }

    public interface OnErrorListener {
        void onError(int i, int i2);
    }

    public interface OnProgressListener {
        void onUpdateProgress(int i);
    }

    private static native int nativeAddPictureToList(String str);

    private static native void nativeDestroy();

    private static native int nativeGetAudioFrameSize();

    private static native float nativeGetDecDuration();

    private static native float nativeGetDecFps();

    private static native int nativeGetDecHeight();

    private static native int nativeGetDecRotation();

    private static native int nativeGetDecWidth();

    private static native double nativeGetOneDecodeImage(byte[] bArr);

    private static native void nativePauseRecord();

    private static native void nativeResumeRecord();

    private static native void nativeSetAudioChannels(int i);

    private static native int nativeSetDecodePos(float f);

    private static native void nativeSetPicFmt(int i);

    private static native void nativeSetRotation(int i);

    private static native int nativeStartAudioClip(String str, String str2, float f, float f2);

    private static native int nativeStartDecodeVideo(String str, int i, int i2, int i3);

    private static native int nativeStartMediaClip(String str, String str2, int i, int i2, float f, float f2);

    private static native int nativeStartMixMediaAudio(String str, String str2, String str3, String str4, int i, int i2, int i3);

    private static native int nativeStartPicturesToVideo(String str, int i, int i2, int i3, float f);

    private static native void nativeStartRecord(String str, int i, int i2, int i3, int i4, int i5, int i6);

    private static native int nativeStopDecodeVideo();

    private static native void nativeStopMix();

    private static native void nativeputAudio(byte[] bArr, int i);

    private static native void nativeputImage(byte[] bArr);

    private static native void nativestopRecord();

    static {
        System.loadLibrary("mediaprocess");
    }

    public int startDecodeFile(String inFilePath, int outWidth, int outHeight, int outPix) {
        return nativeStartDecodeVideo(inFilePath, outWidth, outHeight, outPix);
    }

    public int getDecodeFileWidth() {
        return nativeGetDecWidth();
    }

    public int getDecodeFileHeight() {
        return nativeGetDecHeight();
    }

    public float getDecodeFileDuration() {
        return nativeGetDecDuration();
    }

    public int getDecodeFileRotation() {
        return nativeGetDecRotation();
    }

    public float getDecodeFileFps() {
        return nativeGetDecFps();
    }

    public double getDecodeOneImage(byte[] imageBuf) {
        return nativeGetOneDecodeImage(imageBuf);
    }

    public int StopDecodeVideo() {
        return nativeStopDecodeVideo();
    }

    public int setDecodeVideoPos(float pos) {
        return nativeSetDecodePos(pos);
    }

    public int StartMixMediaAudio(String inMediaFilePath, String outMediaFilePath, String inSoundFilePath, String inMusciFilePath, float srcMediaRatio, float srcSoundRatio, float bgMusicRatio) {
        return nativeStartMixMediaAudio(inMediaFilePath, outMediaFilePath, inSoundFilePath, inMusciFilePath, (int) (srcMediaRatio * 256.0f), (int) (srcSoundRatio * 256.0f), (int) (bgMusicRatio * 256.0f));
    }

    public int StartMediaClip(String inMeidaFilePath, String outMediaFilePath, int outWidth, int outHeight, float startTime, float endTime) {
        return nativeStartMediaClip(inMeidaFilePath, outMediaFilePath, outWidth, outHeight, startTime, endTime);
    }

    public int StartAudioClip(String inMeidaFilePath, String outMediaFilePath, float startTime, float endTime) {
        return nativeStartAudioClip(inMeidaFilePath, outMediaFilePath, startTime, endTime);
    }

    public int startPicturesToVideo(String outMeidaFilePath, int outWidth, int outHeight, int outBitrate, float duration) {
        return nativeStartPicturesToVideo(outMeidaFilePath, outWidth, outHeight, outBitrate, duration);
    }

    public int addPictureList(Vector<String> imageFileList) {
        for (int i = FMT_IMG_RGBA; i < imageFileList.size(); i += MSG_MIX_COMPLETED) {
            nativeAddPictureToList((String) imageFileList.get(i));
        }
        return FMT_IMG_RGBA;
    }

    public void startMediaRecord(String outPath, int imgWidth, int imgHeight, int outWidth, int outHeight, int bitrate, int frameRate) {
        nativeStartRecord(outPath, imgWidth, imgHeight, bitrate, frameRate, outWidth, outHeight);
    }

    public void pauseMediaRecord() {
        nativePauseRecord();
    }

    public void resumeMediaRecord() {
        nativeResumeRecord();
    }

    public void stopMediaRecord() {
        nativestopRecord();
    }

    public void putMediaRecordImage(byte[] image) {
        nativeputImage(image);
    }

    public void putMediaRecordAudio(byte[] audio, int size) {
        nativeputAudio(audio, size);
    }

    public void setMediaRecordRation(int rotation) {
        nativeSetRotation(rotation);
    }

    public int getMediaRecordAudioFrameSize() {
        return nativeGetAudioFrameSize();
    }

    public int setMediaRecordAudioChannels(int channels) {
        nativeSetAudioChannels(channels);
        return FMT_IMG_RGBA;
    }

    public void setMediaRecordPixFormate(int fmt) {
        nativeSetPicFmt(fmt);
    }

    public void stopMix() {
        nativeStopMix();
    }

    public void destroy() {
        nativeDestroy();
    }
}
