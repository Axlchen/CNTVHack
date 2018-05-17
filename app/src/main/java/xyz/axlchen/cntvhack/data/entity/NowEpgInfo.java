package xyz.axlchen.cntvhack.data.entity;

import com.google.gson.annotations.SerializedName;

public class NowEpgInfo {

    private String errcode;
    private String msg;
    @SerializedName("t")
    private String itemName;
    @SerializedName("ints")
    private long start;
    @SerializedName("d")
    private long now;
    @SerializedName("inte")
    private long end;
    @SerializedName("s")
    private String startString;
    @SerializedName("e")
    private String endString;

    public String getErrcode() {
        return errcode;
    }

    public String getMsg() {
        return msg;
    }

    public String getItemName() {
        return itemName;
    }

    public long getStart() {
        return start;
    }

    public long getNow() {
        return now;
    }

    public long getEnd() {
        return end;
    }

    public String getStartString() {
        return startString;
    }

    public String getEndString() {
        return endString;
    }

    @Override
    public String toString() {
        return "NowEpgInfo{" +
                "errcode='" + errcode + '\'' +
                ", msg='" + msg + '\'' +
                ", itemName='" + itemName + '\'' +
                ", start=" + start +
                ", now=" + now +
                ", end=" + end +
                ", startString='" + startString + '\'' +
                ", endString='" + endString + '\'' +
                '}';
    }
}