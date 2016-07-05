package com.testerhome.android.app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bin Li on 2016/6/14.
 */

public class TopicEntity implements Parcelable {

    private long id;
    private String title;
    private String created_at;
    private String node_name;
    private UserEntity user;

    public TopicEntity() {
    }

    public TopicEntity(long id, String title, String created_at, String node_name, String username, String avatar) {
        this.id = id;
        this.title = title;
        this.created_at = created_at;
        this.node_name = node_name;
        this.user = new UserEntity(username, avatar);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.created_at);
        dest.writeString(this.node_name);
        dest.writeParcelable(this.user, flags);
    }

    protected TopicEntity(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.created_at = in.readString();
        this.node_name = in.readString();
        this.user = in.readParcelable(UserEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<TopicEntity> CREATOR = new Parcelable.Creator<TopicEntity>() {
        @Override
        public TopicEntity createFromParcel(Parcel source) {
            return new TopicEntity(source);
        }

        @Override
        public TopicEntity[] newArray(int size) {
            return new TopicEntity[size];
        }
    };
}
