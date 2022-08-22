package cz.zcu.fav.kiv.ups.sp.client.net.response.game;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponsePrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.exception.InvalidResponseException;
import cz.zcu.fav.kiv.ups.sp.client.net.response.IResolver;
import cz.zcu.fav.kiv.ups.sp.client.net.response.game.utils.GameResolverUtils;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.GameController;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author honza on 15.01.2022
 * @version 0.0.0
 * @project temp
 */
public class OpponentReconnectResolver implements IResolver {

    private final static Logger logger = LogManager.getLogger(OpponentReconnectResolver.class);


    @Override
    public void resolve(ResponseMessage responseMessage) throws InvalidResponseException {
        logger.debug("OPPONENT_RECONNECT response: " + responseMessage + " received.");

        GameController gameController = GameResolverUtils.validatedController(logger);
        if (gameController == null) {
            return;
        }

        if (responseMessage.getResponse() == ResponsePrefix.OPPONENT_RECONNECT) {
            gameController.onOpponentReconnect();
        } else throw new InvalidResponseException(responseMessage);
    }
}
