package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.utils.EmailUtil;

import qalsdk.BaseConstants;
import shouji.gexing.framework.utils.StringUtils;
import shouji.gexing.framework.utils.TelUtil;

public class GetRegisterCodeActivity extends BaseActivity implements OnClickListener {
    private CheckBox cb_register_checkbox;
    private RelativeLayout container_rl;
    private boolean from_forget;
    private boolean is_bind;
    private String phoneNumber;
    private EditText phone_et;
    boolean phone_num_change;
    private TextView register_bt;
    private TextView title_tv;

    /* renamed from: com.gitrose.mobile.GetRegisterCodeActivity.1 */
    class C06801 implements TextWatcher {
        C06801() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String phone = GetRegisterCodeActivity.this.phone_et.getText().toString().replaceAll(" ", BaseConstants.ah);
            if (phone.length() == 11 && GetRegisterCodeActivity.this.phone_num_change) {
                GetRegisterCodeActivity.this.phone_num_change = false;
                String new_phone_1 = phone.substring(0, 3);
                String new_phone_2 = phone.substring(3, 7);
                GetRegisterCodeActivity.this.phone_et.setText(new StringBuilder(String.valueOf(new_phone_1)).append(" ").append(new_phone_2).append(" ").append(phone.substring(7, 11)).toString());
                GetRegisterCodeActivity.this.phone_et.setSelection(GetRegisterCodeActivity.this.phone_et.getText().toString().length());
            } else if (phone.length() < 11) {
                GetRegisterCodeActivity.this.phone_num_change = true;
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.gitrose.mobile.GetRegisterCodeActivity.2 */
    class C06812 implements OnCheckedChangeListener {
        C06812() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                GetRegisterCodeActivity.this.register_bt.setClickable(true);
            } else {
                GetRegisterCodeActivity.this.register_bt.setClickable(false);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.GetRegisterCodeActivity.3 */
    class C16513 extends QGHttpHandler<String> {
        private final /* synthetic */ String val$phoneNumber;

        C16513(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {

            super($anonymous0, $anonymous1, $anonymous2);
            this.val$phoneNumber = str;

        }

        public void onGetDataSuccess(String data) {
            GetRegisterCodeActivity.this.finish();
            Intent intent = new Intent(GetRegisterCodeActivity.this, CheckRegisterCodeActivity.class);
            intent.putExtra("phonenumber", this.val$phoneNumber);
            intent.putExtra("isBind", GetRegisterCodeActivity.this.is_bind);
            GetRegisterCodeActivity.this.startActivity(intent);
            GetRegisterCodeActivity.this.animationForNew();
        }
    }

    /* renamed from: com.gitrose.mobile.GetRegisterCodeActivity.4 */
    class RegisterCodeHandler extends QGHttpHandler<String> {
        private final /* synthetic */ String strEmailAddress;

        RegisterCodeHandler(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {

            super($anonymous0, $anonymous1, $anonymous2);
            this.strEmailAddress = str;

        }

        public void onGetDataSuccess(String data) {
            GetRegisterCodeActivity.this.finish();
            Intent intent = new Intent(GetRegisterCodeActivity.this, CheckRegisterCodeActivity.class);
            intent.putExtra("phonenumber", this.strEmailAddress);
            GetRegisterCodeActivity.this.startActivity(intent);
            GetRegisterCodeActivity.this.animationForNew();
        }
    }

    /* renamed from: com.gitrose.mobile.GetRegisterCodeActivity.5 */
    class C16535 extends QGHttpHandler<String> {
        private final /* synthetic */ String val$phoneNumber;

        C16535(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {

            super($anonymous0, $anonymous1, $anonymous2);
            this.val$phoneNumber = str;

        }

        public void onGetDataSuccess(String data) {
            GetRegisterCodeActivity.this.finish();
            Intent intent = new Intent(GetRegisterCodeActivity.this, CheckRegisterCodeActivity.class);
            intent.putExtra("phonenumber", this.val$phoneNumber);
            intent.putExtra("from_forget", GetRegisterCodeActivity.this.from_forget);
            GetRegisterCodeActivity.this.startActivity(intent);
            GetRegisterCodeActivity.this.animationForNew();
        }
    }

    public GetRegisterCodeActivity() {
        this.from_forget = false;
        this.is_bind = false;
        this.phone_num_change = true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_get_code);
        this.from_forget = getIntent().getBooleanExtra("from_forget", false);
        this.is_bind = getIntent().getBooleanExtra("isBind", false);
        initUI();
    }

    private void initUI() {
        this.title_tv = (TextView) findViewById(R.id.title_tv_text);
        findViewById(R.id.title_tv_left).setOnClickListener(this);
        this.container_rl = (RelativeLayout) findViewById(R.id.container_rl);
        this.phone_et = (EditText) findViewById(R.id.activity_register_get_code_et_phone);
        this.register_bt = (TextView) findViewById(R.id.activity_register_get_code_next);
        this.register_bt.setOnClickListener(this);
        this.title_tv.setText(getResources().getString(R.string.phone_verify));
        if (this.is_bind) {
            ((TextView) findViewById(R.id.register_get_code_phone_title)).setText(getResources().getString(R.string.bind_phone_valite_title));
        }
//        this.phone_et.addTextChangedListener(new C06801());
        this.cb_register_checkbox = (CheckBox) findViewById(R.id.cb_register_checkbox);
        findViewById(R.id.tv_register_xieyi).setOnClickListener(this);
        if (this.cb_register_checkbox.isChecked()) {
            this.register_bt.setClickable(true);
        } else {
            this.register_bt.setClickable(false);
        }
        this.cb_register_checkbox.setOnCheckedChangeListener(new C06812());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                finish();
                animationForOld();
                break;
            case R.id.activity_register_get_code_next:
                break;
            case R.id.tv_register_xieyi:
                Intent i = new Intent(this, WebViewActivity.class);
                i.putExtra("web_url", ConstantURL.AGREEMENT_URL);
                i.putExtra("agree", true);
                startActivityForNew(i);
                return;
            default:
                return;
        }

        this.phoneNumber = this.phone_et.getText().toString().replace(" ", BaseConstants.ah);
        if (StringUtils.isEmpty(this.phoneNumber) || !EmailUtil.isEmail(this.phoneNumber)) {
            this.phone_et.startAnimation(shakeAnimation(4));
        } else if (this.from_forget) {
            getForgetCode(this.phoneNumber);
        } else if (this.is_bind) {
            getBinderCode(this.phoneNumber);
        } else {
            getRegisterCode(this.phoneNumber);
        }
    }

    private void getBinderCode(String phoneNumber) {
        QGHttpRequest.getInstance().checkPhoneCanBindRequest(this, phoneNumber, new C16513(this, true, this.container_rl, phoneNumber));
    }

    private void getRegisterCode(String phoneNumber) {
        QGHttpRequest.getInstance().getRegisterVerfityCodeRequest(this, phoneNumber, new RegisterCodeHandler(this, true, this.container_rl, phoneNumber));
    }

    private void getForgetCode(String phoneNumber) {
        QGHttpRequest.getInstance().getForgetVerfityCodeRequest(this, phoneNumber, new C16535(this, true, this.container_rl, phoneNumber));
    }
}
