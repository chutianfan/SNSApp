package com.gitrose.mobile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class MyRelativeLayout extends RelativeLayout {
    int count;

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.count = 0;
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.count = 0;
    }

    public MyRelativeLayout(Context context) {
        super(context);
        this.count = 0;
    }
}
