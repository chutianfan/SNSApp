package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.model.UserInfo;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.view.CompatViewPager;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
//import com.umeng.socialize.media.UMImage;
//import com.umeng.socialize.sso.QZoneSsoHandler;
//import com.umeng.socialize.sso.UMQQSsoHandler;
//import com.umeng.socialize.weixin.controller.UMWXHandler;
//import io.rong.message.BuildConfig;
import java.util.ArrayList;
import java.util.List;
//import shouji.gexing.framework.utils.BoundInstallApk;

public class SearchActivity extends BaseActivity {
    public static final String DOWN = "down";
    public static final String UP = "up";
    private static final String LEN = "20";
    private Bitmap bitmap;
    private String content;
    private String image_url;
    UMSocialService mController;
    private List<UserInfo> mFriendsList;
    private TextView mKeyEditText;
    private TextView mMyTutu;
    private TutuUsers mPersonalInfo;
    private OnEditorActionListener searchAction;
    private String title;
    private String topicId;
    private String uid;

    /* renamed from: com.gitrose.mobile.SearchActivity.1 */
    class C03231 implements OnEditorActionListener {
        C03231() {
        }

        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            if (actionId == 4 || (event != null && event.getKeyCode() == 66)) {
//                SearchActivity.this.findViewById(R.id.search).performClick();
            }
            return false;
        }
    }

    /* renamed from: com.gitrose.mobile.SearchActivity.2 */
    class C06902 implements SnsPostListener {
        C06902() {
        }

        public void onStart() {
        }

        public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
            if (eCode != CompatViewPager.SNAP_VELOCITY) {
                String eMsg = BuildConfig.FLAVOR;
//                if (eCode == BoundInstallApk.INSTALL_PARSE_FAILED_BAD_MANIFEST) {
//                    String str = "\u6ca1\u6709\u6388\u6743";
//                }
            }
        }
    }

    public SearchActivity() {
        this.bitmap = null;
        this.searchAction = new C03231();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.mPersonalInfo = MyApplication.getInstance().getUserinfo();
        this.image_url = new StringBuilder(ConstantURL.AVATAR_DOMAIN).append(this.mPersonalInfo.getUid()).append("/").append(this.mPersonalInfo.getAvatartime()).append(Constant.HEAD_BIG_SIZE).toString();
        try {
            this.bitmap = BitmapFactory.decodeFile(ImageUtils.getImageFileNameByUrl(this, this.image_url));
        } catch (Exception e) {
        }
        initView();
        setShare();
    }

    private void initView() {
//        this.mMyTutu = (TextView) findViewById(R.id.my_tutu);
        if (this.mPersonalInfo == null || this.mPersonalInfo.getUid() == null) {
            this.mMyTutu.setText(R.string.search_my_tutu_null);
        } else {
            this.mMyTutu.setText(getResources().getString(R.string.search_my_tutu, new Object[]{this.mPersonalInfo.getUid()}));
        }
        this.mKeyEditText = (TextView) findViewById(R.id.search_with);
        this.mKeyEditText.setOnEditorActionListener(this.searchAction);
        this.mFriendsList = new ArrayList();
    }

    public void onActionBar(View view) {
        switch (view.getId()) {
            case R.id.search_back:
                doBack();
            default:
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_top:
                startRealSearchActivity();
            case R.id.search_contacts_friends:
                if (MyApplication.getInstance().getUserinfo().getIsbind_phone() == 0) {
                    startBindPhoneActivity();
                } else {
                    startContactsFriendsActivity();
                }
            case R.id.search_qq_friends:
                share(SHARE_MEDIA.QQ);
            case R.id.search_wechat_friends:
                share(SHARE_MEDIA.WEIXIN);
            default:
        }
    }

    private void setShare() {
//        this.mController = UMServiceFactory.getUMSocialService("com.umeng.share");
//        String title = "\u9080\u8bf7\u4f60\u52a0\u5165Tutu";
//        String shareContent = "\u6211\u5728Tutu\uff0c\u6211\u7684Tutu\u53f7\u662f*****\uff0c90\u540e\u300100\u540e\u90fd\u5728\u73a9\uff01\u5feb\u6765\u52a0\u5165\u5427\uff01";
//        if (!(this.mPersonalInfo == null || this.mPersonalInfo.getUid() == null)) {
//            shareContent = "\u6211\u5728Tutu\uff0c\u6211\u7684Tutu\u53f7\u662f" + this.mPersonalInfo.getUid() + "\uff0c90\u540e\u300100\u540e\u90fd\u5728\u73a9\uff01\u5feb\u6765\u52a0\u5165\u5427\uff01";
//            this.mController.setShareMedia(new UMImage((Context) this, (int) R.drawable.ic_share));
//        }
//        String url = new StringBuilder(ConstantURL.INVITE_URL).append(this.mPersonalInfo.getUid()).toString();
//        this.mController.setShareContent(shareContent);
//        String appID = "wx8fa99b405996914a";
//        String AppSecret = "779948ea2c933e2e1a6d65b48b680763";
//        UMWXHandler wxHandler = new UMWXHandler(this, appID, AppSecret);
//        wxHandler.setTitle(title);
//        wxHandler.setTargetUrl(url);
//        wxHandler.addToSocialSDK();
//        UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, AppSecret);
//        wxCircleHandler.setToCircle(true);
//        wxCircleHandler.setTitle(title);
//        wxCircleHandler.setTargetUrl(url);
//        wxCircleHandler.addToSocialSDK();
//        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, Constant.QQ_APP_ID, "Z6sykUsn8G08ujo2");
//        qqSsoHandler.setTitle(title);
//        qqSsoHandler.setTargetUrl(url);
//        qqSsoHandler.addToSocialSDK();
//        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, Constant.QQ_APP_ID, "Z6sykUsn8G08ujo2");
//        qZoneSsoHandler.setTargetUrl(url);
//        qZoneSsoHandler.addToSocialSDK();
    }

    private void share(SHARE_MEDIA platform) {
        this.mController.postShare(this, platform, new C06902());
    }

    private void startRealSearchActivity() {
        Intent intent = new Intent();
        intent.setClass(this, RealSearchActivity.class);
        intent.putExtras(new Bundle());
        startActivityForNew(intent);
    }

    private void startContactsFriendsActivity() {
        Intent intent = new Intent();
        intent.setClass(this, ContactsFriendsActivity.class);
        intent.putExtras(new Bundle());
        startActivityForNew(intent);
    }

    private void startBindPhoneActivity() {
        Intent intent = new Intent();
        intent.setClass(this, BindPhoneNumberActivity.class);
        intent.putExtras(new Bundle());
        startActivityForNew(intent);
    }

    private void doBack() {
        finish();
    }
}
