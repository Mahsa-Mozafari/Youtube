package com.example.videoplayer.Model.ContentPck;


import com.example.videoplayer.Model.Category;

public class Podcast extends Content {
    private String creator;

    public Podcast(ContentSpecialStatus isExclusive, String title, String description, int duration, Category category, String fileLink, String thumbnail, String creator) {
        super(isExclusive, title, description, duration, category, fileLink, thumbnail);
        this.creator=creator;
    }


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
