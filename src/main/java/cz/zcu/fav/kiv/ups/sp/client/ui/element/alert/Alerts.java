package cz.zcu.fav.kiv.ups.sp.client.ui.element.alert;

import cz.zcu.fav.kiv.ups.sp.client.ui.controller.manager.ControllerManager;
import javafx.scene.control.Alert;

/**
 * @author honza on 11.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class Alerts {


    public static void showConnectionLostAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText("Connection lost!");
        alert.setContentText("Application will be terminated...");

        alert.initOwner(ControllerManager.getScene().getWindow());
        alert.showAndWait();
    }

    public static void showOpponentConnectionLostAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText("Opponent connection lost!");
        alert.setContentText("You will be returned to lobby...");

        alert.initOwner(ControllerManager.getScene().getWindow());
        alert.showAndWait();
    }

}
