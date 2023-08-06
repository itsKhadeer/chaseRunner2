package com.example.chaserunner2;

public class recViewModel {

    String imgUrl,name, description;

    public recViewModel(String imgUrl, String name, String description) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

//    public void setImgUrl(String imgUrl) {this.imgUrl = imgUrl;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

//    public void setDescription(String description) {this.description = description;}
}
