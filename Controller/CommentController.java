package com.example.videoplayer.Controller;


import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.ContentPck.Content;
import com.example.videoplayer.Model.Database;
import com.example.videoplayer.Model.Comment;

public class CommentController {
    private Database database;
    private static CommentController commentController;
    private AuthController authController;

    public static  CommentController getInstance(){
        if (commentController==null){
           commentController=new CommentController();
        }
        return commentController;
    }

    private CommentController(){
        this.database=Database.getInstance();
    }

    public AuthController getAuthController() {
        if (authController == null) {
            authController = AuthController.getInstance();
        }
        return authController;
    }

    public boolean addComment (int contentId, String text){
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return false;
        }

        User user = (User) loggedInUser;

        for (Content content : database.getContents()) {
            if (content.getContentId() == contentId) {
                Comment comment = new Comment(user);
                comment.setCommentText(text);
                content.getComments().add(comment);
                return true;
            }
        }

        return false;
    }
}