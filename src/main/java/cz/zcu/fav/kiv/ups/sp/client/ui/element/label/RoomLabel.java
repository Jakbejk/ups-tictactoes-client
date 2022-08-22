package cz.zcu.fav.kiv.ups.sp.client.ui.element.label;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.RequestPrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.manager.NetManager;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * @author honza on 09.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class RoomLabel extends Label {

    public RoomLabel(String text) {
        super.setText(text);
        super.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(1))));
        super.setPadding(new Insets(5, 10, 5, 10));
        super.setStyle("-fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");

        super.setOnMouseClicked(e -> NetManager.sendMessage(RequestPrefix.LOBBY_JOIN, super.getText()));
    }
}
