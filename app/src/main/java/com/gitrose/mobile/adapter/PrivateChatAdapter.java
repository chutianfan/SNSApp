package com.gitrose.mobile.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.gitrose.mobile.R;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.model.MessagePic;
import com.gitrose.mobile.model.PrivateMeassageModel;
import com.gitrose.mobile.utils.TextUtil;
//import io.rong.message.BuildConfig;
import java.util.Date;
//import shouji.gexing.framework.utils.FaceUtils;
//import shouji.gexing.framework.utils.TimeUtils;

public class PrivateChatAdapter extends ArrayListAdapter<PrivateMeassageModel> {
    public ImageGetter getter;
    DisplayImageOptions mediaImageOptions;
    private OnClickListener onClickListener;

    /* renamed from: com.gitrose.mobile.adapter.PrivateChatAdapter.1 */
    class C03411 implements ImageGetter {
        C03411() {
        }

        public Drawable getDrawable(String source) {
            Drawable d = PrivateChatAdapter.this.mContext.getResources().getDrawable(Integer.parseInt(source));
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            return d;
        }
    }

    private static class ViewHolder {
        ImageView avatar_iv_left;
        ImageView avatar_iv_right;
        TextView content_tv_left;
        TextView content_tv_right;
        ImageView iv_media_left_pic;
        ImageView iv_media_right_pic;
        ImageView iv_system_avatar;
        ImageView iv_system_pic;
        LinearLayout ll_media_left;
        LinearLayout ll_media_right;
        LinearLayout ll_system_media;
        LinearLayout ll_system_text;
        RelativeLayout rl_media_left_look;
        RelativeLayout rl_media_right_look;
        RelativeLayout rl_system_look;
        TextView sys_tv;
        TextView time_tv_left;
        TextView time_tv_right;
        TextView tv_media_left_content;
        TextView tv_media_left_look;
        TextView tv_media_left_title;
        TextView tv_media_right_content;
        TextView tv_media_right_look;
        TextView tv_media_right_title;
        TextView tv_system_content;
        TextView tv_system_look;
        TextView tv_system_time;
        TextView tv_system_title;

        private ViewHolder() {
        }
    }

    public PrivateChatAdapter(Context context, OnClickListener onClickListener) {
        super(context);
        this.mediaImageOptions = new Builder().showImageForEmptyUri((int) R.drawable.default_pic_bg).showImageOnFail((int) R.drawable.chat_media_default_bg).cacheInMemory(true).cacheOnDisk(true).displayer(new RoundedBitmapDisplayer(10)).build();
        this.getter = new C03411();
        this.onClickListener = onClickListener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PrivateMeassageModel item = (PrivateMeassageModel) this.mList.get(position);
        ViewHolder holder;
        SpannableString ss;
        if (MyApplication.getInstance().getUserinfo().getUid() != null && item.getUid() != null && item.getUid().equals(MyApplication.getInstance().getUserinfo().getUid())) {
            int typeRight;
            if (convertView == null || convertView.findViewById(R.id.private_chat_item_right_iv_avatar) == null) {
                holder = new ViewHolder();
                convertView = View.inflate(this.mContext, R.layout.activity_private_chat_item_right, null);
                holder.avatar_iv_right = (ImageView) convertView.findViewById(R.id.private_chat_item_right_iv_avatar);
                holder.content_tv_right = (TextView) convertView.findViewById(R.id.private_chat_item_right_tv_content);
                holder.time_tv_right = (TextView) convertView.findViewById(R.id.private_chat_item_right_tv_time);
                holder.ll_media_right = (LinearLayout) convertView.findViewById(R.id.ll_media_right);
                holder.iv_media_right_pic = (ImageView) convertView.findViewById(R.id.iv_media_right_pic);
                holder.tv_media_right_title = (TextView) convertView.findViewById(R.id.tv_media_right_title);
                holder.tv_media_right_content = (TextView) convertView.findViewById(R.id.tv_media_right_content);
                holder.rl_media_right_look = (RelativeLayout) convertView.findViewById(R.id.rl_media_right_look);
                holder.tv_media_right_look = (TextView) convertView.findViewById(R.id.tv_media_right_look);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(new StringBuilder(ConstantURL.AVATAR_DOMAIN).append(item.getUid()).append("/").append(item.getAvatartime()).append(Constant.HEAD_BIG_SIZE).toString(), holder.avatar_iv_right, Constant.AVATAR_OPTIONS);
            holder.avatar_iv_right.setOnClickListener(this.onClickListener);
            holder.avatar_iv_right.setTag(item);
            String type = item.getType();
            if (type == null) {
                typeRight = 1;
            } else {
                typeRight = Integer.valueOf(type).intValue();
            }
            if (typeRight == 1) {
                holder.content_tv_right.setVisibility(View.VISIBLE);
                holder.ll_media_right.setVisibility(View.GONE);
//                ss = TextUtil.formatContent(Html.fromHtml(FaceUtils.getCharSequence(item.getContent()), this.getter, null), this.mContext);
//                holder.content_tv_right.setMovementMethod(LinkMovementMethod.getInstance());
//                holder.content_tv_right.setText(ss);
            } else if (typeRight == 2) {
                holder.content_tv_right.setVisibility(View.GONE);
                holder.ll_media_right.setVisibility(View.VISIBLE);
                MessagePic messageRight = item.getMessagepictext();
//                if (!(messageRight == null || BuildConfig.FLAVOR.equals(messageRight))) {
//                    ImageLoader.getInstance().displayImage(messageRight.getPic(), holder.iv_media_right_pic, this.mediaImageOptions);
//                    holder.tv_media_right_title.setText(messageRight.getTitle());
//                    holder.tv_media_right_content.setText(messageRight.getContent());
//                    holder.rl_media_right_look.setTag(messageRight.getButtonlink());
//                    holder.rl_media_right_look.setOnClickListener(this.onClickListener);
//                    holder.tv_media_right_look.setTag(messageRight.getButtonlink());
//                    holder.tv_media_right_look.setOnClickListener(this.onClickListener);
//                }
            }
            if (item.getAddtime() != null) {
                //holder.time_tv_right.setText(TimeUtils.getShowTimeString2(new Date(TimeUtils.FromatTimeToMinnisecond(item.getAddtime())), "yyyy-MM-dd HH:mm"));
                if (position == 0) {
                    holder.time_tv_right.setVisibility(View.VISIBLE);
                } else {
                    try {
//                        if (TimeUtils.getBetweenMins(((PrivateMeassageModel) this.mList.get(position - 1)).getAddtime(), ((PrivateMeassageModel) this.mList.get(position)).getAddtime()) > 5.0d) {
//                            holder.time_tv_right.setVisibility(View.VISIBLE);
//                        } else {
//                            holder.time_tv_right.setVisibility(View.GONE);
//                        }
                    } catch (Exception e) {
                        holder.time_tv_right.setVisibility(View.GONE);
                    }
                }
            } else {
                holder.time_tv_right.setVisibility(View.GONE);
            }
        } else if (item.getUid() == null || !item.getUid().equals(Constant.SYSTEM_UID)) {
            if (convertView == null || convertView.findViewById(R.id.private_chat_item_left_iv_avatar) == null) {
                holder = new ViewHolder();
                convertView = View.inflate(this.mContext, R.layout.activity_private_chat_item_left, null);
                holder.avatar_iv_left = (ImageView) convertView.findViewById(R.id.private_chat_item_left_iv_avatar);
                holder.content_tv_left = (TextView) convertView.findViewById(R.id.private_chat_item_left_tv_content);
                holder.time_tv_left = (TextView) convertView.findViewById(R.id.private_chat_item_left_tv_time);
                holder.ll_media_left = (LinearLayout) convertView.findViewById(R.id.ll_media_left);
                holder.iv_media_left_pic = (ImageView) convertView.findViewById(R.id.iv_media_left_pic);
                holder.tv_media_left_title = (TextView) convertView.findViewById(R.id.tv_media_left_title);
                holder.tv_media_left_content = (TextView) convertView.findViewById(R.id.tv_media_left_content);
                holder.rl_media_left_look = (RelativeLayout) convertView.findViewById(R.id.rl_media_left_look);
                holder.tv_media_left_look = (TextView) convertView.findViewById(R.id.tv_media_left_look);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(new StringBuilder(ConstantURL.AVATAR_DOMAIN).append(item.getUid()).append("/").append(item.getAvatartime()).append(Constant.HEAD_BIG_SIZE).toString(), holder.avatar_iv_left, Constant.AVATAR_OPTIONS);
            holder.avatar_iv_left.setOnClickListener(this.onClickListener);
            holder.avatar_iv_left.setTag(item);
            int typeLeft = Integer.valueOf(item.getType()).intValue();
            if (typeLeft == 1) {
                holder.content_tv_left.setVisibility(View.VISIBLE);
                holder.ll_media_left.setVisibility(View.GONE);
//                ss = TextUtil.formatContent(Html.fromHtml(FaceUtils.getCharSequence(item.getContent()), this.getter, null), this.mContext);
//                holder.content_tv_left.setMovementMethod(LinkMovementMethod.getInstance());
//                holder.content_tv_left.setText(ss);
            } else if (typeLeft == 2) {
                holder.content_tv_left.setVisibility(View.GONE);
                holder.ll_media_left.setVisibility(View.VISIBLE);
                MessagePic messageLeft = item.getMessagepictext();
//                if (!(messageLeft == null || BuildConfig.FLAVOR.equals(messageLeft))) {
//                    ImageLoader.getInstance().displayImage(messageLeft.getPic(), holder.iv_media_left_pic, this.mediaImageOptions);
//                    holder.tv_media_left_title.setText(messageLeft.getTitle());
//                    holder.tv_media_left_content.setText(messageLeft.getContent());
//                    holder.rl_media_left_look.setTag(messageLeft.getButtonlink());
//                    holder.rl_media_left_look.setOnClickListener(this.onClickListener);
//                    holder.tv_media_left_look.setTag(messageLeft.getButtonlink());
//                    holder.tv_media_left_look.setOnClickListener(this.onClickListener);
//                }
            }
            if (item.getAddtime() != null) {
                //holder.time_tv_left.setText(TimeUtils.getShowTimeString2(new Date(TimeUtils.FromatTimeToMinnisecond(item.getAddtime())), "yyyy-MM-dd HH:mm"));
                if (position == 0) {
                    holder.time_tv_left.setVisibility(View.VISIBLE);
                } else {
                    try {
//                        if (TimeUtils.getBetweenMins(((PrivateMeassageModel) this.mList.get(position - 1)).getAddtime(), ((PrivateMeassageModel) this.mList.get(position)).getAddtime()) > 5.0d) {
//                            holder.time_tv_left.setVisibility(View.VISIBLE);
//                        } else {
//                            holder.time_tv_left.setVisibility(View.GONE);
//                        }
                    } catch (Exception e2) {
                        holder.time_tv_left.setVisibility(View.GONE);
                    }
                }
            } else {
                holder.time_tv_left.setVisibility(View.GONE);
            }
        } else {
            if (convertView == null || convertView.findViewById(R.id.ll_system_text) == null) {
                holder = new ViewHolder();
                convertView = View.inflate(this.mContext, R.layout.activity_private_chat_item_system, null);
                holder.ll_system_text = (LinearLayout) convertView.findViewById(R.id.ll_system_text);
                holder.sys_tv = (TextView) convertView.findViewById(R.id.private_chat_item_system_tv_content);
                holder.ll_system_media = (LinearLayout) convertView.findViewById(R.id.ll_system_media);
                holder.tv_system_time = (TextView) convertView.findViewById(R.id.tv_system_time);
                holder.iv_system_avatar = (ImageView) convertView.findViewById(R.id.iv_system_avatar);
                holder.iv_system_pic = (ImageView) convertView.findViewById(R.id.iv_system_pic);
                holder.tv_system_title = (TextView) convertView.findViewById(R.id.tv_system_title);
                holder.tv_system_content = (TextView) convertView.findViewById(R.id.tv_system_content);
                holder.rl_system_look = (RelativeLayout) convertView.findViewById(R.id.rl_system_look);
                holder.tv_system_look = (TextView) convertView.findViewById(R.id.tv_system_look);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            int typeSystem = Integer.valueOf(item.getType()).intValue();
            if (typeSystem == 1) {
                holder.ll_system_text.setVisibility(View.VISIBLE);
                holder.ll_system_media.setVisibility(View.GONE);
                holder.sys_tv.setText(item.getContent());
                holder.sys_tv.setVisibility(View.VISIBLE);
            } else if (typeSystem == 2) {
                holder.ll_system_text.setVisibility(View.GONE);
                holder.ll_system_media.setVisibility(View.VISIBLE);
                //holder.tv_system_time.setText(TimeUtils.getShowTimeString2(new Date(TimeUtils.FromatTimeToMinnisecond(item.getAddtime())), "yyyy-MM-dd HH:mm"));
                ImageLoader.getInstance().displayImage(new StringBuilder(ConstantURL.AVATAR_DOMAIN).append(item.getUid()).append("/").append(item.getAvatartime()).append(Constant.HEAD_BIG_SIZE).toString(), holder.iv_system_avatar, Constant.AVATAR_OPTIONS);
                MessagePic messagePic = item.getMessagepictext();
//                if (!(messagePic == null || BuildConfig.FLAVOR.equals(messagePic))) {
//                    ImageLoader.getInstance().displayImage(messagePic.getPic(), holder.iv_system_pic, this.mediaImageOptions);
//                    holder.tv_system_title.setText(messagePic.getTitle());
//                    holder.tv_system_content.setText(messagePic.getContent());
//                    holder.rl_system_look.setOnClickListener(this.onClickListener);
//                    holder.rl_system_look.setTag(messagePic.getButtonlink());
//                    holder.tv_system_look.setTag(messagePic.getButtonlink());
//                    holder.tv_system_look.setOnClickListener(this.onClickListener);
//                }
            }
        }
        return convertView;
    }
}
