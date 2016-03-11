package com.gitrose.mobile;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.constant.Constant;

public class BindPhoneNumberActivity extends BaseActivity implements OnClickListener {
    private String phoneNumber;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        findViewById(R.id.title_tv_left).setOnClickListener(this);
        this.phoneNumber = getIntent().getStringExtra("phoneNumber");
        Button btn = (Button) findViewById(R.id.btn_bind_phone);
        if (this.phoneNumber == null) {
            ((ImageView) findViewById(R.id.img_bind_phone_icon)).setImageResource(R.drawable.icon_unbind_phone_finish);
            ((TextView) findViewById(R.id.tv_bind_phone_title)).setText(getResources().getString(R.string.unbind_phone_title));
            ((TextView) findViewById(R.id.tv_bind_phone_text)).setText(getResources().getString(R.string.unbind_phone_text));
            btn.setText("\u7ed1\u5b9a\u624b\u673a\u53f7");
        } else {
            ((ImageView) findViewById(R.id.img_bind_phone_icon)).setImageResource(R.drawable.icon_bind_phone_finish);
            ((TextView) findViewById(R.id.tv_bind_phone_title)).setText("\u7ed1\u5b9a\u624b\u673a\u53f7: +86" + this.phoneNumber);
            ((TextView) findViewById(R.id.tv_bind_phone_text)).setText(getResources().getString(R.string.bind_phone_text));
            btn.setText("\u5b8c\u6210");
        }
        btn.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.title_tv_left) {
            finish();
        } else if (v.getId() != R.id.btn_bind_phone) {
        } else {
            if (this.phoneNumber == null) {
                Intent i = new Intent(this, GetRegisterCodeActivity.class);
                i.putExtra("isBind", true);
                startActivityForNew(i);
                superFinish();
                return;
            }
            MyApplication.getInstance().getUserinfo().setIsbind_phone(1);
            Editor edit = getSharedPreferences(Constant.PREFENCES_NAME, 0).edit();
            edit.putString(Constant.IS_BIND_PHONE, Constant.SYSTEM_UID);
            edit.commit();
            superFinish();
        }
    }
}
