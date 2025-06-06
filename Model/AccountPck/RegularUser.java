package com.example.videoplayer.Model.AccountPck;


import com.example.videoplayer.Model.ContentPck.Content;
import com.example.videoplayer.Model.Playlist;

import java.util.ArrayList;

public class RegularUser extends User {
    private final int MAX_CONTENT_PER_PLAYLIST;
    private final int MAX_PLAYLIST;

    public RegularUser(String username, String fullName, String phoneNumber, String email, String profileCover) {
        super(username, fullName, phoneNumber, email, new ArrayList<>(), profileCover);
        this.MAX_CONTENT_PER_PLAYLIST=10;
        this.MAX_PLAYLIST=3;
    }


    public int getMAX_CONTENT_PER_PLAYLIST() {
        return MAX_CONTENT_PER_PLAYLIST;
    }

    public int getMAX_PLAYLIST() {
        return MAX_PLAYLIST;
    }

    @Override
    public boolean canCreatePlaylist() {
        return (getPlaylists().size() - 2) < MAX_PLAYLIST;
    }

    @Override
    public boolean addToPlaylist(Playlist playlist, Content content) {
        if (playlist.getContents().size() >= MAX_CONTENT_PER_PLAYLIST) {
            return false;
        }
        playlist.getContents().add(content);
        return true;
    }
}

