package cz.zcu.fav.kiv.ups.sp.client.ui.controller;

import cz.zcu.fav.kiv.ups.sp.client.ui.controller.abstracts.AbstractController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author honza on 05.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public enum ControllerType {

    LOGIN("/fxml/login.fxml"),
    GAME("/fxml/game.fxml"),
    LOBBY("/fxml/lobby.fxml"),
    NET("/fxml/net.fxml");

    private final FXMLLoader fxmlLoader;

    ControllerType(String fxmlPath) {
        this.fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractController> T getController() {
        return (T) this.fxmlLoader.getController();
    }

    public <T extends Parent> T getParent() {
        try {
            return fxmlLoader.load();
        } catch (Exception e) {
            Logger logger = LogManager.getLogger(ControllerType.class);
            logger.error("Can not find path to fxml [\"" + this.fxmlLoader.getClassLoader() + "\"]", e);
            logger.info("Application is shutting down.");

            Platform.exit();
            System.exit(1);
            return null;
        }
    }

}
