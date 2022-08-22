package cz.zcu.fav.kiv.ups.sp.client.ui.element.label.animated;

import cz.zcu.fav.kiv.ups.sp.client.ui.element.text.ColorText;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * @author honza on 12.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class WaitingLabel extends Label {

    private final Timeline timeline;

    private final static char DOT = '.';


    protected WaitingLabel(String text) {
        ColorText colorText = new ColorText(String.format("%s%c", text, DOT), Color.RED);
        setGraphic(colorText);

        this.timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    String statusText = colorText.getText();
                    colorText.setText(String.format("%s%c%c%c", text, DOT, DOT, DOT).equals(statusText) ? String.format("%s%c", text, DOT) : statusText + DOT);
                }),
                new KeyFrame(Duration.millis(1000))
        );
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void start() {
        this.timeline.playFromStart();
    }

    public void stop() {
        this.timeline.stop();
    }

}
