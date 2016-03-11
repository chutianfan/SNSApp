package com.gitrose.mobile.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
//import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
//import com.sea_monster.core.network.ApiReqeust;
import com.gitrose.mobile.C0306R;
import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.model.RCExtraModel;
import com.gitrose.mobile.utils.TextUtil;
import com.gitrose.mobile.view.RoundProgressBar;
//import io.rong.imlib.RongIMClient.Message;
//import io.rong.imlib.RongIMClient.SentStatus;
//import io.rong.message.BuildConfig;
//import io.rong.message.ImageMessage;
//import io.rong.message.RichContentMessage;
//import io.rong.message.TextMessage;
//import io.rong.message.VoiceMessage;
import java.util.Date;
//import shouji.gexing.framework.utils.DebugUtils;
//import shouji.gexing.framework.utils.FaceUtils;
//import shouji.gexing.framework.utils.TimeUtils;

//public class RCChatAdapter extends ArrayListAdapter<Message> {
//    private static /* synthetic */ int[] $SWITCH_TABLE$io$rong$imlib$RongIMClient$SentStatus;
//    public ImageGetter getter;
//    private Gson gson;
//    DisplayImageOptions mediaImageOptions;
//    private OnClickListener onClickListener;
//    private OnLongClickListener onLongClickListener;
//    private String target_avatar;
//    String to_User_avatartime;
//
//    /* renamed from: com.gitrose.mobile.adapter.RCChatAdapter.1 */
//    class C03431 implements ImageGetter {
//        C03431() {
//        }
//
//        public Drawable getDrawable(String source) {
//            Drawable d = RCChatAdapter.this.mContext.getResources().getDrawable(Integer.parseInt(source));
//            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//            return d;
//        }
//    }
//
//    private static class ViewHolder {
//        ImageView avatar_iv_left;
//        ImageView avatar_iv_right;
//        TextView content_tv_left;
//        TextView content_tv_right;
//        ImageView iv_chat_image_left;
//        ImageView iv_chat_image_right;
//        ImageView iv_chat_voice_left;
//        ImageView iv_chat_voice_right;
//        ImageView iv_failed;
//        ImageView iv_media_left_pic;
//        ImageView iv_media_right_pic;
//        ImageView iv_system_avatar;
//        ImageView iv_system_pic;
//        LinearLayout ll_chat_media_left;
//        LinearLayout ll_chat_media_right;
//        LinearLayout ll_media_left;
//        LinearLayout ll_media_right;
//        LinearLayout ll_system_media;
//        LinearLayout ll_system_text;
//        RoundProgressBar progress_bar;
//        RelativeLayout progress_rl;
//        TextView progress_tv;
//        ProgressBar right_pb;
//        RelativeLayout rl_chat_image_left;
//        RelativeLayout rl_chat_image_right;
//        RelativeLayout rl_chat_voice_left;
//        RelativeLayout rl_chat_voice_right;
//        RelativeLayout rl_media_left_look;
//        RelativeLayout rl_media_right_look;
//        RelativeLayout rl_system_look;
//        TextView sys_tv;
//        TextView time_tv_left;
//        TextView time_tv_right;
//        TextView tv_media_left_content;
//        TextView tv_media_left_look;
//        TextView tv_media_left_title;
//        TextView tv_media_right_content;
//        TextView tv_media_right_look;
//        TextView tv_media_right_title;
//        TextView tv_system_content;
//        TextView tv_system_look;
//        TextView tv_system_time;
//        TextView tv_system_title;
//        TextView tv_voice_duration_left;
//        TextView tv_voice_duration_right;
//
//        private ViewHolder() {
//        }
//    }
//
//    static /* synthetic */ int[] $SWITCH_TABLE$io$rong$imlib$RongIMClient$SentStatus() {
//        int[] iArr = $SWITCH_TABLE$io$rong$imlib$RongIMClient$SentStatus;
//        if (iArr == null) {
//            iArr = new int[SentStatus.values().length];
//            try {
//                iArr[SentStatus.DESTROYED.ordinal()] = 6;
//            } catch (NoSuchFieldError e) {
//            }
//            try {
//                iArr[SentStatus.FAILED.ordinal()] = 2;
//            } catch (NoSuchFieldError e2) {
//            }
//            try {
//                iArr[SentStatus.READ.ordinal()] = 5;
//            } catch (NoSuchFieldError e3) {
//            }
//            try {
//                iArr[SentStatus.RECEIVED.ordinal()] = 4;
//            } catch (NoSuchFieldError e4) {
//            }
//            try {
//                iArr[SentStatus.SENDING.ordinal()] = 1;
//            } catch (NoSuchFieldError e5) {
//            }
//            try {
//                iArr[SentStatus.SENT.ordinal()] = 3;
//            } catch (NoSuchFieldError e6) {
//            }
//            $SWITCH_TABLE$io$rong$imlib$RongIMClient$SentStatus = iArr;
//        }
//        return iArr;
//    }
//
//    public RCChatAdapter(Context context, OnClickListener onClickListener, OnLongClickListener onLongClickListener) {
//        super(context);
//        this.mediaImageOptions = new Builder().showImageForEmptyUri((int) C0306R.drawable.default_pic_bg).showImageOnFail((int) C0306R.drawable.chat_media_default_bg).cacheInMemory(true).cacheOnDisk(true).displayer(new RoundedBitmapDisplayer(10)).build();
//        this.getter = new C03431();
//        this.onClickListener = onClickListener;
//        this.onLongClickListener = onLongClickListener;
//        this.gson = new Gson();
//    }
//
//    public void setTargetAvatarTime(String avatar) {
//        this.target_avatar = avatar;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//        RCExtraModel model;
//        ImageMessage imageMessage;
//        VoiceMessage voiceMessage;
//        ViewHolder holder;
//        Uri ThumUri;
//        RichContentMessage richContentMessage;
//        Message message = (Message) this.mList.get(position);
//        String sendUserId = message.getSenderUserId();
//        boolean is_system = false;
//        boolean is_image = false;
//        if (message.getContent() instanceof TextMessage) {
//            TextMessage textmessage = (TextMessage) message.getContent();
//            if (textmessage.getExtra() != null) {
//                model = (RCExtraModel) this.gson.fromJson(textmessage.getExtra(), RCExtraModel.class);
//                if (!(model == null || model.getCounter() == null || !model.getCounter().equals("isSystem"))) {
//                    is_system = true;
//                }
//            }
//        } else {
//            if (message.getContent() instanceof ImageMessage) {
//                imageMessage = (ImageMessage) message.getContent();
//                is_image = true;
//                if (imageMessage.getExtra() != null) {
//                    model = (RCExtraModel) this.gson.fromJson(imageMessage.getExtra(), RCExtraModel.class);
//                    if (!(model == null || model.getCounter() == null || !model.getCounter().equals("isSystem"))) {
//                        is_system = true;
//                    }
//                }
//            } else {
//                if (message.getContent() instanceof VoiceMessage) {
//                    voiceMessage = (VoiceMessage) message.getContent();
//                    if (voiceMessage.getExtra() != null) {
//                        model = (RCExtraModel) this.gson.fromJson(voiceMessage.getExtra(), RCExtraModel.class);
//                        if (!(model == null || model.getCounter() == null || !model.getCounter().equals("isSystem"))) {
//                            is_system = true;
//                        }
//                    }
//                } else {
//                    if (message.getContent() instanceof RichContentMessage) {
//                        RichContentMessage richMessage = (RichContentMessage) message.getContent();
//                        if (richMessage.getExtra() != null) {
//                            model = (RCExtraModel) this.gson.fromJson(richMessage.getExtra(), RCExtraModel.class);
//                            if (!(model == null || model.getCounter() == null || !model.getCounter().equals("isSystem"))) {
//                                is_system = true;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        if (!(MyApplication.getInstance().getUserinfo().getUid() == null || sendUserId == null)) {
//            if (sendUserId.equals(MyApplication.getInstance().getUserinfo().getUid())) {
//                if (convertView == null || convertView.findViewById(C0306R.id.private_chat_item_right_iv_avatar) == null) {
//                    holder = new ViewHolder();
//                    convertView = View.inflate(this.mContext, C0306R.layout.activity_private_chat_item_right, null);
//                    holder.avatar_iv_right = (ImageView) convertView.findViewById(C0306R.id.private_chat_item_right_iv_avatar);
//                    holder.content_tv_right = (TextView) convertView.findViewById(C0306R.id.private_chat_item_right_tv_content);
//                    holder.time_tv_right = (TextView) convertView.findViewById(C0306R.id.private_chat_item_right_tv_time);
//                    holder.ll_media_right = (LinearLayout) convertView.findViewById(C0306R.id.ll_media_right);
//                    holder.iv_media_right_pic = (ImageView) convertView.findViewById(C0306R.id.iv_media_right_pic);
//                    holder.tv_media_right_title = (TextView) convertView.findViewById(C0306R.id.tv_media_right_title);
//                    holder.tv_media_right_content = (TextView) convertView.findViewById(C0306R.id.tv_media_right_content);
//                    holder.ll_chat_media_right = (LinearLayout) convertView.findViewById(C0306R.id.ll_chat_media_right);
//                    holder.rl_media_right_look = (RelativeLayout) convertView.findViewById(C0306R.id.rl_media_right_look);
//                    holder.tv_media_right_look = (TextView) convertView.findViewById(C0306R.id.tv_media_right_look);
//                    holder.rl_chat_image_right = (RelativeLayout) convertView.findViewById(C0306R.id.rl_chat_image_right);
//                    holder.iv_chat_image_right = (ImageView) convertView.findViewById(C0306R.id.iv_chat_image_right);
//                    holder.rl_chat_voice_right = (RelativeLayout) convertView.findViewById(C0306R.id.rl_chat_voice_right);
//                    holder.iv_chat_voice_right = (ImageView) convertView.findViewById(C0306R.id.iv_chat_voice_right);
//                    holder.tv_voice_duration_right = (TextView) convertView.findViewById(C0306R.id.tv_voice_duration_right);
//                    holder.right_pb = (ProgressBar) convertView.findViewById(C0306R.id.private_chat_item_right_tv_pb);
//                    holder.iv_failed = (ImageView) convertView.findViewById(C0306R.id.private_chat_item_right_iv_failed);
//                    holder.progress_tv = (TextView) convertView.findViewById(C0306R.id.iv_chat_image_right_tv_progress);
//                    holder.progress_rl = (RelativeLayout) convertView.findViewById(C0306R.id.iv_chat_image_right_pb_rl);
//                    holder.progress_bar = (RoundProgressBar) convertView.findViewById(C0306R.id.iv_chat_image_right_pb_loading);
//                    convertView.setTag(holder);
//                } else {
//                    holder = (ViewHolder) convertView.getTag();
//                }
//                String time = new StringBuilder(String.valueOf(message.getSentTime())).toString();
//                if (!(MyApplication.getInstance() == null || MyApplication.getInstance().getUserinfo() == null)) {
//                    time = MyApplication.getInstance().getUserinfo().getAvatartime();
//                }
//                String avat_url = new StringBuilder(ConstantURL.AVATAR_DOMAIN).append(sendUserId).append("/").append(time).append(Constant.HEAD_BIG_SIZE).toString();
//                ImageLoader.getInstance().displayImage(avat_url, holder.avatar_iv_right, Constant.AVATAR_OPTIONS);
//                holder.avatar_iv_right.setOnClickListener(this.onClickListener);
//                holder.avatar_iv_right.setTag(message);
//                holder.right_pb.setVisibility(4);
//                holder.iv_failed.setVisibility(4);
//                holder.iv_failed.setOnClickListener(this.onClickListener);
//                holder.iv_failed.setTag(message);
//                switch ($SWITCH_TABLE$io$rong$imlib$RongIMClient$SentStatus()[message.getSentStatus().ordinal()]) {
//                    case TopicDetailPagerActivity.FLOW_FIRST_WHAT /*1*/:
//                        if (!is_image) {
//                            holder.right_pb.setVisibility(0);
//                            break;
//                        }
//                        break;
//                    case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
//                        holder.iv_failed.setVisibility(0);
//                        break;
//                    case ApiReqeust.PUT_METHOD /*3*/:
//                        holder.right_pb.setVisibility(4);
//                        holder.iv_failed.setVisibility(4);
//                        holder.progress_rl.setVisibility(8);
//                        break;
//                }
//                if (message.getSentTime() != 0) {
//                    holder.time_tv_right.setText(TimeUtils.getShowTimeString2(new Date(message.getSentTime()), "yyyy-MM-dd HH:mm"));
//                    if (position == 0) {
//                        holder.time_tv_right.setVisibility(0);
//                    } else {
//                        try {
//                            if (TimeUtils.getBetweenMins2(((Message) this.mList.get(position - 1)).getSentTime(), ((Message) this.mList.get(position)).getSentTime()) > 5.0d) {
//                                holder.time_tv_right.setVisibility(0);
//                            } else {
//                                holder.time_tv_right.setVisibility(8);
//                            }
//                        } catch (Exception e) {
//                            holder.time_tv_right.setVisibility(8);
//                        }
//                    }
//                } else {
//                    holder.time_tv_right.setVisibility(8);
//                }
//                if (message.getContent() instanceof TextMessage) {
//                    holder.content_tv_right.setVisibility(0);
//                    holder.ll_media_right.setVisibility(8);
//                    holder.rl_chat_image_right.setVisibility(8);
//                    holder.rl_chat_voice_right.setVisibility(8);
//                    CharSequence ss = TextUtil.formatContent(Html.fromHtml(FaceUtils.getCharSequence(((TextMessage) message.getContent()).getContent()), this.getter, null), this.mContext);
//                    holder.content_tv_right.setMovementMethod(LinkMovementMethod.getInstance());
//                    holder.content_tv_right.setText(ss);
//                    holder.content_tv_right.setTag(C0306R.id.private_chat_converview, Integer.valueOf(position));
//                    holder.content_tv_right.setOnLongClickListener(this.onLongClickListener);
//                } else {
//                    if (message.getContent() instanceof ImageMessage) {
//                        holder.content_tv_right.setVisibility(8);
//                        holder.ll_media_right.setVisibility(8);
//                        holder.rl_chat_image_right.setVisibility(0);
//                        holder.rl_chat_voice_right.setVisibility(8);
//                        imageMessage = (ImageMessage) message.getContent();
//                        ThumUri = imageMessage.getThumUri();
//                        DebugUtils.error("ThumUri:" + ThumUri + "    pos" + position);
//                        if (ThumUri != null || imageMessage.getRemoteUri() == null) {
//                            if (holder.iv_chat_image_right != null) {
//                                holder.iv_chat_image_right.setImageURI(ThumUri);
//                            }
//                        } else if (holder.iv_chat_image_right != null) {
//                            ImageLoader.getInstance().displayImage(imageMessage.getRemoteUri().toString(), holder.iv_chat_image_right);
//                        }
//                        holder.rl_chat_image_right.setTag(Integer.valueOf(position));
//                        holder.rl_chat_image_right.setOnClickListener(this.onClickListener);
//                        holder.rl_chat_image_right.setTag(C0306R.id.private_chat_converview, Integer.valueOf(position));
//                        holder.rl_chat_image_right.setOnLongClickListener(this.onLongClickListener);
//                    } else {
//                        if (message.getContent() instanceof VoiceMessage) {
//                            holder.content_tv_right.setVisibility(8);
//                            holder.ll_media_right.setVisibility(8);
//                            holder.rl_chat_image_right.setVisibility(8);
//                            holder.rl_chat_voice_right.setVisibility(0);
//                            voiceMessage = (VoiceMessage) message.getContent();
//                            int duration = voiceMessage.getDuration();
//                            holder.tv_voice_duration_right.setText(new StringBuilder(String.valueOf(duration)).append("\"").toString());
//                            Uri voiceUri = voiceMessage.getUri();
//                            holder.rl_chat_voice_right.setTag(Integer.valueOf(position));
//                            holder.rl_chat_voice_right.setOnClickListener(this.onClickListener);
//                            holder.rl_chat_voice_right.setTag(C0306R.id.private_chat_converview, Integer.valueOf(position));
//                            holder.rl_chat_voice_right.setOnLongClickListener(this.onLongClickListener);
//                        } else {
//                            if (message.getContent() instanceof RichContentMessage) {
//                                holder.content_tv_right.setVisibility(8);
//                                holder.ll_media_right.setVisibility(0);
//                                holder.rl_chat_image_right.setVisibility(8);
//                                holder.rl_chat_voice_right.setVisibility(8);
//                                richContentMessage = (RichContentMessage) message.getContent();
//                                if (richContentMessage != null) {
//                                    if (!BuildConfig.FLAVOR.equals(richContentMessage)) {
//                                        ImageLoader.getInstance().displayImage(richContentMessage.getUrl(), holder.iv_media_right_pic, this.mediaImageOptions);
//                                        holder.tv_media_right_title.setText(richContentMessage.getTitle());
//                                        holder.tv_media_right_content.setText(richContentMessage.getContent());
//                                        model = (RCExtraModel) new Gson().fromJson(richContentMessage.getExtra(), RCExtraModel.class);
//                                        if (!(model == null || model.getContentLink() == null)) {
//                                            holder.rl_media_right_look.setTag(model.getContentLink());
//                                            holder.rl_media_right_look.setOnClickListener(this.onClickListener);
//                                        }
//                                        if (!(model == null || model.getButtonlink() == null)) {
//                                            holder.tv_media_right_look.setTag(model.getButtonlink());
//                                            holder.tv_media_right_look.setOnClickListener(this.onClickListener);
//                                        }
//                                        if (!(model == null || model.getButtonlink() == null)) {
//                                            holder.ll_chat_media_right.setTag(model.getButtonlink());
//                                            holder.ll_chat_media_right.setOnClickListener(this.onClickListener);
//                                        }
//                                        holder.ll_media_right.setTag(C0306R.id.private_chat_converview, Integer.valueOf(position));
//                                        holder.ll_media_right.setOnLongClickListener(this.onLongClickListener);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                convertView.setOnClickListener(this.onClickListener);
//                return convertView;
//            }
//        }
//        if (is_system) {
//            if (convertView == null || convertView.findViewById(C0306R.id.ll_system_text) == null) {
//                holder = new ViewHolder();
//                convertView = View.inflate(this.mContext, C0306R.layout.activity_private_chat_item_system, null);
//                holder.ll_system_text = (LinearLayout) convertView.findViewById(C0306R.id.ll_system_text);
//                holder.sys_tv = (TextView) convertView.findViewById(C0306R.id.private_chat_item_system_tv_content);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            if (message.getContent() instanceof TextMessage) {
//                TextMessage tm = (TextMessage) message.getContent();
//                holder.sys_tv.setText(tm.getContent());
//            }
//        } else {
//            if (convertView == null || convertView.findViewById(C0306R.id.private_chat_item_left_iv_avatar) == null) {
//                holder = new ViewHolder();
//                convertView = View.inflate(this.mContext, C0306R.layout.activity_private_chat_item_left, null);
//                holder.avatar_iv_left = (ImageView) convertView.findViewById(C0306R.id.private_chat_item_left_iv_avatar);
//                holder.content_tv_left = (TextView) convertView.findViewById(C0306R.id.private_chat_item_left_tv_content);
//                holder.time_tv_left = (TextView) convertView.findViewById(C0306R.id.private_chat_item_left_tv_time);
//                holder.ll_media_left = (LinearLayout) convertView.findViewById(C0306R.id.ll_media_left);
//                holder.iv_media_left_pic = (ImageView) convertView.findViewById(C0306R.id.iv_media_left_pic);
//                holder.tv_media_left_title = (TextView) convertView.findViewById(C0306R.id.tv_media_left_title);
//                holder.tv_media_left_content = (TextView) convertView.findViewById(C0306R.id.tv_media_left_content);
//                holder.ll_chat_media_left = (LinearLayout) convertView.findViewById(C0306R.id.ll_chat_media_left);
//                holder.rl_media_left_look = (RelativeLayout) convertView.findViewById(C0306R.id.rl_media_left_look);
//                holder.tv_media_left_look = (TextView) convertView.findViewById(C0306R.id.tv_media_left_look);
//                holder.rl_chat_image_left = (RelativeLayout) convertView.findViewById(C0306R.id.rl_chat_image_left);
//                holder.iv_chat_image_left = (ImageView) convertView.findViewById(C0306R.id.iv_chat_image_left);
//                holder.rl_chat_voice_left = (RelativeLayout) convertView.findViewById(C0306R.id.rl_chat_voice_left);
//                holder.iv_chat_voice_left = (ImageView) convertView.findViewById(C0306R.id.iv_chat_voice_left);
//                holder.tv_voice_duration_left = (TextView) convertView.findViewById(C0306R.id.tv_voice_duration_left);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            if (this.target_avatar == null) {
//                this.target_avatar = new StringBuilder(String.valueOf(System.currentTimeMillis())).toString();
//            }
//            StringBuilder stringBuilder = new StringBuilder(ConstantURL.AVATAR_DOMAIN);
//            avat_url = r26.append(sendUserId).append("/").append(this.target_avatar).append(Constant.HEAD_BIG_SIZE).toString();
//            ImageLoader.getInstance().displayImage(avat_url, holder.avatar_iv_left, Constant.AVATAR_OPTIONS);
//            holder.avatar_iv_left.setOnClickListener(this.onClickListener);
//            holder.avatar_iv_left.setTag(message);
//            if (message.getSentTime() != 0) {
//                holder.time_tv_left.setText(TimeUtils.getShowTimeString2(new Date(message.getSentTime()), "yyyy-MM-dd HH:mm"));
//                if (position == 0) {
//                    holder.time_tv_left.setVisibility(0);
//                } else {
//                    try {
//                        if (TimeUtils.getBetweenMins2(((Message) this.mList.get(position - 1)).getSentTime(), ((Message) this.mList.get(position)).getSentTime()) > 5.0d) {
//                            holder.time_tv_left.setVisibility(0);
//                        } else {
//                            holder.time_tv_left.setVisibility(8);
//                        }
//                    } catch (Exception e2) {
//                        holder.time_tv_left.setVisibility(8);
//                    }
//                }
//            } else {
//                holder.time_tv_left.setVisibility(8);
//            }
//            if (message.getContent() instanceof TextMessage) {
//                holder.content_tv_left.setVisibility(0);
//                holder.ll_media_left.setVisibility(8);
//                holder.rl_chat_image_left.setVisibility(8);
//                holder.rl_chat_voice_left.setVisibility(8);
//                String content = ((TextMessage) message.getContent()).getContent();
//                if (content != null) {
//                    ss = TextUtil.formatContent(Html.fromHtml(FaceUtils.getCharSequence(content), this.getter, null), this.mContext);
//                    holder.content_tv_left.setMovementMethod(LinkMovementMethod.getInstance());
//                    holder.content_tv_left.setText(ss);
//                }
//                holder.content_tv_left.setTag(C0306R.id.private_chat_converview, Integer.valueOf(position));
//                holder.content_tv_left.setOnLongClickListener(this.onLongClickListener);
//            } else {
//                if (message.getContent() instanceof ImageMessage) {
//                    holder.content_tv_left.setVisibility(8);
//                    holder.ll_media_left.setVisibility(8);
//                    holder.rl_chat_image_left.setVisibility(0);
//                    holder.rl_chat_voice_left.setVisibility(8);
//                    imageMessage = (ImageMessage) message.getContent();
//                    ThumUri = imageMessage.getThumUri();
//                    if (ThumUri == null) {
//                        ImageLoader.getInstance().displayImage(imageMessage.getRemoteUri().toString(), holder.iv_chat_image_left);
//                    } else {
//                        holder.iv_chat_image_left.setImageURI(ThumUri);
//                    }
//                    holder.rl_chat_image_left.setTag(Integer.valueOf(position));
//                    holder.rl_chat_image_left.setOnClickListener(this.onClickListener);
//                    holder.rl_chat_image_left.setTag(C0306R.id.private_chat_converview, Integer.valueOf(position));
//                    holder.rl_chat_image_left.setOnLongClickListener(this.onLongClickListener);
//                } else {
//                    if (message.getContent() instanceof VoiceMessage) {
//                        holder.content_tv_left.setVisibility(8);
//                        holder.ll_media_left.setVisibility(8);
//                        holder.rl_chat_image_left.setVisibility(8);
//                        holder.rl_chat_voice_left.setVisibility(0);
//                        duration = ((VoiceMessage) message.getContent()).getDuration();
//                        holder.tv_voice_duration_left.setText(new StringBuilder(String.valueOf(duration)).append("\"").toString());
//                        holder.rl_chat_voice_left.setTag(Integer.valueOf(position));
//                        holder.rl_chat_voice_left.setOnClickListener(this.onClickListener);
//                        holder.rl_chat_voice_left.setTag(C0306R.id.private_chat_converview, Integer.valueOf(position));
//                        holder.rl_chat_voice_left.setOnLongClickListener(this.onLongClickListener);
//                    } else {
//                        if (message.getContent() instanceof RichContentMessage) {
//                            holder.content_tv_left.setVisibility(8);
//                            holder.ll_media_left.setVisibility(0);
//                            holder.rl_chat_image_left.setVisibility(8);
//                            holder.rl_chat_voice_left.setVisibility(8);
//                            richContentMessage = (RichContentMessage) message.getContent();
//                            if (richContentMessage != null) {
//                                if (!BuildConfig.FLAVOR.equals(richContentMessage)) {
//                                    ImageLoader.getInstance().displayImage(richContentMessage.getUrl(), holder.iv_media_left_pic, this.mediaImageOptions);
//                                    holder.tv_media_left_title.setText(richContentMessage.getTitle());
//                                    holder.tv_media_left_content.setText(richContentMessage.getContent());
//                                    model = (RCExtraModel) new Gson().fromJson(richContentMessage.getExtra(), RCExtraModel.class);
//                                    if (!(model == null || model.getContentLink() == null)) {
//                                        holder.rl_media_left_look.setTag(model.getContentLink());
//                                        holder.rl_media_left_look.setOnClickListener(this.onClickListener);
//                                    }
//                                    if (!(model == null || model.getButtonlink() == null)) {
//                                        holder.tv_media_left_look.setTag(model.getButtonlink());
//                                        holder.tv_media_left_look.setOnClickListener(this.onClickListener);
//                                    }
//                                    if (!(model == null || model.getButtonlink() == null)) {
//                                        holder.ll_chat_media_left.setTag(model.getButtonlink());
//                                        holder.ll_chat_media_left.setOnClickListener(this.onClickListener);
//                                    }
//                                    holder.ll_media_left.setTag(C0306R.id.private_chat_converview, Integer.valueOf(position));
//                                    holder.ll_media_left.setOnLongClickListener(this.onLongClickListener);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        convertView.setOnClickListener(this.onClickListener);
//        return convertView;
//    }
//}
