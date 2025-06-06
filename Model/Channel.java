package com.example.videoplayer.Model;



import com.example.videoplayer.Model.AccountPck.User;

import java.util.ArrayList;

public class Channel {
    private static int idCounter=1;
    private int channelId;
    private String channelName;
    private String description;
    private String channelCover;
    private String creator;
    private ArrayList<Playlist> playlists;
    private ArrayList<User> subscribers;
    private User owner;

    public Channel(String channelName, String description, String channelCover, String creator) {
        this.channelId = idCounter++;
        this.channelName = channelName;
        this.description = description;
        this.channelCover = channelCover;
        this.creator = creator;
        this.playlists = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public String getCreator() {
        return creator;
    }

    public String getDescription() {
        return description;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public String getChannelName() {
        return channelName;
    }

    public ArrayList<User> getSubscribers() {
        return subscribers;
    }

    public int getChannelId() {
        return channelId;
    }

    public String getChannelCover() {
        return channelCover;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public static void setIdCounter(int idCounter) {
        Channel.idCounter = idCounter;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public void setChannelCover(String channelCover) {
        this.channelCover = channelCover;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setSubscribers(ArrayList<User> subscribers) {
        this.subscribers = subscribers;
    }
}
