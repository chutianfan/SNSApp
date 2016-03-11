package com.gitrose.mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
//import com.sea_monster.core.network.AbstractHttpRequest;
//import com.sea_monster.core.network.ApiReqeust;
import com.gitrose.mobile.R;
//import com.gitrose.mobile.PersonalActivity;
//import com.gitrose.mobile.TopicDetailPagerActivity;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.ConstantURL;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.GroupMemberBean;
import java.util.List;
//import org.apache.http.Header;
//import shouji.gexing.framework.utils.DebugUtils;

public class SortGroupMemberAdapter extends BaseAdapter implements SectionIndexer {
    private List<GroupMemberBean> list;
    private Context mContext;
    public int mCurrentPosition;
    private OnClickListener mOnClickListener;

    /* renamed from: com.gitrose.mobile.adapter.SortGroupMemberAdapter.1 */
    class C03451 implements OnClickListener {
        C03451() {
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.head_image:
                    SortGroupMemberAdapter.this.mCurrentPosition = Integer.parseInt(String.valueOf(view.getTag()));
                    if (view.getTag() != null) {
                        GroupMemberBean gmb = (GroupMemberBean) SortGroupMemberAdapter.this.list.get(Integer.parseInt(String.valueOf(view.getTag())));
                        if (gmb.getTutuid() != null) {
                            SortGroupMemberAdapter.this.startPersonalActivity(gmb.getTutuid());
                        }
                    }
                case R.id.relation_button:
                    SortGroupMemberAdapter.this.mCurrentPosition = Integer.parseInt(String.valueOf(view.getTag()));
                    if (view.getTag() != null) {
                        SortGroupMemberAdapter.this.switchButtonDo(view, (GroupMemberBean) SortGroupMemberAdapter.this.list.get(Integer.parseInt(String.valueOf(view.getTag()))));
                    }
                default:
            }
        }
    }

    static final class ViewHolder {
        Button tvButton;
        ImageView tvImage;
        TextView tvLetter;
        TextView tvTitle;

        ViewHolder() {
        }
    }

    /* renamed from: com.gitrose.mobile.adapter.SortGroupMemberAdapter.2 */
//    class C07162 extends QGHttpHandler {
//        private final /* synthetic */ String val$devicesId;
//        private final /* synthetic */ String val$my_tutu_id;
//        private final /* synthetic */ String val$user_id;
//
//        C07162(Context $anonymous0, String str, String str2, String str3) {
//            this.val$devicesId = str;
//            this.val$my_tutu_id = str2;
//            this.val$user_id = str3;
//            super($anonymous0);
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            super.onFailure(statusCode, headers, responseString, throwable);
//        }
//
//        public void onGetDataSuccess(Object data) {
//            Intent intent = new Intent("action.update.contacts");
//            Bundle bundle = new Bundle();
//            bundle.putString("devicesId", this.val$devicesId);
//            bundle.putString("my_tutu_id", this.val$my_tutu_id);
//            bundle.putString("tutuid", this.val$user_id);
//            bundle.putString("relation", "3");
//            intent.putExtras(bundle);
//            SortGroupMemberAdapter.this.mContext.sendBroadcast(intent);
//        }
//    }

    public SortGroupMemberAdapter(Context mContext, List<GroupMemberBean> list) {
        this.list = null;
        this.mOnClickListener = new C03451();
        this.mContext = mContext;
        this.list = list;
    }

    public void updateListView(List<GroupMemberBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return this.list.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        GroupMemberBean mContent = (GroupMemberBean) this.list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(this.mContext).inflate(R.layout.activity_group_member_item, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.tvButton = (Button) view.findViewById(R.id.relation_button);
            viewHolder.tvImage = (ImageView) view.findViewById(R.id.head_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (position == getPositionForSection(getSectionForPosition(position))) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }
        //DebugUtils.error("debug", ((GroupMemberBean) this.list.get(position)).getRelation());
        switch (Integer.parseInt(((GroupMemberBean) this.list.get(position)).getRelation())) {
//            case AbstractHttpRequest.LOW /*-1*/:
//                viewHolder.tvButton.setVisibility(0);
//                viewHolder.tvButton.setText("\u9080\u8bf7");
//                viewHolder.tvButton.setTextColor(this.mContext.getResources().getColor(R.color.action_bar_bg));
//                viewHolder.tvButton.setBackgroundResource(R.drawable.round_border_big);
//                break;
            case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
//            case TopicDetailPagerActivity.FLOW_FIRST_WHAT /*1*/:
//                viewHolder.tvButton.setVisibility(View.VISIBLE);
//                viewHolder.tvButton.setText("\u52a0\u597d\u53cb");
//                viewHolder.tvButton.setTextColor(this.mContext.getResources().getColor(R.color.white));
//                viewHolder.tvButton.setBackgroundResource(R.drawable.round_border_big_green);
//                break;
            case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
//            case ApiReqeust.PUT_METHOD /*3*/:
//                viewHolder.tvButton.setVisibility(0);
//                viewHolder.tvButton.setText("\u5df2\u6dfb\u52a0");
//                viewHolder.tvButton.setTextColor(this.mContext.getResources().getColor(R.color.setting_text_color));
//                viewHolder.tvButton.setBackgroundResource(R.color.white);
//                break;
//            case PersonalActivity.TOPIC_DETAIL_RESULT /*4*/:
//                viewHolder.tvButton.setVisibility(View.INVISIBLE);
//                viewHolder.tvButton.setBackgroundResource(R.drawable.round_border_big_green);
//                break;
            default:
                viewHolder.tvButton.setText("\u9080\u8bf7");
                viewHolder.tvButton.setTextColor(this.mContext.getResources().getColor(R.color.action_bar_bg));
                viewHolder.tvButton.setBackgroundResource(R.drawable.round_border_big);
                break;
        }
        viewHolder.tvImage.setOnClickListener(this.mOnClickListener);
        viewHolder.tvButton.setOnClickListener(this.mOnClickListener);
        viewHolder.tvButton.setTag(Integer.valueOf(position));
        viewHolder.tvImage.setTag(Integer.valueOf(position));
        viewHolder.tvTitle.setText(((GroupMemberBean) this.list.get(position)).getName());
        ImageLoader.getInstance().displayImage(((GroupMemberBean) this.list.get(position)).getHead_img(), viewHolder.tvImage, Constant.AVATAR_OPTIONS);
        return view;
    }

    public int getSectionForPosition(int position) {
        return ((GroupMemberBean) this.list.get(position)).getSortLetters().charAt(0);
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            if (((GroupMemberBean) this.list.get(i)).getSortLetters().toUpperCase().charAt(0) == section) {
                return i;
            }
        }
        return -1;
    }

    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        return sortStr.matches("[A-Z]") ? sortStr : "#";
    }

    public Object[] getSections() {
        return null;
    }

    private void switchButtonDo(View view, GroupMemberBean gmb) {
        switch (Integer.parseInt(gmb.getRelation())) {
//            case AbstractHttpRequest.LOW /*-1*/:
//                sendSMS(gmb.getPhonenumber(), "\u6211\u5728Tutu\uff0c\u6211\u7684Tutu\u53f7\u662f" + MyApplication.getInstance().getUserinfo().getUid() + "\uff0c90\u540e\u300100\u540e\u90fd\u5728\u73a9\uff01\u5feb\u6765\u52a0\u5165\u5427\uff01" + new StringBuilder(ConstantURL.INVITE_URL).append(MyApplication.getInstance().getUserinfo().getUid()).toString());
            case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
//            case TopicDetailPagerActivity.FLOW_FIRST_WHAT /*1*/:
//                addFriends(gmb.getDevicesId(), gmb.getMy_tutu_id(), gmb.getTutuid());
            default:
        }
    }

    private void sendSMS(String number, String smsBody) {
        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + number));
        intent.putExtra("sms_body", smsBody);
        this.mContext.startActivity(intent);
    }

    private void startPersonalActivity(String user_id) {
//        Intent intent = new Intent();
//        intent.setClass(this.mContext, PersonalActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString(PersonalActivity.PERSONAL_USER_ID, user_id);
//        intent.putExtras(bundle);
//        this.mContext.startActivity(intent);
    }

    private void addFriends(String devicesId, String my_tutu_id, String user_id) {
        //QGHttpRequest.getInstance().getAddFriendsRequest(this.mContext, user_id, new C07162(this.mContext, devicesId, my_tutu_id, user_id));
    }

    private void updateRelation(int position, String ex_relation, boolean isFriends) {
    }
}
