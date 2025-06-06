package com.example.videoplayer.Panel;

import com.example.videoplayer.Controller.AuthController;
import com.example.videoplayer.Controller.ChannelController;
import com.example.videoplayer.Controller.UserController;
import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.Channel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class ChannelPanel {

    public static Stage ctrlStage;
    @FXML
    private Button channelBtn;

    @FXML
    private ImageView channelCover;

    @FXML
    private Label channelInfo;

    @FXML
    private Button homeBtn;

    @FXML
    private Button libraryBtn;

    @FXML
    private AnchorPane overlayPane;

    @FXML
    private Button subscriptionBtn;

    private UserController userController = UserController.getInstance();

    @FXML
    public void initialize() {
        Account loggedInUser = AuthController.getInstance().getLoggedInUser();
        if (loggedInUser instanceof User user) {
            Channel channel = user.getChannel();

            if (channel != null) {
                String coverLink = channel.getChannelCover();
                if (coverLink != null && !coverLink.isEmpty()) {
                    Image image = new Image(coverLink);
                    Circle clip = new Circle(50, 50, 50);
                    channelCover.setClip(clip);
                    channelCover.setImage(image);
                }
                channelInfo.setText(ChannelController.getInstance().viewChannelInfo());
            } else {
                channelInfo.setText("No channel found.");
            }
        }
    }

    @FXML
    void handleEditChannelName() {
        Account loggedInUser = AuthController.getInstance().getLoggedInUser();
        if (loggedInUser instanceof User user) {
            Channel channel = user.getChannel();
            TextInputDialog dialog = new TextInputDialog(channel.getChannelName());
            dialog.setTitle("Edit Channel Name");
            dialog.setHeaderText("Enter new channel name:");
            dialog.setContentText("Name:");

            dialog.showAndWait().ifPresent(newName -> {
                if (!newName.isBlank()) {
                    Channel updated = userController.editChannelName(newName);
                    if (updated != null) {
                        showAlert("Channel name updated successfully.", Alert.AlertType.INFORMATION);
                        loadChannelInfo();
                    } else {
                        showAlert("Failed to update channel name.", Alert.AlertType.ERROR);
                    }
                } else {
                    showAlert("Channel name cannot be empty.", Alert.AlertType.WARNING);
                }
            });
        }
    }

    private void loadChannelInfo() {
        Account loggedInUser = AuthController.getInstance().getLoggedInUser();
        if (loggedInUser instanceof User user) {
            Channel channel = user.getChannel();
            channelInfo.setText(ChannelController.getInstance().viewChannelInfo());

        }
    }

    @FXML
    void handleEditDescription() {
        Account loggedInUser = AuthController.getInstance().getLoggedInUser();
        if (loggedInUser instanceof User user) {
            Channel channel = user.getChannel();
            TextInputDialog dialog = new TextInputDialog(channel.getDescription());
            dialog.setTitle("Edit Channel Description");
            dialog.setHeaderText("Enter new description:");
            dialog.setContentText("Description:");

            dialog.showAndWait().ifPresent(newDescription -> {
                if (!newDescription.isBlank()) {
                    Channel updated = UserController.getInstance().editChannelDescription(newDescription);
                    if (updated != null) {
                        showAlert("Channel description updated successfully.", Alert.AlertType.INFORMATION);
                        loadChannelInfo();
                    } else {
                        showAlert("Failed to update description.", Alert.AlertType.ERROR);
                    }
                } else {
                    showAlert("Description cannot be empty.", Alert.AlertType.WARNING);
                }
            });
        }
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void goToChannelPlaylistPanel(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/channelPlaylist-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();
    }


    @FXML
    void showSubscribers(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/subscribers.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();

    }


    @FXML
    void showCreatePlaylistForChannel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/createPlaylistForChannel.fxml"));
            AnchorPane popup = loader.load();
            CreatePlaylistForChannelPanel controller = loader.getController();
            controller.setChannelPanel(this);

            popup.setLayoutX((overlayPane.getPrefWidth() - popup.getPrefWidth()) / 2);
            popup.setLayoutY((overlayPane.getPrefHeight() - popup.getPrefHeight()) / 2);

            overlayPane.getChildren().clear();
            overlayPane.getChildren().add(popup);
            overlayPane.setVisible(true);
            overlayPane.setManaged(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeOverlay() {
        overlayPane.getChildren().clear();
        overlayPane.setVisible(false);
        overlayPane.setManaged(false);
    }

    @FXML
    public void goToLibrary(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/library-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();

    }

    @FXML
    public void goToHome(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();

    }

    @FXML
    public void goToChannel(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/channel-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();

    }

    @FXML
    public void goToSubscription(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/subscription-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();

    }

}