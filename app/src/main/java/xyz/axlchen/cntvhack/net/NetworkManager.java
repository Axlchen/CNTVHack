package xyz.axlchen.cntvhack.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NetworkManager {

    private static Retrofit sRetrofit;

    private NetworkManager() {

    }

    public static Retrofit getClient() {
        if (sRetrofit == null) {
            synchronized (NetworkManager.class) {
                if (sRetrofit == null) {
                    sRetrofit = new Retrofit.Builder().baseUrl(NetworkConfig.API_HOST)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return sRetrofit;
    }
}