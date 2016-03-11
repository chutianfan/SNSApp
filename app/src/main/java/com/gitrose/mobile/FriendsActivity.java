package com.gitrose.mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
//import com.baidu.location.C0051C;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.XListView.IXListViewListener;
//import com.sea_monster.core.network.ApiReqeust;
import com.gitrose.mobile.adapter.FriendsAdapter1;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.base.BaseUploadFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.FriendsList;
import com.gitrose.mobile.model.UserInfo;
import com.gitrose.mobile.utils.ContactsUtils;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.view.BaseDialog;
//import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.ArrayList;
import java.util.List;
//import org.apache.http.Header;

public class FriendsActivity extends BaseActivity implements OnTouchListener, OnClickListener, IXListViewListener {
    public static final String GET_FRIENDS_WITH = "user_id";
    private static final String LEN = "50";
    public static final int TAG_RELATION_FANS = 0;
    public static final int TAG_RELATION_FOLLOWS = 1;
    private static FriendsActivity friendsActivity;
    private RelativeLayout container_rl;
    private boolean isSelf;
    private int mCurrentPosition;
    private int mDelFriendCount;
    private View mDelView;
    private BaseDialog mDialog;
    private View mEmptyView;
    private FriendsAdapter1 mFriendsAdapter;
    private List<UserInfo> mFriendsList;
    private OnItemClickListener mFriendsListItemClickListener;
    private SwipeMenuListView mFriendsListView;
    private GestureDetector mGestureDetector;
    private RemarkBroadcastReceiver mRemarkBroadcastReceiver;
    private String mStartuid;
    private String mWithUid;
    SwipeMenuCreator menuCreator;
    private OnMenuItemClickListener menuItemClickListener;
    private boolean need_to_refresh;

    /* renamed from: com.gitrose.mobile.FriendsActivity.2 */
    class C02742 implements OnItemClickListener {
        C02742() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
            int position = pos + -1 > 0 ? pos - 1 : FriendsActivity.TAG_RELATION_FANS;
            String mytutu = null;
            String uid = ((UserInfo) FriendsActivity.this.mFriendsList.get(position)).getUid();
            if (!(MyApplication.getInstance().getUserinfo() == null || MyApplication.getInstance().getUserinfo().getUid() == null)) {
                mytutu = MyApplication.getInstance().getUserinfo().getUid();
            }
            if (!FriendsActivity.this.isSelf) {
                FriendsActivity.this.startPersonalActivity(uid);
            } else if (mytutu.equals(uid)) {
                FriendsActivity.this.startPersonalActivity(uid);
            } else {
                FriendsActivity.this.startPrivateChatActivity(uid, ((UserInfo) FriendsActivity.this.mFriendsList.get(position)).getNickname(), ((UserInfo) FriendsActivity.this.mFriendsList.get(position)).getAddtime());
            }
        }
    }

    class GestureListener extends SimpleOnGestureListener {
        GestureListener() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            FriendsActivity.this.mFriendsListView.smoothScrollToPosition(FriendsActivity.TAG_RELATION_FANS);
            return true;
        }

        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    class RemarkBroadcastReceiver extends BroadcastReceiver {
        RemarkBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
//            if (Constant.MODIFY_MARKNAME_ACTION.equals(intent.getAction())) {
//                FriendsActivity.this.modifyMarkName(intent.getStringExtra(SocializeProtocolConstants.PROTOCOL_KEY_UID), intent.getStringExtra("remark"));
//            }
        }
    }

    /* renamed from: com.gitrose.mobile.FriendsActivity.1 */
    class C05121 implements OnMenuItemClickListener {
        C05121() {
        }

        public void onMenuItemClick(int position, SwipeMenu menu, int index) {
            FriendsActivity.this.mCurrentPosition = position;
            UserInfo user = (UserInfo) FriendsActivity.this.mFriendsList.get(position);
            switch (index) {
                case FriendsActivity.TAG_RELATION_FANS /*0*/:
                    if (user.getIsblock() == FriendsActivity.TAG_RELATION_FOLLOWS) {
                        FriendsActivity.this.unblockMessage(((UserInfo) FriendsActivity.this.mFriendsList.get(position)).getUid());
                    } else if (user.getIsblock() == 0) {
                        FriendsActivity.this.blockMessage(((UserInfo) FriendsActivity.this.mFriendsList.get(position)).getUid());
                    }
                case FriendsActivity.TAG_RELATION_FOLLOWS /*1*/:
//                    PlaySound.getInstance(FriendsActivity.this).toPlay(R.raw.delete);
                    if ("0".equals(user.getRelation()) || Constant.SYSTEM_UID.equals(user.getRelation())) {
                        FriendsActivity.this.addOrDeleteFriends(user.getUid(), false);
                    } else {
                        FriendsActivity.this.mDialog.show(FriendsActivity.this.mDelView);
                    }
                default:
            }
        }
    }

    /* renamed from: com.gitrose.mobile.FriendsActivity.3 */
    class C05133 implements SwipeMenuCreator {
        C05133() {
        }

        public void create(SwipeMenu menu) {
            UserInfo user = (UserInfo) FriendsActivity.this.mFriendsList.get(menu.getViewType());
            if (user.getIsblock() == FriendsActivity.TAG_RELATION_FOLLOWS) {
                FriendsActivity.this.createSwipeMenu(menu, Integer.parseInt(user.getRelation()), true);
            } else {
                FriendsActivity.this.createSwipeMenu(menu, Integer.parseInt(user.getRelation()), false);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.FriendsActivity.4 */
    class C06424 extends FriendsAdapter1 {
        C06424(Context $anonymous0, List $anonymous1, SwipeMenuListView $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void createMenu(SwipeMenu menu) {
            FriendsActivity.this.menuCreator.create(menu);
        }
    }

    /* renamed from: com.gitrose.mobile.FriendsActivity.5 */
//    class C06435 extends QGHttpHandler<FriendsList> {
//        private final /* synthetic */ String val$direction;
//
//        C06435(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {
//            this.val$direction = str;
//            super($anonymous0, $anonymous1, $anonymous2);
//        }
//
//        public void onFinish() {
//            FriendsActivity.this.onRefreshComplete();
//            if (FriendsActivity.this.mFriendsList.size() > 0) {
//                FriendsActivity.this.mStartuid = ((UserInfo) FriendsActivity.this.mFriendsList.get(FriendsActivity.TAG_RELATION_FANS)).getUid();
//            }
//            super.onFinish();
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            super.onFailure(statusCode, headers, responseString, throwable);
//            if (FriendsActivity.this.mFriendsList.size() == 0) {
//                FriendsActivity.this.showEmtpyTip(FriendsActivity.this.getResources().getString(R.string.error_network_refresh_tip));
//            }
//        }
//
//        public void onGetDataSuccess(FriendsList data) {
//            if (data != null && data.getList().size() > 0) {
//                if (FriendsActivity.this.mDelFriendCount != 0 && FriendsActivity.this.isSelf) {
//                    FriendsActivity.this.mFriendsList.clear();
//                }
//                FriendsActivity.this.mDelFriendCount = FriendsActivity.TAG_RELATION_FANS;
//                if (this.val$direction.equals(HomeActivity.UP)) {
//                    FriendsActivity.this.mFriendsList.addAll(FriendsActivity.TAG_RELATION_FANS, data.getList());
//                } else {
//                    FriendsActivity.this.mFriendsList.addAll(data.getList());
//                }
//                FriendsActivity.this.notifyViewUpdate();
//            }
//        }
//    }

    /* renamed from: com.gitrose.mobile.FriendsActivity.6 */
//    class C06446 extends QGHttpHandler {
//        private final /* synthetic */ String val$user_id;
//
//        C06446(Context $anonymous0, String str) {
//            this.val$user_id = str;
//            super($anonymous0);
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            super.onFailure(statusCode, headers, responseString, throwable);
//        }
//
//        public void onGetDataSuccess(Object data) {
//            FriendsActivity.this.updateRelation(FriendsActivity.this.mCurrentPosition, ((UserInfo) FriendsActivity.this.mFriendsList.get(FriendsActivity.this.mCurrentPosition)).getRelation(), true);
//            FriendsActivity friendsActivity = FriendsActivity.this;
//            friendsActivity.mDelFriendCount = friendsActivity.mDelFriendCount - 1;
//            Intent intent = new Intent("action.update.contacts");
//            Bundle bundle = new Bundle();
//            bundle.putString("devicesId", ContactsUtils.getUDID(FriendsActivity.this));
//            bundle.putString("my_tutu_id", MyApplication.getInstance().getUserinfo().getUid());
//            bundle.putString("tutuid", this.val$user_id);
//            bundle.putString("relation", "3");
//            intent.putExtras(bundle);
//            FriendsActivity.friendsActivity.sendBroadcast(intent);
//        }
//    }

    /* renamed from: com.gitrose.mobile.FriendsActivity.7 */
//    class C06457 extends QGHttpHandler {
//        private final /* synthetic */ String val$user_id;
//
//        C06457(Context $anonymous0, String str) {
//            this.val$user_id = str;
//            super($anonymous0);
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            super.onFailure(statusCode, headers, responseString, throwable);
//        }
//
//        public void onGetDataSuccess(Object data) {
//            FriendsActivity.this.updateRelation(FriendsActivity.this.mCurrentPosition, ((UserInfo) FriendsActivity.this.mFriendsList.get(FriendsActivity.this.mCurrentPosition)).getRelation(), false);
//            FriendsActivity friendsActivity = FriendsActivity.this;
//            friendsActivity.mDelFriendCount = friendsActivity.mDelFriendCount + FriendsActivity.TAG_RELATION_FOLLOWS;
//            Intent intent = new Intent("action.update.contacts");
//            Bundle bundle = new Bundle();
//            bundle.putString("devicesId", ContactsUtils.getUDID(FriendsActivity.this));
//            bundle.putString("my_tutu_id", MyApplication.getInstance().getUserinfo().getUid());
//            bundle.putString("tutuid", this.val$user_id);
//            bundle.putString("relation", Constant.SYSTEM_UID);
//            intent.putExtras(bundle);
//            FriendsActivity.friendsActivity.sendBroadcast(intent);
//        }
//    }

    /* renamed from: com.gitrose.mobile.FriendsActivity.8 */
    class C06468 extends QGHttpHandler<String> {
        C06468(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(String data) {
            FriendsActivity.this.updateIsBlock(FriendsActivity.this.mCurrentPosition, FriendsActivity.TAG_RELATION_FOLLOWS);
        }
    }

    /* renamed from: com.gitrose.mobile.FriendsActivity.9 */
    class C06479 extends QGHttpHandler<String> {
        C06479(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onGetDataSuccess(String data) {
            FriendsActivity.this.updateIsBlock(FriendsActivity.this.mCurrentPosition, FriendsActivity.TAG_RELATION_FANS);
        }
    }

    public FriendsActivity() {
        this.need_to_refresh = false;
        this.menuItemClickListener = new C05121();
        this.mFriendsListItemClickListener = new C02742();
        this.menuCreator = new C05133();
    }

    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_friends);
//        Intent intent = getIntent();
//        this.mWithUid = intent.getExtras().getString(GET_FRIENDS_WITH);
//        this.isSelf = intent.getExtras().getBoolean("isSelf");
//        initView();
//        getRelationusers(this.mWithUid, this.mStartuid, LEN, HomeActivity.UP, true);
//        friendsActivity = this;
    }

    protected void onResume() {
        super.onResume();
//        if (this.need_to_refresh) {
//            this.need_to_refresh = false;
//            if (this.mWithUid.equals(MyApplication.getInstance().getUserinfo().getUid())) {
//                this.mDelFriendCount += TAG_RELATION_FOLLOWS;
//                getRelationusers(this.mWithUid, null, LEN, HomeActivity.UP, true);
//            }
//        }
    }

    public static FriendsActivity getObject() {
        return friendsActivity;
    }

    public void needToRefresh() {
        this.need_to_refresh = true;
    }

    private void initPop() {
        this.mDelView = getLayoutInflater().inflate(R.layout.dialog_base_layout, null);
        this.mDelView.findViewById(R.id.pop_tv_submit).setOnClickListener(this);
        this.mDelView.findViewById(R.id.pop_tv_cancel).setOnClickListener(this);
        ((TextView) this.mDelView.findViewById(R.id.tv_tip_title)).setText("\u786e\u5b9a\u5220\u9664\u8be5\u597d\u53cb\uff1f");
        this.mDialog = new BaseDialog(this);
    }

    private void initView() {
        this.container_rl = (RelativeLayout) findViewById(R.id.main_pull_refresh_view);
        this.mFriendsListView = (SwipeMenuListView) findViewById(R.id.friends_listview);
        this.mFriendsListView.setOnItemClickListener(this.mFriendsListItemClickListener);
        this.mEmptyView = findViewById(R.id.rl_empty_tip);
//        this.mEmptyView.setVisibility(4);
        this.mFriendsList = new ArrayList();
        this.mFriendsAdapter = new C06424(this, this.mFriendsList, this.mFriendsListView);
        this.mFriendsAdapter.setOnMenuItemClickListener(this.menuItemClickListener);
        this.mFriendsListView.setCustomAdatper(this.mFriendsAdapter);
        this.mGestureDetector = new GestureDetector(new GestureListener());
        this.mFriendsListView.setPullLoadEnable(true);
        this.mFriendsListView.setPullRefreshEnable(true);
        this.mFriendsListView.setXListViewListener(this);
        findViewById(R.id.friends_action_bar).setOnTouchListener(this);
        registerBroadCast();
        initPop();
    }

    public void onRefresh() {
//        PlaySound.getInstance(this).toPlay(R.raw.refresh);
//        if (this.mFriendsList.size() <= 0 || (this.isSelf && this.mDelFriendCount != 0)) {
//            getRelationusers(this.mWithUid, null, LEN, HomeActivity.UP, false);
//        } else {
//            getRelationusers(this.mWithUid, ((UserInfo) this.mFriendsList.get(TAG_RELATION_FANS)).getUid(), LEN, HomeActivity.UP, false);
//        }
    }

    public void onLoadMore() {
//        if (this.mFriendsList.size() <= 0 || (this.isSelf && this.mDelFriendCount != 0)) {
//            getRelationusers(this.mWithUid, null, LEN, HomeActivity.UP, false);
//        } else {
//            getRelationusers(this.mWithUid, ((UserInfo) this.mFriendsList.get(this.mFriendsList.size() - 1)).getUid(), LEN, HomeActivity.DOWN, false);
//        }
    }

    final void onRefreshComplete() {
        this.mFriendsListView.stopRefresh();
        this.mFriendsListView.stopLoadMore();
    }

    private void getRelationusers(String user_id, String startuid, String len, String direction, boolean isloading) {
//        QGHttpRequest.getInstance().getFriendsListRequest(this, user_id, startuid, len, direction, new C06435(this, isloading, this.container_rl, direction));
    }

    private void notifyViewUpdate() {
        if (this.mFriendsList.size() > 0) {
            this.mFriendsAdapter.notifyDataSetChanged();
        } else {
            showEmtpyTip(getResources().getString(R.string.empty_friend_tip));
        }
    }

    private void showEmtpyTip(String tip) {
//        this.mFriendsListView.setVisibility(4);
//        this.mEmptyView.setVisibility(TAG_RELATION_FANS);
//        ((TextView) this.mEmptyView.findViewById(R.id.tv_emtpy_tip)).setText(tip);
    }

    private void addFriends(String user_id) {
//        QGHttpRequest.getInstance().getAddFriendsRequest(this, user_id, new C06446(this, user_id));
    }

    private void delFriends(String user_id) {
//        QGHttpRequest.getInstance().getDelFriendsRequest(this, user_id, new C06457(this, user_id));
    }

    private void updateIsBlock(int position, int isblock) {
        ((UserInfo) this.mFriendsList.get(position)).setIsblock(isblock);
        this.mFriendsAdapter.notifyDataSetChanged();
    }

    private void updateRelation(int position, String ex_relation, boolean isFriends) {
        switch (Integer.parseInt(ex_relation)) {
            case TAG_RELATION_FANS /*0*/:
                if (!isFriends) {
                    ((UserInfo) this.mFriendsList.get(position)).setRelation("0");
                    break;
                } else {
                    ((UserInfo) this.mFriendsList.get(position)).setRelation("2");
                    break;
                }
            case TAG_RELATION_FOLLOWS /*1*/:
                if (!isFriends) {
                    ((UserInfo) this.mFriendsList.get(position)).setRelation(Constant.SYSTEM_UID);
                    break;
                } else {
                    ((UserInfo) this.mFriendsList.get(position)).setRelation("3");
                    break;
                }
            case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
                if (!isFriends) {
                    ((UserInfo) this.mFriendsList.get(position)).setRelation("0");
                    break;
                } else {
                    ((UserInfo) this.mFriendsList.get(position)).setRelation("2");
                    break;
                }
//            case ApiReqeust.PUT_METHOD /*3*/:
//                if (!isFriends) {
//                    ((UserInfo) this.mFriendsList.get(position)).setRelation(Constant.SYSTEM_UID);
//                    break;
//                } else {
//                    ((UserInfo) this.mFriendsList.get(position)).setRelation("3");
//                    break;
//                }
        }
        this.mFriendsAdapter.notifyDataSetChanged();
    }

    private void addOrDeleteFriends(String user_id, boolean isFriends) {
        if (isFriends) {
            delFriends(user_id);
        } else {
            addFriends(user_id);
        }
    }

    private void blockMessage(String to_uid) {
        QGHttpRequest.getInstance().blockMessageRequest(this, to_uid, new C06468(this, false, this.container_rl));
    }

    private void unblockMessage(String to_uid) {
        QGHttpRequest.getInstance().unblockMessageRequest(this, to_uid, new C06479(this, false, this.container_rl));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.friends_user_head:
                startPersonalActivity(view.getTag().toString());
            case R.id.pop_tv_submit:
                this.mDialog.dismiss();
                UserInfo user = (UserInfo) this.mFriendsList.get(this.mCurrentPosition);
                if ("0".equals(user.getRelation()) || Constant.SYSTEM_UID.equals(user.getRelation())) {
                    addOrDeleteFriends(user.getUid(), false);
                } else {
                    addOrDeleteFriends(user.getUid(), true);
                }
            case R.id.pop_tv_cancel:
                this.mDialog.dismiss();
            case R.id.rl_empty_click_tip:
//                getRelationusers(this.mWithUid, this.mStartuid, LEN, HomeActivity.UP, true);
            default:
        }
    }

    private void startPersonalActivity(String user_id) {
//        Intent intent = new Intent();
//        intent.setClass(this, PersonalActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString(GET_FRIENDS_WITH, user_id);
//        intent.putExtras(bundle);
//        startActivityForNew(intent);
    }

    private void startPrivateChatActivity(String user_id, String user_name, String avatar_time) {
//        Intent intent = new Intent();
//        intent.setClass(this, RCChatActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("touid", user_id);
//        bundle.putString("avatartime", avatar_time);
//        bundle.putString("nickname", user_name);
//        intent.putExtras(bundle);
//        startActivityForNew(intent);
    }

    private void startSearchActivity() {
//        Intent intent = new Intent();
//        intent.setClass(this, SearchActivity.class);
//        intent.putExtras(new Bundle());
//        startActivityForNew(intent);
    }

    public void onActionBar(View view) {
        switch (view.getId()) {
            case R.id.friends_back:
                doBack();
            case R.id.friends_add:
                startSearchActivity();
            default:
        }
    }

    private SwipeMenuItem menuIsBlock() {
        SwipeMenuItem isBlockItem = new SwipeMenuItem(getApplicationContext());
//        isBlockItem.setBackground(new ColorDrawable(Color.rgb(C0051C.f69t, C0051C.f69t, C0051C.f57j)));
        isBlockItem.setWidth(dp2px(90));
        isBlockItem.setTitle((int) R.string.menuitem_is_block);
        isBlockItem.setTitleSize(18);
        isBlockItem.setTitleColor(-1);
        return isBlockItem;
    }

    private SwipeMenuItem menuNotBlock() {
        SwipeMenuItem notBlockItem = new SwipeMenuItem(getApplicationContext());
//        notBlockItem.setBackground(new ColorDrawable(Color.rgb(C0051C.f69t, C0051C.f69t, C0051C.f57j)));
        notBlockItem.setWidth(dp2px(90));
        notBlockItem.setTitle((int) R.string.menuitem_not_block);
        notBlockItem.setTitleSize(18);
        notBlockItem.setTitleColor(-1);
        return notBlockItem;
    }

    private SwipeMenuItem menuDelete() {
        SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
        deleteItem.setBackground(new ColorDrawable(Color.rgb(MotionEventCompat.ACTION_MASK, TAG_RELATION_FANS, TAG_RELATION_FANS)));
        deleteItem.setWidth(dp2px(90));
        deleteItem.setTitle((int) R.string.menuitem_delete);
        deleteItem.setTitleSize(18);
        deleteItem.setTitleColor(-1);
        return deleteItem;
    }

    private SwipeMenuItem menuAdd() {
        SwipeMenuItem addItem = new SwipeMenuItem(getApplicationContext());
        addItem.setBackground(new ColorDrawable(Color.rgb(MotionEventCompat.ACTION_MASK, TAG_RELATION_FANS, TAG_RELATION_FANS)));
        addItem.setWidth(dp2px(90));
        addItem.setTitle((int) R.string.menuitem_add);
        addItem.setTitleSize(18);
        addItem.setTitleColor(-1);
        return addItem;
    }

    private void createSwipeMenu(SwipeMenu menu, int relation, boolean block) {
        if (relation != 4) {
            if (block) {
                menu.addMenuItem(menuIsBlock());
            } else {
                menu.addMenuItem(menuNotBlock());
            }
            switch (relation) {
                case TAG_RELATION_FANS /*0*/:
                    menu.addMenuItem(menuAdd());
                case TAG_RELATION_FOLLOWS /*1*/:
                    menu.addMenuItem(menuAdd());
                case BaseUploadFragmentActivity.PHOTOZOOM /*2*/:
                    menu.addMenuItem(menuDelete());
//                case ApiReqeust.PUT_METHOD /*3*/:
//                    menu.addMenuItem(menuDelete());
                default:
            }
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TAG_RELATION_FOLLOWS, (float) dp, getResources().getDisplayMetrics());
    }

    private void doBack() {
        finish();
    }

    public boolean onTouch(View v, MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
    }

    private void registerBroadCast() {
        this.mRemarkBroadcastReceiver = new RemarkBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.MODIFY_MARKNAME_ACTION);
        registerReceiver(this.mRemarkBroadcastReceiver, filter);
    }

    private void modifyMarkName(String uid, String rename) {
        for (int i = this.mFriendsList.size() - 1; i >= 0; i--) {
            if (uid.equals(((UserInfo) this.mFriendsList.get(i)).getUid())) {
                ((UserInfo) this.mFriendsList.get(i)).setNickname(rename);
                this.mFriendsAdapter.setUsersList(this.mFriendsList);
            }
        }
    }

    protected void onDestroy() {
        unregisterReceiver(this.mRemarkBroadcastReceiver);
        super.onDestroy();
    }
}
