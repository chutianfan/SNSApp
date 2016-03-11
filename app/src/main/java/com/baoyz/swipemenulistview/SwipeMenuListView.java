package com.baoyz.swipemenulistview;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ListAdapter;

public class SwipeMenuListView extends XListView {
    private static final int TOUCH_STATE_NONE = 0;
    private static final int TOUCH_STATE_X = 1;
    private static final int TOUCH_STATE_Y = 2;
    private int MAX_X;
    private int MAX_Y;
    private SwipeMenuAdapter mAdapter;
    private Interpolator mCloseInterpolator;
    private float mDownX;
    private float mDownY;
    private int mLastOpenPosition;
    private SwipeMenuCreator mMenuCreator;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private OnSwipeListener mOnSwipeListener;
    private Interpolator mOpenInterpolator;
    private int mTouchPosition;
    private int mTouchState;
    private SwipeMenuLayout mTouchView;

    public interface OnMenuItemClickListener {
        void onMenuItemClick(int i, SwipeMenu swipeMenu, int i2);
    }

    public interface OnSwipeListener {
        void onSwipeEnd(int i);

        void onSwipeStart(int i);
    }

    /* renamed from: com.baoyz.swipemenulistview.SwipeMenuListView.1 */
    class C06261 extends SwipeMenuAdapter {
        C06261(Context $anonymous0, ListAdapter $anonymous1) {
            super($anonymous0, $anonymous1);
        }

        public void createMenu(SwipeMenu menu) {
            if (SwipeMenuListView.this.mMenuCreator != null) {
                SwipeMenuListView.this.mMenuCreator.create(menu);
            }
        }

        public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
            if (SwipeMenuListView.this.mOnMenuItemClickListener != null) {
                SwipeMenuListView.this.mOnMenuItemClickListener.onMenuItemClick(view.getPosition(), menu, index);
            }
            if (SwipeMenuListView.this.mTouchView != null) {
                SwipeMenuListView.this.mTouchView.smoothCloseMenu();
            }
        }
    }

    public SwipeMenuListView(Context context) {
        super(context);
        this.MAX_Y = 5;
        this.MAX_X = 3;
        this.mLastOpenPosition = -1;
        init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.MAX_Y = 5;
        this.MAX_X = 3;
        this.mLastOpenPosition = -1;
        init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.MAX_Y = 5;
        this.MAX_X = 3;
        this.mLastOpenPosition = -1;
        init();
    }

    private void init() {
        this.MAX_X = dp2px(this.MAX_X);
        this.MAX_Y = dp2px(this.MAX_Y);
        this.mTouchState = TOUCH_STATE_NONE;
    }

    public void setCustomAdatper(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    public void setAdapter(ListAdapter adapter) {
        setAdapter(adapter, false);
    }

    public void setAdapter(ListAdapter adapter, boolean isNeedChangeMenu) {
        SwipeMenuAdapter c06261 = new C06261(getContext(), adapter);
        this.mAdapter = c06261;
        super.setAdapter(c06261);
    }

    public void setCloseInterpolator(Interpolator interpolator) {
        this.mCloseInterpolator = interpolator;
    }

    public void setOpenInterpolator(Interpolator interpolator) {
        this.mOpenInterpolator = interpolator;
    }

    public Interpolator getOpenInterpolator() {
        return this.mOpenInterpolator;
    }

    public Interpolator getCloseInterpolator() {
        return this.mCloseInterpolator;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() != 0 && this.mTouchView == null) {
            return super.onTouchEvent(ev);
        }
        int actionMasked = MotionEventCompat.getActionMasked(ev);
        switch (ev.getAction()) {
            case TOUCH_STATE_NONE /*0*/:
                int oldPos = this.mTouchPosition;
                this.mDownX = ev.getX();
                this.mDownY = ev.getY();
                this.mTouchState = TOUCH_STATE_NONE;
                this.mTouchPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
                if (this.mTouchPosition == oldPos && this.mTouchView != null && this.mTouchView.isOpen()) {
                    this.mTouchState = TOUCH_STATE_X;
                    this.mTouchView.onSwipe(ev);
                    return true;
                }
                View view = getChildAt(this.mTouchPosition - getFirstVisiblePosition());
                if (this.mTouchView == null || !this.mTouchView.isOpen()) {
                    if (view instanceof SwipeMenuLayout) {
                        this.mTouchView = (SwipeMenuLayout) view;
                    }
                    if (this.mTouchView != null) {
                        this.mTouchView.onSwipe(ev);
                        break;
                    }
                }
                this.mTouchView.smoothCloseMenu();
                this.mTouchView = null;
                return super.onTouchEvent(ev);
                //break;
            case TOUCH_STATE_X /*1*/:
                if (this.mTouchState == TOUCH_STATE_X) {
                    if (this.mTouchView != null) {
                        this.mTouchView.onSwipe(ev);
                        if (!this.mTouchView.isOpen()) {
                            this.mTouchPosition = -1;
                            this.mTouchView = null;
                        }
                    }
                    if (this.mOnSwipeListener != null) {
                        this.mOnSwipeListener.onSwipeEnd(this.mTouchPosition);
                    }
                    ev.setAction(3);
                    super.onTouchEvent(ev);
                    return true;
                }
                break;
            case TOUCH_STATE_Y /*2*/:
                float dy = Math.abs(ev.getY() - this.mDownY);
                float dx = Math.abs(ev.getX() - this.mDownX);
                if (this.mTouchState != TOUCH_STATE_X) {
                    if (this.mTouchState == 0) {
                        if (Math.abs(dy) <= ((float) this.MAX_Y)) {
                            if (dx > ((float) this.MAX_X)) {
                                this.mTouchState = TOUCH_STATE_X;
                                if (this.mOnSwipeListener != null) {
                                    this.mOnSwipeListener.onSwipeStart(this.mTouchPosition);
                                    break;
                                }
                            }
                        }
                        this.mTouchState = TOUCH_STATE_Y;
                        break;
                    }
                }
                if (this.mTouchView != null) {
                    this.mTouchView.onSwipe(ev);
                }
                getSelector().setState(new int[TOUCH_STATE_X]);
                ev.setAction(3);
                super.onTouchEvent(ev);
                return true;
                //break;
        }
        return super.onTouchEvent(ev);
    }

    public void smoothCloseMenu(int position) {
        if (position >= getFirstVisiblePosition() && position <= getLastVisiblePosition()) {
            View view = getChildAt(position - getFirstVisiblePosition());
            if (view instanceof SwipeMenuLayout) {
                this.mTouchPosition = position;
                if (this.mTouchView == null) {
                    this.mLastOpenPosition = position;
                    this.mTouchView = (SwipeMenuLayout) view;
                    this.mTouchView.smoothOpenMenu();
                } else if (this.mTouchView.isOpen()) {
                    this.mTouchView.smoothCloseMenu();
                    if (position != this.mLastOpenPosition) {
                        this.mLastOpenPosition = position;
                        this.mTouchView = (SwipeMenuLayout) view;
                        this.mTouchView.smoothOpenMenu();
                    }
                } else {
                    this.mLastOpenPosition = position;
                    this.mTouchView = (SwipeMenuLayout) view;
                    this.mTouchView.smoothOpenMenu();
                }
            }
        }
    }

    public void smoothOpenMenu(int position) {
        if (position >= getFirstVisiblePosition() && position <= getLastVisiblePosition()) {
            View view = getChildAt(position - getFirstVisiblePosition());
            if (view instanceof SwipeMenuLayout) {
                this.mTouchPosition = position;
                if (this.mTouchView != null && this.mTouchView.isOpen()) {
                    this.mTouchView.smoothCloseMenu();
                }
                this.mTouchView = (SwipeMenuLayout) view;
                this.mTouchView.smoothOpenMenu();
            }
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TOUCH_STATE_X, (float) dp, getContext().getResources().getDisplayMetrics());
    }

    public void setMenuCreator(SwipeMenuCreator menuCreator) {
        this.mMenuCreator = menuCreator;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.mOnSwipeListener = onSwipeListener;
    }
}
