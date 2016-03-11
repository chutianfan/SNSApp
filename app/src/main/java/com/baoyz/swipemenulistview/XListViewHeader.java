package com.baoyz.swipemenulistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gitrose.mobile.R;

public class XListViewHeader extends LinearLayout {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_READY = 1;
    public static final int STATE_REFRESHING = 2;
    private final int ROTATE_ANIM_DURATION;
    private ImageView mArrowImageView;
    private LinearLayout mContainer;
    private TextView mHintTextView;
    private ProgressBar mProgressBar;
    private Animation mRotateDownAnim;
    private Animation mRotateUpAnim;
    private int mState;
    public int minHeight;

    public XListViewHeader(Context context) {
        super(context);
        this.mState = STATE_NORMAL;
        this.minHeight = STATE_NORMAL;
        this.ROTATE_ANIM_DURATION = 180;
        initView(context);
    }

    public XListViewHeader(Context context, int minHeight) {
        super(context);
        this.mState = STATE_NORMAL;
        this.minHeight = STATE_NORMAL;
        this.ROTATE_ANIM_DURATION = 180;
        this.minHeight = minHeight;
        initView(context);
    }

    public XListViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mState = STATE_NORMAL;
        this.minHeight = STATE_NORMAL;
        this.ROTATE_ANIM_DURATION = 180;
        initView(context);
    }

    private void initView(Context context) {
        LayoutParams lp = new LayoutParams(-1, this.minHeight);
        this.mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.xlistview_header, null);
        addView(this.mContainer, lp);
        setGravity(80);
        this.mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
        this.mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
        this.mProgressBar = (ProgressBar) findViewById(R.id.xlistview_header_progressbar);
        this.mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, STATE_READY, 0.5f, STATE_READY, 0.5f);
        this.mRotateUpAnim.setDuration(180);
        this.mRotateUpAnim.setFillAfter(true);
        this.mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, STATE_READY, 0.5f, STATE_READY, 0.5f);
        this.mRotateDownAnim.setDuration(180);
        this.mRotateDownAnim.setFillAfter(true);
    }

    public void setState(int state) {
        if (state != this.mState) {
            if (state == STATE_REFRESHING) {
                this.mArrowImageView.clearAnimation();
                this.mArrowImageView.setVisibility(View.INVISIBLE);
                this.mProgressBar.setVisibility(View.VISIBLE);
            } else {
                this.mArrowImageView.setVisibility(View.VISIBLE);
                this.mProgressBar.setVisibility(View.INVISIBLE);
            }
            switch (state) {
                case STATE_NORMAL /*0*/:
                    if (this.mState == STATE_READY) {
                        this.mArrowImageView.startAnimation(this.mRotateDownAnim);
                    }
                    if (this.mState == STATE_REFRESHING) {
                        this.mArrowImageView.clearAnimation();
                    }
                    this.mHintTextView.setText(R.string.xlistview_header_hint_normal);
                    break;
                case STATE_READY /*1*/:
                    if (this.mState != STATE_READY) {
                        this.mArrowImageView.clearAnimation();
                        this.mArrowImageView.startAnimation(this.mRotateUpAnim);
                        this.mHintTextView.setText(R.string.xlistview_header_hint_ready);
                        break;
                    }
                    break;
                case STATE_REFRESHING /*2*/:
                    this.mHintTextView.setText(R.string.xlistview_header_hint_loading);
                    break;
            }
            this.mState = state;
        }
    }

    public void setVisiableHeight(int height) {
        if (height < this.minHeight) {
            height = this.minHeight;
        }
        LayoutParams lp = (LayoutParams) this.mContainer.getLayoutParams();
        lp.height = height;
        this.mContainer.setLayoutParams(lp);
    }

    public int getVisiableHeight() {
        return this.mContainer.getHeight();
    }
}
