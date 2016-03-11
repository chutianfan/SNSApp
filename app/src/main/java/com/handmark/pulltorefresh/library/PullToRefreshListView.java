package com.handmark.pulltorefresh.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.internal.EmptyViewMethodAccessor;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;
import com.gitrose.mobile.R;

public class PullToRefreshListView extends PullToRefreshAdapterViewBase<ListView> {
    private static /* synthetic */ int[] f6587x57a4d715;
    private LoadingLayout mFooterLoadingView;
    private LoadingLayout mHeaderLoadingView;
    private boolean mListViewExtrasEnabled;
    private FrameLayout mLvFooterLoadingFrame;

    protected class InternalListView extends ListView implements EmptyViewMethodAccessor {
        private boolean mAddedLvFooter;

        public InternalListView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mAddedLvFooter = false;
        }

        protected void dispatchDraw(Canvas canvas) {
            try {
                super.dispatchDraw(canvas);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

        public boolean dispatchTouchEvent(MotionEvent ev) {
            try {
                return super.dispatchTouchEvent(ev);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                return false;
            }
        }

        public void setAdapter(ListAdapter adapter) {
            if (!(PullToRefreshListView.this.mLvFooterLoadingFrame == null || this.mAddedLvFooter)) {
                addFooterView(PullToRefreshListView.this.mLvFooterLoadingFrame, null, false);
                this.mAddedLvFooter = true;
            }
            super.setAdapter(adapter);
        }

        public void setEmptyView(View emptyView) {
            PullToRefreshListView.this.setEmptyView(emptyView);
        }

        public void setEmptyViewInternal(View emptyView) {
            super.setEmptyView(emptyView);
        }
    }

    @TargetApi(9)
    final class InternalListViewSDK9 extends InternalListView {
        public InternalListViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
            OverscrollHelper.overScrollBy(PullToRefreshListView.this, deltaX, scrollX, deltaY, scrollY, isTouchEvent);
            return returnValue;
        }
    }

    static /* synthetic */ int[] m6710x57a4d715() {
        int[] iArr = f6587x57a4d715;
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
                iArr[Mode.PULL_FROM_START.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            f6587x57a4d715 = iArr;
        }
        return iArr;
    }

    public PullToRefreshListView(Context context) {
        super(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshListView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshListView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    protected void onRefreshing(boolean doScroll) {
        ListAdapter adapter = ((ListView) this.mRefreshableView).getAdapter();
        if (!this.mListViewExtrasEnabled || !getShowViewWhileRefreshing() || adapter == null || adapter.isEmpty()) {
            super.onRefreshing(doScroll);
            return;
        }
        LoadingLayout origLoadingView;
        LoadingLayout listViewLoadingView;
        LoadingLayout oppositeListViewLoadingView;
        int selection;
        int scrollToY;
        super.onRefreshing(false);
        switch (m6710x57a4d715()[getCurrentMode().ordinal()]) {
            case 3:
            case 5:
                origLoadingView = getFooterLayout();
                listViewLoadingView = this.mFooterLoadingView;
                oppositeListViewLoadingView = this.mHeaderLoadingView;
                selection = ((ListView) this.mRefreshableView).getCount() - 1;
                scrollToY = getScrollY() - getFooterSize();
                break;
            default:
                origLoadingView = getHeaderLayout();
                listViewLoadingView = this.mHeaderLoadingView;
                oppositeListViewLoadingView = this.mFooterLoadingView;
                selection = 0;
                scrollToY = getScrollY() + getHeaderSize();
                break;
        }
        origLoadingView.reset();
        origLoadingView.hideAllViews();
        oppositeListViewLoadingView.setVisibility(View.GONE);
        listViewLoadingView.setVisibility(View.VISIBLE);
        listViewLoadingView.refreshing();
        if (doScroll) {
            disableLoadingLayoutVisibilityChanges();
            setHeaderScroll(scrollToY);
            ((ListView) this.mRefreshableView).setSelection(selection);
            smoothScrollTo(0);
        }
    }

    public void smoothScroll(int selection) {
        ((ListView) this.mRefreshableView).setSelection(selection);
        smoothScrollTo(selection);
    }

    protected void onReset() {
        boolean scrollLvToEdge = true;
        if (this.mListViewExtrasEnabled) {
            LoadingLayout originalLoadingLayout;
            LoadingLayout listViewLoadingLayout;
            int selection;
            int scrollToHeight;
            switch (m6710x57a4d715()[getCurrentMode().ordinal()]) {
                case 3:
                case 5:
                    originalLoadingLayout = getFooterLayout();
                    listViewLoadingLayout = this.mFooterLoadingView;
                    selection = ((ListView) this.mRefreshableView).getCount() - 1;
                    scrollToHeight = getFooterSize();
                    if (Math.abs(((ListView) this.mRefreshableView).getLastVisiblePosition() - selection) > 1) {
                        scrollLvToEdge = false;
                    }
                    break;
                default:
                    originalLoadingLayout = getHeaderLayout();
                    listViewLoadingLayout = this.mHeaderLoadingView;
                    scrollToHeight = -getHeaderSize();
                    selection = 0;
                    if (Math.abs(((ListView) this.mRefreshableView).getFirstVisiblePosition() - 0) > 1) {
                        scrollLvToEdge = false;
                        break;
                    }
                    break;
            }
            if (listViewLoadingLayout.getVisibility() == View.VISIBLE) {
                originalLoadingLayout.showInvisibleViews();
                listViewLoadingLayout.setVisibility(View.INVISIBLE);
                if (scrollLvToEdge && getState() != State.MANUAL_REFRESHING) {
                    ((ListView) this.mRefreshableView).setSelection(selection);
                    setHeaderScroll(scrollToHeight);
                }
            }
            super.onReset();
            return;
        }
        super.onReset();
    }

    protected LoadingLayoutProxy createLoadingLayoutProxy(boolean includeStart, boolean includeEnd) {
        LoadingLayoutProxy proxy = super.createLoadingLayoutProxy(includeStart, includeEnd);
        if (this.mListViewExtrasEnabled) {
            Mode mode = getMode();
            if (includeStart && mode.showHeaderLoadingLayout()) {
                proxy.addLayout(this.mHeaderLoadingView);
            }
            if (includeEnd && mode.showFooterLoadingLayout()) {
                proxy.addLayout(this.mFooterLoadingView);
            }
        }
        return proxy;
    }

    protected ListView createListView(Context context, AttributeSet attrs) {
        if (VERSION.SDK_INT >= 9) {
            return new InternalListViewSDK9(context, attrs);
        }
        return new InternalListView(context, attrs);
    }

    protected ListView createRefreshableView(Context context, AttributeSet attrs) {
        ListView lv = createListView(context, attrs);
        lv.setId(R.id.listView);
        return lv;
    }

    protected void handleStyledAttributes(TypedArray a) {
        super.handleStyledAttributes(a);
        this.mListViewExtrasEnabled = a.getBoolean(14, true);
        if (this.mListViewExtrasEnabled) {
            LayoutParams lp = new LayoutParams(-1, -2, 1);
            FrameLayout frame = new FrameLayout(getContext());
            this.mHeaderLoadingView = createLoadingLayout(getContext(), Mode.PULL_FROM_START, a);
            this.mHeaderLoadingView.setVisibility(View.GONE);
            frame.addView(this.mHeaderLoadingView, lp);
            ((ListView) this.mRefreshableView).addHeaderView(frame, null, false);
            this.mLvFooterLoadingFrame = new FrameLayout(getContext());
            this.mFooterLoadingView = createLoadingLayout(getContext(), Mode.PULL_FROM_END, a);
            this.mFooterLoadingView.setVisibility(View.GONE);
            this.mLvFooterLoadingFrame.addView(this.mFooterLoadingView, lp);
            if (!a.hasValue(13)) {
                setScrollingWhileRefreshingEnabled(true);
            }
        }
    }
}
