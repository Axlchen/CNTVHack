package xyz.axlchen.cntvhack.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.axlchen.cntvhack.R;
import xyz.axlchen.cntvhack.adapter.ProgramVideoListAdapter;
import xyz.axlchen.cntvhack.data.entity.DataWrapper;
import xyz.axlchen.cntvhack.data.entity.ProgramDetail;
import xyz.axlchen.cntvhack.data.entity.ProgramVideoInfo;
import xyz.axlchen.cntvhack.net.service.ProgramService;

public class ProgramDetailActivity extends BaseActivity {

    public static final String PROGRAM_ID = "program_id";
    private String mProgramId;
    private static final String TAG = "ProgramDetailActivity";
    private ProgramVideoListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_detail);
        mProgramId = getIntent().getStringExtra(PROGRAM_ID);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ProgramVideoListAdapter(recyclerView, null);
        recyclerView.setAdapter(mAdapter);
        if (!TextUtils.isEmpty(mProgramId)) {
            getData();
        }
    }

    private void getData() {
        mRetrofit = mRetrofit.newBuilder().baseUrl("http://media.app.cntvwb.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProgramService programService = mRetrofit.create(ProgramService.class);
        programService.getProgramDetail(mProgramId).enqueue(new Callback<DataWrapper<ProgramDetail>>() {
            @Override
            public void onResponse(Call<DataWrapper<ProgramDetail>> call, Response<DataWrapper<ProgramDetail>> response) {

            }

            @Override
            public void onFailure(Call<DataWrapper<ProgramDetail>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        programService.getProgramVideoList(mProgramId, 1, 20).enqueue(new Callback<DataWrapper<List<ProgramVideoInfo>>>() {
            @Override
            public void onResponse(Call<DataWrapper<List<ProgramVideoInfo>>> call, Response<DataWrapper<List<ProgramVideoInfo>>> response) {
                if (response.body() != null && response.body().getData() != null && response.body().getData().size() > 0) {
                    setData(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DataWrapper<List<ProgramVideoInfo>>> call, Throwable t) {

            }
        });
    }

    private void setData(List<ProgramVideoInfo> programVideoList) {
        mAdapter.setProgramVideoList(programVideoList);
    }
}
