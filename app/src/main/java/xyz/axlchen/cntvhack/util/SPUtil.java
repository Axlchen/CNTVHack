package xyz.axlchen.cntvhack.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

public final class SPUtil {

    private static final String FILE_NAME = "default_config";

    private SPUtil() {

    }

    public static void putString(Context context, String key, @Nullable String value) {
        getSharedPreferences(context).edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, @Nullable String defValue) {
        return getSharedPreferences(context).getString(key, defValue);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }
}
