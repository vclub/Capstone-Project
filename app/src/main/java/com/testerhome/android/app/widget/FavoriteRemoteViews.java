package com.testerhome.android.app.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RemoteViews;

import com.testerhome.android.app.R;
import com.testerhome.android.app.provider.favorite.FavoriteCursor;

/**
 * Created by Bin Li on 2016/7/9.
 */

public class FavoriteRemoteViews extends RemoteViews {

    private Context mContext;
    private AppWidgetManager mAppWidgetManager;
    private int[] mAppWidgetIds;

    public FavoriteRemoteViews(Context context){
        super(context.getPackageName(), R.layout.favorite_collection_widget);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
        this.mAppWidgetManager = AppWidgetManager.getInstance(mContext);
        this.mAppWidgetIds = getAppWidgetIds();
    }

    private Class<? extends AppWidgetProvider> getappWidgetProvider(){
        return WidgetCollectionProvider.class;
    }

    private Intent getProviderIntent(){
        return new Intent(mContext, getappWidgetProvider());
    }

    public int[] getAppWidgetIds(){
        ComponentName provider = new ComponentName(mContext, getappWidgetProvider());
        return mAppWidgetManager.getAppWidgetIds(provider);
    }

    public void loading() {
        setViewVisibility(R.id.widget_loading, View.VISIBLE);
        setViewVisibility(R.id.widget_refresh, View.GONE);
        mAppWidgetManager.updateAppWidget(mAppWidgetIds, this);
    }

    public void loadComplate() {
        setViewVisibility(R.id.widget_loading, View.GONE);
        setViewVisibility(R.id.widget_refresh, View.VISIBLE);
        mAppWidgetManager.updateAppWidget(mAppWidgetIds, this);
    }

    public void setOnRefreshClickPendingIntent(){
        Intent intent = getProviderIntent();
        intent.setAction(WidgetCollectionProvider.ACTION_REFRESH_MANUAL);
        PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        setOnClickPendingIntent(R.id.widget_refresh, refreshPendingIntent);
    }

    public void bindListViewAdapter() {
        int listViewResId = R.id.widgetCollectionList;
        Intent serviceIntent = new Intent(mContext, WidgetService.class);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        setRemoteAdapter(listViewResId, serviceIntent);

        Intent listItemIntent = getProviderIntent();
        listItemIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, listViewResId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, listItemIntent, 0);

        setPendingIntentTemplate(listViewResId, pendingIntent);
    }

    public void notifyAppWidgetViewDataChanged() {
        int[] appIds = getAppWidgetIds();
        mAppWidgetManager.notifyAppWidgetViewDataChanged(appIds, R.id.widgetCollectionList);
    }

    public RemoteViews applyFavoriteView(final FavoriteCursor favoriteCursor){

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.list_item_widget_topic);
        views.setTextViewText(R.id.tv_topic_title, favoriteCursor.getTitle());
        return views;
    }
}
