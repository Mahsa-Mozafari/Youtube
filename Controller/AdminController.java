package com.example.videoplayer.Controller;


import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.AccountPck.Admin;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.Channel;
import com.example.videoplayer.Model.ContentPck.Content;
import com.example.videoplayer.Model.Report;
import com.example.videoplayer.Model.Database;

import java.util.ArrayList;


public class AdminController {
    private static AdminController adminController;
    private Database database;
    private AuthController authController;
    private ContentController contentController;

    private AdminController(){
        this.database=Database.getInstance();
    }

    public AuthController getAuthController() {
        if (authController == null) {
            authController = AuthController.getInstance();
        }
        return authController;
    }

    public ContentController getContentController() {
        if (contentController == null) {
           contentController= ContentController.getInstance();
        }
        return contentController;

    }

    public static AdminController getInstance(){
        if (adminController==null){
            adminController=new AdminController();
        }
        return adminController;
    }

    public String getAdminInfo() {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof Admin)) {
            return "Admin should login";
        }
        return "Admin Info:\n" +
                "Username: " + Admin.getInstance().getUsername() + "\n" +
                "Full Name: " + Admin.getInstance().getFullName() + "\n" +
                "Phone Number: " + Admin.getInstance().getPhoneNumber() + "\n" +
                "Email: " + Admin.getInstance().getEmail() + "\n" +
                "Profile Cover: " + Admin.getInstance().getProfileCoverLink();
    }


    public String viewPopularContents() {
        Account loggedInUser = AuthController.getInstance().getLoggedInUser();
        if (!(loggedInUser instanceof Admin)) {
            return "Access denied: Only admin can view popular contents.";
        }

        ArrayList<Content> sortedContents = contentController.sortContentByLikes();
        StringBuilder result = new StringBuilder("Most Popular Contents (by likes):\n");

        for (Content content : sortedContents) {
            result.append("Title: ").append(content.getTitle())
                    .append("Likes: ").append(content.getLikes())
                    .append("Views: ").append(content.getViews())
                    .append("\n");
        }

        return result.toString();
    }

    public String viewPopularChannels() {
        Account loggedInUser = AuthController.getInstance().getLoggedInUser();
        if (!(loggedInUser instanceof Admin)) {
            return "Access denied: Only admin can view popular channels.";
        }

        ArrayList<Channel> channels = new ArrayList<>(database.getChannels());
        for (int i = 0; i < channels.size() - 1; i++) {
            for (int j = 0; j < channels.size() - i - 1; j++) {
                if (channels.get(j).getSubscribers().size() < channels.get(j + 1).getSubscribers().size()) {
                    Channel temp = channels.get(j);
                    channels.set(j, channels.get(j + 1));
                    channels.set(j + 1, temp);
                }
            }
        }

        StringBuilder result = new StringBuilder("Most Popular Channels (by followers):\n");

        for (Channel channel : channels) {
            result.append("Channel Name: ").append(channel.getChannelName())
                    .append("Subscribers: ").append(channel.getSubscribers().size())
                    .append("\n");
        }

        return result.toString();
    }

    public String getAllChannels() {
        Account loggedInUser = AuthController.getInstance().getLoggedInUser();
        if (!(loggedInUser instanceof Admin)) {
            return "Access denied: Only admin can view all channels.";
        }

        ArrayList<Channel> channels = new ArrayList<>(database.getChannels());
        if (channels.isEmpty()) {
            return "No channels found in the system.";
        }

        StringBuilder result = new StringBuilder("All Channels:\n");
        for (Channel channel : channels) {
            result.append("Channel Name: ").append(channel.getChannelName())
                    .append("Creator: ").append(channel.getCreator())
                    .append("Subscribers: ").append(channel.getSubscribers().size())
                    .append("\n");
        }
        return result.toString();
    }

    public String getAllReports() {
        Account loggedInUser = AuthController.getInstance().getLoggedInUser();
        if (!(loggedInUser instanceof Admin)) {
            return "Access denied: Only admin can view all reports.";
        }

        ArrayList<Report> reports = new ArrayList<>(database.getReports());
        if (reports.isEmpty()) {
            return "No reports found in the system.";
        }

        StringBuilder result = new StringBuilder("All Reports:\n");
        for (Report report : reports) {
            result.append("Reporter: ").append(report.getReporter().getUsername())
                    .append("Reported Content: ").append(report.getReportedContentId())
                    .append("Description: ").append(report.getDescription())
                    .append("\n");
        }

        return result.toString();
    }

    public String getAllUsers() {
        Account loggedInUser = AuthController.getInstance().getLoggedInUser();
        if (!(loggedInUser instanceof Admin)) {
            return "Access denied: Only admin can view all users.";
        }

        ArrayList<User> users = new ArrayList<>(database.getUsers());

        if (users.isEmpty()) {
            return "No users found in the system.";
        }

        StringBuilder result = new StringBuilder("All Registered Users:\n");

        for (User user : users) {
            result.append("Username: ").append(user.getUsername())
                    .append("Full Name: ").append(user.getFullName())
                    .append("Email: ").append(user.getEmail())
                    .append("Phone: ").append(user.getPhoneNumber())
                    .append("\n");
        }

        return result.toString();
    }

    public String getAllContents() {
        Account loggedInUser = AuthController.getInstance().getLoggedInUser();
        if (!(loggedInUser instanceof Admin)) {
            return "Access denied: Only admin can view all contents.";
        }

        ArrayList<Content> contents = new ArrayList<>(database.getContents());
        if (contents.isEmpty()) {
            return "No contents found in the system.";
        }

        StringBuilder result = new StringBuilder("All Contents:\n");
        for (Content content : contents) {
            result.append("Title: ").append(content.getTitle())
                    .append("Uploader: ").append(content.getUploader().getUsername())
                    .append("Views: ").append(content.getViews())
                    .append("Likes: ").append(content.getLikes())
                    .append("\n");
        }

        return result.toString();
    }
    public String confirmReport(int reportedContentId){
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof Admin)) {
            return "Access denied: Only admin can confirm reports.";
        }

        Report report = findReport(reportedContentId);
        if (report == null) {
            return "Report not found.";
        }

        Content contentToDelete = getContentController().findContentById(reportedContentId);
        if (contentToDelete == null) {
            return "Content not found.";
        }
        database.getContents().remove(contentToDelete);

        User userToBan = findUserById(report.getReportedUserId());
        if (userToBan != null) {
            userToBan.setBanned(true);
            database.getReports().remove(report);
            return "Report confirmed. Content deleted and user banned.";
        }

        return "Content deleted, but user could not be banned.";
    }

    public String rejectReport(int reportedContentId){
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof Admin)) {
            return "Access denied: Only admin can reject reports.";
        }
        Report report = findReport(reportedContentId);
        if (report == null) {
            return "Report not found.";
        }
        database.getReports().remove(report);
        return "Report rejected successfully.";
    }

    public String unbanUser(int reportedUserId){
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof Admin)) {
            return "Access denied: Only admin can unban users.";
        }

        User userToUnban = findUserById(reportedUserId);
        if (userToUnban != null) {
            userToUnban.setBanned(false);
            return "User unbanned successfully.";
        }
        return "User not found.";
    }

    public String banUser(int reportedUserId){
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof Admin)) {
            return "Access denied: Only admin can ban users.";
        }

        User userToBan = findUserById(reportedUserId);
        if (userToBan != null) {
            userToBan.setBanned(true);
            return "User banned successfully.";
        }

        return "User not found.";
    }

    public String deleteReportedContent(int reportedContentId){
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof Admin)) {
            return "Access denied: Only admin can delete content.";
        }

        Content contentToDelete = getContentController().findContentById(reportedContentId);
        if (contentToDelete != null) {
            database.getContents().remove(contentToDelete);
            return "Reported content deleted successfully.";
        }
        return "Content not found.";
    }

    private User findUserById(int userId) {
        for (User user : database.getUsers()) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }
    private Report findReport(int contentId){
        for (Report report: database.getReports()){
            if (report.getReportedContentId()==contentId) {
                return report;
            }
        }
        return null;
    }

}





