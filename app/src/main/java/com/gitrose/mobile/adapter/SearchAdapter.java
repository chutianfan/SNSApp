package com.gitrose.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gitrose.greendao.TopicInfo;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.R;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.TopicInfoList;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by GITRose on 1/5/2016.
 */
public class SearchAdapter extends BaseAdapter {

    public Context mContext;
    private ArrayList<TopicInfo> topicInfoList;
    private ArrayList<TutuUsers> userInfoList;
    private int nCurrentTab = 0;


    public SearchAdapter(Context context)
    {
        this.mContext = context;
    }

    public void SetList(ArrayList<TopicInfo> list, int nType)
    {
        this.topicInfoList = list;
        this.nCurrentTab = nType;
        notifyDataSetChanged();
    }

    public void SetUserList(ArrayList<TutuUsers> list, int nType)
    {
        this.userInfoList = list;
        this.nCurrentTab = nType;
        notifyDataSetChanged();
    }

    public class ViewHolder {
        ImageView iv_auth_left;
        ImageView iv_auth_right;
        ImageView iv_image_left;
        ImageView iv_image_right;
        ImageView iv_left_redpacket;
        ImageView iv_level_left;
        ImageView iv_level_right;
        CircleImageView iv_nearby_icon_left;
        CircleImageView iv_new_icon_right;
        ImageView iv_right_redpacket;
        ImageView iv_video_mark_left;
        ImageView iv_video_mark_right;
        ImageView iv_zan_left;
        ImageView iv_zan_right;
        TextView nearby_zan_left;
        LinearLayout rl_nearby_right;
        LinearLayout rl_new_left;
        TextView tv_name_left;
        TextView tv_name_right;
        TextView tv_nearby_left;
        TextView tv_nearby_right;
        TextView tv_nearby_zan_right;
    }

    private static class UserViewHolder {
        ImageView avatar_iv;
        ImageView isauth_iv;
        RelativeLayout item_rl;
        ImageView level_iv;
        TextView name_tv;
        ImageView rank_iv;
        TextView rank_tv;
        ImageView relation_iv;
        TextView sigh_tv;

        private UserViewHolder() {
        }
    }

    @Override
    public int getCount() {
        int nCount = 0;
        if(nCurrentTab == 0) {
            if (this.topicInfoList != null)
                nCount = this.topicInfoList.size() % 2 == 0 ? this.topicInfoList.size() / 2 : (this.topicInfoList.size() / 2) + 1;
        }
        else if(nCurrentTab == 1)
        {
            if(this.userInfoList != null)
                nCount = this.userInfoList.size();
        }

        return nCount;
    }

    @Override
    public Object getItem(int position) {
//        if(this.topicInfoList == null)
//            return null;
//
//        return topicInfoList.get(position);
        Object obj = null;
        if(nCurrentTab == 0)
        {
            if(this.topicInfoList != null)
                obj = topicInfoList.get(position);
        }
        else if(nCurrentTab == 0)
        {
            if(this.userInfoList != null)
                obj = userInfoList.get(position);
        }

        return obj;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if(nCurrentTab == 0)
            view = GetVideoView(position, convertView, parent);
        else if(nCurrentTab == 1)
            view = GetUserInfoView(position, convertView, parent);

        return view;
    }

    private View GetUserInfoView(int position, View convertView, ViewGroup parent) {

        UserViewHolder viewholder;
        TutuUsers item = this.userInfoList.get(position);

        if (convertView == null || convertView.findViewById(R.id.square_hotuser_rank_rl) == null) {

            viewholder = new UserViewHolder();
            convertView = View.inflate(this.mContext, R.layout.activity_square_hot_user_item, null);
            viewholder.item_rl = (RelativeLayout) convertView.findViewById(R.id.square_hot_user_item_rl);
            viewholder.rank_tv = (TextView) convertView.findViewById(R.id.item_rank_tv);
            viewholder.name_tv = (TextView) convertView.findViewById(R.id.friends_user_name_nick);
            viewholder.sigh_tv = (TextView) convertView.findViewById(R.id.friends_introduce);
            viewholder.rank_iv = (ImageView) convertView.findViewById(R.id.item_rank_iv);
            viewholder.avatar_iv = (ImageView) convertView.findViewById(R.id.friends_user_head);
            viewholder.isauth_iv = (ImageView) convertView.findViewById(R.id.iv_isauth);
            viewholder.level_iv = (ImageView) convertView.findViewById(R.id.friends_userhonorlevel);
            viewholder.relation_iv = (ImageView) convertView.findViewById(R.id.fans_item_right_iv);

            convertView.setTag(viewholder);

        } else {

            viewholder = (UserViewHolder) convertView.getTag();

        }

        String strUserIcon = ImageUtils.getUserIconUrl(item.getProfileFile());
        ImageLoader.getInstance().displayImage(strUserIcon, viewholder.avatar_iv, Constant.AVATAR_OPTIONS);

        String nick = item.getNickname();
//            if (!(item.getRemarkname() == null || item.getRemarkname().equals(""))) {
//                nick = item.getRemarkname();
//            }
        if (nick.length() > 7) {
            nick = nick.substring(0, 6) + "...";
        }
        viewholder.name_tv.setText(nick);
        viewholder.sigh_tv.setText(item.getRemarkname());

        return convertView;
    }

    private View GetVideoView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        String isAuth;
        int level;
        TopicInfo topicInfo;
        int likeNum;
        CharSequence string;
        TopicInfo square_right = null;
        TopicInfo square_left = this.topicInfoList.get(position * 2);
        if ((position * 2) + 1 < this.topicInfoList.size()) {
            square_right = this.topicInfoList.get((position * 2) + 1);
        }
        if (convertView == null || !(convertView.getTag() instanceof ViewHolder)) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(this.mContext, R.layout.square_nearby_item, null);
            viewHolder.rl_new_left = (LinearLayout) convertView.findViewById(R.id.ll_nearby_left);
            viewHolder.iv_nearby_icon_left = (CircleImageView) convertView.findViewById(R.id.iv_nearby_icon_left);
            viewHolder.iv_auth_left = (ImageView) convertView.findViewById(R.id.iv_auth_left);
            viewHolder.iv_level_left = (ImageView) convertView.findViewById(R.id.iv_level_left);
            viewHolder.tv_name_left = (TextView) convertView.findViewById(R.id.tv_name_left);
            viewHolder.iv_image_left = (ImageView) convertView.findViewById(R.id.iv_image_left);
            viewHolder.iv_video_mark_left = (ImageView) convertView.findViewById(R.id.iv_video_mark_left);
            viewHolder.nearby_zan_left = (TextView) convertView.findViewById(R.id.tv_nearby_zan_left);
            viewHolder.iv_zan_left = (ImageView) convertView.findViewById(R.id.iv_nearby_left);
            viewHolder.tv_nearby_left = (TextView) convertView.findViewById(R.id.tv_nearby_left);
            viewHolder.iv_left_redpacket = (ImageView) convertView.findViewById(R.id.left_redpacket);
            viewHolder.rl_nearby_right = (LinearLayout) convertView.findViewById(R.id.rl_nearby_right);
            viewHolder.iv_new_icon_right = (CircleImageView) convertView.findViewById(R.id.iv_nearby_icon_right);
            viewHolder.iv_auth_right = (ImageView) convertView.findViewById(R.id.iv_auth_right);
            viewHolder.iv_level_right = (ImageView) convertView.findViewById(R.id.iv_level_right);
            viewHolder.tv_name_right = (TextView) convertView.findViewById(R.id.tv_name_right);
            viewHolder.iv_image_right = (ImageView) convertView.findViewById(R.id.iv_image_right);
            viewHolder.iv_video_mark_right = (ImageView) convertView.findViewById(R.id.iv_video_mark_right);
            viewHolder.tv_nearby_zan_right = (TextView) convertView.findViewById(R.id.tv_nearby_zan_right);
            viewHolder.iv_zan_right = (ImageView) convertView.findViewById(R.id.iv_zan_right);
            viewHolder.tv_nearby_right = (TextView) convertView.findViewById(R.id.tv_nearby_right);
            viewHolder.iv_right_redpacket = (ImageView) convertView.findViewById(R.id.right_redpacket);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (square_left != null) {
            viewHolder.rl_new_left.setVisibility(View.VISIBLE);

            // set values to controls
//            viewHolder.iv_image_left.

            ImageLoader.getInstance().displayImage(square_left.getContent(), viewHolder.iv_image_left, Constant.SMALL_PICTURE_OPTIONS_WHITE);

        } else {
            viewHolder.rl_new_left.setVisibility(View.INVISIBLE);
        }
        if (square_right != null) {
            viewHolder.rl_nearby_right.setVisibility(View.VISIBLE);

            // set values to controls

            ImageLoader.getInstance().displayImage(square_right.getContent(), viewHolder.iv_image_right, Constant.SMALL_PICTURE_OPTIONS_WHITE);

        } else {
            viewHolder.rl_nearby_right.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
}
