package shouji.gexing.framework.utils;

import java.util.regex.Pattern;

public class TelUtil {
    public static boolean isTel(String tel) {
        return Pattern.compile("^((13[0-9])|(14[0-9])|(15[0,0-9])|(18[0,0-9]))\\d{8}$").matcher(tel).matches();
    }
}
