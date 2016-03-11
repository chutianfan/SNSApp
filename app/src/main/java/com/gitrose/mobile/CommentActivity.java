package com.gitrose.mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.TransportMediator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
//import com.sea_monster.core.network.ApiReqeust;
//import com.tencent.android.tpush.XGPushClickedResult;
//import com.tencent.connect.share.QzoneShare;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.Comment;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.utils.QuickReturnUtils;
import com.gitrose.mobile.utils.TouchUtil;
import com.gitrose.mobile.view.CircleImageView;
import com.gitrose.mobile.view.CommentDragView;
import com.gitrose.mobile.view.KeyboardRelativeLayout;
import com.gitrose.mobile.view.KeyboardRelativeLayout.IOnKeyboardStateChangedListener;
//import com.umeng.update.UpdateStatus;
//import io.rong.message.BuildConfig;
import java.io.FileInputStream;
import java.util.Timer;
import java.util.TimerTask;
//import shouji.gexing.framework.utils.BoundInstallApk;
//import shouji.gexing.framework.utils.ToastUtil;
//import shouji.gexing.framework.utils.UiUtils;

public class CommentActivity extends BaseActivity implements OnClickListener, OnTouchListener, IOnKeyboardStateChangedListener {
    public static final int CENTER_IN_PARENT = 0;
    public static int comment_view_px_size;
    private static int mScreenHeight;
    private static int mScreenWidth;
    private static int statusBarHeight;
    private Comment comment;
    private CommentDragView commentDragView;
    private String commentInput;
    private KeyboardRelativeLayout comment_keyboard;
    private EditText et_comment;
    private ImageView image_comment;
    private boolean isReply;
    private ImageView iv_comment;
    private CircleImageView iv_reply_icon;
    private int listPosition;
    private LinearLayout ll_comment_reply;
    private LinearLayout ll_comment_tip;
    private int mClickTimeout;
    private RelativeLayout mCommmentView;
    private RelativeLayout mContentRelativeLayout;
    private float mFirstDownX;
    private float mFirstDownY;
    private Handler mHandler;
    private int mMaxFont;
    private int mMinFont;
    private int mPicHeight;
    private TextView mTextViewTip;
    private int mTouchSlop;
    private LinearLayout mbuttomLinearLayout;
    private float[] points;
    private int realpicHeight;
    private int realpicWidth;
    private String replyCommentId;
    private ScrollView scrollview_comment;
    private boolean theme;
    private int themeHeight;
    private int themeWidth;
    private String topicId;
    private ImageView tv_comment_bg;
    private TextView tv_comment_err;
    private TextView tv_comment_send;

    /* renamed from: com.gitrose.mobile.CommentActivity.1 */
    class C02541 implements TextWatcher {
        int fount;

        C02541() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String content = s.toString();
            if (content.length() > 0) {
                int font = CommentActivity.this.mMaxFont - (content.length() / 5);
                if (font < CommentActivity.this.mMinFont) {
                    font = CommentActivity.this.mMinFont;
                }
                this.fount = font;
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            CommentActivity.this.et_comment.setTextSize((float) this.fount);
            CommentActivity.this.setCommentTextColor();
        }
    }

    /* renamed from: com.gitrose.mobile.CommentActivity.2 */
    class C02552 implements Runnable {
        private final /* synthetic */ int val$what;

        C02552(int i) {
            this.val$what = i;
        }

        public void run() {
            if (CommentActivity.this.mContentRelativeLayout != null) {
                LayoutParams params = new LayoutParams(-1, -2);
                if (this.val$what == 0) {
                    params.addRule(13);
                } else {
                    params.topMargin = CommentActivity.this.getResources().getDimensionPixelOffset(R.dimen.action_bar_height);
                }
                CommentActivity.this.mContentRelativeLayout.setLayoutParams(params);
                CommentActivity.this.mContentRelativeLayout.requestLayout();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.CommentActivity.3 */
    class C02573 extends Thread {
        private final /* synthetic */ TranslateAnimation val$outanimation;

        /* renamed from: com.gitrose.mobile.CommentActivity.3.1 */
        class C02561 implements Runnable {
            private final /* synthetic */ TranslateAnimation val$outanimation;

            C02561(TranslateAnimation translateAnimation) {
                this.val$outanimation = translateAnimation;
            }

            public void run() {
                CommentActivity.this.ll_comment_tip.startAnimation(this.val$outanimation);
                CommentActivity.this.ll_comment_tip.setVisibility(View.GONE);
            }
        }

        C02573(TranslateAnimation translateAnimation) {
            this.val$outanimation = translateAnimation;
        }

        public void run() {
            super.run();
            CommentActivity.this.mHandler.postDelayed(new C02561(this.val$outanimation), 2000);
        }
    }

    /* renamed from: com.gitrose.mobile.CommentActivity.4 */
    class C02584 extends TimerTask {
        private final /* synthetic */ Activity val$mContext;
        private final /* synthetic */ View val$view;

        C02584(Activity activity, View view) {
            this.val$mContext = activity;
            this.val$view = view;
        }

        public void run() {
//            InputMethodManager imm = (InputMethodManager) this.val$mContext.getSystemService(Context.INPUT_SERVICE);
//            if (imm.isActive()) {
//                imm.showSoftInput(this.val$view, 0);
//            }
        }
    }

    /* renamed from: com.gitrose.mobile.CommentActivity.5 */
    class C02595 implements Runnable {
        C02595() {
        }

        public void run() {
            CommentActivity.this.mTextViewTip.setVisibility(View.VISIBLE);
        }
    }

    /* renamed from: com.gitrose.mobile.CommentActivity.6 */
    class C02606 implements Runnable {
        C02606() {
        }

        public void run() {
            CommentActivity.this.scrollview_comment.fullScroll(TransportMediator.KEYCODE_MEDIA_RECORD);
            CommentActivity.this.scrollview_comment.requestLayout();
        }
    }

    public CommentActivity() {
        this.theme = false;
        this.isReply = false;
        this.replyCommentId = BuildConfig.FLAVOR;
        this.commentInput = "input_01";
        this.mMaxFont = 0;
        this.mMinFont = 0;
        this.mHandler = new Handler();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_page);
        initView();
        getStatusbarHeight();
        initData();
    }

    private void getStatusbarHeight() {
        statusBarHeight = 0;
    }

    private void initView() {
//        if (mScreenWidth == 0) {
//            mScreenWidth = UiUtils.getInstance(this).getmScreenWidth();
//        }
//        if (mScreenHeight == 0) {
//            mScreenHeight = UiUtils.getInstance(this).getmScreenHeight();
//        }
        if (comment_view_px_size == 0) {
            comment_view_px_size = QuickReturnUtils.dp2px(this, 150);
        }
        this.mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        Resources res = getResources();
        this.mMaxFont = res.getInteger(R.integer.max_font);
        this.mMinFont = res.getInteger(R.integer.min_font);
        this.comment_keyboard = (KeyboardRelativeLayout) findViewById(R.id.comment_keyboard);
        this.comment_keyboard.setOnKeyboardStateChangedListener(this);
        this.scrollview_comment = (ScrollView) findViewById(R.id.scrollview_comment);
        this.image_comment = (ImageView) findViewById(R.id.image_comment);
        this.image_comment.setOnTouchListener(this);
        this.commentDragView = (CommentDragView) findViewById(R.id.moveView_topic);
        this.mCommmentView = (RelativeLayout) findViewById(R.id.rl_comment_et);
        findViewById(R.id.tv_comment_close).setOnClickListener(this);
        this.tv_comment_send = (TextView) findViewById(R.id.tv_comment_send);
        this.tv_comment_send.setOnClickListener(this);
        this.tv_comment_bg = (ImageView) findViewById(R.id.tv_comment_bg);
        this.tv_comment_bg.setOnClickListener(this);
        findViewById(R.id.tv_comment_emotion).setOnClickListener(this);
        ImageView iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(this);
        TouchUtil.setDelegate(this, iv_cancel);
        this.iv_comment = (ImageView) findViewById(R.id.iv_comment);
        this.et_comment = (EditText) findViewById(R.id.et_comment);
        this.et_comment.setHintTextColor(getResources().getColor(R.color.comment_reply_normal_color));
        this.et_comment.setOnClickListener(this);
        this.et_comment.addTextChangedListener(new C02541());
        this.ll_comment_tip = (LinearLayout) findViewById(R.id.ll_comment_tip);
        this.tv_comment_err = (TextView) findViewById(R.id.tv_comment_err);
        this.ll_comment_reply = (LinearLayout) findViewById(R.id.ll_comment_reply);
        this.iv_reply_icon = (CircleImageView) findViewById(R.id.iv_reply_icon);
        this.iv_reply_icon.setOnClickListener(this);
        findViewById(R.id.iv_reply_delete).setOnClickListener(this);
        this.mContentRelativeLayout = (RelativeLayout) findViewById(R.id.content_rl);
        this.mbuttomLinearLayout = (LinearLayout) findViewById(R.id.rl_comment_bottom);
        this.mTextViewTip = (TextView) findViewById(R.id.textview_tip);
        LayoutParams layoutParams = (LayoutParams) this.mContentRelativeLayout.getLayoutParams();
        layoutParams.height = mScreenWidth + 168;
        this.mContentRelativeLayout.setLayoutParams(layoutParams);
    }

    public LinearLayout getButtomLinearLayout() {
        return this.mbuttomLinearLayout;
    }

    public KeyboardRelativeLayout getKeyboardRelativeLayout() {
        return this.comment_keyboard;
    }

    public void changeContentRelativeLayout(int what) {
        this.mHandler.postDelayed(new C02552(what), 200);
    }

    private void initData() {
        Intent intent = getIntent();
        this.theme = intent.getBooleanExtra("theme", false);
        if (this.theme) {
            this.commentDragView.setVisibility(View.GONE);
            this.ll_comment_reply.setVisibility(View.GONE);
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(MyApplication.getInstance().themeFile));
                this.themeWidth = bitmap.getWidth();
                this.themeHeight = bitmap.getHeight();
                this.image_comment.setImageBitmap(bitmap);
                this.commentDragView.fillData(this.scrollview_comment, statusBarHeight, this.image_comment, this.themeHeight, this.themeWidth, null, this.comment, new float[]{500.0f / ((float) mScreenWidth), 500.0f / ((float) this.themeHeight)});
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        this.commentDragView.setVisibility(View.VISIBLE);
//        String imageUrl = intent.getStringExtra(QzoneShare.SHARE_TO_QQ_IMAGE_URL);
//        this.topicId = intent.getStringExtra("topicId");
//        this.comment = (Comment) intent.getSerializableExtra("comment");
//        this.listPosition = intent.getIntExtra("listPosition", 0);
//        if (this.comment == null || BuildConfig.FLAVOR.equals(this.comment)) {
//            this.ll_comment_reply.setVisibility(8);
//            this.isReply = false;
//        } else {
//            this.ll_comment_reply.setVisibility(0);
//            this.isReply = true;
//            this.commentInput = this.comment.getTxtframe();
//            if (!this.commentInput.startsWith("input")) {
//                this.commentInput = "input_01";
//            }
//            String avatarUrl = ImageUtils.getAvatarUrl(this.comment.getUid(), this.comment.getAvatartime());
//            ImageLoader.getInstance().displayImage(iconUrl, this.iv_reply_icon, Constant.AVATAR_OPTIONS);
//        }
//        this.realpicWidth = intent.getIntExtra("picWidth", 0);
//        this.realpicHeight = intent.getIntExtra("picHeight", 0);
//        this.points = intent.getFloatArrayExtra("point");
//        this.commentDragView.fillData(this.scrollview_comment, statusBarHeight, this.image_comment, this.realpicHeight, this.realpicWidth, imageUrl, this.comment, this.points);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_comment_close:
                finish();
            case R.id.tv_comment_send:
                this.tv_comment_send.setClickable(false);
                String commentText = this.et_comment.getText().toString();
                float dragX;
                float dragY;
                if (this.theme) {
                    //PlaySound.getInstance(this).toPlay(R.raw.send);
                    dragX = this.commentDragView.dragViewPointX;
                    dragY = this.commentDragView.dragViewPointY;
                    String themePointX = BuildConfig.FLAVOR;
                    String themePointY = BuildConfig.FLAVOR;
                    String themeInput = BuildConfig.FLAVOR;
                    if (this.commentDragView.isShown()) {
                        themePointX = String.valueOf(dragX);
                        themePointY = String.valueOf(dragY);
                        themeInput = this.commentInput;
                    } else {
                        themePointX = BuildConfig.FLAVOR;
                        themePointY = BuildConfig.FLAVOR;
                        themeInput = BuildConfig.FLAVOR;
                    }
                    if (!BuildConfig.FLAVOR.equals(themeInput)) {
                        if (themeInput.startsWith("input") && (commentText == null || BuildConfig.FLAVOR.equals(commentText))) {
                            this.tv_comment_send.setClickable(true);
                            initAnimation(getResources().getString(R.string.comment_content_null_tip));
                            //ToastUtil.toastShort(this, getResources().getString(R.string.comment_content_null_tip), 0);
                            return;
                        }
                    }
                    String[] topicParams = new String[]{MyApplication.getInstance().themeFile.getAbsolutePath(), commentText, themePointX, themePointY, themeInput};
                    Intent i = new Intent();
                    i.putExtra("topicParams", topicParams);
                    setResult(10, i);
                    finish();
                    return;
                }
                String textInput;
//                PlaySound.getInstance(this).toPlay(R.raw.send);
                dragX = this.commentDragView.dragViewPointX;
                dragY = this.commentDragView.dragViewPointY;
                if (this.isReply) {
                    if (this.ll_comment_reply.isShown()) {
                        this.replyCommentId = this.comment.getCommentid();
                    } else {
                        this.replyCommentId = BuildConfig.FLAVOR;
                    }
                    textInput = this.commentInput;
                } else {
                    textInput = this.commentInput;
                }
                if (!BuildConfig.FLAVOR.equals(this.commentInput)) {
                    if (!(textInput.startsWith("input") && (commentText == null || BuildConfig.FLAVOR.equals(commentText)))) {
                        Intent intent = new Intent();
                        String[] commentParams = new String[]{this.topicId, commentText, this.replyCommentId, new StringBuilder(String.valueOf(dragX)).toString(), new StringBuilder(String.valueOf(dragY)).toString(), this.commentInput};
                        Intent intent2 = intent;
                        intent2.putExtra("listPosition", this.listPosition);
                        intent.putExtra("commentParams", commentParams);
                        setResult(9, intent);
                        finish();
                        return;
                    }
                }
                this.tv_comment_send.setClickable(true);
//                ToastUtil.toastShort(this, getResources().getString(R.string.comment_content_null_tip), 0);
                initAnimation(getResources().getString(R.string.comment_content_null_tip));
            case R.id.et_comment:
                this.et_comment.setCursorVisible(true);
                this.et_comment.setFocusable(true);
                this.et_comment.setFocusableInTouchMode(true);
                this.et_comment.requestFocus();
                showSoftInput(this, this.et_comment);
            case R.id.iv_cancel:
                this.commentInput = BuildConfig.FLAVOR;
                this.commentDragView.setVisibility(View.GONE);
                hideSoftInput(this, this.et_comment);
            case R.id.tv_comment_bg:
                startActivityForResult(new Intent(this, CommentInputActivity.class), 1);
                animationForBottom();
            case R.id.tv_comment_emotion:
                startActivityForResult(new Intent(this, CommentEmotionActivity.class), 2);
                animationForBottom();
            case R.id.iv_reply_icon:
            case R.id.iv_reply_delete:
                hideSoftInput(this, this.et_comment);
                this.et_comment.setHint(getResources().getString(R.string.send_comment_tip));
                this.et_comment.setCursorVisible(false);
                TranslateAnimation animation_delete = new TranslateAnimation(1, 0.0f, 1, -0.1f, 1, 0.0f, 1, 0.0f);
                animation_delete.setDuration(100);
                animation_delete.setRepeatCount(0);
                TranslateAnimation animation_edit = new TranslateAnimation(1, 0.8f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
                animation_edit.setDuration(800);
                animation_edit.setRepeatCount(0);
                this.tv_comment_bg.startAnimation(animation_edit);
                this.ll_comment_reply.setVisibility(View.GONE);
            default:
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (!this.commentDragView.isShown()) {
                this.commentDragView.setVisibility(View.VISIBLE);
            }
            int emotionId = data.getIntExtra("emotionID", View.VISIBLE);
            this.commentInput = data.getStringExtra("emotionNAME");
            if (requestCode == 1) {
                this.et_comment.setBackgroundResource(emotionId);
                this.et_comment.setVisibility(View.VISIBLE);
                this.iv_comment.setVisibility(View.GONE);
                this.et_comment.setCursorVisible(true);
                this.et_comment.setFocusable(true);
                this.et_comment.setFocusableInTouchMode(true);
                this.et_comment.requestFocus();
                showSoftInput(this, this.et_comment);
                setCommentTextColor();
            } else if (requestCode == 2) {
                this.et_comment.setText(BuildConfig.FLAVOR);
                this.et_comment.setClickable(false);
                this.et_comment.setCursorVisible(false);
                this.et_comment.setVisibility(View.GONE);
                this.iv_comment.setVisibility(View.VISIBLE);
                this.iv_comment.setImageResource(emotionId);
            }
        }
    }

    private void setCommentTextColor() {
        if (this.commentInput.startsWith("input")) {
            int resId;
            try {
                switch ((Integer.parseInt(this.commentInput.substring(6, 8)) - 1) / 4) {
                    case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
                    case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
                    //case ApiReqeust.PUT_METHOD /*3*/:
                   // case TopicDetailPagerActivity.FRAGMENT_SIZE /*5*/:
                    //case UpdateStatus.Ignore /*7*/:
                        resId = R.color.black;
                        break;
                    //case TopicDetailPagerActivity.FLOW_FIRST_WHAT /*1*/:
//                    case PersonalActivity.TOPIC_DETAIL_RESULT /*4*/:
                    //case XGPushClickedResult.NOTIFACTION_DOWNLOAD_CANCEL_TYPE /*6*/:
                    case TransportMediator.FLAG_KEY_MEDIA_PLAY_PAUSE /*8*/:
                        resId = R.color.white;
                        break;
                    default:
                        resId = R.color.black;
                        break;
                }
            } catch (Exception e) {
                resId = R.color.black;
            }
            this.et_comment.setTextColor(getResources().getColor(resId));
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
                this.mFirstDownX = x;
                this.mFirstDownY = y;
                break;
//            case TopicDetailPagerActivity.FLOW_FIRST_WHAT /*1*/:
//                float deltaX = Math.abs(x - this.mFirstDownX);
//                float time = (float) (event.getEventTime() - event.getDownTime());
//                float[] point = new float[2];
//                if (Math.abs(y - this.mFirstDownY) < ((float) this.mTouchSlop) && deltaX < ((float) this.mTouchSlop) && time < ((float) this.mClickTimeout)) {
//                    //PlaySound.getInstance(this).toPlay(R.raw.comment);
//                    hideSoftInput(this, this.et_comment);
//                    if (this.commentDragView.isShown()) {
//                        this.et_comment.setCursorVisible(false);
//                        this.et_comment.setFocusable(false);
//                    } else {
//                        this.commentDragView.setVisibility(View.VISIBLE);
//                        if (this.et_comment.getVisibility() == View.VISIBLE) {
//                            showSoftInput(this, this.et_comment);
//                            this.et_comment.setCursorVisible(true);
//                            if (!this.commentInput.startsWith("input")) {
//                                this.commentInput = "input_01";
//                            }
//                            this.et_comment.setBackgroundResource(getCommentViewBackground(this.commentInput));
//                            this.iv_comment.setVisibility(View.GONE);
//                        } else if (this.iv_comment.getVisibility() == View.VISIBLE) {
//                            this.iv_comment.setVisibility(View.GONE);
//                            this.et_comment.setVisibility(View.VISIBLE);
//                            this.et_comment.setCursorVisible(true);
//                            this.et_comment.setFocusable(true);
//                            this.et_comment.setFocusableInTouchMode(true);
//                            this.et_comment.requestFocus();
//                            showSoftInput(this, this.et_comment);
//                            if (!this.commentInput.startsWith("input")) {
//                                this.commentInput = "input_01";
//                            }
//                            this.et_comment.setBackgroundResource(getCommentViewBackground(this.commentInput));
//                            setCommentTextColor();
//                        }
//                    }
//                    if (!this.theme) {
//                        LayoutParams lpImage = (LayoutParams) this.image_comment.getLayoutParams();
//                        lpImage.width = mScreenWidth;
//                        this.mPicHeight = (this.realpicHeight * lpImage.width) / this.realpicWidth;
//                        break;
//                    }
//                    point[0] = x / ((float) mScreenWidth);
//                    point[1] = y / ((float) this.themeHeight);
//                    this.commentDragView.fillData(this.scrollview_comment, statusBarHeight, this.image_comment, this.themeHeight, this.themeWidth, null, this.comment, point);
//                    break;
//                }
        }
        return true;
    }

    private int getCommentViewBackground(String toastBgName) {
        int resID = getResources().getIdentifier(toastBgName, "drawable", getPackageName());
        if (resID == 0) {
//            return R.drawable.input_01;
        }
        return resID;
    }

    private void initAnimation(String err_text) {
        this.ll_comment_tip.setVisibility(View.VISIBLE);
        this.tv_comment_err.setText(err_text);
        TranslateAnimation animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -3.0f, 1, 0.0f);
        animation.setDuration(800);
        animation.setRepeatCount(0);
        this.ll_comment_tip.startAnimation(animation);
        TranslateAnimation outanimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -0.0f, 1, -3.0f);
        outanimation.setDuration(800);
        outanimation.setRepeatCount(0);
        new C02573(outanimation).start();
    }

    public void showSoftInput(Activity mContext, View view) {
        new Timer().schedule(new C02584(mContext, view), 500);
    }

    public void hideSoftInput(Activity mContext, View view) {
//        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_SERVICE);
//        if (imm.isActive()) {
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
    }

    public void onKeyboardStateChanged(int state) {
        switch (state) {
//            case BoundInstallApk.INSTALL_FAILED_INVALID_URI /*-3*/:
//                this.mTextViewTip.setVisibility(8);
//                this.mTextViewTip.requestLayout();
//                if (this.commentDragView.getFullScroll()) {
//                    this.mHandler.postDelayed(new C02606(), 100);
//                }
//            case BoundInstallApk.INSTALL_FAILED_INVALID_APK /*-2*/:
//                this.mHandler.postDelayed(new C02595(), 50);
//            default:
        }
    }
}
