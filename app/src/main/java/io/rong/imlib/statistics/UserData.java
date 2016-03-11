package io.rong.imlib.statistics;

import android.util.Log;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import qalsdk.BaseConstants;

public class UserData {
    public static final String BYEAR_KEY = "byear";
    public static final String CUSTOM_KEY = "custom";
    public static final String EMAIL_KEY = "email";
    public static final String GENDER_KEY = "gender";
    public static final String NAME_KEY = "name";
    public static final String ORG_KEY = "organization";
    public static final String PHONE_KEY = "phone";
    public static final String PICTURE_KEY = "picture";
    public static final String PICTURE_PATH_KEY = "picturePath";
    public static final String USERNAME_KEY = "username";
    public static int byear;
    public static Map<String, String> custom;
    public static String email;
    public static String gender;
    public static boolean isSynced;
    public static String name;
    public static String org;
    public static String phone;
    public static String picture;
    public static String picturePath;
    public static String username;

    static {
        byear = 0;
        isSynced = true;
    }

//    static void setData(Map<String, String> data) {
//        if (data.containsKey(NAME_KEY)) {
//            name = (String) data.get(NAME_KEY);
//        }
//        if (data.containsKey(USERNAME_KEY)) {
//            username = (String) data.get(USERNAME_KEY);
//        }
//        if (data.containsKey(EMAIL_KEY)) {
//            email = (String) data.get(EMAIL_KEY);
//        }
//        if (data.containsKey(ORG_KEY)) {
//            org = (String) data.get(ORG_KEY);
//        }
//        if (data.containsKey(PHONE_KEY)) {
//            phone = (String) data.get(PHONE_KEY);
//        }
//        if (data.containsKey(PICTURE_PATH_KEY)) {
//            picturePath = (String) data.get(PICTURE_PATH_KEY);
//        }
//        if (!(picturePath == null || new File(picturePath).isFile())) {
//            if (Statistics.sharedInstance().isLoggingEnabled()) {
//                Log.w(Statistics.TAG, "Provided file " + picturePath + " can not be opened");
//            }
//            picturePath = null;
//        }
//        if (data.containsKey(PICTURE_KEY)) {
//            picture = (String) data.get(PICTURE_KEY);
//        }
//        if (data.containsKey(GENDER_KEY)) {
//            gender = (String) data.get(GENDER_KEY);
//        }
//        if (data.containsKey(BYEAR_KEY)) {
//            try {
//                byear = Integer.parseInt((String) data.get(BYEAR_KEY));
//            } catch (NumberFormatException e) {
//                if (Statistics.sharedInstance().isLoggingEnabled()) {
//                    Log.w(Statistics.TAG, "Incorrect byear number format");
//                }
//                byear = 0;
//            }
//        }
//        isSynced = false;
//    }
//
//    static void setCustomData(Map<String, String> data) {
//        custom = new HashMap();
//        custom.putAll(data);
//        isSynced = false;
//    }

//    static String getDataForRequest() {
//        if (!isSynced) {
//            isSynced = true;
//            JSONObject json = toJSON();
//            if (json != null) {
//                String result = json.toString();
//                try {
//                    result = URLEncoder.encode(result, AsyncHttpResponseHandler.DEFAULT_CHARSET);
//                    if (result == null || result.equals(BaseConstants.ah)) {
//                        result = BaseConstants.ah;
//                        if (picturePath != null) {
//                            result = result + "&user_details&picturePath=" + URLEncoder.encode(picturePath, AsyncHttpResponseHandler.DEFAULT_CHARSET);
//                        }
//                        if (result != null) {
//                            return result;
//                        }
//                    }
//                    result = "&user_details=" + result;
//                    if (picturePath != null) {
//                        result = result + "&picturePath=" + URLEncoder.encode(picturePath, AsyncHttpResponseHandler.DEFAULT_CHARSET);
//                    }
//                    if (result != null) {
//                        return result;
//                    }
//                } catch (UnsupportedEncodingException e) {
//                }
//            }
//        }
//        return BaseConstants.ah;
//    }

//    static JSONObject toJSON() {
//        JSONObject json = new JSONObject();
//        try {
//            if (name != null) {
//                if (name == BaseConstants.ah) {
//                    json.put(NAME_KEY, JSONObject.NULL);
//                } else {
//                    json.put(NAME_KEY, name);
//                }
//            }
//            if (username != null) {
//                if (username == BaseConstants.ah) {
//                    json.put(USERNAME_KEY, JSONObject.NULL);
//                } else {
//                    json.put(USERNAME_KEY, username);
//                }
//            }
//            if (email != null) {
//                if (email == BaseConstants.ah) {
//                    json.put(EMAIL_KEY, JSONObject.NULL);
//                } else {
//                    json.put(EMAIL_KEY, email);
//                }
//            }
//            if (org != null) {
//                if (org == BaseConstants.ah) {
//                    json.put(ORG_KEY, JSONObject.NULL);
//                } else {
//                    json.put(ORG_KEY, org);
//                }
//            }
//            if (phone != null) {
//                if (phone == BaseConstants.ah) {
//                    json.put(PHONE_KEY, JSONObject.NULL);
//                } else {
//                    json.put(PHONE_KEY, phone);
//                }
//            }
//            if (picture != null) {
//                if (picture == BaseConstants.ah) {
//                    json.put(PICTURE_KEY, JSONObject.NULL);
//                } else {
//                    json.put(PICTURE_KEY, picture);
//                }
//            }
//            if (gender != null) {
//                if (gender == BaseConstants.ah) {
//                    json.put(GENDER_KEY, JSONObject.NULL);
//                } else {
//                    json.put(GENDER_KEY, gender);
//                }
//            }
//            if (byear != 0) {
//                if (byear > 0) {
//                    json.put(BYEAR_KEY, byear);
//                } else {
//                    json.put(BYEAR_KEY, JSONObject.NULL);
//                }
//            }
//            if (custom != null) {
//                if (custom.isEmpty()) {
//                    json.put(CUSTOM_KEY, JSONObject.NULL);
//                } else {
//                    json.put(CUSTOM_KEY, new JSONObject(custom));
//                }
//            }
//        } catch (JSONException e) {
//            if (Statistics.sharedInstance().isLoggingEnabled()) {
//                Log.w(Statistics.TAG, "Got exception converting an UserData to JSON", e);
//            }
//        }
//        return json;
//    }

//    static void fromJSON(JSONObject json) {
//        if (json != null) {
//            name = json.optString(NAME_KEY, null);
//            username = json.optString(USERNAME_KEY, null);
//            email = json.optString(EMAIL_KEY, null);
//            org = json.optString(ORG_KEY, null);
//            phone = json.optString(PHONE_KEY, null);
//            picture = json.optString(PICTURE_KEY, null);
//            gender = json.optString(GENDER_KEY, null);
//            byear = json.optInt(BYEAR_KEY, 0);
//            if (!json.isNull(CUSTOM_KEY)) {
//                try {
//                    JSONObject customJson = json.getJSONObject(CUSTOM_KEY);
//                    HashMap<String, String> custom = new HashMap(customJson.length());
//                    Iterator<String> nameItr = customJson.keys();
//                    while (nameItr.hasNext()) {
//                        String key = (String) nameItr.next();
//                        if (!customJson.isNull(key)) {
//                            custom.put(key, customJson.getString(key));
//                        }
//                    }
//                } catch (JSONException e) {
//                    if (Statistics.sharedInstance().isLoggingEnabled()) {
//                        Log.w(Statistics.TAG, "Got exception converting an Custom Json to Custom User data", e);
//                    }
//                }
//            }
//        }
//    }
//
//    public static String getPicturePathFromQuery(URL url) {
//        String query = url.getQuery();
//        if (query == null) {
//            return BaseConstants.ah;
//        }
//        String[] pairs = query.split("&");
//        String ret = BaseConstants.ah;
//        if (!url.getQuery().contains(PICTURE_PATH_KEY)) {
//            return ret;
//        }
//        for (String pair : pairs) {
//            int idx = pair.indexOf("=");
//            if (pair.substring(0, idx).equals(PICTURE_PATH_KEY)) {
//                try {
//                    return URLDecoder.decode(pair.substring(idx + 1), AsyncHttpResponseHandler.DEFAULT_CHARSET);
//                } catch (UnsupportedEncodingException e) {
//                    return BaseConstants.ah;
//                }
//            }
//        }
//        return ret;
//    }
}
