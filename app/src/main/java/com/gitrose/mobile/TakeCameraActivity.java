package com.gitrose.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.gitrose.mobile.base.BaseFragmentActivity;

public class TakeCameraActivity extends BaseFragmentActivity {

    private static TakeCameraActivity takeCameraActivity;
    private FragmentManager fragmentManager;
    private TakeCameraFragment takeCameraFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_camera);

        takeCameraActivity = this;
        initView();
    }

    public static TakeCameraActivity getIntance() {
        return takeCameraActivity;
    }

    private void initView()
    {
        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        this.takeCameraFragment = new TakeCameraFragment();
        transaction.add((int) R.id.square_container, this.takeCameraFragment).commit();
    }
}
