package com.example.videoplayer.Model.AccountPck;


import com.example.videoplayer.Model.Category;
import com.example.videoplayer.Model.Channel;
import com.example.videoplayer.Model.ContentPck.Content;
import com.example.videoplayer.Model.Playlist;

import java.util.ArrayList;

public abstract class User extends Account {
    private double credit;
    private ArrayList<Playlist> playlists;
    private Channel channel;
    private ArrayList<Channel> subscriptions;
    private ArrayList<Category> favoriteCategories;
    private int userId;
    private static int idCounter=1;
    private boolean banned;
    private ArrayList<Content> likedContents;

    public User(String username, String fullName, String phoneNumber, String email, ArrayList<Category> favoriteCategories, String profileCover) {
        super(username, fullName, phoneNumber, email, profileCover);
        this.credit = 0.0;
        this.favoriteCategories = favoriteCategories != null ? favoriteCategories : new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
        this.likedContents=new ArrayList<>();
        this.channel = null;
        this.userId=idCounter++;
        playlists.add(new Playlist("Liked"));
        playlists.add(new Playlist("Watch Later"));
    }

    public abstract boolean canCreatePlaylist();
    public abstract boolean addToPlaylist(Playlist playlist, Content content);

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public ArrayList<Category> getFavoriteCategories() {
        return favoriteCategories;
    }

    public ArrayList<Channel> getSubscriptions() {
        return subscriptions;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public ArrayList<Content> getLikedContents() {
        return likedContents;
    }

    public void setLikedContents(ArrayList<Content> likedContents) {
        this.likedContents = likedContents;
    }

    public Channel getChannel() {
        return channel;
    }

    public double getCredit() {
        return credit;
    }

    public void setSubscriptions(ArrayList<Channel> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void setFavoriteCategories(ArrayList<Category> favoriteCategories) {
        this.favoriteCategories = favoriteCategories;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isBanned(){
        return this.banned;
    }
}

