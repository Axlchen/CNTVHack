package xyz.axlchen.cntvhack.data.entity;

import com.google.gson.annotations.SerializedName;

public class ChannelLiveInfo {

    private String ack;
    @SerializedName("flv_url")
    private FlvUrl flvUrl;
    @SerializedName("hls_url")
    private HlsUrl hlsUrl;

    public String getAck() {
        return ack;
    }

    public FlvUrl getFlvUrl() {
        return flvUrl;
    }

    public HlsUrl getHlsUrl() {
        return hlsUrl;
    }

    @Override
    public String toString() {
        return "ChannelLiveInfo{" +
                "ack='" + ack + '\'' +
                ", flvUrl=" + flvUrl +
                ", hlsUrl=" + hlsUrl +
                '}';
    }

    public class FlvUrl {
        private String flv1;
        private String flv2;
        private String flv3;
        private String flv4;
        private String flv5;
        private String flv6;

        public String getFlv1() {
            return flv1;
        }

        public String getFlv2() {
            return flv2;
        }

        public String getFlv3() {
            return flv3;
        }

        public String getFlv4() {
            return flv4;
        }

        public String getFlv5() {
            return flv5;
        }

        public String getFlv6() {
            return flv6;
        }

        @Override
        public String toString() {
            return "FlvUrl{" +
                    "flv1='" + flv1 + '\'' +
                    ", flv2='" + flv2 + '\'' +
                    ", flv3='" + flv3 + '\'' +
                    ", flv4='" + flv4 + '\'' +
                    ", flv5='" + flv5 + '\'' +
                    ", flv6='" + flv6 + '\'' +
                    '}';
        }
    }

    public class HlsUrl {
        private String hls1;
        private String hls2;
        private String hls3;
        private String hls4;
        private String hls5;
        private String hls6;

        public String getHls1() {
            return hls1;
        }

        public String getHls2() {
            return hls2;
        }

        public String getHls3() {
            return hls3;
        }

        public String getHls4() {
            return hls4;
        }

        public String getHls5() {
            return hls5;
        }

        public String getHls6() {
            return hls6;
        }

        @Override
        public String toString() {
            return "HlsUrl{" +
                    "hls1='" + hls1 + '\'' +
                    ", hls2='" + hls2 + '\'' +
                    ", hls3='" + hls3 + '\'' +
                    ", hls4='" + hls4 + '\'' +
                    ", hls5='" + hls5 + '\'' +
                    ", hls6='" + hls6 + '\'' +
                    '}';
        }
    }
}
