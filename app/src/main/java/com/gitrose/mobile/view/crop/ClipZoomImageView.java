package com.gitrose.mobile.view.crop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.android.volley.DefaultRetryPolicy;
//import com.sea_monster.core.network.ApiReqeust;
import com.gitrose.mobile.C0306R;
import com.gitrose.mobile.CropPicActivity;
//import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
//import shouji.gexing.framework.utils.ToastUtil;

public class ClipZoomImageView extends ImageView implements OnScaleGestureListener, OnTouchListener, OnGlobalLayoutListener {
    public static float SCALE_MAX;
    private static float SCALE_MID;
    private static final String TAG;
    public float initScale;
    private boolean isAutoScale;
    private boolean isCanDrag;
    private int lastPointerCount;
    boolean ll_bg_white;
    private GestureDetector mGestureDetector;
    private int mHorizontalPadding;
    private float mLastX;
    private float mLastY;
    private ScaleGestureDetector mScaleGestureDetector;
    public final Matrix mScaleMatrix;
    private int mTouchSlop;
    private int mVerticalPadding;
    private final float[] matrixValues;
    Context mcontext;
    private boolean once;

    /* renamed from: com.gitrose.mobile.view.crop.ClipZoomImageView.1 */
    class C03731 extends SimpleOnGestureListener {
        C03731() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (!ClipZoomImageView.this.isAutoScale && (ClipImageLayout.getObject() == null || !ClipImageLayout.getObject().is_to_small)) {
                float x = e.getX();
                float y = e.getY();
                if (ClipZoomImageView.this.getScale() < ClipZoomImageView.SCALE_MID) {
                    ClipZoomImageView.this.postDelayed(new AutoScaleRunnable(ClipZoomImageView.SCALE_MID, x, y), 16);
                    ClipZoomImageView.this.isAutoScale = true;
                } else {
                    ClipZoomImageView.this.postDelayed(new AutoScaleRunnable(ClipZoomImageView.this.initScale, x, y), 16);
                    ClipZoomImageView.this.isAutoScale = true;
                }
            }
            return true;
        }
    }

    private class AutoScaleRunnable implements Runnable {
        static final float BIGGER = 1.07f;
        static final float SMALLER = 0.93f;
        private float mTargetScale;
        private float tmpScale;
        private float f1183x;
        private float f1184y;

        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.mTargetScale = targetScale;
            this.f1183x = x;
            this.f1184y = y;
            if (ClipZoomImageView.this.getScale() < this.mTargetScale) {
                this.tmpScale = BIGGER;
            } else {
                this.tmpScale = SMALLER;
            }
        }

        public void run() {
            ClipZoomImageView.this.mScaleMatrix.postScale(this.tmpScale, this.tmpScale, this.f1183x, this.f1184y);
            ClipZoomImageView.this.checkBorder();
            ClipZoomImageView.this.setImageMatrix(ClipZoomImageView.this.mScaleMatrix);
            float currentScale = ClipZoomImageView.this.getScale();
            if ((this.tmpScale <= DefaultRetryPolicy.DEFAULT_BACKOFF_MULT || currentScale >= this.mTargetScale) && (this.tmpScale >= DefaultRetryPolicy.DEFAULT_BACKOFF_MULT || this.mTargetScale >= currentScale)) {
                float deltaScale = this.mTargetScale / currentScale;
                ClipZoomImageView.this.mScaleMatrix.postScale(deltaScale, deltaScale, this.f1183x, this.f1184y);
                ClipZoomImageView.this.checkBorder();
                ClipZoomImageView.this.setImageMatrix(ClipZoomImageView.this.mScaleMatrix);
                ClipZoomImageView.this.isAutoScale = false;
                return;
            }
            ClipZoomImageView.this.postDelayed(this, 16);
        }
    }

    static {
        TAG = ClipZoomImageView.class.getSimpleName();
        SCALE_MAX = 4.0f;
        SCALE_MID = 2.0f;
    }

    public ClipZoomImageView(Context context) {
        this(context, null);
    }

    public ClipZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initScale = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
        this.once = true;
        this.matrixValues = new float[9];
        this.mScaleGestureDetector = null;
        this.mScaleMatrix = new Matrix();
        this.ll_bg_white = true;
        this.mcontext = context;
        setScaleType(ScaleType.MATRIX);
        this.mGestureDetector = new GestureDetector(context, new C03731());
        this.mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
    }

    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();
        if (getDrawable() != null && ((scale < SCALE_MAX && scaleFactor > DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) || (scale > this.initScale && scaleFactor < DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))) {
            if (scaleFactor * scale < this.initScale) {
                scaleFactor = this.initScale / scale;
            }
            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale;
            }
            this.mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBorder();
            setImageMatrix(this.mScaleMatrix);
        }
        return true;
    }

    public RectF getMatrixRectF() {
        Matrix matrix = this.mScaleMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rect.set(0.0f, 0.0f, (float) d.getIntrinsicWidth(), (float) d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    public boolean onScaleBegin(ScaleGestureDetector detector) {
        this.initScale = ClipImageLayout.getObject().getscale();
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (ClipImageLayout.getObject() == null || !ClipImageLayout.getObject().is_to_small || event.getPointerCount() <= 1) {
            if (ClipImageLayout.getObject() != null) {
                ClipImageLayout.getObject().initDivderScale();
            }
            if (!this.mGestureDetector.onTouchEvent(event)) {
                this.mScaleGestureDetector.onTouchEvent(event);
                float x = 0.0f;
                float y = 0.0f;
                int pointerCount = event.getPointerCount();
                int i = 0;
                while (i < pointerCount) {
                    try {
                        x += event.getX(i);
                        y += event.getY(i);
                        i++;
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
                x /= (float) pointerCount;
                y /= (float) pointerCount;
                if (pointerCount != this.lastPointerCount) {
                    this.isCanDrag = false;
                    this.mLastX = x;
                    this.mLastY = y;
                }
                this.lastPointerCount = pointerCount;
                RectF rectF;
                switch (event.getAction()) {
//                    case TopicDetailPagerActivity.FLOW_FIRST_WHAT /*1*/:
//                    case ApiReqeust.PUT_METHOD /*3*/:
//                        this.lastPointerCount = 0;
//                        if (ClipImageLayout.getObject() != null && ClipImageLayout.getObject().is_to_small) {
//                            if (this.ll_bg_white) {
//                                setBackgroundResource(C0306R.color.home_bg);
//                                this.ll_bg_white = false;
//                            } else {
//                                setBackgroundResource(C0306R.color.white);
//                                this.ll_bg_white = true;
//                            }
//                        }
//                        if (getDrawable() != null) {
//                            rectF = getMatrixRectF();
//                            if (rectF.width() <= ((float) (getWidth() - (this.mHorizontalPadding * 2))) && rectF.height() <= ((float) (getHeight() - (this.mVerticalPadding * 2)))) {
//                                ClipImageLayout.getObject().toFullSmall(true);
//                                if (CropPicActivity.getObject() != null) {
//                                    CropPicActivity.getObject().initChangeBt(true);
//                                    break;
//                                }
//                            }
//                        }
//                        break;
                    case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
                        float dx = x - this.mLastX;
                        float dy = y - this.mLastY;
                        if (!this.isCanDrag) {
                            this.isCanDrag = isCanDrag(dx, dy);
                        }
                        if (this.isCanDrag && getDrawable() != null) {
                            rectF = getMatrixRectF();
                            if (rectF.width() <= ((float) (getWidth() - (this.mHorizontalPadding * 2)))) {
                                dx = 0.0f;
                            }
                            if (rectF.height() <= ((float) (getHeight() - (this.mVerticalPadding * 2)))) {
                                dy = 0.0f;
                            }
                            this.mScaleMatrix.postTranslate(dx, dy);
                            checkBorder();
                            setImageMatrix(this.mScaleMatrix);
                        }
                        this.mLastX = x;
                        this.mLastY = y;
                        break;
                    default:
                        break;
                }
            }
        }
        //ToastUtil.toastShort(this.mcontext, "\u5f53\u524d\u56fe\u7247\u5df2\u9501\u5b9a\uff0c\u4e0d\u80fd\u62d6\u52a8\u54e6", 1);
        return true;
    }

    public final float getScale() {
        this.mScaleMatrix.getValues(this.matrixValues);
        return Math.abs(this.matrixValues[0] == 0.0f ? this.matrixValues[1] : this.matrixValues[0]);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    public void onGlobalLayout() {
        if (this.once) {
            Drawable d = getDrawable();
            if (d != null) {
                this.mVerticalPadding = (getHeight() - (getWidth() - (this.mHorizontalPadding * 2))) / 2;
                int width = getWidth();
                int height = getHeight();
                int dw = d.getIntrinsicWidth();
                int dh = d.getIntrinsicHeight();
                float scale = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
                if (dw < getWidth() - (this.mHorizontalPadding * 2) && dh > getHeight() - (this.mVerticalPadding * 2)) {
                    scale = ((((float) getWidth()) * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) - ((float) (this.mHorizontalPadding * 2))) / ((float) dw);
                }
                if (dh < getHeight() - (this.mVerticalPadding * 2) && dw > getWidth() - (this.mHorizontalPadding * 2)) {
                    scale = ((((float) getHeight()) * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) - ((float) (this.mVerticalPadding * 2))) / ((float) dh);
                }
                if (dw < getWidth() - (this.mHorizontalPadding * 2) && dh < getHeight() - (this.mVerticalPadding * 2)) {
                    scale = Math.max(((((float) getWidth()) * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) - ((float) (this.mHorizontalPadding * 2))) / ((float) dw), ((((float) getHeight()) * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) - ((float) (this.mVerticalPadding * 2))) / ((float) dh));
                }
                this.initScale = scale;
                SCALE_MID = this.initScale * 2.0f;
                SCALE_MAX = this.initScale * 4.0f;
                this.mScaleMatrix.postTranslate((float) ((width - dw) / 2), (float) ((height - dh) / 2));
                this.mScaleMatrix.postScale(scale, scale, (float) (getWidth() / 2), (float) (getHeight() / 2));
                setImageMatrix(this.mScaleMatrix);
                this.once = false;
            }
        }
    }

    public Bitmap clip(boolean is_to_small) {
        return Bitmap.createBitmap(canvasBorder(), this.mHorizontalPadding, this.mVerticalPadding, getWidth() - (this.mHorizontalPadding * 2), getWidth() - (this.mHorizontalPadding * 2));
    }

    public Bitmap canvasBorder() {
        float left = getMatrixRectF().left;
        float right = getMatrixRectF().right;
        float top = getMatrixRectF().top;
        float bottom = getMatrixRectF().bottom;
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        Paint mPaint = new Paint();
        this.mVerticalPadding = (getHeight() - (getWidth() - (this.mHorizontalPadding * 2))) / 2;
        mPaint.setColor(Color.parseColor("#f2f3f7"));
        mPaint.setStyle(Style.FILL);
        canvas.drawRect((float) this.mHorizontalPadding, (float) this.mVerticalPadding, left, (float) (getHeight() - this.mVerticalPadding), mPaint);
        canvas.drawRect(right, (float) this.mVerticalPadding, (float) (getWidth() - this.mHorizontalPadding), (float) (getHeight() - this.mVerticalPadding), mPaint);
        canvas.drawRect(left, (float) this.mVerticalPadding, right, top, mPaint);
        Canvas canvas2 = canvas;
        float f = left;
        float f2 = right;
        canvas2.drawRect(f, bottom, f2, (float) (getHeight() - this.mVerticalPadding), mPaint);
        return bitmap;
    }

    public void checkBorder() {
        RectF rect = getMatrixRectF();
        float deltaX = 0.0f;
        float deltaY = 0.0f;
        int width = getWidth();
        int height = getHeight();
        Log.e(TAG, "rect.width() =  " + rect.width() + " , width - 2 * mHorizontalPadding =" + (width - (this.mHorizontalPadding * 2)));
        if (((double) rect.width()) + 0.01d >= ((double) (width - (this.mHorizontalPadding * 2)))) {
            if (rect.left > ((float) this.mHorizontalPadding)) {
                deltaX = (-rect.left) + ((float) this.mHorizontalPadding);
            }
            if (rect.right < ((float) (width - this.mHorizontalPadding))) {
                deltaX = ((float) (width - this.mHorizontalPadding)) - rect.right;
            }
        }
        if (((double) rect.height()) + 0.01d >= ((double) (height - (this.mVerticalPadding * 2)))) {
            if (rect.top > ((float) this.mVerticalPadding)) {
                deltaY = (-rect.top) + ((float) this.mVerticalPadding);
            }
            if (rect.bottom < ((float) (height - this.mVerticalPadding))) {
                deltaY = ((float) (height - this.mVerticalPadding)) - rect.bottom;
            }
        }
        this.mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((double) ((dx * dx) + (dy * dy))) >= ((double) this.mTouchSlop);
    }

    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }
}
