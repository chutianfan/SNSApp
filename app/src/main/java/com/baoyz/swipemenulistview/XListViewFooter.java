package com.baoyz.swipemenulistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.gitrose.mobile.R;

public class XListViewFooter extends LinearLayout {
    public static final int STATE_LOADING = 2;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_READY = 1;
    private String footerHintNormalText;
    private View mContentView;
    private Context mContext;
    private TextView mHintView;
    private boolean mIsShowText;
    private View mProgressBar;

    public XListViewFooter(Context context) {
        super(context);
        this.mIsShowText = true;
        initView(context);
    }

    public XListViewFooter(Context context, int layout, boolean isShowText) {
        super(context);
        this.mIsShowText = true;
        initView(context, layout);
        this.mIsShowText = isShowText;
    }

    public XListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIsShowText = true;
        initView(context);
    }

    public void setState(int state) {
        this.mHintView.setVisibility(View.INVISIBLE);
        this.mProgressBar.setVisibility(View.INVISIBLE);
        this.mHintView.setVisibility(View.INVISIBLE);
        if (state == STATE_READY && this.mIsShowText) {
            this.mHintView.setVisibility(View.VISIBLE);
            this.mHintView.setText(R.string.xlistview_footer_hint_ready);
        } else if (state == STATE_LOADING) {
            this.mProgressBar.setVisibility(View.VISIBLE);
        } else if (this.mIsShowText) {
            this.mHintView.setVisibility(View.VISIBLE);
            this.mHintView.setText(this.footerHintNormalText);
        }
    }

    public void setBottomMargin(int height) {
        if (height >= 0) {
            LayoutParams lp = (LayoutParams) this.mContentView.getLayoutParams();
            lp.bottomMargin = height;
            this.mContentView.setLayoutParams(lp);
        }
    }

    public int getBottomMargin() {
        return ((LayoutParams) this.mContentView.getLayoutParams()).bottomMargin;
    }

    public void changeFooterHintNormalText(String footerHintNormalText) {
        if (footerHintNormalText != null) {
            this.footerHintNormalText = footerHintNormalText;
        }
    }

    public void normal() {
        this.mHintView.setVisibility(View.VISIBLE);
        this.mProgressBar.setVisibility(View.GONE);
    }

    public void loading() {
        this.mHintView.setVisibility(View.GONE);
        this.mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hide() {
        LayoutParams lp = (LayoutParams) this.mContentView.getLayoutParams();
        lp.height = STATE_NORMAL;
        this.mContentView.setLayoutParams(lp);
    }

    public void show() {
        LayoutParams lp = (LayoutParams) this.mContentView.getLayoutParams();
        lp.height = -2;
        this.mContentView.setLayoutParams(lp);
    }

    private void initView(Context context) {
        initView(context, R.layout.xlistview_foot);
    }

    private void initView(Context context, int layoutID) {
        this.mContext = context;
        LinearLayout moreView = (LinearLayout) LayoutInflater.from(this.mContext).inflate(layoutID, null);
        addView(moreView);
        moreView.setLayoutParams(new LayoutParams(-1, -2));
        this.mContentView = moreView.findViewById(R.id.xlistview_footer_content);
        this.mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
        this.mHintView = (TextView) moreView.findViewById(R.id.xlistview_footer_hint_textview);
        if (!this.mIsShowText) {
            this.mHintView.setVisibility(View.INVISIBLE);
        }
        this.footerHintNormalText = getResources().getString(R.string.xlistview_footer_hint_normal);
    }
}
