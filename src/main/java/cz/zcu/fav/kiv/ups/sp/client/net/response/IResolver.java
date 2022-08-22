package cz.zcu.fav.kiv.ups.sp.client.net.response;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponsePrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.exception.InvalidResponseException;

import java.util.Arrays;

/**
 * @author Hana Hrkalova on 06.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public interface IResolver {

    void resolve(ResponseMessage responseMessage) throws InvalidResponseException;
}
