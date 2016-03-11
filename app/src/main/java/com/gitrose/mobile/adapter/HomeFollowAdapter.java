package com.gitrose.mobile.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.support.v4.app.Fragment;
import android.support.v4.util.TimeUtils;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
//import com.nineoldandroids.animation.Animator;
//import com.nineoldandroids.animation.Animator.AnimatorListener;
//import com.nineoldandroids.animation.ObjectAnimator;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.mobile.R;
import com.gitrose.mobile.HomeFollowFragment;
import com.gitrose.mobile.LoginActivity;
//import com.gitrose.mobile.PersonalNewActivity;
//import com.gitrose.mobile.PhoneDeviceActivity;
import com.gitrose.mobile.SearchActivity;
//import com.gitrose.mobile.ThemeActivity;
import com.gitrose.mobile.TopicDetailActivity;
import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.WebViewActivity;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.auth.CookieParser;
import com.gitrose.mobile.base.ITopicDisplay;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.Comment;
import com.gitrose.mobile.model.HomeHuatiPoi;
import com.gitrose.mobile.model.HotCommentList;
import com.gitrose.mobile.model.HuaTi;
import com.gitrose.mobile.model.Operation;
import com.gitrose.mobile.model.OperationButton;
import com.gitrose.mobile.model.RepostUserInfo;
import com.gitrose.mobile.model.RepostUserList;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.utils.QuickReturnUtils;
import com.gitrose.mobile.view.AutoCoverFlow;
import com.gitrose.mobile.view.AutoCoverFlowHelper;
import com.gitrose.mobile.view.CircleImageView;
import com.gitrose.mobile.view.CommentImageView;
import com.gitrose.mobile.view.TextViewFixTouchConsume;
import com.gitrose.mobile.view.TopicMore;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.litepal.util.Const.TableSchema;
import qalsdk.BaseConstants;
import shouji.gexing.framework.utils.UiUtils;

public class HomeFollowAdapter extends BaseAdapter implements OnClickListener {
    public static final int MAX_REQUEST_COMMENT_SIZE = 50;
    public static final int SELECT_COMMENT_WHAT = 2;
    private SimpleDateFormat dateFormat;
    private Fragment fragment;
    private boolean isFromPersonal;
    private boolean isTheme;
    private boolean isTopicDetail;
    private int isTopicPagerActivity;
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
    private OnItemClickListener onItemClickListener;

    /* renamed from: com.gitrose.mobile.adapter.HomeFollowAdapter.1 */
    class C08401 extends Handler {
        C08401() {
        }

        public void handleMessage(Message msg) {
            if (msg.what == HomeFollowAdapter.SELECT_COMMENT_WHAT) {
                HomeFollowAdapter.this.changeAvatorView((AdapterView) msg.obj, msg.arg1);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.HomeFollowAdapter.2 */
    class C08412 implements OnItemClickListener {
        C08412() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            ArrayList<HomeHuatiPoi> huatiPois = (ArrayList) adapterView.getTag();
            if (huatiPois != null && huatiPois.size() > 0 && position < huatiPois.size()) {
                HomeHuatiPoi homeHuatiPoi = (HomeHuatiPoi) huatiPois.get(position);
                if (homeHuatiPoi != null) {
                    Intent intent = new Intent(HomeFollowAdapter.this.mContext, TopicDetailActivity.class);
                    intent.putExtra(TopicDetailActivity.TOPIC_ID, homeHuatiPoi.getTopicid());
                    HomeFollowAdapter.this.mContext.startActivity(intent);
                    HomeFollowAdapter.this.animationForNew();
                }
            }
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.HomeFollowAdapter.3 */
    class C08423 implements OnClickListener {
        C08423() {
        }

        public void onClick(View v) {
//            WebViewActivity.openWebView(HomeFollowAdapter.this.mContext, (String) v.getTag());
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.HomeFollowAdapter.4 */
    class C08434 implements Runnable {
        private final /* synthetic */ int val$commentPos;
        private final /* synthetic */ AutoCoverFlow val$fancyCoverFlow;

        C08434(AutoCoverFlow autoCoverFlow, int i) {
            this.val$fancyCoverFlow = autoCoverFlow;
            this.val$commentPos = i;
        }

        public void run() {
            if (this.val$fancyCoverFlow != null) {
                this.val$fancyCoverFlow.setSelection(this.val$commentPos);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.HomeFollowAdapter.5 */
    class C08445 implements Runnable {
        private final /* synthetic */ int val$commentPos;
        private final /* synthetic */ AutoCoverFlow val$fancyCoverFlow;

        C08445(AutoCoverFlow autoCoverFlow, int i) {
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
            TopicInfo mTopicInfo = (TopicInfo) parent.getTag(R.id.fancy_cover_flow_topicinfo);
            int pos = position - 1;
            TopicInfo topicInfo = (TopicInfo) HomeFollowAdapter.this.mTopicList.get(((Integer) parent.getTag(R.id.fancy_cover_flow_pos)).intValue());
            if (!(mTopicInfo == null || BaseConstants.ah.equals(mTopicInfo))) {
                topicInfo = mTopicInfo;
            }
            List<Comment> comments = topicInfo.getCommentlist();
            if (pos >= 0 && comments != null && pos <= comments.size() - 1 && position == parent.getSelectedItemPosition()) {
//                Intent intent = new Intent(HomeFollowAdapter.this.mContext, PersonalNewActivity.class);
//                Comment comment = (Comment) topicInfo.getCommentlist().get(pos);
//                if (comment.getIskana() == 0) {
//                    intent.putExtra(PersonalNewActivity.PERSONAL_USER_ID, comment.getUid());
//                    HomeFollowAdapter.this.mContext.startActivity(intent);
//                }
            }
        }
    }

    class AvatarOnItemSelectedListener implements OnItemSelectedListener {
        String commentId;
        List<Comment> mCommentList;

        /* renamed from: com.gitrose.mobile.adapter.HomeFollowAdapter.AvatarOnItemSelectedListener.1 */
        class C17811 extends QGHttpHandler<HotCommentList> {
            private final /* synthetic */ AutoCoverFlowAdapter val$adapter;
            private final /* synthetic */ AdapterView val$parent;
            private final /* synthetic */ int val$position;
            private final /* synthetic */ ImageView val$progressView;
            private final /* synthetic */ TopicInfo val$topicInfo;

            C17811(Context $anonymous0, TopicInfo topicInfo, AdapterView adapterView, AutoCoverFlowAdapter autoCoverFlowAdapter, int i, ImageView imageView) {

                super($anonymous0);
                this.val$topicInfo = topicInfo;
                this.val$parent = adapterView;
                this.val$adapter = autoCoverFlowAdapter;
                this.val$position = i;
                this.val$progressView = imageView;
                
            }

            public void onGetDataSuccess(HotCommentList data) {
                List<Comment> comments = data.getCommentlist();
                if (comments == null || comments.size() <= 0) {
                    this.val$adapter.setDown(false);
                    HomeFollowAdapter.this.stopProgressAnim(this.val$progressView);
                    this.val$progressView.setVisibility(View.INVISIBLE);
                    return;
                }
                int totalCount = 0;
                if (data.getTotalcomment() != null) {
                    totalCount = Integer.parseInt(data.getTotalcomment());
                }
                this.val$topicInfo.setTotalcomment(totalCount);
                for (int i = AvatarOnItemSelectedListener.this.mCommentList.size() - 1; i >= 0; i--) {
                    if (((Comment) AvatarOnItemSelectedListener.this.mCommentList.get(i)).getCommentid() == null) {
                        AvatarOnItemSelectedListener.this.mCommentList.remove(i);
                    }
                }
                AvatarOnItemSelectedListener.this.mCommentList.addAll(comments);
                ((TextView) ((View) this.val$parent.getParent().getParent()).findViewById(R.id.tv_home_comment)).setText(totalCount == 0 ? HomeFollowAdapter.this.mContext.getString(R.string.comment_tip) : new StringBuilder(String.valueOf(totalCount)).toString());
                this.val$adapter.notifyDataSetChanged();
                ((AutoCoverFlow) this.val$parent).setSelection(this.val$position);
                HomeFollowAdapter.this.sendSelectedCommentMsg(this.val$parent, this.val$position);
            }

            public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
                HomeFollowAdapter.this.stopProgressAnim(this.val$progressView);
                this.val$progressView.setVisibility(View.INVISIBLE);
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        }

        AvatarOnItemSelectedListener() {
            this.mCommentList = null;
            this.commentId = null;
        }

        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int listPosition = ((Integer) parent.getTag(R.id.fancy_cover_flow_pos)).intValue();
            TopicInfo topicInfo = (TopicInfo) parent.getTag(R.id.fancy_cover_flow_topicinfo);
            HomeFollowAdapter.this.changeAvatorView(parent, position);
            showComment(parent, position, listPosition, topicInfo);
            loadComment(parent, position, listPosition, topicInfo);
        }

        private void showComment(AdapterView<?> adapterView, int position, int listPosition, TopicInfo topicInfo) {
            View convertView = HomeFollowAdapter.this.getConvertView(listPosition);
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
            if (position != 0 && listPosition < HomeFollowAdapter.this.mTopicList.size()) {
                AutoCoverFlowAdapter adapter = (AutoCoverFlowAdapter) parent.getAdapter();
                TopicInfo topicInfo = (TopicInfo) HomeFollowAdapter.this.mTopicList.get(listPosition);
                if (position == parent.getCount() - 2 && adapter.isDown() && topicInfo.getTopicid() != null) {
                    View view = coverFlow.getChildAt(coverFlow.getChildCount() - 1);
                    if (view instanceof ImageView) {
                        view.setVisibility(View.VISIBLE);
                        HomeFollowAdapter.this.startProgressAnim((ImageView) view);
                        loadDownComment(parent, (ImageView) view, adapter, position, listPosition, mTopicInfo);
                    }
                }
            }
        }

        private void loadDownComment(AdapterView<?> parent, ImageView progressView, AutoCoverFlowAdapter adapter, int position, int listPosition, TopicInfo mTopicInfo) {
            TopicInfo topicInfo = (TopicInfo) HomeFollowAdapter.this.mTopicList.get(listPosition);
            if (mTopicInfo == null || BaseConstants.ah.equals(mTopicInfo)) {
                this.mCommentList = topicInfo.getCommentlist();
            } else {
                this.mCommentList = mTopicInfo.getCommentlist();
            }
            if (this.mCommentList != null && position - 1 < this.mCommentList.size()) {
                for (int i = this.mCommentList.size() - 1; i >= 0; i--) {
                    this.commentId = ((Comment) this.mCommentList.get(i)).getCommentid();
                    if (this.commentId != null && !BaseConstants.ah.equals(this.commentId)) {
                        break;
                    }
                }
                QGHttpRequest.getInstance().hotCommentRequest(HomeFollowAdapter.this.mContext, topicInfo.getTopicid(), this.commentId, SearchActivity.DOWN, new C17811(HomeFollowAdapter.this.mContext, topicInfo, parent, adapter, position, progressView));
            }
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
                this.moreView.setOnClickListener(HomeFollowAdapter.this);
                return;
            }
            this.moreView.setVisibility(View.GONE);
        }
    }

    public static class ViewHolder {
        TextView commentTv;
        FrameLayout commentsFl;
        AutoCoverFlow fancyCoverFlow;
        View forward_line;
        GridView gd_huati_poi;
        LinearLayout gd_ll;
        ImageView is_redpaket_iv;
        ImageView iv_huati_poi;
        CircleImageView iv_user_icon;
        ImageView iv_user_level;
        LinearLayout ll_forward;
        TextView locationTv;
        TextView moreTopicTv;
        TextView noHasCommentTv;
        TextView playCountTv;
        RelativeLayout rl_huati_item;
        RelativeLayout rl_topic_item;
        TextView switchCommentImg;
        CommentImageView toastImageView;
        TextView topicLiveMark;
        TextView topicLookTitleTv;
        TextView topicTimeTv;
        LinearLayout topic_foot_ll;
        TextView tv_forword;
        TextView tv_forword_name_1;
        TextView tv_forword_name_2;
        TextView tv_forword_name_3;
        TextView tv_home_comment;
        TextView tv_home_forward;
        TextView tv_home_more;
        TextView tv_home_zan;
        TextView tv_huati_poi;
        TextViewFixTouchConsume tv_topic_detail;
        TextView tv_topic_device;
        TextView userNameTv;
        ImageView user_follow_status;
        ImageView videoMarkImg;
    }

    /* renamed from: com.gitrose.mobile.adapter.HomeFollowAdapter.7 */
    class C14377 implements Animator.AnimatorListener {
        private final /* synthetic */ View val$view;

        C14377(View view) {
            this.val$view = view;
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            HomeFollowAdapter.this.switchComment(this.val$view);
        }

        public void onAnimationCancel(Animator animation) {
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.HomeFollowAdapter.6 */
    class C17806 extends QGHttpHandler<String> {
        C17806(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(String data) {
        }
    }

    public HomeFollowAdapter(Context context) {
        this.mAvatarBorderWidth = SELECT_COMMENT_WHAT;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.mHeaderCount = 0;
        this.mClickListPosition = -1;
        this.isTheme = false;
        this.isFromPersonal = false;
        this.mHandler = new C08401();
        this.onItemClickListener = new C08412();
        this.mContext = context;
    }

    public HomeFollowAdapter(Context context, List<TopicInfo> picList, boolean isUseRealTime, int isTopicPagerActivity) {
        this(context, null, picList);
        this.mIsUseRealTime = isUseRealTime;
        this.isTopicDetail = true;
        this.isTopicPagerActivity = isTopicPagerActivity;
    }

    public HomeFollowAdapter(Context context, ListView listView, List<TopicInfo> picList) {
        this.mAvatarBorderWidth = SELECT_COMMENT_WHAT;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.mHeaderCount = 0;
        this.mClickListPosition = -1;
        this.isTheme = false;
        this.isFromPersonal = false;
        this.mHandler = new C08401();
        this.onItemClickListener = new C08412();
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

    public HomeFollowAdapter(Context context, ListView listView, List<TopicInfo> picList, boolean isTheme, Fragment fragment) {
        this.mAvatarBorderWidth = SELECT_COMMENT_WHAT;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.mHeaderCount = 0;
        this.mClickListPosition = -1;
        this.isTheme = false;
        this.isFromPersonal = false;
        this.mHandler = new C08401();
        this.onItemClickListener = new C08412();
        this.fragment = fragment;
        this.isTheme = isTheme;
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

    public void initHomeTopicAdapter(Context context, ListView listView, List<TopicInfo> picList, boolean fromPersonal) {
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
        this.isFromPersonal = fromPersonal;
        this.isTheme = false;
    }

    public void setData(List<TopicInfo> picList) {
        this.mTopicList = picList;
        notifyDataSetChanged();
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
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup vGroup) {
//        TopicInfo topicinfo = (TopicInfo)mTopicList.get(i);
//        View view1;
//
//        ViewHolder viewgroup = new ViewHolder();
//
//        if (view == null || isTopicDetail || view.findViewById(R.id.rl_topic_item) == null)
//        {
//            if (!isTopicDetail)
//            {
//                view = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_home_follow, null);
//            }
//
//            viewgroup.rl_topic_item = (RelativeLayout)view.findViewById(R.id.rl_topic_item);
//            viewgroup.ll_forward = (LinearLayout)view.findViewById(R.id.ll_forward);
//            viewgroup.tv_forword_name_1 = (TextView)view.findViewById(R.id.tv_forword_name_1);
//            viewgroup.tv_forword_name_2 = (TextView)view.findViewById(R.id.tv_forword_name_2);
//            viewgroup.tv_forword_name_3 = (TextView)view.findViewById(R.id.tv_forword_name_3);
//            viewgroup.tv_forword = (TextView)view.findViewById(R.id.tv_forword);
//            viewgroup.forward_line = view.findViewById(R.id.forward_line);
//            viewgroup.iv_user_icon = (CircleImageView)view.findViewById(R.id.iv_user_icon);
//            viewgroup.user_follow_status = (ImageView)view.findViewById(R.id.iv_user_follow_status);
//            viewgroup.iv_user_level = (ImageView)view.findViewById(R.id.iv_user_level);
//            viewgroup.tv_home_comment = (TextView)view.findViewById(R.id.tv_home_comment);
//            viewgroup.tv_home_zan = (TextView)view.findViewById(R.id.tv_home_zan);
//            viewgroup.tv_home_forward = (TextView)view.findViewById(R.id.tv_home_forward);
//            viewgroup.tv_home_more = (TextView)view.findViewById(R.id.tv_home_more);
//            viewgroup.locationTv = (TextView)view.findViewById(R.id.tv_topic_location);
//            viewgroup.playCountTv = (TextView)view.findViewById(R.id.tv_play_count);
//            viewgroup.toastImageView = (CommentImageView)view.findViewById(R.id.toastView_topic);
//            viewgroup.userNameTv = (TextView)view.findViewById(R.id.tv_user_name);
//            viewgroup.topicTimeTv = (TextView)view.findViewById(R.id.tv_topic_time);
//            viewgroup.topicLookTitleTv = (TextView)view.findViewById(R.id.tv_topic_play_title);
//            viewgroup.topicLiveMark = (TextView)view.findViewById(R.id.img_topic_live_mark);
//            viewgroup.topic_foot_ll = (LinearLayout)view.findViewById(R.id.ll_topic_foot);
//            viewgroup.tv_topic_device = (TextView)view.findViewById(R.id.tv_topic_device);
//            viewgroup.moreTopicTv = (TextView)view.findViewById(R.id.tv_topic_more);
//            viewgroup.fancyCoverFlow = (AutoCoverFlow)view.findViewById(R.id.fancyCoverFlow);
//            viewgroup.noHasCommentTv = (TextView)view.findViewById(R.id.tv_no_has_comment);
//            viewgroup.commentsFl = (FrameLayout)view.findViewById(R.id.fl_comments);
//            viewgroup.videoMarkImg = (ImageView)view.findViewById(R.id.img_topic_video_mark);
//            viewgroup.switchCommentImg = (TextView)view.findViewById(R.id.img_topic_switch_comment);
//            viewgroup.is_redpaket_iv = (ImageView)view.findViewById(R.id.img_topic_switch_isredpacket);
//            viewgroup.tv_topic_detail = (TextViewFixTouchConsume)view.findViewById(R.id.tv_topic_detail);
//            viewgroup.rl_huati_item = (RelativeLayout)view.findViewById(R.id.rl_huati_item);
//            viewgroup.iv_huati_poi = (ImageView)view.findViewById(R.id.iv_huati_poi);
//            viewgroup.tv_huati_poi = (TextView)view.findViewById(R.id.tv_huati_poi);
//            viewgroup.gd_huati_poi = (GridView)view.findViewById(R.id.gd_huati_poi);
//            viewgroup.gd_ll = (LinearLayout)view.findViewById(R.id.gd_ll);
//            view.setTag(viewgroup);
//            view1 = view;
//        } else
//        {
//            viewgroup = (ViewHolder)view.getTag();
//            view1 = view;
//        }
//        if (isTheme)
//        {
//            ((ViewHolder) (viewgroup)).toastImageView.setFragment(fragment);
//        }
//        if (topicinfo.getType().intValue() == 20)
//        {
//            ((ViewHolder) (viewgroup)).rl_huati_item.setVisibility(View.VISIBLE);
//            ((ViewHolder) (viewgroup)).iv_huati_poi.setVisibility(View.GONE);
//            ((ViewHolder) (viewgroup)).rl_topic_item.setVisibility(View.GONE);
//            Object obj = (new StringBuilder("#")).append(topicinfo.getIdtext()).toString();
//            String strCount = (new StringBuilder(String.valueOf(topicinfo.getNewcount()))).toString();
//            obj = (new StringBuilder("<font color=\"")).append("#53cbab").append("\">").append(((String) (obj))).append("      </font><font color=\"").append("#333333").append("\">").append(mContext.getResources().getString(R.string.home_huati_tip_1)).append("</font>").toString();
//            strCount = (new StringBuilder("<font color=\"")).append("#53cbab").append("\">").append(strCount).append("</font><font color=\"").append("#333333").append("\">").append(mContext.getResources().getString(R.string.home_huati_tip_2)).append("</font>").toString();
//            ((ViewHolder) (viewgroup)).tv_huati_poi.setText(Html.fromHtml((new StringBuilder(String.valueOf(obj))).append(view).toString()));
//            view = topicinfo.getTopiclist();
//            view = new HomeFollowTopicAdapter(mContext, view);
//            int j = view.getCount();
//            float f;
//            if (j % 2 == 0)
//            {
//                i = j / 2;
//            } else
//            {
//                i = j / 2 + 1;
//            }
//            f = (float)(UiUtils.getInstance(mContext).getmScreenWidth() - mContext.getResources().getDimensionPixelOffset(R.dimen.personal_top_margintop) * 3 - mContext.getResources().getDimensionPixelSize(R.dimen.home_head_icon_magin)) / 3.5F;
//            obj = new android.widget.FrameLayout.LayoutParams(-1, (int)(2.0F * f) + mContext.getResources().getDimensionPixelOffset(R.dimen.personal_top_margintop) * 3);
//            obj.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.home_head_icon_magin);
//            obj.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.home_head_icon_magin);
//            obj.gravity = 17;
//            ((ViewHolder) (viewgroup)).gd_ll.setLayoutParams(((android.view.ViewGroup.LayoutParams) (obj)));
//            obj = new android.widget.LinearLayout.LayoutParams((int)f * i + mContext.getResources().getDimensionPixelOffset(R.dimen.personal_top_margintop) * (i - 1) + mContext.getResources().getDimensionPixelOffset(R.dimen.home_head_icon_magin), -2);
//            ((ViewHolder) (viewgroup)).gd_huati_poi.setLayoutParams(((android.view.ViewGroup.LayoutParams) (obj)));
//            if (j <= 3)
//            {
//                ((ViewHolder) (viewgroup)).gd_huati_poi.setNumColumns(j);
//            } else
//            {
//                ((ViewHolder) (viewgroup)).gd_huati_poi.setNumColumns(i);
//            }
//            ((ViewHolder) (viewgroup)).gd_huati_poi.setAdapter(view);
//            ((ViewHolder) (viewgroup)).gd_huati_poi.setTag(topicinfo.getTopiclist());
//            ((ViewHolder) (viewgroup)).gd_huati_poi.setOnItemClickListener(onItemClickListener);
//            ((ViewHolder) (viewgroup)).rl_huati_item.setTag(topicinfo);
//            ((ViewHolder) (viewgroup)).rl_huati_item.setOnClickListener(this);
//            return view1;
//        }
//        if (topicinfo.getType().intValue() == 21)
//        {
//            ((ViewHolder) (viewgroup)).rl_huati_item.setVisibility(View.VISIBLE);
//            ((ViewHolder) (viewgroup)).iv_huati_poi.setVisibility(View.VISIBLE);
//            ((ViewHolder) (viewgroup)).rl_topic_item.setVisibility(View.GONE);
//            Object obj1 = topicinfo.getIdtext();
//            view = (new StringBuilder(String.valueOf(topicinfo.getNewcount()))).toString();
//            obj1 = (new StringBuilder("<font color=\"")).append("#53cbab").append("\">").append(((String) (obj1))).append("      </font><font color=\"").append("#333333").append("\">").append(mContext.getResources().getString(R.string.home_huati_tip_1)).append("</font>").toString();
//            view = (new StringBuilder("<font color=\"")).append("#53cbab").append("\">").append(view).append("</font><font color=\"").append("#333333").append("\">").append(mContext.getResources().getString(R.string.home_huati_tip_2)).append("</font>").toString();
//            ((ViewHolder) (viewgroup)).tv_huati_poi.setText(Html.fromHtml((new StringBuilder(String.valueOf(obj1))).append(view).toString()));
//            view = topicinfo.getTopiclist();
//            view = new HomeFollowTopicAdapter(mContext, view);
//            int k = view.getCount();
//            float f1;
//            if (k % 2 == 0)
//            {
//                i = k / 2;
//            } else
//            {
//                i = k / 2 + 1;
//            }
//            f1 = (float)(UiUtils.getInstance(mContext).getmScreenWidth() - mContext.getResources().getDimensionPixelOffset(R.dimen.personal_top_margintop) * 3 - mContext.getResources().getDimensionPixelSize(R.dimen.home_head_icon_magin)) / 3.5F;
//            obj1 = new android.widget.FrameLayout.LayoutParams(-1, (int)(2.0F * f1) + mContext.getResources().getDimensionPixelOffset(R.dimen.personal_top_margintop) * 3);
//            obj1.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.home_head_icon_magin);
//            obj1.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.home_head_icon_magin);
//            obj1.gravity = 17;
//            ((ViewHolder) (viewgroup)).gd_ll.setLayoutParams(((android.view.ViewGroup.LayoutParams) (obj1)));
//            obj1 = new android.widget.LinearLayout.LayoutParams((int)f1 * i + mContext.getResources().getDimensionPixelOffset(R.dimen.personal_top_margintop) * (i - 1) + mContext.getResources().getDimensionPixelOffset(R.dimen.home_head_icon_magin), -2);
//            ((ViewHolder) (viewgroup)).gd_huati_poi.setLayoutParams(((android.view.ViewGroup.LayoutParams) (obj1)));
//            if (k <= 3)
//            {
//                ((ViewHolder) (viewgroup)).gd_huati_poi.setNumColumns(k);
//            } else
//            {
//                ((ViewHolder) (viewgroup)).gd_huati_poi.setNumColumns(i);
//            }
//            ((ViewHolder) (viewgroup)).gd_huati_poi.setAdapter(view);
//            ((ViewHolder) (viewgroup)).gd_huati_poi.setTag(topicinfo.getTopiclist());
//            ((ViewHolder) (viewgroup)).gd_huati_poi.setOnItemClickListener(onItemClickListener);
//            ((ViewHolder) (viewgroup)).rl_huati_item.setTag(topicinfo);
//            ((ViewHolder) (viewgroup)).rl_huati_item.setOnClickListener(this);
//            return view1;
//        }
//        ((ViewHolder) (viewgroup)).rl_topic_item.setVisibility(View.VISIBLE);
//        ((ViewHolder) (viewgroup)).rl_huati_item.setVisibility(View.GONE);
//        if (topicinfo.getFromrepost() == null)
//        {
//            topicinfo.setFromrepost("0");
//        }
//        int l;
//        boolean flag1;
//        if (Integer.parseInt(topicinfo.getFromrepost()) == 0)
//        {
//            ((ViewHolder) (viewgroup)).ll_forward.setVisibility(View.GONE);
//        } else
//        {
//            ((ViewHolder) (viewgroup)).ll_forward.setVisibility(View.VISIBLE);
//            RepostUserInfo repostuserinfo = topicinfo.getRepostuserinfo();
//            if (repostuserinfo != null)
//            {
//                l = repostuserinfo.getTotal();
//                Object obj5 = repostuserinfo.getUserlist();
//                if (l == 1)
//                {
//                    RepostUserList repostuserlist = (RepostUserList)((ArrayList) (obj5)).get(0);
//                    view = repostuserlist.getRemarkname();
//                    if (view == null || "".equals(view))
//                    {
//                        view = repostuserlist.getNickname();
//                    }
//                    view = (new StringBuilder("<font color=\"")).append("#78BAA6").append("\">").append(view).append("</font><font color=\"").append("#999999").append("\">").append(mContext.getResources().getString(0x7f0701ee)).append("</font>").toString();
//                    ((ViewHolder) (viewgroup)).tv_forword_name_1.setText(Html.fromHtml(view));
//                    ((ViewHolder) (viewgroup)).tv_forword_name_1.setTag(R.id.forword_user_id_1, repostuserlist.getUid());
//                    ((ViewHolder) (viewgroup)).tv_forword_name_1.setOnClickListener(this);
//                    ((ViewHolder) (viewgroup)).tv_forword_name_1.setVisibility(View.VISIBLE);
//                    ((ViewHolder) (viewgroup)).tv_forword.setText(null);
//                    ((ViewHolder) (viewgroup)).tv_forword_name_2.setVisibility(View.INVISIBLE);
//                    ((ViewHolder) (viewgroup)).tv_forword_name_3.setVisibility(View.INVISIBLE);
//                } else
//                {
//                    if (obj5 != null && ((ArrayList) (obj5)).size() == 1)
//                    {
//                        RepostUserList repostuserlist1 = (RepostUserList)((ArrayList) (obj5)).get(0);
//                        view = repostuserlist1.getRemarkname();
//                        if (view == null || "".equals(view))
//                        {
//                            view = repostuserlist1.getNickname();
//                        }
//                        view = (new StringBuilder("<font color=\"")).append("#78BAA6").append("\">").append(view).append("</font>").toString();
//                        ((ViewHolder) (viewgroup)).tv_forword_name_1.setText(Html.fromHtml(view));
//                        ((ViewHolder) (viewgroup)).tv_forword_name_1.setTag(R.id.forword_user_id_1, repostuserlist1.getUid());
//                        ((ViewHolder) (viewgroup)).tv_forword_name_1.setOnClickListener(this);
//                        ((ViewHolder) (viewgroup)).tv_forword_name_1.setVisibility(View.VISIBLE);
//                        ((ViewHolder) (viewgroup)).tv_forword_name_2.setVisibility(View.INVISIBLE);
//                        ((ViewHolder) (viewgroup)).tv_forword_name_3.setVisibility(View.INVISIBLE);
//                    } else
//                    if (obj5 != null && ((ArrayList) (obj5)).size() == 2)
//                    {
//                        RepostUserList repostuserlist2 = (RepostUserList)((ArrayList) (obj5)).get(0);
//                        view = repostuserlist2.getRemarkname();
//                        Object obj3;
//                        if (view == null || "".equals(view))
//                        {
//                            view = repostuserlist2.getNickname();
//                        }
//                        obj3 = view;
//                        if (view.length() > 8)
//                        {
//                            obj3 = (new StringBuilder(String.valueOf(view.substring(0, 7)))).append("...").toString();
//                        }
//                        view = (new StringBuilder("<font color=\"")).append("#78BAA6").append("\">").append(((String) (obj3))).append("</font>").toString();
//                        ((ViewHolder) (viewgroup)).tv_forword_name_1.setText(Html.fromHtml(view));
//                        ((ViewHolder) (viewgroup)).tv_forword_name_1.setTag(R.id.forword_user_id_1, repostuserlist2.getUid());
//                        ((ViewHolder) (viewgroup)).tv_forword_name_1.setOnClickListener(this);
//                        ((ViewHolder) (viewgroup)).tv_forword_name_1.setVisibility(View.VISIBLE);
//                        obj5 = (RepostUserList)((ArrayList) (obj5)).get(1);
//                        view = ((RepostUserList) (obj5)).getRemarkname();
//                        if (view != null && !"".equals(view))
//                        {
//                            view = (new StringBuilder("\u3001")).append(view).toString();
//                        } else
//                        {
//                            view = (new StringBuilder("\u3001")).append(((RepostUserList) (obj5)).getNickname()).toString();
//                        }
//                        obj3 = view;
//                        if (view.length() > 9)
//                        {
//                            obj3 = (new StringBuilder(String.valueOf(view.substring(0, 8)))).append("...").toString();
//                        }
//                        view = (new StringBuilder("<font color=\"")).append("#78BAA6").append("\">").append(((String) (obj3))).append("</font>").toString();
//                        ((ViewHolder) (viewgroup)).tv_forword_name_2.setText(Html.fromHtml(view));
//                        ((ViewHolder) (viewgroup)).tv_forword_name_2.setTag(R.id.forword_user_id_2, ((RepostUserList) (obj5)).getUid());
//                        ((ViewHolder) (viewgroup)).tv_forword_name_2.setOnClickListener(this);
//                        ((ViewHolder) (viewgroup)).tv_forword_name_2.setVisibility(View.VISIBLE);
//                        ((ViewHolder) (viewgroup)).tv_forword_name_3.setVisibility(View.INVISIBLE);
//                    } else
//                    if (obj5 != null && ((ArrayList) (obj5)).size() == 3)
//                    {
//                        RepostUserList repostuserlist3 = (RepostUserList)((ArrayList) (obj5)).get(0);
//                        view = repostuserlist3.getRemarkname();
//                        Object obj4;
//                        if (view == null || "".equals(view))
//                        {
//                            view = repostuserlist3.getNickname();
//                        }
//                        obj4 = view;
//                        if (view.length() > 5)
//                        {
//                            obj4 = (new StringBuilder(String.valueOf(view.substring(0, 4)))).append("...").toString();
//                        }
//                        view = (new StringBuilder("<font color=\"")).append("#78BAA6").append("\">").append(((String) (obj4))).append("</font>").toString();
//                        ((ViewHolder) (viewgroup)).tv_forword_name_1.setText(Html.fromHtml(view));
//                        ((ViewHolder) (viewgroup)).tv_forword_name_1.setTag(R.id.forword_user_id_1, repostuserlist3.getUid());
//                        ((ViewHolder) (viewgroup)).tv_forword_name_1.setOnClickListener(this);
//                        ((ViewHolder) (viewgroup)).tv_forword_name_1.setVisibility(View.VISIBLE);
//                        repostuserlist3 = (RepostUserList)((ArrayList) (obj5)).get(1);
//                        view = repostuserlist3.getRemarkname();
//                        if (view != null && !"".equals(view))
//                        {
//                            view = (new StringBuilder("\u3001")).append(view).toString();
//                        } else
//                        {
//                            view = (new StringBuilder("\u3001")).append(repostuserlist3.getNickname()).toString();
//                        }
//                        obj4 = view;
//                        if (view.length() > 6)
//                        {
//                            obj4 = (new StringBuilder(String.valueOf(view.substring(0, 5)))).append("...").toString();
//                        }
//                        view = (new StringBuilder("<font color=\"")).append("#78BAA6").append("\">").append(((String) (obj4))).append("</font>").toString();
//                        ((ViewHolder) (viewgroup)).tv_forword_name_2.setText(Html.fromHtml(view));
//                        ((ViewHolder) (viewgroup)).tv_forword_name_2.setTag(R.id.forword_user_id_2, repostuserlist3.getUid());
//                        ((ViewHolder) (viewgroup)).tv_forword_name_2.setOnClickListener(this);
//                        ((ViewHolder) (viewgroup)).tv_forword_name_2.setVisibility(View.VISIBLE);
//                        obj5 = (RepostUserList)((ArrayList) (obj5)).get(2);
//                        view = ((RepostUserList) (obj5)).getRemarkname();
//                        if (view != null && !"".equals(view))
//                        {
//                            view = (new StringBuilder("\u3001")).append(view).toString();
//                        } else
//                        {
//                            view = (new StringBuilder("\u3001")).append(((RepostUserList) (obj5)).getNickname()).toString();
//                        }
//                        obj4 = view;
//                        if (view.length() > 6)
//                        {
//                            obj4 = (new StringBuilder(String.valueOf(view.substring(0, 5)))).append("...").toString();
//                        }
//                        view = (new StringBuilder("<font color=\"")).append("#78BAA6").append("\">").append(((String) (obj4))).append("</font>").toString();
//                        ((ViewHolder) (viewgroup)).tv_forword_name_3.setText(Html.fromHtml(view));
//                        ((ViewHolder) (viewgroup)).tv_forword_name_3.setTag(R.id.forword_user_id_3, ((RepostUserList) (obj5)).getUid());
//                        ((ViewHolder) (viewgroup)).tv_forword_name_3.setOnClickListener(this);
//                        ((ViewHolder) (viewgroup)).tv_forword_name_3.setVisibility(View.VISIBLE);
//                    }
//                    view = (new StringBuilder(String.valueOf(repostuserinfo.getTotal()))).toString();
//                    if (repostuserinfo.getTotal() > 3)
//                    {
//                        view = (new StringBuilder("<font color=\"")).append("#999999").append("\">").append(mContext.getResources().getString(R.string.home_forward_tip_1)).append("</font><font color=\"").append("#78BAA6").append("\">").append(view).append("</font><font color=\"").append("#999999").append("\">").append(mContext.getResources().getString(0x7f0701ed)).append("</font>").toString();
//                    } else
//                    {
//                        view = (new StringBuilder(String.valueOf(mContext.getResources().getString(R.id.home_forward_tip_3)))).append("</font>").toString();
//                    }
//                    ((ViewHolder) (viewgroup)).tv_forword.setText(Html.fromHtml(view));
//                }
//            }
//        }
//        if (!mIsUseRealTime && topicinfo.getFormattime() != null)
//        {
//            ((ViewHolder) (viewgroup)).topicTimeTv.setText(topicinfo.getFormattime());
//        } else
//        if (mIsUseRealTime && topicinfo.getAddtime() != null)
//        {
//            try
//            {
//                view = dateFormat.parse(topicinfo.getAddtime());
//                ((ViewHolder) (viewgroup)).topicTimeTv.setText(TimeUtils.getShowTimeStringefore(mContext, view, "MM-dd HH:mm"));
//            }
//            // Misplaced declaration of an exception variable
//            catch (View view)
//            {
//                ((ViewHolder) (viewgroup)).topicTimeTv.setText(mContext.getString(R.string.comment_now_time));
//            }
//        } else
//        {
//            ((ViewHolder) (viewgroup)).topicTimeTv.setText(mContext.getString(R.string.comment_now_time));
//        }
//        view = topicinfo.getIskana();
//        if (view != null && !"".equals(view))
//        {
//            if (Integer.valueOf(view).intValue() == 0)
//            {
//                view = topicinfo.getUserinfo();
//                if (view != null && !"".equals(view))
//                {
//                    Object obj2 = view.getRemarkname();
//                    TutuUsers tutuusers;
//                    boolean flag;
//                    if (obj2 != null && !"".equals(obj2))
//                    {
//                        ((ViewHolder) (viewgroup)).userNameTv.setText(((CharSequence) (obj2)));
//                    } else
//                    {
//                        ((ViewHolder) (viewgroup)).userNameTv.setText(view.getNickname());
//                    }
//                    obj2 = ImageUtils.getAvatarUrl(view.getUid(), view.getAvatartime());
//                    ImageLoader.getInstance().displayImage(((String) (obj2)), ((ViewHolder) (viewgroup)).iv_user_icon, Constant.AVATAR_OPTIONS);
//                    if (view.getUserhonorlevel() == null)
//                    {
//                        view.setUserhonorlevel(Integer.valueOf(0));
//                    }
//                    l = view.getUserhonorlevel().intValue();
//                    if (l <= 0)
//                    {
//                        ((ViewHolder) (viewgroup)).iv_user_level.setVisibility(View.INVISIBLE);
//                    } else
//                    {
//                        ((ViewHolder) (viewgroup)).iv_user_level.setVisibility(View.VISIBLE);
//                        String s = ImageUtils.getLevelUrl((new StringBuilder(String.valueOf(l))).toString());
//                        ImageLoader.getInstance().displayImage(s, ((ViewHolder) (viewgroup)).iv_user_level, Constant.LEVEL_OPTIONS);
//                    }
//                    obj2 = topicinfo.getUid();
//                    tutuusers = MyApplication.getInstance().getUserinfo();
//                    if (tutuusers != null && ((String) (obj2)).equals(tutuusers.getUid()) || topicinfo.getIskana() != null && topicinfo.getIskana().equals("1"))
//                    {
//                        ((ViewHolder) (viewgroup)).user_follow_status.setVisibility(View.INVISIBLE);
//                    } else
//                    {
//                        ((ViewHolder) (viewgroup)).user_follow_status.setVisibility(View.VISIBLE);
//                        String s1 = view.getRelation();
//                        view = view.getIsauth();
//                        if (s1 != null && ("0".equals(s1) || "1".equals(s1)))
//                        {
//                            if (view != null && Integer.parseInt(view) == 1)
//                            {
//                                ((ViewHolder) (viewgroup)).user_follow_status.setImageResource(R.drawable.user_follow_status_auth_0);
//                            } else
//                            if (view != null && Integer.parseInt(view) == 2)
//                            {
//                                ((ViewHolder) (viewgroup)).user_follow_status.setImageResource(R.drawable.user_follow_status_daren_0);
//                            } else
//                            {
//                                ((ViewHolder) (viewgroup)).user_follow_status.setImageResource(R.drawable.user_follow_status_0);
//                            }
//                        } else
//                        if (s1 != null && "2".equals(s1))
//                        {
//                            if (view != null && Integer.parseInt(view) == 1)
//                            {
//                                ((ViewHolder) (viewgroup)).user_follow_status.setImageResource(R.drawable.user_follow_status_auth_1);
//                            } else
//                            if (view != null && Integer.parseInt(view) == 2)
//                            {
//                                ((ViewHolder) (viewgroup)).user_follow_status.setImageResource(R.drawable.user_follow_status_daren_1);
//                            } else
//                            {
//                                ((ViewHolder) (viewgroup)).user_follow_status.setImageResource(R.drawable.user_follow_status_1);
//                            }
//                        } else
//                        if (s1 != null && "3".equals(s1))
//                        {
//                            if (view != null && Integer.parseInt(view) == 1)
//                            {
//                                ((ViewHolder) (viewgroup)).user_follow_status.setImageResource(R.drawable.user_follow_status_auth_2);
//                            } else
//                            if (view != null && Integer.parseInt(view) == 2)
//                            {
//                                ((ViewHolder) (viewgroup)).user_follow_status.setImageResource(R.drawable.user_follow_status_daren_2);
//                            } else
//                            {
//                                ((ViewHolder) (viewgroup)).user_follow_status.setImageResource(R.drawable.user_follow_status_2);
//                            }
//                        }
//                        ((ViewHolder) (viewgroup)).user_follow_status.setTag(0x7f0a0017, topicinfo);
//                        ((ViewHolder) (viewgroup)).user_follow_status.setOnClickListener(this);
//                        if (isTopicPagerActivity == 1)
//                        {
//                            TouchUtil.setDelegate((TopicDetailPagerActivity)mContext, ((ViewHolder) (viewgroup)).user_follow_status);
//                        } else
//                        if (isTopicPagerActivity == 2)
//                        {
//                            TouchUtil.setDelegate((TopicDetailActivity)mContext, ((ViewHolder) (viewgroup)).user_follow_status);
//                        } else
//                        if (isFromPersonal)
//                        {
//                            TouchUtil.setDelegate((PersonalNewActivity)mContext, ((ViewHolder) (viewgroup)).user_follow_status);
//                        } else
//                        {
//                            TouchUtil.setDelegate((HomeFragmentActivity)mContext, ((ViewHolder) (viewgroup)).user_follow_status);
//                        }
//                    }
//                } else
//                {
//                    ((ViewHolder) (viewgroup)).iv_user_level.setVisibility(View.INVISIBLE);
//                    ((ViewHolder) (viewgroup)).user_follow_status.setVisibility(View.INVISIBLE);
//                }
//            } else
//            if (Integer.valueOf(view).intValue() == 1)
//            {
//                ((ViewHolder) (viewgroup)).iv_user_icon.setImageResource(R.drawable.topic_no_name);
//                ((ViewHolder) (viewgroup)).userNameTv.setText(mContext.getString(R.string.no_name_tip));
//                ((ViewHolder) (viewgroup)).userNameTv.setTextColor(mContext.getResources().getColor(R.color.no_name_color));
//                ((ViewHolder) (viewgroup)).iv_user_level.setVisibility(View.INVISIBLE);
//                ((ViewHolder) (viewgroup)).user_follow_status.setVisibility(View.INVISIBLE);
//            }
//        } else
//        {
//            view = topicinfo.getUserinfo();
//            if (view != null)
//            {
//                String s2 = view.getRemarkname();
//                if (s2 != null && !"".equals(s2))
//                {
//                    ((ViewHolder) (viewgroup)).userNameTv.setText(s2);
//                } else
//                {
//                    ((ViewHolder) (viewgroup)).userNameTv.setText(topicinfo.getUserinfo().getNickname());
//                }
//                view = ImageUtils.getAvatarUrl(view.getUid(), view.getAvatartime());
//                if (view != null && !"".equals(view))
//                {
//                    ImageLoader.getInstance().displayImage(view, ((ViewHolder) (viewgroup)).iv_user_icon, Constant.AVATAR_OPTIONS);
//                } else
//                {
//                    ((ViewHolder) (viewgroup)).iv_user_icon.setImageResource(R.drawable.img_personal_info_selector);
//                }
//            }
//        }
//        ((ViewHolder) (viewgroup)).iv_user_icon.setTag(topicinfo);
//        ((ViewHolder) (viewgroup)).iv_user_icon.setOnClickListener(onClickListener);
//        view = topicinfo.getDesc();
//        if (view != null && !"".equals(view))
//        {
//            ((ViewHolder) (viewgroup)).tv_topic_detail.setText(TextUtil.formatContent(view, mContext));
//            ((ViewHolder) (viewgroup)).tv_topic_detail.setMovementMethod(com.gitrose.mobile.view.TextViewFixTouchConsume.LocalLinkMovementMethod.getInstance());
//            if (!isTopicDetail)
//            {
//                ((ViewHolder) (viewgroup)).tv_topic_detail.setMaxLines(6);
//                ((ViewHolder) (viewgroup)).tv_topic_detail.post(new Run(((ViewHolder) (viewgroup)).tv_topic_detail, view, ((ViewHolder) (viewgroup)).moreTopicTv, i));
//            } else
//            {
//                ((ViewHolder) (viewgroup)).tv_topic_detail.setVisibility(View.VISIBLE);
//                ((ViewHolder) (viewgroup)).moreTopicTv.setVisibility(View.GONE);
//            }
//        } else
//        {
//            ((ViewHolder) (viewgroup)).tv_topic_detail.setVisibility(View.GONE);
//            ((ViewHolder) (viewgroup)).moreTopicTv.setVisibility(View.GONE);
//        }
//        view = topicinfo.getPoitext();
//        if (view != null && !"".equals(view))
//        {
//            ((ViewHolder) (viewgroup)).locationTv.setText(view);
//            ((ViewHolder) (viewgroup)).locationTv.setVisibility(View.VISIBLE);
//        } else
//        {
//            ((ViewHolder) (viewgroup)).locationTv.setVisibility(View.GONE);
//        }
//        view = topicinfo.getViews();
//        if (view == null)
//        {
//            ((ViewHolder) (viewgroup)).playCountTv.setText("0");
//        } else
//        {
//            ((ViewHolder) (viewgroup)).playCountTv.setText((new StringBuilder()).append(view).toString());
//        }
//        l = topicinfo.getLikenum();
//        obj2 = ((ViewHolder) (viewgroup)).tv_home_zan;
//        if (l == 0)
//        {
//            view = mContext.getString(R.string.home_zan_tip);
//        } else
//        {
//            view = (new StringBuilder(String.valueOf(l))).toString();
//        }
//        ((TextView) (obj2)).setText(view);
//        view = ((ViewHolder) (viewgroup)).tv_home_zan;
//        if (topicinfo.getIslike() == 1)
//        {
//            flag1 = true;
//        } else
//        {
//            flag1 = false;
//        }
//        view.setSelected(flag1);
//        l = topicinfo.getRepostnum();
//        obj2 = ((ViewHolder) (viewgroup)).tv_home_forward;
//        if (l == 0)
//        {
//            view = mContext.getString(R.string.home_forward_tip);
//        } else
//        {
//            view = (new StringBuilder(String.valueOf(l))).toString();
//        }
//        ((TextView) (obj2)).setText(view);
//        view = ((ViewHolder) (viewgroup)).tv_home_forward;
//        if (topicinfo.getUserisrepost() == 1)
//        {
//            flag1 = true;
//        } else
//        {
//            flag1 = false;
//        }
//        view.setSelected(flag1);
//        l = topicinfo.getTotalcomment();
//        obj2 = ((ViewHolder) (viewgroup)).tv_home_comment;
//        if (l == 0)
//        {
//            view = mContext.getString(R.string.comment_tip);
//        } else
//        {
//            view = (new StringBuilder(String.valueOf(l))).toString();
//        }
//        ((TextView) (obj2)).setText(view);
//        if (topicinfo.getType().intValue() != 5)
//        {
//            ((ViewHolder) (viewgroup)).videoMarkImg.setVisibility(View.GONE);
//            ((ViewHolder) (viewgroup)).switchCommentImg.setVisibility(View.GONE);
//        } else
//        {
//            ((ViewHolder) (viewgroup)).videoMarkImg.setVisibility(View.GONE);
//            ((ViewHolder) (viewgroup)).switchCommentImg.setVisibility(View.VISIBLE);
//            ((ViewHolder) (viewgroup)).switchCommentImg.setSelected(false);
//            ((ViewHolder) (viewgroup)).switchCommentImg.setText(mContext.getString(R.string.topic_comment_no_see_str));
//            ((ViewHolder) (viewgroup)).switchCommentImg.setOnClickListener(this);
//        }
//        if (topicinfo.getType().intValue() == 2)
//        {
//            ((ViewHolder) (viewgroup)).topicLookTitleTv.setText(mContext.getResources().getString(R.string.topic_play_num_live));
//            ((ViewHolder) (viewgroup)).topicLiveMark.setVisibility(View.VISIBLE);
//            ((ViewHolder) (viewgroup)).topicLiveMark.setOnClickListener(this);
//            ((ViewHolder) (viewgroup)).topicLiveMark.setTag(topicinfo);
//            ((ViewHolder) (viewgroup)).commentsFl.setVisibility(View.GONE);
//            ((ViewHolder) (viewgroup)).topic_foot_ll.setVisibility(View.GONE);
//        } else
//        {
//            ((ViewHolder) (viewgroup)).topicLookTitleTv.setText(mContext.getResources().getString(R.string.topic_play_num));
//            ((ViewHolder) (viewgroup)).topicLiveMark.setVisibility(View.GONE);
//            ((ViewHolder) (viewgroup)).commentsFl.setVisibility(View.VISIBLE);
//            ((ViewHolder) (viewgroup)).topic_foot_ll.setVisibility(View.VISIBLE);
//        }
//        flag = false;
//        l = ((flag) ? 1 : 0);
//        if (topicinfo.getIsredpacket() != null)
//        {
//            l = ((flag) ? 1 : 0);
//            if (!topicinfo.getIsredpacket().equals(""))
//            {
//                l = Integer.parseInt(topicinfo.getIsredpacket());
//            }
//        }
//        if (l == 1)
//        {
//            ((ViewHolder) (viewgroup)).is_redpaket_iv.setVisibility(View.VISIBLE);
//            ((ViewHolder) (viewgroup)).is_redpaket_iv.setTag(Integer.valueOf(i));
//            ((ViewHolder) (viewgroup)).is_redpaket_iv.setTag(R.id.topic_red_packet_topicinfo, topicinfo);
//            ((ViewHolder) (viewgroup)).is_redpaket_iv.setTag(R.id.topic_red_packet_zan_tv, ((ViewHolder) (viewgroup)).tv_home_zan);
//            ((ViewHolder) (viewgroup)).is_redpaket_iv.setOnClickListener(this);
//        } else
//        {
//            ((ViewHolder) (viewgroup)).is_redpaket_iv.setVisibility(View.GONE);
//        }
//        clearOperationButtons(((ViewHolder) (viewgroup)).commentsFl);
//        if (topicinfo.getShowtype() == 0)
//        {
//            ((ViewHolder) (viewgroup)).tv_home_comment.setVisibility(View.VISIBLE);
//            ((ViewHolder) (viewgroup)).tv_home_more.setVisibility(View.VISIBLE);
//            if (topicinfo.getCommentlist() == null || topicinfo.getCommentlist().size() == 0)
//            {
//                obj2 = ((ViewHolder) (viewgroup)).noHasCommentTv;
//                if (topicinfo.getEmptycommenttxt() != null)
//                {
//                    view = topicinfo.getEmptycommenttxt();
//                } else
//                {
//                    view = mContext.getString(R.string.comment_no_data_tip);
//                }
//                ((TextView) (obj2)).setText(view);
//                ((ViewHolder) (viewgroup)).fancyCoverFlow.setVisibility(View.INVISIBLE);
//                ((ViewHolder) (viewgroup)).noHasCommentTv.setVisibility(View.VISIBLE);
//                ((ViewHolder) (viewgroup)).noHasCommentTv.setOnClickListener(null);
//            } else
//            {
//                ((ViewHolder) (viewgroup)).fancyCoverFlow.setVisibility(View.VISIBLE);
//                ((ViewHolder) (viewgroup)).noHasCommentTv.setVisibility(View.INVISIBLE);
//                ((ViewHolder) (viewgroup)).fancyCoverFlow.setAdapter(new AutoCoverFlowAdapter(mContext, topicinfo.getCommentlist()));
//                ((ViewHolder) (viewgroup)).fancyCoverFlow.setTag(R.id.fancy_cover_flow_pos, Integer.valueOf(i));
//                ((ViewHolder) (viewgroup)).fancyCoverFlow.setSelection(0);
//                ((ViewHolder) (viewgroup)).fancyCoverFlow.setOnItemClickListener(mAvatarOnItemClickListener);
//                ((ViewHolder) (viewgroup)).fancyCoverFlow.setOnItemSelectedListener(mAvatarOnItemSelectedListener);
//            }
//        } else
//        {
//            ((ViewHolder) (viewgroup)).fancyCoverFlow.setVisibility(View.INVISIBLE);
//            ((ViewHolder) (viewgroup)).noHasCommentTv.setVisibility(View.INVISIBLE);
//            ((ViewHolder) (viewgroup)).tv_home_comment.setVisibility(View.GONE);
//            ((ViewHolder) (viewgroup)).tv_home_more.setVisibility(View.GONE);
//            addOperationBtns(topicinfo, ((ViewHolder) (viewgroup)).commentsFl);
//        }
//        ((ViewHolder) (viewgroup)).toastImageView.fillData(i, topicinfo);
//        ((ViewHolder) (viewgroup)).userNameTv.setTag(topicinfo);
//        ((ViewHolder) (viewgroup)).userNameTv.setOnClickListener(this);
//        ((ViewHolder) (viewgroup)).tv_home_more.setTag(topicinfo);
//        ((ViewHolder) (viewgroup)).tv_home_comment.setTag(Integer.valueOf(i));
//        ((ViewHolder) (viewgroup)).tv_home_zan.setTag(R.id.topic_fav_topicinfo, topicinfo);
//        ((ViewHolder) (viewgroup)).tv_home_zan.setOnClickListener(this);
//        ((ViewHolder) (viewgroup)).tv_home_forward.setTag(R.id.topic_repost_topicinfo, topicinfo);
//        ((ViewHolder) (viewgroup)).tv_home_forward.setOnClickListener(this);
//        ((ViewHolder) (viewgroup)).locationTv.setTag(topicinfo);
//        ((ViewHolder) (viewgroup)).locationTv.setOnClickListener(this);
//        if (!isTopicDetail)
//        {
//            ((ViewHolder) (viewgroup)).tv_home_comment.setOnClickListener(this);
//        } else
//        {
//            ((ViewHolder) (viewgroup)).tv_home_comment.setOnClickListener(onClickListener);
//        }
//        ((ViewHolder) (viewgroup)).tv_home_more.setOnClickListener(onClickListener);
//        return view1;
//    }
//
//    final void clearOperationButtons(FrameLayout commentFl) {
//        if (commentFl.getChildCount() > SELECT_COMMENT_WHAT) {
//            commentFl.removeViews(SELECT_COMMENT_WHAT, commentFl.getChildCount() - 2);
//        }
//    }
//
//    private void addOperationBtns(TopicInfo topicInfo, FrameLayout commentFl) {
//        Operation operation = topicInfo.getSpecialdata();
//        if (operation != null) {
//            List<OperationButton> operationButtons = operation.getButtonlist();
//            int btnHeight = (int) this.mContext.getResources().getDimension(R.dimen.login_bt_height);
//            int btnMargin = (int) this.mContext.getResources().getDimension(R.dimen.home_item_operation_margin);
//            int size = operationButtons.size();
//            OperationButton operationButton;
//            LayoutParams btnLayoutParams;
//            if (size == 1) {
//                operationButton = (OperationButton) operationButtons.get(0);
//                btnLayoutParams = new LayoutParams(-1, btnHeight);
//                btnLayoutParams.setMargins(btnMargin, 0, btnMargin, btnMargin);
//                addOperationButton(commentFl, operationButton, R.drawable.login_button, R.color.setting_title_text_color, btnLayoutParams);
//            } else if (size > 1) {
//                int btnWdith = (UiUtils.getInstance(this.mContext).getmScreenWidth() - (btnMargin * 3)) / SELECT_COMMENT_WHAT;
//                operationButton = (OperationButton) operationButtons.get(0);
//                btnLayoutParams = new LayoutParams(btnWdith, btnHeight);
//                btnLayoutParams.setMargins(btnMargin, 0, btnMargin, btnMargin);
//                addOperationButton(commentFl, operationButton, R.drawable.login_button, R.color.setting_title_text_color, btnLayoutParams);
//                OperationButton operationButton1 = (OperationButton) operationButtons.get(1);
//                LayoutParams btnLayoutParams1 = new LayoutParams(btnWdith, btnHeight);
//                btnLayoutParams1.setMargins((btnMargin * SELECT_COMMENT_WHAT) + btnWdith, 0, btnMargin, btnMargin);
//                addOperationButton(commentFl, operationButton1, R.drawable.grey_button, R.color.white, btnLayoutParams1);
//            }
//        }
//    }
//
//    private void addOperationButton(FrameLayout commentFl, OperationButton operationButton, int background, int color, LayoutParams lp) {
//        Button button = new Button(this.mContext);
//        button.setTextColor(this.mContext.getResources().getColor(color));
//        button.setTextSize(SELECT_COMMENT_WHAT, 16.0f);
//        button.setTag(operationButton.getGotourl());
//        button.setBackgroundResource(background);
//        button.setOnClickListener(new C08423());
//        String btnText = operationButton.getButtontxt();
//        if (btnText != null) {
//            button.setText(btnText);
//        }
//        lp.gravity = 80;
//        commentFl.addView(button, lp);
//    }
//
//    public void notifyLastCommentPos(int listPosition) {
//        notifyCommentPos(listPosition, -1, null);
//    }
//
//    public void notifyLastCommentPos(int listPosition, TopicInfo mTopicInfo) {
//        notifyCommentPos(listPosition, -1, mTopicInfo);
//    }
//
//    public void notifyCommentPos(int listPosition, int commentPos, TopicInfo mTopicInfo) {
//        List<Comment> comments;
//        if (mTopicInfo == null || BaseConstants.ah.equals(mTopicInfo)) {
//            comments = ((TopicInfo) this.mTopicList.get(listPosition)).getCommentlist();
//        } else {
//            comments = mTopicInfo.getCommentlist();
//        }
//        if (comments != null) {
//            int commentSize = comments.size();
//            if (commentPos != -1) {
//                commentPos++;
//            } else {
//                commentPos = commentSize;
//            }
//            updateTopic(listPosition, commentPos, mTopicInfo);
//        }
//    }
//
//    public void updateTopic(int listPos) {
//        notifyDataSetChanged();
//    }
//
//    public void updateTopic(int listPos, int commentPos, TopicInfo mTopicInfo) {
//        View convertView = getConvertView(listPos);
//        if (convertView != null) {
//            TopicInfo topicInfo;
//            if (mTopicInfo == null || BaseConstants.ah.equals(mTopicInfo)) {
//                topicInfo = getItem(listPos);
//            } else {
//                topicInfo = mTopicInfo;
//            }
//            CommentImageView toastImageView = (CommentImageView) convertView.findViewById(R.id.toastView_topic);
//            TextView favTopicTv = (TextView) convertView.findViewById(R.id.tv_home_zan);
//            favTopicTv.setText(topicInfo.getLikenum() == 0 ? this.mContext.getString(R.string.home_zan_tip) : new StringBuilder(String.valueOf(topicInfo.getLikenum())).toString());
//            favTopicTv.setSelected(topicInfo.getIslike() == 1);
//            int totalComment = topicInfo.getTotalcomment();
//            ((TextView) convertView.findViewById(R.id.tv_home_comment)).setText(totalComment == 0 ? this.mContext.getString(R.string.comment_tip) : new StringBuilder(String.valueOf(totalComment)).toString());
//            AutoCoverFlow fancyCoverFlow = (AutoCoverFlow) convertView.findViewById(R.id.fancyCoverFlow);
//            TextView noHasCommentTv = (TextView) convertView.findViewById(R.id.tv_no_has_comment);
//            if (topicInfo.getCommentlist().size() == 0) {
//                toastImageView.getChildAt(3).setVisibility(View.INVISIBLE);
//                fancyCoverFlow.setVisibility(View.INVISIBLE);
//                noHasCommentTv.setVisibility(View.VISIBLE);
//                noHasCommentTv.setOnClickListener(null);
//                return;
//            }
//            toastImageView.getChildAt(3).setVisibility(View.VISIBLE);
//            fancyCoverFlow.setVisibility(View.VISIBLE);
//            noHasCommentTv.setVisibility(View.INVISIBLE);
//            fancyCoverFlow.setAdapter(new AutoCoverFlowAdapter(this.mContext, topicInfo.getCommentlist()));
//            fancyCoverFlow.setTag(R.id.fancy_cover_flow_pos, Integer.valueOf(listPos));
//            fancyCoverFlow.setTag(R.id.fancy_cover_flow_topicinfo, topicInfo);
//            fancyCoverFlow.setOnItemClickListener(this.mAvatarOnItemClickListener);
//            fancyCoverFlow.setOnItemSelectedListener(this.mAvatarOnItemSelectedListener);
//            if (commentPos != -1) {
//                fancyCoverFlow.postDelayed(new C08434(fancyCoverFlow, commentPos), 100);
//            }
//        }

        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateTopicFromNetWork(int r65, com.gitrose.greendao.TopicInfo r66, int r67, android.view.View.OnClickListener r68) {
        /*
        r64 = this;
        r5 = r64.getConvertView(r65);
        if (r5 == 0) goto L_0x02ec;
    L_0x0006:
        if (r66 == 0) goto L_0x02ec;
    L_0x0008:
        r61 = 2131363078; // 0x7f0a0506 float:1.8345955E38 double:1.0530332757E-314;
        r0 = r61;
        r59 = r5.findViewById(r0);
        r59 = (android.widget.ImageView) r59;
        r52 = r66.getUid();
        r61 = com.gitrose.mobile.application.MyApplication.getInstance();
        r60 = r61.getUserinfo();
        if (r60 == 0) goto L_0x002f;
    L_0x0021:
        r61 = r60.getUid();
        r0 = r52;
        r1 = r61;
        r61 = r0.equals(r1);
        if (r61 != 0) goto L_0x0042;
    L_0x002f:
        r61 = r66.getIskana();
        if (r61 == 0) goto L_0x02ed;
    L_0x0035:
        r61 = r66.getIskana();
        r62 = "1";
        r61 = r61.equals(r62);
        if (r61 == 0) goto L_0x02ed;
    L_0x0042:
        r61 = 4;
        r0 = r59;
        r1 = r61;
        r0.setVisibility(r1);
    L_0x004b:
        r61 = 2131361903; // 0x7f0a006f float:1.8343571E38 double:1.053032695E-314;
        r0 = r61;
        r39 = r5.findViewById(r0);
        r39 = (com.gitrose.mobile.view.CommentImageView) r39;
        r0 = r66;
        r1 = r39;
        r1.mTopicInfo = r0;
        r61 = 2131362235; // 0x7f0a01bb float:1.8344245E38 double:1.053032859E-314;
        r0 = r61;
        r18 = r5.findViewById(r0);
        r18 = (com.gitrose.mobile.view.CircleImageView) r18;
        r61 = 2131362238; // 0x7f0a01be float:1.834425E38 double:1.0530328606E-314;
        r0 = r61;
        r58 = r5.findViewById(r0);
        r58 = (android.widget.TextView) r58;
        r16 = r66.getIskana();
        if (r16 == 0) goto L_0x04c9;
    L_0x0078:
        r61 = "";
        r0 = r61;
        r1 = r16;
        r61 = r0.equals(r1);
        if (r61 != 0) goto L_0x04c9;
    L_0x0085:
        r53 = r66.getUserinfo();
        r61 = java.lang.Integer.valueOf(r16);
        r61 = r61.intValue();
        if (r61 != 0) goto L_0x0481;
    L_0x0093:
        if (r53 == 0) goto L_0x00ca;
    L_0x0095:
        r31 = r53.getRemarkname();
        if (r31 == 0) goto L_0x0474;
    L_0x009b:
        r61 = "";
        r0 = r61;
        r1 = r31;
        r61 = r0.equals(r1);
        if (r61 != 0) goto L_0x0474;
    L_0x00a8:
        r0 = r58;
        r1 = r31;
        r0.setText(r1);
    L_0x00af:
        r61 = r53.getUid();
        r62 = r53.getAvatartime();
        r4 = com.gitrose.mobile.utils.ImageUtils.getAvatarUrl(r61, r62);
        r61 = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        r62 = com.gitrose.mobile.constant.Constant.AVATAR_OPTIONS;
        r0 = r61;
        r1 = r18;
        r2 = r62;
        r0.displayImage(r4, r1, r2);
    L_0x00ca:
        r0 = r18;
        r1 = r66;
        r0.setTag(r1);
        r0 = r18;
        r1 = r68;
        r0.setOnClickListener(r1);
        r0 = r58;
        r1 = r66;
        r0.setTag(r1);
        r0 = r58;
        r1 = r64;
        r0.setOnClickListener(r1);
        r61 = 2131363079; // 0x7f0a0507 float:1.8345957E38 double:1.053033276E-314;
        r0 = r61;
        r49 = r5.findViewById(r0);
        r49 = (android.widget.TextView) r49;
        r61 = 2131362242; // 0x7f0a01c2 float:1.834426E38 double:1.0530328626E-314;
        r0 = r61;
        r48 = r5.findViewById(r0);
        r48 = (com.gitrose.mobile.view.TextViewFixTouchConsume) r48;
        r61 = 2131362243; // 0x7f0a01c3 float:1.8344261E38 double:1.053032863E-314;
        r0 = r61;
        r22 = r5.findViewById(r0);
        r22 = (android.widget.TextView) r22;
        r40 = r66.getDesc();
        if (r40 == 0) goto L_0x04d5;
    L_0x010d:
        r61 = "";
        r0 = r61;
        r1 = r40;
        r61 = r0.equals(r1);
        if (r61 != 0) goto L_0x04d5;
    L_0x011a:
        r61 = 0;
        r0 = r48;
        r1 = r61;
        r0.setVisibility(r1);
        r0 = r64;
        r0 = r0.mContext;
        r61 = r0;
        r0 = r40;
        r1 = r61;
        r61 = com.gitrose.mobile.utils.TextUtil.formatContent(r0, r1);
        r0 = r48;
        r1 = r61;
        r0.setText(r1);
        r61 = android.text.method.LinkMovementMethod.getInstance();
        r0 = r48;
        r1 = r61;
        r0.setMovementMethod(r1);
    L_0x0143:
        r29 = r66.getPoitext();
        if (r29 == 0) goto L_0x04e0;
    L_0x0149:
        r61 = "";
        r0 = r61;
        r1 = r29;
        r61 = r0.equals(r1);
        if (r61 != 0) goto L_0x04e0;
    L_0x0156:
        r0 = r22;
        r1 = r29;
        r0.setText(r1);
        r61 = 0;
        r0 = r22;
        r1 = r61;
        r0.setVisibility(r1);
    L_0x0166:
        r0 = r22;
        r1 = r66;
        r0.setTag(r1);
        r0 = r22;
        r1 = r64;
        r0.setOnClickListener(r1);
        r61 = 2131363088; // 0x7f0a0510 float:1.8345975E38 double:1.0530332806E-314;
        r0 = r61;
        r7 = r5.findViewById(r0);
        r7 = (android.widget.TextView) r7;
        r61 = r66.getLikenum();
        if (r61 != 0) goto L_0x04eb;
    L_0x0185:
        r0 = r64;
        r0 = r0.mContext;
        r61 = r0;
        r62 = 2131165572; // 0x7f070184 float:1.7945365E38 double:1.0529356947E-314;
        r61 = r61.getString(r62);
    L_0x0192:
        r0 = r61;
        r7.setText(r0);
        r61 = r66.getIslike();
        r62 = 1;
        r0 = r61;
        r1 = r62;
        if (r0 != r1) goto L_0x04fe;
    L_0x01a3:
        r61 = 1;
    L_0x01a5:
        r0 = r61;
        r7.setSelected(r0);
        r61 = 2131363089; // 0x7f0a0511 float:1.8345977E38 double:1.053033281E-314;
        r0 = r61;
        r8 = r5.findViewById(r0);
        r8 = (android.widget.TextView) r8;
        r61 = r66.getRepostnum();
        if (r61 != 0) goto L_0x0502;
    L_0x01bb:
        r0 = r64;
        r0 = r0.mContext;
        r61 = r0;
        r62 = 2131165675; // 0x7f0701eb float:1.7945574E38 double:1.0529357456E-314;
        r61 = r61.getString(r62);
    L_0x01c8:
        r0 = r61;
        r8.setText(r0);
        r61 = r66.getUserisrepost();
        r62 = 1;
        r0 = r61;
        r1 = r62;
        if (r0 != r1) goto L_0x0515;
    L_0x01d9:
        r61 = 1;
    L_0x01db:
        r0 = r61;
        r8.setSelected(r0);
        r61 = 2131361814; // 0x7f0a0016 float:1.834339E38 double:1.053032651E-314;
        r0 = r61;
        r1 = r66;
        r8.setTag(r0, r1);
        r0 = r68;
        r8.setOnClickListener(r0);
        r61 = 2131363090; // 0x7f0a0512 float:1.834598E38 double:1.0530332816E-314;
        r0 = r61;
        r47 = r5.findViewById(r0);
        r47 = (android.widget.TextView) r47;
        r0 = r47;
        r1 = r66;
        r0.setTag(r1);
        r0 = r47;
        r1 = r68;
        r0.setOnClickListener(r1);
        r61 = 2131363073; // 0x7f0a0501 float:1.8345945E38 double:1.053033273E-314;
        r0 = r61;
        r21 = r5.findViewById(r0);
        r21 = (android.widget.LinearLayout) r21;
        r61 = 2131363074; // 0x7f0a0502 float:1.8345947E38 double:1.0530332737E-314;
        r0 = r61;
        r44 = r5.findViewById(r0);
        r44 = (android.widget.TextView) r44;
        r61 = 2131363075; // 0x7f0a0503 float:1.8345949E38 double:1.053033274E-314;
        r0 = r61;
        r45 = r5.findViewById(r0);
        r45 = (android.widget.TextView) r45;
        r61 = 2131363076; // 0x7f0a0504 float:1.834595E38 double:1.0530332747E-314;
        r0 = r61;
        r46 = r5.findViewById(r0);
        r46 = (android.widget.TextView) r46;
        r61 = 2131363077; // 0x7f0a0505 float:1.8345953E38 double:1.053033275E-314;
        r0 = r61;
        r43 = r5.findViewById(r0);
        r43 = (android.widget.TextView) r43;
        r61 = 2131362239; // 0x7f0a01bf float:1.8344253E38 double:1.053032861E-314;
        r0 = r61;
        r17 = r5.findViewById(r0);
        r17 = (android.widget.ImageView) r17;
        r61 = r66.getUserinfo();
        if (r61 == 0) goto L_0x0269;
    L_0x0250:
        r42 = r66.getUserinfo();
        r19 = r42.getUserhonorlevel();
        if (r19 == 0) goto L_0x0260;
    L_0x025a:
        r61 = r19.intValue();
        if (r61 > 0) goto L_0x0519;
    L_0x0260:
        r61 = 4;
        r0 = r17;
        r1 = r61;
        r0.setVisibility(r1);
    L_0x0269:
        r36 = r66.getFromrepost();
        if (r36 != 0) goto L_0x0279;
    L_0x026f:
        r61 = "0";
        r0 = r66;
        r1 = r61;
        r0.setFromrepost(r1);
    L_0x0279:
        r61 = r66.getFromrepost();
        r14 = java.lang.Integer.parseInt(r61);
        if (r14 != 0) goto L_0x054a;
    L_0x0283:
        r61 = 8;
        r0 = r21;
        r1 = r61;
        r0.setVisibility(r1);
    L_0x028c:
        r41 = r66.getTotalcomment();
        r61 = 2131363087; // 0x7f0a050f float:1.8345973E38 double:1.05303328E-314;
        r0 = r61;
        r61 = r5.findViewById(r0);
        r61 = (android.widget.TextView) r61;
        if (r41 != 0) goto L_0x0b81;
    L_0x029d:
        r0 = r64;
        r0 = r0.mContext;
        r62 = r0;
        r63 = 2131165573; // 0x7f070185 float:1.7945367E38 double:1.052935695E-314;
        r62 = r62.getString(r63);
    L_0x02aa:
        r61.setText(r62);
        r61 = 2131363085; // 0x7f0a050d float:1.8345969E38 double:1.053033279E-314;
        r0 = r61;
        r6 = r5.findViewById(r0);
        r6 = (com.gitrose.mobile.view.AutoCoverFlow) r6;
        r61 = 2131363084; // 0x7f0a050c float:1.8345967E38 double:1.0530332786E-314;
        r0 = r61;
        r27 = r5.findViewById(r0);
        r27 = (android.widget.TextView) r27;
        r61 = r66.getCommentlist();
        if (r61 == 0) goto L_0x0b90;
    L_0x02c9:
        r61 = r66.getCommentlist();
        r61 = r61.size();
        if (r61 != 0) goto L_0x0b90;
    L_0x02d3:
        r61 = 4;
        r0 = r61;
        r6.setVisibility(r0);
        r61 = 0;
        r0 = r27;
        r1 = r61;
        r0.setVisibility(r1);
        r61 = 0;
        r0 = r27;
        r1 = r61;
        r0.setOnClickListener(r1);
    L_0x02ec:
        return;
    L_0x02ed:
        r61 = 0;
        r0 = r59;
        r1 = r61;
        r0.setVisibility(r1);
        r42 = r66.getUserinfo();
        if (r42 == 0) goto L_0x004b;
    L_0x02fc:
        r30 = r42.getRelation();
        r15 = r42.getIsauth();
        if (r30 == 0) goto L_0x038e;
    L_0x0306:
        r61 = "0";
        r0 = r61;
        r1 = r30;
        r61 = r0.equals(r1);
        if (r61 != 0) goto L_0x0320;
    L_0x0313:
        r61 = "1";
        r0 = r61;
        r1 = r30;
        r61 = r0.equals(r1);
        if (r61 == 0) goto L_0x038e;
    L_0x0320:
        if (r15 == 0) goto L_0x036a;
    L_0x0322:
        r61 = java.lang.Integer.parseInt(r15);
        r62 = 1;
        r0 = r61;
        r1 = r62;
        if (r0 != r1) goto L_0x036a;
    L_0x032e:
        r61 = 2130838831; // 0x7f02052f float:1.7282655E38 double:1.052774263E-314;
        r0 = r59;
        r1 = r61;
        r0.setImageResource(r1);
    L_0x0338:
        r61 = 2131361815; // 0x7f0a0017 float:1.8343393E38 double:1.0530326516E-314;
        r0 = r59;
        r1 = r61;
        r2 = r66;
        r0.setTag(r1, r2);
        r0 = r59;
        r1 = r64;
        r0.setOnClickListener(r1);
        r0 = r64;
        r0 = r0.isTopicPagerActivity;
        r61 = r0;
        r62 = 1;
        r0 = r61;
        r1 = r62;
        if (r0 != r1) goto L_0x042b;
    L_0x0359:
        r0 = r64;
        r0 = r0.mContext;
        r61 = r0;
        r61 = (com.gitrose.mobile.TopicDetailPagerActivity) r61;
        r0 = r61;
        r1 = r59;
        com.gitrose.mobile.utils.TouchUtil.setDelegate(r0, r1);
        goto L_0x004b;
    L_0x036a:
        if (r15 == 0) goto L_0x0383;
    L_0x036c:
        r61 = java.lang.Integer.parseInt(r15);
        r62 = 2;
        r0 = r61;
        r1 = r62;
        if (r0 != r1) goto L_0x0383;
    L_0x0378:
        r61 = 2130838834; // 0x7f020532 float:1.7282662E38 double:1.0527742647E-314;
        r0 = r59;
        r1 = r61;
        r0.setImageResource(r1);
        goto L_0x0338;
    L_0x0383:
        r61 = 2130838828; // 0x7f02052c float:1.728265E38 double:1.0527742617E-314;
        r0 = r59;
        r1 = r61;
        r0.setImageResource(r1);
        goto L_0x0338;
    L_0x038e:
        if (r30 == 0) goto L_0x03dc;
    L_0x0390:
        r61 = "2";
        r0 = r61;
        r1 = r30;
        r61 = r0.equals(r1);
        if (r61 == 0) goto L_0x03dc;
    L_0x039d:
        if (r15 == 0) goto L_0x03b6;
    L_0x039f:
        r61 = java.lang.Integer.parseInt(r15);
        r62 = 1;
        r0 = r61;
        r1 = r62;
        if (r0 != r1) goto L_0x03b6;
    L_0x03ab:
        r61 = 2130838832; // 0x7f020530 float:1.7282657E38 double:1.0527742637E-314;
        r0 = r59;
        r1 = r61;
        r0.setImageResource(r1);
        goto L_0x0338;
    L_0x03b6:
        if (r15 == 0) goto L_0x03d0;
    L_0x03b8:
        r61 = java.lang.Integer.parseInt(r15);
        r62 = 2;
        r0 = r61;
        r1 = r62;
        if (r0 != r1) goto L_0x03d0;
    L_0x03c4:
        r61 = 2130838835; // 0x7f020533 float:1.7282664E38 double:1.052774265E-314;
        r0 = r59;
        r1 = r61;
        r0.setImageResource(r1);
        goto L_0x0338;
    L_0x03d0:
        r61 = 2130838829; // 0x7f02052d float:1.7282651E38 double:1.052774262E-314;
        r0 = r59;
        r1 = r61;
        r0.setImageResource(r1);
        goto L_0x0338;
    L_0x03dc:
        if (r30 == 0) goto L_0x0338;
    L_0x03de:
        r61 = "3";
        r0 = r61;
        r1 = r30;
        r61 = r0.equals(r1);
        if (r61 == 0) goto L_0x0338;
    L_0x03eb:
        if (r15 == 0) goto L_0x0405;
    L_0x03ed:
        r61 = java.lang.Integer.parseInt(r15);
        r62 = 1;
        r0 = r61;
        r1 = r62;
        if (r0 != r1) goto L_0x0405;
    L_0x03f9:
        r61 = 2130838833; // 0x7f020531 float:1.728266E38 double:1.052774264E-314;
        r0 = r59;
        r1 = r61;
        r0.setImageResource(r1);
        goto L_0x0338;
    L_0x0405:
        if (r15 == 0) goto L_0x041f;
    L_0x0407:
        r61 = java.lang.Integer.parseInt(r15);
        r62 = 2;
        r0 = r61;
        r1 = r62;
        if (r0 != r1) goto L_0x041f;
    L_0x0413:
        r61 = 2130838836; // 0x7f020534 float:1.7282666E38 double:1.0527742657E-314;
        r0 = r59;
        r1 = r61;
        r0.setImageResource(r1);
        goto L_0x0338;
    L_0x041f:
        r61 = 2130838830; // 0x7f02052e float:1.7282653E38 double:1.0527742627E-314;
        r0 = r59;
        r1 = r61;
        r0.setImageResource(r1);
        goto L_0x0338;
    L_0x042b:
        r0 = r64;
        r0 = r0.isTopicPagerActivity;
        r61 = r0;
        r62 = 2;
        r0 = r61;
        r1 = r62;
        if (r0 != r1) goto L_0x044a;
    L_0x0439:
        r0 = r64;
        r0 = r0.mContext;
        r61 = r0;
        r61 = (com.gitrose.mobile.TopicDetailActivity) r61;
        r0 = r61;
        r1 = r59;
        com.gitrose.mobile.utils.TouchUtil.setDelegate(r0, r1);
        goto L_0x004b;
    L_0x044a:
        r0 = r64;
        r0 = r0.isFromPersonal;
        r61 = r0;
        if (r61 == 0) goto L_0x0463;
    L_0x0452:
        r0 = r64;
        r0 = r0.mContext;
        r61 = r0;
        r61 = (com.gitrose.mobile.PersonalNewActivity) r61;
        r0 = r61;
        r1 = r59;
        com.gitrose.mobile.utils.TouchUtil.setDelegate(r0, r1);
        goto L_0x004b;
    L_0x0463:
        r0 = r64;
        r0 = r0.mContext;
        r61 = r0;
        r61 = (com.gitrose.mobile.HomeFragmentActivity) r61;
        r0 = r61;
        r1 = r59;
        com.gitrose.mobile.utils.TouchUtil.setDelegate(r0, r1);
        goto L_0x004b;
    L_0x0474:
        r61 = r53.getNickname();
        r0 = r58;
        r1 = r61;
        r0.setText(r1);
        goto L_0x00af;
    L_0x0481:
        r61 = java.lang.Integer.valueOf(r16);
        r61 = r61.intValue();
        r62 = 1;
        r0 = r61;
        r1 = r62;
        if (r0 != r1) goto L_0x00ca;
    L_0x0491:
        r61 = 2130838517; // 0x7f0203f5 float:1.7282019E38 double:1.052774108E-314;
        r0 = r18;
        r1 = r61;
        r0.setImageResource(r1);
        r0 = r64;
        r0 = r0.mContext;
        r61 = r0;
        r62 = 2131165596; // 0x7f07019c float:1.7945414E38 double:1.0529357066E-314;
        r61 = r61.getString(r62);
        r0 = r58;
        r1 = r61;
        r0.setText(r1);
        r0 = r64;
        r0 = r0.mContext;
        r61 = r0;
        r61 = r61.getResources();
        r62 = 2131296381; // 0x7f09007d float:1.8210677E38 double:1.053000323E-314;
        r61 = r61.getColor(r62);
        r0 = r58;
        r1 = r61;
        r0.setTextColor(r1);
        goto L_0x00ca;
    L_0x04c9:
        r61 = 2130837982; // 0x7f0201de float:1.7280933E38 double:1.052773844E-314;
        r0 = r18;
        r1 = r61;
        r0.setImageResource(r1);
        goto L_0x00ca;
    L_0x04d5:
        r61 = 8;
        r0 = r48;
        r1 = r61;
        r0.setVisibility(r1);
        goto L_0x0143;
    L_0x04e0:
        r61 = 8;
        r0 = r22;
        r1 = r61;
        r0.setVisibility(r1);
        goto L_0x0166;
    L_0x04eb:
        r61 = new java.lang.StringBuilder;
        r62 = r66.getLikenum();
        r62 = java.lang.String.valueOf(r62);
        r61.<init>(r62);
        r61 = r61.toString();
        goto L_0x0192;
    L_0x04fe:
        r61 = 0;
        goto L_0x01a5;
    L_0x0502:
        r61 = new java.lang.StringBuilder;
        r62 = r66.getRepostnum();
        r62 = java.lang.String.valueOf(r62);
        r61.<init>(r62);
        r61 = r61.toString();
        goto L_0x01c8;
    L_0x0515:
        r61 = 0;
        goto L_0x01db;
    L_0x0519:
        r61 = 0;
        r0 = r17;
        r1 = r61;
        r0.setVisibility(r1);
        r61 = new java.lang.StringBuilder;
        r61.<init>();
        r0 = r61;
        r1 = r19;
        r61 = r0.append(r1);
        r61 = r61.toString();
        r20 = com.gitrose.mobile.utils.ImageUtils.getLevelUrl(r61);
        r61 = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        r62 = com.gitrose.mobile.constant.Constant.LEVEL_OPTIONS;
        r0 = r61;
        r1 = r20;
        r2 = r17;
        r3 = r62;
        r0.displayImage(r1, r2, r3);
        goto L_0x0269;
    L_0x054a:
        r61 = 0;
        r0 = r21;
        r1 = r61;
        r0.setVisibility(r1);
        r51 = "#78BAA6";
        r50 = "#999999";
        r38 = r66.getRepostuserinfo();
        if (r38 == 0) goto L_0x028c;
    L_0x055f:
        r37 = r38.getTotal();
        r57 = r38.getUserlist();
        r61 = 1;
        r0 = r37;
        r1 = r61;
        if (r0 != r1) goto L_0x0633;
    L_0x056f:
        r61 = 0;
        r0 = r57;
        r1 = r61;
        r53 = r0.get(r1);
        r53 = (com.gitrose.mobile.model.RepostUserList) r53;
        r32 = r53.getRemarkname();
        if (r32 == 0) goto L_0x062d;
    L_0x0581:
        r61 = "";
        r0 = r61;
        r1 = r32;
        r61 = r0.equals(r1);
        if (r61 != 0) goto L_0x062d;
    L_0x058e:
        r23 = r32;
    L_0x0590:
        r61 = new java.lang.StringBuilder;
        r62 = "<font color=\"";
        r61.<init>(r62);
        r0 = r61;
        r1 = r51;
        r61 = r0.append(r1);
        r62 = "\">";
        r61 = r61.append(r62);
        r0 = r61;
        r1 = r23;
        r61 = r0.append(r1);
        r62 = "</font><font color=\"";
        r61 = r61.append(r62);
        r0 = r61;
        r1 = r50;
        r61 = r0.append(r1);
        r62 = "\">";
        r61 = r61.append(r62);
        r0 = r64;
        r0 = r0.mContext;
        r62 = r0;
        r62 = r62.getResources();
        r63 = 2131165678; // 0x7f0701ee float:1.794558E38 double:1.052935747E-314;
        r62 = r62.getString(r63);
        r61 = r61.append(r62);
        r62 = "</font>";
        r61 = r61.append(r62);
        r9 = r61.toString();
        r61 = android.text.Html.fromHtml(r9);
        r0 = r44;
        r1 = r61;
        r0.setText(r1);
        r61 = 2131361816; // 0x7f0a0018 float:1.8343395E38 double:1.053032652E-314;
        r62 = r53.getUid();
        r0 = r44;
        r1 = r61;
        r2 = r62;
        r0.setTag(r1, r2);
        r0 = r44;
        r1 = r64;
        r0.setOnClickListener(r1);
        r61 = 0;
        r0 = r44;
        r1 = r61;
        r0.setVisibility(r1);
        r61 = 0;
        r0 = r43;
        r1 = r61;
        r0.setText(r1);
        r61 = 4;
        r0 = r45;
        r1 = r61;
        r0.setVisibility(r1);
        r61 = 4;
        r0 = r46;
        r1 = r61;
        r0.setVisibility(r1);
        goto L_0x028c;
    L_0x062d:
        r23 = r53.getNickname();
        goto L_0x0590;
    L_0x0633:
        if (r57 == 0) goto L_0x0779;
    L_0x0635:
        r61 = r57.size();
        r62 = 1;
        r0 = r61;
        r1 = r62;
        if (r0 != r1) goto L_0x0779;
    L_0x0641:
        r61 = 0;
        r0 = r57;
        r1 = r61;
        r53 = r0.get(r1);
        r53 = (com.gitrose.mobile.model.RepostUserList) r53;
        r32 = r53.getRemarkname();
        if (r32 == 0) goto L_0x0773;
    L_0x0653:
        r61 = "";
        r0 = r61;
        r1 = r32;
        r61 = r0.equals(r1);
        if (r61 != 0) goto L_0x0773;
    L_0x0660:
        r23 = r32;
    L_0x0662:
        r61 = new java.lang.StringBuilder;
        r62 = "<font color=\"";
        r61.<init>(r62);
        r0 = r61;
        r1 = r51;
        r61 = r0.append(r1);
        r62 = "\">";
        r61 = r61.append(r62);
        r0 = r61;
        r1 = r23;
        r61 = r0.append(r1);
        r62 = "</font>";
        r61 = r61.append(r62);
        r9 = r61.toString();
        r61 = android.text.Html.fromHtml(r9);
        r0 = r44;
        r1 = r61;
        r0.setText(r1);
        r61 = 2131361816; // 0x7f0a0018 float:1.8343395E38 double:1.053032652E-314;
        r62 = r53.getUid();
        r0 = r44;
        r1 = r61;
        r2 = r62;
        r0.setTag(r1, r2);
        r0 = r44;
        r1 = r64;
        r0.setOnClickListener(r1);
        r61 = 0;
        r0 = r44;
        r1 = r61;
        r0.setVisibility(r1);
        r61 = 4;
        r0 = r45;
        r1 = r61;
        r0.setVisibility(r1);
        r61 = 4;
        r0 = r46;
        r1 = r61;
        r0.setVisibility(r1);
    L_0x06c9:
        r61 = new java.lang.StringBuilder;
        r62 = r38.getTotal();
        r62 = java.lang.String.valueOf(r62);
        r61.<init>(r62);
        r28 = r61.toString();
        r61 = r38.getTotal();
        r62 = 3;
        r0 = r61;
        r1 = r62;
        if (r0 <= r1) goto L_0x0b5a;
    L_0x06e6:
        r61 = new java.lang.StringBuilder;
        r62 = "<font color=\"";
        r61.<init>(r62);
        r0 = r61;
        r1 = r50;
        r61 = r0.append(r1);
        r62 = "\">";
        r61 = r61.append(r62);
        r0 = r64;
        r0 = r0.mContext;
        r62 = r0;
        r62 = r62.getResources();
        r63 = 2131165676; // 0x7f0701ec float:1.7945576E38 double:1.052935746E-314;
        r62 = r62.getString(r63);
        r61 = r61.append(r62);
        r62 = "</font><font color=\"";
        r61 = r61.append(r62);
        r0 = r61;
        r1 = r51;
        r61 = r0.append(r1);
        r62 = "\">";
        r61 = r61.append(r62);
        r0 = r61;
        r1 = r28;
        r61 = r0.append(r1);
        r62 = "</font><font color=\"";
        r61 = r61.append(r62);
        r0 = r61;
        r1 = r50;
        r61 = r0.append(r1);
        r62 = "\">";
        r61 = r61.append(r62);
        r0 = r64;
        r0 = r0.mContext;
        r62 = r0;
        r62 = r62.getResources();
        r63 = 2131165677; // 0x7f0701ed float:1.7945578E38 double:1.0529357466E-314;
        r62 = r62.getString(r63);
        r61 = r61.append(r62);
        r62 = "</font>";
        r61 = r61.append(r62);
        r13 = r61.toString();
    L_0x0766:
        r61 = android.text.Html.fromHtml(r13);
        r0 = r43;
        r1 = r61;
        r0.setText(r1);
        goto L_0x028c;
    L_0x0773:
        r23 = r53.getNickname();
        goto L_0x0662;
    L_0x0779:
        if (r57 == 0) goto L_0x0908;
    L_0x077b:
        r61 = r57.size();
        r62 = 2;
        r0 = r61;
        r1 = r62;
        if (r0 != r1) goto L_0x0908;
    L_0x0787:
        r61 = 0;
        r0 = r57;
        r1 = r61;
        r54 = r0.get(r1);
        r54 = (com.gitrose.mobile.model.RepostUserList) r54;
        r33 = r54.getRemarkname();
        if (r33 == 0) goto L_0x08ec;
    L_0x0799:
        r61 = "";
        r0 = r61;
        r1 = r33;
        r61 = r0.equals(r1);
        if (r61 != 0) goto L_0x08ec;
    L_0x07a6:
        r24 = r33;
    L_0x07a8:
        r61 = r24.length();
        r62 = 8;
        r0 = r61;
        r1 = r62;
        if (r0 <= r1) goto L_0x07d6;
    L_0x07b4:
        r61 = new java.lang.StringBuilder;
        r62 = 0;
        r63 = 7;
        r0 = r24;
        r1 = r62;
        r2 = r63;
        r62 = r0.substring(r1, r2);
        r62 = java.lang.String.valueOf(r62);
        r61.<init>(r62);
        r62 = "...";
        r61 = r61.append(r62);
        r24 = r61.toString();
    L_0x07d6:
        r61 = new java.lang.StringBuilder;
        r62 = "<font color=\"";
        r61.<init>(r62);
        r0 = r61;
        r1 = r51;
        r61 = r0.append(r1);
        r62 = "\">";
        r61 = r61.append(r62);
        r0 = r61;
        r1 = r24;
        r61 = r0.append(r1);
        r62 = "</font>";
        r61 = r61.append(r62);
        r10 = r61.toString();
        r61 = android.text.Html.fromHtml(r10);
        r0 = r44;
        r1 = r61;
        r0.setText(r1);
        r61 = 2131361816; // 0x7f0a0018 float:1.8343395E38 double:1.053032652E-314;
        r62 = r54.getUid();
        r0 = r44;
        r1 = r61;
        r2 = r62;
        r0.setTag(r1, r2);
        r0 = r44;
        r1 = r64;
        r0.setOnClickListener(r1);
        r61 = 0;
        r0 = r44;
        r1 = r61;
        r0.setVisibility(r1);
        r61 = 1;
        r0 = r57;
        r1 = r61;
        r55 = r0.get(r1);
        r55 = (com.gitrose.mobile.model.RepostUserList) r55;
        r34 = r55.getRemarkname();
        if (r34 == 0) goto L_0x08f2;
    L_0x083d:
        r61 = "";
        r0 = r61;
        r1 = r34;
        r61 = r0.equals(r1);
        if (r61 != 0) goto L_0x08f2;
    L_0x084a:
        r61 = new java.lang.StringBuilder;
        r62 = "\u3001";
        r61.<init>(r62);
        r0 = r61;
        r1 = r34;
        r61 = r0.append(r1);
        r25 = r61.toString();
    L_0x085e:
        r61 = r25.length();
        r62 = 9;
        r0 = r61;
        r1 = r62;
        if (r0 <= r1) goto L_0x088c;
    L_0x086a:
        r61 = new java.lang.StringBuilder;
        r62 = 0;
        r63 = 8;
        r0 = r25;
        r1 = r62;
        r2 = r63;
        r62 = r0.substring(r1, r2);
        r62 = java.lang.String.valueOf(r62);
        r61.<init>(r62);
        r62 = "...";
        r61 = r61.append(r62);
        r25 = r61.toString();
    L_0x088c:
        r61 = new java.lang.StringBuilder;
        r62 = "<font color=\"";
        r61.<init>(r62);
        r0 = r61;
        r1 = r51;
        r61 = r0.append(r1);
        r62 = "\">";
        r61 = r61.append(r62);
        r0 = r61;
        r1 = r25;
        r61 = r0.append(r1);
        r62 = "</font>";
        r61 = r61.append(r62);
        r11 = r61.toString();
        r61 = android.text.Html.fromHtml(r11);
        r0 = r45;
        r1 = r61;
        r0.setText(r1);
        r61 = 2131361817; // 0x7f0a0019 float:1.8343397E38 double:1.0530326526E-314;
        r62 = r55.getUid();
        r0 = r45;
        r1 = r61;
        r2 = r62;
        r0.setTag(r1, r2);
        r0 = r45;
        r1 = r64;
        r0.setOnClickListener(r1);
        r61 = 0;
        r0 = r45;
        r1 = r61;
        r0.setVisibility(r1);
        r61 = 4;
        r0 = r46;
        r1 = r61;
        r0.setVisibility(r1);
        goto L_0x06c9;
    L_0x08ec:
        r24 = r54.getNickname();
        goto L_0x07a8;
    L_0x08f2:
        r61 = new java.lang.StringBuilder;
        r62 = "\u3001";
        r61.<init>(r62);
        r62 = r55.getNickname();
        r61 = r61.append(r62);
        r25 = r61.toString();
        goto L_0x085e;
    L_0x0908:
        if (r57 == 0) goto L_0x06c9;
    L_0x090a:
        r61 = r57.size();
        r62 = 3;
        r0 = r61;
        r1 = r62;
        if (r0 != r1) goto L_0x06c9;
    L_0x0916:
        r61 = 0;
        r0 = r57;
        r1 = r61;
        r54 = r0.get(r1);
        r54 = (com.gitrose.mobile.model.RepostUserList) r54;
        r33 = r54.getRemarkname();
        if (r33 == 0) goto L_0x0b28;
    L_0x0928:
        r61 = "";
        r0 = r61;
        r1 = r33;
        r61 = r0.equals(r1);
        if (r61 != 0) goto L_0x0b28;
    L_0x0935:
        r24 = r33;
    L_0x0937:
        r61 = r24.length();
        r62 = 5;
        r0 = r61;
        r1 = r62;
        if (r0 <= r1) goto L_0x0965;
    L_0x0943:
        r61 = new java.lang.StringBuilder;
        r62 = 0;
        r63 = 4;
        r0 = r24;
        r1 = r62;
        r2 = r63;
        r62 = r0.substring(r1, r2);
        r62 = java.lang.String.valueOf(r62);
        r61.<init>(r62);
        r62 = "...";
        r61 = r61.append(r62);
        r24 = r61.toString();
    L_0x0965:
        r61 = new java.lang.StringBuilder;
        r62 = "<font color=\"";
        r61.<init>(r62);
        r0 = r61;
        r1 = r51;
        r61 = r0.append(r1);
        r62 = "\">";
        r61 = r61.append(r62);
        r0 = r61;
        r1 = r24;
        r61 = r0.append(r1);
        r62 = "</font>";
        r61 = r61.append(r62);
        r10 = r61.toString();
        r61 = android.text.Html.fromHtml(r10);
        r0 = r44;
        r1 = r61;
        r0.setText(r1);
        r61 = 2131361816; // 0x7f0a0018 float:1.8343395E38 double:1.053032652E-314;
        r62 = r54.getUid();
        r0 = r44;
        r1 = r61;
        r2 = r62;
        r0.setTag(r1, r2);
        r0 = r44;
        r1 = r64;
        r0.setOnClickListener(r1);
        r61 = 0;
        r0 = r44;
        r1 = r61;
        r0.setVisibility(r1);
        r61 = 1;
        r0 = r57;
        r1 = r61;
        r55 = r0.get(r1);
        r55 = (com.gitrose.mobile.model.RepostUserList) r55;
        r34 = r55.getRemarkname();
        if (r34 == 0) goto L_0x0b2e;
    L_0x09cc:
        r61 = "";
        r0 = r61;
        r1 = r34;
        r61 = r0.equals(r1);
        if (r61 != 0) goto L_0x0b2e;
    L_0x09d9:
        r61 = new java.lang.StringBuilder;
        r62 = "\u3001";
        r61.<init>(r62);
        r0 = r61;
        r1 = r34;
        r61 = r0.append(r1);
        r25 = r61.toString();
    L_0x09ed:
        r61 = r25.length();
        r62 = 6;
        r0 = r61;
        r1 = r62;
        if (r0 <= r1) goto L_0x0a1b;
    L_0x09f9:
        r61 = new java.lang.StringBuilder;
        r62 = 0;
        r63 = 5;
        r0 = r25;
        r1 = r62;
        r2 = r63;
        r62 = r0.substring(r1, r2);
        r62 = java.lang.String.valueOf(r62);
        r61.<init>(r62);
        r62 = "...";
        r61 = r61.append(r62);
        r25 = r61.toString();
    L_0x0a1b:
        r61 = new java.lang.StringBuilder;
        r62 = "<font color=\"";
        r61.<init>(r62);
        r0 = r61;
        r1 = r51;
        r61 = r0.append(r1);
        r62 = "\">";
        r61 = r61.append(r62);
        r0 = r61;
        r1 = r25;
        r61 = r0.append(r1);
        r62 = "</font>";
        r61 = r61.append(r62);
        r11 = r61.toString();
        r61 = android.text.Html.fromHtml(r11);
        r0 = r45;
        r1 = r61;
        r0.setText(r1);
        r61 = 2131361817; // 0x7f0a0019 float:1.8343397E38 double:1.0530326526E-314;
        r62 = r55.getUid();
        r0 = r45;
        r1 = r61;
        r2 = r62;
        r0.setTag(r1, r2);
        r0 = r45;
        r1 = r64;
        r0.setOnClickListener(r1);
        r61 = 0;
        r0 = r45;
        r1 = r61;
        r0.setVisibility(r1);
        r61 = 2;
        r0 = r57;
        r1 = r61;
        r56 = r0.get(r1);
        r56 = (com.gitrose.mobile.model.RepostUserList) r56;
        r35 = r56.getRemarkname();
        if (r35 == 0) goto L_0x0b44;
    L_0x0a82:
        r61 = "";
        r0 = r61;
        r1 = r35;
        r61 = r0.equals(r1);
        if (r61 != 0) goto L_0x0b44;
    L_0x0a8f:
        r61 = new java.lang.StringBuilder;
        r62 = "\u3001";
        r61.<init>(r62);
        r0 = r61;
        r1 = r35;
        r61 = r0.append(r1);
        r26 = r61.toString();
    L_0x0aa3:
        r61 = r26.length();
        r62 = 6;
        r0 = r61;
        r1 = r62;
        if (r0 <= r1) goto L_0x0ad1;
    L_0x0aaf:
        r61 = new java.lang.StringBuilder;
        r62 = 0;
        r63 = 5;
        r0 = r26;
        r1 = r62;
        r2 = r63;
        r62 = r0.substring(r1, r2);
        r62 = java.lang.String.valueOf(r62);
        r61.<init>(r62);
        r62 = "...";
        r61 = r61.append(r62);
        r26 = r61.toString();
    L_0x0ad1:
        r61 = new java.lang.StringBuilder;
        r62 = "<font color=\"";
        r61.<init>(r62);
        r0 = r61;
        r1 = r51;
        r61 = r0.append(r1);
        r62 = "\">";
        r61 = r61.append(r62);
        r0 = r61;
        r1 = r26;
        r61 = r0.append(r1);
        r62 = "</font>";
        r61 = r61.append(r62);
        r12 = r61.toString();
        r61 = android.text.Html.fromHtml(r12);
        r0 = r46;
        r1 = r61;
        r0.setText(r1);
        r61 = 2131361818; // 0x7f0a001a float:1.83434E38 double:1.053032653E-314;
        r62 = r56.getUid();
        r0 = r46;
        r1 = r61;
        r2 = r62;
        r0.setTag(r1, r2);
        r0 = r46;
        r1 = r64;
        r0.setOnClickListener(r1);
        r61 = 0;
        r0 = r46;
        r1 = r61;
        r0.setVisibility(r1);
        goto L_0x06c9;
    L_0x0b28:
        r24 = r54.getNickname();
        goto L_0x0937;
    L_0x0b2e:
        r61 = new java.lang.StringBuilder;
        r62 = "\u3001";
        r61.<init>(r62);
        r62 = r55.getNickname();
        r61 = r61.append(r62);
        r25 = r61.toString();
        goto L_0x09ed;
    L_0x0b44:
        r61 = new java.lang.StringBuilder;
        r62 = "\u3001";
        r61.<init>(r62);
        r62 = r56.getNickname();
        r61 = r61.append(r62);
        r26 = r61.toString();
        goto L_0x0aa3;
    L_0x0b5a:
        r61 = new java.lang.StringBuilder;
        r0 = r64;
        r0 = r0.mContext;
        r62 = r0;
        r62 = r62.getResources();
        r63 = 2131165678; // 0x7f0701ee float:1.794558E38 double:1.052935747E-314;
        r62 = r62.getString(r63);
        r62 = java.lang.String.valueOf(r62);
        r61.<init>(r62);
        r62 = "</font>";
        r61 = r61.append(r62);
        r13 = r61.toString();
        goto L_0x0766;
    L_0x0b81:
        r62 = new java.lang.StringBuilder;
        r63 = java.lang.String.valueOf(r41);
        r62.<init>(r63);
        r62 = r62.toString();
        goto L_0x02aa;
    L_0x0b90:
        r61 = 0;
        r0 = r61;
        r6.setVisibility(r0);
        r61 = 4;
        r0 = r27;
        r1 = r61;
        r0.setVisibility(r1);
        r61 = new com.gitrose.mobile.adapter.AutoCoverFlowAdapter;
        r0 = r64;
        r0 = r0.mContext;
        r62 = r0;
        r63 = r66.getCommentlist();
        r61.<init>(r62, r63);
        r0 = r61;
        r6.setAdapter(r0);
        r61 = 2131361807; // 0x7f0a000f float:1.8343377E38 double:1.0530326477E-314;
        r62 = java.lang.Integer.valueOf(r65);
        r0 = r61;
        r1 = r62;
        r6.setTag(r0, r1);
        r61 = 2131361808; // 0x7f0a0010 float:1.8343379E38 double:1.053032648E-314;
        r0 = r61;
        r1 = r66;
        r6.setTag(r0, r1);
        r0 = r64;
        r0 = r0.mAvatarOnItemClickListener;
        r61 = r0;
        r0 = r61;
        r6.setOnItemClickListener(r0);
        r0 = r64;
        r0 = r0.mAvatarOnItemSelectedListener;
        r61 = r0;
        r0 = r61;
        r6.setOnItemSelectedListener(r0);
        r61 = -1;
        r0 = r67;
        r1 = r61;
        if (r0 == r1) goto L_0x02ec;
    L_0x0bea:
        r61 = new com.gitrose.mobile.adapter.HomeFollowAdapter$5;
        r0 = r61;
        r1 = r64;
        r2 = r67;
        r0.<init>(r6, r2);
        r62 = 100;
        r0 = r61;
        r1 = r62;
        r6.postDelayed(r0, r1);
        goto L_0x02ec;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gitrose.mobile.adapter.HomeFollowAdapter.updateTopicFromNetWork(int, com.gitrose.greendao.TopicInfo, int, android.view.View$OnClickListener):void");
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
        try {
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
                if (isNeedUpload) {
//                    QGHttpRequest.getInstance().viewTopicRequest(this.mContext, sb.toString(), new C17806(this.mContext));
                }
            }
        } catch (Exception e) {
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
                startPersonalActivity(v);
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
                switchCommentShow(v);
                break;
            case R.id.img_topic_live_mark:
                if (this.fragment != null && (this.fragment instanceof HomeFollowFragment)) {
                    TopicInfo topicinfo = (TopicInfo) v.getTag();
                    if (topicinfo != null) {
                        ((HomeFollowFragment) this.fragment).startAVActivity(topicinfo);
                    }
                }
                break;
            case R.id.tv_forword_name_1:
                startPersonPage((String) v.getTag(R.id.forword_user_id_1));
                break;
            case R.id.tv_forword_name_2:
                startPersonPage((String) v.getTag(R.id.forword_user_id_2));
                break;
            case R.id.tv_forword_name_3:
                startPersonPage((String) v.getTag(R.id.forword_user_id_3));
                break;
            case R.id.iv_user_follow_status:
                TopicMore.followUser(this.mContext, (ImageView) v);
                break;
            case R.id.tv_topic_device:
                if (MyApplication.getInstance().isLogin()) {
//                    this.mContext.startActivity(new Intent(this.mContext, PhoneDeviceActivity.class));
//                    animationForNew();
                    return;
                }
                this.mContext.startActivity(new Intent(this.mContext, LoginActivity.class));
                animationForNew();
                break;
            case R.id.tv_topic_more:
                startTopDetailActivity(v, false);
                break;
            case R.id.img_topic_switch_isredpacket:
                TopicMore.reciveRedPacket(this.mContext, (ImageView) v, this, null, this.onClickListener, this.isTopicDetail);
                break;
            case R.id.tv_home_comment:
                startTopDetailActivity(v, true);
                break;
            case R.id.tv_home_zan:
                TopicMore.favTopic(this.mContext, (TextView) v);
                break;
            case R.id.tv_home_forward:
                TopicMore.repostTopic(this.mContext, (TextView) v);
                break;
            case R.id.rl_huati_item:
                TopicInfo topInfo = (TopicInfo) v.getTag();
                if (topInfo != null) {
                    if (topInfo.getType() == null) {
                        topInfo.setType(Integer.valueOf(0));
                    }
                    int type = topInfo.getType().intValue();
                    if (type == 20 || type == 21) {
//                        intent = new Intent(this.mContext, ThemeActivity.class);
//                        HuaTi huaTiPoi = new HuaTi();
//                        huaTiPoi.setTopicid(topInfo.getTopicid());
//                        huaTiPoi.setHuatitext(topInfo.getIdtext());
//                        if (type == 20) {
//                            huaTiPoi.setType(true);
//                        } else {
//                            huaTiPoi.setType(false);
//                        }
//                        intent.putExtra("huati", huaTiPoi);
//                        this.mContext.startActivity(intent);
//                        animationForNew();
                    }
                }
                break;
            default:
        }
    }

    private void startPersonPage(String uid) {
//        Intent intent = new Intent(this.mContext, PersonalNewActivity.class);
//        intent.putExtra(PersonalNewActivity.PERSONAL_USER_ID, uid);
//        this.mContext.startActivity(intent);
//        animationForNew();
//        PlaySound.getInstance(this.mContext).toPlay(R.raw.open);
    }

    private void switchCommentShow(View view) {
        if (VERSION.SDK_INT >= 11) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object) view, "rotationY", 0.0f, 0.0f).setDuration(300);
            objectAnimator.addListener(new C14377(view));
            objectAnimator.start();
            return;
        }
        switchComment(view);
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
        TopicInfo topicInfo = (TopicInfo) view.getTag();
        if (topicInfo != null) {
            String isName = topicInfo.getIskana();
            Intent intent;
            Bundle bundle;
            if (isName == null || BaseConstants.ah.equals(isName)) {
//                intent = new Intent();
//                intent.setClass(this.mContext, PersonalNewActivity.class);
//                bundle = new Bundle();
//                bundle.putString(PersonalNewActivity.PERSONAL_USER_ID, topicInfo.getUid());
//                intent.putExtras(bundle);
//                this.mContext.startActivity(intent);
//                animationForNew();
//                PlaySound.getInstance(this.mContext).toPlay(R.raw.open);
            } else if (Integer.valueOf(isName).intValue() == 0) {
//                intent = new Intent();
//                intent.setClass(this.mContext, PersonalNewActivity.class);
//                bundle = new Bundle();
//                bundle.putString(PersonalNewActivity.PERSONAL_USER_ID, topicInfo.getUid());
//                intent.putExtras(bundle);
//                this.mContext.startActivity(intent);
//                animationForNew();
//                PlaySound.getInstance(this.mContext).toPlay(R.raw.open);
            } else {
                Toast.makeText(this.mContext, this.mContext.getString(R.string.theme_isname_tip), Toast.LENGTH_LONG).show();
            }
        }
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
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.setClass(this.mContext, TopicDetailPagerActivity.class);
                if (this.isFromPersonal) {
//                    TopicDetailPagerActivity.CLICK_FRAGMENT_POS = position;
//                    bundle.putInt(TopicDetailPagerActivity.LIST_POS, position);
//                    if (((PersonalNewActivity) this.mContext).mCurrentTab == 0) {
//                        bundle.putInt(TableSchema.COLUMN_TYPE, 0);
//                    } else if (((PersonalNewActivity) this.mContext).mCurrentTab == 1) {
//                        bundle.putInt(TableSchema.COLUMN_TYPE, 1);
//                    }
//                    bundle.putInt("total", this.mTopicList.size());
//                    bundle.putBoolean("from_personal", true);
//                    MyApplication.getInstance().setmPicList((ArrayList) this.mTopicList);
                } else {
                    topics.add(topicInfo);
                    bundle.putBoolean("isFromHomePage", flag);
                    bundle.putSerializable("topics", (Serializable) topics);
                }
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

    public void refreshPaperActivityRedpack() {
//        if (this.isTopicPagerActivity == 1) {
//            ((TopicDetailPagerActivity) this.mContext).refreshRedpack();
//        } else if (this.isTopicPagerActivity == SELECT_COMMENT_WHAT) {
//            ((TopicDetailActivity) this.mContext).refreshRedpack();
//        }
    }

    private void animationForNew() {
        ((Activity) this.mContext).overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
