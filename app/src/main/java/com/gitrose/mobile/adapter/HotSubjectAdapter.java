package com.gitrose.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.gitrose.mobile.R;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.model.SubjectInfo;
import java.util.List;
import qalsdk.BaseConstants;

public class HotSubjectAdapter extends BaseAdapter {
    private boolean isSquareHuati;
    private Context mContext;
    private List<SubjectInfo> mSubjectsList;
    private ViewHolder viewholder;

    private static class ViewHolder {
        TextView subject_count;
        ImageView subject_image;
        TextView subject_name;
        TextView subject_text;
        ImageView top_line;
        TextView tvLetter;

        private ViewHolder() {
        }
    }

    public HotSubjectAdapter(Context context) {
        this.mContext = context;
    }

    public HotSubjectAdapter(Context mContext, List<SubjectInfo> mSubjectsList) {
        this.mContext = mContext;
        this.mSubjectsList = mSubjectsList;
    }

    public HotSubjectAdapter(Context context, boolean isSquareHuati, List<SubjectInfo> subjectsList) {
        this.mContext = context;
        this.isSquareHuati = isSquareHuati;
        this.mSubjectsList = subjectsList;
    }

    public void setUsersList(List<SubjectInfo> subjectsList) {
        this.mSubjectsList = subjectsList;
        notifyDataSetChanged();
    }

    public int getCount() {
        if (this.mSubjectsList == null) {
            return 0;
        }
        return this.mSubjectsList.size();
    }

    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    public int getItemViewType(int position) {
        return position;
    }

    public Object getItem(int position) {
        return this.mSubjectsList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        this.viewholder = null;
        SubjectInfo subject = (SubjectInfo) this.mSubjectsList.get(position);
        if (convertView == null) {
            this.viewholder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.activity_hotsubject_item, null);
            this.viewholder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
            this.viewholder.subject_image = (ImageView) convertView.findViewById(R.id.subject_image);
            this.viewholder.subject_name = (TextView) convertView.findViewById(R.id.subject_name);
            this.viewholder.subject_count = (TextView) convertView.findViewById(R.id.subject_count);
            this.viewholder.subject_text = (TextView) convertView.findViewById(R.id.subject_text);
            this.viewholder.top_line = (ImageView) convertView.findViewById(R.id.top_line);
            convertView.setTag(this.viewholder);
        } else {
            this.viewholder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(subject.getPicurl(), this.viewholder.subject_image, Constant.AVATAR_OPTIONS);
        if (subject.getViewcount() == null || subject.getViewcount().trim().equals(BaseConstants.ah)) {
            this.viewholder.subject_count.setVisibility(View.GONE);
        } else {
            this.viewholder.subject_count.setText(this.mContext.getResources().getString(R.string.subject_users_count, new Object[]{subject.getJoinusercount()}));
        }
        this.viewholder.subject_name.setText("#" + subject.getHttext());
        this.viewholder.subject_text.setText(subject.getDesc());
        if (this.isSquareHuati) {
            this.viewholder.tvLetter.setVisibility(View.GONE);
        } else if (position == 0) {
            this.viewholder.tvLetter.setVisibility(View.VISIBLE);
            this.viewholder.tvLetter.setText("\u70ed\u95e8\u8bdd\u9898");
            this.viewholder.top_line.setVisibility(View.GONE);
        } else {
            this.viewholder.tvLetter.setVisibility(View.GONE);
            this.viewholder.top_line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public int getSectionForPosition(int position) {
        return ((SubjectInfo) this.mSubjectsList.get(position)).getSortLetters().charAt(0);
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            if (((SubjectInfo) this.mSubjectsList.get(i)).getSortLetters().toUpperCase().charAt(0) == section) {
                return i;
            }
        }
        return -1;
    }
}
