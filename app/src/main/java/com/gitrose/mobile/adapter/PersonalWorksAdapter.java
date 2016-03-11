package com.gitrose.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.mobile.R;
import com.gitrose.mobile.constant.Constant;
import java.util.List;
import qalsdk.BaseConstants;

public class PersonalWorksAdapter extends BaseAdapter {
    private Context mContext;
    private int mGroupPos;
    private OnClickListener mOncliClickListener;
    private List<TopicInfo> mPicList;

    private static class ViewHolder {
        ImageView work_pic;
        RelativeLayout work_pic_rl;
        ImageView works_pic_camera;
        ImageView works_pic_check;
        ImageView works_pic_redpacket;

        private ViewHolder() {
        }
    }

    public PersonalWorksAdapter(Context context, List<TopicInfo> works) {
        this.mContext = context;
        this.mPicList = works;
    }

    public void setOnClick(int group_pos, OnClickListener onClickListener) {
        this.mGroupPos = group_pos;
        this.mOncliClickListener = onClickListener;
    }

    public int getCount() {
        return this.mPicList.size();
    }

    public Object getItem(int position) {
        return this.mPicList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.activity_works_item, null);
            viewholder.work_pic = (ImageView) convertView.findViewById(R.id.works_pic);
            viewholder.works_pic_check = (ImageView) convertView.findViewById(R.id.works_pic_check);
            viewholder.works_pic_redpacket = (ImageView) convertView.findViewById(R.id.works_pic_redpacket);
            viewholder.work_pic_rl = (RelativeLayout) convertView.findViewById(R.id.works_pic_rl);
            viewholder.works_pic_camera = (ImageView) convertView.findViewById(R.id.works_pic_camera);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        if (((TopicInfo) this.mPicList.get(position)).isCamera()) {
            viewholder.work_pic_rl.setVisibility(View.GONE);
            viewholder.works_pic_camera.setVisibility(View.VISIBLE);
            viewholder.works_pic_camera.setOnClickListener(this.mOncliClickListener);
        }
        if (((TopicInfo) this.mPicList.get(position)).getType() != null) {
            if (((TopicInfo) this.mPicList.get(position)).getType().intValue() == 5) {
                viewholder.works_pic_check.setVisibility(View.VISIBLE);
            } else {
                viewholder.works_pic_check.setVisibility(View.GONE);
            }
        }
        if (((TopicInfo) this.mPicList.get(position)).getIsredpacket() == null || !Constant.SYSTEM_UID.equals(((TopicInfo) this.mPicList.get(position)).getIsredpacket())) {
            viewholder.works_pic_redpacket.setVisibility(View.GONE);
        } else {
            viewholder.works_pic_redpacket.setVisibility(View.VISIBLE);
        }
        String content_url = ((TopicInfo) this.mPicList.get(position)).getSmallcontent();
        if (content_url == null || content_url.equals(BaseConstants.ah)) {
            content_url = ((TopicInfo) this.mPicList.get(position)).getContent();
        }
        ImageLoader.getInstance().displayImage(content_url, viewholder.work_pic, Constant.SMALL_PICTURE_OPTIONS_WHITE);
        convertView.setTag(R.id.personal_9_group_pos, Integer.valueOf(this.mGroupPos));
        convertView.setTag(R.id.personal_9_child_pos, Integer.valueOf(position));
        convertView.setOnClickListener(this.mOncliClickListener);
        return convertView;
    }
}
