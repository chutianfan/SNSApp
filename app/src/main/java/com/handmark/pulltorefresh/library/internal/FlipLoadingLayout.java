package com.handmark.pulltorefresh.library.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView.ScaleType;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.gitrose.mobile.R;

@SuppressLint({"ViewConstructor"})
public class FlipLoadingLayout extends LoadingLayout {
    private static /* synthetic */ int[] f6389x57a4d715 = null;
    static final int FLIP_ANIMATION_DURATION = 150;
    private final Animation mResetRotateAnimation;
    private final Animation mRotateAnimation;

    static /* synthetic */ int[] m6475x57a4d715() {
        int[] iArr = f6389x57a4d715;
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
            f6389x57a4d715 = iArr;
        }
        return iArr;
    }

    public FlipLoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        int rotateAngle = mode == Mode.PULL_FROM_START ? -180 : 180;
        this.mRotateAnimation = new RotateAnimation(0.0f, (float) rotateAngle, 1, 0.5f, 1, 0.5f);
        this.mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        this.mRotateAnimation.setDuration(150);
        this.mRotateAnimation.setFillAfter(true);
        this.mResetRotateAnimation = new RotateAnimation((float) rotateAngle, 0.0f, 1, 0.5f, 1, 0.5f);
        this.mResetRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        this.mResetRotateAnimation.setDuration(150);
        this.mResetRotateAnimation.setFillAfter(true);
    }

    protected void onLoadingDrawableSet(Drawable imageDrawable) {
        if (imageDrawable != null) {
            int dHeight = imageDrawable.getIntrinsicHeight();
            int dWidth = imageDrawable.getIntrinsicWidth();
            LayoutParams lp = (LayoutParams) this.mHeaderImage.getLayoutParams();
            int max = Math.max(dHeight, dWidth);
            lp.height = max;
            lp.width = max;
            this.mHeaderImage.requestLayout();
            this.mHeaderImage.setScaleType(ScaleType.MATRIX);
            Matrix matrix = new Matrix();
            matrix.postTranslate(((float) (lp.width - dWidth)) / 2.0f, ((float) (lp.height - dHeight)) / 2.0f);
            matrix.postRotate(getDrawableRotationAngle(), ((float) lp.width) / 2.0f, ((float) lp.height) / 2.0f);
            this.mHeaderImage.setImageMatrix(matrix);
        }
    }

    protected void onPullImpl(float scaleOfLayout) {
    }

    protected void pullToRefreshImpl() {
        if (this.mRotateAnimation == this.mHeaderImage.getAnimation()) {
            this.mHeaderImage.startAnimation(this.mResetRotateAnimation);
        }
    }

    protected void refreshingImpl() {
        this.mHeaderImage.clearAnimation();
        this.mHeaderImage.setVisibility(View.INVISIBLE);
        this.mHeaderProgress.setVisibility(View.VISIBLE);
    }

    protected void releaseToRefreshImpl() {
        this.mHeaderImage.startAnimation(this.mRotateAnimation);
    }

    protected void resetImpl() {
        this.mHeaderImage.clearAnimation();
        this.mHeaderProgress.setVisibility(View.GONE);
        this.mHeaderImage.setVisibility(View.VISIBLE);
    }

    protected int getDefaultDrawableResId() {
        return R.drawable.swipe_load;
    }

    private float getDrawableRotationAngle() {
        switch (m6475x57a4d715()[this.mMode.ordinal()]) {
            case 2:
                if (this.mScrollDirection == Orientation.HORIZONTAL) {
                    return 270.0f;
                }
                return 0.0f;
            case 3:
                if (this.mScrollDirection == Orientation.HORIZONTAL) {
                    return 90.0f;
                }
                return 180.0f;
            default:
                return 0.0f;
        }
    }
}
