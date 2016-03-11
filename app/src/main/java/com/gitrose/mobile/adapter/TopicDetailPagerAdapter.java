package com.gitrose.mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gitrose.mobile.TopicDetailFragment;

import java.util.List;

public class TopicDetailPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> list;

    public TopicDetailPagerAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.list = list;
    }

    public Fragment getItem(int arg0) {
        return TopicDetailFragment.newInstance();
    }

    public int getCount() {
        return this.list.size();
    }
}
