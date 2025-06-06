package com.example.videoplayer.Panel;

import com.example.videoplayer.Controller.AuthController;
import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.ContentPck.Content;
import com.example.videoplayer.Model.Playlist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlaylistPanel {
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

        public void initialize() {
            refreshUserPlaylists();
        }

        public void refreshUserPlaylists() {
            Account loggedInUser = AuthController.getInstance().getLoggedInUser();

            if (loggedInUser instanceof User) {
                ArrayList<Playlist> playlists = ((User) loggedInUser).getPlaylists();
                loadPlaylists(playlists);
            } else {
                playlistContainer.getChildren().clear();
                Label noUserLabel = new Label("No user logged in or invalid account.");
                playlistContainer.getChildren().add(noUserLabel);
            }
        }

        private void loadPlaylists(ArrayList<Playlist> playlists) {
            playlistContainer.getChildren().clear();

            for (Playlist playlist : playlists) {
                VBox contentBox = new VBox(5);

                for (Content content : playlist.getContents()) {
                    HBox contentRow = new HBox(10);
                    contentRow.setAlignment(Pos.CENTER_LEFT);
                    contentRow.setStyle("-fx-padding: 5 10; -fx-background-color: #f4f4f4; -fx-cursor: hand;");

                    ImageView thumbnailView = new ImageView(new Image(new File(content.getThumbnail()).getAbsolutePath()));
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
                            Scene scene = new Scene(loader.load(), 800, 900);
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

    }
