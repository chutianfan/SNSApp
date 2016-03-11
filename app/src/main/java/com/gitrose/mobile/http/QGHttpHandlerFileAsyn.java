package com.gitrose.mobile.http;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
//import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.gitrose.mobile.R;
import java.io.File;
//import org.apache.http.Header;
//import shouji.gexing.framework.utils.DebugUtils;

public abstract class QGHttpHandlerFileAsyn// extends FileAsyncHttpResponseHandler {
{
    private AnimationDrawable animation;
    AnimationDrawable animationDrawable;
    private ViewGroup container;
    private boolean isdialog;
    private RelativeLayout loading;
    private Context mContext;
    private File mFile;

    public QGHttpHandlerFileAsyn(Context context, File file) {
        //super(file);
        this.isdialog = true;
        this.mFile = file;
        this.mContext = context;
    }

    public QGHttpHandlerFileAsyn(Context context, File file, boolean isShowLoading, ViewGroup container) {
//        super(file);
        this.isdialog = true;
        this.mFile = file;
        this.isdialog = isShowLoading;
        this.mContext = context;
        this.container = container;
    }

    public void onStart() {
//        super.onStart();
        if (this.isdialog) {
            addLoading();
        }
    }

    public void onFailure(int arg0, PreferenceActivity.Header[] arg1, Throwable throwable, File arg3) {
        removeLoading();
        if (this.mContext != null && throwable != null) {
            throwable.printStackTrace();
            if (this.mContext != null && throwable.getMessage() != null) {
//                Toast.makeText(this.mContext, throwable.getMessage(), 0).show();
            }
        }
    }

    public void onSuccess(int arg0, PreferenceActivity.Header[] arg1, File file) {
        if (this.isdialog) {
            removeLoading();
        }
        this.mFile = file;
//        DebugUtils.error(file.getAbsolutePath() + "\u4e0b\u8f7d\u6210\u529f---------");
    }

    public void onFinish() {
//        super.onFinish();
        if (this.isdialog) {
            removeLoading();
        }
    }

    public void onProgress(int bytesWritten, int totalSize) {
//        DebugUtils.error("file  down------bytesWritten" + bytesWritten + "----totalSize:" + totalSize + (totalSize > 0 ? (bytesWritten / totalSize) * 100 : -1));
    }

    public void addLoading() {
        if (this.mContext != null && this.container != null) {
            if (this.loading == null) {
                this.loading = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.main_dialog, null);
            }
            LayoutParams lp = new LayoutParams(-1, -1);
            lp.addRule(10, -1);
            this.loading.setLayoutParams(lp);
            this.animationDrawable = (AnimationDrawable) ((ImageView) this.loading.findViewById(R.id.progerss_iv)).getBackground();
            this.animationDrawable.start();
            this.container.addView(this.loading);
        }
    }

    public void removeLoading() {
        if (this.container != null) {
            this.container.removeView(this.loading);
        }
    }
}
