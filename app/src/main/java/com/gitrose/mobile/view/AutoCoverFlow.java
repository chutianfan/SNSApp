package com.gitrose.mobile.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

public class AutoCoverFlow extends Gallery {
    public AutoCoverFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        int i = VERSION.SDK_INT;
    }

    private boolean isScrollingLeft(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2) {
        if (paramMotionEvent2.getX() > paramMotionEvent1.getX()) {
            return true;
        }
        return false;
    }

    public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
        int keyCode;
        if (isScrollingLeft(paramMotionEvent1, paramMotionEvent2)) {
            keyCode = 21;
        } else {
            keyCode = 22;
        }
        onKeyDown(keyCode, null);
        return true;
    }

    public void next() {
        onKeyDown(22, null);
    }

    public void per() {
        onKeyDown(21, null);
    }
}
