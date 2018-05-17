package xyz.axlchen.cntvhack.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChannelCategory {

    @SerializedName("liveCategoryList")
    private List<Item> category;

    public List<Item> getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "ChannelCategory{" +
                "category=" + category +
                '}';
    }

    public static class Item {

        private String title;
        private int isHD;
        private String channelListUrl;
        private String channelSign;

        public String getTitle() {
            return title;
        }

        public int getIsHD() {
            return isHD;
        }

        public String getChannelListUrl() {
            return channelListUrl;
        }

        public String getChannelSign() {
            return channelSign;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "title='" + title + '\'' +
                    ", isHD=" + isHD +
                    ", channelListUrl='" + channelListUrl + '\'' +
                    ", channelSign='" + channelSign + '\'' +
                    '}';
        }
    }
}
