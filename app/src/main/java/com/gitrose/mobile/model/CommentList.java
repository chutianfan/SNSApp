package com.gitrose.mobile.model;

import java.io.Serializable;
import java.util.List;

public class CommentList implements Serializable {
    private static final long serialVersionUID = -6652331221512304637L;
    private List<Comment> commentlist;
    private String total;

    public String getTotal() {
        return this.total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Comment> getCommentlist() {
        return this.commentlist;
    }

    public void setCommentlist(List<Comment> commentlist) {
        this.commentlist = commentlist;
    }
}
