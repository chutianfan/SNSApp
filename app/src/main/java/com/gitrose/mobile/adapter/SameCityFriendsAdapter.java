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
import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.UserInfo;
import com.gitrose.mobile.utils.ImageUtils;
import com.gitrose.mobile.view.CircleImageView;
import java.util.List;

public class SameCityFriendsAdapter extends BaseAdapter {
    private Context mContext;
    private List<TutuUsers> mFriendList;
    private OnClickListener mOnClickListener;

    private static class ViewHolder {
        RelativeLayout sex_age;
        TextView user_age;
        CircleImageView user_head;
        TextView user_introduce;
        ImageView user_is_block;
        TextView user_lasttime;
        TextView user_name;
        TextView user_nearby;
        ImageView user_sex;

        private ViewHolder() {
        }
    }

    public SameCityFriendsAdapter(Context context, List<TutuUsers> friendList, OnClickListener onClickListener) {
        this.mContext = context;
        this.mOnClickListener = onClickListener;
        this.mFriendList = friendList;
    }

    public int getCount() {
        return this.mFriendList.size();
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
            convertView = View.inflate(this.mContext, R.layout.activity_same_city_friends_item, null);
            viewholder.sex_age = (RelativeLayout) convertView.findViewById(R.id.friends_sex_age);
            viewholder.user_head = (CircleImageView) convertView.findViewById(R.id.friends_user_head);
            viewholder.user_nearby = (TextView) convertView.findViewById(R.id.nearby_distance);
            viewholder.user_name = (TextView) convertView.findViewById(R.id.friends_user_name_nick);
            viewholder.user_introduce = (TextView) convertView.findViewById(R.id.friends_introduce);
            viewholder.user_sex = (ImageView) convertView.findViewById(R.id.friends_sex_icon);
            viewholder.user_age = (TextView) convertView.findViewById(R.id.friends_age_text);
            viewholder.user_is_block = (ImageView) convertView.findViewById(R.id.friends_is_block);
            viewholder.user_lasttime = (TextView) convertView.findViewById(R.id.nearby_time);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        TutuUsers userInfo = (TutuUsers) this.mFriendList.get(position);
        ImageLoader.getInstance().displayImage(ImageUtils.getAvatarUrl(userInfo.getUid(), userInfo.getAvatartime()), viewholder.user_head, Constant.AVATAR_OPTIONS);
        viewholder.user_head.setTag(userInfo);
        viewholder.user_head.setOnClickListener(this.mOnClickListener);
        viewholder.user_name.setText(userInfo.getNickname());
        viewholder.user_introduce.setText(userInfo.getSign());
        viewholder.user_age.setText(userInfo.getAge());
        if (1 == userInfo.getIsblock()) {
            viewholder.user_is_block.setVisibility(View.VISIBLE);
        } else {
            viewholder.user_is_block.setVisibility(View.GONE);
        }
        if (userInfo.getGender() != null) {
            switch (Integer.parseInt(String.valueOf(userInfo.getGender()))) {
                case 1:
                    viewholder.sex_age.setBackgroundResource(R.drawable.personal_male_bg);
                    viewholder.user_sex.setImageResource(R.drawable.male);
                    break;
                case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
                    viewholder.sex_age.setBackgroundResource(R.drawable.personal_female_bg);
                    viewholder.user_sex.setImageResource(R.drawable.female);
                    break;
                default:
                    viewholder.sex_age.setBackgroundResource(R.drawable.personal_male_bg);
                    viewholder.user_sex.setImageResource(R.drawable.male);
                    break;
            }
        }
        viewholder.user_nearby.setText(userInfo.getDistance());
        viewholder.user_lasttime.setText(userInfo.getLasttime());
        return convertView;
    }
}
