package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.view.SwitchButton;
import com.gitrose.mobile.view.SwitchButton.OnCheckedChangeListener;
//import com.umeng.socialize.net.utils.SocializeProtocolConstants;
//import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class RemarkUserActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {
    private SwitchButton mBlockPrivateBtn;
    private SwitchButton mBlockTopicBtn;
    private boolean mIsModify;
    private String mNickName;
    private EditText mRemarkEt;
    private String mUid;

    /* renamed from: com.gitrose.mobile.RemarkUserActivity.1 */
    class C03201 implements TextWatcher {
        C03201() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            if (RemarkUserActivity.this.mNickName == null || !RemarkUserActivity.this.mNickName.equals(RemarkUserActivity.this.mRemarkEt)) {
                RemarkUserActivity.this.mIsModify = true;
            }
        }
    }

    /* renamed from: com.gitrose.mobile.RemarkUserActivity.2 */
    class C06822 extends QGHttpHandler<String> {
        C06822(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(String data) {
            if (FriendsActivity.getObject() != null) {
                FriendsActivity.getObject().needToRefresh();
            }
            Intent intent = new Intent();
            intent.setAction(Constant.BLOCK_PRIVATE_ACTION);
//            intent.putExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID, RemarkUserActivity.this.mUid);
            RemarkUserActivity.this.sendBroadcast(intent);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            RemarkUserActivity.this.mBlockPrivateBtn.setChecked(false);
//            Toast.makeText(RemarkUserActivity.this, "\u545c\u545c\uff0c\u5931\u8d25\u4e86\u3002", 0).show();
            super.onFailure(statusCode, headers, responseString, throwable);
        }
    }

    /* renamed from: com.gitrose.mobile.RemarkUserActivity.3 */
    class C06833 extends QGHttpHandler<String> {
        C06833(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(String data) {
            if (FriendsActivity.getObject() != null) {
                FriendsActivity.getObject().needToRefresh();
            }
            Intent intent = new Intent();
            intent.setAction(Constant.UNBLOCK_PRIVATE_ACTION);
//            intent.putExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID, RemarkUserActivity.this.mUid);
            RemarkUserActivity.this.sendBroadcast(intent);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            RemarkUserActivity.this.mBlockPrivateBtn.setChecked(true);
//            Toast.makeText(RemarkUserActivity.this, "\u545c\u545c\uff0c\u5931\u8d25\u4e86\u3002", 0).show();
            super.onFailure(statusCode, headers, responseString, throwable);
        }
    }

    /* renamed from: com.gitrose.mobile.RemarkUserActivity.4 */
    class C06844 extends QGHttpHandler<String> {
        C06844(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(String data) {
            Intent intent = new Intent();
            intent.setAction(Constant.BLOCK_TOPIC_ACTION);
//            intent.putExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID, RemarkUserActivity.this.mUid);
            RemarkUserActivity.this.sendBroadcast(intent);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            RemarkUserActivity.this.mBlockTopicBtn.setChecked(false);
//            Toast.makeText(RemarkUserActivity.this, "\u545c\u545c\uff0c\u5931\u8d25\u4e86\u3002", 0).show();
            super.onFailure(statusCode, headers, responseString, throwable);
        }
    }

    /* renamed from: com.gitrose.mobile.RemarkUserActivity.5 */
    class C06855 extends QGHttpHandler<String> {
        C06855(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(String data) {
            Intent intent = new Intent();
            intent.setAction(Constant.UNBLOCK_TOPIC_ACTION);
//            intent.putExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID, RemarkUserActivity.this.mUid);
            RemarkUserActivity.this.sendBroadcast(intent);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            RemarkUserActivity.this.mBlockTopicBtn.setChecked(true);
//            Toast.makeText(RemarkUserActivity.this, "\u545c\u545c\uff0c\u5931\u8d25\u4e86\u3002", 0).show();
            super.onFailure(statusCode, headers, responseString, throwable);
        }
    }

    /* renamed from: com.gitrose.mobile.RemarkUserActivity.6 */
//    class C06866 extends QGHttpHandler<String> {
//        private final /* synthetic */ Intent val$i;
//
//        C06866(Context $anonymous0, Intent intent) {
//            this.val$i = intent;
//            super($anonymous0);
//        }
//
//        public void onGetDataSuccess(String data) {
//            String remark;
//            try {
//                remark = new JSONObject(data).getString("remark");
//            } catch (JSONException e) {
//                remark = RemarkUserActivity.this.mRemarkEt.getText().toString();
//            }
//            this.val$i.setAction(Constant.MODIFY_MARKNAME_ACTION);
//            this.val$i.putExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID, RemarkUserActivity.this.mUid);
//            this.val$i.putExtra("remark", remark);
//            RemarkUserActivity.this.sendBroadcast(this.val$i);
//            RemarkUserActivity.this.setResult(2, this.val$i);
//            RemarkUserActivity.this.finish();
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            Toast.makeText(RemarkUserActivity.this, "\u4fee\u6539\u5907\u6ce8\u5931\u8d25\u5443...", 0).show();
//            RemarkUserActivity.this.setResult(2, this.val$i);
//            super.onFailure(statusCode, headers, responseString, throwable);
//        }
//    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark);
//        this.mUid = getIntent().getStringExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID);
        this.mNickName = getIntent().getStringExtra("remark");
        initView();
    }

    private void initView() {
        boolean z = true;
        ((ImageView) findViewById(R.id.title_tv_left)).setOnClickListener(this);
        this.mRemarkEt = (EditText) findViewById(R.id.et_remark_name);
        this.mBlockPrivateBtn = (SwitchButton) findViewById(R.id.sbtn_block_user_private);
        this.mBlockTopicBtn = (SwitchButton) findViewById(R.id.sbtn_block_user_topic);
        this.mBlockPrivateBtn.setChecked(getIntent().getIntExtra("messageBolck", 0) == 1);
        SwitchButton switchButton = this.mBlockTopicBtn;
        if (getIntent().getIntExtra("topicBolck", 0) != 1) {
            z = false;
        }
        switchButton.setChecked(z);
        if (this.mNickName != null) {
            this.mRemarkEt.setText(this.mNickName);
            this.mRemarkEt.setSelection(this.mNickName.length());
        }
        this.mBlockPrivateBtn.setOnCheckedChangeListener(this);
        this.mBlockTopicBtn.setOnCheckedChangeListener(this);
        this.mRemarkEt.addTextChangedListener(new C03201());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                submitRemark();
            default:
        }
    }

    public void onCheckedChanged(SwitchButton switchButton, boolean isChecked) {
        if (switchButton == this.mBlockPrivateBtn) {
            if (isChecked) {
                QGHttpRequest.getInstance().blockMessageRequest(this, this.mUid, new C06822(this));
            } else {
                QGHttpRequest.getInstance().unblockMessageRequest(this, this.mUid, new C06833(this));
            }
        } else if (isChecked) {
            QGHttpRequest.getInstance().blockUserTopic(this, this.mUid, new C06844(this));
        } else {
            QGHttpRequest.getInstance().unblockUserTopic(this, this.mUid, new C06855(this));
        }
    }

    public void submitRemark() {
        int i = 1;
        Intent i2 = new Intent();
        i2.putExtra("messageBolck", this.mBlockPrivateBtn.isChecked() ? 1 : 0);
        String str = "topicBolck";
        if (!this.mBlockTopicBtn.isChecked()) {
            i = 0;
        }
        i2.putExtra(str, i);
//        if (this.mIsModify) {
//            QGHttpRequest.getInstance().setRemarkRequest(this, this.mUid, this.mRemarkEt.getText().toString(), new C06866(this, i2));
//            return;
//        }
        setResult(2, i2);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            submitRemark();
        }
        return false;
    }
}
