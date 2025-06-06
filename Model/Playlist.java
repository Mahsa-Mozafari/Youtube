package com.example.videoplayer.Model;


import com.example.videoplayer.Model.ContentPck.Content;

import java.util.ArrayList;

public class Playlist {
    private String playlistName;
    private ArrayList<Content> contents;
    private int playlistId;
    private static int idCounter;

    public Playlist(String playlistName){
        this.playlistName=playlistName;
        this.playlistId=idCounter++;
        this.contents=new ArrayList<>();
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public ArrayList<Content> getContents() {
        return contents;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public static void setIdCounter(int idCounter) {
        Playlist.idCounter = idCounter;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public void setContents(ArrayList<Content> contents) {
        this.contents = contents;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
}

