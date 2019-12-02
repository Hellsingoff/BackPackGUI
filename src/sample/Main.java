package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    static AnchorPane background, start, items;

    @Override
    public void start(Stage primaryStage) throws Exception {
        background = FXMLLoader.load(getClass().getResource("BG.fxml"));
        start = FXMLLoader.load(getClass().getResource("Start.fxml"));
        items = FXMLLoader.load(getClass().getResource("Items.fxml"));
        primaryStage.setTitle("BackPack!");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(background, 800, 480));
        primaryStage.show();
        background.getChildren().add(start);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
