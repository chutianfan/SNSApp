package com.gitrose.mobile;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
//import io.rong.message.BuildConfig;
//import org.apache.http.Header;

public class ModifySignActivity extends BaseActivity implements OnClickListener {
    private ImageView backImg;
    private LinearLayout err_ll;
    private TextView err_tv;
    private Handler mHandler;
    private TextView mSignLengthHintTv;
    private RelativeLayout rl_container;
    private EditText signEt;

    /* renamed from: com.gitrose.mobile.ModifySignActivity.2 */
    class C02932 extends Thread {
        private final /* synthetic */ TranslateAnimation val$outanimation;

        /* renamed from: com.gitrose.mobile.ModifySignActivity.2.1 */
        class C02921 implements Runnable {
            private final /* synthetic */ TranslateAnimation val$outanimation;

            C02921(TranslateAnimation translateAnimation) {
                this.val$outanimation = translateAnimation;
            }

            public void run() {
                ModifySignActivity.this.err_ll.startAnimation(this.val$outanimation);
                ModifySignActivity.this.err_ll.setVisibility(View.GONE);
            }
        }

        C02932(TranslateAnimation translateAnimation) {
            this.val$outanimation = translateAnimation;
        }

        public void run() {
            super.run();
            ModifySignActivity.this.mHandler.postDelayed(new C02921(this.val$outanimation), 2000);
        }
    }

    /* renamed from: com.gitrose.mobile.ModifySignActivity.1 */
//    class C06571 extends QGHttpHandler<String> {
//        private final /* synthetic */ String val$sign;
//
//        C06571(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {
//            this.val$sign = str;
//            super($anonymous0, $anonymous1, $anonymous2);
//        }
//
//        public void onGetDataSuccess(String data) {
//            Toast.makeText(ModifySignActivity.this, "\u4fee\u6539\u6210\u529f\uff01", 0).show();
//            MyApplication.getInstance().getUserinfo().setSign(this.val$sign);
//            ModifySignActivity.this.finish();
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            ModifySignActivity.this.initAnimation("\u7f51\u7edc\u5f02\u5e38");
//            super.onFailure(statusCode, headers, responseString, throwable);
//        }
//    }

    public ModifySignActivity() {
        this.mHandler = new Handler();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_signature);
        this.backImg = (ImageView) findViewById(R.id.title_tv_left);
        this.signEt = (EditText) findViewById(R.id.et_modify_sign);
        this.mSignLengthHintTv = (TextView) findViewById(R.id.tv_sign_length_hint);
        this.mSignLengthHintTv.setVisibility(View.INVISIBLE);
        this.rl_container = (RelativeLayout) findViewById(R.id.rl_container);
        String sign = MyApplication.getInstance().getUserinfo().getSign();
        if (sign != null) {
            this.signEt.setText(sign);
        }
        this.signEt.requestFocus();
        this.err_tv = (TextView) findViewById(R.id.login_err_tv);
        this.err_ll = (LinearLayout) findViewById(R.id.login_ll_tv);
        this.backImg.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                finish();
            case R.id.title_tv_right:
                if (this.signEt.getText() == null || BuildConfig.FLAVOR.equals(this.signEt.getText().toString())) {
                    modifySign(BuildConfig.FLAVOR);
                } else {
                    modifySign(this.signEt.getText().toString());
                }
            default:
        }
    }

    private void modifySign(String sign) {
        //QGHttpRequest.getInstance().setUserInfo(this, null, null, null, null, null, null, sign, new C06571(this, true, this.rl_container, sign));
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
        new C02932(outanimation).start();
    }
}
