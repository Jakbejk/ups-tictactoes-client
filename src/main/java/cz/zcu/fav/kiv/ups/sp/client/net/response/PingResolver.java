package cz.zcu.fav.kiv.ups.sp.client.net.response;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hana Hrkalova on 06.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class PingResolver implements IResolver {

    private final static Logger logger = LogManager.getLogger(PingResolver.class);

    @Override
    public void resolve(ResponseMessage responseMessage) {
        logger.debug("PING request: " + responseMessage + " received.");
    }
}
