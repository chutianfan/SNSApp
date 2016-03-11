package com.gitrose.mobile.view;

import android.content.Context;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.gitrose.mobile.constant.Constant;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Map.Entry;

public class CustomImageDownaloder extends BaseImageDownloader {
    public CustomImageDownaloder(Context context) {
        super(context);
    }

    public CustomImageDownaloder(Context context, int connectTimeout, int readTimeout) {
        super(context, connectTimeout, readTimeout);
    }

    protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
        HttpURLConnection conn = super.createConnection(url, extra);
        Map<String, String> headers = (Map) extra;
        conn.setConnectTimeout(this.connectTimeout);
        conn.setRequestProperty("Referer", Constant.IMAGE_DOWNLOAD_REFERER);
        conn.setReadTimeout(this.readTimeout);
        if (headers != null) {
            for (Entry<String, String> header : headers.entrySet()) {
                conn.setRequestProperty((String) header.getKey(), (String) header.getValue());
            }
        }
        return conn;
    }
}
