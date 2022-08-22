package cz.zcu.fav.kiv.ups.sp.client.net.response.utils;

import cz.zcu.fav.kiv.ups.sp.client.ui.controller.GameController;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.LobbyController;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.LoginController;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.manager.ControllerManager;
import org.apache.log4j.Logger;

/**
 * @author Hana Hrkalova on 08.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class ResolverUtils {


    public static boolean isLoginController(Logger logger) {
        if (!(ControllerManager.getController() instanceof LoginController)) {
            if (ControllerManager.getController() != null) {
                logger.warn(String.format("LOGIN request can not be proceed on %s controller.", ControllerManager.getController().getClass().getSimpleName()));
            } else {
                logger.warn("LOGIN request can not be proceed on null controller.");
            }
            return false;
        }
        return true;
    }

    public static boolean isLobbyController(Logger logger) {
        if (!(ControllerManager.getController() instanceof LobbyController)) {
            if (ControllerManager.getController() != null) {
                logger.warn(String.format("LOBBY request can not be proceed on %s controller.", ControllerManager.getController().getClass().getSimpleName()));
            } else {
                logger.warn("LOBBY request can not be proceed on null controller.");
            }
            return false;
        }
        return true;
    }

    public static boolean isGameController(Logger logger) {
        if (!(ControllerManager.getController() instanceof GameController)) {
            if (ControllerManager.getController() != null) {
                logger.warn(String.format("GAME request can not be proceed on %s controller.", ControllerManager.getController().getClass().getSimpleName()));
            } else {
                logger.warn("GAME request can not be proceed on null controller.");
            }
            return false;
        }
        return true;
    }
}
