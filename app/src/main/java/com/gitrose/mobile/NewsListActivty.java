package com.gitrose.mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gitrose.mobile.adapter.NewsFragmentViewPagerAdapter;
import com.gitrose.mobile.application.MyApplication;
import com.gitrose.mobile.base.BaseFragment;
import com.gitrose.mobile.base.BaseFragmentActivity;
import com.gitrose.mobile.constant.Constant;
import java.util.ArrayList;
import java.util.List;
import org.litepal.util.Const.TableSchema;
import qalsdk.BaseConstants;

public class NewsListActivty extends BaseFragmentActivity implements OnClickListener {
    private static NewsListActivty newslistActivity;
    private NewsListFragment allFragment;
    private TextView all_tv;
    private NewsListFragment atFragment;
    private TextView at_tv;
    private NewsListFragment commentFragment;
    private TextView comment_tv;
    public RelativeLayout contianer_rl;
    private int cur_index;
    private BaseFragment current_frgment;
    private NewsListFragment followFragment;
    private TextView follow_tv;
    private FragmentManager fragmentManager;
    private boolean isnews;
    private NewsFragmentViewPagerAdapter mFragmentAdapter;
    private List<Fragment> mFragmentList;
    private TipsBroadcastReciver mTipsBroadcastReceiver;
    private int startX;
    private View translate_iv;
    private ViewPager viewPaper;
    private int width;
    private NewsListFragment zanFragment;
    private TextView zan_tv;

    /* renamed from: com.gitrose.mobile.NewsListActivty.1 */
    class C07121 implements Runnable {
        C07121() {
        }

        public void run() {
            if (NewsListActivty.this.allFragment != null) {
                NewsListActivty.this.allFragment.clearNews();
            }
            if (NewsListActivty.this.commentFragment != null) {
                NewsListActivty.this.commentFragment.clearNews();
            }
            if (NewsListActivty.this.atFragment != null) {
                NewsListActivty.this.atFragment.clearNews();
            }
            if (NewsListActivty.this.zanFragment != null) {
                NewsListActivty.this.zanFragment.clearNews();
            }
            if (NewsListActivty.this.followFragment != null) {
                NewsListActivty.this.followFragment.clearNews();
            }
        }
    }

    class TipsBroadcastReciver extends BroadcastReceiver {
        TipsBroadcastReciver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (Constant.FOLLOW_USER_ACTION.equals(intent.getAction()) || Constant.UNFOLLOW_USER_ACTION.equals(intent.getAction())) {
                String uid = intent.getStringExtra("to_uid");
                String relation = intent.getStringExtra("relation");
                if (uid != null && relation != null && !relation.equals(BaseConstants.ah)) {
                    if (NewsListActivty.this.allFragment != null) {
                        NewsListActivty.this.allFragment.onReciverBroadcast(uid, relation);
                    }
                    if (NewsListActivty.this.followFragment != null) {
                        NewsListActivty.this.followFragment.onReciverBroadcast(uid, relation);
                    }
                }
            }
        }
    }

    public NewsListActivty() {
        this.isnews = false;
        this.startX = 0;
        this.cur_index = 0;
        this.mFragmentList = new ArrayList();
        this.current_frgment = null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().setNewsNum(0);
        setContentView(R.layout.activity_news_all);
        if (!MyApplication.getInstance().isLogin()) {
            finish();
        }
        newslistActivity = this;
        this.mTipsBroadcastReceiver = new TipsBroadcastReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.FOLLOW_USER_ACTION);
        filter.addAction(Constant.UNFOLLOW_USER_ACTION);
        registerReceiver(this.mTipsBroadcastReceiver, filter);
        initUi();
    }

    private void initUi() {
        this.contianer_rl = (RelativeLayout) findViewById(R.id.container_rl);
        this.all_tv = (TextView) findViewById(R.id.news_title_all_tv);
        this.zan_tv = (TextView) findViewById(R.id.news_title_all_zan);
        this.comment_tv = (TextView) findViewById(R.id.news_title_all_comment);
        this.at_tv = (TextView) findViewById(R.id.news_title_all_at);
        this.follow_tv = (TextView) findViewById(R.id.news_title_all_follow);
        this.translate_iv = findViewById(R.id.translate_iv);
        this.at_tv.setText(getResources().getString(R.string.news_notification_at).substring(1));
        this.viewPaper = (ViewPager) findViewById(R.id.news_activity_container_vp);
        this.all_tv.setOnClickListener(this);
        this.zan_tv.setOnClickListener(this);
        this.comment_tv.setOnClickListener(this);
        this.at_tv.setOnClickListener(this);
        this.follow_tv.setOnClickListener(this);
        this.width = (int) getResources().getDimension(R.dimen.news_titile_translate_width);
        this.all_tv.setSelected(true);
        this.fragmentManager = getSupportFragmentManager();
        this.allFragment = new NewsListFragment();
        this.zanFragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TableSchema.COLUMN_TYPE, Constant.LIVE_CUSTOM_TYPE_LIKE);
        this.zanFragment.setArguments(bundle);
        this.commentFragment = new NewsListFragment();
        Bundle commentBundle = new Bundle();
        commentBundle.putString(TableSchema.COLUMN_TYPE, "comment");
        this.commentFragment.setArguments(commentBundle);
        this.atFragment = new NewsListFragment();
        Bundle atBundle = new Bundle();
        atBundle.putString(TableSchema.COLUMN_TYPE, "at");
        this.atFragment.setArguments(atBundle);
        this.followFragment = new NewsListFragment();
        Bundle followBundle = new Bundle();
        followBundle.putString(TableSchema.COLUMN_TYPE, Constant.LIVE_CUSTOM_TYPE_FOLLOW);
        this.followFragment.setArguments(followBundle);
        this.mFragmentList.add(this.allFragment);
        this.mFragmentList.add(this.zanFragment);
        this.mFragmentList.add(this.commentFragment);
        this.mFragmentList.add(this.atFragment);
        this.mFragmentList.add(this.followFragment);
        this.mFragmentAdapter = new NewsFragmentViewPagerAdapter(this, this.fragmentManager, this.viewPaper, this.mFragmentList);
        this.viewPaper.setAdapter(this.mFragmentAdapter);
        this.viewPaper.setCurrentItem(0);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.news_title_all_tv:
                setCurrentItem(0);
                break;
            case R.id.news_title_all_zan:
                setCurrentItem(1);
                break;
            case R.id.news_title_all_comment:
                setCurrentItem(2);
                break;
            case R.id.news_title_all_at:
                setCurrentItem(3);
                break;
            case R.id.news_title_all_follow:
                setCurrentItem(4);
                break;
            default:
        }
    }

    private void setCurrentItem(int index) {
        switchUi(index);
        this.viewPaper.setCurrentItem(index);
    }

    public void switchUi(int index) {
        this.all_tv.setSelected(false);
        this.zan_tv.setSelected(false);
        this.comment_tv.setSelected(false);
        this.at_tv.setSelected(false);
        this.follow_tv.setSelected(false);
        switch (index) {
            case 0:
                this.all_tv.setSelected(true);
                break;
            case 1:
                this.zan_tv.setSelected(true);
                break;
            case 2:
                this.comment_tv.setSelected(true);
                break;
            case 3:
                this.at_tv.setSelected(true);
                break;
            case 4:
                this.follow_tv.setSelected(true);
                break;
        }
        moveFrontBg(this.startX, this.width * index, index);
        this.startX = this.width * index;
    }

    public void moveFrontBg(int startX, int toX, int index) {
        if (index == 4) {
            this.translate_iv.getLayoutParams().width = (int) getResources().getDimension(R.dimen.news_titile_follow_tv_width);
        } else {
            this.translate_iv.getLayoutParams().width = (int) getResources().getDimension(R.dimen.news_titile_tv_width);
        }
        TranslateAnimation anim = new TranslateAnimation((float) startX, (float) toX, 0.0f, 0.0f);
        anim.setDuration(400);
        anim.setFillAfter(true);
        this.translate_iv.startAnimation(anim);
    }

    public static NewsListActivty getObject() {
        return newslistActivity;
    }

    public void torefesh() {
        if (this.allFragment != null) {
            this.allFragment.torefesh();
        }
    }

    public void clearNews() {
        new Handler().postDelayed(new C07121(), 1000);
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mTipsBroadcastReceiver);
    }
}
