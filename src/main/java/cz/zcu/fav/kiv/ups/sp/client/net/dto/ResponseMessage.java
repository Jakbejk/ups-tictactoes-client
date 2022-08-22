package cz.zcu.fav.kiv.ups.sp.client.net.dto;

/**
 * @author Hana Hrkalova on 15.11.2021
 * @version 0.0.0
 * @project ups
 */
public class ResponseMessage {

    private ResponsePrefix response;
    private String message;

    public ResponsePrefix getResponse() {
        return response;
    }

    public void setResponse(ResponsePrefix response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContentMessage() {
        if (response == null || response == ResponsePrefix.UNKNOWN) {
            return message;
        }

        return message.substring(response.getStatus().length());
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "response=" + response +
                ", message='" + message + '\'' +
                '}';
    }
}
