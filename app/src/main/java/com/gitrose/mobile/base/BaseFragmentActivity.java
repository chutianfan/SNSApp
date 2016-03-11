package com.gitrose.mobile.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.gitrose.mobile.R;
import com.gitrose.mobile.application.ActivityManager;
import com.gitrose.mobile.utils.PlaySound;
//import com.umeng.analytics.MobclickAgent;

public class BaseFragmentActivity extends FragmentActivity {
    protected boolean isLandscape;

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        ActivityManager.getScreenManager().pushActivity(this);
        if (getResources().getConfiguration().orientation == 2) {
            this.isLandscape = true;
        } else {
            this.isLandscape = false;
        }
    }

    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getScreenManager().popActivity(this);
    }

    public View getViewById(int id) {
        return findViewById(id);
    }

    public void startActivityForNew(Intent intent) {
        startActivity(intent);
        animationForNew();
    }

    public void animationForNew() {
        overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
//        PlaySound.getInstance(this).toPlay(R.raw.open);
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

    public void removeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    public void finish() {
        super.finish();
        animationForOld();
    }

    public void finish2() {
        super.finish();
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        animationForNew();
    }
}
