package com.gitrose.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.R;
//import com.gitrose.mobile.PersonalNewActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.view.CircleImageView;
import java.util.List;
import qalsdk.BaseConstants;

public class SearchDataFansAdapter extends BaseAdapter {
    private boolean isSquareSearch;
    private Context mContext;
    private List<TutuUsers> mFriendList;
    private OnClickListener mOnclicklistener;

    private static class ViewHolder {
        TextView follow_me_time;
        ImageView follow_me_time_iv;
        ImageView friends_userhonorlevel;
        ImageView iv_isauth;
        TextView label_tv;
        ImageView relation_iv;
        RelativeLayout sex_age;
        TextView user_age;
        CircleImageView user_head;
        TextView user_introduce;
        ImageView user_is_block;
        TextView user_name;
        ImageView user_sex;

        private ViewHolder() {
        }
    }

    public SearchDataFansAdapter(Context context, List<TutuUsers> friendList, OnClickListener onclicklistener) {
        this.mContext = context;
        this.mFriendList = friendList;
        this.mOnclicklistener = onclicklistener;
    }

    public SearchDataFansAdapter(Context context, boolean isSquareSearch, List<TutuUsers> friendList) {
        this.mContext = context;
        this.isSquareSearch = isSquareSearch;
        this.mFriendList = friendList;
    }

    public int getCount() {
        if (this.mFriendList == null) {
            return 0;
        }
        return this.mFriendList.size();
    }

    public void setUsersList(List<TutuUsers> friendList) {
        this.mFriendList = friendList;
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return this.mFriendList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = View.inflate(this.mContext, R.layout.activity_fans_item, null);
            viewholder.label_tv = (TextView) convertView.findViewById(R.id.fans_item_catalog);
            viewholder.sex_age = (RelativeLayout) convertView.findViewById(R.id.friends_sex_age);
            viewholder.user_head = (CircleImageView) convertView.findViewById(R.id.friends_user_head);
            viewholder.user_name = (TextView) convertView.findViewById(R.id.friends_user_name_nick);
            viewholder.user_introduce = (TextView) convertView.findViewById(R.id.friends_introduce);
            viewholder.user_sex = (ImageView) convertView.findViewById(R.id.friends_sex_icon);
            viewholder.user_age = (TextView) convertView.findViewById(R.id.friends_age_text);
            viewholder.follow_me_time = (TextView) convertView.findViewById(R.id.fans_follow_me_time);
            viewholder.follow_me_time_iv = (ImageView) convertView.findViewById(R.id.fans_follow_me_time_iv);
            viewholder.user_is_block = (ImageView) convertView.findViewById(R.id.friends_is_block);
            viewholder.friends_userhonorlevel = (ImageView) convertView.findViewById(R.id.friends_userhonorlevel);
            viewholder.relation_iv = (ImageView) convertView.findViewById(R.id.fans_item_right_iv);
            viewholder.iv_isauth = (ImageView) convertView.findViewById(R.id.iv_isauth);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        TutuUsers userInfo = (TutuUsers) this.mFriendList.get(position);
        ImageLoader.getInstance().displayImage(ImageUtils.getAvatarUrl(userInfo.getUid(), userInfo.getAvatartime()), viewholder.user_head, Constant.AVATAR_OPTIONS);
        viewholder.user_head.setTag(Integer.valueOf(position));
        viewholder.user_head.setOnClickListener(this.mOnclicklistener);
        String nick = userInfo.getNickname();
        if (!(userInfo.getRemarkname() == null || userInfo.getRemarkname().equals(BaseConstants.ah))) {
            nick = userInfo.getRemarkname();
        }
        if (nick.length() > 7) {
            nick = nick.substring(0, 6) + "...";
        }
        viewholder.user_name.setText(nick);
        viewholder.user_introduce.setText(userInfo.getSign());
        viewholder.user_age.setText(String.valueOf(userInfo.getAge()));
        viewholder.follow_me_time_iv.setVisibility(View.INVISIBLE);
        viewholder.label_tv.setVisibility(View.GONE);
        viewholder.iv_isauth.setVisibility(View.VISIBLE);
        if (userInfo.getIsauth() != null && Integer.parseInt(userInfo.getIsauth()) == 1) {
            viewholder.iv_isauth.setBackgroundResource(R.drawable.personal_isauth);
        } else if (userInfo.getIsauth() == null || Integer.parseInt(userInfo.getIsauth()) != 2) {
            viewholder.iv_isauth.setVisibility(View.GONE);
        } else {
            viewholder.iv_isauth.setBackgroundResource(R.drawable.personal_daren);
        }
        if (userInfo.getUserhonorlevel().intValue() > 0) {
            int level = userInfo.getUserhonorlevel().intValue();
            if (level <= 0) {
                viewholder.friends_userhonorlevel.setVisibility(View.INVISIBLE);
            } else {
                viewholder.friends_userhonorlevel.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(ImageUtils.getLevelUrl(new StringBuilder(String.valueOf(level)).toString()), viewholder.friends_userhonorlevel, Constant.LEVEL_OPTIONS);
            }
        }
        if (1 == userInfo.getIsblock()) {
            viewholder.user_is_block.setVisibility(View.VISIBLE);
        } else {
            viewholder.user_is_block.setVisibility(View.GONE);
        }
        if (userInfo.getGender().intValue() != -1) {
            switch (userInfo.getGender().intValue()) {
                case 1:
                    viewholder.sex_age.setBackgroundResource(R.drawable.personal_male_bg);
                    viewholder.user_sex.setImageResource(R.drawable.male);
                    break;
                case 2:
                    viewholder.sex_age.setBackgroundResource(R.drawable.personal_female_bg);
                    viewholder.user_sex.setImageResource(R.drawable.female);
                    break;
                default:
                    viewholder.sex_age.setBackgroundResource(R.drawable.personal_male_bg);
                    viewholder.user_sex.setImageResource(R.drawable.male);
                    break;
            }
        }
        if (this.isSquareSearch) {
            viewholder.relation_iv.setVisibility(View.GONE);
        } else {
            viewholder.relation_iv.setVisibility(View.VISIBLE);
            if (userInfo.getRelation() != null) {
                switch (Integer.parseInt(userInfo.getRelation())) {
                    case 0:
                    case 1:
                        viewholder.relation_iv.setBackgroundResource(R.drawable.fans_follow_me_button);
                        break;
                    case 2:
                        viewholder.relation_iv.setBackgroundResource(R.drawable.fans_i_follow_button);
                        break;
                    case 3:
                        viewholder.relation_iv.setBackgroundResource(R.drawable.fans_2_follow_button);
                        break;
                    default:
                        viewholder.relation_iv.setVisibility(View.INVISIBLE);
                        break;
                }
            }
            viewholder.relation_iv.setTag(Integer.valueOf(position));
            viewholder.relation_iv.setOnClickListener(this.mOnclicklistener);
        }
        return convertView;
    }
}
