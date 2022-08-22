package cz.zcu.fav.kiv.ups.sp.client.ui.controller;

import cz.zcu.fav.kiv.ups.sp.client.net.main.NetworkManager;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.abstracts.AbstractController;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.manager.ControllerManager;
import cz.zcu.fav.kiv.ups.sp.client.ui.element.text.ColorText;
import cz.zcu.fav.kiv.ups.sp.client.ui.element.textfield.NumberTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author honza on 17.01.2022
 * @version 0.0.0
 * @project temp
 */
public class NetController extends AbstractController implements Initializable {

    @FXML
    private TextField hostNameTextField;
    @FXML
    private NumberTextField portNumberTextField;
    @FXML
    private Label infoLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetWindow();
    }

    @FXML
    public void connect() {
        try {
            NetworkManager.start(hostNameTextField.getText(), portNumberTextField.getValue());
            ControllerManager.changeTo(ControllerType.LOGIN);
        } catch (Exception e) {
            this.infoLabel.setVisible(true);
            this.infoLabel.setManaged(true);
        }
    }

    @Override
    public void resetWindow() {
        this.infoLabel.setGraphic(new ColorText("Client can not connect to server. Please try again later.", Color.RED));
        this.infoLabel.setText(null);
        this.infoLabel.setVisible(false);
        this.infoLabel.setManaged(false);
    }
}
