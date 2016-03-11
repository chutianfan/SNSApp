package com.gitrose.mobile;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.os.Handler;
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
//import com.sina.weibo.sdk.component.WidgetRequestParam;
//import com.sina.weibo.sdk.openapi.InviteAPI;
//import com.tencent.connect.share.QzoneShare;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.adapter.HomeFollowAdapter;
//import com.gitrose.mobile.adapter.RecommendListAdapter;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.avsdk.control.Util;
import com.gitrose.mobile.base.BaseUploadFragment;
import com.gitrose.mobile.base.ITopicDisplay;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.dao.RequestDao;
//import com.gitrose.mobile.download.video.DownLoadClientImpl;
//import com.gitrose.mobile.download.video.DownLoadObserver;
//import com.gitrose.mobile.download.video.VideoDownloader;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpHandlerAsyn;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.Comment;
//import com.gitrose.mobile.model.RecommendDomin;
import com.gitrose.mobile.model.TopicInfoList;
import com.gitrose.mobile.model.UploadTopic;
import com.gitrose.mobile.model.VideoDownLoad;
import com.gitrose.mobile.utils.Base64Coder;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.utils.ShareUtils;
//import com.gitrose.mobile.utils.VideoUtils;
import com.gitrose.mobile.view.AutoCoverFlowHelper;
import com.gitrose.mobile.view.AutoCoverFlowHelper.OnScrollListPosListener;
import com.gitrose.mobile.view.BaseDialog;
import com.gitrose.mobile.view.QuickReturnListViewOnScrollListener;
import com.gitrose.mobile.view.TipPop;
import com.gitrose.mobile.view.TopTip;
import com.gitrose.mobile.view.TopicMore;
//import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
//import io.rong.imlib.RongIMClient;
import org.apache.http.Header;

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
import shouji.gexing.framework.utils.UiUtils;

@SuppressLint({"ValidFragment"})
public class HomeFollowFragment extends BaseUploadFragment implements OnClickListener, IXListViewListener, OnTouchListener, OnScrollListPosListener, OnDismissListener, ITopicDisplay {
    public static final int ADD_TOPIC_RESULT = 10;
    public static final int DOWN_MESSAGE = 336;
    public static final int FAV_RESULT = 15;
    public static final String GET_WORKS_ID = "works_id";
    public static final int IS_BLOCK_RESULT = 16;
    public static final String REQUEST_SIZE = "17";
    public static final String SP_KEY = "topic_list";
    public static final String SP_KEY_FRIEND = "topic_list_friend";
    private static HomeFollowFragment followFragment;
//    public static RongIMClient mRongIMClient;
    private View headView;
    private LinearLayout home_bottom_bar;
    public ImageView home_follow_point;
    private boolean isHot;
    private View loginView;
    private BaseDialog logindialog;
    private int mCurrentPosition;
    private View mDelCommentDialogView;
    private TipPop mDelCommentTipPop;
    private BaseDialog mDialog;
//    private VideoDownloader mDownloader;
    private AutoCoverFlowHelper mFlowHelper;
    private GestureDetector mGestureDetector;
    private Handler mHandler;
    private HomeFollowAdapter mHomeAdapter;
    private boolean mIsDelCommentFinish;
    private boolean mIsFinishRequest;
    private boolean mIsPause;
    private HashMap<String, Integer> mLocalTopicMap;
    private MediaPlayer mMediaPlayer;
    private View mMoreView;
//    private QavsdkControl mQavsdkControl;
    private RequestDao mRequestDao;
    private TutuUsers mSelfUserInfo;
    private ImageView mTakePhotoImg;
    private View mTakePicDialogView;
    private RelativeLayout mTitleBarRl;
    private TopicBroadcastReceiver mTopicBroadcastReceiver;
    private List<TopicInfo> mTopicList;
    private XListView mTopicLv;
    TopicInfo mTopicinfo;
//    private RecommendListAdapter recommendAdapter;
//    private ArrayList<RecommendDomin> recommendList;
    private QuickReturnListViewOnScrollListener scrollListener;
    ShareUtils share_util;
    private TextView tv_home_fav;
    private TextView tv_home_friend;
    private int uploadLocalView;

    /* renamed from: com.gitrose.mobile.HomeFollowFragment.11 */
    class AnonymousClass11 implements Runnable {
        private final /* synthetic */ Intent val$intent;

        AnonymousClass11(Intent intent) {
            this.val$intent = intent;
        }

        public void run() {
            HomeFollowFragment.this.share_util = new ShareUtils();
//            HomeFollowFragment.this.share_util.initShare(HomeFragmentActivity.getIntance(), HomeFollowFragment.this.getActivity(), this.val$intent);
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFollowFragment.1 */
    class C06821 implements OnCompletionListener {
        C06821() {
        }

        public void onCompletion(MediaPlayer mp) {
            HomeFollowFragment.this.mFlowHelper.startAutoFlow(HomeFollowFragment.this.mCurrentPosition + 1, true, true);
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFollowFragment.2 */
    class C06832 implements OnErrorListener {
        C06832() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
//            MobclickAgent.onEvent(HomeFollowFragment.this.getActivity(), "media_player_error");
            HomeFollowFragment.this.mFlowHelper.pauseVideoPlay(true);
            return true;
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFollowFragment.5 */
    class C06845 implements Runnable {
        C06845() {
        }

        public void run() {
            HomeFollowFragment.this.mTopicLv.setSelection(0);
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFollowFragment.8 */
    class C06858 implements OnClickListener {
        C06858() {
        }

        public void onClick(View v) {
            HomeFollowFragment.this.mDialog.show(HomeFollowFragment.this.mDelCommentDialogView);
        }
    }

    class GestureListener extends SimpleOnGestureListener {
        GestureListener() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            HomeFollowFragment.this.backTop();
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
                    HomeFollowFragment.this.deleteTopic(topicId);
                } else if (localTopicId != null) {
                    HomeFollowFragment.this.deleteLocalTopic(localTopicId);
                }
                HomeFollowFragment.this.mHomeAdapter.notifyDataSetChanged();
            } else if (Constant.BLOCK_TOPIC_ACTION.equals(intent.getAction())) {
//                uid = intent.getStringExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID);
//                if (uid != null) {
//                    HomeFollowFragment.this.deleteBlockUserTopic(uid);
//                }
            } else if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                String SYSTEM_HOME_KEY = "homekey";
                String reason = intent.getStringExtra("reason");
                if (reason != null && SYSTEM_HOME_KEY.equals(reason)) {
                    MyApplication.getInstance().setClick_home(true);
                }
            } else if (Constant.MODIFY_MARKNAME_ACTION.equals(intent.getAction())) {
//                uid = intent.getStringExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID);
//                String rename = intent.getStringExtra("remark");
//                if (uid != null && rename != null) {
//                    HomeFollowFragment.this.modifyMarkName(uid, rename);
//                }
            } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                HomeFollowFragment.this.notifyNetStatus();
            } else if (Constant.ZAN_TOPIC_ACTION.equals(intent.getAction())) {
                String zan_topic_id = intent.getStringExtra("topic_zan_topicId");
                int zan_status = intent.getIntExtra("topic_zan_status", 0);
                if (HomeFollowFragment.this.mTopicList != null) {
//                    if (HomeFollowFragment.this.mTopicList != null) {
//                        if (HomeFollowFragment.this.mTopicList.size() > 0) {
//                            i = 0;
//                            while (true) {
//                                if (i < HomeFollowFragment.this.mTopicList.size()) {
//                                    topicInfo = (TopicInfo) HomeFollowFragment.this.mTopicList.get(i);
//                                    if (!(topicInfo == null || topicInfo.getTopicid() == null || zan_topic_id == null)) {
//                                        if (topicInfo.getTopicid().equals(zan_topic_id)) {
//                                            topicInfo.setIslike(zan_status);
//                                            return;
//                                        }
//                                    }
//                                    i++;
//                                } else {
//                                    return;
//                                }
//                            }
//                        }
//                    }
                }
            } else if (Constant.ADD_FAV_TOPIC_ACTION.equals(intent.getAction())) {
                topicId = intent.getStringExtra("topicId");
                if (HomeFollowFragment.this.mTopicList != null) {
//                    if (HomeFollowFragment.this.mTopicList != null) {
//                        if (HomeFollowFragment.this.mTopicList.size() > 0) {
//                            i = 0;
//                            while (true) {
//                                if (i < HomeFollowFragment.this.mTopicList.size()) {
//                                    topicInfo = (TopicInfo) HomeFollowFragment.this.mTopicList.get(i);
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
//                        }
//                    }
                }
            } else if (Constant.DEL_FAV_TOPIC_ACTION.equals(intent.getAction())) {
                topicId = intent.getStringExtra("topicId");
                if (HomeFollowFragment.this.mTopicList != null) {
                    if (HomeFollowFragment.this.mTopicList != null) {
                        if (HomeFollowFragment.this.mTopicList.size() > 0) {
//                            i = 0;
//                            while (true) {
//                                if (i < HomeFollowFragment.this.mTopicList.size()) {
//                                    topicInfo = (TopicInfo) HomeFollowFragment.this.mTopicList.get(i);
//                                    if (topicInfo != null) {
//                                        if (topicInfo.getTopicid().equals(topicId)) {
//                                            topicInfo.setIsfav(Integer.valueOf(0));
//                                            HomeFollowFragment.this.mHomeAdapter.notifyDataSetChanged();
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
            } else if (Constant.UNFOLLOW_USER_ACTION.equals(intent.getAction()) || Constant.FOLLOW_USER_ACTION.equals(intent.getAction())) {
//                uid = intent.getStringExtra("to_uid");
//                String relation = intent.getStringExtra("relation");
//                if (HomeFollowFragment.this.mTopicList != null) {
//                    if (HomeFollowFragment.this.mTopicList != null) {
//                        if (HomeFollowFragment.this.mTopicList.size() > 0) {
//                            boolean isFound = false;
//                            i = 0;
//                            while (true) {
//                                if (i >= HomeFollowFragment.this.mTopicList.size()) {
//                                    break;
//                                }
//                                topicInfo = (TopicInfo) HomeFollowFragment.this.mTopicList.get(i);
//                                if (topicInfo != null) {
//                                    TutuUsers users = topicInfo.getUserinfo();
//                                    if (!(users == null || users.getUid() == null || uid == null)) {
//                                        if (users.getUid().equals(uid)) {
//                                            users.setRelation(relation);
//                                            isFound = true;
//                                        }
//                                    }
//                                }
//                                i++;
//                            }
//                            if (isFound) {
//                                HomeFollowFragment.this.mHomeAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                }
            }
        }
    }

    class UpLoadTopic {
        Comment comment;
        TopicInfo topic;
        UploadTopic uploadTopic;

        public UpLoadTopic(UploadTopic uploadTopic, TopicInfo topic, Comment comment) {
            this.uploadTopic = uploadTopic;
            this.topic = topic;
            this.comment = comment;
        }

        public UploadTopic getUploadTopic() {
            return this.uploadTopic;
        }

        public void setUploadTopic(UploadTopic uploadTopic) {
            this.uploadTopic = uploadTopic;
        }

        public TopicInfo getTopic() {
            return this.topic;
        }

        public void setTopic(TopicInfo topic) {
            this.topic = topic;
        }

        public Comment getComment() {
            return this.comment;
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFollowFragment.3 */
    class C14013 extends QuickReturnListViewOnScrollListener {
        C14013(QuickReturnType $anonymous0, View $anonymous1, int $anonymous2, View $anonymous3, int $anonymous4) {
            super($anonymous0, $anonymous1, $anonymous2, $anonymous3, $anonymous4);
        }

        protected void onRealScroll(AbsListView listview, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            HomeFollowFragment.this.mFlowHelper.startAutoFlow(visibleItemCount);
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFollowFragment.4 */
    class C14024 implements IUploadCall {
        C14024() {
        }

        public void gpuback(File file) {
//            MyApplication.getInstance().themeFile = file;
//            Intent intent = new Intent(HomeFollowFragment.this.getActivity(), FilterAndStickerActivity.class);
//            intent.putExtra(QzoneShare.SHARE_TO_QQ_IMAGE_URL, MyApplication.getInstance().themeFile.getAbsolutePath());
//            HomeFollowFragment.this.startActivityForNew(intent);
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFollowFragment.6 */
    class C16546 extends QGHttpHandler<String> {
        C16546(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(String data) {
//            HomeFollowFragment.this.startActivity(new Intent(HomeFollowFragment.this.getActivity(), AvPrepareLiveActivity.class));
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            TopTip.show(HomeFollowFragment.this.getActivity(), (RelativeLayout) HomeFollowFragment.this.getActivity().findViewById(R.id.container_rl), HomeFollowFragment.this.getResources().getString(R.string.err_tips), responseString, -1);
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFollowFragment.7 */
    class C16557 extends QGHttpHandler<TopicInfoList> {
        private final /* synthetic */ String val$direction;

        C16557(Context $anonymous0, String str) {

            super($anonymous0);
            this.val$direction = str;

        }

        public void onGetDataSuccess(TopicInfoList data) {
            if (data != null) {
//                HomeFollowFragment.this.saveTopicInfoListToLocal(data);
//                if (data.getDeletelocallist() == 1) {
//                    HomeFollowFragment.this.mTopicList.clear();
//                }
//                HomeFollowFragment.this.filterTopics(data);
//                List<TopicInfo> topics = data.getList();
//                if (topics != null && topics.size() > 0) {
//                    int requestSize = topics.size();
//                    if (requestSize > 0) {
//                        HomeFollowFragment.this.mFlowHelper.stopAutoFlow();
//                        if (SearchActivity.UP.equals(this.val$direction)) {
//                            HomeFollowFragment.this.mTopicList.addAll(0, data.getList());
//                            HomeFollowFragment.this.moveUploadingTopicPos(requestSize);
//                            if (HomeFollowFragment.this.mTopicList.size() > 0) {
//                                HomeFollowFragment.this.mTopicLv.setSelection(0);
//                                HomeFollowFragment.this.mHomeAdapter.notifyDataSetChanged();
//                            }
//                            MyApplication.getInstance().setNewfollowhtcount(0);
//                            MyApplication.getInstance().setNewfollowpoicount(0);
//                        } else {
//                            HomeFollowFragment.this.mTopicList.addAll(topics);
//                            HomeFollowFragment.this.mHomeAdapter.notifyDataSetChanged();
//                        }
//                        HomeFollowFragment.this.mTopicLv.setPullLoadEnable(true);
//                    }
//                }
            }
        }

        public void onFinish() {
            HomeFollowFragment.this.onRefreshComplete();
            HomeFollowFragment.this.mIsFinishRequest = true;
            super.onFinish();
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFollowFragment.9 */
    class C16569 extends QGHttpHandler<String> {
        private final /* synthetic */ int val$commentPos;
        private final /* synthetic */ int val$listPos;

        C16569(Context $anonymous0, int i, int i2) {

            super($anonymous0);
            this.val$listPos = i;
            this.val$commentPos = i2;

        }

        public void onGetDataSuccess(String data) {
            HomeFollowFragment.this.mIsDelCommentFinish = true;
            if (HomeFollowFragment.this.mDelCommentTipPop != null) {
                HomeFollowFragment.this.mDelCommentTipPop.dismiss();
            }
            TopicInfo topicInfo = (TopicInfo) HomeFollowFragment.this.mTopicList.get(this.val$listPos);
            topicInfo.setTotalcomment(topicInfo.getTotalcomment() - 1);
            topicInfo.getCommentlist().remove(this.val$commentPos);
//            HomeFollowFragment.this.mHomeAdapter.notifyCommentPos(this.val$listPos, this.val$commentPos, null);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            if (HomeFollowFragment.this.mDelCommentTipPop != null) {
                HomeFollowFragment.this.mDelCommentTipPop.dismiss();
            }
            Toast.makeText(HomeFollowFragment.this.getActivity(), HomeFollowFragment.this.getString(R.string.network_error_tip), Toast.LENGTH_LONG).show();
        }
    }

    /* renamed from: com.gitrose.mobile.HomeFollowFragment.10 */
    class AnonymousClass10 extends QGHttpHandlerAsyn<TopicInfo> {
        private final /* synthetic */ Comment val$comment;
        private final /* synthetic */ String val$picPath;
        private final /* synthetic */ TopicInfo val$topic;
        private final /* synthetic */ File val$uploadPicFile;
        private final /* synthetic */ UploadTopic val$uploadTopic;
        private final /* synthetic */ String val$videoFilePath;

        AnonymousClass10(Context $anonymous0, String str, TopicInfo topicInfo, File file, UploadTopic uploadTopic, String str2, Comment comment) {

            super($anonymous0);
            this.val$videoFilePath = str;
            this.val$topic = topicInfo;
            this.val$uploadPicFile = file;
            this.val$uploadTopic = uploadTopic;
            this.val$picPath = str2;
            this.val$comment = comment;

        }

        public void onGetDataSuccess(TopicInfo data) {
//            MobclickAgent.onEvent(HomeFollowFragment.this.getActivity(), "send_topic_success_num");
//            if (data != null) {
//                if (!(data.getVideourl() == null || this.val$videoFilePath == null)) {
//                    VideoUtils.renameAndMoveFile(HomeFollowFragment.this.getActivity(), new File(this.val$videoFilePath), data.getVideourl());
//                }
//                String localTopicId = this.val$topic.getLocalRequestId();
//                if (localTopicId != null) {
//                    HomeFollowFragment.this.mTopicList.set(((Integer) HomeFollowFragment.this.mLocalTopicMap.get(localTopicId)).intValue(), data);
//                    HomeFollowFragment.this.mHomeAdapter.notifyDataSetChanged();
//                }
//                RequestService.sendUploadTopicSuccessBrocat(HomeFollowFragment.this.getActivity(), localTopicId, data);
//                HomeFollowFragment.this.mRequestDao.removeLocalTopic(localTopicId);
//                HomeFollowFragment.this.mLocalTopicMap.remove(localTopicId);
//                if (this.val$uploadPicFile.exists()) {
//                    this.val$uploadPicFile.delete();
//                }
//                HomeFollowFragment.this.shareToOther(this.val$picPath, data, this.val$uploadTopic.getSync());
//                return;
//            }
//            int pos = ((Integer) HomeFollowFragment.this.mLocalTopicMap.get(this.val$topic.getLocalRequestId())).intValue();
//            if (HomeFollowFragment.this.mTopicList.size() > pos && pos >= 0) {
//                ((TopicInfo) HomeFollowFragment.this.mTopicList.get(pos)).setEmptycommenttxt(null);
//                HomeFollowFragment.this.mHomeAdapter.notifyDataSetChanged();
//            }
        }

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {

        }
    }

    public HomeFollowFragment() {
        this.isHot = true;
        this.mCurrentPosition = -1;
        this.mIsFinishRequest = true;
    }

    public static HomeFollowFragment getInstance() {
        return followFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.isHot = bundle.getBoolean("isHot");
        }
        followFragment = this;
        View view = inflater.inflate(R.layout.activity_home_follow, null);
        this.mHandler = new Handler();
        initView(view);
        initData();
        return view;
    }

    public void startAVActivity(TopicInfo topicinfo) {
//        this.mTopicinfo = topicinfo;
//        MyApplication.getInstance().getUserinfo().setCreater(false);
//        if (!Util.isNetworkAvailable(getActivity())) {
//            Toast.makeText(getActivity(), getString(R.string.empty_news_tips_net), 0).show();
//        } else if (this.mTopicinfo.getUid() != null) {
//            Intent enter_intent = new Intent(getActivity(), AvActivity.class);
//            enter_intent.putExtra(Util.EXTRA_TOPICID, this.mTopicinfo.getTopicid());
//            enter_intent.putExtra("topicinfo", this.mTopicinfo);
//            enter_intent.putExtra("enterRoom", true);
//            startActivity(enter_intent);
//        }
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
        this.home_follow_point = (ImageView) view.findViewById(R.id.home_follow_point);
        view.findViewById(R.id.iv_add_friend).setOnClickListener(this);
        view.findViewById(R.id.img_take_photo).setOnClickListener(this);
        this.home_bottom_bar = (LinearLayout) view.findViewById(R.id.home_bottom_bar);
        this.tv_home_fav = (TextView) view.findViewById(R.id.tv_home_fav);
        this.tv_home_fav.setOnClickListener(this);
        this.tv_home_friend = (TextView) view.findViewById(R.id.tv_home_friend);
        this.tv_home_friend.setOnClickListener(this);
        if (this.isHot) {
            this.tv_home_fav.setSelected(true);
            this.tv_home_friend.setSelected(false);
        } else {
            this.tv_home_fav.setSelected(false);
            this.tv_home_friend.setSelected(true);
        }
        this.mTakePhotoImg = (ImageView) view.findViewById(R.id.img_take_photo);
        this.mTopicLv = (XListView) view.findViewById(R.id.lv_pic_list);
        this.mTitleBarRl = (RelativeLayout) view.findViewById(R.id.home_action_bar);
        this.mTopicList = new ArrayList();
        this.mHomeAdapter = new HomeFollowAdapter(getActivity(), this.mTopicLv, this.mTopicList, true, this);
        this.mHomeAdapter.setOnClickListener(this);
        this.headView = LayoutInflater.from(getActivity()).inflate(R.layout.home_recomand_headview, null);
//        this.recommendList = new ArrayList();
//        this.recommendAdapter = new RecommendListAdapter(getActivity(), this.recommendList, true);
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
//        this.mDownloader = new VideoDownloader(this, this.mTopicList);
//        DownLoadClientImpl.getInstance().registerObserver(this);
        this.logindialog = new BaseDialog(getActivity());
        this.loginView = View.inflate(getActivity(), R.layout.dialog_login_base_layout, null);
        ((TextView) this.loginView.findViewById(R.id.tv_tip_title)).setText(getResources().getString(R.string.login_tutu));
        this.loginView.findViewById(R.id.pop_login_tv_cancel).setOnClickListener(this);
        this.loginView.findViewById(R.id.pop_login_tv_submit).setOnClickListener(this);
    }

    private void initData() {
        this.mLocalTopicMap = new HashMap();
        this.mRequestDao = new RequestDao(getActivity());
        TopicInfoList topicInfoList = getTopicInfoListFromLocal();
        if (topicInfoList != null && !BaseConstants.ah.equals(topicInfoList)) {
            filterTopics(topicInfoList);
            List<TopicInfo> topics = topicInfoList.getList();
            if (topics.size() > 0) {
                this.mFlowHelper.stopAutoFlow();
                this.mTopicList.addAll(topics);
                this.mHomeAdapter.notifyDataSetChanged();
            }
        }
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
//        if (this.mDownloader != null) {
//            this.mDownloader.resumeVideo();
//        }
        if (this.mFlowHelper != null) {
            this.mFlowHelper.startAutoFlow(this.mCurrentPosition + this.mTopicLv.getHeaderViewsCount(), true, true);
        }
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
//        if (this.mDownloader != null) {
//            this.mDownloader.pauseVideo();
//        }
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
        this.mMediaPlayer.setOnCompletionListener(new C06821());
        this.mMediaPlayer.setOnErrorListener(new C06832());
    }

    private void addListViewListener() {
        int headerHeight = getResources().getDimensionPixelSize(R.dimen.action_bar_height);
        this.scrollListener = new C14013(QuickReturnType.HEADER, this.mTitleBarRl, -headerHeight, this.home_bottom_bar, getResources().getDimensionPixelSize(R.dimen.home_foot_height));
        this.scrollListener.setCanSlideInIdleScrollState(true);
        this.mTopicLv.setOnScrollListener(this.scrollListener);
    }

    private void initDialog() {
//        modifyPicture(new C14024(), true);
        this.mDialog = new BaseDialog(getActivity());
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
        this.mHandler.postDelayed(new C06845(), 600);
    }

    public void notifyData(VideoDownLoad t) {
        if (this.mCurrentPosition >= 0 && this.mTopicList.size() > this.mCurrentPosition) {
            TopicInfo topicInfo = (TopicInfo) this.mTopicList.get(this.mCurrentPosition);
            if (topicInfo != null && t.getUrl().equals(topicInfo.getVideourl())) {
                this.mFlowHelper.updateVideoProgress(t.getProgress(), t.getStatus(), t.getName());
            }
        }
    }

//    public VideoDownloader getDownloader() {
//        return this.mDownloader;
//    }

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
            this.mCurrentPosition = position;
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
    }

    public void onRefresh() {
//        MyApplication.getInstance().setNewFollowNum(0);
        if (getInstance() != null) {
            getInstance().home_follow_point.setVisibility(View.INVISIBLE);
        }
        if (HomeFragment.getInstance() != null) {
            HomeFragment.getInstance().home_follow_point.setVisibility(View.INVISIBLE);
        }
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
//                if (MyApplication.getInstance().isLogin()) {
//                    MobclickAgent.onEvent(getActivity(), "click_search");
//                    startActivityForNew(new Intent(getActivity(), SearchActivity.class));
//                    return;
//                }
//                this.logindialog.show(this.loginView);
                break;
            case R.id.tv_home_fav:
                this.tv_home_fav.setSelected(false);
                this.tv_home_friend.setSelected(true);
                switchToHotFragment(v);
                break;
            case R.id.img_take_photo:
//                MobclickAgent.onEvent(getActivity(), "click_photo");
//                takeTopic();
                break;
            case R.id.circleimg_current_show_avatar:
            case R.id.iv_user_icon:
                TopicInfo topic = (TopicInfo) v.getTag();
                if (topic != null) {
                    String isName = topic.getIskana();
                    if (isName == null || BaseConstants.ah.equals(isName)) {
                        startPersonalActivity(topic.getUid());
                    } else if (Integer.valueOf(isName).intValue() == 0) {
                        startPersonalActivity(topic.getUid());
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.theme_isname_tip), Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.share_tv_cancel:
            case R.id.photo_pop_tv_cancel:
                this.mDialog.dismiss();
                break;
            case R.id.pop_tv_submit:
                delComment(v);
                break;
            case R.id.pop_tv_cancel:
                this.mDialog.dismiss();
                if (this.mDelCommentTipPop != null) {
                    this.mDelCommentTipPop.dismiss();
                }
                break;
            case R.id.pop_login_tv_submit:
                startActivityForNew(new Intent(getActivity(), LoginActivity.class));
                this.logindialog.dismiss();
                break;
            case R.id.pop_login_tv_cancel:
                this.logindialog.dismiss();
                break;
            case R.id.photo_pop_tv_capture:
                this.mDialog.dismiss();
                getPicFromCapture();
                break;
            case R.id.photo_pop_tv_album:
//                this.mDialog.dismiss();
//                intent = new Intent(getActivity(), ChooImg2VideoActivity.class);
//                intent.putExtra("is_take_photo", true);
//                startActivityForNew(intent);
                break;
            case R.id.photo_pop_tv_live:
                this.mDialog.dismiss();
                this.mSelfUserInfo = MyApplication.getInstance().getUserinfo();
                checkCanLive();
                break;
            case R.id.take_small_video:
//                this.mDialog.dismiss();
//                startActivityForNew(new Intent(getActivity(), FFmpegRecorderActivity.class));
                break;
            case R.id.share_tv_block:
                blockTopic();
                break;
            case R.id.share_tv_report:
                reportTopic();
                break;
            case R.id.share_tv_fav:
                takeFav();
                break;
            case R.id.tv_topic_more:
            case R.id.tv_home_more:
//                TopicInfo topicInfo = (TopicInfo) v.getTag();
//                if (topicInfo != null) {
//                    intent = new Intent(getActivity(), ShareActivity.class);
//                    intent.putExtra(QzoneShare.SHARE_TO_QQ_TITLE, Constant.PREFENCES_NAME);
//                    intent.putExtra(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT, topicInfo.getDesc());
//                    intent.putExtra(Util.EXTRA_TOPICID, topicInfo.getTopicid());
//                    intent.putExtra("topic_uid", topicInfo.getUid());
//                    intent.putExtra(InviteAPI.KEY_URL, topicInfo.getContent());
//                    intent.putExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID, topicInfo.getUid());
//                    intent.putExtra("topicInfo", topicInfo);
//                    intent.putExtra("publish_nickname", topicInfo.getUserinfo().getNickname());
//                    startActivityForResult(intent, 0);
//                    showLayerMask();
//                    animationForBottom();
//                }
                break;
            default:
        }
    }

    private void checkCanLive() {
//        QGHttpRequest.getInstance().checkCanLive(getActivity(), new C16546(getActivity()));
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
        TopicInfo topicInfo = (TopicInfo) this.mMoreView.getTag();
        if (topicInfo != null) {
            TopicMore.reportTopic(getActivity(), this.mDialog, topicInfo.getTopicid());
        }
    }

    private void takeFav() {
        TopicInfo topicInfo = (TopicInfo) this.mMoreView.getTag();
        if (topicInfo != null) {
            TopicMore.takeFav(getActivity(), this.mDialog, topicInfo);
        }
    }

    private void blockTopic() {
        TopicInfo topicInfo = (TopicInfo) this.mMoreView.getTag();
        if (topicInfo != null) {
            TopicMore.blockTopic(getActivity(), this.mDialog, topicInfo.getUid());
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
        startLoginActivity();
    }

    private void startLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), LoginActivity.class);
        startActivityForNew(intent);
    }

    private final void showPublishTopicDialog() {
        if (this.mTakePicDialogView == null) {
            this.mTakePicDialogView = View.inflate(getActivity(), R.layout.dialog_take_photo, null);
            this.mTakePicDialogView.findViewById(R.id.photo_pop_tv_live).setOnClickListener(this);
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
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
//        if (up) {
//            requestData(SearchActivity.UP, startTopicId, endTopicId);
//        } else {
//            requestData(SearchActivity.DOWN, endTopicId, startTopicId);
//        }
    }

    private void requestData(String direction, String startTopicId, String endTopicId) {
//        if (this.mIsFinishRequest) {
//            this.mIsFinishRequest = false;
//            QGHttpRequest.getInstance().getHomeHotOrFriendList(getActivity(), startTopicId, endTopicId, REQUEST_SIZE, direction, this.isHot, new C16557(getActivity(), direction));
//        }
    }

    private void onRefreshComplete() {
        this.mTopicLv.stopRefresh();
        this.mTopicLv.stopLoadMore();
    }

    private void locationSelectedPos(int requestSize) {
//        this.scrollListener.setHeaderStopWork(true);
//        View v = this.mTopicLv.getChildAt(1);
//        int top = v == null ? UiUtils.getInstance(getActivity()).getmScreenWidth() / 4 : (UiUtils.getInstance(getActivity()).getmScreenWidth() / 4) + v.getTop();
//        this.mHomeAdapter.notifyDataSetChanged();
//        this.mTopicLv.setSelectionFromTop(requestSize + 1, top);
//        this.scrollListener.setHeaderStopWork(false);
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

    private void showDelCommentDialog() {
        if (this.mDelCommentDialogView == null) {
            this.mDelCommentDialogView = View.inflate(getActivity(), R.layout.dialog_base_layout, null);
            ((TextView) this.mDelCommentDialogView.findViewById(R.id.tv_tip_title)).setText(getResources().getString(R.string.topic_del_comment));
            this.mDelCommentDialogView.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
            this.mDelCommentDialogView.findViewById(R.id.pop_tv_submit).setOnClickListener(this);
            this.mDelCommentTipPop = new TipPop(getActivity(), new String[]{getResources().getString(R.string.delete)}, new C06858(), this);
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
                this.mHomeAdapter.notifyDataSetChanged();
            }
        }
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
                return;
            }
            i--;
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
        this.mDialog.dismiss();
        Object[] params = (Object[]) ((View) v.getParent()).getTag();
        String topicId = (String) params[1];
        String commentId = (String) params[3];
        QGHttpRequest.getInstance().delCommentRequest(getActivity(), topicId, commentId, new C16569(getActivity(), ((Integer) params[0]).intValue(), ((Integer) params[2]).intValue()));
    }

    public void requestActivityResult(int requestCode, int resultCode, Intent data) {
//        if (this.share_util != null) {
//            this.share_util.onActivityResult(requestCode, resultCode, data);
//            this.share_util = null;
//        }
//        switch (resultCode) {
//            case PersonalNewActivity.WORK_LIST_9 /*4*/:
//                updateTopicData(data);
//            case FAV_RESULT /*15*/:
//                int listPosition = data.getIntExtra("listPos", 0);
//                int isFav = data.getIntExtra("isFav", 0);
//                if (this.mTopicList.size() > listPosition) {
//                    ((TopicInfo) this.mTopicList.get(listPosition)).setIsfav(Integer.valueOf(isFav));
//                }
//            default:
//                super.onActivityResult(requestCode, resultCode, data);
//        }
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
                        List<Comment> comments = topicInfo.getCommentlist();
                        if (comments == null || comments.size() <= 0) {
                            topicInfo.setCommentlist(new ArrayList());
                        } else {
                            comments.clear();
                        }
                        topicInfo.getCommentlist().addAll(backTopicInfo.getCommentlist());
                        topicInfo.setLikenum(backTopicInfo.getLikenum());
                        topicInfo.setIslike(backTopicInfo.getIslike());
                        topicInfo.setTotalcomment(backTopicInfo.getTotalcomment());
                        topicInfo.setUserisrepost(backTopicInfo.getUserisrepost());
                        topicInfo.setRepostnum(backTopicInfo.getRepostnum());
                        topicInfo.setViews(backTopicInfo.getViews());
//                        this.mHomeAdapter.updateTopic(clickPos);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadTopic(UploadTopic uploadTopic) {
        try {
            if (MyApplication.getInstance().getUserinfo() != null) {
                SharedPreferences sp = getActivity().getSharedPreferences(Constant.PREFENCES_NAME, 0);
                String uid = MyApplication.getInstance().getUserinfo().getUid();
                TopicInfo topic = new TopicInfo();
                if (!(uid == null || BaseConstants.ah.equals(uid))) {
                    topic.setUid(uid);
                    String localTopicId = new StringBuilder(String.valueOf(uid)).append(System.currentTimeMillis()).toString();
                    topic.setType(Integer.valueOf(uploadTopic.getType()));
                    topic.setLocalRequestId(localTopicId);
                    topic.setContent("file://" + uploadTopic.getPicFile());
                    topic.setWidth(new StringBuilder(String.valueOf(uploadTopic.getPic_width())).toString());
                    topic.setHeight(new StringBuilder(String.valueOf(uploadTopic.getPic_height())).toString());
                    topic.setAddtime(new StringBuilder(String.valueOf(System.currentTimeMillis())).toString());
                }
                TutuUsers userinfo = new TutuUsers();
                userinfo.setNickname(sp.getString(Constant.NICK_NAME_PERFERENCE, getActivity().getResources().getString(R.string.wo)));
                userinfo.setAvatartime(sp.getString(Constant.AVATAR_TIME_PERFERENCE, BaseConstants.ah));
                topic.setUserinfo(userinfo);
                if (uploadTopic.getType() == 5) {
                    topic.setVideourl("file://" + uploadTopic.getVideoFile());
                    topic.setVideotimes(uploadTopic.getVideoTime());
                }
                topic.setDesc(uploadTopic.getTopicDesc());
                topic.setPoiid(uploadTopic.getPointId());
                topic.setPoitext(uploadTopic.getPointText());
                String iskana = uploadTopic.getIsName();
                if (iskana == null || BaseConstants.ah.equals(iskana)) {
                    topic.setIskana(BaseConstants.f3860s);
                } else {
                    topic.setIskana(iskana);
                }
                topic.setEmptycommenttxt(getResources().getString(R.string.upload_topic_tips));
                this.mLocalTopicMap.put(topic.getLocalRequestId(), Integer.valueOf(0));
                this.mTopicList.add(0, topic);
                this.mHomeAdapter.notifyDataSetChanged();
                backTop();
                this.mFlowHelper.startAutoFlow(1, true, false);
//                this.mRequestDao.addLocalTopic(topic);
                uploadTopic(uploadTopic, topic, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadTopic(UploadTopic uploadTopic, TopicInfo topic, Comment comment) {
//        if (NetUtils.checkNet(getActivity()) || comment == null) {
//            String picPath = uploadTopic.getPicFile();
//            File uploadPicFile = new File(picPath);
//            String videoFilePath = uploadTopic.getVideoFile();
//            if (uploadTopic.getType() != 5 || (uploadTopic.getOssPicUri() != null && !uploadTopic.getOssPicUri().equals(BaseConstants.ah) && uploadTopic.getOssVideoUri() != null && !uploadTopic.getOssVideoUri().equals(BaseConstants.ah))) {
//                if (uploadTopic.getType() != 1 || (uploadTopic.getOssPicUri() != null && !uploadTopic.getOssPicUri().equals(BaseConstants.ah))) {
//                    MobclickAgent.onEvent(getActivity(), "send_topic_total_num");
//                    QGHttpRequest.getInstance().addTopicRequestNew(getActivity(), uploadTopic.getType(), uploadTopic.getOssPicUri(), uploadTopic.getOssVideoUri(), uploadTopic.getVideoTime(), uploadTopic.getTopicDesc(), uploadTopic.getPointId(), uploadTopic.getPointText(), uploadTopic.getIsName(), new StringBuilder(String.valueOf(uploadTopic.getPic_width())).toString(), new StringBuilder(String.valueOf(uploadTopic.getPic_height())).toString(), new AnonymousClass10(getActivity(), videoFilePath, topic, uploadPicFile, uploadTopic, picPath, comment));
//                    return;
//                }
//                return;
//            }
//            return;
//        }
//        this.mRequestDao.insertComment(comment);
//        ((TopicInfo) this.mTopicList.get(((Integer) this.mLocalTopicMap.get(topic.getLocalRequestId())).intValue())).setEmptycommenttxt(getString(R.string.upload_topic_failure_tip));
//        this.mHomeAdapter.notifyDataSetChanged();
    }

    public void shareToOther(String picPath, TopicInfo data, int themeSync) {
//        String topText = null;
//        String platform = null;
//        if (themeSync == 1) {
//            topText = getString(R.string.theme_tip_top_1);
//            platform = "QZONE";
//        } else if (themeSync == 2) {
//            topText = getString(R.string.theme_tip_top_2);
//            platform = "SINA";
//        } else if (themeSync == 3) {
//            topText = getString(R.string.theme_tip_top_3);
//            platform = "WEIXIN_CIRCLE";
//        } else if (themeSync == 4) {
//            topText = getString(R.string.theme_tip_top_4);
//            platform = "FACEBOOK";
//        } else if (themeSync == 5) {
//            topText = getString(R.string.theme_tip_top_5);
//            platform = "TWITTER";
//        }
//        TopTip.showThemeSuccesss(getActivity(), (RelativeLayout) getActivity().findViewById(R.id.container_rl), getString(R.string.theme_tip_top_success), topText, picPath, getResources().getDimensionPixelOffset(R.dimen.theme_top_tip_height));
//        if (platform != null) {
//            Intent intent = new Intent();
//            intent.putExtra(Util.EXTRA_TOPICID, data.getTopicid());
//            intent.putExtra(InviteAPI.KEY_URL, data.getContent());
//            intent.putExtra("publish_nickname", data.getUserinfo().getNickname());
//            intent.putExtra("is_redpacket", data.getIsredpacket());
//            intent.putExtra(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT, data.getDesc());
//            intent.putExtra("share_to_name", platform);
//            this.mHandler.postDelayed(new AnonymousClass11(intent), 3000);
//        }
    }
}
