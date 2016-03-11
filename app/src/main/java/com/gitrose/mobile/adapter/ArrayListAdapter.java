package com.gitrose.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public abstract class ArrayListAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mList;

    public abstract View getView(int i, View view, ViewGroup viewGroup);

    public ArrayListAdapter(List<T> list) {
        this.mList = list;
    }

    public ArrayListAdapter(Context context) {
        this.mContext = context;
    }

    public ArrayListAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
    }

    public List<T> getList() {
        return this.mList;
    }

    public void setList(List<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void setList(T[] array) {
        List list = new ArrayList(array.length);
        for (T t : array) {
            list.add(t);
        }
        setList(list);
    }

    public int getCount() {
        if (this.mList != null) {
            return this.mList.size();
        }
        return 0;
    }

    public Object getItem(int position) {
        return this.mList == null ? null : this.mList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    protected View getViewById(View view, int id) {
        return view.findViewById(id);
    }
}
