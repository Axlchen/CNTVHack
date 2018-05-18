package xyz.axlchen.cntvhack;

import android.app.Application;

import xyz.axlchen.cntvhack.core.AppInitializer;
import xyz.axlchen.cntvhack.util.ContextUtil;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtil.setApplication(this);
        AppInitializer.init();
    }
}
