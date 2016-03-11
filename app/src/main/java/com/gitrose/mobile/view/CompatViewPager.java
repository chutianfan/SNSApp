package com.gitrose.mobile.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
//import com.baidu.location.LocationClientOption;
//import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;

@SuppressLint({"ClickableViewAccessibility"})
public class CompatViewPager extends ViewPager {
    public static final int SNAP_VELOCITY = 200;
    PointF curP;
    PointF downP;
    private int mClickTimeout;
    private int mMaxVelocity;
    private int mTouchSlop;
    OnSingleTouchListener onSingleTouchListener;
    private VelocityTracker velocityTracker;

    public interface OnSingleTouchListener {
        void onSingleTouch();
    }

    public CompatViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.downP = new PointF();
        this.curP = new PointF();
        initVelocityParams(context);
    }

    private void initVelocityParams(Context context) {
        this.mMaxVelocity = ViewConfiguration.getMaximumFlingVelocity();
        this.mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean onTouchEvent(MotionEvent event) {
        addVelocityTracker(event);
        this.curP.x = event.getX();
        this.curP.y = event.getY();
        switch (event.getAction()) {
            case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
                this.downP.x = event.getX();
                this.downP.y = event.getY();
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;
//            case TopicDetailPagerActivity.FLOW_FIRST_WHAT /*1*/:
//                float deltax = Math.abs(this.curP.x - this.downP.x);
//                float time = (float) (event.getEventTime() - event.getDownTime());
//                if (Math.abs(this.curP.y - this.downP.y) >= ((float) this.mTouchSlop) || deltax >= ((float) this.mTouchSlop) || time >= ((float) this.mClickTimeout)) {
//                    removeVelocityTracker();
//                    break;
//                }
//                onSingleTouch();
//                return true;
                //break;
            case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
                int verlocityX = Math.abs(getScrollXVelocity());
                int verlocityY = Math.abs(getScrollYVelocity());
                if (verlocityX <= verlocityY) {
                    if (verlocityY > SNAP_VELOCITY && verlocityX < SNAP_VELOCITY) {
                        getParent().getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                    }
                }
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }
        return super.onTouchEvent(event);
    }

    public void onSingleTouch() {
        if (this.onSingleTouchListener != null) {
            this.onSingleTouchListener.onSingleTouch();
        }
    }

    public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }

    private void addVelocityTracker(MotionEvent event) {
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(event);
    }

    private void removeVelocityTracker() {
        if (this.velocityTracker != null) {
            this.velocityTracker.clear();
            this.velocityTracker.recycle();
            this.velocityTracker = null;
        }
    }

    private int getScrollXVelocity() {
//        this.velocityTracker.computeCurrentVelocity(LocationClientOption.MIN_SCAN_SPAN, (float) this.mMaxVelocity);
//        return (int) this.velocityTracker.getXVelocity();
        return 0;
    }

    private int getScrollYVelocity() {
//        this.velocityTracker.computeCurrentVelocity(LocationClientOption.MIN_SCAN_SPAN, (float) this.mMaxVelocity);
//        return (int) this.velocityTracker.getYVelocity();
        return 0;
    }
}
