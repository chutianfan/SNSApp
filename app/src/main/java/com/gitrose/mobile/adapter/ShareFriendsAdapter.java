package com.gitrose.mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.gitrose.mobile.R;
//import com.gitrose.mobile.PersonalActivity;
import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.model.UserInfo;
import com.gitrose.mobile.view.CircleImageView;
import java.util.List;

public class ShareFriendsAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private OnClickListener mOnClickListener;
    private List<UserInfo> mUsersList;
    private ViewHolder viewholder;

    /* renamed from: com.gitrose.mobile.adapter.ShareFriendsAdapter.1 */
    class C03441 implements OnClickListener {
        C03441() {
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.friends_user_head:
                    ShareFriendsAdapter.this.startPersonalActivity(((UserInfo) ShareFriendsAdapter.this.mUsersList.get(Integer.parseInt(view.getTag().toString()))).getUid());
                default:
            }
        }
    }

    private static class ViewHolder {
        RelativeLayout sex_age;
        TextView user_age;
        ImageView user_follow_cancle;
        CircleImageView user_head;
        TextView user_introduce;
        ImageView user_is_block;
        TextView user_name;
        ImageView user_right_line;
        ImageView user_sex;

        private ViewHolder() {
        }
    }

    public ShareFriendsAdapter(Context context) {
        this.mOnClickListener = new C03441();
        this.mContext = context;
    }

    public void setUsersList(List<UserInfo> usersList) {
        this.mUsersList = usersList;
        notifyDataSetChanged();
    }

    public int getCount() {
        if (this.mUsersList == null) {
            return 0;
        }
        return this.mUsersList.size();
    }

    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    public int getItemViewType(int position) {
        return position;
    }

    public Object getItem(int position) {
        return this.mUsersList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        this.viewholder = null;
        if (convertView == null) {
            this.viewholder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.activity_friends_item, null);
            this.viewholder.sex_age = (RelativeLayout) convertView.findViewById(R.id.friends_sex_age);
            this.viewholder.user_head = (CircleImageView) convertView.findViewById(R.id.friends_user_head);
            this.viewholder.user_name = (TextView) convertView.findViewById(R.id.friends_user_name_nick);
            this.viewholder.user_introduce = (TextView) convertView.findViewById(R.id.friends_introduce);
            this.viewholder.user_sex = (ImageView) convertView.findViewById(R.id.friends_sex_icon);
//            this.viewholder.user_follow_cancle = (ImageView) convertView.findViewById(R.id.friends_follow_cancle);
//            this.viewholder.user_right_line = (ImageView) convertView.findViewById(R.id.right_line);
            this.viewholder.user_age = (TextView) convertView.findViewById(R.id.friends_age_text);
            this.viewholder.user_is_block = (ImageView) convertView.findViewById(R.id.friends_is_block);
            convertView.setTag(this.viewholder);
        } else {
            this.viewholder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(new StringBuilder(ConstantURL.AVATAR_DOMAIN).append(((UserInfo) this.mUsersList.get(position)).getUid()).append("/").append(((UserInfo) this.mUsersList.get(position)).getAvatartime()).append(Constant.HEAD_SMALL_SIZE).toString(), this.viewholder.user_head, Constant.AVATAR_OPTIONS);
        this.viewholder.user_head.setTag(Integer.valueOf(position));
        this.viewholder.user_follow_cancle.setVisibility(View.GONE);
        if (this.mOnClickListener != null) {
            this.viewholder.user_head.setOnClickListener(this.mOnClickListener);
        }
        this.viewholder.user_follow_cancle.setVisibility(View.GONE);
        this.viewholder.user_right_line.setVisibility(View.GONE);
        this.viewholder.user_is_block.setVisibility(View.GONE);
        this.viewholder.user_name.setText(((UserInfo) this.mUsersList.get(position)).getNickname());
        this.viewholder.user_introduce.setText(((UserInfo) this.mUsersList.get(position)).getSign());
        this.viewholder.user_age.setText(((UserInfo) this.mUsersList.get(position)).getAge());
        if (((UserInfo) this.mUsersList.get(position)).getGender() != null) {
            switch (Integer.parseInt(((UserInfo) this.mUsersList.get(position)).getGender())) {
                case 1:
                    this.viewholder.sex_age.setBackgroundResource(R.drawable.personal_male_bg);
                    this.viewholder.user_sex.setImageResource(R.drawable.male);
                    break;
                case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
                    this.viewholder.sex_age.setBackgroundResource(R.drawable.personal_female_bg);
                    this.viewholder.user_sex.setImageResource(R.drawable.female);
                    break;
                default:
                    this.viewholder.sex_age.setBackgroundResource(R.drawable.personal_male_bg);
                    this.viewholder.user_sex.setImageResource(R.drawable.male);
                    break;
            }
        }
        return convertView;
    }

    private void startPersonalActivity(String user_id) {
//        Intent intent = new Intent();
//        intent.setClass(this.mContext, PersonalActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString(PersonalActivity.PERSONAL_USER_ID, user_id);
//        intent.putExtras(bundle);
//        this.mContext.startActivity(intent);
    }
}
