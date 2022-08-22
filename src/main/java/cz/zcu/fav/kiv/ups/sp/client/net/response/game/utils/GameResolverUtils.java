package cz.zcu.fav.kiv.ups.sp.client.net.response.game.utils;

import cz.zcu.fav.kiv.ups.sp.client.net.response.utils.ResolverUtils;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.GameController;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.manager.ControllerManager;
import org.apache.log4j.Logger;

/**
 * @author honza on 09.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class GameResolverUtils {

    /**
     * Validate if Controller is now set on "GameController" and is not "null".
     * If any problem is detected, method will log with "logger".
     *
     * @param logger Logger.
     * @return Game Controller.
     */
    public static GameController validatedController(Logger logger) {
        if (!ResolverUtils.isGameController(logger)) {
            return null;
        }
        GameController lobbyController = ControllerManager.getController();
        if (lobbyController == null) {
            logger.error("Controller is not initialized.");
            return null;
        }
        return lobbyController;
    }

}
