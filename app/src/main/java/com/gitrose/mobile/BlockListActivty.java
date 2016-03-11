package com.gitrose.mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.XListView.IXListViewListener;
import com.gitrose.mobile.adapter.BlockListAdapter;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.BlockUserList;
import com.gitrose.mobile.model.UserInfo;
import com.gitrose.mobile.utils.PlaySound;
//import com.umeng.socialize.net.utils.SocializeProtocolConstants;
//import io.rong.message.BuildConfig;
import java.util.ArrayList;
//import org.apache.http.Header;
//import shouji.gexing.framework.utils.UiUtils;

public class BlockListActivty extends BaseActivity implements OnClickListener, IXListViewListener {
    private RelativeLayout container_rl;
    SwipeMenuCreator creator;
    OnClickListener itemClick;
    private int len;
    private BlockListAdapter mBlockAdapter;
    private View mEmptyView;
    private SwipeMenuListView mListView;
    private String mStatus;
    private UnBlockBroadcastReceiver mUnBlockBroadcastReceiver;
    private ArrayList<UserInfo> mUserList;
    private TextView title_tv;

    /* renamed from: com.gitrose.mobile.BlockListActivty.2 */
    class C02522 implements OnClickListener {
        C02522() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tips_list_item_iv_avatar:
//                    BlockListActivty.this.startActivity(new Intent(BlockListActivty.this, PersonalActivity.class));
//                    BlockListActivty.this.animationForNew();
                default:
            }
        }
    }

    private class UnBlockBroadcastReceiver extends BroadcastReceiver {
        private UnBlockBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (Constant.UNBLOCK_TOPIC_ACTION.equals(intent.getAction()) || Constant.UNBLOCK_PRIVATE_ACTION.equals(intent.getAction())) {
                //BlockListActivty.this.deleteBlockUserInfo(intent.getStringExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID));
            } else if (Constant.BLOCK_TOPIC_ACTION.equals(intent.getAction()) || Constant.BLOCK_PRIVATE_ACTION.equals(intent.getAction())) {
                BlockListActivty.this.requestBlockUser();
            }
        }
    }

    /* renamed from: com.gitrose.mobile.BlockListActivty.1 */
    class C05081 implements SwipeMenuCreator {
        C05081() {
        }

        public void create(SwipeMenu menu) {
            SwipeMenuItem openItem = new SwipeMenuItem(BlockListActivty.this.getApplicationContext());
            openItem.setBackground(new ColorDrawable(Color.rgb(MotionEventCompat.ACTION_MASK, 0, 0)));
            //openItem.setWidth(UiUtils.getInstance(BlockListActivty.this).DipToPixels(80.0f));
            openItem.setTitle("\u5220\u9664");
            openItem.setTitleSize(18);
            openItem.setTitleColor(-1);
            menu.addMenuItem(openItem);
        }
    }

    /* renamed from: com.gitrose.mobile.BlockListActivty.3 */
    class C05093 implements OnMenuItemClickListener {
        C05093() {
        }

        public void onMenuItemClick(int position, SwipeMenu menu, int index) {
            //PlaySound.getInstance(BlockListActivty.this).toPlay(R.raw.delete);
            BlockListActivty.this.deleteBolck(position);
        }
    }

    /* renamed from: com.gitrose.mobile.BlockListActivty.4 */
//    class C06324 extends QGHttpHandler<BlockUserList> {
//        private final /* synthetic */ String val$driection;
//
//        C06324(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {
//            this.val$driection = str;
//            super($anonymous0, $anonymous1, $anonymous2);
//        }
//
//        public void onFinish() {
//            super.onFinish();
//            BlockListActivty.this.onRefreshComplete();
//        }
//
//        public void onGetDataSuccess(BlockUserList data) {
//            BlockListActivty.this.responseData(this.val$driection, data);
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            super.onFailure(statusCode, headers, responseString, throwable);
//            if (BlockListActivty.this.mUserList.size() == 0) {
//                BlockListActivty.this.showEmtpyTip(BlockListActivty.this.getResources().getString(R.string.error_network_tip));
//            }
//        }
//    }
//
//    /* renamed from: com.gitrose.mobile.BlockListActivty.5 */
//    class C06335 extends QGHttpHandler<BlockUserList> {
//        private final /* synthetic */ String val$driection;
//
//        C06335(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {
//            this.val$driection = str;
//            super($anonymous0, $anonymous1, $anonymous2);
//        }
//
//        public void onFinish() {
//            super.onFinish();
//            BlockListActivty.this.onRefreshComplete();
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            super.onFailure(statusCode, headers, responseString, throwable);
//            if (BlockListActivty.this.mUserList.size() == 0) {
//                BlockListActivty.this.showEmtpyTip(BlockListActivty.this.getResources().getString(R.string.error_network_refresh_tip));
//            }
//        }
//
//        public void onGetDataSuccess(BlockUserList data) {
//            BlockListActivty.this.responseData(this.val$driection, data);
//        }
//    }
//
//    /* renamed from: com.gitrose.mobile.BlockListActivty.6 */
//    class C06346 extends QGHttpHandler<String> {
//        private final /* synthetic */ int val$pos;
//
//        C06346(Context $anonymous0, int i) {
//            this.val$pos = i;
//            super($anonymous0);
//        }
//
//        public void onGetDataSuccess(String data) {
//            if (BlockListActivty.this.mUserList.size() > this.val$pos) {
//                BlockListActivty.this.mUserList.remove(this.val$pos);
//                BlockListActivty.this.notifyViewUpdate();
//            }
//        }
//    }
//
//    /* renamed from: com.gitrose.mobile.BlockListActivty.7 */
//    class C06357 extends QGHttpHandler<String> {
//        private final /* synthetic */ int val$pos;
//
//        C06357(Context $anonymous0, int i) {
//            this.val$pos = i;
//            super($anonymous0);
//        }
//
//        public void onGetDataSuccess(String data) {
//            if (BlockListActivty.this.mUserList.size() > this.val$pos) {
//                BlockListActivty.this.mUserList.remove(this.val$pos);
//                BlockListActivty.this.notifyViewUpdate();
//            }
//        }
//    }

    public BlockListActivty() {
        this.len = 50;
        this.creator = new C05081();
        this.itemClick = new C02522();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        this.mStatus = getIntent().getStringExtra("status");
        initUI();
//        getData(BuildConfig.FLAVOR, this.len, HomeActivity.UP, true);
    }

    private void initUI() {
        this.container_rl = (RelativeLayout) findViewById(R.id.container_rl);
        this.title_tv = (TextView) findViewById(R.id.title_tv_text);
        this.title_tv.setText("private".equals(this.mStatus) ? "\u5c4f\u853d\u4ed6\uff08\u5979\uff09\u7684\u79c1\u4fe1" : "\u5c4f\u853d\u4ed6\uff08\u5979\uff09\u7684\u5185\u5bb9");
        findViewById(R.id.title_tv_left).setOnClickListener(this);
        this.mListView = (SwipeMenuListView) findViewById(R.id.activity_news_list_lv);
        this.mEmptyView = findViewById(R.id.rl_empty_tip);
        this.mEmptyView.setVisibility(View.INVISIBLE);
        this.mUserList = new ArrayList();
        this.mBlockAdapter = new BlockListAdapter(this, this.mUserList);
        this.mListView.setMenuCreator(this.creator);
        this.mListView.setAdapter(this.mBlockAdapter);
        this.mListView.setOnMenuItemClickListener(new C05093());
        this.mListView.setPullLoadEnable(false);
        this.mListView.setPullRefreshEnable(true);
        this.mListView.setXListViewListener(this);
        registerBroadCast();
    }

    public void onRefresh() {
        String uid = null;
        if (this.mUserList.size() > 0) {
            uid = ((UserInfo) this.mUserList.get(0)).getUid();
        }
        //PlaySound.getInstance(this).toPlay(R.raw.refresh);
//        getData(uid, this.len, HomeActivity.UP, false);
    }

    public void onLoadMore() {
        String uid = null;
        if (this.mUserList.size() > 0) {
            uid = ((UserInfo) this.mUserList.get(this.mUserList.size() - 1)).getUid();
        }
//        getData(uid, this.len, HomeActivity.DOWN, false);
    }

    final void onRefreshComplete() {
        this.mListView.stopRefresh();
        this.mListView.stopLoadMore();
    }

    private void getData(String uid, int len, String driection, boolean isloading) {
        if ("private".equals(this.mStatus)) {
            //QGHttpRequest.getInstance().blockUserPrivateList(this, uid, len, driection, new C06324(this, isloading, this.container_rl, driection));
            return;
        }
        //QGHttpRequest.getInstance().blockUserTopicList(this, uid, len, driection, new C06335(this, isloading, this.container_rl, driection));
    }

    private void responseData(String driection, BlockUserList data) {
//        if (data != null) {
//            if (driection.equals(HomeActivity.UP)) {
//                this.mUserList.addAll(0, data.getList());
//                if (this.mUserList.size() > 0) {
//                    this.mListView.setPullLoadEnable(true);
//                }
//            } else {
//                this.mUserList.addAll(data.getList());
//            }
//        }
//        notifyViewUpdate();
    }

    private void notifyViewUpdate() {
        if (this.mUserList.size() > 0) {
            this.mBlockAdapter.notifyDataSetChanged();
            return;
        }
        int i;
        Resources resources = getResources();
        if ("private".equals(this.mStatus)) {
            i = R.string.emtpy_bolck_private_tip;
        } else {
            i = R.string.empty_topic_tip;
        }
        showEmtpyTip(resources.getString(i));
    }

    private void showEmtpyTip(String tip) {
        this.mListView.setVisibility(View.VISIBLE);
        this.mEmptyView.setVisibility(View.GONE);
        ((TextView) this.mEmptyView.findViewById(R.id.tv_emtpy_tip)).setText(tip);
    }

    private void deleteBolck(int pos) {
        String uid = ((UserInfo) this.mUserList.get(pos)).getUid();
        if ("private".equals(this.mStatus)) {
            //QGHttpRequest.getInstance().unblockMessageRequest(this, uid, new C06346(this, pos));
        } else {
            //QGHttpRequest.getInstance().unblockUserTopic(this, uid, new C06357(this, pos));
        }
    }

    private void registerBroadCast() {
        this.mUnBlockBroadcastReceiver = new UnBlockBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        if ("private".equals(this.mStatus)) {
            filter.addAction(Constant.UNBLOCK_PRIVATE_ACTION);
            filter.addAction(Constant.BLOCK_PRIVATE_ACTION);
        } else {
            filter.addAction(Constant.UNBLOCK_TOPIC_ACTION);
            filter.addAction(Constant.BLOCK_TOPIC_ACTION);
        }
        registerReceiver(this.mUnBlockBroadcastReceiver, filter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv_left:
                finish();
                animationForOld();
//            case R.id.rl_empty_click_tip:
//                getData(BuildConfig.FLAVOR, this.len, HomeActivity.UP, true);
            default:
        }
    }

    protected void onDestroy() {
        unregisterReceiver(this.mUnBlockBroadcastReceiver);
        super.onDestroy();
    }

    private void deleteBlockUserInfo(String uid) {
        for (int i = this.mUserList.size() - 1; i >= 0; i--) {
            if (((UserInfo) this.mUserList.get(i)).getUid().equals(uid)) {
                this.mUserList.remove(i);
                notifyViewUpdate();
            }
        }
    }

    private void requestBlockUser() {
//        if (this.mUserList.size() > 0) {
//            getData(((UserInfo) this.mUserList.get(0)).getUid(), this.len, HomeActivity.UP, true);
//        } else {
//            getData(BuildConfig.FLAVOR, this.len, HomeActivity.UP, true);
//        }
    }
}
