package cz.zcu.fav.kiv.ups.sp.client.net.response.login;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponsePrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.exception.InvalidResponseException;
import cz.zcu.fav.kiv.ups.sp.client.net.response.IResolver;
import cz.zcu.fav.kiv.ups.sp.client.net.response.utils.ResolverUtils;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.ControllerType;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.LoginController;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.manager.ControllerManager;
import javafx.application.Platform;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hana Hrkalova on 06.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class LoginResolver implements IResolver {

    private final static Logger logger = LogManager.getLogger(LoginResolver.class);

    @Override
    public void resolve(ResponseMessage responseMessage) throws InvalidResponseException {
        logger.debug("LOGIN request: " + responseMessage + " received.");

        if (!ResolverUtils.isLoginController(logger)) {
            return;
        }
        LoginController loginController = ControllerManager.getController();
        if (loginController == null) {
            logger.error("Controller is not initialized.");
            return;
        }


        if (responseMessage.getResponse() == ResponsePrefix.OK) {
            Platform.runLater(() -> ControllerManager.changeTo(ControllerType.LOBBY));
        } else if (responseMessage.getResponse() == ResponsePrefix.ERROR) {
            Platform.runLater(() -> loginController.onLoginFailure(responseMessage));
        } else {
            throw new InvalidResponseException(responseMessage);
        }
    }

}
