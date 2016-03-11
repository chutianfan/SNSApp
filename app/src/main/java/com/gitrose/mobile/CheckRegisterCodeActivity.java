package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.umeng.socialize.common.SocializeConstants;

import org.apache.http.Header;

import shouji.gexing.framework.utils.StringUtils;

public class CheckRegisterCodeActivity extends BaseActivity implements OnClickListener {
    private EditText code_et;
    private RelativeLayout container_rl;
    private boolean from_forget;
    private boolean is_bind;
    private TextView mes_tips_tv;
    private Button next_bt;
    private String phoneNumber;
    private TextView reset_send_bt;
    CountDownTimer timer;
    private TextView title_tv;

    /* renamed from: com.gitrose.mobile.CheckRegisterCodeActivity.1 */
    class C06151 extends CountDownTimer {
        C06151(long $anonymous0, long $anonymous1) {
            super($anonymous0, $anonymous1);
        }

        public void onTick(long millisUntilFinished) {
            CheckRegisterCodeActivity.this.reset_send_bt.setText(new StringBuilder(String.valueOf(CheckRegisterCodeActivity.this.getResources().getString(R.string.resend))).append(SocializeConstants.OP_OPEN_PAREN).append(millisUntilFinished / 1000).append(SocializeConstants.OP_CLOSE_PAREN).toString());
            CheckRegisterCodeActivity.this.reset_send_bt.setTextColor(CheckRegisterCodeActivity.this.getResources().getColor(R.color.home_release_time_color));
            CheckRegisterCodeActivity.this.reset_send_bt.setOnClickListener(null);
        }

        public void onFinish() {
            CheckRegisterCodeActivity.this.reset_send_bt.setText(CheckRegisterCodeActivity.this.getResources().getString(R.string.resend));
            CheckRegisterCodeActivity.this.reset_send_bt.setTextColor(CheckRegisterCodeActivity.this.getResources().getColor(R.color.action_bar_bg));
            CheckRegisterCodeActivity.this.reset_send_bt.setOnClickListener(CheckRegisterCodeActivity.this);
        }
    }

    /* renamed from: com.gitrose.mobile.CheckRegisterCodeActivity.2 */
    class C16202 extends QGHttpHandler<String> {
        C16202(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(String data) {
            CheckRegisterCodeActivity.this.timer.start();
        }
    }

    /* renamed from: com.gitrose.mobile.CheckRegisterCodeActivity.3 */
    class C16213 extends QGHttpHandler<String> {
        C16213(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(String data) {
            CheckRegisterCodeActivity.this.timer.start();
        }
    }

    /* renamed from: com.gitrose.mobile.CheckRegisterCodeActivity.4 */
    class CheckRegisterHandler extends QGHttpHandler<String> {
        private final /* synthetic */ String val$phoneNumber;

        CheckRegisterHandler(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {

            super($anonymous0, $anonymous1, $anonymous2);
            this.val$phoneNumber = str;

        }

        public void onGetDataSuccess(String data) {
            CheckRegisterCodeActivity.this.finish();
            Intent intent = new Intent(CheckRegisterCodeActivity.this, RegisterActivity.class);
            intent.putExtra("phonenumber", this.val$phoneNumber);
            CheckRegisterCodeActivity.this.startActivity(intent);
            CheckRegisterCodeActivity.this.animationForNew();
        }
    }

    /* renamed from: com.gitrose.mobile.CheckRegisterCodeActivity.5 */
    class C16235 extends QGHttpHandler<String> {
        private final /* synthetic */ String val$phoneNumber;

        C16235(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {

            super($anonymous0, $anonymous1, $anonymous2);
            this.val$phoneNumber = str;

        }

        public void onGetDataSuccess(String data) {
            CheckRegisterCodeActivity.this.finish();
            Intent intent = new Intent(CheckRegisterCodeActivity.this, RegisterActivity.class);
            intent.putExtra("phonenumber", this.val$phoneNumber);
            intent.putExtra("from_forget", CheckRegisterCodeActivity.this.from_forget);
            CheckRegisterCodeActivity.this.startActivity(intent);
            CheckRegisterCodeActivity.this.animationForNew();
        }
    }

    /* renamed from: com.gitrose.mobile.CheckRegisterCodeActivity.6 */
    class C16246 extends QGHttpHandler<String> {
        private final /* synthetic */ String val$phoneNumber;

        C16246(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {

            super($anonymous0, $anonymous1, $anonymous2);
            this.val$phoneNumber = str;

        }

        public void onGetDataSuccess(String data) {
            CheckRegisterCodeActivity.this.finish();
            Intent intent = new Intent(CheckRegisterCodeActivity.this, RegisterActivity.class);
            intent.putExtra("phonenumber", this.val$phoneNumber);
            intent.putExtra("isBind", CheckRegisterCodeActivity.this.is_bind);
            CheckRegisterCodeActivity.this.startActivity(intent);
            CheckRegisterCodeActivity.this.animationForNew();
        }
    }

    public CheckRegisterCodeActivity() {
        this.from_forget = false;
        this.is_bind = false;
        this.timer = new C06151(60000, 1000);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_check_code);
        this.phoneNumber = getIntent().getStringExtra("phonenumber");
        this.from_forget = getIntent().getBooleanExtra("from_forget", false);
        this.is_bind = getIntent().getBooleanExtra("isBind", false);
        Log.e("from_forget", this.from_forget + "---------");
        initUI();
//        this.timer.start();
    }

    private void initUI() {
        this.title_tv = (TextView) findViewById(R.id.title_tv_text);
        findViewById(R.id.title_tv_left).setOnClickListener(this);
        this.title_tv.setText(getResources().getString(R.string.verfity_code));
        this.container_rl = (RelativeLayout) findViewById(R.id.container_rl);
        this.mes_tips_tv = (TextView) findViewById(R.id.register_check_code_tv_sms);
        this.code_et = (EditText) findViewById(R.id.register_check_code_et_code);
        this.reset_send_bt = (TextView) findViewById(R.id.register_check_code_bt_send);
        this.next_bt = (Button) findViewById(R.id.register_check_code_bt_check);
        this.reset_send_bt.setOnClickListener(this);
        this.next_bt.setOnClickListener(this);
        this.mes_tips_tv.setText(new StringBuilder(String.valueOf(getResources().getString(R.string.phone_code))).append(this.phoneNumber).append(getResources().getString(R.string.receive_message)).toString());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                finish();
                animationForOld();
            case R.id.register_check_code_bt_send:
                getRegisterCode(this.phoneNumber);
            case R.id.register_check_code_bt_check:
                String code = this.code_et.getText().toString();
                if (StringUtils.isEmpty(code)) {
                    this.code_et.startAnimation(shakeAnimation(4));
                } else if (this.from_forget) {
                    checkForgetCode(code, this.phoneNumber);
                } else if (this.is_bind) {
                    checkBindCode(code, this.phoneNumber);
                } else {
                    checkRegisterCode(code, this.phoneNumber);
                }
            default:
        }
    }

    private void getRegisterCode(String phoneNumber) {
        if (this.is_bind) {
            QGHttpRequest.getInstance().getPhoneBindVerifyCodeRequest(this, phoneNumber, new C16213(this, true, this.container_rl));
        } else {
            QGHttpRequest.getInstance().getRegisterVerfityCodeRequest(this, phoneNumber, new C16202(this, true, this.container_rl));
        }
    }

    private void checkRegisterCode(String code, String phoneNumber) {
        QGHttpRequest.getInstance().checkVerfityCodeRequest(this, phoneNumber, code, new CheckRegisterHandler(this, true, this.container_rl, phoneNumber));
    }

    private void checkForgetCode(String code, String phoneNumber) {
        QGHttpRequest.getInstance().checkForgetVerfityCodeRequest(this, phoneNumber, code, new C16235(this, true, this.container_rl, phoneNumber));
    }

    private void checkBindCode(String code, String phoneNumber) {
        QGHttpRequest.getInstance().checkPhoneBindVerifyCodeRequest(this, phoneNumber, code, new C16246(this, true, this.container_rl, phoneNumber));
    }

    protected void onPause() {
        super.onPause();
        if (this.timer != null) {
            this.timer.cancel();
        }
    }
}
