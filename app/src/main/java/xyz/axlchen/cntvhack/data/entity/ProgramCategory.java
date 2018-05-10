package xyz.axlchen.cntvhack.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProgramCategory {

    @SerializedName("lanmuCategory")
    private List<Item> category;

    public List<Item> getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "ProgramCategory{" +
                "category=" + category +
                '}';
    }

    public static class Item {

        private String title;
        @SerializedName("lanmuId")
        private String id;

        public String getTitle() {
            return title;
        }

        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "title='" + title + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }
}
