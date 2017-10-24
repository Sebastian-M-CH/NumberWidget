package ch.swisstechlab.mynumberwidget.access;

import android.Manifest;
import android.content.Context;
import android.telephony.TelephonyManager;
import static android.Manifest.permission.READ_PHONE_STATE;

import ch.swisstechlab.mynumberwidget.permissions.PermissionHandler;

/**
 * Created by Sebastian on 05.06.2017.
 */
public class PhoneNumberAccess {

    PermissionHandler permissionHandler;
    TelephonyManager tMgr;
    Context context;

    public PhoneNumberAccess(Context context) {
        this.context = context;
        this.tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        this.permissionHandler = PermissionHandler.getInstance(context);
    }

    public String getPhoneNumber() {
        boolean hasPermission = permissionHandler.hasPermission(READ_PHONE_STATE);
        if(hasPermission) {
            return getPhoneNumberFromTelephonyManager();
        }
        return "please open the app";
    }

    public boolean checkCompatibility () {
        if(hasPermission(READ_PHONE_STATE))
            return !getPhoneNumberFromTelephonyManager().isEmpty();

        throw new IllegalStateException();
    }

    private String getPhoneNumberFromTelephonyManager() {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
    }

    private boolean hasPermission(String permission) {
       return permissionHandler.hasPermission(permission);
    }

}
