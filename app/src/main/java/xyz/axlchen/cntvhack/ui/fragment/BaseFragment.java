package xyz.axlchen.cntvhack.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import retrofit2.Retrofit;
import xyz.axlchen.cntvhack.core.CoreClassManager;

public class BaseFragment extends Fragment {

    protected Retrofit mRetrofit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRetrofit = CoreClassManager.getGsonRetrofit();
    }
}
