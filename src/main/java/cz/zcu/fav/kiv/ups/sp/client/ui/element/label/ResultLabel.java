package cz.zcu.fav.kiv.ups.sp.client.ui.element.label;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * @author honza on 10.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class ResultLabel extends Label {

    public ResultLabel(boolean answer) {
        this.setText("  ");
        this.setBackground(new Background(new BackgroundFill(answer ? Color.GREEN : Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
    }

}
