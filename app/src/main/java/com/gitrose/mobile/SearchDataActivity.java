package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
//import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.gitrose.mobile.adapter.FriendsAdapter;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.FriendsList;
import com.gitrose.mobile.model.UserInfo;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.view.ScrollListView;
//import io.rong.message.BuildConfig;
import java.util.ArrayList;
import java.util.List;
//import org.apache.http.Header;

public class SearchDataActivity extends BaseActivity implements OnTouchListener {
    private static final String LEN = "50";
    private RelativeLayout container_rl;
    private String key_word;
    private String mEnduid;
    private FriendsAdapter mFriendsAdapter;
    private List<UserInfo> mFriendsList;
    private OnItemClickListener mFriendsListItemClickListener;
    private GestureDetector mGestureDetector;
//    private OnRefreshListener<ScrollView> mOnRefreshListener;
//    private PullToRefreshScrollView mPullToRefreshView;
    private ScrollListView mScrollListView;
    private ScrollView mScrollView;
    private TextView mSearchNoFriends;
    private String mStartuid;

    /* renamed from: com.gitrose.mobile.SearchDataActivity.1 */
    class C03241 implements OnItemClickListener {
        C03241() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            SearchDataActivity.this.startPersonalActivity(((UserInfo) SearchDataActivity.this.mFriendsList.get(position)).getUid());
        }
    }

    class GestureListener extends SimpleOnGestureListener {
        GestureListener() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (SearchDataActivity.this.mScrollView != null) {
                SearchDataActivity.this.mScrollView.scrollTo(0, 0);
            }
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

    /* renamed from: com.gitrose.mobile.SearchDataActivity.2 */
//    class C05282 implements OnRefreshListener<ScrollView> {
//        C05282() {
//        }
//
//        public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
//            if (refreshView.getCurrentMode() == Mode.PULL_FROM_START) {
//                PlaySound.getInstance(SearchDataActivity.this).toPlay(R.raw.refresh);
//                if (SearchDataActivity.this.mFriendsList != null) {
//                    SearchDataActivity.this.getSearchUsers(SearchDataActivity.this.key_word, SearchDataActivity.this.mStartuid, SearchDataActivity.LEN, HomeActivity.UP, false);
//                } else {
//                    SearchDataActivity.this.getSearchUsers(SearchDataActivity.this.key_word, null, SearchDataActivity.LEN, HomeActivity.UP, false);
//                }
//            } else if (refreshView.getCurrentMode() != Mode.PULL_FROM_END) {
//            } else {
//                if (SearchDataActivity.this.mFriendsList == null || SearchDataActivity.this.mFriendsList.size() <= 0) {
//                    SearchDataActivity.this.getSearchUsers(SearchDataActivity.this.key_word, null, SearchDataActivity.LEN, HomeActivity.UP, false);
//                } else {
//                    SearchDataActivity.this.getSearchUsers(SearchDataActivity.this.key_word, SearchDataActivity.this.mEnduid, SearchDataActivity.LEN, HomeActivity.DOWN, false);
//                }
//            }
//        }
//    }

    /* renamed from: com.gitrose.mobile.SearchDataActivity.3 */
//    class C06913 extends QGHttpHandler<FriendsList> {
//        private final /* synthetic */ String val$direction;
//
//        C06913(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2, String str) {
//            this.val$direction = str;
//            super($anonymous0, $anonymous1, $anonymous2);
//        }
//
//        public void onFinish() {
//            SearchDataActivity.this.mPullToRefreshView.onRefreshComplete();
//            if (SearchDataActivity.this.mFriendsList != null && SearchDataActivity.this.mFriendsList.size() > 0) {
//                SearchDataActivity.this.mStartuid = ((UserInfo) SearchDataActivity.this.mFriendsList.get(0)).getUid();
//                SearchDataActivity.this.mEnduid = ((UserInfo) SearchDataActivity.this.mFriendsList.get(SearchDataActivity.this.mFriendsList.size() - 1)).getUid();
//            }
//            super.onFinish();
//        }
//
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            super.onFailure(statusCode, headers, responseString, throwable);
//            if (SearchDataActivity.this.mFriendsList == null || SearchDataActivity.this.mFriendsList.size() <= 0) {
//                SearchDataActivity.this.mPullToRefreshView.setVisibility(8);
//                SearchDataActivity.this.mSearchNoFriends.setVisibility(0);
//                return;
//            }
//            SearchDataActivity.this.mPullToRefreshView.setVisibility(0);
//            SearchDataActivity.this.mSearchNoFriends.setVisibility(8);
//        }
//
//        public void onGetDataSuccess(FriendsList data) {
//            if (data != null && data.getList().size() > 0) {
//                SearchDataActivity.this.mPullToRefreshView.setVisibility(0);
//                SearchDataActivity.this.mSearchNoFriends.setVisibility(8);
//                if (this.val$direction.equals(HomeActivity.UP)) {
//                    SearchDataActivity.this.mFriendsList.addAll(0, data.getList());
//                    SearchDataActivity.this.mFriendsAdapter.setUsersList(SearchDataActivity.this.mFriendsList);
//                    return;
//                }
//                SearchDataActivity.this.mFriendsList.addAll(data.getList());
//                SearchDataActivity.this.mFriendsAdapter.setUsersList(SearchDataActivity.this.mFriendsList);
//            } else if (!this.val$direction.equals(HomeActivity.UP)) {
//            } else {
//                if (SearchDataActivity.this.mFriendsList == null || SearchDataActivity.this.mFriendsList.size() <= 0) {
//                    SearchDataActivity.this.mPullToRefreshView.setVisibility(8);
//                    SearchDataActivity.this.mSearchNoFriends.setVisibility(0);
//                }
//            }
//        }
//    }

    public SearchDataActivity() {
        this.mFriendsListItemClickListener = new C03241();
//        this.mOnRefreshListener = new C05282();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_data);
        initView();
        Intent intent = getIntent();
        if (intent != null) {
            try {
                this.key_word = intent.getStringExtra("key_word");
                this.mFriendsList = (List) intent.getSerializableExtra("friends_list");
                if (this.mFriendsList != null) {
                    this.mStartuid = ((UserInfo) this.mFriendsList.get(0)).getUid();
                    this.mEnduid = ((UserInfo) this.mFriendsList.get(this.mFriendsList.size() - 1)).getUid();
                    this.mFriendsAdapter.setUsersList(this.mFriendsList);
                    return;
                }
//                this.mPullToRefreshView.setVisibility(8);
//                this.mSearchNoFriends.setVisibility(0);
                return;
            } catch (Exception e) {
//                this.mPullToRefreshView.setVisibility(8);
//                this.mSearchNoFriends.setVisibility(0);
                return;
            }
        }
//        getSearchUsers(BuildConfig.FLAVOR, this.mStartuid, LEN, HomeActivity.UP, true);
    }

    private void initView() {
        this.container_rl = (RelativeLayout) findViewById(R.id.main_pull_refresh_view);
        this.mSearchNoFriends = (TextView) findViewById(R.id.search_no_friends);
//        this.mPullToRefreshView = (PullToRefreshScrollView) findViewById(R.id.search_friends_refresh_view);
//        this.mPullToRefreshView.setOnRefreshListener(this.mOnRefreshListener);
//        this.mScrollView = (ScrollView) this.mPullToRefreshView.getRefreshableView();
        this.mScrollListView = (ScrollListView) findViewById(R.id.search_friends_listview);
        this.mScrollListView.setOnItemClickListener(this.mFriendsListItemClickListener);
        this.mFriendsList = new ArrayList();
        this.mFriendsAdapter = new FriendsAdapter(this, this.mFriendsList);
        this.mScrollListView.setAdapter(this.mFriendsAdapter);
        this.mGestureDetector = new GestureDetector(new GestureListener());
        findViewById(R.id.search_data_action_bar).setOnTouchListener(this);
    }

    public void onActionBar(View view) {
        switch (view.getId()) {
            case R.id.search_data_back:
                finish();
            default:
        }
    }

    private void startPersonalActivity(String user_id) {
//        Intent intent = new Intent();
//        intent.setClass(this, PersonalActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString(PersonalActivity.PERSONAL_USER_ID, user_id);
//        intent.putExtras(bundle);
//        startActivityForNew(intent);
    }

    private void getSearchUsers(String keyword, String startuid, String len, String direction, boolean isloading) {
//        QGHttpRequest.getInstance().getSearchRequest(this, keyword, startuid, len, direction, new C06913(this, isloading, this.container_rl, direction));
    }

    public boolean onTouch(View v, MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
    }
}
