package cz.zcu.fav.kiv.ups.sp.client.net.exception;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;

/**
 * @author Hana Hrkalova on 15.11.2021
 * @version 0.0.0
 * @project ups
 */
public class InvalidResponseException extends Exception {

    public static final String MESSAGE = "Invalid response type.";

    private final ResponseMessage responseMessage;

    public InvalidResponseException(ResponseMessage responseMessage) {
        super(MESSAGE);
        this.responseMessage = responseMessage;
    }

    public ResponseMessage getResponseMessage() {
        return this.responseMessage;
    }

}
