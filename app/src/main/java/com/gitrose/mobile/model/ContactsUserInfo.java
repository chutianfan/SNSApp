package com.gitrose.mobile.model;

import java.util.List;

public class ContactsUserInfo {
    List<ContactsAddressInfo> add;
    String birth;
    String cpy;
    List<ContactsDateInfo> date;
    String depart;
    List<ContactsEmailInfo> email;
    String first;
    String fstVoc;
    String head;
    List<ContactsIMInfo> im;
    String job;
    String last;
    String local_id;
    String lstVoc;
    String name;
    String nick;
    String note;
    List<ContactsPhoneInfo> phone;
    String phonenumber;
    String pre;
    String relation;
    String tutuid;
    List<ContactsUrlInfo> url;
    int version;

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTutuid() {
        return this.tutuid;
    }

    public void setTutuid(String tutuid) {
        this.tutuid = tutuid;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getHead() {
        return this.head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getLocal_id() {
        return this.local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String getFstVoc() {
        return this.fstVoc;
    }

    public void setFstVoc(String fstVoc) {
        this.fstVoc = fstVoc;
    }

    public String getLstVoc() {
        return this.lstVoc;
    }

    public void setLstVoc(String lstVoc) {
        this.lstVoc = lstVoc;
    }

    public List<ContactsAddressInfo> getAdd() {
        return this.add;
    }

    public void setAdd(List<ContactsAddressInfo> add) {
        this.add = add;
    }

    public String getBirth() {
        return this.birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCpy() {
        return this.cpy;
    }

    public void setCpy(String cpy) {
        this.cpy = cpy;
    }

    public List<ContactsDateInfo> getDate() {
        return this.date;
    }

    public void setDate(List<ContactsDateInfo> date) {
        this.date = date;
    }

    public String getDepart() {
        return this.depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public List<ContactsEmailInfo> getEmail() {
        return this.email;
    }

    public void setEmail(List<ContactsEmailInfo> email) {
        this.email = email;
    }

    public List<ContactsIMInfo> getIm() {
        return this.im;
    }

    public void setIm(List<ContactsIMInfo> im) {
        this.im = im;
    }

    public List<ContactsUrlInfo> getUrl() {
        return this.url;
    }

    public void setUrl(List<ContactsUrlInfo> url) {
        this.url = url;
    }

    public String getLast() {
        return this.last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getFirst() {
        return this.first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<ContactsPhoneInfo> getPhone() {
        return this.phone;
    }

    public void setPhone(List<ContactsPhoneInfo> phone) {
        this.phone = phone;
    }

    public String getPre() {
        return this.pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }
}
