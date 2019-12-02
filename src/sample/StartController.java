package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class StartController {
    @FXML private Slider sliderSize;
    @FXML private Label labelSize;
    private double size = 50;

    public double getSize() {
        return size;
    }

    @FXML
    private void initialize() {
        labelSize.setText(String.valueOf(size));
        sliderSize.setValue(size);
        sliderSize.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelSize.setText(Double.toString(newValue.intValue()));
        });
    }

    public void wikiClick() throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URI("https://ru.wikipedia.org/wiki/%D0%97%D0%B0%D0%B4%D0%B0%D1%87%D0%B0"
                + "_%D0%BE_%D1%80%D1%8E%D0%BA%D0%B7%D0%B0%D0%BA%D0%B5#%D0%97%D0%B0%D0%B4%D0%B0%D1%87%D0%B0"
                + "_%D0%BE_%D0%BD%D0%B5%D0%BE%D0%B3%D1%80%D0%B0%D0%BD%D0%B8%D1%87%D0%B5%D0%BD%D0%BD%D0%BE%D0%BC"
                + "_%D1%80%D1%8E%D0%BA%D0%B7%D0%B0%D0%BA%D0%B5"));
    }

    public void toItems(MouseEvent mouseEvent) {
        size = sliderSize.getValue();
        Main.background.getChildren().set(1, Main.items);
    }
}
