package com.testerhome.android.app.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.testerhome.android.app.R;


/**
 * Created by Bin Li on 2016/7/6.
 */

public class WidgetCollectionProvider extends AppWidgetProvider {

    public static final String ACTION_REFRESH_MANUAL = "testerhome.action.APPWIDGET_REFRESH_MANUAL";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_REFRESH_MANUAL.equals(intent.getAction())){
            FavoriteRemoteViews remoteViews = new FavoriteRemoteViews(context);
            remoteViews.loading();
            remoteViews.notifyAppWidgetViewDataChanged();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        FavoriteRemoteViews favoriteRemoteViews = new FavoriteRemoteViews(context);
        favoriteRemoteViews.setOnRefreshClickPendingIntent();
        favoriteRemoteViews.bindListViewAdapter();
        appWidgetManager.updateAppWidget(appWidgetIds, favoriteRemoteViews);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews initViews(Context context,
                                  AppWidgetManager widgetManager,
                                  int widgetId) {

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews mView = new RemoteViews(context.getPackageName(),
                R.layout.favorite_collection_widget);

        mView.setTextViewText(R.id.tv_topic_title, context.getString(R.string.my_favorite));

        mView.setRemoteAdapter(widgetId, intent);

        return mView;
    }

}
