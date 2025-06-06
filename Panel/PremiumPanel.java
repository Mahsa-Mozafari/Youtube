package com.example.videoplayer.Panel;

import com.example.videoplayer.Controller.PremiumController;
import com.example.videoplayer.Controller.UserController;
import com.example.videoplayer.Model.AccountPck.PremiumPackage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class PremiumPanel {

    @FXML
    private ImageView bronzeImage, silverImage, goldImage;

    @FXML private TextField creditField;

    private PremiumPackage selectedPackage = null;
    public static Stage ctrlStage;

    @FXML
    public void initialize() {
        bronzeImage.setOnMouseClicked(e -> selectPackage(PremiumPackage.BRONZE, bronzeImage));
        silverImage.setOnMouseClicked(e -> selectPackage(PremiumPackage.SILVER, silverImage));
        goldImage.setOnMouseClicked(e -> selectPackage(PremiumPackage.GOLD, goldImage));
    }

    private void selectPackage(PremiumPackage pkg, ImageView selectedImage) {
        selectedPackage = pkg;

        bronzeImage.setStyle("");
        silverImage.setStyle("");
        goldImage.setStyle("");
        selectedImage.setStyle("-fx-effect: dropshadow(three-pass-box, red, 10, 0, 0, 0);");
    }

    @FXML
    public void handlePurchase(ActionEvent event) {
        if (selectedPackage == null) {
            showAlert("Please select a package.");
            return;
        }
        String result = UserController.getInstance().buyPremium(selectedPackage);
        showAlert(result);
    }

    @FXML
    public void handleExtend(ActionEvent event) {
        if (selectedPackage == null) {
            showAlert("Please select a package.");
            return;
        }
        String result = PremiumController.getInstance().extendSubscription(selectedPackage);
        showAlert(result);
    }

    @FXML
    public void handleIncreaseCredit(ActionEvent event) {
        try {
            double amount = Double.parseDouble(creditField.getText());
            String result = UserController.getInstance().increaseCredit(amount);
            showAlert(result);
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid number.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Status");
        alert.setHeaderText(null);
        alert.setContentText(msg);
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
