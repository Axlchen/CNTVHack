package xyz.axlchen.cntvhack.data.entity;

import com.google.gson.annotations.SerializedName;

public class ShortVideoDetailInfo {

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
        return "ShortVideoDetailInfo{" +
                "title='" + title + '\'' +
                ", hlsUrl='" + hlsUrl + '\'' +
                '}';
    }
}
