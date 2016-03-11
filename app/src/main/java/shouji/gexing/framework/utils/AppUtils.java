package shouji.gexing.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.support.v4.media.TransportMediator;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

//import com.sina.weibo.sdk.codestyle.SampleCode;
import com.tencent.qalsdk.sdk.MsfConstants;
import com.umeng.socialize.common.SocializeConstants;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map.Entry;

import io.rong.imlib.statistics.UserData;
import qalsdk.BaseConstants;

public class AppUtils {
    public static int getApiLevel() {
        return Integer.valueOf(VERSION.SDK).intValue();
    }

    public static void exitApp(Context context) {
//        Intent intent = new Intent(SampleCode.ACTION_MAIN);
//        intent.addCategory("android.intent.category.HOME");
//        intent.setFlags(1073741824);
//        context.startActivity(intent);
    }

    public static void openKeyboard(Activity activity) {
//        ((InputMethodManager) activity.getSystemService("input_method")).toggleSoftInput(0, 2);
    }

    public static void hiddenKeyboard(Activity activity) {
//        try {
//            ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
    }

    public static String getUAStr(Context context, String appname) {
        String version;
        PackageManager manager = null;
        if (context != null) {
            manager = context.getPackageManager();
        }

        String UAStr = BaseConstants.ah;LinkedHashMap<String, String> map = getUAMap(context);
        if (manager != null) {
            try {
                version = manager.getPackageInfo(context.getPackageName(), 0).versionName;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
                return UAStr;
            } catch (NullPointerException e2) {
                e2.printStackTrace();
                return UAStr;
            }
        }
        version = null;
        UAStr = new StringBuilder(String.valueOf(UAStr)).append(appname).append("/").append(version).append("/adr (").toString();
        for (Entry<String, String> entry : map.entrySet()) {
            UAStr = new StringBuilder(String.valueOf(UAStr)).append((String) entry.getValue()).append(",").toString();
        }
        return UAStr.substring(0, UAStr.length() - 1) + SocializeConstants.OP_CLOSE_PAREN;
    }

    public static LinkedHashMap<String, String> getUAMap(Context context) {
        LinkedHashMap<String, String> map = new LinkedHashMap();
        if (context == null) {
            map.put("MOBILE_TYPE", null);
            map.put("IMEI", null);
            map.put("IMSI", null);
            map.put("SDK_VERSION", null);
            map.put("DM", null + MsfConstants.f2891j + null);
            map.put("INCHES", null);
            map.put("DENSITY", null);
            map.put("NET_TYPE", null);
            map.put("CHANNAL", null);
            map.put("LANGUAGE", null);
        } else {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
            map.put("MOBILE_TYPE", Build.MODEL);
//            String deviceid = manager.getDeviceId();
            String deviceid = "";
            if (deviceid == null || deviceid.trim().equals(BaseConstants.ah)) {
                deviceid = Secure.getString(context.getContentResolver(), "android_id");
            }
            map.put("IMEI", deviceid);
//            map.put("IMSI", manager.getSubscriberId());
            map.put("IMSI", "");
            map.put("SDK_VERSION", VERSION.RELEASE);
            map.put("DM", dm.widthPixels + MsfConstants.f2891j + dm.heightPixels);
            map.put("INCHES", new StringBuilder(String.valueOf(getScreenInches(context))).toString());
            map.put("DENSITY", new StringBuilder(String.valueOf(dm.densityDpi)).toString());
            map.put("NET_TYPE", NetUtils.getNetWorkType(context));
            map.put("CHANNAL", getMetaDate(context, "UMENG_CHANNEL"));
            map.put("LANGUAGE", Locale.getDefault().getLanguage());
        }
        return map;
    }

    public static String getMetaDate(Context context, String data_name) {
//        String value = BaseConstants.ah;
//        try {
//            value = context.getPackageManager().getApplicationInfo(context.getPackageName(), TransportMediator.FLAG_KEY_MEDIA_NEXT).metaData.getString(data_name);
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return value;

        return "";
    }

    public static double getScreenInches(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dens = dm.densityDpi;
        return Math.sqrt(Math.pow(((double) width) / ((double) dens), 2.0d) + Math.pow(((double) height) / ((double) dens), 2.0d));
    }
}
