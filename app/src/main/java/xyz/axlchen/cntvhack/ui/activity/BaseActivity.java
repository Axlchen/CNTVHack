package xyz.axlchen.cntvhack.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import retrofit2.Retrofit;
import xyz.axlchen.cntvhack.core.CoreClassManager;

public class BaseActivity extends AppCompatActivity {

    protected Retrofit mRetrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRetrofit = CoreClassManager.getGsonRetrofit();
    }
}
