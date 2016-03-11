package com.gitrose.mobile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;
//import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;

public class DemoRela extends RelativeLayout {
    private int downX;
    private int downY;
    private int editTextHeight;
    private int editTextWidth;
    private int lastX;
    private int lastY;
    private int mClickTimeout;
    private float mFirstDownX;
    private float mFirstDownY;
    private int mTouchSlop;
    private int upX;
    private int upY;
    private View viewDrag;

    public DemoRela(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.editTextWidth = getWidth();
        this.editTextHeight = getHeight();
        this.viewDrag = getChildAt(1);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int dx;
        int dy;
        switch (event.getAction()) {
            case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
                Log.e("jinkuan", "MotionEvent.ACTION_DOWN.....");
                this.downX = (int) event.getX();
                this.downY = (int) event.getY();
                this.lastX = (int) event.getX();
                this.lastY = (int) event.getY();
                Log.d("\u6309\u4e0b\uff1a", this.downX + "----X\u8f74\u5750\u6807");
                Log.d("\u6309\u4e0b\uff1a", this.downY + "----Y\u8f74\u5750\u6807");
                this.viewDrag.layout(this.downX, this.downY, this.downX + this.viewDrag.getWidth(), this.downY + this.viewDrag.getHeight());
                break;
//            case TopicDetailPagerActivity.FLOW_FIRST_WHAT /*1*/:
//                Log.e("jinkuan", "MotionEvent.ACTION_UP.....");
//                dx = ((int) event.getX()) - this.lastX;
//                dy = ((int) event.getY()) - this.lastY;
//                this.upX = (int) event.getX();
//                this.upY = (int) event.getY();
//                moveDrag(dx, dy);
//                Log.d("\u79bb\u5f00\uff1a", this.upX + "----X\u8f74\u5750\u6807");
//                Log.d("\u79bb\u5f00\uff1a", this.upY + "----Y\u8f74\u5750\u6807");
//                break;
            case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
                Log.e("jinkuan", "MotionEvent.ACTION_MOVE....");
                dx = ((int) event.getX()) - this.lastX;
                dy = ((int) event.getY()) - this.lastY;
                break;
        }
        return true;
    }

    private void moveDrag(int dx, int dy) {
        int l = this.viewDrag.getLeft() + dx;
        int b = this.viewDrag.getBottom() + dy;
        int r = this.viewDrag.getRight() + dx;
        int t = this.viewDrag.getTop() + dy;
        if (l < 0) {
            l = 0;
            r = 0 + this.viewDrag.getWidth();
        }
        if (t < 0) {
            t = 0;
            b = 0 + this.viewDrag.getHeight();
        }
        if (r > this.editTextWidth) {
            r = this.editTextWidth;
            l = r - this.viewDrag.getWidth();
        }
        if (b > this.editTextHeight) {
            b = this.editTextHeight;
            t = b - this.viewDrag.getHeight();
        }
        this.viewDrag.layout(l, t, r, b);
    }
}
