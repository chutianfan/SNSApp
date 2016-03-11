package com.gitrose.mobile.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SavedFrames implements Parcelable {
    public static final Creator<SavedFrames> CREATOR;
    String cachePath;
    byte[] frameBytesData;
    int frameSize;
    long timeStamp;

    /* renamed from: com.gitrose.mobile.model.SavedFrames.1 */
    static class C08951 implements Creator<SavedFrames> {
        C08951() {
        }

        public SavedFrames createFromParcel(Parcel paramParcel) {
            SavedFrames savedFrame = new SavedFrames();
            savedFrame.readFromParcel(paramParcel);
            return savedFrame;
        }

        public SavedFrames[] newArray(int paramInt) {
            return new SavedFrames[paramInt];
        }
    }

    public byte[] getFrameBytesData() {
        return this.frameBytesData;
    }

    public void setFrameBytesData(byte[] frameBytesData) {
        this.frameBytesData = frameBytesData;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public SavedFrames(byte[] frameBytesData, long timeStamp) {
        this.frameBytesData = null;
        this.timeStamp = 0;
        this.cachePath = null;
        this.frameSize = 0;
        this.frameBytesData = frameBytesData;
        this.timeStamp = timeStamp;
    }

    public String getCachePath() {
        return this.cachePath;
    }

    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    public int getframeSize() {
        return this.frameSize;
    }

    public void setframeSize(int frameSize) {
        this.frameSize = frameSize;
    }

    public SavedFrames(Parcel in) {
        this.frameBytesData = null;
        this.timeStamp = 0;
        this.cachePath = null;
        this.frameSize = 0;
        readFromParcel(in);
    }

    public SavedFrames() {
        this.frameBytesData = null;
        this.timeStamp = 0;
        this.cachePath = null;
        this.frameSize = 0;
        this.frameSize = 0;
        this.frameBytesData = new byte[0];
        this.timeStamp = 0;
        this.cachePath = null;
    }

    static {
        CREATOR = new C08951();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int arg1) {
        out.writeLong(this.timeStamp);
        out.writeInt(this.frameSize);
        out.writeByteArray(this.frameBytesData);
        out.writeString(this.cachePath);
    }

    private void readFromParcel(Parcel in) {
        this.timeStamp = in.readLong();
        this.frameSize = in.readInt();
        this.frameBytesData = new byte[this.frameSize];
        in.readByteArray(this.frameBytesData);
        this.cachePath = in.readString();
    }
}
