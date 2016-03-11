package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
//import com.tencent.connect.share.QzoneShare;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.http.ConstantURL;
//import shouji.gexing.framework.utils.NetUtils;

public class WebViewActivity extends BaseActivity implements OnClickListener {
    private WebView about_webview;
    private boolean isFunction;
    private ViewGroup rl_webView;
    private TextView title_tv_text;
    private ImageView webProgress;

    /* renamed from: com.gitrose.mobile.WebViewActivity.1 */
    class C03341 extends WebViewClient {
        C03341() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        }
    }

    /* renamed from: com.gitrose.mobile.WebViewActivity.2 */
    class C03352 extends WebChromeClient {
        C03352() {
        }

        public void onProgressChanged(WebView view, int progress) {
            if (progress != 100) {
                WebViewActivity.this.loadingAnimation(true);
            } else {
                WebViewActivity.this.loadingAnimation(false);
            }
        }

        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (!TextUtils.isEmpty(title) && !WebViewActivity.this.isFunction) {
                WebViewActivity.this.setTitle(title);
                WebViewActivity.this.title_tv_text.setText(title);
            }
        }

        public void onReachedMaxAppCacheSize(long spaceNeeded, long totalUsedQuota, QuotaUpdater quotaUpdater) {
            quotaUpdater.updateQuota(2 * spaceNeeded);
        }
    }

    public static void openWebView(Context context, String title, String url) {
//        Intent i = new Intent(context, WebViewActivity.class);
//        i.putExtra(QzoneShare.SHARE_TO_QQ_TITLE, title);
//        i.putExtra("web_url", url);
//        context.startActivity(i);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
        loadingAnimation(true);
    }

    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        this.title_tv_text = (TextView) findViewById(R.id.title_tv_text);
        this.rl_webView = (ViewGroup) findViewById(R.id.rl_webView);
        this.about_webview = (WebView) findViewById(R.id.about_webview);
        addLoadingProgress();
        settingWebView();
        Intent intent = getIntent();
        this.isFunction = intent.getBooleanExtra("isFunction", false);
        if (this.isFunction) {
            this.title_tv_text.setText("\u529f\u80fd\u4ecb\u7ecd");
            this.about_webview.loadUrl(ConstantURL.FUNCTION_URL);
            return;
        }
        String webUrl = intent.getStringExtra("web_url");
        this.title_tv_text.setText("\u52a0\u8f7d\u4e2d...");
        this.about_webview.loadUrl(webUrl);
    }

    private void addLoadingProgress() {
        RelativeLayout loading = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.main_dialog, null);
        this.rl_webView.addView(loading);
        this.webProgress = (ImageView) loading.findViewById(R.id.progerss_iv);
    }

    private void loadingAnimation(boolean isWork) {
        AnimationDrawable animationDrawable = (AnimationDrawable) this.webProgress.getBackground();
        if (isWork) {
            if (!animationDrawable.isRunning()) {
                animationDrawable.start();
            }
//            this.webProgress.setVisibility(0);
            return;
        }
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
//        this.webProgress.setVisibility(8);
    }

    private void settingWebView() {
        WebSettings webseting = this.about_webview.getSettings();
        webseting.setJavaScriptEnabled(true);
        webseting.setAllowFileAccess(true);
        webseting.setAppCacheEnabled(true);
        webseting.setDomStorageEnabled(true);
        webseting.setAppCacheMaxSize(8388608);
        webseting.setAppCachePath(getApplicationContext().getDir("cache", 0).getPath());
//        if (NetUtils.checkNet(this)) {
//            webseting.setCacheMode(-1);
//        } else {
//            webseting.setCacheMode(1);
//        }
        this.about_webview.setWebViewClient(new C03341());
        this.about_webview.setWebChromeClient(new C03352());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
            default:
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
