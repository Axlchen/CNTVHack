package xyz.axlchen.cntvhack.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.axlchen.cntvhack.R;
import xyz.axlchen.cntvhack.adapter.ProgramCategoryAdapter;
import xyz.axlchen.cntvhack.core.Config;
import xyz.axlchen.cntvhack.data.entity.DataWrapper;
import xyz.axlchen.cntvhack.data.entity.ProgramCategory;
import xyz.axlchen.cntvhack.net.service.ProgramService;

public class ProgramFragment extends BaseFragment {

    private static final String TAG = "ProgramFragment";
    private static final String URL_KEY = "media_lanmuList_url";
    private TabLayout mTabLayout;
    private ProgramCategoryAdapter mAdapter;

    public ProgramFragment() {
        // Required empty public constructor
    }

    public static ProgramFragment newInstance() {
        ProgramFragment fragment = new ProgramFragment();
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
        return inflater.inflate(R.layout.fragment_program, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(5);
        mAdapter = new ProgramCategoryAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(viewPager, true);
        getData();
    }

    private void getData() {
        String url = Config.getUrlByName(URL_KEY);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        mRetrofit.create(ProgramService.class).getProgramCategory(url).enqueue(new Callback<DataWrapper<ProgramCategory>>() {
            @Override
            public void onResponse(Call<DataWrapper<ProgramCategory>> call, Response<DataWrapper<ProgramCategory>> response) {
                if (response.body() != null && response.body().getData() != null) {
                    setData(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DataWrapper<ProgramCategory>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    private void setData(ProgramCategory programCategory) {
        if (programCategory.getCategory() != null && programCategory.getCategory().size() > 0) {
            mAdapter.setCategories(programCategory.getCategory());
        }
    }

}
