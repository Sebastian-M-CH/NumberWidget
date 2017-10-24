package ch.swisstechlab.mynumberwidget.controller;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import ch.swisstechlab.mynumberwidget.access.PhoneNumberAccess;
import ch.swisstechlab.mynumberwidget.permissions.PermissionHandler;
import ch.swisstechlab.mynumberwidget.view.DefaultWidgetProvider;
import ch.swisstechlab.mynumberwidget.view.MainActivity;

/**
 * Created by Sebastian on 10.08.2017.
 */
public class MainController {

    PermissionHandler permissionHandler;
    PhoneNumberAccess phoneNumberAccess;
    MainActivity activity;

    public MainController(MainActivity activity) {
        this.activity = activity;
        this.permissionHandler = PermissionHandler.getInstance(activity);
        this.phoneNumberAccess = new PhoneNumberAccess(activity.getBaseContext());
    }

    public boolean requestPermission (String permission,int requestcode) {
        return permissionHandler.requestPermission(activity, permission, requestcode);
    }

    public boolean hasPermission (String permission) {
        return permissionHandler.hasPermission(permission);
    }

    public boolean isCompatible() {
        return phoneNumberAccess.checkCompatibility();
    }

    public String getPhoneNumber() {
        return phoneNumberAccess.getPhoneNumber();
    }

    public void updateWidget() {
        Context context = activity.getApplicationContext();
        ComponentName name = new ComponentName(context, DefaultWidgetProvider.class);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
        Intent intent = new Intent(activity, DefaultWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        activity.sendBroadcast(intent);
    }
}
