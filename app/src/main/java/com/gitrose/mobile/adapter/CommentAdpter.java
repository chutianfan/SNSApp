package com.gitrose.mobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.R;
//import com.gitrose.mobile.PersonalNewActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.model.Comment;
import com.gitrose.mobile.model.TopicReciveRedPacketModel.TopicReciveItem;
import com.gitrose.mobile.model.TopicZanModel.TopicZanItemModel;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.utils.TextUtil;
import com.gitrose.mobile.utils.WalletUtil;
import io.rong.common.ResourceUtils;
import java.util.List;
import qalsdk.BaseConstants;
import shouji.gexing.framework.utils.TimeUtils;

public class CommentAdpter extends BaseAdapter implements OnClickListener {
    private List<Comment> mComments;
    private Context mContext;
    private LayoutInflater mInflater;
    private OnClickListener mOnClickListener;
    private OnLongClickListener mOnLongClickListener;
    private List<TopicReciveItem> mRedpacketUsers;
    private List<TopicZanItemModel> mZanUsers;
    private int type;

    static class ViewHolder {
        ImageView commentAvatarImg;
        TextView commentContentTv;
        View commentMoreView;
        ImageView commentPic;
        TextView commentTimeTv;
        TextView commentUserNameTv;
        RelativeLayout fl_comment_content;
        ImageView iv_isauth;
        LinearLayout replyCommentLl;
        ImageView replyImg;
        TextView replyUserNameTv;
        ImageView tips_list_item_iv_level;
        TextView tv_reply_user_name;

        ViewHolder() {
        }
    }

    static class ZanViewHolder {
        ImageView avatar_iv;
        ImageView isauth_iv;
        RelativeLayout item_rl;
        ImageView level_iv;
        TextView luck_tv;
        TextView money_tv;
        TextView nickname_tv;
        TextView time_tv;
        LinearLayout zan_right_ll;

        ZanViewHolder() {
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.CommentAdpter.1 */
    class C17791 extends SimpleImageLoadingListener {
        private final /* synthetic */ ImageView val$imageView;
        private final /* synthetic */ int val$pos;

        C17791(ImageView imageView, int i) {
            this.val$imageView = imageView;
            this.val$pos = i;
        }

        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (this.val$pos == ((Integer) this.val$imageView.getTag()).intValue()) {
                this.val$imageView.setBackgroundDrawable(new BitmapDrawable(loadedImage));
            }
        }
    }

    public CommentAdpter(Context context, List<Comment> comments, OnClickListener onClickListener, OnLongClickListener onLongClickListener) {
        this.type = 0;
        this.mContext = context;
        this.mComments = comments;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mOnClickListener = onClickListener;
        this.mOnLongClickListener = onLongClickListener;
    }

    public CommentAdpter(Context context, OnClickListener onClickListener, OnLongClickListener onLongClickListener) {
        this.type = 0;
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mOnClickListener = onClickListener;
        this.mOnLongClickListener = onLongClickListener;
    }

    public void setCommentList(List<Comment> comments) {
        this.mComments = comments;
        this.type = 0;
        notifyDataSetChanged();
    }

    public void setZanList(List<TopicZanItemModel> zanUsers) {
        this.mZanUsers = zanUsers;
        this.type = 1;
        notifyDataSetChanged();
    }

    public void setRedPaketList(List<TopicReciveItem> redPacketUsers) {
        this.mRedpacketUsers = redPacketUsers;
        this.type = 2;
        notifyDataSetChanged();
    }

    public int getCount() {
        if (this.type == 0) {
            if (this.mComments != null) {
                return this.mComments.size();
            }
            return 0;
        } else if (this.type == 1) {
            if (this.mZanUsers != null) {
                return this.mZanUsers.size();
            }
            return 0;
        } else if (this.mRedpacketUsers != null) {
            return this.mRedpacketUsers.size();
        } else {
            return 0;
        }
    }

    public Comment getItem(int position) {
        return (Comment) this.mComments.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.type == 0) {
            return getCommentView(position, convertView);
        }
        if (this.type == 1) {
            return getZanUserView(position, convertView);
        }
        if (this.type == 2) {
            return getRedPacketUserView(position, convertView);
        }
        return convertView;
    }

    private View getZanUserView(int position, View convertView) {
        if (this.mZanUsers == null || this.mZanUsers.get(position) == null) {
            return convertView;
        }
        return initUserUI(convertView, position, null, ((TopicZanItemModel) this.mZanUsers.get(position)).getUserinfo(), 0.0f, ((TopicZanItemModel) this.mZanUsers.get(position)).getAddtime(), false);
    }

    private View getRedPacketUserView(int position, View convertView) {
        if (this.mRedpacketUsers == null || this.mRedpacketUsers.get(position) == null) {
            return convertView;
        }
        TutuUsers userInfo = ((TopicReciveItem) this.mRedpacketUsers.get(position)).getUserinfo();
        boolean isluck = false;
        if (((TopicReciveItem) this.mRedpacketUsers.get(position)).getIsluck() == 1) {
            isluck = true;
        }
        return initUserUI(convertView, position, null, userInfo, ((TopicReciveItem) this.mRedpacketUsers.get(position)).getMoney(), ((TopicReciveItem) this.mRedpacketUsers.get(position)).getAddtime(), isluck);
    }

    private View initUserUI(View convertView, int position, ZanViewHolder zan_holder, TutuUsers userInfo, float money, String time, boolean isLucky) {
        if (convertView == null || convertView.findViewById(R.id.zan_item_user_head_iv) == null) {
            zan_holder = new ZanViewHolder();
            convertView = this.mInflater.inflate(R.layout.item_topic_detail_zan, null);
            zan_holder.item_rl = (RelativeLayout) convertView.findViewById(R.id.zan_item_rl);
            zan_holder.avatar_iv = (ImageView) convertView.findViewById(R.id.zan_item_user_head_iv);
            zan_holder.isauth_iv = (ImageView) convertView.findViewById(R.id.zan_item_iv_isauth);
            zan_holder.nickname_tv = (TextView) convertView.findViewById(R.id.zan_item_tv_user_name_nick);
            zan_holder.level_iv = (ImageView) convertView.findViewById(R.id.zan_item_iv_userhonorlevel);
            zan_holder.time_tv = (TextView) convertView.findViewById(R.id.zan_item_time_tv);
            zan_holder.money_tv = (TextView) convertView.findViewById(R.id.zan_item_right_money_tv);
            zan_holder.zan_right_ll = (LinearLayout) convertView.findViewById(R.id.zan_item_right_rl);
            zan_holder.luck_tv = (TextView) convertView.findViewById(R.id.zan_item_right_money_isluck);
            convertView.setTag(zan_holder);
        } else {
            zan_holder = (ZanViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(ImageUtils.getAvatarUrl(userInfo.getUid(), userInfo.getAvatartime()), zan_holder.avatar_iv, Constant.AVATAR_OPTIONS);
        zan_holder.avatar_iv.setTag(userInfo.getUid());
        zan_holder.avatar_iv.setOnClickListener(this);
        zan_holder.item_rl.setTag(userInfo.getUid());
        zan_holder.item_rl.setOnClickListener(this);
        String nick = userInfo.getNickname();
        if (!(userInfo.getRemarkname() == null || userInfo.getRemarkname().equals(BaseConstants.ah))) {
            nick = userInfo.getRemarkname();
        }
        if (nick.length() > 10) {
            nick = nick.substring(0, 9) + "...";
        }
        zan_holder.nickname_tv.setText(nick);
        zan_holder.isauth_iv.setVisibility(View.VISIBLE);
        if (userInfo.getIsauth() != null && !BaseConstants.ah.equals(userInfo.getIsauth()) && Integer.parseInt(userInfo.getIsauth()) == 1) {
            zan_holder.isauth_iv.setBackgroundResource(R.drawable.personal_isauth);
        } else if (userInfo.getIsauth() == null || BaseConstants.ah.equals(userInfo.getIsauth()) || Integer.parseInt(userInfo.getIsauth()) != 2) {
            zan_holder.isauth_iv.setVisibility(View.GONE);
        } else {
            zan_holder.isauth_iv.setBackgroundResource(R.drawable.personal_daren);
        }
        int level = userInfo.getUserhonorlevel().intValue();
        if (level <= 0) {
            zan_holder.level_iv.setVisibility(View.INVISIBLE);
        } else {
            zan_holder.level_iv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(ImageUtils.getLevelUrl(new StringBuilder(String.valueOf(level)).toString()), zan_holder.level_iv, Constant.LEVEL_OPTIONS);
        }
        if (this.type == 1) {
            zan_holder.zan_right_ll.setVisibility(View.GONE);
        } else if (this.type == 2) {
            zan_holder.zan_right_ll.setVisibility(View.VISIBLE);
            zan_holder.money_tv.setText(WalletUtil.getMoney2DecimalPoint(money) + this.mContext.getResources().getString(R.string.red_packet_total_amount_end));
            if (isLucky) {
                zan_holder.luck_tv.setVisibility(View.VISIBLE);
            } else {
                zan_holder.luck_tv.setVisibility(View.GONE);
            }
        }
        if (time != null) {
            zan_holder.time_tv.setText(TimeUtils.getShowTimeString(this.mContext, TimeUtils.strToDateLong(time)));
        }
        return convertView;
    }

    private View getCommentView(int position, View convertView) {
        ViewHolder holder;
        TutuUsers user;
        String formataddtime;
        String txtframe;
        Comment replyComment;
        String content;
        String uContent;
        String replyTxtframe;
        if (convertView == null || convertView.findViewById(R.id.img_topic_detail_comment_user_avatar) == null) {
            holder = new ViewHolder();
            convertView = this.mInflater.inflate(R.layout.item_topic_detail, null);
            holder.commentMoreView = convertView.findViewById(R.id.img_comment_more);
            holder.commentAvatarImg = (ImageView) convertView.findViewById(R.id.img_topic_detail_comment_user_avatar);
            holder.commentUserNameTv = (TextView) convertView.findViewById(R.id.tv_comment_user_name);
            holder.fl_comment_content = (RelativeLayout) convertView.findViewById(R.id.fl_comment_content);
            holder.commentContentTv = (TextView) convertView.findViewById(R.id.tv_comment_content);
            holder.commentPic = (ImageView) convertView.findViewById(R.id.img_comment_bg);
            holder.replyCommentLl = (LinearLayout) convertView.findViewById(R.id.ll_reply_comment);
            holder.tv_reply_user_name = (TextView) convertView.findViewById(R.id.tv_reply_user_name);
            holder.replyUserNameTv = (TextView) convertView.findViewById(R.id.tv_reply_user_info);
            holder.replyImg = (ImageView) convertView.findViewById(R.id.img_rely_comment);
            holder.commentTimeTv = (TextView) convertView.findViewById(R.id.tv_comment_time);
            holder.iv_isauth = (ImageView) convertView.findViewById(R.id.iv_isauth);
            holder.tips_list_item_iv_level = (ImageView) convertView.findViewById(R.id.tips_list_item_iv_level);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Comment comment = getItem(position);
        if (comment.getIskana() == 0) {
            int level;
            String level_url;
            String avatarUrl;
            String uid = comment.getUid();
            user = comment.getUserinfo();
            String remarkName = user.getRemarkname();
            if (remarkName != null) {
                if (!BaseConstants.ah.equals(remarkName)) {
                    holder.commentUserNameTv.setText(remarkName);
                    holder.commentUserNameTv.setTextColor(this.mContext.getResources().getColor(R.color.topic_username_color));
                    holder.commentUserNameTv.setTag(uid);
                    holder.commentUserNameTv.setOnClickListener(this);
                    holder.iv_isauth.setVisibility(View.VISIBLE);
                    if (user.getIsauth() == null && !BaseConstants.ah.equals(user.getIsauth()) && Integer.parseInt(user.getIsauth()) == 1) {
                        holder.iv_isauth.setBackgroundResource(R.drawable.personal_isauth);
                    } else if (user.getIsauth() == null && !BaseConstants.ah.equals(user.getIsauth()) && Integer.parseInt(user.getIsauth()) == 2) {
                        holder.iv_isauth.setBackgroundResource(R.drawable.personal_daren);
                    } else {
                        holder.iv_isauth.setVisibility(View.INVISIBLE);
                    }
                    level = 0;
                    if (user.getUserhonorlevel() != null) {
                        level = user.getUserhonorlevel().intValue();
                    }
                    if (level > 0) {
                        holder.tips_list_item_iv_level.setVisibility(View.INVISIBLE);
                    } else {
                        holder.tips_list_item_iv_level.setVisibility(View.VISIBLE);
                        level_url = ImageUtils.getLevelUrl(new StringBuilder(String.valueOf(level)).toString());
                        ImageLoader.getInstance().displayImage(level_url, holder.tips_list_item_iv_level, Constant.LEVEL_OPTIONS);
                    }
                    avatarUrl = ImageUtils.getAvatarUrl(user.getUid(), user.getAvatartime());
                    ImageLoader.getInstance().displayImage(avatarUrl, holder.commentAvatarImg);
                    holder.commentAvatarImg.setTag(uid);
                    holder.commentAvatarImg.setOnClickListener(this);
                }
            }
            CharSequence nickName = user.getNickname();
            TextView textView = holder.commentUserNameTv;
            if (nickName == null) {
                nickName = this.mContext.getString(R.string.comment_name_tip);
            }
            textView.setText(nickName);
            holder.commentUserNameTv.setTextColor(this.mContext.getResources().getColor(R.color.topic_username_color));
            holder.commentUserNameTv.setTag(uid);
            holder.commentUserNameTv.setOnClickListener(this);
            holder.iv_isauth.setVisibility(View.VISIBLE);
            if (user.getIsauth() == null) {
            }
            if (user.getIsauth() == null) {
            }
            holder.iv_isauth.setVisibility(View.INVISIBLE);
            level = 0;
            if (user.getUserhonorlevel() != null) {
                level = user.getUserhonorlevel().intValue();
            }
            if (level > 0) {
                holder.tips_list_item_iv_level.setVisibility(View.VISIBLE);
                level_url = ImageUtils.getLevelUrl(new StringBuilder(String.valueOf(level)).toString());
                ImageLoader.getInstance().displayImage(level_url, holder.tips_list_item_iv_level, Constant.LEVEL_OPTIONS);
            } else {
                holder.tips_list_item_iv_level.setVisibility(View.INVISIBLE);
            }
            avatarUrl = ImageUtils.getAvatarUrl(user.getUid(), user.getAvatartime());
            ImageLoader.getInstance().displayImage(avatarUrl, holder.commentAvatarImg);
            holder.commentAvatarImg.setTag(uid);
            holder.commentAvatarImg.setOnClickListener(this);
        } else {
            holder.commentAvatarImg.setImageResource(R.drawable.topic_no_name);
            holder.commentUserNameTv.setText(this.mContext.getString(R.string.no_name_tip));
            holder.commentUserNameTv.setTextColor(this.mContext.getResources().getColor(R.color.no_name_color));
            holder.iv_isauth.setVisibility(View.INVISIBLE);
        }
        TextView textView2 = holder.commentTimeTv;
        if (comment.getFormataddtime() != null) {
            formataddtime = comment.getFormataddtime();
        } else {
            formataddtime = this.mContext.getString(R.string.comment_now_time);
        }
        textView2.setText(formataddtime);
        String commentContent = comment.getContent();
        if (commentContent != null) {
            if (!BaseConstants.ah.equals(commentContent)) {
                holder.commentContentTv.setVisibility(View.VISIBLE);
                holder.commentContentTv.setText(TextUtil.formatContent(commentContent, this.mContext));
                holder.commentContentTv.setMovementMethod(LinkMovementMethod.getInstance());
                holder.commentContentTv.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                holder.commentContentTv.setTag(R.id.topic_comment_reply, Boolean.valueOf(false));
                holder.commentContentTv.setOnClickListener(this.mOnClickListener);
                holder.commentContentTv.setTag(comment);
                holder.commentContentTv.setOnLongClickListener(this.mOnLongClickListener);
                txtframe = comment.getTxtframe();
                if (txtframe != null) {
                    if (!txtframe.contains("input")) {
                        holder.commentPic.setVisibility(View.VISIBLE);
                        holder.commentPic.setTag(Integer.valueOf(position));
                        setCommentViewBackground(holder.commentPic, txtframe, position);
                        holder.fl_comment_content.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                        holder.fl_comment_content.setTag(R.id.topic_comment_reply, Boolean.valueOf(false));
                        holder.fl_comment_content.setOnClickListener(this.mOnClickListener);
                        holder.fl_comment_content.setTag(comment);
                        holder.fl_comment_content.setOnLongClickListener(this.mOnLongClickListener);
                        replyComment = comment.getReplydata();
                        if (replyComment == null) {
                            user = replyComment.getUserinfo();
                            holder.replyCommentLl.setVisibility(View.VISIBLE);
                            content = "<font color=\"" + "#4DB69A" + "\">" + user.getNickname() + " </font><font color=\"" + "#999999" + "\">" + this.mContext.getResources().getString(R.string.comment_reply_tip) + "</font>";
                            holder.tv_reply_user_name.setText(Html.fromHtml(content));
                            uContent = replyComment.getContent();
                            if (uContent == null) {
                                if (BaseConstants.ah.equals(uContent)) {
                                    holder.replyUserNameTv.setVisibility(View.GONE);
                                    replyTxtframe = replyComment.getTxtframe();
                                    if (replyTxtframe != null) {
                                        if (!replyTxtframe.contains("input")) {
                                            holder.replyImg.setVisibility(View.VISIBLE);
                                            holder.replyImg.setTag(Integer.valueOf(position));
                                            setCommentViewBackground(holder.replyImg, replyTxtframe, position);
                                            holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                                            holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                                            holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                                            holder.replyCommentLl.setTag(replyComment);
                                            holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                                        }
                                    }
                                    holder.replyImg.setVisibility(View.GONE);
                                    holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                                    holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                                    holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                                    holder.replyCommentLl.setTag(replyComment);
                                    holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                                }
                            }
                            holder.replyUserNameTv.setVisibility(View.VISIBLE);
                            holder.replyUserNameTv.setText(TextUtil.formatContent(uContent, this.mContext));
                            holder.replyUserNameTv.setMovementMethod(LinkMovementMethod.getInstance());
                            replyTxtframe = replyComment.getTxtframe();
                            if (replyTxtframe != null) {
                                if (replyTxtframe.contains("input")) {
                                    holder.replyImg.setVisibility(View.VISIBLE);
                                    holder.replyImg.setTag(Integer.valueOf(position));
                                    setCommentViewBackground(holder.replyImg, replyTxtframe, position);
                                    holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                                    holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                                    holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                                    holder.replyCommentLl.setTag(replyComment);
                                    holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                                }
                            }
                            holder.replyImg.setVisibility(View.GONE);
                            holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                            holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                            holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                            holder.replyCommentLl.setTag(replyComment);
                            holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                        } else {
                            holder.replyCommentLl.setVisibility(View.GONE);
                        }
                        holder.commentMoreView.setTag(Integer.valueOf(position));
                        holder.commentMoreView.setSelected(false);
                        holder.commentMoreView.setOnClickListener(this.mOnClickListener);
                        return convertView;
                    }
                }
                holder.commentPic.setVisibility(View.GONE);
                replyComment = comment.getReplydata();
                if (replyComment == null) {
                    holder.replyCommentLl.setVisibility(View.GONE);
                } else {
                    user = replyComment.getUserinfo();
                    holder.replyCommentLl.setVisibility(View.VISIBLE);
                    content = "<font color=\"" + "#4DB69A" + "\">" + user.getNickname() + " </font><font color=\"" + "#999999" + "\">" + this.mContext.getResources().getString(R.string.comment_reply_tip) + "</font>";
                    holder.tv_reply_user_name.setText(Html.fromHtml(content));
                    uContent = replyComment.getContent();
                    if (uContent == null) {
                        if (BaseConstants.ah.equals(uContent)) {
                            holder.replyUserNameTv.setVisibility(View.GONE);
                            replyTxtframe = replyComment.getTxtframe();
                            if (replyTxtframe != null) {
                                if (replyTxtframe.contains("input")) {
                                    holder.replyImg.setVisibility(View.VISIBLE);
                                    holder.replyImg.setTag(Integer.valueOf(position));
                                    setCommentViewBackground(holder.replyImg, replyTxtframe, position);
                                    holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                                    holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                                    holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                                    holder.replyCommentLl.setTag(replyComment);
                                    holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                                }
                            }
                            holder.replyImg.setVisibility(View.GONE);
                            holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                            holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                            holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                            holder.replyCommentLl.setTag(replyComment);
                            holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                        }
                    }
                    holder.replyUserNameTv.setVisibility(View.VISIBLE);
                    holder.replyUserNameTv.setText(TextUtil.formatContent(uContent, this.mContext));
                    holder.replyUserNameTv.setMovementMethod(LinkMovementMethod.getInstance());
                    replyTxtframe = replyComment.getTxtframe();
                    if (replyTxtframe != null) {
                        if (replyTxtframe.contains("input")) {
                            holder.replyImg.setVisibility(View.VISIBLE);
                            holder.replyImg.setTag(Integer.valueOf(position));
                            setCommentViewBackground(holder.replyImg, replyTxtframe, position);
                            holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                            holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                            holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                            holder.replyCommentLl.setTag(replyComment);
                            holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                        }
                    }
                    holder.replyImg.setVisibility(View.GONE);
                    holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                    holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                    holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                    holder.replyCommentLl.setTag(replyComment);
                    holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                }
                holder.commentMoreView.setTag(Integer.valueOf(position));
                holder.commentMoreView.setSelected(false);
                holder.commentMoreView.setOnClickListener(this.mOnClickListener);
                return convertView;
            }
        }
        holder.commentContentTv.setVisibility(View.GONE);
        txtframe = comment.getTxtframe();
        if (txtframe != null) {
            if (txtframe.contains("input")) {
                holder.commentPic.setVisibility(View.VISIBLE);
                holder.commentPic.setTag(Integer.valueOf(position));
                setCommentViewBackground(holder.commentPic, txtframe, position);
                holder.fl_comment_content.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                holder.fl_comment_content.setTag(R.id.topic_comment_reply, Boolean.valueOf(false));
                holder.fl_comment_content.setOnClickListener(this.mOnClickListener);
                holder.fl_comment_content.setTag(comment);
                holder.fl_comment_content.setOnLongClickListener(this.mOnLongClickListener);
                replyComment = comment.getReplydata();
                if (replyComment == null) {
                    user = replyComment.getUserinfo();
                    holder.replyCommentLl.setVisibility(View.VISIBLE);
                    content = "<font color=\"" + "#4DB69A" + "\">" + user.getNickname() + " </font><font color=\"" + "#999999" + "\">" + this.mContext.getResources().getString(R.string.comment_reply_tip) + "</font>";
                    holder.tv_reply_user_name.setText(Html.fromHtml(content));
                    uContent = replyComment.getContent();
                    if (uContent == null) {
                        if (BaseConstants.ah.equals(uContent)) {
                            holder.replyUserNameTv.setVisibility(View.GONE);
                            replyTxtframe = replyComment.getTxtframe();
                            if (replyTxtframe != null) {
                                if (replyTxtframe.contains("input")) {
                                    holder.replyImg.setVisibility(View.VISIBLE);
                                    holder.replyImg.setTag(Integer.valueOf(position));
                                    setCommentViewBackground(holder.replyImg, replyTxtframe, position);
                                    holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                                    holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                                    holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                                    holder.replyCommentLl.setTag(replyComment);
                                    holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                                }
                            }
                            holder.replyImg.setVisibility(View.GONE);
                            holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                            holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                            holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                            holder.replyCommentLl.setTag(replyComment);
                            holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                        }
                    }
                    holder.replyUserNameTv.setVisibility(View.VISIBLE);
                    holder.replyUserNameTv.setText(TextUtil.formatContent(uContent, this.mContext));
                    holder.replyUserNameTv.setMovementMethod(LinkMovementMethod.getInstance());
                    replyTxtframe = replyComment.getTxtframe();
                    if (replyTxtframe != null) {
                        if (replyTxtframe.contains("input")) {
                            holder.replyImg.setVisibility(View.VISIBLE);
                            holder.replyImg.setTag(Integer.valueOf(position));
                            setCommentViewBackground(holder.replyImg, replyTxtframe, position);
                            holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                            holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                            holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                            holder.replyCommentLl.setTag(replyComment);
                            holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                        }
                    }
                    holder.replyImg.setVisibility(View.GONE);
                    holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                    holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                    holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                    holder.replyCommentLl.setTag(replyComment);
                    holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                } else {
                    holder.replyCommentLl.setVisibility(View.GONE);
                }
                holder.commentMoreView.setTag(Integer.valueOf(position));
                holder.commentMoreView.setSelected(false);
                holder.commentMoreView.setOnClickListener(this.mOnClickListener);
                return convertView;
            }
        }
        holder.commentPic.setVisibility(View.GONE);
        replyComment = comment.getReplydata();
        if (replyComment == null) {
            holder.replyCommentLl.setVisibility(View.GONE);
        } else {
            user = replyComment.getUserinfo();
            holder.replyCommentLl.setVisibility(View.VISIBLE);
            content = "<font color=\"" + "#4DB69A" + "\">" + user.getNickname() + " </font><font color=\"" + "#999999" + "\">" + this.mContext.getResources().getString(R.string.comment_reply_tip) + "</font>";
            holder.tv_reply_user_name.setText(Html.fromHtml(content));
            uContent = replyComment.getContent();
            if (uContent == null) {
                if (BaseConstants.ah.equals(uContent)) {
                    holder.replyUserNameTv.setVisibility(View.GONE);
                    replyTxtframe = replyComment.getTxtframe();
                    if (replyTxtframe != null) {
                        if (replyTxtframe.contains("input")) {
                            holder.replyImg.setVisibility(View.VISIBLE);
                            holder.replyImg.setTag(Integer.valueOf(position));
                            setCommentViewBackground(holder.replyImg, replyTxtframe, position);
                            holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                            holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                            holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                            holder.replyCommentLl.setTag(replyComment);
                            holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                        }
                    }
                    holder.replyImg.setVisibility(View.GONE);
                    holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                    holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                    holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                    holder.replyCommentLl.setTag(replyComment);
                    holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                }
            }
            holder.replyUserNameTv.setVisibility(View.VISIBLE);
            holder.replyUserNameTv.setText(TextUtil.formatContent(uContent, this.mContext));
            holder.replyUserNameTv.setMovementMethod(LinkMovementMethod.getInstance());
            replyTxtframe = replyComment.getTxtframe();
            if (replyTxtframe != null) {
                if (replyTxtframe.contains("input")) {
                    holder.replyImg.setVisibility(View.VISIBLE);
                    holder.replyImg.setTag(Integer.valueOf(position));
                    setCommentViewBackground(holder.replyImg, replyTxtframe, position);
                    holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
                    holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
                    holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
                    holder.replyCommentLl.setTag(replyComment);
                    holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
                }
            }
            holder.replyImg.setVisibility(View.GONE);
            holder.replyCommentLl.setTag(R.id.topic_comment_pos, Integer.valueOf(position));
            holder.replyCommentLl.setTag(R.id.topic_comment_reply, Boolean.valueOf(true));
            holder.replyCommentLl.setOnClickListener(this.mOnClickListener);
            holder.replyCommentLl.setTag(replyComment);
            holder.replyCommentLl.setOnLongClickListener(this.mOnLongClickListener);
        }
        holder.commentMoreView.setTag(Integer.valueOf(position));
        holder.commentMoreView.setSelected(false);
        holder.commentMoreView.setOnClickListener(this.mOnClickListener);
        return convertView;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_topic_detail_comment_user_avatar:
            case R.id.tv_comment_user_name:
            case R.id.zan_item_rl:
            case R.id.zan_item_user_head_iv:
                startPersonalActivity(v);
            default:
        }
    }

    private void startPersonalActivity(View view) {
        String uid = (String) view.getTag();
        if (uid != null) {
//            Intent intent = new Intent();
//            intent.setClass(this.mContext, PersonalNewActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString(PersonalNewActivity.PERSONAL_USER_ID, uid);
//            intent.putExtras(bundle);
//            this.mContext.startActivity(intent);
//            animationForNew();
//            PlaySound.getInstance(this.mContext).toPlay(R.raw.open);
        }
    }

    private void animationForNew() {
        ((Activity) this.mContext).overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
    }

    private void setCommentViewBackground(ImageView imageView, String textFrame, int pos) {
        int resID = this.mContext.getResources().getIdentifier(textFrame, ResourceUtils.drawable, this.mContext.getPackageName());
        if (resID == 0) {
            ImageLoader.getInstance().loadImage(new StringBuilder(ConstantURL.TOPIC_DOWNLOAD_EMOTION).append(textFrame).append(".png").toString(), new C17791(imageView, pos));
        } else {
            imageView.setBackgroundResource(resID);
        }
    }
}
