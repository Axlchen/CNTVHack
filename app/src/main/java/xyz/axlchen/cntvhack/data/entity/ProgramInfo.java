package xyz.axlchen.cntvhack.data.entity;


import com.google.gson.annotations.SerializedName;

public class ProgramInfo {

    private String mediaId;
    private String mediaName;
    private String logoImg;
    @SerializedName("biref")
    private String description;

    public String getMediaId() {
        return mediaId;
    }

    public String getMediaName() {
        return mediaName;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ProgramInfo{" +
                "mediaId='" + mediaId + '\'' +
                ", mediaName='" + mediaName + '\'' +
                ", logoImg='" + logoImg + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
