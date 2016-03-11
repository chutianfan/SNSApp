package com.gitrose.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gitrose.greendao.TopicInfo;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.UploadTopic;
import com.gitrose.mobile.view.BaseDialog;

import shouji.gexing.framework.utils.SpUtils;

public class HomeFragmentActivity extends BaseFragmentActivity implements OnClickListener {
    private static HomeFragmentActivity homeFragmentActivity;
    private final String CURRENT_FRAGMENT_HOT;
    private boolean current_fragment_hot;
    private BaseDialog dialog;
    private HomeFollowFragment followFragment;
    private FragmentManager fragmentManager;
    public HomeFragment hotFragment;
    private boolean isHotFragment;
    private boolean isUploadTopic;
    private boolean isredPackage;
    private View loginView;
    private String picPath;
    private int sysnc;
    private TopicInfo topicinfo;
    private UploadTopic uploadTopic;

    public HomeFragmentActivity() {
        this.isHotFragment = true;
        this.isUploadTopic = false;
        this.isredPackage = false;
        this.sysnc = -1;
        this.CURRENT_FRAGMENT_HOT = "current_fragment_hot";
        this.current_fragment_hot = true;
    }

    public static HomeFragmentActivity getIntance() {
        return homeFragmentActivity;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            savedInstanceState.remove("android:support:fragments");
        }

        setContentView(R.layout.activity_home_page);
        if (MyApplication.getInstance().getUserinfo() != null) {
            this.current_fragment_hot = ((Boolean) SpUtils.getFromLocal(this, "current_fragment_hot", MyApplication.getInstance().getUserinfo().getUid(), Boolean.valueOf(true))).booleanValue();
        }
        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        this.hotFragment = new HomeFragment();
        Bundle hotBundle = new Bundle();
        hotBundle.putBoolean("isHot", true);
        this.hotFragment.setArguments(hotBundle);
        this.followFragment = new HomeFollowFragment();
        Bundle followBundle = new Bundle();
        followBundle.putBoolean("isHot", false);
        this.followFragment.setArguments(followBundle);
        if (this.current_fragment_hot) {
            this.isHotFragment = true;
            transaction.add((int) R.id.fragment_container, this.hotFragment).commitAllowingStateLoss();
            transaction.add((int) R.id.fragment_container, this.followFragment);
            transaction.hide(this.followFragment);
        } else {
            this.isHotFragment = false;
            transaction.add((int) R.id.fragment_container, this.followFragment).commitAllowingStateLoss();
            transaction.add((int) R.id.fragment_container, this.hotFragment);
            transaction.hide(this.hotFragment);
        }
        homeFragmentActivity = this;
        this.dialog = new BaseDialog(this);
        this.loginView = View.inflate(this, R.layout.dialog_base_layout, null);
        ((TextView) this.loginView.findViewById(R.id.tv_tip_title)).setText(getResources().getString(R.string.login_tutu));
        this.loginView.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
        this.loginView.findViewById(R.id.pop_tv_submit).setOnClickListener(this);
    }

    protected void onResume() {
        super.onResume();
        if (!getCurrentFragment()) {
            UploadTopic uploadTopic = getUploadTopic();
            this.isHotFragment = false;
            FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
            if (this.followFragment == null) {
                this.followFragment = new HomeFollowFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHot", false);
                this.followFragment.setArguments(bundle);
                fragmentTransaction.add((int) R.id.fragment_container, this.followFragment);
            }
            this.hotFragment.onPause();
            fragmentTransaction.hide(this.hotFragment);
            fragmentTransaction.show(this.followFragment);
            fragmentTransaction.commitAllowingStateLoss();
            this.followFragment.onResume();
            if (!(isUploadTopic() || uploadTopic == null)) {
                this.followFragment.uploadTopic(uploadTopic);
            }
            if (isIsredPackage()) {
                this.followFragment.onRefresh();
                this.followFragment.shareToOther(this.picPath, this.topicinfo, this.sysnc);
            }
            setUploadTopic(true);
            setIsredPackage(false);
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Constant.UPLOAD_TOPIC_ACTION.equals(intent.getAction())) {
            this.isHotFragment = false;
            FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
            if (this.followFragment == null) {
                this.followFragment = new HomeFollowFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHot", false);
                this.followFragment.setArguments(bundle);
                fragmentTransaction.add((int) R.id.fragment_container, this.followFragment);
            }
            this.hotFragment.onPause();
            fragmentTransaction.hide(this.hotFragment);
            fragmentTransaction.show(this.followFragment);
            fragmentTransaction.commitAllowingStateLoss();
            this.followFragment.onResume();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.isHotFragment) {
            this.hotFragment.requestActivityResult(requestCode, resultCode, data);
        } else {
            this.followFragment.requestActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void switchFragment() {
        FragmentTransaction fragmentTransaction;
        if (!this.isHotFragment) {
            this.isHotFragment = true;
            if (MyApplication.getInstance().getUserinfo() != null) {
                SpUtils.saveToLocal(this, "current_fragment_hot", MyApplication.getInstance().getUserinfo().getUid(), Boolean.valueOf(true));
            }
            fragmentTransaction = this.fragmentManager.beginTransaction();
            this.followFragment.onPause();
            fragmentTransaction.hide(this.followFragment);
            fragmentTransaction.show(this.hotFragment);
            fragmentTransaction.commitAllowingStateLoss();
            this.hotFragment.onResume();
        } else if (MyApplication.getInstance().isLogin()) {
            this.isHotFragment = false;
            if (MyApplication.getInstance().getUserinfo() != null) {
                SpUtils.saveToLocal(this, "current_fragment_hot", MyApplication.getInstance().getUserinfo().getUid(), Boolean.valueOf(false));
            }
            fragmentTransaction = this.fragmentManager.beginTransaction();
            if (this.followFragment == null) {
                this.followFragment = new HomeFollowFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHot", false);
                this.followFragment.setArguments(bundle);
                fragmentTransaction.add((int) R.id.fragment_container, this.followFragment);
            }
            this.hotFragment.onPause();
            fragmentTransaction.hide(this.hotFragment);
            fragmentTransaction.show(this.followFragment);
            fragmentTransaction.commitAllowingStateLoss();
            this.followFragment.onResume();
        } else {
            this.dialog.show(this.loginView);
        }
    }

    public boolean getCurrentFragment() {
        return this.isHotFragment;
    }

    public void setCurrentFrament(boolean isHotFragment) {
        this.isHotFragment = isHotFragment;
    }

    public UploadTopic getUploadTopic() {
        return this.uploadTopic;
    }

    public void setUploadTopic(UploadTopic uploadTopic) {
        this.uploadTopic = uploadTopic;
    }

    public boolean isUploadTopic() {
        return this.isUploadTopic;
    }

    public void setUploadTopic(boolean isUploadTopic) {
        this.isUploadTopic = isUploadTopic;
    }

    public boolean isIsredPackage() {
        return this.isredPackage;
    }

    public void setIsredPackage(boolean isredPackage) {
        this.isredPackage = isredPackage;
    }

    public int getSysnc() {
        return this.sysnc;
    }

    public void setSysnc(int sysnc) {
        this.sysnc = sysnc;
    }

    public TopicInfo getTopicinfo() {
        return this.topicinfo;
    }

    public void setTopicinfo(TopicInfo topicinfo) {
        this.topicinfo = topicinfo;
    }

    public String getPicPath() {
        return this.picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_tv_submit:
                startActivityForNew(new Intent(this, LoginActivity.class));
                this.dialog.dismiss();
            case R.id.pop_tv_cancel:
                this.dialog.dismiss();
            default:
        }
    }
}
