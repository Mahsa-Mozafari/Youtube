package com.example.videoplayer.Model;



import com.example.videoplayer.Model.AccountPck.User;

import java.util.Date;

public class Comment {
    private User commenter;
    private String commentText;
    private Date date;

    public Comment(User commenter){
        this.commenter=commenter;
        this.commentText="";
        this.date=new Date();
    }

    public Date getDate() {
        return date;
    }

    public String getCommentText() {
        return commentText;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

