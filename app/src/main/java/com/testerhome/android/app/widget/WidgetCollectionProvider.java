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

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            RemoteViews remoteViews = initViews(context, appWidgetManager, widgetId);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);

            appWidgetManager.notifyAppWidgetViewDataChanged(widgetId, R.id.widgetCollectionList);
        }
    }

    private RemoteViews initViews(Context context,
                                  AppWidgetManager widgetManager,
                                  int widgetId) {

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews mView = new RemoteViews(context.getPackageName(),
                R.layout.favorite_collection_widget);

        mView.setTextViewText(R.id.tv_topic_title, "My favorite");

        mView.setRemoteAdapter(widgetId, intent);

        return mView;
    }

}
