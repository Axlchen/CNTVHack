package xyz.axlchen.cntvhack.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import retrofit2.Retrofit;
import xyz.axlchen.cntvhack.net.DaggerNetComponent;
import xyz.axlchen.cntvhack.net.NetModule;

public class BaseFragment extends Fragment {
    @Inject
    protected Retrofit mRetrofit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerNetComponent.builder().netModule(new NetModule()).build().inject(this);
    }

}
