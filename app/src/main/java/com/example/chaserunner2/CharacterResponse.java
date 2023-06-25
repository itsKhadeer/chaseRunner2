package com.example.chaserunner2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CharacterResponse {
    @SerializedName("characters")
    private ArrayList<Character> characters;

    public ArrayList<Character> getCharacters() {
        return characters;
    }
}
