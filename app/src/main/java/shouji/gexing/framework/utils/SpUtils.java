package shouji.gexing.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;

public class SpUtils {
    public static final String KEY_NAME = "pref_app";

    public static <T> boolean saveToLocal(Context mContext, String key, T t) {
        return saveToLocal(mContext, KEY_NAME, key, t);
    }

    public static <T> boolean saveToLocal(Context mContext, String name, String key, T t) {
        SharedPreferences sp;
        boolean ret = false;
        if (name == null) {
            sp = getDefaultSharedPreferences(mContext);
        } else {
            sp = mContext.getSharedPreferences(name, 3);
        }
        if (t instanceof Boolean) {
            ret = sp.edit().putBoolean(key, ((Boolean) t).booleanValue()).commit();
        }
        if (t instanceof String) {
            ret = sp.edit().putString(key, (String) t).commit();
        }
        if (t instanceof Integer) {
            ret = sp.edit().putInt(key, ((Integer) t).intValue()).commit();
        }
        if (t instanceof Float) {
            ret = sp.edit().putFloat(key, ((Float) t).floatValue()).commit();
        }
        if (t instanceof Long) {
            return sp.edit().putLong(key, ((Long) t).longValue()).commit();
        }
        return ret;
    }

    public static Object getFromLocal(Context mContext, String key, Object defaultValue) {
        return getFromLocal(mContext, KEY_NAME, key, defaultValue);
    }

    public static Object getFromLocal(Context mContext, String name, String key, Object defaultValue) {
        SharedPreferences sp;
        if (name == null) {
            sp = getDefaultSharedPreferences(mContext);
        } else {
            sp = mContext.getSharedPreferences(name, 3);
        }
        Map<String, ?> map = sp.getAll();
        return (map == null || map.get(key) == null) ? defaultValue : map.get(key);
    }

    public static boolean deleteLocalValue(Context context, String name, String key) {
        SharedPreferences sp;
        if (name == null) {
            sp = getDefaultSharedPreferences(context);
        } else {
            sp = context.getSharedPreferences(name, 3);
        }
        return sp.edit().remove(key).commit();
    }

    public static boolean clearSp(String name, Context mContext) {
        SharedPreferences sp;
        if (name == null) {
            sp = getDefaultSharedPreferences(mContext);
        } else {
            sp = mContext.getSharedPreferences(name, 3);
        }
        return sp.edit().clear().commit();
    }

    private static SharedPreferences getDefaultSharedPreferences(Context mContext) {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }
}
