package com.gitrose.mobile.utils;

import com.gitrose.mobile.model.GroupMemberBean;
import java.util.Comparator;

public class PinyinComparator implements Comparator<GroupMemberBean> {
    public int compare(GroupMemberBean o1, GroupMemberBean o2) {
        if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
            return -1;
        }
        if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
            return 1;
        }
        return o1.getSortLetters().compareTo(o2.getSortLetters());
    }
}
