package xyz.axlchen.cntvhack.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import retrofit2.Retrofit;
import xyz.axlchen.cntvhack.net.DaggerNetComponent;
import xyz.axlchen.cntvhack.net.NetModule;

public class BaseActivity extends AppCompatActivity {

    @Inject
    protected Retrofit mRetrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build().inject(this);
    }
}
