package ch.swisstechlab.mynumberwidget.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Sebastian on 11.05.2017.
 */
public class PermissionHandler {

    private static Context context;
    private static PermissionHandler instance = null;
    private PermissionHandler(Context context) {
        this.context = context;
    }

    public static PermissionHandler getInstance(Context context) {
        if(instance == null) {
            instance = new PermissionHandler(context);
        }
        return instance;
    }

    public boolean requestPermission(Activity activity, String permission,int requestcode) {
        if (!hasPermission(permission)) {
            activity.requestPermissions(new String[]{permission},
                    requestcode);
        }
        return hasPermission(permission);
    }

    public boolean hasPermission(String permission) {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }
}
