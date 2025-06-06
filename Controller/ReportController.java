package com.example.videoplayer.Controller;


import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.ContentPck.Content;
import com.example.videoplayer.Model.Report;
import com.example.videoplayer.Model.Database;

public class ReportController {
    private static ReportController reportController;
    private AuthController authController;
    private Database database;
    private ContentController contentController;

    private ReportController(){
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

    public static ReportController getInstance(){
        if (reportController==null){
            reportController=new ReportController();
        }
        return reportController;
    }
    public String createReport(int reportedContentId, String description) {
        Account loggedInUser = getAuthController().getLoggedInUser();
        if (!(loggedInUser instanceof User)) {
            return "Only users can report content.";
        }
        User user = (User) loggedInUser;

        Content reportedContent = getContentController().findContentById(reportedContentId);
        if (reportedContent == null) {
            return "Content not found.";
        }

        User uploader = reportedContent.getUploader();
        if (uploader == null) {
            return "Uploader not found.";
        }

        Report newReport = new Report(reportedContentId, user, description);
        newReport.setReportedUserId(uploader.getUserId());
        database.getReports().add(newReport);
        return "Report submitted successfully.";
    }

}
