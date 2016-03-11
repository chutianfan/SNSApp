package com.gitrose.mobile;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baoyz.swipemenulistview.XListView;
import com.baoyz.swipemenulistview.XListView.IXListViewListener;
import com.handmark.pulltorefresh.library.QuickReturnType;
import com.nostra13.universalimageloader.core.ImageLoader;
//import com.sina.weibo.sdk.component.WidgetRequestParam;
//import com.sina.weibo.sdk.openapi.InviteAPI;
//import com.tencent.connect.share.QzoneShare;
//import com.tencent.openqq.protocol.imsdk.im_common;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.adapter.HomeTopicAdapter;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.avsdk.control.Util;
import com.gitrose.mobile.base.BaseUploadFragment;
import com.gitrose.mobile.base.ITopicDisplay;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.dao.RequestDao;
import com.gitrose.mobile.download.video.DownLoadClientImpl;
import com.gitrose.mobile.download.video.DownLoadObserver;
import com.gitrose.mobile.download.video.VideoDownloader;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.Comment;
import com.gitrose.mobile.model.TopicInfoList;
import com.gitrose.mobile.model.VideoDownLoad;
import com.gitrose.mobile.utils.Base64Coder;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.view.AutoCoverFlowHelper;
import com.gitrose.mobile.view.AutoCoverFlowHelper.OnScrollListPosListener;
import com.gitrose.mobile.view.BaseDialog;
import com.gitrose.mobile.view.CircleImageView;
import com.gitrose.mobile.view.QuickReturnListViewOnScrollListener;
import com.gitrose.mobile.view.RoundProgressBar;
import com.gitrose.mobile.view.TipPop;
import com.gitrose.mobile.view.TopTip;
import com.gitrose.mobile.view.TopicMore;
//import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
//import io.rong.imlib.RongIMClient;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
//import org.apache.http.Header;
import qalsdk.BaseConstants;
import shouji.gexing.framework.utils.NetUtils;
import shouji.gexing.framework.utils.SpUtils;
import shouji.gexing.framework.utils.UiUtils;

@SuppressLint({"ValidFragment"})
public class HomeFragment extends BaseUploadFragment implements OnClickListener, IXListViewListener, OnTouchListener, OnScrollListPosListener, OnDismissListener, ITopicDisplay, DownLoadObserver<VideoDownLoad> {
    public static final int ADD_COMMENT_RESULT = 9;
    public static final int ADD_TOPIC_RESULT = 10;
    public static final int DOWN_MESSAGE = 336;
    public static final int FAV_RESULT = 15;
    public static final String GET_WORKS_ID = "works_id";
    public static final int IS_BLOCK_RESULT = 16;
    public static final String REQUEST_SIZE = "17";
    public static final String SP_KEY = "topic_list";
    public static final String SP_KEY_FRIEND = "topic_list_friend";
    private static HomeFragment homeFragment;
//    public static RongIMClient mRongIMClient;
    private LinearLayout home_bottom_bar;
    public ImageView home_follow_point;
    private boolean isHot;
    private ImageView iv_guide_finish;
    private ImageView iv_user_guide;
    private LinearLayout ll_user_guide_middle;
    private View loginView;
    private BaseDialog logindialog;
    private CircleImageView mCurrentAvator;
    private int mCurrentPosition;
    private View mDelCommentDialogView;
    private TipPop mDelCommentTipPop;
    private BaseDialog mDialog;
    private BaseDialog mDialogDown;
    private TextView mDownTextView;
    private VideoDownloader mDownloader;
    private AutoCoverFlowHelper mFlowHelper;
    private GestureDetector mGestureDetector;
    private Handler mHandler;
    private HomeTopicAdapter mHomeAdapter;
    private boolean mIsDelCommentFinish;
    private boolean mIsFinishRequest;
    private boolean mIsPause;
    private HashMap<String, Integer> mLocalTopicMap;
    private MediaPlayer mMediaPlayer;
    private View mMoreView;
//    private QavsdkControl mQavsdkControl;
    private RequestDao mRequestDao;
    private RoundProgressBar mRoundProgressBar;
    private final int mScrollableItems;
    private TutuUsers mSelfUserInfo;
    private Button mTakePhotoImg;
    private View mTakePicDialogView;
    private RelativeLayout mTitleBarRl;
    private TopicBroadcastReceiver mTopicBroadcastReceiver;
    private List<TopicInfo> mTopicList;
    private XListView mTopicLv;
    TopicInfo mTopicinfo;
    private RelativeLayout rl_user_guide;
    private RelativeLayout rl_user_guide_top;
    private QuickReturnListViewOnScrollListener scrollListener;
    private int statusBarHeight;
    private TextView tv_home_fav;
    private TextView tv_home_friend;
    private int uploadLocalView;

    /* renamed from: com.gitrose.mobile.HomeFragment.1 */
    class C06861 implements OnCompletionListener {
        C06861() {
        }

        public void onCompletion(MediaPlayer mp) {
            HomeFragment.this.mFlowHelper.startAutoFlow(HomeFragment.this.mCurrentPosition + 1, true, true);
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFragment.2 */
    class C06872 implements OnErrorListener {
        C06872() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
//            MobclickAgent.onEvent(HomeFragment.this.getActivity(), "media_player_error");
            HomeFragment.this.mFlowHelper.pauseVideoPlay(true);
            return true;
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFragment.5 */
    class C06885 implements Runnable {
        C06885() {
        }

        public void run() {
            HomeFragment.this.mTopicLv.setSelection(0);
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFragment.6 */
    class C06896 implements Runnable {
        C06896() {
        }

        public void run() {
            if (HomeFragment.this.ll_user_guide_middle.getVisibility() == View.GONE) {
                HomeFragment.this.ll_user_guide_middle.setVisibility(View.VISIBLE);
                HomeFragment.this.iv_guide_finish.setVisibility(View.VISIBLE);
                HomeFragment.this.rl_user_guide.setOnClickListener(HomeFragment.this);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFragment.9 */
    class C06909 implements OnClickListener {
        C06909() {
        }

        public void onClick(View v) {
            HomeFragment.this.mDialog.show(HomeFragment.this.mDelCommentDialogView);
        }
    }

    @SuppressLint({"HandlerLeak"})
    private class DownHandler extends Handler {
        private DownHandler() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HomeFragment.DOWN_MESSAGE /*336*/:
                    try {
                        int progress = msg.arg1;
                        if (progress != 100) {
                            if (progress == -100) {
                                HomeFragment.this.mDialogDown.dismiss();
                                HomeFragment.this.resumeVideoAndCommentFlow();
                            }
                            if (HomeFragment.this.mRoundProgressBar.getProgress() < progress) {
                                HomeFragment.this.mRoundProgressBar.setProgress(progress);
                                HomeFragment.this.mDownTextView.setText(new StringBuilder(String.valueOf(progress)).append("%").toString());
                                return;
                            }
                            return;
                        }
                        HomeFragment.this.mRoundProgressBar.setProgress(100);
                        HomeFragment.this.mDownTextView.setText("100%");
                        HomeFragment.this.mDialogDown.dismiss();
                        HomeFragment.this.resumeVideoAndCommentFlow();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                default:
            }
        }
    }

    class GestureListener extends SimpleOnGestureListener {
        GestureListener() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            HomeFragment.this.backTop();
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

    class TopicBroadcastReceiver extends BroadcastReceiver {
        TopicBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            String topicId;
            if (Constant.DEL_TOPIC_ACTION.equals(intent.getAction())) {
                topicId = intent.getStringExtra("topicId");
                String localTopicId = intent.getStringExtra("localTopicId");
                if (topicId != null) {
                    HomeFragment.this.deleteTopic(topicId);
                } else if (localTopicId != null) {
                    HomeFragment.this.deleteLocalTopic(localTopicId);
                }
                HomeFragment.this.mHomeAdapter.notifyDataSetChanged();
            } else if (Constant.BLOCK_TOPIC_ACTION.equals(intent.getAction())) {
//                uid = intent.getStringExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID);
//                HomeFragment.this.deleteBlockUserTopic(uid);
            } else if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                String SYSTEM_HOME_KEY = "homekey";
                String reason = intent.getStringExtra("reason");
                if (reason != null && SYSTEM_HOME_KEY.equals(reason)) {
                    MyApplication.getInstance().setClick_home(true);
                }
            } else if (Constant.MODIFY_MARKNAME_ACTION.equals(intent.getAction())) {
//                uid = intent.getStringExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID);
//                String rename = intent.getStringExtra("remark");
//                HomeFragment.this.modifyMarkName(uid, rename);
            } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                HomeFragment.this.notifyNetStatus();
            } else if (Constant.ZAN_TOPIC_ACTION.equals(intent.getAction())) {
                String zan_topic_id = intent.getStringExtra("topic_zan_topicId");
                int zan_status = intent.getIntExtra("topic_zan_status", 0);
                if (HomeFragment.this.mTopicList != null) {
                    if (HomeFragment.this.mTopicList != null) {
                        if (HomeFragment.this.mTopicList.size() > 0) {
//                            i = 0;
//                            while (true) {
//                                if (i < HomeFragment.this.mTopicList.size()) {
//                                    topicInfo = (TopicInfo) HomeFragment.this.mTopicList.get(i);
//                                    if (topicInfo != null) {
//                                        if (BaseConstants.ah.equals(topicInfo)) {
//                                            continue;
//                                        } else {
//                                            if (topicInfo.getTopicid().equals(zan_topic_id)) {
//                                                topicInfo.setIslike(zan_status);
//                                                return;
//                                            }
//                                        }
//                                    }
//                                    i++;
//                                } else {
//                                    return;
//                                }
//                            }
                        }
                    }
                }
            } else if (Constant.ADD_FAV_TOPIC_ACTION.equals(intent.getAction())) {
                topicId = intent.getStringExtra("topicId");
                if (HomeFragment.this.mTopicList != null) {
                    if (HomeFragment.this.mTopicList != null) {
                        if (HomeFragment.this.mTopicList.size() > 0) {
//                            i = 0;
//                            while (true) {
//                                if (i < HomeFragment.this.mTopicList.size()) {
//                                    topicInfo = (TopicInfo) HomeFragment.this.mTopicList.get(i);
//                                    if (topicInfo != null) {
//                                        if (topicInfo.getTopicid().equals(topicId)) {
//                                            topicInfo.setIsfav(Integer.valueOf(1));
//                                            return;
//                                        }
//                                    }
//                                    i++;
//                                } else {
//                                    return;
//                                }
//                            }
                        }
                    }
                }
            } else if (Constant.DEL_FAV_TOPIC_ACTION.equals(intent.getAction())) {
                topicId = intent.getStringExtra("topicId");
                if (HomeFragment.this.mTopicList != null) {
                    if (HomeFragment.this.mTopicList != null) {
                        if (HomeFragment.this.mTopicList.size() > 0) {
//                            i = 0;
//                            while (true) {
//                                if (i < HomeFragment.this.mTopicList.size()) {
//                                    topicInfo = (TopicInfo) HomeFragment.this.mTopicList.get(i);
//                                    if (topicInfo != null) {
//                                        if (topicInfo.getTopicid().equals(topicId)) {
//                                            topicInfo.setIsfav(Integer.valueOf(0));
//                                            HomeFragment.this.mHomeAdapter.notifyDataSetChanged();
//                                            return;
//                                        }
//                                    }
//                                    i++;
//                                } else {
//                                    return;
//                                }
//                            }
                        }
                    }
                }
            } else if (Constant.FOLLOW_USER_ACTION.equals(intent.getAction()) || Constant.UNFOLLOW_USER_ACTION.equals(intent.getAction())) {
//                uid = intent.getStringExtra("to_uid");
//                String relation = intent.getStringExtra("relation");
//                if (HomeFragment.this.mTopicList != null) {
//                    if (HomeFragment.this.mTopicList != null) {
//                        if (HomeFragment.this.mTopicList.size() > 0) {
//                            boolean isFound = false;
//                            i = 0;
//                            while (true) {
//                                if (i >= HomeFragment.this.mTopicList.size()) {
//                                    break;
//                                }
//                                topicInfo = (TopicInfo) HomeFragment.this.mTopicList.get(i);
//                                if (topicInfo != null) {
//                                    TutuUsers users = topicInfo.getUserinfo();
//                                    if (users != null) {
//                                        if (users.getUid().equals(uid)) {
//                                            users.setRelation(relation);
//                                            isFound = true;
//                                        }
//                                    }
//                                }
//                                i++;
//                            }
//                            if (isFound) {
//                                HomeFragment.this.mHomeAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                }
            }
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFragment.3 */
    class C14033 extends QuickReturnListViewOnScrollListener {
        C14033(QuickReturnType $anonymous0, View $anonymous1, int $anonymous2, View $anonymous3, int $anonymous4) {
            super($anonymous0, $anonymous1, $anonymous2, $anonymous3, $anonymous4);
        }

        protected void onRealScroll(AbsListView listview, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            HomeFragment.this.mFlowHelper.startAutoFlow(visibleItemCount);
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFragment.4 */
    class C14044 implements IUploadCall {
        C14044() {
        }

        public void gpuback(File file) {
//            MyApplication.getInstance().themeFile = file;
//            Intent intent = new Intent(HomeFragment.this.getActivity(), FilterAndStickerActivity.class);
//            intent.putExtra(QzoneShare.SHARE_TO_QQ_IMAGE_URL, MyApplication.getInstance().themeFile.getAbsolutePath());
//            HomeFragment.this.startActivityForNew(intent);
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFragment.10 */
    class AnonymousClass10 extends QGHttpHandler<String> {
        private final /* synthetic */ int val$commentPos;
        private final /* synthetic */ int val$listPos;

        AnonymousClass10(Context $anonymous0, int i, int i2) {

            super($anonymous0);
            this.val$listPos = i;
            this.val$commentPos = i2;

        }

        public void onGetDataSuccess(String data) {
            HomeFragment.this.mIsDelCommentFinish = true;
            if (HomeFragment.this.mDelCommentTipPop != null) {
                HomeFragment.this.mDelCommentTipPop.dismiss();
            }
            TopicInfo topicInfo = (TopicInfo) HomeFragment.this.mTopicList.get(this.val$listPos);
            topicInfo.setTotalcomment(topicInfo.getTotalcomment() - 1);
            topicInfo.getCommentlist().remove(this.val$commentPos);
            HomeFragment.this.mHomeAdapter.notifyCommentPos(this.val$listPos, this.val$commentPos, null);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            if (HomeFragment.this.mDelCommentTipPop != null) {
                HomeFragment.this.mDelCommentTipPop.dismiss();
            }
            Toast.makeText(HomeFragment.this.getActivity(), HomeFragment.this.getString(R.string.network_error_tip), Toast.LENGTH_LONG).show();
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFragment.7 */
    class C16577 extends QGHttpHandler<String> {
        C16577(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(String data) {
//            HomeFragment.this.startActivity(new Intent(HomeFragment.this.getActivity(), AvPrepareLiveActivity.class));
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            TopTip.show(HomeFragment.this.getActivity(), (RelativeLayout) HomeFragment.this.getActivity().findViewById(R.id.container_rl), HomeFragment.this.getResources().getString(R.string.err_tips), responseString, -1);
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFragment.8 */
    class C16588 extends QGHttpHandler<TopicInfoList> {
        private final /* synthetic */ String val$direction;

        C16588(Context $anonymous0, String str) {

            super($anonymous0);
            this.val$direction = str;

        }

        public void onGetDataSuccess(TopicInfoList data) {
            if (data != null) {
                HomeFragment.this.saveTopicInfoListToLocal(data);
                HomeFragment.this.filterTopics(data);
                List<TopicInfo> topics = data.getList();
                int requestSize = topics.size();
                if (requestSize > 0) {
                    HomeFragment.this.mFlowHelper.stopAutoFlow();
                    if (SearchActivity.UP.equals(this.val$direction)) {
                        HomeFragment.this.mTopicList.addAll(0, data.getList());
                        HomeFragment.this.moveUploadingTopicPos(requestSize);
                        if (HomeFragment.this.mTopicList.size() > 0) {
                            HomeFragment.this.mTopicLv.setSelection(0);
                            HomeFragment.this.mHomeAdapter.notifyDataSetChanged();
                        }
                    } else {
                        HomeFragment.this.mTopicList.addAll(topics);
                        HomeFragment.this.mHomeAdapter.notifyDataSetChanged();
                    }
                    HomeFragment.this.mTopicLv.setPullLoadEnable(true);
                }
            }
        }

        public void onFinish() {
            HomeFragment.this.onRefreshComplete();
            HomeFragment.this.mIsFinishRequest = true;
            super.onFinish();
        }
    }

    public HomeFragment() {
        this.isHot = true;
        this.mCurrentPosition = -1;
        this.mScrollableItems = 17;
        this.mIsFinishRequest = true;
    }

    public static HomeFragment getInstance() {
        return homeFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.isHot = bundle.getBoolean("isHot");
        }
        homeFragment = this;
        View view = inflater.inflate(R.layout.activity_home, null);
        this.mHandler = new Handler();
        initView(view);
//        if (((Boolean) SpUtils.getFromLocal(getActivity(), Constant.KEY_HOME_GUIDE, Boolean.valueOf(true))).booleanValue()) {
//            this.rl_user_guide.setVisibility(View.VISIBLE);
//            if (TabMainActivity.getInstance() != null) {
//                TabMainActivity.getInstance().user_guide_bottom.setVisibility(View.VISIBLE);
//            }
//        } else
//        {
//            this.rl_user_guide.setVisibility(View.GONE);
//            if (TabMainActivity.getInstance() != null) {
//                TabMainActivity.getInstance().user_guide_bottom.setVisibility(View.GONE);
//            }
//        }

        getStatusbarHeight();
        initData();
        return view;
    }

    public void startAVActivity(TopicInfo topicinfo) {
        this.mTopicinfo = topicinfo;
        if (MyApplication.getInstance().getUserinfo() == null) {
            startLoginActivity();
            return;
        }
        MyApplication.getInstance().getUserinfo().setCreater(false);
        if (!Util.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), getString(R.string.empty_news_tips_net), Toast.LENGTH_LONG).show();
        } else if (this.mTopicinfo.getUid() != null) {
//            Intent enter_intent = new Intent(getActivity(), AvActivity.class);
//            enter_intent.putExtra("topicinfo", this.mTopicinfo);
//            enter_intent.putExtra("enterRoom", true);
//            startActivity(enter_intent);
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null) {
//            this.mCurrentPhotoFile = (File) savedInstanceState.getSerializable("mCurrentPhotoFile");
//        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        if (outState != null) {
//            outState.putSerializable("mCurrentPhotoFile", this.mCurrentPhotoFile);
//        }
    }

    private void initView(View view) {
//        this.home_follow_point = (ImageView) view.findViewById(R.id.home_follow_point);
        this.rl_user_guide = (RelativeLayout) view.findViewById(R.id.rl_user_guide);
        this.rl_user_guide.setOnClickListener(null);
        this.rl_user_guide_top = (RelativeLayout) view.findViewById(R.id.rl_user_guide_top);
        this.iv_user_guide = (ImageView) view.findViewById(R.id.iv_user_guide);
        this.iv_user_guide.setOnClickListener(this);
        this.ll_user_guide_middle = (LinearLayout) view.findViewById(R.id.ll_user_guide_middle);
        this.iv_guide_finish = (ImageView) view.findViewById(R.id.iv_guide_finish);
        this.iv_guide_finish.setOnClickListener(this);
//        view.findViewById(R.id.iv_add_friend).setOnClickListener(this);
        view.findViewById(R.id.img_take_photo).setOnClickListener(this);
//        this.home_bottom_bar = (LinearLayout) view.findViewById(R.id.home_bottom_bar);
//        this.tv_home_fav = (TextView) view.findViewById(R.id.tv_home_fav);
//        this.tv_home_fav.setOnClickListener(this);
//        this.tv_home_friend = (TextView) view.findViewById(R.id.tv_home_friend);
//        this.tv_home_friend.setOnClickListener(this);
//        if (this.isHot) {
//            this.tv_home_fav.setSelected(true);
//            this.tv_home_friend.setSelected(false);
//        } else {
//            this.tv_home_fav.setSelected(false);
//            this.tv_home_friend.setSelected(true);
//        }
        this.mTakePhotoImg = (Button) view.findViewById(R.id.img_take_photo);
        this.mTopicLv = (XListView) view.findViewById(R.id.lv_pic_list);
        this.mTitleBarRl = (RelativeLayout) view.findViewById(R.id.home_action_bar);
        this.mCurrentAvator = (CircleImageView) view.findViewById(R.id.circleimg_current_show_avatar);
        this.mTopicList = new ArrayList();
        this.mHomeAdapter = new HomeTopicAdapter(getActivity(), this.mTopicLv, this.mTopicList, true, getActivity(), this);
        this.mHomeAdapter.setOnClickListener(this);
        this.mTopicLv.setAdapter(this.mHomeAdapter);
        this.mTopicLv.setPullRefreshEnable(true);
        this.mTopicLv.setPullLoadEnable(false);
        this.mTopicLv.setXListViewListener(this);
        this.mFlowHelper = new AutoCoverFlowHelper(getActivity(), this.mTopicLv, (Fragment) this);
        this.mFlowHelper.setOnScrollListPosListener(this);
        notifyNetStatus();
        initMediaPalyer();
        addListViewListener();
        this.mTakePhotoImg.setOnClickListener(this);
        this.mGestureDetector = new GestureDetector(new GestureListener());
        this.mTitleBarRl.setOnTouchListener(this);
        initDialog();
        registerBroadCast();
        this.mDownloader = new VideoDownloader(this, this.mTopicList);
        DownLoadClientImpl.getInstance().registerObserver(this);
        this.logindialog = new BaseDialog(getActivity());
        this.loginView = View.inflate(getActivity(), R.layout.dialog_login_base_layout, null);
        ((TextView) this.loginView.findViewById(R.id.tv_tip_title)).setText(getResources().getString(R.string.login_tutu));
        this.loginView.findViewById(R.id.pop_login_tv_cancel).setOnClickListener(this);
        this.loginView.findViewById(R.id.pop_login_tv_submit).setOnClickListener(this);
    }

    private void initData() {
        this.mLocalTopicMap = new HashMap();
        this.mRequestDao = new RequestDao(getActivity());
        if (this.mTopicList.size() > 0) {
            this.mCurrentAvator.setTag(((TopicInfo) this.mTopicList.get(0)).getUid());
        }
        TopicInfoList topicInfoList = getTopicInfoListFromLocal();
        if (topicInfoList == null || BaseConstants.ah.equals(topicInfoList)) {
            this.mTopicLv.setPullRefreshEnable(true);
            this.mTopicLv.autoRefresh();
            return;
        }

        filterTopics(topicInfoList);
        List<TopicInfo> topics = topicInfoList.getList();
        if (topics.size() > 0) {
            this.mFlowHelper.stopAutoFlow();
            this.mTopicList.addAll(topics);
            this.mHomeAdapter.notifyDataSetChanged();
        }
        this.mTopicLv.setPullRefreshEnable(true);
        this.mTopicLv.setPullLoadEnable(true);
    }

    public void onResume() {
        super.onResume();
        this.mIsPause = false;
        if (this.mDialog != null && this.mDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        if (this.isHot == ((HomeFragmentActivity) getActivity()).getCurrentFragment()) {
            resumeVideoAndCommentFlow();
        }
    }

    private void resumeVideoAndCommentFlow() {
        if (this.mDownloader != null) {
            this.mDownloader.resumeVideo();
        }
//        if (this.mFlowHelper != null) {
//            this.mFlowHelper.startAutoFlow(this.mCurrentPosition + this.mTopicLv.getHeaderViewsCount(), true, true);
//        }
    }

    public void onPause() {
        super.onPause();
        this.mIsPause = true;
        if (this.uploadLocalView >= 3) {
            this.mHomeAdapter.uploadLocalViews();
            this.uploadLocalView = 0;
        } else {
            this.uploadLocalView++;
        }
        this.mFlowHelper.pauseVideoPlay();
        if (this.mDownloader != null) {
            this.mDownloader.pauseVideo();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        unRegisterBrocast();
        this.mFlowHelper.releaseMediaPlay();
//        DownLoadClientImpl.getInstance().unregisterObserver(this);
    }

    private void notifyNetStatus() {
        if (!NetUtils.checkNet(getActivity())) {
            Constant.netWorkStatus = -1;
        } else if (NetUtils.getWifi(getActivity())) {
            Constant.netWorkStatus = 1;
        } else {
            Constant.netWorkStatus = 0;
        }
    }

    private void initMediaPalyer() {
        this.mMediaPlayer = new MediaPlayer();
        this.mMediaPlayer.setAudioStreamType(3);
        this.mMediaPlayer.setLooping(true);
        this.mMediaPlayer.setOnCompletionListener(new C06861());
        this.mMediaPlayer.setOnErrorListener(new C06872());
    }

    private void addListViewListener() {
        int headerHeight = getResources().getDimensionPixelSize(R.dimen.action_bar_height);
        this.scrollListener = new C14033(QuickReturnType.HEADER, this.mTitleBarRl, -headerHeight, this.home_bottom_bar, getResources().getDimensionPixelSize(R.dimen.home_foot_height));
        this.scrollListener.setCanSlideInIdleScrollState(true);
        this.mTopicLv.setOnScrollListener(this.scrollListener);
    }

    private void initDialog() {
//        modifyPicture(new C14044(), true);
        if (getActivity() != null) {
            this.mDialog = new BaseDialog(getActivity());
            this.mDialogDown = new BaseDialog(getActivity(), R.style.Transparent);
        }
    }

    private void registerBroadCast() {
        this.mTopicBroadcastReceiver = new TopicBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.DEL_TOPIC_ACTION);
        filter.addAction(Constant.BLOCK_TOPIC_ACTION);
        filter.addAction(Constant.MODIFY_MARKNAME_ACTION);
        filter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction(Constant.ZAN_TOPIC_ACTION);
        filter.addAction(Constant.ADD_FAV_TOPIC_ACTION);
        filter.addAction(Constant.DEL_FAV_TOPIC_ACTION);
        filter.addAction(Constant.FOLLOW_USER_ACTION);
        filter.addAction(Constant.UNFOLLOW_USER_ACTION);
        getActivity().registerReceiver(this.mTopicBroadcastReceiver, filter);
    }

    private void unRegisterBrocast() {
        getActivity().unregisterReceiver(this.mTopicBroadcastReceiver);
    }

    private void backTop() {
        this.mTopicLv.smoothScrollBy(-2147483647, AutoCoverFlowHelper.RAPID_DELAY_TIME);
        this.mHandler.postDelayed(new C06885(), 600);
    }

    @Override
    public VideoDownloader getDownloader() {
        return this.mDownloader;
    }

    public void notifyData(VideoDownLoad t) {
        if (this.mCurrentPosition >= 0 && this.mTopicList.size() > this.mCurrentPosition) {
            TopicInfo topicInfo = (TopicInfo) this.mTopicList.get(this.mCurrentPosition);
            if (topicInfo != null && t.getUrl().equals(topicInfo.getVideourl())) {
                this.mFlowHelper.updateVideoProgress(t.getProgress(), t.getStatus(), t.getName());
            }
        }
    }

    @Override
    public void onDelCommentListener(View commentView, int listPos, String topicId, int commentPos, String commentId, boolean isHasframe, TopicInfo mTopicInfo) {
        int i = 0;
//        PlaySound.getInstance(getActivity()).toPlay(R.raw.delete);
        Object[] delCommentParams = new Object[]{Integer.valueOf(listPos), topicId, Integer.valueOf(commentPos), commentId};
        showDelCommentDialog();
        this.mDelCommentDialogView.setTag(delCommentParams);
        TipPop tipPop = this.mDelCommentTipPop;
        if (isHasframe) {
            i = getResources().getDimensionPixelOffset(R.dimen.white_broder_width);
        }
        tipPop.show(commentView, i);
    }

    public AutoCoverFlowHelper getFlowHelper() {
        return this.mFlowHelper;
    }

    public MediaPlayer getMediaPlayer() {
        return this.mMediaPlayer;
    }

    public int getBlockTopic() {
        return 0;
    }

    public void showLayerMask() {
        View view = new View(getActivity());
        view.setLayoutParams(new LayoutParams(-2, -2));
        this.mDialog.show(view);
    }

    public boolean isPause() {
        return this.mIsPause;
    }

    public int getDownloadPos() {
        return this.mCurrentPosition;
    }

    public View getConvertView() {
        return null;
    }

    public void onDismiss() {
        if (this.mIsDelCommentFinish) {
            this.mIsDelCommentFinish = false;
        } else {
            this.mFlowHelper.resumeAutoFlow();
        }
    }

    public void onScrollPos(ListView parent, int pos) {
        int position = pos - 1;
        if (position >= 0) {
            changeAvatar(position);
            this.mCurrentPosition = position;
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
    }

    public void onRefresh() {
        request(true);
        this.mFlowHelper.pauseVideoPlay();
    }

    public void onLoadMore() {
        request(false);
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.home_action_bar:
                backTop();
                break;
            case R.id.iv_add_friend:
                if (MyApplication.getInstance().isLogin()) {
//                    MobclickAgent.onEvent(getActivity(), "click_search");
                    startActivityForNew(new Intent(getActivity(), SearchActivity.class));
                    return;
                }
                this.logindialog.show(this.loginView);
                break;
            case R.id.tv_home_friend:
                this.tv_home_friend.setSelected(false);
                this.tv_home_fav.setSelected(true);
                switchToFriendFragment(v);
                break;
            case R.id.img_take_photo:
//                MobclickAgent.onEvent(getActivity(), "click_photo");
//                takeTopic();
                break;
            case R.id.circleimg_current_show_avatar:
            case R.id.iv_user_icon:
//                TopicInfo topic = (TopicInfo) v.getTag();
//                if (topic != null) {
//                    String isName = topic.getIskana();
//                    if (isName == null || BaseConstants.ah.equals(isName)) {
//                        startPersonalActivity(topic.getUid());
//                    } else if (Integer.valueOf(isName).intValue() == 0) {
//                        startPersonalActivity(topic.getUid());
//                    } else {
//                        Toast.makeText(getActivity(), getString(R.string.theme_isname_tip), Toast.LENGTH_LONG).show();
//                    }
//                }
                break;
            case R.id.iv_user_guide:
//                this.rl_user_guide_top.setVisibility(View.GONE);
//                this.iv_user_guide.setVisibility(View.GONE);
//                this.mTopicLv.smoothScrollBy((UiUtils.getInstance(getActivity()).getmScreenHeight() - this.statusBarHeight) / 2, im_common.MSF_WPA_TMP_MSG);
//                this.mHandler.postDelayed(new C06896(), 350);
                break;
            case R.id.iv_guide_finish:
                if (this.rl_user_guide.getVisibility() == View.VISIBLE) {
                    this.rl_user_guide.setVisibility(View.GONE);
                }
                if (TabMainActivity.getInstance() != null) {
                    TabMainActivity.getInstance().user_guide_bottom.setVisibility(View.GONE);
                }
                SpUtils.saveToLocal(getActivity(), Constant.KEY_HOME_GUIDE, Boolean.valueOf(false));
                break;
            case R.id.share_tv_cancel:
            case R.id.photo_pop_tv_cancel:
                this.mDialog.dismiss();
                break;
            case R.id.pop_tv_submit:
//                delComment(v);
                break;
            case R.id.pop_tv_cancel:
//                this.mDialog.dismiss();
//                if (this.mDelCommentTipPop != null) {
//                    this.mDelCommentTipPop.dismiss();
//                }
                break;
            case R.id.pop_login_tv_submit:
//                startActivityForNew(new Intent(getActivity(), LoginActivity.class));
//                this.logindialog.dismiss();
                break;
            case R.id.pop_login_tv_cancel:
//                this.logindialog.dismiss();
                break;
            case R.id.photo_pop_tv_capture:
//                this.mDialog.dismiss();
//                getPicFromCapture();
                break;
            case R.id.photo_pop_tv_album:
//                this.mDialog.dismiss();
//                intent = new Intent(getActivity(), ChooImg2VideoActivity.class);
//                intent.putExtra("is_take_photo", true);
//                startActivityForNew(intent);
                break;
            case R.id.photo_pop_tv_live:
//                this.mDialog.dismiss();
//                this.mSelfUserInfo = MyApplication.getInstance().getUserinfo();
//                checkCanLive();
                break;
            case R.id.take_small_video:
//                this.mDialog.dismiss();
//                startActivityForNew(new Intent(getActivity(), FFmpegRecorderActivity.class));
                break;
            case R.id.share_tv_block:
//                blockTopic();
                break;
            case R.id.share_tv_report:
//                reportTopic();
                break;
            case R.id.share_tv_fav:
//                takeFav();
                break;
            case R.id.tv_topic_more:
            case R.id.tv_home_more:
//                TopicInfo topicInfo = (TopicInfo) v.getTag();
//                intent = new Intent(getActivity(), ShareActivity.class);
//                intent.putExtra(QzoneShare.SHARE_TO_QQ_TITLE, Constant.PREFENCES_NAME);
//                intent.putExtra(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT, topicInfo.getDesc());
//                intent.putExtra(Util.EXTRA_TOPICID, topicInfo.getTopicid());
//                intent.putExtra("topic_uid", topicInfo.getUid());
//                intent.putExtra(InviteAPI.KEY_URL, topicInfo.getContent());
//                intent.putExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID, topicInfo.getUid());
//                intent.putExtra("topicInfo", topicInfo);
//                intent.putExtra("publish_nickname", topicInfo.getUserinfo().getNickname());
//                startActivityForResult(intent, 0);
//                animationForBottom();
//                showLayerMask();
                break;
            default:
        }
    }

    private void checkCanLive() {
        QGHttpRequest.getInstance().checkCanLive(getActivity(), new C16577(getActivity()));
    }

    private void switchToFriendFragment(View v) {
        if (this.isHot) {
            ((HomeFragmentActivity) getActivity()).switchFragment();
        }
    }

    private void switchToHotFragment(View v) {
        if (!this.isHot) {
            ((HomeFragmentActivity) getActivity()).switchFragment();
        }
    }

    private void reportTopic() {
        TopicMore.reportTopic(getActivity(), this.mDialog, ((TopicInfo) this.mMoreView.getTag()).getTopicid());
    }

    private void takeFav() {
        TopicMore.takeFav(getActivity(), this.mDialog, (TopicInfo) this.mMoreView.getTag());
    }

    private void blockTopic() {
        TopicMore.blockTopic(getActivity(), this.mDialog, ((TopicInfo) this.mMoreView.getTag()).getUid());
    }

    private void seePersonalInfo() {
        if (!MyApplication.getInstance().isLogin()) {
            startLoginActivity();
        } else if (MyApplication.getInstance().getUserinfo().getUid() != null) {
            startPersonalActivity(MyApplication.getInstance().getUserinfo().getUid());
//            PlaySound.getInstance(getActivity()).toPlay(R.raw.open);
        }
    }

    private void startPersonalActivity(String uid) {
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), PersonalNewActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString(PersonalNewActivity.PERSONAL_USER_ID, uid);
//        intent.putExtras(bundle);
//        startActivityForNew(intent);
    }

    private void takeTopic() {
        if (MyApplication.getInstance().isLogin()) {
//            PlaySound.getInstance(getActivity()).toPlay(R.raw.camera);
            showPublishTopicDialog();
            return;
        }
        this.logindialog.show(this.loginView);
    }

    private void startLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), LoginActivity.class);
        startActivityForNew(intent);
    }

    private final void showPublishTopicDialog() {
        if (this.mTakePicDialogView == null) {
            this.mTakePicDialogView = View.inflate(getActivity(), R.layout.dialog_take_photo, null);
//            this.mTakePicDialogView.findViewById(R.id.photo_pop_tv_live).setOnClickListener(this);
            this.mTakePicDialogView.findViewById(R.id.photo_pop_tv_capture).setOnClickListener(this);
            this.mTakePicDialogView.findViewById(R.id.photo_pop_tv_album).setOnClickListener(this);
            this.mTakePicDialogView.findViewById(R.id.photo_pop_tv_cancel).setOnClickListener(this);
            this.mTakePicDialogView.findViewById(R.id.take_small_video).setOnClickListener(this);
        }
        this.mDialog.show(this.mTakePicDialogView);
    }

    private void saveTopicInfoListToLocal(TopicInfoList data) {
        Editor editor = getActivity().getSharedPreferences("home_topic_data", 0).edit();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            new ObjectOutputStream(baos).writeObject(data);
            String stringData = new String(Base64Coder.encode(baos.toByteArray()));
            if (this.isHot) {
                editor.putString(SP_KEY, stringData);
            } else if (!(MyApplication.getInstance().getUserinfo() == null || MyApplication.getInstance().getUserinfo().getUid() == null)) {
                editor.putString(new StringBuilder(SP_KEY_FRIEND).append(MyApplication.getInstance().getUserinfo().getUid()).toString(), stringData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        editor.commit();
    }

    private TopicInfoList getTopicInfoListFromLocal() {
        SharedPreferences sp = getActivity().getSharedPreferences("home_topic_data", 0);
        String data = null;
        try {
            if (this.isHot) {
                data = sp.getString(SP_KEY, null);
            } else if (!(MyApplication.getInstance().getUserinfo() == null || MyApplication.getInstance().getUserinfo().getUid() == null)) {
                data = sp.getString(new StringBuilder(SP_KEY_FRIEND).append(MyApplication.getInstance().getUserinfo().getUid()).toString(), null);
            }
            if (data == null) {
                return null;
            }
            return (TopicInfoList) new ObjectInputStream(new ByteArrayInputStream(Base64Coder.decode(data))).readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private void request(boolean up) {
        String startTopicId = null;
        String endTopicId = null;
        int i = 0;
        if (this.mTopicList.size() > 0) {
            while (this.mTopicList.size() > i) {
                if (this.mLocalTopicMap.size() == 0 || !this.mLocalTopicMap.containsValue(Integer.valueOf(i))) {
                    startTopicId = ((TopicInfo) this.mTopicList.get(i)).getTopicid();
                    break;
                }
                i++;
            }
            endTopicId = ((TopicInfo) this.mTopicList.get(this.mTopicList.size() - 1)).getTopicid();
        }
        if (up) {
            requestData(SearchActivity.UP, startTopicId, endTopicId);
        } else {
            requestData(SearchActivity.DOWN, endTopicId, startTopicId);
        }

    }

    private void requestData(String direction, String startTopicId, String endTopicId) {
        if (this.mIsFinishRequest) {
            this.mIsFinishRequest = false;
            QGHttpRequest.getInstance().getHomeHotOrFriendList(getActivity(), startTopicId, endTopicId, REQUEST_SIZE, direction, this.isHot, new C16588(getActivity(), direction));
        }
    }

    private void onRefreshComplete() {
        this.mTopicLv.stopRefresh();
        this.mTopicLv.stopLoadMore();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void locationSelectedPos(int requestSize) {
        this.scrollListener.setHeaderStopWork(true);
        View v = this.mTopicLv.getChildAt(1);
        int top = v == null ? UiUtils.getInstance(getActivity()).getmScreenWidth() / 4 : (UiUtils.getInstance(getActivity()).getmScreenWidth() / 4) + v.getTop();
        this.mHomeAdapter.notifyDataSetChanged();
        this.mTopicLv.setSelectionFromTop(requestSize + 1, top);
        this.scrollListener.setHeaderStopWork(false);
    }

    private void moveUploadingTopicPos(int moveSize) {
        for (Entry<String, Integer> entry : this.mLocalTopicMap.entrySet()) {
            entry.setValue(Integer.valueOf(((Integer) entry.getValue()).intValue() + moveSize));
        }
    }

    private void filterTopics(TopicInfoList data) {
        List<String> deleteList = data.getDeletelist();
        if (data.getDeletelist() != null) {
            int size = deleteList.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    deleteTopic((String) deleteList.get(i));
                }
            }
        }
    }

    private void changeAvatar(int currentPos) {
        if (currentPos < this.mTopicList.size()) {
            TopicInfo topicInfo = (TopicInfo) this.mTopicList.get(currentPos);
            String avatorUrl = ImageUtils.getAvatarUrl(topicInfo.getUid(), topicInfo.getUserinfo().getAvatartime());
            this.mCurrentAvator.setTag(topicInfo.getUid());
            ImageLoader.getInstance().displayImage(avatorUrl, this.mCurrentAvator, Constant.AVATAR_OPTIONS);
        }
    }

    private void showDelCommentDialog() {
        if (this.mDelCommentDialogView == null) {
            this.mDelCommentDialogView = View.inflate(getActivity(), R.layout.dialog_base_layout, null);
            ((TextView) this.mDelCommentDialogView.findViewById(R.id.tv_tip_title)).setText(getResources().getString(R.string.topic_del_comment));
            this.mDelCommentDialogView.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
            this.mDelCommentDialogView.findViewById(R.id.pop_tv_submit).setOnClickListener(this);
            this.mDelCommentTipPop = new TipPop(getActivity(), new String[]{getResources().getString(R.string.delete)}, new C06909(), this);
        }
    }

    private void deleteLocalTopic(String localTopicId) {
        for (int i = this.mTopicList.size() - 1; i >= 0; i--) {
            if (localTopicId.equals(((TopicInfo) this.mTopicList.get(i)).getLocalRequestId())) {
                this.mTopicList.remove(i);
                this.mLocalTopicMap.remove(localTopicId);
                this.mHomeAdapter.notifyDataSetChanged();
                for (Entry<String, Integer> entry : this.mLocalTopicMap.entrySet()) {
                    if (((Integer) entry.getValue()).intValue() < i) {
                        entry.setValue(Integer.valueOf(((Integer) entry.getValue()).intValue() - 1));
                    }
                }
                if (this.mCurrentPosition > 0 && this.mTopicList.size() > this.mCurrentPosition) {
                    changeAvatar(this.mCurrentPosition);
                }
                this.mHomeAdapter.notifyDataSetChanged();
            }
        }
        changeAvatar(this.mCurrentPosition);
        this.mHomeAdapter.notifyDataSetChanged();
    }

    private void deleteTopic(String topicId) {
        int i = this.mTopicList.size() - 1;
        while (i >= 0) {
            if (topicId.equals(((TopicInfo) this.mTopicList.get(i)).getTopicid()) || ((TopicInfo) this.mTopicList.get(i)).getType().intValue() == 2) {
                this.mTopicList.remove(i);
                this.mHomeAdapter.notifyDataSetChanged();
                for (Entry<String, Integer> entry : this.mLocalTopicMap.entrySet()) {
                    if (((Integer) entry.getValue()).intValue() < i) {
                        entry.setValue(Integer.valueOf(((Integer) entry.getValue()).intValue() - 1));
                    } else if (((Integer) entry.getValue()).intValue() == i) {
                        this.mLocalTopicMap.remove(entry.getKey());
                    }
                }
                if (this.mCurrentPosition > 0 && this.mTopicList.size() > this.mCurrentPosition) {
                    changeAvatar(this.mCurrentPosition);
                    return;
                }
            }
            i--;
        }
        if (this.mCurrentPosition > 0) {
        }
    }

    private void modifyMarkName(String uid, String rename) {
        for (int i = this.mTopicList.size() - 1; i >= 0; i--) {
            if (uid.equals(((TopicInfo) this.mTopicList.get(i)).getUid())) {
                ((TopicInfo) this.mTopicList.get(i)).getUserinfo().setNickname(rename);
                this.mHomeAdapter.notifyDataSetChanged();
            }
        }
    }

    private void deleteBlockUserTopic(String uid) {
        for (int i = this.mTopicList.size() - 1; i >= 0; i--) {
            if (uid.equals(((TopicInfo) this.mTopicList.get(i)).getUid())) {
                this.mTopicList.remove(i);
                this.mHomeAdapter.notifyDataSetChanged();
            }
        }
    }

    private void delComment(View v) {
//        this.mDialog.dismiss();
//        Object[] params = (Object[]) ((View) v.getParent()).getTag();
//        String topicId = params[1];
//        String commentId = params[3];
//        QGHttpRequest.getInstance().delCommentRequest(getActivity(), topicId, commentId, new AnonymousClass10(getActivity(), ((Integer) params[0]).intValue(), ((Integer) params[2]).intValue()));
    }

    public void requestActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 4:
                updateTopicData(data);
            case FAV_RESULT /*15*/:
                int listPosition = data.getIntExtra("listPos", 0);
                int isFav = data.getIntExtra("isFav", 0);
                if (this.mTopicList.size() > listPosition) {
                    ((TopicInfo) this.mTopicList.get(listPosition)).setIsfav(Integer.valueOf(isFav));
                }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void updateTopicData(Intent data) {
        try {
            ArrayList<TopicInfo> topicInfos = (ArrayList) data.getSerializableExtra("topics");
            if (topicInfos != null && topicInfos.size() > 0) {
                int clickPos = this.mHomeAdapter.mClickListPosition;
                if (clickPos != -1 && topicInfos.size() > 0) {
                    TopicInfo backTopicInfo = (TopicInfo) topicInfos.get(0);
                    TopicInfo topicInfo = (TopicInfo) this.mTopicList.get(clickPos);
                    if (backTopicInfo != null) {
                        List<Comment> commentList = topicInfo.getCommentlist();
                        if (commentList == null || commentList.size() <= 0) {
                            List<Comment> hotComment = new ArrayList();
                            topicInfo.setCommentlist(commentList);
                        } else {
                            commentList.clear();
                        }
                        topicInfo.getCommentlist().addAll(backTopicInfo.getCommentlist());
                        topicInfo.setLikenum(backTopicInfo.getLikenum());
                        topicInfo.setIslike(backTopicInfo.getIslike());
                        topicInfo.setTotalcomment(backTopicInfo.getTotalcomment());
                        topicInfo.setUserisrepost(backTopicInfo.getUserisrepost());
                        topicInfo.setRepostnum(backTopicInfo.getRepostnum());
                        topicInfo.setViews(backTopicInfo.getViews());
                        this.mHomeAdapter.updateTopic(clickPos);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getStatusbarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            this.statusBarHeight = getResources().getDimensionPixelSize(Integer.parseInt(c.getField("status_bar_height").get(c.newInstance()).toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
