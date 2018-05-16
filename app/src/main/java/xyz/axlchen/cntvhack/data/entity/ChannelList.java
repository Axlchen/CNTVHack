package xyz.axlchen.cntvhack.data.entity;

import java.util.List;

public class ChannelList {

    private List<ChannelInfo> items;

    public List<ChannelInfo> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "ChannelList{" +
                "items=" + items +
                '}';
    }

    public class ChannelInfo {
        private String title;
        private String channelId;
        private String channelImg;
        private String p2pUrl;
        private String audioUrl;

        public String getTitle() {
            return title;
        }

        public String getChannelId() {
            return channelId;
        }

        public String getChannelImg() {
            return channelImg;
        }

        public String getP2pUrl() {
            return p2pUrl;
        }

        public String getAudioUrl() {
            return audioUrl;
        }

        @Override
        public String toString() {
            return "ChannelList{" +
                    "title='" + title + '\'' +
                    ", channelId='" + channelId + '\'' +
                    ", channelImg='" + channelImg + '\'' +
                    ", p2pUrl='" + p2pUrl + '\'' +
                    ", audioUrl='" + audioUrl + '\'' +
                    '}';
        }
    }
}
