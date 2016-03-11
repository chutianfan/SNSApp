package com.gitrose.mobile.download.video;

import android.util.Log;
import com.gitrose.mobile.model.VideoDownLoad;
import com.gitrose.mobile.view.CompatViewPager;
import java.io.File;
import java.net.URI;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.HttpContext;

public abstract class DownLoadRunnable<T extends VideoDownLoad> implements Runnable {
    public static final String TAG = "DownLoadRunnable";
    protected T bean;
    protected HttpClient client;
    protected long curPosition;
    protected long endPos;
    protected File file;
    protected HttpGet get;
    protected boolean isCancelled;
    protected boolean isFinished;
    protected boolean isPause;
    protected long startPos;

    /* renamed from: com.gitrose.mobile.download.video.DownLoadRunnable.1 */
    class C08901 extends DefaultRedirectHandler {
        C08901() {
        }

        public URI getLocationURI(HttpResponse response, HttpContext context) throws ProtocolException {
            URI uri = super.getLocationURI(response, context);
            Log.d(DownLoadRunnable.TAG, "redictory:" + uri.toString());
            return uri;
        }
    }

    public DownLoadRunnable(T bean) {
        this.isCancelled = false;
        this.isFinished = false;
        this.isPause = false;
        this.bean = bean;
        this.get = new HttpGet(bean.getUrl());
        this.endPos = bean.getEndPos();
        this.client = getClient();
        this.file = new File(bean.getTempPath());
        if (this.file.exists()) {
            this.startPos = this.file.length();
            bean.setStartPos(this.startPos);
            return;
        }
        this.startPos = 0;
        bean.setStartPos(this.startPos);
    }

    private HttpClient getClient() {
        DefaultHttpClient client = HttpFactory.createHttpClient(5, 60000);
        client.getParams().setParameter("http.protocol.allow-circular-redirects", Boolean.valueOf(true));
        client.setRedirectHandler(new C08901());
        return client;
    }

    protected long getContetnLengteh(String url, HttpClient client) {
        try {
            HttpResponse response = client.execute(new HttpHead(url));
            if (response.getStatusLine().getStatusCode() == CompatViewPager.SNAP_VELOCITY) {
                Header[] headers = response.getAllHeaders();
                for (Header header : headers) {
                    if (header.getName().toLowerCase().equals("content-length")) {
                        return Long.valueOf(header.getValue()).longValue();
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public boolean isDone() {
        return isCancelled() || this.isFinished;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        this.isCancelled = true;
        if (!(!mayInterruptIfRunning || this.get == null || this.get.isAborted())) {
            this.get.abort();
        }
        return isCancelled();
    }

    public boolean pause() {
        this.isPause = true;
        if (!(this.get == null || this.get.isAborted())) {
            this.get.abort();
        }
        return this.isFinished;
    }
}
