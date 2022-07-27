package cz.zcu.fav.kiv.ups.launch;

import cz.zcu.fav.kiv.ups.controller.manager.ControllerManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class TicTacToes extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        ControllerManager.setInstance(new ControllerManager(stage));
        stage.show();
    }
}
