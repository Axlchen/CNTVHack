package xyz.axlchen.cntvhack.ui.activity;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
        mTitle = findViewById(R.id.tv_title);
        mDescription = findViewById(R.id.tv_description);
        mIvLogo = findViewById(R.id.iv_logo);
        mCollapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        mAppBarLayout = findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    setStatusBar(true, 0);
                    actionBar.setDisplayShowTitleEnabled(false);
                    mCollapsingToolbarLayout.setTitleEnabled(false);
                } else if (state == State.COLLAPSED) {
                    setStatusBar(false, android.R.color.white);
                    actionBar.setDisplayShowTitleEnabled(true);
                    mCollapsingToolbarLayout.setTitleEnabled(true);
                } else if (state == State.COLLAPSING) {
                    setStatusBar(true, 0);
                    actionBar.setDisplayShowTitleEnabled(false);
                    mCollapsingToolbarLayout.setTitleEnabled(false);
                }
            }
        });
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ProgramVideoListAdapter(recyclerView, null);
        recyclerView.setAdapter(mAdapter);
        SwipeRefreshLayout refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(@NonNull SwipeRefreshLayout parent, @Nullable View child) {
                return recyclerView.canScrollVertically(-1);
            }
        });
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
        mDescription.setText(detail.getDescription());
        mCollapsingToolbarLayout.setTitle(detail.getMediaName());
        Glide.with(this)
                .asBitmap()
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        mTitle.setVisibility(View.VISIBLE);
                        mDescription.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(@NonNull Palette palette) {
                                Palette.Swatch swatch = palette.getLightVibrantSwatch();
                                if (swatch != null) {
                                    int textColor = swatch.getTitleTextColor();
                                    mTitle.setTextColor(textColor);
                                    mDescription.setTextColor(textColor);
                                }
                                mTitle.setVisibility(View.VISIBLE);
                                mDescription.setVisibility(View.VISIBLE);

                                int background = getResources().getColor(android.R.color.darker_gray);
                                int lightVibrantColor = palette.getLightVibrantColor(background);
                                ValueAnimator valueAnimator = ValueAnimator
                                        .ofArgb(background, lightVibrantColor)
                                        .setDuration(600);
                                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        int animatedValue = (int) animation.getAnimatedValue();
                                        Log.d(TAG, Color.red(animatedValue) + " " + Color.green(animatedValue) + " "
                                                + Color.blue(animatedValue));
                                        mAppBarLayout.setBackgroundColor(animatedValue);
                                    }
                                });
                                valueAnimator.start();
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

    private void setStatusBar(boolean translucent, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (translucent) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().getDecorView().setSystemUiVisibility(0);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(getResources().getColor(color));
            }
        }
    }
}