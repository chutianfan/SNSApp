package com.gitrose.mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.handmark.pulltorefresh.library.QuickReturnType;
import com.nostra13.universalimageloader.core.ImageLoader;
//import com.sina.weibo.sdk.component.WidgetRequestParam;
//import com.sina.weibo.sdk.openapi.InviteAPI;
//import com.tencent.connect.share.QzoneShare;
//import com.tencent.openqq.protocol.imsdk.im_common;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.adapter.HomeFollowAdapter;
import com.gitrose.mobile.adapter.TopicEmotionAdapter;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.avsdk.control.Util;
import com.gitrose.mobile.base.BaseFragmentActivity;
import com.gitrose.mobile.base.ITopicDisplay;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.dao.RequestDao;
import com.gitrose.mobile.download.video.DownLoadClientImpl;
import com.gitrose.mobile.download.video.DownLoadObserver;
import com.gitrose.mobile.download.video.VideoDownloader;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.Comment;
import com.gitrose.mobile.model.CommentEmotion;
import com.gitrose.mobile.model.VideoDownLoad;
import com.gitrose.mobile.utils.CommentPicUtils;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.view.AutoCoverFlowHelper;
import com.gitrose.mobile.view.BaseDialog;
import com.gitrose.mobile.view.CircleImageView;
import com.gitrose.mobile.view.KeyboardRelativeLayout;
import com.gitrose.mobile.view.KeyboardRelativeLayout.IOnKeyboardStateChangedListener;
import com.gitrose.mobile.view.QuickReturnListViewOnScrollListener;
import com.gitrose.mobile.view.TipPop;
import com.gitrose.mobile.view.TopTip;
import com.gitrose.mobile.view.TopicEmotionView;
import com.gitrose.mobile.view.TopicMore;
//import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.http.Header;
import org.litepal.util.Const.TableSchema;
import qalsdk.BaseConstants;
//import qalsdk.ab;

public class TopicDetailPagerActivity extends BaseFragmentActivity implements OnClickListener, ITopicDisplay, OnDismissListener, DownLoadObserver<VideoDownLoad>, OnTouchListener, IOnKeyboardStateChangedListener {
    public static int CLICK_FRAGMENT_POS = 0;
    public static final String COMMENT_ID = "comment_id";
    public static final int FAV_TOPIC = 1;
    public static final String LIST_POS = "list_pos";
    public static final int MY_TOPIC = 0;
    public static final int USER_IS_RESPORT = 1;
    public static boolean flag;
    private static TopicDetailPagerActivity topicDetailPagerActivity;
    private CircleImageView avatorView;
    private ImageView backImg;
    private int currrentPos;
    private TipPop delCommentPop;
    private ImageView delImg;
    private OnItemClickListener emotionTitleClick;
    private ArrayList<CommentEmotion> emotions;
    public EditText et_topic_comment;
    private boolean from_personal;
    private GridView gv_emotion;
    private int isBlock;
    private boolean isCheckPlayVideo;
    public boolean isFinish;
    private boolean isFromHomePage;
    private boolean isPlay;
    private boolean isSelf;
    private boolean isWork;
    private ImageView iv_topic_emotion;
    private LinearLayout ll_emotion_page;
    private boolean lookRedPacket;
    private BroadcastReceiver mBroadcastReceiver;
    private View mDelTopicView;
    private GestureDetector mDetector;
    private BaseDialog mDialog;
    private VideoDownloader mDownloader;
    private AutoCoverFlowHelper mFlowHelper;
    private TopicDetailFragment[] mFragments;
    private GestureDetector mGestureDetector;
    private SimpleOnGestureListener mGestureListener;
    private Handler mHandler;
    private View mHeadView;
    private InputMethodManager mInputMethodManager;
    private boolean mIsDelCommentFinish;
    private boolean mIsNewVersion;
    private boolean mIsPause;
    private MediaPlayer mMediaPlayer;
    private View mMoreView;
    private ViewPager mPager;
    private RequestDao mRequestDao;
    private final int mScrollableItems;
    private HomeFollowAdapter mTopicAdapter;
    private ArrayList<TopicInfo> mTopicInfos;
    public Comment replyComment;
    private RelativeLayout rl_emotion_bottom;
    private QuickReturnListViewOnScrollListener scrollListener;
    private TopicDetailPagerAdapter topicDetailPagerAdapter;
    private TopicEmotionAdapter topicEmotionAdapter;
    private String topicUid;
    private int type;
    private String userId;
    private View view_block;

    /* renamed from: com.gitrose.mobile.TopicDetailPagerActivity.1 */
    class C08171 implements OnItemClickListener {
        C08171() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            TopicDetailPagerActivity.this.topicEmotionAdapter.setSelectedPosition(position);
            TopicDetailPagerActivity.this.topicEmotionAdapter.notifyDataSetChanged();
            TopicDetailPagerActivity.this.ll_emotion_page.removeAllViews();
            TopicDetailPagerActivity.this.ll_emotion_page.addView(new TopicEmotionView(TopicDetailPagerActivity.this, TopicDetailPagerActivity.this.rl_emotion_bottom, ((CommentEmotion) TopicDetailPagerActivity.this.emotions.get(position)).getList()));
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailPagerActivity.2 */
    class C08182 extends BroadcastReceiver {
        C08182() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int i;
            if (action.equals(Constant.UPLOAD_TOPIC_FINISH_ACTION)) {
                String localTopicId = intent.getStringExtra("localTopicId");
                TopicInfo topic = (TopicInfo) intent.getSerializableExtra("topic");
                i = TopicDetailPagerActivity.MY_TOPIC;
                while (i < TopicDetailPagerActivity.this.mTopicInfos.size()) {
                    if (localTopicId != null && localTopicId.equals(((TopicInfo) TopicDetailPagerActivity.this.mTopicInfos.get(i)).getLocalRequestId())) {
                        TopicDetailPagerActivity.this.mTopicInfos.set(i, topic);
                    }
                    i += TopicDetailPagerActivity.USER_IS_RESPORT;
                }
            } else if (action.equals(Constant.ZAN_TOPIC_ACTION)) {
                String zan_topic_id = intent.getStringExtra("topic_zan_topicId");
                int zan_status = intent.getIntExtra("topic_zan_status", TopicDetailPagerActivity.MY_TOPIC);
                for (i = TopicDetailPagerActivity.MY_TOPIC; i < TopicDetailPagerActivity.this.mTopicInfos.size(); i += TopicDetailPagerActivity.USER_IS_RESPORT) {
                    TopicInfo topicInfo = (TopicInfo) TopicDetailPagerActivity.this.mTopicInfos.get(i);
                    if (topicInfo.getTopicid().equals(zan_topic_id)) {
                        topicInfo.setIslike(zan_status);
                        return;
                    }
                }
            } else if (action.equals(Constant.REPOST_TOPIC_ACTION)) {
                String repost_topic_id = intent.getStringExtra("topic_repost_topicId");
                for (i = TopicDetailPagerActivity.MY_TOPIC; i < TopicDetailPagerActivity.this.mTopicInfos.size(); i += TopicDetailPagerActivity.USER_IS_RESPORT) {
                    TopicInfo topicInfo = (TopicInfo) TopicDetailPagerActivity.this.mTopicInfos.get(i);
                    if (topicInfo.getTopicid().equals(repost_topic_id)) {
                        topicInfo.setFromrepost(Constant.SYSTEM_UID);
                        return;
                    }
                }
            } else if (action.equals(Constant.DEL_REPOST_TOPIC_ACTION)) {
                if (TopicDetailPagerActivity.this.isSelf) {
                    int pageNum = TopicDetailPagerActivity.this.mPager.getCurrentItem();
                    TopicDetailPagerActivity.this.mPager.setAdapter(null);
                    TopicDetailPagerActivity.this.mTopicInfos.remove(pageNum);
                    if (TopicDetailPagerActivity.this.mTopicInfos.size() == 0) {
                        TopicDetailPagerActivity.this.finish();
                        return;
                    }
                    TopicDetailPagerActivity.this.getFlowHelper().pauseVideoPlay();
                    TopicDetailPagerActivity.this.mPager.setAdapter(new TopicDetailPagerAdapter(TopicDetailPagerActivity.this.getSupportFragmentManager()));
                    if (pageNum - 1 >= 0) {
                        TopicDetailPagerActivity.this.currrentPos = pageNum - 1;
                    } else {
                        TopicDetailPagerActivity.this.currrentPos = TopicDetailPagerActivity.MY_TOPIC;
                    }
                    TopicDetailPagerActivity.this.mPager.setCurrentItem(TopicDetailPagerActivity.this.currrentPos);
                    TopicDetailFragment topicDetailFragment = TopicDetailPagerActivity.this.getFragmentByPos(TopicDetailPagerActivity.this.currrentPos);
                    if (topicDetailFragment != null) {
                        topicDetailFragment.requestTopicInfo();
                        return;
                    }
                    return;
                }
                String repost_topic_id = intent.getStringExtra("topic_repost_topicId");
                for (i = TopicDetailPagerActivity.MY_TOPIC; i < TopicDetailPagerActivity.this.mTopicInfos.size(); i += TopicDetailPagerActivity.USER_IS_RESPORT) {
                    TopicInfo topicInfo = (TopicInfo) TopicDetailPagerActivity.this.mTopicInfos.get(i);
                    if (topicInfo.getTopicid().equals(repost_topic_id)) {
                        topicInfo.setFromrepost(BaseConstants.f3860s);
                        return;
                    }
                }
            } else if (action.equals(Constant.DEL_FAV_TOPIC_ACTION) && TopicDetailPagerActivity.this.isSelf) {
                String topicId = intent.getStringExtra("topicId");
                int pageNum = TopicDetailPagerActivity.this.mPager.getCurrentItem();
                for (i = TopicDetailPagerActivity.MY_TOPIC; i < TopicDetailPagerActivity.this.mTopicInfos.size(); i += TopicDetailPagerActivity.USER_IS_RESPORT) {
                    if (topicId.equals(((TopicInfo) TopicDetailPagerActivity.this.mTopicInfos.get(i)).getTopicid())) {
                        TopicDetailPagerActivity.this.mPager.setAdapter(null);
                        TopicDetailPagerActivity.this.mTopicInfos.remove(pageNum);
                        if (TopicDetailPagerActivity.this.mTopicInfos.size() == 0) {
                            TopicDetailPagerActivity.this.finish();
                            return;
                        }
                        TopicDetailPagerActivity.this.getFlowHelper().pauseVideoPlay();
                        TopicDetailPagerActivity.this.mPager.setAdapter(new TopicDetailPagerAdapter(TopicDetailPagerActivity.this.getSupportFragmentManager()));
                        if (pageNum - 1 >= 0) {
                            TopicDetailPagerActivity.this.currrentPos = pageNum - 1;
                        } else {
                            TopicDetailPagerActivity.this.currrentPos = TopicDetailPagerActivity.MY_TOPIC;
                        }
                        TopicDetailPagerActivity.this.mPager.setCurrentItem(TopicDetailPagerActivity.this.currrentPos);
                        TopicDetailFragment topicDetailFragment = TopicDetailPagerActivity.this.getFragmentByPos(TopicDetailPagerActivity.this.currrentPos);
                        if (topicDetailFragment != null) {
                            topicDetailFragment.requestTopicInfo();
                            return;
                        }
                        return;
                    }
                }
            }
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailPagerActivity.3 */
    class C08193 implements OnTouchListener {
        C08193() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            TopicDetailPagerActivity.this.mInputMethodManager.hideSoftInputFromWindow(TopicDetailPagerActivity.this.et_topic_comment.getWindowToken(), TopicDetailPagerActivity.MY_TOPIC);
            if (TopicDetailPagerActivity.this.rl_emotion_bottom.getVisibility() == View.VISIBLE) {
                TopicDetailPagerActivity.this.isCheckPlayVideo = true;
                TopicDetailPagerActivity.this.rl_emotion_bottom.setVisibility(View.GONE);
                TopicDetailPagerActivity.this.iv_topic_emotion.setSelected(false);
                TopicDetailFragment topicDetailFragment = TopicDetailPagerActivity.this.getFragmentByPos(TopicDetailPagerActivity.this.mPager.getCurrentItem());
                int firstPosition = topicDetailFragment.getCommentLv().getFirstVisiblePosition();
                TopicDetailPagerActivity.this.isPlay = false;
                TopicDetailPagerActivity.this.checkVideoInScreen(topicDetailFragment.getCommentLv(), firstPosition);
            }
            TopicDetailPagerActivity.this.et_topic_comment.setText(BaseConstants.ah);
            TopicDetailPagerActivity.this.et_topic_comment.setHint(TopicDetailPagerActivity.this.getResources().getString(R.string.topic_detail_send_hint));
            TopicDetailPagerActivity.this.replyComment = null;
            return false;
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailPagerActivity.5 */
    class C08205 extends SimpleOnGestureListener {
        C08205() {
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (velocityX <= 30.0f) {
                return false;
            }
            TopicDetailPagerActivity.this.finish();
            return true;
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailPagerActivity.6 */
    class C08216 implements OnTouchListener {
        C08216() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (TopicDetailPagerActivity.this.mPager.getCurrentItem() == 0) {
                return TopicDetailPagerActivity.this.mDetector.onTouchEvent(event);
            }
            return false;
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailPagerActivity.7 */
    class C08227 implements OnCompletionListener {
        C08227() {
        }

        public void onCompletion(MediaPlayer mp) {
            TopicDetailPagerActivity.this.getFlowHelper().startAutoFlow(TopicDetailPagerActivity.MY_TOPIC, true, true);
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailPagerActivity.8 */
    class C08238 implements OnErrorListener {
        C08238() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
//            MobclickAgent.onEvent(TopicDetailPagerActivity.this, "media_player_error");
            TopicDetailPagerActivity.this.mFlowHelper.pauseVideoPlay(true);
            return true;
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailPagerActivity.9 */
    class C08249 implements OnPreparedListener {
        C08249() {
        }

        public void onPrepared(MediaPlayer mp) {
            TopicDetailPagerActivity.this.isPlay = true;
        }
    }

    class GestureListener extends SimpleOnGestureListener {
        GestureListener() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            TopicDetailPagerActivity.this.backTop();
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

    /* renamed from: com.gitrose.mobile.TopicDetailPagerActivity.10 */
    class AnonymousClass10 extends QuickReturnListViewOnScrollListener {
        AnonymousClass10(QuickReturnType $anonymous0, View $anonymous1, int $anonymous2, View $anonymous3, int $anonymous4) {
            super($anonymous0, $anonymous1, $anonymous2, $anonymous3, $anonymous4);
        }

        protected void onRealScroll(AbsListView listview, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (TopicDetailPagerActivity.this.isCheckPlayVideo) {
                TopicDetailPagerActivity.this.checkVideoInScreen(listview, firstVisibleItem);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailPagerActivity.14 */
    class AnonymousClass14 extends QGHttpHandler<String> {
        private final /* synthetic */ int val$arg0;
        private final /* synthetic */ TopicInfo val$topic;
        private final /* synthetic */ String val$topicId;

        AnonymousClass14(Context $anonymous0, TopicInfo topicInfo, String str, int i) {

            super($anonymous0);
            this.val$topic = topicInfo;
            this.val$topicId = str;
            this.val$arg0 = i;

        }

        public void onGetDataSuccess(String data) {
//            GreenDaoUtils.delTopicInfo(TopicDetailPagerActivity.this, this.val$topic);
            TopicDetailPagerActivity.this.delTopicBroadcast(this.val$topicId, false);
            TopicDetailPagerActivity.this.delTopicProcess(this.val$arg0);
        }

        public void onFinish() {
            TopicDetailPagerActivity.this.isWork = false;
            super.onFinish();
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailPagerActivity.15 */
    class AnonymousClass15 extends QGHttpHandler<String> {
        private final /* synthetic */ int val$arg0;
        private final /* synthetic */ TopicInfo val$topic;
        private final /* synthetic */ String val$topicId;

        AnonymousClass15(Context $anonymous0, TopicInfo topicInfo, String str, int i) {

            super($anonymous0);
            this.val$topic = topicInfo;
            this.val$topicId = str;
            this.val$arg0 = i;

        }

        public void onGetDataSuccess(String data) {
//            GreenDaoUtils.delTopicInfo(TopicDetailPagerActivity.this, this.val$topic);
            TopicDetailPagerActivity.this.delTopicBroadcast(this.val$topicId, false);
            TopicDetailPagerActivity.this.delTopicProcess(this.val$arg0);
        }

        public void onFinish() {
            TopicDetailPagerActivity.this.isWork = false;
            super.onFinish();
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailPagerActivity.16 */
    class AnonymousClass16 extends QGHttpHandler<String> {
        private final /* synthetic */ String val$commentId;
        private final /* synthetic */ int val$commentPos;
        private final /* synthetic */ TopicInfo val$mTopicInfo;

        AnonymousClass16(Context $anonymous0, TopicInfo topicInfo, int i, String str) {

            super($anonymous0);
            this.val$mTopicInfo = topicInfo;
            this.val$commentPos = i;
            this.val$commentId = str;

        }

        public void onGetDataSuccess(String data) {
            TopicDetailPagerActivity.this.mIsDelCommentFinish = true;
            if (TopicDetailPagerActivity.this.delCommentPop != null) {
                TopicDetailPagerActivity.this.delCommentPop.dismiss();
            }
            int currentItem = TopicDetailPagerActivity.this.mPager.getCurrentItem();
            if (TopicDetailPagerActivity.this.mTopicInfos.size() > currentItem) {
                List<Comment> commentList;
                TopicInfo topic = (TopicInfo) TopicDetailPagerActivity.this.mTopicInfos.get(currentItem);
                topic.setTotalcomment(topic.getTotalcomment() - 1);
                if (this.val$mTopicInfo == null || BaseConstants.ah.equals(this.val$mTopicInfo)) {
                    commentList = topic.getCommentlist();
                } else {
                    commentList = this.val$mTopicInfo.getCommentlist();
                }
                commentList.remove(this.val$commentPos);
                TopicDetailFragment fragment = TopicDetailPagerActivity.this.mFragments[currentItem];
//                TopicDetailPagerActivity.this.mTopicAdapter.notifyCommentPos(currentItem, this.val$commentPos, this.val$mTopicInfo);
                fragment.delNewComment(this.val$commentId);
            }
        }

        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            if (TopicDetailPagerActivity.this.delCommentPop != null) {
                TopicDetailPagerActivity.this.delCommentPop.dismiss();
            }
            Toast.makeText(TopicDetailPagerActivity.this, TopicDetailPagerActivity.this.getResources().getString(R.string.network_error_tip), Toast.LENGTH_LONG).show();
        }

        public void onFinish() {
            TopicDetailPagerActivity.this.isWork = false;
            super.onFinish();
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailPagerActivity.4 */
    class C17684 extends QGHttpHandler<ArrayList<CommentEmotion>> {
        C17684(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(ArrayList<CommentEmotion> data) {
            TopicDetailPagerActivity.this.emotions = data;
            if (data != null && data.size() > 0) {
                int count = data.size();
                int width = TopicDetailPagerActivity.this.getResources().getInteger(R.integer.comment_emotion_item_width);
                TopicDetailPagerActivity.this.gv_emotion.setLayoutParams(new LayoutParams(count * width, TopicDetailPagerActivity.this.getResources().getDimensionPixelSize(R.dimen.action_bar_height)));
                TopicDetailPagerActivity.this.gv_emotion.setColumnWidth(width);
                TopicDetailPagerActivity.this.gv_emotion.setHorizontalSpacing(10);
                TopicDetailPagerActivity.this.gv_emotion.setVerticalSpacing(10);
                TopicDetailPagerActivity.this.gv_emotion.setStretchMode(GridView.STRETCH_SPACING);
                TopicDetailPagerActivity.this.gv_emotion.setNumColumns(count);
                TopicDetailPagerActivity.this.topicEmotionAdapter.setList((List) data);
                TopicDetailPagerActivity.this.ll_emotion_page.addView(new TopicEmotionView(TopicDetailPagerActivity.this, TopicDetailPagerActivity.this.rl_emotion_bottom, ((CommentEmotion) TopicDetailPagerActivity.this.emotions.get(TopicDetailPagerActivity.MY_TOPIC)).getList()));
            }
        }
    }

    public class TopicDetailPagerAdapter extends FragmentStatePagerAdapter {
        public TopicDetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int arg0) {
            return TopicDetailPagerActivity.this.getFragmentByPos(arg0);
        }

        public int getCount() {
            if (TopicDetailPagerActivity.this.mTopicInfos == null || TopicDetailPagerActivity.this.mTopicInfos.size() <= 0) {
                return TopicDetailPagerActivity.MY_TOPIC;
            }
            return TopicDetailPagerActivity.this.mTopicInfos.size();
        }

        public int getItemPosition(Object object) {
            return -2;
        }
    }

    public TopicDetailPagerActivity() {
        this.mScrollableItems = 17;
        this.type = MY_TOPIC;
        this.mIsPause = false;
        this.replyComment = null;
        this.topicUid = BaseConstants.ah;
        this.isCheckPlayVideo = true;
        this.from_personal = false;
        this.isFinish = false;
        this.emotionTitleClick = new C08171();
        this.mBroadcastReceiver = new C08182();
    }

    static {
        CLICK_FRAGMENT_POS = MY_TOPIC;
        flag = false;
    }

    public static TopicDetailPagerActivity getInstance() {
        return topicDetailPagerActivity;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail_pager);
        topicDetailPagerActivity = this;
        this.mHandler = new Handler();
        getIntentData();
        initView();
        initEmotionView();
        requestEmotionData();
    }

    private void initView() {
        ((KeyboardRelativeLayout) findViewById(R.id.container_rl)).setOnKeyboardStateChangedListener(this);
        this.backImg = (ImageView) findViewById(R.id.title_tv_left);
        this.delImg = (ImageView) findViewById(R.id.iv_topic_del);
        this.delImg.setVisibility(View.INVISIBLE);
        this.avatorView = (CircleImageView) getViewById(R.id.circleimg_current_show_avatar);
        this.mHeadView = (View) getViewById(R.id.title_rl);
        this.topicDetailPagerAdapter = new TopicDetailPagerAdapter(getSupportFragmentManager());
        this.mPager = (ViewPager) findViewById(R.id.viewpager);
        addPagerGesture();
        initPop();
        this.backImg.setOnClickListener(this);
        this.delImg.setOnClickListener(this);
        this.avatorView.setOnClickListener(this);
        this.mRequestDao = new RequestDao(this);
        initScorllListViewListener();
        initMediaPalyer();
        this.mFlowHelper = new AutoCoverFlowHelper(this, false);
        this.mTopicAdapter = new HomeFollowAdapter(this, this.mTopicInfos, false, USER_IS_RESPORT);
        this.mTopicAdapter.setOnClickListener(this);
        viewPagerInit();
        this.mGestureDetector = new GestureDetector(new GestureListener());
        this.mHeadView.setOnTouchListener(this);
        this.mDownloader = new VideoDownloader(this, this.mTopicInfos);
        registerBoradcastReceiver();
        DownLoadClientImpl.getInstance().registerObserver(this);
        this.mIsNewVersion = VERSION.SDK_INT >= 14;
        this.view_block = findViewById(R.id.view_block);
        this.view_block.setOnTouchListener(new C08193());
    }

    private void initEmotionView() {
        this.mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        this.iv_topic_emotion = (ImageView) findViewById(R.id.iv_topic_emotion);
        this.iv_topic_emotion.setOnClickListener(this);
        this.et_topic_comment = (EditText) findViewById(R.id.et_topic_comment);
        this.et_topic_comment.setOnClickListener(this);
        findViewById(R.id.iv_topic_send).setOnClickListener(this);
        findViewById(R.id.iv_add_emotion).setOnClickListener(this);
        this.ll_emotion_page = (LinearLayout) findViewById(R.id.ll_emotion_page);
        this.rl_emotion_bottom = (RelativeLayout) findViewById(R.id.rl_emotion_bottom);
        this.gv_emotion = (GridView) findViewById(R.id.gv_emotion);
        this.gv_emotion.setSelector(new ColorDrawable(MY_TOPIC));
        this.gv_emotion.setOnItemClickListener(this.emotionTitleClick);
        this.topicEmotionAdapter = new TopicEmotionAdapter(this);
        this.topicEmotionAdapter.setSelectedPosition(MY_TOPIC);
        this.gv_emotion.setAdapter(this.topicEmotionAdapter);
        if (this.replyComment != null && !BaseConstants.ah.equals(this.replyComment)) {
            setReplyCommentData(this.replyComment);
        }
    }

    private void requestEmotionData() {
        QGHttpRequest.getInstance().getTopicEmotion(this, new C17684(this));
    }

    private void addPagerGesture() {
        this.mGestureListener = new C08205();
        this.mDetector = new GestureDetector(this.mGestureListener);
        this.mPager.setOnTouchListener(new C08216());
    }

    private void initMediaPalyer() {
        this.mMediaPlayer = new MediaPlayer();
        this.mMediaPlayer.setAudioStreamType(3);
        this.mMediaPlayer.setLooping(true);
        this.mMediaPlayer.setOnCompletionListener(new C08227());
        this.mMediaPlayer.setOnErrorListener(new C08238());
        this.mMediaPlayer.setOnPreparedListener(new C08249());
    }

    private void initScorllListViewListener() {
        int footHeight = getResources().getDimensionPixelSize(R.dimen.topic_detail_item_foot_height);
        this.scrollListener = new AnonymousClass10(QuickReturnType.HEADER, this.mHeadView, -footHeight, null, footHeight);
        this.scrollListener.setCanSlideInIdleScrollState(true);
    }

    private void viewPagerInit() {
        this.mPager.setAdapter(new TopicDetailPagerAdapter(getSupportFragmentManager()));
        this.mPager.setCurrentItem(this.currrentPos);
        this.mPager.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageSelected(int position) {
                TopicDetailPagerActivity.this.startAutoFlow(false);
                if (position != TopicDetailPagerActivity.this.currrentPos) {
                    TopicDetailPagerActivity.this.currrentPos = position;
                    TopicDetailPagerActivity.this.getFragmentByPos(position).requestTopicInfo();
                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
        startAutoFlow(true);
    }

    public void startAutoFlow(boolean isFirst) {
        int currrent = this.mPager.getCurrentItem();
        if (this.mTopicInfos != null && this.mTopicInfos.size() > 0) {
            TopicInfo topic = (TopicInfo) this.mTopicInfos.get(currrent);
            if (topic != null) {
                String avatarTime;
                String uid = topic.getUid();
                if (this.userId == null || BaseConstants.ah.equals(this.userId) || !this.userId.equals(MyApplication.getInstance().login_uid)) {
                    avatarTime = topic.getUserinfo().getAvatartime();
                } else {
                    avatarTime = MyApplication.getInstance().getUserinfo().getAvatartime();
                }
                ImageLoader.getInstance().displayImage(ImageUtils.getAvatarUrl(topic.getUid(), avatarTime), this.avatorView, Constant.AVATAR_OPTIONS);
                this.avatorView.setTag(uid);
                delImgVisibility(uid, topic.getUserisrepost());
                if (!isFirst) {
                    this.mFlowHelper.startAutoFlow(currrent, true, false);
                }
            }
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        this.topicUid = intent.getStringExtra("topicUid");
        this.replyComment = (Comment) intent.getSerializableExtra("comment");
        this.isFromHomePage = intent.getBooleanExtra("isFromHomePage", false);
        this.currrentPos = intent.getIntExtra(LIST_POS, MY_TOPIC);
        this.type = intent.getIntExtra(TableSchema.COLUMN_TYPE, MY_TOPIC);
        this.isBlock = intent.getIntExtra("isblock", MY_TOPIC);
        this.userId = intent.getStringExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID);
        this.lookRedPacket = intent.getBooleanExtra("lookRedPacket", false);
        if (this.userId == null || BaseConstants.ah.equals(this.userId) || !this.userId.equals(MyApplication.getInstance().login_uid)) {
            this.isSelf = false;
        } else {
            this.isSelf = true;
        }
        this.from_personal = intent.getBooleanExtra("from_personal", false);
        if (this.from_personal) {
            this.mTopicInfos = MyApplication.getInstance().getmPicList();
        } else {
            this.mTopicInfos = (ArrayList) intent.getSerializableExtra("topics");
        }
        if (this.isFromHomePage) {
            CLICK_FRAGMENT_POS = MY_TOPIC;
        }
        if (this.mTopicInfos != null) {
            this.mFragments = new TopicDetailFragment[this.mTopicInfos.size()];
        } else {
            finish();
        }
    }

    private void initPop() {
        this.mDialog = new BaseDialog(this);
        this.mDelTopicView = getLayoutInflater().inflate(R.layout.dialog_base_layout, null);
        this.mDelTopicView.findViewById(R.id.pop_tv_submit).setOnClickListener(this);
        this.mDelTopicView.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
        String[] strArr = new String[USER_IS_RESPORT];
        strArr[MY_TOPIC] = getResources().getString(R.string.delete);
        this.delCommentPop = new TipPop(this, strArr, new OnClickListener() {
            public void onClick(View v) {
                TopicDetailPagerActivity.this.showDialog(true);
            }
        }, this);
    }

    public void showDialog(boolean isDelComment) {
        TextView tipTitle = (TextView) this.mDelTopicView.findViewById(R.id.tv_tip_title);
        if (isDelComment) {
            tipTitle.setText(getResources().getString(R.string.topic_del_comment));
        } else {
            tipTitle.setText(getResources().getString(this.type == USER_IS_RESPORT ? R.string.topic_detail_del_fav_tip : R.string.topic_detail_del_topic_tip));
        }
        this.mDelTopicView.findViewById(R.id.pop_tv_submit).setTag(Boolean.valueOf(isDelComment));
        this.mDialog.show(this.mDelTopicView);
    }

    private final void showMoreDialog(View moreView) {
        if (this.mMoreView == null) {
            this.mMoreView = View.inflate(this, R.layout.dialog_topic_more, null);
            this.mMoreView.findViewById(R.id.share_tv_block).setOnClickListener(this);
            this.mMoreView.findViewById(R.id.share_tv_report).setOnClickListener(this);
            this.mMoreView.findViewById(R.id.share_tv_fav).setOnClickListener(this);
            this.mMoreView.findViewById(R.id.share_tv_cancel).setOnClickListener(this);
        }
        TopicInfo topicInfo = (TopicInfo) moreView.getTag();
        if (topicInfo != null) {
            if (this.type == USER_IS_RESPORT) {
                topicInfo.setIsfav(Integer.valueOf(USER_IS_RESPORT));
            }
            Integer isFav = topicInfo.getIsfav();
            if (isFav == null) {
                isFav = Integer.valueOf(MY_TOPIC);
            }
            ((TextView) this.mMoreView.findViewById(R.id.share_tv_fav)).setText(isFav.intValue() == USER_IS_RESPORT ? getResources().getString(R.string.cancel_fav) : getResources().getString(R.string.fav));
        }
        this.mMoreView.setTag(topicInfo);
        this.mDialog.show(this.mMoreView);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                this.mInputMethodManager.hideSoftInputFromWindow(this.et_topic_comment.getWindowToken(), MY_TOPIC);
                finish();
            case R.id.circleimg_current_show_avatar:
            case R.id.iv_user_icon:
                startPersonalActivity(v);
            case R.id.share_tv_cancel:
                this.mDialog.dismiss();
            case R.id.iv_topic_emotion:
                this.isCheckPlayVideo = false;
                this.mInputMethodManager.hideSoftInputFromWindow(this.et_topic_comment.getWindowToken(), MY_TOPIC);
                this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        if (TopicDetailPagerActivity.this.rl_emotion_bottom.getVisibility() == View.GONE) {
                            TopicDetailPagerActivity.this.rl_emotion_bottom.setVisibility(View.VISIBLE);
                            TopicDetailPagerActivity.this.iv_topic_emotion.setSelected(true);
                            TopicDetailPagerActivity.this.mFlowHelper.pauseVideoPlay();
                            TopicDetailPagerActivity.this.view_block.setVisibility(View.VISIBLE);
                            return;
                        }
                        TopicDetailPagerActivity.this.rl_emotion_bottom.setVisibility(View.GONE);
                        TopicDetailPagerActivity.this.iv_topic_emotion.setSelected(false);
                        TopicDetailPagerActivity.this.mInputMethodManager.showSoftInput(TopicDetailPagerActivity.this.et_topic_comment, TopicDetailPagerActivity.MY_TOPIC);
                        TopicDetailPagerActivity.this.mFlowHelper.pauseVideoPlay();
                        TopicDetailPagerActivity.this.view_block.setVisibility(View.GONE);
                    }
                }, 300);
            case R.id.et_topic_comment:
                if (this.rl_emotion_bottom.getVisibility() == View.VISIBLE) {
                    this.rl_emotion_bottom.setVisibility(View.GONE);
                    this.iv_topic_emotion.setSelected(false);
                    this.isCheckPlayVideo = true;
                }
                this.mInputMethodManager.showSoftInput(this.et_topic_comment, MY_TOPIC);
            case R.id.iv_topic_send:
                this.view_block.setVisibility(View.GONE);
                this.mInputMethodManager.hideSoftInputFromWindow(this.et_topic_comment.getWindowToken(), MY_TOPIC);
                if (this.rl_emotion_bottom.getVisibility() == View.VISIBLE) {
                    this.rl_emotion_bottom.setVisibility(View.GONE);
                    this.iv_topic_emotion.setSelected(false);
                }
                String commentContent = this.et_topic_comment.getText().toString().trim();
                if (commentContent == null || BaseConstants.ah.equals(commentContent)) {
                    Toast.makeText(this, R.string.comment_content_null_tip, Toast.LENGTH_LONG).show();
                } else {
                    sendComment(randomCommentInput(), commentContent);
                }
            case R.id.iv_topic_del:
                showDialog(false);
            case R.id.pop_tv_submit:
                if (((Boolean) v.getTag()).booleanValue()) {
                    delHotComment(v);
                } else {
                    delTopic();
                }
            case R.id.pop_tv_cancel:
                this.mDialog.dismiss();
                if (this.delCommentPop != null) {
                    this.delCommentPop.dismiss();
                }
            case R.id.share_tv_block:
                blockTopic();
            case R.id.share_tv_report:
                reportTopic();
            case R.id.share_tv_fav:
                takeFav();
            case R.id.tv_topic_more:
            case R.id.tv_home_more:
//                TopicInfo topicInfo = (TopicInfo) v.getTag();
//                Intent intent = new Intent(this, ShareActivity.class);
//                intent.putExtra(QzoneShare.SHARE_TO_QQ_TITLE, Constant.PREFENCES_NAME);
//                intent.putExtra(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT, topicInfo.getDesc());
//                intent.putExtra(Util.EXTRA_TOPICID, topicInfo.getTopicid());
//                intent.putExtra("topic_uid", topicInfo.getUid());
//                intent.putExtra(InviteAPI.KEY_URL, topicInfo.getContent());
//                intent.putExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID, topicInfo.getUid());
//                intent.putExtra("topicInfo", topicInfo);
//                intent.putExtra("publish_nickname", topicInfo.getUserinfo().getNickname());
//                startActivityForResult(intent, MY_TOPIC);
//                showLayerMask();
//                animationForBottom();
            case R.id.tv_home_comment:
                this.mFragments[this.mPager.getCurrentItem()].backComment(false);
                this.mFragments[this.mPager.getCurrentItem()].refreshComment();
            default:
        }
    }

    public void refreshRedpack() {
        this.mFragments[this.mPager.getCurrentItem()].backComment(false);
        this.mFragments[this.mPager.getCurrentItem()].refreshRedPacket();
    }

    public void sendComment(String inputName, String comentContent) {
        this.view_block.setVisibility(View.GONE);
        if (!(inputName == null || BaseConstants.ah.equals(inputName))) {
            this.iv_topic_emotion.setSelected(false);
            this.et_topic_comment.setText(BaseConstants.ah);
        }
        if (MyApplication.getInstance().getUserinfo() == null) {
            startActivityForNew(new Intent(this, LoginActivity.class));
            return;
        }
        int pos = this.mPager.getCurrentItem();
        if (this.mTopicInfos != null && this.mTopicInfos.size() > 0) {
            TopicInfo topicInfo = (TopicInfo) this.mTopicInfos.get(pos);
            if (topicInfo != null) {
                SharedPreferences sp = getSharedPreferences(Constant.PREFENCES_NAME, MY_TOPIC);
                String uid = MyApplication.getInstance().getUserinfo().getUid();
                Comment comment = new Comment();
                comment.setUid(uid);
                comment.setLocalCommentId(new StringBuilder(String.valueOf(uid)).append(System.currentTimeMillis()).toString());
                comment.setTopicId(topicInfo.getTopicid());
                comment.setTxtframe(inputName);
                if (this.replyComment == null || BaseConstants.ah.equals(this.replyComment)) {
                    comment.setReplyCommentId(BaseConstants.ah);
                    comment.setTopicUid(topicInfo.getUid());
                } else {
                    comment.setReplyCommentId(this.replyComment.getCommentid());
                    comment.setTopicUid(this.replyComment.getUid());
                }
                comment.setContent(comentContent);
                TutuUsers user = new TutuUsers();
                user.setUid(uid);
                user.setNickname(sp.getString(Constant.NICK_NAME_PERFERENCE, getResources().getString(R.string.wo)));
                user.setAvatartime(sp.getString(Constant.AVATAR_TIME_PERFERENCE, BaseConstants.ah));
                comment.setUserinfo(user);
                this.mFragments[this.mPager.getCurrentItem()].addComment(comment);
                this.et_topic_comment.setText(BaseConstants.ah);
                this.et_topic_comment.setHint(getResources().getString(R.string.topic_detail_send_hint));
                this.replyComment = null;
            }
        }
    }

    private String randomCommentInput() {
        int inputSize = CommentPicUtils.inputName.length;
        int number = new Random().nextInt(inputSize);
        if (number < 0 || number >= inputSize) {
            return null;
        }
        return CommentPicUtils.inputName[number];
    }

    private void reportTopic() {
        TopicMore.reportTopic(this, this.mDialog, ((TopicInfo) this.mMoreView.getTag()).getTopicid());
    }

    private void takeFav() {
        if (this.type == USER_IS_RESPORT) {
            delTopic();
            return;
        }
        TopicMore.takeFav(this, this.mDialog, (TopicInfo) this.mMoreView.getTag());
    }

    private void blockTopic() {
        TopicInfo topicInfo = (TopicInfo) this.mMoreView.getTag();
        if (this.isBlock == 0) {
            TopicMore.blockTopic(this, this.mDialog, topicInfo.getUid());
            finish();
            return;
        }
        TopicMore.unBlockTopic(this, this.mDialog, topicInfo.getUid());
    }

    private void delTopic() {
        int arg0 = this.mPager.getCurrentItem();
        if (this.mTopicInfos.size() > arg0) {
            TopicInfo topic = (TopicInfo) this.mTopicInfos.get(arg0);
            String topicId = topic.getTopicid();
            String localTopicId = topic.getLocalRequestId();
            if (this.type == 0) {
                if (topicId != null) {
                    if (!this.isWork) {
                        this.isWork = true;
                        QGHttpRequest.getInstance().delTopicRequest(this, topicId, new AnonymousClass14(this, topic, topicId, arg0));
                    }
                } else if (localTopicId != null) {
                    this.mRequestDao.removeLocalTopic(localTopicId);
                    delTopicBroadcast(localTopicId, true);
                    delTopicProcess(arg0);
                }
            } else if (!this.isWork) {
                this.isWork = true;
                QGHttpRequest.getInstance().delFavoriteRequest(this, topicId, new AnonymousClass15(this, topic, topicId, arg0));
            }
        }
    }

    private void delTopicProcess(int pageNum) {
        this.mDialog.dismiss();
        this.mPager.setAdapter(null);
        this.mTopicInfos.remove(pageNum);
        if (this.mTopicInfos.size() == 0) {
            finish();
            return;
        }
        String string;
        getFlowHelper().pauseVideoPlay();
        this.mPager.setAdapter(new TopicDetailPagerAdapter(getSupportFragmentManager()));
        if (pageNum - 1 >= 0) {
            this.currrentPos = pageNum - 1;
        } else {
            this.currrentPos = MY_TOPIC;
        }
        this.mPager.setCurrentItem(this.currrentPos);
        startAutoFlow(false);
        getFragmentByPos(this.currrentPos).requestTopicInfo();
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.container_rl);
        if (this.type == USER_IS_RESPORT) {
            string = getString(R.string.topic_delete_fav_success_tip);
        } else {
            string = getString(R.string.topic_delete_success_tip);
        }
        TopTip.show(this, relativeLayout, string, null, -1, getResources().getDimensionPixelOffset(R.dimen.action_bar_height));
    }

    private void delImgVisibility(String uid, int userIsReport) {
        if (uid != null && MyApplication.getInstance().isLogin() && (this.type == USER_IS_RESPORT || uid.equals(MyApplication.getInstance().getUserinfo().getUid()))) {
            this.delImg.setVisibility(View.VISIBLE);
        } else if (userIsReport == USER_IS_RESPORT) {
            this.delImg.setVisibility(View.VISIBLE);
        } else {
            this.delImg.setVisibility(View.INVISIBLE);
        }
    }

    private void checkVideoInScreen(AbsListView listview, int firstVisibleItem) {
        if (firstVisibleItem <= USER_IS_RESPORT) {
            int[] locations = new int[2];
            ((ListView) listview).getChildAt(1 - firstVisibleItem).findViewById(R.id.img_topic_switch_comment).getLocationInWindow(locations);
            if (locations[USER_IS_RESPORT] > 0 && !this.isPlay) {
                getFlowHelper().startAutoFlow(MY_TOPIC, true, true);
                this.isPlay = true;
            } else if (locations[USER_IS_RESPORT] < 0 && this.isPlay) {
                getFlowHelper().pauseVideoPlay();
                this.isPlay = false;
            }
        } else if (this.isPlay) {
            getFlowHelper().pauseVideoPlay();
            this.isPlay = false;
        }
    }

    public void startPersonalActivity(View v) {
        TopicInfo topicInfo = (TopicInfo) v.getTag();
        if (topicInfo != null) {
            String isName = topicInfo.getIskana();
            Intent intent;
            Bundle bundle;
            if (isName == null || BaseConstants.ah.equals(isName)) {
//                intent = new Intent();
//                intent.setClass(this, PersonalNewActivity.class);
//                bundle = new Bundle();
//                bundle.putString(PersonalNewActivity.PERSONAL_USER_ID, topicInfo.getUid());
//                intent.putExtras(bundle);
//                startActivityForNew(intent);
//                PlaySound.getInstance(this).toPlay(R.raw.open);
            } else if (Integer.valueOf(isName).intValue() == 0) {
//                intent = new Intent();
//                intent.setClass(this, PersonalNewActivity.class);
//                bundle = new Bundle();
//                bundle.putString(PersonalNewActivity.PERSONAL_USER_ID, topicInfo.getUid());
//                intent.putExtras(bundle);
//                startActivityForNew(intent);
//                PlaySound.getInstance(this).toPlay(R.raw.open);
            } else {
                Toast.makeText(this, getString(R.string.theme_isname_tip), Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 15:
                int isFav = data.getIntExtra("isFav", -1);
                int currentItem = this.mPager.getCurrentItem();
                if (this.mTopicInfos.size() <= currentItem) {
                    return;
                }
                if (isFav == 0 && this.type == USER_IS_RESPORT) {
                    delTopicBroadcast(((TopicInfo) this.mTopicInfos.get(currentItem)).getTopicid(), false);
                    delTopicProcess(currentItem);
                } else if (this.mTopicInfos.size() > currentItem) {
                    ((TopicInfo) this.mTopicInfos.get(currentItem)).setIsfav(Integer.valueOf(isFav));
                    this.mDialog.dismiss();
                }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private TopicDetailFragment getFragmentByPos(int arg0) {
        if (this.mFragments[arg0] == null) {
            this.mFragments[arg0] = TopicDetailFragment.newInstance();
        }
        Bundle bundle = new Bundle();
        if (this.mTopicInfos != null && this.mTopicInfos.size() > 0) {
            bundle.putSerializable("topic", (Serializable) this.mTopicInfos.get(arg0));
        }
        bundle.putBoolean("isSelf", this.isSelf);
        bundle.putInt("listPos", arg0);
        bundle.putBoolean("lookRedPacket", this.lookRedPacket);
        this.mFragments[arg0].setBundle(bundle);
        return this.mFragments[arg0];
    }

    public void registerBoradcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.UPLOAD_TOPIC_FINISH_ACTION);
        intentFilter.addAction(Constant.ZAN_TOPIC_ACTION);
        intentFilter.addAction(Constant.REPOST_TOPIC_ACTION);
        intentFilter.addAction(Constant.DEL_REPOST_TOPIC_ACTION);
        intentFilter.addAction(Constant.DEL_FAV_TOPIC_ACTION);
        registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    protected void onDestroy() {
        this.mFlowHelper.releaseMediaPlay();
        unregisterReceiver(this.mBroadcastReceiver);
        DownLoadClientImpl.getInstance().unregisterObserver(this);
        super.onDestroy();
    }

    public void onDelCommentListener(View commentView, int listPos, String topicId, int commentPos, String commentId, boolean isHasFrame, TopicInfo topicInfo) {
        int i = MY_TOPIC;
//        PlaySound.getInstance(this).toPlay(R.raw.delete);
        this.mDelTopicView.setTag(new Object[]{Integer.valueOf(listPos), topicId, Integer.valueOf(commentPos), commentId, topicInfo});
        TipPop tipPop = this.delCommentPop;
        if (isHasFrame) {
            i = getResources().getDimensionPixelOffset(R.dimen.white_broder_width);
        }
        tipPop.show(commentView, i);
    }

    public AutoCoverFlowHelper getFlowHelper() {
        return this.mFlowHelper;
    }

    private void delHotComment(View v) {
        Object[] params = (Object[]) ((View) v.getParent()).getTag();
        String topicId = (String) params[USER_IS_RESPORT];
        int commentPos = ((Integer) params[2]).intValue();
        String commentId = (String) params[3];
        TopicInfo mTopicInfo = (TopicInfo) params[4];
        this.mDialog.dismiss();
        if (!this.isWork) {
            this.isWork = true;
            QGHttpRequest.getInstance().delCommentRequest(this, topicId, commentId, new AnonymousClass16(this, mTopicInfo, commentPos, commentId));
        }
    }

    private void delTopicBroadcast(String topicId, boolean isLocal) {
        Intent intent = new Intent();
        if (this.type == 0) {
            intent.putExtra(isLocal ? "localTopicId" : "topicId", topicId);
            intent.setAction(Constant.DEL_TOPIC_ACTION);
        } else {
            intent.putExtra("topicId", topicId);
            intent.setAction(Constant.DEL_FAV_TOPIC_ACTION);
        }
        sendBroadcast(intent);
    }

    public void onDismiss() {
        if (this.mIsDelCommentFinish) {
            this.mIsDelCommentFinish = false;
        } else {
            getFlowHelper().resumeAutoFlow();
        }
    }

    public void finish() {
        this.isFinish = true;
        Intent i = new Intent();
        i.putExtra(TableSchema.COLUMN_TYPE, this.type);
        i.putExtra("topics", this.mTopicInfos);
        setResult(4, i);
        if (this.mTopicAdapter != null) {
            this.mTopicAdapter.uploadLocalViews();
        }
        super.finish();
    }

    public BaseDialog getDialog() {
        return this.mDialog;
    }

    public QuickReturnListViewOnScrollListener getScrollListViewListener() {
        return this.scrollListener;
    }

    public MediaPlayer getMediaPlayer() {
        return this.mMediaPlayer;
    }

    protected void onPause() {
        super.onPause();
        this.mIsPause = true;
        AutoCoverFlowHelper flowHelper = getFlowHelper();
        if (flowHelper != null) {
            flowHelper.pauseVideoPlay();
        }
    }

    protected void onResume() {
        super.onResume();
        this.mIsPause = false;
        if (this.mDialog != null) {
            this.mDialog.dismiss();
        }
        AutoCoverFlowHelper helper = getFlowHelper();
        if (helper != null && this.isPlay) {
            helper.startAutoFlow(MY_TOPIC, true, true);
        }
    }

    private void clickComment() {
        if (MyApplication.getInstance().isLogin()) {
//            PlaySound.getInstance(this).toPlay(R.raw.comment);
            Intent intent = new Intent(this, CommentActivity.class);
            int pos = this.mPager.getCurrentItem();
            if (this.mTopicInfos != null && this.mTopicInfos.size() > 0) {
                TopicInfo topicInfo = (TopicInfo) this.mTopicInfos.get(pos);
                if (topicInfo != null) {
                    intent.putExtra("topicId", topicInfo.getTopicid());
                    intent.putExtra("listPosition", pos);
                    intent.putExtra("picWidth", topicInfo.getWidth());
                    intent.putExtra("picHeight", topicInfo.getHeight());
                    intent.putExtra("imageUrl", topicInfo.getContent());
                    intent.putExtra("point", new float[]{0.5f, 0.5f});
                    startActivityForResult(intent, MY_TOPIC);
                    return;
                }
                return;
            }
            return;
        }
        TopicMore.startLoginActivity(this);
    }

    public VideoDownloader getDownloader() {
        return this.mDownloader;
    }

    public void notifyData(VideoDownLoad t) {
        int currrent = this.mPager.getCurrentItem();
        if (this.mTopicInfos.size() > currrent) {
            TopicInfo topicInfo = (TopicInfo) this.mTopicInfos.get(currrent);
            if (topicInfo != null && t.getUrl().equals(topicInfo.getVideourl()) && this.mIsNewVersion) {
                AutoCoverFlowHelper flowHelper = getFlowHelper();
                if (flowHelper != null) {
                    flowHelper.updateVideoProgress(t.getProgress(), t.getStatus(), t.getName());
                }
            }
        }
    }

    public int getBlockTopic() {
        return MY_TOPIC;
    }

    public void showLayerMask() {
        View view = new View(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.mDialog.show(view);
    }

    public boolean isPause() {
        return this.mIsPause;
    }

    public int getDownloadPos() {
        return this.mPager.getCurrentItem();
    }

    public boolean onTouch(View v, MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
    }

    private void backTop() {
        TopicDetailFragment fragment = this.mFragments[this.mPager.getCurrentItem()];
        if (fragment != null) {
            ListView commentLv = fragment.getCommentLv();
            if (commentLv.getFirstVisiblePosition() < 17) {
                commentLv.smoothScrollToPosition(MY_TOPIC);
            } else {
                commentLv.setSelection(17);
                commentLv.smoothScrollToPosition(MY_TOPIC);
            }
            commentLv.clearFocus();
        }
    }

    public View getConvertView() {
        TopicDetailFragment fragment = this.mFragments[this.mPager.getCurrentItem()];
        if (fragment == null) {
            return null;
        }
        return fragment.getConvertView();
    }

    public HomeFollowAdapter getTopicAdapter() {
        return this.mTopicAdapter;
    }

    public void showCommentView() {
        if (this.isFromHomePage && this.mFragments != null && this.mPager != null && this.mPager.getCurrentItem() <= this.mFragments.length - 1) {
            if (this.mFragments[this.mPager.getCurrentItem()] == null) {
                this.mFragments[this.mPager.getCurrentItem()] = TopicDetailFragment.newInstance();
            }
            this.mFragments[this.mPager.getCurrentItem()].backComment(this.isFromHomePage);
            this.mHandler.postDelayed(new Runnable() {

                /* renamed from: com.gitrose.mobile.TopicDetailPagerActivity.17.1 */
                class C08161 implements Runnable {
                    C08161() {
                    }

                    public void run() {
                        TopicDetailPagerActivity.flag = false;
                    }
                }

                public void run() {
                    TopicDetailPagerActivity.this.mFragments[TopicDetailPagerActivity.this.mPager.getCurrentItem()].mCommentLv.smoothScrollBy(-TopicDetailPagerActivity.this.getResources().getDimensionPixelOffset(R.dimen.topic_detail_item_foot_height), 300);
                    TopicDetailPagerActivity.flag = true;
                    if (TopicDetailPagerActivity.this.mHandler == null) {
                        TopicDetailPagerActivity.this.mHandler = new Handler();
                    }
                    TopicDetailPagerActivity.this.mHandler.postDelayed(new C08161(), 500);
                }
            }, 300);
        }
    }

    public void showSoftInput() {
        this.et_topic_comment.setFocusable(true);
        this.et_topic_comment.setFocusableInTouchMode(true);
        this.et_topic_comment.requestFocus();
        this.mInputMethodManager.showSoftInput(this.et_topic_comment, MY_TOPIC);
        if (this.rl_emotion_bottom.getVisibility() == View.VISIBLE) {
            this.rl_emotion_bottom.setVisibility(View.GONE);
            this.iv_topic_emotion.setSelected(false);
        }
    }

    public void hideSoftInput() {
        this.mInputMethodManager.hideSoftInputFromWindow(this.et_topic_comment.getWindowToken(), MY_TOPIC);
        if (this.rl_emotion_bottom.getVisibility() == View.VISIBLE) {
            this.rl_emotion_bottom.setVisibility(View.GONE);
            this.iv_topic_emotion.setSelected(false);
        }
    }

    public void onKeyboardStateChanged(int state) {
        switch (state) {
            case -3:
                this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        TopicDetailPagerActivity.this.view_block.setVisibility(View.VISIBLE);
                    }
                }, 300);
                this.mFlowHelper.pauseVideoPlay();
            case -2:
                this.view_block.setVisibility(View.GONE);
                if (this.isCheckPlayVideo) {
                    TopicDetailFragment topicDetailFragment = getFragmentByPos(this.mPager.getCurrentItem());
                    if (topicDetailFragment.getCommentLv() != null) {
                        int firstPosition = topicDetailFragment.getCommentLv().getFirstVisiblePosition();
                        this.isPlay = false;
                        checkVideoInScreen(topicDetailFragment.getCommentLv(), firstPosition);
                    }
                    String content = this.et_topic_comment.getText().toString().trim();
                    if (content == null || BaseConstants.ah.equals(content)) {
                        this.et_topic_comment.setText(BaseConstants.ah);
                        this.et_topic_comment.setHint(getResources().getString(R.string.topic_detail_send_hint));
                        this.replyComment = null;
                        return;
                    }
                    this.et_topic_comment.setText(content);
                    this.et_topic_comment.setSelection(content.length());
                }
            default:
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getAction() != 0) {
            return super.onKeyDown(keyCode, event);
        }
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                if (TopicDetailPagerActivity.this.view_block.getVisibility() == View.VISIBLE) {
                    TopicDetailPagerActivity.this.view_block.setVisibility(View.GONE);
                }
                if (TopicDetailPagerActivity.this.rl_emotion_bottom.getVisibility() == View.VISIBLE) {
                    TopicDetailPagerActivity.this.isCheckPlayVideo = true;
                    TopicDetailPagerActivity.this.rl_emotion_bottom.setVisibility(View.GONE);
                    TopicDetailPagerActivity.this.iv_topic_emotion.setSelected(false);
                    TopicDetailPagerActivity.this.et_topic_comment.setText(BaseConstants.ah);
                    TopicDetailPagerActivity.this.et_topic_comment.setHint(TopicDetailPagerActivity.this.getResources().getString(R.string.topic_detail_send_hint));
                    TopicDetailPagerActivity.this.replyComment = null;
                    TopicDetailFragment topicDetailFragment = TopicDetailPagerActivity.this.getFragmentByPos(TopicDetailPagerActivity.this.mPager.getCurrentItem());
                    int firstPosition = topicDetailFragment.getCommentLv().getFirstVisiblePosition();
                    TopicDetailPagerActivity.this.isPlay = false;
                    TopicDetailPagerActivity.this.checkVideoInScreen(topicDetailFragment.getCommentLv(), firstPosition);
                    return;
                }
                TopicDetailPagerActivity.this.finish();
            }
        }, 300);
        return true;
    }

    public void setReplyCommentData(Comment comment) {
        String name;
        this.replyComment = comment;
        String remarkname = comment.getUserinfo().getRemarkname();
        if (remarkname == null || BaseConstants.ah.equals(remarkname)) {
            name = comment.getUserinfo().getNickname();
        } else {
            name = remarkname;
        }
        this.et_topic_comment.setHint(new StringBuilder(String.valueOf(getResources().getString(R.string.comment_reply_tip))).append(name).append(":").toString());
        this.et_topic_comment.setFocusable(true);
        this.et_topic_comment.setFocusableInTouchMode(true);
        this.et_topic_comment.requestFocus();
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                TopicDetailPagerActivity.this.mInputMethodManager.toggleSoftInput(TopicDetailPagerActivity.MY_TOPIC, 2);
            }
        }, 500);
    }
}
