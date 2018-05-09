package xyz.axlchen.cntvhack.util;

import android.app.Application;

public final class ContextUtil {

    private static Application sApplication;

    private ContextUtil() {

    }

    public static Application getApplication() {
        if (sApplication == null) {
            throw new RuntimeException("please call setApplication() at Application");
        }
        return sApplication;
    }

    public static void setApplication(Application application) {
        sApplication = application;
    }
}
