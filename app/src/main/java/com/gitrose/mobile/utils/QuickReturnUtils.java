package com.gitrose.mobile.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;
import java.util.Dictionary;
import java.util.Hashtable;

public class QuickReturnUtils {
    private static long lastClickTime;
    private static int sActionBarHeight;
    private static Dictionary<Integer, Integer> sListViewItemHeights;
    private static TypedValue sTypedValue;

    static {
        sTypedValue = new TypedValue();
        sListViewItemHeights = new Hashtable();
    }

    public static int dp2px(Context context, int dp) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        display.getMetrics(displaymetrics);
        return (int) ((((float) dp) * displaymetrics.density) + 0.5f);
    }

    public static int px2dp(Context context, int px) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        display.getMetrics(displaymetrics);
        return (int) ((((float) px) / displaymetrics.density) + 0.5f);
    }

    public static int getScrollY(ListView lv) {
        View c = lv.getChildAt(0);
        if (c == null) {
            return 0;
        }
        return (-c.getTop()) + (c.getHeight() * lv.getFirstVisiblePosition());
    }

    public static int getScrollY(AbsListView lv) {
        int i = 0;
        View c = lv.getChildAt(0);
        if (c != null) {
            int firstVisiblePosition = lv.getFirstVisiblePosition();
            i = -c.getTop();
            sListViewItemHeights.put(Integer.valueOf(lv.getFirstVisiblePosition()), Integer.valueOf(c.getHeight()));
            if (i < 0) {
                i = 0;
            }
            for (int i2 = 0; i2 < firstVisiblePosition; i2++) {
                if (sListViewItemHeights.get(Integer.valueOf(i2)) != null) {
                    i += ((Integer) sListViewItemHeights.get(Integer.valueOf(i2))).intValue();
                }
            }
        }
        return i;
    }

    public static int getActionBarHeight(Context context) {
        if (sActionBarHeight != 0) {
            return sActionBarHeight;
        }
        context.getTheme().resolveAttribute(16843499, sTypedValue, true);
        sActionBarHeight = TypedValue.complexToDimensionPixelSize(sTypedValue.data, context.getResources().getDisplayMetrics());
        return sActionBarHeight;
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
