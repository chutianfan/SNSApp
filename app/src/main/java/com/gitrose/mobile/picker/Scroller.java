package com.gitrose.mobile.picker;

import android.content.Context;
import android.util.FloatMath;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.android.volley.DefaultRetryPolicy;
//import com.tencent.android.tpush.XGPushManager;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;

public class Scroller {
    private static float ALPHA = 0.0f;
    private static float DECELERATION_RATE = 0.0f;
    private static final int DEFAULT_DURATION = 250;
    private static float END_TENSION = 0.0f;
    private static final int FLING_MODE = 1;
    private static final int NB_SAMPLES = 100;
    private static final int SCROLL_MODE = 0;
    //private static final float[] SPLINE;
    private static float START_TENSION;
    private static float sViscousFluidNormalize;
    private static float sViscousFluidScale;
    private int mCurrX;
    private int mCurrY;
    private float mDeceleration;
    private float mDeltaX;
    private float mDeltaY;
    private int mDuration;
    private float mDurationReciprocal;
    private int mFinalX;
    private int mFinalY;
    private boolean mFinished;
    private boolean mFlywheel;
    private Interpolator mInterpolator;
    private int mMaxX;
    private int mMaxY;
    private int mMinX;
    private int mMinY;
    private int mMode;
    private final float mPpi;
    private long mStartTime;
    private int mStartX;
    private int mStartY;
    private float mVelocity;

//    static {
//        DECELERATION_RATE = (float) (Math.log(0.75d) / Math.log(0.9d));
//        ALPHA = 800.0f;
//        START_TENSION = 0.4f;
//        END_TENSION = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT - START_TENSION;
//        SPLINE = new float[XGPushManager.OPERATION_REQ_UNREGISTER];
//        float x_min = 0.0f;
//        for (int i = 0; i <= NB_SAMPLES; i += FLING_MODE) {
//            float x;
//            float coef;
//            float t = ((float) i) / 100.0f;
//            float x_max = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
//            while (true) {
//                x = x_min + ((x_max - x_min) / 2.0f);
//                coef = (3.0f * x) * (DefaultRetryPolicy.DEFAULT_BACKOFF_MULT - x);
//                float tx = ((((DefaultRetryPolicy.DEFAULT_BACKOFF_MULT - x) * START_TENSION) + (END_TENSION * x)) * coef) + ((x * x) * x);
//                if (((double) Math.abs(tx - t)) < 1.0E-5d) {
//                    break;
//                } else if (tx > t) {
//                    x_max = x;
//                } else {
//                    x_min = x;
//                }
//            }
//            SPLINE[i] = coef + ((x * x) * x);
//        }
//        SPLINE[NB_SAMPLES] = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
//        sViscousFluidScale = 8.0f;
//        sViscousFluidNormalize = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
//        sViscousFluidNormalize = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT / viscousFluid(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//    }

    public Scroller(Context context) {
        this(context, null);
    }

    public Scroller(Context context, Interpolator interpolator) {
        this(context, interpolator, context.getApplicationInfo().targetSdkVersion >= 11);
    }

    public Scroller(Context context, Interpolator interpolator, boolean flywheel) {
        this.mFinished = true;
        this.mInterpolator = interpolator;
        this.mPpi = context.getResources().getDisplayMetrics().density * 160.0f;
        this.mDeceleration = computeDeceleration(ViewConfiguration.getScrollFriction());
        this.mFlywheel = flywheel;
    }

    public final void setFriction(float friction) {
        this.mDeceleration = computeDeceleration(friction);
    }

    private float computeDeceleration(float friction) {
        return (386.0878f * this.mPpi) * friction;
    }

    public final boolean isFinished() {
        return this.mFinished;
    }

    public final void forceFinished(boolean finished) {
        this.mFinished = finished;
    }

    public final int getDuration() {
        return this.mDuration;
    }

    public final int getCurrX() {
        return this.mCurrX;
    }

    public final int getCurrY() {
        return this.mCurrY;
    }

    public float getCurrVelocity() {
        return this.mVelocity - ((this.mDeceleration * ((float) timePassed())) / 2000.0f);
    }

    public final int getStartX() {
        return this.mStartX;
    }

    public final int getStartY() {
        return this.mStartY;
    }

    public final int getFinalX() {
        return this.mFinalX;
    }

    public final int getFinalY() {
        return this.mFinalY;
    }

    public boolean computeScrollOffset() {
//        if (this.mFinished) {
//            return false;
//        }
//        int timePassed = (int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
//        if (timePassed < this.mDuration) {
//            switch (this.mMode) {
//                case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
//                    float x = ((float) timePassed) * this.mDurationReciprocal;
//                    if (this.mInterpolator == null) {
//                        x = viscousFluid(x);
//                    } else {
//                        x = this.mInterpolator.getInterpolation(x);
//                    }
//                    this.mCurrX = this.mStartX + Math.round(this.mDeltaX * x);
//                    this.mCurrY = this.mStartY + Math.round(this.mDeltaY * x);
//                    return true;
//                case FLING_MODE /*1*/:
//                    float t = ((float) timePassed) / ((float) this.mDuration);
//                    int index = (int) (100.0f * t);
//                    float t_inf = ((float) index) / 100.0f;
//                    float t_sup = ((float) (index + FLING_MODE)) / 100.0f;
//                    float d_inf = SPLINE[index];
//                    float distanceCoef = d_inf + (((t - t_inf) / (t_sup - t_inf)) * (SPLINE[index + FLING_MODE] - d_inf));
//                    this.mCurrX = this.mStartX + Math.round(((float) (this.mFinalX - this.mStartX)) * distanceCoef);
//                    this.mCurrX = Math.min(this.mCurrX, this.mMaxX);
//                    this.mCurrX = Math.max(this.mCurrX, this.mMinX);
//                    this.mCurrY = this.mStartY + Math.round(((float) (this.mFinalY - this.mStartY)) * distanceCoef);
//                    this.mCurrY = Math.min(this.mCurrY, this.mMaxY);
//                    this.mCurrY = Math.max(this.mCurrY, this.mMinY);
//                    if (this.mCurrX != this.mFinalX || this.mCurrY != this.mFinalY) {
//                        return true;
//                    }
//                    this.mFinished = true;
//                    return true;
//                default:
//                    return true;
//            }
//        }
        this.mCurrX = this.mFinalX;
        this.mCurrY = this.mFinalY;
        this.mFinished = true;
        return true;
    }

    public void startScroll(int startX, int startY, int dx, int dy) {
        startScroll(startX, startY, dx, dy, DEFAULT_DURATION);
    }

    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        this.mMode = 0;
        this.mFinished = false;
        this.mDuration = duration;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mStartX = startX;
        this.mStartY = startY;
        this.mFinalX = startX + dx;
        this.mFinalY = startY + dy;
        this.mDeltaX = (float) dx;
        this.mDeltaY = (float) dy;
        this.mDurationReciprocal = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT / ((float) this.mDuration);
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
//        float coeffX;
//        float coeffY;
//        if (this.mFlywheel && !this.mFinished) {
//            float oldVel = getCurrVelocity();
//            float dx = (float) (this.mFinalX - this.mStartX);
//            float dy = (float) (this.mFinalY - this.mStartY);
//            float hyp = FloatMath.sqrt((dx * dx) + (dy * dy));
//            float oldVelocityX = (dx / hyp) * oldVel;
//            float oldVelocityY = (dy / hyp) * oldVel;
//            if (Math.signum((float) velocityX) == Math.signum(oldVelocityX)) {
//                if (Math.signum((float) velocityY) == Math.signum(oldVelocityY)) {
//                    velocityX = (int) (((float) velocityX) + oldVelocityX);
//                    velocityY = (int) (((float) velocityY) + oldVelocityY);
//                }
//            }
//        }
//        this.mMode = FLING_MODE;
//        this.mFinished = false;
//        float velocity = FloatMath.sqrt((float) ((velocityX * velocityX) + (velocityY * velocityY)));
//        this.mVelocity = velocity;
//        double l = Math.log((double) ((START_TENSION * velocity) / ALPHA));
//        this.mDuration = (int) (1000.0d * Math.exp(l / (((double) DECELERATION_RATE) - 1.0d)));
//        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
//        this.mStartX = startX;
//        this.mStartY = startY;
//        if (velocity == 0.0f) {
//            coeffX = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
//        } else {
//            coeffX = ((float) velocityX) / velocity;
//        }
//        if (velocity == 0.0f) {
//            coeffY = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
//        } else {
//            coeffY = ((float) velocityY) / velocity;
//        }
//        int totalDistance = (int) (((double) ALPHA) * Math.exp((((double) DECELERATION_RATE) / (((double) DECELERATION_RATE) - 1.0d)) * l));
//        this.mMinX = minX;
//        this.mMaxX = maxX;
//        this.mMinY = minY;
//        this.mMaxY = maxY;
//        this.mFinalX = Math.round(((float) totalDistance) * coeffX) + startX;
//        this.mFinalX = Math.min(this.mFinalX, this.mMaxX);
//        this.mFinalX = Math.max(this.mFinalX, this.mMinX);
//        this.mFinalY = Math.round(((float) totalDistance) * coeffY) + startY;
//        this.mFinalY = Math.min(this.mFinalY, this.mMaxY);
//        this.mFinalY = Math.max(this.mFinalY, this.mMinY);
    }

    static float viscousFluid(float x) {
        x *= sViscousFluidScale;
        if (x < DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) {
            x -= DefaultRetryPolicy.DEFAULT_BACKOFF_MULT - ((float) Math.exp((double) (-x)));
        } else {
            x = 0.36787945f + ((DefaultRetryPolicy.DEFAULT_BACKOFF_MULT - 0.36787945f) * (DefaultRetryPolicy.DEFAULT_BACKOFF_MULT - ((float) Math.exp((double) (DefaultRetryPolicy.DEFAULT_BACKOFF_MULT - x)))));
        }
        return x * sViscousFluidNormalize;
    }

    public void abortAnimation() {
        this.mCurrX = this.mFinalX;
        this.mCurrY = this.mFinalY;
        this.mFinished = true;
    }

    public void extendDuration(int extend) {
        this.mDuration = timePassed() + extend;
        this.mDurationReciprocal = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT / ((float) this.mDuration);
        this.mFinished = false;
    }

    public int timePassed() {
        return (int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
    }

    public void setFinalX(int newX) {
        this.mFinalX = newX;
        this.mDeltaX = (float) (this.mFinalX - this.mStartX);
        this.mFinished = false;
    }

    public void setFinalY(int newY) {
        this.mFinalY = newY;
        this.mDeltaY = (float) (this.mFinalY - this.mStartY);
        this.mFinished = false;
    }

    public boolean isScrollingInDirection(float xvel, float yvel) {
        return !this.mFinished && Math.signum(xvel) == Math.signum((float) (this.mFinalX - this.mStartX)) && Math.signum(yvel) == Math.signum((float) (this.mFinalY - this.mStartY));
    }
}
