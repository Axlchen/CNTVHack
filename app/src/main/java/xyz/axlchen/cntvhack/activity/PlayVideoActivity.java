package xyz.axlchen.cntvhack.activity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.axlchen.cntvhack.R;
import xyz.axlchen.cntvhack.data.entity.ShortVideoDetailInfo;
import xyz.axlchen.cntvhack.listener.CommonOrientationEventListener;
import xyz.axlchen.cntvhack.net.NetworkConfig;
import xyz.axlchen.cntvhack.net.service.ShortVideoService;
import xyz.axlchen.cntvhack.util.MediaSourceHelper;
import xyz.axlchen.cntvhack.util.NetworkUtil;

public class PlayVideoActivity extends BaseActivity {

    public static final String VIDEO_ID = "video_id";
    public static final String TAG = "PlayVideoActivity";

    private String mVideoId;
    private DefaultBandwidthMeter mBandwidthMeter;
    private SimpleExoPlayer mPlayer;
    private CommonOrientationEventListener mOrientationEventListener;
    private MediaSourceHelper mMediaSourceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mVideoId = getIntent().getStringExtra(VIDEO_ID);

        PlayerView playerView = findViewById(R.id.exo_play_view);
        playerView.showController();

        // 1. Create a default TrackSelector
        // Measures bandwidth during playback. Can be null if not required.
        mBandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(mBandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        mPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        mPlayer.addListener(new Player.DefaultEventListener() {
            @Override
            public void onLoadingChanged(boolean isLoading) {
                super.onLoadingChanged(isLoading);
                Log.d(TAG, "onLoadingChanged:" + isLoading);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                super.onPlayerStateChanged(playWhenReady, playbackState);
                Log.d(TAG, "onPlayerStateChanged:" + playbackState);
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                super.onPlayerError(error);
                Log.d(TAG, "onPlayerError:" + error.getMessage());
            }
        });

        playerView.setPlayer(mPlayer);
        getVideoInfo();

        mOrientationEventListener = new CommonOrientationEventListener(this);
        if (mOrientationEventListener.canDetectOrientation()) {
            mOrientationEventListener.enable();
        }
        mMediaSourceHelper = new MediaSourceHelper(this);
        NetworkUtil.getNetworkType(this);
    }

    private void getVideoInfo() {
        if (!TextUtils.isEmpty(mVideoId)) {
            mRetrofit = mRetrofit.newBuilder().baseUrl(NetworkConfig.VDN_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mRetrofit.create(ShortVideoService.class)
                    .getVideoInfo(mVideoId)
                    .enqueue(new Callback<ShortVideoDetailInfo>() {
                        @Override
                        public void onResponse(Call<ShortVideoDetailInfo> call, Response<ShortVideoDetailInfo> response) {
                            if (response.body() != null) {
                                startPlay(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<ShortVideoDetailInfo> call, Throwable t) {

                        }
                    });
        }
    }

    private void startPlay(ShortVideoDetailInfo shortVideoDetailInfo) {
        MediaSource mediaSource = mMediaSourceHelper.buildMediaSource(Uri.parse(shortVideoDetailInfo.getHlsUrl()), null, null, null);

        // Prepare the player with the source.
        mPlayer.prepare(mediaSource);
        mPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPlayer != null) {
            mPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer != null) {
            mPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        if (mOrientationEventListener != null) {
            mOrientationEventListener.disable();
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}