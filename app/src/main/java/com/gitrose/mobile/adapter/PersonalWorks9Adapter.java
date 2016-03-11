package com.gitrose.mobile.adapter;

import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.gitrose.greendao.TopicInfo;
import com.gitrose.mobile.PersonalWorksListActiviy;
import com.gitrose.mobile.R;
import com.gitrose.mobile.model.TopicInfoLable;
import com.gitrose.mobile.view.ScrollGridView;
import java.util.ArrayList;
import java.util.List;

public class PersonalWorks9Adapter extends HomeFollowAdapter {
    public static final int FENG = 4;
    public static final int OTHER_PUBLISH_EMPTEY = 3;
    public static final int SELF_FAV_EMPTEY = 2;
    public static final int SELF_PUBLISH_EMPTEY = 1;
    private Handler handler;
    public boolean isEmpty;
    public boolean isInitHomeAdapter;
    private ArrayList<TopicInfoLable> lable_list;
    private ArrayList<TopicInfo> mListTopicInfo;
    private Context mContext;
    private OnClickListener mOncliClickListener;
    public int mType;
    public boolean notify_home_adapter;

    private static class ViewHolder9 {
        ScrollGridView gridview;
        ImageView title_divder_iv;
        TextView title_name;

        private ViewHolder9() {
        }
    }

    public PersonalWorks9Adapter(Context context) {

        super(context);
        this.isInitHomeAdapter = false;
        this.notify_home_adapter = false;
        this.isEmpty = false;
        this.handler = new Handler();
        this.mContext = context;
    }

    public void initHomeAdapter(Context context, ListView listView, List<TopicInfo> picList, boolean fromPersonal) {
        initHomeTopicAdapter(context, listView, picList, fromPersonal);
        this.isInitHomeAdapter = true;
    }

    public void setList(ArrayList<TopicInfoLable> lable_list, int type) {
        this.lable_list = lable_list;
        this.notify_home_adapter = false;
        this.isEmpty = false;
        if ((this.lable_list == null || this.lable_list.size() == 0) && type != SELF_PUBLISH_EMPTEY) {
            setEmptyType(type);
        }
        ((PersonalWorksListActiviy) this.mContext).pauseDetail();
        notifyDataSetChanged();
    }

    public void setTopicList(ArrayList<TopicInfo> listTopicInfo, int type)
    {
        mListTopicInfo = listTopicInfo;
        this.notify_home_adapter = false;
        this.isEmpty = false;
        if ((this.lable_list == null || this.lable_list.size() == 0) && type != SELF_PUBLISH_EMPTEY) {
            setEmptyType(type);
        }

        notifyDataSetChanged();
    }

    public void setData(List<TopicInfo> picList, int type) {
        this.notify_home_adapter = true;
        this.isEmpty = false;
        if ((picList == null || picList.size() == 0) && type != SELF_PUBLISH_EMPTEY) {
            setEmptyType(type);
        }
        ((PersonalWorksListActiviy) this.mContext).mCurrentPosition = -1;
        ((PersonalWorksListActiviy) this.mContext).mFlowHelper.resetCurrentPosition(-1);
        ((PersonalWorksListActiviy) this.mContext).pauseDetail();
        super.setData(picList);
        ((PersonalWorksListActiviy) this.mContext).resumeVideoAndCommentFlow();
    }

    public void setEmptyType(int type) {
        this.mType = type;
        this.isEmpty = true;
    }

    public void setOnClick(OnClickListener onClickListener) {
        this.mOncliClickListener = onClickListener;
    }

    public int getCount() {
        if (this.isEmpty) {
            return SELF_PUBLISH_EMPTEY;
        }
        if (this.notify_home_adapter) {
            if (this.mTopicList != null) {
                return this.mTopicList.size();
            }
            return 0;
        } else if (this.mListTopicInfo != null) {
            return this.mListTopicInfo.size();
        } else {
            return 0;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.isEmpty) {
            return getEmptyView(position, convertView, parent);
        }
        if (this.notify_home_adapter) {
            return super.getView(position, convertView, parent);
        }

        ViewHolder9 viewHolder;
        if (convertView == null || convertView.findViewById(R.id.frgment_9_item_title_tv) == null) {
            viewHolder = new ViewHolder9();
            convertView = View.inflate(this.mContext, R.layout.fragment_personal_works_9_item, null);
            viewHolder.title_name = (TextView) convertView.findViewById(R.id.frgment_9_item_title_tv);
            viewHolder.title_divder_iv = (ImageView) convertView.findViewById(R.id.title_divider_line_iv);
            viewHolder.gridview = (ScrollGridView) convertView.findViewById(R.id.personal_expand_child_gridview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder9) convertView.getTag();
        }
        if (position == 0) {
            viewHolder.title_divder_iv.setVisibility(View.VISIBLE);
        } else {
            viewHolder.title_divder_iv.setVisibility(View.GONE);
        }
        String name = ((TopicInfo) this.mListTopicInfo.get(position)).getAddtime();
        if (name.length() <= SELF_FAV_EMPTEY || name.length() >= 5) {
            viewHolder.title_name.setText(name);
        } else {
            viewHolder.title_name.setText(Html.fromHtml(name.substring(0, SELF_FAV_EMPTEY) + "<small>" + name.substring(SELF_FAV_EMPTEY) + "</small>"));
        }
        PersonalWorksAdapter mPersonalWorksAdapter = new PersonalWorksAdapter(this.mContext, this.mListTopicInfo);
        viewHolder.gridview.setAdapter(mPersonalWorksAdapter);
        mPersonalWorksAdapter.setOnClick(position, this.mOncliClickListener);
        convertView.setClickable(true);
        return convertView;
    }

    private View getEmptyView(int position, View convertView, ViewGroup parent) {
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
