package cz.zcu.fav.kiv.ups.sp.client.net.response.game;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponsePrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.exception.InvalidResponseException;
import cz.zcu.fav.kiv.ups.sp.client.net.response.IResolver;
import cz.zcu.fav.kiv.ups.sp.client.net.response.game.utils.GameResolverUtils;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.GameController;
import javafx.application.Platform;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hana Hrkalova on 09.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class GameEndResolver implements IResolver {

    private final static Logger logger = LogManager.getLogger(GameEndResolver.class);

    @Override
    public void resolve(ResponseMessage responseMessage) throws InvalidResponseException {
        logger.debug("GAME_END response: " + responseMessage + " received.");

        GameController gameController = GameResolverUtils.validatedController(logger);
        if (gameController == null) {
            return;
        }

        if (responseMessage.getResponse() == ResponsePrefix.END_GAME) {
            Platform.runLater(() -> gameController.onGameEnd(responseMessage));
        } else {
            throw new InvalidResponseException(responseMessage);
        }
    }
}
