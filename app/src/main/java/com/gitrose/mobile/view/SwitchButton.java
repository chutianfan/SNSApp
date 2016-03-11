package com.gitrose.mobile.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import com.gitrose.mobile.R;

public class SwitchButton extends View {
    public static final int CLICK = 0;
    public static final int DEFAULT_HEIGTH = 45;
    public static final int DEFAULT_WIDTH = 100;
    public static final int LEFT = 1;
    public static final int PER_POST_TIME = 20;
    public static final int RIGHT = 2;
    private final float VELOCITY;
    private float firstDownX;
    private float firstDownY;
    private boolean isChecked;
    private boolean isScroll;
    private float lastDownX;
    private AnimRunnable mAnim;
    private float mAnimMove;
    private int mBorderColor;
    private int mClickTimeout;
    private int mCriclePostion;
    private int mEndCirclePos;
    private Handler mHander;
    private int mHeight;
    private OnCheckedChangeListener mOnCheckChangeListener;
    private Paint mPaint;
    private int mSelectBg;
    private int mSelectCirlceColor;
    private int mStartCriclePos;
    private int mTouchSlop;
    private int mUnSelectBg;
    private int mUnSelectCircleColor;
    private int mWidth;
    private int status;

    class AnimRunnable implements Runnable {
        AnimRunnable() {
        }

        public void run() {
            SwitchButton.this.mHander.postDelayed(this, 20);
            moveView();
        }

        private void moveView() {
            SwitchButton switchButton;
            if ((SwitchButton.this.status == 0 && SwitchButton.this.isChecked) || SwitchButton.this.status == SwitchButton.LEFT) {
                switchButton = SwitchButton.this;
                switchButton.mCriclePostion = (int) (((float) switchButton.mCriclePostion) - SwitchButton.this.mAnimMove);
                if (SwitchButton.this.mCriclePostion < SwitchButton.this.mHeight / SwitchButton.RIGHT) {
                    SwitchButton.this.mCriclePostion = SwitchButton.this.mHeight / SwitchButton.RIGHT;
                    stopView(false);
                }
            } else if ((SwitchButton.this.status == 0 && !SwitchButton.this.isChecked) || SwitchButton.this.status == SwitchButton.RIGHT) {
                switchButton = SwitchButton.this;
                switchButton.mCriclePostion = (int) (((float) switchButton.mCriclePostion) + SwitchButton.this.mAnimMove);
                if (SwitchButton.this.mCriclePostion > SwitchButton.this.mWidth - (SwitchButton.this.mHeight / SwitchButton.RIGHT)) {
                    SwitchButton.this.mCriclePostion = SwitchButton.this.mWidth - (SwitchButton.this.mHeight / SwitchButton.RIGHT);
                    stopView(true);
                }
            }
            SwitchButton.this.invalidate();
        }

        private void stopView(boolean endChecked) {
            SwitchButton.this.mHander.removeCallbacks(SwitchButton.this.mAnim);
            SwitchButton.this.isScroll = false;
            SwitchButton.this.isChecked = endChecked;
            if (SwitchButton.this.mOnCheckChangeListener != null) {
                SwitchButton.this.mOnCheckChangeListener.onCheckedChanged(SwitchButton.this, SwitchButton.this.isChecked);
            }
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(SwitchButton switchButton, boolean z);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.VELOCITY = 350.0f;
        Resources res = getResources();
        int defaultSelectBg = res.getColor(R.color.default_switch_button_select_bg);
        int defaultUnSelectBg = res.getColor(R.color.default_switch_button_unselect_bg);
        int defaultBorderColor = res.getColor(R.color.default_switch_button_border_color);
        int defaultSelectCircleColor = res.getColor(R.color.default_switch_button_select_color);
        int defaultUnSelectCircleColor = res.getColor(R.color.default_switch_button_unselect_color);
        //TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);
//        this.mSelectBg = a.getColor(CLICK, defaultSelectBg);
//        this.mUnSelectBg = a.getColor(LEFT, defaultUnSelectBg);
//        this.mBorderColor = a.getColor(RIGHT, defaultBorderColor);
//        this.mSelectCirlceColor = a.getColor(3, defaultSelectCircleColor);
//        this.mUnSelectCircleColor = a.getColor(4, defaultUnSelectCircleColor);
//        this.isChecked = a.getBoolean(5, false);
//        a.recycle();
        initView(context);
    }

    private void initView(Context context) {
        this.mPaint = new Paint(LEFT);
        this.mAnimMove = (float) (((int) ((350.0f * getResources().getDisplayMetrics().density) + 0.5f)) / DEFAULT_WIDTH);
        this.mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mHander = new Handler();
        this.mAnim = new AnimRunnable();
    }

    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rect = new RectF(0.0f, 0.0f, (float) this.mWidth, (float) this.mHeight);
        if (this.isChecked) {
            this.mPaint.setStyle(Style.FILL);
            this.mPaint.setColor(this.mSelectBg);
            canvas.drawRoundRect(rect, (float) (this.mHeight / RIGHT), (float) (this.mHeight / RIGHT), this.mPaint);
            this.mPaint.setColor(this.mSelectCirlceColor);
            canvas.drawCircle((float) this.mCriclePostion, (float) (this.mHeight / RIGHT), (float) ((this.mHeight / RIGHT) - 2), this.mPaint);
            return;
        }
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setColor(this.mBorderColor);
        canvas.drawRoundRect(rect, (float) (this.mHeight / RIGHT), (float) (this.mHeight / RIGHT), this.mPaint);
        RectF innerRect = new RectF(2.0f, 2.0f, (float) (this.mWidth - 2), (float) (this.mHeight - 2));
        this.mPaint.setColor(this.mUnSelectBg);
        canvas.drawRoundRect(innerRect, (float) ((this.mHeight / RIGHT) - 2), (float) ((this.mHeight / RIGHT) - 2), this.mPaint);
        this.mPaint.setColor(this.mUnSelectCircleColor);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth(2.0f);
        canvas.drawCircle((float) this.mCriclePostion, (float) (this.mHeight / RIGHT), (float) ((this.mHeight / RIGHT) - 2), this.mPaint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case CLICK /*0*/:
                getParent().requestDisallowInterceptTouchEvent(true);
                if (this.isScroll) {
                    return false;
                }
                this.firstDownX = x;
                this.firstDownY = y;
                this.lastDownX = x;
                this.mCriclePostion = this.isChecked ? this.mEndCirclePos : this.mStartCriclePos;
                return true;
            case LEFT /*1*/:
                float totalY = y - this.firstDownY;
                float time = (float) (event.getEventTime() - event.getDownTime());
                if (x - this.firstDownX >= ((float) this.mTouchSlop) || totalY >= ((float) this.mTouchSlop) || time >= ((float) this.mClickTimeout)) {
                    setCriclePositon(x - this.lastDownX);
                    this.status = this.mCriclePostion < this.mWidth / RIGHT ? LEFT : RIGHT;
                    startAutoScroll();
                    return true;
                }
                this.status = CLICK;
                startAutoScroll();
                return true;
            case RIGHT /*2*/:
                setCriclePositon(x - this.lastDownX);
                this.lastDownX = x;
                invalidate();
                return true;
            default:
                return true;
        }
    }

    private void startAutoScroll() {
        this.isScroll = true;
        this.mHander.postDelayed(this.mAnim, 20);
    }

    private synchronized void setCriclePositon(float delaX) {
        int pos = (int) (((float) this.mCriclePostion) + delaX);
        if (pos < this.mHeight / RIGHT) {
            this.mCriclePostion = this.mHeight / RIGHT;
        } else if (pos > this.mWidth - (this.mHeight / RIGHT)) {
            this.mCriclePostion = this.mWidth - (this.mHeight / RIGHT);
        } else {
            this.mCriclePostion = pos;
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == 0 || widthMode == ExploreByTouchHelper.INVALID_ID) {
            this.mWidth = DEFAULT_WIDTH;
        } else {
            this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == 0 || heightMode == ExploreByTouchHelper.INVALID_ID) {
            this.mHeight = DEFAULT_HEIGTH;
        } else {
            this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
        }
        this.mStartCriclePos = this.mHeight / RIGHT;
        this.mEndCirclePos = (this.mWidth - (this.mHeight / RIGHT)) - 1;
        this.mCriclePostion = this.isChecked ? this.mEndCirclePos : this.mStartCriclePos;
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
        invalidate();
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckChangeListener) {
        this.mOnCheckChangeListener = onCheckChangeListener;
    }
}
