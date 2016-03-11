package shouji.gexing.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class UiUtils {
    private static DisplayMetrics mDisplayMetrics;
    private static UiUtils utils;

    static {
        utils = null;
    }

    private UiUtils() {
    }

    public static UiUtils getInstance(Context mContext) {
        if (utils == null) {
            utils = new UiUtils();
        }
        if (mContext != null) {
            mDisplayMetrics = new DisplayMetrics();
            ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(mDisplayMetrics);
        }
        return utils;
    }

    public float getDensity() {
        return mDisplayMetrics.density;
    }

    public int getmScreenWidth() {
        return mDisplayMetrics.widthPixels;
    }

    public int getmScreenHeight() {
        return mDisplayMetrics.heightPixels;
    }

    public int DipToPixels(float dpValue) {
        return (int) ((dpValue * getDensity()) + 0.5f);
    }

    public int PixelsToDip(float pxValue) {
        return (int) ((pxValue / getDensity()) + 0.5f);
    }

    public static int px2sp(float pxValue, Context context) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int sp2px(float spValue, Context context) {
        return (int) ((spValue * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static double getInches(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dens = dm.densityDpi;
        return Math.sqrt(Math.pow(((double) width) / ((double) dens), 2.0d) + Math.pow(((double) height) / ((double) dens), 2.0d));
    }

    public static double getScreen(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dens = dm.densityDpi;
        return Math.sqrt(Math.pow(((double) width) / ((double) dens), 2.0d) + Math.pow(((double) height) / ((double) dens), 2.0d));
    }
}
