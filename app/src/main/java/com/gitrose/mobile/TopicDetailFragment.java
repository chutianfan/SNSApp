package com.gitrose.mobile;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baoyz.swipemenulistview.XListView;
import com.baoyz.swipemenulistview.XListView.IXListViewListener;
//import com.sina.weibo.sdk.component.WidgetRequestParam;
//import com.sina.weibo.sdk.openapi.InviteAPI;
//import com.tencent.connect.share.QzoneShare;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.adapter.CommentAdpter;
import com.gitrose.mobile.adapter.HomeFollowAdapter;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.avsdk.control.Util;
import com.gitrose.mobile.base.ITopicDisplay;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.dao.RequestDao;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.Comment;
import com.gitrose.mobile.model.NewCommentList;
import com.gitrose.mobile.model.TopicInfoList;
import com.gitrose.mobile.model.TopicReciveRedPacketModel;
import com.gitrose.mobile.model.TopicReciveRedPacketModel.TopicReciveItem;
import com.gitrose.mobile.model.TopicZanModel;
import com.gitrose.mobile.model.TopicZanModel.TopicZanItemModel;
//import com.gitrose.mobile.utils.GreenDaoUtils;
import com.gitrose.mobile.view.BaseDialog;
import com.gitrose.mobile.view.QuickReturnListViewOnScrollListener;
import com.gitrose.mobile.view.TipPop;
import com.gitrose.mobile.view.TopTip;
import com.gitrose.mobile.view.TopicMore;
//import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.view.ShareActivity;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import qalsdk.BaseConstants;
import shouji.gexing.framework.utils.NetUtils;

public class TopicDetailFragment extends Fragment implements IXListViewListener, OnClickListener {
    private TopicFAVBroadcastReceiver broadcastReceiver;
    private Bundle bundle;
    private RelativeLayout container_rl;
    private TipPop copyPop;
    private boolean isSelf;
    private int len;
    private boolean lookRedPacket;
    private CommentAdpter mCommentAdpter;
    private View mCommentDetailView;
    private View mCommentDividerView;
    public XListView mCommentLv;
    private TipPop mCommentPop;
    private View mCommentPopView;
    private TextView mCommentTitleView;
    private ImageView mCommentTriangleView;
    private View mConvertView;
    private BaseDialog mDialog;
    private Handler mHandler;
    private int mListPos;
    private List<Comment> mNewComments;
    private OnClickListener mOnClickListener;
    private QuickReturnListViewOnScrollListener mOnScrollListener;
    private TextView mRecived_num;
    private TextView mRedpacketTitleView;
    private RequestDao mRequestDao;
    private HomeFollowAdapter mTopicAdapter;
    private TopicInfo mTopicInfo;
    private TextView mZanTitleView;
    private OnLongClickListener onLongClickListener;
    private int recive_num;
    private ArrayList<TopicReciveItem> recive_red_packet_list;
    private View redPacket_divider_view;
    private int total_num;
    private int type;
    private ArrayList<TopicZanItemModel> zan_list;

    /* renamed from: com.gitrose.mobile.TopicDetailFragment.12 */
    class AnonymousClass12 implements OnClickListener {
        private final /* synthetic */ String val$commentContent;
        private final /* synthetic */ boolean val$isReply;
        private final /* synthetic */ int val$position;
        private final /* synthetic */ View val$view;

        /* renamed from: com.gitrose.mobile.TopicDetailFragment.12.1 */
        class C17611 extends QGHttpHandler<String> {
            private final /* synthetic */ String val$commentId;
            private final /* synthetic */ boolean val$isReply;
            private final /* synthetic */ int val$position;

            C17611(Context $anonymous0, boolean z, int i, String str) {

                super($anonymous0);
                this.val$isReply = z;
                this.val$position = i;
                this.val$commentId = str;

            }

            public void onGetDataSuccess(String data) {
                if (this.val$isReply) {
                    ((Comment) TopicDetailFragment.this.mNewComments.get(this.val$position)).setReplydata(null);
                } else {
                    TopicDetailFragment.this.mNewComments.remove(this.val$position);
                }
                TopicDetailFragment.this.mCommentAdpter.notifyDataSetChanged();
                TopicDetailFragment.this.delHotComment(this.val$commentId);
            }
        }

        /* renamed from: com.gitrose.mobile.TopicDetailFragment.12.2 */
        class C17622 extends QGHttpHandler<String> {
            C17622(Context $anonymous0) {
                super($anonymous0);
            }

            public void onGetDataSuccess(String data) {
                Toast.makeText(TopicDetailFragment.this.getActivity(), TopicDetailFragment.this.getResources().getString(R.string.report_success), Toast.LENGTH_LONG).show();
            }
        }

        AnonymousClass12(String str, View view, int i, boolean z) {
            this.val$commentContent = str;
            this.val$view = view;
            this.val$position = i;
            this.val$isReply = z;
        }

        public void onClick(View v) {
            if (TopicDetailFragment.this.getResources().getString(R.string.copy).equals(v.getTag())) {
                TopicDetailFragment.this.copyContent(this.val$commentContent);
                this.val$view.setSelected(false);
                TopicDetailFragment.this.copyPop.dismiss();
            } else if (TopicDetailFragment.this.getResources().getString(R.string.delete).equals(v.getTag())) {
                if (TopicDetailFragment.this.mNewComments != null && TopicDetailFragment.this.mNewComments.size() > 0 && this.val$position < TopicDetailFragment.this.mNewComments.size()) {
                    Comment comment = (Comment) TopicDetailFragment.this.mNewComments.get(this.val$position);
                    if (comment != null) {
                        String cId = null;
                        if (this.val$isReply) {
                            Comment replyComment = comment.getReplydata();
                            if (replyComment != null) {
                                cId = replyComment.getCommentid();
                            }
                        } else {
                            cId = comment.getCommentid();
                        }
                        String commentId = cId;
                        QGHttpRequest.getInstance().delCommentRequest(TopicDetailFragment.this.getActivity(), TopicDetailFragment.this.mTopicInfo.getTopicid(), commentId, new C17611(TopicDetailFragment.this.getActivity(), this.val$isReply, this.val$position, commentId));
                    }
                }
                this.val$view.setSelected(false);
                TopicDetailFragment.this.copyPop.dismiss();
            } else if (TopicDetailFragment.this.getResources().getString(R.string.report).equals(v.getTag())) {
                if (TopicDetailFragment.this.mNewComments != null && TopicDetailFragment.this.mNewComments.size() > 0) {
                    Comment comment = (Comment) TopicDetailFragment.this.mNewComments.get(this.val$position);
                    if (comment != null) {
                        String commentId = null;
                        if (this.val$isReply) {
                            Comment replyComment = comment.getReplydata();
                            if (replyComment != null) {
                                commentId = replyComment.getCommentid();
                            }
                        } else {
                            commentId = comment.getCommentid();
                        }
                        QGHttpRequest.getInstance().reportCommentRequest(TopicDetailFragment.this.getActivity(), commentId, new C17622(TopicDetailFragment.this.getActivity()));
                    }
                }
                this.val$view.setSelected(false);
                TopicDetailFragment.this.copyPop.dismiss();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailFragment.1 */
    class C08121 implements OnLongClickListener {
        C08121() {
        }

        public boolean onLongClick(View v) {
            int position = ((Integer) v.getTag(R.id.topic_comment_pos)).intValue();
            if (position < 0) {
                return false;
            }
            Comment comment = (Comment) TopicDetailFragment.this.mNewComments.get(position);
            if (comment != null) {
                TutuUsers users = MyApplication.getInstance().getUserinfo();
                boolean isReply = ((Boolean) v.getTag(R.id.topic_comment_reply)).booleanValue();
                if (users != null) {
                    String userId;
                    Comment replyComment;
                    String commentContent;
                    String content;
                    if (TopicDetailFragment.this.isSelf) {
                        userId = users.getUid();
                        if (isReply) {
                            replyComment = comment.getReplydata();
                            if (replyComment != null) {
                                String replyUid = replyComment.getUid();
                                commentContent = replyComment.getContent();
                                if (userId == null || !userId.equals(replyUid)) {
                                    if (commentContent == null || BaseConstants.ah.equals(commentContent)) {
                                        TopicDetailFragment.this.showCopyPop(v, true, position, commentContent, new String[]{TopicDetailFragment.this.getResources().getString(R.string.delete), TopicDetailFragment.this.getResources().getString(R.string.report)});
                                    } else {
                                        TopicDetailFragment.this.showCopyPop(v, true, position, commentContent, new String[]{TopicDetailFragment.this.getResources().getString(R.string.copy), TopicDetailFragment.this.getResources().getString(R.string.delete), TopicDetailFragment.this.getResources().getString(R.string.report)});
                                    }
                                } else if (commentContent == null || BaseConstants.ah.equals(commentContent)) {
                                    TopicDetailFragment.this.showCopyPop(v, true, position, commentContent, new String[]{TopicDetailFragment.this.getResources().getString(R.string.delete)});
                                } else {
                                    TopicDetailFragment.this.showCopyPop(v, true, position, commentContent, new String[]{TopicDetailFragment.this.getResources().getString(R.string.copy), TopicDetailFragment.this.getResources().getString(R.string.delete)});
                                }
                            }
                        } else {
                            content = comment.getContent();
                            String uid = comment.getUid();
                            if (userId == null || !userId.equals(uid)) {
                                if (content == null || BaseConstants.ah.equals(content)) {
                                    TopicDetailFragment.this.showCopyPop(v, false, position, content, new String[]{TopicDetailFragment.this.getResources().getString(R.string.delete), TopicDetailFragment.this.getResources().getString(R.string.report)});
                                } else {
                                    TopicDetailFragment.this.showCopyPop(v, false, position, content, new String[]{TopicDetailFragment.this.getResources().getString(R.string.copy), TopicDetailFragment.this.getResources().getString(R.string.delete), TopicDetailFragment.this.getResources().getString(R.string.report)});
                                }
                            } else if (content == null || BaseConstants.ah.equals(content)) {
                                TopicDetailFragment.this.showCopyPop(v, false, position, content, new String[]{TopicDetailFragment.this.getResources().getString(R.string.delete)});
                            } else {
                                TopicDetailFragment.this.showCopyPop(v, false, position, content, new String[]{TopicDetailFragment.this.getResources().getString(R.string.copy), TopicDetailFragment.this.getResources().getString(R.string.delete)});
                            }
                        }
                    } else if (isReply) {
                        replyComment = comment.getReplydata();
                        if (replyComment != null) {
                            commentContent = replyComment.getContent();
                            String replyUserId = replyComment.getUid();
                            if (users != null) {
                                userId = users.getUid();
                                if (userId == null || !userId.equals(replyUserId)) {
                                    if (commentContent == null || BaseConstants.ah.equals(commentContent)) {
                                        TopicDetailFragment.this.showCopyPop(v, true, position, commentContent, new String[]{TopicDetailFragment.this.getResources().getString(R.string.report)});
                                    } else {
                                        TopicDetailFragment.this.showCopyPop(v, true, position, commentContent, new String[]{TopicDetailFragment.this.getResources().getString(R.string.copy), TopicDetailFragment.this.getResources().getString(R.string.report)});
                                    }
                                } else if (commentContent == null || BaseConstants.ah.equals(commentContent)) {
                                    TopicDetailFragment.this.showCopyPop(v, true, position, commentContent, new String[]{TopicDetailFragment.this.getResources().getString(R.string.delete)});
                                } else {
                                    TopicDetailFragment.this.showCopyPop(v, true, position, commentContent, new String[]{TopicDetailFragment.this.getResources().getString(R.string.copy), TopicDetailFragment.this.getResources().getString(R.string.delete)});
                                }
                            }
                        }
                    } else {
                        String commentUserId = comment.getUid();
                        content = comment.getContent();
                        if (users != null) {
                            userId = users.getUid();
                            if (userId == null || !userId.equals(commentUserId)) {
                                if (content == null || BaseConstants.ah.equals(content)) {
                                    TopicDetailFragment.this.showCopyPop(v, false, position, content, new String[]{TopicDetailFragment.this.getResources().getString(R.string.report)});
                                } else {
                                    TopicDetailFragment.this.showCopyPop(v, false, position, content, new String[]{TopicDetailFragment.this.getResources().getString(R.string.copy), TopicDetailFragment.this.getResources().getString(R.string.report)});
                                }
                            } else if (content == null || BaseConstants.ah.equals(content)) {
                                TopicDetailFragment.this.showCopyPop(v, false, position, content, new String[]{TopicDetailFragment.this.getResources().getString(R.string.delete)});
                            } else {
                                TopicDetailFragment.this.showCopyPop(v, false, position, content, new String[]{TopicDetailFragment.this.getResources().getString(R.string.copy), TopicDetailFragment.this.getResources().getString(R.string.delete)});
                            }
                        }
                    }
                }
            }
            return true;
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailFragment.2 */
    class C08132 implements OnClickListener {
        C08132() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_user_icon:
                    ((TopicDetailPagerActivity) TopicDetailFragment.this.getActivity()).startPersonalActivity(v);
                case R.id.tv_home_forward:
                    TopicMore.repostTopic(TopicDetailFragment.this.getActivity(), (TextView) v);
                case R.id.tv_home_more:
                    if (v.getTag() != null) {
                        TopicInfo topicInfo = (TopicInfo) v.getTag();
                        Intent intent = new Intent(TopicDetailFragment.this.getActivity(), ShareActivity.class);
                        intent.putExtra("title", Constant.PREFENCES_NAME);
                        intent.putExtra("content", topicInfo.getDesc());
                        intent.putExtra(Util.EXTRA_TOPICID, topicInfo.getTopicid());
                        intent.putExtra("topic_uid", topicInfo.getUid());
                        intent.putExtra("url", topicInfo.getContent());
                        intent.putExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID, topicInfo.getUid());
                        intent.putExtra("topicInfo", topicInfo);
                        String nickname = Constant.APP_CACHE_DIR_NAME2;
                        if (topicInfo.getUserinfo() != null) {
                            nickname = topicInfo.getUserinfo().getNickname();
                        }
                        intent.putExtra("publish_nickname", nickname);
                        TopicDetailFragment.this.startActivityForResult(intent, 0);
                        TopicDetailFragment.this.showLayerMask();
                        TopicDetailFragment.this.animationForBottom();
                    }
                default:
            }
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailFragment.3 */
    class C08143 implements OnTouchListener {
        C08143() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (TopicDetailPagerActivity.getInstance() != null) {
                TopicDetailPagerActivity.getInstance().hideSoftInput();
                TopicDetailPagerActivity.getInstance().et_topic_comment.setText(BaseConstants.ah);
                TopicDetailPagerActivity.getInstance().et_topic_comment.setHint(TopicDetailFragment.this.getResources().getString(R.string.topic_detail_send_hint));
                TopicDetailPagerActivity.getInstance().replyComment = null;
            }
            return false;
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailFragment.4 */
    class C08154 implements Runnable {
        C08154() {
        }

        public void run() {
            if (TopicDetailFragment.this.getActivity() != null) {
                ((TopicDetailPagerActivity) TopicDetailFragment.this.getActivity()).showCommentView();
            }
        }
    }

    class TopicFAVBroadcastReceiver extends BroadcastReceiver {
        TopicFAVBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(Constant.ADD_FAV_TOPIC_ACTION)) {
                String topicId = intent.getStringExtra("topicId");
                if (TopicDetailFragment.this.mTopicInfo != null && TopicDetailFragment.this.mTopicInfo.getTopicid().equals(topicId)) {
                    TopicDetailFragment.this.mTopicInfo.setIsfav(Integer.valueOf(1));
                }
            }
            if (intent != null && intent.getAction().equals(Constant.DEL_FAV_TOPIC_ACTION)) {
                String topicId = intent.getStringExtra("topicId");
                if (TopicDetailFragment.this.mTopicInfo != null && TopicDetailFragment.this.mTopicInfo.getTopicid().equals(topicId)) {
                    TopicDetailFragment.this.mTopicInfo.setIsfav(Integer.valueOf(0));
                }
            }
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailFragment.10 */
    class AnonymousClass10 extends QGHttpHandler<String> {
        AnonymousClass10(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(String data) {
            Toast.makeText(TopicDetailFragment.this.getActivity(), TopicDetailFragment.this.getResources().getString(R.string.report_success), Toast.LENGTH_LONG).show();
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailFragment.11 */
    class AnonymousClass11 extends QGHttpHandler<String> {
        private final /* synthetic */ String val$commentId;
        private final /* synthetic */ int val$pos;

        AnonymousClass11(Context $anonymous0, int i, String str) {

            super($anonymous0);
            this.val$pos = i;
            this.val$commentId = str;
            
        }

        public void onGetDataSuccess(String data) {
            TopicDetailFragment.this.mNewComments.remove(this.val$pos);
            TopicDetailFragment.this.mCommentAdpter.notifyDataSetChanged();
            TopicDetailFragment.this.delHotComment(this.val$commentId);
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailFragment.5 */
    class C17635 extends QGHttpHandler<TopicZanModel> {
        private final /* synthetic */ String val$direction;
        private final /* synthetic */ boolean val$notify;

        C17635(Context $anonymous0, String str, boolean z) {

            super($anonymous0);
            this.val$direction = str;
            this.val$notify = z;
            
        }

        public void onGetDataSuccess(TopicZanModel data) {
            if (data != null) {
                if (this.val$direction.equals(SearchActivity.UP)) {
                    TopicDetailFragment.this.zan_list.addAll(data.getList());
                }
                if (this.val$notify) {
                    TopicDetailFragment.this.mCommentAdpter.setZanList(TopicDetailFragment.this.zan_list);
                }
            }
        }

        public void onFinish() {
            TopicDetailFragment.this.mCommentLv.stopLoadMore();
            TopicDetailFragment.this.mCommentLv.stopRefresh();
            super.onFinish();
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailFragment.6 */
    class C17646 extends QGHttpHandler<TopicReciveRedPacketModel> {
        private final /* synthetic */ String val$direction;
        private final /* synthetic */ boolean val$notify;

        C17646(Context $anonymous0, String str, boolean z) {

            super($anonymous0);
            this.val$direction = str;
            this.val$notify = z;
            
        }

        public void onGetDataSuccess(TopicReciveRedPacketModel data) {
            if (data != null) {
                if (this.val$direction.equals(SearchActivity.UP)) {
                    TopicDetailFragment.this.recive_red_packet_list.addAll(data.getRecivelist());
                }
                TopicDetailFragment.this.total_num = data.getRedpacketinfo().getTotalnum();
                TopicDetailFragment.this.recive_num = data.getRedpacketinfo().getRecivenum();
                TopicDetailFragment.this.mRedpacketTitleView.setText(new StringBuilder(String.valueOf(TopicDetailFragment.this.getResources().getString(R.string.tv_topic_detail_redpaket_title))).append(" ").append(TopicDetailFragment.this.total_num).toString());
                TopicDetailFragment.this.mRecived_num.setText(new StringBuilder(String.valueOf(TopicDetailFragment.this.getResources().getString(R.string.tv_topic_detail_recive_redpaket))).append(TopicDetailFragment.this.recive_num).append(TopicDetailFragment.this.getResources().getString(R.string.tv_topic_detail_redpaket_num_end)).toString());
                if (this.val$notify) {
                    TopicDetailFragment.this.mCommentAdpter.setRedPaketList(TopicDetailFragment.this.recive_red_packet_list);
                }
                if (TopicDetailFragment.this.lookRedPacket) {
                    TopicDetailFragment.this.type = 2;
                    TopicDetailFragment.this.mCommentAdpter.setRedPaketList(TopicDetailFragment.this.recive_red_packet_list);
                    TopicDetailFragment.this.onRefresh();
                    TopicDetailFragment.this.lookRedPacket = false;
                }
            }
        }

        public void onFinish() {
            TopicDetailFragment.this.mCommentLv.stopLoadMore();
            TopicDetailFragment.this.mCommentLv.stopRefresh();
            super.onFinish();
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailFragment.7 */
    class C17657 extends QGHttpHandler<TopicInfoList> {
        C17657(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(TopicInfoList data) {
            if (TopicDetailFragment.this.getActivity() != null && !((TopicDetailPagerActivity) TopicDetailFragment.this.getActivity()).isFinish) {
                TopicInfo topicInfo = (TopicInfo) data.getList().get(0);
                if (topicInfo != null) {
                    TopicDetailFragment.this.parseData(topicInfo);
                    List<Comment> newCommentList = TopicDetailFragment.this.mTopicInfo.getCommentlist();
                    if (newCommentList != null && newCommentList.size() > 0) {
                        TopicDetailFragment.this.mNewComments.clear();
                        TopicDetailFragment.this.mNewComments.addAll(newCommentList);
                    }
                    TopicDetailFragment.this.mCommentTitleView.setText(new StringBuilder(String.valueOf(TopicDetailFragment.this.getResources().getString(R.string.tv_topic_detail_comment_title))).append(" ").append(TopicDetailFragment.this.mTopicInfo.getTotalcomment()).toString());
                    TopicDetailFragment.this.mZanTitleView.setText(new StringBuilder(String.valueOf(TopicDetailFragment.this.getResources().getString(R.string.news_notification_zan))).append(" ").append(TopicDetailFragment.this.mTopicInfo.getLikenum()).toString());
                }
                TopicDetailFragment.this.mCommentAdpter.notifyDataSetChanged();
//                TopicDetailFragment.this.mTopicAdapter.updateTopicFromNetWork(TopicDetailFragment.this.mListPos, TopicDetailFragment.this.mTopicInfo, 0, TopicDetailFragment.this.mOnClickListener);
                if (TopicDetailFragment.this.getActivity() != null) {
                    ((ITopicDisplay) TopicDetailFragment.this.getActivity()).getFlowHelper().startAvatarFlow(TopicDetailFragment.this.mListPos);
                }
            }
        }

        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailFragment.8 */
    class C17668 extends QGHttpHandler<NewCommentList> {
        private final /* synthetic */ String val$direction;

        C17668(Context $anonymous0, String str) {

            super($anonymous0);
            this.val$direction = str;
            
        }

        public void onGetDataSuccess(NewCommentList data) {
            if (SearchActivity.DOWN.equals(this.val$direction)) {
                if (TopicDetailFragment.this.mNewComments == null || TopicDetailFragment.this.mNewComments.size() <= 0) {
                    TopicDetailFragment.this.mNewComments.addAll(0, data.getCommentlist());
                    if (data.getCommentlist().size() == 0) {
                        TopTip.show(TopicDetailFragment.this.getActivity(), (RelativeLayout) TopicDetailFragment.this.getActivity().findViewById(R.id.container_rl), TopicDetailFragment.this.getString(R.string.topic_detail_tip_no_data), null, -1, TopicDetailFragment.this.getResources().getDimensionPixelOffset(R.dimen.action_bar_height));
                    } else {
                        TopTip.show(TopicDetailFragment.this.getActivity(), (RelativeLayout) TopicDetailFragment.this.getActivity().findViewById(R.id.container_rl), String.format(TopicDetailFragment.this.getString(R.string.topic_detail_tip_has_data), new Object[]{Integer.valueOf(data.getCommentlist().size())}), null, -1, TopicDetailFragment.this.getResources().getDimensionPixelOffset(R.dimen.action_bar_height));
                    }
                } else {
                    for (int i = TopicDetailFragment.this.mNewComments.size() - 1; i >= 0; i--) {
                        if (((Comment) TopicDetailFragment.this.mNewComments.get(i)).getCommentid() == null) {
                            TopicDetailFragment.this.mNewComments.remove(i);
                        }
                    }
                    TopicDetailFragment.this.mNewComments.addAll(data.getCommentlist());
                    TopicDetailFragment.this.mOnScrollListener.setFooterStopWork(false);
                    if (data.getCommentlist().size() == 0) {
                        TopicDetailFragment.this.mCommentLv.changeFooterHintNormalText(TopicDetailFragment.this.getResources().getString(R.string.footer_hint_normal_text));
                    }
                }
                TopicDetailFragment.this.mCommentAdpter.notifyDataSetChanged();
            }
        }

        public void onFinish() {
            TopicDetailFragment.this.mCommentLv.stopLoadMore();
            TopicDetailFragment.this.mCommentLv.stopRefresh();
            super.onFinish();
        }

        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        }
    }

    /* renamed from: com.gitrose.mobile.TopicDetailFragment.9 */
    class C17679 extends QGHttpHandler<Comment> {
        private final /* synthetic */ Comment val$comment;
        private final /* synthetic */ TopicInfo val$topicInfo;

        C17679(Context $anonymous0, TopicInfo topicInfo, Comment comment) {

            super($anonymous0);
            this.val$topicInfo = topicInfo;
            this.val$comment = comment;
            
        }

        public void onGetDataSuccess(Comment data) {
            if (TopicDetailFragment.this.getActivity() != null) {
//                MobclickAgent.onEvent(TopicDetailFragment.this.getActivity(), "send_comment_success_num");
            }
            if (data != null) {
                this.val$topicInfo.setUserComment(true);
//                if (GreenDaoUtils.getFriendInfo(TopicDetailFragment.this.getActivity(), this.val$comment.getTopicUid()) != null) {
//                    GreenDaoUtils.updateFriendInfo(TopicDetailFragment.this.getActivity(), this.val$comment.getTopicUid(), new StringBuilder(String.valueOf(System.currentTimeMillis())).toString());
//                }
            }
        }

        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            if (TopicDetailFragment.this.getActivity() != null) {
//                MobclickAgent.onEvent(TopicDetailFragment.this.getActivity(), "send_comment_err_num");
            }
            if (statusCode == 100019 || statusCode == 100020) {
                Toast.makeText(TopicDetailFragment.this.getActivity(), responseString, Toast.LENGTH_LONG).show();
                this.val$topicInfo.setUserComment(true);
                List<Comment> commentList = this.val$topicInfo.getCommentlist();
                this.val$topicInfo.setTotalcomment(this.val$topicInfo.getTotalcomment() - 1);
                this.val$topicInfo.getCommentlist().remove(commentList.size() - 1);
                if (commentList.size() > 0) {
//                    TopicDetailFragment.this.mTopicAdapter.notifyLastCommentPos(TopicDetailFragment.this.mListPos);
                    return;
                } else {
                    TopicDetailFragment.this.mTopicAdapter.notifyDataSetChanged();
                    return;
                }
            }
            TopicDetailFragment.this.mRequestDao.insertComment(this.val$comment);
        }
    }

    public TopicDetailFragment() {
        this.mNewComments = new ArrayList();
        this.broadcastReceiver = null;
        this.zan_list = new ArrayList();
        this.recive_red_packet_list = new ArrayList();
        this.len = 50;
        this.type = 0;
        this.onLongClickListener = new C08121();
        this.mOnClickListener = new C08132();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = inflater.inflate(R.layout.fragment_topic_detail, null);
        this.mHandler = new Handler();
        this.mRequestDao = new RequestDao(getActivity());
        if (getBundle() != null) {
            this.mTopicInfo = (TopicInfo) getBundle().getSerializable("topic");
            this.mListPos = getBundle().getInt("listPos", 0);
            this.isSelf = getBundle().getBoolean("isSelf");
            this.lookRedPacket = getBundle().getBoolean("lookRedPacket");
            if (this.lookRedPacket) {
                this.type = 2;
            }
        }
        this.container_rl = (RelativeLayout) contentView.findViewById(R.id.container_rl);
        this.mCommentLv = (XListView) contentView.findViewById(R.id.lv_topic_list);
        this.mCommentLv.setPullLoadEnable(true);
        this.mCommentLv.setPullRefreshEnable(true);
        if (this.mTopicInfo != null) {
            List<Comment> newCommentList = this.mTopicInfo.getCommentlist();
            if (newCommentList != null && newCommentList.size() > 0) {
                this.mNewComments.clear();
                this.mNewComments.addAll(newCommentList);
            }
            initHeadView();
            if (this.lookRedPacket) {
                this.mCommentAdpter = new CommentAdpter(getActivity(), this, this.onLongClickListener);
            } else {
                this.mCommentAdpter = new CommentAdpter(getActivity(), this.mNewComments, this, this.onLongClickListener);
            }
            this.mCommentLv.setAdapter(this.mCommentAdpter);
            this.mOnScrollListener = ((TopicDetailPagerActivity) getActivity()).getScrollListViewListener();
            this.mCommentLv.setOnScrollListener(this.mOnScrollListener);
            this.mCommentLv.setXListViewListener(this);
            this.mCommentLv.setOnTouchListener(new C08143());
            getTopicLikeList(this.mTopicInfo.getTopicid(), BaseConstants.f3860s, SearchActivity.UP, false);
            if (this.mTopicInfo.getIsredpacket() == null || BaseConstants.ah.equals(this.mTopicInfo.getIsredpacket()) || Integer.parseInt(this.mTopicInfo.getIsredpacket()) != 1) {
                this.mRedpacketTitleView.setVisibility(View.GONE);
                this.redPacket_divider_view.setVisibility(View.GONE);
            } else {
                this.redPacket_divider_view.setVisibility(View.VISIBLE);
                this.mRedpacketTitleView.setVisibility(View.VISIBLE);
                getTopicRedpacketList(this.mTopicInfo.getTopicid(), BaseConstants.f3860s, SearchActivity.UP, false);
            }
        }
        if (this.mListPos == TopicDetailPagerActivity.CLICK_FRAGMENT_POS) {
            requestTopicInfo();
            TopicDetailPagerActivity.CLICK_FRAGMENT_POS = -1;
        }
        this.mHandler.postDelayed(new C08154(), 200);
        registerReceiver();
        return contentView;
    }

    public void getTopicLikeList(String topicid, String startlikeflag, String direction, boolean notify) {
        QGHttpRequest.getInstance().getTopicLikeList(getActivity(), topicid, startlikeflag, direction, new StringBuilder(String.valueOf(this.len)).toString(), new C17635(getActivity(), direction, notify));
    }

    public void getTopicRedpacketList(String topicid, String startlikeflag, String direction, boolean notify) {
        QGHttpRequest.getInstance().getTopicReciveList(getActivity(), topicid, startlikeflag, direction, new StringBuilder(String.valueOf(this.len)).toString(), new C17646(getActivity(), direction, notify));
    }

    public void requestTopicInfo() {
        if (this.mTopicInfo != null && getActivity() != null) {
            String mTopicId = this.mTopicInfo.getTopicid();
//            this.mTopicAdapter.updateTopicFromNetWork(this.mListPos, this.mTopicInfo, 0, this.mOnClickListener);
            QGHttpRequest.getInstance().topicDetailRequest(getActivity(), mTopicId, null, new C17657(getActivity(), true, this.container_rl));
        }
    }

    protected void parseData(TopicInfo topicInfo) {
        if (this.mTopicInfo != null && topicInfo != null) {
            this.mTopicInfo.setAddtime(topicInfo.getAddtime());
            TutuUsers userinfo = new TutuUsers();
            userinfo.setUid(topicInfo.getUserinfo().getUid());
            userinfo.setAvatartime(topicInfo.getUserinfo().getAvatartime());
            userinfo.setNickname(topicInfo.getUserinfo().getNickname());
            userinfo.setRemarkname(topicInfo.getUserinfo().getRemarkname());
            this.mTopicInfo.setUserinfo(userinfo);
            this.mTopicInfo.setContent(topicInfo.getContent());
            this.mTopicInfo.setCreatetime(topicInfo.getCreatetime());
            this.mTopicInfo.setDesc(topicInfo.getDesc());
            this.mTopicInfo.setEmptycommenttxt(topicInfo.getEmptycommenttxt());
            this.mTopicInfo.setFromrepost(topicInfo.getFromrepost());
            this.mTopicInfo.setFormattime(topicInfo.getFormattime());
            this.mTopicInfo.setHeight(topicInfo.getHeight());
            this.mTopicInfo.setCommentlist(topicInfo.getCommentlist());
            this.mTopicInfo.setHuatilist(topicInfo.getHuatilist());
            this.mTopicInfo.setIs_del(topicInfo.getIs_del());
            this.mTopicInfo.setIsfav(topicInfo.getIsfav());
            this.mTopicInfo.setIskana(topicInfo.getIskana());
            this.mTopicInfo.setIslike(topicInfo.getIslike());
            this.mTopicInfo.setLocation(topicInfo.getLocation());
            this.mTopicInfo.setLikenum(topicInfo.getLikenum());
            this.mTopicInfo.setLocaltopicid(topicInfo.getLocaltopicid());
            this.mTopicInfo.setLocalViews(topicInfo.getLocalViews());
            this.mTopicInfo.setNewcount(topicInfo.getNewcount());
            this.mTopicInfo.setRepostnum(topicInfo.getRepostnum());
            this.mTopicInfo.setReposttopicid(topicInfo.getReposttopicid());
            this.mTopicInfo.setRepostuserinfo(topicInfo.getRepostuserinfo());
            this.mTopicInfo.setRoottopicid(topicInfo.getRoottopicid());
            this.mTopicInfo.setShowtype(topicInfo.getShowtype());
            this.mTopicInfo.setSize(topicInfo.getSize());
            this.mTopicInfo.setSmallcontent(topicInfo.getSmallcontent());
            this.mTopicInfo.setSpecialdata(topicInfo.getSpecialdata());
            this.mTopicInfo.setStatus(topicInfo.getStatus());
            this.mTopicInfo.setTopicid(topicInfo.getTopicid());
            this.mTopicInfo.setTopiclist(topicInfo.getTopiclist());
            this.mTopicInfo.setTimes(topicInfo.getTimes());
            this.mTopicInfo.setTotalcomment(topicInfo.getTotalcomment());
            this.mTopicInfo.setUpdatetime(topicInfo.getUpdatetime());
            this.mTopicInfo.setUserhonorlevel(topicInfo.getUserhonorlevel());
            this.mTopicInfo.setUserinfo(topicInfo.getUserinfo());
            this.mTopicInfo.setUid(topicInfo.getUid());
            this.mTopicInfo.setUserComment(topicInfo.isUserComment());
            this.mTopicInfo.setUserisrepost(topicInfo.getUserisrepost());
            this.mTopicInfo.setViews(topicInfo.getViews());
        }
    }

    private void initHeadView() {
        this.mCommentDetailView = View.inflate(getActivity(), R.layout.head_topic_detail, null);
        this.mCommentTriangleView = (ImageView) this.mCommentDetailView.findViewById(R.id.img_topic_detail_comment_triangle);
        this.mCommentTitleView = (TextView) this.mCommentDetailView.findViewById(R.id.tv_topic_detail_comment_title);
        this.mZanTitleView = (TextView) this.mCommentDetailView.findViewById(R.id.tv_topic_detail_zan_title);
        this.mRedpacketTitleView = (TextView) this.mCommentDetailView.findViewById(R.id.tv_topic_detail_redpacket_title);
        this.mCommentDividerView = this.mCommentDetailView.findViewById(R.id.v_topic_detail_comment_divider);
        this.mConvertView = this.mCommentDetailView.findViewById(R.id.rl_topic_item);
        this.redPacket_divider_view = this.mCommentDetailView.findViewById(R.id.topic_detail_redpacket_divder_v);
        this.mRecived_num = (TextView) this.mCommentDetailView.findViewById(R.id.tv_topic_detail_redpacket_recived_num);
        this.mCommentTitleView.setOnClickListener(this);
        this.mZanTitleView.setOnClickListener(this);
        this.mRedpacketTitleView.setOnClickListener(this);
        if (this.mTopicInfo != null) {
            this.mCommentTitleView.setText(new StringBuilder(String.valueOf(getResources().getString(R.string.tv_topic_detail_comment_title))).append(" ").append(this.mTopicInfo.getTotalcomment()).toString());
            this.mZanTitleView.setText(new StringBuilder(String.valueOf(getResources().getString(R.string.news_notification_zan))).append(" ").append(this.mTopicInfo.getLikenum()).toString());
        }
        new ArrayList(1).add(this.mTopicInfo);
//        this.mTopicAdapter = ((TopicDetailPagerActivity) getActivity()).getTopicAdapter();
//        this.mTopicAdapter.getView(this.mListPos, this.mConvertView, null);
        this.mCommentLv.addHeaderView(this.mCommentDetailView);
        refreshTitle();
    }

    public void onRefresh() {
        if (this.mTopicInfo != null) {
            refreshTitle();
            switch (this.type) {
                case 0:
                    String startCommentId = null;
                    if (this.mNewComments != null && this.mNewComments.size() > 0) {
                        startCommentId = ((Comment) this.mNewComments.get(0)).getCommentid();
                    }
                    requestCommentData(SearchActivity.UP, startCommentId);
                case 1:
                    String startlikeflag = BaseConstants.f3860s;
                    if (this.zan_list != null && this.zan_list.size() > 0) {
                        startlikeflag = ((TopicZanItemModel) this.zan_list.get(0)).getLikeflag();
                    }
                    getTopicLikeList(this.mTopicInfo.getTopicid(), startlikeflag, SearchActivity.UP, true);
                case 2:
                    String startReciveflag = BaseConstants.f3860s;
                    if (this.recive_red_packet_list != null && this.recive_red_packet_list.size() > 0) {
                        startReciveflag = ((TopicReciveItem) this.recive_red_packet_list.get(0)).getItemflag();
                    }
                    getTopicRedpacketList(this.mTopicInfo.getTopicid(), startReciveflag, SearchActivity.UP, true);
                default:
            }
        }
    }

    public void onLoadMore() {
        this.mOnScrollListener.setFooterStopWork(true);
        switch (this.type) {
            case 0:
                String lastCommentId = null;
                if (this.mNewComments != null && this.mNewComments.size() > 0) {
                    int i = this.mNewComments.size() - 1;
                    while (i >= 0) {
                        lastCommentId = ((Comment) this.mNewComments.get(i)).getCommentid();
                        if (lastCommentId == null || BaseConstants.ah.equals(lastCommentId)) {
                            i--;
                        }
                    }
                }
                requestCommentData(SearchActivity.DOWN, lastCommentId);
            case 1:
                String startlikeflag = BaseConstants.f3860s;
                if (this.zan_list != null && this.zan_list.size() > 0) {
                    startlikeflag = ((TopicZanItemModel) this.zan_list.get(this.zan_list.size() - 1)).getLikeflag();
                }
                getTopicLikeList(this.mTopicInfo.getTopicid(), startlikeflag, SearchActivity.DOWN, true);
            case 2:
                String startRedPacketflag = BaseConstants.f3860s;
                if (this.recive_red_packet_list != null && this.recive_red_packet_list.size() > 0) {
                    ((TopicReciveItem) this.recive_red_packet_list.get(this.recive_red_packet_list.size() - 1)).getItemflag();
                }
                getTopicRedpacketList(this.mTopicInfo.getTopicid(), startRedPacketflag, SearchActivity.DOWN, true);
            default:
        }
    }

    private void requestCommentData(String direction, String startCommentId) {
        QGHttpRequest.getInstance().newCommentListRequest(getActivity(), this.mTopicInfo.getTopicid(), startCommentId, 30, direction, new C17668(getActivity(), direction));
    }

    public static TopicDetailFragment newInstance() {
        return new TopicDetailFragment();
    }

    public void addComment(Comment comment) {
        this.mCommentLv.smoothScrollToPosition(1);
        this.mTopicInfo.setTotalcomment(this.mTopicInfo.getTotalcomment() + 1);
        List<Comment> hotComments = this.mTopicInfo.getCommentlist();
        if (hotComments == null) {
            hotComments = new ArrayList();
        }
        hotComments.add(comment);
        this.mTopicInfo.setCommentlist(hotComments);
//        this.mTopicAdapter.notifyLastCommentPos(this.mListPos, this.mTopicInfo);
        notifyNewComment(comment);
        uploadComment(comment, this.mTopicInfo);
    }

    private void notifyNewComment(Comment comment) {
        if (comment != null) {
            this.mNewComments.add(comment);
            this.mOnScrollListener.setFooterStopWork(false);
            this.mCommentAdpter.notifyDataSetChanged();
        }
    }

    private void uploadComment(Comment comment, TopicInfo topicInfo) {
        if (NetUtils.checkNet(getActivity())) {
            if (getActivity() != null) {
//                MobclickAgent.onEvent(getActivity(), "send_comment_total_num");
            }
            QGHttpRequest.getInstance().addComment(getActivity(), comment.getTopicId(), comment.getContent(), comment.getReplyCommentId(), comment.getTxtframe(), new C17679(getActivity(), topicInfo, comment));
            return;
        }
        this.mRequestDao.insertComment(comment);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_topic_detail_comment_title:
                refreshComment();
            case R.id.tv_topic_detail_zan_title:
                this.type = 1;
                this.mCommentAdpter.setZanList(this.zan_list);
                onRefresh();
            case R.id.tv_topic_detail_redpacket_title:
                refreshRedPacket();
            case R.id.img_comment_more:
                showCommentPop(v);
            case R.id.fl_comment_content:
            case R.id.tv_comment_content:
            case R.id.ll_reply_comment:
                Comment comment = (Comment) v.getTag();
                int iskana = comment.getIskana();
                if (comment != null) {
                    String name;
                    if (iskana == 0) {
                        String remarkname = comment.getUserinfo().getRemarkname();
                        if (remarkname == null || BaseConstants.ah.equals(remarkname)) {
                            name = comment.getUserinfo().getNickname();
                        } else {
                            name = remarkname;
                        }
                    } else {
                        name = getActivity().getString(R.string.no_name_tip);
                    }
                    if (TopicDetailPagerActivity.getInstance() != null) {
                        TopicDetailPagerActivity.getInstance().et_topic_comment.setHint(new StringBuilder(String.valueOf(getResources().getString(R.string.comment_reply_tip))).append(name).append(":").toString());
                        TopicDetailPagerActivity.getInstance().replyComment = comment;
                        TopicDetailPagerActivity.getInstance().showSoftInput();
                    }
                }
            case R.id.tv_pop_comment_del:
                if (((Integer) v.getTag()).intValue() == 1) {
                    delNewComment();
                } else {
                    reportComment();
                }
            case R.id.tv_pop_comment_comment:
                replyComment();
            default:
        }
    }

    public void refreshComment() {
        this.type = 0;
        this.mCommentAdpter.setCommentList(this.mNewComments);
        onRefresh();
    }

    public void refreshRedPacket() {
        this.type = 2;
        this.mCommentAdpter.setRedPaketList(this.recive_red_packet_list);
        onRefresh();
    }

    private void refreshTitle() {
        switch (this.type) {
            case 0:
                this.mCommentTitleView.setTextColor(getResources().getColor(R.color.setting_title_text_color));
                this.mZanTitleView.setTextColor(getResources().getColor(R.color.action_bar_bg));
                this.mRedpacketTitleView.setTextColor(getResources().getColor(R.color.action_bar_bg));
            case 1:
                this.mCommentTitleView.setTextColor(getResources().getColor(R.color.action_bar_bg));
                this.mZanTitleView.setTextColor(getResources().getColor(R.color.setting_title_text_color));
                this.mRedpacketTitleView.setTextColor(getResources().getColor(R.color.action_bar_bg));
            case 2:
                this.mCommentTitleView.setTextColor(getResources().getColor(R.color.action_bar_bg));
                this.mZanTitleView.setTextColor(getResources().getColor(R.color.action_bar_bg));
                this.mRedpacketTitleView.setTextColor(getResources().getColor(R.color.setting_title_text_color));
            default:
        }
    }

    public void backComment(boolean flag) {
        int[] location = new int[2];
        if (this.mCommentTriangleView != null) {
            this.mCommentTriangleView.getLocationInWindow(location);
        }
        if (flag) {
            this.mCommentLv.smoothScrollBy(location[1], 0);
        } else {
            this.mCommentLv.smoothScrollBy(location[1], location[1]);
        }
    }

    private void replyComment() {
        int pos = ((Integer) this.mCommentPopView.getTag()).intValue();
        Comment comment = (Comment) this.mNewComments.get(pos);
        if (comment != null) {
            TopicMore.clickTopicIntent(getActivity(), this.mTopicInfo, pos, comment, ((ITopicDisplay) getActivity()).getMediaPlayer().getCurrentPosition());
        }
        this.mCommentPop.dismiss();
    }

    private void reportComment() {
        Comment comment = (Comment) this.mNewComments.get(((Integer) this.mCommentPopView.getTag()).intValue());
        if (comment != null) {
            QGHttpRequest.getInstance().reportCommentRequest(getActivity(), comment.getCommentid(), new AnonymousClass10(getActivity()));
            this.mCommentPop.dismiss();
        }
    }

    private void delNewComment() {
        int pos = ((Integer) this.mCommentPopView.getTag()).intValue();
        Comment comment = (Comment) this.mNewComments.get(pos);
        if (comment != null) {
            String commentId = comment.getCommentid();
            QGHttpRequest.getInstance().delCommentRequest(getActivity(), this.mTopicInfo.getTopicid(), commentId, new AnonymousClass11(getActivity(), pos, commentId));
        }
        this.mCommentPop.dismiss();
    }

    private void showCommentPop(View view) {
        int pos = ((Integer) view.getTag()).intValue();
        Comment comment = (Comment) this.mNewComments.get(pos);
        if (comment == null) {
            return;
        }
        if (MyApplication.getInstance().isLogin()) {
            StateListDrawable drawable;
            if (this.mCommentPopView == null) {
                this.mCommentPopView = View.inflate(getActivity(), R.layout.pop_topic_detail_comment, null);
                this.mCommentPopView.findViewById(R.id.tv_pop_comment_comment).setOnClickListener(this);
            }
            TextView mChangeTv = (TextView) this.mCommentPopView.findViewById(R.id.tv_pop_comment_del);
            mChangeTv.setOnClickListener(this);
            if (comment.getUid().equals(MyApplication.getInstance().getUserinfo().getUid())) {
                mChangeTv.setText(getResources().getString(R.string.delete));
                mChangeTv.setTag(Integer.valueOf(1));
                drawable = (StateListDrawable) getResources().getDrawable(R.drawable.img_topic_detail_pop_del_selector);
            } else {
                mChangeTv.setText(getResources().getString(R.string.report));
                mChangeTv.setTag(Integer.valueOf(0));
                drawable = (StateListDrawable) getResources().getDrawable(R.drawable.img_topic_detail_pop_report_selector);
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mChangeTv.setCompoundDrawables(drawable, null, null, null);
            this.mCommentPopView.setTag(Integer.valueOf(pos));
            if (this.mCommentPop == null) {
                this.mCommentPop = new TipPop();
            }
            this.mCommentPop.showLeft(this.mCommentPopView, view, 10);
            return;
        }
        TopicMore.startLoginActivity(getActivity());
    }

    public void delHotComment(String commentId) {
        List<Comment> hotComments = this.mTopicInfo.getCommentlist();
        if (hotComments != null) {
            for (int i = 0; i < hotComments.size(); i++) {
                if (commentId.equals(((Comment) hotComments.get(i)).getCommentid())) {
                    hotComments.remove(i);
//                    this.mTopicAdapter.updateTopic(this.mListPos, -1, this.mTopicInfo);
                    return;
                }
            }
        }
    }

    public void delNewComment(String commentId) {
        int i = 0;
        while (i < this.mNewComments.size()) {
            Comment comment = (Comment) this.mNewComments.get(i);
            if (comment == null || !commentId.equals(comment.getCommentid())) {
                i++;
            } else {
                this.mNewComments.remove(i);
                this.mCommentAdpter.notifyDataSetChanged();
                return;
            }
        }
    }

    public XListView getCommentLv() {
        return this.mCommentLv;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public View getConvertView() {
        return this.mConvertView;
    }

    private void showCopyPop(View view, boolean isReply, int position, String commentContent, String[] menu) {
        this.copyPop = new TipPop(getActivity(), menu, new AnonymousClass12(commentContent, view, position, isReply));
        view.setSelected(true);
        this.copyPop.show(view);
    }

    @SuppressLint({"NewApi"})
    private void copyContent(String content) {
        if (VERSION.SDK_INT > 11) {
            ((ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE)).setText(content);
        } else {
            ((android.text.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE)).setText(content);
        }
        Toast.makeText(getActivity(), getResources().getString(R.string.copy_success), Toast.LENGTH_LONG).show();
    }

    public void showLayerMask() {
        View view = new View(getActivity());
        view.setLayoutParams(new LayoutParams(-2, -2));
        if (this.mDialog == null) {
            this.mDialog = new BaseDialog(getActivity());
        }
        this.mDialog.show(view);
    }

    public void onResume() {
        super.onResume();
        if (this.mDialog != null && this.mDialog.isShowing()) {
            this.mDialog.dismiss();
        }
    }

    public void animationForBottom() {
        Constant.animation_1 = R.anim.main_translatey100to0;
        Constant.animation_2 = R.anim.main_translatey0tof100;
        getActivity().overridePendingTransition(R.anim.main_translatey100to0, R.anim.main_translatey0tof100);
    }

    protected void registerReceiver() {
        this.broadcastReceiver = new TopicFAVBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ADD_FAV_TOPIC_ACTION);
        filter.addAction(Constant.DEL_FAV_TOPIC_ACTION);
        getActivity().registerReceiver(this.broadcastReceiver, filter);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.broadcastReceiver != null) {
            getActivity().unregisterReceiver(this.broadcastReceiver);
            this.broadcastReceiver = null;
        }
    }
}
