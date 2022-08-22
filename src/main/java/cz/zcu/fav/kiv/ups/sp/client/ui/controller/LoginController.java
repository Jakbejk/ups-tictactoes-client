package cz.zcu.fav.kiv.ups.sp.client.ui.controller;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.RequestPrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.manager.NetManager;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.abstracts.AbstractController;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.abstracts.ILoginEvent;
import cz.zcu.fav.kiv.ups.sp.client.ui.element.text.ColorText;
import cz.zcu.fav.kiv.ups.sp.client.ui.element.label.animated.WaitingLabel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author honza on 04.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class LoginController extends AbstractController implements ILoginEvent {

    @FXML
    private TextField loginTextField;
    @FXML
    private Label infoLabel;
    @FXML
    private Button submitButton;
    @FXML
    private WaitingLabel lostConnectionLabel;


    private static String username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public static String getUsername() {
        return username;
    }

    @Override
    public void resetWindow() {
        this.lostConnectionLabel.setVisible(false);
        this.lostConnectionLabel.setManaged(false);
    }

    @FXML
    public void login() {
        if (username == null || !"".equals(loginTextField.getText().trim())) {
            username = loginTextField.getText();
        }
        NetManager.sendMessage(RequestPrefix.LOGIN, username);
    }

    @Override
    public void onLoginFailure(ResponseMessage responseMessage) {
        ColorText colorText = new ColorText("Login Error. Try a new name.", Color.RED);
        infoLabel.setGraphic(colorText);
    }

    @Override
    public void onConnectionLost() {
        this.lostConnectionLabel.setVisible(true);
        this.lostConnectionLabel.setManaged(true);
        this.lostConnectionLabel.start();
    }

    @Override
    public void onReconnect() {
        this.lostConnectionLabel.setVisible(false);
        this.lostConnectionLabel.setManaged(false);
        this.lostConnectionLabel.stop();
    }
}
