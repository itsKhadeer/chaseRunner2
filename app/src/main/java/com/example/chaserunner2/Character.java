package com.example.chaserunner2;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Character {
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private String type;

    @SerializedName("imageUrl")
    private String imageUrl;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
