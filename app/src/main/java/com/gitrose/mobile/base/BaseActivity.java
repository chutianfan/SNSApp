package com.gitrose.mobile.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import com.gitrose.mobile.R;
import com.gitrose.mobile.application.ActivityManager;
import com.gitrose.mobile.application.MyApplication;
//import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends Activity {
    protected MyApplication cache;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cache = (MyApplication) getApplicationContext();
        ActivityManager.getScreenManager().pushActivity(this);
    }

    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
        if (MyApplication.getInstance().isClick_home()) {
            MyApplication.getInstance().setClick_home(false);
            MyApplication.getInstance().initLocation();
        }
    }

    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (MyApplication.getInstance() != null) {
            MyApplication.getInstance().getmRequestQueue().cancelAll((Object) this);
        }
        ActivityManager.getScreenManager().popActivity(this);
    }

    public View getViewById(int id) {
        return findViewById(id);
    }

    public void startActivityForNew(Intent intent) {
        startActivity(intent);
        animationForNew();
    }

    public Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f);
        translateAnimation.setInterpolator(new CycleInterpolator((float) counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

    public void animationForNew() {
        overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
    }

    public void animationForOld() {
        overridePendingTransition(R.anim.main_translatexf100to0, R.anim.main_translatex0to100);
    }

    public void animationForBottom() {
        overridePendingTransition(R.anim.main_translatey100to0, R.anim.main_translatey0tof100);
    }

    public void animationForOTop() {
        overridePendingTransition(R.anim.main_translateyf100to0, R.anim.main_translatey0to100);
    }

    public void finish() {
        super.finish();
        animationForOld();
    }

    public void superFinish() {
        super.finish();
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        animationForNew();
    }
}
