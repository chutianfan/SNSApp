package com.baoyz.swipemenulistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.gitrose.mobile.R;
//import com.gitrose.mobile.PersonalNewActivity;

public class XListView extends ListView implements OnScrollListener {
    private static final float OFFSET_RADIO = 1.8f;
    private static final int PULL_LOAD_MORE_DELTA = 50;
    private static final int SCROLLBACK_FOOTER = 1;
    private static final int SCROLLBACK_HEADER = 0;
    private static final int SCROLL_DURATION = 400;
    private int footLayoutID;
    private boolean isHorizontalSliding;
    private boolean mAutoRefresh;
    private boolean mEnablePullLoad;
    private boolean mEnablePullRefresh;
    private XListViewFooter mFooterView;
    private TextView mHeaderTimeView;
    private XListViewHeader mHeaderView;
    private RelativeLayout mHeaderViewContent;
    private int mHeaderViewHeight;
    private boolean mIsFooterReady;
    private boolean mIsShowFootText;
    private float mLastX;
    private float mLastY;
    private IXListViewListener mListViewListener;
    private boolean mPullLoading;
    private boolean mPullRefreshing;
    private int mScrollBack;
    private OnScrollListener mScrollListener;
    private Scroller mScroller;
    private int mTotalItemCount;
    private int mTouchSlop;
    private int minHeadHeight;

    public static final class styleable {
        public static final int[] XListView;
        public static final int XListView_autoRefresh = 3;
        public static final int XListView_footViewShowText = 2;
        public static final int XListView_footViewXml = 1;
        public static final int XListView_minHeadViewHeight = 0;

        static {
            XListView = new int[]{R.attr.minHeadViewHeight, R.attr.footViewXml, R.attr.footViewShowText, R.attr.autoRefresh};
        }
    }

    /* renamed from: com.baoyz.swipemenulistview.XListView.1 */
    class C03791 implements OnGlobalLayoutListener {
        C03791() {
        }

        public void onGlobalLayout() {
            XListView.this.mHeaderViewHeight = XListView.this.mHeaderViewContent.getHeight();
            if (XListView.this.mAutoRefresh) {
                XListView.this.autoRefresh();
            }
            XListView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    /* renamed from: com.baoyz.swipemenulistview.XListView.2 */
    class C03802 implements OnClickListener {
        C03802() {
        }

        public void onClick(View v) {
            XListView.this.startLoadMore();
        }
    }

    public interface IXListViewListener {
        void onLoadMore();

        void onRefresh();
    }

    public interface OnXScrollListener extends OnScrollListener {
        void onXScrolling(View view);
    }

    public XListView(Context context) {
        super(context);
        this.mLastY = -1.0f;
        this.mLastX = -1.0f;
        this.mEnablePullRefresh = true;
        this.mPullRefreshing = false;
        this.mIsFooterReady = false;
        this.minHeadHeight = SCROLLBACK_HEADER;
        this.footLayoutID = SCROLLBACK_HEADER;
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs) {
        this(context, attrs, SCROLLBACK_HEADER);
    }

    public XListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mLastY = -1.0f;
        this.mLastX = -1.0f;
        this.mEnablePullRefresh = true;
        this.mPullRefreshing = false;
        this.mIsFooterReady = false;
        this.minHeadHeight = SCROLLBACK_HEADER;
        this.footLayoutID = SCROLLBACK_HEADER;
        TypedArray a = context.obtainStyledAttributes(attrs, styleable.XListView);
        this.minHeadHeight = (int) a.getDimension(SCROLLBACK_HEADER, 0.0f);
        this.footLayoutID = a.getResourceId(SCROLLBACK_FOOTER, R.layout.xlistview_foot);
        this.mIsShowFootText = a.getBoolean(2, true);
        this.mAutoRefresh = a.getBoolean(3, false);
        a.recycle();
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        super.setOnScrollListener(this);
        this.mScroller = new Scroller(context, new DecelerateInterpolator());
        this.mHeaderView = new XListViewHeader(context, this.minHeadHeight);
        this.mHeaderViewContent = (RelativeLayout) this.mHeaderView.findViewById(R.id.xlistview_header_content);
        this.mHeaderTimeView = (TextView) this.mHeaderView.findViewById(R.id.xlistview_header_time);
        addHeaderView(this.mHeaderView);
        this.mFooterView = new XListViewFooter(context, this.footLayoutID, this.mIsShowFootText);
        this.mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new C03791());
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setAdapter(ListAdapter adapter) {

        super.setAdapter(adapter);
        if (!this.mIsFooterReady) {
            this.mIsFooterReady = true;
            addFooterView(this.mFooterView);
        }

    }

    public void setPullRefreshEnable(boolean enable) {
        this.mEnablePullRefresh = enable;
        if (this.mEnablePullRefresh) {
            this.mHeaderViewContent.setVisibility(View.VISIBLE);
        } else {
            this.mHeaderViewContent.setVisibility(View.INVISIBLE);
        }
    }

    public void setPullLoadEnable(boolean enable) {
        this.mEnablePullLoad = enable;
        if (this.mEnablePullLoad) {
            this.mPullLoading = false;
            this.mFooterView.show();
            this.mFooterView.setState(SCROLLBACK_HEADER);
            this.mFooterView.setOnClickListener(new C03802());
            return;
        }
        this.mFooterView.hide();
        this.mFooterView.setOnClickListener(null);
    }

    public void stopRefresh() {
        if (this.mPullRefreshing) {
            this.mPullRefreshing = false;
            resetHeaderHeight();
        }
    }

    public void stopLoadMore() {
        if (this.mPullLoading) {
            this.mPullLoading = false;
            this.mFooterView.setState(SCROLLBACK_HEADER);
        }
    }

    public void setRefreshTime(String time) {
        this.mHeaderTimeView.setText(time);
    }

    private void invokeOnScrolling() {
        if (this.mScrollListener instanceof OnXScrollListener) {
            ((OnXScrollListener) this.mScrollListener).onXScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta) {
        this.mHeaderView.setVisiableHeight(((int) delta) + this.mHeaderView.getVisiableHeight());
        if (this.mEnablePullRefresh && !this.mPullRefreshing) {
            if (this.mHeaderView.getVisiableHeight() > this.mHeaderViewHeight) {
                this.mHeaderView.setState(SCROLLBACK_FOOTER);
            } else {
                this.mHeaderView.setState(SCROLLBACK_HEADER);
            }
        }
        setSelection(SCROLLBACK_HEADER);
    }

    private void resetHeaderHeight() {
        int height = this.mHeaderView.getVisiableHeight();
        if (height != 0) {
            if (!this.mPullRefreshing || height > this.mHeaderViewHeight) {
                int finalHeight = SCROLLBACK_HEADER;
                if (this.mPullRefreshing && height > this.mHeaderViewHeight) {
                    finalHeight = this.mHeaderViewHeight;
                }
                this.mScrollBack = SCROLLBACK_HEADER;
                this.mScroller.startScroll(SCROLLBACK_HEADER, height, SCROLLBACK_HEADER, finalHeight - height, SCROLL_DURATION);
                invalidate();
            }
        }
    }

    private void updateFooterHeight(float delta) {
        int height = this.mFooterView.getBottomMargin() + ((int) delta);
        if (this.mEnablePullLoad && !this.mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) {
                this.mFooterView.setState(SCROLLBACK_FOOTER);
            } else {
                this.mFooterView.setState(SCROLLBACK_HEADER);
            }
        }
        this.mFooterView.setBottomMargin(height);
    }

    private void resetFooterHeight() {
        int bottomMargin = this.mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            this.mScrollBack = SCROLLBACK_FOOTER;
            this.mScroller.startScroll(SCROLLBACK_HEADER, bottomMargin, SCROLLBACK_HEADER, -bottomMargin, SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {
        this.mPullLoading = true;
        this.mFooterView.setState(2);
        if (this.mListViewListener != null) {
            this.mListViewListener.onLoadMore();
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        boolean z = true;
        if (this.mLastY == -1.0f) {
            this.mLastY = ev.getRawY();
        }
        if (this.mLastX == -1.0f) {
            this.mLastX = ev.getRawX();
        }
        switch (ev.getAction()) {
            case SCROLLBACK_HEADER /*0*/:
                this.mLastY = ev.getRawY();
                this.mLastX = ev.getRawX();
                break;
            case 2:
                float deltaY = ev.getRawY() - this.mLastY;
                float deltaX = ev.getRawX() - this.mLastX;
                this.mLastY = ev.getRawY();
                this.mLastX = ev.getRawX();
                if (!this.isHorizontalSliding || (Math.abs(deltaY) > Math.abs(deltaX) && Math.abs(deltaY) > ((float) this.mTouchSlop))) {
                    if (getFirstVisiblePosition() != 0 || (this.mHeaderView.getVisiableHeight() <= this.minHeadHeight && deltaY <= 0.0f)) {
                        if (getLastVisiblePosition() == this.mTotalItemCount - 1 && (this.mFooterView.getBottomMargin() > 0 || deltaY < 0.0f)) {
                            updateFooterHeight((-deltaY) / OFFSET_RADIO);
                            break;
                        }
                    }
                    else {
                        updateHeaderHeight(deltaY / OFFSET_RADIO);
                        invokeOnScrolling();
                    }
                }
                break;
            default:
                this.mLastY = -1.0f;
                if (getFirstVisiblePosition() != 0) {
                    if (getLastVisiblePosition() == this.mTotalItemCount - 1) {
                        if (this.mEnablePullLoad && this.mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA && !this.mPullLoading) {
                            startLoadMore();
                        }
                        resetFooterHeight();
                        break;
                    }
                }
                else if (this.mEnablePullRefresh && this.mHeaderView.getVisiableHeight() > this.mHeaderViewHeight) {
                    this.mPullRefreshing = z;
                    this.mHeaderView.setState(2);
                    if (this.mListViewListener != null) {
                        this.mListViewListener.onRefresh();
                    }
                }
                resetHeaderHeight();
                break;
        }
        try {
            z = super.onTouchEvent(ev);
        } catch (Exception e) {
        }
        return z;
    }

    public void computeScroll() {

        if (this.mScroller.computeScrollOffset()) {
            if (this.mScrollBack == 0) {
                this.mHeaderView.setVisiableHeight(this.mScroller.getCurrY() + this.minHeadHeight);
            } else {
                this.mFooterView.setBottomMargin(this.mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }

        super.computeScroll();
    }

    public void setOnScrollListener(OnScrollListener l) {
        this.mScrollListener = l;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.mTotalItemCount = totalItemCount;
        if (this.mScrollListener != null) {
            this.mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public void setXListViewListener(IXListViewListener l) {
        this.mListViewListener = l;
    }

    public void changeFooterHintNormalText(String footerHintNormalText) {
        this.mFooterView.changeFooterHintNormalText(footerHintNormalText);
    }

    public void autoRefresh() {
        this.mHeaderView.setState(2);
        this.mScrollBack = SCROLLBACK_HEADER;
        this.mScroller.startScroll(SCROLLBACK_HEADER, SCROLLBACK_HEADER, SCROLLBACK_HEADER, this.mHeaderViewHeight + this.minHeadHeight, SCROLL_DURATION);
        invalidate();
        this.mPullRefreshing = true;
        if (this.mListViewListener != null) {
            this.mListViewListener.onRefresh();
        }
    }

    public void setHorizontalSliding(boolean isHorizontalSliding) {
        this.isHorizontalSliding = isHorizontalSliding;
    }
}
