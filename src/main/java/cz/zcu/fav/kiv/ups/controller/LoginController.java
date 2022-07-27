package cz.zcu.fav.kiv.ups.controller;

import cz.zcu.fav.kiv.ups.controller.abstracts.AbstractController;
import cz.zcu.fav.kiv.ups.controller.manager.ControllerManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController extends AbstractController {

    @FXML
    private Label infoLabel;
    @FXML
    private Label reconnectLabel;
    @FXML
    private TextField loginInput;

    @Override
    public void reset() {
        infoLabel.setVisible(false);
        infoLabel.setManaged(false);
        infoLabel.setText(null);
        reconnectLabel.setVisible(false);
        reconnectLabel.setManaged(false);
        reconnectLabel.setText(null);
        loginInput.setText(null);
    }

    @FXML
    public void login() {
        ControllerManager.getInstance().changeTo(ControllerManager.GAME);
    }
}
