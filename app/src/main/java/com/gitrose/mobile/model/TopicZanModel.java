package com.gitrose.mobile.model;

import com.gitrose.greendao.TutuUsers;
import java.util.ArrayList;

public class TopicZanModel {
    private int likenum;
    private ArrayList<TopicZanItemModel> list;

    public class TopicZanItemModel {
        private String addtime;
        private String likeflag;
        private TutuUsers userinfo;

        public TutuUsers getUserinfo() {
            return this.userinfo;
        }

        public void setUserinfo(TutuUsers userinfo) {
            this.userinfo = userinfo;
        }

        public String getAddtime() {
            return this.addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getLikeflag() {
            return this.likeflag;
        }

        public void setLikeflag(String likeflag) {
            this.likeflag = likeflag;
        }
    }

    public int getLikenum() {
        return this.likenum;
    }

    public void setLikenum(int likenum) {
        this.likenum = likenum;
    }

    public ArrayList<TopicZanItemModel> getList() {
        return this.list;
    }

    public void setList(ArrayList<TopicZanItemModel> list) {
        this.list = list;
    }
}
