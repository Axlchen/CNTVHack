package xyz.axlchen.cntvhack.data.entity;

import com.google.gson.annotations.SerializedName;

public class ProgramDetail {

    private String mediaName;
    @SerializedName("biref")
    private String description;

    public String getMediaName() {
        return mediaName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ProgramInfo{" +
                ", mediaName='" + mediaName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
