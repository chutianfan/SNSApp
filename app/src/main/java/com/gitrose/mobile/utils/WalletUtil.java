package com.gitrose.mobile.utils;

import java.text.DecimalFormat;

public class WalletUtil {
    public static String getMoney2DecimalPoint(float account) {
        return new DecimalFormat("0.00").format((double) (account / 100.0f));
    }
}
