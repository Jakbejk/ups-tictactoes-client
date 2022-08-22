package cz.zcu.fav.kiv.ups.sp.client;

import cz.zcu.fav.kiv.ups.sp.client.net.main.NetworkManager;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.manager.ControllerManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hana Hrkalova on 04.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class App extends Application {

    private final static Logger logger = LogManager.getLogger(App.class);

    @Override
    public void start(Stage stage) {
        ControllerManager.start(stage);
    }


    public static void main(String[] args) {
        launch(args);
    }


}
