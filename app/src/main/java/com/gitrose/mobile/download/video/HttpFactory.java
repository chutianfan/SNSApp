package com.gitrose.mobile.download.video;

import android.util.Log;
//import com.alipay.sdk.cons.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
//import com.tencent.TIMGroupManager;
//import com.tencent.imsdk.BaseConstants;
//import com.tencent.qalsdk.core.EndpointKey;
//import com.gitrose.mobile.ThemeActivity;
import com.gitrose.mobile.R;
import com.gitrose.mobile.view.CompatViewPager;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import qalsdk.BaseConstants;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

public class HttpFactory {

    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_ADD_OPTION = 8192;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_APP_ID = 128;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_CREATE_TIME = 2;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_FACE_URL = 4096;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_GROUP_TYPE = 16384;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_INTRODUCTION = 2048;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_LAST_MSG = 32768;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_LAST_MSG_TIME = 64;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_MAX_MEMBER_NUM = 512;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_MEMBER_NUM = 256;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_NAME = 1;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_NEXT_MSG_SEQ = 32;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_NOTIFICATION = 1024;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_OWNER_UIN = 4;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_SEQ = 8;
    public static final int TIM_GET_GROUP_BASE_INFO_FLAG_TIME = 16;
    public static final int TIM_GET_GROUP_MEM_INFO_FLAG_JOIN_TIME = 1;
    public static final int TIM_GET_GROUP_MEM_INFO_FLAG_MSG_FLAG = 2;
    public static final int TIM_GET_GROUP_MEM_INFO_FLAG_ROLE_INFO = 8;
    public static final int TIM_GET_GROUP_MEM_INFO_FLAG_SHUTUP_TIME = 16;

    private static boolean DEBUG = false;
    private static final String TAG = "HttpFactory";

    private static final class GzipDecompressingEntity extends HttpEntityWrapper {
        public GzipDecompressingEntity(HttpEntity entity) {
            super(entity);
        }

        public InputStream getContent() throws IOException, IllegalStateException {
            InputStream wrappedin = this.wrappedEntity.getContent();
            if (HttpFactory.DEBUG) {
                Log.d(HttpFactory.TAG, "GzipDecompressingEntity");
            }
            return new GZIPInputStream(wrappedin);
        }

        public long getContentLength() {
            return -1;
        }
    }

    private static final class GzipHttpRequestInterceptor implements HttpRequestInterceptor {
        private GzipHttpRequestInterceptor() {
        }

        public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
            if (HttpFactory.DEBUG) {
                Log.d(HttpFactory.TAG, "GzipHttpRequestInterceptor");
            }
            request.setHeader(AsyncHttpClient.HEADER_ACCEPT_ENCODING, AsyncHttpClient.ENCODING_GZIP);
        }
    }

    private static final class GzipHttpResponseInterceptor implements HttpResponseInterceptor {
        private GzipHttpResponseInterceptor() {
        }

        public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
            Header header = response.getEntity().getContentEncoding();
            if (header != null) {
                HeaderElement[] codecs = header.getElements();
                for (HeaderElement name : codecs) {
                    if (name.getName().equalsIgnoreCase(AsyncHttpClient.ENCODING_GZIP)) {
                        response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                        return;
                    }
                }
            }
        }
    }

    static {
        DEBUG = false;
    }

    public static InputStream getUngzippedContent(HttpEntity entity) throws IOException {
        InputStream responseStream = entity.getContent();
        if (responseStream == null) {
            return responseStream;
        }
        Header header = entity.getContentEncoding();
        if (header == null) {
            return responseStream;
        }
        String contentEncoding = header.getValue();
        if (contentEncoding == null) {
            return responseStream;
        }
        if (contentEncoding.contains(AsyncHttpClient.ENCODING_GZIP)) {
            if (DEBUG) {
                Log.d(TAG, "getUngzippedContent");
            }
            responseStream = new GZIPInputStream(responseStream);
        }
        return responseStream;
    }

    public static final DefaultHttpClient createHttpClient(int retryCount, int timeout) {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, AsyncHttpResponseHandler.DEFAULT_CHARSET);
        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        HttpConnectionParams.setConnectionTimeout(params, timeout);
        HttpConnectionParams.setSoTimeout(params, timeout);
        HttpConnectionParams.setSocketBufferSize(params, TIM_GET_GROUP_BASE_INFO_FLAG_ADD_OPTION);
        HttpClientParams.setRedirecting(params, true);
        ConnManagerParams.setTimeout(params, (long) timeout);
        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(50));
        ConnManagerParams.setMaxTotalConnections(params, CompatViewPager.SNAP_VELOCITY);
        SchemeRegistry supportedSchemes = new SchemeRegistry();
        supportedSchemes.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        supportedSchemes.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        DefaultHttpClient httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(params, supportedSchemes), params);
        httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(retryCount, true));
        return httpClient;
    }

    public static final DefaultHttpClient createHttpClient(int retryCount) {
//        HttpParams params = new BasicHttpParams();
//        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//        HttpProtocolParams.setContentCharset(params, AsyncHttpResponseHandler.DEFAULT_CHARSET);
//        HttpConnectionParams.setStaleCheckingEnabled(params, false);
//        HttpConnectionParams.setConnectionTimeout(params, 15000);
//        HttpConnectionParams.setSoTimeout(params, BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT);
//        HttpConnectionParams.setSocketBufferSize(params, TIM_GET_GROUP_BASE_INFO_FLAG_ADD_OPTION);
//        HttpClientParams.setRedirecting(params, true);
//        ConnManagerParams.setTimeout(params, BaseConstants.DEFAULT_MSG_TIMEOUT);
//        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(50));
//        ConnManagerParams.setMaxTotalConnections(params, CompatViewPager.SNAP_VELOCITY);
//        SchemeRegistry supportedSchemes = new SchemeRegistry();
//        supportedSchemes.register(new Scheme(EndpointKey.f2712d, PlainSocketFactory.getSocketFactory(), 80));
//        supportedSchemes.register(new Scheme(R.f238a, SSLSocketFactory.getSocketFactory(), 443));
//        DefaultHttpClient httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(params, supportedSchemes), params);
//        httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(retryCount, true));
//        httpClient.addRequestInterceptor(new GzipHttpRequestInterceptor());
//        httpClient.addResponseInterceptor(new GzipHttpResponseInterceptor());
//        return httpClient;
        return null;
    }

    public static final DefaultHttpClient createHttpClient() {
        return createHttpClient(3);
    }

    public static final DefaultHttpClient createHttpClient(String proxyUri, int port) {
//        HttpParams params = new BasicHttpParams();
//        params.setParameter("http.route.default-proxy", new HttpHost(proxyUri, port));
//        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//        HttpProtocolParams.setContentCharset(params, AsyncHttpResponseHandler.DEFAULT_CHARSET);
//        HttpConnectionParams.setStaleCheckingEnabled(params, false);
//        HttpConnectionParams.setConnectionTimeout(params, ThemeActivity.RESULT_CODE);
//        HttpConnectionParams.setSoTimeout(params, BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT);
//        HttpConnectionParams.setSocketBufferSize(params, TIM_GET_GROUP_BASE_INFO_FLAG_ADD_OPTION);
//        HttpClientParams.setRedirecting(params, true);
//        ConnManagerParams.setTimeout(params, BaseConstants.DEFAULT_MSG_TIMEOUT);
//        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(50));
//        ConnManagerParams.setMaxTotalConnections(params, CompatViewPager.SNAP_VELOCITY);
//        SchemeRegistry supportedSchemes = new SchemeRegistry();
//        supportedSchemes.register(new Scheme(EndpointKey.f2712d, PlainSocketFactory.getSocketFactory(), 80));
//        supportedSchemes.register(new Scheme(R.f238a, SSLSocketFactory.getSocketFactory(), 443));
//        DefaultHttpClient httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(params, supportedSchemes), params);
//        httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
//        httpClient.addRequestInterceptor(new GzipHttpRequestInterceptor());
//        httpClient.addResponseInterceptor(new GzipHttpResponseInterceptor());
//        return httpClient;

        return  null;
    }
}
