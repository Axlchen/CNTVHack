package xyz.axlchen.cntvhack.data.entity;

import java.util.List;

public class ShortVideoList {
    private List<VideoInfo> videoList;

    public List<VideoInfo> getVideoList() {
        return videoList;
    }

    @Override
    public String toString() {
        return "ShortVideoList{" +
                "videoList=" + videoList +
                '}';
    }
}
