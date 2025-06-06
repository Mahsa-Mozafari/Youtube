package com.example.videoplayer.Panel;

import com.example.videoplayer.Controller.AuthController;
import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.Channel;
import com.example.videoplayer.Model.Playlist;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class userChannelPanel {
    @FXML
    private Label channelNameLabel, channelDescriptionLabel, followersLabel;

    @FXML
    private VBox playlistContainer;


    private void addPlaylistCardToUI(String playlistName) {
        HBox playlistCard = new HBox();
        playlistCard.setSpacing(10);
        playlistCard.setPadding(new Insets(10));
        playlistCard.setStyle("-fx-background-color: #2f2f2f; -fx-background-radius: 8;");

        Label nameLabel = new Label(playlistName);
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16;");

        playlistCard.getChildren().add(nameLabel);
        playlistContainer.getChildren().add(playlistCard);
    }

    public void initializeChannelInfo() {
        Account loggedInUser = AuthController.getInstance().getLoggedInUser();
        if (!(loggedInUser instanceof User)) return;

        User user = (User) loggedInUser;
        Channel userChannel = user.getChannel();
        if (userChannel == null) return;

        channelNameLabel.setText(userChannel.getChannelName());
        channelDescriptionLabel.setText(userChannel.getDescription());
        followersLabel.setText(userChannel.getSubscribers().size() + " followers");

        for (Playlist p : userChannel.getPlaylists()) {
            addPlaylistCardToUI(p.getPlaylistName());
        }
    }
}
