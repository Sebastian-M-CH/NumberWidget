package ch.swisstechlab.mynumberwidget.view;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import ch.swisstechlab.mynumberwidget.R;
import ch.swisstechlab.mynumberwidget.access.PhoneNumberAccess;

/**
 * Created by Sebastian on 05.06.2017.
 */
public class DefaultWidgetProvider extends AppWidgetProvider {

    PhoneNumberAccess phoneNumberRepository;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        phoneNumberRepository = new PhoneNumberAccess(context);
        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.defaultwidgetlayout);
            remoteViews.setTextViewText(R.id.textView, phoneNumberRepository.getPhoneNumber());
            Intent intent = new Intent(context, DefaultWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}