package xyz.axlchen.cntvhack.data.entity;


public class DataWrapper<T> {
    private int code;
    private String msg;
    private String status;
    private T data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "DataWrapper{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
