package com.testerhome.android.app.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.testerhome.android.app.R;
import com.testerhome.android.app.models.TopicEntity;
import com.testerhome.android.app.provider.favorite.FavoriteColumns;
import com.testerhome.android.app.ui.TopicDetailActivity;
import com.testerhome.android.app.util.ImageUrlConvert;
import com.testerhome.android.app.util.StringUtils;

/**
 * Created by Bin Li on 2016/7/5.
 */

public class FavoriteAdapter extends CursorAdapter {

    public FavoriteAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_topic, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        long topicId = cursor.getLong(0);
        String topicTitle = cursor.getString(cursor.getColumnIndex(FavoriteColumns.TITLE));
        String usernameStr = cursor.getString(cursor.getColumnIndex(FavoriteColumns.NAME));
        String publishDate = cursor.getString(cursor.getColumnIndex(FavoriteColumns.CREATE_AT));
        String avatar = cursor.getString(cursor.getColumnIndex(FavoriteColumns.AVATAR_URL));
        String node = cursor.getString(cursor.getColumnIndex(FavoriteColumns.NODE_NAME));

        TextView title = (TextView)view.findViewById(R.id.tv_topic_title);
        title.setText(topicTitle);

        SimpleDraweeView topicUserAvatar = (SimpleDraweeView)view.findViewById(R.id.sdv_topic_user_avatar);
        topicUserAvatar.setImageURI(Uri.parse(ImageUrlConvert.getImageUrl(avatar)));

        TextView username = (TextView)view.findViewById(R.id.tv_topic_username);
        username.setText(usernameStr);

        TextView topicPublishDate = (TextView)view.findViewById(R.id.tv_topic_publish_date);
        topicPublishDate.setText(StringUtils.formatPublishDateTime(publishDate));

        TextView topicName = (TextView)view.findViewById(R.id.tv_topic_name);
        topicName.setText(node);



        View topicItem = view.findViewById(R.id.rl_topic_item);
        topicItem.setTag(new TopicEntity(topicId, topicTitle, publishDate, node, usernameStr, avatar));

        topicItem.setOnClickListener(v -> {
            TopicEntity info = (TopicEntity) v.getTag();
            mContext.startActivity(new Intent(mContext, TopicDetailActivity.class).putExtra("topicInfo", info));
        });
    }
}
