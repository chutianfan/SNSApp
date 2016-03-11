package com.gitrose.mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.internal.view.SupportMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuLayout;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuView;
import com.baoyz.swipemenulistview.SwipeMenuView.OnSwipeItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
//import com.sea_monster.core.network.ApiReqeust;
import com.gitrose.mobile.R;
//import com.gitrose.mobile.PersonalActivity;
import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.model.UserInfo;
import com.gitrose.mobile.view.CircleImageView;
import java.util.List;

public class FriendsAdapter1 extends BaseAdapter implements OnSwipeItemClickListener {
    private Context mContext;
    private OnClickListener mOnClickListener;
    private SwipeMenuListView mSwipeMenuListView;
    private List<UserInfo> mUsersList;
    private OnMenuItemClickListener onMenuItemClickListener;
    private ViewHolder viewholder;

    /* renamed from: com.gitrose.mobile.adapter.FriendsAdapter1.1 */
    class C03391 implements OnClickListener {
        C03391() {
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.friends_user_head:
                    FriendsAdapter1.this.startPersonalActivity(((UserInfo) FriendsAdapter1.this.mUsersList.get(Integer.parseInt(view.getTag().toString()))).getUid());
//                case R.id.friends_follow_cancle:
//                    FriendsAdapter1.this.mSwipeMenuListView.smoothCloseMenu(Integer.parseInt(view.getTag().toString()) + 1);
//                default:
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

    public FriendsAdapter1(Context context) {
        this.mOnClickListener = new C03391();
        this.mContext = context;
    }

    public FriendsAdapter1(Context context, List<UserInfo> users) {
        this.mOnClickListener = new C03391();
        this.mContext = context;
        this.mUsersList = users;
    }

    public FriendsAdapter1(Context mContext, List<UserInfo> mUsers, SwipeMenuListView menuListView) {
        this.mOnClickListener = new C03391();
        this.mContext = mContext;
        this.mUsersList = mUsers;
        this.mSwipeMenuListView = menuListView;
    }

    public void setUsersList(List<UserInfo> usersList) {
        this.mUsersList = usersList;
        notifyDataSetChanged();
    }

    public int getCount() {
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
        SwipeMenuLayout layout;
        SwipeMenu menu = new SwipeMenu(this.mContext);
        menu.setViewType(position);
        createMenu(menu);
        SwipeMenuView menuView;
        if (convertView == null) {
            View contentView = getView2(position, convertView, parent);
            menuView = new SwipeMenuView(menu, (SwipeMenuListView) parent);
            SwipeMenuListView listView = (SwipeMenuListView) parent;
            layout = new SwipeMenuLayout(contentView, menuView, listView.getCloseInterpolator(), listView.getOpenInterpolator());
            menuView.setPosition(position);
            menuView.setOnSwipeItemClickListener(this);
        } else {
            layout = (SwipeMenuLayout) convertView;
            menuView = layout.getMenuView();
            if (!(menuView == null || menu == null)) {
                menuView.change(menu);
                menuView.setPosition(position);
                menuView.setOnSwipeItemClickListener(this);
            }
            getView2(position, layout.getContentView(), parent);
        }
        layout.closeMenu();
        return layout;
    }

    public void createMenu(SwipeMenu menu) {
        SwipeMenuItem item = new SwipeMenuItem(this.mContext);
        item.setTitle("Item 1");
        //item.setBackground(new ColorDrawable(-7829368));
        item.setWidth(300);
        menu.addMenuItem(item);
        item = new SwipeMenuItem(this.mContext);
        item.setTitle("Item 2");
        item.setBackground(new ColorDrawable(SupportMenu.CATEGORY_MASK));
        item.setWidth(300);
        menu.addMenuItem(item);
    }

    public View getView2(int position, View convertView, ViewGroup parent) {
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
        this.viewholder.user_follow_cancle.setTag(Integer.valueOf(position));
        if (this.mOnClickListener != null) {
            this.viewholder.user_head.setOnClickListener(this.mOnClickListener);
            this.viewholder.user_follow_cancle.setOnClickListener(this.mOnClickListener);
        }
        if (this.mSwipeMenuListView == null) {
            this.viewholder.user_follow_cancle.setVisibility(View.GONE);
            this.viewholder.user_right_line.setVisibility(View.GONE);
        }
        this.viewholder.user_name.setText(((UserInfo) this.mUsersList.get(position)).getNickname());
        this.viewholder.user_introduce.setText(((UserInfo) this.mUsersList.get(position)).getSign());
        this.viewholder.user_age.setText(((UserInfo) this.mUsersList.get(position)).getAge());
        if (1 == ((UserInfo) this.mUsersList.get(position)).getIsblock()) {
            this.viewholder.user_is_block.setVisibility(View.VISIBLE);
        } else {
            this.viewholder.user_is_block.setVisibility(View.GONE);
        }
        if (((UserInfo) this.mUsersList.get(position)).getGender() != null) {
            switch (Integer.parseInt(((UserInfo) this.mUsersList.get(position)).getGender())) {
                case 1:
                    this.viewholder.sex_age.setBackgroundResource(R.drawable.personal_male_bg);
                    this.viewholder.user_sex.setImageResource(R.drawable.male);
                    break;
                case 2:
                    this.viewholder.sex_age.setBackgroundResource(R.drawable.personal_female_bg);
                    this.viewholder.user_sex.setImageResource(R.drawable.female);
                    break;
                default:
                    this.viewholder.sex_age.setBackgroundResource(R.drawable.personal_male_bg);
                    this.viewholder.user_sex.setImageResource(R.drawable.male);
                    break;
            }
        }
        switch (Integer.parseInt(((UserInfo) this.mUsersList.get(position)).getRelation())) {
            case 0:
                if (this.mSwipeMenuListView != null) {
                    this.viewholder.user_follow_cancle.setVisibility(View.VISIBLE);
                }
                this.viewholder.user_follow_cancle.setImageResource(R.drawable.friends_list_add);
                break;
            case 1:
                if (this.mSwipeMenuListView != null) {
                    this.viewholder.user_follow_cancle.setVisibility(View.VISIBLE);
                }
                this.viewholder.user_follow_cancle.setImageResource(R.drawable.friends_list_add);
                break;
            case 2:
                if (this.mSwipeMenuListView != null) {
                    this.viewholder.user_follow_cancle.setVisibility(View.VISIBLE);
                }
                this.viewholder.user_follow_cancle.setImageResource(R.drawable.friends_list_is);
                break;
//            case ApiReqeust.PUT_METHOD /*3*/:
//                if (this.mSwipeMenuListView != null) {
//                    this.viewholder.user_follow_cancle.setVisibility(VIew.VISIBLE);
//                }
//                this.viewholder.user_follow_cancle.setImageResource(R.drawable.friends_list_both);
//                break;
//            case PersonalActivity.TOPIC_DETAIL_RESULT /*4*/:
//                this.viewholder.user_follow_cancle.setVisibility(View.GONE);
//                this.viewholder.user_follow_cancle.setImageResource(R.drawable.friends_list_both);
//                break;
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

    public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
        if (this.onMenuItemClickListener != null) {
            this.onMenuItemClickListener.onMenuItemClick(view.getPosition(), menu, index);
            ((SwipeMenuLayout) view.getParent()).smoothCloseMenu();
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }
}
