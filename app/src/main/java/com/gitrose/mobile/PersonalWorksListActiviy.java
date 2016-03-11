package com.gitrose.mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.adapter.PersonalWorks9Adapter;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.avsdk.control.Util;
import com.gitrose.mobile.base.BaseUploadActivity.IUploadCall;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.base.ITopicDisplay;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.dao.RequestDao;
import com.gitrose.mobile.download.video.DownLoadClientImpl;
import com.gitrose.mobile.download.video.DownLoadObserver;
import com.gitrose.mobile.download.video.VideoDownloader;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.TopicInfoList;
import com.gitrose.mobile.model.VideoDownLoad;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.view.AutoCoverFlowHelper;
import com.gitrose.mobile.view.AutoCoverFlowHelper.OnScrollListPosListener;
import com.gitrose.mobile.view.BaseDialog;
import com.gitrose.mobile.view.CircleImageView;
import com.gitrose.mobile.view.TipPop;
import com.gitrose.mobile.view.TopicMore;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.apache.http.Header;
import qalsdk.BaseConstants;
import shouji.gexing.framework.utils.NetUtils;

@SuppressLint({"ValidFragment"})
public class PersonalWorksListActiviy extends BaseUploadFragmentActivity implements OnClickListener, OnDismissListener, OnScrollListPosListener, ITopicDisplay, DownLoadObserver<VideoDownLoad>, IUploadCall, OnTouchListener {
    public static final int ADD_TOPIC_RESULT = 10;
    public static final int DOWN_MESSAGE = 336;
    public static final int FAV_RESULT = 15;
    public static final int IS_BLOCK_RESULT = 16;
    public static final String REQUEST_SIZE = "17";
    public static final String SP_KEY = "topic_list";
    private BaseDialog dialog;
    private boolean followCommit;
    private boolean isFirst;
    private boolean isWorkingDelTheme;
    private int len;
    public int mCurrentPosition;
    private View mDelCommentDialogView;
    private TipPop mDelCommentTipPop;
    private BaseDialog mDialog;
    public VideoDownloader mDownloader;
    private View mEmptyView;
    public AutoCoverFlowHelper mFlowHelper;
    private GestureDetector mGestureDetector;
    private Handler mHandler;
    public PersonalWorks9Adapter mHomeAdapter;
    private boolean mIsDelCommentFinish;
    private boolean mIsFinishRequest;
    private boolean mIsPause;
    private HashMap<String, Integer> mLocalTopicMap;
    private MediaPlayer mMediaPlayer;
    private View mMoreView;
    private TutuUsers mPersonalInfo;
    private RequestDao mRequestDao;
    private final int mScrollableItems;
    private List<TopicInfo> mTopicList;
    private ListView mXListView;
    private int needTop;
    private SharedPreferences sp;
    private String uid;
    private int uploadLocalView;
    CircleImageView userImageView;

    /* renamed from: com.gitrose.mobile.PersonalWorksListActiviy.1 */
    class C07321 implements OnScrollListener {
        C07321() {
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (PersonalWorksListActiviy.this.mHomeAdapter != null && PersonalNewActivity.getObject() != null) {
//                if (PersonalNewActivity.getObject().mCurrentWorklist != 5 && PersonalNewActivity.getObject().mCurrentCollectlist != 7) {
//                    return;
//                }
                if (PersonalWorksListActiviy.this.isFirst) {
                    PersonalWorksListActiviy.this.isFirst = false;
                    for (int i = 0; i < 4; i++) {
                        PersonalWorksListActiviy.this.mFlowHelper.startAutoFlow(visibleItemCount);
                    }
                    return;
                }
                PersonalWorksListActiviy.this.mFlowHelper.startAutoFlow(visibleItemCount);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PersonalWorksListActiviy.2 */
    class C07332 implements OnCompletionListener {
        C07332() {
        }

        public void onCompletion(MediaPlayer mp) {
            if (PersonalWorksListActiviy.this.mFlowHelper != null) {
                PersonalWorksListActiviy.this.mFlowHelper.startAutoFlow(PersonalWorksListActiviy.this.mCurrentPosition + 2, true, true);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PersonalWorksListActiviy.3 */
    class C07343 implements OnErrorListener {
        C07343() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            PersonalWorksListActiviy.this.mFlowHelper.pauseVideoPlay(true);
            return true;
        }
    }

    /* renamed from: com.gitrose.mobile.PersonalWorksListActiviy.4 */
    class C07354 implements DialogInterface.OnDismissListener {
        C07354() {
        }

        public void onDismiss(DialogInterface dialog) {
            if (!PersonalWorksListActiviy.this.isWorkingDelTheme) {
                PersonalWorksListActiviy.this.setFollowCommit(true);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PersonalWorksListActiviy.6 */
    class C07366 implements OnClickListener {
        C07366() {
        }

        public void onClick(View v) {
            PersonalWorksListActiviy.this.mDialog.show(PersonalWorksListActiviy.this.mDelCommentDialogView);
        }
    }

    class GestureListener extends SimpleOnGestureListener {
        GestureListener() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            PersonalWorksListActiviy.this.backTop();
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

    /* renamed from: com.gitrose.mobile.PersonalWorksListActiviy.5 */
    class C16855 extends QGHttpHandler<TopicInfoList> {
        private final /* synthetic */ String val$direction;

        C16855(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {

            super($anonymous0, $anonymous1, $anonymous2);
            this.val$direction = str;
            
        }

        public void onFinish() {
            super.onFinish();
            PersonalWorksListActiviy.this.mIsFinishRequest = true;
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            if (PersonalWorksListActiviy.this.mTopicList.size() == 0) {
                PersonalWorksListActiviy.this.showEmtpyTip(PersonalWorksListActiviy.this.getResources().getString(R.string.error_network_tip));
            }
        }

        public void onGetDataSuccess(TopicInfoList data) {
            if (data != null && data.getList() != null) {
                if (this.val$direction.equals(SearchActivity.UP)) {
                    PersonalWorksListActiviy.this.mTopicList.addAll(0, data.getList());
                } else if (this.val$direction.equals(SearchActivity.DOWN)) {
                    PersonalWorksListActiviy.this.mTopicList.addAll(data.getList());
                }
                PersonalWorksListActiviy.this.mHomeAdapter.notifyDataSetChanged();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PersonalWorksListActiviy.7 */
    class C16867 extends QGHttpHandler<String> {
        private final /* synthetic */ int val$commentPos;
        private final /* synthetic */ int val$listPos;

        C16867(Context $anonymous0, int i, int i2) {

            super($anonymous0);
            this.val$listPos = i;
            this.val$commentPos = i2;

        }

        public void onGetDataSuccess(String data) {
            PersonalWorksListActiviy.this.mIsDelCommentFinish = true;
            if (PersonalWorksListActiviy.this.mDelCommentTipPop != null) {
                PersonalWorksListActiviy.this.mDelCommentTipPop.dismiss();
            }
//            TopicInfo topicInfo = (TopicInfo) PersonalWorksListActiviy.this.mTopicList.get(this.val$listPos);
//            topicInfo.setTotalcomment(topicInfo.getTotalcomment() - 1);
//            topicInfo.getCommentlist().remove(this.val$commentPos);
//            PersonalWorksListActiviy.this.mHomeAdapter.notifyCommentPos(this.val$listPos, this.val$commentPos, null);
        }

        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            if (PersonalWorksListActiviy.this.mDelCommentTipPop != null) {
                PersonalWorksListActiviy.this.mDelCommentTipPop.dismiss();
            }
            Toast.makeText(PersonalWorksListActiviy.this, PersonalWorksListActiviy.this.getString(R.string.network_error_tip), Toast.LENGTH_LONG).show();
        }
    }

    public PersonalWorksListActiviy() {
        this.mScrollableItems = 17;
        this.needTop = 0;
        this.mIsFinishRequest = true;
        this.mCurrentPosition = -1;
        this.mHandler = new Handler();
        this.isFirst = true;
        this.isWorkingDelTheme = false;
        this.len = 21;
        this.followCommit = true;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initListActivity(ListView view) {
        this.sp = getSharedPreferences(Constant.PREFENCES_NAME, 0);
        this.mTopicList = new ArrayList();
        initMainView(view);
        this.needTop = getResources().getDimensionPixelOffset(R.dimen.theme_top_height2);
        this.dialog = new BaseDialog(this);
        this.mFlowHelper = new AutoCoverFlowHelper((Context) this, this.mXListView, true);
        this.mFlowHelper.setOnScrollListPosListener(this);
        addListViewListener();
        initData();
    }

    public void setData(List<TopicInfo> topicList) {
        this.mTopicList = topicList;
        if (this.mDownloader != null) {
            this.mDownloader.setTopicInfos(this.mTopicList);
        }
    }

    private void initMainView(ListView view) {
        this.mXListView = view;
        this.mGestureDetector = new GestureDetector(new GestureListener());
        initMediaPalyer();
        initDialog();
        this.mDownloader = new VideoDownloader(this, this.mTopicList);
        DownLoadClientImpl.getInstance().registerObserver(this);
    }

    private void addListViewListener() {
        this.mXListView.setOnScrollListener(new C07321());
    }

    public void onPause() {
        super.onPause();
        this.mIsPause = true;
        if (this.uploadLocalView >= 3) {
            if (this.mHomeAdapter != null) {
                this.mHomeAdapter.uploadLocalViews();
            }
            this.uploadLocalView = 0;
        } else {
            this.uploadLocalView++;
        }
        pauseDetail();
    }

    public void pauseDetail() {
        if (this.mFlowHelper != null) {
            this.mFlowHelper.pauseVideoPlay();
        }
        if (this.mDownloader != null) {
            this.mDownloader.pauseVideo();
        }
    }

    public void onResume() {
        super.onResume();
        this.mIsPause = false;
        if (this.mDialog != null && this.mDialog.isShowing()) {
            this.mDialog.dismiss();
        }
    }

    public void resumeVideoAndCommentFlow() {
        if (this.mDownloader != null) {
            this.mDownloader.resumeVideo();
        }
        if (this.mFlowHelper != null) {
            this.mFlowHelper.startAutoFlow(this.mCurrentPosition + this.mXListView.getHeaderViewsCount(), true, true);
        }
    }

    public void onDestroy() {
        this.mFlowHelper.releaseMediaPlay();
        DownLoadClientImpl.getInstance().unregisterObserver(this);
        super.onDestroy();
    }

    private void initData() {
        this.mLocalTopicMap = new HashMap();
        this.mRequestDao = new RequestDao(this);
    }

    private void deleteTopic(String topicId) {
        for (int i = this.mTopicList.size() - 1; i >= 0; i--) {
            if (topicId.equals(((TopicInfo) this.mTopicList.get(i)).getTopicid())) {
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
        }
        if (this.mCurrentPosition > 0) {
        }
    }

    private void initDialog() {
        this.mDialog = new BaseDialog(this);
    }

    private void initMediaPalyer() {
        this.mMediaPlayer = new MediaPlayer();
        this.mMediaPlayer.setAudioStreamType(3);
        this.mMediaPlayer.setLooping(true);
        this.mMediaPlayer.setOnCompletionListener(new C07332());
        this.mMediaPlayer.setOnErrorListener(new C07343());
    }

    private void backTop() {
        if (this.mXListView.getFirstVisiblePosition() < 17) {
            this.mXListView.smoothScrollToPosition(0);
        } else {
            this.mXListView.setSelection(17);
            this.mXListView.smoothScrollToPosition(0);
        }
        this.mXListView.clearFocus();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_action_bar:
                backTop();
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
                        Toast.makeText(this, getString(R.string.theme_isname_tip), Toast.LENGTH_LONG).show();
                    }
                }
            case R.id.share_tv_cancel:
            case R.id.photo_pop_tv_cancel:
                this.mDialog.dismiss();
            case R.id.share_tv_block:
                blockTopic();
            case R.id.share_tv_report:
                reportTopic();
            case R.id.share_tv_fav:
                takeFav();
            case R.id.tv_topic_more:
                showMoreDialog(v);
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
//                startActivityForResult(intent, 0);
//                showLayerMask();
//                overridePendingTransition(R.anim.main_translatey100to0, R.anim.main_translatey0tof100);
            default:
        }
    }

    public boolean isFollowCommit() {
        return this.followCommit;
    }

    public void setFollowCommit(boolean followCommit) {
        this.followCommit = followCommit;
    }

    public void showCancelFollowDialog() {
        View cancelFollowView = View.inflate(this, R.layout.dialog_base_layout, null);
        ((TextView) cancelFollowView.findViewById(R.id.tv_tip_title)).setText(getResources().getString(R.string.sure_cancel_follow));
        cancelFollowView.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
        cancelFollowView.findViewById(R.id.pop_tv_submit).setOnClickListener(this);
        this.dialog = new BaseDialog(this);
        this.dialog.show(cancelFollowView);
        this.dialog.setOnDismissListener(new C07354());
    }

    private void reportTopic() {
        TopicMore.reportTopic(this, this.mDialog, ((TopicInfo) this.mMoreView.getTag()).getTopicid());
    }

    private void takeFav() {
        TopicMore.takeFav(this, this.mDialog, (TopicInfo) this.mMoreView.getTag());
    }

    private void blockTopic() {
        TopicMore.blockTopic(this, this.mDialog, ((TopicInfo) this.mMoreView.getTag()).getUid());
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
            CharSequence string;
            Integer isFav = topicInfo.getIsfav();
            if (isFav == null) {
                isFav = Integer.valueOf(0);
            }
            TextView textView = (TextView) this.mMoreView.findViewById(R.id.share_tv_fav);
            if (isFav.intValue() == 1) {
                string = getResources().getString(R.string.cancel_fav);
            } else {
                string = getResources().getString(R.string.fav);
            }
            textView.setText(string);
        }
        this.mMoreView.setTag(moreView.getTag());
        this.mDialog.show(this.mMoreView);
    }

    private void startPersonalActivity(String uid) {
        Intent intent = new Intent();
        intent.setClass(this, PersonalNewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("user_id", uid);
        intent.putExtras(bundle);
        startActivityForNew(intent);
    }

    private void startLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivityForNew(intent);
    }

    public void startActivityForNew(Intent intent) {
        startActivity(intent);
        animationForNew();
    }

    private void seePersonalInfo() {
        if (!MyApplication.getInstance().isLogin()) {
            startLoginActivity();
        } else if (MyApplication.getInstance().getUserinfo().getUid() != null) {
            startPersonalActivity(MyApplication.getInstance().getUserinfo().getUid());
//            PlaySound.getInstance(this).toPlay(R.raw.open);
        }
    }

    private void request(boolean up) {
        String startTopicId = null;
        String direction = SearchActivity.UP;
        if (this.mTopicList.size() > 0) {
            if (up) {
                startTopicId = ((TopicInfo) this.mTopicList.get(0)).getTopicid();
                direction = SearchActivity.UP;
            } else {
                startTopicId = ((TopicInfo) this.mTopicList.get(this.mTopicList.size() - 1)).getTopicid();
                direction = SearchActivity.DOWN;
            }
        }
        requestData(direction, startTopicId, this.len);
    }

    private void requestData(String direction, String startTopicId, int len) {
        if (this.mIsFinishRequest) {
            this.mIsFinishRequest = false;
            QGHttpRequest.getInstance().getPersonalWorksListRequest(this, this.uid, startTopicId, new StringBuilder(String.valueOf(len)).toString(), direction, new C16855(this, false, null, direction));
        }
    }

    public void showEmtpyTip(String tip) {
        this.mXListView.setVisibility(View.INVISIBLE);
        this.mEmptyView.setVisibility(View.GONE);
        if (this.mPersonalInfo == null || this.mPersonalInfo.getStatus() == null || !this.mPersonalInfo.getStatus().equals("-2")) {
            ((TextView) this.mEmptyView.findViewById(R.id.tv_emtpy_tip)).setText(tip);
        } else {
            ((TextView) this.mEmptyView.findViewById(R.id.tv_emtpy_tip)).setText(R.string.empty_other_topic_tip_feng);
        }
    }

    public void notifyData(VideoDownLoad t) {
        if (this.mCurrentPosition >= 0 && this.mTopicList.size() > this.mCurrentPosition) {
            TopicInfo topicInfo = (TopicInfo) this.mTopicList.get(this.mCurrentPosition);
            if (topicInfo != null && t.getUrl().equals(topicInfo.getVideourl())) {
                this.mFlowHelper.updateVideoProgress(t.getProgress(), t.getStatus(), t.getName());
            }
        }
    }

    public VideoDownloader getDownloader() {
        return this.mDownloader;
    }

    public void onDelCommentListener(View commentView, int listPos, String topicId, int commentPos, String commentId, boolean isHasframe, TopicInfo mTopicInfo) {
        int i = 0;
//        PlaySound.getInstance(this).toPlay(R.raw.delete);
        Object[] delCommentParams = new Object[]{Integer.valueOf(listPos), topicId, Integer.valueOf(commentPos), commentId};
        showDelCommentDialog();
        this.mDelCommentDialogView.setTag(delCommentParams);
        TipPop tipPop = this.mDelCommentTipPop;
        if (isHasframe) {
            i = getResources().getDimensionPixelOffset(R.dimen.white_broder_width);
        }
        tipPop.show(commentView, i);
    }

    private void showDelCommentDialog() {
        if (this.mDelCommentDialogView == null) {
            this.mDelCommentDialogView = View.inflate(this, R.layout.dialog_base_layout, null);
            ((TextView) this.mDelCommentDialogView.findViewById(R.id.tv_tip_title)).setText(getResources().getString(R.string.topic_del_comment));
            this.mDelCommentDialogView.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
            this.mDelCommentDialogView.findViewById(R.id.pop_tv_submit).setOnClickListener(this);
            this.mDelCommentTipPop = new TipPop(this, new String[]{getResources().getString(R.string.delete)}, new C07366(), this);
        }
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
        View view = new View(this);
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

    public void onScrollPos(ListView parent, int pos) {
        int position = pos - parent.getHeaderViewsCount();
        if (position >= 0) {
            changeAvatar(position);
            this.mCurrentPosition = position;
//            DebugUtils.error("mCurrentPosition-----------" + this.mCurrentPosition);
            return;
        }
        this.mCurrentPosition = -1;
        this.mFlowHelper.resetCurrentPosition(-1);
    }

    public void onDismiss() {
        if (this.mIsDelCommentFinish) {
            this.mIsDelCommentFinish = false;
        } else {
            this.mFlowHelper.resumeAutoFlow();
        }
    }

    private void changeAvatar(int currentPos) {
    }

    private void delComment(View v) {
        this.mDialog.dismiss();
        Object[] params = (Object[]) ((View) v.getParent()).getTag();
        String topicId = (String) params[1];
        String commentId = (String) params[3];
        QGHttpRequest.getInstance().delCommentRequest(this, topicId, commentId, new C16867(this, ((Integer) params[0]).intValue(), ((Integer) params[2]).intValue()));
    }

    public void gpuback(File file) {
        MyApplication.getInstance().themeFile = file;
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("theme", true);
        startActivityForNew(intent);
    }

    public boolean onTouch(View v, MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
    }

    private void notifyNetStatus() {
        if (!NetUtils.checkNet(this)) {
            Constant.netWorkStatus = -1;
        } else if (NetUtils.getWifi(this)) {
            Constant.netWorkStatus = 1;
        } else {
            Constant.netWorkStatus = 0;
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

    public void onReceive(Context context, Intent intent) {
        String topicId;
        if (Constant.DEL_TOPIC_ACTION.equals(intent.getAction())) {
            topicId = intent.getStringExtra("topicId");
            String localTopicId = intent.getStringExtra("localTopicId");
            if (topicId != null) {
                deleteTopic(topicId);
            } else if (localTopicId != null) {
                deleteLocalTopic(localTopicId);
            }
            this.mHomeAdapter.notifyDataSetChanged();
        } else if (Constant.BLOCK_TOPIC_ACTION.equals(intent.getAction())) {
            deleteBlockUserTopic(intent.getStringExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID));
        } else if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
            String SYSTEM_HOME_KEY = "homekey";
            String reason = intent.getStringExtra("reason");
            if (reason != null && SYSTEM_HOME_KEY.equals(reason)) {
                MyApplication.getInstance().setClick_home(true);
            }
        } else if (Constant.MODIFY_MARKNAME_ACTION.equals(intent.getAction())) {
            modifyMarkName(intent.getStringExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID), intent.getStringExtra("remark"));
        } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            notifyNetStatus();
        } else if (Constant.ZAN_TOPIC_ACTION.equals(intent.getAction())) {
            String zan_topic_id = intent.getStringExtra("topic_zan_topicId");
            int zan_status = intent.getIntExtra("topic_zan_status", 0);
            if (this.mTopicList != null && this.mTopicList != null) {
                if (this.mTopicList.size() > 0) {
                    int i = 0;
                    while (true) {
                        if (i < this.mTopicList.size()) {
                            TopicInfo topicInfo = (TopicInfo) this.mTopicList.get(i);
                            if (!(topicInfo == null || topicInfo.getTopicid() == null || zan_topic_id == null)) {
                                if (topicInfo.getTopicid().equals(zan_topic_id)) {
                                    topicInfo.setIslike(zan_status);
                                    return;
                                }
                            }
                            i++;
                        } else {
                            return;
                        }
                    }
                }
            }
        } else if (Constant.ADD_FAV_TOPIC_ACTION.equals(intent.getAction())) {
            topicId = intent.getStringExtra("topicId");
            if (this.mTopicList != null && this.mTopicList != null) {
                if (this.mTopicList.size() > 0) {
                    int i = 0;
                    while (true) {
                        if (i < this.mTopicList.size()) {
                            TopicInfo topicInfo = (TopicInfo) this.mTopicList.get(i);
                            if (topicInfo != null) {
                                if (topicInfo.getTopicid().equals(topicId)) {
                                    topicInfo.setIsfav(Integer.valueOf(1));
                                    return;
                                }
                            }
                            i++;
                        } else {
                            return;
                        }
                    }
                }
            }
        } else if (Constant.DEL_FAV_TOPIC_ACTION.equals(intent.getAction())) {
            topicId = intent.getStringExtra("topicId");
            if (this.mTopicList != null && this.mTopicList != null) {
                if (this.mTopicList.size() > 0) {
                    int i = 0;
                    while (true) {
                        if (i < this.mTopicList.size()) {
                            TopicInfo topicInfo = (TopicInfo) this.mTopicList.get(i);
                            if (topicInfo != null) {
                                if (topicInfo.getTopicid().equals(topicId)) {
                                    topicInfo.setIsfav(Integer.valueOf(0));
                                    this.mHomeAdapter.notifyDataSetChanged();
                                    return;
                                }
                            }
                            i++;
                        } else {
                            return;
                        }
                    }
                }
            }
        } else if (Constant.UNFOLLOW_USER_ACTION.equals(intent.getAction()) || Constant.FOLLOW_USER_ACTION.equals(intent.getAction())) {
            String uid = intent.getStringExtra("to_uid");
            String relation = intent.getStringExtra("relation");
            if (this.mTopicList != null && this.mTopicList != null) {
                if (this.mTopicList.size() > 0) {
                    boolean isFound = false;
                    int i = 0;
                    while (true) {
                        if (i >= this.mTopicList.size()) {
                            break;
                        }
                        TopicInfo topicInfo = (TopicInfo) this.mTopicList.get(i);
                        if (topicInfo != null) {
                            TutuUsers users = topicInfo.getUserinfo();
                            if (users != null) {
                                if (users.getUid().equals(uid)) {
                                    users.setRelation(relation);
                                    isFound = true;
                                }
                            }
                        }
                        i++;
                    }
                    if (isFound) {
                        this.mHomeAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    public TopicInfo getHotFirstItemData() {
        if (this.mTopicList == null || this.mTopicList.size() == 0) {
            return null;
        }
        return (TopicInfo) this.mTopicList.get(0);
    }
}
