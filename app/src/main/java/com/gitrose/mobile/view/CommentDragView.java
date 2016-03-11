package com.gitrose.mobile.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.media.TransportMediator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
//import com.baidu.location.LocationClientOption;
import com.nostra13.universalimageloader.core.ImageLoader;
//import com.sea_monster.core.network.ApiReqeust;
//import com.tencent.android.tpush.XGPushClickedResult;
import com.gitrose.mobile.R;
//import com.gitrose.mobile.PersonalActivity;
//import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.Comment;
import com.gitrose.mobile.utils.QuickReturnUtils;
//import com.umeng.update.UpdateStatus;
//import io.rong.message.BuildConfig;
import java.util.Timer;
import java.util.TimerTask;
//import shouji.gexing.framework.utils.UiUtils;

public class CommentDragView extends RelativeLayout {
    public static final int SNAP_VELOCITY = 200;
    public static int baikuangHeight;
    public static int baikuangWidth;
    public static int comment_view_px_size;
    private static int mDefaultPicWidth;
    private static int mDefaultPicheight;
    private int bgTopHeight;
    public float dragViewPointX;
    public float dragViewPointY;
    private boolean fullScroll;
    private boolean isAllowMove;
    public LinearLayout mButtomLinearLayout;
    private int mClickTimeout;
    private RelativeLayout mCommmentView;
    private Context mContext;
    private float mInterceptDownX;
    private float mInterceptDownY;
    public KeyboardRelativeLayout mKeyboardRelativeLayout;
    private int mMaxFont;
    private int mMaxVelocity;
    private int mMinFont;
    private ImageView mPicView;
    private ScrollView mScrollView;
    private int mTouchSlop;
    private float middlePointY;
    private VelocityTracker velocityTracker;
    private int verlocity;

    /* renamed from: com.gitrose.mobile.view.CommentDragView.1 */
    class C03601 extends TimerTask {
        private final /* synthetic */ Activity val$mContext;
        private final /* synthetic */ View val$view;

        C03601(Activity activity, View view) {
            this.val$mContext = activity;
            this.val$view = view;
        }

        public void run() {
            InputMethodManager imm = (InputMethodManager) this.val$mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.showSoftInput(this.val$view, 0);
            }
        }
    }

    public CommentDragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.dragViewPointX = 0.0f;
        this.dragViewPointY = 0.0f;
        this.mMaxFont = 0;
        this.mMinFont = 0;
        this.isAllowMove = false;
        this.middlePointY = 0.55f;
        this.fullScroll = true;
        this.mContext = context;
        initChildView(context);
    }

    private void initChildView(Context context) {
        this.mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mMaxVelocity = ViewConfiguration.getMaximumFlingVelocity();
        if (mDefaultPicWidth == 0) {
            //mDefaultPicWidth = UiUtils.getInstance(getContext()).getmScreenWidth();
        }
        if (mDefaultPicheight == 0) {
            //mDefaultPicheight = UiUtils.getInstance(getContext()).getmScreenHeight();
        }
        if (comment_view_px_size == 0) {
            comment_view_px_size = getResources().getDimensionPixelOffset(R.dimen.home_comment_width);
        }
        if (baikuangWidth == 0) {
            baikuangWidth = QuickReturnUtils.dp2px(getContext(), 10);
        }
        if (baikuangHeight == 0) {
            baikuangHeight = QuickReturnUtils.dp2px(getContext(), 28);
        }
        Resources res = context.getResources();
        this.mMaxFont = res.getInteger(R.integer.max_font);
        this.mMinFont = res.getInteger(R.integer.min_font);
    }

    public boolean getFullScroll() {
        return this.fullScroll;
    }

    public void fillData(ScrollView scrollView, int statusBarHeight, ImageView commentImage, int realHeight, int realWdith, String imageUrl, Comment comment, float[] point) {
        this.mScrollView = scrollView;
        this.mPicView = commentImage;
        this.mCommmentView = (RelativeLayout) getChildAt(0);
        measurePicView(realHeight, realWdith, imageUrl);
        setComment(statusBarHeight, comment, point);
    }

    private void setComment(int statusBarHeight, Comment comment, float[] point) {
//        EditText mEdit = (EditText) this.mCommmentView.getChildAt(0);
//        if (comment == null)// || BuildConfig.FLAVOR.equals(comment)) {
//        {
//            this.dragViewPointX = point[0];
//            this.dragViewPointY = point[1];
//            if (this.dragViewPointY >= this.middlePointY) {
//                this.fullScroll = true;
//            } else {
//                this.fullScroll = false;
//            }
//            mEdit.setHint(this.mContext.getResources().getString(R.string.send_comment_tip));
//            mEdit.setCursorVisible(false);
//            mEdit.setBackgroundResource(getCommentViewBackground("input_01"));
//            resetCommentViewMargin(statusBarHeight, point[0], point[1]);
//            return;
//        }
//        this.dragViewPointX = Float.valueOf(comment.getLocationx()).floatValue();
//        this.dragViewPointY = Float.valueOf(comment.getLocationy()).floatValue();
//        if (this.dragViewPointY >= this.middlePointY) {
//            this.fullScroll = true;
//        } else {
//            this.fullScroll = false;
//        }
//        String content = "\u56de\u590d " + comment.getNickname();
//        mEdit.setHint(content);
//        mEdit.setCursorVisible(false);
//        int font = this.mMaxFont - (content.length() / 5);
//        if (font < this.mMinFont) {
//            font = this.mMinFont;
//        }
//        mEdit.setTextSize((float) font);
//        String txtFrame = comment.getTxtframe();
//        if (txtFrame == null)// || BuildConfig.FLAVOR.equals(txtFrame) || !txtFrame.startsWith("input")) {
//        {
//            setCommentViewTextStyle(mEdit, "input_01");
//        } else {
//            setCommentViewTextStyle(mEdit, txtFrame);
//        }
//        resetCommentViewMargin(statusBarHeight, Float.parseFloat(comment.getLocationx()), Float.parseFloat(comment.getLocationy()));
    }

    private void resetCommentViewMargin(int statusBarHeight, float scaleX, float scaleY) {
        EditText edit = (EditText) this.mCommmentView.getChildAt(0);
        LayoutParams editParams = (LayoutParams) edit.getLayoutParams();
        editParams.height = comment_view_px_size;
        editParams.width = comment_view_px_size;
        edit.setPadding(comment_view_px_size / 7, comment_view_px_size / 5, comment_view_px_size / 7, comment_view_px_size / 5);
        edit.setLayoutParams(editParams);
        ImageView imageEmotion = (ImageView) this.mCommmentView.getChildAt(1);
        LayoutParams imageParams = (LayoutParams) imageEmotion.getLayoutParams();
        imageParams.height = comment_view_px_size;
        imageParams.width = comment_view_px_size;
        imageEmotion.setLayoutParams(imageParams);
        this.bgTopHeight = 0;
        int x = ((int) (((float) mDefaultPicWidth) * scaleX)) - (comment_view_px_size / 2);
        int y = (((int) (((float) this.bgTopHeight) + (((float) mDefaultPicWidth) * scaleY))) - (comment_view_px_size / 2)) + 84;
        if (x < 0) {
            x = 0;
        } else if (x > mDefaultPicWidth - comment_view_px_size) {
            x = mDefaultPicWidth - comment_view_px_size;
        }
        if (y < this.bgTopHeight) {
            y = this.bgTopHeight;
        } else if (y > ((mDefaultPicWidth + this.bgTopHeight) - comment_view_px_size) + getResources().getDimensionPixelOffset(R.dimen.home_item_head_view_width)) {
            y = ((mDefaultPicWidth + this.bgTopHeight) - comment_view_px_size) + getResources().getDimensionPixelOffset(R.dimen.home_item_head_view_width);
        }
        LayoutParams lp = (LayoutParams) this.mCommmentView.getLayoutParams();
        lp.setMargins(x, y, 0, 0);
        this.mCommmentView.setLayoutParams(lp);
        if (getVisibility() == View.VISIBLE) {
            edit.setFocusable(true);
            edit.setFocusableInTouchMode(true);
            edit.requestFocus();
            showSoftInput((Activity) this.mContext, edit);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        EditText editComment = (EditText) this.mCommmentView.getChildAt(0);
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
//            case TopicDetailPagerActivity.FLOW_FIRST_WHAT /*1*/:
//                this.isAllowMove = false;
//                this.mScrollView.requestDisallowInterceptTouchEvent(false);
//                float deltax = Math.abs(x - this.mInterceptDownX);
//                float time = (float) (event.getEventTime() - event.getDownTime());
//                if (Math.abs(y - this.mInterceptDownY) < ((float) this.mTouchSlop) && deltax < ((float) this.mTouchSlop) && time < ((float) this.mClickTimeout)) {
//                    hideSoftInput((Activity) this.mContext, this.mCommmentView);
//                    editComment.setCursorVisible(false);
//                    editComment.setFocusable(false);
//                    break;
//                }
            case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
                this.mScrollView.requestDisallowInterceptTouchEvent(true);
                float deltaX = x - this.mInterceptDownX;
                float deltaY = y - this.mInterceptDownY;
                if (this.isAllowMove) {
                    editComment.setCursorVisible(false);
                    editComment.setFocusable(false);
                    moveCommentView((int) deltaX, (int) deltaY);
                }
                this.mInterceptDownX = x;
                this.mInterceptDownY = y;
                hideSoftInput((Activity) this.mContext, this.mCommmentView);
                break;
        }
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        addVelocityTracker(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
                if (this.mScrollView != null) {
                    this.mScrollView.requestDisallowInterceptTouchEvent(true);
                }
                this.mInterceptDownX = x;
                this.mInterceptDownY = y;
                break;
//            case TopicDetailPagerActivity.FLOW_FIRST_WHAT /*1*/:
//                if (this.mScrollView != null) {
//                    this.mScrollView.requestDisallowInterceptTouchEvent(false);
//                }
//                removeVelocityTracker();
//                break;
            case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
                this.verlocity = getScrollXVelocity();
                if (Math.abs(this.verlocity) > SNAP_VELOCITY) {
                    if (this.mScrollView != null) {
                        this.mScrollView.requestDisallowInterceptTouchEvent(true);
                    }
                    this.isAllowMove = true;
                    removeVelocityTracker();
                    return true;
                }
                break;
        }
        return false;
    }

    private void moveCommentView(int deltaX, int deltaY) {
        if (this.mCommmentView == null)// || BuildConfig.FLAVOR.equals(this.mCommmentView)) {
        {
            this.mCommmentView = (RelativeLayout) getChildAt(0);
        }
        LayoutParams lp = (LayoutParams) this.mCommmentView.getLayoutParams();
        int left = lp.leftMargin + deltaX;
        int top = lp.topMargin + deltaY;
        int right = 0;
        int maxLeftNoContainBorder = mDefaultPicWidth - comment_view_px_size;
        if (left < (-baikuangWidth)) {
            left = -baikuangWidth;
        } else if (left >= maxLeftNoContainBorder && left <= baikuangWidth + maxLeftNoContainBorder) {
            right = maxLeftNoContainBorder - left;
        } else if (left > baikuangWidth + maxLeftNoContainBorder) {
            left = maxLeftNoContainBorder + baikuangWidth;
            right = -baikuangWidth;
        }
        if (top < (this.bgTopHeight - baikuangHeight) + 84) {
            top = (this.bgTopHeight - baikuangHeight) + 84;
        } else if (top > (((mDefaultPicWidth + this.bgTopHeight) + (baikuangHeight * 2)) - comment_view_px_size) + 28) {
            top = (((mDefaultPicWidth + this.bgTopHeight) + (baikuangHeight * 2)) - comment_view_px_size) + 28;
        }
        this.dragViewPointX = ((float) ((comment_view_px_size / 2) + left)) / ((float) mDefaultPicWidth);
        this.dragViewPointY = ((float) (((top - 84) + (comment_view_px_size / 2)) - this.bgTopHeight)) / ((float) mDefaultPicWidth);
        if (this.dragViewPointY >= this.middlePointY) {
            this.fullScroll = true;
        } else {
            this.fullScroll = false;
        }
        lp.setMargins(left, top, right, 0);
        this.mCommmentView.setLayoutParams(lp);
    }

    private void measurePicView(int realHeight, int realWidth, String imageUrl) {
        int picHeight;
        LayoutParams lp = (LayoutParams) this.mPicView.getLayoutParams();
        lp.width = mDefaultPicWidth;
        if (realWidth == 0 || realHeight == 0) {
            realWidth = 640;
            realHeight = 640;
        }
        if (realHeight >= realWidth) {
            picHeight = mDefaultPicWidth;
            lp.width = (mDefaultPicWidth * realWidth) / realHeight;
        } else {
            lp.width = mDefaultPicWidth;
            picHeight = (mDefaultPicWidth * realHeight) / realWidth;
        }
        lp.height = picHeight;
        this.mPicView.setLayoutParams(lp);
        this.mPicView.setScaleType(ScaleType.FIT_XY);
        if (imageUrl != null)// && !BuildConfig.FLAVOR.equals(imageUrl)) {
        {
            ImageLoader.getInstance().displayImage(imageUrl, this.mPicView, Constant.BIG_PICTURE_OPTIONS);
        }
    }

    private int getCommentViewBackground(String toastBgName) {
        int resID = getResources().getIdentifier(toastBgName, "drawable", getContext().getPackageName());
        if (resID == 0) {
//            return R.drawable.input_01;
        }
        return resID;
    }

    private void setCommentViewTextStyle(EditText editText, String textFrame) {
        int resId;
        try {
            switch ((Integer.parseInt(textFrame.substring(6, 8)) - 1) / 4) {
                case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
                case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
//                case ApiReqeust.PUT_METHOD /*3*/:
//                case TopicDetailPagerActivity.FRAGMENT_SIZE /*5*/:
////                case UpdateStatus.Ignore /*7*/:
//                    resId = R.color.black;
//                    break;
//                case TopicDetailPagerActivity.FLOW_FIRST_WHAT /*1*/:
//                case PersonalActivity.TOPIC_DETAIL_RESULT /*4*/:
//                case XGPushClickedResult.NOTIFACTION_DOWNLOAD_CANCEL_TYPE /*6*/:
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
        try {
            editText.setTextColor(getResources().getColor(resId));
            editText.setBackgroundResource(getCommentViewBackground(textFrame));
        } catch (Exception e2) {
            editText.setTextColor(getResources().getColor(R.color.black));
            editText.setBackgroundResource(getCommentViewBackground("input_01"));
        }
    }

    private void addVelocityTracker(MotionEvent event) {
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(event);
    }

    private void removeVelocityTracker() {
        if (this.velocityTracker != null) {
            this.velocityTracker.clear();
            this.velocityTracker.recycle();
            this.velocityTracker = null;
        }
    }

    private int getScrollXVelocity() {
        if (this.velocityTracker == null) {
            return 0;
        }
//        this.velocityTracker.computeCurrentVelocity(LocationClientOption.MIN_SCAN_SPAN, (float) this.mMaxVelocity);
        return (int) this.velocityTracker.getXVelocity();
    }

    public void hideSoftInput(Activity mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showSoftInput(Activity mContext, View view) {
        new Timer().schedule(new C03601(mContext, view), 500);
    }
}
