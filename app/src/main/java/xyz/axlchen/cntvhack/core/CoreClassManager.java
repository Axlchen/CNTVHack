package xyz.axlchen.cntvhack.core;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.axlchen.cntvhack.net.NetworkConfig;

public final class CoreClassManager {
    private static Retrofit sGsonRetrofit;
    private static Retrofit sScalarRetrofit;

    private CoreClassManager() {

    }

    public static Retrofit getGsonRetrofit() {
        if (sGsonRetrofit == null) {
            synchronized (CoreClassManager.class) {
                if (sGsonRetrofit == null) {
                    sGsonRetrofit = new Retrofit.Builder().baseUrl(NetworkConfig.API_HOST)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return sGsonRetrofit;
    }

    public static Retrofit getScalarRetrofit() {
        if (sScalarRetrofit == null) {
            synchronized (CoreClassManager.class) {
                if (sScalarRetrofit == null) {
                    sScalarRetrofit = new Retrofit.Builder().baseUrl(NetworkConfig.API_HOST)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return sScalarRetrofit;
    }
}
