package xyz.axlchen.cntvhack.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.axlchen.cntvhack.R;
import xyz.axlchen.cntvhack.adapter.ChannelListAdapter;
import xyz.axlchen.cntvhack.data.entity.ChannelList;
import xyz.axlchen.cntvhack.data.entity.DataWrapper;
import xyz.axlchen.cntvhack.data.entity.ProgramInfo;
import xyz.axlchen.cntvhack.net.service.ChannelService;
import xyz.axlchen.cntvhack.net.service.ProgramService;

public class ChannelListFragment extends BaseFragment {

    private static final String URL = "url";
    private ChannelListAdapter mAdapter;
    private String mListUrl;

    public ChannelListFragment() {
    }

    public static ChannelListFragment newInstance(String listUrl) {
        ChannelListFragment fragment = new ChannelListFragment();
        Bundle args = new Bundle();
        args.putString(URL, listUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mListUrl = getArguments().getString(URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_channel_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new ChannelListAdapter(recyclerView, null);
        recyclerView.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        mRetrofit = mRetrofit.newBuilder().addConverterFactory(GsonConverterFactory.create()).build();
        mRetrofit.create(ChannelService.class).getChannelList(mListUrl).enqueue(new Callback<DataWrapper<ChannelList>>() {
            @Override
            public void onResponse(Call<DataWrapper<ChannelList>> call, Response<DataWrapper<ChannelList>> response) {
                if (response.body() != null && response.body().getData() != null) {
                    setData(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DataWrapper<ChannelList>> call, Throwable t) {

            }
        });
    }

    private void setData(ChannelList channelList) {
        if (channelList.getItems() != null && channelList.getItems().size() > 0) {
            mAdapter.setChannelList(channelList.getItems());
        }
    }
}