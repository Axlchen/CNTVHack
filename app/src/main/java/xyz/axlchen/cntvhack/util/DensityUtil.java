package xyz.axlchen.cntvhack.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class DensityUtil {

    public static final float XXHDPI = 2f;

    private DensityUtil() {

    }

    public static float getDensity(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.density;
    }
}
