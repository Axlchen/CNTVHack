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
import xyz.axlchen.cntvhack.adapter.ProgramListAdapter;
import xyz.axlchen.cntvhack.data.entity.DataWrapper;
import xyz.axlchen.cntvhack.data.entity.ProgramInfo;
import xyz.axlchen.cntvhack.net.service.ProgramService;

public class ProgramListFragment extends BaseFragment {

    private static final String CHANNEL_ID = "channel_id";
    private static final String TAG = "ProgramListFragment";
    private String mChannelId;
    private ProgramListAdapter mAdapter;

    public ProgramListFragment() {
        // Required empty public constructor
    }

    public static ProgramListFragment newInstance(String channelId) {
        ProgramListFragment fragment = new ProgramListFragment();
        Bundle args = new Bundle();
        args.putString(CHANNEL_ID, channelId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mChannelId = getArguments().getString(CHANNEL_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_program_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new ProgramListAdapter(recyclerView, null);
        recyclerView.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        mRetrofit = mRetrofit.newBuilder().baseUrl("http://media.app.cntvwb.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRetrofit.create(ProgramService.class).getProgramList(mChannelId, 1, 20).enqueue(new Callback<DataWrapper<List<ProgramInfo>>>() {
            @Override
            public void onResponse(Call<DataWrapper<List<ProgramInfo>>> call, Response<DataWrapper<List<ProgramInfo>>> response) {
                if (response.body() != null && response.body().getData() != null && response.body().getData().size() > 0) {
                    setData(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DataWrapper<List<ProgramInfo>>> call, Throwable t) {

            }
        });
    }

    private void setData(List<ProgramInfo> programInfoList) {
        mAdapter.setProgramList(programInfoList);
    }
}
