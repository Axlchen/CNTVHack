package xyz.axlchen.cntvhack.data.entity;

import com.google.gson.annotations.SerializedName;

public class ShortVideoInfo {
    private String guid;
    //        video_focus_date	String	1525487417000
//        video_url	String	http://news.cctv.com/2018/05/05/VIDE11vN9VKMdPpjPIBAsdx4180505.shtml
    @SerializedName("video_length")
    private String length;
    //        page_id	String	PAGEgatdPhsUfULOOw4hbFAz180425
    @SerializedName("video_title")
    private String title;
    @SerializedName("video_id")
    private String id;
    //        page_name	String	前线
    @SerializedName("video_key_frame_url")
    private String keyFrame;

    public String getGuid() {
        return guid;
    }

    public String getLength() {
        return length;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getKeyFrame() {
        return keyFrame;
    }

    @Override
    public String toString() {
        return "ShortVideoInfo{" +
                "guid='" + guid + '\'' +
                ", length='" + length + '\'' +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", keyFrame='" + keyFrame + '\'' +
                '}';
    }
}