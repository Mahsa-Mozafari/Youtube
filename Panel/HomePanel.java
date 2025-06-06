package com.example.videoplayer.Panel;

import com.example.videoplayer.Controller.ContentController;
import com.example.videoplayer.Model.Channel;
import com.example.videoplayer.Model.ContentPck.Content;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class HomePanel {
    public static Stage ctrlStage;
    @FXML
    private VBox mainContainer;

    @FXML
    private TextField searchField;

    @FXML
    public void initialize() {
        showSuggestions();
    }

    private void showSuggestions() {
        mainContainer.getChildren().clear();
        Label title = new Label("Suggestions");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        mainContainer.getChildren().add(title);

        ArrayList<Content> suggestions = ContentController.getInstance().getSuggestions();

        for (Content content : suggestions) {
            HBox itemBox = new HBox(10);
            itemBox.setAlignment(Pos.CENTER_LEFT);
            itemBox.setPadding(new Insets(10));
            itemBox.setStyle(
                    "-fx-padding: 15; " +
                            "-fx-background-color: #ffffff; " +
                            "-fx-background-radius: 8; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 6, 0, 0, 2);"
            );

            ImageView thumbnail = new ImageView(new Image("file:" + content.getThumbnail()));
            thumbnail.setFitWidth(160);
            thumbnail.setFitHeight(90);
            thumbnail.setPreserveRatio(true);

            Label label = new Label(content.getTitle() + " | " + content.getCategory());
            label.setStyle("-fx-font-size: 16px; -fx-font-wight: bold;");

            thumbnail.setOnMouseClicked(e -> {
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

            itemBox.getChildren().addAll(thumbnail, label);
            mainContainer.getChildren().addAll(itemBox,new Separator());
        }
    }


    @FXML
    private void onSearch(ActionEvent event) {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            showSuggestions();
            return;
        }

        mainContainer.getChildren().clear();
        Label title = new Label("Search Results");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        mainContainer.getChildren().add(title);

        Map<String, ArrayList<?>> results = ContentController.getInstance().searchInContentsAndChannels(keyword);
        ArrayList<Content> contents = (ArrayList<Content>) results.get("contents");
        ArrayList<Channel> channels = (ArrayList<Channel>) results.get("channels");


        if (contents.isEmpty() && channels.isEmpty()) {
            mainContainer.getChildren().add(new Label("No results found."));
            return;
        }

        for (Content content : contents) {
            mainContainer.getChildren().addAll(createContentBox(content),new Separator());
        }

        for (Channel channel : channels) {
            mainContainer.getChildren().addAll(createChannelBox(channel), new Separator());
        }
    }
    private HBox createContentBox(Content content) {
        ImageView imageView = new ImageView(new Image("file:" + content.getThumbnail()));
        imageView.setFitHeight(180);
        imageView.setFitWidth(110);
        imageView.setPreserveRatio(true);

        Label label = new Label(content.getTitle());
        label.setStyle("-fx-font-size: 16px; -fx-padding: 10 ; -fx-font-weight: bold;");

        HBox box = new HBox(10, imageView, label);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(10));
        box.setStyle(
                "-fx-padding: 15; " +
                        "-fx-background-color: #ffffff; " +
                        "-fx-background-radius: 8; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 6, 0, 0, 2);"
        );

       imageView.setOnMouseClicked(e -> {
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

        return box;
    }

    private HBox createChannelBox(Channel channel) {
        ImageView imageView = new ImageView();
        try {
            String path = channel.getChannelCover();
            File file = new File(path);
            if (file.exists()) {
                imageView.setImage(new Image(file.toURI().toString()));
            } else {
                System.out.println("File not found: " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

        Label label = new Label(channel.getChannelName());
        label.setStyle("-fx-font-size: 14px; -fx-padding: 10;");

        HBox box = new HBox(10, imageView, label);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setStyle("-fx-padding: 10; -fx-background-color: #e0e0e0;");

        imageView.setOnMouseClicked(e -> openChannelSection(channel));
        label.setOnMouseClicked(e -> openChannelSection(channel));

        return box;
    }

    private void openChannelSection(Channel channel) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/videoplayer/channelSec-view.fxml"));
            Parent root = loader.load();

            ChannelSecPanel controller = loader.getController();
            controller.setViewedChannel(channel);

            Scene scene = new Scene(root);
            ctrlStage.setScene(scene);
            ctrlStage.setTitle(channel.getChannelName());
            ctrlStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
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
