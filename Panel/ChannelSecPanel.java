package com.example.videoplayer.Panel;

import com.example.videoplayer.Controller.AuthController;
import com.example.videoplayer.Controller.UserController;
import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.Channel;
import com.example.videoplayer.Model.ContentPck.Content;
import com.example.videoplayer.Model.Playlist;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ChannelSecPanel {
    @FXML
    private ImageView channelCover;
    @FXML
    private Label channelNameLabel;
    @FXML
    private Label subscriberCountLabel;
    @FXML
    private VBox playlistContainer;
    @FXML
    private Button followButton;

    private Channel viewedChannel;
    public static Stage ctrlStage;

    public void setViewedChannel(Channel channel) {
        this.viewedChannel = channel;
        updateUI();
    }

    private UserController userController = UserController.getInstance();

    private void updateUI() {
        if (viewedChannel == null) return;

        channelNameLabel.setText(viewedChannel.getChannelName());

        String coverLink = viewedChannel.getChannelCover();
        if (coverLink != null && !coverLink.isEmpty()) {
            Image image = new Image(coverLink);
            Circle clip = new Circle(50, 50, 50);
            channelCover.setClip(clip);
            channelCover.setImage(image);
        }

        subscriberCountLabel.setText(viewedChannel.getSubscribers().size() + " subscribers");

        Account loggedIn = AuthController.getInstance().getLoggedInUser();

        if (loggedIn instanceof User user) {
            if (user.getChannel() != viewedChannel) {
                followButton.setVisible(true);
                updateFollowButtonText(user);

                followButton.setOnAction(e -> {
                    if (user.getSubscriptions().contains(viewedChannel)) {
                        userController.unsubscribe(viewedChannel.getChannelId());
                        followButton.setText("Subscribe");
                    } else {
                        userController.subscribe(viewedChannel.getChannelId());
                        followButton.setText("Unsubscribe");
                    }
                    subscriberCountLabel.setText(viewedChannel.getSubscribers().size() + " subscribers");
                });
            } else {
                followButton.setVisible(false);
            }
        } else {
            followButton.setVisible(false);
        }

        loadChannelPlaylists(viewedChannel.getPlaylists());
    }

    private void updateFollowButtonText(User user) {
        if (user.getSubscriptions().contains(viewedChannel)) {
            followButton.setText("Unsubscribe");
        } else {
            followButton.setText("Subscribe");
        }
    }

    private void loadChannelPlaylists(ArrayList<Playlist> playlists) {
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
                        Scene scene = new Scene(loader.load(), 800, 900);
                        ContentPlayerPanel controller = loader.getController();
                        controller.setContent(content);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
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
    void backToHome(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();

    }
}
