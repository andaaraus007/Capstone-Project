package com.udacity.a49erscomrss.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.core.app.TaskStackBuilder;

import com.udacity.a49erscomrss.R;
import com.udacity.a49erscomrss.activity.DetailedActivity;
import com.udacity.a49erscomrss.activity.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class A49ersRSSWidget extends AppWidgetProvider {
    public static final String EXTRA_FROM_WIDGET = "from_widget";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(
                context.getPackageName(),
                R.layout.a49ers_rss_widget
        );

        Intent titleIntent = new Intent(context, MainActivity.class);
        PendingIntent titlePendingIntent = PendingIntent.getActivity(context, 0, titleIntent, 0);
        views.setOnClickPendingIntent(R.id.widgetTitleLabel, titlePendingIntent);

        Intent intent = new Intent(context, A49ersRssAppRemoteViewsService.class);
        views.setRemoteAdapter(R.id.a49ers_rss_app_listview, intent);

        Intent clickIntentTemplate = new Intent(context, DetailedActivity.class);
        PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(clickIntentTemplate)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.a49ers_rss_app_listview, clickPendingIntentTemplate);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action != null && action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, A49ersRSSWidget.class);

            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.a49ers_rss_app_listview);
        }
        super.onReceive(context, intent);
    }

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, A49ersRSSWidget.class));
        context.sendBroadcast(intent);
    }
}

