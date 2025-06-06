package com.example.videoplayer.Panel;

import com.example.videoplayer.Controller.AuthController;
import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.AccountPck.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPanel {

public static Stage ctrlStage;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField usernameText;

    @FXML
    void backToStart(ActionEvent event)throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();
    }

    @FXML
    void loginAct(ActionEvent event) throws IOException {
        String result = AuthController.getInstance().login(usernameText.getText(), passwordText.getText());

        if (result.equals("success")) {
            Account user = AuthController.getInstance().getLoggedInUser();
            if (user instanceof Admin) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/admin-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                ctrlStage.setScene(scene);
                ctrlStage.show();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/home-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 900, 500);
                ctrlStage.setScene(scene);
                ctrlStage.show();
            }

        } else if (result.equals("banned_user")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Access Denied");
            alert.setHeaderText(null);
            alert.setContentText("You are banned");
            alert.show();
        } else if (result.equals("invalid")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.show();
        }
    }
}
