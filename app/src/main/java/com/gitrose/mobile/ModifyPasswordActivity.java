package com.gitrose.mobile;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
//import shouji.gexing.framework.utils.StringUtils;

public class ModifyPasswordActivity extends BaseActivity implements OnClickListener {
    private ImageView backImg;
    private EditText confirm_pass_et;
    private RelativeLayout container_rl;
    private EditText init_pass_et;
    private EditText new_pass_et;
    private TextView titleTv;

    /* renamed from: com.gitrose.mobile.ModifyPasswordActivity.1 */
    class C06561 extends QGHttpHandler<String> {
        C06561(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(String data) {
            Toast.makeText(ModifyPasswordActivity.this, "\u4fee\u6539\u6210\u529f", Toast.LENGTH_LONG).show();
            ModifyPasswordActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        this.titleTv = (TextView) findViewById(R.id.title_tv_text);
        this.backImg = (ImageView) findViewById(R.id.title_tv_left);
        this.titleTv.setText("\u4fee\u6539\u5bc6\u7801");
        this.backImg.setOnClickListener(this);
        this.init_pass_et = (EditText) findViewById(R.id.et_init_pwd);
        this.new_pass_et = (EditText) findViewById(R.id.et_new_pwd);
        this.confirm_pass_et = (EditText) findViewById(R.id.et_confirm_pwd);
        this.container_rl = (RelativeLayout) findViewById(R.id.container_rl);
        findViewById(R.id.modify_pass_sure_bt).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                finish();
            case R.id.modify_pass_sure_bt:
                String oldpass = this.init_pass_et.getText().toString();
                String newpassword = this.new_pass_et.getText().toString();
                String confirmpassword = this.confirm_pass_et.getText().toString();
//                if (StringUtils.isEmpty(oldpass) || StringUtils.isEmpty(newpassword) || StringUtils.isEmpty(confirmpassword)) {
//                    Toast.makeText(this, "\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a", 0).show();
//                } else if (newpassword.length() < 6) {
//                    Toast.makeText(this, "\u5bc6\u7801\u4e0d\u7684\u5c11\u4e8e\u516d\u4f4d", 0).show();
//                } else if (newpassword.equals(confirmpassword)) {
//                    updatePassword(newpassword, oldpass);
//                } else {
//                    Toast.makeText(this, "\u4e24\u6b21\u5bc6\u7801\u8f93\u5165\u4e0d\u4e00\u81f4", 0).show();
//                }
            default:
        }
    }

    private void updatePassword(String newpassword, String oldpassword) {
        QGHttpRequest.getInstance().updatePasswordRequest(this, newpassword, oldpassword, new C06561(this, true, this.container_rl));
    }
}
