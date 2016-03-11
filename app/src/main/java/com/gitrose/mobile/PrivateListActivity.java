package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.view.MotionEventCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.gitrose.mobile.adapter.PrivateListAdapter;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.SessionList;
import com.gitrose.mobile.model.SessionModel;
import com.gitrose.mobile.utils.PlaySound;
import java.util.ArrayList;
//import org.apache.http.Header;
//import shouji.gexing.framework.utils.UiUtils;

public class PrivateListActivity extends BaseActivity implements OnClickListener {
    private static PrivateListActivity privateListActivity;
    private PrivateListAdapter adapter;
    private RelativeLayout container_rl;
    SwipeMenuCreator creator;
    OnClickListener itemClick;
    private int len;
    private SwipeMenuListView listview;
    private View mEmptyView;
    private int page;
    private ArrayList<SessionModel> session_list;
    private TextView title_tv;

    /* renamed from: com.gitrose.mobile.PrivateListActivity.2 */
    class C03022 implements OnClickListener {
        C03022() {
        }

        public void onClick(View v) {
            SessionModel sessionModel = (SessionModel) v.getTag();
            Intent intent;
            switch (v.getId()) {
                case R.id.private_list_item_ll:
                    intent = new Intent(PrivateListActivity.this, PrivateChatActivity.class);
                    intent.putExtra("touid", sessionModel.getUid());
                    intent.putExtra("nickname", sessionModel.getNickname());
                    PrivateListActivity.this.startActivityForNew(intent);
                case R.id.private_list_item_iv_avatar:
//                    intent = new Intent(PrivateListActivity.this, PersonalActivity.class);
//                    intent.putExtra(PersonalActivity.PERSONAL_USER_ID, sessionModel.getUid());
//                    PrivateListActivity.this.startActivityForNew(intent);
                default:
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PrivateListActivity.4 */
    class C03034 implements OnItemClickListener {
        C03034() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
            int position;
            boolean z = false;
            if (pos - 1 > 0) {
                position = pos - 1;
            } else {
                position = 0;
            }
            if (PrivateListActivity.this.session_list != null && PrivateListActivity.this.session_list.size() > 0) {
                if (((SessionModel) PrivateListActivity.this.session_list.get(position)).getCount() > 0) {
                    ((SessionModel) PrivateListActivity.this.session_list.get(position)).setCount(0);
                }
//                Intent intent = new Intent(PrivateListActivity.this, RCChatActivity.class);
//                intent.putExtra("targetid", ((SessionModel) PrivateListActivity.this.session_list.get(position)).getUid());
//                String str = "needtoclear";
//                if (((SessionModel) PrivateListActivity.this.session_list.get(position)).getCount() > 0) {
//                    z = true;
//                }
//                intent.putExtra(str, z);
//                intent.putExtra("targetname", ((SessionModel) PrivateListActivity.this.session_list.get(position)).getNickname());
//                PrivateListActivity.this.startActivityForNew(intent);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PrivateListActivity.1 */
    class C05221 implements SwipeMenuCreator {
        C05221() {
        }

        public void create(SwipeMenu menu) {
            SwipeMenuItem openItem = new SwipeMenuItem(PrivateListActivity.this.getApplicationContext());
            openItem.setBackground(new ColorDrawable(Color.rgb(MotionEventCompat.ACTION_MASK, 0, 0)));
//            openItem.setWidth(UiUtils.getInstance(PrivateListActivity.this).DipToPixels(80.0f));
            openItem.setTitle("\u5220\u9664");
            openItem.setTitleSize(18);
            openItem.setTitleColor(-1);
            menu.addMenuItem(openItem);
        }
    }

    /* renamed from: com.gitrose.mobile.PrivateListActivity.3 */
    class C05233 implements OnMenuItemClickListener {
        C05233() {
        }

        public void onMenuItemClick(int position, SwipeMenu menu, int index) {
            switch (index) {
//                case BaseUploadFragmentActivity.PHOTOHRAPH /*0*/:
//                    PlaySound.getInstance(PrivateListActivity.this).toPlay(R.raw.delete);
//                    PrivateListActivity.this.deleteSession(position, ((SessionModel) PrivateListActivity.this.session_list.get(position)).getUid());
                default:
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PrivateListActivity.5 */
    class C06735 extends QGHttpHandler<SessionList> {
        C06735(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(SessionList data) {
            PrivateListActivity.this.session_list.addAll(data.getSessionlist());
            PrivateListActivity.this.notifyViewUpdate();
        }

        public void onFinish() {
            super.onFinish();
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            if (PrivateListActivity.this.session_list.size() == 0) {
                PrivateListActivity.this.showEmtpyTip(PrivateListActivity.this.getResources().getString(R.string.error_network_tip));
            }
        }
    }

    /* renamed from: com.gitrose.mobile.PrivateListActivity.6 */
//    class C06746 extends QGHttpHandler<String> {
//        private final /* synthetic */ int val$pos;
//
//        C06746(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, int i) {
//            this.val$pos = i;
//            super($anonymous0, $anonymous1, $anonymous2);
//        }
//
//        public void onGetDataSuccess(String data) {
//            int private_num = MyApplication.getInstance().getPrivateNum();
//            if (((SessionModel) PrivateListActivity.this.session_list.get(this.val$pos)).getCount() > 0 && private_num >= ((SessionModel) PrivateListActivity.this.session_list.get(this.val$pos)).getCount()) {
//                MyApplication.getInstance().setPrivateNum(private_num - ((SessionModel) PrivateListActivity.this.session_list.get(this.val$pos)).getCount());
//            }
//            PrivateListActivity.this.session_list.remove(this.val$pos);
//            PrivateListActivity.this.notifyViewUpdate();
//        }
//    }

    public PrivateListActivity() {
        this.page = 0;
        this.len = 100;
        this.creator = new C05221();
        this.itemClick = new C03022();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_list);
        initUI();
        privateListActivity = this;
        getData(this.page, this.len, true);
    }

    protected void onResume() {
        super.onResume();
        if (this.session_list != null) {
            this.adapter.setList(this.session_list);
        }
    }

    public static PrivateListActivity getObject() {
        return privateListActivity;
    }

    public void torefresh() {
        getData(this.page, this.len, false);
    }

    private void initUI() {
        this.container_rl = (RelativeLayout) findViewById(R.id.container_rl);
        this.title_tv = (TextView) findViewById(R.id.title_tv_text);
        this.title_tv.setText("\u79c1\u4fe1");
        findViewById(R.id.title_tv_left).setOnClickListener(this);
        this.listview = (SwipeMenuListView) findViewById(R.id.activity_private_list_lv);
        this.mEmptyView = findViewById(R.id.rl_empty_tip);
//        this.mEmptyView.setVisibility(4);
        this.adapter = new PrivateListAdapter(this, this.itemClick);
        this.session_list = new ArrayList();
        this.listview.setMenuCreator(this.creator);
        this.listview.setAdapter(this.adapter);
        this.listview.setPullLoadEnable(false);
        this.listview.setPullRefreshEnable(false);
        this.listview.setOnMenuItemClickListener(new C05233());
        this.listview.setOnItemClickListener(new C03034());
    }

    private void getData(int page, int len, boolean isloading) {
        QGHttpRequest.getInstance().getSessionListRequest(this, page, len, new C06735(this, isloading, this.container_rl));
    }

    private void notifyViewUpdate() {
        if (this.session_list.size() > 0) {
            this.adapter.setList(this.session_list);
        } else {
            showEmtpyTip(getResources().getString(R.string.empty_private_tip));
        }
    }

    private void showEmtpyTip(String tip) {
//        this.listview.setVisibility(4);
//        this.mEmptyView.setVisibility(0);
        ((TextView) this.mEmptyView.findViewById(R.id.tv_emtpy_tip)).setText(tip);
    }

    private void deleteSession(int pos, String to_uid) {
//        QGHttpRequest.getInstance().deleteSessionRequest(this, to_uid, new C06746(this, true, this.container_rl, pos));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                finish();
            default:
        }
    }
}
