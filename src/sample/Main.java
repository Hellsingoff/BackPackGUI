package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    static AnchorPane background, start, items, loading, end;

    @Override
    public void start(Stage primaryStage) throws IOException {
        background = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("BG.fxml")));
        start = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Start.fxml")));
        items = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddItems.fxml")));
        loading = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Loading.fxml")));
        end = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("End.fxml")));
        primaryStage.setTitle("BackPack!");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(background, 800, 480));
        background.getChildren().add(start);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
