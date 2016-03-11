package com.gitrose.mobile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
//import com.umeng.socialize.utils.Log;

public class KeyboardRelativeLayout extends RelativeLayout {
    public static final byte KEYBOARD_STATE_HIDE = (byte) -2;
    public static final byte KEYBOARD_STATE_INIT = (byte) -1;
    public static final byte KEYBOARD_STATE_SHOW = (byte) -3;
    private boolean mHasInit;
    private boolean mHasKeyboard;
    public int mHeight;
    public int mSoftInputHeight;
    private IOnKeyboardStateChangedListener onKeyboardStateChangedListener;

    public interface IOnKeyboardStateChangedListener {
        void onKeyboardStateChanged(int i);
    }

    public KeyboardRelativeLayout(Context context) {
        super(context);
        this.mHasInit = false;
        this.mHasKeyboard = false;
        this.mSoftInputHeight = 0;
    }

    public KeyboardRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mHasInit = false;
        this.mHasKeyboard = false;
        this.mSoftInputHeight = 0;
    }

    public void setOnKeyboardStateChangedListener(IOnKeyboardStateChangedListener onKeyboardStateChangedListener) {
        this.onKeyboardStateChangedListener = onKeyboardStateChangedListener;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.mHasInit) {
            this.mHeight = this.mHeight < b ? b : this.mHeight;
        } else {
            this.mHasInit = true;
            this.mHeight = b;
            if (this.onKeyboardStateChangedListener != null) {
                this.onKeyboardStateChangedListener.onKeyboardStateChanged(-1);
            }
        }
        if (this.mHasInit && this.mHeight > b) {
            this.mHasKeyboard = true;
            if (this.onKeyboardStateChangedListener != null) {
                this.onKeyboardStateChangedListener.onKeyboardStateChanged(-3);
            }
        }
        if (this.mHasInit && this.mHasKeyboard && this.mHeight == b) {
            this.mHasKeyboard = false;
            if (this.onKeyboardStateChangedListener != null) {
                this.onKeyboardStateChangedListener.onKeyboardStateChanged(-2);
            }
        }
    }

    public int getSoftInputHeight() {
        return this.mHeight;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int height = oldh - h;
        if (height > 0) {
            this.mSoftInputHeight = height;
        }
        //Log.m1631i("yesongsong", "...mSoftInputHeight...:" + this.mSoftInputHeight);
    }
}
