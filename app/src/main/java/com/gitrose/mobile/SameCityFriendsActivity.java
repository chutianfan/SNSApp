package com.gitrose.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
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
import com.baoyz.swipemenulistview.XListView;
import com.baoyz.swipemenulistview.XListView.IXListViewListener;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.adapter.SameCityFriendsAdapter;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.FriendsList;
import com.gitrose.mobile.model.UserInfo;
import com.gitrose.mobile.picker.AreaPicker;
import com.gitrose.mobile.utils.PlaySound;
import com.gitrose.mobile.view.BaseDialog;
//import io.rong.message.BuildConfig;
import java.util.ArrayList;
import java.util.List;
//import org.apache.http.Header;
//import shouji.gexing.framework.utils.DebugUtils;
//import shouji.gexing.framework.utils.SpUtils;

public class SameCityFriendsActivity extends BaseActivity implements OnTouchListener, OnClickListener, IXListViewListener {
    private static final String LEN = "20";
    private AreaPicker areaPicker;
    private RelativeLayout container_rl;
    private BaseDialog dialog;
    private boolean isLocationFrist;
    private boolean isSetP2C;
    private View mAreaView;
    private String mCity;
    private SameCityFriendsAdapter mFriendsAdapter;
    private List<TutuUsers> mFriendsList;
    private OnItemClickListener mFriendsListItemClickListener;
    private String mGender;
    private GestureDetector mGestureDetector;
    private String mLatitude;
    private XListView mListView;
    private String mLongitude;
    private List<String> mMunicipality;
    private TextView mNearbyCity;
    private String mProvince;
    private View mSexView;
    private String mTempCity;
    private String mTempProvine;
    private TextView mTipTv;
    private OnClickListener nearbyClickListener;

    /* renamed from: com.gitrose.mobile.SameCityFriendsActivity.1 */
    class C03211 implements OnClickListener {
        C03211() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.friends_user_head:
                    UserInfo userInfo = (UserInfo) v.getTag();
//                    Intent intent = new Intent(SameCityFriendsActivity.this, PersonalActivity.class);
//                    intent.putExtra(PersonalActivity.PERSONAL_USER_ID, userInfo.getUid());
//                    SameCityFriendsActivity.this.startActivityForNew(intent);
                case R.id.nearby_select_all:
                    SameCityFriendsActivity.this.mGender = null;
                    SameCityFriendsActivity.this.mFriendsList.clear();
//                    SameCityFriendsActivity.this.getSameCityFriends(SameCityFriendsActivity.this.mLatitude, SameCityFriendsActivity.this.mLongitude, SameCityFriendsActivity.this.mProvince, SameCityFriendsActivity.this.mCity, null, null, HomeActivity.UP, true);
                    SameCityFriendsActivity.this.dialog.dismiss();
//                    SpUtils.clearSp("nearby_sex", SameCityFriendsActivity.this);
                case R.id.nearby_select_male:
                    SameCityFriendsActivity.this.mGender = String.valueOf(1);
//                    SameCityFriendsActivity.this.getSameCityFriends(SameCityFriendsActivity.this.mLatitude, SameCityFriendsActivity.this.mLongitude, SameCityFriendsActivity.this.mProvince, SameCityFriendsActivity.this.mCity, null, SameCityFriendsActivity.this.mGender, HomeActivity.UP, true);
                    SameCityFriendsActivity.this.dialog.dismiss();
                    SameCityFriendsActivity.this.mFriendsList.clear();
//                    SpUtils.saveToLocal(SameCityFriendsActivity.this, "nearby_sex", "sex", SameCityFriendsActivity.this.mGender);
                case R.id.nearby_select_female:
                    SameCityFriendsActivity.this.mGender = String.valueOf(2);
                    SameCityFriendsActivity.this.mFriendsList.clear();
//                    SameCityFriendsActivity.this.getSameCityFriends(SameCityFriendsActivity.this.mLatitude, SameCityFriendsActivity.this.mLongitude, SameCityFriendsActivity.this.mProvince, SameCityFriendsActivity.this.mCity, null, SameCityFriendsActivity.this.mGender, HomeActivity.UP, true);
                    SameCityFriendsActivity.this.dialog.dismiss();
//                    SpUtils.saveToLocal(SameCityFriendsActivity.this, "nearby_sex", "sex", SameCityFriendsActivity.this.mGender);
                case R.id.pop_nearby_cancel:
                    SameCityFriendsActivity.this.dialog.dismiss();
                default:
            }
        }
    }

    /* renamed from: com.gitrose.mobile.SameCityFriendsActivity.2 */
    class C03222 implements OnItemClickListener {
        C03222() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
            int position = pos + -2 >= 0 ? pos - 2 : 0;
            TutuUsers ui = null;
            if (SameCityFriendsActivity.this.mFriendsList != null && position < SameCityFriendsActivity.this.mFriendsList.size()) {
                ui = (TutuUsers) SameCityFriendsActivity.this.mFriendsList.get(position);
            }
            if (ui != null && ui.getUid() != null) {
                SameCityFriendsActivity.this.startPersonalActivity(ui.getUid());
            }
        }
    }

    class GestureListener extends SimpleOnGestureListener {
        GestureListener() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            SameCityFriendsActivity.this.mListView.smoothScrollToPosition(0);
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

    /* renamed from: com.gitrose.mobile.SameCityFriendsActivity.3 */
    class C06893 extends QGHttpHandler<FriendsList> {
        C06893(Context $anonymous0, boolean $anonymous1, ViewGroup $anonymous2) {
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        public void onFinish() {
            super.onFinish();
            SameCityFriendsActivity.this.onRefreshComplete();
        }

        public void onGetDataSuccess(FriendsList data) {
            if (data != null && !BuildConfig.FLAVOR.equals(data)) {
                SameCityFriendsActivity.this.mFriendsList.addAll(SameCityFriendsActivity.this.mFriendsList.size(), data.getList());
                SameCityFriendsActivity.this.notifyDataSetChanged();
            }
        }
    }

    public SameCityFriendsActivity() {
        this.mGender = null;
        this.mMunicipality = new ArrayList();
        this.isLocationFrist = true;
        this.isSetP2C = false;
        this.nearbyClickListener = new C03211();
        this.mFriendsListItemClickListener = new C03222();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AiTheme);
        setContentView(R.layout.activity_same_city_friends);
        shoudLocation();
        this.mLatitude = String.valueOf(MyApplication.getInstance().getLatitude());
        this.mLongitude = String.valueOf(MyApplication.getInstance().getLongitude());
        if (this.isLocationFrist) {
            this.mTempProvine = MyApplication.getInstance().getLocationProvince();
            this.mTempCity = MyApplication.getInstance().getLocationCity();
        } else if (this.isSetP2C) {
            this.mProvince = MyApplication.getInstance().getLocationProvince();
            this.mCity = MyApplication.getInstance().getLocationCity();
        }
        this.mMunicipality.add("\u5317\u4eac\u5e02");
        this.mMunicipality.add("\u4e0a\u6d77\u5e02");
        this.mMunicipality.add("\u5929\u6d25\u5e02");
        this.mMunicipality.add("\u91cd\u5e86\u5e02");
        if (this.mMunicipality.contains(this.mTempCity)) {
            this.mTempCity = MyApplication.getInstance().getLocationDistrict();
        }
        initView();
//        this.mGender = (String) SpUtils.getFromLocal(this, "nearby_sex", "sex", "01");
        if (this.mGender.equals("01")) {
//            getSameCityFriends(this.mLatitude, this.mLongitude, this.mProvince, this.mCity, null, null, HomeActivity.UP, true);
            return;
        }
//        getSameCityFriends(this.mLatitude, this.mLongitude, this.mProvince, this.mCity, null, this.mGender, HomeActivity.UP, true);
    }

    private void initView() {
        findViewById(R.id.same_city_back).setOnClickListener(this);
        findViewById(R.id.same_city_gender).setOnClickListener(this);
        this.container_rl = (RelativeLayout) findViewById(R.id.main_pull_refresh_view);
        if (this.mTempProvine == null) {
            this.mTempProvine = BuildConfig.FLAVOR;
        }
        if (this.mTempCity == null) {
            this.mTempCity = BuildConfig.FLAVOR;
        }
        this.mListView = (XListView) findViewById(R.id.same_city_friends_listview);
        this.mListView.setOnItemClickListener(this.mFriendsListItemClickListener);
        addHeadView();
        this.mFriendsList = new ArrayList();
        this.mFriendsAdapter = new SameCityFriendsAdapter(this, this.mFriendsList, this.nearbyClickListener);
        this.mListView.setAdapter(this.mFriendsAdapter);
        this.mListView.setPullLoadEnable(true);
        this.mListView.setPullRefreshEnable(true);
        this.mListView.setXListViewListener(this);
        this.mGestureDetector = new GestureDetector(new GestureListener());
        findViewById(R.id.same_city_action_bar).setOnTouchListener(this);
        this.dialog = new BaseDialog(this);
        this.mAreaView = getLayoutInflater().inflate(R.layout.dialog_modify_area, null);
        this.areaPicker = (AreaPicker) this.mAreaView.findViewById(R.id.areaPicker);
        this.mSexView = getLayoutInflater().inflate(R.layout.dialog_nearby_sex_selector, null);
        this.mSexView.findViewById(R.id.nearby_select_all).setOnClickListener(this.nearbyClickListener);
        this.mSexView.findViewById(R.id.nearby_select_male).setOnClickListener(this.nearbyClickListener);
        this.mSexView.findViewById(R.id.nearby_select_female).setOnClickListener(this.nearbyClickListener);
        this.mSexView.findViewById(R.id.pop_nearby_cancel).setOnClickListener(this.nearbyClickListener);
        this.mTipTv = (TextView) findViewById(R.id.search_no_friends);
    }

    private void addHeadView() {
        View searchView = getLayoutInflater().inflate(R.layout.head_same_city_friends, null);
        this.mListView.addHeaderView(searchView, null, true);
        this.mNearbyCity = (TextView) searchView.findViewById(R.id.search_with_same_city);
        this.mNearbyCity.setText(this.mTempProvine + " " + this.mTempCity);
    }

    private void getSameCityFriends(String latitude, String longitude, String province, String city, String startuid, String gender, String direction, boolean isloading) {
//        DebugUtils.error(this.mProvince + ";" + this.mCity);
        QGHttpRequest.getInstance().getSameCityFriendsRequest(this, String.valueOf(latitude), String.valueOf(longitude), province, city, startuid, gender, LEN, direction, new C06893(this, isloading, this.container_rl));
    }

    private void notifyDataSetChanged() {
        this.mFriendsAdapter.notifyDataSetChanged();
//        this.mTipTv.setVisibility(this.mFriendsList.size() == 0 ? 0 : 4);
    }

    public void onRefresh() {
        shoudLocation();
        this.mFriendsList.clear();
//        PlaySound.getInstance(this).toPlay(R.raw.refresh);
//        getSameCityFriends(this.mLatitude, this.mLongitude, this.mProvince, this.mCity, null, this.mGender, HomeActivity.UP, false);
    }

    public void onLoadMore() {
        String endUid = null;
        if (this.mFriendsList.size() > 0) {
            endUid = ((TutuUsers) this.mFriendsList.get(this.mFriendsList.size() - 1)).getUid();
        }
//        getSameCityFriends(this.mLatitude, this.mLongitude, this.mProvince, this.mCity, endUid, this.mGender, HomeActivity.DOWN, false);
    }

    final void onRefreshComplete() {
        this.mListView.stopRefresh();
        this.mListView.stopLoadMore();
    }

    private void shoudLocation() {
        MyApplication.getInstance().initLocation();
        this.mLatitude = String.valueOf(MyApplication.getInstance().getLatitude());
        this.mLongitude = String.valueOf(MyApplication.getInstance().getLongitude());
        if (this.isLocationFrist) {
            this.mTempProvine = MyApplication.getInstance().getLocationProvince();
            this.mTempCity = MyApplication.getInstance().getLocationCity();
        } else if (this.isSetP2C) {
            this.mProvince = MyApplication.getInstance().getLocationProvince();
            this.mCity = MyApplication.getInstance().getLocationCity();
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

    public boolean onTouch(View v, MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.same_city_back:
                finish();
            case R.id.same_city_gender:
                this.dialog.show(this.mSexView);
            case R.id.pop_area_close:
                this.dialog.dismiss();
            case R.id.pop_area_submit:
                setNearby();
                this.mFriendsList.clear();
//                getSameCityFriends(this.mLatitude, this.mLongitude, this.mProvince, this.mCity, null, this.mGender, HomeActivity.UP, true);
                this.dialog.dismiss();
            case R.id.same_city_top:
                this.dialog.show(this.mAreaView);
            default:
        }
    }

    private void setNearby() {
        this.mFriendsList.clear();
        this.mProvince = this.areaPicker.getArea()[0];
        this.mCity = this.areaPicker.getArea()[1];
        this.isLocationFrist = false;
        this.isSetP2C = false;
        this.mNearbyCity.setText(this.mProvince + " " + this.mCity);
    }
}
