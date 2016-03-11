package com.gitrose.mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;

public class ChoiseHiddenModeActivity extends BaseActivity {
    public static final String FRIEND_MODE = "friend";
    public static final String PRIVATE_MODE = "none";
    public static final String PUBLIC_MODE = "all";
    private View mChoiceFriendModeImg;
    private View mChoicePrivateModeImg;
    private View mChoicePublicModeImg;
    private String mode;
    private SharedPreferences sp;

    /* renamed from: com.gitrose.mobile.ChoiseHiddenModeActivity.1 */
//    class C06411 extends QGHttpHandler<String> {
//        private final /* synthetic */ String val$mode;
//
//        C06411(Context $anonymous0, String str) {
//            this.val$mode = str;
//            super($anonymous0);
//        }
//
//        public void onGetDataSuccess(String data) {
//            Editor edit = ChoiseHiddenModeActivity.this.sp.edit();
//            edit.putString(Constant.LOCATION_STATUS, this.val$mode);
//            edit.commit();
//        }
//    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_hidden_mode);
        this.mChoicePublicModeImg = (View) getViewById(R.id.img_choice_public_mode);
        this.mChoiceFriendModeImg = (View) getViewById(R.id.img_choice_friend_mode);
        this.mChoicePrivateModeImg = (View) getViewById(R.id.img_choice_private_mode);
        getMode();
        setMode(this.mode);
    }

    private void getMode() {
        this.sp = getSharedPreferences(Constant.PREFENCES_NAME, 0);
        this.mode = this.sp.getString(Constant.LOCATION_STATUS, null);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                finish();
            case R.id.rl_public_mode:
                setMode(PUBLIC_MODE);
                uploadMode(PUBLIC_MODE);
            case R.id.rl_friend_mode:
                setMode(FRIEND_MODE);
                uploadMode(FRIEND_MODE);
            case R.id.rl_private_mode:
                setMode(PRIVATE_MODE);
                uploadMode(PRIVATE_MODE);
            default:
        }
    }

    public void setMode(String mode) {
//        if (PRIVATE_MODE.equals(mode)) {
//            this.mChoicePublicModeImg.setVisibility(4);
//            this.mChoicePrivateModeImg.setVisibility(0);
//            this.mChoiceFriendModeImg.setVisibility(4);
//        } else if (FRIEND_MODE.equals(mode)) {
//            this.mChoicePublicModeImg.setVisibility(4);
//            this.mChoicePrivateModeImg.setVisibility(4);
//            this.mChoiceFriendModeImg.setVisibility(0);
//        } else {
//            this.mChoicePublicModeImg.setVisibility(0);
//            this.mChoicePrivateModeImg.setVisibility(4);
//            this.mChoiceFriendModeImg.setVisibility(4);
//        }
    }

    final void uploadMode(String mode) {
//        QGHttpRequest.getInstance().setUserInfo(this, mode, new C06411(this, mode));
    }
}
