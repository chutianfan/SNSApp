package com.gitrose.mobile.http;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache.Entry;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.constant.Constant;
//import io.rong.message.BuildConfig;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.http.client.utils.URLEncodedUtils;

import shouji.gexing.framework.utils.AppUtils;
//import org.apache.http.entity.mime.MIME;
//import org.apache.http.message.BasicNameValuePair;
//import shouji.gexing.framework.utils.AppUtils;
//import shouji.gexing.framework.utils.DebugUtils;
import shouji.gexing.framework.utils.NetworkUtils;

public class QGClient {
    private static QGClient mInstance;
    public AsyncHttpClient mClient;
    public RequestQueue mRequestQueue;
    private Context mcontext;
    private DefaultRetryPolicy retryPolicy;

    /* renamed from: com.gitrose.mobile.http.QGClient.1 */
    class C07201 extends StringRequest {
        C07201(String $anonymous0, Listener $anonymous1, ErrorListener $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap();
            headers.put("FROM", "mobile");
            headers.put("User-Agent", AppUtils.getUAStr(QGClient.this.mcontext, Constant.DOMAIN));
            return headers;
        }
    }

    /* renamed from: com.gitrose.mobile.http.QGClient.2 */
    class C07212 extends StringRequest {
        C07212(String $anonymous0, Listener $anonymous1, ErrorListener $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap();
            headers.put("FROM", "mobile");
            //headers.put("User-Agent", AppUtils.getUAStr(QGClient.this.mcontext, Constant.DOMAIN));
            return headers;
        }
    }

    /* renamed from: com.gitrose.mobile.http.QGClient.3 */
    class C07223 extends StringRequest {
        private final /* synthetic */ HashMap val$params;

        C07223(int $anonymous0, String $anonymous1, Listener $anonymous2, ErrorListener $anonymous3, HashMap hashMap) {
            super($anonymous0, $anonymous1, $anonymous2, $anonymous3);
            this.val$params = hashMap;
        }

        protected Map<String, String> getParams() throws AuthFailureError {
            return this.val$params;
        }

        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap();
            headers.put("FROM", "mobile");
            headers.put("User-Agent", AppUtils.getUAStr(QGClient.this.mcontext, Constant.DOMAIN));
            headers.put("Content-Type", "application/x-www-form-urlencoded");

            return headers;
        }
    }

    private QGClient() {
        this.mClient = new AsyncHttpClient();
        this.mRequestQueue = MyApplication.getInstance().getmRequestQueue();
        this.retryPolicy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    public static QGClient getInstance() {
        if (mInstance == null) {
            mInstance = new QGClient();
        }
        return mInstance;
    }

    public void getCache(Context context, String url, HashMap<String, String> params, QGHttpHandler<?> handler) {
        this.mcontext = context;
        String URL = getUrlWithQueryString(true, url, params);
        //DebugUtils.error(URL);
        if (this.mRequestQueue.getCache().get(URL) != null) {
            handler.onResponse(new String(this.mRequestQueue.getCache().get(URL).data));
        } else {
            handler.onFinish();
        }
    }

    public void get(Context context, String url, HashMap<String, String> params, QGHttpHandler<?> handler) {
        this.mcontext = context;
        String URL = getUrlWithQueryString(true, url, params);
        //DebugUtils.error(URL);
        Entry cacheEntry = this.mRequestQueue.getCache().get(URL);
        if (!NetworkUtils.hasNetWork(context)) {
            if (cacheEntry != null) {
                handler.onResponse(new String(this.mRequestQueue.getCache().get(URL).data));
            } else {
                handler.onFinish();
            }
            if (MyApplication.getInstance().isIsfirstalert()) {
                MyApplication.getInstance().setIsfirstalert(false);
                Toast.makeText(context, "\u7f51\u7edc\u4e0d\u7ed9\u529b\uff0c\u8bf7\u68c0\u67e5\u7f51\u7edc\u540e\u91cd\u65b0\u8bd5\u8bd5\uff01", Toast.LENGTH_LONG).show();
            }
        } else if (cacheEntry != null && !cacheEntry.isExpired()) {
            handler.onResponse(new String(this.mRequestQueue.getCache().get(URL).data));
        } else if (NetworkUtils.hasNetWork(context)) {
            StringRequest request = new C07201(URL, handler, handler);
            request.setRetryPolicy(this.retryPolicy);
            request.setTag(context);
            this.mRequestQueue.add(request);
        } else {
            handler.onFailure(0, null, "\u7f51\u7edc\u4e0d\u7ed9\u529b\uff0c\u8bf7\u68c0\u67e5\u7f51\u7edc\u540e\u91cd\u65b0\u8bd5\u8bd5\uff01", null);
        }
    }

    public void get(Context context, String url, HashMap<String, String> params, QGHttpHandler<?> handler, boolean isrefresh) {
        if (isrefresh) {
            this.mcontext = context;
            String URL = getUrlWithQueryString(true, url, params);
//            DebugUtils.error(URL);
            Entry cacheEntry = this.mRequestQueue.getCache().get(URL);
            if (!NetworkUtils.hasNetWork(context)) {
                if (cacheEntry != null) {
                    handler.onResponse(new String(this.mRequestQueue.getCache().get(URL).data));
                } else {
                    handler.onFinish();
                }
                if (MyApplication.getInstance().isIsfirstalert()) {
                    MyApplication.getInstance().setIsfirstalert(false);
                    Toast.makeText(context, "\u7f51\u7edc\u4e0d\u7ed9\u529b\uff0c\u8bf7\u68c0\u67e5\u7f51\u7edc\u540e\u91cd\u65b0\u8bd5\u8bd5\uff01", Toast.LENGTH_LONG).show();
                    return;
                }
                return;
            } else if (NetworkUtils.hasNetWork(context)) {
                this.mRequestQueue.getCache().remove(URL);
                StringRequest request = new C07212(URL, handler, handler);
                request.setRetryPolicy(this.retryPolicy);
                request.setTag(context);
                this.mRequestQueue.add(request);
                return;
            } else {
                handler.onFailure(0, null, "\u7f51\u7edc\u4e0d\u7ed9\u529b\uff0c\u8bf7\u68c0\u67e5\u7f51\u7edc\u540e\u91cd\u65b0\u8bd5\u8bd5\uff01", null);
                return;
            }
        }
        get(context, url, params, handler);
    }

    public void post(Context context, String url, HashMap<String, String> params, QGHttpHandler<?> handler) {
        this.mcontext = context;
//        DebugUtils.error(getUrlWithQueryString(true, url, params));
        this.mRequestQueue.add(new C07223(1, url, handler, handler, params));

//        StringRequest request = new StringRequest(1, url, handler, handler)
//        {
//            public Map<String, String> getHeaders()
//                    throws AuthFailureError
//            {
//                HashMap localHashMap = new HashMap();
//                localHashMap.put("FROM", "mobile");
//                localHashMap.put("User-Agent", AppUtils.getUAStr(QGClient.this.mcontext, Constant.DOMAIN));
//                localHashMap.put("Content-Type", "application/x-www-form-urlencoded");
//                return localHashMap;
//            }
//
//            protected HashMap<String, String> getParams()
//                    throws AuthFailureError
//            {
//                return this.getParams();
//            }
//        };
//
//        this.mRequestQueue.add(request);
    }

    public void postAsyn(Context context, String url, RequestParams params, QGHttpHandlerAsyn<?> handler) {
        this.mcontext = context;
        this.mClient.post(context, url, params, handler);
//        String strError = AsyncHttpClient.getUrlWithQueryString(true, url, params);
    }

    public void getfile(Context context, String url, RequestParams params, QGHttpHandlerFileAsyn handler) {
        this.mcontext = context;
//        this.mClient.addHeader("FROM", "mobile");
//        this.mClient.addHeader("referer", "tutu.xingqing.com");
//        this.mClient.addHeader("User-Agent", AppUtils.getUAStr(this.mcontext, Constant.DOMAIN));
//        this.mClient.get(context, url, params, handler);
//        DebugUtils.error(AsyncHttpClient.getUrlWithQueryString(true, url, params));
    }

    public String getUrlWithQueryString(boolean shouldEncodeUrl, String url, HashMap<String, String> params) {
        if (shouldEncodeUrl) {
            url = url.replace(" ", "%20");
        }
        if (params == null) {
            return url;
        }
        String paramString = getParamsList(params).trim();
//        if (paramString.equals(BuildConfig.FLAVOR) || paramString.equals("?")) {
//            return url;
//        }
        return new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(url)).append(url.contains("?") ? "&" : "?").toString())).append(paramString).toString();
    }

    public String getParamsList(HashMap<String, String> params) {
//        List<BasicNameValuePair> lparams = new LinkedList();
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            lparams.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
//        }
        //return URLEncodedUtils.format(lparams, AsyncHttpResponseHandler.DEFAULT_CHARSET);

        return "";
    }

    public boolean checkCacheIsExpired(String url, HashMap<String, String> params) {
        Boolean isExpired = Boolean.valueOf(false);
        Entry cacheEntry = this.mRequestQueue.getCache().get(getUrlWithQueryString(true, url, params));
        if (cacheEntry != null) {
            isExpired = Boolean.valueOf(cacheEntry.isExpired());
        }
        return isExpired.booleanValue();
    }

    public void clearCache() {
        this.mRequestQueue.getCache().clear();
    }
}
