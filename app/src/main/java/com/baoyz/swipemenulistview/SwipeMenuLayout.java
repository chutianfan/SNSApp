package com.baoyz.swipemenulistview;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import com.gitrose.mobile.view.AutoCoverFlowHelper;

public class SwipeMenuLayout extends FrameLayout {
    private static final int CONTENT_VIEW_ID = 1;
    private static final int MENU_VIEW_ID = 2;
    private static final int STATE_CLOSE = 0;
    private static final int STATE_OPEN = 1;
    private int MAX_VELOCITYX;
    private int MIN_FLING;
    private boolean isFling;
    private int mBaseX;
    private Interpolator mCloseInterpolator;
    private ScrollerCompat mCloseScroller;
    private View mContentView;
    private int mDownX;
    private GestureDetectorCompat mGestureDetector;
    private OnGestureListener mGestureListener;
    private SwipeMenuView mMenuView;
    private Interpolator mOpenInterpolator;
    private ScrollerCompat mOpenScroller;
    private int position;
    private int state;

    /* renamed from: com.baoyz.swipemenulistview.SwipeMenuLayout.1 */
    class C01131 extends SimpleOnGestureListener {
        C01131() {
        }

        public boolean onDown(MotionEvent e) {
            SwipeMenuLayout.this.isFling = false;
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > ((float) SwipeMenuLayout.this.MIN_FLING) && velocityX < ((float) SwipeMenuLayout.this.MAX_VELOCITYX)) {
                SwipeMenuLayout.this.isFling = true;
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    public SwipeMenuLayout(View contentView, SwipeMenuView menuView) {
        this(contentView, menuView, null, null);
    }

    public SwipeMenuLayout(View contentView, SwipeMenuView menuView, Interpolator closeInterpolator, Interpolator openInterpolator) {
        super(contentView.getContext());
        this.state = STATE_CLOSE;
        this.MIN_FLING = dp2px(15);
        this.MAX_VELOCITYX = -dp2px(AutoCoverFlowHelper.RAPID_DELAY_TIME);
        this.mCloseInterpolator = closeInterpolator;
        this.mOpenInterpolator = openInterpolator;
        this.mContentView = contentView;
        this.mMenuView = menuView;
        this.mMenuView.setLayout(this);
        init();
    }

    private SwipeMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.state = STATE_CLOSE;
        this.MIN_FLING = dp2px(15);
        this.MAX_VELOCITYX = -dp2px(AutoCoverFlowHelper.RAPID_DELAY_TIME);
    }

    private SwipeMenuLayout(Context context) {
        super(context);
        this.state = STATE_CLOSE;
        this.MIN_FLING = dp2px(15);
        this.MAX_VELOCITYX = -dp2px(AutoCoverFlowHelper.RAPID_DELAY_TIME);
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
        this.mMenuView.setPosition(position);
    }

    private void init() {
        setLayoutParams(new LayoutParams(-1, -2));
        this.mGestureListener = new C01131();
        this.mGestureDetector = new GestureDetectorCompat(getContext(), this.mGestureListener);
        if (this.mCloseInterpolator != null) {
            this.mCloseScroller = ScrollerCompat.create(getContext(), this.mCloseInterpolator);
        } else {
            this.mCloseScroller = ScrollerCompat.create(getContext());
        }
        if (this.mOpenInterpolator != null) {
            this.mOpenScroller = ScrollerCompat.create(getContext(), this.mOpenInterpolator);
        } else {
            this.mOpenScroller = ScrollerCompat.create(getContext());
        }
        this.mContentView.setLayoutParams(new LayoutParams(-1, -2));
        if (this.mContentView.getId() < STATE_OPEN) {
            this.mContentView.setId(STATE_OPEN);
        }
        this.mMenuView.setId(MENU_VIEW_ID);
        this.mMenuView.setLayoutParams(new LayoutParams(-2, -2));
        addView(this.mContentView);
        addView(this.mMenuView);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public boolean onSwipe(MotionEvent event) {
        this.mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case STATE_CLOSE /*0*/:
                this.mDownX = (int) event.getX();
                this.isFling = false;
                break;
            case STATE_OPEN /*1*/:
                if (this.isFling || ((float) this.mDownX) - event.getX() > ((float) (this.mMenuView.getWidth() / MENU_VIEW_ID))) {
                    smoothOpenMenu();
                    break;
                }
                smoothCloseMenu();
                return false;
//                break;
            case MENU_VIEW_ID /*2*/:
                int dis = (int) (((float) this.mDownX) - event.getX());
                if (this.state == STATE_OPEN) {
                    dis += this.mMenuView.getWidth();
                }
                swipe(dis);
                break;
        }
        return true;
    }

    public boolean isOpen() {
        return this.state == STATE_OPEN;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private void swipe(int dis) {
        if (dis > this.mMenuView.getWidth()) {
            dis = this.mMenuView.getWidth();
        }
        if (dis < 0) {
            dis = STATE_CLOSE;
        }
        this.mContentView.layout(-dis, this.mContentView.getTop(), this.mContentView.getWidth() - dis, getMeasuredHeight());
        this.mMenuView.layout(this.mContentView.getWidth() - dis, this.mMenuView.getTop(), (this.mContentView.getWidth() + this.mMenuView.getWidth()) - dis, this.mMenuView.getBottom());
    }

    public void computeScroll() {
        if (this.state == STATE_OPEN) {
            if (this.mOpenScroller.computeScrollOffset()) {
                swipe(this.mOpenScroller.getCurrX());
                postInvalidate();
            }
        } else if (this.mCloseScroller.computeScrollOffset()) {
            swipe(this.mBaseX - this.mCloseScroller.getCurrX());
            postInvalidate();
        }
    }

    public void smoothCloseMenu() {
        this.state = STATE_CLOSE;
        this.mBaseX = -this.mContentView.getLeft();
        this.mCloseScroller.startScroll(STATE_CLOSE, STATE_CLOSE, this.mBaseX, STATE_CLOSE, 350);
        postInvalidate();
    }

    public void smoothOpenMenu() {
        this.state = STATE_OPEN;
        this.mOpenScroller.startScroll(-this.mContentView.getLeft(), STATE_CLOSE, this.mMenuView.getWidth(), STATE_CLOSE, 350);
        postInvalidate();
    }

    public void closeMenu() {
        if (this.mCloseScroller.computeScrollOffset()) {
            this.mCloseScroller.abortAnimation();
        }
        if (this.state == STATE_OPEN) {
            this.state = STATE_CLOSE;
            swipe(STATE_CLOSE);
        }
    }

    public void openMenu() {
        if (this.state == 0) {
            this.state = STATE_OPEN;
            swipe(this.mMenuView.getWidth());
        }
    }

    public View getContentView() {
        return this.mContentView;
    }

    public SwipeMenuView getMenuView() {
        return this.mMenuView;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(STATE_OPEN, (float) dp, getContext().getResources().getDisplayMetrics());
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mMenuView.measure(MeasureSpec.makeMeasureSpec(STATE_CLOSE, STATE_CLOSE), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.mContentView.layout(STATE_CLOSE, STATE_CLOSE, getMeasuredWidth(), this.mContentView.getMeasuredHeight());
        this.mMenuView.layout(getMeasuredWidth(), STATE_CLOSE, getMeasuredWidth() + this.mMenuView.getMeasuredWidth(), this.mContentView.getMeasuredHeight());
    }

    public void setMenuHeight(int measuredHeight) {
        Log.i("byz", "pos = " + this.position + ", height = " + measuredHeight);
        LayoutParams params = (LayoutParams) this.mMenuView.getLayoutParams();
        if (params.height != measuredHeight) {
            params.height = measuredHeight;
            this.mMenuView.setLayoutParams(this.mMenuView.getLayoutParams());
        }
    }
}
