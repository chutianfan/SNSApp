package com.gitrose.mobile.model;

import com.gitrose.greendao.TutuUsers;
import java.io.Serializable;
import java.util.ArrayList;

public class RecommendModel implements Serializable {
    private static final long serialVersionUID = 1;
    ArrayList<Theme> htlist;
    ArrayList<Theme> poilist;
    ArrayList<TutuUsers> userlist;

    public RecommendModel(ArrayList<TutuUsers> userlist, ArrayList<Theme> htlist, ArrayList<Theme> poilist) {
        this.userlist = userlist;
        this.htlist = htlist;
        this.poilist = poilist;
    }

    public ArrayList<TutuUsers> getUserlist() {
        return this.userlist;
    }

    public void setUserlist(ArrayList<TutuUsers> userlist) {
        this.userlist = userlist;
    }

    public ArrayList<Theme> getHtlist() {
        return this.htlist;
    }

    public void setHtlist(ArrayList<Theme> htlist) {
        this.htlist = htlist;
    }

    public ArrayList<Theme> getPoilist() {
        return this.poilist;
    }

    public void setPoilist(ArrayList<Theme> poilist) {
        this.poilist = poilist;
    }
}
