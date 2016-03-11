package com.gitrose.mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.support.v4.media.TransportMediator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toast;
//import com.sina.weibo.sdk.auth.AuthInfo;
//import com.sina.weibo.sdk.auth.Oauth2AccessToken;
//import com.sina.weibo.sdk.auth.WeiboAuthListener;
//import com.sina.weibo.sdk.auth.sso.SsoHandler;
//import com.sina.weibo.sdk.component.ShareRequestParam;
//import com.sina.weibo.sdk.exception.WeiboException;
//import com.sina.weibo.sdk.net.RequestListener;
//import com.sina.weibo.sdk.openapi.UsersAPI;
//import com.sina.weibo.sdk.openapi.models.User;
//import com.tencent.connect.UserInfo;
//import com.tencent.connect.auth.QQAuth;
//import com.tencent.tauth.IUiListener;
//import com.tencent.tauth.Tencent;
//import com.tencent.tauth.UiError;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.application.ActivityManager;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.auth.UserAuth;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.UserInfo;
import com.gitrose.mobile.utils.ShareUtils;
import com.gitrose.mobile.view.KeyboardRelativeLayout;
import com.gitrose.mobile.view.KeyboardRelativeLayout.IOnKeyboardStateChangedListener;
//import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import io.rong.imlib.statistics.UserData;
import java.util.Locale;
//import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import qalsdk.BaseConstants;
//import qalsdk.ab;
//import shouji.gexing.framework.utils.//DebugUtils;
import shouji.gexing.framework.utils.TelUtil;

public class LoginActivity extends BaseActivity implements OnClickListener, IOnKeyboardStateChangedListener {
    private LinearLayout container_rl;
    private LinearLayout err_ll;
    private TextView err_tv;
    private LinearLayout foreign_ll;
    private ImageView icon_iv;
    private boolean isShowKeyboard;
    boolean isSinaMessage;
    boolean isrequest;
    private Button login_bt;
    private ImageView login_message_iv;
    private ImageView login_qq_iv;
    private ScrollView login_scrollview;
//    private ImageView login_sina_iv;
//    private Oauth2AccessToken mAccessToken;
//    private AuthInfo mAuthInfo;
    private Handler mHandler;
    private UserInfo mInfo;
    private InputMethodManager mInputMethodManager;
    private MyApplication mMyApplication;
//    private SsoHandler mSsoHandler;
//    private UsersAPI mUsersAPI;
    private RelativeLayout or_rl;
    private ImageView pass_iv_delete;
    private EditText password_et;
    private EditText phone_et;
    private ImageView phone_iv_delete;
    private boolean phone_num_change;
    private String qq_isvisible;
    private RelativeLayout qq_rl;
    private TextView register_tv;
    private KeyboardRelativeLayout rl_keyborad;
    private ScrollView scroll_view;
    private Animation translateAnimation;

    /* renamed from: com.gitrose.mobile.LoginActivity.1 */
    class C06961 implements Runnable {
        C06961() {
        }

        public void run() {
            LoginActivity.this.login_scrollview.fullScroll(TransportMediator.KEYCODE_MEDIA_RECORD);
        }
    }

    /* renamed from: com.gitrose.mobile.LoginActivity.2 */
    class C06982 extends Thread {
        private final /* synthetic */ TranslateAnimation val$outanimation;

        /* renamed from: com.gitrose.mobile.LoginActivity.2.1 */
        class C06971 implements Runnable {
            private final /* synthetic */ TranslateAnimation val$outanimation;

            C06971(TranslateAnimation translateAnimation) {
                this.val$outanimation = translateAnimation;
            }

            public void run() {
                LoginActivity.this.err_ll.startAnimation(this.val$outanimation);
                LoginActivity.this.err_ll.setVisibility(View.GONE);
            }
        }

        C06982(TranslateAnimation translateAnimation) {
            this.val$outanimation = translateAnimation;
        }

        public void run() {
            super.run();
            LoginActivity.this.mHandler.postDelayed(new C06971(this.val$outanimation), 2000);
        }
    }

    /* renamed from: com.gitrose.mobile.LoginActivity.3 */
    class C06993 implements OnFocusChangeListener {
        C06993() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus && LoginActivity.this.phone_et.length() > 0) {
                LoginActivity.this.phone_iv_delete.setVisibility(View.VISIBLE);
            } else if (!hasFocus || LoginActivity.this.phone_et.length() == 0) {
                LoginActivity.this.phone_iv_delete.setVisibility(View.GONE);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.LoginActivity.4 */
    class C07004 implements OnFocusChangeListener {
        C07004() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus && LoginActivity.this.password_et.length() > 0) {
                LoginActivity.this.pass_iv_delete.setVisibility(View.VISIBLE);
            } else if (!hasFocus || LoginActivity.this.password_et.length() == 0) {
                LoginActivity.this.pass_iv_delete.setVisibility(View.GONE);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.LoginActivity.5 */
    class C07015 implements TextWatcher {
        C07015() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (LoginActivity.this.phone_et.getText().toString().length() == 0) {
                LoginActivity.this.phone_iv_delete.setVisibility(View.GONE);
            } else if (LoginActivity.this.phone_et.getText().toString().length() > 0 && LoginActivity.this.phone_et.hasFocus()) {
                LoginActivity.this.phone_iv_delete.setVisibility(View.VISIBLE);
            }
            String phone_num = LoginActivity.this.phone_et.getText().toString().replaceAll(" ", BaseConstants.ah);
            if (phone_num.length() == 11 && LoginActivity.this.phone_num_change) {
                LoginActivity.this.phone_num_change = false;
                String new_phone_1 = phone_num.substring(0, 3);
                String new_phone_2 = phone_num.substring(3, 7);
                LoginActivity.this.phone_et.setText(new StringBuilder(String.valueOf(new_phone_1)).append(" ").append(new_phone_2).append(" ").append(phone_num.substring(7, 11)).toString());
                LoginActivity.this.phone_et.setSelection(LoginActivity.this.phone_et.getText().toString().length());
            } else if (phone_num.length() < 11) {
                LoginActivity.this.phone_num_change = true;
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.gitrose.mobile.LoginActivity.6 */
    class C07026 implements TextWatcher {
        C07026() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (LoginActivity.this.password_et.getText().toString().length() == 0) {
                LoginActivity.this.pass_iv_delete.setVisibility(View.GONE);
            } else if (LoginActivity.this.password_et.getText().toString().length() > 0 && LoginActivity.this.password_et.hasFocus()) {
                LoginActivity.this.pass_iv_delete.setVisibility(View.VISIBLE);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.gitrose.mobile.LoginActivity.11 */
//    class AnonymousClass11 implements IUiListener {
//        private final /* synthetic */ String val$access_token;
//        private final /* synthetic */ String val$openid;
//
//        AnonymousClass11(String str, String str2) {
//            this.val$openid = str;
//            this.val$access_token = str2;
//        }
//
//        public void onError(UiError arg0) {
//            //DebugUtils.error("onError------" + arg0.toString());
//            LoginActivity.this.isrequest = false;
//            LoginActivity.this.login_qq_iv.setSelected(false);
//            LoginActivity.this.icon_iv.clearAnimation();
//            LoginActivity.this.qqlogin(this.val$openid, this.val$access_token, BaseConstants.ah);
//        }
//
//        public void onComplete(Object arg0) {
//            //DebugUtils.error("onComplete------" + arg0.toString());
//            try {
//                JSONObject jsonobj = (JSONObject) arg0;
//                if (jsonobj.has("nickname")) {
//                    LoginActivity.this.qqlogin(this.val$openid, this.val$access_token, jsonobj.getString("nickname"));
//                    return;
//                }
//                LoginActivity.this.qqlogin(this.val$openid, this.val$access_token, BaseConstants.ah);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        public void onCancel() {
//            LoginActivity.this.isrequest = false;
//            LoginActivity.this.login_qq_iv.setSelected(false);
//            LoginActivity.this.icon_iv.clearAnimation();
//        }
//    }

    /* renamed from: com.gitrose.mobile.LoginActivity.13 */
//    class AnonymousClass13 implements RequestListener {
//        private final /* synthetic */ String val$accessToken;
//        private final /* synthetic */ long val$expiresin;
//        private final /* synthetic */ boolean val$isMesssage;
//        private final /* synthetic */ String val$phoneNum;
//        private final /* synthetic */ long val$uid;
//
//        AnonymousClass13(boolean z, String str, long j, String str2, long j2) {
//            this.val$isMesssage = z;
//            this.val$phoneNum = str;
//            this.val$uid = j;
//            this.val$accessToken = str2;
//            this.val$expiresin = j2;
//        }
//
//        public void onComplete(String response) {
//            if (!TextUtils.isEmpty(response)) {
//                String nickname = User.parse(response).name;
//                if (this.val$isMesssage) {
//                    LoginActivity.this.startSinaMessageLogin(nickname, this.val$phoneNum, new StringBuilder(String.valueOf(this.val$uid)).toString(), this.val$accessToken, new StringBuilder(String.valueOf(this.val$expiresin)).toString());
//                } else {
//                    LoginActivity.this.startSinaLogin(nickname, new StringBuilder(String.valueOf(this.val$uid)).toString(), this.val$accessToken, new StringBuilder(String.valueOf(this.val$expiresin)).toString());
//                }
//            }
//        }
//
//        public void onWeiboException(WeiboException e) {
//            Toast.makeText(LoginActivity.this, "Auth exception : " + e.getMessage(), 1).show();
//            //DebugUtils.error("e.getMessage()-----" + e.getMessage());
//        }
//    }

//    class AuthListener implements WeiboAuthListener {
//        AuthListener() {
//        }
//
//        public void onComplete(Bundle values) {
//            LoginActivity.this.mAccessToken = Oauth2AccessToken.parseAccessToken(values);
//            String phoneNum = LoginActivity.this.mAccessToken.getPhoneNum();
//            if (LoginActivity.this.mAccessToken.isSessionValid()) {
//                LoginActivity.this.getSinaUserinfo(LoginActivity.this.isSinaMessage);
//                ShareUtils.writeAccessToken(LoginActivity.this, LoginActivity.this.mAccessToken);
//                return;
//            }
//            Toast.makeText(LoginActivity.this, "\u6388\u6743\u5931\u8d25", 1).show();
//        }
//
//        public void onCancel() {
//            //DebugUtils.error("cancel-----");
//            LoginActivity.this.isrequest = false;
//        }
//
//        public void onWeiboException(WeiboException e) {
//            //DebugUtils.error("e.getMessage()-----" + e.getMessage());
//            Toast.makeText(LoginActivity.this, "Auth exception : " + e.getMessage(), 1).show();
//        }
//    }

    /* renamed from: com.gitrose.mobile.LoginActivity.12 */
    class AnonymousClass12 extends QGHttpHandler<TutuUsers> {
        private final /* synthetic */ String val$access_token;
        private final /* synthetic */ String val$nickname;
        private final /* synthetic */ String val$openid;

        AnonymousClass12(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str, String str2, String str3) {

            super($anonymous0, $anonymous1, $anonymous2);
            this.val$openid = str;
            this.val$access_token = str2;
            this.val$nickname = str3;
            
        }

        public void onGetDataSuccess(TutuUsers data) {
            LoginActivity.this.loginSuccess(data);
            onFinish();
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            if (statusCode == 100817) {
                LoginActivity.this.startQQLoginRegActivity(this.val$openid, this.val$access_token, this.val$nickname);
            } else {
                LoginActivity.this.initAnimation(responseString);
            }
            LoginActivity.this.icon_iv.clearAnimation();
            onFinish();
        }

        public void onFinish() {
            super.onFinish();
            LoginActivity.this.isrequest = false;
            LoginActivity.this.login_qq_iv.setSelected(false);
            LoginActivity.this.icon_iv.clearAnimation();
        }
    }

    /* renamed from: com.gitrose.mobile.LoginActivity.14 */
    class AnonymousClass14 extends QGHttpHandler<TutuUsers> {
        private final /* synthetic */ String val$accessToken;
        private final /* synthetic */ String val$expiresin;
        private final /* synthetic */ String val$nickname;
        private final /* synthetic */ String val$wbuid;

        AnonymousClass14(Context $anonymous0, String str, String str2, String str3, String str4) {

            super($anonymous0);
            this.val$wbuid = str;
            this.val$accessToken = str2;
            this.val$expiresin = str3;
            this.val$nickname = str4;
            
        }

        public void onGetDataSuccess(TutuUsers data) {
            LoginActivity.this.loginSuccess(data);
            onFinish();
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            if (statusCode == 105106) {
                LoginActivity.this.startSinaLoginRegActivity(this.val$wbuid, this.val$accessToken, this.val$expiresin, this.val$nickname, false, BaseConstants.ah);
            } else {
                LoginActivity.this.initAnimation(responseString);
            }
            LoginActivity.this.icon_iv.clearAnimation();
            onFinish();
        }

        public void onFinish() {
            super.onFinish();
            LoginActivity.this.isrequest = false;
            LoginActivity.this.login_qq_iv.setSelected(false);
            LoginActivity.this.icon_iv.clearAnimation();
        }
    }

    /* renamed from: com.gitrose.mobile.LoginActivity.15 */
    class AnonymousClass15 extends QGHttpHandler<TutuUsers> {
        private final /* synthetic */ String val$accessToken;
        private final /* synthetic */ String val$expiresin;
        private final /* synthetic */ String val$nickname;
        private final /* synthetic */ String val$phone;
        private final /* synthetic */ String val$wbuid;

        AnonymousClass15(Context $anonymous0, String str, String str2, String str3, String str4, String str5) {

            super($anonymous0);
            this.val$wbuid = str;
            this.val$accessToken = str2;
            this.val$expiresin = str3;
            this.val$nickname = str4;
            this.val$phone = str5;
            
        }

        public void onGetDataSuccess(TutuUsers data) {
            LoginActivity.this.loginSuccess(data);
            onFinish();
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            if (statusCode == 105106) {
                LoginActivity.this.startSinaLoginRegActivity(this.val$wbuid, this.val$accessToken, this.val$expiresin, this.val$nickname, true, this.val$phone);
            } else {
                LoginActivity.this.initAnimation(responseString);
            }
            LoginActivity.this.icon_iv.clearAnimation();
            onFinish();
        }

        public void onFinish() {
            super.onFinish();
            LoginActivity.this.isrequest = false;
            LoginActivity.this.login_qq_iv.setSelected(false);
            LoginActivity.this.icon_iv.clearAnimation();
        }
    }

    /* renamed from: com.gitrose.mobile.LoginActivity.7 */
    class C16607 extends QGHttpHandler<TutuUsers> {
        private final /* synthetic */ String val$accesstoken;
        private final /* synthetic */ String val$expires;
        private final /* synthetic */ String val$fbuid;
        private final /* synthetic */ String val$nickname;

        C16607(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str, String str2, String str3, String str4) {

            super($anonymous0, $anonymous1, $anonymous2);
            this.val$fbuid = str;
            this.val$accesstoken = str2;
            this.val$expires = str3;
            this.val$nickname = str4;
            
        }

        public void onGetDataSuccess(TutuUsers data) {
            LoginActivity.this.loginSuccess(data);
            onFinish();
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            if (statusCode == 105206) {
                LoginActivity.this.startFaceBookLoginRegActivity(this.val$fbuid, this.val$accesstoken, this.val$expires, this.val$nickname);
            } else {
                LoginActivity.this.initAnimation(responseString);
            }
            onFinish();
        }

        public void onFinish() {
            super.onFinish();
            LoginActivity.this.isrequest = false;
            LoginActivity.this.login_qq_iv.setSelected(false);
            LoginActivity.this.icon_iv.clearAnimation();
        }
    }

    /* renamed from: com.gitrose.mobile.LoginActivity.8 */
    class C16618 extends QGHttpHandler<TutuUsers> {
        private final /* synthetic */ String val$accesstoken;
        private final /* synthetic */ String val$accesstokensecret;
        private final /* synthetic */ String val$nickname;
        private final /* synthetic */ String val$ttuid;

        C16618(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str, String str2, String str3, String str4) {

            super($anonymous0, $anonymous1, $anonymous2);
            this.val$ttuid = str;
            this.val$accesstoken = str2;
            this.val$accesstokensecret = str3;
            this.val$nickname = str4;
            
        }

        public void onGetDataSuccess(TutuUsers data) {
            LoginActivity.this.loginSuccess(data);
            onFinish();
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            if (statusCode == 105306) {
                LoginActivity.this.startTwitterLoginRegActivity(this.val$ttuid, this.val$accesstoken, this.val$accesstokensecret, this.val$nickname);
            } else {
                LoginActivity.this.initAnimation(responseString);
            }
            onFinish();
        }

        public void onFinish() {
            super.onFinish();
            LoginActivity.this.isrequest = false;
            LoginActivity.this.login_qq_iv.setSelected(false);
            LoginActivity.this.icon_iv.clearAnimation();
        }
    }

    /* renamed from: com.gitrose.mobile.LoginActivity.9 */
    public class LoginHandler extends QGHttpHandler<TutuUsers> {
        LoginHandler(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(TutuUsers data) {
            LoginActivity.this.loginSuccess(data);
            onFinish();
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            LoginActivity.this.initAnimation(responseString);
            LoginActivity.this.icon_iv.clearAnimation();
            onFinish();
        }

        public void onFinish() {
            super.onFinish();
            LoginActivity.this.isrequest = false;
            LoginActivity.this.login_bt.setSelected(false);
            LoginActivity.this.login_bt.setText(LoginActivity.this.getResources().getString(R.string.login));
        }
    }

    public LoginActivity() {
        this.mHandler = new Handler();
        this.isShowKeyboard = true;
        this.isrequest = false;
        this.mInfo = null;
        this.qq_isvisible = "false";
        this.phone_num_change = true;
//        this.mAccessToken = null;
        this.isSinaMessage = false;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.mMyApplication = MyApplication.getInstance();
        this.mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        MobclickAgent.updateOnlineConfig(this);
//        this.qq_isvisible = MobclickAgent.getConfigParams(this, "qq_isvisible");
        initUI();
        this.foreign_ll.setVisibility(View.GONE);
        this.scroll_view.setVisibility(View.VISIBLE);
//        this.mMyApplication.mTencent = Tencent.createInstance(Constant.QQ_APP_ID, getApplicationContext());
//        this.mMyApplication.mQQAuth = QQAuth.createInstance(Constant.QQ_APP_ID, getApplicationContext());
//        this.mInfo = new UserInfo(this, this.mMyApplication.mQQAuth.getQQToken());
//        this.mAuthInfo = new AuthInfo(this, Constant.SINA_APP_KEY, Constant.SINA_REDIRECT_URL, Constant.SINA_SCOPE);
//        this.mSsoHandler = new SsoHandler(this, this.mAuthInfo);
    }

    private void initUI() {
        this.foreign_ll = (LinearLayout) findViewById(R.id.foreign_login_ll);
        this.scroll_view = (ScrollView) findViewById(R.id.login_scrollview);
        findViewById(R.id.tv_login_close).setOnClickListener(this);
        this.rl_keyborad = (KeyboardRelativeLayout) findViewById(R.id.rl_keyborad);
        this.container_rl = (LinearLayout) findViewById(R.id.container_rl);
        this.phone_et = (EditText) findViewById(R.id.activity_login_et_phone);
        this.password_et = (EditText) findViewById(R.id.activity_login_et_password);
        this.login_bt = (Button) findViewById(R.id.activity_login_bt);
        this.register_tv = (TextView) findViewById(R.id.activity_login_register_bt);
        this.phone_iv_delete = (ImageView) findViewById(R.id.activity_login_phone_iv_delete);
        this.pass_iv_delete = (ImageView) findViewById(R.id.activity_login_pass_iv_delete);
        this.err_tv = (TextView) findViewById(R.id.login_err_tv);
        this.err_ll = (LinearLayout) findViewById(R.id.login_ll_tv);
        this.icon_iv = (ImageView) findViewById(R.id.login_icon_iv);
        findViewById(R.id.activity_login_tv_forget_password).setOnClickListener(this);
        this.qq_rl = (RelativeLayout) findViewById(R.id.activity_login_rl_qq);
        this.or_rl = (RelativeLayout) findViewById(R.id.activity_login_rl_or);
        this.login_qq_iv = (ImageView) findViewById(R.id.activity_login_qq_iv);
        this.login_qq_iv.setOnClickListener(this);
//        this.login_sina_iv = (ImageView) findViewById(R.id.login_sina_iv);
//        this.login_sina_iv.setOnClickListener(this);
        this.login_message_iv = (ImageView) findViewById(R.id.login_message_iv);
        this.login_message_iv.setOnClickListener(this);
//        if (this.qq_isvisible == null || !this.qq_isvisible.equals("false")) {
//            this.qq_rl.setVisibility(View.VISIBLE);
//            this.or_rl.setVisibility(View.VISIBLE);
//        } else {
//            this.qq_rl.setVisibility(View.GONE);
//            this.or_rl.setVisibility(View.GONE);
//        }

        this.qq_rl.setVisibility(View.VISIBLE);
        this.or_rl.setVisibility(View.VISIBLE);

        this.login_bt.setOnClickListener(this);
        this.register_tv.setOnClickListener(this);
        this.phone_iv_delete.setOnClickListener(this);
        this.pass_iv_delete.setOnClickListener(this);
        this.container_rl.setOnClickListener(this);
        this.login_scrollview = (ScrollView) findViewById(R.id.login_scrollview);
        this.rl_keyborad.setOnKeyboardStateChangedListener(this);
        initet();
        this.translateAnimation = AnimationUtils.loadAnimation(this, R.anim.login_scale_animation);
        String language = Locale.getDefault().getLanguage();
        language = Locale.getDefault().getLanguage();
//        if (language == null || !language.equals(SocializeProtocolConstants.PROTOCOL_KEY_EN)) {
//            MyApplication.getInstance().ischina = true;
//        } else {
//            MyApplication.getInstance().ischina = false;
//        }
    }

    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new C06961(), 500);
    }

    private void initAnimation(String err_text) {
        this.err_ll.setVisibility(View.VISIBLE);
        this.err_tv.setText(err_text);
        TranslateAnimation animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -3.0f, 1, 0.0f);
        animation.setDuration(800);
        animation.setRepeatCount(0);
        this.err_ll.startAnimation(animation);
        TranslateAnimation outanimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -0.0f, 1, -3.0f);
        outanimation.setDuration(800);
        outanimation.setRepeatCount(0);
        new C06982(outanimation).start();
    }

    private void initet() {
//        this.phone_et.setText("13154159840");
//        this.password_et.setText("qwertyuiop");
        this.phone_et.setOnFocusChangeListener(new C06993());
        this.password_et.setOnFocusChangeListener(new C07004());
        this.phone_et.addTextChangedListener(new C07015());
        this.password_et.addTextChangedListener(new C07026());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.container_rl:
                if (!this.mInputMethodManager.isActive()) {
                    return;
                }
                if (this.phone_et.isFocusable()) {
                    this.mInputMethodManager.hideSoftInputFromWindow(this.phone_et.getWindowToken(), 0);
                } else {
                    this.mInputMethodManager.hideSoftInputFromWindow(this.password_et.getWindowToken(), 0);
                }
                break;
            case R.id.tv_login_close:
                finish();
                break;
            case R.id.activity_login_phone_iv_delete:
                this.phone_et.setText(BaseConstants.ah);
                break;
            case R.id.activity_login_pass_iv_delete:
                this.password_et.setText(BaseConstants.ah);
                break;
            case R.id.activity_login_bt:
                if (!this.isrequest) {
//                    String phoneNumber = this.phone_et.getText().toString().replace(" ", BaseConstants.ah);
//                    String password = this.password_et.getText().toString();
                    String phoneNumber = "13154159840";
                    String password = "qwertyuiop";

                    if (this.mInputMethodManager.isActive()) {
                        if (this.phone_et.isFocusable()) {
                            this.mInputMethodManager.hideSoftInputFromWindow(this.phone_et.getWindowToken(), 0);
                        } else {
                            this.mInputMethodManager.hideSoftInputFromWindow(this.password_et.getWindowToken(), 0);
                        }
                    }
                    if (phoneNumber.equals(BaseConstants.ah) || !TelUtil.isTel(phoneNumber)) {
                        this.phone_et.startAnimation(shakeAnimation(4));
                    } else if (password.equals(BaseConstants.ah)) {
                        this.password_et.startAnimation(shakeAnimation(4));
                    } else {
                        login(phoneNumber, password);
                        this.login_scrollview.smoothScrollTo(0, 0);
                    }
                }
                break;
            case R.id.activity_login_tv_forget_password:
                Intent intent = new Intent(this, GetRegisterCodeActivity.class);
                intent.putExtra("from_forget", true);
                startActivity(intent);
                animationForNew();
                break;
            case R.id.activity_login_qq_iv:
//                if (!this.isrequest) {
//                    qqAuthorization();
//                }
                break;
//            case R.id.login_sina_iv:
//                if (!this.isrequest) {
//                    this.isSinaMessage = false;
//                    this.mSsoHandler.authorize(new AuthListener());
//                }
//            case R.id.login_message_iv:
//                if (!this.isrequest) {
//                    this.isSinaMessage = true;
//                    this.mSsoHandler.registerOrLoginByMobile("\u9a8c\u8bc1\u7801\u767b\u5f55", new AuthListener());
//                }
            case R.id.activity_login_register_bt:
                startActivity(new Intent(this, GetRegisterCodeActivity.class));
                animationForNew();
                break;
            default:
        }
    }

    private void facebooklogin(String fbuid, String accesstoken, String expires, String nickname) {
//        QGHttpRequest.getInstance().faceBookLoginRequest(this, fbuid, accesstoken, expires, new C16607(this, false, this.rl_keyborad, fbuid, accesstoken, expires, nickname));
    }

    private void twitterlogin(String ttuid, String accesstoken, String accesstokensecret, String nickname) {
//        QGHttpRequest.getInstance().twitterLoginRequest(this, ttuid, accesstoken, accesstokensecret, new C16618(this, false, this.rl_keyborad, ttuid, accesstoken, accesstokensecret, nickname));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (this.mSsoHandler != null) {
//            this.mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }
    }

    private void login(String phoneNumber, String password) {
        this.isrequest = true;
        this.login_bt.setSelected(true);
        this.login_bt.setText(getResources().getString(R.string.login_ing));
        QGHttpRequest.getInstance().loginRequest(this, phoneNumber, password, new LoginHandler(this, false, this.rl_keyborad));
    }

    private void loginSuccess(TutuUsers data) {
        ActivityManager.getScreenManager().popAllActivity();
        UserAuth.getInstance().login(this, data);
        Intent mIntent = new Intent(this, TabMainActivity.class);
        mIntent.putExtra("tag", 0);
        startActivityForNew(mIntent);
        finish();
        animationForNew();
    }

    private void qqAuthorization() {
//        this.mMyApplication.mTencent.logout(this);
//        this.isrequest = true;
//        this.login_qq_iv.setSelected(true);
//        this.mMyApplication.mTencent.login((Activity) this, ChoiseHiddenModeActivity.PUBLIC_MODE, new IUiListener() {
//            public void onError(UiError arg0) {
//                LoginActivity.this.isrequest = false;
//                LoginActivity.this.login_qq_iv.setSelected(false);
//                LoginActivity.this.icon_iv.clearAnimation();
//                Toast.makeText(LoginActivity.this, arg0.errorMessage, 0).show();
//            }
//
//            public void onComplete(Object obj) {
//                LoginActivity.this.mInfo = new UserInfo(LoginActivity.this, LoginActivity.this.mMyApplication.mQQAuth.getQQToken());
//                //DebugUtils.error("qqAuthorization onComplete callback mInfo = " + LoginActivity.this.mInfo.toString());
//                JSONObject arg0 = (JSONObject) obj;
//                try {
//                    //DebugUtils.error(arg0.toString());
//                    LoginActivity.this.getQQUserInfo(arg0.getString(SocializeProtocolConstants.PROTOCOL_KEY_OPENID), arg0.getString(ShareRequestParam.REQ_PARAM_TOKEN));
//                    //DebugUtils.error(obj.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    onError(new UiError(BaseConstants.f3843b, LoginActivity.this.getResources().getString(R.string.qq_login_err), LoginActivity.this.getResources().getString(R.string.qq_login_err)));
//                }
//            }
//
//            public void onCancel() {
//                LoginActivity.this.isrequest = false;
//                LoginActivity.this.login_qq_iv.setSelected(false);
//                LoginActivity.this.icon_iv.clearAnimation();
//            }
//        });
    }

    public void getQQUserInfo(String openid, String access_token) {
//        this.mInfo.getUserInfo(new AnonymousClass11(openid, access_token));
    }

    private void qqlogin(String openid, String access_token, String nickname) {
//        QGHttpRequest.getInstance().qqLoginRequest(this, openid, access_token, new AnonymousClass12(this, false, this.rl_keyborad, openid, access_token, nickname));
    }

    private void getSinaUserinfo(boolean isMesssage) {
//        this.mUsersAPI = new UsersAPI(this, Constant.SINA_APP_KEY, this.mAccessToken);
//        long uid = Long.parseLong(this.mAccessToken.getUid());
//        String accessToken = this.mAccessToken.getToken();
//        long expiresin = this.mAccessToken.getExpiresTime();
//        this.mUsersAPI.show(uid, new AnonymousClass13(isMesssage, this.mAccessToken.getPhoneNum(), uid, accessToken, expiresin));
    }

    private void startSinaLogin(String nickname, String wbuid, String accessToken, String expiresin) {
//        QGHttpRequest.getInstance().sinaLoginRequest(this, wbuid, accessToken, expiresin, new AnonymousClass14(this, wbuid, accessToken, expiresin, nickname));
    }

    private void startSinaMessageLogin(String nickname, String phone, String wbuid, String accessToken, String expiresin) {
//        QGHttpRequest.getInstance().sinaMessageLoginRequest(this, phone, wbuid, accessToken, expiresin, new AnonymousClass15(this, wbuid, accessToken, expiresin, nickname, phone));
    }

    private void startQQLoginRegActivity(String openid, String access_token, String nickname) {
//        Bundle bundle = new Bundle();
//        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_OPENID, openid);
//        bundle.putString(ShareRequestParam.REQ_PARAM_TOKEN, access_token);
//        bundle.putString("nickname", nickname);
//        Intent intent = new Intent(this, QQLoginRegActivity.class);
//        intent.putExtras(bundle);
//        startActivityForNew(intent);
    }

    private void startFaceBookLoginRegActivity(String ttuid, String access_token, String expiresin, String nickname) {
//        Bundle bundle = new Bundle();
//        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_UID, ttuid);
//        bundle.putString(ShareRequestParam.REQ_PARAM_TOKEN, access_token);
//        bundle.putString("expiresin", expiresin);
//        bundle.putString("nickname", nickname);
//        bundle.putBoolean("from_facebook_auth", true);
//        Intent intent = new Intent(this, QQLoginRegActivity.class);
//        intent.putExtras(bundle);
//        startActivityForNew(intent);
    }

    private void startTwitterLoginRegActivity(String fbuid, String access_token, String accesstokensecret, String nickname) {
//        Bundle bundle = new Bundle();
//        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_UID, fbuid);
//        bundle.putString(ShareRequestParam.REQ_PARAM_TOKEN, access_token);
//        bundle.putString("accesstokensecret", accesstokensecret);
//        bundle.putString("nickname", nickname);
//        bundle.putBoolean("from_twitter_auth", true);
//        Intent intent = new Intent(this, QQLoginRegActivity.class);
//        intent.putExtras(bundle);
//        startActivityForNew(intent);
    }

    private void startSinaLoginRegActivity(String wbuid, String access_token, String expiresin, String nickname, boolean fromSinaMessage, String phone) {
//        Bundle bundle = new Bundle();
//        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_UID, wbuid);
//        bundle.putString(ShareRequestParam.REQ_PARAM_TOKEN, access_token);
//        bundle.putString("expiresin", expiresin);
//        bundle.putString("nickname", nickname);
//        bundle.putBoolean("from_sina_auth", true);
//        if (fromSinaMessage) {
//            bundle.putBoolean("from_sina_auth", false);
//            bundle.putBoolean("fromSinaMessage", fromSinaMessage);
//            bundle.putString(UserData.PHONE_KEY, phone);
//        }
//        Intent intent = new Intent(this, QQLoginRegActivity.class);
//        intent.putExtras(bundle);
//        startActivityForNew(intent);
    }

    public void onKeyboardStateChanged(int state) {
        switch (state) {
            case -3 /*-3*/:
                if (this.isShowKeyboard) {
                    this.isShowKeyboard = false;
                    this.login_scrollview.smoothScrollBy(0, getResources().getInteger(R.integer.login_scroll));
                }
            case -2 /*-2*/:
                this.isShowKeyboard = true;
            default:
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (4 != event.getKeyCode()) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        return true;
    }
}
