package com.example.videoplayer.Panel;

import com.example.videoplayer.Controller.ChannelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class CreateChannelPanel {
    public static Stage ctrlStage;

    @FXML
    private TextField channelNameField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button chooseCoverBtn;

    @FXML
    private Button createChannelBtn;

    @FXML
    private javafx.scene.control.Label selectedCoverLabel;

    private File selectedCoverFile = null;

    @FXML
    private void handleChooseCover() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Cover Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) chooseCoverBtn.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            selectedCoverFile = file;
            selectedCoverLabel.setText(file.getName());
        } else {
            selectedCoverLabel.setText("No file selected");
        }
    }

    @FXML
    private void handleCreateChannel() throws IOException {
        String name = channelNameField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (name.isEmpty() || description.isEmpty() || selectedCoverFile == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Information", "Please fill in all fields and choose a cover image.");
            return;
        }

        String coverPath = selectedCoverFile.getAbsolutePath();
        String result = ChannelController.getInstance().createChannel(name,description,coverPath);
        if (result.equals("Channel created successfully.")) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/channel-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 500);
            ctrlStage.setScene(scene);
            ctrlStage.show();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", result);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
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