package com.gitrose.mobile.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.gitrose.mobile.R;
//import com.gitrose.mobile.TopicDetailPagerActivity;

public class SideBar extends View {
    public static String[] f1181b;
    private int choose;
    private TextView mTextDialog;
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private Paint paint;

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String str);
    }

    static {
        f1181b = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    }

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.choose = -1;
        this.paint = new Paint();
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.choose = -1;
        this.paint = new Paint();
    }

    public SideBar(Context context) {
        super(context);
        this.choose = -1;
        this.paint = new Paint();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / f1181b.length;
        for (int i = 0; i < f1181b.length; i++) {
            this.paint.setColor(Color.rgb(33, 65, 98));
            this.paint.setTypeface(Typeface.DEFAULT_BOLD);
            this.paint.setAntiAlias(true);
            this.paint.setTextSize(20.0f);
            if (i == this.choose) {
                this.paint.setColor(Color.parseColor("#3399ff"));
                this.paint.setFakeBoldText(true);
            }
            canvas.drawText(f1181b[i], ((float) (width / 2)) - (this.paint.measureText(f1181b[i]) / 2.0f), (float) ((singleHeight * i) + singleHeight), this.paint);
            this.paint.reset();
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int oldChoose = this.choose;
        OnTouchingLetterChangedListener listener = this.onTouchingLetterChangedListener;
        int c = (int) ((y / ((float) getHeight())) * ((float) f1181b.length));
        switch (action) {
//            case TopicDetailPagerActivity.FLOW_FIRST_WHAT /*1*/:
//                setBackgroundDrawable(new ColorDrawable(0));
//                this.choose = -1;
//                invalidate();
//                if (this.mTextDialog != null) {
//                    this.mTextDialog.setVisibility(View.INVISIBLE);
//                    break;
//                }
//                break;
            default:
                setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != c && c >= 0 && c < f1181b.length) {
                    if (listener != null) {
                        listener.onTouchingLetterChanged(f1181b[c]);
                    }
                    if (this.mTextDialog != null) {
                        this.mTextDialog.setText(f1181b[c]);
                        this.mTextDialog.setVisibility(View.VISIBLE);
                    }
                    this.choose = c;
                    invalidate();
                    break;
                }
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }
}
