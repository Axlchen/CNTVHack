package xyz.axlchen.cntvhack.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.axlchen.cntvhack.R;
import xyz.axlchen.cntvhack.adapter.ShortVideoCategoryAdapter;
import xyz.axlchen.cntvhack.data.entity.TotalShortVideoList;
import xyz.axlchen.cntvhack.net.service.ShortVideoService;

public class ShortVideoFragment extends BaseFragment {

    private static final String TAG = "ShortVideoFragment";

    private TabLayout mTabLayout;
    private ShortVideoCategoryAdapter mAdapter;

    public ShortVideoFragment() {
        // Required empty public constructor
    }

    public static ShortVideoFragment newInstance() {
        ShortVideoFragment fragment = new ShortVideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_short_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(5);
        mAdapter = new ShortVideoCategoryAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(viewPager, true);
        getData();
    }

    private void getData() {
        mRetrofit.create(ShortVideoService.class)
                .getTotal(1, 20)
                .enqueue(new Callback<TotalShortVideoList>() {
                    @Override
                    public void onResponse(Call<TotalShortVideoList> call, Response<TotalShortVideoList> response) {
                        if (response.body() != null) {
                            setData(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<TotalShortVideoList> call, Throwable t) {
                        Log.d(TAG, t.toString());
                    }
                });
    }

    private void setData(TotalShortVideoList totalVideoList) {
        if (totalVideoList.getItemList() != null && totalVideoList.getItemList().size() > 0) {
            mAdapter.setCategories(totalVideoList.getItemList());
        }
    }
}
