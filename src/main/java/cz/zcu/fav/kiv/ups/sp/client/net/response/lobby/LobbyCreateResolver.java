package cz.zcu.fav.kiv.ups.sp.client.net.response.lobby;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponsePrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.exception.InvalidResponseException;
import cz.zcu.fav.kiv.ups.sp.client.net.response.IResolver;
import cz.zcu.fav.kiv.ups.sp.client.net.response.lobby.utils.LobbyResolverUtils;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.LobbyController;
import javafx.application.Platform;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hana Hrkalova on 09.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class LobbyCreateResolver implements IResolver {

    private final static Logger logger = LogManager.getLogger(LobbyCreateResolver.class);

    @Override
    public void resolve(ResponseMessage responseMessage) throws InvalidResponseException {
        logger.debug("LOBBY_CREATE request: " + responseMessage + " received.");

        LobbyController lobbyController = LobbyResolverUtils.validateController(logger);
        if (lobbyController == null) {
            return;
        }

        if (responseMessage.getResponse() == ResponsePrefix.OK) {
            Platform.runLater(() -> lobbyController.onLobbyCreateSuccess(responseMessage));
        } else if (responseMessage.getResponse() == ResponsePrefix.ERROR) {
            Platform.runLater(() -> lobbyController.onLobbyCreateError(responseMessage));
        } else {
            throw new InvalidResponseException(responseMessage);
        }


    }
}
