package com.gitrose.mobile;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
//import com.tencent.connect.common.Constants;
//import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.application.ActivityManager;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.application.MyContext;
import com.gitrose.mobile.avsdk.control.Util;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
//import com.gitrose.mobile.model.MessageNum;
//import com.gitrose.mobile.model.PublishLiveModel;
//import com.gitrose.mobile.model.UploadTopic;
import com.gitrose.mobile.model.MessageNum;
import com.gitrose.mobile.model.PublishLiveModel;
import com.gitrose.mobile.model.UploadTopic;
import com.gitrose.mobile.view.AnimationTabHost;
import com.gitrose.mobile.view.BaseDialog;
//import com.umeng.analytics.MobclickAgent;
//import com.umeng.update.UmengUpdateAgent;
import qalsdk.BaseConstants;
//import shouji.gexing.framework.utils.StringUtils;

public class TabMainActivity extends TabActivity implements OnClickListener {
    private static TabMainActivity mainActivity;
    private int cur_index;
    private BaseDialog dialog;
    private ImageView home_chat_point;
    private ImageView home_my_point;
    private ImageView home_nearby;
    private ImageView home_news_point;
    private Intent intent;
    boolean isnews;
    private View loginView;
    private long mExitTime;
    private AnimationTabHost tabHost;
    private int tag;
    private ImageView tv_chat;
    private ImageView tv_home;
    private ImageView tv_my;
    private ImageView tv_news;
    private String update_url;
    public View user_guide_bottom;
    private View mTakePicDialogView;
    private BaseDialog mDialog;

    /* renamed from: com.gitrose.mobile.TabMainActivity.3 */
    class C07913 implements DialogInterface.OnClickListener {
        C07913() {
        }

        public void onClick(DialogInterface dialog, int whichButton) {
            TabMainActivity.this.finish();
        }
    }

    /* renamed from: com.gitrose.mobile.TabMainActivity.4 */
    class C07924 implements DialogInterface.OnClickListener {
        C07924() {
        }

        public void onClick(DialogInterface dialog, int whichButton) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(TabMainActivity.this.update_url));
            TabMainActivity.this.startActivity(intent);
            TabMainActivity.this.finish();
        }
    }

    /* renamed from: com.gitrose.mobile.TabMainActivity.5 */
//    class C07935 implements OnKeyListener {
//        C07935() {
//        }
//
//        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
//            switch (i) {
//                case PersonalNewActivity.WORK_LIST_9 /*4*/:
//                    TabMainActivity.this.finish();
//                    return true;
//                default:
//                    return false;
//            }
//        }
//    }

    /* renamed from: com.gitrose.mobile.TabMainActivity.1 */
    class C17531 extends QGHttpHandler<PublishLiveModel> {
        C17531(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(PublishLiveModel data) {
            if (data != null) {
//                TopicInfo topic_info = data.getTopicinfo();
//                Intent intent = new Intent(TabMainActivity.this, AvActivity.class);
//                intent.putExtra("topicinfo", topic_info);
//                intent.putExtra("enterRoom", true);
//                TabMainActivity.this.startActivity(intent);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.TabMainActivity.2 */
    class C17542 extends QGHttpHandler<MessageNum> {
        C17542(Context $anonymous0, boolean $anonymous1) {
            super($anonymous0, $anonymous1);
        }

        public void onGetDataSuccess(MessageNum data) {
            TabMainActivity.this.changeNewPoint(data.getNewtipscount(), data.getNewfanscount(), data.getNewhottopiccount(), data.getNewfollowtopiccount(), data.getNewfollowhtcount(), data.getNewfollowpoicount());
            TabMainActivity.this.showUnReadPoint();
        }
    }

    public TabMainActivity() {
        this.intent = null;
        this.cur_index = 0;
        this.mExitTime = 0;
        this.update_url = BaseConstants.ah;
        this.tag = 0;
        this.isnews = false;
    }

    public static TabMainActivity getInstance() {
        return mainActivity;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_main_activity);
        this.user_guide_bottom = findViewById(R.id.user_guide_bottom);
        mainActivity = this;
        ActivityManager.getScreenManager().pushActivity(this);
        this.tabHost = (AnimationTabHost) getTabHost();

        TabSpec tabSpecHome = this.tabHost.newTabSpec("0");
        tabSpecHome.setIndicator(getLayoutInflater().inflate(R.layout.tab_indicator, null));
        this.intent = new Intent(this, HomeFragmentActivity.class);
        tabSpecHome.setContent(this.intent);
        this.tabHost.addTab(tabSpecHome);

        TabSpec tabSpecSearch = this.tabHost.newTabSpec("1");
        tabSpecSearch.setIndicator(getLayoutInflater().inflate(R.layout.tab_indicator, null));
        this.intent = new Intent(this, SquareActivity.class);
        tabSpecSearch.setContent(this.intent);
        this.tabHost.addTab(tabSpecSearch);

        TabSpec tabSpecCamera = this.tabHost.newTabSpec("2");
        tabSpecCamera.setIndicator(getLayoutInflater().inflate(R.layout.tab_indicator, null));
        this.intent = new Intent(this, TakeCameraActivity.class);
        tabSpecCamera.setContent(this.intent);
        this.tabHost.addTab(tabSpecCamera);

        TabSpec tabSpecMove = this.tabHost.newTabSpec("3");
        tabSpecMove.setIndicator(getLayoutInflater().inflate(R.layout.tab_indicator, null));
        this.intent = new Intent(this, HomeFragmentActivity.class);
        tabSpecMove.setContent(this.intent);
        this.tabHost.addTab(tabSpecMove);

        TabSpec tabSpecMyPage = this.tabHost.newTabSpec("4");
        tabSpecMyPage.setIndicator(getLayoutInflater().inflate(R.layout.tab_indicator, null));
        this.intent = new Intent(this, PersonalNewActivity.class);
        this.intent.putExtra("isFromHome", true);
        if (MyApplication.getInstance().isLogin()) {
            TutuUsers user = MyApplication.getInstance().getUserinfo();
            if (!(user == null || user.getUid() == null)) {
                this.intent.putExtra("userid", user.getUid());
            }
        }
        tabSpecMyPage.setContent(this.intent);
        this.tabHost.addTab(tabSpecMyPage);

//        TabSpec tabSpecNear = this.tabHost.newTabSpec("1");
//        tabSpecNear.setIndicator(getLayoutInflater().inflate(R.layout.tab_indicator, null));
//        this.intent = new Intent(this, SquareActivity.class);
//        tabSpecNear.setContent(this.intent);
//        this.tabHost.addTab(tabSpecNear);

//        TabSpec tabSpecChat = this.tabHost.newTabSpec("2");
//        tabSpecChat.setIndicator(getLayoutInflater().inflate(R.layout.tab_indicator, null));
//        this.intent = new Intent(this, RCConversationActivity.class);
//        tabSpecChat.setContent(this.intent);
//        this.tabHost.addTab(tabSpecChat);

//        TabSpec tabSpecNews = this.tabHost.newTabSpec("2");
//        tabSpecNews.setIndicator(getLayoutInflater().inflate(R.layout.tab_indicator, null));
//        this.intent = new Intent(this, NewsListActivty.class);
//        tabSpecNews.setContent(this.intent);
//        this.tabHost.addTab(tabSpecNews);

//        TabSpec tabSpecMy = this.tabHost.newTabSpec("4");
//        tabSpecMy.setIndicator(getLayoutInflater().inflate(R.layout.tab_indicator, null));
//        this.intent = new Intent(this, PersonalNewActivity.class);
//        this.intent.putExtra("isFromHome", true);
//        if (MyApplication.getInstance().isLogin()) {
//            TutuUsers user = MyApplication.getInstance().getUserinfo();
//            if (!(user == null || user.getUid() == null)) {
//                this.intent.putExtra(PersonalNewActivity.PERSONAL_USER_ID, user.getUid());
//            }
//        }
//        tabSpecMy.setContent(this.intent);
//        this.tabHost.addTab(tabSpecMy);
        this.tag = getIntent().getIntExtra("tag", 0);
        this.tabHost.setCurrentTab(this.tag);
        if (getIntent().getAction() != null && getIntent().getAction().equals(Constant.PUSH_HOME_FOLLOW_ACTION)) {
            HomeFragmentActivity.getIntance().setCurrentFrament(false);
            HomeFragmentActivity.getIntance().setUploadTopic(true);
        }

        initView();

//        if (MyApplication.getInstance().isLogin()) {
//            initUnReadMessage();
//        }

        setUiChange(this.tag);

        this.tabHost.setOpenAnimation(true);

//        startTutuService();

//        boolean fromLive = getIntent().getBooleanExtra("fromLive", false);
//        String topicId = getIntent().getStringExtra(Util.EXTRA_TOPICID);
//        if (fromLive && MyApplication.getInstance().isLogin()) {
//            getLiveRoomTopicInfo(topicId);
//        }
    }

    private void getLiveRoomTopicInfo(String topicid) {
        QGHttpRequest.getInstance().getLiveRoomTopicInfo(this, topicid, new C17531(this));
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Constant.UPLOAD_TOPIC_ACTION.equals(intent.getAction())) {
            HomeFragmentActivity.getIntance().setCurrentFrament(false);
            HomeFragmentActivity.getIntance().setUploadTopic(false);
            HomeFragmentActivity.getIntance().setUploadTopic((UploadTopic) intent.getSerializableExtra("uploadTopic"));
        } else if (Constant.PUSH_HOME_FOLLOW_ACTION.equals(intent.getAction())) {
            HomeFragmentActivity.getIntance().setCurrentFrament(false);
            HomeFragmentActivity.getIntance().setUploadTopic(true);
            this.tabHost.setCurrentTab(0);
            setUiChange(0);
        } else if (Constant.REDPACKAGRE_HOME_FOLLOW_ACTION.equals(intent.getAction())) {
            HomeFragmentActivity.getIntance().setCurrentFrament(false);
            HomeFragmentActivity.getIntance().setIsredPackage(true);
            TopicInfo topicinfo = (TopicInfo) intent.getSerializableExtra("topicinfo");
            int sysnc = intent.getIntExtra("themeSync", -1);
            String picPath = intent.getStringExtra("picPath");
            HomeFragmentActivity.getIntance().setTopicinfo(topicinfo);
            HomeFragmentActivity.getIntance().setSysnc(sysnc);
            HomeFragmentActivity.getIntance().setPicPath(picPath);
            this.tabHost.setCurrentTab(0);
            setUiChange(0);
        }
        int newtag = intent.getIntExtra("tag", -1);
        boolean go_zan = intent.getBooleanExtra("zan", false);
        if (newtag != -1) {
            this.tabHost.setCurrentTab(newtag);
            setUiChange(newtag);
//            if (go_zan && SquareActivity.getIntance() != null) {
//                SquareActivity.getIntance().refreshView(true);
//            }
        }
    }

    private void initView() {
        this.tv_home = (ImageView) findViewById(R.id.tv_home);
        this.tv_home.setOnClickListener(this);
        this.home_nearby = (ImageView) findViewById(R.id.home_nearby);
        this.home_nearby.setOnClickListener(this);
        this.tv_chat = (ImageView) findViewById(R.id.tv_chat);
        this.tv_chat.setOnClickListener(this);
        this.tv_news = (ImageView) findViewById(R.id.tv_news);
        this.tv_news.setOnClickListener(this);
        this.tv_my = (ImageView) findViewById(R.id.tv_my);
        this.tv_my.setOnClickListener(this);

//        this.home_chat_point = (ImageView) findViewById(R.id.home_chat_point);
//        this.home_news_point = (ImageView) findViewById(R.id.home_news_point);
//        this.home_my_point = (ImageView) findViewById(R.id.home_my_point);

        this.dialog = new BaseDialog(this);
        this.loginView = View.inflate(this, R.layout.dialog_base_layout, null);
        ((TextView) this.loginView.findViewById(R.id.tv_tip_title)).setText(getResources().getString(R.string.login_tutu));
        this.loginView.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
        this.loginView.findViewById(R.id.pop_tv_submit).setOnClickListener(this);
    }

    private void initUnReadMessage() {
        QGHttpRequest.getInstance().getUserMessageNum(this, new C17542(this, false));
    }

    private void changeNewPoint(int newTipsCount, int fansNum, int hotNum, int followNum, int newfollowhtcount, int newfollowpoicount) {
//        int private_num;
//        if (MyContext.getInstance().getRongIMClient() != null) {
//            private_num = MyContext.getInstance().getRongIMClient().getTotalUnreadCount();
//        } else {
//            private_num = 0;
//        }
//        MyApplication app = MyApplication.getInstance();
//        app.setNewsNum(newTipsCount);
//        app.setNewFanscount(fansNum);
//        app.setNewHotNum(hotNum);
//        app.setNewFollowNum(followNum);
//        app.setNewfollowhtcount(newfollowhtcount);
//        app.setNewfollowpoicount(newfollowpoicount);
//        app.setPrivateNum(private_num);
    }

    protected void onResume() {
        super.onResume();
    }

    public void showUnReadPoint() {
//        if (MyApplication.getInstance().isLogin()) {
//            if (MyApplication.getInstance().getNewsNum() > 0) {
//                this.home_news_point.setVisibility(View.VISIBLE);
//            } else {
//                this.home_news_point.setVisibility(View.INVISIBLE);
//            }
//            if (MyApplication.getInstance().getPrivateNum() > 0) {
//                this.home_chat_point.setVisibility(View.VISIBLE);
//            } else {
//                this.home_chat_point.setVisibility(View.GONE);
//            }
//            int fansCount = MyApplication.getInstance().getNewFanscount();
//            int huatiCount = MyApplication.getInstance().getNewfollowhtcount();
//            if ((fansCount + huatiCount) + MyApplication.getInstance().getNewfollowpoicount() > 0) {
//                this.home_my_point.setVisibility(View.VISIBLE);
//            } else {
//                this.home_my_point.setVisibility(View.INVISIBLE);
//            }
//            if (MyApplication.getInstance().getNewFollowNum() > 0) {
//                if (HomeFragment.getInstance() != null) {
//                    HomeFragment.getInstance().home_follow_point.setVisibility(View.VISIBLE);
//                }
//                if (HomeFollowFragment.getInstance() != null) {
//                    HomeFollowFragment.getInstance().home_follow_point.setVisibility(View.VISIBLE);
//                    return;
//                }
//                return;
//            }
//            if (HomeFragment.getInstance() != null) {
//                HomeFragment.getInstance().home_follow_point.setVisibility(View.INVISIBLE);
//            }
//            if (HomeFollowFragment.getInstance() != null) {
//                HomeFollowFragment.getInstance().home_follow_point.setVisibility(View.INVISIBLE);
//            }
//        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_tv_submit:
                startActivity(new Intent(this, LoginActivity.class));
                this.dialog.dismiss();
                break;
            case R.id.pop_tv_cancel:
                this.dialog.dismiss();
                break;
            case R.id.tv_home:
                setUiChange(0);
                break;
            case R.id.home_nearby:
                setUiChange(1);
                break;
            case R.id.tv_chat:
                if (MyApplication.getInstance().isLogin()) {
                    setUiChange(2);
                    MyApplication.getInstance().setPrivateNum(0);
                    return;
                }
                this.dialog.show(this.loginView);
                break;
            case R.id.tv_news:
                if (MyApplication.getInstance().isLogin()) {
                    setUiChange(3);
                    MyApplication.getInstance().setNewsNum(0);
                    return;
                }
                this.dialog.show(this.loginView);
                break;
            case R.id.tv_my:
                if (MyApplication.getInstance().isLogin()) {
                    setUiChange(4);
                    return;
                }
                this.dialog.show(this.loginView);
                break;
            default:
        }
    }

    private void setUiChange(int index) {
        clearNews(index);
        getTabHost().setCurrentTab(index);

        switch (this.cur_index) {
            case 0:
                this.tv_home.setSelected(false);
                break;
            case 1:
                this.home_nearby.setSelected(false);
                break;
            case 2:
                this.tv_chat.setSelected(false);
                break;
            case 3:
                this.tv_news.setSelected(false);
                break;
            case 4:
                this.tv_my.setSelected(false);
                break;
        }
        switch (index) {
            case 0:
                this.tv_home.setSelected(true);
                break;
            case 1:
                this.home_nearby.setSelected(true);
                break;
            case 2:
                this.tv_chat.setSelected(true);
                break;
            case 3:
                this.tv_news.setSelected(true);
                break;
            case 4:
                this.tv_my.setSelected(true);
                break;
        }
        this.cur_index = index;
    }

    public void clearNews(int index) {
//        if (!(!this.isnews || index == 3 || NewsListActivty.getObject() == null)) {
//            NewsListActivty.getObject().clearNews();
//        }
//        if (index == 3) {
//            this.isnews = true;
//        } else {
//            this.isnews = false;
//        }
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() != 4 || event.getAction() != 0 || event.getRepeatCount() != 0) {
            return super.dispatchKeyEvent(event);
        }
        if (System.currentTimeMillis() - this.mExitTime > 2000) {
            Toast.makeText(getApplicationContext(), "종료하시려면 한번 더 클릭하세요. '13S'", Toast.LENGTH_LONG).show();
            this.mExitTime = System.currentTimeMillis();
        } else {
            MyApplication.getInstance().existRongIMClient();
            finish();
            System.exit(0);
        }
        return true;
    }

    private void checkVersionUsable() {
//        String min_version = MobclickAgent.getConfigParams(this, "min_version");
//        this.update_url = MobclickAgent.getConfigParams(this, "update_url");
//        if (this.update_url == null || this.update_url.trim().equals(BaseConstants.ah)) {
//            this.update_url = ConstantURL.MAIN_URL_APPLE;
//        }
//        int min_version_code = 0;
//        if (!StringUtils.isEmpty(min_version)) {
//            try {
//                min_version_code = Integer.valueOf(min_version).intValue();
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }
//        int version_code = 0;
//        try {
//            version_code = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
//        } catch (NameNotFoundException e2) {
//            e2.printStackTrace();
//        }
//        if (version_code < min_version_code) {
//            AlertDialog dialog = new Builder(this).setTitle("\u5e94\u7528\u7248\u672c\u8fc7\u4f4e").setMessage("\u60a8\u5f53\u524d\u7684\u5e94\u7528\u7248\u672c\u8fc7\u4f4e\uff0c\u65e0\u6cd5\u7ee7\u7eed\u4f7f\u7528\uff0c\u8bf7\u5347\u7ea7\u5230\u6700\u65b0\u7248\u672c\uff01").setNegativeButton("\u53d6\u6d88", new C07913()).setPositiveButton("\u5347\u7ea7", new C07924()).show();
//            dialog.setOnKeyListener(new C07935());
//            dialog.setCanceledOnTouchOutside(false);
//            return;
//        }
//        UmengUpdateAgent.setUpdateOnlyWifi(false);
//        UmengUpdateAgent.update(this);
    }

//    private void startTutuService() {
//        startService(new Intent(this, TutuService.class));
//    }

    private void startContactsService() {
        TutuUsers user = MyApplication.getInstance().getUserinfo();
        if (user != null && user.getUid() != null) {
            Intent intent = new Intent(this, ContactsService.class);
            Bundle bundle = new Bundle();
            bundle.putString("tutu_id", user.getUid());
            intent.putExtras(bundle);
            startService(intent);
        }
    }

    protected void onPause() {
        super.overridePendingTransition(Constant.animation_1, Constant.animation_2);
        Constant.animation_1 = R.anim.main_translatex100to0;
        Constant.animation_2 = R.anim.main_translatex0tof100;
        super.onPause();
    }

    private final void showPublishTopicDialog() {
        if (this.mTakePicDialogView == null) {
            this.mTakePicDialogView = View.inflate(this, R.layout.dialog_take_photo, null);
            this.mTakePicDialogView.findViewById(R.id.photo_pop_tv_live).setOnClickListener(this);
            this.mTakePicDialogView.findViewById(R.id.photo_pop_tv_capture).setOnClickListener(this);
            this.mTakePicDialogView.findViewById(R.id.photo_pop_tv_album).setOnClickListener(this);
            this.mTakePicDialogView.findViewById(R.id.photo_pop_tv_cancel).setOnClickListener(this);
            this.mTakePicDialogView.findViewById(R.id.take_small_video).setOnClickListener(this);
        }
        this.mDialog.show(this.mTakePicDialogView);
    }
}
