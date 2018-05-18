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
import xyz.axlchen.cntvhack.adapter.ChannelCategoryAdapter;
import xyz.axlchen.cntvhack.data.entity.ChannelCategory;
import xyz.axlchen.cntvhack.data.entity.DataWrapper;
import xyz.axlchen.cntvhack.net.service.ChannelService;

public class ChannelFragment extends BaseFragment {

    private static final String TAG = "ChannelFragment";
    private TabLayout mTabLayout;
    private ChannelCategoryAdapter mAdapter;

    public ChannelFragment() {
        // Required empty public constructor
    }

    public static ChannelFragment newInstance() {
        ChannelFragment fragment = new ChannelFragment();
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
        return inflater.inflate(R.layout.fragment_channel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(5);
        mAdapter = new ChannelCategoryAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(viewPager, true);
        getData();
    }

    private void getData() {
        mRetrofit.create(ChannelService.class).getChannelCategory().enqueue(new Callback<DataWrapper<ChannelCategory>>() {
            @Override
            public void onResponse(Call<DataWrapper<ChannelCategory>> call, Response<DataWrapper<ChannelCategory>> response) {
                if (response.body() != null && response.body().getData() != null) {
                    setData(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DataWrapper<ChannelCategory>> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    private void setData(ChannelCategory channelCategory) {
        if (channelCategory.getCategory() != null && channelCategory.getCategory().size() > 0) {
            mAdapter.setCategories(channelCategory.getCategory());
        }
    }
}
