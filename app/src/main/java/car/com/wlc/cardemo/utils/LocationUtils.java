package car.com.wlc.cardemo.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import car.com.wlc.cardemo.utils.permission.MPermissionUtils;
import car.com.wlc.cardemo.utils.permission.PermissionsChecker;

/**
 * Created by Administrator on 2017/3/2.
 */

public class LocationUtils {
    /**
     * @param permission
     * @param context
     */
    public static void setPermission(String permission, final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            String[] strings = {permission};
            PermissionsChecker permissionsChecker = new PermissionsChecker(context);
            boolean b = permissionsChecker.lacksPermissions(strings);

            if (!b) {
                MPermissionUtils.requestPermissionsResult((Activity) context, 1, strings, new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied() {
                        Log.i("info", "onPermissionGranted: " + "授权失败");
                        MPermissionUtils.showTipsDialog(context);
                    }
                });
            }
        }
    }
}
//
//                MPermissionUtils.requestPermissionsResult(context, 1, strings, onPermissionListener {
//                    @Override
//                    public void onPermissionGranted() {
//                        Log.i("info", "onPermissionGranted: " + "授权成功");
//                    }
//
//                    @Override
//                    public void onPermissionDenied() {
//                        Log.i("info", "onPermissionGranted: " + "授权失败");
//                        MPermissionUtils.showTipsDialog(context);
//                    }
//
//                });
