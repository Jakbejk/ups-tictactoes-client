package cz.zcu.fav.kiv.ups.controller.abstracts;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractController implements Initializable, IAbstractController {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reset();
    }
}
