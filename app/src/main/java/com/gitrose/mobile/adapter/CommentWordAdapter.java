package com.gitrose.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.gitrose.mobile.R;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.model.CommentEmotion;

public class CommentWordAdapter extends ArrayListAdapter<CommentEmotion> {
    private int selectedPosition;

    public class ViewHolder {
        RelativeLayout iv_comment_rl;
        ImageView iv_comment_title;
    }

    public CommentWordAdapter(Context context) {
        super(context);
        this.selectedPosition = 0;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(this.mContext, R.layout.comment_pic_title_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_comment_title = (ImageView) convertView.findViewById(R.id.iv_comment_title);
            viewHolder.iv_comment_rl = (RelativeLayout) convertView.findViewById(R.id.iv_comment_rl);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(new StringBuilder(ConstantURL.DOWNLOAD_STICKER_URL).append(((CommentEmotion) this.mList.get(position)).getTypepic()).append(".png").toString(), viewHolder.iv_comment_title);
        if (this.selectedPosition == position) {
            viewHolder.iv_comment_rl.setSelected(true);
            viewHolder.iv_comment_rl.setPressed(true);
        } else {
            viewHolder.iv_comment_rl.setSelected(false);
            viewHolder.iv_comment_rl.setPressed(false);
        }
        return convertView;
    }
}
