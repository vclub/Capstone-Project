package com.testerhome.android.app.widget;

import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.testerhome.android.app.R;
import com.testerhome.android.app.models.TopicEntity;
import com.testerhome.android.app.provider.favorite.FavoriteColumns;

/**
 * Created by Bin Li on 2016/7/6.
 */
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory();
    }


    private class ListRemoteViewsFactory implements RemoteViewsFactory {

        private Cursor mCursor;

        @Override
        public void onCreate() {
            mCursor = getContentResolver().query(
                    FavoriteColumns.CONTENT_URI,
                    FavoriteColumns.ALL_COLUMNS,
                    null,
                    null,
                    null
            );
        }

        @Override
        public void onDataSetChanged() {
            mCursor.requery();
        }

        @Override
        public void onDestroy() {
            mCursor.close();
            mCursor = null;
        }

        @Override
        public int getCount() {
            return mCursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            mCursor.moveToPosition(i);

            long topicId = mCursor.getLong(0);
            String topicTitle = mCursor.getString(mCursor.getColumnIndex(FavoriteColumns.TITLE));
            String usernameStr = mCursor.getString(mCursor.getColumnIndex(FavoriteColumns.NAME));
            String publishDate = mCursor.getString(mCursor.getColumnIndex(FavoriteColumns.CREATE_AT));
            String avatar = mCursor.getString(mCursor.getColumnIndex(FavoriteColumns.AVATAR_URL));
            String node = mCursor.getString(mCursor.getColumnIndex(FavoriteColumns.NODE_NAME));

            RemoteViews views = new RemoteViews(getPackageName(), R.layout.list_item_widget_topic);
            views.setTextViewText(R.id.tv_topic_title, topicTitle);

            Intent intent = new Intent();
            intent.putExtra("topicInfo", new TopicEntity(topicId, topicTitle, publishDate, node, usernameStr, avatar));
            views.setOnClickFillInIntent(R.id.rl_topic_item, intent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
