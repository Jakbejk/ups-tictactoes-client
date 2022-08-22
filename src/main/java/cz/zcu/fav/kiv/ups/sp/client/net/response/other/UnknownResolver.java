package cz.zcu.fav.kiv.ups.sp.client.net.response.other;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.exception.InvalidResponseException;
import cz.zcu.fav.kiv.ups.sp.client.net.response.IResolver;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hana Hrkalova on 06.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class UnknownResolver implements IResolver {

    private final static Logger logger = LogManager.getLogger(UnknownResolver.class);

    @Override
    public void resolve(ResponseMessage responseMessage) throws InvalidResponseException {
        logger.debug("UNKNOWN response: " + responseMessage + " received.");
        throw new InvalidResponseException(responseMessage);
    }
}
