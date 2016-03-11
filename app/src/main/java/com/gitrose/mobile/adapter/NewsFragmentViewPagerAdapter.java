package com.gitrose.mobile.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import com.gitrose.mobile.NewsListActivty;
import java.util.List;
//import shouji.gexing.framework.utils.DebugUtils;

public class NewsFragmentViewPagerAdapter extends PagerAdapter implements OnPageChangeListener {
    private int currentPageIndex;
    private FragmentManager fragmentManager;
    private List<Fragment> fragments;
    private Context mContext;
    private ViewPager viewPager;

    public NewsFragmentViewPagerAdapter(Context context, FragmentManager fragmentManager, ViewPager viewPager, List<Fragment> fragments) {
        this.currentPageIndex = 0;
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
        this.viewPager = viewPager;
        this.viewPager.setOnPageChangeListener(this);
        this.mContext = context;
    }

    public int getCount() {
        return this.fragments.size();
    }

    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((Fragment) this.fragments.get(position)).getView());
//        DebugUtils.error("destroyItem--------");
    }

    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) this.fragments.get(position);
        if (!fragment.isAdded()) {
            FragmentTransaction ft = this.fragmentManager.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
            ft.commitAllowingStateLoss();
            this.fragmentManager.executePendingTransactions();
        }
        if (fragment.getView().getParent() == null) {
            container.addView(fragment.getView());
        }
//        DebugUtils.error("instantiateItem--------");
        return fragment.getView();
    }

    public int getCurrentPageIndex() {
        return this.currentPageIndex;
    }

    public int setCurrentPageIndex(int index) {
        this.currentPageIndex = index;
        return index;
    }

    public void onPageScrolled(int i, float v, int i2) {
    }

    public void onPageSelected(int i) {
        ((Fragment) this.fragments.get(this.currentPageIndex)).onPause();
        ((Fragment) this.fragments.get(this.currentPageIndex)).onStop();
        if (((Fragment) this.fragments.get(i)).isAdded()) {
            ((Fragment) this.fragments.get(i)).onResume();
        }
        this.currentPageIndex = i;
        ((NewsListActivty) this.mContext).switchUi(i);
    }

    public void onPageScrollStateChanged(int i) {
    }
}
