package com.example.videoplayer.Controller;


import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.Category;
import com.example.videoplayer.Model.Channel;
import com.example.videoplayer.Model.ContentPck.*;
import com.example.videoplayer.Model.Database;
import com.example.videoplayer.Model.Playlist;

import java.util.ArrayList;
import java.util.Date;

public class ChannelController {
    private static ChannelController  channelController;
    private Database database;
    private AuthController authController;

    public ChannelController(){
        this.database=Database.getInstance();
    }
    public static ChannelController getInstance(){
        if (channelController==null){
            channelController=new ChannelController();
        }
        return channelController;
    }

    public AuthController getAuthController() {
        if (authController == null) {
            authController = AuthController.getInstance();
        }
        return authController;
    }


    public String createChannel(String channelName, String description, String channelCover) {
        Account loggedInUser = getAuthController().getLoggedInUser();

        if (!(loggedInUser instanceof User)) {
            return "Only regular users can create channels.";
        }

        User user = (User) loggedInUser;
        if(user.getChannel()!=null){
            return "You have already created a channel";
        }
        Channel newChannel = new Channel(channelName, description, channelCover, loggedInUser.getFullName());
        user.setChannel(newChannel);
        Playlist allContents = new Playlist("allContents");
        newChannel.getPlaylists().add(allContents);
        database.getChannels().add(newChannel);

        return "Channel created successfully.";
    }

    public String publishPodcast(ContentSpecialStatus code, String title, String description, int duration, Category category, String fileLink, String thumbnail, String creator,Playlist selectedPlaylist) {
        Account loggedInUser = getAuthController().getLoggedInUser();

        if (!(loggedInUser instanceof User)) {
            return "Only regular users can publish podcasts.";
        }

        User user = (User) loggedInUser;
        for (Channel channel : database.getChannels()) {
            if (channel.getCreator().equals(loggedInUser.getFullName())) {
                if (!channel.getPlaylists().get(0).getPlaylistName().equals("allContents")) {
                    return "No valid 'allContents' playlist found.";
                }

                Podcast newPodcast = new Podcast(code, title, description, duration, category, fileLink, thumbnail,creator);
                database.getContents().add(newPodcast);
                newPodcast.setUploader(user);
                channel.getPlaylists().get(0).getContents().add(newPodcast);
                if(selectedPlaylist!=null && !selectedPlaylist.getPlaylistName().equalsIgnoreCase("allContents"))
                    selectedPlaylist.getContents().add(newPodcast);
                return "Podcast published successfully.";
            }
        }
        return "User does not own a channel.";

    }

    public String publishNormalVideo(ContentSpecialStatus code, String title, String description, int duration, Category category, String fileLink, String thumbnail, VideoResolution resolution, VideoFormat format,Playlist selectedPlaylist){
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return "Only regular users can publish videos.";
        }

        User user = (User) loggedInUser;
        for (Channel channel : database.getChannels()) {
            if (channel.getCreator().equals(loggedInUser.getFullName())) {
                if (!channel.getPlaylists().get(0).getPlaylistName().equals("allContents")) {
                    return "No valid 'allContents' playlist found.";
                }

                NormalVideo newNormalVideo = new NormalVideo(code, title, description, duration, category, fileLink, thumbnail,resolution, format);
                database.getContents().add(newNormalVideo);
                newNormalVideo.setUploader(user);
                channel.getPlaylists().get(0).getContents().add(newNormalVideo);
                if(selectedPlaylist!=null && !selectedPlaylist.getPlaylistName().equalsIgnoreCase("allContents"))
                    selectedPlaylist.getContents().add(newNormalVideo);
                return "Normal video published successfully.";
            }
        }
        return "User does not own a channel.";
    }


    public String publishShortVideo(ContentSpecialStatus code, String title, String description, int duration, Category category, String fileLink, String thumbnail, String subtitle, String musicReference) {
        if (duration >= 30) {
            return "Short video must be under 30 seconds.";
        }

        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return "Only regular users can publish videos.";
        }

        User user = (User) loggedInUser;
        for (Channel channel : database.getChannels()) {
            if (channel.getCreator().equals(loggedInUser.getFullName())) {
                if (!channel.getPlaylists().get(0).getPlaylistName().equals("allContents")) {
                    return "No valid 'allContents' playlist found.";
                }

                ShortVideo newShortVideo = new ShortVideo(code, title, description, duration, category, fileLink, thumbnail, subtitle, musicReference);
                database.getContents().add(newShortVideo);
                newShortVideo.setUploader(user);
                channel.getPlaylists().get(0).getContents().add(newShortVideo);

                return "Short video published successfully.";
            }
        }
        return "User does not own a channel.";
    }

    public String publishLiveStream(ContentSpecialStatus code, String title, String description, int duration, Category category, String fileLink, String thumbnail, String subtitle, Date scheduledTime) {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return "Only regular users can publish live streams.";
        }

        User user = (User) loggedInUser;
        for (Channel channel : database.getChannels()) {
            if (channel.getCreator().equals(loggedInUser.getFullName())) {
                if (!channel.getPlaylists().get(0).getPlaylistName().equals("allContents")) {
                    return "No valid 'allContents' playlist found.";
                }

                LiveStream newLiveStream = new LiveStream(code, title, description, duration, category, fileLink, thumbnail, subtitle, scheduledTime);
                database.getContents().add(newLiveStream);
                newLiveStream.setUploader(user);
                channel.getPlaylists().get(0).getContents().add(newLiveStream);

                return "Live stream published successfully.";
            }
        }
        return "User does not own a channel.";
    }

    public String viewChannelInfo(){
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return "User should login";
        }
        User user=(User) loggedInUser;
        return "Channel Info:\n" +
                "Channel Name: " + user.getChannel().getChannelName() + "\n" +
                "Description: " + user.getChannel().getDescription()+ "\n"+
                "Creator: " + user.getChannel().getCreator()+ "\n";
    }

    public Channel findChannelById(int channelId) {
            for (Channel channel :database.getChannels()) {
                if (channel.getChannelId() == channelId) {
                    return channel;
                }
            }
            return null;
    }

    public ArrayList<Content> getChannelContents(Channel channel) {
        ArrayList<Content> contents = new ArrayList<>();
        for (Playlist playlist : channel.getPlaylists()) {
            contents.addAll(playlist.getContents());
        }
        return contents;
    }

}

