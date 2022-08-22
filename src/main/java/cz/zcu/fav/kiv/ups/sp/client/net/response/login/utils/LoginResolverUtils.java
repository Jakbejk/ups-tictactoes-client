package cz.zcu.fav.kiv.ups.sp.client.net.response.login.utils;

import cz.zcu.fav.kiv.ups.sp.client.net.response.utils.ResolverUtils;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.LobbyController;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.manager.ControllerManager;
import org.apache.log4j.Logger;

/**
 * @author Hana Hrkalova on 09.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class LoginResolverUtils {

    /**
     * Validate if Controller is now set on "LoginController" and is not "null".
     * If any problem is detected, method will log with "logger".
     *
     * @param logger Logger.
     * @return Login Controller.
     */
    public static LobbyController validateController(Logger logger) {
        if (!ResolverUtils.isLobbyController(logger)) {
            return null;
        }
        LobbyController lobbyController = ControllerManager.getController();
        if (lobbyController == null) {
            logger.error("Controller is not initialized.");
            return null;
        }
        return lobbyController;
    }
}
