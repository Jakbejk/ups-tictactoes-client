package cz.zcu.fav.kiv.ups.controller.manager;

import cz.zcu.fav.kiv.ups.controller.abstracts.AbstractController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public final class ControllerManager {

    // ---------------------------------------------------------- CONTROLLER TYPES -------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    public static final ControllerType LOGIN = ControllerType.LOGIN;
    public static final ControllerType GAME = ControllerType.GAME;
    public static final ControllerType SCORE = ControllerType.SCORE;
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    /**
     * Runtime exception signalizing that {@link #instance} was not initialized using method {@link #setInstance(ControllerManager)} before calling {@link #getInstance()} method.
     */
    private static final RuntimeException INSTANCE_NOT_INITIALIZED = new RuntimeException() {
        @Override
        public String getMessage() {
            return "Instance must be initialized before usage.";
        }

        @Override
        public String getLocalizedMessage() {
            return this.getMessage();
        }
    };
    private static ControllerManager instance;
    private final Stage stage;
    private ControllerType current;

    public ControllerManager(Stage stage) {
        this.stage = stage;
    }

    /**
     * Return singleton instance.
     *
     * @return Instance
     */
    public static ControllerManager getInstance() {
        if (instance != null) {
            return instance;
        } else {
            throw INSTANCE_NOT_INITIALIZED;
        }
    }

    /**
     * Initial setter.
     * <br>
     * Instance can be set only one time!!!!!
     * <br>
     * Set instance for future calls of {@link #getInstance()}
     *
     * @param controllerManager Instance
     */
    public static void setInstance(ControllerManager controllerManager) {
        if (controllerManager != null) {
            if (instance == null) {
                instance = controllerManager;
                instance.changeTo(ControllerType.LOGIN);
            } else {
                System.err.println("Controller instance can set be only one time");
            }
        }
    }

    /**
     * @param <T> Generic parameter of parent
     * @return Parent node
     */
    public <T extends Parent> T getParent() {
        return this.current.getParent();
    }

    public <T extends AbstractController> T getController() {
        return this.current.getController();
    }

    /**
     * Change parent and controller according to scene according to controllerType parameter.
     *
     * @param controllerType Controller type to be set as current. Should not be null
     */
    public void changeTo(ControllerType controllerType) {
        if (controllerType != null) {
            controllerType.load();
            if (controllerType.getParent() != null) {
                this.stage.setScene(new Scene(controllerType.getParent()));
            }
            this.current = controllerType;
        }
    }

    public enum ControllerType {
        LOGIN("/cz.zcu.fav.kiv.ups.controller/login-controller.fxml"), GAME("/cz.zcu.fav.kiv.ups.controller/game-controller.fxml"), SCORE("/cz.zcu.fav.kiv.ups.controller/score-controller.fxml");

        private final String fxmlPath;
        private boolean isInit = false;
        private Parent parent;
        private AbstractController abstractController;

        ControllerType(String fxmlPath) {
            this.fxmlPath = fxmlPath;
        }

        public void load() {
            if (!isInit) {
                try {
                    URL resourceUrl = getClass().getResource(this.fxmlPath);
                    if (resourceUrl != null) {
                        FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                        this.parent = fxmlLoader.load();
                        this.abstractController = fxmlLoader.getController();
                        this.isInit = true;
                    } else {
                        System.err.println("FXML resource URL is not declared for " + this + " type");
                        this.isInit = false;
                    }
                } catch (Exception e) {
                    System.err.println("Can not load FXML from resource: \"" + this.fxmlPath + "\".");
                    this.isInit = false;
                }
            }
        }

        @SuppressWarnings("unchecked")
        public <T extends AbstractController> T getController() {
            return (T) this.abstractController;
        }

        @SuppressWarnings("unchecked")
        public <T extends Parent> T getParent() {
            return (T) this.parent;
        }
    }
}
