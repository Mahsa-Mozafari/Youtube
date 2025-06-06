package com.example.videoplayer.Controller;


import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.Channel;
import com.example.videoplayer.Model.ContentPck.Content;
import com.example.videoplayer.Model.Playlist;
import com.example.videoplayer.Model.Database;

import java.util.ArrayList;

public class LibraryController {
    private Database database;
    private static LibraryController libraryController;
    private AuthController authController;

    private LibraryController(){
        this.database=Database.getInstance();
    }

    public AuthController getAuthController() {
        if (authController == null) {
            authController = AuthController.getInstance();
        }
        return authController;
    }

    public static LibraryController getInstance(){
        if (libraryController==null){
            libraryController=new LibraryController();
        }
        return libraryController;
    }

    public String showSubscriptions() {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return "You must be logged in to view subscriptions.";
        }

        User user = (User) loggedInUser;
        ArrayList<Channel> subscriptions = user.getSubscriptions();

        if (subscriptions == null || subscriptions.isEmpty()) {
            return "You have no subscriptions.";
        }

        StringBuilder subscriptionsList = new StringBuilder("Your subscriptions:\n");
        for (Channel channel : subscriptions) {
            subscriptionsList.append(channel.getChannelName()).append("\n");
        }

        return subscriptionsList.toString();
    }


    public String showLikedContents() {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return "You must be logged in to view liked contents.";
        }

        User user = (User) loggedInUser;
        Playlist likedPlaylist = null;
        for (Playlist playlist : user.getPlaylists()) {
            if (playlist.getPlaylistName().equals("Liked")) {
                likedPlaylist = playlist;
                break;
            }
        }

        if (likedPlaylist != null) {
            StringBuilder likedContentsList = new StringBuilder("Your liked contents:\n");
            for (Content content : likedPlaylist.getContents()) {
                likedContentsList.append(content.getTitle()).append("\n");
            }
            return likedContentsList.toString();
        }

        return "You have no liked contents.";
    }

}

