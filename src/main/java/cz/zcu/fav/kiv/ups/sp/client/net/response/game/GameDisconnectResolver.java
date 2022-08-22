package cz.zcu.fav.kiv.ups.sp.client.net.response.game;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponsePrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.exception.InvalidResponseException;
import cz.zcu.fav.kiv.ups.sp.client.net.response.IResolver;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.ControllerType;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.manager.ControllerManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hana Hrkalova on 06.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class GameDisconnectResolver implements IResolver {

    private final static Logger logger = LogManager.getLogger(GameCloseResolver.class);

    @Override
    public void resolve(ResponseMessage responseMessage) throws InvalidResponseException {
        logger.debug("GAME_DISCONNECT request: " + responseMessage + " received.");

        if (responseMessage.getResponse() == ResponsePrefix.OK) {
            ControllerManager.changeTo(ControllerType.LOGIN);
        } else throw new InvalidResponseException(responseMessage);
    }
}
