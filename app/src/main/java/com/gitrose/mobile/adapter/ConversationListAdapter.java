package com.gitrose.mobile.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.gitrose.mobile.R;
import com.gitrose.mobile.application.MyContext;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.model.MessageRelationModel;
//import io.rong.imlib.RongIMClient.GetUserInfoCallback;
//import io.rong.imlib.RongIMClient.GetUserInfoCallback.ErrorCode;
//import io.rong.imlib.RongIMClient.UserInfo;
import java.util.Date;
import java.util.HashMap;
//import shouji.gexing.framework.utils.FaceUtils;
//import shouji.gexing.framework.utils.TimeUtils;

public class ConversationListAdapter extends ArrayListAdapter<MessageRelationModel> {
    public ImageGetter getter;
    private OnClickListener mOnClickListener;
    //private HashMap<String, UserInfo> userMap;

    /* renamed from: com.gitrose.mobile.adapter.ConversationListAdapter.1 */
    class C03371 implements ImageGetter {
        C03371() {
        }

        public Drawable getDrawable(String source) {
            Drawable d = ConversationListAdapter.this.mContext.getResources().getDrawable(Integer.parseInt(source));
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            return d;
        }
    }

    private static class ViewHolder {
        ImageView isblock_iv;
        ImageView iv_avatar;
        TextView news_tv;
        TextView tv_chat;
        TextView tv_name;
        TextView tv_time;

        private ViewHolder() {
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.ConversationListAdapter.2 */
//    class C05352 implements GetUserInfoCallback {
//        private final /* synthetic */ ImageView val$UserAvatarImg;
//        private final /* synthetic */ String val$uid;
//
//        C05352(String str, ImageView imageView) {
//            this.val$uid = str;
//            this.val$UserAvatarImg = imageView;
//        }
//
//        public void onSuccess(UserInfo userInfo) {
//            ConversationListAdapter.this.userMap.put(this.val$uid, userInfo);
//            ImageLoader.getInstance().displayImage(userInfo.getPortraitUri(), this.val$UserAvatarImg, Constant.AVATAR_OPTIONS);
//        }
//
//        public void onError(ErrorCode arg0) {
//        }
//    }

    public ConversationListAdapter(Context context, OnClickListener onClickListener) {
        super(context);
//        this.userMap = new HashMap();
//        this.getter = new C03371();
//        this.mOnClickListener = onClickListener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = View.inflate(this.mContext, R.layout.activity_private_list_item, null);
            viewholder.iv_avatar = (ImageView) convertView.findViewById(R.id.private_list_item_iv_avatar);
            viewholder.news_tv = (TextView) convertView.findViewById(R.id.private_list_item_tv_news_count);
            viewholder.isblock_iv = (ImageView) convertView.findViewById(R.id.private_list_item_iv_isblock);
            viewholder.tv_name = (TextView) convertView.findViewById(R.id.private_list_item_tv_name);
            viewholder.tv_time = (TextView) convertView.findViewById(R.id.private_list_item_tv_time);
            viewholder.tv_chat = (TextView) convertView.findViewById(R.id.private_list_item_tv_chat_content);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
//        MessageRelationModel item = (MessageRelationModel) this.mList.get(position);
//        viewholder.tv_name.setText(item.getNickname());
//        ImageLoader.getInstance().displayImage(new StringBuilder(ConstantURL.AVATAR_DOMAIN).append(item.getUid()).append("/").append(item.getConversatin().getSentTime()).append(Constant.HEAD_BIG_SIZE).toString(), viewholder.iv_avatar, Constant.AVATAR_OPTIONS);
//        viewholder.iv_avatar.setOnClickListener(this.mOnClickListener);
//        viewholder.iv_avatar.setTag(item);
//        viewholder.tv_time.setText(TimeUtils.getShowTimeStringefore(new Date(Long.parseLong(item.getLastmsgtime())), "MM-dd yyyy"));
//        String content = item.getLastmsg();
//        if (content != null) {
//            viewholder.tv_chat.setText(Html.fromHtml(FaceUtils.getCharSequence(content), this.getter, null));
//        }
//        int unreadCount = item.getConversatin().getUnreadMessageCount();
//        if (unreadCount > 0) {
//            viewholder.news_tv.setVisibility(View.VISIBLE);
//            viewholder.news_tv.setText(new StringBuilder(String.valueOf(unreadCount)).toString());
//        } else {
//            viewholder.news_tv.setVisibility(View.INVISIBLE);
//        }
//        if (item.getIsblock().equals(Constant.SYSTEM_UID)) {
//            viewholder.isblock_iv.setVisibility(View.VISIBLE);
//        } else {
//            viewholder.isblock_iv.setVisibility(View.GONE);
//        }
        return convertView;
    }

    public void setUserInfo(TextView userNameTv, ImageView UserAvatarImg, String uid) {
//        MyContext.getInstance().mRongIMClient.getUserInfo(uid, new C05352(uid, UserAvatarImg));
    }
}
