package com.gitrose.mobile.auth;

//import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CookieParser {
    public static synchronized String parseCookies2Json(CookieStore cookieStore) {
        String stringBuffer;
        synchronized (CookieParser.class) {
            if (cookieStore != null) {
                StringBuffer sb = new StringBuffer();
                sb.append("[");
                CopyOnWriteArrayList<Cookie> cookies = new CopyOnWriteArrayList();
                cookies.addAll(cookieStore.getCookies());
                for (int i = 0; i < cookies.size(); i++) {
                    Cookie cookie = (Cookie) cookies.get(i);
                    sb.append("{");
                    appendIntegerJson(sb, "version", cookie.getVersion());
                    //appendStringJson(sb, SocializeProtocolConstants.PROTOCOL_KEY_FRIENDS_NAME, cookie.getName());
                    appendStringJson(sb, "value", cookie.getValue());
                    appendStringJson(sb, "path", cookie.getPath());
                    appendStringJson(sb, "domain", cookie.getDomain());
                    appendLongJson(sb, "expiry", cookie.getExpiryDate() == null ? 0 : cookie.getExpiryDate().getTime());
                    sb = sb.deleteCharAt(sb.length() - 1);
                    sb.append("}");
                    sb.append(",");
                }
                sb = sb.deleteCharAt(sb.length() - 1);
                sb.append("]");
                stringBuffer = sb.toString();
            } else {
                stringBuffer = null;
            }
        }
        return stringBuffer;
    }

    public static CookieStore pareseJson2Cookies(String cookieJson) throws JSONException {
        BasicCookieStore cookieStore = new BasicCookieStore();
        if (cookieJson == null) {
            return null;
        }
        JSONArray array = new JSONArray(cookieJson);
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            //String name = jsonObject.getString(SocializeProtocolConstants.PROTOCOL_KEY_FRIENDS_NAME);
            String value = jsonObject.getString("value");
            int version = jsonObject.getInt("version");
            String path = jsonObject.getString("path");
            String domain = jsonObject.getString("domain");
            long expiry = jsonObject.getLong("expiry");
//            BasicClientCookie cookie = new BasicClientCookie(name, value);
//            cookie.setExpiryDate(expiry == 0 ? null : new Date(expiry));
//            cookie.setDomain(domain);
//            cookie.setPath(path);
//            cookie.setVersion(version);
//            cookieStore.addCookie(cookie);
        }
        return cookieStore;
    }

    public static void appendStringJson(StringBuffer sb, String key, String value) {
        sb.append("\"").append(key).append("\":");
        sb.append("\"").append(value).append("\"");
        sb.append(",");
    }

    public static void appendIntegerJson(StringBuffer sb, String key, int value) {
        sb.append("\"").append(key).append("\":");
        sb.append(value);
        sb.append(",");
    }

    public static void appendLongJson(StringBuffer sb, String key, long value) {
        sb.append("\"").append(key).append("\":");
        sb.append(value);
        sb.append(",");
    }
}
