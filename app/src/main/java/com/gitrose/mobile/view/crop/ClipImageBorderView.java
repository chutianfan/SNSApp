package com.gitrose.mobile.view.crop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class ClipImageBorderView extends View {
    private int mBorderColor;
    private int mBorderWidth;
    private int mHorizontalPadding;
    private Paint mPaint;
    private int mVerticalPadding;
    private int mWidth;

    public ClipImageBorderView(Context context) {
        this(context, null);
    }

    public ClipImageBorderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipImageBorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mBorderColor = Color.parseColor("#53CBAB");
        this.mBorderWidth = 1;
        this.mBorderWidth = (int) TypedValue.applyDimension(1, (float) this.mBorderWidth, getResources().getDisplayMetrics());
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mWidth = getWidth() - (this.mHorizontalPadding * 2);
        this.mVerticalPadding = (getHeight() - this.mWidth) / 2;
        this.mPaint.setColor(Color.parseColor("#40FFFFFF"));
        this.mPaint.setStyle(Style.FILL);
        canvas.drawRect(0.0f, 0.0f, (float) this.mHorizontalPadding, (float) getHeight(), this.mPaint);
        canvas.drawRect((float) (getWidth() - this.mHorizontalPadding), 0.0f, (float) getWidth(), (float) getHeight(), this.mPaint);
        canvas.drawRect((float) this.mHorizontalPadding, 0.0f, (float) (getWidth() - this.mHorizontalPadding), (float) this.mVerticalPadding, this.mPaint);
        canvas.drawRect((float) this.mHorizontalPadding, (float) (getHeight() - this.mVerticalPadding), (float) (getWidth() - this.mHorizontalPadding), (float) getHeight(), this.mPaint);
        this.mPaint.setColor(this.mBorderColor);
        this.mPaint.setStrokeWidth((float) this.mBorderWidth);
        this.mPaint.setStyle(Style.STROKE);
        canvas.drawRect((float) this.mHorizontalPadding, (float) this.mVerticalPadding, (float) (getWidth() - this.mHorizontalPadding), (float) (getHeight() - this.mVerticalPadding), this.mPaint);
    }

    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }
}
