package com.gitrose.mobile.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.application.ActivityManager;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.UserInfo;
import java.util.Date;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.json.JSONException;

public class UserAuth {
    private static UserAuth userAuth;

    private UserAuth() {
    }

    public static UserAuth getInstance() {
        if (userAuth == null) {
            synchronized (UserAuth.class) {
                if (userAuth == null) {
                    userAuth = new UserAuth();
                }
            }
        }
        return userAuth;
    }

    public boolean authUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constant.PREFENCES_NAME, 0);
        String uid = sp.getString(Constant.UID, null);
        if (uid == null) {
            return false;
        }
        TutuUsers info = new TutuUsers();
        String avatarTime = sp.getString(Constant.AVATAR_TIME_PERFERENCE, null);
        String nickName = sp.getString(Constant.NICK_NAME_PERFERENCE, null);
        String locationStatus = sp.getString(Constant.LOCATION_STATUS, null);
        String logintype = sp.getString(Constant.LOGIN_TYPE, null);
        String isbind_qq = sp.getString(Constant.IS_BIND_QQ, null);
        String isbind_weibo = sp.getString(Constant.IS_BIND_WEIBO, null);
        String isbind_phone = sp.getString(Constant.IS_BIND_PHONE, null);
        String rc_token = sp.getString(Constant.RC_TOLEN, null);
        info.setUid(uid);
        info.setAvatartime(avatarTime);
        info.setNickname(nickName);
        info.setLogintype(logintype);
        info.setLocationstatus(locationStatus);
        info.setIsbind_qq(Integer.parseInt(isbind_qq));
        info.setIsbind_weibo(Integer.parseInt(isbind_weibo));
        info.setIsbind_phone(Integer.parseInt(isbind_phone));
        info.setRctoken(rc_token);
        return checkAuthTokenValid(context, info, sp.getString(Constant.TOKEN, null));
    }

    public void login(Context context, TutuUsers userInfo) {
        String authToken = CookieParser.parseCookies2Json(MyApplication.getInstance().mHttpClient.getCookieStore());
        Editor edit = context.getSharedPreferences(Constant.PREFENCES_NAME, 0).edit();
        edit.putString(Constant.UID, userInfo.getUid());
        edit.putString(Constant.TOKEN, authToken);
        edit.putString(Constant.AVATAR_TIME_PERFERENCE, userInfo.getAvatartime());
        edit.putString(Constant.NICK_NAME_PERFERENCE, userInfo.getNickname());
        edit.putString(Constant.LOCATION_STATUS, userInfo.getLocationstatus());
        edit.putString(Constant.LOGIN_TYPE, userInfo.getLogintype());
        edit.putString(Constant.IS_BIND_QQ, new StringBuilder(String.valueOf(userInfo.getIsbind_qq())).toString());
        edit.putString(Constant.IS_BIND_WEIBO, new StringBuilder(String.valueOf(userInfo.getIsbind_weibo())).toString());
        edit.putString(Constant.IS_BIND_PHONE, new StringBuilder(String.valueOf(userInfo.getIsbind_phone())).toString());
        edit.putString(Constant.IS_BIND_PHONE, new StringBuilder(String.valueOf(userInfo.getIsbind_phone())).toString());
        edit.putString(Constant.RC_TOLEN, userInfo.getRctoken());
        edit.commit();
        loadUserInfo(userInfo, null);
    }

    public void invinvalidateUserIdentity(Context context) {
        Editor edit = context.getSharedPreferences(Constant.PREFENCES_NAME, 0).edit();
        edit.remove(Constant.UID);
        edit.remove(Constant.TOKEN);
        edit.commit();
        MyApplication.getInstance().removeClientCookieFromHttpClient();
        MyApplication.getInstance().setIsLogin(false);
        MyApplication.getInstance().isPassAuth = false;
        MyApplication.getInstance().setUserinfo(null);
        ActivityManager.getScreenManager().popAllActivity();
    }

    private boolean checkAuthTokenValid(Context context, TutuUsers userinfo, String authToken) {
        try {
            CookieStore store = CookieParser.pareseJson2Cookies(authToken);
            if (store == null) {
                return false;
            }
            for (Cookie cookie : store.getCookies()) {
                if ("al".equals(cookie.getName())) {
                    if (cookie.isExpired(new Date())) {
                        return false;
                    }
                    loadUserInfo(userinfo, store);
                    return true;
                }
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadUserInfo(TutuUsers userInfo, CookieStore cookieStore) {
        MyApplication.getInstance().setUserinfo(userInfo);
        if (cookieStore == null) {
            MyApplication.getInstance().setClientCookieFromHttpClient();
        } else {
            MyApplication.getInstance().setCookieFromToken(cookieStore);
        }
        MyApplication.getInstance().setIsLogin(true);
    }

    private void restartApplication(Context context) {
        if (context != null) {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            intent.addFlags(67108864);
            context.startActivity(intent);
        }
    }
}
