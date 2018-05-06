package xyz.axlchen.cntvhack.net;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    @Provides
    @Singleton
    public static Retrofit provideRetrofit() {
        return new Retrofit.Builder().baseUrl(NetworkConfig.API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
