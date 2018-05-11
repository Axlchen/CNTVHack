package xyz.axlchen.cntvhack.data.entity;


public class ProgramVideoInfo {

    private String title;
    private String guid;
    private String image1;
    private String image2;
    private long putTime;

    public String getTitle() {
        return title;
    }

    public String getGuid() {
        return guid;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public long getPutTime() {
        return putTime;
    }

    @Override
    public String toString() {
        return "ProgramVideoInfo{" +
                "title='" + title + '\'' +
                ", guid='" + guid + '\'' +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", putTime=" + putTime +
                '}';
    }
}
