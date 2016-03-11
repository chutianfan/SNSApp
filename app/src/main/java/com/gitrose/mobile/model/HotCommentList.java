package com.gitrose.mobile.model;

import java.util.List;

public class HotCommentList {
    private List<Comment> commentlist;
    private String totalcomment;

    public String getTotalcomment() {
        return this.totalcomment;
    }

    public void setTotalcomment(String totalcomment) {
        this.totalcomment = totalcomment;
    }

    public List<Comment> getCommentlist() {
        return this.commentlist;
    }

    public void setCommentlist(List<Comment> commentlist) {
        this.commentlist = commentlist;
    }
}
