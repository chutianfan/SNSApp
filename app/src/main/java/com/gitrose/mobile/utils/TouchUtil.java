package com.gitrose.mobile.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.TouchDelegate;
import android.view.View;
import com.android.volley.DefaultRetryPolicy;

public class TouchUtil {
    private static final int MINSIZE = 72;

    /* renamed from: com.gitrose.mobile.utils.TouchUtil.1 */
    static class C03591 implements Runnable {
        private final /* synthetic */ Activity val$activity;
        private final /* synthetic */ View val$view;

        C03591(View view, Activity activity) {
            this.val$view = view;
            this.val$activity = activity;
        }

        public void run() {
            Rect bounds = new Rect();
            this.val$view.getHitRect(bounds);
            DisplayMetrics dm = new DisplayMetrics();
            this.val$activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int minsize = TouchUtil.MINSIZE;
            if (dm.density > DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) {
                minsize = (int) (((float) TouchUtil.MINSIZE) * dm.density);
            }
            boolean changed = false;
            if (bounds.width() < minsize) {
                int cx = bounds.centerX();
                bounds.left = cx - (minsize / 2);
                bounds.right = (minsize / 2) + cx;
                changed = true;
            }
            if (bounds.height() < minsize) {
                int cy = bounds.centerY();
                bounds.top = cy - (minsize / 2);
                bounds.bottom = (minsize / 2) + cy;
                changed = true;
            }
            if (changed) {
                TouchDelegate touchDelegate = new TouchDelegate(bounds, this.val$view);
                if (View.class.isInstance(this.val$view.getParent())) {
                    ((View) this.val$view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        }
    }

    public static void setDelegate(Activity activity, View view) {
        view.post(new C03591(view, activity));
    }
}
