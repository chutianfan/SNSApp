package com.handmark.pulltorefresh.library.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.gitrose.mobile.R;

import qalsdk.BaseConstants;

@SuppressLint({"ViewConstructor"})
public abstract class LoadingLayout extends FrameLayout implements ILoadingLayout {
    private static /* synthetic */ int[] f4896x57a4d715 = null;
    private static /* synthetic */ int[] f4897x9c6cb41e = null;
    static final Interpolator ANIMATION_INTERPOLATOR;
    static final String LOG_TAG = "PullToRefresh-LoadingLayout";
    private boolean hideAllViews;
    protected final ImageView mHeaderImage;
    protected final ProgressBar mHeaderProgress;
    private final TextView mHeaderText;
    private FrameLayout mInnerLayout;
    protected final Mode mMode;
    private final TextView mPasswordText;
    private CharSequence mPullLabel;
    private CharSequence mRefreshingLabel;
    private CharSequence mReleaseLabel;
    protected final Orientation mScrollDirection;
    private final TextView mSubHeaderText;
    private boolean mUseIntrinsicAnimation;

    protected abstract int getDefaultDrawableResId();

    protected abstract void onLoadingDrawableSet(Drawable drawable);

    protected abstract void onPullImpl(float f);

    protected abstract void pullToRefreshImpl();

    protected abstract void refreshingImpl();

    protected abstract void releaseToRefreshImpl();

    protected abstract void resetImpl();

    static /* synthetic */ int[] m5247x57a4d715() {
        int[] iArr = f4896x57a4d715;
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
            f4896x57a4d715 = iArr;
        }
        return iArr;
    }

    static /* synthetic */ int[] m5248x9c6cb41e() {
        int[] iArr = f4897x9c6cb41e;
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
            f4897x9c6cb41e = iArr;
        }
        return iArr;
    }

    static {
        ANIMATION_INTERPOLATOR = new LinearInterpolator();
    }

    public LoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs) {

        super(context);
        ColorStateList colors;
        
        this.hideAllViews = false;
        this.mMode = mode;
        this.mScrollDirection = scrollDirection;
        switch (m5248x9c6cb41e()[scrollDirection.ordinal()]) {
            case 2:
                LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header_horizontal, this);
                break;
            default:
                LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header_vertical, this);
                break;
        }
        this.mInnerLayout = (FrameLayout) findViewById(R.id.fl_inner);
        this.mHeaderText = (TextView) this.mInnerLayout.findViewById(R.id.pull_to_refresh_text);
        this.mHeaderProgress = (ProgressBar) this.mInnerLayout.findViewById(R.id.pull_to_refresh_progress);
        this.mSubHeaderText = (TextView) this.mInnerLayout.findViewById(R.id.pull_to_refresh_sub_text);
        this.mHeaderImage = (ImageView) this.mInnerLayout.findViewById(R.id.pull_to_refresh_image);
        this.mPasswordText = (TextView) this.mInnerLayout.findViewById(R.id.pull_to_refresh_password_text);
        LayoutParams lp = (LayoutParams) this.mInnerLayout.getLayoutParams();
        switch (m5247x57a4d715()[mode.ordinal()]) {
            case 3:
                lp.gravity = scrollDirection == Orientation.VERTICAL ? 48 : 3;
                this.mPullLabel = context.getString(R.string.pull_to_refresh_from_bottom_pull_label);
                this.mRefreshingLabel = context.getString(R.string.pull_to_refresh_from_bottom_refreshing_label);
                this.mReleaseLabel = context.getString(R.string.pull_to_refresh_from_bottom_release_label);
                break;
            default:
                lp.gravity = scrollDirection == Orientation.VERTICAL ? 80 : 5;
                this.mPullLabel = context.getString(R.string.pull_to_refresh_pull_label);
                this.mRefreshingLabel = context.getString(R.string.pull_to_refresh_refreshing_label);
                this.mReleaseLabel = context.getString(R.string.pull_to_refresh_release_label);
                break;
        }
        if (attrs.hasValue(1)) {
            Drawable background = attrs.getDrawable(1);
            if (background != null) {
                ViewCompat.setBackground(this, background);
            }
        }
        if (attrs.hasValue(10)) {
            TypedValue styleID = new TypedValue();
            attrs.getValue(10, styleID);
            setTextAppearance(styleID.data);
        }
        if (attrs.hasValue(11)) {
            TypedValue styleID = new TypedValue();
            attrs.getValue(11, styleID);
            setSubTextAppearance(styleID.data);
        }
        if (attrs.hasValue(2)) {
            colors = attrs.getColorStateList(2);
            if (colors != null) {
                setTextColor(colors);
            }
        }
        if (attrs.hasValue(3)) {
            colors = attrs.getColorStateList(3);
            if (colors != null) {
                setSubTextColor(colors);
            }
        }
        Drawable imageDrawable = null;
        if (attrs.hasValue(6)) {
            imageDrawable = attrs.getDrawable(6);
        }
        switch (m5247x57a4d715()[mode.ordinal()]) {
            case 3:
                if (!attrs.hasValue(8)) {
                    if (attrs.hasValue(18)) {
                        Utils.warnDeprecation("ptrDrawableBottom", "ptrDrawableEnd");
                        imageDrawable = attrs.getDrawable(18);
                        break;
                    }
                }
                imageDrawable = attrs.getDrawable(8);
                break;
            default:
                if (!attrs.hasValue(7)) {
                    if (attrs.hasValue(17)) {
                        Utils.warnDeprecation("ptrDrawableTop", "ptrDrawableStart");
                        imageDrawable = attrs.getDrawable(17);
                        break;
                    }
                }
                imageDrawable = attrs.getDrawable(7);
                break;
        }
        if (imageDrawable == null) {
            imageDrawable = context.getResources().getDrawable(getDefaultDrawableResId());
        }
        setLoadingDrawable(imageDrawable);
        reset();
    }

    public final void setHeight(int height) {
        getLayoutParams().height = height;
        requestLayout();
    }

    public final void setWidth(int width) {
        getLayoutParams().width = width;
        requestLayout();
    }

    public final int getContentSize() {
        switch (m5248x9c6cb41e()[this.mScrollDirection.ordinal()]) {
            case 2:
                return this.mInnerLayout.getWidth();
            default:
                return this.mInnerLayout.getHeight();
        }
    }

    public final void hideAllViews() {
        if (this.mHeaderText.getVisibility() == View.VISIBLE) {
            this.mHeaderText.setVisibility(View.INVISIBLE);
        }
        if (this.mHeaderProgress.getVisibility() == View.VISIBLE) {
            this.mHeaderProgress.setVisibility(View.INVISIBLE);
        }
        if (this.mHeaderImage.getVisibility() == View.VISIBLE) {
            this.mHeaderImage.setVisibility(View.INVISIBLE);
        }
        if (this.mSubHeaderText.getVisibility() == View.VISIBLE) {
            this.mSubHeaderText.setVisibility(View.INVISIBLE);
        }
    }

    public final void hideAllViewsForGONE() {
        if (this.mInnerLayout != null) {
            this.mInnerLayout.setVisibility(View.GONE);
        }
        if (this.mHeaderText != null) {
            this.mHeaderText.setVisibility(View.GONE);
        }
        if (this.mHeaderProgress != null) {
            this.mHeaderProgress.setVisibility(View.GONE);
        }
        if (this.mHeaderImage != null) {
            this.mHeaderImage.setVisibility(View.GONE);
        }
        if (this.mSubHeaderText != null) {
            this.mSubHeaderText.setVisibility(View.GONE);
        }
    }

    public final void onPull(float scaleOfLayout) {
        if (!this.mUseIntrinsicAnimation) {
            onPullImpl(scaleOfLayout);
        }
    }

    public final void pullToRefresh() {
        if (this.mHeaderText != null) {
            this.mHeaderText.setText(this.mPullLabel);
        }
        pullToRefreshImpl();
    }

    public final void refreshing() {
        if (this.mHeaderText != null) {
            this.mHeaderText.setText(this.mRefreshingLabel);
        }
        if (this.mUseIntrinsicAnimation) {
            ((AnimationDrawable) this.mHeaderImage.getDrawable()).start();
        } else {
            refreshingImpl();
        }
        if (this.mSubHeaderText != null) {
            this.mSubHeaderText.setVisibility(View.GONE);
        }
    }

    public final void releaseToRefresh() {
        if (this.mHeaderText != null) {
            this.mHeaderText.setText(this.mReleaseLabel);
        }
        releaseToRefreshImpl();
    }

    public final void reset() {
        if (this.hideAllViews) {
            hideAllViewsForGONE();
            return;
        }
        if (this.mHeaderText != null) {
            this.mHeaderText.setText(this.mPullLabel);
        }
        this.mHeaderImage.setVisibility(View.VISIBLE);
        if (this.mUseIntrinsicAnimation) {
            ((AnimationDrawable) this.mHeaderImage.getDrawable()).stop();
        } else {
            resetImpl();
        }
        if (this.mSubHeaderText == null) {
            return;
        }
        if (TextUtils.isEmpty(this.mSubHeaderText.getText())) {
            this.mSubHeaderText.setVisibility(View.GONE);
        } else {
            this.mSubHeaderText.setVisibility(View.VISIBLE);
        }
    }

    public void setLastUpdatedLabel(CharSequence label) {
        setSubHeaderText(label);
    }

    public final void setLoadingDrawable(Drawable imageDrawable) {
        this.mHeaderImage.setImageDrawable(imageDrawable);
        this.mUseIntrinsicAnimation = imageDrawable instanceof AnimationDrawable;
        onLoadingDrawableSet(imageDrawable);
    }

    public void setPullLabel(CharSequence pullLabel) {
        this.mPullLabel = pullLabel;
    }

    public void setRefreshingLabel(CharSequence refreshingLabel) {
        this.mRefreshingLabel = refreshingLabel;
    }

    public void setReleaseLabel(CharSequence releaseLabel) {
        this.mReleaseLabel = releaseLabel;
    }

    public void setPasswordText(String password) {
        if (this.mPasswordText != null) {
            if (password == null || BaseConstants.ah.equals(password)) {
                this.mPasswordText.setVisibility(View.GONE);
            } else {
                this.mPasswordText.setVisibility(View.VISIBLE);
            }
            this.mPasswordText.setText(password);
        }
    }

    public void setTextTypeface(Typeface tf) {
        this.mHeaderText.setTypeface(tf);
    }

    public final void showInvisibleViews() {
        if (View.INVISIBLE == this.mHeaderText.getVisibility()) {
            this.mHeaderText.setVisibility(View.VISIBLE);
        }
        if (View.INVISIBLE == this.mHeaderProgress.getVisibility()) {
            this.mHeaderProgress.setVisibility(View.VISIBLE);
        }
        if (View.INVISIBLE == this.mHeaderImage.getVisibility()) {
            this.mHeaderImage.setVisibility(View.VISIBLE);
        }
        if (View.INVISIBLE == this.mSubHeaderText.getVisibility()) {
            this.mSubHeaderText.setVisibility(View.VISIBLE);
        }
    }

    public void setHide(boolean ishide) {
        this.hideAllViews = ishide;
        if (ishide) {
            hideAllViewsForGONE();
        }
    }

    private void setSubHeaderText(CharSequence label) {
        if (this.mSubHeaderText == null) {
            return;
        }
        if (TextUtils.isEmpty(label)) {
            this.mSubHeaderText.setVisibility(View.GONE);
            return;
        }
        this.mSubHeaderText.setText(label);
        if (View.GONE == this.mSubHeaderText.getVisibility()) {
            this.mSubHeaderText.setVisibility(View.VISIBLE);
        }
    }

    private void setSubTextAppearance(int value) {
        if (this.mSubHeaderText != null) {
            this.mSubHeaderText.setTextAppearance(getContext(), value);
        }
    }

    private void setSubTextColor(ColorStateList color) {
        if (this.mSubHeaderText != null) {
            this.mSubHeaderText.setTextColor(color);
        }
    }

    private void setTextAppearance(int value) {
        if (this.mHeaderText != null) {
            this.mHeaderText.setTextAppearance(getContext(), value);
        }
        if (this.mSubHeaderText != null) {
            this.mSubHeaderText.setTextAppearance(getContext(), value);
        }
    }

    private void setTextColor(ColorStateList color) {
        if (this.mHeaderText != null) {
            this.mHeaderText.setTextColor(color);
        }
        if (this.mSubHeaderText != null) {
            this.mSubHeaderText.setTextColor(color);
        }
    }
}
