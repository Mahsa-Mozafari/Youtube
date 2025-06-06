package com.example.videoplayer.Panel;
import com.example.videoplayer.Controller.AdminController;
import com.example.videoplayer.Controller.ContentController;
import com.example.videoplayer.Model.AccountPck.User;
import com.example.videoplayer.Model.Channel;
import com.example.videoplayer.Model.ContentPck.Content;
import com.example.videoplayer.Model.Database;
import com.example.videoplayer.Model.Report;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.io.IOException;
import java.util.ArrayList;


public class AdminPanel {


    public static Stage ctrlStage;
    Database database = Database.getInstance();

    @FXML
    private Button unbanButton;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> usernameCol, fullNameCol, emailCol, phoneCol;

    @FXML
    private TableView<Content> contentTable;
    @FXML
    private TableColumn<Content, String> titleCol, uploaderCol;
    @FXML
    private TableColumn<Content, Integer> viewsCol, likesCol;
    @FXML
    private BarChart<String, Number> contentBarChart;

    @FXML
    private BarChart<String, Number> channelBarChart;
    @FXML
    private TableView<Channel> channelTable;
    @FXML
    private TableColumn<Channel, String> channelNameCol, creatorCol;
    @FXML
    private TableColumn<Channel, Integer> subsCol;

    @FXML
    private TableView<Report> reportTable;
    @FXML
    private TableColumn<Report, String> reporterCol, reportedContentCol, descriptionCol;

    private ContentController contentController=ContentController.getInstance();

    @FXML
    public void initialize() {
        unbanButton.setVisible(false);

        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.isBanned()) {
                unbanButton.setVisible(true);
            } else {
                unbanButton.setVisible(false);
            }
        });

        usernameCol.setCellValueFactory(data -> {
            User user = data.getValue();
            String displayUsername = user.isBanned() ? user.getUsername() + " (BANNED)" : user.getUsername();
            return new javafx.beans.property.SimpleStringProperty(displayUsername);
        });

        fullNameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFullName()));
        emailCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));
        phoneCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPhoneNumber()));

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        uploaderCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUploader().getUsername()));
        viewsCol.setCellValueFactory(new PropertyValueFactory<>("views"));
        likesCol.setCellValueFactory(new PropertyValueFactory<>("likes"));

        channelNameCol.setCellValueFactory(new PropertyValueFactory<>("channelName"));
        creatorCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCreator()));
        subsCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSubscribers().size()).asObject());

        reporterCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReporter().getUsername()));
        reportedContentCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getReportedContentId())));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        refreshTables();

        loadPopularContentsChart();
        loadPopularChannelsChart();
    }

    public void loadPopularContentsChart() {
        ArrayList<Content> contents = contentController.sortContentByLikes();

        XYChart.Series<String, Number> likesSeries = new XYChart.Series<>();
        likesSeries.setName("Likes");

        for (Content content : contents) {
            likesSeries.getData().add(new XYChart.Data<>(content.getTitle(), content.getLikes()));
        }

        contentBarChart.getData().clear();
        contentBarChart.getData().add(likesSeries);
    }

    public void loadPopularChannelsChart() {
        ArrayList<Channel> channels = new ArrayList<>(database.getChannels());

        channels.sort((c1, c2) -> Integer.compare(c2.getSubscribers().size(), c1.getSubscribers().size()));

        XYChart.Series<String, Number> subscriberSeries = new XYChart.Series<>();
        subscriberSeries.setName("Subscribers");

        for (Channel channel : channels) {
            subscriberSeries.getData().add(new XYChart.Data<>(channel.getChannelName(), channel.getSubscribers().size()));
        }

        channelBarChart.getData().clear();
        channelBarChart.getData().add(subscriberSeries);
    }

    @FXML
    private void handleConfirmReport() {
        Report report = reportTable.getSelectionModel().getSelectedItem();
        if (report != null) {
            String result = AdminController.getInstance().confirmReport(report.getReportedContentId());
            showAlert(result);
            refreshTables();
        } else {
            showAlert("Choose Report");
        }
    }

    @FXML
    private void handleRejectReport() {
        Report report = reportTable.getSelectionModel().getSelectedItem();
        if (report != null) {
            String result = AdminController.getInstance().rejectReport(report.getReportedContentId());
            showAlert(result);
            reportTable.getItems().setAll(database.getReports());
        } else {
            showAlert("Choose Report");
        }
    }

    @FXML
    private void handleUnbanUser() {
        User selected = userTable.getSelectionModel().getSelectedItem();

        if (selected != null && selected.isBanned()) {
            selected.setBanned(false);
            showAlert("User " + selected.getUsername() + " has been unbanned.");
            refreshTables();
        } else {
            showAlert("Please select a banned user to unban.");
        }
    }

    @FXML
    private void handleBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/login-view.fxml"));
        Scene scene = new Scene(loader.load(), 900, 500);
        ctrlStage.setScene(scene);
        ctrlStage.show();
    }

    private void refreshTables() {
        userTable.getItems().setAll(database.getUsers());
        contentTable.getItems().setAll(database.getContents());
        channelTable.getItems().setAll(database.getChannels());
        reportTable.getItems().setAll(database.getReports());
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}