package com.gitrose.mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.TextView;

import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.application.ActivityManager;
import com.gitrose.mobile.auth.UserAuth;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;

public class ReadyActivity extends BaseActivity implements View.OnClickListener {

    private TextView m_txtStart;
    private String m_strEmail;
    private String m_strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready);

        m_strEmail = getIntent().getStringExtra("email");
        m_strPassword = getIntent().getStringExtra("password");

        initView();
    }

    private void initView()
    {
        m_txtStart = (TextView)findViewById(R.id.activity_login_bt);
        m_txtStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_login_bt:
                goLogin();
                break;
        }
    }

    private void goLogin()
    {
        QGHttpRequest.getInstance().LoginRequest(this, m_strEmail, m_strPassword, new LoginResult(this));
    }

    class LoginResult extends QGHttpHandler<TutuUsers>
    {

        public LoginResult(Context context) {
            super(context);
        }

        @Override
        public void onGetDataSuccess(TutuUsers tutuUsers) {
            loginSuccess(tutuUsers);
            onFinish();
        }

        public void onFinish() {
            super.onFinish();
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
}
