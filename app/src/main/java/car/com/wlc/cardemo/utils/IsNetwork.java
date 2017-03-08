package car.com.wlc.cardemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2016/11/26.
 */

public class IsNetwork {

    public static boolean isNetworkConnected(Context context) {
             if (context != null) {
                    ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                             .getSystemService(Context.CONNECTIVITY_SERVICE);
                 NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                                 .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                 NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                     if (mNetworkInfo != null ||mWiFiNetworkInfo != null) {
                         if (mNetworkInfo.isAvailable() || mWiFiNetworkInfo.isAvailable())
                             return true;
                         }
                 }
             return false;
         }
}
