package com.gitrose.mobile.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;
import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;

public class ReboundScrollView extends ScrollView {
    private static final int ANIM_TIME = 300;
    private static final float MOVE_FACTOR = 0.5f;
    private boolean canPullDown;
    private boolean canPullUp;
    private View contentView;
    private boolean isMoved;
    private Rect originalRect;
    private float startY;

    public ReboundScrollView(Context context) {
        super(context);
        this.originalRect = new Rect();
        this.canPullDown = false;
        this.canPullUp = false;
        this.isMoved = false;
    }

    public ReboundScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.originalRect = new Rect();
        this.canPullDown = false;
        this.canPullUp = false;
        this.isMoved = false;
    }

    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            this.contentView = getChildAt(0);
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.contentView != null) {
            this.originalRect.set(this.contentView.getLeft(), this.contentView.getTop(), this.contentView.getRight(), this.contentView.getBottom());
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean shouldMove = false;
        if (this.contentView == null) {
            return super.dispatchTouchEvent(ev);
        }
        boolean isTouchOutOfScrollView;
        if (ev.getY() >= ((float) getHeight()) || ev.getY() <= 0.0f) {
            isTouchOutOfScrollView = true;
        } else {
            isTouchOutOfScrollView = false;
        }
        if (!isTouchOutOfScrollView) {
            switch (ev.getAction()) {
                case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
                    this.canPullDown = isCanPullDown();
                    this.canPullUp = isCanPullUp();
                    this.startY = ev.getY();
                    break;
                case 1:
                    boundBack();
                    break;
                case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
                    if (!this.canPullDown && !this.canPullUp) {
                        this.startY = ev.getY();
                        this.canPullDown = isCanPullDown();
                        this.canPullUp = isCanPullUp();
                        break;
                    }
                    int deltaY = (int) (ev.getY() - this.startY);
                    if ((this.canPullDown && deltaY > 0) || ((this.canPullUp && deltaY < 0) || (this.canPullUp && this.canPullDown))) {
                        shouldMove = true;
                    }
                    if (shouldMove) {
                        int offset = (int) (((float) deltaY) * MOVE_FACTOR);
                        this.contentView.layout(this.originalRect.left, this.originalRect.top + offset, this.originalRect.right, this.originalRect.bottom + offset);
                        this.isMoved = true;
                        break;
                    }
                    break;
            }
            return super.dispatchTouchEvent(ev);
        } else if (!this.isMoved) {
            return true;
        } else {
            boundBack();
            return true;
        }
    }

    private void boundBack() {
        if (this.isMoved) {
            TranslateAnimation anim = new TranslateAnimation(0.0f, 0.0f, (float) this.contentView.getTop(), (float) this.originalRect.top);
            anim.setDuration(300);
            this.contentView.startAnimation(anim);
            this.contentView.layout(this.originalRect.left, this.originalRect.top, this.originalRect.right, this.originalRect.bottom);
            this.canPullDown = false;
            this.canPullUp = false;
            this.isMoved = false;
        }
    }

    private boolean isCanPullDown() {
        return getScrollY() == 0 || this.contentView.getHeight() < getHeight() + getScrollY();
    }

    private boolean isCanPullUp() {
        return this.contentView.getHeight() <= getHeight() + getScrollY();
    }
}
