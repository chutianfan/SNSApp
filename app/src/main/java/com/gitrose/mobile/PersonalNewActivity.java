package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.util.TimeUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.adapter.PersonalEmptyAdapter;
import com.gitrose.mobile.adapter.PersonalWorks9Adapter;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.TopicInfoLable;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.view.BaseDialog;
import com.gitrose.mobile.view.CircleImageView;
import com.gitrose.mobile.view.TopTip;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import shouji.gexing.framework.utils.UiUtils;

/**
 * Created by GITRose on 1/7/2016.
 */
public class PersonalNewActivity extends PersonalWorksListActiviy implements View.OnTouchListener, BaseUploadFragmentActivity.IUploadCall {

    private static PersonalNewActivity personalActivity;
    private String uid;
    private String tipid;
    private Boolean fromNotifi;
    private String nickName;
    private Boolean isFromHome;
    private PullToRefreshBase.OnRefreshListener<ListView> mOnRefreshListener;
    private ArrayList<TopicInfoLable> lable_topic_list;

    public PersonalNewActivity() {

        this.mOnRefreshListener = new ListViewRefreshHandler();
        this.mCurrentTab = 0;
    }

    class ListViewRefreshHandler implements PullToRefreshBase.OnRefreshListener<ListView> {
        ListViewRefreshHandler() {
        }

        public void onRefresh(PullToRefreshBase<ListView> refreshView) {
            if (refreshView.getCurrentMode() != PullToRefreshBase.Mode.PULL_FROM_START && refreshView.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_END) {

            }
        }
    }



    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.remove("android:support:fragments");
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_personal);
        personalActivity = this;
        Intent intent = getIntent();
        this.uid = intent.getStringExtra("userid");
        this.tipid = intent.getStringExtra("tipid");
        this.fromNotifi = intent.getBooleanExtra("fromNotifi", false);
        this.nickName = intent.getStringExtra("nickname");
        this.isFromHome = intent.getBooleanExtra("isFromHome", false);
        modifyPicture(this, true);

        if ((this.uid == null || this.uid.equals("")) && ((this.nickName == null || this.nickName.equals("")) && !MyApplication.getInstance().isLogin())) {
            finish();
        }

        initView();
        addScrollListener();
        getPersonalInfo(true, this.uid, Constant.SYSTEM_UID, "21", false, true);
    }

    private void getPersonalInfo(boolean isSelf, String user_id, String gettopiclist, String len, boolean isLoading, boolean isFromCreate) {
        if (this.mTopRefreshView.getVisibility() != View.VISIBLE) {
            this.mTopRefreshView.setVisibility(View.VISIBLE);
            TopTip.inFlyAnim(this.mTopRefreshView);
        }

        this.mTopRefreshView.setRotation(50.0f);
        getUserInfo(user_id, this.nickName, gettopiclist, len, isLoading, false);
    }

    private void getUserInfo(String user_id, String nickname, String gettopiclist, String len, boolean isLoading, boolean refresh) {
        QGHttpRequest.getInstance().getUserInfoHttpRequest(this, user_id, nickname, gettopiclist, len, new C16849(this, isLoading, this.container_rl, refresh));
    }

    class C16849 extends QGHttpHandler<TutuUsers> {
        private final boolean val$refresh;

        C16849(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, boolean z) {

            super($anonymous0, $anonymous1, $anonymous2);
            this.val$refresh = z;

        }

        public void onFinish() {
            super.onFinish();
            PersonalNewActivity.this.mPullToRefreshView.onRefreshComplete();
            PersonalNewActivity.this.mTopRefreshView.clearAnimation();
            PersonalNewActivity.this.mTopRefreshView.setVisibility(View.INVISIBLE);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            PersonalNewActivity.this.findViewById(R.id.no_personal_rl).setVisibility(View.VISIBLE);
            PersonalNewActivity.this.findViewById(R.id.personal_settings).setVisibility(View.GONE);
            TextView null_tv = (TextView) PersonalNewActivity.this.findViewById(R.id.personal_null_tv);
            ViewGroup.LayoutParams params = null_tv.getLayoutParams();
            params.height = UiUtils.getInstance(PersonalNewActivity.this).getmScreenHeight() - UiUtils.getInstance(PersonalNewActivity.this).DipToPixels(247.0f);
            null_tv.setLayoutParams(params);
        }

        public void onResponse(String responseString) {

            super.onResponse(responseString);

//            if (PersonalNewActivity.this.findViewById(R.id.no_personal_rl).getVisibility() == View.VISIBLE) {
//                PersonalNewActivity.this.findViewById(R.id.no_personal_rl).setVisibility(View.GONE);
//                PersonalNewActivity.this.findViewById(R.id.personal_settings).setVisibility(View.VISIBLE);
//            }
//            if (this.val$refresh) {
//
//                return;
//            }
        }

        public void onGetDataSuccess(TutuUsers data) {
            if (data != null) {
                PersonalNewActivity.this.mPersonalInfo = data;
                MyApplication.getInstance().setUserinfo(PersonalNewActivity.this.mPersonalInfo);
                PersonalNewActivity.this.initData(false, this.val$refresh);
            }
        }
    }

    private int topicnum = 0;
    private int favnum = 0;
    private int livenum = 0;
    private ArrayList<TopicInfo> mWorksPicList;
    private ArrayList<TopicInfo> mCollectsPicList;
    private String sign;
    public int mCurrentTab;

    private void initData(boolean fromlocal, boolean refesh) {
        this.uid = this.mPersonalInfo.getUid();

        if (this.mPersonalInfo.getTopicnum() == null || this.mPersonalInfo.getTopicnum().equals("null")) {
            this.topicnum = 0;
        } else {
            this.topicnum = this.mPersonalInfo.getTopicnum().intValue();
        }
        if (this.mPersonalInfo.getFavnum() == null || this.mPersonalInfo.getFavnum().equals("null")) {
            this.favnum = 0;
        } else {
            this.favnum = this.mPersonalInfo.getFavnum().intValue();
        }

        this.livenum = this.mPersonalInfo.getLiveroomnum();

        this.tv_topic_num.setText(new StringBuilder(String.valueOf(this.topicnum)).toString());
        this.tv_fav_num.setText(new StringBuilder(String.valueOf(this.favnum)).toString());
        this.tv_live_num.setText(new StringBuilder(String.valueOf(this.livenum)).toString());


        this.mWorksPicList.clear();
        this.mCollectsPicList.clear();
        if (this.mPersonalInfo.getTopiclist() != null) {
            this.mWorksPicList.addAll(this.mPersonalInfo.getTopiclist());
        }
        if (this.mPersonalInfo.getFavlist() != null) {
            this.mCollectsPicList.addAll(this.mPersonalInfo.getFavlist());
        }

        this.tableview_rl.setVisibility(View.VISIBLE);

//        this.mListView.setAdapter(new PersonalEmptyAdapter(this, WORK_LIST_9));

//        if (this.mPersonalInfo == null || this.mPersonalInfo.getStatus() == null || !this.mPersonalInfo.getStatus().equals("-2")) {
//
//            if (refesh) {
//                if (this.mCurrentTab == 0) {
//                    refreshState(findViewById(R.id.ll_person_topic));
//                } else if (this.mCurrentTab == 1) {
////                    refreshState(findViewById(R.id.ll_person_fav));
//                }
//            }
//        } else {
////            this.status = true;
////            this.img_feng.setVisibility(FRAGMENT_ONE);
////            this.mListView.setAdapter(new PersonalEmptyAdapter(this, WORK_LIST_9));
//        }

        refreshState(findViewById(R.id.ll_person_topic));

        initViewData();
    }

    private void refreshState(View view) {
        int empty_type;
        this.tv_topic_num.setSelected(true);
        this.tv_topic_name.setSelected(true);
        this.tv_fav_num.setSelected(false);
        this.tv_fav_name.setSelected(false);
        if (this.mHomeAdapter == null) {
            this.mHomeAdapter = new PersonalWorks9Adapter(this);
            this.mListView.setAdapter(this.mHomeAdapter);
//            this.mHomeAdapter.setOnClick(this.m9GridItemClickListener);
        }
//        if (this.isSelf) {
//            this.lable_topic_list = dealData(this.mWorksPicList_Local, true);
//            empty_type = FRAGMENT_TWO;
//        } else {
//            this.lable_topic_list = dealData(this.mWorksPicList, false);
//            empty_type = FRAGMENT_FOURE;
//        }

//        this.lable_topic_list = dealData(this.mWorksPicList, false);
        empty_type = 3;

        this.mHomeAdapter.setTopicList(this.mWorksPicList, 1);
        this.mCurrentTab = 0;
    }

    private void initViewData() {
        this.sign = this.mPersonalInfo.getSign();
        this.mPersonalIntroduce.setMaxLines(2);
        this.mPersonalIntroduce.setText(this.sign);
        String avat_url = ImageUtils.getUserIconUrl(this.mPersonalInfo.getProfileFile());
        ImageLoader.getInstance().displayImage(avat_url, this.img_wall, Constant.AVATAR_OPTIONS);
    }

    private void addScrollListener() {
        this.mPullToRefreshView.setOnRefreshListener3(new ScrollHandler());
    }

    class ScrollHandler implements PullToRefreshBase.OnRefreshListener3 {
        ScrollHandler() {
        }

        public void onStartRefresh(int scrollY) {
            if (PersonalNewActivity.this.mTopRefreshView.getVisibility() != View.VISIBLE && ((ListView) PersonalNewActivity.this.mPullToRefreshView.getRefreshableView()).getScrollY() == 0) {
                PersonalNewActivity.this.mTopRefreshView.setVisibility(View.VISIBLE);
                TopTip.inFlyAnim(PersonalNewActivity.this.mTopRefreshView);
            }

            PersonalNewActivity.this.mTopRefreshView.setRotation((float) scrollY);
        }

        public void onRefresh(boolean isRefresh) {
            if (isRefresh && ((ListView) PersonalNewActivity.this.mPullToRefreshView.getRefreshableView()).getScrollY() == 0) {
                PersonalNewActivity.this.mTopRefreshView.startAnimation(AnimationUtils.loadAnimation(PersonalNewActivity.this, R.anim.top_refresh_anim));
            } else if (PersonalNewActivity.this.mTopRefreshView.getVisibility() == View.VISIBLE) {
                TopTip.outFlyAnim(PersonalNewActivity.this.mTopRefreshView);
                PersonalNewActivity.this.mTopRefreshView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private PullToRefreshListView mPullToRefreshView;
    private ListView mListView;
    private View header_view;
    private BaseDialog base_dialog;
    private RelativeLayout container_rl;
    private CircleImageView mPersonalHead;
    private TextView mPersonalIntroduce;
    private ImageView mTopRefreshView;
    private ImageView chat_iv;
    private RelativeLayout tableview_rl;
    private ImageView img_wall;
    private TextView tv_topic_num;
    private TextView tv_topic_name;
    private TextView tv_live_num;
    private TextView tv_live_name;
    private TextView tv_fav_num;
    private TextView tv_fav_name;
    private GestureDetector mGestureDetector;
    public TutuUsers mPersonalInfo;

    private void initView() {

        this.mPullToRefreshView = (PullToRefreshListView) findViewById(R.id.personal_activity_total_listview);
        this.mPullToRefreshView.setOnRefreshListener(this.mOnRefreshListener);
        this.mPullToRefreshView.getHeaderLayout().setBackgroundColor(0);
        this.mListView = (ListView) this.mPullToRefreshView.getRefreshableView();
        this.header_view = View.inflate(this, R.layout.activity_personal_header, null);
        this.mListView.addHeaderView(this.header_view);
        this.mPullToRefreshView.setMode(PullToRefreshBase.Mode.BOTH);
        this.mListView.setAdapter(null);
        initListActivity(this.mListView);
        base_dialog = new BaseDialog(this);
        this.container_rl = (RelativeLayout) this.header_view.findViewById(R.id.main_pull_refresh_view);
        this.mPersonalHead = (CircleImageView) this.header_view.findViewById(R.id.personal_info_head);
        this.mPersonalIntroduce = (TextView) this.header_view.findViewById(R.id.personal_introduce);
        this.mTopRefreshView = (ImageView) this.header_view.findViewById(R.id.personal_top_refresh);
        this.chat_iv = (ImageView) this.header_view.findViewById(R.id.personal_other_chat_iv);
        this.chat_iv.setOnClickListener(this);
        this.tableview_rl = (RelativeLayout) this.header_view.findViewById(R.id.tabview_relative);
        this.mGestureDetector = new GestureDetector(new GestureListener());
        findViewById(R.id.personal_action_bar).setOnTouchListener(this);
        this.img_wall = (ImageView) this.header_view.findViewById(R.id.img_wall);
        this.mPullToRefreshView.setImageView(this.img_wall);
        LinearLayout ll_topic_self = (LinearLayout) this.header_view.findViewById(R.id.ll_topic_self);
        this.tv_topic_num = (TextView) this.header_view.findViewById(R.id.tv_topic_num);
        this.tv_topic_name = (TextView) this.header_view.findViewById(R.id.tv_topic_name);
        this.tv_fav_num = (TextView) this.header_view.findViewById(R.id.tv_fav_num);
        this.tv_fav_name = (TextView) this.header_view.findViewById(R.id.tv_fav_name);
        this.tv_live_num = (TextView) this.header_view.findViewById(R.id.tv_live_num);
        this.tv_live_name = (TextView) this.header_view.findViewById(R.id.tv_live_name);
        LinearLayout ll_topic_fav = (LinearLayout) this.header_view.findViewById(R.id.ll_person_fav);
        LinearLayout ll_topic_live = (LinearLayout) this.header_view.findViewById(R.id.ll_person_live);

        PersonalEmptyAdapter personalAdapter = new PersonalEmptyAdapter(this, 0);
        this.mListView.setAdapter(personalAdapter);

        this.mWorksPicList = new ArrayList();
        this.mCollectsPicList = new ArrayList();
    }

    public static PersonalNewActivity getObject() {
        return personalActivity;
    }

    class GestureListener extends GestureDetector.SimpleOnGestureListener {
        GestureListener() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (PersonalNewActivity.this.mListView != null) {
                PersonalNewActivity.this.mListView.scrollTo(0, 0);
            }
            return true;
        }

        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
    }
}
