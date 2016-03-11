package com.gitrose.mobile.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.gitrose.mobile.R;
import com.gitrose.mobile.model.PersonalTabItem;
import java.util.List;

@SuppressLint({"NewApi"})
public class PersonalTabIndicatorView extends LinearLayout implements OnClickListener, OnFocusChangeListener {
    public static final int BSSEEID = 16776960;
    private static final int FOOTER_COLOR = -15291;
    private static final float FOOTER_LINE_HEIGHT = 4.0f;
    private static final float FOOTER_TRIANGLE_HEIGHT = 10.0f;
    private static final String TAG = "TitleFlowIndicator";
    private boolean DEBUG;
    private boolean mChangeOnClick;
    private Context mContext;
    private int mCurrID;
    private int mCurrentScroll;
    private float mFooterLineHeight;
    private float mFooterTriangleHeight;
    private LayoutInflater mInflater;
    private int mOldTab;
    private Paint mPaintFooterLine;
    private Paint mPaintFooterTriangle;
    private Path mPath;
    private int mPerItemWidth;
    private List<PersonalTabItem> mPersonalTabs;
    private int mSelectedTab;
    private ColorStateList mTextColor;
    private ColorStateList mTextColorSelected;
    private float mTextSizeNormal;
    private float mTextSizeSelected;
    private int mTotal;
    private ViewPager mViewPager;

    public PersonalTabIndicatorView(Context context) {
        super(context);
        this.DEBUG = false;
        this.mCurrentScroll = 0;
        this.mPath = new Path();
        this.mSelectedTab = 0;
        this.mOldTab = -1;
        this.mChangeOnClick = true;
        this.mCurrID = 0;
        this.mPerItemWidth = 0;
        this.mTotal = 0;
        initDraw(FOOTER_LINE_HEIGHT, FOOTER_COLOR);
    }

    public PersonalTabIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.DEBUG = false;
        this.mCurrentScroll = 0;
        this.mPath = new Path();
        this.mSelectedTab = 0;
        this.mOldTab = -1;
        this.mChangeOnClick = true;
        this.mCurrID = 0;
        this.mPerItemWidth = 0;
        this.mTotal = 0;
        setFocusable(true);
        setOnFocusChangeListener(this);
        this.mContext = context;
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.personal_tab_indicatorview);
//        int footerColor = a.getColor(7, FOOTER_COLOR);
//        this.mTextColor = a.getColorStateList(2);
//        this.mTextColorSelected = a.getColorStateList(3);
//        this.mTextSizeNormal = a.getDimension(4, 0.0f);
//        this.mTextSizeSelected = a.getDimension(5, this.mTextSizeNormal);
//        this.mFooterLineHeight = a.getDimension(6, FOOTER_LINE_HEIGHT);
//        this.mFooterTriangleHeight = a.getDimension(8, FOOTER_TRIANGLE_HEIGHT);
//        initDraw(this.mFooterLineHeight, footerColor);
//        a.recycle();
    }

    public PersonalTabIndicatorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.DEBUG = false;
        this.mCurrentScroll = 0;
        this.mPath = new Path();
        this.mSelectedTab = 0;
        this.mOldTab = -1;
        this.mChangeOnClick = true;
        this.mCurrID = 0;
        this.mPerItemWidth = 0;
        this.mTotal = 0;
        setFocusable(true);
        setOnFocusChangeListener(this);
        this.mContext = context;
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.personal_tab_indicatorview);
//        int footerColor = a.getColor(7, FOOTER_COLOR);
//        this.mTextColor = a.getColorStateList(2);
//        this.mTextColorSelected = a.getColorStateList(3);
//        this.mTextSizeNormal = a.getDimension(4, 0.0f);
//        this.mTextSizeSelected = a.getDimension(5, this.mTextSizeNormal);
//        this.mFooterLineHeight = a.getDimension(6, FOOTER_LINE_HEIGHT);
//        this.mFooterTriangleHeight = a.getDimension(8, FOOTER_TRIANGLE_HEIGHT);
//        initDraw(this.mFooterLineHeight, footerColor);
//        a.recycle();
    }

    private void initDraw(float footerLineHeight, int footerColor) {
        this.mPaintFooterLine = new Paint();
        this.mPaintFooterLine.setStyle(Style.FILL_AND_STROKE);
        this.mPaintFooterLine.setStrokeWidth(footerLineHeight);
        this.mPaintFooterLine.setColor(footerColor);
        this.mPaintFooterTriangle = new Paint();
        this.mPaintFooterTriangle.setStyle(Style.FILL_AND_STROKE);
        this.mPaintFooterTriangle.setColor(footerColor);
        this.mInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    protected void onDraw(Canvas canvas) {
        float scroll_x;
        super.onDraw(canvas);
        if (this.mTotal != 0) {
            this.mPerItemWidth = getWidth() / this.mTotal;
            scroll_x = (float) ((this.mCurrentScroll - ((getWidth() + this.mViewPager.getPageMargin()) * this.mSelectedTab)) / this.mTotal);
        } else {
            this.mPerItemWidth = getWidth();
            scroll_x = (float) this.mCurrentScroll;
        }
        Path path = this.mPath;
        path.rewind();
        float left_x = (((float) (this.mSelectedTab * this.mPerItemWidth)) + 40.0f) + scroll_x;
        float right_x = (((float) ((this.mSelectedTab + 1) * this.mPerItemWidth)) - 40.0f) + scroll_x;
        float top_y = (((float) getHeight()) - this.mFooterLineHeight) - this.mFooterTriangleHeight;
        float bottom_y = ((float) getHeight()) - this.mFooterLineHeight;
        path.moveTo(left_x, top_y + DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        path.lineTo(right_x, top_y + DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        path.lineTo(right_x, bottom_y + DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        path.lineTo(left_x, bottom_y + DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        path.close();
        canvas.drawPath(path, this.mPaintFooterTriangle);
    }

    public void onScrolled(int h) {
        this.mCurrentScroll = h;
        invalidate();
    }

    public synchronized void onSwitched(int position) {
        if (this.mSelectedTab != position) {
            setCurrentTab(position);
            invalidate();
        }
    }

    public void init(int startPos, List<PersonalTabItem> tabs, ViewPager mViewPager) {
        removeAllViews();
        this.mViewPager = mViewPager;
        this.mPersonalTabs = tabs;
        this.mTotal = tabs.size();
        for (PersonalTabItem item : this.mPersonalTabs) {
            add(item);
        }
        setCurrentTab(startPos);
        invalidate();
    }

    public void updateChildType(int position, boolean show, String type) {
        View child = getChildAt(position);
        TextView textView = (TextView) child.findViewById(R.id.personal_tab_title_type);
        RelativeLayout relativeLayout = (RelativeLayout) child.findViewById(R.id.textview_layout);
        View view = child.findViewById(R.id.tab_line);
        if (position == getChildCount() - 1) {
            view.setVisibility(View.GONE);
        }
        if (show) {
            relativeLayout.setBackgroundResource(R.color.action_bar_bg);
            textView.setVisibility(View.VISIBLE);
            textView.setText(type);
        } else {
            textView.setVisibility(View.GONE);
            textView.setText(type);
        }
        ((PersonalTabItem) this.mPersonalTabs.get(position)).currentType = type;
    }

    protected void add(PersonalTabItem item) {
        View tabIndicator = this.mInflater.inflate(R.layout.item_personal_tab_indicator, this, false);
        TextView titleText = (TextView) tabIndicator.findViewById(R.id.personal_tab_title);
        TextView typeText = (TextView) tabIndicator.findViewById(R.id.personal_tab_title_type);
        ImageView iconImage = (ImageView) tabIndicator.findViewById(R.id.personal_tab_title_icon);
        if (this.mTextColor != null) {
            titleText.setTextColor(this.mTextColor);
        }
        if (this.mTextSizeNormal > 0.0f) {
            titleText.setTextSize(0, this.mTextSizeNormal);
        }
        titleText.setText(item.name);
        if (item.icon > 0) {
            titleText.setCompoundDrawablesWithIntrinsicBounds(0, item.icon, 0, 0);
        }
        if (item.iconnormal != 0) {
            iconImage.setImageResource(item.iconnormal);
        }
        typeText.setText(item.currentType);
        int i = this.mCurrID;
        this.mCurrID = i + 1;
        tabIndicator.setId(BSSEEID + i);
        tabIndicator.setOnClickListener(this);
        ((LayoutParams) tabIndicator.getLayoutParams()).gravity = Gravity.NO_GRAVITY;
        addView(tabIndicator);
    }

    public void setDisplayedPage(int index) {
        this.mSelectedTab = index;
    }

    public void setChangeOnClick(boolean changeOnClick) {
        this.mChangeOnClick = changeOnClick;
    }

    public boolean getChangeOnClick() {
        return this.mChangeOnClick;
    }

    public void onClick(View v) {
        setCurrentTab(v.getId() - BSSEEID);
    }

    public int getTabCount() {
        return getChildCount();
    }

    public synchronized void setCurrentTab(int index) {
        if (index >= 0) {
            if (index < getTabCount()) {
                View oldTab = getChildAt(this.mSelectedTab);
                oldTab.setSelected(false);
                setTabTextSize(oldTab, false);
                this.mSelectedTab = index;
                View newTab = getChildAt(this.mSelectedTab);
                newTab.setSelected(true);
                setTabTextSize(newTab, true);
                this.mViewPager.setCurrentItem(this.mSelectedTab);
                invalidate();
            }
        }
    }

    private void setTabTextSize(View tab, boolean selected) {
        TextView titleText = (TextView) tab.findViewById(R.id.personal_tab_title);
        titleText.setTextSize(0, selected ? this.mTextSizeSelected : this.mTextSizeNormal);
        titleText.setTextColor(selected ? this.mTextColorSelected : this.mTextColor);
        ((ImageView) tab.findViewById(R.id.personal_tab_title_icon)).setImageResource(selected ? ((PersonalTabItem) this.mPersonalTabs.get(this.mSelectedTab)).iconselected : ((PersonalTabItem) this.mPersonalTabs.get(this.mSelectedTab)).iconnormal);
    }

    public void onFocusChange(View v, boolean hasFocus) {
        if (v == this && hasFocus && getTabCount() > 0) {
            getChildAt(this.mSelectedTab).requestFocus();
        } else if (hasFocus) {
            int numTabs = getTabCount();
            for (int i = 0; i < numTabs; i++) {
                if (getChildAt(i) == v) {
                    setCurrentTab(i);
                    return;
                }
            }
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.mCurrentScroll == 0 && this.mSelectedTab != 0) {
            this.mCurrentScroll = (getWidth() + this.mViewPager.getPageMargin()) * this.mSelectedTab;
        }
    }

    public int getSelectedTab() {
        return this.mSelectedTab;
    }
}
