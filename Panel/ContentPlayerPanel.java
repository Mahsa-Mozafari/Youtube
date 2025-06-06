package com.example.videoplayer.Panel;

import com.example.videoplayer.Controller.AuthController;
import com.example.videoplayer.Controller.CommentController;
import com.example.videoplayer.Controller.ContentController;
import com.example.videoplayer.Controller.ReportController;
import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.Comment;
import com.example.videoplayer.Model.ContentPck.Content;
import com.example.videoplayer.Model.Playlist;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class ContentPlayerPanel implements Initializable {
    private MediaPlayer mediaPlayer;
    @FXML
    private Label titleLabel;
    @FXML
    MediaView mediaView;
    @FXML private Label likesLabel;
    @FXML private Label viewsLabel;
    @FXML private ImageView thumbnailImg;
    @FXML private ImageView likeBtn, dislikeBtn, reportBtn;
    @FXML private Button sendCommentBtn;
    @FXML private TextField commentTextField;
    @FXML private ListView<String> commentListView;
    @FXML private MenuButton addToPlaylistMenu;
    @FXML private ImageView downButton;
    @FXML
    private ImageView pauseButton;
    @FXML
    private ImageView playButton;
    @FXML
    private ImageView stopButton;
    @FXML
    private StackPane audioStackPane;
    @FXML
    private ImageView muteImage;
    @FXML
    private ImageView soundImage;
    @FXML
    private Label timeLabel;
    @FXML
    private Slider videoSlider, volumeSlider;
    private Content currentContent;
    private boolean isMuted = false;
    public static Stage ctrlStage;


    public void setContent(Content content) {
        this.currentContent = content;
        setupAddToPlaylistMenu();
        playMedia(content.getFileLink());
        updateUI();

        ContentController.getInstance().playContent(content.getContentId());
        viewsLabel.setText("Views: " + content.getViews());
    }

    private void updateUI() {
        titleLabel.setText(currentContent.getTitle());
        likesLabel.setText("Likes: " + currentContent.getLikes());
        viewsLabel.setText("Views: " + currentContent.getViews());


        commentListView.getItems().clear();
        for (Comment comment : currentContent.getComments()) {
            commentListView.getItems().add(comment.getCommenter().getUsername() + ": " + comment.getCommentText());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupButtons();
        setupSliders();
    }
    private void setupButtons(){
        likeBtn.setOnMouseClicked(e -> {
            ContentController.getInstance().likeContent(currentContent.getContentId());
            updateUI();
        });

        dislikeBtn.setOnMouseClicked(e -> {
            ContentController.getInstance().dislikeContent(currentContent.getContentId());
            updateUI();
        });

        reportBtn.setOnMouseClicked(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Report Content");
            dialog.setHeaderText("Why are you reporting this content?");
            dialog.setContentText("Description:");
            dialog.showAndWait().ifPresent(desc -> {
                String result = ReportController.getInstance().createReport(currentContent.getContentId(), desc);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, result);
                alert.show();
            });
        });

        sendCommentBtn.setOnMouseClicked(e -> {
            String text = commentTextField.getText().trim();
            if (!text.isEmpty()) {
                boolean added = CommentController.getInstance().addComment(currentContent.getContentId(), text);
                if (added) {
                    commentTextField.clear();
                    updateUI();
                }
            }
        });

        playButton.setOnMouseClicked(e -> {
            if (mediaPlayer != null) mediaPlayer.play();
            updateUI();
        });

        pauseButton.setOnMouseClicked(e -> {
            if (mediaPlayer != null) mediaPlayer.pause();
            updateUI();
        });

        stopButton.setOnMouseClicked(e -> {
            if (mediaPlayer != null) mediaPlayer.stop();
            updateUI();
        });
    }

    private void setupSliders() {
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (mediaPlayer != null && !isMuted) {
                mediaPlayer.setVolume(newVal.doubleValue() / 100);
            }
        });

        videoSlider.setOnMouseReleased(e -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(videoSlider.getValue()));
            }
        });
    }




    @FXML
    private void toggleMute() {
        if (mediaPlayer != null) {
            isMuted = !isMuted;
            mediaPlayer.setVolume(isMuted ? 0 : volumeSlider.getValue() / 100);
            muteImage.setVisible(isMuted);
            soundImage.setVisible(!isMuted);
        }
    }

    private void playMedia(String filePath) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        Media media = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.setOnReady(() -> {
            videoSlider.setMax(mediaPlayer.getMedia().getDuration().toSeconds());

            boolean hasVideo = media.getWidth() > 0;

            if (hasVideo) {
                mediaView.setVisible(true);
                thumbnailImg.setVisible(false);
            } else {
                mediaView.setVisible(false);
                thumbnailImg.setVisible(true);

                String path = currentContent.getThumbnail();
                File file = new File(path);
                URI uri = file.toURI();
                Image image = new Image(uri.toString());
                thumbnailImg.setImage(image);
            }
        });

        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.stop();
            videoSlider.setValue(0);
            timeLabel.setText("00:00");
        });

        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            videoSlider.setValue(newTime.toSeconds());
            timeLabel.setText(String.format("%02d:%02d",
                    (int) newTime.toMinutes(),
                    (int) newTime.toSeconds() % 60));
        });

        mediaPlayer.play();
    }

    private void setupAddToPlaylistMenu() {
        addToPlaylistMenu.getItems().clear();

        Account loggedInUser = AuthController.getInstance().getLoggedInUser();
        if (!(loggedInUser instanceof User)) return;

        User user = (User) loggedInUser;
        for (Playlist playlist : user.getPlaylists()) {
            String name = playlist.getPlaylistName();
            if (name.equalsIgnoreCase("Liked") || name.equalsIgnoreCase("Watch Later"))
                continue;

            MenuItem item = new MenuItem(name);
            item.setOnAction(e -> {
                if (!playlist.getContents().contains(currentContent)) {
                    playlist.getContents().add(currentContent);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Added to playlist: " + name);
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Content already exists in playlist: " + name);
                    alert.show();
                }
            });
            addToPlaylistMenu.getItems().add(item);
        }
    }

    @FXML
    public void backToHome(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();
    }
}