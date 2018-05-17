package xyz.axlchen.cntvhack.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.axlchen.cntvhack.R;
import xyz.axlchen.cntvhack.adapter.ShortVideoListAdapter;
import xyz.axlchen.cntvhack.data.entity.ShortVideoList;
import xyz.axlchen.cntvhack.net.service.ShortVideoService;


public class ShortVideoListFragment extends BaseFragment {

    private static final String CATEGORY_ID = "category_id";
    private static final String TAG = "ShortVideoListFragment";
    private String mCategoryId;
    private ShortVideoListAdapter mAdapter;

    public ShortVideoListFragment() {
        // Required empty public constructor
    }

    public static ShortVideoListFragment newInstance(String categoryId) {
        ShortVideoListFragment fragment = new ShortVideoListFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoryId = getArguments().getString(CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_short_video_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new ShortVideoListAdapter(recyclerView,null);
        recyclerView.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        mRetrofit.create(ShortVideoService.class)
                .getShortVideoListByCategory(mCategoryId, 1, 20)
                .enqueue(new Callback<ShortVideoList>() {
                    @Override
                    public void onResponse(Call<ShortVideoList> call, Response<ShortVideoList> response) {
                        if (response.body() != null) {
                            setData(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ShortVideoList> call, Throwable t) {
                        Log.d(TAG, t.toString());
                    }
                });
    }

    private void setData(ShortVideoList shortVideoList) {
        mAdapter.setVideoList(shortVideoList.getVideoList());
    }
}