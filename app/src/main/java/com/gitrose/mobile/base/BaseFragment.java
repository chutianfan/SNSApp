package com.gitrose.mobile.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.gitrose.mobile.C0306R;

public class BaseFragment extends Fragment {
    protected Dialog baseDialog;
    protected boolean isLandscape;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == 2) {
            this.isLandscape = true;
        } else {
            this.isLandscape = false;
        }
    }

    protected View getViewById(View view, int id) {
        return view.findViewById(id);
    }

    public void startActivityForNew(Intent intent) {
        startActivity(intent);
        animationForNew();
    }

    public void animationForNew() {
        getActivity().overridePendingTransition(C0306R.anim.main_translatex100to0, C0306R.anim.main_translatex0tof100);
    }

    public void animationForOld() {
        getActivity().overridePendingTransition(C0306R.anim.main_translatexf100to0, C0306R.anim.main_translatex0to100);
    }

    public BaseFragmentActivity getBaseActivity() {
        return (BaseFragmentActivity) getActivity();
    }

    public void animationForBottom() {
        getActivity().overridePendingTransition(C0306R.anim.main_translatey100to0, C0306R.anim.main_translatey0tof100);
    }

    public void animationForOTop() {
        getActivity().overridePendingTransition(C0306R.anim.main_translateyf100to0, C0306R.anim.main_translatey0to100);
    }
}
