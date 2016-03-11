package com.gitrose.mobile.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build.VERSION;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
//import com.tencent.qalsdk.im_open.stat_reg.RspBody;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.R;
//import com.gitrose.mobile.PersonalNewActivity;
//import com.gitrose.mobile.VideoViewPlayActivity;
import com.gitrose.mobile.WebViewActivity;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.ITopicDisplay;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.download.video.DownLoadObserver;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.model.Comment;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.utils.TextUtil;
import com.gitrose.mobile.utils.VideoUtils;
import com.gitrose.mobile.view.VideoPlayTextureView.MediaState;
import io.rong.common.ResourceUtils;
import java.io.File;
import java.util.List;
import qalsdk.BaseConstants;
import shouji.gexing.framework.utils.StringUtils;
import shouji.gexing.framework.utils.UiUtils;

@SuppressLint({"NewApi"})
public final class CommentImageView extends FrameLayout {
    public static final String BLACK_COLOR = "#000000";
    public static final int IMG_FAILURE = -1;
    public static final int IMG_FINISH = 1;
    public static final int IMG_LOADING = 0;
    public static final String WHITE_COLOR = "#FFFFFF";
    public static int comment_view_px_size;
    boolean from_live;
    private boolean isBufferChange;
    public boolean isHasVideo;
    private boolean isHasWhiteisFrame;
    private boolean isMoved;
    public boolean isOpenComment;
    private boolean isPlaying;
    private boolean isReceiveVideoData;
    private boolean isReleased;
    private int mClickTimeout;
    int mClickTopicCount;
    public Runnable mClickTopicRunnable;
    private OnTouchListener mCommentListener;
    public int mCommentWidth;
    private TextView mCommmentView;
    private Context mContext;
    private int mCounter;
    private int mDefaultPicWidth;
    private int mDoubleClickTimeOut;
    private float mFirstDownX;
    private float mFirstDownY;
    public int mImgLoadStatus;
    private boolean mIsDelComment;
    private boolean mIsNewVersion;
    private int mListPosition;
    private int mLongClickTimeOut;
    private Runnable mLongPressRunnable;
    private int mMaxFont;
    private int mMinFont;
    private VideoPlayTextureView mNewVideoView;
    private ImageView mPicView;
    private int mPosition;
    private RoundProgressBar mProgressBar;
    private ImageView mStartPlayBtn;
    public TopicInfo mTopicInfo;
    private Runnable mTouchFlingRunnable;
    private int mTouchSlop;
    private ProgressBar mVideoProgress;
    float[] point;
    private Fragment themeDetailFragment;
    final Runnable videoProgressRunnable;

    /* renamed from: com.gitrose.mobile.view.CommentImageView.1 */
    class C09191 implements Runnable {
        C09191() {
        }

        public void run() {
            if (CommentImageView.this.mClickTopicCount == CommentImageView.IMG_FINISH) {
                if (CommentImageView.this.mTopicInfo == null || CommentImageView.this.mTopicInfo.getType().intValue() != 2) {
                    CommentImageView.this.clickTopic();
                } else {
                    CommentImageView.this.clickToLive();
                }
            }
            CommentImageView.this.mClickTopicCount = CommentImageView.IMG_LOADING;
        }
    }

    /* renamed from: com.gitrose.mobile.view.CommentImageView.2 */
    class C09202 implements Runnable {
        C09202() {
        }

        public void run() {
            CommentImageView commentImageView = CommentImageView.this;
            commentImageView.mCounter = commentImageView.mCounter + CommentImageView.IMG_FAILURE;
            if (CommentImageView.this.mCounter <= 0 && !CommentImageView.this.isReleased && !CommentImageView.this.isMoved) {
                CommentImageView.this.longClickComment();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.view.CommentImageView.3 */
    class C09213 implements Runnable {
        C09213() {
        }

        public void run() {
            ITopicDisplay iTopicDisplay;
            if (CommentImageView.this.themeDetailFragment != null) {
                iTopicDisplay = (ITopicDisplay) CommentImageView.this.themeDetailFragment;
            } else {
                iTopicDisplay = (ITopicDisplay) CommentImageView.this.mContext;
            }
            AutoCoverFlowHelper flowHelper = iTopicDisplay.getFlowHelper();
            if (flowHelper != null) {
                flowHelper.resumeShortAutoFlow();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.view.CommentImageView.4 */
    class C09224 implements Runnable {
        C09224() {
        }

        public void run() {
            boolean z = false;
            if (!CommentImageView.this.isPlaying && !CommentImageView.this.isReceiveVideoData) {
                int i;
                CommentImageView.this.mVideoProgress.postDelayed(this, 1000);
                ProgressBar access$11 = CommentImageView.this.mVideoProgress;
                if (CommentImageView.this.isBufferChange) {
                    i = CommentImageView.IMG_LOADING;
                } else {
                    i = CommentImageView.IMG_FINISH;
                }
                access$11.setProgress(i);
                CommentImageView commentImageView = CommentImageView.this;
                if (!CommentImageView.this.isBufferChange) {
                    z = true;
                }
                commentImageView.isBufferChange = z;
            }
        }
    }

    /* renamed from: com.gitrose.mobile.view.CommentImageView.5 */
    class C09235 implements OnClickListener {
        C09235() {
        }

        public void onClick(View v) {
            if (CommentImageView.this.mIsNewVersion) {
                CommentImageView.this.downloadVideo();
                CommentImageView.this.mStartPlayBtn.setVisibility(View.INVISIBLE);
                return;
            }
//            VideoViewPlayActivity.startVideoViewPlayActivity(CommentImageView.this.mContext, CommentImageView.this.mTopicInfo);
        }
    }

    /* renamed from: com.gitrose.mobile.view.CommentImageView.9 */
    class C09249 implements Runnable {
        C09249() {
        }

        public void run() {
            CommentImageView.this.mPicView.setVisibility(View.INVISIBLE);
        }
    }

    final class OnCommentListener implements OnTouchListener {
        OnCommentListener() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            float x = event.getX();
            float y = event.getY();
            float deltaX;
            switch (action) {
                case CommentImageView.IMG_LOADING /*0*/:
                    CommentImageView.this.mFirstDownX = x;
                    CommentImageView.this.mFirstDownY = y;
                    CommentImageView.this.isReleased = false;
                    if (CommentImageView.this.mIsDelComment) {
                        CommentImageView commentImageView = CommentImageView.this;
                        commentImageView.mCounter = commentImageView.mCounter + CommentImageView.IMG_FINISH;
                        CommentImageView.this.isMoved = false;
                        CommentImageView.this.setAutoFlowState(false);
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        CommentImageView.this.postDelayed(CommentImageView.this.mLongPressRunnable, (long) CommentImageView.this.mLongClickTimeOut);
                        break;
                    }
                    break;
                case CommentImageView.IMG_FINISH /*1*/:
                    CommentImageView.this.isReleased = true;
                    deltaX = Math.abs(x - CommentImageView.this.mFirstDownX);
                    float time = (float) (event.getEventTime() - event.getDownTime());
                    if (Math.abs(y - CommentImageView.this.mFirstDownY) >= ((float) CommentImageView.this.mTouchSlop) || deltaX >= ((float) CommentImageView.this.mTouchSlop) || time >= ((float) CommentImageView.this.mClickTimeout)) {
                        if (CommentImageView.this.mIsDelComment) {
                            if (CommentImageView.this.isMoved && CommentImageView.this.isReleased) {
                                CommentImageView.this.setAutoFlowState(true);
                            }
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                        }
                    }
                    CommentImageView.this.clickComment();
                    break;
//                case PersonalNewActivity.REMARK_NAME_RESULT /*2*/:
//                    if (CommentImageView.this.mIsDelComment && !CommentImageView.this.isMoved) {
//                        deltaX = Math.abs(x - CommentImageView.this.mFirstDownX);
//                        float deltaY = Math.abs(y - CommentImageView.this.mFirstDownY);
//                        if (deltaX > ((float) CommentImageView.this.mTouchSlop) || deltaY > ((float) CommentImageView.this.mTouchSlop)) {
//                            CommentImageView.this.isMoved = true;
//                            break;
//                        }
//                    }
            }
            return true;
        }
    }

    /* renamed from: com.gitrose.mobile.view.CommentImageView.8 */
    class C14738 implements ImageLoadingProgressListener {
        C14738() {
        }

        public void onProgressUpdate(String imageUri, View view, int current, int total) {
            CommentImageView.this.mProgressBar.setProgress((current * 100) / total);
        }
    }

    /* renamed from: com.gitrose.mobile.view.CommentImageView.6 */
    class C18046 extends SimpleImageLoadingListener {
        private final /* synthetic */ Comment val$comment;
        private final /* synthetic */ int val$pos;

        C18046(int i, Comment comment) {
            this.val$pos = i;
            this.val$comment = comment;
        }

        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (this.val$pos == CommentImageView.this.mPosition) {
                CommentImageView.this.mCommmentView.setBackgroundDrawable(new BitmapDrawable(loadedImage));
                CommentImageView.this.setCommentStyle(this.val$comment);
            }
        }

        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            super.onLoadingFailed(imageUri, view, failReason);
            if (this.val$pos == CommentImageView.this.mPosition) {
                CommentImageView.this.mCommmentView.setBackgroundResource(R.drawable.comment_input_01);
                CommentImageView.this.setCommentStyle(this.val$comment);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.view.CommentImageView.7 */
    class C18057 extends SimpleImageLoadingListener {
        C18057() {
        }

        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            super.onLoadingComplete(imageUri, view, loadedImage);
            CommentImageView.this.mProgressBar.setVisibility(View.INVISIBLE);
            if (!CommentImageView.this.from_live) {
                CommentImageView.this.startCoverFlow();
            }
        }

        public void onLoadingStarted(String imageUri, View view) {
            CommentImageView.this.mProgressBar.setVisibility(View.VISIBLE);
            super.onLoadingStarted(imageUri, view);
        }

        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            CommentImageView.this.mProgressBar.setProgress(CommentImageView.IMG_FAILURE);
            CommentImageView.this.mImgLoadStatus = CommentImageView.IMG_FAILURE;
            super.onLoadingFailed(imageUri, view, failReason);
        }
    }

    public CommentImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMaxFont = IMG_LOADING;
        this.mMinFont = IMG_LOADING;
        this.isHasVideo = true;
        this.from_live = false;
        this.point = new float[2];
        this.mClickTopicCount = IMG_LOADING;
        this.mClickTopicRunnable = new C09191();
        this.mCounter = IMG_LOADING;
        this.mLongPressRunnable = new C09202();
        this.mTouchFlingRunnable = new C09213();
        this.videoProgressRunnable = new C09224();
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        this.mIsNewVersion = VERSION.SDK_INT >= 14;
        this.mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.mLongClickTimeOut = ViewConfiguration.getLongPressTimeout();
        this.mDoubleClickTimeOut = ViewConfiguration.getDoubleTapTimeout();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mDefaultPicWidth = UiUtils.getInstance(context).getmScreenWidth();
        this.mCommentWidth = getResources().getDimensionPixelOffset(R.dimen.home_comment_width);
        this.mMaxFont = getResources().getInteger(R.integer.max_font);
        this.mMinFont = getResources().getInteger(R.integer.min_font);
        if (comment_view_px_size == 0) {
            comment_view_px_size = getResources().getDimensionPixelOffset(R.dimen.home_comment_width);
        }
        this.mCommentListener = new OnCommentListener();
        addVideoView();
    }

    private void addVideoView() {
        LayoutParams lp = new LayoutParams(IMG_FAILURE, IMG_FAILURE);
        lp.gravity = 17;
        if (this.mIsNewVersion) {
            this.mNewVideoView = new VideoPlayTextureView(this.mContext);
            addView(this.mNewVideoView, IMG_LOADING, lp);
            return;
        }
        addView(new View(this.mContext), IMG_LOADING, lp);
    }

    public void fillData(int listPosition, TopicInfo topicInfo, boolean from_live) {
        this.from_live = from_live;
        fillData(listPosition, topicInfo);
    }

    public void fillData(int listPosition, TopicInfo topicInfo) {
        if (topicInfo != null) {
            measureLayoutParams();
            this.mImgLoadStatus = IMG_LOADING;
            this.mTopicInfo = topicInfo;
            this.mListPosition = listPosition;
            this.isOpenComment = true;
            this.isReceiveVideoData = false;
            this.isPlaying = false;
            this.isHasVideo = this.mTopicInfo.getType().intValue() == 5;
            this.mPicView = (ImageView) getChildAt(IMG_FINISH);
            this.mProgressBar = (RoundProgressBar) getChildAt(2);
            this.mCommmentView = (TextView) getChildAt(3);
            this.mStartPlayBtn = (ImageView) getChildAt(4);
            this.mVideoProgress = (ProgressBar) getChildAt(5);
            this.mPicView.setVisibility(View.VISIBLE);
            this.mProgressBar.setProgress(IMG_LOADING);
            this.mProgressBar.setVisibility(View.INVISIBLE);
            if (this.mVideoProgress != null) {
                this.mVideoProgress.setVisibility(View.INVISIBLE);
                this.mVideoProgress.setProgress(IMG_LOADING);
            }
            if (this.mCommmentView != null) {
                this.mCommmentView.setVisibility(View.INVISIBLE);
                this.mCommmentView.setOnTouchListener(this.mCommentListener);
            }
            if (this.mStartPlayBtn != null) {
                this.mStartPlayBtn.setVisibility(View.INVISIBLE);
                this.mStartPlayBtn.setOnClickListener(new C09235());
            }
            measurePicView();
            loadPicture();
        }
    }

    private void measureLayoutParams() {
        RelativeLayout.LayoutParams parentLp = (RelativeLayout.LayoutParams) getLayoutParams();
        parentLp.height = this.mDefaultPicWidth;
        parentLp.width = this.mDefaultPicWidth;
        setLayoutParams(parentLp);
    }

    public void nextComment(int position, TopicInfo topicInfo) {
        List<Comment> comments;
        if (topicInfo == null || BaseConstants.ah.equals(topicInfo)) {
            comments = this.mTopicInfo.getCommentlist();
        } else {
            comments = topicInfo.getCommentlist();
        }
        if (comments == null || comments.size() <= 0 || position >= comments.size()) {
            this.mCommmentView.setVisibility(View.INVISIBLE);
            this.mIsDelComment = false;
            return;
        }
        this.mPosition = position;
        this.mCommmentView.setVisibility(View.VISIBLE);
        setCommentViewBackground((Comment) comments.get(position), position);
        this.mIsDelComment = isRequestLongPressListener(comments);
    }

    private void setCommentViewBackground(Comment comment, int pos) {
        if (comment.getTxtframe() != null) {
            int resID = getResources().getIdentifier(comment.getTxtframe(), ResourceUtils.drawable, this.mContext.getPackageName());
            if (resID == 0) {
                ImageLoader.getInstance().loadImage(new StringBuilder(ConstantURL.TOPIC_DOWNLOAD_EMOTION).append(comment.getTxtframe()).append(".png").toString(), new C18046(pos, comment));
                return;
            }
            this.mCommmentView.setBackgroundResource(resID);
            setCommentStyle(comment);
        }
    }

    private void setCommentStyle(Comment comment) {
        try {
            String content = comment.getContent();
            if (!(content == null || BaseConstants.ah.equals(content))) {
                String replyPart = null;
                Comment replyComment = comment.getReplydata();
                if (replyComment != null) {
                    TutuUsers replyUser = replyComment.getUserinfo();
                    if (replyUser != null) {
                        String remarkName = replyUser.getRemarkname();
                        replyPart = (remarkName == null || BaseConstants.ah.equals(remarkName)) ? new StringBuilder(String.valueOf(this.mContext.getString(R.string.comment_reply_tip))).append(" ").append(replyUser.getNickname()).append(": ").toString() : new StringBuilder(String.valueOf(this.mContext.getString(R.string.comment_reply_tip))).append(" ").append(remarkName).append(": ").toString();
                    }
                }
                this.mCommmentView.setText(Html.fromHtml(getTextSource(replyPart, TextUtil.formatCommentContent(content), comment.getTxtframe())));
            }
            resetCommentViewMargin(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetCommentViewMargin(Comment comment) {
        LayoutParams lp = (LayoutParams) this.mCommmentView.getLayoutParams();
        if (comment.getContent() == null || BaseConstants.ah.equals(comment.getContent())) {
            this.mCommmentView.setText(BaseConstants.ah);
            lp.width = comment_view_px_size;
            lp.height = comment_view_px_size;
            lp.gravity = 81;
            lp.setMargins(IMG_LOADING, IMG_LOADING, IMG_LOADING, 25);
        } else {
            lp.width = this.mDefaultPicWidth;
            lp.height = -2;
            lp.gravity = 81;
            lp.setMargins(20, IMG_LOADING, 20, 25);
            this.mCommmentView.setPadding(55, 40, 55, 40);
        }
        this.mCommmentView.setLayoutParams(lp);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case IMG_LOADING /*0*/:
                this.mFirstDownX = x;
                this.mFirstDownY = y;
                break;
            case IMG_FINISH /*1*/:
                float deltaX = Math.abs(x - this.mFirstDownX);
                float time = (float) (event.getEventTime() - event.getDownTime());
                if (Math.abs(y - this.mFirstDownY) < ((float) this.mTouchSlop) && deltaX < ((float) this.mTouchSlop) && time < ((float) this.mClickTimeout) && this.mTopicInfo != null && this.mTopicInfo.getTopicid() != null) {
                    this.point[IMG_LOADING] = x / ((float) this.mDefaultPicWidth);
                    this.point[IMG_FINISH] = y / ((float) this.mDefaultPicWidth);
                    this.mClickTopicCount += IMG_FINISH;
                    if (this.mClickTopicCount != 2) {
                        postDelayed(this.mClickTopicRunnable, (long) this.mDoubleClickTimeOut);
                        break;
                    }
                    this.mClickTopicCount = IMG_LOADING;
                    doubleClickTopic();
                    break;
                }
        }
        return true;
    }

    private void doubleClickTopic() {
        if (this.mTopicInfo.getType().intValue() == 2) {
            clickToLive();
            return;
        }
        View likeView = ((View) getParent()).findViewById(R.id.tv_home_zan);
        if (likeView != null) {
            likeView.performClick();
        }
    }

    private void clickToLive() {
        View liveView = findViewById(R.id.img_topic_live_mark);
        if (liveView != null) {
            liveView.performClick();
        }
    }

    private void clickTopic() {
        switch (this.mImgLoadStatus) {
            case IMG_FAILURE /*-1*/:
                this.mProgressBar.setProgress(IMG_LOADING);
                loadPicture();
            case IMG_LOADING /*0*/:
            case IMG_FINISH /*1*/:
                if (this.mTopicInfo.getShowtype() == IMG_FINISH && this.mTopicInfo.getSpecialdata() != null) {
                    //WebViewActivity.openWebView(this.mContext, this.mTopicInfo.getSpecialdata().getGotourl());
                } else if (this.mTopicInfo.getTopicid() != null) {
                    int videoPos = IMG_LOADING;
                    if (this.isHasVideo) {
                        MediaPlayer mediaPlayer;
                        if (this.themeDetailFragment != null) {
                            mediaPlayer = ((ITopicDisplay) this.themeDetailFragment).getMediaPlayer();
                        } else {
                            mediaPlayer = ((ITopicDisplay) this.mContext).getMediaPlayer();
                        }
                        if (mediaPlayer != null) {
                            videoPos = mediaPlayer.getCurrentPosition();
                        }
                    }
                    TopicMore.clickTopicIntent(this.mContext, this.mTopicInfo, this.mListPosition, this.mPosition, null, videoPos, this.point);
                }
            default:
        }
    }

    private void loadPicture() {
        DisplayImageOptions option = Constant.BIG_PICTURE_OPTIONS;
        if (this.mTopicInfo != null && this.mTopicInfo.getType().intValue() == 5) {
            option = Constant.LIVE_PICTURE_OPTIONS;
        }
        ImageLoader.getInstance().displayImage(this.mTopicInfo.getContent(), this.mPicView, option, new C18057(), new C14738());
    }

    private synchronized void startCoverFlow() {
        this.mImgLoadStatus = IMG_FINISH;
        if (VERSION.SDK_INT >= 14) {
            ITopicDisplay iTopicDisplay;
            if (this.themeDetailFragment != null) {
                iTopicDisplay = (ITopicDisplay) this.themeDetailFragment;
            } else {
                iTopicDisplay = (ITopicDisplay) this.mContext;
            }
            AutoCoverFlowHelper flowHelper = iTopicDisplay.getFlowHelper();
            if (flowHelper != null && iTopicDisplay.getDownloadPos() == this.mListPosition) {
                flowHelper.startAutoFlow(this.mListPosition + flowHelper.mHeaderCount, true, false);
            }
        } else if (this.mTopicInfo.getType().intValue() == 5) {
            this.mStartPlayBtn.setVisibility(View.VISIBLE);
        }
    }

    private void measurePicView() {
        LayoutParams picLayoutParam = (LayoutParams) this.mPicView.getLayoutParams();
        int picWidth;
        int picHeight;
        if (this.mTopicInfo.getType().intValue() == 5) {
            picWidth = this.mDefaultPicWidth;
            picHeight = this.mDefaultPicWidth;
            this.mPicView.setScaleType(ScaleType.CENTER_CROP);
            picLayoutParam.height = picHeight;
            picLayoutParam.width = picWidth;
        } else {
            int realHeight;
            int realWidth;
            try {
                realHeight = Integer.parseInt(this.mTopicInfo.getHeight());
                realWidth = Integer.parseInt(this.mTopicInfo.getWidth());
                if (realWidth == 0 || realHeight == 0) {
                    realHeight = 640;
                    realWidth = 640;
                    this.mTopicInfo.setHeight("640");
                    this.mTopicInfo.setWidth("640");
                }
            } catch (Exception e) {
                realHeight = 640;
                realWidth = 640;
                this.mTopicInfo.setHeight("640");
                this.mTopicInfo.setWidth("640");
            }
            if (realHeight >= realWidth) {
                picHeight = this.mDefaultPicWidth;
                picWidth = (this.mDefaultPicWidth * realWidth) / realHeight;
            } else {
                picWidth = this.mDefaultPicWidth;
                picHeight = (this.mDefaultPicWidth * realHeight) / realWidth;
            }
            this.mPicView.setScaleType(ScaleType.FIT_CENTER);
            picLayoutParam.height = this.mDefaultPicWidth;
            picLayoutParam.width = this.mDefaultPicWidth;
        }
        picLayoutParam.gravity = 17;
        this.mPicView.requestLayout();
    }

    private String getTextSource(String replyPart, String content, String toastBgName) {
        String contextColor;
        String uNameColor = "#777777";
        String newCommentColor = "#333333";
        if (replyPart == null) {
            replyPart = BaseConstants.ah;
        }
        try {
            if (!toastBgName.startsWith("comment")) {
                int number;
                if (toastBgName.length() <= 8) {
                    number = Integer.parseInt(toastBgName.substring(6, 8)) + IMG_FAILURE;
                    this.isHasWhiteisFrame = true;
                    switch (number >> 2) {
//                        case IMG_LOADING /*0*/:
//                        case PersonalNewActivity.REMARK_NAME_RESULT /*2*/:
//                        case PersonalNewActivity.FRAGMENT_FOURE /*3*/:
//                        case PersonalNewActivity.WORK_LIST_DETAIL /*5*/:
//                        case PersonalNewActivity.COLLECT_LIST_DETAIL /*7*/:
//                            contextColor = newCommentColor;
//                            break;
//                        case IMG_FINISH /*1*/:
//                        case PersonalNewActivity.WORK_LIST_9 /*4*/:
//                        case PersonalNewActivity.COLLECT_LIST_9 /*6*/:
//                        case RspBody.UINT32_HELLO_INTERVAL_FIELD_NUMBER /*8*/:
//                            contextColor = newCommentColor;
//                            break;
                        default:
                            contextColor = newCommentColor;
                            break;
                    }
                }
                number = Integer.parseInt(toastBgName.substring(toastBgName.lastIndexOf("_") + IMG_FINISH));
                this.isHasWhiteisFrame = true;
                switch (number) {
                    case IMG_LOADING /*0*/:
                        contextColor = newCommentColor;
                        break;
                    case IMG_FINISH /*1*/:
                        contextColor = newCommentColor;
                        break;
                    default:
                        contextColor = newCommentColor;
                        break;
                }
            }
            contextColor = newCommentColor;
        } catch (Exception e) {
            contextColor = newCommentColor;
            this.isHasWhiteisFrame = false;
        }
        replyPart = StringUtils.ToDBC(replyPart);
        return "<font color=\"" + uNameColor + "\">" + replyPart + "</font><font color=\"" + contextColor + "\">" + StringUtils.ToDBC(content) + "</font>";
    }

    private boolean isRequestLongPressListener(List<Comment> commentList) {
        TutuUsers userInfo = MyApplication.getInstance().getUserinfo();
        String uid = ((Comment) commentList.get(this.mPosition)).getUid();
        return (uid == null || userInfo == null || !uid.equals(userInfo.getUid())) ? false : true;
    }

    private void clickComment() {
        if (this.mTopicInfo.getTopicid() != null && this.mTopicInfo.getType().intValue() != 2) {
            int videoPos = IMG_LOADING;
            if (this.isHasVideo) {
                ITopicDisplay iTopicDisplay;
                if (this.themeDetailFragment != null) {
                    iTopicDisplay = (ITopicDisplay) this.themeDetailFragment;
                } else {
                    iTopicDisplay = (ITopicDisplay) this.mContext;
                }
                MediaPlayer mediaPlayer = iTopicDisplay.getMediaPlayer();
                if (mediaPlayer != null) {
                    videoPos = mediaPlayer.getCurrentPosition();
                }
            }
            TopicMore.clickTopicIntent(this.mContext, this.mTopicInfo, this.mListPosition, IMG_LOADING, (Comment) this.mTopicInfo.getCommentlist().get(this.mPosition), videoPos, this.point);
        }
    }

    private void longClickComment() {
        if (this.mIsDelComment) {
            Comment comment = (Comment) this.mTopicInfo.getCommentlist().get(this.mPosition);
            if (this.themeDetailFragment != null) {
                ((ITopicDisplay) this.themeDetailFragment).onDelCommentListener(this.mCommmentView, this.mListPosition, this.mTopicInfo.getTopicid(), this.mPosition, comment.getCommentid(), this.isHasWhiteisFrame, this.mTopicInfo);
                return;
            } else {
                ((ITopicDisplay) this.mContext).onDelCommentListener(this.mCommmentView, this.mListPosition, this.mTopicInfo.getTopicid(), this.mPosition, comment.getCommentid(), this.isHasWhiteisFrame, this.mTopicInfo);
                return;
            }
        }
        setAutoFlowState(true);
    }

    private void setAutoFlowState(boolean isAutoFlow) {
        if (isAutoFlow) {
            postDelayed(this.mTouchFlingRunnable, 1000);
            return;
        }
        ITopicDisplay iTopicDisplay;
        if (this.themeDetailFragment != null) {
            iTopicDisplay = (ITopicDisplay) this.themeDetailFragment;
        } else {
            iTopicDisplay = (ITopicDisplay) this.mContext;
        }
        iTopicDisplay.getFlowHelper().stopAutoFlow();
    }

    public void hideComment() {
        this.mCommmentView.setVisibility(View.INVISIBLE);
    }

    public void showComment() {
        List<Comment> comments = this.mTopicInfo.getCommentlist();
        if (comments == null || comments.size() <= 0) {
            this.mCommmentView.setVisibility(View.INVISIBLE);
        } else {
            this.mCommmentView.setVisibility(View.VISIBLE);
        }
    }

    public void notifyDownloadVideo() {
        boolean isWifiPlay = this.mContext.getSharedPreferences(Constant.PLAY_SOUND_PERFERENCE, IMG_LOADING).getBoolean(PlaySound.IS_WIFI_PLAY_VIDEO, true);
        if (Constant.netWorkStatus == IMG_FINISH && isWifiPlay) {
            this.mStartPlayBtn.setVisibility(View.INVISIBLE);
            downloadVideo();
            return;
        }
        this.mStartPlayBtn.setVisibility(View.VISIBLE);
    }

    private void downloadVideo() {
        skipVideoProgress();
        if (this.themeDetailFragment != null) {
            ((DownLoadObserver) this.themeDetailFragment).getDownloader().downoadVieo();
        } else {
            ((DownLoadObserver) this.mContext).getDownloader().downoadVieo();
        }
        String videoUrl = this.mTopicInfo.getVideourl();
        if (videoUrl != null && this.mTopicInfo.getType().intValue() == 5) {
            if (videoUrl.startsWith("file:///")) {
                String videoPath = videoUrl.substring(7);
                if (new File(videoPath).exists()) {
                    play(videoPath);
                    return;
                }
                return;
            }
            play(VideoUtils.getVideoAbsoluteFileNameByUrl(this.mContext, videoUrl));
        }
    }

    private void skipVideoProgress() {
        if (!this.isPlaying) {
            this.mVideoProgress.setVisibility(View.VISIBLE);
            this.mVideoProgress.post(this.videoProgressRunnable);
        }
    }

    public void updateVideoProgress(int progress, int status, String fileName) {
        if (!(this.isPlaying || this.mVideoProgress.getVisibility() == View.VISIBLE)) {
            this.mVideoProgress.setVisibility(View.VISIBLE);
        }
        if (progress > IMG_FINISH) {
            this.mVideoProgress.setProgress(progress);
            this.isReceiveVideoData = true;
        }
        if (status == 9) {
            play(VideoUtils.getVideoFilePathByName(this.mContext, fileName));
        }
    }

    @SuppressLint({"NewApi"})
    private void play(String videoFilePath) {
        if (new File(videoFilePath).exists()) {
            ITopicDisplay iTopicDisplay;
            if (this.themeDetailFragment != null) {
                iTopicDisplay = (ITopicDisplay) this.themeDetailFragment;
            } else {
                iTopicDisplay = (ITopicDisplay) this.mContext;
            }
            this.isReceiveVideoData = true;
            this.isPlaying = true;
            this.mVideoProgress.removeCallbacks(this.videoProgressRunnable);
            this.mVideoProgress.setVisibility(View.INVISIBLE);
            if (!iTopicDisplay.isPause()) {
                AutoCoverFlowHelper flowHelper = iTopicDisplay.getFlowHelper();
                flowHelper.videoAutoFlow(this.mListPosition + flowHelper.mHeaderCount);
                MediaPlayer mediaPlayer = iTopicDisplay.getMediaPlayer();
                if (this.mIsNewVersion) {
                    if (!(this.mNewVideoView.getMediaState() == MediaState.PAUSE && videoFilePath.equals(this.mNewVideoView.getVideoPath()))) {
                        recodeViews();
                        this.mNewVideoView.setMediaPlayer(mediaPlayer);
                        this.mNewVideoView.prepare(videoFilePath);
                    }
                    this.mNewVideoView.play();
                }
                postDelayed(new C09249(), 100);
            }
        }
    }

    public void pauseVideoView() {
        if (this.mIsNewVersion) {
            this.mNewVideoView.pause();
        }
    }

    public void startVideoView() {
        if (this.mIsNewVersion) {
            this.mNewVideoView.play();
        }
    }

    public void showCover() {
        this.mPicView.setVisibility(View.VISIBLE);
    }

    public void recodeViews() {
        if (this.mTopicInfo != null) {
            Integer views = this.mTopicInfo.getViews();
            if (views == null) {
                this.mTopicInfo.setViews(Integer.valueOf(IMG_LOADING));
            } else {
                this.mTopicInfo.setViews(Integer.valueOf(views.intValue() + IMG_FINISH));
            }
            this.mTopicInfo.setLocalViews(this.mTopicInfo.getLocalViews() + IMG_FINISH);
            View convertView = (View) getParent();
//            if (convertView != null) {
//                ((TextView) convertView.findViewById(R.id.tv_play_count)).setText(this.mTopicInfo.getViews());
//            }
        }
    }

    public void setFragment(Fragment themeDetailFragment) {
        this.themeDetailFragment = themeDetailFragment;
    }
}
