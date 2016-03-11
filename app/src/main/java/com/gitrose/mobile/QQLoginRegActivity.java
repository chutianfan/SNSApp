package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
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
import com.gitrose.mobile.model.UserInfo;
//import com.umeng.socialize.net.utils.SocializeProtocolConstants;
//import io.rong.message.BuildConfig;
//import org.apache.http.Header;

public class QQLoginRegActivity extends BaseActivity implements OnClickListener {
    private ImageView backImg;
    private RelativeLayout container_rl;
    private LinearLayout err_ll;
    private TextView err_tv;
    private String mAccess_token;
    private Handler mHandler;
    private String mNickname;
    private String mOpenid;
    private EditText nickNameEt;

    /* renamed from: com.gitrose.mobile.QQLoginRegActivity.2 */
    class C03052 extends Thread {
        private final /* synthetic */ TranslateAnimation val$outanimation;

        /* renamed from: com.gitrose.mobile.QQLoginRegActivity.2.1 */
        class C03041 implements Runnable {
            private final /* synthetic */ TranslateAnimation val$outanimation;

            C03041(TranslateAnimation translateAnimation) {
                this.val$outanimation = translateAnimation;
            }

            public void run() {
                QQLoginRegActivity.this.err_ll.startAnimation(this.val$outanimation);
//                QQLoginRegActivity.this.err_ll.setVisibility(8);
            }
        }

        C03052(TranslateAnimation translateAnimation) {
            this.val$outanimation = translateAnimation;
        }

        public void run() {
            super.run();
            QQLoginRegActivity.this.mHandler.postDelayed(new C03041(this.val$outanimation), 2000);
        }
    }

    /* renamed from: com.gitrose.mobile.QQLoginRegActivity.1 */
    class C06751 extends QGHttpHandler<TutuUsers> {
        C06751(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(TutuUsers data) {
            UserAuth.getInstance().login(QQLoginRegActivity.this, data);
//            QQLoginRegActivity.this.startActivity(new Intent(QQLoginRegActivity.this, HomeActivity.class));
            QQLoginRegActivity.this.finish();
            QQLoginRegActivity.this.animationForNew();
            onFinish();
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            QQLoginRegActivity.this.initAnimation(responseString);
            super.onFailure(statusCode, headers, responseString, throwable);
        }
    }

    public QQLoginRegActivity() {
        this.mHandler = new Handler();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq_reg);
//        this.mOpenid = getIntent().getExtras().getString(SocializeProtocolConstants.PROTOCOL_KEY_OPENID);
//        this.mAccess_token = getIntent().getExtras().getString(SocializeProtocolConstants.PROTOCOL_KEY_ACCESSTOKEN);
        this.mNickname = getIntent().getExtras().getString("nickname");
        this.backImg = (ImageView) findViewById(R.id.title_tv_left);
        this.nickNameEt = (EditText) findViewById(R.id.et_modify_nickname);
        this.nickNameEt.setText(this.mNickname);
        this.err_tv = (TextView) findViewById(R.id.login_err_tv);
        this.err_ll = (LinearLayout) findViewById(R.id.login_ll_tv);
        this.container_rl = (RelativeLayout) findViewById(R.id.container_rl);
        this.backImg.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                finish();
            case R.id.title_tv_right:
                if (this.nickNameEt.getText() == null || BuildConfig.FLAVOR.equals(this.nickNameEt.getText().toString())) {
                    initAnimation("\u60a8\u8fd8\u6ca1\u6709\u8f93\u5165\u6587\u5b57");
                } else if (this.nickNameEt.getText().toString().length() > 12) {
                    initAnimation("\u8bf7\u8f93\u516512\u4e2a\u5b57\u4ee5\u5185");
                } else {
                    modifyNickName(this.nickNameEt.getText().toString());
                }
            default:
        }
    }

    private void modifyNickName(String nickName) {
        QGHttpRequest.getInstance().qqLoginRegRequest(this, this.mOpenid, this.mAccess_token, nickName, new C06751(this, true, this.container_rl));
    }

    private void initAnimation(String err_text) {
//        this.err_ll.setVisibility(0);
        this.err_tv.setText(err_text);
        TranslateAnimation animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -3.0f, 1, 0.0f);
        animation.setDuration(800);
        animation.setRepeatCount(0);
        this.err_ll.startAnimation(animation);
        TranslateAnimation outanimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -0.0f, 1, -3.0f);
        outanimation.setDuration(800);
        outanimation.setRepeatCount(0);
        new C03052(outanimation).start();
    }
}
