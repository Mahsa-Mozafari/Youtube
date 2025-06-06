package com.example.videoplayer.Panel;
import com.example.videoplayer.Controller.UserController;
import com.example.videoplayer.Model.Category;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CategoryPanel {

    public static Stage ctrlStage;

    @FXML private CheckBox newsCheck;
    @FXML private CheckBox gameCheck;
    @FXML private CheckBox podcastCheck;
    @FXML private CheckBox musicCheck;
    @FXML private CheckBox liveCheck;
    @FXML private CheckBox societyCheck;
    @FXML private CheckBox historyCheck;

    @FXML private Button submitBtn;

    @FXML
    void submitAct(ActionEvent event) throws IOException {
        List<String> selectedCategories = new ArrayList<>();

        if (newsCheck.isSelected()) selectedCategories.add("News");
        if (gameCheck.isSelected()) selectedCategories.add("Game");
        if (podcastCheck.isSelected()) selectedCategories.add("Podcast");
        if (musicCheck.isSelected()) selectedCategories.add("Music");
        if (liveCheck.isSelected()) selectedCategories.add("Live");
        if (societyCheck.isSelected()) selectedCategories.add("Society");
        if (historyCheck.isSelected()) selectedCategories.add("History");

        if (selectedCategories.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No selection", "Please select at least one category.");
            return;
        }

        if (selectedCategories.size() > 4) {
            showAlert(Alert.AlertType.ERROR, "Too many categories", "You can only select up to 4 categories.");
            return;
        }

        String input = String.join(",", selectedCategories);
        String result = UserController.getInstance().setFavoriteCategories(input);

        if (result.equals("Categories updated.")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/login-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 500);
            LoginPanel.ctrlStage.setScene(scene);
            LoginPanel.ctrlStage.show();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", result);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}