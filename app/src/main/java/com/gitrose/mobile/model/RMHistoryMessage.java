package com.gitrose.mobile.model;

import java.util.List;

public class RMHistoryMessage {
    List<HistoryMessage> msglist;
    List<HistoryConversation> sessionlist;

    public List<HistoryMessage> getMsglist() {
        return this.msglist;
    }

    public void setMsglist(List<HistoryMessage> msglist) {
        this.msglist = msglist;
    }

    public List<HistoryConversation> getSessionlist() {
        return this.sessionlist;
    }

    public void setSessionlist(List<HistoryConversation> sessionlist) {
        this.sessionlist = sessionlist;
    }
}
