package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.FriendsList;
import com.gitrose.mobile.model.UserInfo;
//import io.rong.message.BuildConfig;
import java.io.Serializable;
import java.util.List;
//import org.apache.http.Header;
//import shouji.gexing.framework.utils.ToastUtil;

public class RealSearchActivity extends BaseActivity {
    private RelativeLayout container_rl;
    private List<UserInfo> mFriendsList;
    private EditText mKeyWord;
    private TextView mShowKeyWork;
    private OnEditorActionListener searchAction;
    private TextWatcher textChangeListener;

    /* renamed from: com.gitrose.mobile.RealSearchActivity.1 */
    class C03161 implements TextWatcher {
        C03161() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().trim().equals(BuildConfig.FLAVOR)) {
//                RealSearchActivity.this.findViewById(R.id.show_key_work).setVisibility(8);
                return;
            }
//            RealSearchActivity.this.findViewById(R.id.show_key_work).setVisibility(0);
            RealSearchActivity.this.mShowKeyWork.setText(s);
        }

        public void beforeTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.gitrose.mobile.RealSearchActivity.2 */
    class C03172 implements OnEditorActionListener {
        C03172() {
        }

        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            if (actionId == 4 || (event != null && event.getKeyCode() == 66)) {
                RealSearchActivity.this.findViewById(R.id.real_search).performClick();
            }
            return false;
        }
    }

    /* renamed from: com.gitrose.mobile.RealSearchActivity.3 */
    class C06783 extends QGHttpHandler<FriendsList> {
        C06783(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onFinish() {
            super.onFinish();
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        public void onGetDataSuccess(FriendsList data) {
            if (data != null && data.getList() != null) {
                if (data.getList().size() == 1) {
                    RealSearchActivity.this.startPersonalActivity(((TutuUsers) data.getList().get(0)).getUid());
                } else {
                    RealSearchActivity.this.startSearchDataActivity(RealSearchActivity.this.mKeyWord.getText().toString(), data.getList());
                }
            }
        }
    }

    public RealSearchActivity() {
        this.textChangeListener = new C03161();
        this.searchAction = new C03172();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_search);
        initView();
    }

    private void initView() {
        this.container_rl = (RelativeLayout) findViewById(R.id.main_pull_refresh_view);
        this.mKeyWord = (EditText) findViewById(R.id.real_search_with);
        this.mShowKeyWork = (TextView) findViewById(R.id.show_real_search_with);
        this.mKeyWord.setOnEditorActionListener(this.searchAction);
        this.mKeyWord.addTextChangedListener(this.textChangeListener);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_key_work:
            case R.id.show_real_search:
                if (this.mKeyWord.getText().toString().equals(BuildConfig.FLAVOR)) {
//                    ToastUtil.toastShort(getApplicationContext(), "\u8bf7\u5148\u8f93\u5165Tutu\u53f7", 2);
                } else {
//                    getSearchUsers(this.mKeyWord.getText().toString(), null, "50", HomeActivity.UP, true);
                }
            default:
        }
    }

    public void onActionBar(View view) {
        switch (view.getId()) {
            case R.id.real_search_back:
                finish();
            case R.id.real_search:
                if (this.mKeyWord.getText().toString().equals(BuildConfig.FLAVOR)) {
//                    ToastUtil.toastShort(getApplicationContext(), "\u8bf7\u5148\u8f93\u5165Tutu\u53f7", 2);
                } else {
//                    getSearchUsers(this.mKeyWord.getText().toString(), null, "50", HomeActivity.UP, true);
                }
            case R.id.key_word_clear:
                this.mKeyWord.setText(BuildConfig.FLAVOR);
            default:
        }
    }

    private void startSearchDataActivity(String keyword, List<TutuUsers> userlist) {
        if (keyword.equals(BuildConfig.FLAVOR)) {
//            ToastUtil.toastShort(getApplicationContext(), "\u8bf7\u5148\u8f93\u5165Tutu\u53f7", 2);
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, SearchDataActivity.class);
        intent.putExtra("key_word", keyword);
        intent.putExtra("friends_list", (Serializable) userlist);
        startActivityForNew(intent);
    }

    private void getSearchUsers(String keyword, String startuid, String len, String direction, boolean isloading) {
        QGHttpRequest.getInstance().getSearchRequest(this, keyword, startuid, len, direction, new C06783(this, isloading, this.container_rl));
    }

    private void startPersonalActivity(String user_id) {
//        Intent intent = new Intent();
//        intent.setClass(this, PersonalActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString(PersonalActivity.PERSONAL_USER_ID, user_id);
//        intent.putExtras(bundle);
//        startActivityForNew(intent);
    }
}
