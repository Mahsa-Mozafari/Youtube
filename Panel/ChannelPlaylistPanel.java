package com.example.videoplayer.Panel;

import com.example.videoplayer.Controller.AuthController;
import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.Channel;
import com.example.videoplayer.Model.ContentPck.Content;
import com.example.videoplayer.Model.Playlist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ChannelPlaylistPanel {
    public static Stage ctrlStage;

    @FXML
    private Button channelBtn;
    @FXML
    private Button homeBtn;
    @FXML
    private Button libraryBtn;
    @FXML
    private VBox playlistContainer;
    @FXML
    private Button subscriptionBtn;

    private Playlist selectedPlaylist;

    public void initialize() {
        refreshChannelPlaylists();
    }

    public void refreshChannelPlaylists() {
        Account loggedInUser = AuthController.getInstance().getLoggedInUser();

        if (loggedInUser instanceof User user) {
            Channel channel = user.getChannel();

            if (channel != null) {
                ArrayList<Playlist> playlists = channel.getPlaylists();
                loadChannelPlaylists(playlists);
            } else {
                playlistContainer.getChildren().clear();
                Label noChannelLabel = new Label("You don't have a channel yet.");
                playlistContainer.getChildren().add(noChannelLabel);
            }
        } else {
            playlistContainer.getChildren().clear();
            Label noUserLabel = new Label("No user logged in or invalid account.");
            playlistContainer.getChildren().add(noUserLabel);
        }
    }
    public void loadChannelPlaylists(ArrayList<Playlist> playlists) {
        playlistContainer.getChildren().clear();

        for (Playlist playlist : playlists) {
            VBox contentBox = new VBox(5);

            for (Content content : playlist.getContents()) {
                HBox contentRow = new HBox(10);
                contentRow.setAlignment(Pos.CENTER_LEFT);
                contentRow.setStyle("-fx-padding: 5 10; -fx-background-color: #f4f4f4; -fx-cursor: hand;");

                ImageView thumbnailView = new ImageView(new Image(new File(content.getThumbnail()).toURI().toString()));
                thumbnailView.setFitHeight(50);
                thumbnailView.setFitWidth(80);

                Label contentLabel = new Label(content.getTitle());
                contentLabel.setStyle("-fx-font-size: 24px;");
                contentLabel.setWrapText(true);
                contentLabel.setMaxWidth(600);

                contentRow.getChildren().addAll(thumbnailView, contentLabel);
                thumbnailView.setOnMouseClicked(e -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/contentPlayer-view.fxml"));
                        Scene scene = new Scene(loader.load(),800,900);
                        ContentPlayerPanel controller = loader.getController();
                        controller.setContent(content);
                        ctrlStage.setScene(scene);
                        ctrlStage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                contentBox.getChildren().add(contentRow);
            }

            Button publishBtn = new Button("Add Content");
            publishBtn.setOnAction(e -> {
                selectedPlaylist = playlist;
                try {
                    goToPublishPanel();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            if (!playlist.getPlaylistName().equalsIgnoreCase("All Contents")) {
                contentBox.getChildren().add(publishBtn);
            }

            TitledPane pane = new TitledPane(playlist.getPlaylistName(), contentBox);
            playlistContainer.getChildren().add(pane);
        }
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


    public void goToPublishPanel() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/publish-view.fxml"));
        Parent root = fxmlLoader.load();

        PublishPanel publishPanel = fxmlLoader.getController();
        publishPanel.setSelectedPlaylist(selectedPlaylist);

        Scene scene = new Scene(root, 574, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();
    }




}
