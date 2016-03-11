package org.litepal.util;

import android.text.TextUtils;
import java.util.Collection;
import java.util.Locale;
//import org.litepal.exceptions.DataSupportException;
//import org.litepal.parser.LitePalAttr;
//import org.litepal.util.Const.LitePal;
import qalsdk.BaseConstants;
import shouji.gexing.framework.utils.StringUtils;

public class BaseUtility {
    private BaseUtility() {
    }

    public static String changeCase(String string) {
        if (string == null) {
            return null;
        }
//        String cases = LitePalAttr.getInstance().getCases();
//        if (Const.LitePal.CASES_KEEP.equals(cases)) {
//            return string;
//        }
//        if (Const.LitePal.CASES_UPPER.equals(cases)) {
//            return string.toUpperCase(Locale.getDefault());
//        }
        return string.toLowerCase(Locale.getDefault());
    }

    public static boolean containsIgnoreCases(Collection<String> collection, String string) {
        if (collection == null) {
            return false;
        }
        if (string == null) {
            return collection.contains(null);
        }
        for (String element : collection) {
            if (string.equalsIgnoreCase(element)) {
                return true;
            }
        }
        return false;
    }

    public static String capitalize(String string) {
        if (TextUtils.isEmpty(string)) {
            return string == null ? null : BaseConstants.ah;
        } else {
            return new StringBuilder(String.valueOf(string.substring(0, 1).toUpperCase(Locale.getDefault()))).append(string.substring(1)).toString();
        }
    }

    public static int count(String string, String mark) {
        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(mark)) {
            return 0;
        }
        int count = 0;
        int index = string.indexOf(mark);
        while (index != -1) {
            count++;
            string = string.substring(mark.length() + index);
            index = string.indexOf(mark);
        }
        return count;
    }

//    public static void checkConditionsCorrect(String... conditions) {
//        if (conditions != null) {
//            int conditionsSize = conditions.length;
//            if (conditionsSize > 0 && conditionsSize != count(conditions[0], "?") + 1) {
//                throw new DataSupportException(DataSupportException.UPDATE_CONDITIONS_EXCEPTION);
//            }
//        }
//    }

    public static boolean isFieldTypeSupported(String fieldType) {
        if (StringUtils.CLASSNAME_BOOLEAN.equals(fieldType) || "java.lang.Boolean".equals(fieldType) || StringUtils.CLASSNAME_FLOAT.equals(fieldType) || "java.lang.Float".equals(fieldType) || StringUtils.CLASSNAME_DOUBLE.equals(fieldType) || "java.lang.Double".equals(fieldType) || StringUtils.CLASSNAME_INT.equals(fieldType) || "java.lang.Integer".equals(fieldType) || StringUtils.CLASSNAME_LONG.equals(fieldType) || "java.lang.Long".equals(fieldType) || "short".equals(fieldType) || "java.lang.Short".equals(fieldType) || StringUtils.CLASSNAME_CHAR.equals(fieldType) || "java.lang.Character".equals(fieldType) || StringUtils.CLASSNAME_STRING.equals(fieldType) || "java.util.Date".equals(fieldType)) {
            return true;
        }
        return false;
    }
}
