package cz.zcu.fav.kiv.ups.sp.client.ui.controller.manager;

import cz.zcu.fav.kiv.ups.sp.client.ui.controller.ControllerType;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.abstracts.AbstractController;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author honza on 05.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public final class ControllerManager {

    private final static Logger logger = LogManager.getLogger(ControllerManager.class);

    private final static ControllerType INIT_CONTROLLER_TYPE = ControllerType.NET;

    private final static ObjectProperty<ControllerType> controllerTypeProperty = new SimpleObjectProperty<>(null);


    private final static Map<ControllerType, Scene> controllerTypeSceneMap = new HashMap<>();
    private final static Map<ControllerType, AbstractController> controllerTypeControllerMap = new HashMap<>();

    /**
     * Start app using given stage.
     * Set listener on {@link #changeTo(ControllerType)} method use.
     * Change current {@link AbstractController} state and {@link Parent} state.
     * Must be used just one time at the start, then stage will be changed using method {@link #changeTo(ControllerType)}
     *
     * @param stage to be showed.
     */
    public static void start(Stage stage) {
        if (stage == null) {
            logger.error("Stage can not be null!");
            return;
        }

        stage.setScene(new Scene(new VBox()));

        controllerTypeProperty.addListener((observable, oldValue, newValue) -> {
            if (!controllerTypeSceneMap.containsKey(newValue)) {
                controllerTypeSceneMap.put(newValue, new Scene(newValue.getParent()));
            }

            if (!controllerTypeControllerMap.containsKey(newValue)) {
                controllerTypeControllerMap.put(newValue, newValue.getController());
            }

            Platform.runLater(() -> controllerTypeControllerMap.get(newValue).resetWindow());
            Platform.runLater(() -> stage.setScene(controllerTypeSceneMap.get(newValue)));
        });
        changeTo(INIT_CONTROLLER_TYPE);

        stage.setOnCloseRequest((e) -> {
            Platform.exit();
            System.exit(0);
        });
        Platform.runLater(() -> stage.setTitle("Quiz"));
        Platform.runLater(stage::show);
    }

    /**
     * Change controller and scene according to {@link ControllerType} enum.
     *
     * @param controllerType type of controller to be used.
     */
    public static void changeTo(ControllerType controllerType) {
        ControllerManager.controllerTypeProperty.set(controllerType);
    }

    /**
     * @param <T> generic parameter, used for passing result to any {@link AbstractController} extended variable.
     * @return current controller in use.
     */
    @SuppressWarnings("unchecked")
    public static <T extends AbstractController> T getController() {
        return (T) controllerTypeControllerMap.get(controllerTypeProperty.get());
    }

    public static Scene getScene() {
        return controllerTypeSceneMap.get(controllerTypeProperty.get());
    }

}
