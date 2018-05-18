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
import xyz.axlchen.cntvhack.data.entity.ChannelLiveInfo;
import xyz.axlchen.cntvhack.listener.CommonOrientationEventListener;
import xyz.axlchen.cntvhack.net.NetworkConfig;
import xyz.axlchen.cntvhack.net.service.LiveService;
import xyz.axlchen.cntvhack.util.MediaSourceHelper;

public class ChannelLiveActivity extends BaseActivity {

    public static final String CHANNEL_ID = "channel_id";
    private static final String TAG = "ChannelLiveActivity";

    private String mChannelId;
    private DefaultBandwidthMeter mBandwidthMeter;
    private SimpleExoPlayer mPlayer;
    private CommonOrientationEventListener mOrientationEventListener;
    private MediaSourceHelper mMediaSourceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mChannelId = getIntent().getStringExtra(CHANNEL_ID);

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

        mOrientationEventListener = new CommonOrientationEventListener(this);
        if (mOrientationEventListener.canDetectOrientation()) {
            mOrientationEventListener.enable();
        }
        mMediaSourceHelper = new MediaSourceHelper(this);
        getLiveInfo();
    }

    private void getLiveInfo() {
        if (!TextUtils.isEmpty(mChannelId)) {
            mRetrofit = mRetrofit.newBuilder().baseUrl(NetworkConfig.LIVE_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mRetrofit.create(LiveService.class)
                    .getChannelLiveInfo(mChannelId)
                    .enqueue(new Callback<ChannelLiveInfo>() {
                        @Override
                        public void onResponse(Call<ChannelLiveInfo> call, Response<ChannelLiveInfo> response) {
                            if (response.body() != null) {
                                startPlay(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<ChannelLiveInfo> call, Throwable t) {
                            Log.d(TAG, t.toString());
                        }
                    });
        }
    }

    private void startPlay(ChannelLiveInfo channelLiveInfo) {
        ChannelLiveInfo.FlvUrl flvUrl = channelLiveInfo.getFlvUrl();
        ChannelLiveInfo.HlsUrl hlsUrl = channelLiveInfo.getHlsUrl();
        MediaSource mediaSource = null;
        if (flvUrl != null && !TextUtils.isEmpty(flvUrl.getFlv1())) {
            mediaSource = mMediaSourceHelper.buildMediaSource(Uri.parse(flvUrl.getFlv1()), null, null, null);
        } else if (hlsUrl != null && !TextUtils.isEmpty(hlsUrl.getHls1())) {
            mediaSource = mMediaSourceHelper.buildMediaSource(Uri.parse(hlsUrl.getHls1()), null, null, null);
        }
        // Prepare the player with the source.
        if (mediaSource != null) {
            mPlayer.prepare(mediaSource);
            mPlayer.setPlayWhenReady(true);
        }
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