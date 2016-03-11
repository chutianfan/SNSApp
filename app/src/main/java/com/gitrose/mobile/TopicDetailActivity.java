package com.gitrose.mobile;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.XListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.mobile.adapter.HomeTopicAdapter;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.base.ITopicDisplay;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.dao.RequestDao;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.Comment;
import com.gitrose.mobile.model.TopicInfoList;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.view.AutoCoverFlowHelper;
import com.gitrose.mobile.view.BaseDialog;
import com.gitrose.mobile.view.CircleImageView;
import com.gitrose.mobile.view.TipPop;
//import io.rong.message.BuildConfig;
import java.util.ArrayList;
import java.util.List;
//import org.apache.http.Header;
//import shouji.gexing.framework.utils.NetUtils;

public class TopicDetailActivity extends BaseActivity implements OnClickListener, ITopicDisplay, OnDismissListener {
    public static final String COMMENT_ID = "comment_id";
    public static final int FLOW_FIRST_WHAT = 1;
    public static final int FLOW_SELECTED_WHAT = 2;
    public static final String LIST_POS = "list_pos";
    public static final String LOCAL_ID = "local_id";
    public static final String TOPIC_ID = "topic_id";
    private CircleImageView avatorView;
    private ImageView backImg;
    private RelativeLayout container_rl;
    private TipPop delCommentPop;
    private ImageView delImg;
    private View delPop;
    private ImageView delTopicImg;
    private boolean fromNotifi;
    private HomeTopicAdapter mAdapter;
    private BroadcastReceiver mBroadcastReceiver;
    private String mCommentId;
    private BaseDialog mDialog;
    private AutoCoverFlowHelper mFlowHelper;
    @SuppressLint({"HandlerLeak"})
    private Handler mHandler;
    private boolean mIsDelCommentFinish;
    private String mLocalTopicId;
    private RequestDao mRequestDao;
    private String mTipId;
    private String mTopicId;
    private List<TopicInfo> mTopicList;
    private XListView mTopicLv;
    private static TopicDetailActivity topicDetailActivity;

    public static TopicDetailActivity getInstance() {
        return topicDetailActivity;
    }

    /* renamed from: com.gitrose.mobile.TopicDetailActivity.1 */
    class C03281 extends Handler {
        C03281() {
        }

        public void handleMessage(Message msg) {
//            if (msg.what == TopicDetailActivity.FLOW_FIRST_WHAT) {
//                TopicDetailActivity.this.mFlowHelper.startAutoFlow(TopicDetailActivity.FLOW_FIRST_WHAT, false);
//            } else if (msg.what == TopicDetailActivity.FLOW_SELECTED_WHAT) {
//                TopicDetailActivity.this.mFlowHelper.locationSelectedComment(TopicDetailActivity.FLOW_FIRST_WHAT, msg.arg1, 0);
//            }
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailActivity.2 */
    class C03292 extends BroadcastReceiver {
        C03292() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.UPLOAD_TOPIC_FINISH_ACTION) && intent.getStringExtra("localTopicId").equals(TopicDetailActivity.this.mLocalTopicId)) {
                TopicInfo topic = (TopicInfo) intent.getSerializableExtra("topic");
                TopicDetailActivity.this.mTopicList.clear();
                TopicDetailActivity.this.mTopicList.add(topic);
                TopicDetailActivity.this.mAdapter.notifyDataSetChanged();
                TopicDetailActivity.this.findSelectedComment();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailActivity.3 */
    class C03303 implements OnClickListener {
        C03303() {
        }

        public void onClick(View v) {
            TopicDetailActivity.this.showDialog(true);
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailActivity.4 */
    class C07034 extends QGHttpHandler<TopicInfoList> {
        C07034(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(TopicInfoList data) {
//            TopicInfo topicInfo = (TopicInfo) data.getList().get(0);
//            String avatorUrl = ImageUtils.getAvatarUrl(topicInfo.getUid(), topicInfo.getAvatartime());
//            TopicDetailActivity.this.avatorView.setTag(topicInfo.getUid());
//            ImageLoader.getInstance().displayImage(avatorUrl, TopicDetailActivity.this.avatorView, Constant.AVATAR_OPTIONS);
//            TopicDetailActivity.this.mTopicList.add(topicInfo);
//            TopicDetailActivity.this.mAdapter.notifyDataSetChanged();
//            TopicDetailActivity.this.delImgVisibility();
//            TopicDetailActivity.this.findSelectedComment();
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
//            TopicDetailActivity.this.delTopicImg.setVisibility(0);
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailActivity.5 */
    class C07045 extends QGHttpHandler<String> {
        C07045(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(String data) {
            TopicDetailActivity.this.sendBroadcast(TopicDetailActivity.this.mTopicId, false);
            TopicDetailActivity.this.delTopicProcess();
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailActivity.6 */
    class C07056 extends QGHttpHandler<String> {
        C07056(Context $anonymous0, boolean $anonymous1) {
            super($anonymous0, $anonymous1);
        }

        public void onGetDataSuccess(String data) {
            if (MyApplication.getInstance().getNewsNum() > 0) {
                MyApplication.getInstance().setNewsNum(MyApplication.getInstance().getNewsNum() - 1);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailActivity.7 */
//    class C07067 extends QGHttpHandler<CommentList> {
//        private final /* synthetic */ Comment val$comment;
//        private final /* synthetic */ TopicInfo val$topicInfo;
//
//        C07067(Context $anonymous0, TopicInfo topicInfo, Comment comment) {
//            this.val$topicInfo = topicInfo;
//            this.val$comment = comment;
//            super($anonymous0);
//        }
//
//        public void onGetDataSuccess(CommentList data) {
//            if (data != null) {
//                this.val$topicInfo.setUserComment(true);
//                List<Comment> commentList = this.val$topicInfo.getCommentlist();
//                this.val$topicInfo.setTotalcomment(Integer.parseInt(data.getTotal()));
//                commentList.clear();
//                commentList.addAll(data.getCommentlist());
//                TopicDetailActivity.this.mAdapter.notifyLastCommentPos(0, true, false);
//            }
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            if (statusCode == 100019 || statusCode == 100020) {
//                Toast.makeText(TopicDetailActivity.this, responseString, 0).show();
//                this.val$topicInfo.setUserComment(true);
//                List<Comment> commentList = this.val$topicInfo.getCommentlist();
//                this.val$topicInfo.setTotalcomment(this.val$topicInfo.getTotalcomment() - 1);
//                this.val$topicInfo.getCommentlist().remove(commentList.size() - 1);
//                if (commentList.size() > 0) {
//                    TopicDetailActivity.this.mAdapter.notifyCommentPos(0, -1, true, false);
//                    return;
//                } else {
//                    TopicDetailActivity.this.mAdapter.notifyDataSetChanged();
//                    return;
//                }
//            }
//            TopicDetailActivity.this.mRequestDao.insertComment(this.val$comment);
//        }
//    }
//
//    /* renamed from: com.gitrose.mobile.TopicDetailActivity.8 */
//    class C07078 extends QGHttpHandler<String> {
//        private final /* synthetic */ int val$commentPos;
//        private final /* synthetic */ int val$listPos;
//
//        C07078(Context $anonymous0, int i, int i2) {
//            this.val$listPos = i;
//            this.val$commentPos = i2;
//            super($anonymous0);
//        }
//
//        public void onGetDataSuccess(String data) {
//            if (TopicDetailActivity.this.delCommentPop != null) {
//                TopicDetailActivity.this.delCommentPop.dismiss();
//            }
//            TopicInfo topicInfo = (TopicInfo) TopicDetailActivity.this.mTopicList.get(this.val$listPos);
//            topicInfo.setTotalcomment(topicInfo.getTotalcomment() - 1);
//            topicInfo.getCommentlist().remove(this.val$commentPos);
//            TopicDetailActivity.this.mAdapter.notifyCommentPos(this.val$listPos, this.val$commentPos - 1, true, false);
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            if (TopicDetailActivity.this.delCommentPop != null) {
//                TopicDetailActivity.this.delCommentPop.dismiss();
//            }
//            Toast.makeText(TopicDetailActivity.this, "\u4eb2\uff0c\u7f51\u7edc\u7e41\u5fd9\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5...", 0).show();
//        }
//    }

    public TopicDetailActivity() {
        this.mHandler = new C03281();
        this.mBroadcastReceiver = new C03292();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        topicDetailActivity = this;
        this.mTopicId = getIntent().getStringExtra(TOPIC_ID);
        this.mCommentId = getIntent().getStringExtra(COMMENT_ID);
        this.mLocalTopicId = getIntent().getStringExtra(LOCAL_ID);
        this.mTipId = getIntent().getStringExtra("tipid");
        this.fromNotifi = getIntent().getBooleanExtra("fromNotifi", false);
        this.container_rl = (RelativeLayout) findViewById(R.id.container_rl);
        this.delTopicImg = (ImageView) findViewById(R.id.img_del_topic);
        this.mTopicLv = (XListView) findViewById(R.id.lv_topic_list);
        this.mTopicLv.setPullLoadEnable(false);
        this.mTopicLv.setPullRefreshEnable(false);
        this.backImg = (ImageView) findViewById(R.id.title_tv_left);
        this.delImg = (ImageView) findViewById(R.id.title_right_iv);
        this.avatorView = (CircleImageView) getViewById(R.id.circleimg_current_show_avatar);
        initPop();
        this.backImg.setOnClickListener(this);
        this.delImg.setOnClickListener(this);
        this.avatorView.setOnClickListener(this);
        this.mTopicList = new ArrayList(FLOW_FIRST_WHAT);
//        this.mFlowHelper = new AutoCoverFlowHelper(this.mTopicLv);
//        this.mAdapter = new HomeTopicAdapter(this, this.mTopicLv, this.mTopicList, true, false);
        this.mTopicLv.setAdapter(this.mAdapter);
        this.mRequestDao = new RequestDao(this);
        if (!(this.mTipId == null || this.mTipId.trim().equals(BuildConfig.FLAVOR))) {
            readTip(this.mTipId);
        }
        registerBoradcastReceiver();
        requestTopicInfo();
    }

    private void initPop() {
        this.mDialog = new BaseDialog(this);
        this.delPop = getLayoutInflater().inflate(R.layout.dialog_base_layout, null);
        this.delPop.findViewById(R.id.pop_tv_submit).setOnClickListener(this);
        this.delPop.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
        String[] strArr = new String[FLOW_FIRST_WHAT];
        strArr[0] = "\u5220\u9664";
        this.delCommentPop = new TipPop(this, strArr, new C03303());
    }

    public void requestTopicInfo() {
        if (this.mTopicId == null && this.mLocalTopicId != null) {
            TopicInfo topicInfo = this.mRequestDao.getTopic(this.mLocalTopicId);
            if (topicInfo != null) {
                this.mTopicList.add(topicInfo);
                this.mAdapter.notifyDataSetChanged();
                delImgVisibility();
                findSelectedComment();
                return;
            }
        }
        QGHttpRequest.getInstance().topicDetailRequest(this, this.mTopicId, this.mCommentId, new C07034(this, true, this.container_rl));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                finish();
            case R.id.circleimg_current_show_avatar:
                startPersonalActivity(v);
            case R.id.title_right_iv:
                showDialog(false);
            case R.id.pop_tv_submit:
                if (((Boolean) v.getTag()).booleanValue()) {
                    delComment(v);
                } else {
                    delTopic();
                }
            case R.id.pop_tv_cancel:
                this.mDialog.dismiss();
                if (this.delCommentPop != null) {
                    this.delCommentPop.dismiss();
                }
            default:
        }
    }

    private void delImgVisibility() {
        String uid = ((TopicInfo) this.mTopicList.get(0)).getUid();
//        if (uid != null && MyApplication.getInstance().isLogin() && uid.equals(MyApplication.getInstance().getUserinfo().getUid())) {
//            this.delImg.setVisibility(0);
//        } else {
//            this.delImg.setVisibility(4);
//        }
    }

    private void findSelectedComment() {
        Message msg = this.mHandler.obtainMessage();
        if (this.mCommentId != null) {
            TopicInfo topicInfo = (TopicInfo) this.mTopicList.get(0);
            topicInfo.setUserComment(true);
            List<Comment> comments = topicInfo.getCommentlist();
            int commentSize = comments.size();
            int selectPos = 0;
            for (int i = 0; i < commentSize; i += FLOW_FIRST_WHAT) {
                if (((Comment) comments.get(i)).getCommentid().equals(this.mCommentId)) {
                    selectPos = i;
                }
            }
            msg.what = FLOW_SELECTED_WHAT;
            msg.arg1 = selectPos;
        } else {
            msg.what = FLOW_FIRST_WHAT;
        }
        this.mHandler.sendMessageDelayed(msg, 200);
    }

    private void delTopic() {
        if (this.mTopicId != null) {
            QGHttpRequest.getInstance().delTopicRequest(this, this.mTopicId, new C07045(this));
        } else if (this.mLocalTopicId != null) {
            this.mRequestDao.removeLocalTopic(this.mLocalTopicId);
            sendBroadcast(this.mLocalTopicId, true);
            delTopicProcess();
        }
    }

    private void delTopicProcess() {
        this.mDialog.dismiss();
//        Toast.makeText(this, "\u5220\u9664\u4e3b\u9898\u6210\u529f\uff01", 0).show();
        finish();
    }

    private void sendBroadcast(String topicId, boolean isLocal) {
        Intent intent = new Intent();
        intent.putExtra(isLocal ? "localTopicId" : "topicId", topicId);
        intent.setAction(Constant.DEL_TOPIC_ACTION);
        sendBroadcast(intent);
    }

    protected void onPause() {
        super.onPause();
        this.mFlowHelper.stopAutoFlow();
    }

    protected void onResume() {
        super.onResume();
        this.mFlowHelper.resumeAutoFlow();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
//            case HomeActivity.ADD_COMMENT_RESULT /*9*/:
//                String uid = MyApplication.getInstance().getUserinfo().getUid();
//                String[] commentParams = data.getStringArrayExtra("commentParams");
//                int listPos = data.getIntExtra("listPosition", 0);
//                Comment comment = new Comment();
//                comment.setUid(uid);
//                comment.setCommentid(new StringBuilder(String.valueOf(uid)).append(System.currentTimeMillis()).toString());
//                comment.setTopicId(commentParams[0]);
//                comment.setLocationx(commentParams[3]);
//                comment.setLocationy(commentParams[4]);
//                comment.setTxtframe(commentParams[5]);
//                comment.setReplyCommentId(commentParams[FLOW_SELECTED_WHAT]);
//                comment.setContent(commentParams[FLOW_FIRST_WHAT]);
//                TopicInfo topicInfo = (TopicInfo) this.mTopicList.get(listPos);
//                List<Comment> comList = topicInfo.getCommentlist();
//                topicInfo.setTotalcomment(topicInfo.getTotalcomment() + FLOW_FIRST_WHAT);
//                comList.add(comment);
//                this.mAdapter.notifyLastCommentPos(listPos, true, comList.size() == FLOW_FIRST_WHAT);
//                uploadComment(commentParams, comment, topicInfo);
//            case HomeActivity.FAV_RESULT /*15*/:
//                int listPosition = data.getIntExtra("listPos", 0);
//                int isFav = data.getIntExtra("isFav", 0);
//                if (this.mTopicList.size() > listPosition) {
//                    ((TopicInfo) this.mTopicList.get(listPosition)).setIsfav(isFav);
//                }
//            default:
//                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void readTip(String tip_id) {
        QGHttpRequest.getInstance().readTipRequest(MyApplication.getInstance(), tip_id, new C07056(this, false));
    }

    private void uploadComment(String[] commentParams, Comment comment, TopicInfo topicInfo) {
//        if (NetUtils.checkNet(this)) {
//            QGHttpRequest.getInstance().addComment(this, commentParams[0], commentParams[FLOW_FIRST_WHAT], commentParams[FLOW_SELECTED_WHAT], commentParams[3], commentParams[4], commentParams[5], new C07067(this, topicInfo, comment));
//        } else {
//            this.mRequestDao.insertComment(comment);
//        }
    }

    public void finish() {
//        if (this.fromNotifi && HomeActivity.getIntance() == null) {
//            startActivity(new Intent(this, HomeActivity.class));
//        }
        super.finish();
    }

    private void startPersonalActivity(View view) {
//        String uid = (String) view.getTag();
//        if (uid != null) {
//            Intent intent = new Intent();
//            intent.setClass(this, PersonalActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString(PersonalActivity.PERSONAL_USER_ID, uid);
//            intent.putExtras(bundle);
//            startActivityForNew(intent);
//        }
    }

    public void registerBoradcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.UPLOAD_TOPIC_FINISH_ACTION);
        registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    protected void onDestroy() {
        unregisterReceiver(this.mBroadcastReceiver);
        super.onDestroy();
    }

    public AutoCoverFlowHelper getFlowHelper() {
        return this.mFlowHelper;
    }

    @Override
    public MediaPlayer getMediaPlayer() {
        return null;
    }

    @Override
    public boolean isPause() {
        return false;
    }

    @Override
    public void onDelCommentListener(View view, int i, String str, int i2, String str2, boolean z, TopicInfo topicInfo) {

    }

    @Override
    public void showLayerMask() {

    }

    public void showDialog(boolean isDelComment) {
        CharSequence string;
        TextView tipTitle = (TextView) this.delPop.findViewById(R.id.tv_tip_title);
        if (isDelComment) {
            string = getResources().getString(R.string.topic_del_comment);
        } else {
            string = getResources().getString(R.string.topic_detail_del_topic_tip);
        }
        tipTitle.setText(string);
        this.delPop.findViewById(R.id.pop_tv_submit).setTag(Boolean.valueOf(isDelComment));
        this.mDialog.show(this.delPop);
    }

    public void onDelCommentListener(View commentView, int listPos, String topicId, int commentPos, String commentId, boolean isHasFrame) {
//        int i = 0;
//        PlaySound.getInstance(this).toPlay(R.raw.delete);
//        this.delPop.setTag(new Object[]{Integer.valueOf(listPos), topicId, Integer.valueOf(commentPos), commentId});
//        TipPop tipPop = this.delCommentPop;
//        if (isHasFrame) {
//            i = getResources().getDimensionPixelOffset(R.dimen.white_broder_width);
//        }
//        tipPop.show(commentView, i);
    }

    private void delComment(View v) {
//        this.mDialog.dismiss();
//        Object[] params = (Object[]) ((View) v.getParent()).getTag();
//        String topicId = params[FLOW_FIRST_WHAT];
//        String commentId = params[3];
//        QGHttpRequest.getInstance().delCommentRequest(this, topicId, commentId, new C07078(this, ((Integer) params[0]).intValue(), ((Integer) params[FLOW_SELECTED_WHAT]).intValue()));
    }

    public void onDismiss() {
        if (this.mIsDelCommentFinish) {
            this.mIsDelCommentFinish = false;
        } else {
            this.mFlowHelper.resumeAutoFlow();
        }
    }

    public int getBlockTopic() {
        return 0;
    }

    @Override
    public View getConvertView() {
        return null;
    }

    @Override
    public int getDownloadPos() {
        return 0;
    }
}
