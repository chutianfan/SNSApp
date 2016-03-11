package com.gitrose.mobile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.ImageLoader;
//import com.tencent.android.tpush.XGIOperateCallback;
//import com.tencent.android.tpush.XGPushManager;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.auth.CookieParser;
import com.gitrose.mobile.auth.UserAuth;
import com.gitrose.mobile.base.BaseUploadActivity;
import com.gitrose.mobile.base.BaseUploadActivity.IUploadCall;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
//import com.gitrose.mobile.http.QGHttpHandlerAsyn;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.UserInfo;
import com.gitrose.mobile.picker.AreaPicker;
import com.gitrose.mobile.picker.DatePicker;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.view.BaseDialog;
import com.gitrose.mobile.view.CircleImageView;
import com.gitrose.mobile.view.TipPop;
//import com.umeng.socialize.utils.Log;
//import io.rong.message.BuildConfig;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//import org.apache.http.Header;
import org.json.JSONException;
//import shouji.gexing.framework.utils.DebugUtils;

public class AccountManagerActivity extends BaseUploadActivity implements OnClickListener, IUploadCall {
    private AreaPicker areaPicker;
    private View areaView;
    private View avatorView;
    private View birthView;
    private CircleImageView cirlceimg_modify_avator;
    private TipPop copyPop;
    private DatePicker datePicker;
    private BaseDialog dialog;
    private boolean isGoOtherActivity;
    private View logoutView;
    private boolean mIsModify;
    private RelativeLayout rl_modify_pwd;
    private View sexView;
    private TextView tv_modify_area;
    private TextView tv_modify_birth;
    private TextView tv_modify_name;
    private TextView tv_modify_sex;
    private TextView tv_modify_sign;
    private TextView tv_tutu_number;

    /* renamed from: com.gitrose.mobile.AccountManagerActivity.1 */
    class C02511 implements OnClickListener {
        C02511() {
        }

        public void onClick(View v) {
            if ("\u590d\u5236".equals(v.getTag())) {
                AccountManagerActivity.this.copyNumber();
                AccountManagerActivity.this.copyPop.dismiss();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.AccountManagerActivity.2 */
//    class C05072 implements XGIOperateCallback {
//        C05072() {
//        }
//
//        public void onSuccess(Object arg0, int arg1) {
//            Log.m1629e("registerPush----onSuccess---", "------");
//        }
//
//        public void onFail(Object arg0, int arg1, String arg2) {
//            Log.m1629e("registerPush----onFail---", "------");
//        }
//    }

    /* renamed from: com.gitrose.mobile.AccountManagerActivity.3 */
    class C06273 extends QGHttpHandler<String> {
        C06273(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(String data) {
        }
    }

    /* renamed from: com.gitrose.mobile.AccountManagerActivity.5 */
//    class C06285 extends QGHttpHandler<String> {
//        private final /* synthetic */ String val$gender;
//
//        C06285(Context $anonymous0, String str) {
//            this.val$gender = str;
//            super($anonymous0);
//        }
//
//        public void onGetDataSuccess(String data) {
//            MyApplication.getInstance().getUserinfo().setGender(this.val$gender);
//            AccountManagerActivity.this.mIsModify = true;
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            Toast.makeText(AccountManagerActivity.this, "\u545c\u545c\uff0c\u7f51\u7edc\u51fa\u73b0\u95ee\u9898\uff0c\u4fee\u6539\u5931\u8d25...", 0).show();
//            String gender = MyApplication.getInstance().getUserinfo().getGender();
//            if (gender != null) {
//                if (Constant.SYSTEM_UID.equals(gender)) {
//                    AccountManagerActivity.this.tv_modify_sex.setText("\u7537");
//                } else if ("2".equals(gender)) {
//                    AccountManagerActivity.this.tv_modify_sex.setText("\u5973");
//                } else {
//                    AccountManagerActivity.this.tv_modify_sex.setText(BuildConfig.FLAVOR);
//                }
//            }
//            super.onFailure(statusCode, headers, responseString, throwable);
//        }
//    }

    /* renamed from: com.gitrose.mobile.AccountManagerActivity.6 */
//    class C06296 extends QGHttpHandler<String> {
//        private final /* synthetic */ String val$birth;
//
//        C06296(Context $anonymous0, String str) {
//            this.val$birth = str;
//            super($anonymous0);
//        }
//
//        public void onGetDataSuccess(String data) {
//            MyApplication.getInstance().getUserinfo().setBirthday(this.val$birth);
//            AccountManagerActivity.this.mIsModify = true;
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            Toast.makeText(AccountManagerActivity.this, "\u545c\u545c\uff0c\u7f51\u7edc\u51fa\u73b0\u95ee\u9898\uff0c\u4fee\u6539\u5931\u8d25...", 0).show();
//            AccountManagerActivity.this.tv_modify_birth.setText(MyApplication.getInstance().getUserinfo().getBirthday() == null ? MyApplication.getInstance().getUserinfo().getBirthday() : BuildConfig.FLAVOR);
//            super.onFailure(statusCode, headers, responseString, throwable);
//        }
//    }

    /* renamed from: com.gitrose.mobile.AccountManagerActivity.7 */
//    class C06307 extends QGHttpHandler<String> {
//        private final /* synthetic */ String[] val$areas;
//
//        C06307(Context $anonymous0, String[] strArr) {
//            this.val$areas = strArr;
//            super($anonymous0);
//        }
//
//        public void onGetDataSuccess(String data) {
//            AccountManagerActivity.this.mIsModify = true;
//            UserInfo user = MyApplication.getInstance().getUserinfo();
//            user.setProvince(this.val$areas[0]);
//            user.setCity(this.val$areas[1]);
//            user.setArea(this.val$areas[2]);
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            Toast.makeText(AccountManagerActivity.this, "\u545c\u545c\uff0c\u7f51\u7edc\u51fa\u73b0\u95ee\u9898\uff0c\u4fee\u6539\u5931\u8d25...", 0).show();
//            AccountManagerActivity.this.tv_modify_area.setText(MyApplication.getInstance().getUserinfo().getArea() == null ? MyApplication.getInstance().getUserinfo().getArea() : BuildConfig.FLAVOR);
//            super.onFailure(statusCode, headers, responseString, throwable);
//        }
//    }

    /* renamed from: com.gitrose.mobile.AccountManagerActivity.8 */
//    class C06318 extends QGHttpHandler<UserInfo> {
//        private final /* synthetic */ String val$uid;
//
//        C06318(Context $anonymous0, String str) {
//            this.val$uid = str;
//            super($anonymous0);
//        }
//
//        public void onGetDataSuccess(UserInfo data) {
//            data.setUid(this.val$uid);
//            MyApplication.getInstance().setUserinfo(data);
//            AccountManagerActivity.this.initData();
//        }
//    }

    /* renamed from: com.gitrose.mobile.AccountManagerActivity.4 */
//    class C07404 extends QGHttpHandlerAsyn<UserInfo> {
//        C07404(Context $anonymous0) {
//            super($anonymous0);
//        }
//
//        public void onGetDataSuccess(UserInfo data) {
//            String avatarTime = data.getAvatartime();
//            ImageLoader.getInstance().displayImage(ImageUtils.getAvatarUrl(MyApplication.getInstance().getUserinfo().getUid(), avatarTime), AccountManagerActivity.this.cirlceimg_modify_avator, Constant.AVATAR_OPTIONS);
//            MyApplication.getInstance().getUserinfo().setAvatartime(data.getAvatartime());
//            Editor edit = AccountManagerActivity.this.getSharedPreferences(Constant.PREFENCES_NAME, 0).edit();
//            edit.putString(Constant.AVATAR_TIME_PERFERENCE, avatarTime);
//            edit.commit();
//            AccountManagerActivity.this.mIsModify = true;
//        }
//
//        public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
//            ImageLoader.getInstance().displayImage(ImageUtils.getAvatarUrl(MyApplication.getInstance().getUserinfo().getUid(), AccountManagerActivity.this.getSharedPreferences(Constant.PREFENCES_NAME, 0).getString(Constant.AVATAR_TIME_PERFERENCE, null)), AccountManagerActivity.this.cirlceimg_modify_avator, Constant.AVATAR_OPTIONS);
//            Toast.makeText(AccountManagerActivity.this, "\u545c\u545c\uff0c\u7f51\u7edc\u51fa\u73b0\u95ee\u9898\uff0c\u4fee\u6539\u5931\u8d25...", 0).show();
//            super.onFailure(statusCode, headers, responseBytes, throwable);
//        }
//    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AiTheme);
        setContentView(R.layout.activity_account_manager);
        ImageView backImg = (ImageView) findViewById(R.id.title_tv_left);
        this.rl_modify_pwd = (RelativeLayout) findViewById(R.id.rl_modify_pwd);
        this.cirlceimg_modify_avator = (CircleImageView) getViewById(R.id.cirlceimg_modify_avator);
        this.tv_modify_area = (TextView) getViewById(R.id.tv_modify_area);
        this.tv_modify_sign = (TextView) getViewById(R.id.tv_modify_signature);
        this.tv_modify_birth = (TextView) getViewById(R.id.tv_modify_brith);
        this.tv_modify_sex = (TextView) getViewById(R.id.tv_modify_sex);
        this.tv_modify_name = (TextView) getViewById(R.id.tv_modify_name);
        this.tv_tutu_number = (TextView) getViewById(R.id.tv_tutu_name);
        backImg.setOnClickListener(this);
        this.dialog = new BaseDialog(this);
        modifyPicture(this, true);
        initPop();
        getUserInfo();
    }

    @SuppressLint({"SimpleDateFormat"})
    public void initData() {
        TutuUsers user = MyApplication.getInstance().getUserinfo();
        if (user.getNickname() != null) {
            this.tv_modify_name.setText(user.getNickname());
        }
        String province = user.getProvince();
        String city = user.getCity();
        if (user.getArea() != null) {
            this.tv_modify_area.setText(new StringBuilder(String.valueOf(province)).append(" ").append(city).append(" ").append(user.getArea()).toString());
        }
        if (user.getSign() != null) {
            this.tv_modify_sign.setText(user.getSign());
        }
        if (user.getGender() != null) {
            if (Constant.SYSTEM_UID.equals(user.getGender())) {
                this.tv_modify_sex.setText("\u7537");
            } else if ("2".equals(user.getGender())) {
                this.tv_modify_sex.setText("\u5973");
            } else {
                this.tv_modify_sex.setText(BuildConfig.FLAVOR);
            }
        }
        String birth = user.getBirthday();
        if (birth != null) {
            Date date;
            this.tv_modify_birth.setText(birth);
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(birth);
            } catch (ParseException e) {
                date = new Date();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            this.datePicker.setCalendar(calendar);
        }
        if (Constant.SYSTEM_UID.equals(user.getIsqqlogin())) {
            this.rl_modify_pwd.setVisibility(View.GONE);
        } else {
            this.rl_modify_pwd.setVisibility(View.VISIBLE);
        }
        this.tv_tutu_number.setText(user.getUid());
        ImageLoader.getInstance().displayImage(ImageUtils.getAvatarUrl(user.getUid(), user.getAvatartime()), this.cirlceimg_modify_avator, Constant.AVATAR_OPTIONS);
    }

    private void initPop() {
        this.sexView = getLayoutInflater().inflate(R.layout.dialog_modify_sex, null);
        this.sexView.findViewById(R.id.tv_select_male).setOnClickListener(this);
        this.sexView.findViewById(R.id.tv_select_female).setOnClickListener(this);
        this.sexView.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
        this.avatorView = getLayoutInflater().inflate(R.layout.dialog_take_photo, null);
        this.avatorView.findViewById(R.id.photo_pop_tv_capture).setOnClickListener(this);
        this.avatorView.findViewById(R.id.photo_pop_tv_album).setOnClickListener(this);
        this.avatorView.findViewById(R.id.photo_pop_tv_cancel).setOnClickListener(this);
        this.birthView = getLayoutInflater().inflate(R.layout.dialog_modify_birth, null);
        this.birthView.findViewById(R.id.pop_birth_close).setOnClickListener(this);
        this.birthView.findViewById(R.id.pop_birth_submit).setOnClickListener(this);
        this.datePicker = (DatePicker) this.birthView.findViewById(R.id.timePicker);
        this.areaView = getLayoutInflater().inflate(R.layout.dialog_modify_area, null);
        this.areaView.findViewById(R.id.pop_area_close).setOnClickListener(this);
        this.areaView.findViewById(R.id.pop_area_submit).setOnClickListener(this);
        this.areaPicker = (AreaPicker) this.areaView.findViewById(R.id.areaPicker);
        this.logoutView = View.inflate(this, R.layout.dialog_base_layout, null);
        ((TextView) this.logoutView.findViewById(R.id.tv_tip_title)).setText("\u786e\u5b9a\u8981\u9000\u51faTutu\u5417\uff1f");
        this.logoutView.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
        this.logoutView.findViewById(R.id.pop_tv_submit).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                finish();
            case R.id.rl_modify_avator:
//                PlaySound.getInstance(this).toPlay(R.raw.camera);
                this.dialog.show(this.avatorView);
            case R.id.rl_modify_name:
                this.mIsModify = true;
//                PlaySound.getInstance(this).toPlay(R.raw.camera);
                startOtherActivity(ModifyNickNameActivity.class);
            case R.id.rl_tutu_number:
                showCopyPop();
            case R.id.rl_modify_sex:
                this.dialog.show(this.sexView);
            case R.id.rl_modify_brith:
//                PlaySound.getInstance(this).toPlay(R.raw.camera);
                this.dialog.show(this.birthView);
            case R.id.rl_modify_area:
//                PlaySound.getInstance(this).toPlay(R.raw.camera);
                this.dialog.show(this.areaView);
            case R.id.rl_modify_signature:
                this.mIsModify = true;
                startOtherActivity(ModifySignActivity.class);
            case R.id.rl_modify_pwd:
                startOtherActivity(ModifyPasswordActivity.class);
            case R.id.rl_logout:
                this.dialog.show(this.logoutView);
            case R.id.pop_tv_submit:
                logout();
            case R.id.pop_tv_cancel:
                this.dialog.dismiss();
            case R.id.pop_area_close:
                this.dialog.dismiss();
            case R.id.pop_area_submit:
                modifyArea(this.areaPicker.getArea());
                this.tv_modify_area.setText(this.areaPicker.getAreaName());
                this.dialog.dismiss();
            case R.id.pop_birth_close:
                this.dialog.dismiss();
            case R.id.pop_birth_submit:
                modifyBirth(this.datePicker.getDate());
                this.tv_modify_birth.setText(this.datePicker.getDate());
                this.dialog.dismiss();
            case R.id.tv_select_male:
                modifyGender(Constant.SYSTEM_UID);
                this.tv_modify_sex.setText("\u7537");
                this.dialog.dismiss();
            case R.id.tv_select_female:
                modifyGender("2");
                this.tv_modify_sex.setText("\u5973");
                this.dialog.dismiss();
            case R.id.photo_pop_tv_capture:
                this.dialog.dismiss();
                getPicFromCapture();
            case R.id.photo_pop_tv_album:
                this.dialog.dismiss();
                getPicFromContent();
            case R.id.photo_pop_tv_cancel:
                this.dialog.dismiss();
            default:
        }
    }

    private void showCopyPop() {
        this.copyPop = new TipPop(this, new String[]{"\u590d\u5236"}, new C02511());
        this.copyPop.show(this.tv_tutu_number);
    }

    @SuppressLint({"NewApi"})
    private void copyNumber() {
        String uid = MyApplication.getInstance().getUserinfo().getUid();
//        if (VERSION.SDK_INT > 11) {
//            ((ClipboardManager) getSystemService("clipboard")).setText(uid);
//        } else {
//            ((android.text.ClipboardManager) getSystemService("clipboard")).setText(uid);
//        }
        Toast.makeText(this, "\u590d\u5236Tutu\u53f7\u6210\u529f", Toast.LENGTH_LONG).show();
    }

    private void logout() {
//        XGPushManager.registerPush(this, "*", new C05072());
//        if (!(MyApplication.getInstance().getUserinfo() == null || !"0".equals(MyApplication.getInstance().getUserinfo().getIsqqlogin()) || MyApplication.getInstance().mTencent == null)) {
//            MyApplication.getInstance().mTencent.logout(MyApplication.getInstance());
//            DebugUtils.error("log out QQ");
//        }
        QGHttpRequest.getInstance().logoutRequest(this, new C06273(this));
        UserAuth.getInstance().invinvalidateUserIdentity(this);
        startActivityForNew(new Intent(this, LoginActivity.class));
        finish();
    }

    private void startOtherActivity(Class<? extends Activity> clazz) {
        startActivityForNew(new Intent(this, clazz));
    }

    public void gpuback(File file) {
        ImageLoader.getInstance().displayImage("file://" + file.getAbsolutePath(), this.cirlceimg_modify_avator, Constant.AVATAR_OPTIONS);
//        QGHttpRequest.getInstance().setAvatarRequest(this, file, new C07404(this));
    }

    private void modifyGender(String gender) {
//        QGHttpRequest.getInstance().setUserInfo(this, null, gender, null, null, null, null, null, new C06285(this, gender));
    }

    private void modifyBirth(String birth) {
//        QGHttpRequest.getInstance().setUserInfo(this, null, null, birth, null, null, null, null, new C06296(this, birth));
    }

    private void modifyArea(String[] areas) {
//        QGHttpRequest.getInstance().setUserInfo(this, null, null, null, areas[0], areas[1], areas[2], null, new C06307(this, areas));
    }

    private void getUserInfo() {
        TutuUsers user = MyApplication.getInstance().getUserinfo();
        String uid = user.getUid();
        if (user != null) {
//            QGHttpRequest.getInstance().getSelfInfoHttpRequest(this, user.getUid(), "0", "0", new C06318(this, uid));
        }
    }

    protected void onResume() {
        if (this.isGoOtherActivity) {
            initData();
        }
        this.isGoOtherActivity = true;
        super.onResume();
    }

    public void finish() {
        super.finish();
//        if (this.mIsModify && PersonalActivity.getObject() != null) {
//            PersonalActivity.getObject().needToRefresh();
//        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == 2) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String authToken = CookieParser.parseCookies2Json(MyApplication.getInstance().getCookieStore());
        outState.putSerializable("userinfo", MyApplication.getInstance().getUserinfo());
        outState.putString("authToken", authToken);
        outState.putSerializable("mCurrentPhotoFile", this.mCurrentPhotoFile);
        outState.putSerializable("mCropPhotoFile", this.mCropPhotoFile);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            TutuUsers userinfo = (TutuUsers) savedInstanceState.getSerializable("userinfo");
            String authToken = savedInstanceState.getString("authToken");
            this.mCurrentPhotoFile = (File) savedInstanceState.getSerializable("mCurrentPhotoFile");
            this.mCropPhotoFile = (File) savedInstanceState.getSerializable("mCropPhotoFile");
            MyApplication.getInstance().recoverData(CookieParser.pareseJson2Cookies(authToken), userinfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
