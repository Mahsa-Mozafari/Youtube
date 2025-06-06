package com.example.videoplayer.Model.ContentPck;


import com.example.videoplayer.Model.Category;

public class NormalVideo extends Video {
    private VideoFormat format;
    private VideoResolution resolution;

    public NormalVideo(ContentSpecialStatus isExclusive, String title, String description, int duration, Category category, String fileLink, String thumbnail, VideoResolution resolution, VideoFormat format) {
        super(isExclusive, title, description, duration, category, fileLink, thumbnail);
        this.format=format;
        this.resolution=resolution;
    }


    public VideoFormat getFormat() {
        return format;
    }

    public VideoResolution getResolution() {
        return resolution;
    }

    public void setFormat(VideoFormat format) {
        this.format = format;
    }

    public void setResolution(VideoResolution resolution) {
        this.resolution = resolution;
    }


}
