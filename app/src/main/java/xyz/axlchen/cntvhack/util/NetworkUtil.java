package xyz.axlchen.cntvhack.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkUtil {

    public static final int NO_NETWORK = 0x01;
    public static final int MOBILE_NETWORK = 0x02;
    public static final int WIFI_NETWORK = 0x03;
    private static final String TAG = "NetworkUtil";

    private NetworkUtil() {

    }

    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return NO_NETWORK;
        }
        if (activeNetworkInfo.getState() != NetworkInfo.State.CONNECTED) {
            return NO_NETWORK;
        }
        int type = activeNetworkInfo.getType();
        if (type == ConnectivityManager.TYPE_MOBILE) {
            return MOBILE_NETWORK;
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            return WIFI_NETWORK;
        }
        return NO_NETWORK;
    }
}
