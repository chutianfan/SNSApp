package com.gitrose.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gitrose.mobile.base.BaseActivity;

public class StartActivity extends BaseActivity implements View.OnClickListener {

    private TextView txtRegister;
    private TextView txtLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        InitView();
    }

    private void InitView()
    {
        txtRegister = (TextView)findViewById(R.id.activity_register_bt);
        txtLogin = (TextView)findViewById(R.id.activity_login_bt);

        txtRegister.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(StartActivity.this, StartTabActivity.class);

        switch(v.getId()){

            case R.id.activity_login_bt:
                intent.putExtra("isLogin", true);
                break;
            case R.id.activity_register_bt:
                intent.putExtra("isLogin", false);
                break;
        }

        startActivity(intent);
        animationForNew();
        finish();
    }
}
