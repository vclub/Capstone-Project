package com.testerhome.android.app.models;

/**
 * Created by Bin Li on 2016/6/16.
 */

public class TopicDetailEntity extends TopicEntity {

    private String body_html;
    private String body;
    private String hits;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody_html() {
        return body_html;
    }

    public void setBody_html(String body_html) {
        this.body_html = body_html;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }
}
