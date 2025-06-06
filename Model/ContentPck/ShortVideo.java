package com.example.videoplayer.Model.ContentPck;


import com.example.videoplayer.Model.Category;

public class ShortVideo extends Video {
    private String musicReference;

    public ShortVideo(ContentSpecialStatus isExclusive, String title, String description, int duration, Category category, String fileLink, String thumbnail, String videoSubtitle, String musicReference) {
        super(isExclusive, title, description, duration, category, fileLink, thumbnail);
        this.musicReference=musicReference;
    }


    public String getMusicReference() {
        return musicReference;
    }

    public void setMusicReference(String musicReference) {
        this.musicReference = musicReference;
    }
}