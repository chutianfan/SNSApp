package com.gitrose.mobile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import com.gitrose.mobile.R;

public class AnimationTabHost extends TabHost {
    private boolean isOpenAnimation;
    private int mTabCount;
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;

    public AnimationTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.slideLeftIn = AnimationUtils.loadAnimation(context, R.anim.main_translatex100to0);
        this.slideLeftOut = AnimationUtils.loadAnimation(context, R.anim.main_translatex0tof100);
        this.slideRightIn = AnimationUtils.loadAnimation(context, R.anim.main_translatexf100to0);
        this.slideRightOut = AnimationUtils.loadAnimation(context, R.anim.main_translatex0to100);
        this.isOpenAnimation = false;
    }

    public void setOpenAnimation(boolean isOpenAnimation) {
        this.isOpenAnimation = isOpenAnimation;
    }

    public boolean setTabAnimation(int[] animationResIDs) {
        if (3 != animationResIDs.length) {
            return false;
        }
        this.slideLeftIn = AnimationUtils.loadAnimation(getContext(), animationResIDs[0]);
        this.slideLeftOut = AnimationUtils.loadAnimation(getContext(), animationResIDs[1]);
        this.slideRightIn = AnimationUtils.loadAnimation(getContext(), animationResIDs[2]);
        this.slideRightOut = AnimationUtils.loadAnimation(getContext(), animationResIDs[3]);
        return true;
    }

    public int getTabCount() {
        return this.mTabCount;
    }

    public void addTab(TabSpec tabSpec) {
        this.mTabCount++;
        super.addTab(tabSpec);
    }

    public void setCurrentTab(int index) {

        int mCurrentTabID = getCurrentTab();
        if (getCurrentView() != null && this.isOpenAnimation) {
            if (mCurrentTabID == this.mTabCount - 1 && index == 0) {
                getCurrentView().startAnimation(this.slideLeftOut);
            } else if (mCurrentTabID == 0 && index == this.mTabCount - 1) {
                getCurrentView().startAnimation(this.slideRightOut);
            } else if (index > mCurrentTabID) {
                getCurrentView().startAnimation(this.slideLeftOut);
            } else if (index < mCurrentTabID) {
                getCurrentView().startAnimation(this.slideRightOut);
            }
        }
        super.setCurrentTab(index);
        if (!this.isOpenAnimation) {
            return;
        }
        if (mCurrentTabID == this.mTabCount - 1 && index == 0 && getCurrentView() != null) {
            getCurrentView().startAnimation(this.slideLeftIn);
        } else if (mCurrentTabID == 0 && index == this.mTabCount - 1) {
            getCurrentView().startAnimation(this.slideRightIn);
        } else if (index > mCurrentTabID) {
            getCurrentView().startAnimation(this.slideLeftIn);
        } else if (index < mCurrentTabID) {
            getCurrentView().startAnimation(this.slideRightIn);
        }


    }
}
