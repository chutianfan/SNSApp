package com.gitrose.mobile.http;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.gitrose.mobile.R;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class QGHttpHandlerAsyn<T> extends TextHttpResponseHandler {
    private AnimationDrawable animation;
    AnimationDrawable animationDrawable;
    private ViewGroup container;
    private boolean isdialog;
    private RelativeLayout loading;
    private Context mContext;

    public abstract void onGetDataSuccess(T t);

    public QGHttpHandlerAsyn(Context context) {
        this.isdialog = true;
        this.mContext = context;
    }

    public QGHttpHandlerAsyn(Context context, boolean isdialog) {
        this.isdialog = true;
        this.mContext = context;
        this.isdialog = isdialog;
    }

    public QGHttpHandlerAsyn(Context context, ViewGroup container) {
        this.isdialog = true;
        this.mContext = context;
        this.container = container;
    }

    public QGHttpHandlerAsyn(Context context, boolean isShowLoading, ViewGroup container) {
        this.isdialog = true;
        this.isdialog = isShowLoading;
        this.mContext = context;
        this.container = container;
    }

    public void onStart() {
        super.onStart();
        if (this.isdialog) {
            addLoading();
        }
    }

    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        removeLoading();
        if (this.mContext != null) {
            if (responseString != null) {
                if (!(this.mContext == null || responseString.trim().equals(""))) {
                    Toast.makeText(this.mContext, responseString, Toast.LENGTH_LONG).show();
                }
            }
            if (throwable != null) {
                throwable.printStackTrace();
                if (this.mContext != null && throwable.getMessage() != null) {
                    Toast.makeText(this.mContext, throwable.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        try {
            removeLoading();
            JSONObject jsonObject = new JSONObject(responseString);
            if (jsonObject.has("code") && jsonObject.getString("code").equals("0")) {
                if (jsonObject.has("result")) {
                    Object obj = null;
                    Gson gson = new Gson();
                    Type type = getType();
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
                    return;
                }
                onGetDataSuccess(null);
                onFinish();
            } else if (jsonObject.has("code") && Integer.parseInt(jsonObject.getString("code")) >= 1) {
                onFailure(jsonObject.getInt("code"), headers, jsonObject.getString("msg"), null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onFailure(statusCode, headers, "\u6570\u636e\u5f02\u5e38", e);
        } catch (Exception e2) {
            e2.printStackTrace();
            onFailure(statusCode, headers, "\u6570\u636e\u5f02\u5e38", e2);
        }
    }

    public void onFinish() {
        super.onFinish();
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

    public void removeLoading() {
        if (this.container != null) {
            this.container.removeView(this.loading);
        }
    }
}
