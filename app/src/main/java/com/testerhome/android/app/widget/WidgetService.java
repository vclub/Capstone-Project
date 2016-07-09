package com.testerhome.android.app.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.testerhome.android.app.provider.favorite.FavoriteColumns;
import com.testerhome.android.app.provider.favorite.FavoriteCursor;

/**
 * Created by Bin Li on 2016/7/6.
 */
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }


    private class ListRemoteViewsFactory implements RemoteViewsFactory {

        private Context mContext;
        private Cursor mCursor;

        public ListRemoteViewsFactory(Context context, Intent intent) {
            this.mContext = context;
        }

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
            if (mCursor == null){
                return 0;
            }
            return mCursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (getCount() == 0){
                return null;
            }
            mCursor.moveToPosition(position);

//            long topicId = mCursor.getLong(0);
//            String topicTitle = mCursor.getString(mCursor.getColumnIndex(FavoriteColumns.TITLE));
//            String usernameStr = mCursor.getString(mCursor.getColumnIndex(FavoriteColumns.NAME));
//            String publishDate = mCursor.getString(mCursor.getColumnIndex(FavoriteColumns.CREATE_AT));
//            String avatar = mCursor.getString(mCursor.getColumnIndex(FavoriteColumns.AVATAR_URL));
//            String node = mCursor.getString(mCursor.getColumnIndex(FavoriteColumns.NODE_NAME));
//
//            RemoteViews views = new RemoteViews(getPackageName(), R.layout.list_item_widget_topic);
//            views.setTextViewText(R.id.tv_topic_title, topicTitle);
//
//            Intent intent = new Intent();
//            intent.putExtra("topicInfo", new TopicEntity(topicId, topicTitle, publishDate, node, usernameStr, avatar));
//            views.setOnClickFillInIntent(R.id.rl_topic_item, intent);

            FavoriteRemoteViews remoteViews = new FavoriteRemoteViews(mContext);
            remoteViews.loadComplate();

            return remoteViews.applyFavoriteView(new FavoriteCursor(mCursor));
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
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
