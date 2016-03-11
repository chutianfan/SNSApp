package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.auth.UserAuth;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import org.apache.http.Header;
import shouji.gexing.framework.utils.StringUtils;

public class RegisterActivity extends BaseActivity implements OnClickListener {
    private RelativeLayout container_rl;
    private ImageView divder_iv;
    private LinearLayout err_ll;
    private TextView err_tv;
    private boolean from_forget;
    private boolean is_bind;
    private Handler mHandler;
    private String nickname;
    private EditText nickname_et;
    private String old_pass;
    private EditText old_password_et;
    private String password;
    private EditText password_et;
    private String phoneNumber;
    private Button register_bt;
    private TextView title_tv;

    /* renamed from: com.gitrose.mobile.RegisterActivity.1 */
    class C07621 extends Thread {
        private final /* synthetic */ TranslateAnimation val$outanimation;

        /* renamed from: com.gitrose.mobile.RegisterActivity.1.1 */
        class C07611 implements Runnable {
            private final /* synthetic */ TranslateAnimation val$outanimation;

            C07611(TranslateAnimation translateAnimation) {
                this.val$outanimation = translateAnimation;
            }

            public void run() {
                RegisterActivity.this.err_ll.startAnimation(this.val$outanimation);
                RegisterActivity.this.err_ll.setVisibility(View.GONE);
            }
        }

        C07621(TranslateAnimation translateAnimation) {
            this.val$outanimation = translateAnimation;
        }

        public void run() {
            super.run();
            RegisterActivity.this.mHandler.postDelayed(new C07611(this.val$outanimation), 2000);
        }
    }

    /* renamed from: com.gitrose.mobile.RegisterActivity.2 */
    class C17002 extends QGHttpHandler<String> {
        C17002(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(String data) {
            RegisterActivity.this.finish();
            Intent i = new Intent(RegisterActivity.this, BindPhoneNumberActivity.class);
            i.putExtra("phoneNumber", RegisterActivity.this.phoneNumber);
            RegisterActivity.this.startActivityForNew(i);
        }
    }

    /* renamed from: com.gitrose.mobile.RegisterActivity.3 */
    class C17013 extends QGHttpHandler<TutuUsers> {
        C17013(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(TutuUsers data) {
            RegisterActivity.this.finish();
            UserAuth.getInstance().login(RegisterActivity.this, data);
            RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, RecommendActivity.class));
            RegisterActivity.this.animationForNew();
        }

        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            RegisterActivity.this.initAnimation(responseString);
        }
    }

    /* renamed from: com.gitrose.mobile.RegisterActivity.4 */
    class C17024 extends QGHttpHandler<TutuUsers> {
        C17024(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(TutuUsers data) {
            RegisterActivity.this.finish();
            UserAuth.getInstance().login(RegisterActivity.this, data);
            Intent mIntent = new Intent(RegisterActivity.this, TabMainActivity.class);
            mIntent.putExtra("tag", 0);
            RegisterActivity.this.startActivityForNew(mIntent);
            RegisterActivity.this.animationForNew();
        }

        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            RegisterActivity.this.initAnimation(responseString);
        }
    }

    public RegisterActivity() {
        this.from_forget = false;
        this.mHandler = new Handler();
        this.is_bind = false;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.phoneNumber = getIntent().getStringExtra("phonenumber");
        this.from_forget = getIntent().getBooleanExtra("from_forget", false);
        this.is_bind = getIntent().getBooleanExtra("isBind", false);
        initUI();
    }

    private void initUI() {
        this.title_tv = (TextView) findViewById(R.id.title_tv_text);
        findViewById(R.id.title_tv_left).setOnClickListener(this);
        this.err_ll = (LinearLayout) findViewById(R.id.register_err_ll);
        this.err_tv = (TextView) findViewById(R.id.register_err_tv);
        this.container_rl = (RelativeLayout) findViewById(R.id.container_rl);
        this.nickname_et = (EditText) findViewById(R.id.activity_register_et_nickname);
        this.old_password_et = (EditText) findViewById(R.id.activity_register_et_oldpass);
        this.password_et = (EditText) findViewById(R.id.activity_register_et_password);
        this.register_bt = (Button) findViewById(R.id.activity_register_bt);
        this.divder_iv = (ImageView) findViewById(R.id.register_divider_iv);
        this.register_bt.setOnClickListener(this);
        if (this.from_forget) {
            this.title_tv.setText(getResources().getString(R.string.reset_password));
            this.nickname_et.setVisibility(View.GONE);
            this.divder_iv.setVisibility(View.GONE);
            this.old_password_et.setVisibility(View.VISIBLE);
            this.password_et.setVisibility(View.VISIBLE);
            this.password_et.setHint(getResources().getString(R.string.sure_password));
            this.register_bt.setText(getResources().getString(R.string.reset));
        } else if (this.is_bind) {
            this.title_tv.setText(getResources().getString(R.string.set_password));
            this.nickname_et.setVisibility(View.GONE);
            this.divder_iv.setVisibility(View.GONE);
            this.old_password_et.setVisibility(View.VISIBLE);
            this.password_et.setVisibility(View.VISIBLE);
            this.password_et.setHint(getResources().getString(R.string.sure_password));
            this.register_bt.setText(getResources().getString(R.string.reset));
            findViewById(R.id.register_title).setVisibility(View.VISIBLE);
        } else {
            this.title_tv.setText(getResources().getString(R.string.set_password));
            this.nickname_et.setHint(getResources().getString(R.string.set_nickname));
            this.divder_iv.setVisibility(View.VISIBLE);
            this.nickname_et.setVisibility(View.VISIBLE);
            this.old_password_et.setVisibility(View.VISIBLE);
            this.old_password_et.setHint(getResources().getString(R.string.set_password_less_6));
            this.password_et.setHint(getResources().getString(R.string.sure_password));
            this.password_et.setVisibility(View.VISIBLE);
            this.register_bt.setText(getResources().getString(R.string.finish));
        }
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
        new C07621(outanimation).start();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                finish();
                animationForOld();
                break;
            case R.id.activity_register_bt:
                break;
            default:
                return;
        }
        this.nickname = this.nickname_et.getText().toString();
        this.password = this.password_et.getText().toString();
        this.old_pass = this.old_password_et.getText().toString();
        if (this.from_forget || this.is_bind) {
            if (this.password.length() < 6) {
                initAnimation(getResources().getString(R.string.password_no_less_6));
            } else if (!this.old_pass.equals(this.password)) {
                initAnimation(getResources().getString(R.string.password_diffrent));
            } else if (this.from_forget) {
                resetPassword(this.password);
            } else {
                bindPhoneNumber(this.password);
            }
        } else if (StringUtils.isEmpty(this.nickname) || !checkNickName(this.nickname)) {
            initAnimation(getResources().getString(R.string.nickname_less_2));
        } else if (StringUtils.isEmpty(this.password) || this.password.length() < 6) {
            initAnimation(getResources().getString(R.string.password_no_less_6));
        } else if (this.old_pass.equals(this.password)) {
            register(this.password, this.nickname);
        } else {
            initAnimation(getResources().getString(R.string.password_diffrent));
        }
    }

    private void bindPhoneNumber(String password) {
        QGHttpRequest.getInstance().confirmBindPhoneRequest(this, this.phoneNumber, password, new C17002(this, true, this.container_rl));
    }

    private boolean checkNickName(String nickname) {
        int len = 0;
        char[] chars = nickname.toCharArray();
        for (char s : chars) {
            if (s <= '\u4e00' || s >= '\u9fff') {
                len++;
            } else {
                len += 2;
            }
        }
        if (len < 4 || len > 24) {
            return false;
        }
        return true;
    }

    private void register(String password, String nickname) {
//        QGHttpRequest.getInstance().registerRequest(this, this.phoneNumber, password, nickname, new C17013(this, true, this.container_rl));
//        QGHttpRequest.getInstance().newChangePWRequest(this, "test", password, new newChangePWHandler(this, true, this.container_rl));
    }

    private void resetPassword(String password) {
        QGHttpRequest.getInstance().resetPasswordRequest(this, this.phoneNumber, password, new C17024(this, true, this.container_rl));
    }

    class newChangePWHandler extends QGHttpHandler<String>{

        public newChangePWHandler(Context context, boolean isShowLoading, ViewGroup container) {
            super(context, isShowLoading, container);
        }

        @Override
        public void onGetDataSuccess(String strEmailAddress) {

            RegisterActivity.this.UserInfoEdit();
        }
    }

    private void UserInfoEdit()
    {
//        QGHttpRequest.getInstance().userInfoChangeRequest(this, nickname, "", "", "", new userInfoChangeHandler(this, true, this.container_rl));
    }

    class userInfoChangeHandler extends QGHttpHandler<String>{

        public userInfoChangeHandler(Context context, boolean isShowLoading, ViewGroup container) {
            super(context, isShowLoading, container);
        }

        @Override
        public void onGetDataSuccess(String s) {

        }
    }
}
