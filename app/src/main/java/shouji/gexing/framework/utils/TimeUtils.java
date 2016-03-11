package shouji.gexing.framework.utils;

import android.content.Context;
//import com.tencent.connect.common.Constants;
//import com.tencent.qalsdk.im_open.http;
//import com.gitrose.mobile.ThemeActivity;
import com.gitrose.mobile.R;
import com.umeng.socialize.common.SocializeConstants;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import qalsdk.BaseConstants;

/**
 * Created by GITRose on 12/23/2015.
 */
public class TimeUtils {

    public static long ONEDAYMILLIS;

    static {
        ONEDAYMILLIS = 86400000;
    }

    public static Date getNow() {
        return new Date();
    }

    public static String getShowTimeStringefore(Context context, Date time, String format) {
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        long timeDistance = currentTimeMillis - time.getTime();
        long todayStartMillis = currentTimeMillis - ((long) ((((now.getHours() * 3600) + (now.getMinutes() * 60)) + now.getSeconds()) * BaseConstants.f3843b));
        if (timeDistance < 3600000) {
            if (timeDistance / 60000 == 0) {
                return context.getString(R.string.a_moment_ago);
            }
            return (timeDistance / 60000) + context.getString(R.string.minutes_ago);
        } else if (timeDistance < 86400000) {
            return (timeDistance / 3600000) + context.getString(R.string.hours_before);
        } else {
            if (time.getTime() > todayStartMillis - ONEDAYMILLIS) {
                return "1 " + context.getString(R.string.days_ago);
            }
            if (time.getTime() > todayStartMillis - (2 * ONEDAYMILLIS)) {
                return "2 " + context.getString(R.string.days_ago);
            }
            if (time.getTime() > todayStartMillis - (3 * ONEDAYMILLIS)) {
                return "3 " + context.getString(R.string.days_ago);
            }
            if (time.getTime() > todayStartMillis - (4 * ONEDAYMILLIS)) {
                return "4 " + context.getString(R.string.days_ago);
            }
            if (time.getTime() > todayStartMillis - (5 * ONEDAYMILLIS)) {
                return "5 " + context.getString(R.string.days_ago);
            }
            if (time.getTime() > todayStartMillis - (6 * ONEDAYMILLIS)) {
                return "6 " + context.getString(R.string.days_ago);
            }
            if (time.getTime() > todayStartMillis - (7 * ONEDAYMILLIS)) {
                return "7 " + context.getString(R.string.days_ago);
            }
            return getSimpleDateFormat(format, time);
        }
    }

    public static String getSimpleDateFormat(String pattern, Date time) {
        return new SimpleDateFormat(pattern).format(time);
    }

    public static Date strToDateLong(String strDate) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate, new ParsePosition(0));
    }

    public static String getShowTimeString(Context context, Date time) {
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        long timeDistance = currentTimeMillis - time.getTime();
        long todayStartMillis = currentTimeMillis - ((long) ((((now.getHours() * 3600) + (now.getMinutes() * 60)) + now.getSeconds()) * BaseConstants.f3843b));
        long yesterdayStartMillis = todayStartMillis - ONEDAYMILLIS;
        if (timeDistance < 3600000) {
            if (timeDistance / 60000 == 0) {
                return context.getString(R.string.a_moment_ago);
            }
            return (timeDistance / 60000) + context.getResources().getString(R.string.minutes_ago);
        } else if (timeDistance < 10800000) {
            return (timeDistance / 3600000) + context.getResources().getString(R.string.hours_before);
        } else {
            if (time.getTime() > todayStartMillis) {
                return context.getString(R.string.taday) + getSimpleDateFormat("HH:mm", time);
            }
            if (time.getTime() > yesterdayStartMillis) {
                return context.getString(R.string.yesterday) + getSimpleDateFormat("HH:mm", time);
            }
            return getSimpleDateFormat("yyyy-MM-dd HH:mm", time);
        }
    }

    public static long FromatTimeToMinnisecond(String str1) {
        long l1 = 0;
        try {
            l1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str1).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l1;
    }
}
