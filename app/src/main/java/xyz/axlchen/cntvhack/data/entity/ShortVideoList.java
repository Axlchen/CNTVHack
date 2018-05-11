package xyz.axlchen.cntvhack.data.entity;

import java.util.List;

public class ShortVideoList {
    private List<ShortVideoInfo> videoList;

    public List<ShortVideoInfo> getVideoList() {
        return videoList;
    }

    @Override
    public String toString() {
        return "ShortVideoList{" +
                "videoList=" + videoList +
                '}';
    }
}
