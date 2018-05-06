package xyz.axlchen.cntvhack.data.entity;

import com.google.gson.annotations.SerializedName;

public class VideoDetailInfo {

    private String title;

    @SerializedName("hls_url")
    private String hlsUrl;

    public String getTitle() {
        return title;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }

    @Override
    public String toString() {
        return "VideoDetailInfo{" +
                "title='" + title + '\'' +
                ", hlsUrl='" + hlsUrl + '\'' +
                '}';
    }
}
