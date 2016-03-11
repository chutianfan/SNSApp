package com.gitrose.mobile.utils;

/**
 * Created by GITRose on 1/2/2016.
 */
public class EmailUtil {

    public static Boolean isEmail(String strEmail)
    {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()){
            return false;
        }

        return true;
    }

}
