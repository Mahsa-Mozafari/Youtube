package com.example.videoplayer.Model.AccountPck;

public class Admin extends Account {
    private static Admin admin;

    public Admin(String username,String password, String fullName, String phoneNumber, String email, String profileCover) {
        super(username, fullName, phoneNumber, email, profileCover);

    }

    public static Admin getInstance() {
        if (admin == null) {
            admin = new Admin("Mahsa123","Ma123","Mahsa Mozafari" ,"000000", "Mahsa@gmail.com"," ");
        }
        return admin;
    }
}

