package xyz.axlchen.cntvhack.activitys;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.axlchen.cntvhack.R;
import xyz.axlchen.cntvhack.data.entity.VideoDetailInfo;
import xyz.axlchen.cntvhack.net.NetworkConfig;
import xyz.axlchen.cntvhack.net.services.ShortVideoService;

public class PlayVideoActivity extends BaseActivity {

    public static final String VIDEO_ID = "video_id";

    private String mVideoId;
    private DefaultBandwidthMeter mBandwidthMeter;
    private SimpleExoPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        mVideoId = getIntent().getStringExtra(VIDEO_ID);

        PlayerView playerView = findViewById(R.id.exo_play_view);

        // 1. Create a default TrackSelector
        // Measures bandwidth during playback. Can be null if not required.
        mBandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(mBandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        mPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        playerView.setPlayer(mPlayer);
        getVideoInfo();
    }

    private void getVideoInfo() {
        if (!TextUtils.isEmpty(mVideoId)) {
            mRetrofit = mRetrofit.newBuilder().baseUrl(NetworkConfig.VDN_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mRetrofit.create(ShortVideoService.class)
                    .getVideoInfo(mVideoId)
                    .enqueue(new Callback<VideoDetailInfo>() {
                        @Override
                        public void onResponse(Call<VideoDetailInfo> call, Response<VideoDetailInfo> response) {
                            if (response.body() != null) {
                                startPlay(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<VideoDetailInfo> call, Throwable t) {

                        }
                    });
        }
    }

    private void startPlay(VideoDetailInfo videoDetailInfo) {
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getString(R.string.app_name)), mBandwidthMeter);


        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse("http://153.37.99.16/v.cctv.com/flash/mp4video62/TMS/2018/05/06/55836a791e1fcf6ad39c6a97e9798a9e_850_h264_818_aac_32.mp4"));

        HlsMediaSource hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videoDetailInfo.getHlsUrl()));
        // Prepare the player with the source.
        mPlayer.prepare(hlsMediaSource);
        mPlayer.setPlayWhenReady(true);
        mPlayer.setRepeatMode(Player.REPEAT_MODE_OFF);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
    }
}
