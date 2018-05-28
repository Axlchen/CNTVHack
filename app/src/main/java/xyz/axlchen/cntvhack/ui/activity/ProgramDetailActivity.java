package xyz.axlchen.cntvhack.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

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
import xyz.axlchen.cntvhack.listener.AppBarStateChangeListener;
import xyz.axlchen.cntvhack.net.service.ProgramService;

public class ProgramDetailActivity extends BaseActivity {

    public static final String PROGRAM_ID = "program_id";
    private static final String TAG = "ProgramDetailActivity";
    private String mProgramId;
    private ProgramVideoListAdapter mAdapter;
    private TextView mTitle;
    private TextView mDescription;
    private ImageView mIvLogo;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private View mRlInfo;
    private AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_detail);
        initView();
        mProgramId = getIntent().getStringExtra(PROGRAM_ID);
        if (!TextUtils.isEmpty(mProgramId)) {
            getData();
        }
    }

    private void initView() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mRlInfo = findViewById(R.id.rl_info);
        mTitle = findViewById(R.id.tv_title);
        mDescription = findViewById(R.id.tv_description);
        mIvLogo = findViewById(R.id.iv_logo);
        mCollapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        mAppBarLayout = findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    actionBar.setDisplayShowTitleEnabled(false);
                    mCollapsingToolbarLayout.setTitleEnabled(false);
                } else if (state == State.COLLAPSED) {
                    actionBar.setDisplayShowTitleEnabled(true);
                    mCollapsingToolbarLayout.setTitleEnabled(true);
                } else if (state == State.COLLAPSING) {
                    actionBar.setDisplayShowTitleEnabled(false);
                    mCollapsingToolbarLayout.setTitleEnabled(false);
                }
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ProgramVideoListAdapter(recyclerView, null);
        recyclerView.setAdapter(mAdapter);
    }

    private void getData() {
        mRetrofit = mRetrofit.newBuilder().baseUrl("http://media.app.cntvwb.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProgramService programService = mRetrofit.create(ProgramService.class);
        programService.getProgramDetail(mProgramId).enqueue(new Callback<DataWrapper<ProgramDetail>>() {
            @Override
            public void onResponse(Call<DataWrapper<ProgramDetail>> call, Response<DataWrapper<ProgramDetail>> response) {
                if (response.body() != null && response.body().getData() != null) {
                    setDetail(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DataWrapper<ProgramDetail>> call, Throwable t) {
                Log.d(TAG, t.toString());
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

    private void setDetail(ProgramDetail detail) {
        mTitle.setText(detail.getMediaName());
        mCollapsingToolbarLayout.setTitle(detail.getMediaName());
        mDescription.setText(detail.getDescription());
        Glide.with(this)
                .asBitmap()
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(@NonNull Palette palette) {
                                int lightVibrantColor = palette.getLightVibrantColor(mAppBarLayout.getSolidColor());
                                mAppBarLayout.setBackgroundColor(lightVibrantColor);
                            }
                        });
                        return false;
                    }
                })
                .load(detail.getLogoImg())
                .apply(new RequestOptions().circleCrop())
                .into(mIvLogo);
    }

    private void setData(List<ProgramVideoInfo> programVideoList) {
        mAdapter.setProgramVideoList(programVideoList);
    }
}
