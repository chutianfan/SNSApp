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
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.model.SessionModel;
import java.util.Date;
//import shouji.gexing.framework.utils.FaceUtils;
//import shouji.gexing.framework.utils.TimeUtils;

public class PrivateListAdapter extends ArrayListAdapter<SessionModel> {
    public ImageGetter getter;
    private OnClickListener mOnClickListener;

    /* renamed from: com.gitrose.mobile.adapter.PrivateListAdapter.1 */
    class C03421 implements ImageGetter {
        C03421() {
        }

        public Drawable getDrawable(String source) {
            Drawable d = PrivateListAdapter.this.mContext.getResources().getDrawable(Integer.parseInt(source));
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

    public PrivateListAdapter(Context context, OnClickListener onClickListener) {
        super(context);
        this.getter = new C03421();
        this.mOnClickListener = onClickListener;
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
        SessionModel item = (SessionModel) this.mList.get(position);
        ImageLoader.getInstance().displayImage(new StringBuilder(ConstantURL.AVATAR_DOMAIN).append(item.getUid()).append("/").append(item.getAvatartime()).append(Constant.HEAD_BIG_SIZE).toString(), viewholder.iv_avatar, Constant.AVATAR_OPTIONS);
        viewholder.iv_avatar.setOnClickListener(this.mOnClickListener);
        viewholder.iv_avatar.setTag(item);
        if (item.getCount() > 0) {
            viewholder.news_tv.setVisibility(View.VISIBLE);
            viewholder.news_tv.setText(new StringBuilder(String.valueOf(item.getCount())).toString());
        } else {
            viewholder.news_tv.setVisibility(View.INVISIBLE);
        }
        if (item.isBlock()) {
            viewholder.isblock_iv.setVisibility(View.VISIBLE);
        } else {
            viewholder.isblock_iv.setVisibility(View.INVISIBLE);
        }
        viewholder.tv_name.setText(item.getNickname());
        if (item.getUptime() != null) {
            //viewholder.tv_time.setText(TimeUtils.getShowTimeStringefore(new Date(TimeUtils.FromatTimeToMinnisecond(item.getUptime())), "MM-dd yyyy"));
        } else {
            viewholder.tv_time.setVisibility(View.INVISIBLE);
        }
        //viewholder.tv_chat.setText(Html.fromHtml(FaceUtils.getCharSequence(item.getMessage()), this.getter, null));
        return convertView;
    }
}
