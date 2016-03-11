package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import com.android.volley.DefaultRetryPolicy;
import com.handmark.pulltorefresh.library.internal.FlipLoadingLayout;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;
import com.handmark.pulltorefresh.library.internal.RotateLoadingLayout;
import com.handmark.pulltorefresh.library.internal.Utils;
import com.handmark.pulltorefresh.library.internal.ViewCompat;
import com.gitrose.mobile.R;

import static com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_START;

public abstract class PullToRefreshBase<T extends View> extends LinearLayout implements IPullToRefresh<T> {
    private static /* synthetic */ int[] f4893x57a4d715 = null;
    private static /* synthetic */ int[] f4894x9c6cb41e = null;
    private static /* synthetic */ int[] f4895x9d4cd55f = null;
    static final boolean DEBUG = false;
    static final int DEMO_SCROLL_INTERVAL = 225;
    private static final int DURATION = 200;
    static final float FRICTION = 2.0f;
    private static final int LEN = 200;
    static final String LOG_TAG = "PullToRefresh";
    private static final int MAX_DY = 200;
    public static final int SMOOTH_SCROLL_DURATION_MS = 200;
    public static final int SMOOTH_SCROLL_LONG_DURATION_MS = 325;
    static final String STATE_CURRENT_MODE = "ptr_current_mode";
    static final String STATE_MODE = "ptr_mode";
    static final String STATE_SCROLLING_REFRESHING_ENABLED = "ptr_disable_scrolling";
    static final String STATE_SHOW_REFRESHING_VIEW = "ptr_show_refreshing_view";
    static final String STATE_STATE = "ptr_state";
    static final String STATE_SUPER = "ptr_super";
    static final boolean USE_HW_LAYERS = false;
    float changeY;
    float curY;
    float currentX;
    float currentY;
    long down_time;
    int imageH;
    ImageView imageView;
    int imageViewH;
    int left;
    private Mode mCurrentMode;
    private SmoothScrollRunnable mCurrentSmoothScrollRunnable;
    private boolean mFilterTouchEvents;
    private LoadingLayout mFooterLayout;
    private LoadingLayout mHeaderLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    private float mLastMotionX;
    private float mLastMotionY;
    private boolean mLayoutVisibilityChangesEnabled;
    private AnimationStyle mLoadingAnimationStyle;
    private Mode mMode;
    private OnPullEventListener<T> mOnPullEventListener;
    private OnRefreshListener<T> mOnRefreshListener;
    private OnRefreshListener2<T> mOnRefreshListener2;
    private OnRefreshListener3 mOnRefreshListener3;
    private boolean mOverScrollEnabled;
    T mRefreshableView;
    private FrameLayout mRefreshableViewWrapper;
    private Interpolator mScrollAnimationInterpolator;
    private Scroller mScroller;
    private boolean mScrollingWhileRefreshingEnabled;
    private boolean mShowViewWhileRefreshing;
    private State mState;
    private int mTouchSlop;
    int rootH;
    int rootW;
    boolean scrollerType;
    float startX;
    float startY;
    TouchTool tool;
    int top;
    long up_time;

    /* renamed from: com.handmark.pulltorefresh.library.PullToRefreshBase.2 */
    class C03892 implements Runnable {
        C03892() {
        }

        public void run() {
            PullToRefreshBase.this.requestLayout();
        }
    }

    public enum AnimationStyle {
        ROTATE,
        FLIP;

        static AnimationStyle getDefault() {
            return FLIP;
        }

        static AnimationStyle mapIntToValue(int modeInt) {
            switch (modeInt) {
                case 1:
                    return FLIP;
                default:
                    return ROTATE;
            }
        }

        LoadingLayout createLoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs) {
            switch (mode) {
                case PULL_FROM_START:
                    return new FlipLoadingLayout(context, mode, scrollDirection, attrs);
                default:
                    return new RotateLoadingLayout(context, mode, scrollDirection, attrs);
            }
//            return new FlipLoadingLayout(context, mode, scrollDirection, attrs);
        }
    }

    public enum Mode {
        DISABLED(0),
        PULL_FROM_START(1),
        PULL_FROM_END(2),
        BOTH(3),
        MANUAL_REFRESH_ONLY(4);
        
        public static Mode PULL_DOWN_TO_REFRESH;
        public static Mode PULL_UP_TO_REFRESH;
        private int mIntValue;

        static {
            PULL_DOWN_TO_REFRESH = PULL_FROM_START;
            PULL_UP_TO_REFRESH = PULL_FROM_END;
        }

        static Mode mapIntToValue(int modeInt) {
            for (Mode value : values()) {
                if (modeInt == value.getIntValue()) {
                    return value;
                }
            }
            return getDefault();
        }

        static Mode getDefault() {
            return PULL_FROM_START;
        }

        private Mode(int modeInt) {
            this.mIntValue = modeInt;
        }

        boolean permitsPullToRefresh() {
            return (this == DISABLED || this == MANUAL_REFRESH_ONLY) ? PullToRefreshBase.DEBUG : true;
        }

        public boolean showHeaderLoadingLayout() {
            return (this == PULL_FROM_START || this == BOTH) ? true : PullToRefreshBase.DEBUG;
        }

        public boolean showFooterLoadingLayout() {
            return (this == PULL_FROM_END || this == BOTH || this == MANUAL_REFRESH_ONLY) ? true : PullToRefreshBase.DEBUG;
        }

        int getIntValue() {
            return this.mIntValue;
        }
    }

    public interface OnLastItemVisibleListener {
        void onLastItemVisible();
    }

    public interface OnPullEventListener<V extends View> {
        void onPullEvent(PullToRefreshBase<V> pullToRefreshBase, State state, Mode mode);
    }

    public interface OnRefreshListener2<V extends View> {
        void onPullDownToRefresh(PullToRefreshBase<V> pullToRefreshBase);

        void onPullUpToRefresh(PullToRefreshBase<V> pullToRefreshBase);
    }

    public interface OnRefreshListener3 {
        void onRefresh(boolean z);

        void onStartRefresh(int i);
    }

    public interface OnRefreshListener<V extends View> {
        void onRefresh(PullToRefreshBase<V> pullToRefreshBase);
    }

    interface OnSmoothScrollFinishedListener {
        void onSmoothScrollFinished();
    }

    public enum Orientation {
        VERTICAL,
        HORIZONTAL
    }

    final class SmoothScrollRunnable implements Runnable {
        private boolean mContinueRunning;
        private int mCurrentY;
        private final long mDuration;
        private final Interpolator mInterpolator;
        private OnSmoothScrollFinishedListener mListener;
        private final int mScrollFromY;
        private final int mScrollToY;
        private long mStartTime;

        public SmoothScrollRunnable(int fromY, int toY, long duration, OnSmoothScrollFinishedListener listener) {
            this.mContinueRunning = true;
            this.mStartTime = -1;
            this.mCurrentY = -1;
            this.mScrollFromY = fromY;
            this.mScrollToY = toY;
            this.mInterpolator = PullToRefreshBase.this.mScrollAnimationInterpolator;
            this.mDuration = duration;
            this.mListener = listener;
        }

        public void run() {
            if (this.mStartTime == -1) {
                this.mStartTime = System.currentTimeMillis();
            } else {
                this.mCurrentY = this.mScrollFromY - Math.round(((float) (this.mScrollFromY - this.mScrollToY)) * this.mInterpolator.getInterpolation(((float) Math.max(Math.min(((System.currentTimeMillis() - this.mStartTime) * 1000) / this.mDuration, 1000), 0)) / 1000.0f));
                PullToRefreshBase.this.setHeaderScroll(this.mCurrentY);
            }
            if (this.mContinueRunning && this.mScrollToY != this.mCurrentY) {
                ViewCompat.postOnAnimation(PullToRefreshBase.this, this);
            } else if (this.mListener != null) {
                this.mListener.onSmoothScrollFinished();
            }
        }

        public void stop() {
            this.mContinueRunning = PullToRefreshBase.DEBUG;
            PullToRefreshBase.this.removeCallbacks(this);
        }
    }

    public enum State {
        RESET(0),
        PULL_TO_REFRESH(1),
        RELEASE_TO_REFRESH(2),
        REFRESHING(8),
        MANUAL_REFRESHING(9),
        OVERSCROLLING(16);
        
        private int mIntValue;

        static State mapIntToValue(int stateInt) {
            for (State value : values()) {
                if (stateInt == value.getIntValue()) {
                    return value;
                }
            }
            return RESET;
        }

        private State(int intValue) {
            this.mIntValue = intValue;
        }

        int getIntValue() {
            return this.mIntValue;
        }
    }

    public class TouchTool {
        private int startX;
        private int startY;

        public TouchTool(int startX, int startY, int endX, int endY) {
            this.startX = startX;
            this.startY = startY;
        }

        public int getScrollX(float dx) {
            return (int) (((float) this.startX) + (dx / 2.5f));
        }

        public int getScrollY(float dy) {
            return (int) (((float) this.startY) + (dy / 2.5f));
        }
    }

    /* renamed from: com.handmark.pulltorefresh.library.PullToRefreshBase.1 */
    class C12911 implements OnSmoothScrollFinishedListener {
        C12911() {
        }

        public void onSmoothScrollFinished() {
            PullToRefreshBase.this.callRefreshListener();
        }
    }

    /* renamed from: com.handmark.pulltorefresh.library.PullToRefreshBase.3 */
    class C12923 implements OnSmoothScrollFinishedListener {
        C12923() {
        }

        public void onSmoothScrollFinished() {
            PullToRefreshBase.this.smoothScrollTo(0, 200, 225, null);
        }
    }

    protected abstract T createRefreshableView(Context context, AttributeSet attributeSet);

    public abstract Orientation getPullToRefreshScrollDirection();

    protected abstract boolean isReadyForPullEnd();

    protected abstract boolean isReadyForPullStart();

    static /* synthetic */ int[] m5244x57a4d715() {
        int[] iArr = f4893x57a4d715;
        if (iArr == null) {
            iArr = new int[Mode.values().length];
            try {
                iArr[Mode.BOTH.ordinal()] = 4;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Mode.DISABLED.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Mode.MANUAL_REFRESH_ONLY.ordinal()] = 5;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[Mode.PULL_FROM_END.ordinal()] = 3;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[PULL_FROM_START.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            f4893x57a4d715 = iArr;
        }
        return iArr;
    }

    static /* synthetic */ int[] m5245x9c6cb41e() {
        int[] iArr = f4894x9c6cb41e;
        if (iArr == null) {
            iArr = new int[Orientation.values().length];
            try {
                iArr[Orientation.HORIZONTAL.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Orientation.VERTICAL.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            f4894x9c6cb41e = iArr;
        }
        return iArr;
    }

    static /* synthetic */ int[] m5246x9d4cd55f() {
        int[] iArr = f4895x9d4cd55f;
        if (iArr == null) {
            iArr = new int[State.values().length];
            try {
                iArr[State.MANUAL_REFRESHING.ordinal()] = 5;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[State.OVERSCROLLING.ordinal()] = 6;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[State.PULL_TO_REFRESH.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[State.REFRESHING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[State.RELEASE_TO_REFRESH.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[State.RESET.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
            f4895x9d4cd55f = iArr;
        }
        return iArr;
    }

    public PullToRefreshBase(Context context) {
        super(context);
        this.mIsBeingDragged = DEBUG;
        this.mState = State.RESET;
        this.mMode = Mode.getDefault();
        this.mShowViewWhileRefreshing = true;
        this.mScrollingWhileRefreshingEnabled = DEBUG;
        this.mFilterTouchEvents = true;
        this.mOverScrollEnabled = true;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mLoadingAnimationStyle = AnimationStyle.getDefault();
        init(context, null);
    }

    public PullToRefreshBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIsBeingDragged = DEBUG;
        this.mState = State.RESET;
        this.mMode = Mode.getDefault();
        this.mShowViewWhileRefreshing = true;
        this.mScrollingWhileRefreshingEnabled = DEBUG;
        this.mFilterTouchEvents = true;
        this.mOverScrollEnabled = true;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mLoadingAnimationStyle = AnimationStyle.getDefault();
        init(context, attrs);
    }

    public PullToRefreshBase(Context context, Mode mode) {
        super(context);
        this.mIsBeingDragged = DEBUG;
        this.mState = State.RESET;
        this.mMode = Mode.getDefault();
        this.mShowViewWhileRefreshing = true;
        this.mScrollingWhileRefreshingEnabled = DEBUG;
        this.mFilterTouchEvents = true;
        this.mOverScrollEnabled = true;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mLoadingAnimationStyle = AnimationStyle.getDefault();
        this.mMode = mode;
        init(context, null);
    }

    public PullToRefreshBase(Context context, Mode mode, AnimationStyle animStyle) {
        super(context);
        this.mIsBeingDragged = DEBUG;
        this.mState = State.RESET;
        this.mMode = Mode.getDefault();
        this.mShowViewWhileRefreshing = true;
        this.mScrollingWhileRefreshingEnabled = DEBUG;
        this.mFilterTouchEvents = true;
        this.mOverScrollEnabled = true;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mLoadingAnimationStyle = AnimationStyle.getDefault();
        this.mMode = mode;
        this.mLoadingAnimationStyle = animStyle;
        init(context, null);
    }

    public void addView(View child, int index, LayoutParams params) {
        T refreshableView = getRefreshableView();
        if (refreshableView instanceof ViewGroup) {
            ((ViewGroup) refreshableView).addView(child, index, params);
            return;
        }
        throw new UnsupportedOperationException("Refreshable View is not a ViewGroup so can't addView");
    }

    public final boolean demo() {
        if (this.mMode.showHeaderLoadingLayout() && isReadyForPullStart()) {
            smoothScrollToAndBack((-getHeaderSize()) * 2);
            return true;
        } else if (!this.mMode.showFooterLoadingLayout() || !isReadyForPullEnd()) {
            return DEBUG;
        } else {
            smoothScrollToAndBack(getFooterSize() * 2);
            return true;
        }
    }

    public final Mode getCurrentMode() {
        return this.mCurrentMode;
    }

    public final boolean getFilterTouchEvents() {
        return this.mFilterTouchEvents;
    }

    public final ILoadingLayout getLoadingLayoutProxy() {
        return getLoadingLayoutProxy(true, true);
    }

    public final ILoadingLayout getLoadingLayoutProxy(boolean includeStart, boolean includeEnd) {
        return createLoadingLayoutProxy(includeStart, includeEnd);
    }

    public final Mode getMode() {
        return this.mMode;
    }

    public final T getRefreshableView() {
        return this.mRefreshableView;
    }

    public final boolean getShowViewWhileRefreshing() {
        return this.mShowViewWhileRefreshing;
    }

    public final State getState() {
        return this.mState;
    }

    public final boolean isDisableScrollingWhileRefreshing() {
        return isScrollingWhileRefreshingEnabled() ? DEBUG : true;
    }

    public final boolean isPullToRefreshEnabled() {
        return this.mMode.permitsPullToRefresh();
    }

    public final boolean isPullToRefreshOverScrollEnabled() {
        return (VERSION.SDK_INT >= 9 && this.mOverScrollEnabled && OverscrollHelper.isAndroidOverScrollEnabled(this.mRefreshableView)) ? true : DEBUG;
    }

    public final boolean isRefreshing() {
        return (this.mState == State.REFRESHING || this.mState == State.MANUAL_REFRESHING) ? true : DEBUG;
    }

    public final boolean isScrollingWhileRefreshingEnabled() {
        return this.mScrollingWhileRefreshingEnabled;
    }

    public final boolean onInterceptTouchEvent(MotionEvent event) {
        if (!isPullToRefreshEnabled()) {
            return DEBUG;
        }
        int action = event.getAction();
        if (action == 3 || action == 1) {
            this.mIsBeingDragged = DEBUG;
            return DEBUG;
        } else if (action != 0 && this.mIsBeingDragged) {
            return true;
        } else {
            switch (action) {
                case 0:
                    if (isReadyForPull()) {
                        float y = event.getY();
                        this.mInitialMotionY = y;
                        this.mLastMotionY = y;
                        y = event.getX();
                        this.mInitialMotionX = y;
                        this.mLastMotionX = y;
                        this.mIsBeingDragged = DEBUG;
                        break;
                    }
                    break;
                case 2:
                    if (this.mScrollingWhileRefreshingEnabled || !isRefreshing()) {
                        if (isReadyForPull()) {
                            float diff;
                            float y2 = event.getY();
                            float x = event.getX();
                            float oppositeDiff;
                            switch (m5245x9c6cb41e()[getPullToRefreshScrollDirection().ordinal()]) {
                                case 2:
                                    diff = x - this.mLastMotionX;
                                    oppositeDiff = y2 - this.mLastMotionY;
                                    break;
                                default:
                                    diff = y2 - this.mLastMotionY;
                                    oppositeDiff = x - this.mLastMotionX;
                                    break;
                            }
                            float absDiff = Math.abs(diff);
                            if (absDiff > ((float) this.mTouchSlop) && (!this.mFilterTouchEvents || absDiff > Math.abs(oppositeDiff))) {
                                if (!this.mMode.showHeaderLoadingLayout() || diff < DefaultRetryPolicy.DEFAULT_BACKOFF_MULT || !isReadyForPullStart()) {
                                    if (this.mMode.showFooterLoadingLayout() && diff <= -1.0f && isReadyForPullEnd()) {
                                        this.mLastMotionY = y2;
                                        this.mLastMotionX = x;
                                        this.mIsBeingDragged = true;
                                        if (this.mMode == Mode.BOTH) {
                                            this.mCurrentMode = Mode.PULL_FROM_END;
                                            break;
                                        }
                                    }
                                }
                                this.mLastMotionY = y2;
                                this.mLastMotionX = x;
                                this.mIsBeingDragged = true;
                                if (this.mMode == Mode.BOTH) {
                                    this.mCurrentMode = PULL_FROM_START;
                                    break;
                                }
                            }
                        }
                    }
                    return true;
            }
            return this.mIsBeingDragged;
        }
    }

    public final void onRefreshComplete() {
        if (isRefreshing()) {
            setState(State.RESET, new boolean[0]);
        }
    }

    public final boolean onTouchEvent(MotionEvent event) {
        if (!isPullToRefreshEnabled()) {
            return DEBUG;
        }
        if (!this.mScrollingWhileRefreshingEnabled && isRefreshing()) {
            return true;
        }
        if (event.getAction() == 0 && event.getEdgeFlags() != 0) {
            return DEBUG;
        }
        switch (event.getAction()) {
            case 0:
                if (!isReadyForPull()) {
                    return DEBUG;
                }
                float y = event.getY();
                this.mInitialMotionY = y;
                this.mLastMotionY = y;
                y = event.getX();
                this.mInitialMotionX = y;
                this.mLastMotionX = y;
                return true;
            case 1:
            case 3:
                if (!this.mIsBeingDragged) {
                    return DEBUG;
                }
                this.mIsBeingDragged = DEBUG;
                if (this.mState == State.RELEASE_TO_REFRESH && (this.mOnRefreshListener != null || this.mOnRefreshListener2 != null)) {
                    setState(State.REFRESHING, true);
                    return true;
                } else if (isRefreshing()) {
                    smoothScrollTo(0);
                    return true;
                } else {
                    setState(State.RESET, new boolean[0]);
                    return true;
                }
            case 2:
                if (!this.mIsBeingDragged) {
                    return DEBUG;
                }
                this.mLastMotionY = event.getY();
                this.mLastMotionX = event.getX();
                pullEvent();
                return true;
            default:
                return DEBUG;
        }
    }

    public final void setScrollingWhileRefreshingEnabled(boolean allowScrollingWhileRefreshing) {
        this.mScrollingWhileRefreshingEnabled = allowScrollingWhileRefreshing;
    }

    public void setDisableScrollingWhileRefreshing(boolean disableScrollingWhileRefreshing) {
        setScrollingWhileRefreshingEnabled(disableScrollingWhileRefreshing ? DEBUG : true);
    }

    public final void setFilterTouchEvents(boolean filterEvents) {
        this.mFilterTouchEvents = filterEvents;
    }

    public void setLastUpdatedLabel(CharSequence label) {
        getLoadingLayoutProxy().setLastUpdatedLabel(label);
    }

    public void setLoadingDrawable(Drawable drawable) {
        getLoadingLayoutProxy().setLoadingDrawable(drawable);
    }

    public void setLoadingDrawable(Drawable drawable, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setLoadingDrawable(drawable);
    }

    public void setLongClickable(boolean longClickable) {
        getRefreshableView().setLongClickable(longClickable);
    }

    public final void setMode(Mode mode) {
        if (mode != this.mMode) {
            this.mMode = mode;
            updateUIForMode();
        }
    }

    public void setOnPullEventListener(OnPullEventListener<T> listener) {
        this.mOnPullEventListener = listener;
    }

    public final void setOnRefreshListener(OnRefreshListener<T> listener) {
        this.mOnRefreshListener = listener;
        this.mOnRefreshListener2 = null;
    }

    public final void setOnRefreshListener(OnRefreshListener2<T> listener) {
        this.mOnRefreshListener2 = listener;
        this.mOnRefreshListener = null;
    }

    public void setPullLabel(CharSequence pullLabel) {
        getLoadingLayoutProxy().setPullLabel(pullLabel);
    }

    public void setPullLabel(CharSequence pullLabel, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setPullLabel(pullLabel);
    }

    public final void setPullToRefreshEnabled(boolean enable) {
        setMode(enable ? Mode.getDefault() : Mode.DISABLED);
    }

    public final void setPullToRefreshOverScrollEnabled(boolean enabled) {
        this.mOverScrollEnabled = enabled;
    }

    public final void setRefreshing() {
        setRefreshing(true);
    }

    public final void setRefreshing(boolean doScroll) {
        if (!isRefreshing()) {
            setState(State.MANUAL_REFRESHING, doScroll);
        }
    }

    public void setRefreshingLabel(CharSequence refreshingLabel) {
        getLoadingLayoutProxy().setRefreshingLabel(refreshingLabel);
    }

    public void setRefreshingLabel(CharSequence refreshingLabel, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setRefreshingLabel(refreshingLabel);
    }

    public void setReleaseLabel(CharSequence releaseLabel) {
        setReleaseLabel(releaseLabel, Mode.BOTH);
    }

    public void setReleaseLabel(CharSequence releaseLabel, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setReleaseLabel(releaseLabel);
    }

    public void setPasswordLabel(String password) {
        getLoadingLayoutProxy().setPasswordText(password);
    }

    public void setScrollAnimationInterpolator(Interpolator interpolator) {
        this.mScrollAnimationInterpolator = interpolator;
    }

    public final void setShowViewWhileRefreshing(boolean showView) {
        this.mShowViewWhileRefreshing = showView;
    }

    final void setState(State state, boolean... params) {
        this.mState = state;
        switch (m5246x9d4cd55f()[this.mState.ordinal()]) {
            case 1:
                onReset();
                break;
            case 2:
                onPullToRefresh();
                break;
            case 3:
                onReleaseToRefresh();
                break;
            case 4:
            case 5:
                onRefreshing(params[0]);
                break;
        }
        if (this.mOnPullEventListener != null) {
            this.mOnPullEventListener.onPullEvent(this, this.mState, this.mCurrentMode);
        }
    }

    protected final void addViewInternal(View child, int index, LayoutParams params) {
        super.addView(child, index, params);
    }

    protected final void addViewInternal(View child, LayoutParams params) {
        super.addView(child, -1, params);
    }

    protected LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray attrs) {
        LoadingLayout layout = this.mLoadingAnimationStyle.createLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
        layout.setVisibility(View.INVISIBLE);
        return layout;
    }

    protected LoadingLayoutProxy createLoadingLayoutProxy(boolean includeStart, boolean includeEnd) {
        LoadingLayoutProxy proxy = new LoadingLayoutProxy();
        if (includeStart && this.mMode.showHeaderLoadingLayout()) {
            proxy.addLayout(this.mHeaderLayout);
        }
        if (includeEnd && this.mMode.showFooterLoadingLayout()) {
            proxy.addLayout(this.mFooterLayout);
        }
        return proxy;
    }

    protected final void disableLoadingLayoutVisibilityChanges() {
        this.mLayoutVisibilityChangesEnabled = DEBUG;
    }

    protected final LoadingLayout getFooterLayout() {
        return this.mFooterLayout;
    }

    protected final int getFooterSize() {
        return this.mFooterLayout.getContentSize();
    }

    public final LoadingLayout getHeaderLayout() {
        return this.mHeaderLayout;
    }

    protected final int getHeaderSize() {
        return this.mHeaderLayout.getContentSize();
    }

    protected int getPullToRefreshScrollDuration() {
        return SMOOTH_SCROLL_DURATION_MS;
    }

    protected int getPullToRefreshScrollDurationLonger() {
        return SMOOTH_SCROLL_LONG_DURATION_MS;
    }

    protected FrameLayout getRefreshableViewWrapper() {
        return this.mRefreshableViewWrapper;
    }

    protected void handleStyledAttributes(TypedArray a) {
    }

    protected void onPtrRestoreInstanceState(Bundle savedInstanceState) {
    }

    protected void onPtrSaveInstanceState(Bundle saveState) {
    }

    protected void onPullToRefresh() {
        switch (m5244x57a4d715()[this.mCurrentMode.ordinal()]) {
            case 2:
                this.mHeaderLayout.pullToRefresh();
            case 3:
                this.mFooterLayout.pullToRefresh();
            default:
        }
    }

    protected void onRefreshing(boolean doScroll) {
        if (this.mMode.showHeaderLoadingLayout()) {
            this.mHeaderLayout.refreshing();
        }
        if (this.mMode.showFooterLoadingLayout()) {
            this.mFooterLayout.refreshing();
        }
        if (!doScroll) {
            callRefreshListener();
        } else if (this.mShowViewWhileRefreshing) {
            OnSmoothScrollFinishedListener listener = new C12911();
            switch (m5244x57a4d715()[this.mCurrentMode.ordinal()]) {
                case 3:
                case 5:
                    smoothScrollTo(getFooterSize(), listener);
                default:
                    smoothScrollTo(-getHeaderSize(), listener);
            }
        } else {
            smoothScrollTo(0);
        }
    }

    protected void onReleaseToRefresh() {
        switch (m5244x57a4d715()[this.mCurrentMode.ordinal()]) {
            case 2:
                this.mHeaderLayout.releaseToRefresh();
            case 3:
                this.mFooterLayout.releaseToRefresh();
            default:
        }
    }

    protected void onReset() {
        this.mIsBeingDragged = DEBUG;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mHeaderLayout.reset();
        this.mFooterLayout.reset();
        smoothScrollTo(0);
    }

    protected final void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            setMode(Mode.mapIntToValue(bundle.getInt(STATE_MODE, 0)));
            this.mCurrentMode = Mode.mapIntToValue(bundle.getInt(STATE_CURRENT_MODE, 0));
            this.mScrollingWhileRefreshingEnabled = bundle.getBoolean(STATE_SCROLLING_REFRESHING_ENABLED, DEBUG);
            this.mShowViewWhileRefreshing = bundle.getBoolean(STATE_SHOW_REFRESHING_VIEW, true);
            super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER));
            State viewState = State.mapIntToValue(bundle.getInt(STATE_STATE, 0));
            if (viewState == State.REFRESHING || viewState == State.MANUAL_REFRESHING) {
                setState(viewState, true);
            }
            onPtrRestoreInstanceState(bundle);
            return;
        }
        super.onRestoreInstanceState(state);
    }

    protected final Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        onPtrSaveInstanceState(bundle);
        bundle.putInt(STATE_STATE, this.mState.getIntValue());
        bundle.putInt(STATE_MODE, this.mMode.getIntValue());
        bundle.putInt(STATE_CURRENT_MODE, this.mCurrentMode.getIntValue());
        bundle.putBoolean(STATE_SCROLLING_REFRESHING_ENABLED, this.mScrollingWhileRefreshingEnabled);
        bundle.putBoolean(STATE_SHOW_REFRESHING_VIEW, this.mShowViewWhileRefreshing);
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
        return bundle;
    }

    protected final void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        refreshLoadingViewsSize();
        refreshRefreshableViewSize(w, h);
        post(new C03892());
    }

    protected final void refreshLoadingViewsSize() {
        int maximumPullScroll = (int) (((float) getMaximumPullScroll()) * 1.2f);
        int pLeft = getPaddingLeft();
        int pTop = getPaddingTop();
        int pRight = getPaddingRight();
        int pBottom = getPaddingBottom();
        switch (m5245x9c6cb41e()[getPullToRefreshScrollDirection().ordinal()]) {
            case 1:
                if (this.mMode.showHeaderLoadingLayout()) {
                    this.mHeaderLayout.setHeight(maximumPullScroll);
                    pTop = -maximumPullScroll;
                } else {
                    pTop = 0;
                }
                if (!this.mMode.showFooterLoadingLayout()) {
                    pBottom = 0;
                    break;
                }
                this.mFooterLayout.setHeight(maximumPullScroll);
                pBottom = -maximumPullScroll;
                break;
            case 2:
                if (this.mMode.showHeaderLoadingLayout()) {
                    this.mHeaderLayout.setWidth(maximumPullScroll);
                    pLeft = -maximumPullScroll;
                } else {
                    pLeft = 0;
                }
                if (!this.mMode.showFooterLoadingLayout()) {
                    pRight = 0;
                    break;
                }
                this.mFooterLayout.setWidth(maximumPullScroll);
                pRight = -maximumPullScroll;
                break;
        }
        setPadding(pLeft, pTop, pRight, pBottom);
    }

    protected final void refreshRefreshableViewSize(int width, int height) {
        LayoutParams lp = (LayoutParams) this.mRefreshableViewWrapper.getLayoutParams();
        switch (m5245x9c6cb41e()[getPullToRefreshScrollDirection().ordinal()]) {
            case 1:
                if (lp.height != height) {
                    lp.height = height;
                    this.mRefreshableViewWrapper.requestLayout();
                }
            case 2:
                if (lp.width != width) {
                    lp.width = width;
                    this.mRefreshableViewWrapper.requestLayout();
                }
            default:
        }
    }

    protected final void setHeaderScroll(int value) {
        int maximumPullScroll = getMaximumPullScroll();
        value = Math.min(maximumPullScroll, Math.max(-maximumPullScroll, value));
        if (this.mLayoutVisibilityChangesEnabled) {
            if (value < 0) {
                this.mHeaderLayout.setVisibility(View.VISIBLE);
            } else if (value > 0) {
                this.mFooterLayout.setVisibility(View.VISIBLE);
            } else {
                this.mHeaderLayout.setVisibility(View.INVISIBLE);
                this.mFooterLayout.setVisibility(View.INVISIBLE);
            }
        }
        switch (m5245x9c6cb41e()[getPullToRefreshScrollDirection().ordinal()]) {
            case 1:
                scrollTo(0, value);
            case 2:
                scrollTo(value, 0);
            default:
        }
    }

    protected final void smoothScrollTo(int scrollValue) {
        smoothScrollTo(scrollValue, (long) getPullToRefreshScrollDuration());
    }

    protected final void smoothScrollTo(int scrollValue, OnSmoothScrollFinishedListener listener) {
        smoothScrollTo(scrollValue, (long) getPullToRefreshScrollDuration(), 0, listener);
    }

    protected final void smoothScrollToLonger(int scrollValue) {
        smoothScrollTo(scrollValue, (long) getPullToRefreshScrollDurationLonger());
    }

    protected void updateUIForMode() {
        LayoutParams lp = getLoadingLayoutLayoutParams();
        if (this == this.mHeaderLayout.getParent()) {
            removeView(this.mHeaderLayout);
        }
        if (this.mMode.showHeaderLoadingLayout()) {
            addViewInternal(this.mHeaderLayout, 0, lp);
        }
        if (this == this.mFooterLayout.getParent()) {
            removeView(this.mFooterLayout);
        }
        if (this.mMode.showFooterLoadingLayout()) {
            addViewInternal(this.mFooterLayout, lp);
        }
        refreshLoadingViewsSize();
        this.mCurrentMode = this.mMode != Mode.BOTH ? this.mMode : PULL_FROM_START;
    }

    private void addRefreshableView(Context context, T refreshableView) {
        this.mRefreshableViewWrapper = new FrameLayout(context);
        this.mRefreshableViewWrapper.addView(refreshableView, -1, -1);
        addViewInternal(this.mRefreshableViewWrapper, new LayoutParams(-1, -1));
    }

    private void callRefreshListener() {
        if (this.mOnRefreshListener != null) {
            this.mOnRefreshListener.onRefresh(this);
        } else if (this.mOnRefreshListener2 == null) {
        } else {
            if (this.mCurrentMode == PULL_FROM_START) {
                this.mOnRefreshListener2.onPullDownToRefresh(this);
            } else if (this.mCurrentMode == Mode.PULL_FROM_END) {
                this.mOnRefreshListener2.onPullUpToRefresh(this);
            }
        }
    }

    public static final int PullToRefresh[] = {
            0x7f010000, 0x7f010001, 0x7f010002, 0x7f010003, 0x7f010004, 0x7f010005, 0x7f010006, 0x7f010007, 0x7f010008, 0x7f010009,
            0x7f01000a, 0x7f01000b, 0x7f01000c, 0x7f01000d, 0x7f01000e, 0x7f01000f, 0x7f010010, 0x7f010011, 0x7f010012
    };

    private void init(Context context, AttributeSet attrs) {
        switch (m5245x9c6cb41e()[getPullToRefreshScrollDirection().ordinal()]) {
            case 2:
                setOrientation(LinearLayout.HORIZONTAL);
                break;
            default:
                setOrientation(LinearLayout.VERTICAL);
                break;
        }
        setGravity(17);
        this.mScroller = new Scroller(context);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray a = context.obtainStyledAttributes(attrs, PullToRefresh);
        if (a.hasValue(4)) {
            this.mMode = Mode.mapIntToValue(a.getInteger(4, 0));
        }
        if (a.hasValue(12)) {
            this.mLoadingAnimationStyle = AnimationStyle.mapIntToValue(a.getInteger(12, 0));
        }
        this.mRefreshableView = createRefreshableView(context, attrs);
        addRefreshableView(context, this.mRefreshableView);
        this.mHeaderLayout = createLoadingLayout(context, PULL_FROM_START, a);
        this.mFooterLayout = createLoadingLayout(context, Mode.PULL_FROM_END, a);
        Drawable background;
        if (a.hasValue(0)) {
            background = a.getDrawable(0);
            if (background != null) {
                this.mRefreshableView.setBackgroundDrawable(background);
            }
        } else if (a.hasValue(16)) {
            Utils.warnDeprecation("ptrAdapterViewBackground", "ptrRefreshableViewBackground");
            background = a.getDrawable(16);
            if (background != null) {
                this.mRefreshableView.setBackgroundDrawable(background);
            }
        }
        if (a.hasValue(9)) {
            this.mOverScrollEnabled = a.getBoolean(9, true);
        }
        if (a.hasValue(13)) {
            this.mScrollingWhileRefreshingEnabled = a.getBoolean(13, DEBUG);
        }
        handleStyledAttributes(a);
        a.recycle();
        updateUIForMode();
    }

    private boolean isReadyForPull() {
        switch (m5244x57a4d715()[this.mMode.ordinal()]) {
            case 2:
                return isReadyForPullStart();
            case 3:
                return isReadyForPullEnd();
            case 4:
                return (isReadyForPullEnd() || isReadyForPullStart()) ? true : DEBUG;
            default:
                return DEBUG;
        }
    }

    private void pullEvent() {
        float initialMotionValue;
        float lastMotionValue;
        int newScrollValue;
        int itemDimension;
        switch (m5245x9c6cb41e()[getPullToRefreshScrollDirection().ordinal()]) {
            case 2:
                initialMotionValue = this.mInitialMotionX;
                lastMotionValue = this.mLastMotionX;
                break;
            default:
                initialMotionValue = this.mInitialMotionY;
                lastMotionValue = this.mLastMotionY;
                break;
        }
        switch (m5244x57a4d715()[this.mCurrentMode.ordinal()]) {
            case 3:
                newScrollValue = Math.round(Math.max(initialMotionValue - lastMotionValue, 0.0f) / FRICTION);
                itemDimension = getFooterSize();
                break;
            default:
                newScrollValue = Math.round(Math.min(initialMotionValue - lastMotionValue, 0.0f) / FRICTION);
                itemDimension = getHeaderSize();
                break;
        }
        setHeaderScroll(newScrollValue);
        if (newScrollValue != 0 && !isRefreshing()) {
            float scale = ((float) Math.abs(newScrollValue)) / ((float) itemDimension);
            switch (m5244x57a4d715()[this.mCurrentMode.ordinal()]) {
                case 3:
                    this.mFooterLayout.onPull(scale);
                    break;
                default:
                    this.mHeaderLayout.onPull(scale);
                    break;
            }
            if (this.mState != State.PULL_TO_REFRESH && itemDimension >= Math.abs(newScrollValue)) {
                setState(State.PULL_TO_REFRESH, new boolean[0]);
            } else if (this.mState == State.PULL_TO_REFRESH && itemDimension < Math.abs(newScrollValue)) {
                setState(State.RELEASE_TO_REFRESH, new boolean[0]);
            }
        }
    }

    private LayoutParams getLoadingLayoutLayoutParams() {
        switch (m5245x9c6cb41e()[getPullToRefreshScrollDirection().ordinal()]) {
            case 2:
                return new LayoutParams(-2, -1);
            default:
                return new LayoutParams(-1, -2);
        }
    }

    private int getMaximumPullScroll() {
        switch (m5245x9c6cb41e()[getPullToRefreshScrollDirection().ordinal()]) {
            case 2:
                return Math.round(((float) getWidth()) / FRICTION);
            default:
                return Math.round(((float) getHeight()) / FRICTION);
        }
    }

    private final void smoothScrollTo(int scrollValue, long duration) {
        smoothScrollTo(scrollValue, duration, 0, null);
    }

    private final void smoothScrollTo(int newScrollValue, long duration, long delayMillis, OnSmoothScrollFinishedListener listener) {
        int oldScrollValue;
        if (this.mCurrentSmoothScrollRunnable != null) {
            this.mCurrentSmoothScrollRunnable.stop();
        }
        switch (m5245x9c6cb41e()[getPullToRefreshScrollDirection().ordinal()]) {
            case 2:
                oldScrollValue = getScrollX();
                break;
            default:
                oldScrollValue = getScrollY();
                break;
        }
        if (oldScrollValue != newScrollValue) {
            if (this.mScrollAnimationInterpolator == null) {
                this.mScrollAnimationInterpolator = new DecelerateInterpolator();
            }
            this.mCurrentSmoothScrollRunnable = new SmoothScrollRunnable(oldScrollValue, newScrollValue, duration, listener);
            if (delayMillis > 0) {
                postDelayed(this.mCurrentSmoothScrollRunnable, delayMillis);
            } else {
                post(this.mCurrentSmoothScrollRunnable);
            }
        }
    }

    private final void smoothScrollToAndBack(int y) {
        smoothScrollTo(y, 200, 0, new C12923());
    }

    public void setCurrentMode(Mode mode) {
        this.mCurrentMode = mode;
    }

    public void setHide(boolean isHide) {
        if (this.mHeaderLayout != null) {
            this.mHeaderLayout.setHide(isHide);
        }
        if (this.mFooterLayout != null) {
            this.mFooterLayout.setHide(isHide);
        }
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (!this.mScroller.isFinished()) {
            return super.onTouchEvent(event);
        }
        if (this.imageView != null) {
            this.imageView.getTop();
        }
        this.currentX = event.getX();
        this.currentY = event.getY();
        int[] location;
        int i;
        int y;
        int t;
        ViewGroup.LayoutParams params;
        switch (action) {
            case 0:
                if (this.imageView != null) {
                    if (this.imageH == 0) {
                        this.imageH = this.imageView.getLayoutParams().height;
                    }
                    this.down_time = System.currentTimeMillis();
                    this.left = this.imageView.getLeft();
                    this.top = this.imageView.getBottom();
                    this.rootW = getWidth();
                    this.rootH = getHeight();
                    this.imageViewH = this.imageView.getHeight();
                    this.startX = this.currentX;
                    this.startY = this.currentY;
                    this.tool = new TouchTool(this.imageView.getLeft(), this.imageView.getBottom(), this.imageView.getLeft(), this.imageView.getBottom() + SMOOTH_SCROLL_DURATION_MS);
                    break;
                }
                break;
            case 1:
                if (this.imageView != null) {
                    this.scrollerType = true;
                    if (this.mOnRefreshListener3 != null) {
                        location = new int[2];
                        this.imageView.getLocationOnScreen(location);
                        i = location[0];
                        y = location[1];
                        t = this.tool.getScrollY(this.currentY - this.startY);
                        if (y <= 0 || t < this.top + 150 || ((double) this.imageView.getBottom()) <= ((double) this.imageViewH) * 1.4d) {
                            this.mOnRefreshListener3.onRefresh(DEBUG);
                        } else {
                            this.mOnRefreshListener3.onRefresh(true);
                        }
                    }
                    params = this.imageView.getLayoutParams();
                    params.height = this.imageH;
                    this.imageView.setLayoutParams(params);
                    break;
                }
                break;
            case 2:
                if (this.imageView != null && this.imageView.getTop() >= 0) {
                    if (this.tool != null) {
                        t = this.tool.getScrollY(this.currentY - this.startY);
                        if (t >= this.top && t <= this.imageView.getBottom() + SMOOTH_SCROLL_DURATION_MS) {
                            params = this.imageView.getLayoutParams();
                            location = new int[2];
                            this.imageView.getLocationOnScreen(location);
                            i = location[0];
                            y = location[1];
                            if (y > 0 && t >= this.top + 50) {
                                this.mOnRefreshListener3.onStartRefresh(t - 50);
                            }
                            if (y > 0) {
                                params.height = t;
                                this.imageView.setLayoutParams(params);
                            }
                        }
                    }
                    this.scrollerType = DEBUG;
                    break;
                }
        }
        return super.dispatchTouchEvent(event);
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int x = this.mScroller.getCurrX();
            int y = this.mScroller.getCurrY();
            this.imageView.layout(0, 0, this.imageView.getWidth() + x, y);
            invalidate();
            if (!this.mScroller.isFinished() && this.scrollerType && y > SMOOTH_SCROLL_DURATION_MS) {
                LayoutParams params = (LayoutParams) this.imageView.getLayoutParams();
                params.height = y;
                this.changeY = (float) y;
                this.imageView.setLayoutParams(params);
            }
        }
    }

    public void setOnRefreshListener3(OnRefreshListener3 onRefreshListener3) {
        this.mOnRefreshListener3 = onRefreshListener3;
    }
}
