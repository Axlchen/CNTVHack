package xyz.axlchen.cntvhack.core;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import xyz.axlchen.cntvhack.data.entity.UrlConfig;
import xyz.axlchen.cntvhack.net.service.ConfigService;
import xyz.axlchen.cntvhack.util.ContextUtil;
import xyz.axlchen.cntvhack.util.SPUtil;

public final class Config {

    private static final String TAG = "Config";
    private static final String URL_CONFIG_KEY = "url_config";
    private static Map<String, String> mUrlConfigSet;

    public static String getUrlByName(String key) {
        if (mUrlConfigSet == null) {
            loadUrlConfig();
        }
        if (mUrlConfigSet != null) {
            return mUrlConfigSet.get(key);
        } else {
            return null;
        }
    }

    private static void loadUrlConfig() {
        //get it from sp
        getUrlConfigFromSp();
        //get from network
        new Retrofit.Builder().build().create(ConfigService.class)
                .getUrlConfig().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    SPUtil.putString(ContextUtil.getApplication(), URL_CONFIG_KEY, response.body());
                    getUrlConfigFromSp();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    private static void getUrlConfigFromSp() {
        String urlJson = SPUtil.getString(ContextUtil.getApplication(), URL_CONFIG_KEY, null);
        if (!TextUtils.isEmpty(urlJson)) {
            UrlConfig urlConfig = new Gson().fromJson(urlJson, UrlConfig.class);
            if (urlConfig.getData() != null && urlConfig.getData().size() > 0) {
                if (mUrlConfigSet == null) {
                    mUrlConfigSet = new HashMap<>();
                }
                for (UrlConfig.Item item : urlConfig.getData()) {
                    mUrlConfigSet.put(item.getTitle(), item.getUrl());
                }
            }
        }
    }
}
