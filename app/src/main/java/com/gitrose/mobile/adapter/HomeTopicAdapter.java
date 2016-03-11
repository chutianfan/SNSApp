package com.gitrose.mobile.adapter;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ParseException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//import com.nineoldandroids.animation.Animator;
//import com.nineoldandroids.animation.Animator.AnimatorListener;
//import com.nineoldandroids.animation.ObjectAnimator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.R;
import com.gitrose.mobile.HomeFragment;
import com.gitrose.mobile.LoginActivity;
//import com.gitrose.mobile.PersonalNewActivity;
//import com.gitrose.mobile.PhoneDeviceActivity;
import com.gitrose.mobile.SearchActivity;
//import com.gitrose.mobile.ThemeActivity;
import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.WebViewActivity;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.auth.CookieParser;
import com.gitrose.mobile.base.ITopicDisplay;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.Comment;
//import com.gitrose.mobile.model.HotCommentList;
import com.gitrose.mobile.model.HuaTi;
import com.gitrose.mobile.model.Operation;
import com.gitrose.mobile.model.OperationButton;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.utils.QuickReturnUtils;
import com.gitrose.mobile.utils.TextUtil;
import com.gitrose.mobile.utils.TouchUtil;
import com.gitrose.mobile.view.AutoCoverFlow;
import com.gitrose.mobile.view.AutoCoverFlowHelper;
import com.gitrose.mobile.view.CircleImageView;
import com.gitrose.mobile.view.CommentImageView;
import com.gitrose.mobile.view.TextViewFixTouchConsume;
import com.gitrose.mobile.view.TopicMore;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import org.apache.http.Header;
import qalsdk.BaseConstants;
import shouji.gexing.framework.utils.UiUtils;
import shouji.gexing.framework.utils.TimeUtils;

public class HomeTopicAdapter extends BaseAdapter implements OnClickListener {
    public static final int MAX_REQUEST_COMMENT_SIZE = 50;
    public static final int SELECT_COMMENT_WHAT = 2;
    @SuppressLint({"SimpleDateFormat"})
    private SimpleDateFormat dateFormat;
    private Fragment fragment;
    private boolean isTheme;
    private boolean isTopicDetail;
    private Activity mActivity;
    private AnimationDrawable mAnimation;
    private int mAvatarBorderWidth;
    private AvatarOnItemClickListener mAvatarOnItemClickListener;
    private AvatarOnItemSelectedListener mAvatarOnItemSelectedListener;
    public int mClickListPosition;
    private Context mContext;
    private Handler mHandler;
    private int mHeaderCount;
    private boolean mIsUseRealTime;
    private ListView mListView;
    public List<TopicInfo> mTopicList;
    private OnClickListener onClickListener;

    /* renamed from: com.gitrose.mobile.adapter.HomeTopicAdapter.1 */
    class C08451 extends Handler {
        C08451() {
        }

        public void handleMessage(Message msg) {
            if (msg.what == HomeTopicAdapter.SELECT_COMMENT_WHAT) {
                HomeTopicAdapter.this.changeAvatorView((AdapterView) msg.obj, msg.arg1);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.HomeTopicAdapter.2 */
//    class C08462 implements OnClickListener {
//        C08462() {
//        }
//
//        public void onClick(View v) {
//            WebViewActivity.openWebView(HomeTopicAdapter.this.mContext, (String) v.getTag());
//        }
//    }

    /* renamed from: com.gitrose.mobile.adapter.HomeTopicAdapter.3 */
    class C08473 implements Runnable {
        private final /* synthetic */ int val$commentPos;
        private final /* synthetic */ AutoCoverFlow val$fancyCoverFlow;

        C08473(AutoCoverFlow autoCoverFlow, int i) {
            this.val$fancyCoverFlow = autoCoverFlow;
            this.val$commentPos = i;
        }

        public void run() {
            if (this.val$fancyCoverFlow != null) {
                this.val$fancyCoverFlow.setSelection(this.val$commentPos);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.HomeTopicAdapter.4 */
    class C08484 implements Runnable {
        private final /* synthetic */ int val$commentPos;
        private final /* synthetic */ AutoCoverFlow val$fancyCoverFlow;

        C08484(AutoCoverFlow autoCoverFlow, int i) {
            this.val$fancyCoverFlow = autoCoverFlow;
            this.val$commentPos = i;
        }

        public void run() {
            if (this.val$fancyCoverFlow != null) {
                this.val$fancyCoverFlow.setSelection(this.val$commentPos);
            }
        }
    }

    class AvatarOnItemClickListener implements OnItemClickListener {
        AvatarOnItemClickListener() {
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            TopicInfo mTopicInfo = (TopicInfo) parent.getTag(R.id.fancy_cover_flow_topicinfo);
//            int pos = position - 1;
//            TopicInfo topicInfo = (TopicInfo) HomeTopicAdapter.this.mTopicList.get(((Integer) parent.getTag(R.id.fancy_cover_flow_pos)).intValue());
//            if (!(mTopicInfo == null || BaseConstants.ah.equals(mTopicInfo))) {
//                topicInfo = mTopicInfo;
//            }
//            List<Comment> comments = topicInfo.getCommentlist();
//            if (pos >= 0 && pos <= comments.size() - 1 && position == parent.getSelectedItemPosition()) {
//                Intent intent = new Intent(HomeTopicAdapter.this.mContext, PersonalNewActivity.class);
//                intent.putExtra(PersonalNewActivity.PERSONAL_USER_ID, ((Comment) topicInfo.getCommentlist().get(pos)).getUid());
//                HomeTopicAdapter.this.mContext.startActivity(intent);
//            }
        }
    }

    class AvatarOnItemSelectedListener implements OnItemSelectedListener {
        String commentId;
        List<Comment> mCommentList;

        /* renamed from: com.gitrose.mobile.adapter.HomeTopicAdapter.AvatarOnItemSelectedListener.1 */
//        class C17831 extends QGHttpHandler<HotCommentList> {
//            private final /* synthetic */ AutoCoverFlowAdapter val$adapter;
//            private final /* synthetic */ AdapterView val$parent;
//            private final /* synthetic */ int val$position;
//            private final /* synthetic */ ImageView val$progressView;
//            private final /* synthetic */ TopicInfo val$topicInfo;
//
//            C17831(Context $anonymous0, TopicInfo topicInfo, AdapterView adapterView, AutoCoverFlowAdapter autoCoverFlowAdapter, int i, ImageView imageView) {
//                this.val$topicInfo = topicInfo;
//                this.val$parent = adapterView;
//                this.val$adapter = autoCoverFlowAdapter;
//                this.val$position = i;
//                this.val$progressView = imageView;
//                super($anonymous0);
//            }
//
//            public void onGetDataSuccess(HotCommentList data) {
//                List<Comment> comments = data.getCommentlist();
//                if (comments == null || comments.size() <= 0) {
//                    this.val$adapter.setDown(false);
//                    HomeTopicAdapter.this.stopProgressAnim(this.val$progressView);
//                    this.val$progressView.setVisibility(View.INVISIBLE);
//                    return;
//                }
//                int totalCount = 0;
//                if (data.getTotalcomment() != null) {
//                    totalCount = Integer.parseInt(data.getTotalcomment());
//                }
//                this.val$topicInfo.setTotalcomment(totalCount);
//                for (int i = AvatarOnItemSelectedListener.this.mCommentList.size() - 1; i >= 0; i--) {
//                    if (((Comment) AvatarOnItemSelectedListener.this.mCommentList.get(i)).getCommentid() == null) {
//                        AvatarOnItemSelectedListener.this.mCommentList.remove(i);
//                    }
//                }
//                AvatarOnItemSelectedListener.this.mCommentList.addAll(comments);
//                ((TextView) ((View) this.val$parent.getParent().getParent()).findViewById(R.id.tv_home_comment)).setText(totalCount == 0 ? HomeTopicAdapter.this.mContext.getString(R.string.comment_tip) : new StringBuilder(String.valueOf(totalCount)).toString());
//                this.val$adapter.notifyDataSetChanged();
//                ((AutoCoverFlow) this.val$parent).setSelection(this.val$position);
//                HomeTopicAdapter.this.sendSelectedCommentMsg(this.val$parent, this.val$position);
//            }
//
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                HomeTopicAdapter.this.stopProgressAnim(this.val$progressView);
//                this.val$progressView.setVisibility(View.INVISIBLE);
//                super.onFailure(statusCode, headers, responseString, throwable);
//            }
//        }

        AvatarOnItemSelectedListener() {
            this.mCommentList = null;
            this.commentId = null;
        }

        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int listPosition = ((Integer) parent.getTag(R.id.fancy_cover_flow_pos)).intValue();
            TopicInfo topicInfo = (TopicInfo) parent.getTag(R.id.fancy_cover_flow_topicinfo);
            HomeTopicAdapter.this.changeAvatorView(parent, position);
            showComment(parent, position, listPosition, topicInfo);
            loadComment(parent, position, listPosition, topicInfo);
        }

        private void showComment(AdapterView<?> adapterView, int position, int listPosition, TopicInfo topicInfo) {
            View convertView = HomeTopicAdapter.this.getConvertView(listPosition);
            if (convertView != null) {
                CommentImageView toastImageView = (CommentImageView) convertView.findViewById(R.id.toastView_topic);
                if (toastImageView == null) {
                    return;
                }
                if (position > 0) {
                    toastImageView.nextComment(position - 1, topicInfo);
                } else {
                    toastImageView.hideComment();
                }
            }
        }

        private void loadComment(AdapterView<?> parent, int position, int listPosition, TopicInfo mTopicInfo) {
            AutoCoverFlow coverFlow = (AutoCoverFlow) parent;
            if (position != 0 && HomeTopicAdapter.this.mTopicList.size() > listPosition) {
                AutoCoverFlowAdapter adapter = (AutoCoverFlowAdapter) parent.getAdapter();
                TopicInfo topicInfo = (TopicInfo) HomeTopicAdapter.this.mTopicList.get(listPosition);
                if (position == parent.getCount() - 2 && adapter.isDown() && topicInfo.getTopicid() != null) {
                    View view = coverFlow.getChildAt(coverFlow.getChildCount() - 1);
                    if (view instanceof ImageView) {
                        view.setVisibility(View.VISIBLE);
                        HomeTopicAdapter.this.startProgressAnim((ImageView) view);
                        loadDownComment(parent, (ImageView) view, adapter, position, listPosition, mTopicInfo);
                    }
                }
            }
        }

        private void loadDownComment(AdapterView<?> parent, ImageView progressView, AutoCoverFlowAdapter adapter, int position, int listPosition, TopicInfo mTopicInfo) {
//            TopicInfo topicInfo = (TopicInfo) HomeTopicAdapter.this.mTopicList.get(listPosition);
//            if (mTopicInfo == null || BaseConstants.ah.equals(mTopicInfo)) {
//                this.mCommentList = topicInfo.getCommentlist();
//            } else {
//                this.mCommentList = mTopicInfo.getCommentlist();
//            }
//            if (this.mCommentList != null && position - 1 < this.mCommentList.size()) {
//                for (int i = this.mCommentList.size() - 1; i >= 0; i--) {
//                    this.commentId = ((Comment) this.mCommentList.get(i)).getCommentid();
//                    if (this.commentId != null && !BaseConstants.ah.equals(this.commentId)) {
//                        break;
//                    }
//                }
//                QGHttpRequest.getInstance().hotCommentRequest(HomeTopicAdapter.this.mContext, topicInfo.getTopicid(), this.commentId, SearchActivity.DOWN, new C17831(HomeTopicAdapter.this.mContext, topicInfo, parent, adapter, position, progressView));
//            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    private class Run implements Runnable {
        private String content;
        private TextView desc;
        private TextView moreView;
        private int position;

        public Run(TextView desc, String content, TextView moreView, int position) {
            this.desc = desc;
            this.content = content;
            this.moreView = moreView;
            this.position = position;
        }

        public void run() {
            this.desc.setVisibility(View.VISIBLE);
            if (this.desc.getLayout() == null) {
                return;
            }
            if (this.desc.getLineCount() > 6) {
                this.moreView.setVisibility(View.VISIBLE);
                this.moreView.setTag(Integer.valueOf(this.position));
                this.moreView.setOnClickListener(HomeTopicAdapter.this);
                return;
            }
            this.moreView.setVisibility(View.GONE);
        }
    }

    public static class ViewHolder {
        TextView commentTv;
        FrameLayout commentsFl;
        AutoCoverFlow fancyCoverFlow;
        ImageView is_redpaket_iv;
        CircleImageView iv_user_icon;
        ImageView iv_user_level;
        TextView locationTv;
        TextView moreTopicTv;
        TextView noHasCommentTv;
        TextView playCountTv;
        TextView switchCommentImg;
        CommentImageView toastImageView;
        TextView topicLiveMark;
        TextView topicLookTitleTv;
        TextView topicTimeTv;
        LinearLayout topic_foot_ll;
        TextView tv_home_comment;
        TextView tv_home_get;
        TextView tv_home_forward;
        TextView tv_home_more;
        TextView tv_home_zan;
        TextViewFixTouchConsume tv_topic_detail;
        TextView tv_topic_device;
        TextView userNameTv;
        ImageView user_follow_status;
        ImageView videoMarkImg;
    }

    /* renamed from: com.gitrose.mobile.adapter.HomeTopicAdapter.6 */
    class C14386 implements Animator.AnimatorListener {
        private final /* synthetic */ View val$view;

        C14386(View view) {
            this.val$view = view;
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            HomeTopicAdapter.this.switchComment(this.val$view);
        }

        public void onAnimationCancel(Animator animation) {
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.HomeTopicAdapter.5 */
    class C17825 extends QGHttpHandler<String> {
        C17825(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(String data) {
        }
    }

    public HomeTopicAdapter(Context context, List<TopicInfo> picList, boolean isUseRealTime) {
        this(context, null, (List) picList);
        this.mIsUseRealTime = isUseRealTime;
        this.isTopicDetail = true;
    }

    public HomeTopicAdapter(Context context, ListView listView, List<TopicInfo> picList) {
        this.mAvatarBorderWidth = SELECT_COMMENT_WHAT;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.mHeaderCount = 0;
        this.mClickListPosition = -1;
        this.isTheme = false;
        this.mHandler = new C08451();
        this.mTopicList = picList;
        this.mContext = context;
        this.mListView = listView;
        if (this.mListView != null) {
            this.mHeaderCount = listView.getHeaderViewsCount();
        }
        this.mAvatarOnItemClickListener = new AvatarOnItemClickListener();
        this.mAvatarOnItemSelectedListener = new AvatarOnItemSelectedListener();
        if (context != null) {
            this.mAvatarBorderWidth = QuickReturnUtils.dp2px(context, 1);
        }
    }

    public HomeTopicAdapter(Context context, ListView listView, List<TopicInfo> picList, boolean isTheme, Activity activity, Fragment fragment) {
        this.mAvatarBorderWidth = SELECT_COMMENT_WHAT;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.mHeaderCount = 0;
        this.mClickListPosition = -1;
        this.isTheme = false;
        this.mHandler = new C08451();
        this.fragment = fragment;
        this.isTheme = isTheme;
        this.mActivity = activity;
        this.mTopicList = picList;
        this.mContext = context;
        this.mListView = listView;
        if (this.mListView != null) {
            this.mHeaderCount = listView.getHeaderViewsCount();
        }
        this.mAvatarOnItemClickListener = new AvatarOnItemClickListener();
        this.mAvatarOnItemSelectedListener = new AvatarOnItemSelectedListener();
        if (context != null) {
            this.mAvatarBorderWidth = QuickReturnUtils.dp2px(context, 1);
        }
    }

    public int getCount() {
        return this.mTopicList.size();
    }

    public TopicInfo getItem(int position) {
        return (TopicInfo) this.mTopicList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int i, View view, ViewGroup vGroup) {
        TopicInfo topicinfo = (TopicInfo)mTopicList.get(i);
        Object obj;
        int j;
        boolean flag1;
        ViewHolder viewgroup = new ViewHolder();

        if (view == null || isTopicDetail || view.findViewById(R.id.rl_topic_item) == null)
        {
            if (!isTopicDetail)
            {
                view = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_home_topic, null);
            }

            viewgroup.iv_user_icon = (CircleImageView)view.findViewById(R.id.iv_user_icon);
            viewgroup.user_follow_status = (ImageView)view.findViewById(R.id.iv_user_follow_status);
            viewgroup.iv_user_level = (ImageView)view.findViewById(R.id.iv_user_level);
            viewgroup.tv_home_get = (TextView)view.findViewById(R.id.tv_home_get);
            viewgroup.tv_home_comment = (TextView)view.findViewById(R.id.tv_home_comment);
            viewgroup.tv_home_zan = (TextView)view.findViewById(R.id.tv_home_zan);
            viewgroup.tv_home_forward = (TextView)view.findViewById(R.id.tv_home_forward);
            viewgroup.tv_home_more = (TextView)view.findViewById(R.id.tv_home_more);
            viewgroup.locationTv = (TextView)view.findViewById(R.id.tv_topic_location);
            viewgroup.playCountTv = (TextView)view.findViewById(R.id.tv_play_count);
            viewgroup.toastImageView = (CommentImageView)view.findViewById(R.id.toastView_topic);
            viewgroup.userNameTv = (TextView)view.findViewById(R.id.tv_user_name);
            viewgroup.topicTimeTv = (TextView)view.findViewById(R.id.tv_topic_time);
            viewgroup.topicLookTitleTv = (TextView)view.findViewById(R.id.tv_topic_play_title);
            viewgroup.topicLiveMark = (TextView)view.findViewById(R.id.img_topic_live_mark);
            viewgroup.topic_foot_ll = (LinearLayout)view.findViewById(R.id.ll_topic_foot);
            viewgroup.tv_topic_device = (TextView)view.findViewById(R.id.tv_topic_device);
            viewgroup.moreTopicTv = (TextView)view.findViewById(R.id.tv_topic_more);
            viewgroup.fancyCoverFlow = (AutoCoverFlow)view.findViewById(R.id.fancyCoverFlow);
            viewgroup.noHasCommentTv = (TextView)view.findViewById(R.id.tv_no_has_comment);
            viewgroup.commentsFl = (FrameLayout)view.findViewById(R.id.fl_comments);
            viewgroup.videoMarkImg = (ImageView)view.findViewById(R.id.img_topic_video_mark);
            viewgroup.switchCommentImg = (TextView)view.findViewById(R.id.img_topic_switch_comment);
            viewgroup.is_redpaket_iv = (ImageView)view.findViewById(R.id.img_topic_switch_isredpacket);
            viewgroup.tv_topic_detail = (TextViewFixTouchConsume)view.findViewById(R.id.tv_topic_detail);
            view.setTag(viewgroup);
        } else
        {
            viewgroup = (ViewHolder)view.getTag();
        }

        if (isTheme)
        {
            viewgroup.toastImageView.setFragment(fragment);
        }

        TutuUsers userInfo = topicinfo.getUserinfo();
        String strUserIcon = ImageUtils.getUserIconUrl(userInfo.getProfileFile());
        ImageLoader.getInstance().displayImage(strUserIcon, viewgroup.iv_user_icon, Constant.AVATAR_OPTIONS);
        viewgroup.tv_home_get.setText("10");
        viewgroup.tv_home_comment.setText(String.valueOf(topicinfo.getTotalcomment()));
        viewgroup.tv_home_zan.setText(String.valueOf(topicinfo.getLikenum()));
        viewgroup.playCountTv.setText(String.valueOf(topicinfo.getViews()));
        viewgroup.toastImageView.fillData(i, topicinfo);
        viewgroup.userNameTv.setText(userInfo.getRemarkname());
        viewgroup.tv_topic_detail.setText(topicinfo.getDesc());

        java.util.Date dtAddTime = null;
        try {
            dtAddTime = dateFormat.parse(topicinfo.getAddtime());
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        if(dtAddTime == null)
            dtAddTime = new Date();
        viewgroup.topicTimeTv.setText(TimeUtils.getShowTimeStringefore(mContext, dtAddTime, "MM-dd HH:mm"));

        return view;
    }

    final void clearOperationButtons(FrameLayout commentFl) {
        if (commentFl.getChildCount() > SELECT_COMMENT_WHAT) {
            commentFl.removeViews(SELECT_COMMENT_WHAT, commentFl.getChildCount() - 2);
        }
    }

    private void addOperationBtns(TopicInfo topicInfo, FrameLayout commentFl) {
        Operation operation = topicInfo.getSpecialdata();
        if (operation != null) {
            List<OperationButton> operationButtons = operation.getButtonlist();
            int btnHeight = (int) this.mContext.getResources().getDimension(R.dimen.login_bt_height);
            int btnMargin = (int) this.mContext.getResources().getDimension(R.dimen.home_item_operation_margin);
            int size = operationButtons.size();
            OperationButton operationButton;
            LayoutParams btnLayoutParams;
            if (size == 1) {
                operationButton = (OperationButton) operationButtons.get(0);
                btnLayoutParams = new LayoutParams(-1, btnHeight);
                btnLayoutParams.setMargins(btnMargin, 0, btnMargin, btnMargin);
                addOperationButton(commentFl, operationButton, R.drawable.login_button, R.color.setting_title_text_color, btnLayoutParams);
            } else if (size > 1) {
                int btnWdith = (UiUtils.getInstance(this.mContext).getmScreenWidth() - (btnMargin * 3)) / SELECT_COMMENT_WHAT;
                operationButton = (OperationButton) operationButtons.get(0);
                btnLayoutParams = new LayoutParams(btnWdith, btnHeight);
                btnLayoutParams.setMargins(btnMargin, 0, btnMargin, btnMargin);
                addOperationButton(commentFl, operationButton, R.drawable.login_button, R.color.setting_title_text_color, btnLayoutParams);
                OperationButton operationButton1 = (OperationButton) operationButtons.get(1);
                LayoutParams btnLayoutParams1 = new LayoutParams(btnWdith, btnHeight);
                btnLayoutParams1.setMargins((btnMargin * SELECT_COMMENT_WHAT) + btnWdith, 0, btnMargin, btnMargin);
                addOperationButton(commentFl, operationButton1, R.drawable.grey_button, R.color.white, btnLayoutParams1);
            }
        }
    }

    private void addOperationButton(FrameLayout commentFl, OperationButton operationButton, int background, int color, LayoutParams lp) {
        Button button = new Button(this.mContext);
//        button.setTextColor(this.mContext.getResources().getColor(color));
//        button.setTextSize(SELECT_COMMENT_WHAT, 16.0f);
//        button.setTag(operationButton.getGotourl());
//        button.setBackgroundResource(background);
//        button.setOnClickListener(new C08462());
//        String btnText = operationButton.getButtontxt();
//        if (btnText != null) {
//            button.setText(btnText);
//        }
//        lp.gravity = 80;
//        commentFl.addView(button, lp);
    }

    public void notifyLastCommentPos(int listPosition) {
        notifyCommentPos(listPosition, -1, null);
    }

    public void notifyLastCommentPos(int listPosition, TopicInfo mTopicInfo) {
        notifyCommentPos(listPosition, -1, mTopicInfo);
    }

    public void notifyCommentPos(int listPosition, int commentPos, TopicInfo mTopicInfo) {
        List<Comment> comments;
        if (mTopicInfo == null || BaseConstants.ah.equals(mTopicInfo)) {
            comments = ((TopicInfo) this.mTopicList.get(listPosition)).getCommentlist();
        } else {
            comments = mTopicInfo.getCommentlist();
        }
        if (comments != null) {
            int commentSize = comments.size();
            if (commentPos != -1) {
                commentPos++;
            } else {
                commentPos = commentSize;
            }
            updateTopic(listPosition, commentPos, mTopicInfo);
        }
    }

    public void updateTopic(int listPos) {
        notifyDataSetChanged();
    }

    public void updateTopic(int listPos, int commentPos, TopicInfo mTopicInfo) {
        View convertView = getConvertView(listPos);
        if (convertView != null) {
            TopicInfo topicInfo;
            if (mTopicInfo == null || BaseConstants.ah.equals(mTopicInfo)) {
                topicInfo = getItem(listPos);
            } else {
                topicInfo = mTopicInfo;
            }
            CommentImageView toastImageView = (CommentImageView) convertView.findViewById(R.id.toastView_topic);
            TextView favTopicTv = (TextView) convertView.findViewById(R.id.tv_home_zan);
            favTopicTv.setText(topicInfo.getLikenum() == 0 ? this.mContext.getString(R.string.home_zan_tip) : new StringBuilder(String.valueOf(topicInfo.getLikenum())).toString());
            favTopicTv.setSelected(topicInfo.getIslike() == 1);
            int totalComment = topicInfo.getTotalcomment();
            ((TextView) convertView.findViewById(R.id.tv_home_comment)).setText(totalComment == 0 ? this.mContext.getString(R.string.comment_tip) : new StringBuilder(String.valueOf(totalComment)).toString());
            AutoCoverFlow fancyCoverFlow = (AutoCoverFlow) convertView.findViewById(R.id.fancyCoverFlow);
            TextView noHasCommentTv = (TextView) convertView.findViewById(R.id.tv_no_has_comment);
            if (topicInfo.getCommentlist().size() == 0) {
                toastImageView.getChildAt(3).setVisibility(View.INVISIBLE);
                fancyCoverFlow.setVisibility(View.INVISIBLE);
                noHasCommentTv.setVisibility(View.VISIBLE);
                noHasCommentTv.setOnClickListener(null);
                return;
            }
            toastImageView.getChildAt(3).setVisibility(View.VISIBLE);
            fancyCoverFlow.setVisibility(View.VISIBLE);
            noHasCommentTv.setVisibility(View.INVISIBLE);
            fancyCoverFlow.setAdapter(new AutoCoverFlowAdapter(this.mContext, topicInfo.getCommentlist()));
            fancyCoverFlow.setTag(R.id.fancy_cover_flow_pos, Integer.valueOf(listPos));
            fancyCoverFlow.setTag(R.id.fancy_cover_flow_topicinfo, topicInfo);
            fancyCoverFlow.setOnItemClickListener(this.mAvatarOnItemClickListener);
            fancyCoverFlow.setOnItemSelectedListener(this.mAvatarOnItemSelectedListener);
            if (commentPos != -1) {
                fancyCoverFlow.postDelayed(new C08473(fancyCoverFlow, commentPos), 100);
            }
        }
    }

    public void updateTopicFromNetWork(int listPos, TopicInfo topicInfo, int commentPos) {
        View convertView = getConvertView(listPos);
        if (convertView != null) {
            TextView tv_topic_detail;
            TextView locationTv;
            String topicDesc;
            String pointText;
            TextView favTopicTv;
            TextView forwardTopicTv;
            int totalComment;
            AutoCoverFlow fancyCoverFlow;
            TextView noHasCommentTv;
            convertView.findViewById(R.id.iv_user_follow_status).setVisibility(View.INVISIBLE);
            ((CommentImageView) convertView.findViewById(R.id.toastView_topic)).mTopicInfo = topicInfo;
            ImageView iv_user_icon = (CircleImageView) convertView.findViewById(R.id.iv_user_icon);
            TextView userNameTv = (TextView) convertView.findViewById(R.id.tv_user_name);
            String isName = topicInfo.getIskana();
            if (isName != null) {
                if (!BaseConstants.ah.equals(isName)) {
                    TutuUsers user = topicInfo.getUserinfo();
                    if (Integer.valueOf(isName).intValue() == 0) {
                        String remarkName = user.getRemarkname();
                        if (remarkName != null) {
                            if (!BaseConstants.ah.equals(remarkName)) {
                                userNameTv.setText(remarkName);
                                ImageLoader.getInstance().displayImage(ImageUtils.getAvatarUrl(user.getUid(), user.getAvatartime()), iv_user_icon, Constant.AVATAR_OPTIONS);
                            }
                        }
                        userNameTv.setText(user.getNickname());
                        ImageLoader.getInstance().displayImage(ImageUtils.getAvatarUrl(user.getUid(), user.getAvatartime()), iv_user_icon, Constant.AVATAR_OPTIONS);
                    } else if (Integer.valueOf(isName).intValue() == 1) {
                        iv_user_icon.setImageResource(R.drawable.topic_no_name);
                        userNameTv.setText(this.mContext.getString(R.string.no_name_tip));
                        userNameTv.setTextColor(this.mContext.getResources().getColor(R.color.no_name_color));
                    }
                    iv_user_icon.setTag(topicInfo);
                    iv_user_icon.setOnClickListener(this.onClickListener);
                    userNameTv.setTag(topicInfo);
                    userNameTv.setOnClickListener(this);
                    tv_topic_detail = (TextView) convertView.findViewById(R.id.tv_topic_detail);
                    locationTv = (TextView) convertView.findViewById(R.id.tv_topic_location);
                    topicDesc = topicInfo.getDesc();
                    if (topicDesc != null) {
                        if (!BaseConstants.ah.equals(topicDesc)) {
                            tv_topic_detail.setVisibility(View.VISIBLE);
                            tv_topic_detail.setText(TextUtil.formatContent(topicDesc, this.mContext));
                            tv_topic_detail.setMovementMethod(LinkMovementMethod.getInstance());
                            pointText = topicInfo.getPoitext();
                            if (pointText != null) {
                                if (!BaseConstants.ah.equals(pointText)) {
                                    locationTv.setText(pointText);
                                    locationTv.setVisibility(View.VISIBLE);
                                    locationTv.setTag(topicInfo);
                                    locationTv.setOnClickListener(this);
                                    favTopicTv = (TextView) convertView.findViewById(R.id.tv_home_zan);
                                    favTopicTv.setText(topicInfo.getLikenum() != 0 ? this.mContext.getString(R.string.home_zan_tip) : new StringBuilder(String.valueOf(topicInfo.getLikenum())).toString());
                                    favTopicTv.setSelected(topicInfo.getIslike() != 1);
                                    forwardTopicTv = (TextView) convertView.findViewById(R.id.tv_home_forward);
                                    forwardTopicTv.setText(topicInfo.getRepostnum() != 0 ? this.mContext.getString(R.string.home_forward_tip) : new StringBuilder(String.valueOf(topicInfo.getRepostnum())).toString());
                                    forwardTopicTv.setSelected(topicInfo.getUserisrepost() != 1);
                                    totalComment = topicInfo.getTotalcomment();
                                    ((TextView) convertView.findViewById(R.id.tv_home_comment)).setText(totalComment != 0 ? this.mContext.getString(R.string.comment_tip) : new StringBuilder(String.valueOf(totalComment)).toString());
                                    fancyCoverFlow = (AutoCoverFlow) convertView.findViewById(R.id.fancyCoverFlow);
                                    noHasCommentTv = (TextView) convertView.findViewById(R.id.tv_no_has_comment);
                                    if (topicInfo.getCommentlist().size() != 0) {
                                        fancyCoverFlow.setVisibility(View.INVISIBLE);
                                        noHasCommentTv.setVisibility(View.VISIBLE);
                                        noHasCommentTv.setOnClickListener(null);
                                    }
                                    fancyCoverFlow.setVisibility(View.VISIBLE);
                                    noHasCommentTv.setVisibility(View.INVISIBLE);
                                    fancyCoverFlow.setAdapter(new AutoCoverFlowAdapter(this.mContext, topicInfo.getCommentlist()));
                                    fancyCoverFlow.setTag(R.id.fancy_cover_flow_pos, Integer.valueOf(listPos));
                                    fancyCoverFlow.setTag(R.id.fancy_cover_flow_topicinfo, topicInfo);
                                    fancyCoverFlow.setOnItemClickListener(this.mAvatarOnItemClickListener);
                                    fancyCoverFlow.setOnItemSelectedListener(this.mAvatarOnItemSelectedListener);
                                    if (commentPos == -1) {
                                        fancyCoverFlow.postDelayed(new C08484(fancyCoverFlow, commentPos), 100);
                                        return;
                                    }
                                    return;
                                }
                            }
                            locationTv.setVisibility(View.GONE);
                            locationTv.setTag(topicInfo);
                            locationTv.setOnClickListener(this);
                            favTopicTv = (TextView) convertView.findViewById(R.id.tv_home_zan);
                            if (topicInfo.getLikenum() != 0) {
                            }
                            favTopicTv.setText(topicInfo.getLikenum() != 0 ? this.mContext.getString(R.string.home_zan_tip) : new StringBuilder(String.valueOf(topicInfo.getLikenum())).toString());
                            if (topicInfo.getIslike() != 1) {
                            }
                            favTopicTv.setSelected(topicInfo.getIslike() != 1);
                            forwardTopicTv = (TextView) convertView.findViewById(R.id.tv_home_forward);
                            if (topicInfo.getRepostnum() != 0) {
                            }
                            forwardTopicTv.setText(topicInfo.getRepostnum() != 0 ? this.mContext.getString(R.string.home_forward_tip) : new StringBuilder(String.valueOf(topicInfo.getRepostnum())).toString());
                            if (topicInfo.getUserisrepost() != 1) {
                            }
                            forwardTopicTv.setSelected(topicInfo.getUserisrepost() != 1);
                            totalComment = topicInfo.getTotalcomment();
                            if (totalComment != 0) {
                            }
                            ((TextView) convertView.findViewById(R.id.tv_home_comment)).setText(totalComment != 0 ? this.mContext.getString(R.string.comment_tip) : new StringBuilder(String.valueOf(totalComment)).toString());
                            fancyCoverFlow = (AutoCoverFlow) convertView.findViewById(R.id.fancyCoverFlow);
                            noHasCommentTv = (TextView) convertView.findViewById(R.id.tv_no_has_comment);
                            if (topicInfo.getCommentlist().size() != 0) {
                                fancyCoverFlow.setVisibility(View.VISIBLE);
                                noHasCommentTv.setVisibility(View.INVISIBLE);
                                fancyCoverFlow.setAdapter(new AutoCoverFlowAdapter(this.mContext, topicInfo.getCommentlist()));
                                fancyCoverFlow.setTag(R.id.fancy_cover_flow_pos, Integer.valueOf(listPos));
                                fancyCoverFlow.setTag(R.id.fancy_cover_flow_topicinfo, topicInfo);
                                fancyCoverFlow.setOnItemClickListener(this.mAvatarOnItemClickListener);
                                fancyCoverFlow.setOnItemSelectedListener(this.mAvatarOnItemSelectedListener);
                                if (commentPos == -1) {
                                    fancyCoverFlow.postDelayed(new C08484(fancyCoverFlow, commentPos), 100);
                                    return;
                                }
                                return;
                            }
                            fancyCoverFlow.setVisibility(View.INVISIBLE);
                            noHasCommentTv.setVisibility(View.VISIBLE);
                            noHasCommentTv.setOnClickListener(null);
                        }
                    }
                    tv_topic_detail.setVisibility(View.GONE);
                    pointText = topicInfo.getPoitext();
                    if (pointText != null) {
                        if (BaseConstants.ah.equals(pointText)) {
                            locationTv.setText(pointText);
                            locationTv.setVisibility(View.VISIBLE);
                            locationTv.setTag(topicInfo);
                            locationTv.setOnClickListener(this);
                            favTopicTv = (TextView) convertView.findViewById(R.id.tv_home_zan);
                            if (topicInfo.getLikenum() != 0) {
                            }
                            favTopicTv.setText(topicInfo.getLikenum() != 0 ? this.mContext.getString(R.string.home_zan_tip) : new StringBuilder(String.valueOf(topicInfo.getLikenum())).toString());
                            if (topicInfo.getIslike() != 1) {
                            }
                            favTopicTv.setSelected(topicInfo.getIslike() != 1);
                            forwardTopicTv = (TextView) convertView.findViewById(R.id.tv_home_forward);
                            if (topicInfo.getRepostnum() != 0) {
                            }
                            forwardTopicTv.setText(topicInfo.getRepostnum() != 0 ? this.mContext.getString(R.string.home_forward_tip) : new StringBuilder(String.valueOf(topicInfo.getRepostnum())).toString());
                            if (topicInfo.getUserisrepost() != 1) {
                            }
                            forwardTopicTv.setSelected(topicInfo.getUserisrepost() != 1);
                            totalComment = topicInfo.getTotalcomment();
                            if (totalComment != 0) {
                            }
                            ((TextView) convertView.findViewById(R.id.tv_home_comment)).setText(totalComment != 0 ? this.mContext.getString(R.string.comment_tip) : new StringBuilder(String.valueOf(totalComment)).toString());
                            fancyCoverFlow = (AutoCoverFlow) convertView.findViewById(R.id.fancyCoverFlow);
                            noHasCommentTv = (TextView) convertView.findViewById(R.id.tv_no_has_comment);
                            if (topicInfo.getCommentlist().size() != 0) {
                                fancyCoverFlow.setVisibility(View.INVISIBLE);
                                noHasCommentTv.setVisibility(View.VISIBLE);
                                noHasCommentTv.setOnClickListener(null);
                            }
                            fancyCoverFlow.setVisibility(View.VISIBLE);
                            noHasCommentTv.setVisibility(View.INVISIBLE);
                            fancyCoverFlow.setAdapter(new AutoCoverFlowAdapter(this.mContext, topicInfo.getCommentlist()));
                            fancyCoverFlow.setTag(R.id.fancy_cover_flow_pos, Integer.valueOf(listPos));
                            fancyCoverFlow.setTag(R.id.fancy_cover_flow_topicinfo, topicInfo);
                            fancyCoverFlow.setOnItemClickListener(this.mAvatarOnItemClickListener);
                            fancyCoverFlow.setOnItemSelectedListener(this.mAvatarOnItemSelectedListener);
                            if (commentPos == -1) {
                                fancyCoverFlow.postDelayed(new C08484(fancyCoverFlow, commentPos), 100);
                                return;
                            }
                            return;
                        }
                    }
                    locationTv.setVisibility(View.GONE);
                    locationTv.setTag(topicInfo);
                    locationTv.setOnClickListener(this);
                    favTopicTv = (TextView) convertView.findViewById(R.id.tv_home_zan);
                    if (topicInfo.getLikenum() != 0) {
                    }
                    favTopicTv.setText(topicInfo.getLikenum() != 0 ? this.mContext.getString(R.string.home_zan_tip) : new StringBuilder(String.valueOf(topicInfo.getLikenum())).toString());
                    if (topicInfo.getIslike() != 1) {
                    }
                    favTopicTv.setSelected(topicInfo.getIslike() != 1);
                    forwardTopicTv = (TextView) convertView.findViewById(R.id.tv_home_forward);
                    if (topicInfo.getRepostnum() != 0) {
                    }
                    forwardTopicTv.setText(topicInfo.getRepostnum() != 0 ? this.mContext.getString(R.string.home_forward_tip) : new StringBuilder(String.valueOf(topicInfo.getRepostnum())).toString());
                    if (topicInfo.getUserisrepost() != 1) {
                    }
                    forwardTopicTv.setSelected(topicInfo.getUserisrepost() != 1);
                    totalComment = topicInfo.getTotalcomment();
                    if (totalComment != 0) {
                    }
                    ((TextView) convertView.findViewById(R.id.tv_home_comment)).setText(totalComment != 0 ? this.mContext.getString(R.string.comment_tip) : new StringBuilder(String.valueOf(totalComment)).toString());
                    fancyCoverFlow = (AutoCoverFlow) convertView.findViewById(R.id.fancyCoverFlow);
                    noHasCommentTv = (TextView) convertView.findViewById(R.id.tv_no_has_comment);
                    if (topicInfo.getCommentlist().size() != 0) {
                        fancyCoverFlow.setVisibility(View.VISIBLE);
                        noHasCommentTv.setVisibility(View.INVISIBLE);
                        fancyCoverFlow.setAdapter(new AutoCoverFlowAdapter(this.mContext, topicInfo.getCommentlist()));
                        fancyCoverFlow.setTag(R.id.fancy_cover_flow_pos, Integer.valueOf(listPos));
                        fancyCoverFlow.setTag(R.id.fancy_cover_flow_topicinfo, topicInfo);
                        fancyCoverFlow.setOnItemClickListener(this.mAvatarOnItemClickListener);
                        fancyCoverFlow.setOnItemSelectedListener(this.mAvatarOnItemSelectedListener);
                        if (commentPos == -1) {
                            fancyCoverFlow.postDelayed(new C08484(fancyCoverFlow, commentPos), 100);
                            return;
                        }
                        return;
                    }
                    fancyCoverFlow.setVisibility(View.INVISIBLE);
                    noHasCommentTv.setVisibility(View.VISIBLE);
                    noHasCommentTv.setOnClickListener(null);
                }
            }
            iv_user_icon.setImageResource(R.drawable.img_personal_info_selector);
            iv_user_icon.setTag(topicInfo);
            iv_user_icon.setOnClickListener(this.onClickListener);
            userNameTv.setTag(topicInfo);
            userNameTv.setOnClickListener(this);
            tv_topic_detail = (TextView) convertView.findViewById(R.id.tv_topic_detail);
            locationTv = (TextView) convertView.findViewById(R.id.tv_topic_location);
            topicDesc = topicInfo.getDesc();
            if (topicDesc != null) {
                if (BaseConstants.ah.equals(topicDesc)) {
                    tv_topic_detail.setVisibility(View.VISIBLE);
                    tv_topic_detail.setText(TextUtil.formatContent(topicDesc, this.mContext));
                    tv_topic_detail.setMovementMethod(LinkMovementMethod.getInstance());
                    pointText = topicInfo.getPoitext();
                    if (pointText != null) {
                        if (BaseConstants.ah.equals(pointText)) {
                            locationTv.setText(pointText);
                            locationTv.setVisibility(View.VISIBLE);
                            locationTv.setTag(topicInfo);
                            locationTv.setOnClickListener(this);
                            favTopicTv = (TextView) convertView.findViewById(R.id.tv_home_zan);
                            if (topicInfo.getLikenum() != 0) {
                            }
                            favTopicTv.setText(topicInfo.getLikenum() != 0 ? this.mContext.getString(R.string.home_zan_tip) : new StringBuilder(String.valueOf(topicInfo.getLikenum())).toString());
                            if (topicInfo.getIslike() != 1) {
                            }
                            favTopicTv.setSelected(topicInfo.getIslike() != 1);
                            forwardTopicTv = (TextView) convertView.findViewById(R.id.tv_home_forward);
                            if (topicInfo.getRepostnum() != 0) {
                            }
                            forwardTopicTv.setText(topicInfo.getRepostnum() != 0 ? this.mContext.getString(R.string.home_forward_tip) : new StringBuilder(String.valueOf(topicInfo.getRepostnum())).toString());
                            if (topicInfo.getUserisrepost() != 1) {
                            }
                            forwardTopicTv.setSelected(topicInfo.getUserisrepost() != 1);
                            totalComment = topicInfo.getTotalcomment();
                            if (totalComment != 0) {
                            }
                            ((TextView) convertView.findViewById(R.id.tv_home_comment)).setText(totalComment != 0 ? this.mContext.getString(R.string.comment_tip) : new StringBuilder(String.valueOf(totalComment)).toString());
                            fancyCoverFlow = (AutoCoverFlow) convertView.findViewById(R.id.fancyCoverFlow);
                            noHasCommentTv = (TextView) convertView.findViewById(R.id.tv_no_has_comment);
                            if (topicInfo.getCommentlist().size() != 0) {
                                fancyCoverFlow.setVisibility(View.INVISIBLE);
                                noHasCommentTv.setVisibility(View.VISIBLE);
                                noHasCommentTv.setOnClickListener(null);
                            }
                            fancyCoverFlow.setVisibility(View.VISIBLE);
                            noHasCommentTv.setVisibility(View.INVISIBLE);
                            fancyCoverFlow.setAdapter(new AutoCoverFlowAdapter(this.mContext, topicInfo.getCommentlist()));
                            fancyCoverFlow.setTag(R.id.fancy_cover_flow_pos, Integer.valueOf(listPos));
                            fancyCoverFlow.setTag(R.id.fancy_cover_flow_topicinfo, topicInfo);
                            fancyCoverFlow.setOnItemClickListener(this.mAvatarOnItemClickListener);
                            fancyCoverFlow.setOnItemSelectedListener(this.mAvatarOnItemSelectedListener);
                            if (commentPos == -1) {
                                fancyCoverFlow.postDelayed(new C08484(fancyCoverFlow, commentPos), 100);
                                return;
                            }
                            return;
                        }
                    }
                    locationTv.setVisibility(View.GONE);
                    locationTv.setTag(topicInfo);
                    locationTv.setOnClickListener(this);
                    favTopicTv = (TextView) convertView.findViewById(R.id.tv_home_zan);
                    if (topicInfo.getLikenum() != 0) {
                    }
                    favTopicTv.setText(topicInfo.getLikenum() != 0 ? this.mContext.getString(R.string.home_zan_tip) : new StringBuilder(String.valueOf(topicInfo.getLikenum())).toString());
                    if (topicInfo.getIslike() != 1) {
                    }
                    favTopicTv.setSelected(topicInfo.getIslike() != 1);
                    forwardTopicTv = (TextView) convertView.findViewById(R.id.tv_home_forward);
                    if (topicInfo.getRepostnum() != 0) {
                    }
                    forwardTopicTv.setText(topicInfo.getRepostnum() != 0 ? this.mContext.getString(R.string.home_forward_tip) : new StringBuilder(String.valueOf(topicInfo.getRepostnum())).toString());
                    if (topicInfo.getUserisrepost() != 1) {
                    }
                    forwardTopicTv.setSelected(topicInfo.getUserisrepost() != 1);
                    totalComment = topicInfo.getTotalcomment();
                    if (totalComment != 0) {
                    }
                    ((TextView) convertView.findViewById(R.id.tv_home_comment)).setText(totalComment != 0 ? this.mContext.getString(R.string.comment_tip) : new StringBuilder(String.valueOf(totalComment)).toString());
                    fancyCoverFlow = (AutoCoverFlow) convertView.findViewById(R.id.fancyCoverFlow);
                    noHasCommentTv = (TextView) convertView.findViewById(R.id.tv_no_has_comment);
                    if (topicInfo.getCommentlist().size() != 0) {
                        fancyCoverFlow.setVisibility(View.VISIBLE);
                        noHasCommentTv.setVisibility(View.INVISIBLE);
                        fancyCoverFlow.setAdapter(new AutoCoverFlowAdapter(this.mContext, topicInfo.getCommentlist()));
                        fancyCoverFlow.setTag(R.id.fancy_cover_flow_pos, Integer.valueOf(listPos));
                        fancyCoverFlow.setTag(R.id.fancy_cover_flow_topicinfo, topicInfo);
                        fancyCoverFlow.setOnItemClickListener(this.mAvatarOnItemClickListener);
                        fancyCoverFlow.setOnItemSelectedListener(this.mAvatarOnItemSelectedListener);
                        if (commentPos == -1) {
                            fancyCoverFlow.postDelayed(new C08484(fancyCoverFlow, commentPos), 100);
                            return;
                        }
                        return;
                    }
                    fancyCoverFlow.setVisibility(View.INVISIBLE);
                    noHasCommentTv.setVisibility(View.VISIBLE);
                    noHasCommentTv.setOnClickListener(null);
                }
            }
            tv_topic_detail.setVisibility(View.GONE);
            pointText = topicInfo.getPoitext();
            if (pointText != null) {
                if (BaseConstants.ah.equals(pointText)) {
                    locationTv.setText(pointText);
                    locationTv.setVisibility(View.VISIBLE);
                    locationTv.setTag(topicInfo);
                    locationTv.setOnClickListener(this);
                    favTopicTv = (TextView) convertView.findViewById(R.id.tv_home_zan);
                    if (topicInfo.getLikenum() != 0) {
                    }
                    favTopicTv.setText(topicInfo.getLikenum() != 0 ? this.mContext.getString(R.string.home_zan_tip) : new StringBuilder(String.valueOf(topicInfo.getLikenum())).toString());
                    if (topicInfo.getIslike() != 1) {
                    }
                    favTopicTv.setSelected(topicInfo.getIslike() != 1);
                    forwardTopicTv = (TextView) convertView.findViewById(R.id.tv_home_forward);
                    if (topicInfo.getRepostnum() != 0) {
                    }
                    forwardTopicTv.setText(topicInfo.getRepostnum() != 0 ? this.mContext.getString(R.string.home_forward_tip) : new StringBuilder(String.valueOf(topicInfo.getRepostnum())).toString());
                    if (topicInfo.getUserisrepost() != 1) {
                    }
                    forwardTopicTv.setSelected(topicInfo.getUserisrepost() != 1);
                    totalComment = topicInfo.getTotalcomment();
                    if (totalComment != 0) {
                    }
                    ((TextView) convertView.findViewById(R.id.tv_home_comment)).setText(totalComment != 0 ? this.mContext.getString(R.string.comment_tip) : new StringBuilder(String.valueOf(totalComment)).toString());
                    fancyCoverFlow = (AutoCoverFlow) convertView.findViewById(R.id.fancyCoverFlow);
                    noHasCommentTv = (TextView) convertView.findViewById(R.id.tv_no_has_comment);
                    if (topicInfo.getCommentlist().size() != 0) {
                        fancyCoverFlow.setVisibility(View.INVISIBLE);
                        noHasCommentTv.setVisibility(View.VISIBLE);
                        noHasCommentTv.setOnClickListener(null);
                    }
                    fancyCoverFlow.setVisibility(View.VISIBLE);
                    noHasCommentTv.setVisibility(View.INVISIBLE);
                    fancyCoverFlow.setAdapter(new AutoCoverFlowAdapter(this.mContext, topicInfo.getCommentlist()));
                    fancyCoverFlow.setTag(R.id.fancy_cover_flow_pos, Integer.valueOf(listPos));
                    fancyCoverFlow.setTag(R.id.fancy_cover_flow_topicinfo, topicInfo);
                    fancyCoverFlow.setOnItemClickListener(this.mAvatarOnItemClickListener);
                    fancyCoverFlow.setOnItemSelectedListener(this.mAvatarOnItemSelectedListener);
                    if (commentPos == -1) {
                        fancyCoverFlow.postDelayed(new C08484(fancyCoverFlow, commentPos), 100);
                        return;
                    }
                    return;
                }
            }
            locationTv.setVisibility(View.GONE);
            locationTv.setTag(topicInfo);
            locationTv.setOnClickListener(this);
            favTopicTv = (TextView) convertView.findViewById(R.id.tv_home_zan);
            if (topicInfo.getLikenum() != 0) {
            }
            favTopicTv.setText(topicInfo.getLikenum() != 0 ? this.mContext.getString(R.string.home_zan_tip) : new StringBuilder(String.valueOf(topicInfo.getLikenum())).toString());
            if (topicInfo.getIslike() != 1) {
            }
            favTopicTv.setSelected(topicInfo.getIslike() != 1);
            forwardTopicTv = (TextView) convertView.findViewById(R.id.tv_home_forward);
            if (topicInfo.getRepostnum() != 0) {
            }
            forwardTopicTv.setText(topicInfo.getRepostnum() != 0 ? this.mContext.getString(R.string.home_forward_tip) : new StringBuilder(String.valueOf(topicInfo.getRepostnum())).toString());
            if (topicInfo.getUserisrepost() != 1) {
            }
            forwardTopicTv.setSelected(topicInfo.getUserisrepost() != 1);
            totalComment = topicInfo.getTotalcomment();
            if (totalComment != 0) {
            }
            ((TextView) convertView.findViewById(R.id.tv_home_comment)).setText(totalComment != 0 ? this.mContext.getString(R.string.comment_tip) : new StringBuilder(String.valueOf(totalComment)).toString());
            fancyCoverFlow = (AutoCoverFlow) convertView.findViewById(R.id.fancyCoverFlow);
            noHasCommentTv = (TextView) convertView.findViewById(R.id.tv_no_has_comment);
            if (topicInfo.getCommentlist().size() != 0) {
                fancyCoverFlow.setVisibility(View.VISIBLE);
                noHasCommentTv.setVisibility(View.INVISIBLE);
                fancyCoverFlow.setAdapter(new AutoCoverFlowAdapter(this.mContext, topicInfo.getCommentlist()));
                fancyCoverFlow.setTag(R.id.fancy_cover_flow_pos, Integer.valueOf(listPos));
                fancyCoverFlow.setTag(R.id.fancy_cover_flow_topicinfo, topicInfo);
                fancyCoverFlow.setOnItemClickListener(this.mAvatarOnItemClickListener);
                fancyCoverFlow.setOnItemSelectedListener(this.mAvatarOnItemSelectedListener);
                if (commentPos == -1) {
                    fancyCoverFlow.postDelayed(new C08484(fancyCoverFlow, commentPos), 100);
                    return;
                }
                return;
            }
            fancyCoverFlow.setVisibility(View.INVISIBLE);
            noHasCommentTv.setVisibility(View.VISIBLE);
            noHasCommentTv.setOnClickListener(null);
        }
    }

    private View getConvertView(int listPos) {
        if (!this.isTopicDetail) {
            return this.mListView.getChildAt((this.mHeaderCount + listPos) - this.mListView.getFirstVisiblePosition());
        }
        if (this.isTheme) {
            return ((ITopicDisplay) this.fragment).getConvertView();
        }
        return ((ITopicDisplay) this.mContext).getConvertView();
    }

    public void uploadLocalViews() {
        if (this.mTopicList != null && this.mTopicList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("{");
            boolean isNeedUpload = false;
            for (TopicInfo topicInfo : this.mTopicList) {
                if (topicInfo.getType().intValue() == 5 && topicInfo.getLocalViews() > 0) {
                    isNeedUpload = true;
                    CookieParser.appendIntegerJson(sb, topicInfo.getTopicid(), topicInfo.getLocalViews());
                    topicInfo.setLocalViews(0);
                }
            }
            sb = sb.deleteCharAt(sb.length() - 1);
            sb.append("}");
//            if (isNeedUpload) {
//                QGHttpRequest.getInstance().viewTopicRequest(this.mContext, sb.toString(), new C17825(this.mContext));
//            }
        }
    }

    private void startProgressAnim(ImageView imgView) {
        if (this.mAnimation == null) {
            this.mAnimation = (AnimationDrawable) this.mContext.getResources().getDrawable(R.drawable.comment_loading_anim);
        }
        imgView.setImageDrawable(this.mAnimation);
        this.mAnimation.start();
    }

    private void stopProgressAnim(ImageView imgView) {
        if (this.mAnimation != null) {
            this.mAnimation.stop();
        }
        imgView.clearAnimation();
    }

    private void changeAvatorView(AdapterView<?> parent, int selectPos) {
        AutoCoverFlow coverFlow = (AutoCoverFlow) parent;
        for (int i = 0; i < coverFlow.getChildCount(); i++) {
            if (coverFlow.getChildAt(i) instanceof CircleImageView) {
                CircleImageView imageView = (CircleImageView) coverFlow.getChildAt(i);
                if (i == selectPos - coverFlow.getFirstVisiblePosition()) {
                    imageView.setHasSelected(true);
                    imageView.setBorderWidth(this.mAvatarBorderWidth);
                    imageView.setLayoutParams(AutoCoverFlowAdapter.mSelectedLayoutParams);
                } else {
                    imageView.setHasSelected(false);
                    imageView.setBorderWidth(0);
                    imageView.setLayoutParams(AutoCoverFlowAdapter.mNormalLayoutParams);
                }
            }
        }
    }

    private void sendSelectedCommentMsg(AdapterView<?> parent, int position) {
        Message msg = this.mHandler.obtainMessage();
        msg.what = SELECT_COMMENT_WHAT;
        msg.arg1 = position;
        msg.obj = parent;
        this.mHandler.sendMessageDelayed(msg, 200);
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_user_name:
//                startPersonalActivity(v);
                break;
            case R.id.tv_topic_location:
//                TopicInfo topicInfo = (TopicInfo) v.getTag();
//                intent = new Intent(this.mContext, ThemeActivity.class);
//                HuaTi huaTi = new HuaTi();
//                huaTi.setTopicid(topicInfo.getPoiid());
//                huaTi.setHuatitext(topicInfo.getPoitext());
//                huaTi.setType(false);
//                intent.putExtra("huati", huaTi);
//                this.mContext.startActivity(intent);
//                animationForNew();
//                PlaySound.getInstance(this.mContext).toPlay(R.raw.open);
            case R.id.img_topic_switch_comment:
//                switchCommentShow(v);
                break;
            case R.id.img_topic_live_mark:
//                if (this.fragment != null && (this.fragment instanceof HomeFragment)) {
//                    TopicInfo topicinfo = (TopicInfo) v.getTag();
//                    if (topicinfo != null) {
//                        ((HomeFragment) this.fragment).startAVActivity(topicinfo);
//                    }
//                }
                break;
            case R.id.iv_user_follow_status:
//                TopicMore.followUser(this.mContext, (ImageView) v);
                break;
            case R.id.tv_topic_device:
//                if (MyApplication.getInstance().isLogin()) {
//                    this.mContext.startActivity(new Intent(this.mContext, PhoneDeviceActivity.class));
//                    animationForNew();
//                    return;
//                }
//                this.mContext.startActivity(new Intent(this.mContext, LoginActivity.class));
//                animationForNew();
            case R.id.tv_topic_more:
//                startTopDetailActivity(v, false);
                break;
            case R.id.img_topic_switch_isredpacket:
//                TopicMore.reciveRedPacket(this.mContext, (ImageView) v, null, this, this.onClickListener, this.isTopicDetail);
                break;
            case R.id.tv_home_comment:
//                startTopDetailActivity(v, true);
                break;
            case R.id.tv_home_zan:
//                TopicMore.favTopic(this.mContext, (TextView) v);
                break;
            case R.id.tv_home_forward:
//                TopicMore.repostTopic(this.mContext, (TextView) v);
                break;
            case R.id.tv_huati_more:
//                String moreLink = (String) v.getTag();
//                intent = new Intent(this.mContext, WebViewActivity.class);
//                intent.putExtra("web_url", moreLink);
//                this.mContext.startActivity(intent);
//                animationForNew();
                break;
            case R.id.iv_huati_2:
            case R.id.tv_huati_2:
            case R.id.iv_huati_3:
            case R.id.tv_huati_3:
            case R.id.iv_huati_1:
            case R.id.tv_huati_1:
            case R.id.iv_huati_4:
            case R.id.tv_huati_4:
            case R.id.iv_huati_5:
            case R.id.tv_huati_5:
            case R.id.iv_huati_6:
            case R.id.tv_huati_6:
//                HuaTi huati = (HuaTi) v.getTag();
//                huati.setType(true);
//                intent = new Intent(this.mContext, ThemeActivity.class);
//                intent.putExtra("huati", huati);
//                this.mContext.startActivity(intent);
//                animationForNew();
                break;
            default:
        }
    }

    private void switchCommentShow(View view) {
//        if (VERSION.SDK_INT >= 11) {
//            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object) view, "rotationY", 0.0f, 0.0f).setDuration(300);
//            objectAnimator.addListener(new C14386(view));
//            objectAnimator.start();
//            return;
//        }
//        switchComment(view);
    }

    private void switchComment(View view) {
        AutoCoverFlowHelper autoFlowHelper;
        if (view.isSelected()) {
            if (this.isTheme) {
                autoFlowHelper = ((ITopicDisplay) this.fragment).getFlowHelper();
                if (autoFlowHelper != null) {
                    autoFlowHelper.resumeCommentShow();
                }
            } else {
                ((ITopicDisplay) this.mContext).getFlowHelper().resumeCommentShow();
            }
            view.setSelected(false);
            ((TextView) view).setText(this.mContext.getString(R.string.topic_comment_no_see_str));
            return;
        }
        if (this.isTheme) {
            autoFlowHelper = ((ITopicDisplay) this.fragment).getFlowHelper();
            if (autoFlowHelper != null) {
                autoFlowHelper.StopCommentShow();
            }
        } else {
            ((ITopicDisplay) this.mContext).getFlowHelper().StopCommentShow();
        }
        view.setSelected(true);
        ((TextView) view).setText(this.mContext.getString(R.string.topic_comment_see_str));
        Toast.makeText(this.mContext, this.mContext.getString(R.string.topic_switch_comment_tip), Toast.LENGTH_LONG).show();
    }

    private void startPersonalActivity(View view) {
//        TopicInfo topicInfo = (TopicInfo) view.getTag();
//        if (topicInfo != null) {
//            String isName = topicInfo.getIskana();
//            Intent intent;
//            Bundle bundle;
//            if (isName == null || BaseConstants.ah.equals(isName)) {
//                intent = new Intent();
//                intent.setClass(this.mContext, PersonalNewActivity.class);
//                bundle = new Bundle();
//                bundle.putString(PersonalNewActivity.PERSONAL_USER_ID, topicInfo.getUid());
//                intent.putExtras(bundle);
//                this.mContext.startActivity(intent);
//                animationForNew();
//                PlaySound.getInstance(this.mContext).toPlay(R.raw.open);
//            } else if (Integer.valueOf(isName).intValue() == 0) {
//                intent = new Intent();
//                intent.setClass(this.mContext, PersonalNewActivity.class);
//                bundle = new Bundle();
//                bundle.putString(PersonalNewActivity.PERSONAL_USER_ID, topicInfo.getUid());
//                intent.putExtras(bundle);
//                this.mContext.startActivity(intent);
//                animationForNew();
//                PlaySound.getInstance(this.mContext).toPlay(R.raw.open);
//            } else {
//                Toast.makeText(this.mContext, this.mContext.getString(R.string.theme_isname_tip), 0).show();
//            }
//        }
    }

    private void startTopDetailActivity(View view, boolean flag) {
        startTopDetailActivity(view, flag, false);
    }

    public void startTopDetailActivity(View view, boolean flag, boolean lookRedPaket) {
        if (MyApplication.getInstance().isLogin()) {
            int position = ((Integer) view.getTag()).intValue();
            TopicInfo topicInfo = (TopicInfo) this.mTopicList.get(position);
            if (topicInfo != null) {
                this.mClickListPosition = position;
                List<TopicInfo> topics = new ArrayList();
                topics.add(topicInfo);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.setClass(this.mContext, TopicDetailPagerActivity.class);
                bundle.putBoolean("isFromHomePage", flag);
                bundle.putSerializable("topics", (Serializable) topics);
                bundle.putBoolean("lookRedPacket", lookRedPaket);
                intent.putExtras(bundle);
                ((Activity) this.mContext).startActivityForResult(intent, 0);
                animationForNew();
//                PlaySound.getInstance(this.mContext).toPlay(R.raw.open);
                return;
            }
            return;
        }
        TopicMore.startLoginActivity(this.mContext);
    }

    private void animationForNew() {
        ((Activity) this.mContext).overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
