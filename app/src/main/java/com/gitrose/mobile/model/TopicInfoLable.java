package com.gitrose.mobile.model;

import com.gitrose.greendao.TopicInfo;
import java.util.ArrayList;

public class TopicInfoLable {
    private String time;
    private ArrayList<TopicInfo> topic_info_list;

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<TopicInfo> getTopic_info_list() {
        return this.topic_info_list;
    }

    public void setTopic_info_list(ArrayList<TopicInfo> topic_info_list) {
        this.topic_info_list = topic_info_list;
    }
}
