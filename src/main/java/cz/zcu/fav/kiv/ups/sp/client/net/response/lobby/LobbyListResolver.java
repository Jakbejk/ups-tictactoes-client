package cz.zcu.fav.kiv.ups.sp.client.net.response.lobby;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponsePrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.exception.InvalidResponseException;
import cz.zcu.fav.kiv.ups.sp.client.net.response.IResolver;
import cz.zcu.fav.kiv.ups.sp.client.net.response.lobby.utils.LobbyResolverUtils;
import cz.zcu.fav.kiv.ups.sp.client.net.response.utils.ResolverUtils;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.LobbyController;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.manager.ControllerManager;
import javafx.application.Platform;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hana Hrkalova on 06.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class LobbyListResolver implements IResolver {

    private final static Logger logger = LogManager.getLogger(LobbyListResolver.class);

    @Override
    public void resolve(ResponseMessage responseMessage) throws InvalidResponseException {
        logger.debug("LOBBY_LIST request: " + responseMessage + " received.");

        LobbyController lobbyController = LobbyResolverUtils.validateController(logger);
        if (lobbyController == null) {
            return;
        }

        if (responseMessage.getResponse() == ResponsePrefix.LOBBY_LIST) {
            Platform.runLater(() -> lobbyController.onRoomListSuccess(responseMessage));
        } else if (responseMessage.getResponse() == ResponsePrefix.ERROR) {
            Platform.runLater(() -> lobbyController.onRoomListError(responseMessage));
        } else {
            throw new InvalidResponseException(responseMessage);
        }
    }
}
