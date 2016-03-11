package com.gitrose.mobile.view.crop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.v4.media.TransportMediator;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.android.volley.DefaultRetryPolicy;
//import com.sea_monster.core.network.ApiReqeust;
//import com.tencent.android.tpush.XGPushClickedResult;
import java.io.IOException;

public class ClipImageLayout extends RelativeLayout {
    private static ClipImageLayout mClipImageLl;
    private float divder_scale;
    private boolean first_to_small;
    boolean is_to_small;
    public ClipImageBorderView mClipImageView;
    private int mHorizontalPadding;
    private int mVerticalPadding;
    public ClipZoomImageView mZoomImageView;
    private Context mcontext;

    public ClipImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mHorizontalPadding = 0;
        this.is_to_small = false;
        this.first_to_small = true;
        mClipImageLl = this;
        this.mcontext = context;
        this.mZoomImageView = new ClipZoomImageView(context);
        this.mClipImageView = new ClipImageBorderView(context);
        LayoutParams lp = new RelativeLayout.LayoutParams(-1, -1);
        addView(this.mZoomImageView, lp);
        addView(this.mClipImageView, lp);
        this.mHorizontalPadding = (int) TypedValue.applyDimension(1, (float) this.mHorizontalPadding, getResources().getDisplayMetrics());
        this.mZoomImageView.setHorizontalPadding(this.mHorizontalPadding);
        this.mClipImageView.setHorizontalPadding(this.mHorizontalPadding);
    }

    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }

    public Bitmap clip() {
        return this.mZoomImageView.clip(this.is_to_small);
    }

    public void setImageUri(Uri uri) {
        this.mZoomImageView.setImageURI(uri);
    }

    public void setImageBitmap(Bitmap bm, String filepath) {
        if (filepath == null) {
            try {
                this.mZoomImageView.setImageBitmap(bm);
                return;
            } catch (Exception e) {
                return;
            }
        }
        this.mZoomImageView.setImageBitmap(rotate(bm, readPictureDegree(filepath)));
    }

    public static int readPictureDegree(String path) {
        try {
            switch (new ExifInterface(path).getAttributeInt("Orientation", 1)) {
//                case ApiReqeust.PUT_METHOD /*3*/:
//                    return 180;
//                case XGPushClickedResult.NOTIFACTION_DOWNLOAD_CANCEL_TYPE /*6*/:
//                    return 90;
                case TransportMediator.FLAG_KEY_MEDIA_PLAY_PAUSE /*8*/:
                    return 270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Bitmap rotate(Bitmap b, int degrees) {
        if (degrees == 0 || degrees == 0 || b == null) {
            return b;
        }
        Matrix m = new Matrix();
        m.setRotate((float) degrees, (float) b.getWidth(), (float) b.getHeight());
        try {
            Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
            if (b == b2) {
                return b;
            }
            b.recycle();
            return b2;
        } catch (OutOfMemoryError e) {
            return b;
        }
    }

    public static ClipImageLayout getObject() {
        return mClipImageLl;
    }

    public void toFullSmall(boolean toSmall) {
        this.is_to_small = toSmall;
        int screen_width = getWidth();
        int screen_height = getHeight();
        float current_scale = this.mZoomImageView.getScale();
        if (toSmall) {
            initDivderScale();
            float top = this.mZoomImageView.getMatrixRectF().top;
            float left = this.mZoomImageView.getMatrixRectF().left;
            float rect_width = this.mZoomImageView.getMatrixRectF().width();
            this.mZoomImageView.mScaleMatrix.postTranslate(((((float) screen_width) - rect_width) / 2.0f) - left, ((((float) screen_height) - this.mZoomImageView.getMatrixRectF().height()) / 2.0f) - top);
            this.mZoomImageView.mScaleMatrix.postScale(getscale() / current_scale, getscale() / current_scale, (float) (this.mZoomImageView.getWidth() / 2), (float) (this.mZoomImageView.getHeight() / 2));
            this.mZoomImageView.setImageMatrix(this.mZoomImageView.mScaleMatrix);
            this.mZoomImageView.canvasBorder();
            return;
        }
        this.mZoomImageView.mScaleMatrix.postScale(this.divder_scale / current_scale, this.divder_scale / current_scale, (float) (getWidth() / 2), (float) (getHeight() / 2));
        this.mZoomImageView.checkBorder();
        this.mZoomImageView.setImageMatrix(this.mZoomImageView.mScaleMatrix);
    }

    public void initDivderScale() {
        if (this.first_to_small) {
            this.divder_scale = this.mZoomImageView.getScale();
            this.first_to_small = false;
        }
    }

    public float getscale() {
        Drawable d = this.mZoomImageView.getDrawable();
        if (d == null) {
            return DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
        }
        int width = this.mZoomImageView.getWidth();
        int height = this.mZoomImageView.getHeight();
        this.mVerticalPadding = (this.mZoomImageView.getHeight() - (this.mZoomImageView.getWidth() - (this.mHorizontalPadding * 2))) / 2;
        int dw = d.getIntrinsicWidth();
        int dh = d.getIntrinsicHeight();
        float scale = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
        if (dw < this.mZoomImageView.getWidth() - (this.mHorizontalPadding * 2) && dh > this.mZoomImageView.getHeight() - (this.mVerticalPadding * 2)) {
            scale = ((((float) this.mZoomImageView.getHeight()) * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) - ((float) (this.mVerticalPadding * 2))) / ((float) dh);
        }
        if (dh < this.mZoomImageView.getHeight() - (this.mVerticalPadding * 2) && dw > this.mZoomImageView.getWidth() - (this.mHorizontalPadding * 2)) {
            scale = ((((float) this.mZoomImageView.getWidth()) * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) - ((float) (this.mHorizontalPadding * 2))) / ((float) dw);
        }
        if (dw < this.mZoomImageView.getWidth() - (this.mHorizontalPadding * 2) && dh < this.mZoomImageView.getHeight() - (this.mVerticalPadding * 2)) {
            scale = Math.min(((((float) this.mZoomImageView.getWidth()) * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) - ((float) (this.mHorizontalPadding * 2))) / ((float) dw), ((((float) this.mZoomImageView.getHeight()) * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) - ((float) (this.mVerticalPadding * 2))) / ((float) dh));
        }
        if (dw < this.mZoomImageView.getWidth() - (this.mHorizontalPadding * 2) || dh < this.mZoomImageView.getHeight() - (this.mVerticalPadding * 2)) {
            return scale;
        }
        return Math.min(((((float) this.mZoomImageView.getWidth()) * DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) - ((float) (this.mHorizontalPadding * 2))) / ((float) dw), ((DefaultRetryPolicy.DEFAULT_BACKOFF_MULT * ((float) this.mZoomImageView.getHeight())) - ((float) (this.mVerticalPadding * 2))) / ((float) dh));
    }
}
