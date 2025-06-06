package com.example.videoplayer.Model.ContentPck;



import com.example.videoplayer.Model.Category;

import java.util.Date;

public class LiveStream extends Video {
    private int viewers;
    private Date scheduledTime;

    public LiveStream(ContentSpecialStatus isExclusive, String title, String description, int duration, Category category, String fileLink, String thumbnail, String videoSubtitle, Date scheduledTime) {
        super(isExclusive, title, description, duration, category, fileLink, thumbnail);
        this.viewers=0;
        this.scheduledTime=scheduledTime;
    }


    public int getViewers() {
        return viewers;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {this.scheduledTime = scheduledTime; }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }
}

