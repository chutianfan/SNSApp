package com.gitrose.mobile.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.gitrose.mobile.R;

public class CircleImageView extends ImageView {
    private static final Config BITMAP_CONFIG;
    private static final int COLORDRAWABLE_DIMENSION = 1;
    private static final int DEFAULT_BORDER_COLOR = -16777216;
    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final ScaleType SCALE_TYPE;
    private int convex;
    private boolean isHasSelected;
    private Bitmap mBitmap;
    private int mBitmapHeight;
    private final Paint mBitmapPaint;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBorderColor;
    private final Paint mBorderPaint;
    private float mBorderRadius;
    private final RectF mBorderRect;
    private int mBorderWidth;
    private float mDrawableRadius;
    private final RectF mDrawableRect;
    private boolean mReady;
    private final Paint mSelectedPaint;
    private boolean mSetupPending;
    private final Matrix mShaderMatrix;

    static {
        SCALE_TYPE = ScaleType.CENTER_CROP;
        BITMAP_CONFIG = Config.RGB_565;
    }

    public CircleImageView(Context context) {
        super(context);
        this.mDrawableRect = new RectF();
        this.mBorderRect = new RectF();
        this.mShaderMatrix = new Matrix();
        this.mBitmapPaint = new Paint();
        this.mBorderPaint = new Paint();
        this.mSelectedPaint = new Paint();
        this.mBorderColor = DEFAULT_BORDER_COLOR;
        this.mBorderWidth = 0;
        this.convex = 0;
        init();
    }

    public CircleImageView(Context context, boolean isNeedPoint) {
        super(context);
        this.mDrawableRect = new RectF();
        this.mBorderRect = new RectF();
        this.mShaderMatrix = new Matrix();
        this.mBitmapPaint = new Paint();
        this.mBorderPaint = new Paint();
        this.mSelectedPaint = new Paint();
        this.mBorderColor = DEFAULT_BORDER_COLOR;
        this.mBorderWidth = 0;
        this.convex = 0;
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mDrawableRect = new RectF();
        this.mBorderRect = new RectF();
        this.mShaderMatrix = new Matrix();
        this.mBitmapPaint = new Paint();
        this.mBorderPaint = new Paint();
        this.mSelectedPaint = new Paint();
        this.mBorderColor = DEFAULT_BORDER_COLOR;
        this.mBorderWidth = 0;
        this.convex = 0;
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0);
//        this.mBorderWidth = a.getDimensionPixelSize(0, 0);
//        this.mBorderColor = a.getColor(COLORDRAWABLE_DIMENSION, DEFAULT_BORDER_COLOR);
//        a.recycle();
        init();
    }

    private void init() {
        //this.convex = (int) getResources().getDimension(R.dimen.item_avatar_convex);
        super.setScaleType(SCALE_TYPE);
        this.mReady = true;
        if (this.mSetupPending) {
            setup();
            this.mSetupPending = false;
        }
    }

    public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            Object[] objArr = new Object[COLORDRAWABLE_DIMENSION];
            objArr[0] = scaleType;
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", objArr));
        }
    }

    protected void onDraw(Canvas canvas) {
        if (getDrawable() != null) {
            if (this.isHasSelected) {
                Path path = new Path();
                path.moveTo((float) (getWidth() / 2), 0.0f);
                path.lineTo((float) ((getWidth() / 2) - (this.convex * 7)), (float) (this.convex * 5));
                path.lineTo((float) ((getWidth() / 2) + (this.convex * 7)), (float) (this.convex * 5));
                path.lineTo((float) (getWidth() / 2), 0.0f);
                int color = getResources().getColor(R.color.action_bar_bg);
                this.mSelectedPaint.setColor(color);
                this.mBorderPaint.setColor(color);
                canvas.drawPath(path, this.mSelectedPaint);
                canvas.drawCircle((float) (getWidth() / 2), (float) ((getHeight() / 2) + this.convex), this.mDrawableRadius - ((float) this.convex), this.mBitmapPaint);
                canvas.drawCircle((float) (getWidth() / 2), (float) ((getHeight() / 2) + this.convex), this.mBorderRadius - ((float) this.convex), this.mBorderPaint);
                return;
            }
            canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), this.mDrawableRadius, this.mBitmapPaint);
            if (this.mBorderWidth != 0) {
                canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), this.mBorderRadius, this.mBorderPaint);
            }
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public void setBorderColor(int borderColor) {
        if (borderColor != this.mBorderColor) {
            this.mBorderColor = borderColor;
            this.mBorderPaint.setColor(this.mBorderColor);
            invalidate();
        }
    }

    public int getBorderWidth() {
        return this.mBorderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        if (borderWidth != this.mBorderWidth) {
            this.mBorderWidth = borderWidth;
            setup();
        }
    }

    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        this.mBitmap = bm;
        setup();
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        this.mBitmap = getBitmapFromDrawable(drawable);
        setup();
    }

    public void setImageResource(int resId) {
        super.setImageResource(resId);
        this.mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        this.mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }
            if (bitmap == null) {
                return bitmap;
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    private void setup() {
        if (!this.mReady) {
            this.mSetupPending = true;
        } else if (this.mBitmap != null) {
            this.mBitmapShader = new BitmapShader(this.mBitmap, TileMode.CLAMP, TileMode.CLAMP);
            this.mBitmapPaint.setAntiAlias(true);
            this.mBitmapPaint.setShader(this.mBitmapShader);
            this.mBorderPaint.setStyle(Style.STROKE);
            this.mBorderPaint.setAntiAlias(true);
            this.mBorderPaint.setColor(this.mBorderColor);
            this.mBorderPaint.setStrokeWidth((float) this.mBorderWidth);
            this.mSelectedPaint.setStyle(Style.FILL);
            this.mSelectedPaint.setAntiAlias(true);
            this.mSelectedPaint.setColor(this.mBorderColor);
            this.mBitmapHeight = this.mBitmap.getHeight();
            this.mBitmapWidth = this.mBitmap.getWidth();
            this.mBorderRect.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
            this.mBorderRadius = Math.min((this.mBorderRect.height() - ((float) this.mBorderWidth)) / 2.0f, (this.mBorderRect.width() - ((float) this.mBorderWidth)) / 2.0f);
            this.mDrawableRect.set((float) this.mBorderWidth, (float) this.mBorderWidth, this.mBorderRect.width() - ((float) this.mBorderWidth), this.mBorderRect.height() - ((float) this.mBorderWidth));
            this.mDrawableRadius = Math.min(this.mDrawableRect.height() / 2.0f, this.mDrawableRect.width() / 2.0f);
            updateShaderMatrix();
            invalidate();
        }
    }

    private void updateShaderMatrix() {
        float scale;
        float dx = 0.0f;
        float dy = 0.0f;
        this.mShaderMatrix.set(null);
        if (((float) this.mBitmapWidth) * this.mDrawableRect.height() > this.mDrawableRect.width() * ((float) this.mBitmapHeight)) {
            scale = this.mDrawableRect.height() / ((float) this.mBitmapHeight);
            dx = (this.mDrawableRect.width() - (((float) this.mBitmapWidth) * scale)) * 0.5f;
        } else {
            scale = this.mDrawableRect.width() / ((float) this.mBitmapWidth);
            dy = (this.mDrawableRect.height() - (((float) this.mBitmapHeight) * scale)) * 0.5f;
        }
        this.mShaderMatrix.setScale(scale, scale);
        this.mShaderMatrix.postTranslate((float) (((int) (dx + 0.5f)) + this.mBorderWidth), (float) (((int) (dy + 0.5f)) + this.mBorderWidth));
        this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
    }

    public boolean isHasSelected() {
        return this.isHasSelected;
    }

    public void setHasSelected(boolean isHasSelected) {
        this.isHasSelected = isHasSelected;
    }
}
