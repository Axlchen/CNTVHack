package xyz.axlchen.cntvhack.data.entity;

import com.google.gson.annotations.SerializedName;

public class ProgramDetail {

    private String mediaName;
    @SerializedName("biref")
    private String description;
    private String logoImg;

    public String getMediaName() {
        return mediaName;
    }

    public String getDescription() {
        return description;
    }

    public String getLogoImg() {
        return logoImg;
    }

    @Override
    public String toString() {
        return "ProgramDetail{" +
                "mediaName='" + mediaName + '\'' +
                ", description='" + description + '\'' +
                ", logoImg='" + logoImg + '\'' +
                '}';
    }
}
