package com.udacity.a49erscomrss.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.a49erscomrss.R;
import com.udacity.a49erscomrss.database.AppDatabase;
import com.udacity.a49erscomrss.database.RssItemEntry;
import com.udacity.a49erscomrss.model.RssItem;

import java.util.List;

public class A49ersRssAppRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = "[Widget]" + A49ersRssAppRemoteViewFactory.class.getSimpleName();
    
    List<RssItemEntry> mRssListEntries;
    private final Context mContext;
    private final AppDatabase mDatabase;

    public A49ersRssAppRemoteViewFactory(Context applicationContext) {
        Log.d(TAG, "A49ersRssAppRemoteViewFactory: ");
        mContext = applicationContext;
        mDatabase = AppDatabase.getInstance(applicationContext);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDataSetChanged() {
        mRssListEntries = mDatabase.rssItemDao().loadAllRssItemsForWidget();
        Log.d(TAG, "onDataSetChanged: list size is " + mRssListEntries.size());
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public int getCount() {
        int count = (mRssListEntries != null) ? mRssListEntries.size() : 0;
        Log.d(TAG, "getCount: " + count);
        return count;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(TAG, "getViewAt: position is " + position);
        if (position == AdapterView.INVALID_POSITION ||
                mRssListEntries == null || mRssListEntries.size() < position) {
            return null;
        }

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.a49ers_rss_widget_list_item);
        RssItemEntry rssItemEntry = mRssListEntries.get(position);
        String title = rssItemEntry.getTitle();
        remoteViews.setTextViewText(R.id.widgetItemTaskNameLabel, title);

        RssItem rssItem = new RssItem(
                rssItemEntry.getGuid(),
                rssItemEntry.getTitle(),
                rssItemEntry.getDescription(),
                rssItemEntry.getLink(),
                true
        );
        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(String.valueOf(R.string.title_activity_detailed), rssItem);
        fillInIntent.putExtra(A49ersRSSWidget.EXTRA_FROM_WIDGET, "widget");
        remoteViews.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        Log.d(TAG, "getLoadingView: ");
        return null;
    }

    @Override
    public int getViewTypeCount() {
        Log.d(TAG, "getViewTypeCount: ");
        return 1;
    }

    @Override
    public long getItemId(int i) {
        Log.d(TAG, "getItemId: ");
        return i;
    }

    @Override
    public boolean hasStableIds() {
        Log.d(TAG, "hasStableIds: ");
        return true;
    }
}
