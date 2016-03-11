package shouji.gexing.framework.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
    public static final int NETWORK_3G = 4;
    public static final int NETWORK_MOBILE = 0;
    public static final int NETWORK_MOBILE_CMNET = 2;
    public static final int NETWORK_MOBILE_CMWAP = 3;
    public static final int NETWORK_NONE = -1;
    public static final int NETWORK_WIFI = 1;

    public static int getAPNType(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            return NETWORK_NONE;
        }
        int nType = networkInfo.getType();
        if (nType == 0) {
            return NETWORK_MOBILE;
        }
        if (nType != NETWORK_WIFI) {
            return NETWORK_NONE;
        }
        return NETWORK_WIFI;
    }

    public static boolean isWifi(Context context) {
        if (getAPNType(context) == NETWORK_WIFI) {
            return true;
        }
        return false;
    }

    public static boolean isMobile(Context context) {
        if (getAPNType(context) == 0) {
            return true;
        }
        return false;
    }

    public static boolean hasNetWork(Context context) {
        return isMobile(context) || isWifi(context);
    }
}
