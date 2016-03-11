package com.gitrose.mobile.model;

public class NetContactUserInfo {
    String local_id;
    String phonenumber;
    String relation;
    String tutu_uid;

    public String getLocal_id() {
        return this.local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getTutu_uid() {
        return this.tutu_uid;
    }

    public void setTutu_uid(String tutu_uid) {
        this.tutu_uid = tutu_uid;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
