package com.example.videoplayer.Panel;

import com.example.videoplayer.Controller.AuthController;
import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.Channel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SubscribersPanel {
    @FXML
    private ListView<String> userListView;

    private ArrayList<User> subscribers;
    public static Stage ctrlStage;

    @FXML
    public void initialize() {
        Account loggedInUser = AuthController.getInstance().getLoggedInUser();

        if (!(loggedInUser instanceof User)) {
            showAlert("Access Denied", "You must be logged in to view subscriptions.");
            return;
        }

        User user = (User) loggedInUser;
        subscribers =user.getChannel().getSubscribers();

        if (subscribers == null || subscribers.isEmpty()) {
            showAlert("No Subscribers", "You have no subscribers.");
        } else {
            for (User user1 : subscribers) {
                userListView.getItems().add(user1.getUsername());
            }
        }

       userListView.setOnMouseClicked(this::handleChannelSelection);
    }

    private void handleChannelSelection(MouseEvent event) {
        String selectedName = userListView.getSelectionModel().getSelectedItem();
        if (selectedName == null) return;

        for (User u : subscribers) {
            if (u.getFullName().equals(selectedName)) {
                System.out.println("Navigating to user: " + u.getFullName());
                break;
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
