package com.example.videoplayer.Controller;


import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.Category;
import com.example.videoplayer.Model.Channel;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.ContentPck.Content;
import com.example.videoplayer.Model.ContentPck.Podcast;
import com.example.videoplayer.Model.ContentPck.Video;
import com.example.videoplayer.Model.Database;
import com.example.videoplayer.Model.Playlist;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContentController {
    private static ContentController contentController;
    private Database database;
    private AuthController authController;
    private PlaylistController playlistController;

    public static ContentController getInstance(){
        if (contentController==null){
            contentController=new ContentController();
        }
        return contentController;
    }

    public ContentController() {
        this.database = Database.getInstance();
    }

    public AuthController getAuthController() {
        if (authController == null) {
            authController = AuthController.getInstance();
        }
        return authController;
    }

    public PlaylistController getPlaylistController() {
        if (playlistController == null) {
           playlistController = PlaylistController.getInstance();
        }
        return playlistController;
    }


    public Map<String, ArrayList<?>> searchInContentsAndChannels(String searchBox) {
        String keyword = searchBox.toLowerCase();
        ArrayList<Content> foundContents = new ArrayList<>();
        ArrayList<Channel> foundChannels = new ArrayList<>();

        for (Content content : database.getContents()) {
            if (content.getTitle().toLowerCase().contains(keyword)) {
                foundContents.add(content);
            }
        }

        for (Channel channel : database.getChannels()) {
            if (channel.getChannelName().toLowerCase().contains(keyword)) {
                foundChannels.add(channel);
            }
        }

        Map<String, ArrayList<?>> result = new HashMap<>();
        result.put("contents", foundContents);
        result.put("channels", foundChannels);
        return result;
    }

    public ArrayList<Content> sortContentByLikes() {
        ArrayList<Content> sortedList = new ArrayList<>(database.getContents());

        for (int i = 0; i < sortedList.size() - 1; i++) {
            for (int j = 0; j < sortedList.size() - i - 1; j++) {
                if (sortedList.get(j).getLikes() < sortedList.get(j + 1).getLikes()) {
                    Content temp = sortedList.get(j);
                    sortedList.set(j, sortedList.get(j + 1));
                    sortedList.set(j + 1, temp);
                }
            }
        }

        return sortedList;
    }

    public ArrayList<Content> sortContentByViews() {
        ArrayList<Content> sortedList = new ArrayList<>(database.getContents());
        for (int i = 0; i < sortedList.size() - 1; i++) {
            for (int j = 0; j < sortedList.size() - i - 1; j++) {
                if (sortedList.get(j).getViews() < sortedList.get(j + 1).getViews()) {
                    Content temp = sortedList.get(j);
                    sortedList.set(j, sortedList.get(j + 1));
                    sortedList.set(j + 1, temp);
                }

            }
        }
        return sortedList;
    }


    public ArrayList<Content> filterByVideo(){
        ArrayList<Content> result = new ArrayList<>();
        for (Content content :database.getContents()) {
            if (content instanceof Video) {
                result.add(content);
            }
        }
        return result;

    }

    public ArrayList<Content> filterByPodcast(){
        ArrayList<Content> result = new ArrayList<>();
        for (Content content : database.getContents()) {
            if (content instanceof Podcast) {
                result.add(content);
            }
        }
        return result;

    }

    public ArrayList<Content> filterByCategory(Category category) {
        ArrayList<Content> result = new ArrayList<>();
        for (Content content : database.getContents()) {
            if (content.getCategory() == category) {
                result.add(content);
            }
        }
        return result;
    }


        public void playContent(int contentId) {
            Account loggedInUser = getAuthController().getLoggedInUser();
            if (!(loggedInUser instanceof User)) {
                return;
            }

            User user = (User) loggedInUser;
            Content content = findContentById(contentId);

            if (content != null) {
                content.setViews(content.getViews() + 1);
                Playlist watchLater = getPlaylistController().findPlaylistByName(user, "Watch Later");
                if (watchLater != null && !watchLater.getContents().contains(content)) {
                    watchLater.getContents().add(content);
                }
            }
        }


    public void likeContent(int contentId) {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return;
        }

        User user = (User) loggedInUser;
        Content content = findContentById(contentId);

        if (content != null) {
            Playlist likedPlaylist = getPlaylistController().findPlaylistByName(user, "Liked");

            if (!user.getLikedContents().contains(content)) {
                content.setLikes(content.getLikes() + 1);
                user.getLikedContents().add(content);

                if (likedPlaylist != null && !likedPlaylist.getContents().contains(content)) {
                    likedPlaylist.getContents().add(content);
                }
            }
        }
    }

    public void dislikeContent(int contentId) {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return;
        }

        User user = (User) loggedInUser;
        Content content = findContentById(contentId);

        if (content != null) {
            Playlist likedPlaylist = getPlaylistController().findPlaylistByName(user, "Liked");

            if (user.getLikedContents().contains(content)) {
                content.setLikes(content.getLikes() - 1);
                user.getLikedContents().remove(content);
                if (likedPlaylist != null) {
                    likedPlaylist.getContents().remove(content);
                }
            }
        }
    }

    public ArrayList<Content> getSuggestions() {
        Account account = getAuthController().getLoggedInUser();
        if (!(account instanceof User)) {
            return new ArrayList<>();
        }

        User user = (User) account;
        ArrayList<Content> allContents = database.getContents();
        for (Content content : allContents) {
            content.setSuggestionPriority(0);

            if (user.getFavoriteCategories().contains(content.getCategory())) {
                content.setSuggestionPriority(content.getSuggestionPriority() + 2);
            }

            for (Content liked : user.getLikedContents()) {
                if (liked.getCategory().equals(content.getCategory())) {
                    content.setSuggestionPriority(content.getSuggestionPriority() + 1);
                    break;
                }
            }
        }

        for (int i = 0; i < allContents.size(); i++) {
            for (int j = i + 1; j < allContents.size(); j++) {
                if (allContents.get(i).getSuggestionPriority() < allContents.get(j).getSuggestionPriority()) {
                    Content temp = allContents.get(i);
                    allContents.set(i, allContents.get(j));
                    allContents.set(j, temp);
                }
            }
        }

        ArrayList<Content> suggestions = new ArrayList<>();
        for (Content content : allContents) {
            if (!suggestions.contains(content)) {
                suggestions.add(content);
                if (suggestions.size() == 4) break;
            }
        }

        return suggestions;
    }



    public Content findContentById(int contentId){
        for(Content content: database.getContents()){
            if(content.getContentId()==contentId){
                return content;
            }
        }
        return null;
    }
}

