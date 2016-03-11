package com.gitrose.mobile.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.nostra13.universalimageloader.core.ImageLoader;
//import com.tencent.connect.common.Constants;
//import com.tencent.connect.share.QzoneShare;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.R;
import com.gitrose.mobile.LoginActivity;
//import com.gitrose.mobile.PersonalNewActivity;
import com.gitrose.mobile.TopicDetailActivity;
import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.adapter.HomeFollowAdapter;
import com.gitrose.mobile.adapter.HomeTopicAdapter;
import com.gitrose.mobile.application.ActivityManager;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.Comment;
import com.gitrose.mobile.model.Praise;
//import com.gitrose.mobile.model.ReciveRedPacketModel;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.utils.PlaySound;
//import com.gitrose.mobile.utils.WalletUtil;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//import org.apache.http.Header;
import qalsdk.BaseConstants;

public class TopicMore {
    public static long lastClickTime;
    public static int[] mLocations;

    /* renamed from: com.gitrose.mobile.view.TopicMore.10 */
    class AnonymousClass10 implements AnimationListener {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ View val$view;

        AnonymousClass10(Context context, View view) {
            this.val$context = context;
            this.val$view = view;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            this.val$view.startAnimation(AnimationUtils.loadAnimation(this.val$context, R.anim.fav_scale_150to100));
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.18 */
    class AnonymousClass18 implements OnClickListener {
        private final /* synthetic */ BaseDialog val$dialog;
        private final /* synthetic */ HomeFollowAdapter val$followAdapter;
        private final /* synthetic */ HomeTopicAdapter val$hotTopicadapter;
        private final /* synthetic */ boolean val$isdetailActiivty;
        private final /* synthetic */ ImageView val$redPacketTextView;

        AnonymousClass18(BaseDialog baseDialog, boolean z, HomeFollowAdapter homeFollowAdapter, ImageView imageView, HomeTopicAdapter homeTopicAdapter) {
            this.val$dialog = baseDialog;
            this.val$isdetailActiivty = z;
            this.val$followAdapter = homeFollowAdapter;
            this.val$redPacketTextView = imageView;
            this.val$hotTopicadapter = homeTopicAdapter;
        }

        public void onClick(View arg0) {
            this.val$dialog.dismiss();
            if (this.val$isdetailActiivty) {
                if (this.val$followAdapter != null) {
                    this.val$followAdapter.refreshPaperActivityRedpack();
                }
            } else if (this.val$followAdapter != null) {
                this.val$followAdapter.startTopDetailActivity(this.val$redPacketTextView, true, true);
            } else if (this.val$hotTopicadapter != null) {
                this.val$hotTopicadapter.startTopDetailActivity(this.val$redPacketTextView, true, true);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.19 */
    class AnonymousClass19 implements OnClickListener {
        private final /* synthetic */ BaseDialog val$dialog;

        AnonymousClass19(BaseDialog baseDialog) {
            this.val$dialog = baseDialog;
        }

        public void onClick(View arg0) {
            this.val$dialog.dismiss();
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.8 */
    static class C09618 implements AnimationListener {
        private final /* synthetic */ int val$XDelta;
        private final /* synthetic */ int val$YDelta;
        private final /* synthetic */ TextView val$arriveView;
        private final /* synthetic */ int val$favCount;
        private final /* synthetic */ ImageView val$favImg;
        private final /* synthetic */ boolean val$isFav;

        C09618(TextView textView, ImageView imageView, int i, boolean z, int i2, int i3) {
            this.val$arriveView = textView;
            this.val$favImg = imageView;
            this.val$favCount = i;
            this.val$isFav = z;
            this.val$XDelta = i2;
            this.val$YDelta = i3;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            TopicMore.outToBottomAnimation(this.val$arriveView, this.val$favImg, this.val$favCount, this.val$isFav, this.val$XDelta, this.val$YDelta);
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.9 */
    class C09629 implements AnimationListener {
        private final /* synthetic */ int val$favCount;
        private final /* synthetic */ ImageView val$favImg;
        private final /* synthetic */ boolean val$isFav;
        private final /* synthetic */ TextView val$textView;

        C09629(TextView textView, int i, boolean z, ImageView imageView) {
            this.val$textView = textView;
            this.val$favCount = i;
            this.val$isFav = z;
            this.val$favImg = imageView;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            this.val$textView.setText(new StringBuilder(String.valueOf(this.val$favCount)).toString());
            this.val$textView.setSelected(this.val$isFav);
            this.val$favImg.setVisibility(View.INVISIBLE);
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.11 */
    class AnonymousClass11 extends QGHttpHandler<Praise> {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ TextView val$repostTextView;
        private final /* synthetic */ TopicInfo val$topicInfo;

        AnonymousClass11(Context $anonymous0, TopicInfo topicInfo, TextView textView, Context context) {

            super($anonymous0);
            this.val$topicInfo = topicInfo;
            this.val$repostTextView = textView;
            this.val$context = context;

        }

        public void onGetDataSuccess(Praise data) {
            int repostCount = data.getRepostnum();
            this.val$topicInfo.setRepostnum(repostCount);
            this.val$topicInfo.setUserisrepost(1);
            this.val$repostTextView.setSelected(true);
            this.val$repostTextView.setText(repostCount);
            Toast.makeText(this.val$context, this.val$context.getResources().getString(R.string.repost_topic_succ), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Constant.REPOST_TOPIC_ACTION);
            intent.putExtra("topic_repost_topicId", this.val$topicInfo.getTopicid());
            this.val$context.sendBroadcast(intent);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            this.val$topicInfo.setUserisrepost(0);
            this.val$repostTextView.setSelected(false);
            Toast.makeText(this.val$context, this.val$context.getResources().getString(R.string.wuwu_un_repost), Toast.LENGTH_LONG).show();
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.12 */
    class AnonymousClass12 extends QGHttpHandler<Praise> {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ TextView val$repostTextView;
        private final /* synthetic */ TopicInfo val$topicInfo;

        AnonymousClass12(Context $anonymous0, TopicInfo topicInfo, TextView textView, Context context) {

            super($anonymous0);
            this.val$topicInfo = topicInfo;
            this.val$repostTextView = textView;
            this.val$context = context;

        }

        public void onGetDataSuccess(Praise data) {
            int repostCount = data.getRepostnum();
            this.val$topicInfo.setRepostnum(repostCount);
            this.val$topicInfo.setUserisrepost(0);
            this.val$repostTextView.setSelected(false);
            this.val$repostTextView.setText(repostCount == 0 ? this.val$context.getString(R.string.home_forward_tip) : new StringBuilder(String.valueOf(repostCount)).toString());
            Toast.makeText(this.val$context, this.val$context.getResources().getString(R.string.del_repost_topic_succ), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Constant.DEL_REPOST_TOPIC_ACTION);
            intent.putExtra("topic_repost_topicId", this.val$topicInfo.getTopicid());
            this.val$context.sendBroadcast(intent);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            this.val$topicInfo.setUserisrepost(1);
            this.val$repostTextView.setSelected(true);
            Toast.makeText(this.val$context, this.val$context.getResources().getString(R.string.wuwu_un_cancel_repost), Toast.LENGTH_LONG).show();
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.13 */
    class AnonymousClass13 extends QGHttpHandler<String> {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ ImageView val$followImageView;
        private final /* synthetic */ String val$isauth;
        private final /* synthetic */ TopicInfo val$topicInfo;
        private final /* synthetic */ TutuUsers val$users;

        AnonymousClass13(Context $anonymous0, String str, ImageView imageView, TutuUsers tutuUsers, TopicInfo topicInfo, Context context) {

            super($anonymous0);
            this.val$isauth = str;
            this.val$followImageView = imageView;
            this.val$users = tutuUsers;
            this.val$topicInfo = topicInfo;
            this.val$context = context;

        }

        public void onGetDataSuccess(String data) {
            if (this.val$isauth != null && Integer.parseInt(this.val$isauth) == 1) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_auth_1);
            } else if (this.val$isauth == null || Integer.parseInt(this.val$isauth) != 2) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_1);
            } else {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_daren_1);
            }
//            this.val$users.setRelation(Constants.VIA_SSO_LOGIN);
//            this.val$topicInfo.setUserinfo(this.val$users);
//            TopicMore.sendFollowBroadcast(this.val$context, this.val$users.getUid(), Constants.VIA_SSO_LOGIN, Constant.FOLLOW_USER_ACTION);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            if (this.val$isauth != null && Integer.parseInt(this.val$isauth) == 1) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_auth_0);
            } else if (this.val$isauth == null || Integer.parseInt(this.val$isauth) != 2) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_0);
            } else {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_daren_0);
            }
            this.val$users.setRelation(BaseConstants.f3860s);
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.14 */
    class AnonymousClass14 extends QGHttpHandler<String> {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ ImageView val$followImageView;
        private final /* synthetic */ String val$isauth;
        private final /* synthetic */ TopicInfo val$topicInfo;
        private final /* synthetic */ TutuUsers val$users;

        AnonymousClass14(Context $anonymous0, String str, ImageView imageView, TutuUsers tutuUsers, TopicInfo topicInfo, Context context) {

            super($anonymous0);
            this.val$isauth = str;
            this.val$followImageView = imageView;
            this.val$users = tutuUsers;
            this.val$topicInfo = topicInfo;
            this.val$context = context;

        }

        public void onGetDataSuccess(String data) {
            if (this.val$isauth != null && Integer.parseInt(this.val$isauth) == 1) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_auth_2);
            } else if (this.val$isauth == null || Integer.parseInt(this.val$isauth) != 2) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_2);
            } else {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_daren_2);
            }
//            this.val$users.setRelation(Constants.VIA_TO_TYPE_QQ_DISCUSS_GROUP);
//            this.val$topicInfo.setUserinfo(this.val$users);
//            TopicMore.sendFollowBroadcast(this.val$context, this.val$users.getUid(), Constants.VIA_TO_TYPE_QQ_DISCUSS_GROUP, Constant.FOLLOW_USER_ACTION);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            if (this.val$isauth != null && Integer.parseInt(this.val$isauth) == 1) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_auth_0);
            } else if (this.val$isauth == null || Integer.parseInt(this.val$isauth) != 2) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_0);
            } else {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_daren_0);
            }
            this.val$users.setRelation(Constant.SYSTEM_UID);
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.15 */
    class AnonymousClass15 extends QGHttpHandler<String> {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ ImageView val$followImageView;
        private final /* synthetic */ String val$isauth;
        private final /* synthetic */ TopicInfo val$topicInfo;
        private final /* synthetic */ TutuUsers val$users;

        AnonymousClass15(Context $anonymous0, String str, ImageView imageView, TutuUsers tutuUsers, TopicInfo topicInfo, Context context) {

            super($anonymous0);
            this.val$isauth = str;
            this.val$followImageView = imageView;
            this.val$users = tutuUsers;
            this.val$topicInfo = topicInfo;
            this.val$context = context;

        }

        public void onGetDataSuccess(String data) {
            if (this.val$isauth != null && Integer.parseInt(this.val$isauth) == 1) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_auth_0);
            } else if (this.val$isauth == null || Integer.parseInt(this.val$isauth) != 2) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_0);
            } else {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_daren_0);
            }
            this.val$users.setRelation(BaseConstants.f3860s);
            this.val$topicInfo.setUserinfo(this.val$users);
            TopicMore.sendFollowBroadcast(this.val$context, this.val$users.getUid(), BaseConstants.f3860s, Constant.UNFOLLOW_USER_ACTION);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            if (this.val$isauth != null && Integer.parseInt(this.val$isauth) == 1) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_auth_1);
            } else if (this.val$isauth == null || Integer.parseInt(this.val$isauth) != 2) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_1);
            } else {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_daren_1);
            }
//            this.val$users.setRelation(Constants.VIA_SSO_LOGIN);
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.16 */
    class AnonymousClass16 extends QGHttpHandler<String> {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ ImageView val$followImageView;
        private final /* synthetic */ String val$isauth;
        private final /* synthetic */ TopicInfo val$topicInfo;
        private final /* synthetic */ TutuUsers val$users;

        AnonymousClass16(Context $anonymous0, String str, ImageView imageView, TutuUsers tutuUsers, TopicInfo topicInfo, Context context) {

            super($anonymous0);
            this.val$isauth = str;
            this.val$followImageView = imageView;
            this.val$users = tutuUsers;
            this.val$topicInfo = topicInfo;
            this.val$context = context;

        }

        public void onGetDataSuccess(String data) {
            if (this.val$isauth != null && Integer.parseInt(this.val$isauth) == 1) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_auth_0);
            } else if (this.val$isauth == null || Integer.parseInt(this.val$isauth) != 2) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_0);
            } else {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_daren_0);
            }
            this.val$users.setRelation(Constant.SYSTEM_UID);
            this.val$topicInfo.setUserinfo(this.val$users);
            TopicMore.sendFollowBroadcast(this.val$context, this.val$users.getUid(), Constant.SYSTEM_UID, Constant.UNFOLLOW_USER_ACTION);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            if (this.val$isauth != null && Integer.parseInt(this.val$isauth) == 1) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_auth_2);
            } else if (this.val$isauth == null || Integer.parseInt(this.val$isauth) != 2) {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_2);
            } else {
                this.val$followImageView.setImageResource(R.drawable.user_follow_status_daren_2);
            }
//            this.val$users.setRelation(Constants.VIA_TO_TYPE_QQ_DISCUSS_GROUP);
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.1 */
    class C18141 extends QGHttpHandler<String> {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ BaseDialog val$dialog;
        private final /* synthetic */ String val$uid;

        C18141(Context $anonymous0, String str, Context context, BaseDialog baseDialog) {

            super($anonymous0);
            this.val$uid = str;
            this.val$context = context;
            this.val$dialog = baseDialog;

        }

        public void onGetDataSuccess(String data) {
            Intent intent = new Intent();
            intent.setAction(Constant.BLOCK_TOPIC_ACTION);
            intent.putExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID, this.val$uid);
            this.val$context.sendBroadcast(intent);
            this.val$dialog.dismiss();
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.20 */
//    class AnonymousClass20 extends QGHttpHandler<ReciveRedPacketModel> {
//        private final /* synthetic */ ImageView val$avatar_iv;
//        private final /* synthetic */ Context val$context;
//        private final /* synthetic */ BaseDialog val$dialog;
//        private final /* synthetic */ View val$dialog_view;
//        private final /* synthetic */ ImageView val$is_auth_iv;
//        private final /* synthetic */ TextView val$money_des;
//        private final /* synthetic */ TextView val$money_num_tv;
//        private final /* synthetic */ TextView val$nickname_tv;
//        private final /* synthetic */ TopicInfo val$topicInfo;
//        private final /* synthetic */ TextView val$zan_tv;
//
//        /* renamed from: com.gitrose.mobile.view.TopicMore.20.1 */
//        class C09601 implements OnClickListener {
//            private final /* synthetic */ Context val$context;
//            private final /* synthetic */ BaseDialog val$dialog;
//            private final /* synthetic */ TopicInfo val$topicInfo;
//
//            C09601(BaseDialog baseDialog, Context context, TopicInfo topicInfo) {
//                this.val$dialog = baseDialog;
//                this.val$context = context;
//                this.val$topicInfo = topicInfo;
//            }
//
//            public void onClick(View arg0) {
//                this.val$dialog.dismiss();
//                Intent intent = new Intent();
//                intent.setClass(this.val$context, PersonalNewActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(PersonalNewActivity.PERSONAL_USER_ID, this.val$topicInfo.getUid());
//                intent.putExtras(bundle);
//                this.val$context.startActivity(intent);
//                TopicMore.animationForNew(this.val$context);
//            }
//        }
//
//        AnonymousClass20(Context $anonymous0, TextView textView, TopicInfo topicInfo, BaseDialog baseDialog, View view, ImageView imageView, TextView textView2, Context context, TextView textView3, TextView textView4, ImageView imageView2) {
//
//            super($anonymous0);
//            this.val$zan_tv = textView;
//            this.val$topicInfo = topicInfo;
//            this.val$dialog = baseDialog;
//            this.val$dialog_view = view;
//            this.val$avatar_iv = imageView;
//            this.val$nickname_tv = textView2;
//            this.val$context = context;
//            this.val$money_num_tv = textView3;
//            this.val$money_des = textView4;
//            this.val$is_auth_iv = imageView2;
//
//        }
//
//        public void onGetDataSuccess(ReciveRedPacketModel data) {
//            if (data != null) {
//                this.val$zan_tv.setText(new StringBuilder(String.valueOf(data.getLikenum())).toString());
//                this.val$topicInfo.setLikenum(data.getLikenum());
//                this.val$zan_tv.setSelected(true);
//                this.val$dialog.show(this.val$dialog_view);
//                ImageLoader.getInstance().displayImage(ImageUtils.getAvatarUrl(data.getUserinfo().getUid(), data.getUserinfo().getAvatartime()), this.val$avatar_iv, Constant.AVATAR_OPTIONS);
//                this.val$avatar_iv.setOnClickListener(new C09601(this.val$dialog, this.val$context, this.val$topicInfo));
//                String nick = data.getUserinfo().getNickname();
//                if (!(data.getUserinfo().getRemarkname() == null || data.getUserinfo().getRemarkname().equals(BaseConstants.ah))) {
//                    nick = data.getUserinfo().getRemarkname();
//                }
//                if (nick.length() > 7) {
//                    nick = nick.substring(0, 6) + "...";
//                }
//                this.val$nickname_tv.setText(new StringBuilder(String.valueOf(nick)).append(this.val$context.getResources().getString(R.string.red_packet_nickname_end)).toString());
//                if (data.getType() == null || !data.getType().equals("success")) {
//                    this.val$money_num_tv.setVisibility(View.GONE);
//                } else {
//                    this.val$money_num_tv.setVisibility(View.VISIBLE);
//                    this.val$money_num_tv.setText(new StringBuilder(String.valueOf(this.val$context.getResources().getString(R.string.money_sign))).append(WalletUtil.getMoney2DecimalPoint(data.getMoney())).toString());
//                }
//                this.val$money_des.setText(data.getDesc());
//                String isauth = data.getUserinfo().getIsauth();
//                this.val$is_auth_iv.setVisibility(View.VISIBLE);
//                if (isauth != null && Integer.parseInt(isauth) == 1) {
//                    this.val$is_auth_iv.setImageResource(R.drawable.personal_isauth);
//                } else if (isauth == null || Integer.parseInt(isauth) != 2) {
//                    this.val$is_auth_iv.setVisibility(View.GONE);
//                } else {
//                    this.val$is_auth_iv.setImageResource(R.drawable.personal_daren);
//                }
//            }
//        }
//    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.2 */
    class C18152 extends QGHttpHandler<String> {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ BaseDialog val$dialog;
        private final /* synthetic */ String val$uid;

        C18152(Context $anonymous0, String str, Context context, BaseDialog baseDialog) {

            super($anonymous0);
            this.val$uid = str;
            this.val$context = context;
            this.val$dialog = baseDialog;

        }

        public void onGetDataSuccess(String data) {
            Intent intent = new Intent();
            intent.setAction(Constant.UNBLOCK_TOPIC_ACTION);
            intent.putExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID, this.val$uid);
            this.val$context.sendBroadcast(intent);
            this.val$dialog.dismiss();
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.3 */
    class C18163 extends QGHttpHandler<String> {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ BaseDialog val$dialog;
        private final /* synthetic */ String val$topicId;
        private final /* synthetic */ TopicInfo val$topicInfo;

        C18163(Context $anonymous0, TopicInfo topicInfo, Context context, BaseDialog baseDialog, String str) {

            super($anonymous0);
            this.val$topicInfo = topicInfo;
            this.val$context = context;
            this.val$dialog = baseDialog;
            this.val$topicId = str;

        }

        public void onGetDataSuccess(String data) {
            this.val$topicInfo.setIsfav(Integer.valueOf(1));
            Toast.makeText(this.val$context, this.val$context.getResources().getString(R.string.fav_success), Toast.LENGTH_LONG).show();
            this.val$dialog.dismiss();
            Intent intent = new Intent();
            intent.putExtra("topicId", this.val$topicId);
            intent.setAction(Constant.ADD_FAV_TOPIC_ACTION);
            this.val$context.sendBroadcast(intent);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            Toast.makeText(this.val$context, this.val$context.getResources().getString(R.string.fav_fail), Toast.LENGTH_LONG).show();
            this.val$dialog.dismiss();
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.4 */
    class C18174 extends QGHttpHandler<String> {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ BaseDialog val$dialog;
        private final /* synthetic */ String val$topicId;
        private final /* synthetic */ TopicInfo val$topicInfo;

        C18174(Context $anonymous0, TopicInfo topicInfo, Context context, BaseDialog baseDialog, String str) {

            super($anonymous0);
            this.val$topicInfo = topicInfo;
            this.val$context = context;
            this.val$dialog = baseDialog;
            this.val$topicId = str;

        }

        public void onGetDataSuccess(String data) {
            this.val$topicInfo.setIsfav(Integer.valueOf(0));
            Toast.makeText(this.val$context, this.val$context.getResources().getString(R.string.cancel_fav_success), Toast.LENGTH_LONG).show();
            this.val$dialog.dismiss();
            Intent intent = new Intent();
            intent.putExtra("topicId", this.val$topicId);
            intent.setAction(Constant.DEL_FAV_TOPIC_ACTION);
            this.val$context.sendBroadcast(intent);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            Toast.makeText(this.val$context, this.val$context.getResources().getString(R.string.cancel_fav_fail), Toast.LENGTH_LONG).show();
            this.val$dialog.dismiss();
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.5 */
    class C18185 extends QGHttpHandler<String> {
        private final /* synthetic */ BaseDialog val$dialog;

        C18185(Context $anonymous0, BaseDialog baseDialog) {

            super($anonymous0);
            this.val$dialog = baseDialog;

        }

        public void onGetDataSuccess(String data) {
            this.val$dialog.dismiss();
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.6 */
    static class C18196 extends QGHttpHandler<Praise> {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ TextView val$favTextView;
        private final /* synthetic */ TopicInfo val$topicInfo;

        C18196(Context $anonymous0, TopicInfo topicInfo, TextView textView, Context context) {

            super($anonymous0);
            this.val$topicInfo = topicInfo;
            this.val$favTextView = textView;
            this.val$context = context;

        }

        public void onGetDataSuccess(Praise data) {
            int favCount = data.getLikecount();
            this.val$topicInfo.setLikenum(favCount);
            this.val$topicInfo.setIslike(1);
            TopicMore.startScaleAnim(this.val$context, this.val$favTextView, (ImageView) ((View) this.val$favTextView.getParent().getParent()).findViewById(R.id.img_topic_fav_anim), favCount, true);
            TopicMore.sendTopicZanBroadcast(this.val$context, this.val$topicInfo.getTopicid(), 1);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            this.val$topicInfo.setIslike(0);
            Toast.makeText(this.val$context, this.val$context.getResources().getString(R.string.wuwu_un_zan), Toast.LENGTH_LONG).show();
        }
    }

    /* renamed from: com.gitrose.mobile.view.TopicMore.7 */
    static class C18207 extends QGHttpHandler<Praise> {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ TextView val$favTextView;
        private final /* synthetic */ TopicInfo val$topicInfo;

        C18207(Context $anonymous0, TopicInfo topicInfo, TextView textView, Context context) {

            super($anonymous0);
            this.val$topicInfo = topicInfo;
            this.val$favTextView = textView;
            this.val$context = context;

        }

        public void onGetDataSuccess(Praise data) {
            int favCount = data.getLikecount();
            this.val$topicInfo.setIslike(0);
            this.val$topicInfo.setLikenum(favCount);
            TopicMore.startScaleAnim(this.val$context, this.val$favTextView, (ImageView) ((View) this.val$favTextView.getParent().getParent()).findViewById(R.id.img_topic_fav_anim), favCount, false);
            TopicMore.sendTopicZanBroadcast(this.val$context, this.val$topicInfo.getTopicid(), 0);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            this.val$topicInfo.setIslike(1);
            Toast.makeText(this.val$context, this.val$context.getResources().getString(R.string.wuwu_un_cancel_zan), Toast.LENGTH_LONG).show();
        }
    }

    private TopicMore() {
    }

    public static void blockTopic(Context context, BaseDialog dialog, String uid) {
//        if (!MyApplication.getInstance().isLogin()) {
//            startLoginActivity(context);
//            dialog.dismiss();
//        } else if (MyApplication.getInstance().getUserinfo().getUid().equals(uid)) {
//            Toast.makeText(context, "\u4e0d\u80fd\u5c4f\u853d\u81ea\u5df1", Toast.LENGTH_LONG).show();
//        } else {
//            QGHttpRequest.getInstance().blockUserTopic(context, uid, new C18141(context, uid, context, dialog));
//        }
    }

    public static void unBlockTopic(Context context, BaseDialog dialog, String uid) {
//        if (MyApplication.getInstance().isLogin()) {
//            QGHttpRequest.getInstance().unblockUserTopic(context, uid, new C18152(context, uid, context, dialog));
//            return;
//        }
//        startLoginActivity(context);
//        dialog.dismiss();
    }

    public static void takeFav(Context context, BaseDialog dialog, TopicInfo topicInfo) {
//        if (MyApplication.getInstance().isLogin()) {
//            String topicId = topicInfo.getTopicid();
//            Integer isFav = topicInfo.getIsfav();
//            if (isFav == null) {
//                isFav = Integer.valueOf(0);
//            }
//            if (isFav.intValue() == 0) {
//                QGHttpRequest.getInstance().addFavoriteRequest(context, topicId, new C18163(context, topicInfo, context, dialog, topicId));
//                return;
//            } else {
//                QGHttpRequest.getInstance().delFavoriteRequest(context, topicId, new C18174(context, topicInfo, context, dialog, topicId));
//                return;
//            }
//        }
//        startLoginActivity(context);
//        dialog.dismiss();
    }

    public static void reportTopic(Context context, BaseDialog dialog, String topicId) {
//        if (MyApplication.getInstance().isLogin()) {
//            QGHttpRequest.getInstance().reportTopicRequest(context, topicId, new C18185(context, dialog));
//            return;
//        }
//        startLoginActivity(context);
//        dialog.dismiss();
    }

    public static void startLoginActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
    }

    public static void favTopic(Context context, TextView favTextView) {
        if (MyApplication.getInstance().isLogin()) {
            TopicInfo topicInfo = (TopicInfo) favTextView.getTag(R.id.topic_fav_topicinfo);
            if (topicInfo.getIslike() == 0) {
                QGHttpRequest.getInstance().getLikeTopicRequest(context, topicInfo.getTopicid(), new C18196(context, topicInfo, favTextView, context));
                return;
            } else {
                QGHttpRequest.getInstance().getDislikeTopicRequest(context, topicInfo.getTopicid(), new C18207(context, topicInfo, favTextView, context));
                return;
            }
        }
        startLoginActivity(context);
    }

    private static void sendTopicZanBroadcast(Context mContext, String topicId, int status) {
        Intent intent = new Intent(Constant.ZAN_TOPIC_ACTION);
        intent.putExtra("topic_zan_topicId", topicId);
        intent.putExtra("topic_zan_status", status);
        mContext.sendBroadcast(intent);
    }

    static {
        mLocations = new int[2];
    }

    public static void startScaleAnim(Context context, TextView arriveView, ImageView favImg, int favCount, boolean isFav) {
        if (favImg != null && arriveView != null) {
            favImg.setSelected(isFav);
            favImg.setVisibility(View.VISIBLE);
            arriveView.getLocationOnScreen(mLocations);
            int arriveX = mLocations[0];
            int arriveY = mLocations[1];
            favImg.getLocationOnScreen(mLocations);
            int XDelta = ((arriveX - mLocations[0]) - (favImg.getWidth() / 2)) + 50;
            int YDelta = (arriveY - mLocations[1]) + ((favImg.getHeight() + arriveView.getHeight()) + 20);
            Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.fav_scale_100to150);
            scaleAnimation.setAnimationListener(new C09618(arriveView, favImg, favCount, isFav, XDelta, YDelta));
            favImg.startAnimation(scaleAnimation);
        }
    }

    private static void outToBottomAnimation(TextView textView, ImageView favImg, int favCount, boolean isFav, int XDelta, int YDelta) {
//        if (favImg != null && textView != null) {
//            AnimationSet set = new AnimationSet(true);
//            set.setDuration(800);
//            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, (float) XDelta, 0.0f, (float) YDelta);
//            ScaleAnimation scaleAnimation = new ScaleAnimation(1.5f, 0.5f, 1.5f, 0.5f, 1, 0.5f, 1, 0.5f);
//            AlphaAnimation alphAnimation = new AlphaAnimation(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, 0.0f);
//            set.addAnimation(translateAnimation);
//            set.addAnimation(scaleAnimation);
//            set.addAnimation(alphAnimation);
//            set.setAnimationListener(new C09629(textView, favCount, isFav, favImg));
//            favImg.startAnimation(set);
//        }
    }

    public static void clickTopicIntent(Context context, TopicInfo topicInfo, int picWidth, int picHeight, int itemPosition, int position, String imageUrl, float[] point, Comment comment, int videoPosition, String videoUrl, int videoLength, String topicUid) {
//        if (!MyApplication.getInstance().isLogin()) {
//            startLoginActivity(context);
//        } else if (comment != null && !BaseConstants.ah.equals(comment)) {
//            Activity activity = ActivityManager.getScreenManager().currentActivity();
//            if (activity != null && activity.getComponentName().getClassName().equals("com.gitrose.mobile.TopicDetailPagerActivity")) {
//                TopicDetailPagerActivity.getInstance().setReplyCommentData(comment);
//            } else if (activity == null || !activity.getComponentName().getClassName().equals("com.gitrose.mobile.TopicDetailActivity")) {
//                List<TopicInfo> topics = new ArrayList();
//                topics.add(topicInfo);
//                PlaySound.getInstance(context).toPlay(R.raw.comment);
//                Intent intent = new Intent(context, TopicDetailPagerActivity.class);
//                intent.putExtra("topics", (Serializable) topics);
//                intent.putExtra("listPosition", itemPosition);
//                intent.putExtra("picWidth", picWidth);
//                intent.putExtra("picHeight", picHeight);
//                intent.putExtra("comment", comment);
//                intent.putExtra(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
//                intent.putExtra("point", point);
//                intent.putExtra("position", position);
//                intent.putExtra("videoPosition", videoPosition);
//                intent.putExtra("videoUrl", videoUrl);
//                intent.putExtra("videoTimes", videoLength);
//                intent.putExtra("topicUid", topicUid);
//                ((Activity) context).startActivityForResult(intent, 0);
//            } else {
//                TopicDetailActivity.getInstance().setReplyCommentData(comment);
//            }
//        }
    }

    public static void clickTopicIntent(Context context, TopicInfo topicInfo, int position, Comment comment, int videoPosition) {
        if (System.currentTimeMillis() - lastClickTime >= 2000) {
            lastClickTime = System.currentTimeMillis();
            clickTopicIntent(context, topicInfo, Integer.parseInt(topicInfo.getWidth()), Integer.parseInt(topicInfo.getHeight()), 0, position, topicInfo.getContent(), new float[]{0.5f, 0.5f}, comment, videoPosition, topicInfo.getVideourl(), topicInfo.getVideotimes(), topicInfo.getUid());
        }
    }

    public static void clickTopicIntent(Context context, TopicInfo topicInfo, int listPos, int position, Comment comment, int videoPosition, float[] points) {
        if (System.currentTimeMillis() - lastClickTime >= 2000) {
            lastClickTime = System.currentTimeMillis();
            clickTopicIntent(context, topicInfo, Integer.parseInt(topicInfo.getWidth()), Integer.parseInt(topicInfo.getHeight()), listPos, position, topicInfo.getContent(), points, comment, videoPosition, topicInfo.getVideourl(), topicInfo.getVideotimes(), topicInfo.getUid());
        }
    }

    public static void favAnim(Context context, View view) {
//        Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.fav_scale_100to150);
//        scaleAnimation.setAnimationListener(new AnonymousClass10(context, view));
//        view.startAnimation(scaleAnimation);
    }

    public static void repostTopic(Context context, TextView repostTextView) {
//        if (MyApplication.getInstance().isLogin()) {
//            TopicInfo topicInfo = (TopicInfo) repostTextView.getTag(R.id.topic_repost_topicinfo);
//            if (topicInfo.getUserisrepost() == 0) {
//                QGHttpRequest.getInstance().repostTopic(context, topicInfo.getTopicid(), new AnonymousClass11(context, topicInfo, repostTextView, context));
//                return;
//            } else {
//                QGHttpRequest.getInstance().delRepostTopic(context, topicInfo.getTopicid(), new AnonymousClass12(context, topicInfo, repostTextView, context));
//                return;
//            }
//        }
        startLoginActivity(context);
    }

    public static void followUser(Context context, ImageView followImageView) {
//        if (MyApplication.getInstance().isLogin()) {
//            TopicInfo topicInfo = (TopicInfo) followImageView.getTag(R.id.add_follow_user);
//            if (topicInfo != null && !BaseConstants.ah.equals(topicInfo)) {
//                TutuUsers users = topicInfo.getUserinfo();
//                if (users != null && !BaseConstants.ah.equals(users)) {
//                    String isauth = users.getIsauth();
//                    String relation = users.getRelation();
//                    if (relation != null && BaseConstants.f3860s.equals(relation)) {
//                        QGHttpRequest.getInstance().addFollowRequest(context, users.getUid(), new AnonymousClass13(context, isauth, followImageView, users, topicInfo, context));
//                        return;
//                    } else if (relation != null && Constant.SYSTEM_UID.equals(relation)) {
//                        QGHttpRequest.getInstance().addFollowRequest(context, users.getUid(), new AnonymousClass14(context, isauth, followImageView, users, topicInfo, context));
//                        return;
//                    } else if (relation != null && Constants.VIA_SSO_LOGIN.equals(relation)) {
//                        QGHttpRequest.getInstance().delFollowRequest(context, users.getUid(), new AnonymousClass15(context, isauth, followImageView, users, topicInfo, context));
//                        return;
//                    } else if (relation != null && Constants.VIA_TO_TYPE_QQ_DISCUSS_GROUP.equals(relation)) {
//                        QGHttpRequest.getInstance().delFollowRequest(context, users.getUid(), new AnonymousClass16(context, isauth, followImageView, users, topicInfo, context));
//                        return;
//                    } else {
//                        return;
//                    }
//                }
//                return;
//            }
//            return;
//        }
        startLoginActivity(context);
    }

    private static void sendFollowBroadcast(Context mContext, String userId, String relation, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra("to_uid", userId);
        intent.putExtra("relation", relation);
        mContext.sendBroadcast(intent);
    }

    public static void reciveRedPacket(Context context, ImageView redPacketTextView, HomeFollowAdapter followAdapter, HomeTopicAdapter hotTopicadapter, OnClickListener onclickListener, boolean isdetailActiivty) {
//        if (MyApplication.getInstance().isLogin()) {
//            TopicInfo topicInfo = (TopicInfo) redPacketTextView.getTag(R.id.topic_red_packet_topicinfo);
//            TextView zan_tv = (TextView) redPacketTextView.getTag(R.id.topic_red_packet_zan_tv);
//            BaseDialog dialog = new BaseDialog(context, 17);
//            View dialog_view = View.inflate(context, R.layout.dialog_redpacket, null);
//            ImageView avatar_iv = (ImageView) dialog_view.findViewById(R.id.dialog_redpaket_avatar_iv);
//            TextView nickname_tv = (TextView) dialog_view.findViewById(R.id.dialog_redpaket_nickname_tv);
//            TextView money_num_tv = (TextView) dialog_view.findViewById(R.id.dialog_redpaket_recive_money_num_tv);
//            TextView money_des = (TextView) dialog_view.findViewById(R.id.dialog_redpaket_recive_money_des_tv);
//            ImageView is_auth_iv = (ImageView) dialog_view.findViewById(R.id.dialog_redpaket_iv_isauth);
//            dialog_view.findViewById(R.id.dialog_redpaket_recive_money_ll).setOnClickListener(new OnClickListener() {
//                public void onClick(View arg0) {
//                }
//            });
//            dialog_view.findViewById(R.id.dialog_redpaket_look_more).setOnClickListener(new AnonymousClass18(dialog, isdetailActiivty, followAdapter, redPacketTextView, hotTopicadapter));
//            dialog_view.findViewById(R.id.ll_pop_content).setOnClickListener(new AnonymousClass19(dialog));
//            String topicid = topicInfo.getTopicid();
//            QGHttpRequest.getInstance().reciveRedPacketTopic(context, r18, new AnonymousClass20(context, zan_tv, topicInfo, dialog, dialog_view, avatar_iv, nickname_tv, context, money_num_tv, money_des, is_auth_iv));
//            return;
//        }
//        startLoginActivity(context);
    }

    private static void animationForNew(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
    }
}
