package com.gitrose.mobile;

import android.content.Context;
import android.content.SharedPreferences.Editor;
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
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
//import io.rong.message.BuildConfig;
//import org.apache.http.Header;

public class ModifyNickNameActivity extends BaseActivity implements OnClickListener {
    private ImageView backImg;
    private LinearLayout err_ll;
    private TextView err_tv;
    private Handler mHandler;
    private EditText nickNameEt;
    private RelativeLayout rl_container;

    /* renamed from: com.gitrose.mobile.ModifyNickNameActivity.2 */
    class C02912 extends Thread {
        private final /* synthetic */ TranslateAnimation val$outanimation;

        /* renamed from: com.gitrose.mobile.ModifyNickNameActivity.2.1 */
        class C02901 implements Runnable {
            private final /* synthetic */ TranslateAnimation val$outanimation;

            C02901(TranslateAnimation translateAnimation) {
                this.val$outanimation = translateAnimation;
            }

            public void run() {
                ModifyNickNameActivity.this.err_ll.startAnimation(this.val$outanimation);
//                ModifyNickNameActivity.this.err_ll.setVisibility(8);
            }
        }

        C02912(TranslateAnimation translateAnimation) {
            this.val$outanimation = translateAnimation;
        }

        public void run() {
            super.run();
            ModifyNickNameActivity.this.mHandler.postDelayed(new C02901(this.val$outanimation), 2000);
        }
    }

    /* renamed from: com.gitrose.mobile.ModifyNickNameActivity.1 */
//    class C06551 extends QGHttpHandler<String> {
//        private final /* synthetic */ String val$nickName;
//
//        C06551(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {
//            this.val$nickName = str;
//            super($anonymous0, $anonymous1, $anonymous2);
//        }
//
//        public void onGetDataSuccess(String data) {
//            MyApplication.getInstance().getUserinfo().setNickname(this.val$nickName);
//            Editor edit = ModifyNickNameActivity.this.getSharedPreferences(Constant.PREFENCES_NAME, 0).edit();
//            edit.putString(Constant.NICK_NAME_PERFERENCE, this.val$nickName);
//            edit.commit();
//            ModifyNickNameActivity.this.finish();
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            ModifyNickNameActivity.this.initAnimation("\u7f51\u7edc\u5f02\u5e38");
//            super.onFailure(statusCode, headers, responseString, throwable);
//        }
//    }
//
//    public ModifyNickNameActivity() {
//        this.mHandler = new Handler();
//    }
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(C0306R.layout.activity_modify_nickname);
//        this.backImg = (ImageView) findViewById(C0306R.id.title_tv_left);
//        this.nickNameEt = (EditText) findViewById(C0306R.id.et_modify_nickname);
//        this.err_tv = (TextView) findViewById(C0306R.id.login_err_tv);
//        this.err_ll = (LinearLayout) findViewById(C0306R.id.login_ll_tv);
//        this.rl_container = (RelativeLayout) findViewById(C0306R.id.rl_container);
//        String nickName = MyApplication.getInstance().getUserinfo().getNickname();
//        if (nickName != null) {
//            this.nickNameEt.setText(nickName);
//        }
//        this.backImg.setOnClickListener(this);
//    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C0306R.id.title_tv_left:
                finish();
            case C0306R.id.title_tv_right:
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
//        QGHttpRequest.getInstance().setUserInfo(this, nickName, null, null, null, null, null, null, new C06551(this, true, this.rl_container, nickName));
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
        new C02912(outanimation).start();
    }
}
