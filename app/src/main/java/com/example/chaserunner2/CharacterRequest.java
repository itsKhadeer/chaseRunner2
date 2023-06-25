package com.example.chaserunner2;
import com.google.gson.annotations.SerializedName;

public class CharacterRequest {
    @SerializedName("type")
    private String type;

    public CharacterRequest(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
