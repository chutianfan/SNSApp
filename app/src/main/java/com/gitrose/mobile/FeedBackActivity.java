package com.gitrose.mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
//import com.umeng.fb.ContactActivity;
//import com.umeng.fb.ConversationActivity;
//import com.umeng.fb.FeedbackAgent;
//import com.umeng.fb.model.Conversation;
//import com.umeng.fb.model.Conversation.SyncListener;
//import com.umeng.fb.model.DevReply;
//import com.umeng.fb.model.Reply;
//import com.umeng.fb.model.UserInfo;
//import com.umeng.socialize.net.utils.SocializeProtocolConstants;
//import io.rong.message.BuildConfig;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedBackActivity extends BaseActivity {
//    private static final String TAG;
    private ReplyListAdapter adapter;
//    private FeedbackAgent agent;
//    private Conversation defaultConversation;
    RelativeLayout header;
    int headerHeight;
    int headerPaddingOriginal;
    InputMethodManager imm;
    private int mLastMotionY;
    private ListView replyListView;
    EditText userReplyContentEdit;

    /* renamed from: com.gitrose.mobile.FeedBackActivity.1 */
    class C02681 implements OnClickListener {
        C02681() {
        }

        @SuppressLint({"NewApi"})
        public void onClick(View v) {
            Intent intent = new Intent();
//            intent.setClass(FeedBackActivity.this, ContactActivity.class);
            FeedBackActivity.this.startActivity(intent);
            if (VERSION.SDK_INT > 4) {
                FeedBackActivity.this.overridePendingTransition(R.anim.umeng_fb_slide_in_from_right, R.anim.umeng_fb_slide_out_from_left);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.FeedBackActivity.2 */
    class C02692 implements OnClickListener {
        C02692() {
        }

        public void onClick(View v) {
            FeedBackActivity.this.finish();
        }
    }

    /* renamed from: com.gitrose.mobile.FeedBackActivity.3 */
    class C02703 implements OnFocusChangeListener {
        C02703() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                FeedBackActivity.this.imm.toggleSoftInput(0, 2);
            } else {
                FeedBackActivity.this.imm.hideSoftInputFromWindow(FeedBackActivity.this.userReplyContentEdit.getWindowToken(), 0);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.FeedBackActivity.4 */
    class C02714 implements OnClickListener {
        C02714() {
        }

        public void onClick(View v) {
            String content = FeedBackActivity.this.userReplyContentEdit.getEditableText().toString().trim();
            if (!BuildConfig.FLAVOR.equals(content.trim())) {
                FeedBackActivity.this.userReplyContentEdit.getEditableText().clear();
//                FeedBackActivity.this.defaultConversation.addUserReply(content);
//                FeedBackActivity.this.sync();
//                UserInfo info = FeedBackActivity.this.agent.getUserInfo();
//                if (info == null) {
//                    info = new UserInfo();
//                }
//                Map<String, String> contact = info.getContact();
//                if (contact == null) {
//                    contact = new HashMap();
//                }
//                if (!(MyApplication.getInstance().getUserinfo() == null || MyApplication.getInstance().getUserinfo().getUid() == null)) {
//                    contact.put(SocializeProtocolConstants.PROTOCOL_KEY_UID, MyApplication.getInstance().getUserinfo().getUid());
//                }
//                info.setContact(contact);
//                FeedBackActivity.this.agent.setUserInfo(info);
//                if (FeedBackActivity.this.imm != null) {
//                    FeedBackActivity.this.imm.hideSoftInputFromWindow(FeedBackActivity.this.userReplyContentEdit.getWindowToken(), 0);
//                }
            }
        }
    }

    /* renamed from: com.gitrose.mobile.FeedBackActivity.5 */
    class C02725 implements OnTouchListener {
        C02725() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (FeedBackActivity.this.replyListView.getAdapter().getCount() >= 2) {
                switch (event.getAction()) {
                    case 0:
                        FeedBackActivity.this.mLastMotionY = (int) event.getY();
                        break;
                    case 1:
                        if (FeedBackActivity.this.replyListView.getFirstVisiblePosition() == 0 && (FeedBackActivity.this.header.getBottom() >= FeedBackActivity.this.headerHeight + 20 || FeedBackActivity.this.header.getTop() > 0)) {
                            FeedBackActivity.this.header.setVisibility(View.VISIBLE);
                            FeedBackActivity.this.header.setPadding(FeedBackActivity.this.header.getPaddingLeft(), FeedBackActivity.this.headerPaddingOriginal, FeedBackActivity.this.header.getPaddingRight(), FeedBackActivity.this.header.getPaddingBottom());
                            break;
                        }
                        FeedBackActivity.this.replyListView.setSelection(1);
                        FeedBackActivity.this.header.setVisibility(View.INVISIBLE);
                        FeedBackActivity.this.header.setPadding(FeedBackActivity.this.header.getPaddingLeft(), -FeedBackActivity.this.headerHeight, FeedBackActivity.this.header.getPaddingRight(), FeedBackActivity.this.header.getPaddingBottom());
                        break;
                    case 2:
                        applyHeaderPadding(event);
                        break;
                    default:
                        break;
                }
            }
            return false;
        }

        private void applyHeaderPadding(MotionEvent ev) {
            int pointerCount = ev.getHistorySize();
            for (int p = 0; p < pointerCount; p++) {
                if (FeedBackActivity.this.replyListView.getFirstVisiblePosition() == 0) {
                    int topPadding = (int) (((double) ((((int) ev.getHistoricalY(p)) - FeedBackActivity.this.mLastMotionY) - FeedBackActivity.this.headerHeight)) / 1.7d);
                    FeedBackActivity.this.header.setVisibility(View.VISIBLE);
                    FeedBackActivity.this.header.setPadding(FeedBackActivity.this.header.getPaddingLeft(), topPadding, FeedBackActivity.this.header.getPaddingRight(), FeedBackActivity.this.header.getPaddingBottom());
                }
            }
        }
    }

    /* renamed from: com.gitrose.mobile.FeedBackActivity.6 */
    class C02736 implements OnScrollListener {
        private int mScrollState;

        C02736() {
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (this.mScrollState == 2 && firstVisibleItem == 0) {
                FeedBackActivity.this.replyListView.setSelection(1);
            }
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            this.mScrollState = scrollState;
        }
    }

    class ReplyListAdapter extends BaseAdapter {
        Context mContext;
        LayoutInflater mInflater;

        class ViewHolder {
            TextView replyContent;
            TextView replyDate;

            ViewHolder() {
            }
        }

        public ReplyListAdapter(Context context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(this.mContext);
        }

        public int getCount() {
//            List<Reply> replyList = FeedBackActivity.this.defaultConversation.getReplyList();
//            return replyList == null ? 0 : replyList.size();

            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
//                convertView = this.mInflater.inflate(R.layout.umeng_fb_list_item, null);
//                holder = new ViewHolder();
//                holder.replyDate = (TextView) convertView.findViewById(R.id.umeng_fb_reply_date);
//                holder.replyContent = (TextView) convertView.findViewById(R.id.umeng_fb_reply_content);
//                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//            Reply reply = (Reply) FeedBackActivity.this.defaultConversation.getReplyList().get(position);
//            LayoutParams layoutParams = new LayoutParams(-2, -2);
//            if (reply instanceof DevReply) {
//                layoutParams.addRule(9);
//                holder.replyContent.setLayoutParams(layoutParams);
//                holder.replyContent.setBackgroundResource(R.drawable.umeng_fb_reply_left_bg);
//            } else {
//                layoutParams.addRule(11);
//                holder.replyContent.setLayoutParams(layoutParams);
//                holder.replyContent.setBackgroundResource(R.drawable.umeng_fb_reply_right_bg);
//            }
//            holder.replyDate.setText(SimpleDateFormat.getDateTimeInstance().format(reply.getDatetime()));
//            holder.replyContent.setText(reply.getContent());
            return convertView;
        }

        public Object getItem(int position) {
            //return FeedBackActivity.this.defaultConversation.getReplyList().get(position);
            return  null;
        }

        public long getItemId(int position) {
            return (long) position;
        }
    }

    /* renamed from: com.gitrose.mobile.FeedBackActivity.7 */
//    class C05117 implements SyncListener {
//        C05117() {
//        }
//
//        public void onSendUserReply(List<Reply> list) {
//            FeedBackActivity.this.adapter.notifyDataSetChanged();
//        }
//
//        public void onReceiveDevReply(List<DevReply> list) {
//        }
//    }
//
//    static {
//        TAG = ConversationActivity.class.getName();
//    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.imm = (InputMethodManager) getSystemService(Context.INPUT_SERVICE);
        setContentView(R.layout.umeng_fb_activity_conversation);
        try {
//            this.agent = new FeedbackAgent(this);
//            this.defaultConversation = this.agent.getDefaultConversation();
//            this.replyListView = (ListView) findViewById(R.id.umeng_fb_reply_list);
//            setListViewHeader();
//            this.adapter = new ReplyListAdapter(this);
//            this.replyListView.setAdapter(this.adapter);
//            sync();
//            View contact_entry = findViewById(R.id.umeng_fb_conversation_contact_entry);
//            contact_entry.setOnClickListener(new C02681());
//            if (this.agent.getUserInfoLastUpdateAt() > 0) {
//                contact_entry.setVisibility(8);
//            }
            findViewById(R.id.umeng_fb_back).setOnClickListener(new C02692());
            this.userReplyContentEdit = (EditText) findViewById(R.id.umeng_fb_reply_content);
            this.userReplyContentEdit.setOnFocusChangeListener(new C02703());
            findViewById(R.id.umeng_fb_send).setOnClickListener(new C02714());
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    private void setListViewHeader() {
        this.header = (RelativeLayout) ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.umeng_fb_list_header, this.replyListView, false);
        this.replyListView.addHeaderView(this.header);
        measureView(this.header);
        this.headerHeight = this.header.getMeasuredHeight();
        this.headerPaddingOriginal = this.header.getPaddingTop();
        this.header.setPadding(this.header.getPaddingLeft(), -this.headerHeight, this.header.getPaddingRight(), this.header.getPaddingBottom());
        this.header.setVisibility(View.GONE);
        this.replyListView.setOnTouchListener(new C02725());
        this.replyListView.setOnScrollListener(new C02736());
    }

    private void measureView(View child) {
        int childHeightSpec;
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(-1, -2);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        if (lpHeight > 0) {
            //childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
        } else {
           // childHeightSpec = MeasureSpec.makeMeasureSpec(0, 0);
        }
        //child.measure(childWidthSpec, childHeightSpec);
    }

    void sync() {
//        this.defaultConversation.sync(new C05117());
    }
}
