package com.gitrose.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.gitrose.mobile.R;

public class PersonalEmptyAdapter extends BaseAdapter {
    public static final int FENG = 4;
    public static final int OTHER_PUBLISH_EMPTEY = 3;
    public static final int SELF_FAV_EMPTEY = 2;
    public static final int SELF_PUBLISH_EMPTEY = 1;
    public Context mContext;
    public int mType;

    public PersonalEmptyAdapter(Context mcontext, int type) {
        this.mContext = mcontext;
        this.mType = type;
    }

    public int getCount() {
        return SELF_PUBLISH_EMPTEY;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(this.mContext).inflate(R.layout.empty_tip_layout, null);
        TextView empty_tv = (TextView) convertView.findViewById(R.id.tv_emtpy_tip);
        switch (this.mType) {
            case SELF_PUBLISH_EMPTEY /*1*/:
                empty_tv.setText(R.string.empty_topic_tip);
                break;
            case SELF_FAV_EMPTEY /*2*/:
                empty_tv.setText(R.string.empty_fav_tip);
                break;
            case OTHER_PUBLISH_EMPTEY /*3*/:
                empty_tv.setText(R.string.empty_other_topic_tip);
                break;
            case FENG /*4*/:
                empty_tv.setText(R.string.empty_other_topic_tip_feng);
                break;
        }
        return convertView;
    }
}
