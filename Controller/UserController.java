package com.example.videoplayer.Controller;


import com.example.videoplayer.Model.*;
import com.example.videoplayer.Model.AccountPck.*;
import com.example.videoplayer.Model.ContentPck.Content;


import java.util.ArrayList;
import java.util.Date;

public class UserController {
    private static UserController userController;
    private Database database;
    private AuthController authController;
    private ChannelController channelController;

    private UserController(){
        this.database=Database.getInstance();
    }

    public AuthController getAuthController() {
        if (authController == null) {
            authController = AuthController.getInstance();
        }
        return authController;
    }

    public ChannelController getChannelController() {
        if (channelController == null) {
            channelController = ChannelController.getInstance();
        }
        return channelController;
    }

    public static UserController getInstance(){
        if (userController==null){
            userController=new UserController();
        }
        return userController;
    }

    public String getAccountInfo(){
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return "User should login";
        }
        User user=(User) loggedInUser;
        return "Account Info:\n" +
                "FullName: " + user.getFullName() + " | " +
                "Password: " + user.getPassword()+ "\n"+
                "Email: " +  user.getEmail()+" | "+
                "Username: "+ user.getUsername()+"\n";
    }

    public User editUserName(String newValue) {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User user)) {
            return null;
        }

        for (User u : database.getUsers()) {
            if (u.getUsername().equalsIgnoreCase(newValue)) {
                return null;
            }
        }

        user.setUsername(newValue);
        return user;
    }

    public User editUserPassword(String newValue) {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User user)) {
            return null;
        }

        for (User u : database.getUsers()) {
            if (u.getPassword().equalsIgnoreCase(newValue)) {
                return null;
            }
        }

        user.setPassword(newValue);
        return user;
    }

    public String subscribe(int channelId){
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return "Only users can subscribe to channels.";
        }

        User user = (User) loggedInUser;
        Channel channel = getChannelController().findChannelById(channelId);

        if (channel == null) {
            return "Channel not found.";
        }

        if (user.getSubscriptions().contains(channel)) {
            return "You are already subscribed to this channel.";
        }

        user.getSubscriptions().add(channel);
        channel.getSubscribers().add(user);
        return "Successfully subscribed to: " + channel.getChannelName();
    }

    public String unsubscribe(int channelId){
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return "Only users can unsubscribe from channels.";
        }

        User user = (User) loggedInUser;
        Channel channel = getChannelController().findChannelById(channelId);

        if (channel == null) {
            return "Channel not found.";
        }

        if (!user.getSubscriptions().contains(channel)) {
            return "You are not subscribed to this channel.";
        }

        user.getSubscriptions().remove(channel);
        channel.getSubscribers().remove(user);
        return "Successfully unsubscribed from: " + channel.getChannelName();
    }

    private ArrayList<Content> getChannelContents(Channel channel) {
        ArrayList<Content> contents = new ArrayList<>();
        for (Playlist playlist : channel.getPlaylists()) {
            contents.addAll(playlist.getContents());
        }
        return contents;
    }

    public String increaseCredit(double amount) {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return "No user logged in.";
        }
        User user = (User) loggedInUser;
        user.setCredit(user.getCredit() + amount);
        return "Credit increased successfully.";
    }

    public String buyPremium(PremiumPackage packageType) {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return "No user logged in.";
        }
        User user = (User) loggedInUser;
        double packageCost = packageType.getPrice();
        if (user.getCredit() < packageCost) {
            return "Not enough credits.";
        }

        if (user instanceof RegularUser) {
            user.setCredit(user.getCredit() - packageCost);
            PremiumUser premiumUser = new PremiumUser(
                    user.getUsername(),
                    user.getFullName(),
                    user.getPhoneNumber(),
                    user.getEmail(),
                    user.getFavoriteCategories(),
                    user.getProfileCoverLink()
            );
            premiumUser.setCredit(user.getCredit());
            premiumUser.setSubscriptionEndDate(new Date(System.currentTimeMillis() + packageType.getDays()));
            database.getUsers().remove(user);
            database.getUsers().add(premiumUser);
            return "Premium package purchased successfully.";
        }

        return "Failed to buy premium package.";
    }


    public Channel editChannelName(String newName) {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return null;
        }

        User user = (User) loggedInUser;

        for (Channel channel : database.getChannels()) {
            if (channel.getCreator().equals(user.getFullName())) {
                channel.setChannelName(newName);
                return channel;
            }
        }

        return null;
    }

    public Channel editChannelDescription(String newDescription) {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return null;
        }

        User user = (User) loggedInUser;

        for (Channel channel : database.getChannels()) {
            if (channel.getCreator().equals(user.getFullName())) {
                channel.setDescription(newDescription);
                return channel;
            }
        }

        return null;
    }


    public String showChannelSubscribers() {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return "Only logged-in users can see subscriber count.";
        }

        User user = (User) loggedInUser;
        int totalSubscribers = 0;

        for (Channel channel : database.getChannels()) {
            if (channel.getCreator().equals(user.getFullName())) {
                totalSubscribers += channel.getSubscribers().size();
            }
        }

        return "Total Subscribers: " + totalSubscribers;
    }

    public String showUserChannelContent() {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return "Only logged-in users can see channel contents.";
        }

        User user = (User) loggedInUser;
        StringBuilder sb = new StringBuilder();
        boolean hasContent = false;

        for (Channel channel : database.getChannels()) {
            if (channel.getCreator().equals(user.getFullName())) {
                ArrayList<Content> contents = getChannelContents(channel);
                for (Content content : contents) {
                    sb.append("Title: ").append(content.getTitle())
                            .append("Likes: ").append(content.getLikes())
                            .append("Views: ").append(content.getViews()).append("\n");
                    hasContent = true;
                }
            }
        }

        return hasContent ? sb.toString() : "No content published by your channel.";
    }


    public String setFavoriteCategories(String input) {
        RegularUser signUpUser = getAuthController().getSignUpUser();

        if (signUpUser == null) {
            return "No user found.";
        }

        String[] parts = input.split(",");
        ArrayList<Category> selected = new ArrayList<>();

        for (String part : parts) {
            String trimmed = part.trim().toUpperCase().replaceAll("\\s+", "");
            boolean isValid = false;

            for (Category c : Category.values()) {
                if (c.name().equalsIgnoreCase(trimmed)) {
                    if (!selected.contains(c)) {
                        selected.add(c);
                    }
                    isValid = true;
                    break;
                }
            }

            if (!isValid) {
                return "Invalid category: " + part.trim();
            }

            if (selected.size() == 4) {
                break;
            }
        }

        if (selected.isEmpty()) {
            return "Select at least one valid category.";
        }

        signUpUser.setFavoriteCategories(selected);
        return "Categories updated.";
    }

}

