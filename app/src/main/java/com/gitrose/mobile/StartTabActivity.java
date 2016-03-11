package com.gitrose.mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.application.ActivityManager;
import com.gitrose.mobile.auth.UserAuth;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;

import qalsdk.BaseConstants;

public class StartTabActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout m_facebook;
    private LinearLayout m_kakaotalk;
    private LinearLayout m_titlebarRegister;
    private LinearLayout m_titlebarLogin;
    private LinearLayout m_mainFrame;
    private LinearLayout m_frameRegister;
    private LinearLayout m_frameLogin;
    private TextView m_titleRegister;
    private TextView m_titleLogin;
    private EditText m_reg_emailAddress;
    private EditText m_emailAddress;
    private EditText m_password;
    private Button m_login;
    private TextView m_getPassword;
    private ImageView m_goRegister;
    private boolean isLogin = false;

    private ImageView phone_iv_delete;
    private ImageView pass_iv_delete;

    private TextView err_tv;
    private LinearLayout err_ll;

    private ImageView m_icon;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_tab);

        initView();
    }

    public StartTabActivity()
    {
        this.mHandler = new Handler();
    }

    private void initView()
    {
        isLogin = getIntent().getBooleanExtra("isLogin", false);

        m_mainFrame = (LinearLayout)findViewById(R.id.ll_frame);

        m_facebook = (LinearLayout)findViewById(R.id.ll_facebook);
        m_facebook.setOnClickListener(this);

        m_kakaotalk = (LinearLayout)findViewById(R.id.ll_kakao);
        m_kakaotalk.setOnClickListener(this);

        m_titlebarRegister = (LinearLayout)findViewById(R.id.ll_register);
        m_titlebarRegister.setOnClickListener(this);

        m_titlebarLogin = (LinearLayout)findViewById(R.id.ll_login);
        m_titlebarLogin.setOnClickListener(this);

        m_titleRegister = (TextView)findViewById(R.id.txt_register);
        m_titleLogin = (TextView)findViewById(R.id.txt_login);

        this.err_ll = (LinearLayout) findViewById(R.id.login_ll_tv);

        m_icon = (ImageView)findViewById(R.id.img_icon);

        this.err_tv = (TextView) findViewById(R.id.login_err_tv);
        this.err_ll = (LinearLayout) findViewById(R.id.login_ll_tv);

        drawLayout();
    }

    private void drawLayout(){

        drawButton();
        m_mainFrame.removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(!isLogin){
            m_frameRegister = (LinearLayout)inflater.inflate(R.layout.layout_register, null);
            m_mainFrame.addView(m_frameRegister);
            m_reg_emailAddress = (EditText)m_mainFrame.findViewById(R.id.activity_login_et_phone);

            m_goRegister = (ImageView)m_mainFrame.findViewById(R.id.activity_register_email_iv_next);
            m_goRegister.setOnClickListener(this);

        } else {
            m_frameLogin = (LinearLayout)inflater.inflate(R.layout.layout_login, null);
            m_mainFrame.addView(m_frameLogin);
            m_emailAddress = (EditText)m_mainFrame.findViewById(R.id.activity_login_et_phone);
            m_password = (EditText)m_mainFrame.findViewById(R.id.activity_login_et_password);

            m_login = (Button)m_mainFrame.findViewById(R.id.activity_login_bt);
            m_login.setOnClickListener(this);

            m_getPassword = (TextView)m_mainFrame.findViewById(R.id.activity_login_tv_forget_password);
            m_getPassword.setOnClickListener(this);

            phone_iv_delete = (ImageView) m_mainFrame.findViewById(R.id.activity_login_phone_iv_delete);
            pass_iv_delete = (ImageView) m_mainFrame.findViewById(R.id.activity_login_pass_iv_delete);

            this.phone_iv_delete.setOnClickListener(this);
            this.pass_iv_delete.setOnClickListener(this);

            this.m_emailAddress.setText("1959756878@qq.com");
            this.m_password.setText("qwertyuiop");

            initet();
        }
    }

    private void initet()
    {
        this.m_emailAddress.setOnFocusChangeListener(new emailFocusChange());
        this.m_password.setOnFocusChangeListener(new passFocusChange());
        this.m_emailAddress.addTextChangedListener(new emailTextChange());
        this.m_password.addTextChangedListener(new passTextChange());
    }

    class emailTextChange implements TextWatcher {
        emailTextChange() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (m_emailAddress.getText().toString().length() == 0) {
                phone_iv_delete.setVisibility(View.GONE);
            } else if (m_emailAddress.getText().toString().length() > 0 && m_emailAddress.hasFocus()) {
                phone_iv_delete.setVisibility(View.VISIBLE);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class passTextChange implements TextWatcher {
        passTextChange() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (m_password.getText().toString().length() == 0) {
                pass_iv_delete.setVisibility(View.GONE);
            } else if (m_password.getText().toString().length() > 0 && m_password.hasFocus()) {
                pass_iv_delete.setVisibility(View.VISIBLE);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class emailFocusChange implements View.OnFocusChangeListener {
        emailFocusChange() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus && m_emailAddress.length() > 0) {
                phone_iv_delete.setVisibility(View.VISIBLE);
            } else if (!hasFocus || m_emailAddress.length() == 0) {
                phone_iv_delete.setVisibility(View.GONE);
            }
        }
    }

    class passFocusChange implements View.OnFocusChangeListener {
        passFocusChange() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus && m_password.length() > 0) {
                pass_iv_delete.setVisibility(View.VISIBLE);
            } else if (!hasFocus || m_password.length() == 0) {
                pass_iv_delete.setVisibility(View.GONE);
            }
        }
    }

    private void drawButton(){

        m_titlebarRegister.setBackgroundResource(R.drawable.btn_tab_01);
        m_titlebarLogin.setBackgroundResource(R.drawable.btn_tab_01);

        m_titleRegister.setTextColor(Color.WHITE);
        m_titleLogin.setTextColor(Color.WHITE);

        if(isLogin){
            m_titlebarLogin.setBackgroundResource(R.drawable.btn_tab_02);
            m_titleLogin.setTextColor(getResources().getColor(R.color.text_pink_color));
        } else {
            m_titlebarRegister.setBackgroundResource(R.drawable.btn_tab_02);
            m_titleRegister.setTextColor(getResources().getColor(R.color.text_pink_color));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_phone_iv_delete:
                this.m_emailAddress.setText("");
                break;
            case R.id.activity_login_pass_iv_delete:
                this.m_password.setText("");
                break;
            case R.id.ll_register:
                isLogin = false;
                drawLayout();
                break;
            case R.id.ll_login:
                isLogin = true;
                drawLayout();
                break;
            case R.id.activity_login_bt :
                goLogin();
                break;
            case R.id.activity_login_tv_forget_password :
                Intent intent = new Intent(this, GetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_register_email_iv_next :
                goRegister();
                break;
        }
    }

    private void goLogin(){

        if (!evaluateAvail(m_emailAddress)) return;
        this.m_login.setEnabled(false);
        this.m_login.setText("로그인중...");
        QGHttpRequest.getInstance().LoginRequest(this, this.m_emailAddress.getText().toString(), this.m_password.getText().toString(), new LoginResult(this));
    }

    class LoginResult extends QGHttpHandler<TutuUsers>
    {

        public LoginResult(Context context) {
            super(context);
        }

        @Override
        public void onGetDataSuccess(TutuUsers tutuUsers) {
            StartTabActivity.this.loginSuccess(tutuUsers);
            onFinish();
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            StartTabActivity.this.initAnimation(responseString);
            onFinish();
        }

        public void onFinish() {
            super.onFinish();
            StartTabActivity.this.m_login.setSelected(false);
            StartTabActivity.this.m_login.setText("로그인");
        }
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

    private void initAnimation(String err_text) {
        this.m_login.setEnabled(true);
        this.err_ll.setVisibility(View.VISIBLE);
        this.err_tv.setText(err_text);
        TranslateAnimation animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -3.0f, 1, 0.0f);
        animation.setDuration(800);
        animation.setRepeatCount(0);
        this.err_ll.startAnimation(animation);
        TranslateAnimation outanimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -0.0f, 1, -3.0f);
        outanimation.setDuration(800);
        outanimation.setRepeatCount(0);
        new ErrorAnimation(outanimation).start();
    }

    class ErrorAnimation extends Thread {
        private final TranslateAnimation val$outanimation;

        class subAnimation implements Runnable {
            private final TranslateAnimation val$outanimation;

            subAnimation(TranslateAnimation translateAnimation) {
                this.val$outanimation = translateAnimation;
            }

            public void run() {
                StartTabActivity.this.err_ll.startAnimation(this.val$outanimation);
                StartTabActivity.this.err_ll.setVisibility(View.GONE);
            }
        }

        ErrorAnimation(TranslateAnimation translateAnimation) {
            this.val$outanimation = translateAnimation;
        }

        public void run() {
            super.run();
            StartTabActivity.this.mHandler.postDelayed(new subAnimation(this.val$outanimation), 2000);
        }
    }

    private void goRegister(){

        if (!evaluateAvail(m_reg_emailAddress)) return;
//        this.m_goRegister.setEnabled(false);
        QGHttpRequest.getInstance().checkEmailRequest(this, this.m_reg_emailAddress.getText().toString(), new CheckEmailAddress(this, false, null));
    }

    public class CheckEmailAddress extends QGHttpHandler<String> {
        @Override
        public void onGetDataSuccess(String strEmail) {
            CheckEmailSuccess();
        }

        CheckEmailAddress(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onFinish() {
            super.onFinish();
        }
    }

    private void CheckEmailSuccess()
    {
//        this.m_goRegister.setEnabled(true);
        Intent intent = new Intent(this, RegisterActivity1.class);
        intent.putExtra("email", this.m_reg_emailAddress.getText().toString());
        startActivity(intent);

        animationForNew();
    }

    private boolean evaluateAvail(EditText editText){

        String emailAddress = editText.getText().toString();

        if (emailAddress.isEmpty()){
            editText.setText("");
            editText.setHint("이메일주소를 입력하십시오.");
            editText.requestFocus();
            editText.startAnimation(shakeAnimation(4));
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            editText.setText("");
            editText.setHint("이메일주소가 정확치 않습니다.");
            editText.requestFocus();
            editText.startAnimation(shakeAnimation(4));
            return false;
        }

        return true;
    }
}
