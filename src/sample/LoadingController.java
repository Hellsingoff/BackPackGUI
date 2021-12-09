package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class LoadingController {
    @FXML
    private Pane logo;

    @FXML
    private void initialize() {
        RotateTransition rt = new RotateTransition(Duration.millis(5000), logo);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();

        // тута удалить лишнее, жадность, перебор
    }
}
