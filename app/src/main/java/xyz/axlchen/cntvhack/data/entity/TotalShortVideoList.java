package xyz.axlchen.cntvhack.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TotalShortVideoList {

    private String title;
    private List<VideoCategory> itemList;
    private List<VideoInfo> videoList;

    public String getTitle() {
        return title;
    }

    public List<VideoCategory> getItemList() {
        return itemList;
    }

    public List<VideoInfo> getVideoList() {
        return videoList;
    }

    @Override
    public String toString() {
        return "TotalShortVideoList{" +
                "title='" + title + '\'' +
                ", itemList=" + itemList +
                ", videoList=" + videoList +
                '}';
    }

    public static class VideoCategory {
        private String imgUrl;
        private String title;
        private String linkUrl;
        private String order;
        @SerializedName("vtype")
        private String vType;

        public String getImgUrl() {
            return imgUrl;
        }

        public String getTitle() {
            return title;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public String getOrder() {
            return order;
        }

        public String getvType() {
            return vType;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setvType(String vType) {
            this.vType = vType;
        }

        @Override
        public String toString() {
            return "VideoCategory{" +
                    "imgUrl='" + imgUrl + '\'' +
                    ", title='" + title + '\'' +
                    ", linkUrl='" + linkUrl + '\'' +
                    ", order='" + order + '\'' +
                    ", vType='" + vType + '\'' +
                    '}';
        }
    }
}