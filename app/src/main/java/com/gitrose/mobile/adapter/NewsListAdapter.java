package com.gitrose.mobile.adapter;

import android.content.Context;
import android.support.v4.util.TimeUtils;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.gitrose.mobile.R;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.model.TipsModel;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.utils.TextUtil;
import com.gitrose.mobile.view.TextViewFixTouchConsume;

import java.util.ArrayList;
import java.util.Date;

import shouji.gexing.framework.utils.UiUtils;
//import shouji.gexing.framework.utils.TimeUtils;

public class NewsListAdapter extends BaseAdapter {
    private ArrayList<TipsModel> list;
    private Context mContext;
    private OnClickListener mOnClickListener;
    private View.OnLongClickListener mOnLongClickListener;

    private static class ViewHolder
    {

        LinearLayout content_item_ll;
        ImageView content_iv;
        RelativeLayout content_rl;
        ImageView follow_iv;
        RelativeLayout item_ll;
        ImageView iv_avatar;
        ImageView iv_isauth;
        ImageView iv_level;
        ImageView iv_news;
        ImageView like_avatar_iv;
        ImageView like_homeCover_iv;
        RelativeLayout like_rl;
        ImageView repaly_iv;
        LinearLayout repaly_ll;
        TextView repaly_tv;
        ImageView topic_iv;
        RelativeLayout topic_rl;
        ImageView topic_video_iv;
        TextViewFixTouchConsume tv_content;
        TextView tv_name;
        TextView tv_news_status;
        TextView tv_time;

        private ViewHolder()
        {
        }

        ViewHolder(ViewHolder viewholder)
        {
            this();
        }
    }

    public NewsListAdapter(Context context, OnClickListener listener, View.OnLongClickListener onLongClickListener) {
        this.mContext = context;
        this.mOnClickListener = listener;
        this.mOnLongClickListener = onLongClickListener;
    }

    public void setList(ArrayList<TipsModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        if (this.list == null) {
            return 0;
        }
        return this.list.size();
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View view, ViewGroup parent) {
//        ViewHolder viewholder;
//        if (convertView == null) {
//            viewholder = new ViewHolder();
////            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.activity_news_list_item, null);
//            convertView = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_news_list_item, null);
//            viewholder.iv_avatar = (ImageView) convertView.findViewById(R.id.tips_list_item_iv_avatar);
//            viewholder.iv_news = (ImageView) convertView.findViewById(R.id.tips_list_item_iv_news);
//            viewholder.item_ll = (RelativeLayout) convertView.findViewById(R.id.tips_list_item_rl_name);
//            viewholder.tv_name = (TextView) convertView.findViewById(R.id.tips_list_item_tv_name);
//            viewholder.tv_time = (TextView) convertView.findViewById(R.id.tips_list_item_tv_time);
//            convertView.setTag(viewholder);
//        } else {
//            viewholder = (ViewHolder) convertView.getTag();
//        }
//        viewholder.iv_avatar.setOnClickListener(this.mOnClickListener);
//        viewholder.iv_avatar.setTag(Integer.valueOf(position));
//        TipsModel item = (TipsModel) this.list.get(position);
//        if (item.getRead() == 1) {
//            viewholder.iv_news.setVisibility(View.GONE);
//        } else {
//            viewholder.iv_news.setVisibility(View.VISIBLE);
//        }
//        ImageLoader.getInstance().displayImage(new StringBuilder(ConstantURL.AVATAR_DOMAIN).append(item.getActionuid()).append("/").append(item.getAvatartime()).append(Constant.HEAD_BIG_SIZE).toString(), viewholder.iv_avatar, Constant.AVATAR_OPTIONS);
//        if (!(item.getNickname() == null || item.getData() == null)) {
//            String content = item.getNickname() + " <small>" + item.getData() + "</small>";
//            if (item.getNickname().length() > 7 || item.getData().length() > 5) {
//                content = item.getNickname() + "<br/>" + "<small>" + item.getData() + "</small>";
//            }
//            viewholder.tv_name.setText(Html.fromHtml(content));
//        }
//
//        long time = shouji.gexing.framework.utils.TimeUtils.FromatTimeToMinnisecond(item.getAddtime());
//        viewholder.tv_time.setText(shouji.gexing.framework.utils.TimeUtils.getShowTimeStringefore(null, new Date(time), "MM-dd yyyy"));
//        return convertView;

        View view1 = null;
        TipsModel tipsmodel;
        ViewHolder viewgroup;
        int j;
        if (view == null)
        {
            viewgroup = new ViewHolder();
            view1 = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_news_list_item, null);
            viewgroup.content_item_ll = (LinearLayout)view1.findViewById(R.id.private_list_item_ll);
            viewgroup.tv_news_status = (TextView)view1.findViewById(R.id.tv_news_status);
            viewgroup.iv_avatar = (ImageView)view1.findViewById(R.id.tips_list_item_iv_avatar);
            viewgroup.iv_news = (ImageView)view1.findViewById(R.id.tips_list_item_iv_news);
            viewgroup.item_ll = (RelativeLayout)view1.findViewById(R.id.tips_list_item_rl_name);
            viewgroup.tv_name = (TextView)view1.findViewById(R.id.tips_list_item_tv_name);
            viewgroup.tv_time = (TextView)view1.findViewById(R.id.tips_list_item_tv_time);
            viewgroup.iv_level = (ImageView)view1.findViewById(R.id.tips_list_item_iv_level);
            viewgroup.tv_content = (TextViewFixTouchConsume)view1.findViewById(R.id.tv_content);
            viewgroup.content_rl = (RelativeLayout)view1.findViewById(R.id.tips_list_item_content_rl);
            viewgroup.iv_isauth = (ImageView)view1.findViewById(R.id.iv_isauth);
            viewgroup.like_rl = (RelativeLayout)view1.findViewById(R.id.tips_list_item_like_rl);
            viewgroup.like_homeCover_iv = (ImageView)view1.findViewById(R.id.tips_list_item_like_homecover_iv);
            viewgroup.like_avatar_iv = (ImageView)view1.findViewById(R.id.tips_list_item_like_avatar_iv);
            viewgroup.topic_rl = (RelativeLayout)view1.findViewById(R.id.tips_list_item_topic_rl);
            viewgroup.topic_iv = (ImageView)view1.findViewById(R.id.tips_list_item_topic_iv);
            viewgroup.topic_video_iv = (ImageView)view1.findViewById(R.id.tips_list_item_topic_video_iv);
            viewgroup.follow_iv = (ImageView)view1.findViewById(R.id.tips_list_item_follow_iv);
            viewgroup.content_iv = (ImageView)view1.findViewById(R.id.tips_list_item_content_iv);
            viewgroup.repaly_ll = (LinearLayout)view1.findViewById(R.id.tips_list_item_replay_content_ll);
            viewgroup.repaly_tv = (TextView)view1.findViewById(R.id.tips_list_item_replay_content_tv);
            viewgroup.repaly_iv = (ImageView)view1.findViewById(R.id.tips_list_item_replay_content_iv);
            view1.setTag(viewgroup);
        } else
        {
            viewgroup = (ViewHolder)view.getTag();
        }

        tipsmodel = (TipsModel)list.get(position);
        j = tipsmodel.getIsHead();
        if(j > 0)
        {
            ((ViewHolder) (viewgroup)).tv_news_status.setVisibility(View.VISIBLE);
            if (j == 1)
            {
                ((ViewHolder) (viewgroup)).tv_news_status.setText(R.string.new_news_tip);
            } else
            {
                ((ViewHolder) (viewgroup)).tv_news_status.setText(R.string.history_news_tip);
            }
            ((ViewHolder) (viewgroup)).tv_news_status.setOnTouchListener(new android.view.View.OnTouchListener() {

                public boolean onTouch(View view2, MotionEvent motionevent)
                {
                    return true;
                }
            });

            ((ViewHolder) (viewgroup)).content_item_ll.setVisibility(View.GONE);
        }

        if( j <= 0)
        {
            ((ViewHolder) (viewgroup)).tv_news_status.setVisibility(View.GONE);
            ((ViewHolder) (viewgroup)).content_item_ll.setVisibility(View.VISIBLE);
            ((ViewHolder) (viewgroup)).content_item_ll.setOnClickListener(mOnClickListener);
            ((ViewHolder) (viewgroup)).content_item_ll.setOnLongClickListener(mOnLongClickListener);
            ((ViewHolder) (viewgroup)).content_item_ll.setTag(Integer.valueOf(position));
            if (tipsmodel.getRead() == 1)
            {
                ((ViewHolder) (viewgroup)).iv_news.setVisibility(View.GONE);
            } else
            {
                ((ViewHolder) (viewgroup)).iv_news.setVisibility(View.VISIBLE);
            }
            if (tipsmodel.getUserhonorlevel() != null)
            {
                int k = Integer.parseInt(tipsmodel.getUserhonorlevel());
                if (k <= 0)
                {
                    ((ViewHolder) (viewgroup)).iv_level.setVisibility(View.INVISIBLE);
                } else
                {
                    ((ViewHolder) (viewgroup)).iv_level.setVisibility(View.VISIBLE);
                    String strURL = ImageUtils.getLevelUrl((new StringBuilder(String.valueOf(k))).toString());
                    ImageLoader.getInstance().displayImage(strURL, ((ViewHolder) (viewgroup)).iv_level, Constant.LEVEL_OPTIONS);
                }
            }
            ((ViewHolder) (viewgroup)).iv_isauth.setVisibility(View.VISIBLE);
            if (tipsmodel.getIsauth() != null && Integer.parseInt(tipsmodel.getIsauth()) == 1)
            {
                ((ViewHolder) (viewgroup)).iv_isauth.setBackgroundResource(R.drawable.personal_isauth);
            } else
            if (tipsmodel.getIsauth() != null && Integer.parseInt(tipsmodel.getIsauth()) == 2)
            {
                ((ViewHolder) (viewgroup)).iv_isauth.setBackgroundResource(R.drawable.personal_daren);
            } else
            {
                ((ViewHolder) (viewgroup)).iv_isauth.setVisibility(View.GONE);
            }
            if (tipsmodel.getCommentinfo() != null && tipsmodel.getCommentinfo().getIskana() != null && tipsmodel.getCommentinfo().getIskana().equals("1"))
            {
                ((ViewHolder) (viewgroup)).iv_avatar.setImageResource(R.drawable.topic_no_name);
                ((ViewHolder) (viewgroup)).tv_name.setText(mContext.getString(R.string.no_name_tip));
                ((ViewHolder) (viewgroup)).tv_name.setTextColor(mContext.getResources().getColor(R.color.no_name_color));
                ((ViewHolder) (viewgroup)).iv_avatar.setOnClickListener(null);
                ((ViewHolder) (viewgroup)).iv_avatar.setTag(Integer.valueOf(position));
            } else
            {
                ((ViewHolder) (viewgroup)).iv_avatar.setOnClickListener(mOnClickListener);
                ((ViewHolder) (viewgroup)).iv_avatar.setTag(Integer.valueOf(position));
                String strAvatarURL = ImageUtils.getAvatarUrl(tipsmodel.getActionuid(), tipsmodel.getAvatartime(), "/100");
                ImageLoader.getInstance().displayImage(strAvatarURL, ((ViewHolder) (viewgroup)).iv_avatar, Constant.AVATAR_OPTIONS);
                String strNickName = tipsmodel.getNickname();
                if (strNickName != null)
                {
                    if (strNickName.length() > 10)
                    {
                        strNickName = (new StringBuilder(String.valueOf(strNickName.substring(0, 8)))).append("...").toString();
                    }
                }
                ((ViewHolder) (viewgroup)).tv_name.setText(((CharSequence) (strNickName)));
                ((ViewHolder) (viewgroup)).tv_name.setTextColor(mContext.getResources().getColor(R.color.topic_username_color));
            }
            ((ViewHolder) (viewgroup)).tv_time.setText(tipsmodel.getFormattime());
            String strAction = tipsmodel.getAction();
            ((ViewHolder) (viewgroup)).topic_rl.setVisibility(View.GONE);
            ((ViewHolder) (viewgroup)).follow_iv.setVisibility(View.GONE);
            ((ViewHolder) (viewgroup)).like_rl.setVisibility(View.GONE);
            ((ViewHolder) (viewgroup)).content_iv.setVisibility(View.GONE);
            ((ViewHolder) (viewgroup)).tv_content.setVisibility(View.GONE);
            ((ViewHolder) (viewgroup)).repaly_ll.setVisibility(View.GONE);
            ((ViewHolder) (viewgroup)).content_rl.setMinimumHeight(View.VISIBLE);
            if (strAction == null)
            {
//                continue; /* Loop/switch isn't completed */
                return view1;
            }
            if (strAction.equals(Constant.XG_TYPE_COMMENT) || strAction.equals(Constant.XG_TYPE_ZAN) || strAction.equals(Constant.XG_TYPE_REPOST_TOPIC) || strAction.equals(Constant.XG_TYPE_TOPIC_AT) || strAction.equals(Constant.XG_TYPE_COMMENT_AT))
            {

                ((ViewHolder) (viewgroup)).topic_rl.setVisibility(View.VISIBLE);
                if (tipsmodel.getTopicinfo() != null)
                {
                    ImageLoader.getInstance().displayImage(tipsmodel.getTopicinfo().getContent(), ((ViewHolder) (viewgroup)).topic_iv, Constant.AVATAR_OPTIONS);
                }
                if (tipsmodel.getTopicinfo() != null && tipsmodel.getTopicinfo().getType() != null && tipsmodel.getTopicinfo().getType().equals("5"))
                {
                    ((ViewHolder) (viewgroup)).topic_video_iv.setVisibility(View.VISIBLE);
                } else
                {
                    ((ViewHolder) (viewgroup)).topic_video_iv.setVisibility(View.GONE);
                }
                if (strAction.equals(Constant.XG_TYPE_ZAN))
                {
                    ((ViewHolder) (viewgroup)).content_iv.setVisibility(View.VISIBLE);
                    ((ViewHolder) (viewgroup)).content_iv.setImageResource(R.drawable.icon_comment_detail_fav_select);
                    return view1;
                }
                if (strAction.equals(Constant.XG_TYPE_COMMENT) || strAction.equals(Constant.XG_TYPE_COMMENT_AT))
                {
                    if (tipsmodel.getCommentinfo() != null)
                    {
                        if (tipsmodel.getCommentinfo().getContent() != null && !tipsmodel.getCommentinfo().getContent().equals(""))
                        {
                            ((ViewHolder) (viewgroup)).tv_content.setVisibility(View.VISIBLE);
                            ((ViewHolder) (viewgroup)).tv_content.setText(TextUtil.formatContent(tipsmodel.getCommentinfo().getContent(), mContext));
                            ((ViewHolder) (viewgroup)).tv_content.setMovementMethod(com.gitrose.mobile.view.TextViewFixTouchConsume.LocalLinkMovementMethod.getInstance());
                            return view1;
                        }
                        if (tipsmodel.getCommentinfo().getTxtframe() != null || !tipsmodel.getCommentinfo().getTxtframe().contains("input"))
                        {
                            ((ViewHolder) (viewgroup)).content_iv.setVisibility(View.VISIBLE);
                            ((ViewHolder) (viewgroup)).content_iv.setTag(Integer.valueOf(position));
                            setCommentViewBackground(((ViewHolder) (viewgroup)).content_iv, tipsmodel.getCommentinfo().getTxtframe(), position);
                            return view1;
                        }
                    }
                } else
                {
                    if (!strAction.equals(Constant.XG_TYPE_TOPIC_AT))
                    {
//                        continue; /* Loop/switch isn't completed */
                    }
                    ((ViewHolder) (viewgroup)).tv_content.setVisibility(View.VISIBLE);
                    if (tipsmodel.getTopicinfo() != null && tipsmodel.getTopicinfo().getDesc() != null)
                    {
                        ((ViewHolder) (viewgroup)).tv_content.setText(TextUtil.formatContent(tipsmodel.getTopicinfo().getDesc(), mContext));
                        ((ViewHolder) (viewgroup)).tv_content.setMovementMethod(com.gitrose.mobile.view.TextViewFixTouchConsume.LocalLinkMovementMethod.getInstance());
                        return view1;
                    }
                }
            }
        }

        String strAction = tipsmodel.getAction();
        if (!strAction.equals(Constant.XG_TYPE_REPOST_TOPIC))
        {
            return view1;
        }
        else
        {
            ((ViewHolder) (viewgroup)).tv_content.setVisibility(View.VISIBLE);
            ((ViewHolder) (viewgroup)).tv_content.setText(tipsmodel.getData());
//            return view1;
        }

        if (!strAction.equals(Constant.XG_TYPE_FOLLOW_ME) && !strAction.equals(Constant.XG_TYPE_ADD_FRIENDS))
        {
            if (strAction.equals(Constant.XG_TYPE_LIKE_USER))
            {
                ((ViewHolder) (viewgroup)).like_rl.setVisibility(View.VISIBLE);
                ((ViewHolder) (viewgroup)).content_iv.setVisibility(View.VISIBLE);
                ((ViewHolder) (viewgroup)).content_iv.setImageResource(R.drawable.icon_comment_detail_fav_select);
                ImageLoader.getInstance().displayImage(tipsmodel.getTohomecoverurl(), ((ViewHolder) (viewgroup)).like_homeCover_iv, Constant.AVATAR_OPTIONS);
                String strAvatarURL = ImageUtils.getAvatarUrl(tipsmodel.getTouid(), tipsmodel.getAvatartime(), "/100");
                ImageLoader.getInstance().displayImage(strAvatarURL, ((ViewHolder) (viewgroup)).like_avatar_iv, Constant.AVATAR_OPTIONS);
                return view1;
            }
            if (!strAction.equals(Constant.XG_TYPE_LIKE_COMMENT) && !strAction.equals(Constant.XG_TYPE_REPLAY_COMMENT))
            {
//                continue; /* Loop/switch isn't completed */
            }
            ((ViewHolder) (viewgroup)).content_rl.setMinimumHeight(UiUtils.getInstance(mContext).DipToPixels(45F));
            ((ViewHolder) (viewgroup)).topic_rl.setVisibility(View.VISIBLE);
            ((ViewHolder) (viewgroup)).repaly_ll.setVisibility(View.VISIBLE);
            if (tipsmodel.getTopicinfo() != null)
            {
                ImageLoader.getInstance().displayImage(tipsmodel.getTopicinfo().getContent(), ((ViewHolder) (viewgroup)).topic_iv, Constant.AVATAR_OPTIONS);
            }
            if (tipsmodel.getTopicinfo() != null && tipsmodel.getTopicinfo().getType() != null && tipsmodel.getTopicinfo().getType().equals("5"))
            {
                ((ViewHolder) (viewgroup)).topic_video_iv.setVisibility(View.VISIBLE);
            } else
            {
                ((ViewHolder) (viewgroup)).topic_video_iv.setVisibility(View.GONE);
            }
            if (!strAction.equals(Constant.XG_TYPE_LIKE_COMMENT))
            {
//                break; /* Loop/switch isn't completed */
            }
            ((ViewHolder) (viewgroup)).content_iv.setVisibility(View.VISIBLE);
            ((ViewHolder) (viewgroup)).content_iv.setImageResource(R.drawable.icon_comment_detail_fav_select);
            if (tipsmodel.getCommentinfo() != null)
            {
                if (tipsmodel.getCommentinfo().getContent() != null && !tipsmodel.getCommentinfo().getContent().equals(""))
                {
                    ((ViewHolder) (viewgroup)).repaly_iv.setVisibility(View.GONE);
                    ((ViewHolder) (viewgroup)).repaly_tv.setVisibility(View.VISIBLE);
                    ((ViewHolder) (viewgroup)).repaly_tv.setText(tipsmodel.getCommentinfo().getContent());
                    return view1;
                }
                if (tipsmodel.getCommentinfo().getTxtframe() != null || !tipsmodel.getCommentinfo().getTxtframe().contains("input"))
                {
                    ((ViewHolder) (viewgroup)).repaly_iv.setVisibility(View.VISIBLE);
                    ((ViewHolder) (viewgroup)).repaly_tv.setVisibility(View.GONE);
                    ((ViewHolder) (viewgroup)).repaly_iv.setTag(Integer.valueOf(position));
                    setCommentViewBackground(((ViewHolder) (viewgroup)).repaly_iv, tipsmodel.getCommentinfo().getTxtframe(), position);
                    return view1;
                }
            }
        }
        else
        {
            ((ViewHolder) (viewgroup)).follow_iv.setVisibility(View.VISIBLE);
            ((ViewHolder) (viewgroup)).tv_content.setVisibility(View.VISIBLE);
            ((ViewHolder) (viewgroup)).tv_content.setText(tipsmodel.getData());
            int nRelation = tipsmodel.getRelation();

            ((ViewHolder) (viewgroup)).follow_iv.setVisibility(View.VISIBLE);
        }

        if (tipsmodel.getCommentinfo() != null)
        {
            if (tipsmodel.getCommentinfo().getContent() != null && !tipsmodel.getCommentinfo().getContent().equals(""))
            {
                ((ViewHolder) (viewgroup)).content_iv.setVisibility(View.VISIBLE);
                ((ViewHolder) (viewgroup)).tv_content.setVisibility(View.VISIBLE);
                ((ViewHolder) (viewgroup)).tv_content.setText(tipsmodel.getCommentinfo().getContent());
            } else
            if (tipsmodel.getCommentinfo().getTxtframe() != null || !tipsmodel.getCommentinfo().getTxtframe().contains("input"))
            {
                ((ViewHolder) (viewgroup)).content_iv.setVisibility(View.VISIBLE);
                ((ViewHolder) (viewgroup)).tv_content.setVisibility(View.GONE);
                ((ViewHolder) (viewgroup)).content_iv.setTag(Integer.valueOf(position));
                setCommentViewBackground(((ViewHolder) (viewgroup)).content_iv, tipsmodel.getCommentinfo().getTxtframe(), position);
            }
        }
        if (tipsmodel.getReplycommentinfo() != null)
        {
            if (tipsmodel.getReplycommentinfo().getContent() != null && !tipsmodel.getReplycommentinfo().getContent().equals(""))
            {
                ((ViewHolder) (viewgroup)).repaly_iv.setVisibility(View.GONE);
                ((ViewHolder) (viewgroup)).repaly_tv.setVisibility(View.VISIBLE);
                ((ViewHolder) (viewgroup)).repaly_tv.setText(tipsmodel.getReplycommentinfo().getContent());
                return view1;
            }
//            continue; /* Loop/switch isn't completed */
        } else
        {
            ((ViewHolder) (viewgroup)).repaly_ll.setVisibility(View.GONE);
            return view1;
        }

        if (tipsmodel.getReplycommentinfo().getTxtframe() != null && !tipsmodel.getReplycommentinfo().getTxtframe().contains(Context.INPUT_METHOD_SERVICE))
        {
            ((ViewHolder) (viewgroup)).repaly_iv.setVisibility(View.VISIBLE);
            ((ViewHolder) (viewgroup)).repaly_tv.setVisibility(View.GONE);
            ((ViewHolder) (viewgroup)).repaly_iv.setTag(Integer.valueOf(position));
            setCommentViewBackground(((ViewHolder) (viewgroup)).repaly_iv, tipsmodel.getReplycommentinfo().getTxtframe(), position);
        }

        return view1;
    }

    private void setCommentViewBackground(ImageView imageview, String s, int i)
    {
        i = mContext.getResources().getIdentifier(s, "drawable", mContext.getPackageName());
        if (i == 0)
        {
            ImageLoader.getInstance().displayImage((new StringBuilder("http://www.gitrose.com/download/newemotion/")).append(s).append(".png").toString(), imageview);
            return;
        } else
        {
            imageview.setBackgroundResource(i);
            return;
        }
    }
}
