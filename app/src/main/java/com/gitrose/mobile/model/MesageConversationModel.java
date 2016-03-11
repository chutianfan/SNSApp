package com.gitrose.mobile.model;

import java.util.ArrayList;

public class MesageConversationModel {
    private String cansendmessage;
    private String errormsg;
    private ArrayList<MessageRelationModel> list;

    public ArrayList<MessageRelationModel> getList() {
        return this.list;
    }

    public void setList(ArrayList<MessageRelationModel> list) {
        this.list = list;
    }

    public String getCansendmessage() {
        return this.cansendmessage;
    }

    public void setCansendmessage(String cansendmessage) {
        this.cansendmessage = cansendmessage;
    }

    public String getErrormsg() {
        return this.errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
}
