package com.example.videoplayer.Model.AccountPck;



import com.example.videoplayer.Model.Category;
import com.example.videoplayer.Model.ContentPck.Content;
import com.example.videoplayer.Model.Playlist;

import java.util.ArrayList;
import java.util.Date;

public class PremiumUser extends User {
    private Date subscriptionEndDate;

    public PremiumUser(String username, String fullName, String phoneNumber, String email, ArrayList<Category> favoriteCategories, String profileCover) {
        super(username, fullName, phoneNumber, email, favoriteCategories, profileCover);
        this.subscriptionEndDate=new Date();
    }


    public Date getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(Date subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    @Override
    public boolean canCreatePlaylist() {
        return true;
    }

    @Override
    public boolean addToPlaylist(Playlist playlist, Content content) {
        return true;
    }
}
