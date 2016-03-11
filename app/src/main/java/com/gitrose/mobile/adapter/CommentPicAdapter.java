package com.gitrose.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.gitrose.mobile.R;
import com.gitrose.mobile.http.ConstantURL;

public class CommentPicAdapter extends ArrayListAdapter<String> {

    public class ViewHolder {
        ImageView iv_comment_pic;
    }

    public CommentPicAdapter(Context context) {
        super(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(this.mContext, R.layout.comment_pic_bg_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_comment_pic = (ImageView) convertView.findViewById(R.id.iv_comment_pic);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(new StringBuilder(ConstantURL.DOWNLOAD_STICKER_URL).append((String) this.mList.get(position)).append(".png").toString(), viewHolder.iv_comment_pic);
        return convertView;
    }
}
