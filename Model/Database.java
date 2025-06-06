package com.example.videoplayer.Model;




import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.ContentPck.Content;

import java.util.ArrayList;


public class Database {
    private static Database database;
    private ArrayList<User> users;
    private ArrayList<Content> contents;
    private ArrayList<Report> reports;
    private ArrayList<Channel> channels;

    private Database() {
        this.users = new ArrayList<>();
        this.contents = new ArrayList<>();
        this.reports = new ArrayList<>();
        this.channels=new ArrayList<>();
    }

    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public ArrayList<Content> getContents() {
        return contents;
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public static Database getDatabase() {
        return database;
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public void setContents(ArrayList<Content> contents) {
        this.contents = contents;
    }

    public static void setDatabase(Database database) {
        Database.database = database;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
