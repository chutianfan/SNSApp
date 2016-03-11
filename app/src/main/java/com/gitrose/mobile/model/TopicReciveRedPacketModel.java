package com.gitrose.mobile.model;

import com.gitrose.greendao.TutuUsers;
import java.util.ArrayList;

public class TopicReciveRedPacketModel {
    private ArrayList<TopicReciveItem> recivelist;
    private TopicRedPacketInfo redpacketinfo;

    public class TopicReciveItem {
        private String addtime;
        private int isluck;
        private String itemflag;
        private float money;
        private String topicid;
        private String uid;
        private TutuUsers userinfo;

        public String getUid() {
            return this.uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTopicid() {
            return this.topicid;
        }

        public void setTopicid(String topicid) {
            this.topicid = topicid;
        }

        public float getMoney() {
            return this.money;
        }

        public void setMoney(float money) {
            this.money = money;
        }

        public int getIsluck() {
            return this.isluck;
        }

        public void setIsluck(int isluck) {
            this.isluck = isluck;
        }

        public String getAddtime() {
            return this.addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getItemflag() {
            return this.itemflag;
        }

        public void setItemflag(String itemflag) {
            this.itemflag = itemflag;
        }

        public TutuUsers getUserinfo() {
            return this.userinfo;
        }

        public void setUserinfo(TutuUsers userinfo) {
            this.userinfo = userinfo;
        }
    }

    public class TopicRedPacketInfo {
        private String addtime;
        private String order_no;
        private String paytime;
        private int recivenum;
        private int status;
        private String topicid;
        private String totalmoney;
        private int totalnum;

        public String getTopicid() {
            return this.topicid;
        }

        public void setTopicid(String topicid) {
            this.topicid = topicid;
        }

        public String getOrder_no() {
            return this.order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getTotalmoney() {
            return this.totalmoney;
        }

        public void setTotalmoney(String totalmoney) {
            this.totalmoney = totalmoney;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPaytime() {
            return this.paytime;
        }

        public void setPaytime(String paytime) {
            this.paytime = paytime;
        }

        public String getAddtime() {
            return this.addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getTotalnum() {
            return this.totalnum;
        }

        public void setTotalnum(int totalnum) {
            this.totalnum = totalnum;
        }

        public int getRecivenum() {
            return this.recivenum;
        }

        public void setRecivenum(int recivenum) {
            this.recivenum = recivenum;
        }
    }

    public TopicRedPacketInfo getRedpacketinfo() {
        return this.redpacketinfo;
    }

    public void setRedpacketinfo(TopicRedPacketInfo redpacketinfo) {
        this.redpacketinfo = redpacketinfo;
    }

    public ArrayList<TopicReciveItem> getRecivelist() {
        return this.recivelist;
    }

    public void setRecivelist(ArrayList<TopicReciveItem> recivelist) {
        this.recivelist = recivelist;
    }
}
