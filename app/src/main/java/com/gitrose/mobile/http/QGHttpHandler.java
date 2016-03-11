package com.gitrose.mobile.http;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.tencent.android.tpush.common.Constants;
import com.tencent.open.SocialConstants;
import com.gitrose.mobile.R;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
//import io.rong.message.BuildConfig;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
//import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
//import shouji.gexing.framework.utils.DebugUtils;

public abstract class QGHttpHandler<T> implements Listener<String>, ErrorListener {
    private AnimationDrawable animation;
    AnimationDrawable animationDrawable;
    private ViewGroup container;
    private boolean isShowPop;
    private boolean isdialog;
    private RelativeLayout loading;
    private String loadingText;
    private Context mContext;
    private PopupWindow popupWindow;
    private int requestingSize;

    /* renamed from: com.gitrose.mobile.http.QGHttpHandler.1 */
    class C03531 implements Runnable {
        C03531() {
        }

        public void run() {
            QGHttpHandler.this.addLoading();
        }
    }

    /* renamed from: com.gitrose.mobile.http.QGHttpHandler.2 */
    class C03542 implements Runnable {
        C03542() {
        }

        public void run() {
            if (QGHttpHandler.this.container != null && QGHttpHandler.this.loading != null) {
                QGHttpHandler.this.container.removeView(QGHttpHandler.this.loading);
            }
        }
    }

    public abstract void onGetDataSuccess(T t);

    public QGHttpHandler(Context context) {
        this.isdialog = true;
        this.isShowPop = false;
        this.mContext = context;
        showLoading();
    }

    public QGHttpHandler(Context context, boolean isdialog) {
        this.isdialog = true;
        this.isShowPop = false;
        this.mContext = context;
        this.isdialog = isdialog;
        if (isdialog) {
            showLoading();
        }
    }

    public QGHttpHandler(Context context, ViewGroup container) {
        this.isdialog = true;
        this.isShowPop = false;
        this.mContext = context;
        this.container = container;
        showLoading();
    }

    public QGHttpHandler(Context context, boolean isShowLoading, ViewGroup container) {
        this.isdialog = true;
        this.isShowPop = false;
        this.isdialog = isShowLoading;
        this.mContext = context;
        this.container = container;
        if (this.isdialog) {
            showLoading();
        }
    }

    public QGHttpHandler(Context context, boolean isShowPop, ViewGroup container, String loadingText) {
        this.isdialog = true;
        this.isShowPop = false;
        this.mContext = context;
        this.isShowPop = isShowPop;
        this.container = container;
        this.loadingText = loadingText;
        showPopWindoLoading();
    }

    public void onResponse(String responseString) {
        try {
            //DebugUtils.error(responseString);
            JSONObject jsonObject = new JSONObject(responseString);
            if (jsonObject.has("code") && jsonObject.getString("code").equals("0")) {
                Gson gson = new Gson();
                Type type = getType();
                if (jsonObject.has("result")) {
                    Object obj = null;
                    String content = jsonObject.getString("result");
                    if (type == String.class || type == Object.class) {
                        if (content == null || content.equals("")) {
                            obj = responseString;
                        } else {
                            String t = content;
                        }
                    } else if (content.equals("")) {
                        obj = null;
                    } else {
                        obj = gson.fromJson(content, type);
                    }
                    onGetDataSuccess((T) obj);
                    onFinish();
                }

                return;
            }
            if (jsonObject.has("code") && Integer.parseInt(jsonObject.getString("code")) >= 1) {
                onFailure(jsonObject.getInt("code"), null, jsonObject.getString("msg"), null);
            }
            onFinish();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void onErrorResponse(VolleyError error) {
        try {
            if (error.toString().equals("com.android.volley.TimeoutError")) {
                onFailure(0, null, "\u7f51\u7edc\u8d85\u65f6", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
        if (!(this.mContext == null || responseString == null)) {

            if (!(this.mContext == null || responseString.trim().equals(""))) {
                Toast.makeText(this.mContext, responseString, Toast.LENGTH_LONG).show();
            }
        }
        onFinish();
    }

    public void onFinish() {
        if (this.isdialog) {
            removeLoading();
        }
    }

    private Type getType() {
        Type type = String.class;
        Type mySuperClass = getClass().getGenericSuperclass();
        if (mySuperClass instanceof ParameterizedType) {
            return ((ParameterizedType) mySuperClass).getActualTypeArguments()[0];
        }
        return type;
    }

    private void showLoading() {
        new Handler(Looper.getMainLooper()).post(new C03531());
    }

    public void addLoading() {
        if (this.mContext != null && this.container != null) {
            if (this.loading == null) {
                this.loading = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.main_dialog, null);
            }
            LayoutParams lp = new LayoutParams(-1, -1);
            lp.addRule(10, -1);
            this.loading.setLayoutParams(lp);
            this.animationDrawable = (AnimationDrawable) ((ImageView) this.loading.findViewById(R.id.progerss_iv)).getBackground();
            this.animationDrawable.start();
            this.container.addView(this.loading);
        }
    }

    public synchronized void removeLoading() {
        if (this.isShowPop) {
            removePopwindowLoading();
        } else {
            this.requestingSize--;
            if (this.requestingSize <= 0) {
                if (this.animationDrawable != null) {
                    this.animationDrawable.stop();
                }
                new Handler(Looper.getMainLooper()).post(new C03542());
                this.requestingSize = 0;
            }
        }
    }

    private void showPopWindoLoading() {
        View loadingView = View.inflate(this.mContext, R.layout.pop_loading, null);
        ((TextView) loadingView.findViewById(R.id.loading_tv)).setText(this.loadingText == null ? "\u6b63\u5728\u52a0\u8f7d..." : this.loadingText);
        ImageView loadingImg = (ImageView) loadingView.findViewById(R.id.loading_img);
        this.popupWindow = new PopupWindow(loadingView, -2, -2);
        this.popupWindow.setBackgroundDrawable(new BitmapDrawable());
        this.popupWindow.setFocusable(false);
        this.popupWindow.update();
        this.popupWindow.showAtLocation(this.container, 17, 0, 0);
        if (this.animation == null) {
            this.animation = (AnimationDrawable) this.mContext.getResources().getDrawable(R.drawable.qingguo_progress_dialog);
        }
        loadingImg.setImageDrawable(this.animation);
        this.animation.start();
    }

    private void removePopwindowLoading() {
        if (this.animation != null) {
            this.animation.stop();
        }
        this.popupWindow.dismiss();
        this.isShowPop = false;
        this.loadingText = null;
    }
}
