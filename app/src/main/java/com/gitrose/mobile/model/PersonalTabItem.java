package com.gitrose.mobile.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.Fragment;
//import io.rong.message.BuildConfig;

public class PersonalTabItem implements Parcelable {
//    public static final Creator<PersonalTabItem> CREATOR;
    public String currentType;
    public Fragment fragment;
    public Class fragmentClass;
    public int icon;
    public int iconnormal;
    public int iconselected;
    public int id;
    public String name;
    public boolean notifyChange;

    /* renamed from: com.gitrose.mobile.model.PersonalTabItem.1 */
    class C03551 implements Creator<PersonalTabItem> {
        C03551() {
        }

        public PersonalTabItem createFromParcel(Parcel p) {
            return new PersonalTabItem(p);
        }

        public PersonalTabItem[] newArray(int size) {
            return new PersonalTabItem[size];
        }
    }

    public PersonalTabItem(int id, String name, Class clazz) {
        this.name = null;
//        this.currentType = BuildConfig.FLAVOR;
        this.fragment = null;
        this.notifyChange = false;
        this.fragmentClass = null;
        this.fragmentClass = clazz;
        this.name = name;
        this.id = id;
    }

    public PersonalTabItem(int id, String name, int normalicom, int selecticon, Class clazz) {
        this.name = null;
//        this.currentType = BuildConfig.FLAVOR;
        this.fragment = null;
        this.notifyChange = false;
        this.fragmentClass = null;
        this.fragmentClass = clazz;
        this.name = name;
        this.id = id;
        this.iconnormal = normalicom;
        this.iconselected = selecticon;
    }

    public PersonalTabItem(Parcel p) {
        boolean z = true;
        this.name = null;
//        this.currentType = BuildConfig.FLAVOR;
        this.fragment = null;
        this.notifyChange = false;
        this.fragmentClass = null;
        this.id = p.readInt();
        this.name = p.readString();
        this.icon = p.readInt();
        if (p.readInt() != 1) {
            z = false;
        }
        this.notifyChange = z;
    }

    public static final Creator<PersonalTabItem> CREATOR = new Creator<PersonalTabItem>() {
        @Override
        public PersonalTabItem createFromParcel(Parcel in) {
            return new PersonalTabItem(in);
        }

        @Override
        public PersonalTabItem[] newArray(int size) {
            return new PersonalTabItem[size];
        }
    };

    public Fragment createFragment() {
        if (this.fragment == null) {
            try {
                this.fragment = (Fragment) this.fragmentClass.getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.fragment;
    }

    static {
//        CREATOR = new C03551();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel p, int flags) {
        p.writeInt(this.id);
        p.writeString(this.name);
        p.writeString(this.currentType);
        p.writeInt(this.notifyChange ? 1 : 0);
    }
}
