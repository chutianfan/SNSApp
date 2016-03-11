package com.gitrose.mobile.view;

import android.content.Context;
import android.widget.Toast;

public class ToastView {
    public static int LENGTH_LONG;
    public static int LENGTH_SHORT;
    private static String oldMsg;
    private static long oneTime;
    protected static Toast toast;
    private static long twoTime;

    static {
        toast = null;
        oneTime = 0;
        twoTime = 0;
        LENGTH_SHORT = 0;
        LENGTH_LONG = 1;
    }

    public static void showToast(Context context, String s, int length) {
        if (toast == null) {
            toast = Toast.makeText(context, s, length);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (!s.equals(oldMsg)) {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            } else if (twoTime - oneTime > ((long) length)) {
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void showToast(Context context, int resId, int length) {
        showToast(context, context.getString(resId), length);
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
