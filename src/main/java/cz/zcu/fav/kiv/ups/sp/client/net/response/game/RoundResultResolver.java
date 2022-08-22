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
 * @author Hana Hrkalova on 10.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class RoundResultResolver implements IResolver {

    private final static Logger logger = LogManager.getLogger(RoundResultResolver.class);

    @Override
    public void resolve(ResponseMessage responseMessage) throws InvalidResponseException {
        logger.debug("ROUND_RESULT request: " + responseMessage + " received.");

        GameController gameController = GameResolverUtils.validatedController(logger);
        if (gameController == null) {
            return;
        }

        if (responseMessage.getResponse() == ResponsePrefix.OK) {
            // everything goes ok, this statement can be ignored
        } else if (responseMessage.getResponse() == ResponsePrefix.ERROR) {
            logger.warn("ROUND_RESULT request ended with ERROR response [Probably server side problem].");
        } else {
            throw new InvalidResponseException(responseMessage);
        }
    }
}
