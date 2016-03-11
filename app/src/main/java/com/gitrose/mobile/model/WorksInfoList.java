package com.gitrose.mobile.model;

import java.util.List;

public class WorksInfoList {
    int total;
    List<WorksInfo> workslist;

    public List<WorksInfo> getFollowlist() {
        return this.workslist;
    }

    public void setWorkslist(List<WorksInfo> workslist) {
        this.workslist = workslist;
    }
}
