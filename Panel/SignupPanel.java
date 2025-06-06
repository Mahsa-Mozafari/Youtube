package com.example.videoplayer.Panel;

import com.example.videoplayer.Controller.AuthController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

public class SignupPanel {
    public static Stage ctrlStage;

    @FXML
    private TextField emailTxt;


    @FXML
    private TextField fullNameTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField phoneNumberTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    private ImageView profileImageView;

    private File selectedProfilePicture;

    @FXML
    public void initialize() {
        Circle clip = new Circle(50, 50, 50);
        profileImageView.setClip(clip);
    }

    @FXML
    private void chooseProfilePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        Stage stage = (Stage) profileImageView.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectedProfilePicture = file;
            Image image = new Image(file.toURI().toString());
            profileImageView.setImage(image);
        }
    }

    @FXML
    void signupAct(ActionEvent event) throws IOException {
        String profileCoverLink=selectedProfilePicture!=null?selectedProfilePicture.toURI().toString():null;
        String result = AuthController.getInstance().signup(usernameTxt.getText(), passwordTxt.getText(),fullNameTxt.getText(),emailTxt.getText(),phoneNumberTxt.getText(),profileCoverLink);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        switch (result) {
            case "duplicate_username":
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Signup Failed");
                alert.setContentText("Username is already taken.");
                alert.show();
                break;
            case "invalid_email":
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Email");
                alert.setContentText("Please enter a valid email address.");
                alert.show();
                break;
            case "invalid_phone":
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Phone");
                alert.setContentText("Please enter a valid phone number.");
                alert.show();
                break;
            case "invalid_password":
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Password");
                alert.setContentText("Password must be at least 8 characters.");
                alert.show();
                break;
            case "weak_password":
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Weak Password");
                alert.setContentText("Password must contain uppercase, lowercase, digit, and special character.");
                alert.show();
                break;
            case "success":
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/category-view.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
                        SignupPanel.ctrlStage.setScene(scene);
                        SignupPanel.ctrlStage.show();
                break;
        }
    }
    @FXML
    void backToStart(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();

    }
}


