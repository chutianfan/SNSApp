package com.gitrose.mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.baoyz.swipemenulistview.XListView;
import com.baoyz.swipemenulistview.XListView.IXListViewListener;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.adapter.HotSubjectAdapter;
import com.gitrose.mobile.adapter.SearchDataFansAdapter;
import com.gitrose.mobile.base.BaseFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.FriendsList;
import com.gitrose.mobile.model.HuaTi;
import com.gitrose.mobile.model.SquareHuati;
import com.gitrose.mobile.model.SubjectInfo;
//import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.List;
import qalsdk.BaseConstants;

public class SquareActivity extends BaseFragmentActivity implements OnClickListener, IXListViewListener, OnItemClickListener {
    private static SquareActivity squareActivity;
    private EditText et_search_content;
    private FragmentManager fragmentManager;
    private HotSubjectAdapter huatiAdapter;
    private String keyword;
    private XListView lv_square_search;
    private Handler mHandler;
    private InputMethodManager mInputMethodManager;
    private FansBroadcastReciver mTipsBroadcastReceiver;
    private RelativeLayout rl_search;
    private SquareFragment squareFragment;
    private ArrayList<SubjectInfo> squareHuatis;
//    private SquareNearbyFragment squareNearbyFragment;
//    private SquareNewFragment squareNewFragment;
//    private SquareRankListFragment squareRankListFragment;
//    private SquareZanFragment squareZanFragment;
    private TextWatcher textChangeListener;
    private ArrayList<TutuUsers> tutuUsers;
    private TextView tv_search_cancle;
    private TextView tv_search_null_hint;
    private TextView tv_square_huati;
    private TextView tv_square_users;
    private SearchDataFansAdapter usersAdapter;

    /* renamed from: com.gitrose.mobile.SquareActivity.1 */
    class C07791 implements TextWatcher {
        C07791() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            SquareActivity.this.lv_square_search.setPullLoadEnable(false);
        }

        public void beforeTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (SquareActivity.this.tutuUsers != null && SquareActivity.this.tutuUsers.size() > 0) {
                SquareActivity.this.tutuUsers.clear();
            }
            if (SquareActivity.this.squareHuatis != null && SquareActivity.this.squareHuatis.size() > 0) {
                SquareActivity.this.squareHuatis.clear();
            }
            String searchContent = s.toString().trim();
            if (searchContent.equals(BaseConstants.ah)) {
                SquareActivity.this.keyword = null;
                SquareActivity.this.lv_square_search.setPullRefreshEnable(false);
                SquareActivity.this.lv_square_search.setPullLoadEnable(false);
                SquareActivity.this.tv_search_null_hint.setVisibility(View.GONE);
                return;
            }
            SquareActivity.this.keyword = searchContent;
            if (SquareActivity.this.tv_square_huati.isSelected()) {
                SquareActivity.this.searchHuati(SquareActivity.this.keyword, null, SearchActivity.UP);
            } else if (SquareActivity.this.tv_square_users.isSelected()) {
                SquareActivity.this.searchUsers(SquareActivity.this.keyword, null, SearchActivity.UP);
            }
        }
    }

    /* renamed from: com.gitrose.mobile.SquareActivity.2 */
    class C07802 implements OnTouchListener {
        C07802() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            SquareActivity.this.mInputMethodManager.hideSoftInputFromWindow(SquareActivity.this.et_search_content.getWindowToken(), 0);
            return false;
        }
    }

    /* renamed from: com.gitrose.mobile.SquareActivity.3 */
    class C07813 implements OnEditorActionListener {
        C07813() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 3) {
                return false;
            }
            SquareActivity.this.mInputMethodManager.hideSoftInputFromWindow(SquareActivity.this.et_search_content.getWindowToken(), 0);
            if (!(SquareActivity.this.keyword == null || BaseConstants.ah.equals(SquareActivity.this.keyword))) {
                if (SquareActivity.this.tv_square_huati.isSelected()) {
                    if (SquareActivity.this.squareHuatis == null || SquareActivity.this.squareHuatis.size() <= 0) {
                        SquareActivity.this.searchHuati(SquareActivity.this.keyword, null, SearchActivity.UP);
                    } else {
                        SquareActivity.this.searchHuati(SquareActivity.this.keyword, ((SubjectInfo) SquareActivity.this.squareHuatis.get(SquareActivity.this.squareHuatis.size() - 1)).getHtid(), SearchActivity.DOWN);
                    }
                } else if (SquareActivity.this.tv_square_users.isSelected()) {
                    if (SquareActivity.this.tutuUsers == null || SquareActivity.this.tutuUsers.size() <= 0) {
                        SquareActivity.this.searchUsers(SquareActivity.this.keyword, null, SearchActivity.UP);
                    } else {
                        SquareActivity.this.searchUsers(SquareActivity.this.keyword, ((TutuUsers) SquareActivity.this.tutuUsers.get(SquareActivity.this.tutuUsers.size() - 1)).getUid(), SearchActivity.DOWN);
                    }
                }
            }
            return true;
        }
    }

    /* renamed from: com.gitrose.mobile.SquareActivity.6 */
    class C07826 implements Runnable {
        C07826() {
        }

        public void run() {
            SquareActivity.this.mInputMethodManager.toggleSoftInput(0, 2);
        }
    }

    class FansBroadcastReciver extends BroadcastReceiver {
        FansBroadcastReciver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (Constant.FOLLOW_USER_ACTION.equals(intent.getAction()) || Constant.UNFOLLOW_USER_ACTION.equals(intent.getAction())) {
                String uid = intent.getStringExtra("to_uid");
                String relation = intent.getStringExtra("relation");
                if (uid != null && relation != null && !relation.equals(BaseConstants.ah)) {
                    if (SquareActivity.this.squareFragment != null) {
                        SquareActivity.this.squareFragment.onReciverBroadcast(uid, relation);
                    }
//                    if (SquareActivity.this.squareRankListFragment != null) {
//                        SquareActivity.this.squareRankListFragment.onReciverBroadcast(uid, relation);
//                    }
                }
            }
        }
    }

    /* renamed from: com.gitrose.mobile.SquareActivity.4 */
    class C17354 extends QGHttpHandler<FriendsList> {
        private final /* synthetic */ String val$direction;

        C17354(Context $anonymous0, String str) {

            super($anonymous0);
            this.val$direction = str;
            
        }

        public void onGetDataSuccess(FriendsList data) {
            List<TutuUsers> users = data.getList();
            if (users != null && users.size() > 0) {
                SquareActivity.this.lv_square_search.setVisibility(View.VISIBLE);
                SquareActivity.this.tv_search_null_hint.setVisibility(View.GONE);
                if (this.val$direction.equals(SearchActivity.UP)) {
                    SquareActivity.this.tutuUsers.clear();
                    SquareActivity.this.tutuUsers.addAll(0, users);
                } else {
                    SquareActivity.this.tutuUsers.addAll(users);
                }
                SquareActivity.this.usersAdapter = new SearchDataFansAdapter(SquareActivity.this, true, SquareActivity.this.tutuUsers);
                SquareActivity.this.lv_square_search.setAdapter(SquareActivity.this.usersAdapter);
                SquareActivity.this.usersAdapter.setUsersList(SquareActivity.this.tutuUsers);
                SquareActivity.this.lv_square_search.setPullRefreshEnable(true);
                if (users.size() < 50) {
                    SquareActivity.this.lv_square_search.setPullLoadEnable(false);
                } else {
                    SquareActivity.this.lv_square_search.setPullLoadEnable(true);
                }
                SquareActivity.this.tv_search_null_hint.setVisibility(View.GONE);
            } else if (SquareActivity.this.tutuUsers != null && SquareActivity.this.tutuUsers.size() < 1) {
                SquareActivity.this.lv_square_search.setPullRefreshEnable(false);
                SquareActivity.this.lv_square_search.setPullLoadEnable(false);
                SquareActivity.this.lv_square_search.setAdapter(null);
                SquareActivity.this.tv_search_null_hint.setVisibility(View.VISIBLE);
            }
        }

        public void onFinish() {
            super.onFinish();
            SquareActivity.this.onRefreshComplete();
        }
    }

    /* renamed from: com.gitrose.mobile.SquareActivity.5 */
    class C17365 extends QGHttpHandler<SquareHuati> {
        private final /* synthetic */ String val$direction;

        C17365(Context $anonymous0, String str) {

            super($anonymous0);
            this.val$direction = str;
            
        }

        public void onGetDataSuccess(SquareHuati data) {
            ArrayList<SubjectInfo> subjectInfos = data.getList();
            if (subjectInfos != null && subjectInfos.size() > 0) {
                SquareActivity.this.tv_search_null_hint.setVisibility(View.GONE);
                if (this.val$direction.equals(SearchActivity.UP)) {
                    SquareActivity.this.squareHuatis.clear();
                    SquareActivity.this.squareHuatis.addAll(0, subjectInfos);
                } else {
                    SquareActivity.this.squareHuatis.addAll(subjectInfos);
                }
                SquareActivity.this.huatiAdapter = new HotSubjectAdapter(SquareActivity.this, true, SquareActivity.this.squareHuatis);
                SquareActivity.this.lv_square_search.setAdapter(SquareActivity.this.huatiAdapter);
                SquareActivity.this.huatiAdapter.setUsersList(SquareActivity.this.squareHuatis);
                SquareActivity.this.lv_square_search.setPullRefreshEnable(true);
                if (subjectInfos.size() < 50) {
                    SquareActivity.this.lv_square_search.setPullLoadEnable(false);
                } else {
                    SquareActivity.this.lv_square_search.setPullLoadEnable(true);
                }
                SquareActivity.this.tv_search_null_hint.setVisibility(View.GONE);
            } else if (SquareActivity.this.squareHuatis != null && SquareActivity.this.squareHuatis.size() < 1) {
                SquareActivity.this.lv_square_search.setPullRefreshEnable(false);
                SquareActivity.this.lv_square_search.setPullLoadEnable(false);
                SquareActivity.this.lv_square_search.setAdapter(null);
                SquareActivity.this.tv_search_null_hint.setVisibility(View.VISIBLE);
            }
        }

        public void onFinish() {
            super.onFinish();
            SquareActivity.this.onRefreshComplete();
        }
    }

    public SquareActivity() {
        this.textChangeListener = new C07791();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_page);
        this.mHandler = new Handler();
        this.mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        this.tutuUsers = new ArrayList();
        this.squareHuatis = new ArrayList();
        squareActivity = this;
        initView();
        initSearchView();
//        this.mTipsBroadcastReceiver = new FansBroadcastReciver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Constant.FOLLOW_USER_ACTION);
//        filter.addAction(Constant.UNFOLLOW_USER_ACTION);
//        registerReceiver(this.mTipsBroadcastReceiver, filter);
    }

    public static SquareActivity getIntance() {
        return squareActivity;
    }

    public void refreshView(boolean toZan) {
        if (toZan) {
            FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
//            if (this.squareZanFragment == null) {
//                this.squareZanFragment = new SquareZanFragment();
//                fragmentTransaction.add((int) R.id.square_container, this.squareZanFragment);
//            } else {
//                fragmentTransaction.show(this.squareZanFragment);
//            }
            if (this.squareFragment != null) {
                fragmentTransaction.hide(this.squareFragment);
            }
//            if (this.squareRankListFragment != null) {
//                fragmentTransaction.hide(this.squareRankListFragment);
//            }
//            if (this.squareNearbyFragment != null) {
//                fragmentTransaction.hide(this.squareNearbyFragment);
//            }
//            if (!isFinishing()) {
//                fragmentTransaction.commitAllowingStateLoss();
//            }
//            MobclickAgent.onEvent(this, "d_praise");
        }
    }

    private void initView() {
        findViewById(R.id.ll_square_search).setOnClickListener(this);
        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        this.squareFragment = new SquareFragment();
        transaction.add((int) R.id.square_container, this.squareFragment).commit();
    }

    private void initSearchView() {
//        View headView = View.inflate(this, R.layout.square_search_headview, null);
//        headView.findViewById(R.id.rl_search_head).setOnClickListener(this);
//        this.rl_search = (RelativeLayout) findViewById(R.id.rl_search);
//        this.et_search_content = (EditText) findViewById(R.id.et_search_content);
//        this.et_search_content.addTextChangedListener(this.textChangeListener);
//        this.tv_search_cancle = (TextView) findViewById(R.id.tv_search_cancle);
//        this.tv_search_cancle.setOnClickListener(this);
//        this.tv_square_huati = (TextView) headView.findViewById(R.id.tv_square_huati);
//        this.tv_square_huati.setOnClickListener(this);
//        this.tv_square_huati.setSelected(true);
//        this.tv_square_users = (TextView) headView.findViewById(R.id.tv_square_users);
//        this.tv_square_users.setOnClickListener(this);
//        this.tv_square_users.setSelected(false);
//        this.lv_square_search = (XListView) findViewById(R.id.lv_square_search);
//        this.lv_square_search.addHeaderView(headView);
//        this.lv_square_search.setPullRefreshEnable(false);
//        this.lv_square_search.setPullLoadEnable(false);
//        this.lv_square_search.setOnItemClickListener(this);
//        this.lv_square_search.setAdapter(null);
//        this.lv_square_search.setXListViewListener(this);
//        this.lv_square_search.setOnTouchListener(new C07802());
//        this.tv_search_null_hint = (TextView) findViewById(R.id.tv_search_null_hint);
//        this.tv_search_null_hint.setOnClickListener(this);
//        this.et_search_content.setOnEditorActionListener(new C07813());
//        findViewById(R.id.rl_search_middle).setOnClickListener(this);
    }

    public void refreshFragment(View v) {
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.tv_square:
                if (this.squareFragment == null) {
                    this.squareFragment = new SquareFragment();
                    fragmentTransaction.add((int) R.id.square_container, this.squareFragment);
                } else {
                    fragmentTransaction.show(this.squareFragment);
                }
//                if (this.squareZanFragment != null) {
//                    fragmentTransaction.hide(this.squareZanFragment);
//                }
//                if (this.squareRankListFragment != null) {
//                    fragmentTransaction.hide(this.squareRankListFragment);
//                }
//                if (this.squareNearbyFragment != null) {
//                    fragmentTransaction.hide(this.squareNearbyFragment);
//                }
//                MobclickAgent.onEvent(this, "d_discover");
                break;
            case R.id.tv_zan:
//                if (this.squareZanFragment == null) {
//                    this.squareZanFragment = new SquareZanFragment();
//                    fragmentTransaction.add((int) R.id.square_container, this.squareZanFragment);
//                } else {
//                    fragmentTransaction.show(this.squareZanFragment);
//                }
                if (this.squareFragment != null) {
                    fragmentTransaction.hide(this.squareFragment);
                }
//                if (this.squareRankListFragment != null) {
//                    fragmentTransaction.hide(this.squareRankListFragment);
//                }
//                if (this.squareNearbyFragment != null) {
//                    fragmentTransaction.hide(this.squareNearbyFragment);
//                }
//                MobclickAgent.onEvent(this, "d_praise");
                break;
            case R.id.tv_nearby:
//                if (this.squareNearbyFragment == null) {
//                    this.squareNearbyFragment = new SquareNearbyFragment();
//                    fragmentTransaction.add((int) R.id.square_container, this.squareNearbyFragment);
//                } else {
//                    fragmentTransaction.show(this.squareNearbyFragment);
//                }
                if (this.squareFragment != null) {
                    fragmentTransaction.hide(this.squareFragment);
                }
//                if (this.squareZanFragment != null) {
//                    fragmentTransaction.hide(this.squareZanFragment);
//                }
//                if (this.squareRankListFragment != null) {
//                    fragmentTransaction.hide(this.squareRankListFragment);
//                }
//                MobclickAgent.onEvent(this, "d_newest");
                break;
            case R.id.tv_rankingList:
//                if (this.squareRankListFragment == null) {
//                    this.squareRankListFragment = new SquareRankListFragment();
//                    fragmentTransaction.add((int) R.id.square_container, this.squareRankListFragment);
//                } else {
//                    fragmentTransaction.show(this.squareRankListFragment);
//                }
                if (this.squareFragment != null) {
                    fragmentTransaction.hide(this.squareFragment);
                }
//                if (this.squareZanFragment != null) {
//                    fragmentTransaction.hide(this.squareZanFragment);
//                }
//                if (this.squareNearbyFragment != null) {
//                    fragmentTransaction.hide(this.squareNearbyFragment);
//                }
//                MobclickAgent.onEvent(this, "d_newest");
                break;
        }
        if (!isFinishing()) {
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    private void searchUsers(String keyword, String startuid, String direction) {
        QGHttpRequest.getInstance().squareSearchUsers(this, keyword, startuid, "50", direction, new C17354(this, direction));
    }

    private void searchHuati(String keyword, String starthtid, String direction) {
        QGHttpRequest.getInstance().squareSearchHuati(this, keyword, starthtid, "50", direction, new C17365(this, direction));
    }

    public void onRefresh() {
        if (this.tv_square_users.isSelected() && this.keyword != null && !BaseConstants.ah.equals(this.keyword)) {
            searchUsers(this.keyword, null, SearchActivity.UP);
        } else if (this.tv_square_huati.isSelected() && this.keyword != null && !BaseConstants.ah.equals(this.keyword)) {
            searchHuati(this.keyword, null, SearchActivity.UP);
        }
    }

    public void onLoadMore() {
        if (this.tv_square_users.isSelected() && this.tutuUsers != null && this.tutuUsers.size() > 0 && this.keyword != null && !BaseConstants.ah.equals(this.keyword)) {
            searchUsers(this.keyword, ((TutuUsers) this.tutuUsers.get(this.tutuUsers.size() - 1)).getUid(), SearchActivity.DOWN);
        } else if (this.tv_square_huati.isSelected() && this.squareHuatis != null && this.squareHuatis.size() > 0 && this.keyword != null && !BaseConstants.ah.equals(this.keyword)) {
            searchHuati(this.keyword, ((SubjectInfo) this.squareHuatis.get(this.squareHuatis.size() - 1)).getHtid(), SearchActivity.DOWN);
        }
    }

    private void onRefreshComplete() {
        this.lv_square_search.stopRefresh();
        this.lv_square_search.stopLoadMore();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent;
        if (!this.tv_square_huati.isSelected() || this.squareHuatis == null || this.squareHuatis.size() <= 0) {
            if (this.tv_square_users.isSelected() && this.tutuUsers != null && this.tutuUsers.size() > 0 && position > 1) {
//                TutuUsers user = (TutuUsers) this.tutuUsers.get(position - 2);
//                intent = new Intent(this, PersonalNewActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(PersonalNewActivity.PERSONAL_USER_ID, user.getUid());
//                intent.putExtras(bundle);
//                startActivityForNew(intent);
            }
        } else if (position > 1) {
//            SubjectInfo subjectInfo = (SubjectInfo) this.squareHuatis.get(position - 2);
//            intent = new Intent(this, ThemeActivity.class);
//            HuaTi huaTiPoi = new HuaTi();
//            huaTiPoi.setTopicid(subjectInfo.getHtid());
//            huaTiPoi.setHuatitext(subjectInfo.getHttext());
//            huaTiPoi.setType(true);
//            intent.putExtra("huati", huaTiPoi);
//            startActivityForNew(intent);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_square_search:
//                this.rl_search.setAnimation(AnimationUtils.loadAnimation(this, R.anim.main_translatey100to0));
//                this.rl_search.setVisibility(View.VISIBLE);
//                this.et_search_content.setFocusable(true);
//                this.et_search_content.setFocusableInTouchMode(true);
//                this.et_search_content.requestFocus();
//                this.mHandler.postDelayed(new C07826(), 500);
//                MobclickAgent.onEvent(this, "d_search");
                break;
            case R.id.tv_search_cancle:
                this.mInputMethodManager.hideSoftInputFromWindow(this.et_search_content.getWindowToken(), 0);
                this.rl_search.setAnimation(AnimationUtils.loadAnimation(this, R.anim.main_translatey0to100));
                this.rl_search.setVisibility(View.GONE);
                this.keyword = BaseConstants.ah;
                this.et_search_content.setText(BaseConstants.ah);
                this.lv_square_search.setAdapter(null);
                this.lv_square_search.setPullRefreshEnable(false);
                this.lv_square_search.setPullLoadEnable(false);
                break;
            case R.id.tv_search_null_hint:
            case R.id.rl_search_head:
                this.mInputMethodManager.hideSoftInputFromWindow(this.et_search_content.getWindowToken(), 0);
                break;
            case R.id.tv_square_huati:
                this.tv_square_huati.setSelected(true);
                this.tv_square_users.setSelected(false);
                if (this.keyword != null && !BaseConstants.ah.equals(this.keyword)) {
                    searchHuati(this.keyword, null, SearchActivity.UP);
                }
                break;
            case R.id.tv_square_users:
                this.tv_square_users.setSelected(true);
                this.tv_square_huati.setSelected(false);
                if (this.keyword != null && !BaseConstants.ah.equals(this.keyword)) {
                    searchUsers(this.keyword, null, SearchActivity.UP);
                }
                break;
            default:
        }
    }
}
