package xyz.axlchen.cntvhack.data.entity;

import java.util.List;

public class UrlConfig {

    private List<Item> data;

    public List<Item> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "UrlConfig{" +
                "data=" + data +
                '}';
    }

    public static class Item {
        private String title;
        private String url;

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
