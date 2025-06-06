package com.example.videoplayer.Controller;


import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.AccountPck.Admin;
import com.example.videoplayer.Model.AccountPck.RegularUser;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.Database;

public class AuthController {
    private static AuthController authController;
    private Database database;
    private Account loggedInUser;
    private RegularUser signUpUser;

    private AuthController() {
        this.database = Database.getInstance();
        this.loggedInUser=null;
    }
    public static AuthController getInstance(){
        if (authController==null){
            authController=new AuthController();
        }
        return authController;
    }


    public Account getLoggedInUser() {
        return loggedInUser;
    }

    public RegularUser getSignUpUser() {
        return signUpUser;
    }

    public String signup(String username, String password, String fullName, String email, String phoneNumber, String profileCoverLink) {
        if (searchForUsername(username) != null) {
            return "duplicate_username";
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return "invalid_email";
        }

        if (!phoneNumber.matches("^(\\+98|0)?9\\d{9}$")) {
            return "invalid_phone";
        }

        if (!password.matches(".{8,}")) {
            return "invalid_password";
        }

        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSymbol = password.matches(".*[@#$%^&+=!].*");

        if (!(hasUpper && hasLower && hasDigit && hasSymbol)) {
            return "weak_password";
        }

        signUpUser = new RegularUser(username, fullName, phoneNumber, email, profileCoverLink);
        signUpUser.setPassword(password);

        database.getUsers().add(signUpUser);

        return "success";
    }

    public String login(String username, String password) {
        if ("Mahsa123".equals(username) && "Ma123".equals(password)) {
            loggedInUser = Admin.getInstance();
            return "success";
        }

        Account account = searchForUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            if (account instanceof User && ((User) account).isBanned()) {
                return "banned_user";
            }
            loggedInUser = account;
            return "success";
        }
        return "invalid";
    }

    public boolean logout() {
       if(loggedInUser!=null){
           loggedInUser=null;
           return true;
       }
       return false;
    }

    public Account searchForUsername(String username){
        for (User user : database.getUsers()){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

}
