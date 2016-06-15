package com.testerhome.android.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.type;

/**
 * Created by Bin Li on 2016/6/14.
 */

public class TopicEntity implements Parcelable {

    private String id;
    private String title;
    private String created_at;
    private String updated_at;
    private String replied_at;
    private int replies_count;
    private String node_name;
    private int node_id;
    private int last_reply_user_id;
    private String last_reply_user_login;
    private UserEntity user;
    private boolean deleted;
    private AbilitiesEntity abilities;

    public TopicEntity() {
    }

    public int getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setReplied_at(String replied_at) {
        this.replied_at = replied_at;
    }

    public void setReplies_count(int replies_count) {
        this.replies_count = replies_count;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public void setNode_id(int node_id) {
        this.node_id = node_id;
    }

    public void setLast_reply_user_id(int last_reply_user_id) {
        this.last_reply_user_id = last_reply_user_id;
    }

    public void setLast_reply_user_login(String last_reply_user_login) {
        this.last_reply_user_login = last_reply_user_login;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setAbilities(AbilitiesEntity abilities) {
        this.abilities = abilities;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getReplied_at() {
        return replied_at;
    }

    public int getReplies_count() {
        return replies_count;
    }

    public String getNode_name() {
        return node_name;
    }

    public int getNode_id() {
        return node_id;
    }

    public int getLast_reply_user_id() {
        return last_reply_user_id;
    }

    public String getLast_reply_user_login() {
        return last_reply_user_login;
    }

    public UserEntity getUser() {
        return user;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public AbilitiesEntity getAbilities() {
        return abilities;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeString(this.replied_at);
        dest.writeInt(this.replies_count);
        dest.writeString(this.node_name);
        dest.writeInt(this.node_id);
        dest.writeInt(this.last_reply_user_id);
        dest.writeString(this.last_reply_user_login);
        dest.writeParcelable(this.user, flags);
        dest.writeByte(this.deleted ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.abilities, flags);
    }

    protected TopicEntity(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.replied_at = in.readString();
        this.replies_count = in.readInt();
        this.node_name = in.readString();
        this.node_id = in.readInt();
        this.last_reply_user_id = in.readInt();
        this.last_reply_user_login = in.readString();
        this.user = in.readParcelable(UserEntity.class.getClassLoader());
        this.deleted = in.readByte() != 0;
        this.abilities = in.readParcelable(AbilitiesEntity.class.getClassLoader());
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
