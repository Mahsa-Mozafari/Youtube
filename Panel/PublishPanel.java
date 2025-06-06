
package com.example.videoplayer.Panel;

import com.example.videoplayer.Controller.AuthController;
import com.example.videoplayer.Controller.ChannelController;
import com.example.videoplayer.Model.AccountPck.Account;
import com.example.videoplayer.Model.Category;
import com.example.videoplayer.Model.ContentPck.ContentSpecialStatus;
import com.example.videoplayer.Model.ContentPck.VideoFormat;
import com.example.videoplayer.Model.ContentPck.VideoResolution;
import com.example.videoplayer.Model.Playlist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;


import javafx.scene.control.*;
import javafx.stage.Stage;

public class PublishPanel {

    public static Stage ctrlStage;

    @FXML
    private Label videoPathLabel;

    @FXML
    private Button chooseLiveBtn;

    @FXML
    private Button chooseLiveThumbnailBtn;

    @FXML
    private Button choosePodcastBtn;

    @FXML
    private Button choosePodcastThumbnailBtn;

    @FXML
    private Button chooseShortBtn;

    @FXML
    private Button chooseShortThumbnailBtn;

    @FXML
    private Button chooseVideoBtn;

    @FXML
    private Button chooseVideoThumbnailBtn;

    @FXML
    private TextField descriptionLive;

    @FXML
    private TextField descriptionPodcast;

    @FXML
    private TextField descriptionShort;

    @FXML
    private TextField descriptionVideo;

    @FXML
    private TextField creatorPodcast;

    @FXML
    private TextField durationLive;

    @FXML
    private TextField durationPodcast;

    @FXML
    private TextField durationShort;

    @FXML
    private TextField durationVideo;

    @FXML
    private ComboBox<String> liveCategoryComboBox;

    @FXML
    private Label livePathLabel;

    @FXML
    private ComboBox<String> liveSpecialStatusComoBox3;

    @FXML
    private Label liveThumbnailPathLabel;

    @FXML
    private TextField musicRefField;

    @FXML
    private ComboBox<String> podcastCategoryComboBox;

    @FXML
    private Label podcastPathLabel;

    @FXML
    private ComboBox<String> podcastSpecialStatusComoBox;

    @FXML
    private Label podcastThumbnailPathLabel;

    @FXML
    private DatePicker schedulePicker;

    @FXML
    private ComboBox<String> shortCategoryComboBox;

    @FXML
    private Label shortPathLabel;

    @FXML
    private ComboBox<String> shortSpecialStatusComoBox;

    @FXML
    private Label shortThumbnailPathLabel;

    @FXML
    private TextField titleLive;

    @FXML
    private TextField titlePodcast;

    @FXML
    private TextField titleShort;

    @FXML
    private TextField titleVideo;

    @FXML
    private ComboBox<String> videoCategoryComboBox;

    @FXML
    private ComboBox<String> videoFormatComboBox;

    @FXML
    private ComboBox<String> videoResolutionComboBox;

    @FXML
    private ComboBox<String> videoSpecialStatusComoBox;

    @FXML
    private Label videoThumbnailPathLabel;

    private Playlist selectedPlaylist;
    private Category selectedCategory;
    private VideoResolution selectedResolution;
    private VideoFormat selectedFormat;
    private ContentSpecialStatus selectedStatus;

    private File selectedFile;
    private File selectedThumbnail;

    @FXML
    void initialize() {
        videoCategoryComboBox.getItems().addAll(" News","Game","Podcast","Music","Live","Society","History");
        videoCategoryComboBox.setOnAction(event -> selectCategoryForVideo());

        podcastCategoryComboBox.getItems().addAll(" News","Game","Podcast","Music","Live","Society","History");
        podcastCategoryComboBox.setOnAction(event -> selectCategoryForPodcast());

        videoSpecialStatusComoBox.getItems().addAll("Normal","Special");
        videoSpecialStatusComoBox.setOnAction(event -> selectSpecialStatusForVideo());

       podcastSpecialStatusComoBox.getItems().addAll("Normal","Special");
        podcastCategoryComboBox.setOnAction(event -> selectSpecialStatusForPodcast());

        videoResolutionComboBox.getItems().addAll("480","720p", "1080p");
        videoResolutionComboBox.setOnAction(event -> selectResolution());

        videoFormatComboBox.getItems().addAll(" MP4","MKV","MOV","WMV");
        videoFormatComboBox.setOnAction(event -> selectFormat());
    }
    @FXML
    private void selectCategoryForVideo() {
        String category = videoCategoryComboBox.getValue();
        if ("Music".equals(category)) {
            selectedCategory = Category.Music;
        } else if ("Game".equals(category)) {
            selectedCategory = Category.Game;
        } else if ("History".equals(category)) {
            selectedCategory = Category.History;
        } else if ("Live".equals(category)) {
            selectedCategory = Category.Live;
        } else if ("News".equals(category)) {
            selectedCategory = Category.News;
        } else if ("Podcast".equals(category)) {
            selectedCategory = Category.Podcast;
        } else if ("Society".equals(category)) {
            selectedCategory = Category.Society;
        }
    }
@FXML
    private void selectCategoryForPodcast() {
        String category = podcastCategoryComboBox.getValue();
        if ("Music".equals(category)) {
            selectedCategory = Category.Music;
        } else if ("Game".equals(category)) {
            selectedCategory = Category.Game;
        } else if ("History".equals(category)) {
            selectedCategory = Category.History;
        } else if ("Live".equals(category)) {
            selectedCategory = Category.Live;
        } else if ("News".equals(category)) {
            selectedCategory = Category.News;
        } else if ("Podcast".equals(category)) {
            selectedCategory = Category.Podcast;
        } else if ("Society".equals(category)) {
            selectedCategory = Category.Society;
        }
    }


    private void selectResolution() {
        String resolution = videoResolutionComboBox.getValue();
        if ("480".equals(resolution)) {
            selectedResolution = VideoResolution.LOW;
        } else if ("720".equals(resolution)) {
            selectedResolution = VideoResolution.MEDIUM;
        }else if ("1080".equals(resolution)) {
            selectedResolution = VideoResolution.HIGH;
        }
    }


    private void selectFormat() {
        String format = videoFormatComboBox.getValue();
        if ("MP4".equals(format)) {
            selectedFormat = VideoFormat.MP4;
        } else if ("MKV".equals(format)) {
            selectedFormat = VideoFormat.MKV;
        }else if ("MOV".equals(format)) {
            selectedFormat = VideoFormat.MOV;
        }else if ("WMV".equals(format)) {
            selectedFormat = VideoFormat.WMV;
        }
    }
@FXML
    private void selectSpecialStatusForVideo() {
        String status = videoSpecialStatusComoBox.getValue();
        if ("Special".equals(status)) {
            selectedStatus = ContentSpecialStatus.SPECIAL;
        } else if ("Normal".equals(status)) {
           selectedStatus = ContentSpecialStatus.NOT_SPECIAL;
        }
    }
@FXML
    private void selectSpecialStatusForPodcast() {
        String status = podcastSpecialStatusComoBox.getValue();
        if ("Special".equals(status)) {
            selectedStatus = ContentSpecialStatus.SPECIAL;
        } else if ("Normal".equals(status)) {
            selectedStatus = ContentSpecialStatus.NOT_SPECIAL;
        }
    }

    @FXML
    void handleChooseFileForVideo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Content");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("video/audio", "*.mp4", "*.mp3", "*.wav", "*.avi")
        );
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
           videoPathLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    void handleChooseThumbnailForVideo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Cover");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.jpeg", "*.png")
        );
        selectedThumbnail = fileChooser.showOpenDialog(null);
        if (selectedThumbnail != null) {
           videoThumbnailPathLabel.setText(selectedThumbnail.getAbsolutePath());
       }
    }

    @FXML
    void handleChooseFileForPodcast(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Content");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("audio", "*.m4a", "*.mp3", "*.wav", "*.aac" , "*.ogg"));
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            podcastPathLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    void handleChooseThumbnailForPodcast(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Cover");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.jpeg", "*.png")
        );
        selectedThumbnail = fileChooser.showOpenDialog(null);
        if (selectedThumbnail != null) {
            podcastThumbnailPathLabel.setText(selectedThumbnail.getAbsolutePath());
        }
    }


    @FXML
    void publishPodcast(ActionEvent event) throws IOException {
        Account user = AuthController.getInstance().getLoggedInUser();
        String msg = ChannelController.getInstance().publishPodcast(
                selectedStatus,
                titlePodcast.getText(),
                descriptionPodcast.getText(),
                Integer.parseInt(durationPodcast.getText()),
                selectedCategory,
                selectedFile.getAbsolutePath(),
                selectedThumbnail.getAbsolutePath(),
                creatorPodcast.getText(), selectedPlaylist

        );
        showAlert(msg);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/channelPlaylist-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();
    }

    @FXML
    void publishNormalVideo(ActionEvent event) throws IOException {
        Account user = AuthController.getInstance().getLoggedInUser();
        String msg = ChannelController.getInstance().publishNormalVideo(
                selectedStatus,
                titleVideo.getText(),
                descriptionVideo.getText(),
                Integer.parseInt(durationVideo.getText()),
                selectedCategory,
                selectedFile.getAbsolutePath(),
                selectedThumbnail.getAbsolutePath(),selectedResolution,
                selectedFormat,selectedPlaylist

        );
        showAlert(msg);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/channelPlaylist-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    public Playlist getSelectedPlaylist() {
        return selectedPlaylist;
    }

    public void setSelectedPlaylist(Playlist selectedPlaylist) {
        this.selectedPlaylist = selectedPlaylist;
    }

    @FXML
    public void backToPlaylistPanel() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/channelPlaylist-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();
    }
}


