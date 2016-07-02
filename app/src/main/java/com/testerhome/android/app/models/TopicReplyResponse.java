package com.testerhome.android.app.models;

import java.util.List;

/**
 * Created by Bin Li on 2016/7/2.
 */
public class TopicReplyResponse {

    private List<TopicReplyEntity> replies;

    public  List<TopicReplyEntity> getTopicReply(){
        return replies;
    }

    public void setTopicReply(List<TopicReplyEntity> replies){
        this.replies = replies;
    }
}
