package shouji.gexing.framework.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

//import com.tencent.qalsdk.im_open.stat_reg.ReqBody;
//import com.tencent.qalsdk.im_open.stat_reg.RspBody;
//import com.gitrose.mobile.HomeFragment;
//import com.gitrose.mobile.PersonalNewActivity;
//import com.gitrose.mobile.PersonalWorksListActiviy;

import io.rong.imlib.statistics.UserData;

public class NetUtils {
    public static final int NETWORK_TYPE_2G = 1;
    public static final int NETWORK_TYPE_3G = 2;
    public static final int NETWORK_TYPE_4G = 4;
    public static final int NETWORK_TYPE_MOBILE = 3;
    public static final int NETWORK_TYPE_NONE = 0;
    public static final int NETWORK_TYPE_OTHER = 5;
    public static final int NETWORK_TYPE_WIFI = 4;

    public static boolean checkNet(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected() && info.getState() == State.CONNECTED) {
                    return true;
                }
            }
        } catch (Exception e) {
//            DebugUtils.exception(e);
        }
        return false;
    }

    public static boolean getWifi(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(NETWORK_TYPE_2G);
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public static String getNetWorkType(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null) {
            return "networkInfo is null";
        }
        if (networkInfo.getType() == 0) {
            switch (((TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getNetworkType()) {
                case NETWORK_TYPE_2G /*1*/:
                case NETWORK_TYPE_3G /*2*/:
                case NETWORK_TYPE_WIFI /*4*/:
//                case PersonalNewActivity.COLLECT_LIST_DETAIL /*7*/:
//                case ReqBody.STR_DEV_TYPE_FIELD_NUMBER /*11*/:
                    return "2G";
                case NETWORK_TYPE_MOBILE /*3*/:
                case NETWORK_TYPE_OTHER /*5*/:
//                case PersonalNewActivity.COLLECT_LIST_9 /*6*/:
//                case RspBody.UINT32_HELLO_INTERVAL_FIELD_NUMBER /*8*/:
//                case HomeFragment.ADD_COMMENT_RESULT /*9*/:
//                case PersonalWorksListActiviy.ADD_TOPIC_RESULT /*10*/:
//                case ReqBody.STR_OS_VER_FIELD_NUMBER /*12*/:
//                case ReqBody.STR_FIRMWARE_VER_FIELD_NUMBER /*14*/:
//                case PersonalWorksListActiviy.FAV_RESULT /*15*/:
                    return "3G";
//                case ReqBody.STR_BUILD_VER_FIELD_NUMBER /*13*/:
//                    return "4G";
                default:
                    return "\u624b\u673a\u7f51\u7edc";
            }
        } else if (NETWORK_TYPE_2G == networkInfo.getType()) {
            return "wifi";
        } else {
            return "\u5176\u4ed6\u7f51\u7edc";
        }
    }
}
