package com.example.videoplayer.Model;


import com.example.videoplayer.Model.AccountPck.User;

public class Report {
    private int reportedContentId;
    private int reportedUserId;
    private User reporter;
    private String description;

    public Report(int reportedContentId, User reporter, String description) {
        this.reportedContentId = reportedContentId;
        this.reporter = reporter;
        this.description = description;
        this.reportedUserId = 0;
    }


    public String getDescription() {
        return description;
    }

    public int getReportedContentId() {
        return reportedContentId;
    }

    public int getReportedUserId() {
        return reportedUserId;
    }

    public User getReporter() {
        return reporter;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReportedContentId(int reportedContentId) {
        this.reportedContentId = reportedContentId;
    }

    public void setReportedUserId(int reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }
}

