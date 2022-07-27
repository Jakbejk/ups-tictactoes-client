module cz.zcu.kiv.fav.ups {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.zcu.fav.kiv.ups.controller to javafx.fxml;
    exports cz.zcu.fav.kiv.ups.launch;
}