package com.gitrose.mobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.constant.Constant;
import shouji.gexing.framework.utils.SpUtils;

public class LoadingAvtivity extends BaseActivity {
    public static final int LOADING_SECOND = 2;
    public static final int TIME_WHAT = 1;
    private MyApplication mApp;
    @SuppressLint({"HandlerLeak"})
    private int time;

    private Handler mHandlder = new Handler()
    {
        public void handleMessage(Message msg)
        {
            if (msg.what != LoadingAvtivity.TIME_WHAT) {
                return;
            }
            if (LoadingAvtivity.this.time < LoadingAvtivity.LOADING_SECOND) {
                LoadingAvtivity loadingAvtivity = LoadingAvtivity.this;
                loadingAvtivity.time = loadingAvtivity.time + LoadingAvtivity.TIME_WHAT;
                Message msg1 = obtainMessage();
                msg1.what = LoadingAvtivity.TIME_WHAT;
                sendMessageDelayed(msg1, 1000);
            } else if (LoadingAvtivity.this.mApp.isFirstApp) {
                LoadingAvtivity.this.finish();
//                SpUtils.saveToLocal(LoadingAvtivity.this, Constant.KEY_ISFIRST, Boolean.valueOf(false));
//                LoadingAvtivity.this.startActivityForNew(new Intent(LoadingAvtivity.this, TipsActivity.class));
                LoadingAvtivity.this.startActivityForNew(new Intent(LoadingAvtivity.this, StartActivity.class));
            } else {
                LoadingAvtivity.this.finish();
                if (MyApplication.getInstance().isPassAuth) {
//                    LoadingAvtivity.this.startActivityForNew(new Intent(LoadingAvtivity.this, HomeActivity.class));
                } else {
                    LoadingAvtivity.this.startActivityForNew(new Intent(LoadingAvtivity.this, StartActivity.class));
                }
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_loading);
        this.mApp = MyApplication.getInstance();
        this.mApp.isFirstApp = ((Boolean) SpUtils.getFromLocal(this, Constant.KEY_ISFIRST, Boolean.valueOf(true))).booleanValue();
        Message msg = this.mHandlder.obtainMessage();
        msg.what = TIME_WHAT;
        this.mHandlder.sendMessageDelayed(msg, 1000);
        MyApplication.getInstance().initLocation();
    }
}
