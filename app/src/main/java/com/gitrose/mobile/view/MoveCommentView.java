package com.gitrose.mobile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
//import shouji.gexing.framework.utils.UiUtils;

public class MoveCommentView extends FrameLayout {
    private static int mScreenHeight;
    private RelativeLayout commentView;
    private int mClickTimeout;
    private float mFirstDownX;
    private float mFirstDownY;
    private int mScreenWidth;
    private float mStartX;
    private float mStartY;
    private int mTouchSlop;
    private ImageView picView;
    private int picheight;
    private int picwidth;

    public MoveCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setImageWidthHeight(int picWidth, int picHeight) {
        this.picwidth = picWidth;
        this.picheight = picHeight;
    }

    private void init(Context context) {
        this.mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
//        this.mScreenWidth = UiUtils.getInstance(getContext()).getmScreenWidth();
//        mScreenHeight = UiUtils.getInstance(getContext()).getmScreenWidth();
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
                this.mFirstDownX = x;
                this.mFirstDownY = y;
                this.mStartX = x;
                this.mStartY = y;
                break;
            case 1:
                float deltaX = Math.abs(x - this.mFirstDownX);
                float time = (float) (event.getEventTime() - event.getDownTime());
                if (Math.abs(y - this.mFirstDownY) >= ((float) this.mTouchSlop) || deltaX < ((float) this.mTouchSlop)) {
                    this.commentView.setFocusable(true);
                    this.commentView.setFocusableInTouchMode(true);
                    break;
                }
                this.commentView.setFocusable(true);
                this.commentView.setFocusableInTouchMode(true);
                break;
            case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
                moveCommentView((int) (x - this.mStartX), (int) (y - this.mStartY));
                this.mStartX = x;
                this.mStartY = y;
                this.commentView.setFocusable(false);
                this.commentView.setFocusableInTouchMode(false);
                break;
        }
        return true;
    }

    private void moveCommentView(int deltaX, int deltaY) {
        LayoutParams lp = (LayoutParams) this.commentView.getLayoutParams();
        int moveLeft = lp.leftMargin + deltaX;
        int moveTop = lp.topMargin + deltaY;
        int moveRight = this.commentView.getRight() + deltaX;
        int moveBottom = this.commentView.getBottom() + deltaY;
        if (moveLeft < 0) {
            moveLeft = 0;
            moveRight = 0 + this.commentView.getWidth();
        }
        if (moveRight > this.mScreenWidth) {
            moveLeft = this.mScreenWidth - this.commentView.getWidth();
        }
        if (moveTop < (mScreenHeight - this.picheight) / 2) {
            moveTop = (mScreenHeight - this.picheight) / 2;
        } else if (moveTop > (mScreenHeight - ((mScreenHeight / 2) - (this.picheight / 2))) + this.commentView.getHeight()) {
            moveTop = (mScreenHeight - ((mScreenHeight / 2) - (this.picheight / 2))) + this.commentView.getHeight();
        }
        lp.setMargins(moveLeft, moveTop, 0, 0);
        this.commentView.setLayoutParams(lp);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.commentView = (RelativeLayout) getChildAt(0);
        super.onLayout(changed, left, top, right, bottom);
    }
}
