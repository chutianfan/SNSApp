package com.gitrose.mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import com.gitrose.greendao.TutuUsers;
import com.gitrose.mobile.adapter.RecommendListAdapter;
import com.gitrose.mobile.application.ActivityManager;
import com.gitrose.mobile.base.BaseActivity;
import com.gitrose.mobile.constant.Constant;
import com.gitrose.mobile.http.QGHttpHandler;
import com.gitrose.mobile.http.QGHttpRequest;
import com.gitrose.mobile.model.HuaTi;
import com.gitrose.mobile.model.RecommendDomin;
import com.gitrose.mobile.model.RecommendModel;
import com.gitrose.mobile.model.Theme;
import com.gitrose.mobile.utils.ImageUtils;
import io.rong.common.ResourceUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import qalsdk.BaseConstants;

public class RecommendActivity extends BaseActivity implements OnClickListener {
    private MyBroadcastReceiver MyBroadcastReceiver;
    AnimationDrawable animationDrawable;
    private RecommendListAdapter mAdapter;
    private List<RecommendDomin> mList;
    private ListView mListView;
    private ImageView progerss_iv;

    /* renamed from: com.gitrose.mobile.RecommendActivity.1 */
    class C07591 implements OnItemClickListener {
        C07591() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            RecommendDomin domin = (RecommendDomin) RecommendActivity.this.mList.get(position);
            int type = domin.getType();
            Intent intent;
            if (type == 0) {
//                String userId = domin.getUserID();
//                intent = new Intent(RecommendActivity.this, PersonalNewActivity.class);
//                intent.putExtra(PersonalNewActivity.PERSONAL_USER_ID, userId);
//                RecommendActivity.this.startActivityForNew(intent);
            } else if (type == 1) {
//                String themeId = domin.getThemeID();
//                String themeName = domin.getTitle();
//                HuaTi huaTi = new HuaTi();
//                huaTi.setType(true);
//                huaTi.setTopicid(themeId);
//                huaTi.setHuatitext(themeName);
//                intent = new Intent(RecommendActivity.this, ThemeActivity.class);
//                intent.putExtra("huati", huaTi);
//                RecommendActivity.this.startActivityForNew(intent);
            } else if (type == 2) {
//                String poiId = domin.getPoiID();
//                String poiName = domin.getTitle();
//                HuaTi huaTi = new HuaTi();
//                huaTi.setType(false);
//                huaTi.setTopicid(poiId);
//                huaTi.setHuatitext(poiName);
//                intent = new Intent(RecommendActivity.this, ThemeActivity.class);
//                intent.putExtra("huati", huaTi);
//                RecommendActivity.this.startActivityForNew(intent);
            }
        }
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        MyBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if ((intent != null && intent.getAction().equals(Constant.FOLLOW_USER_ACTION)) || intent.getAction().equals(Constant.UNFOLLOW_USER_ACTION)) {
                String uid = intent.getStringExtra("to_uid");
                int relation = Integer.parseInt(intent.getStringExtra("relation"));
                boolean selected = false;
                if (relation == 2 || relation == 3) {
                    selected = true;
                }
                for (int i = 0; i < RecommendActivity.this.mList.size(); i++) {
                    String userId = ((RecommendDomin) RecommendActivity.this.mList.get(i)).getUserID();
                    if (userId != null && userId.equals(uid)) {
                        ((RecommendDomin) RecommendActivity.this.mList.get(i)).setSelected(selected);
                        RecommendActivity.this.mAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
            if (intent != null && intent.getAction().equals(Constant.REFRESH_FOLLOW_TOPIC_ACTION)) {
                modifyThemeStatus(true, intent);
            }
            if (intent != null && intent.getAction().equals(Constant.REFRESH_UNFOLLOW_TOPIC_ACTION)) {
                modifyThemeStatus(false, intent);
            }
        }

        private void modifyThemeStatus(boolean isSelected, Intent intent) {
            boolean isTheme = intent.getBooleanExtra("isTheme", true);
            String id = intent.getStringExtra(ResourceUtils.id);
            int i = 0;
            while (i < RecommendActivity.this.mList.size()) {
                String targetId;
                RecommendDomin domin = (RecommendDomin) RecommendActivity.this.mList.get(i);
                if (isTheme) {
                    targetId = domin.getThemeID();
                } else {
                    targetId = domin.getPoiID();
                }
                if (targetId == null || !targetId.equals(id)) {
                    i++;
                } else {
                    domin.setSelected(isSelected);
                    RecommendActivity.this.mAdapter.notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    /* renamed from: com.gitrose.mobile.RecommendActivity.2 */
    class C16972 extends QGHttpHandler<RecommendModel> {
        C16972(Context $anonymous0) {
            super($anonymous0);
        }

        public void onGetDataSuccess(RecommendModel data) {
            if (data != null) {
                RecommendActivity.this.fillData2MainList(data);
            }
        }

        public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        public void onFinish() {
            super.onFinish();
            RecommendActivity.this.hideLoadingView();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        this.mListView = (ListView) findViewById(R.id.recomend_list_lv);
        findViewById(R.id.close_tv).setOnClickListener(this);
        this.mList = new ArrayList();
        this.mAdapter = new RecommendListAdapter(this, this.mList, false);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setOnItemClickListener(new C07591());
        this.progerss_iv = (ImageView) findViewById(R.id.progerss_iv);
        showLoadingView();
        requestData();
        this.MyBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.FOLLOW_USER_ACTION);
        filter.addAction(Constant.UNFOLLOW_USER_ACTION);
        filter.addAction(Constant.REFRESH_FOLLOW_TOPIC_ACTION);
        filter.addAction(Constant.REFRESH_UNFOLLOW_TOPIC_ACTION);
        registerReceiver(this.MyBroadcastReceiver, filter);
    }

    public void showLoadingView() {
        if (this.progerss_iv != null) {
            this.progerss_iv.setVisibility(View.VISIBLE);
            this.animationDrawable = (AnimationDrawable) this.progerss_iv.getBackground();
            this.animationDrawable.start();
        }
    }

    public void hideLoadingView() {
        if (this.progerss_iv != null && this.progerss_iv.getVisibility() != View.GONE) {
            if (this.animationDrawable != null) {
                this.animationDrawable.stop();
            }
            this.progerss_iv.setVisibility(View.GONE);
        }
    }

    private void requestData() {
        QGHttpRequest.getInstance().getRecommendList(this, new C16972(this));
    }

    protected void fillData2MainList(RecommendModel data) {
        int i;
        RecommendDomin domin = null;
        Theme themeInfo;
        String themeId;
        String themeName;
        String imgUrl;
        String joinusercount;
        boolean isSelected;
        RecommendDomin recommendDomin;
        ArrayList<TutuUsers> userinfos = data.getUserlist();
        ArrayList<Theme> themeInfos = data.getHtlist();
        ArrayList<Theme> poiInfos = data.getPoilist();
        if (userinfos != null) {
            for (i = 0; i < userinfos.size(); i++) {
                TutuUsers info = (TutuUsers) userinfos.get(i);
                int age = info.getAge().intValue();
                String nickName = info.getNickname();
                int sex = info.getGender().intValue();
                String uid = info.getUid();
                String avatartime = info.getAvatartime();
                String descinfo = info.getDescinfo();
                int relation = Integer.parseInt(info.getRelation());
                String reMarkName = info.getRemarkname();
                int honorlevel = info.getUserhonorlevel().intValue();
                String isauth = info.getIsauth();
                if (reMarkName != null) {
                    if (!reMarkName.equals(BaseConstants.ah)) {
                        nickName = reMarkName;
                    }
                }
                boolean selected = false;
                if (relation == 2 || relation == 3) {
                    selected = true;
                }
                if (i == 0) {
                    domin = new RecommendDomin(uid, nickName, descinfo, ImageUtils.getAvatarUrl(uid, avatartime), 0, 1, selected, sex, age, honorlevel, isauth);
                } else {
                    domin = new RecommendDomin(uid, nickName, descinfo, ImageUtils.getAvatarUrl(uid, avatartime), 0, 0, selected, sex, age, honorlevel, isauth);
                }
                this.mList.add(domin);
            }
        }
        if (themeInfos != null) {
            for (i = 0; i < themeInfos.size(); i++) {
                themeInfo = (Theme) themeInfos.get(i);
                themeId = themeInfo.getIds();
                themeName = themeInfo.getIdstext();
                imgUrl = themeInfo.getContent();
                joinusercount = themeInfo.getJoinusercount();
                isSelected = false;
                if (Integer.parseInt(themeInfo.getIsfollow()) == 1) {
                    isSelected = true;
                }
                if (i == 0) {
                    recommendDomin = new RecommendDomin(themeId, themeName, joinusercount, imgUrl, 1, 2, isSelected);
                } else {
                    recommendDomin = new RecommendDomin(themeId, themeName, joinusercount, imgUrl, 1, 0, isSelected);
                }
                this.mList.add(domin);
            }
        }
        if (poiInfos != null) {
            for (i = 0; i < poiInfos.size(); i++) {
                themeInfo = (Theme) poiInfos.get(i);
                themeId = themeInfo.getIds();
                themeName = themeInfo.getIdstext();
                imgUrl = themeInfo.getContent();
                joinusercount = themeInfo.getJoinusercount();
                isSelected = false;
                if (Integer.parseInt(themeInfo.getIsfollow()) == 1) {
                    isSelected = true;
                }
                if (i == 0) {
                    recommendDomin = new RecommendDomin(themeId, themeName, joinusercount, imgUrl, 2, 3, isSelected);
                } else {
                    recommendDomin = new RecommendDomin(themeId, themeName, joinusercount, imgUrl, 2, 0, isSelected);
                }
                this.mList.add(domin);
            }
        }
        this.mAdapter.notifyDataSetChanged();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_tv:
                backAction();
            default:
        }
    }

    private void backAction() {
        ActivityManager.getScreenManager().popAllActivity();
        startActivityForNew(new Intent(this, TabMainActivity.class));
        finish();
    }

    public void onBackPressed() {
        super.onBackPressed();
        backAction();
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.MyBroadcastReceiver);
    }
}
