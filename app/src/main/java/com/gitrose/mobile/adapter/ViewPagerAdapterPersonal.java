package com.gitrose.mobile.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.gitrose.mobile.model.PersonalTabItem;
import java.util.ArrayList;

public class ViewPagerAdapterPersonal extends FragmentPagerAdapter {
    private Context context;
    private ArrayList<PersonalTabItem> tabs;

    public ViewPagerAdapterPersonal(Context context, FragmentManager fm, ArrayList<PersonalTabItem> tabs) {
        super(fm);
        this.tabs = null;
        this.context = null;
        this.tabs = tabs;
        this.context = context;
    }

    public Fragment getItem(int pos) {
        Fragment fragment = null;
        if (this.tabs != null && pos < this.tabs.size()) {
            PersonalTabItem tab = (PersonalTabItem) this.tabs.get(pos);
            if (tab == null) {
                return null;
            }
            fragment = tab.createFragment();
        }
        return fragment;
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public int getCount() {
        if (this.tabs == null || this.tabs.size() <= 0) {
            return 0;
        }
        return this.tabs.size();
    }

    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        ((PersonalTabItem) this.tabs.get(position)).fragment = fragment;
        return fragment;
    }
}
