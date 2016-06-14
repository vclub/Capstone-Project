package com.testerhome.android.app.models;

import java.util.List;

/**
 * Created by Bin Li on 2016/6/14.
 */

public class TopicsResponse {

    private List<TopicEntity> topics;

    public List<TopicEntity> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicEntity> topics) {
        this.topics = topics;
    }
}
